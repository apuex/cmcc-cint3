package com.github.apuex.cmcc.cint3.utility;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.apuex.cmcc.cint3.*;
import io.netty.util.concurrent.ScheduledFuture;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import io.netty.channel.ChannelHandlerContext;

public class DynAccessHandler extends io.netty.channel.ChannelInboundHandlerAdapter {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ServerHandler.class);

	public DynAccessHandler(Map<String, String> params) {
		this.params = params;
		this.nodeId = Integer.parseInt(params.get("node-id"));
		this.continuously = Boolean.parseBoolean(params.getOrDefault("continuously", "false"));
		this.pollingInterval = Integer.parseInt(params.getOrDefault("polling-interval", "2"));
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info(String.format("[%s] SYN : connection established.", ctx.channel().remoteAddress()));
		SerialNo.initSerialNo(ctx.channel());
		send(ctx, new Login(SerialNo.nextSerialNo(ctx.channel()), params.get("server-user"), params.get("server-passwd")));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info(String.format("[%s] RCV : %s", ctx.channel().remoteAddress(), msg));
		if(msg instanceof Message) {
			Message message = (Message) msg;
			switch(message.PKType) {
			case LOGIN:
				break;
			case LOGIN_ACK:
				if(this.continuously) {
					this.dynAccessTask = ctx.executor().scheduleWithFixedDelay(() -> {
						sendDynAccess(ctx);
					}, 0, this.pollingInterval, TimeUnit.SECONDS);
				} else {
					sendDynAccess(ctx);
				}
				break;
			case LOGOUT:
				break;
			case LOGOUT_ACK:
				ctx.close();
				break;
			case SET_DYN_ACCESS_MODE:
				break;
			case DYN_ACCESS_MODE_ACK:
				if(!this.continuously) {
					send(ctx, new Logout(SerialNo.nextSerialNo(ctx.channel())));
				} else {
					//sendDynAccess(ctx);
				}
				break;
			case SET_ALARM_MODE:
				break;
			case ALARM_MODE_ACK:
				break;
			case SEND_ALARM:
				break;
			case SEND_ALARM_ACK:
				break;
			case SET_POINT:
				break;
			case SET_POINT_ACK:
				break;
			case REQ_MODIFY_PASSWORD:
				break;
			case MODIFY_PASSWORD_ACK:
				break;
			case HEART_BEAT:
				send(ctx, new HeartBeatAck(message.SerialNo));
				break;
			case HEART_BEAT_ACK:
				break;
			case TIME_CHECK:
				break;
			case TIME_CHECK_ACK:
				break;
			case NOTIFY_PROPERTY_MODIFY:
				break;
			case PROPERTY_MODIFY_ACK:
				break;
			default: // Unsupported PKType
				ctx.close();
				break;
			}
		}
		ctx.fireChannelRead(msg);
	}

	private void sendDynAccess(ChannelHandlerContext ctx) {
		List<Integer> l = new LinkedList<Integer>();
		l.add(this.nodeId);
		send(ctx, new SetDynAccessMode(SerialNo.nextSerialNo(ctx.channel()), 1, 2, EnumAccessMode.ASK_ANSWER, 30, new NodeIDArray(l)));
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info(String.format("[%s] RST : connection closed.", ctx.channel().remoteAddress()));
		ctx.fireChannelInactive();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.info(String.format("[%s] ERR : %s", ctx.channel().remoteAddress(), cause));
		ctx.fireExceptionCaught(cause);
	}

	private void send(ChannelHandlerContext ctx, Message out) {
		ctx.writeAndFlush(out);
	}
	private Map<String, String> params;
	private int nodeId;
	private boolean continuously;
	private int pollingInterval;
	ScheduledFuture dynAccessTask;
}

package com.github.apuex.cmcc.cint3.utility;


import ch.qos.logback.classic.Logger;
import com.github.apuex.cmcc.cint3.SetPoint;
import com.github.apuex.cmcc.cint3.*;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SetPointHandler extends io.netty.channel.ChannelInboundHandlerAdapter {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ServerHandler.class);
	private Map<String, String> params;
	private int LSCID;

	public SetPointHandler(Map<String, String> params) {
		this.params = params;
		this.LSCID = Integer.parseInt(params.getOrDefault("lsc-id", "1"));
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
			case LOGIN_ACK: {
				TA ta = new TA();
				ta.Type = EnumType.AI;
				ta.LSCID = this.LSCID;
				ta.Id = 2;
				ta.Value = 0;
				ta.State = EnumState.NOALARM;
				send(ctx, new SetPoint(SerialNo.nextSerialNo(ctx.channel()), ta));
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
				send(ctx, new Logout(SerialNo.nextSerialNo(ctx.channel())));
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
}

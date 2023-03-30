package com.github.apuex.cmcc.cint3.utility;


import java.util.Map;

import org.slf4j.LoggerFactory;

import com.github.apuex.cmcc.cint3.EnumAlarmMode;
import com.github.apuex.cmcc.cint3.HeartBeatAck;
import com.github.apuex.cmcc.cint3.Login;
import com.github.apuex.cmcc.cint3.Message;
import com.github.apuex.cmcc.cint3.NodeIDArray;
import com.github.apuex.cmcc.cint3.SendAlarm;
import com.github.apuex.cmcc.cint3.SendAlarmAck;
import com.github.apuex.cmcc.cint3.SetAlarmMode;

import ch.qos.logback.classic.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class AlarmModeHandler extends io.netty.channel.ChannelInboundHandlerAdapter {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ServerHandler.class);
	private int serialNo = 0;
	private Map<String, String> params;

	public AlarmModeHandler(Map<String, String> params) {
		this.params = params;
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info(String.format("[%s] SYN : connection established.", ctx.channel().remoteAddress()));
		send(ctx, new Login(++serialNo, params.get("server-user"), params.get("server-passwd")));
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
//				List<Integer> l = new LinkedList<Integer>();
//		        l.add(Integer.parseInt(params.get("node-id")));
				send(ctx, new SetAlarmMode(++serialNo, EnumAlarmMode.HINT, new NodeIDArray()));
			}
				break;
			case LOGOUT:
				break;
			case LOGOUT_ACK:
				break;
			case SET_DYN_ACCESS_MODE:
				break;
			case DYN_ACCESS_MODE_ACK:
				break;
			case SET_ALARM_MODE:
				break;
			case ALARM_MODE_ACK:
				break;
			case SEND_ALARM: {
				SendAlarm req = (SendAlarm) message;
				send(ctx, new SendAlarmAck(message.SerialNo, req.Values));
			}
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
		ReferenceCountUtil.release(msg);
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
		logger.info(String.format("[%s] SND : %s", ctx.channel().remoteAddress(), out));
	}
}

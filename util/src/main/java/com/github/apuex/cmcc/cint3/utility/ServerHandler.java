package com.github.apuex.cmcc.cint3.utility;

import ch.qos.logback.classic.Logger;
import com.github.apuex.cmcc.cint3.SetPoint;
import com.github.apuex.cmcc.cint3.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ServerHandler extends io.netty.channel.ChannelInboundHandlerAdapter {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ServerHandler.class);
	@SuppressWarnings("rawtypes")
	public ServerHandler(Map<String, String> params) {
		this.params = params;
		this.LSCID = Integer.parseInt(params.getOrDefault("lsc-id", "1"));
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info(String.format("[%s] SYN : connection established.", ctx.channel().remoteAddress()));
		SerialNo.initSerialNo(ctx.channel());
		heartBeatTask = ctx.executor().scheduleWithFixedDelay(() -> {
			HeartBeat msg = new HeartBeat(SerialNo.nextSerialNo(ctx.channel()));
			ctx.writeAndFlush(msg);
		}, 5, 5, TimeUnit.SECONDS);

		ctx.fireChannelActive();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info(String.format("[%s] RCV : %s", ctx.channel().remoteAddress(), msg));
		if (msg instanceof Message) {
			Message message = (Message) msg;
			switch (message.PKType) {
			case LOGIN:
				send(ctx, new LoginAck(message.SerialNo, EnumRightMode.LEVEL2));
				break;
			case LOGIN_ACK:
				break;
			case LOGOUT:
				send(ctx, new LogoutAck(message.SerialNo, EnumResult.SUCCESS));
				break;
			case LOGOUT_ACK:
				break;
			case SET_DYN_ACCESS_MODE: {
				SetDynAccessMode req = (SetDynAccessMode) message;
				List<TATD> vl = new LinkedList<TATD>();
				TD td = new TD();
				td.Type = EnumType.DI;
				td.LSCID = this.LSCID;
				td.Id = 4;
				td.Value = 0;
				td.State = EnumState.NOALARM;
				vl.add(td);
				TA ta = new TA();
				ta.Type = EnumType.AI;
				ta.LSCID = this.LSCID;
				ta.Id = 5;
				ta.Value = 123.456f;
				ta.State = EnumState.NOALARM;
				vl.add(ta);
				TATDArray Values = new TATDArray(vl);
				send(ctx, new DynAccessModeAck(message.SerialNo, req.TerminalID, req.GroupID, EnumResult.SUCCESS, Values));
			}
				break;
			case DYN_ACCESS_MODE_ACK:
				break;
			case SET_ALARM_MODE:
				send(ctx, new AlarmModeAck(message.SerialNo, ((SetAlarmMode) message).GroupID, EnumResult.SUCCESS));
				break;
			case ALARM_MODE_ACK:
				break;
			case SEND_ALARM:
				break;
			case SEND_ALARM_ACK:
				break;
			case SET_POINT: {
				SetPoint req = (SetPoint) message;
				if(req.Value instanceof TA) {
					TA ta = (TA)req.Value;
					send(ctx, new SetPointAck(message.SerialNo, ta.LSCID, ta.Id, EnumResult.SUCCESS));
				} else if(req.Value instanceof TD) {
					TD td = (TD)req.Value;
					send(ctx, new SetPointAck(message.SerialNo, td.LSCID, td.Id, EnumResult.SUCCESS));
				} else {
					
				}
			}
				break;
			case SET_POINT_ACK:
				break;
			case REQ_MODIFY_PASSWORD: {
				ModifyPassword req = (ModifyPassword) message;
				if(req.OldPassWord != null
						&& req.OldPassWord.equals(params.get("server-passwd"))
						&& req.NewPassWord != null) {
					params.put("server-passwd", req.NewPassWord);
				}
				send(ctx, new ModifyPasswordAck(message.SerialNo, EnumResult.SUCCESS));
			}
				break;
			case MODIFY_PASSWORD_ACK:
				break;
			case HEART_BEAT:
				send(ctx, new HeartBeatAck(message.SerialNo));
				break;
			case HEART_BEAT_ACK:
				break;
			case TIME_CHECK:
				send(ctx, new TimeCheckAck(message.SerialNo, EnumResult.SUCCESS));
				break;
			case TIME_CHECK_ACK:
				break;
			case NOTIFY_PROPERTY_MODIFY:
				break;
			case PROPERTY_MODIFY_ACK:
				send(ctx, new PropertyModifyAck(message.SerialNo));
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
		heartBeatTask.cancel(false);
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
	private int LSCID;
	private ScheduledFuture heartBeatTask;

}

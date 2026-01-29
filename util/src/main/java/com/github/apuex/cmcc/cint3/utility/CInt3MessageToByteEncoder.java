package com.github.apuex.cmcc.cint3.utility;

import ch.qos.logback.classic.Logger;
import com.github.apuex.cmcc.cint3.SetPoint;
import com.github.apuex.cmcc.cint3.TimeCheck;
import com.github.apuex.cmcc.cint3.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CInt3MessageToByteEncoder extends MessageToByteEncoder<Message> {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(CInt3MessageToByteEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		byte[] array = new byte[64*1024];
		ByteBuffer buf = ByteBuffer.wrap(array);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		switch(msg.PKType) {
		case LOGIN:
			LoginCodec.encode(buf, (Login) msg);
			break;
		case LOGIN_ACK:
			LoginAckCodec.encode(buf, (LoginAck) msg);
			break;
		case LOGOUT:
			LogoutCodec.encode(buf, (Logout) msg);
			break;
		case LOGOUT_ACK:
			LogoutAckCodec.encode(buf, (LogoutAck) msg);
			break;
		case SET_DYN_ACCESS_MODE:
			SetDynAccessModeCodec.encode(buf, (SetDynAccessMode) msg);
			break;
		case DYN_ACCESS_MODE_ACK:
			DynAccessModeAckCodec.encode(buf, (DynAccessModeAck) msg);
			break;
		case SET_ALARM_MODE:
			SetAlarmModeCodec.encode(buf, (SetAlarmMode) msg);
			break;
		case ALARM_MODE_ACK:
			AlarmModeAckCodec.encode(buf, (AlarmModeAck) msg);
			break;
		case SEND_ALARM:
			SendAlarmCodec.encode(buf, (SendAlarm) msg);
			break;
		case SEND_ALARM_ACK:
			SendAlarmAckCodec.encode(buf, (SendAlarmAck) msg);
			break;
		case SET_POINT:
			SetPointCodec.encode(buf, (SetPoint) msg);
			break;
		case SET_POINT_ACK:
			SetPointAckCodec.encode(buf, (SetPointAck) msg);
			break;
		case REQ_MODIFY_PASSWORD:
			ModifyPasswordCodec.encode(buf, (ModifyPassword) msg);
			break;
		case MODIFY_PASSWORD_ACK:
			ModifyPasswordAckCodec.encode(buf, (ModifyPasswordAck) msg);
			break;
		case HEART_BEAT:
			HeartBeatCodec.encode(buf, (HeartBeat) msg);
			break;
		case HEART_BEAT_ACK:
			HeartBeatAckCodec.encode(buf, (HeartBeatAck) msg);
			break;
		case TIME_CHECK:
			TimeCheckCodec.encode(buf, (TimeCheck) msg);
			break;
		case TIME_CHECK_ACK:
			TimeCheckAckCodec.encode(buf, (TimeCheckAck) msg);
			break;
		case NOTIFY_PROPERTY_MODIFY:
			NotifyPropertyModifyCodec.encode(buf, (NotifyPropertyModify) msg);
			break;
		case PROPERTY_MODIFY_ACK:
			PropertyModifyAckCodec.encode(buf, (PropertyModifyAck) msg);
			break;
		default: // Unsupported PKType
			ctx.close();
			break;
		}
		logBytesSent(ctx, array, buf.position());
		logger.info(String.format("[%s] SND : %s", ctx.channel().remoteAddress(), msg));
		out.writeBytes(array, 0, buf.position());
	}

	private static void logBytesSent(ChannelHandlerContext ctx, byte[] array, int size) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("[%s] ENC : bytes[%d] = { ", ctx.channel().remoteAddress(), size));
		for(int i = 0; i != size; ++i) {
			sb.append(String.format("%02X ", 0xff & array[i]));
		}
		sb.append("}");
		logger.info(sb.toString());
	}

	public CInt3MessageToByteEncoder() {
		AlarmModeAckCodec = new AlarmModeAckCodec();
		DynAccessModeAckCodec = new DynAccessModeAckCodec();
		HeartBeatAckCodec = new HeartBeatAckCodec();
		HeartBeatCodec = new HeartBeatCodec();
		LoginAckCodec = new LoginAckCodec();
		LoginCodec = new LoginCodec();
		LogoutAckCodec = new LogoutAckCodec();
		LogoutCodec = new LogoutCodec();
		ModifyPasswordAckCodec = new ModifyPasswordAckCodec();
		ModifyPasswordCodec = new ModifyPasswordCodec();
		SendAlarmAckCodec = new SendAlarmAckCodec();
		SendAlarmCodec = new SendAlarmCodec();
		SetAlarmModeCodec = new SetAlarmModeCodec();
		SetDynAccessModeCodec = new SetDynAccessModeCodec();
		SetPointAckCodec = new SetPointAckCodec();
		SetPointCodec = new SetPointCodec();
		PropertyModifyAckCodec = new PropertyModifyAckCodec();
		NotifyPropertyModifyCodec = new NotifyPropertyModifyCodec();
		TimeCheckAckCodec = new TimeCheckAckCodec();
		TimeCheckCodec = new TimeCheckCodec();
	}

	private AlarmModeAckCodec AlarmModeAckCodec;
	private DynAccessModeAckCodec DynAccessModeAckCodec;
	private HeartBeatAckCodec HeartBeatAckCodec;
	private HeartBeatCodec HeartBeatCodec;
	private LoginAckCodec LoginAckCodec;
	private LoginCodec LoginCodec;
	private LogoutAckCodec LogoutAckCodec;
	private LogoutCodec LogoutCodec;
	private ModifyPasswordAckCodec ModifyPasswordAckCodec;
	private ModifyPasswordCodec ModifyPasswordCodec;
	private SendAlarmAckCodec SendAlarmAckCodec;
	private SendAlarmCodec SendAlarmCodec;
	private SetAlarmModeCodec SetAlarmModeCodec;
	private SetDynAccessModeCodec SetDynAccessModeCodec;
	private SetPointAckCodec SetPointAckCodec;
	private SetPointCodec SetPointCodec;
	private PropertyModifyAckCodec PropertyModifyAckCodec;
	private NotifyPropertyModifyCodec NotifyPropertyModifyCodec;
	private TimeCheckAckCodec TimeCheckAckCodec;
	private TimeCheckCodec TimeCheckCodec;
}

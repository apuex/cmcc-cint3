package com.github.apuex.cmcc.cint3.utility;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.github.apuex.cmcc.cint3.AlarmModeAckCodec;
import com.github.apuex.cmcc.cint3.DynAccessModeAckCodec;
import com.github.apuex.cmcc.cint3.EnumPKType;
import com.github.apuex.cmcc.cint3.HeartBeatAckCodec;
import com.github.apuex.cmcc.cint3.HeartBeatCodec;
import com.github.apuex.cmcc.cint3.LoginAckCodec;
import com.github.apuex.cmcc.cint3.LoginCodec;
import com.github.apuex.cmcc.cint3.LogoutAckCodec;
import com.github.apuex.cmcc.cint3.LogoutCodec;
import com.github.apuex.cmcc.cint3.Message;
import com.github.apuex.cmcc.cint3.ModifyPasswordAckCodec;
import com.github.apuex.cmcc.cint3.ModifyPasswordCodec;
import com.github.apuex.cmcc.cint3.NotifyPropertyModifyCodec;
import com.github.apuex.cmcc.cint3.PropertyModifyAckCodec;
import com.github.apuex.cmcc.cint3.SendAlarmAckCodec;
import com.github.apuex.cmcc.cint3.SendAlarmCodec;
import com.github.apuex.cmcc.cint3.SetAlarmModeCodec;
import com.github.apuex.cmcc.cint3.SetDynAccessModeCodec;
import com.github.apuex.cmcc.cint3.SetPointAckCodec;
import com.github.apuex.cmcc.cint3.SetPointCodec;
import com.github.apuex.cmcc.cint3.TimeCheckAckCodec;
import com.github.apuex.cmcc.cint3.TimeCheckCodec;

import ch.qos.logback.classic.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ByteToCInt3MessageDecoder extends ByteToMessageDecoder {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ByteToCInt3MessageDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 8)
			return; // frame header
		byte[] array = new byte[in.readableBytes()];
		in.getBytes(0, array, 0, array.length);
		ByteBuffer buf = ByteBuffer.wrap(array);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		final int header = buf.getInt();
		if (Message.MESSAGE_HEADER != header) { // message header error
			System.out.printf("[%s] RCV : Message.MESSAGE_HEADER != 0x%04X\n", ctx.channel().remoteAddress(), header);
			ctx.close();
			return;
		}

		final int length = buf.getInt();
		if (in.readableBytes() < length)
			return; // frame header + length

		@SuppressWarnings("unused")
		final int serialNo = buf.getInt();

		final EnumPKType PKType = EnumPKType.fromValue(buf.getInt());

		buf.position(0);
		switch (PKType) {
		case LOGIN:
			out.add(LoginCodec.decode(buf));
			break;
		case LOGIN_ACK:
			out.add(LoginAckCodec.decode(buf));
			break;
		case LOGOUT:
			out.add(LogoutCodec.decode(buf));
			break;
		case LOGOUT_ACK:
			out.add(LogoutAckCodec.decode(buf));
			break;
		case SET_DYN_ACCESS_MODE:
			out.add(SetDynAccessModeCodec.decode(buf));
			break;
		case DYN_ACCESS_MODE_ACK:
			out.add(DynAccessModeAckCodec.decode(buf));
			break;
		case SET_ALARM_MODE:
			out.add(SetAlarmModeCodec.decode(buf));
			break;
		case ALARM_MODE_ACK:
			out.add(AlarmModeAckCodec.decode(buf));
			break;
		case SEND_ALARM:
			out.add(SendAlarmCodec.decode(buf));
			break;
		case SEND_ALARM_ACK:
			out.add(SendAlarmAckCodec.decode(buf));
			break;
		case SET_POINT:
			out.add(SetPointCodec.decode(buf));
			break;
		case SET_POINT_ACK:
			out.add(SetPointAckCodec.decode(buf));
			break;
		case REQ_MODIFY_PASSWORD:
			out.add(ModifyPasswordCodec.decode(buf));
			break;
		case MODIFY_PASSWORD_ACK:
			out.add(ModifyPasswordAckCodec.decode(buf));
			break;
		case HEART_BEAT:
			out.add(HeartBeatCodec.decode(buf));
			break;
		case HEART_BEAT_ACK:
			out.add(HeartBeatAckCodec.decode(buf));
			break;
		case TIME_CHECK:
			out.add(TimeCheckCodec.decode(buf));
			break;
		case TIME_CHECK_ACK:
			out.add(TimeCheckAckCodec.decode(buf));
			break;
		case NOTIFY_PROPERTY_MODIFY:
			out.add(NotifyPropertyModifyCodec.decode(buf));
			break;
		case PROPERTY_MODIFY_ACK:
			out.add(PropertyModifyAckCodec.decode(buf));
			break;
		default: // Unsupported PKType
			ctx.close();
			break;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("[%s] DEC : bytes[%d] = { ", ctx.channel().remoteAddress(), buf.position()));
      	for(int i = 0; i != buf.position(); ++i) {
          sb.append(String.format("%02X ", 0xff & array[i]));
      	}
      	sb.append("}");
      	logger.info(sb.toString());
		in.skipBytes(buf.position());
	}

	public ByteToCInt3MessageDecoder() {
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

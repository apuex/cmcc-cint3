package com.github.apuex.cmcc.cint3.utility;

import ch.qos.logback.classic.Logger;
import com.github.apuex.cmcc.cint3.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ByteToCInt3MessageDecoder extends ByteToMessageDecoder {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ByteToCInt3MessageDecoder.class);
	public final int MAX_MESSAGE_SIZE;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		while (do_decode(ctx, in, out));
	}
	private boolean do_decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		seekMessageHeader(in);
		final int offset = in.readerIndex();
		final int readableBytes = in.readableBytes();
		if (readableBytes < 8) {
			if(logger.isDebugEnabled()) {
				logger.debug(String.format("[%s] RCV : input readable bytes is %d < 8(frame header size)\n"
						, ctx.channel().remoteAddress()
						, in.readableBytes()));
			}
			return false; // frame header
		}
		final int header = in.getIntLE(offset);
		if (Message.MESSAGE_HEADER != header) { // NOT Possible : message header error
			if(logger.isDebugEnabled()) {
				logger.debug(String.format("[%s] RCV : Message.MESSAGE_HEADER != 0x%08X\n"
						, ctx.channel().remoteAddress()
						, header));
			}
			ctx.close();
			return false;
		}

		final int length = in.getIntLE(offset + 4);
		if(MAX_MESSAGE_SIZE < length) {
			in.clear();
			ctx.close();
			return false;
		}
		if (readableBytes < length) {
			if(logger.isDebugEnabled()) {
				logger.debug(String.format("[%s] RCV : input readable bytes is %d < %d(frame size) \n"
						, ctx.channel().remoteAddress()
						, readableBytes
						, length));
			}
			return false; // frame header + length
		}

		final byte[] array = new byte[length];
		//offset must be used, or call in.discardReadBytes() after decode success.
		//in.discardReadBytes();
		in.getBytes(offset, array, 0, array.length);
		ByteBuffer buf = ByteBuffer.wrap(array);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		if(logger.isDebugEnabled()) logBytesReceived(ctx, array, length);

		try {
			buf.position(8);
			@SuppressWarnings("unused") final int serialNo = buf.getInt();

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
		} catch (Throwable t) {
			logger.error("decoder error", t);
			t.printStackTrace();
		}
		in.skipBytes(buf.position());
		return true;
	}

	public static void seekMessageHeader(ByteBuf in) {
		final int offset = in.readerIndex();
		final int size = in.readableBytes();
		for(int i = 0; i < size; ++i) {
			if(4 > (size - i)) return;
			int header = in.getIntLE(offset + i);
			if(Message.MESSAGE_HEADER == header) {
				return;
			} else {
				System.out.printf("[%s] skipped 1 byte\r\n", currentTimestamp());
				in.skipBytes(1);
			}
		}
	}

	private static void logBytesReceived(ChannelHandlerContext ctx, byte[] array, int size) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("[%s] DEC : bytes[%d] = { ", ctx.channel().remoteAddress(), size));
		for(int i = 0; i != size; ++i) {
			sb.append(String.format("%02X ", 0xff & array[i]));
		}
		sb.append("}");
		logger.info(sb.toString());
	}

	private static String currentTimestamp() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		return timstampFormat.format(c.getTime());
	}

	public ByteToCInt3MessageDecoder(int maxMessageSize) {
		MAX_MESSAGE_SIZE = maxMessageSize;
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
	static public final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	static private SimpleDateFormat timstampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT, Locale.CHINA);
}

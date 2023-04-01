package com.github.apuex.cmcc.cint3.utility;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class SerialNo {
	public static final AttributeKey<Integer> MESSAGE_SERIAL_NO_KEY = AttributeKey.valueOf("MESSAGE_SERIAL_NO_KEY");
	public static final AttributeKey<Integer> ALARM_SERIAL_NO_KEY = AttributeKey.valueOf("ALARM_SERIAL_NO_KEY");
	
	public static void initSerialNo(Channel c) {
		c.attr(MESSAGE_SERIAL_NO_KEY).set(0);
	}
	
	public static int nextSerialNo(Channel c) {
		final int serialNo = c.attr(MESSAGE_SERIAL_NO_KEY).get() + 1;
		c.attr(MESSAGE_SERIAL_NO_KEY).set(serialNo);
		return serialNo;
	}

}

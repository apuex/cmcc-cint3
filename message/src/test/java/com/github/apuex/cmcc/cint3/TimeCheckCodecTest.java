/*
 * Copyright (c) 2021-2023 WINCOM.
 * Copyright (c) 2021-2023 Wangxy <xtwxy@hotmail.com>
 *
 * All rights reserved. 
 *
 * This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License 2.0.
 *
 * Contributors:
 *   Wangxy - initial implementation and documentation.
*/

package com.github.apuex.cmcc.cint3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.Assert;
import org.junit.Test;

public class TimeCheckCodecTest {
	@Test
	public void testEncodeTA() {
		byte[] expected = new byte[]
			{ (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x19, (byte)0x00, (byte)0x00, (byte)0x00
			, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x15, (byte)0x05, (byte)0x00, (byte)0x00
			, (byte)0xCC, (byte)0x07, (byte)0x07, (byte)0x0E, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x52
			, (byte)0xF4
			};
		TTime ta = new TTime();
		ta.Years = 1996;
		ta.Month = 7;
		ta.Day = 14;
		ta.Hour = 0;
		ta.Minute = 0;
		ta.Second = 0;
		TimeCheck v = new TimeCheck(2, ta);
		byte[] actual = new byte[25];
		ByteBuffer buf = ByteBuffer.wrap(actual);
		buf.order(ByteOrder.LITTLE_ENDIAN);

		TimeCheckCodec codec = new TimeCheckCodec();
		codec.encode(buf, v);
		v.Length = buf.position();

		System.out.printf("actual[%d] = [ ", v.Length);
		for (int i = 0; i != v.Length; ++i) {
			System.out.printf("%02X ", 0xff & actual[i]);
		}
		System.out.printf("]\n");

		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void testDecodeTA() {
		byte[] input = new byte[]
			{ (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x19, (byte)0x00, (byte)0x00, (byte)0x00
			, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x15, (byte)0x05, (byte)0x00, (byte)0x00
			, (byte)0xCC, (byte)0x07, (byte)0x07, (byte)0x0E, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x52
			, (byte)0xF4
			};
		TTime ta = new TTime();
		ta.Years = 1996;
		ta.Month = 7;
		ta.Day = 14;
		ta.Hour = 0;
		ta.Minute = 0;
		ta.Second = 0;
		TimeCheck expected = new TimeCheck(2, ta);
		expected.Length = input.length;
		expected.CRC16 = (short) 0xF452;
		ByteBuffer buf = ByteBuffer.wrap(input);
		buf.order(ByteOrder.LITTLE_ENDIAN);

		TimeCheckCodec codec = new TimeCheckCodec();
		TimeCheck actual = codec.decode(buf);

		Assert.assertEquals(expected, actual);
	}

}

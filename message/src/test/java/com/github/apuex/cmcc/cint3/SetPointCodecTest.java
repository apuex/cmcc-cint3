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

public class SetPointCodecTest {
	@Test
	public void testEncodeTA() {
		byte[] expected = new byte[]
				{ (byte) 0x5A, (byte) 0x6B, (byte) 0x7C, (byte) 0x7E, (byte) 0x26, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xE9, (byte) 0x03,
				(byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0E,
				(byte) 0x65
				};
		TA ta = new TA();
		ta.Type = EnumType.AI;
		ta.LSCID = 1;
		ta.Id = 2;
		ta.Value = 0;
		ta.State = EnumState.NOALARM;
		SetPoint v = new SetPoint(2, ta);
		byte[] actual = new byte[38];
		ByteBuffer buf = ByteBuffer.wrap(actual);
		buf.order(ByteOrder.LITTLE_ENDIAN);

		SetPointCodec codec = new SetPointCodec();
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
		byte[] input = new byte[] { (byte) 0x5A, (byte) 0x6B, (byte) 0x7C, (byte) 0x7E, (byte) 0x26, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xE9, (byte) 0x03,
				(byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0E,
				(byte) 0x65 };
		TA ta = new TA();
		ta.Type = EnumType.AI;
		ta.LSCID = 1;
		ta.Id = 2;
		ta.Value = 0;
		ta.State = EnumState.NOALARM;
		SetPoint expected = new SetPoint(2, ta);
		expected.Length = input.length;
		expected.CRC16 = (short) 0x650E;
		ByteBuffer buf = ByteBuffer.wrap(input);
		buf.order(ByteOrder.LITTLE_ENDIAN);

		SetPointCodec codec = new SetPointCodec();
		SetPoint actual = codec.decode(buf);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testEncodeTD() {
		byte[] expected = new byte[]
				{ (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x23, (byte)0x00, (byte)0x00, (byte)0x00
				, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xE9, (byte)0x03, (byte)0x00, (byte)0x00
				, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
				, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00
				, (byte)0x00, (byte)0x80, (byte)0x40
				};
		TD td = new TD();
		td.Type = EnumType.DI;
		td.LSCID = 1;
		td.Id = 2;
		td.Value = 0;
		td.State = EnumState.NOALARM;
		SetPoint v = new SetPoint(2, td);
		byte[] actual = new byte[35];
		ByteBuffer buf = ByteBuffer.wrap(actual);
		buf.order(ByteOrder.LITTLE_ENDIAN);

		SetPointCodec codec = new SetPointCodec();
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
	public void testDecodeTD() {
		byte[] input = new byte[]
				{ (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x23, (byte)0x00, (byte)0x00, (byte)0x00
						, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xE9, (byte)0x03, (byte)0x00, (byte)0x00
						, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
						, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00
						, (byte)0x00, (byte)0x80, (byte)0x40
						};
		TD td = new TD();
		td.Type = EnumType.DI;
		td.LSCID = 1;
		td.Id = 2;
		td.Value = 0;
		td.State = EnumState.NOALARM;
		SetPoint expected = new SetPoint(2, td);
		expected.Length = input.length;
		expected.CRC16 = (short) 0x4080;
		ByteBuffer buf = ByteBuffer.wrap(input);
		buf.order(ByteOrder.LITTLE_ENDIAN);

		SetPointCodec codec = new SetPointCodec();
		SetPoint actual = codec.decode(buf);

		Assert.assertEquals(expected, actual);
	}
}

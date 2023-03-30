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
import java.nio.charset.Charset;

/**
 * AI、DI值的结构的父类
 */
public class Util {
	static public final Charset	charset							= Charset.forName("GB18030");
	static public final byte		padding							= 0x20;
	static public final short		CRC16_INITIAL_VALUE	= (short) 0xFFFF;
	// static public final short CRC16_POLYNOMIAL = (short)0xA001;
	// static public final short CRC16_INITIAL_VALUE = (short)0x0000;
	static public final short		CRC16_POLYNOMIAL		= (short) 0x1021;
	static public final boolean	CRC16_REVERSE_BITS	= false;

	static public void encodeString(ByteBuffer buf, String s, int maxLength) {
		byte[] ba = s.getBytes(charset);
		if (ba.length <= maxLength) {
			buf.put(ba);
			for (int i = 0; i != (maxLength - ba.length); ++i) {
				buf.put(padding);
			}
		} else {
			throw new IllegalArgumentException(String.format("String '%s' is too long.", s));
		}
	}

	static public String decodeString(ByteBuffer buf, int maxLength) {
		byte[] ba = new byte[maxLength];
		buf.get(ba);
		String s = new String(ba, charset);
		return s.trim();
	}

	/* CRC16 checksum */
	public static byte reverseBits(byte b) {
		return (byte) ((b & 0x01) << 7 | (b & 0x02) << 5 | (b & 0x04) << 3 | (b & 0x08) << 1 | (b & 0x10) >>> 1
		    | (b & 0x20) >>> 3 | (b & 0x40) >>> 5 | (b & 0x80) >>> 7);
	}

	public static short newCRC16(short crc16, boolean rev, byte b) {
		short bp = rev ? reverseBits(b) : b;
		return (short) (crc16 ^ (bp << 8));
	}

	public static short CRC16(short poly, boolean rev, short crc16, byte b) {
		short crc16p = newCRC16(crc16, rev, b);
		for (int i = 0; i < 8; ++i) {
			crc16p = (0x0000 != (crc16p & 0x8000)) ? (short) ((crc16p << 1) ^ poly) : (short) (crc16p << 1);
		}
		return crc16p;
	}

	public static short CRC16(short crc16, byte b) {
		return CRC16(CRC16_POLYNOMIAL, CRC16_REVERSE_BITS, crc16, b);
	}

	public static short CRC16(byte[] ba, int begin, int end) {
		short crc16 = CRC16_INITIAL_VALUE;
		for (int i = begin; i != end; ++i) {
			crc16 = CRC16(crc16, ba[i]);
		}
		return crc16;
	}

}

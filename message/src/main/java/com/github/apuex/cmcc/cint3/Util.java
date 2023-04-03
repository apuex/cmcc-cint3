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
	static public final Charset	charset							= Charset.forName("UTF-8");
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
			final int paddingLength = (maxLength - ba.length);
			for (int i = 0; i < paddingLength; ++i) {
				buf.put(padding);
			}
		} else {
			buf.put(ba, 0, maxLength);
		}
	}

	static public String decodeString(ByteBuffer buf, int maxLength) {
		byte[] ba = new byte[maxLength];
		buf.get(ba);
		String s = new String(ba, charset);
		return s.trim();
	}

	static public void encodeStringNTS(ByteBuffer buf, String s, int maxLength) {
		byte[] ba = s.getBytes(charset);
		if (ba.length < maxLength) {
			buf.put(ba);
			final int paddingLength = (maxLength - ba.length);
			if (paddingLength > 0) {
				for (int i = 0; i < paddingLength - 1; ++i) {
					buf.put(padding);
				}
			}
		} else {
			buf.put(ba, 0, maxLength - 1);
		}
		buf.put((byte) 0);
	}

	static public String decodeStringUnTrimmed(ByteBuffer buf, int maxLength) {
		byte[] ba = new byte[maxLength];
		buf.get(ba);
		String s = new String(ba, charset);
		return s;
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

	/*
	public static short CRC16(byte[] ba, int begin, int end) {
		short crc16 = CRC16_INITIAL_VALUE;
		for (int i = begin; i != end; ++i) {
			crc16 = CRC16(crc16, ba[i]);
		}
		return crc16;
	}
	*/
	
	public static short crc16(int poly, int initial, byte[] ba, int begin, int end) {
		int crc = 0xFFFF & initial;
		for(int i = begin; i < end; ++i) {
			crc = 0xFFFF & (crc ^ (0xFF & ba[i]));
			for(int j = 0; j < 8; ++j) {
				int flag = crc & 1;
				crc = crc >>> 1;
				if(1 == flag) {
					crc = 0xFFFF & (crc ^ poly);
				}
			}
		}
		return (short)(0xFFFF & crc);
	}
	
	public static short CRC16(byte[] ba, int begin, int end) {
		return crc16(0xA001, 0xFFFF, ba, begin, end);
	}
	/*
crc16Update :: Word16 -> Bool -> Word16 -> Word8 -> Word16
crc16Update poly inverse initial byte = foldl iter crc0 [1..8]
    where crc0           = xor initial (fromIntegral byte)
          iter     crc _ = let flag = (crc .&. 0x0001);
                               crc1 = (shiftR crc 1)
                               in if (1 == flag) then xor crc1 poly else crc1

crc16 :: Word16 -> Bool -> Word16 -> [Word8] -> Word16
crc16 poly inverse initial bs = let newCRC16 = B.foldl (crc16Update poly inverse) initial . B.pack $ bs
                                in (shiftL newCRC16 8) .|. (shiftR newCRC16 8)  

addCRC16 :: BaseFrame -> BaseFrame
addCRC16 f = f { frameCRC16 = crc16 0xA001 False 0xFFFF bytes }
    where bytes = BL.unpack
                    $ BL.drop 4
                    $ BL.take (fromIntegral $ (frameLength f) - 2)
                    $ encode f

	 */
	/*
	static short CRC16(byte[] data, int begin, int end) // CRC校验码
	{
		int flag = 0;
		int crc = 0xFFFF;
		for (int i = 0; i < data.length; i++) {
			crc = (crc ^ (0xFF & data[i]));
			for (int j = 0; j < 8; j++) {
				flag = (crc & 1);
				crc = (crc >>> 1);
				if (flag == 1)
					crc = (0xFFFF & (crc ^ 0xA001));
			}
		}

		return (short) (crc & 0xFFFF);
	} 
	*/  
}

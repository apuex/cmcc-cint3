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

import org.junit.Assert;
import org.junit.Test;

public class CRC16Test {

	@Test
	public void testReverseBits() {
		Assert.assertEquals((byte)(0x1 << 0), Util.reverseBits((byte)((((byte)0x80) & 0xff) >>> 0)));
		Assert.assertEquals((byte)(0x1 << 1), Util.reverseBits((byte)((((byte)0x80) & 0xff) >>> 1)));
		Assert.assertEquals((byte)(0x1 << 2), Util.reverseBits((byte)((((byte)0x80) & 0xff) >>> 2)));
		Assert.assertEquals((byte)(0x1 << 3), Util.reverseBits((byte)((((byte)0x80) & 0xff) >>> 3)));
		Assert.assertEquals((byte)(0x1 << 4), Util.reverseBits((byte)((((byte)0x80) & 0xff) >>> 4)));
		Assert.assertEquals((byte)(0x1 << 5), Util.reverseBits((byte)((((byte)0x80) & 0xff) >>> 5)));
		Assert.assertEquals((byte)(0x1 << 6), Util.reverseBits((byte)((((byte)0x80) & 0xff) >>> 6)));
		Assert.assertEquals((byte)(0x1 << 7), Util.reverseBits((byte)((((byte)0x80) & 0xff) >>> 7)));
	}
	
	@Test
    public void testCalcCRC16() {
        final byte[] input = new byte[] { 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39 };
      	final short crc16 = Util.CRC16(input, 0, input.length);
        //final short crc16 = CRC16(input);
      	System.out.printf("CRC16 = 0x%04X\n", crc16);
      	Assert.assertEquals(0x29B1, crc16);
    }

    @Test
    public void testNfjdNonStandardCRC16() {
        final byte[] input = new byte[]
		        { (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x3A, (byte)0x00, (byte)0x00, (byte)0x00
		        , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x65, (byte)0x00, (byte)0x00, (byte)0x00
		        , (byte)0x75, (byte)0x73, (byte)0x65, (byte)0x72, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
		        , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
		        , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x31, (byte)0x32, (byte)0x33, (byte)0x34
		        , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
		        , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
		        , (byte)0x6C, (byte)0x8A
		        };
		/*
		{ 0x5A, 0x6B, 0x7C, 0x7E, 0x16, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x66, 0x00, 0x00
		, 0x00, 0x02, 0x00, 0x00, 0x00
		, (byte)0xB7, 0x24
		};
		*/

        /*
        		{ (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x12, (byte)0x00, (byte)0x00, (byte)0x00
        		, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xB1, (byte)0x04, (byte)0x00, (byte)0x00
        		, (byte)0x3A, (byte)0xC7
        		};
        		*/
        final short crc16 = Util.CRC16(input, 4, input.length - 2);
      	System.out.printf("CRC16 = 0x%04X\n", crc16);
      	Assert.assertEquals(0x24B7, crc16);
    }

}


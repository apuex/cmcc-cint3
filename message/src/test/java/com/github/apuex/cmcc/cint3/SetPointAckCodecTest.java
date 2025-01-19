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

public class SetPointAckCodecTest {
    @Test
    public void testEncode() {
        byte[] expected = new byte[] 
            { (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x1E, (byte)0x00, (byte)0x00, (byte)0x00
			, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xEA, (byte)0x03, (byte)0x00, (byte)0x00
			, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
			, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x6A, (byte)0xFF
            };
        
        SetPointAck v = new SetPointAck(1, 2, EnumResult.SUCCESS);
      	byte[] actual = new byte[30];
      	ByteBuffer buf = ByteBuffer.wrap(actual);
      	buf.order(ByteOrder.LITTLE_ENDIAN);

      	SetPointAckCodec codec = new SetPointAckCodec();
      	codec.encode(buf, v);
      	v.Length = buf.position();

        System.out.printf("actual[%d] = [ ", v.Length);
      	for(int i = 0; i != v.Length; ++i) {
      		System.out.printf("%02X ", 0xff & actual[i]);
      	}
        System.out.printf("]\n");

      	Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testDecode() {
        byte[] input = new byte[]
			{ (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x1E, (byte)0x00, (byte)0x00, (byte)0x00
			, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xEA, (byte)0x03, (byte)0x00, (byte)0x00
			, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
			, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x6A, (byte)0xFF
			};

        SetPointAck expected = new SetPointAck(1, 2, EnumResult.SUCCESS);
      	expected.Length = input.length;
      	expected.CRC16 = (short)0xFF6A;
      	ByteBuffer buf = ByteBuffer.wrap(input);
      	buf.order(ByteOrder.LITTLE_ENDIAN);
      	SetPointAckCodec codec = new SetPointAckCodec();
        SetPointAck actual = codec.decode(buf);

      	Assert.assertEquals(expected, actual);
    }
}


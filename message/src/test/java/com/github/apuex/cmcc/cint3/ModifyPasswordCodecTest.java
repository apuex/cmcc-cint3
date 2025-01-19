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

public class ModifyPasswordCodecTest {
    @Test
    public void testEncode() {
        byte[] expected = new byte[] 
            { (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x4E, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x4D, (byte)0x04, (byte)0x00, (byte)0x00
            , (byte)0x75, (byte)0x73, (byte)0x65, (byte)0x72, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
            , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
            , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x4F, (byte)0x6C, (byte)0x64, (byte)0x50
            , (byte)0x61, (byte)0x73, (byte)0x73, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
            , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
            , (byte)0x4E, (byte)0x65, (byte)0x77, (byte)0x50, (byte)0x61, (byte)0x73, (byte)0x73, (byte)0x20
            , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
            , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x8C, (byte)0x20
            };
        ModifyPassword v = new ModifyPassword(2, "user", "OldPass", "NewPass");
      	byte[] actual = new byte[78];
      	ByteBuffer buf = ByteBuffer.wrap(actual);
      	buf.order(ByteOrder.LITTLE_ENDIAN);

      	ModifyPasswordCodec codec = new ModifyPasswordCodec();
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
        		{ (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x4E, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x4D, (byte)0x04, (byte)0x00, (byte)0x00
            , (byte)0x75, (byte)0x73, (byte)0x65, (byte)0x72, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
            , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
            , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x4F, (byte)0x6C, (byte)0x64, (byte)0x50
            , (byte)0x61, (byte)0x73, (byte)0x73, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
            , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
            , (byte)0x4E, (byte)0x65, (byte)0x77, (byte)0x50, (byte)0x61, (byte)0x73, (byte)0x73, (byte)0x20
            , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
            , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x8C, (byte)0x20
            };
        ModifyPassword expected = new ModifyPassword(2, "user", "OldPass", "NewPass");
      	expected.Length = input.length;
      	expected.CRC16 = (short)0x208C;
      	ByteBuffer buf = ByteBuffer.wrap(input);
      	buf.order(ByteOrder.LITTLE_ENDIAN);
      	ModifyPasswordCodec codec = new ModifyPasswordCodec();
        ModifyPassword actual = codec.decode(buf);

      	Assert.assertEquals(expected, actual);
    }
}


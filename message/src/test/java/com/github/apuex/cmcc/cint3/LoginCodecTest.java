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

public class LoginCodecTest {
    @Test
    public void testEncode() {
        byte[] expected = new byte[] 
            { (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x3A, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x65, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x75, (byte)0x73, (byte)0x65, (byte)0x72, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
      	    , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
      	    , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x70, (byte)0x61, (byte)0x73, (byte)0x73
      	    , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
      	    , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
            , (byte)0x31, (byte)0x00
            };
        Login v = new Login(1, "user", "pass");
      	byte[] actual = new byte[58];
      	ByteBuffer buf = ByteBuffer.wrap(actual);
      	buf.order(ByteOrder.LITTLE_ENDIAN);

        System.out.printf("BEFORE:\n");
        System.out.printf("arrayOffset: %d\n", buf.arrayOffset());
        System.out.printf("capacity: %d\n", buf.capacity());
        System.out.printf("remaining: %d\n", buf.remaining());
        System.out.printf("limit: %d\n", buf.limit());
        System.out.printf("position: %d\n", buf.position());
        LoginCodec codec = new LoginCodec();
      	codec.encode(buf, v);
      	v.Length = buf.position();
        System.out.printf("AFTER:\n");
        System.out.printf("arrayOffset: %d\n", buf.arrayOffset());
        System.out.printf("capacity: %d\n", buf.capacity());
        System.out.printf("remaining: %d\n", buf.remaining());
        System.out.printf("limit: %d\n", buf.limit());
        System.out.printf("position: %d\n", buf.position());

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
            { (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x3A, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x65, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x75, (byte)0x73, (byte)0x65, (byte)0x72, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
      	    , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
      	    , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x70, (byte)0x61, (byte)0x73, (byte)0x73
      	    , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
      	    , (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20
      	    , (byte)0x31, (byte)0x00
            };
        Login expected = new Login(1, "user", "pass");
      	expected.Length = input.length;
      	expected.CRC16 = (short)0x0031;
      	ByteBuffer buf = ByteBuffer.wrap(input);
      	buf.order(ByteOrder.LITTLE_ENDIAN);
      	LoginCodec codec = new LoginCodec();
        Login actual = codec.decode(buf);

      	Assert.assertEquals(expected, actual);
    }
}


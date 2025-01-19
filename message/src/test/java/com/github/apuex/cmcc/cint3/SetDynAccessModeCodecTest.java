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
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class SetDynAccessModeCodecTest {
    @Test
    public void testEncode() {
        byte[] expected = new byte[] 
            { (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x2E, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x91, (byte)0x01, (byte)0x00, (byte)0x00
            , (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x1E, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x46, (byte)0x94
            };
        List<Integer> l = new LinkedList<Integer>();
        l.add(1);
        l.add(2);
        SetDynAccessMode v = new SetDynAccessMode(2, 1, 2, EnumAccessMode.ASK_ANSWER, 30, new NodeIDArray(l));
      	byte[] actual = new byte[46];
      	ByteBuffer buf = ByteBuffer.wrap(actual);
      	buf.order(ByteOrder.LITTLE_ENDIAN);

      	SetDynAccessModeCodec codec = new SetDynAccessModeCodec();
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
            { (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x2E, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x91, (byte)0x01, (byte)0x00, (byte)0x00
            , (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x1E, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x46, (byte)0x94
            };
        List<Integer> l = new LinkedList<Integer>();
        l.add(1);
        l.add(2);
        SetDynAccessMode expected = new SetDynAccessMode(2, 1, 2, EnumAccessMode.ASK_ANSWER, 30, new NodeIDArray(l));
      	expected.Length = input.length;
      	expected.CRC16 = (short)0x9446;
      	ByteBuffer buf = ByteBuffer.wrap(input);
      	buf.order(ByteOrder.LITTLE_ENDIAN);
      	SetDynAccessModeCodec codec = new SetDynAccessModeCodec();
        SetDynAccessMode actual = codec.decode(buf);

      	Assert.assertEquals(expected, actual);
    }
}


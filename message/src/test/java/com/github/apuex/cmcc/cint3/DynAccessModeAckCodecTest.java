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

public class DynAccessModeAckCodecTest {
    @Test
    public void testEncode() {
        byte[] expected = new byte[] 
            { (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x47, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x92, (byte)0x01, (byte)0x00, (byte)0x00
            , (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x00, (byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00
            , (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00
            , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0F, (byte)0xA1
            };
        List<TATD> vl = new LinkedList<TATD>();
        TD td = new TD();
        td.Type = EnumType.DI;
        td.LSCID = 1;
        td.Id = 2;
        td.Value = 0;
        td.State = EnumState.NOALARM;
        vl.add(td);
        TA ta = new TA();
        ta.Type = EnumType.AI;
        ta.LSCID = 1;
        ta.Id = 2;
        ta.Value = 0;
        ta.State = EnumState.NOALARM;
        vl.add(ta);
        TATDArray Values1 = new TATDArray(vl);

        DynAccessModeAck v = new DynAccessModeAck(1, 2, EnumResult.SUCCESS, Values1);
      	byte[] actual = new byte[71];
      	ByteBuffer buf = ByteBuffer.wrap(actual);
      	buf.order(ByteOrder.LITTLE_ENDIAN);

      	DynAccessModeAckCodec codec = new DynAccessModeAckCodec();
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
                { (byte)0x5A, (byte)0x6B, (byte)0x7C, (byte)0x7E, (byte)0x47, (byte)0x00, (byte)0x00, (byte)0x00
                , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x92, (byte)0x01, (byte)0x00, (byte)0x00
                , (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
                , (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
                , (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
                , (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00
                , (byte)0x00, (byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00
                , (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00
                , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0F, (byte)0xA1
                };

        List<TATD> vl = new LinkedList<TATD>();
        TD td = new TD();
        td.Type = EnumType.DI;
        td.LSCID = 1;
        td.Id = 2;
        td.Value = 0;
        td.State = EnumState.NOALARM;
        vl.add(td);
        TA ta = new TA();
        ta.Type = EnumType.AI;
        ta.LSCID = 1;
        ta.Id = 2;
        ta.Value = 0;
        ta.State = EnumState.NOALARM;
        vl.add(ta);
        TATDArray Values1 = new TATDArray(vl);
        DynAccessModeAck expected = new DynAccessModeAck(1, 2, EnumResult.SUCCESS, Values1);
        expected.Length = input.length;
      	expected.CRC16 = (short)0xA10F;
        DynAccessModeAckCodec codec = new DynAccessModeAckCodec();
     
      	ByteBuffer buf = ByteBuffer.wrap(input);
      	buf.order(ByteOrder.LITTLE_ENDIAN);
        DynAccessModeAck actual = codec.decode(buf);

      	Assert.assertEquals(expected, actual);
    }
}


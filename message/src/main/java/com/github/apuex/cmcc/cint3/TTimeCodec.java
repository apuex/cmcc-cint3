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

/**
 * 时间的结构
 *
 * @author Wangxy
 */
public class TTimeCodec {

    public void encode(ByteBuffer buf, TTime v) {
        buf.putShort(v.Years);
        buf.put(v.Month);
        buf.put(v.Day);
        buf.put(v.Hour);
        buf.put(v.Minute);
        buf.put(v.Second);
    }

    public TTime decode(ByteBuffer buf) {
        TTime v = new TTime();
        v.Years = buf.getShort();
        v.Month = buf.get();
        v.Day = buf.get();
        v.Hour = buf.get();
        v.Minute = buf.get();
        v.Second = buf.get();
        return v;
    }

    
}


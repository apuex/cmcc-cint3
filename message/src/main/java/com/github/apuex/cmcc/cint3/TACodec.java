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
 * 模拟量的值的结构
 *
 * @author Wangxy
 */
public class TACodec {

    public void encode(ByteBuffer buf, TA v) {
        buf.putInt(v.Type.getValue());
        buf.putInt(v.Id);
        buf.putInt(v.LSCID);
        buf.putFloat(v.Value);
        buf.putInt(v.State.getValue());
    }

    public TA decode(ByteBuffer buf) {
        TA v = new TA();
        v.Type = EnumType.fromValue(buf.getInt());
        v.Id = buf.getInt();
        v.LSCID = buf.getInt();
        v.Value = buf.getFloat();
        v.State = EnumState.fromValue(buf.getInt());
        return v;
    }

    
}


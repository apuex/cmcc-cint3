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
 * 数字量的值的结构
 *
 * @author Wangxy
 */
public class TDCodec {

    public void encode(ByteBuffer buf, TD v) {
        buf.putInt(v.Type.getValue());
        buf.putInt(v.Id);
        buf.putInt(v.LSCID);
        buf.put(v.Value);
        buf.putInt(v.State.getValue());
    }

    public TD decode(ByteBuffer buf) {
        TD v = new TD();
        v.Type = EnumType.fromValue(buf.getInt());
        v.Id = buf.getInt();
        v.LSCID = buf.getInt();
        v.Value = buf.get();
        v.State = EnumState.fromValue(buf.getInt());
        return v;
    }

    
}


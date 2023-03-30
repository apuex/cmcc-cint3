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
 * 改口令响应
 *
 * @author Wangxy
 */
public class ModifyPasswordAckCodec {

    public void encode(ByteBuffer buf, ModifyPasswordAck v) {
        final int initialPos = buf.position();
        // Message HEAD - envelope fields
        buf.putInt(v.Header);
        buf.putInt(v.Length);
        buf.putInt(v.SerialNo);
        buf.putInt(v.PKType.getValue());
        // Message CONTENT BEGIN 
        buf.putInt(v.Result.getValue());
        // Message CONTENT END 
        // Message TAIL - envelope fields
        buf.putShort(v.CRC16);
        final int pos = buf.position();
        // Message LENGTH - envelope fields
        v.Length = pos - initialPos;
        buf.position(initialPos + 4);
        buf.putInt(v.Length);
        buf.position(pos - 2);
        v.CRC16 = Util.CRC16(buf.array(), initialPos, pos - 2);
        buf.putShort(v.CRC16);
    }

    public ModifyPasswordAck decode(ByteBuffer buf) {
        ModifyPasswordAck v = new ModifyPasswordAck();
        // Message HEAD - envelope fields
        v.Header = buf.getInt();
        v.Length = buf.getInt();
        v.SerialNo = buf.getInt();
        v.PKType = EnumPKType.fromValue(buf.getInt());
        // Message CONTENT BEGIN 
        v.Result = EnumResult.fromValue(buf.getInt());
        // Message CONTENT END 
        // Message TAIL - envelope fields
        v.CRC16 = buf.getShort();
        return v;
    }

    
}


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
 * 当前告警值的结构
 *
 * @author Wangxy
 */
public class TAlarmCodec {

    public void encode(ByteBuffer buf, TAlarm v) {
        buf.putInt(v.Id);
        buf.putInt(v.LSCID);
        buf.putInt(v.State.getValue());
        Util.encodeStringNTS(buf, v.AlarmDesc, Lengths.ALARM_LENGTH);
        buf.putShort((short)0); // discard padding 2 bytes
    }

    public TAlarm decode(ByteBuffer buf) {
        TAlarm v = new TAlarm();
        v.Id = buf.getInt();
        v.LSCID = buf.getInt();
        v.State = EnumState.fromValue(buf.getInt());
        v.AlarmDesc = Util.decodeString(buf, Lengths.ALARM_LENGTH);
        buf.getShort(); // discard padding 2 bytes
        return v;
    }

    
}


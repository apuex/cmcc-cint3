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

/**
 * 发送时钟消息
 *
 * @author Wangxy
 */
public class TimeCheck extends Message {
    private static final long serialVersionUID = 1L;
    
    public TimeCheck() {
        super(EnumPKType.TIME_CHECK);
    }

    public TimeCheck
    ( TTime Time
    ) {
        super(EnumPKType.TIME_CHECK);
        this.Time = Time;
    }

    public TimeCheck
    ( int SerialNo
    , TTime Time
    ) {
        super(SerialNo, EnumPKType.TIME_CHECK);
        this.Time = Time;
    }

    @Override
    public boolean equals(Object o) {
        TimeCheck r = null;
        if(o instanceof TimeCheck) {
            r = (TimeCheck) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Header == r.Header
            && this.Length == r.Length
            && this.SerialNo == r.SerialNo
            && this.PKType == r.PKType
            && this.Time.equals(r.Time)
            && this.CRC16 == r.CRC16
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("TimeCheck { ")
            .append("Header=").append(String.format("0x%08X", this.Header))
            .append(", ").append("Length=").append(this.Length)
            .append(", ").append("SerialNo=").append(this.SerialNo)
            .append(", ").append("PKType=").append(this.PKType)
            .append(", ").append("Time=").append(this.Time)
            .append(", ").append("CRC16=").append(String.format("0x%04X", this.CRC16))
            .append(" }");

        return builder.toString();
    }

    public TTime Time; // 本机时间
}


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
 * 时钟同步响应
 *
 * @author Wangxy
 */
public class TimeCheckAck extends Message {
    private static final long serialVersionUID = 1L;
    
    public TimeCheckAck() {
        super(EnumPKType.TIME_CHECK_ACK);
    }

    public TimeCheckAck
    ( EnumResult Result
    ) {
        super(EnumPKType.TIME_CHECK_ACK);
        this.Result = Result;
    }

    public TimeCheckAck
    ( int SerialNo
    , EnumResult Result
    ) {
        super(SerialNo, EnumPKType.TIME_CHECK_ACK);
        this.Result = Result;
    }

    @Override
    public boolean equals(Object o) {
        TimeCheckAck r = null;
        if(o instanceof TimeCheckAck) {
            r = (TimeCheckAck) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Header == r.Header
            && this.Length == r.Length
            && this.SerialNo == r.SerialNo
            && this.PKType == r.PKType
            && this.Result == r.Result
            && this.CRC16 == r.CRC16
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("TimeCheckAck { ")
            .append("Header=").append(String.format("0x%08X", this.Header))
            .append(", ").append("Length=").append(this.Length)
            .append(", ").append("SerialNo=").append(this.SerialNo)
            .append(", ").append("PKType=").append(this.PKType)
            .append(", ").append("Result=").append(this.Result)
            .append(", ").append("CRC16=").append(String.format("0x%04X", this.CRC16))
            .append(" }");

        return builder.toString();
    }

    public EnumResult Result; // 报文返回结果
}


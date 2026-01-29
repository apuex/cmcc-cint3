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
 * 写数据请求
 *
 * @author Wangxy
 */
public class SetPoint extends Message {
    private static final long serialVersionUID = 1L;
    
    public SetPoint() {
        super(EnumPKType.SET_POINT);
    }

    public SetPoint
    ( TATD Value
    ) {
        super(EnumPKType.SET_POINT);
        this.Value = Value;
    }

    public SetPoint
    ( int SerialNo
    , TATD Value
    ) {
        super(SerialNo, EnumPKType.SET_POINT);
        this.Value = Value;
    }

    @Override
    public boolean equals(Object o) {
        SetPoint r = null;
        if(o instanceof SetPoint) {
            r = (SetPoint) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Header == r.Header
            && this.Length == r.Length
            && this.SerialNo == r.SerialNo
            && this.PKType == r.PKType
            && this.Value.equals(r.Value)
            && this.CRC16 == r.CRC16
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("SetPoint { ")
            .append("Header=").append(String.format("0x%08X", this.Header))
            .append(", ").append("Length=").append(this.Length)
            .append(", ").append("SerialNo=").append(this.SerialNo)
            .append(", ").append("PKType=").append(this.PKType)
            .append(", ").append("Value=").append(this.Value)
            .append(", ").append("CRC16=").append(String.format("0x%04X", this.CRC16))
            .append(" }");

        return builder.toString();
    }

    public TATD Value; // 5.1.8中的TA/TD的数据结构定义
}


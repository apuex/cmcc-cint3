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
 * 写数据响应
 *
 * @author Wangxy
 */
public class SetPointAck extends Message {
    private static final long serialVersionUID = 1L;
    
    public SetPointAck() {
        super(EnumPKType.SET_POINT_ACK);
    }

    public SetPointAck
    ( int LSCID
    , int Id
    , EnumResult Result
    ) {
        super(EnumPKType.SET_POINT_ACK);
        this.LSCID = LSCID;
        this.Id = Id;
        this.Result = Result;
    }

    public SetPointAck
    ( int SerialNo
    , int LSCID
    , int Id
    , EnumResult Result
    ) {
        super(SerialNo, EnumPKType.SET_POINT_ACK);
        this.LSCID = LSCID;
        this.Id = Id;
        this.Result = Result;
    }

    @Override
    public boolean equals(Object o) {
        SetPointAck r = null;
        if(o instanceof SetPointAck) {
            r = (SetPointAck) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Header == r.Header
            && this.Length == r.Length
            && this.SerialNo == r.SerialNo
            && this.PKType == r.PKType
            && this.LSCID == r.LSCID
            && this.Id == r.Id
            && this.Result == r.Result
            && this.CRC16 == r.CRC16
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("SetPointAck { ")
            .append("Header=").append(this.Header)
            .append(", ").append("Length=").append(this.Length)
            .append(", ").append("SerialNo=").append(this.SerialNo)
            .append(", ").append("PKType=").append(this.PKType)
            .append(", ").append("LSCID=").append(this.LSCID)
            .append(", ").append("Id=").append(this.Id)
            .append(", ").append("Result=").append(this.Result)
            .append(", ").append("CRC16=").append(this.CRC16)
            .append(" }");

        return builder.toString();
    }

    public int LSCID; // LSC ID
    public int Id; // 数据标识ID
    public EnumResult Result; // 报文返回结果
}


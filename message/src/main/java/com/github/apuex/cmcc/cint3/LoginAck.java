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
 * 登录响应
 *
 * @author Wangxy
 */
public class LoginAck extends Message {
    private static final long serialVersionUID = 1L;
    
    public LoginAck() {
        super(EnumPKType.LOGIN_ACK);
    }

    public LoginAck
    ( EnumRightMode RightLevel
    ) {
        super(EnumPKType.LOGIN_ACK);
        this.RightLevel = RightLevel;
    }

    public LoginAck
    ( int SerialNo
    , EnumRightMode RightLevel
    ) {
        super(SerialNo, EnumPKType.LOGIN_ACK);
        this.RightLevel = RightLevel;
    }

    @Override
    public boolean equals(Object o) {
        LoginAck r = null;
        if(o instanceof LoginAck) {
            r = (LoginAck) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Header == r.Header
            && this.Length == r.Length
            && this.SerialNo == r.SerialNo
            && this.PKType == r.PKType
            && this.RightLevel == r.RightLevel
            && this.CRC16 == r.CRC16
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("LoginAck { ")
            .append("Header=").append(this.Header)
            .append(", ").append("Length=").append(this.Length)
            .append(", ").append("SerialNo=").append(this.SerialNo)
            .append(", ").append("PKType=").append(this.PKType)
            .append(", ").append("RightLevel=").append(this.RightLevel)
            .append(", ").append("CRC16=").append(this.CRC16)
            .append(" }");

        return builder.toString();
    }

    public EnumRightMode RightLevel; // 监控系统下级SC向上级SC提供的权限定义
}


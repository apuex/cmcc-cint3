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
 * 登录
 *
 * @author Wangxy
 */
public class Login extends Message {
    private static final long serialVersionUID = 1L;
    
    public Login() {
        super(EnumPKType.LOGIN);
    }

    public Login
    ( String UserName
    , String PassWord
    ) {
        super(EnumPKType.LOGIN);
        this.UserName = UserName;
        this.PassWord = PassWord;
    }

    public Login
    ( int SerialNo
    , String UserName
    , String PassWord
    ) {
        super(SerialNo, EnumPKType.LOGIN);
        this.UserName = UserName;
        this.PassWord = PassWord;
    }

    @Override
    public boolean equals(Object o) {
        Login r = null;
        if(o instanceof Login) {
            r = (Login) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Header == r.Header
            && this.Length == r.Length
            && this.SerialNo == r.SerialNo
            && this.PKType == r.PKType
            && this.UserName.equals(r.UserName)
            && this.PassWord.equals(r.PassWord)
            && this.CRC16 == r.CRC16
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("Login { ")
            .append("Header=").append(String.format("0x%08X", this.Header))
            .append(", ").append("Length=").append(this.Length)
            .append(", ").append("SerialNo=").append(this.SerialNo)
            .append(", ").append("PKType=").append(this.PKType)
            .append(", ").append("UserName=").append(this.UserName)
            .append(", ").append("PassWord=").append(this.PassWord)
            .append(", ").append("CRC16=").append(String.format("0x%04X", this.CRC16))
            .append(" }");

        return builder.toString();
    }

    public String UserName; // Login user name.
    public String PassWord; // Login password.
}


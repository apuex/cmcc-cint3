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
 * 改口令请求
 *
 * @author Wangxy
 */
public class ModifyPassword extends Message {
    private static final long serialVersionUID = 1L;
    
    public ModifyPassword() {
        super(EnumPKType.REQ_MODIFY_PASSWORD);
    }

    public ModifyPassword
    ( String UserName
    , String OldPassWord
    , String NewPassWord
    ) {
        super(EnumPKType.REQ_MODIFY_PASSWORD);
        this.UserName = UserName;
        this.OldPassWord = OldPassWord;
        this.NewPassWord = NewPassWord;
    }

    public ModifyPassword
    ( int SerialNo
    , String UserName
    , String OldPassWord
    , String NewPassWord
    ) {
        super(SerialNo, EnumPKType.REQ_MODIFY_PASSWORD);
        this.UserName = UserName;
        this.OldPassWord = OldPassWord;
        this.NewPassWord = NewPassWord;
    }

    @Override
    public boolean equals(Object o) {
        ModifyPassword r = null;
        if(o instanceof ModifyPassword) {
            r = (ModifyPassword) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Header == r.Header
            && this.Length == r.Length
            && this.SerialNo == r.SerialNo
            && this.PKType == r.PKType
            && this.UserName.equals(r.UserName)
            && this.OldPassWord.equals(r.OldPassWord)
            && this.NewPassWord.equals(r.NewPassWord)
            && this.CRC16 == r.CRC16
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("ModifyPassword { ")
            .append("Header=").append(String.format("0x%08X", this.Header))
            .append(", ").append("Length=").append(this.Length)
            .append(", ").append("SerialNo=").append(this.SerialNo)
            .append(", ").append("PKType=").append(this.PKType)
            .append(", ").append("UserName=").append(this.UserName)
            .append(", ").append("OldPassWord=").append(this.OldPassWord)
            .append(", ").append("NewPassWord=").append(this.NewPassWord)
            .append(", ").append("CRC16=").append(String.format("0x%04X", this.CRC16))
            .append(" }");

        return builder.toString();
    }

    public String UserName; // Login user name.
    public String OldPassWord; // Login password.
    public String NewPassWord; // Login password.
}


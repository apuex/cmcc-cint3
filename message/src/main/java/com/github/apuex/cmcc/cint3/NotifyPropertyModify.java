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
 * 修改数据设置通知
 *
 * @author Wangxy
 */
public class NotifyPropertyModify extends Message {
    private static final long serialVersionUID = 1L;
    
    public NotifyPropertyModify() {
        super(EnumPKType.NOTIFY_PROPERTY_MODIFY);
    }

    public NotifyPropertyModify
    ( int Id
    , EnumModifyType ModifyType
    ) {
        super(EnumPKType.NOTIFY_PROPERTY_MODIFY);
        this.Id = Id;
        this.ModifyType = ModifyType;
    }

    public NotifyPropertyModify
    ( int SerialNo
    , int Id
    , EnumModifyType ModifyType
    ) {
        super(SerialNo, EnumPKType.NOTIFY_PROPERTY_MODIFY);
        this.Id = Id;
        this.ModifyType = ModifyType;
    }

    @Override
    public boolean equals(Object o) {
        NotifyPropertyModify r = null;
        if(o instanceof NotifyPropertyModify) {
            r = (NotifyPropertyModify) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Header == r.Header
            && this.Length == r.Length
            && this.SerialNo == r.SerialNo
            && this.PKType == r.PKType
            && this.Id == r.Id
            && this.ModifyType == r.ModifyType
            && this.CRC16 == r.CRC16
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("NotifyPropertyModify { ")
            .append("Header=").append(String.format("0x%08X", this.Header))
            .append(", ").append("Length=").append(this.Length)
            .append(", ").append("SerialNo=").append(this.SerialNo)
            .append(", ").append("PKType=").append(this.PKType)
            .append(", ").append("Id=").append(this.Id)
            .append(", ").append("ModifyType=").append(this.ModifyType)
            .append(", ").append("CRC16=").append(String.format("0x%04X", this.CRC16))
            .append(" }");

        return builder.toString();
    }

    public int Id; // 数据标识ID
    public EnumModifyType ModifyType; // 对象属性修改类型
}


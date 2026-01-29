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
 * 修改通知收到
 *
 * @author Wangxy
 */
public class PropertyModifyAck extends Message {
    private static final long serialVersionUID = 1L;
    
    public PropertyModifyAck
    ( 
    ) {
        super(EnumPKType.PROPERTY_MODIFY_ACK);
        
    }

    public PropertyModifyAck
    ( int SerialNo
    ) {
        super(SerialNo, EnumPKType.PROPERTY_MODIFY_ACK);
        
    }

    @Override
    public boolean equals(Object o) {
        PropertyModifyAck r = null;
        if(o instanceof PropertyModifyAck) {
            r = (PropertyModifyAck) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Header == r.Header
            && this.Length == r.Length
            && this.SerialNo == r.SerialNo
            && this.PKType == r.PKType
            && this.CRC16 == r.CRC16
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("PropertyModifyAck { ")
            .append("Header=").append(String.format("0x%08X", this.Header))
            .append(", ").append("Length=").append(this.Length)
            .append(", ").append("SerialNo=").append(this.SerialNo)
            .append(", ").append("PKType=").append(this.PKType)
            .append(", ").append("CRC16=").append(String.format("0x%04X", this.CRC16))
            .append(" }");

        return builder.toString();
    }

    
}


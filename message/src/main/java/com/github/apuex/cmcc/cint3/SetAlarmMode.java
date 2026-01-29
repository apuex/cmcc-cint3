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
 * 请求告警数据方式设置
 *
 * @author Wangxy
 */
public class SetAlarmMode extends Message {
    private static final long serialVersionUID = 1L;
    
    public SetAlarmMode() {
        super(EnumPKType.SET_ALARM_MODE);
    }

    public SetAlarmMode
    ( int GroupID
    , EnumAlarmMode Mode
    , NodeIDArray Ids
    ) {
        super(EnumPKType.SET_ALARM_MODE);
        this.GroupID = GroupID;
        this.Mode = Mode;
        this.Ids = Ids;
    }

    public SetAlarmMode
    ( int SerialNo
    , int GroupID
    , EnumAlarmMode Mode
    , NodeIDArray Ids
    ) {
        super(SerialNo, EnumPKType.SET_ALARM_MODE);
        this.GroupID = GroupID;
        this.Mode = Mode;
        this.Ids = Ids;
    }

    @Override
    public boolean equals(Object o) {
        SetAlarmMode r = null;
        if(o instanceof SetAlarmMode) {
            r = (SetAlarmMode) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Header == r.Header
            && this.Length == r.Length
            && this.SerialNo == r.SerialNo
            && this.PKType == r.PKType
            && this.GroupID == r.GroupID
            && this.Mode == r.Mode
            && this.Ids.equals(r.Ids)
            && this.CRC16 == r.CRC16
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("SetAlarmMode { ")
            .append("Header=").append(String.format("0x%08X", this.Header))
            .append(", ").append("Length=").append(this.Length)
            .append(", ").append("SerialNo=").append(this.SerialNo)
            .append(", ").append("PKType=").append(this.PKType)
            .append(", ").append("GroupID=").append(this.GroupID)
            .append(", ").append("Mode=").append(this.Mode)
            .append(", ").append("Ids=").append(this.Ids)
            .append(", ").append("CRC16=").append(String.format("0x%04X", this.CRC16))
            .append(" }");

        return builder.toString();
    }

    public int GroupID; // 相应模式数据包的序号
    public EnumAlarmMode Mode; // 告警等级设定的模式
    public NodeIDArray Ids; // 如果类型是站点，即获取站内所有设备下的监控点数据；如果是设备，即获取该设备下所有监控点数据；如果是监控点，即是该点数据。
}


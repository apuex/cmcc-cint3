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
 * 请求实时数据方式设置
 *
 * @author Wangxy
 */
public class SetDynAccessMode extends Message {
    private static final long serialVersionUID = 1L;
    
    public SetDynAccessMode() {
        super(EnumPKType.SET_DYN_ACCESS_MODE);
    }

    public SetDynAccessMode
    ( int TerminalID
    , int GroupID
    , EnumAccessMode Mode
    , int PollingTime
    , NodeIDArray Ids
    ) {
        super(EnumPKType.SET_DYN_ACCESS_MODE);
        this.TerminalID = TerminalID;
        this.GroupID = GroupID;
        this.Mode = Mode;
        this.PollingTime = PollingTime;
        this.Ids = Ids;
    }

    public SetDynAccessMode
    ( int SerialNo
    , int TerminalID
    , int GroupID
    , EnumAccessMode Mode
    , int PollingTime
    , NodeIDArray Ids
    ) {
        super(SerialNo, EnumPKType.SET_DYN_ACCESS_MODE);
        this.TerminalID = TerminalID;
        this.GroupID = GroupID;
        this.Mode = Mode;
        this.PollingTime = PollingTime;
        this.Ids = Ids;
    }

    @Override
    public boolean equals(Object o) {
        SetDynAccessMode r = null;
        if(o instanceof SetDynAccessMode) {
            r = (SetDynAccessMode) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Header == r.Header
            && this.Length == r.Length
            && this.SerialNo == r.SerialNo
            && this.PKType == r.PKType
            && this.TerminalID == r.TerminalID
            && this.GroupID == r.GroupID
            && this.Mode == r.Mode
            && this.PollingTime == r.PollingTime
            && this.Ids.equals(r.Ids)
            && this.CRC16 == r.CRC16
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("SetDynAccessMode { ")
            .append("Header=").append(String.format("0x%08X", this.Header))
            .append(", ").append("Length=").append(this.Length)
            .append(", ").append("SerialNo=").append(this.SerialNo)
            .append(", ").append("PKType=").append(this.PKType)
            .append(", ").append("TerminalID=").append(this.TerminalID)
            .append(", ").append("GroupID=").append(this.GroupID)
            .append(", ").append("Mode=").append(this.Mode)
            .append(", ").append("PollingTime=").append(this.PollingTime)
            .append(", ").append("Ids=").append(this.Ids)
            .append(", ").append("CRC16=").append(String.format("0x%04X", this.CRC16))
            .append(" }");

        return builder.toString();
    }

    public int TerminalID; // 上级SCID
    public int GroupID; // 相应模式数据包的序号
    public EnumAccessMode Mode; // 下级实时数据访问的方式
    public int PollingTime; // 定时方式时的发送间隔秒数，小于1无效，若出现小于1的值，则按等于1处理。
    public NodeIDArray Ids; // 如果类型是站点，即获取站内所有设备下的监控点数据；如果是设备，即获取该设备下所有监控点数据；如果是监控点，即是该点数据。
}


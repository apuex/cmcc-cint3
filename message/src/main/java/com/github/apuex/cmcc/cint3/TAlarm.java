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

import java.io.Serializable;

/**
 * 当前告警值的结构
 *
 * @author Wangxy
 */
public class TAlarm implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        TAlarm r = null;
        if(o instanceof TAlarm) {
            r = (TAlarm) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Id == r.Id
            && this.LSCID == r.LSCID
            && this.State == r.State
            && this.AlarmDesc.equals(r.AlarmDesc)
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("TAlarm { ")
            .append("Id=").append(this.Id)
            .append(", ").append("LSCID=").append(this.LSCID)
            .append(", ").append("State=").append(this.State)
            .append(", ").append("AlarmDesc=").append(this.AlarmDesc)
            .append(" }");

        return builder.toString();
    }

    public int Id; // 数据标识ID
    public int LSCID; // LSC ID
    public EnumState State; // 数值的状态
    public String AlarmDesc; // 告警描述
}


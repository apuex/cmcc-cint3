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
 * 时间的结构
 *
 * @author Wangxy
 */
public class TTime implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        TTime r = null;
        if(o instanceof TTime) {
            r = (TTime) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Years == r.Years
            && this.Month == r.Month
            && this.Day == r.Day
            && this.Hour == r.Hour
            && this.Minute == r.Minute
            && this.Second == r.Second
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("TTime { ")
            .append("Years=").append(this.Years)
            .append(", ").append("Month=").append(this.Month)
            .append(", ").append("Day=").append(this.Day)
            .append(", ").append("Hour=").append(this.Hour)
            .append(", ").append("Minute=").append(this.Minute)
            .append(", ").append("Second=").append(this.Second)
            .append(" }");

        return builder.toString();
    }

    public short Years; // 年
    public byte Month; // 月
    public byte Day; // 日
    public byte Hour; // 时
    public byte Minute; // 分
    public byte Second; // 秒
}


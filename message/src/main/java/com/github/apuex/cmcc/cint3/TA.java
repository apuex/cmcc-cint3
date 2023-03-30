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
 * 模拟量的值的结构
 *
 * @author Wangxy
 */
public class TA extends TATD {
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        TA r = null;
        if(o instanceof TA) {
            r = (TA) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Type == r.Type
            && this.Id == r.Id
            && this.LSCID == r.LSCID
            && this.Value == r.Value
            && this.State == r.State
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("TA { ")
            .append("Type=").append(this.Type)
            .append(", ").append("Id=").append(this.Id)
            .append(", ").append("LSCID=").append(this.LSCID)
            .append(", ").append("Value=").append(this.Value)
            .append(", ").append("State=").append(this.State)
            .append(" }");

        return builder.toString();
    }

    public EnumType Type; // 监控系统数据的种类
    public int Id; // 数据标识ID
    public int LSCID; // LSC ID
    public float Value; // AI值
    public EnumState State; // 数值的状态
}


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
 * 告警的等级
 *
 * @author Wangxy
 */
public enum EnumAlarmLevel {
    NOALARM(0) //无告警
    , CRITICAL(1) //一级告警
    , MAJOR(2) //二级告警
    , MINOR(3) //三级告警
    , HINT(4) //四级告警
    ;

    EnumAlarmLevel(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumAlarmLevel fromValue(int v) {
        switch(v) {
        case 0: return NOALARM;
        case 1: return CRITICAL;
        case 2: return MAJOR;
        case 3: return MINOR;
        case 4: return HINT;
        default: throw new IllegalArgumentException(String.format("%d is an invalid enum value.", v));
        }
    }
 
    private final int value;
}

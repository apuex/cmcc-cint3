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
 * 告警等级设定的模式
 *
 * @author Wangxy
 */
public enum EnumAlarmMode {
    NOALARM(0) //不做告警上报
    , CRITICAL(1) //一级告警上报
    , MAJOR(2) //二级告警上报
    , MINOR(3) //三级告警上报
    , HINT(4) //四级告警上报
    ;

    EnumAlarmMode(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumAlarmMode fromValue(int v) {
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

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
 * 监控系统数据的种类
 *
 * @author Wangxy
 */
public enum EnumType {
    STATION(0) //局、站
    , DEVICE(1) //设备
    , DI(2) //数字输入量（包含多态数字输入量），遥信
    , AI(3) //模拟输入量，遥测
    , DO(4) //数字输出量，遥控
    , AO(5) //模拟输出量，遥调
    ;

    EnumType(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumType fromValue(int v) {
        switch(v) {
        case 0: return STATION;
        case 1: return DEVICE;
        case 2: return DI;
        case 3: return AI;
        case 4: return DO;
        case 5: return AO;
        default: throw new IllegalArgumentException(String.format("%d is an invalid enum value.", v));
        }
    }
 
    private final int value;
}

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
 * 监控系统下级SC向上级SC提供的权限定义
 *
 * @author Wangxy
 */
public enum EnumRightMode {
    INVALID(0) //无权限
    , LEVEL1(1) //具备数据读的权限,当用户可以读某个数据，而无法写任何数据时返回这一权限值。
    , LEVEL2(2) //具备数据读、写的权限，当用户对某个数据具有读写权限时返回这一权限值。
    ;

    EnumRightMode(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumRightMode fromValue(int v) {
        switch(v) {
        case 0: return INVALID;
        case 1: return LEVEL1;
        case 2: return LEVEL2;
        default: throw new IllegalArgumentException(String.format("%d is an invalid enum value.", v));
        }
    }
 
    private final int value;
}

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
 * 使能的属性
 *
 * @author Wangxy
 */
public enum EnumEnable {
    DISABLE(0) //禁止/不能
    , ENABLE(1) //开放/能
    ;

    EnumEnable(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumEnable fromValue(int v) {
        switch(v) {
        case 0: return DISABLE;
        case 1: return ENABLE;
        default: throw new IllegalArgumentException(String.format("%d is an invalid enum value.", v));
        }
    }
 
    private final int value;
}

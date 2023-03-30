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
 * 报文返回结果
 *
 * @author Wangxy
 */
public enum EnumResult {
    FAILURE(0) //失败
    , SUCCESS(1) //成功
    ;

    EnumResult(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumResult fromValue(int v) {
        switch(v) {
        case 0: return FAILURE;
        case 1: return SUCCESS;
        default: throw new IllegalArgumentException(String.format("%d is an invalid enum value.", v));
        }
    }
 
    private final int value;
}

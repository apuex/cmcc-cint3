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
 * AI、DI值的结构的父类
 */
public class Lengths {
    public static final int NAME_LENGTH = 40; // 名字命名长度 40字节
    public static final int USER_LENGTH = 20; // 用户名长度 20字节
    public static final int PASSWORD_LENGTH = 20; // 口令长度 20字节
    public static final int EVENT_LENGTH = 160; // 事件信息长度 160字节
    public static final int ALARM_LENGTH = 285; // 告警事件信息长度 285字节
    public static final int ALARM_SERIALNO_LENGTH = 10; // 告警序号 10字节
    public static final int LOGIN_LENGTH = 100; // 登录事件信息长度 100字节
    public static final int DES_LENGTH = 40; // 描述信息长度 40字节
    public static final int UNIT_LENGTH = 8; // 数据单位的长度 8字节
    public static final int STATE_LENGTH = 160; // 态值描述长度 160字节
    public static final int VER_LENGTH = 20; // 版本描述的长度 20字节
    public static final int STATION_NAME_LEN = 154; // 局站的名字长度 20字节
}


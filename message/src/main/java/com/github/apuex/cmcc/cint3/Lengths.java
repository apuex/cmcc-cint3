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
    public static final int TIME_LENGTH = 19; // 时间串长度 19字节
    public static final int EVENT_LENGTH = 160; // 事件信息长度 160字节
    public static final int ALARM_LENGTH = 175; // 告警事件信息长度 175字节
    public static final int ALARMSERIALNO_LENGTH = 10; // 告警序号 10字节
    public static final int LOGIN_LENGTH = 100; // 登录事件信息长度 100字节
    public static final int DES_LENGTH = 60; // 描述信息长度 60字节
    public static final int UNIT_LENGTH = 8; // 数据单位的长度 8字节
    public static final int STATE_LENGTH = 160; // 态值描述长度 160字节
    public static final int VER_LENGTH = 20; // 版本描述的长度 20字节
    public static final int SCID_LENGTH = 7; // SC编号长度 7字节
    public static final int SITEID_LENGTH = 20; // 站点编号长度 20字节
    public static final int ROOM_LENGTH = 20; // 机房编号长度 20字节
    public static final int DEVICEID_LENGTH = 26; // 设备编号长度 26字节
    public static final int ID_LENGTH = 20; // 监控点编号长度 20字节
    public static final int NMALARMID_LENGTH = 40; // 网管告警编号 40字节
    public static final int SIGNALNUM_LENGTH = 3; // 同类监控点顺序号 3字节
    public static final int DEVICETYPE_LENGTH = 2; // 设备类型 2字节
}


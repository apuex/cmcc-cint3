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
 * 报文定义
 *
 * @author Wangxy
 */
public enum EnumPKType {
    LOGIN(101) //登录
    , LOGIN_ACK(102) //登录响应
    , LOGOUT(103) //登出
    , LOGOUT_ACK(104) //登出响应
    , SET_DYN_ACCESS_MODE(401) //请求实时数据方式设置
    , DYN_ACCESS_MODE_ACK(402) //实时数据响应
    , SET_ALARM_MODE(501) //请求告警数据方式设置
    , ALARM_MODE_ACK(502) //告警方式设置响应
    , SEND_ALARM(503) //实时告警发送
    , SEND_ALARM_ACK(504) //实时告警发送确认
    , SET_POINT(1001) //写数据请求
    , SET_POINT_ACK(1002) //写数据响应
    , REQ_MODIFY_PASSWORD(1101) //改口令请求
    , MODIFY_PASSWORD_ACK(1102) //改口令响应
    , HEART_BEAT(1201) //确认连接
    , HEART_BEAT_ACK(1202) //回应连接
    , TIME_CHECK(1301) //发送时钟消息
    , TIME_CHECK_ACK(1302) //时钟同步响应
    , NOTIFY_PROPERTY_MODIFY(1501) //通知数据属性修改
    , PROPERTY_MODIFY_ACK(1502) //通知数据属性修改响应
    ;

    EnumPKType(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumPKType fromValue(int v) {
        switch(v) {
        case 101: return LOGIN;
        case 102: return LOGIN_ACK;
        case 103: return LOGOUT;
        case 104: return LOGOUT_ACK;
        case 401: return SET_DYN_ACCESS_MODE;
        case 402: return DYN_ACCESS_MODE_ACK;
        case 501: return SET_ALARM_MODE;
        case 502: return ALARM_MODE_ACK;
        case 503: return SEND_ALARM;
        case 504: return SEND_ALARM_ACK;
        case 1001: return SET_POINT;
        case 1002: return SET_POINT_ACK;
        case 1101: return REQ_MODIFY_PASSWORD;
        case 1102: return MODIFY_PASSWORD_ACK;
        case 1201: return HEART_BEAT;
        case 1202: return HEART_BEAT_ACK;
        case 1301: return TIME_CHECK;
        case 1302: return TIME_CHECK_ACK;
        case 1501: return NOTIFY_PROPERTY_MODIFY;
        case 1502: return PROPERTY_MODIFY_ACK;
        default: throw new IllegalArgumentException(String.format("%d is an invalid enum value.", v));
        }
    }
 
    private final int value;
}

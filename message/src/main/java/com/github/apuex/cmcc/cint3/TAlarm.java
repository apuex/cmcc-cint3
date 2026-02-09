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

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 当前告警值的结构
 *
 * @author Wangxy
 */
public class TAlarm implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SERIAL_NO_PATTERN = "0*([0-9]+)";
    public static final String ALARM_NAME_PATTERN = "([^\\|]+)\\|([^\\|]*)\\|([^\\|]+)\\|([^\\|]+)\\|([^\\|]+)(\\|([^\\|]*))?";
    public static final String ALARM_TIME_PATTERN = "([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})";
    public static final String LSC_ID_PATTERN = "([0-9]+)";
    public static final String NODE_ID_PATTERN = "([0-9A-Fa-f]+\\.[0-9A-Fa-f]{2}\\.[0-9A-Fa-f]{3})";
    public static final String ALARM_DESC_PATTERN = "(\\S+)\\s+(\\S+)\\s+(\\S*)(-触发值)\\s*(\\S+)";

    private Pattern pattern = Pattern.compile(alarmDescPattern());

    public TAlarm() {

    }

    public TAlarm( int Id
                 , int LSCID
                 , EnumState State
                 , String AlarmDesc
                 )
    {
        this.Id = Id;
        this.LSCID = LSCID;
        this.State = State;
        this.AlarmDesc = AlarmDesc;
    }
    public static final String alarmIdFromDescPattern() {
        return String.format("^\\[%s%s\\s+%s.*"
                , SERIAL_NO_PATTERN
                , ALARM_NAME_PATTERN
                , ALARM_TIME_PATTERN
        );
    }
    public static final String alarmDescPattern() {
        return String.format("^(\\S{1})%s\\s+%s\\s+%s\\s+%s\\s+%s\\s+%s\\s+(\\S{1})"
                , SERIAL_NO_PATTERN
                , ALARM_NAME_PATTERN
                , ALARM_TIME_PATTERN
                , LSC_ID_PATTERN
                , NODE_ID_PATTERN
                , ALARM_DESC_PATTERN
        );
    }

    @Override
    public boolean equals(Object o) {
        TAlarm r = null;
        if(o instanceof TAlarm) {
            r = (TAlarm) o;
        } else {
            return false;
        }

        boolean result =
            ( this.Id == r.Id
            && this.LSCID == r.LSCID
            && this.State == r.State
            && this.AlarmDesc.equals(r.AlarmDesc)
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("TAlarm { ")
            .append("Id=").append(this.Id)
            .append(", ").append("LSCID=").append(this.LSCID)
            .append(", ").append("State=").append(this.State)
            .append(", ").append("AlarmDesc=").append(this.AlarmDesc)
            .append(" }");

        return builder.toString();
    }

    public int Id; // 数据标识ID
    public int LSCID; // LSC ID
    public EnumState State; // 数值的状态
    public String AlarmDesc; // 告警描述
}


package com.github.apuex.cmcc.cint3;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseAlarmDescTest {
    @Test
    public void testAlarmIdFromTAlarm() {
        final String desc = "[0000690426\t江门新会区小冈接入间一楼机房传输1||机房环境1[环境量]|机房环境|温度01|较高告警                                                                                                       \t2026-02-05 16:57:16\t0019\t010B.01.001\t二级\t开始\t较高告警-触发值 30.012度                \n" +
                "  ]";
        TAlarm alarm = new TAlarm(139986945, 128, EnumState.MAJOR, desc);
        AlarmId alarmId = AlarmId.fromTAlarm(alarm);
        System.out.printf("testAlarmIdFromTAlarm = %s\n", alarmId);
    }

    @Test
    public void testAlarmIdFromDescPattern() {
        final String desc = "[0000690426\t江门新会区小冈接入间一楼机房传输1||机房环境1[环境量]|机房环境|温度01|较高告警                                                                                                       \t2026-02-05 16:57:16\t0019\t010B.01.001\t二级\t开始\t较高告警-触发值 30.012度                \n" +
                "  ]";
        System.out.printf("testAlarmIdFromDescPattern = %s\n", TAlarm.alarmIdFromDescPattern());
        Pattern pattern = Pattern.compile(TAlarm.alarmIdFromDescPattern());
        Matcher matched = pattern.matcher(desc);
        if(matched.find()) {
            for(int i = 0; i <= matched.groupCount(); ++i) {
                System.out.printf("group[%s] = %s\n", i, matched.group(i));
            }
        }
    }

}

package com.github.apuex.cmcc.cint3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlarmId {
    public AlarmId( final long serialNo
                  , final Date alarmTime
                  , final int LSCId
                  , final int nodeId
                  , final EnumState state
                  , final int AlarmStatus
    ) {
        this.serialNo = serialNo;
        this.alarmTime = alarmTime;
        this.LSCId = LSCId;
        this.nodeId = nodeId;
        this.state = state;
        this.AlarmStatus = AlarmStatus;
    }

    public AlarmId( final long serialNo
                  , final long alarmTime
                  , final int LSCId
                  , final int nodeId
                  , final EnumState state
                  , final int AlarmStatus
    ) {
        this.serialNo = serialNo;
        this.alarmTime = new Date(alarmTime);
        this.LSCId = LSCId;
        this.nodeId = nodeId;
        this.state = state;
        this.AlarmStatus = AlarmStatus;
    }

    public static AlarmId fromTAlarm(final TAlarm alarm) {
        Pattern pattern = Pattern.compile(TAlarm.alarmDescPattern());
        Matcher matched = pattern.matcher(alarm.AlarmDesc);
        if(matched.find()) {
            final int LSCId = alarm.LSCID;
            final int nodeId = alarm.Id;
            final EnumState state = alarm.State;
            final long serialNo = Long.parseLong(matched.group(2));
            final Date timestamp = Util.parseDate(matched.group(10));
            final int alarmState = alarmStateFromString(matched.group(14));
            return new AlarmId(serialNo, timestamp, LSCId, nodeId, state, alarmState);
        } else {
            throw new RuntimeException(String.format("Failed to convert AlarmId from TAlarm %s", alarm));
        }
    }

    public static int alarmStateFromString(String s) {
        if(s.equals("开始")) return 0; else return 2;
    }

    public static AlarmId fromBytes(byte[] bytes) {
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        final long serialNo = buf.getLong();
        final long timestamp = buf.getLong();
        final int LSCId = buf.getInt();
        final int nodeId = buf.getInt();
        final int state = buf.getInt();
        final int alarmState = buf.getInt();
        return new AlarmId(serialNo, timestamp, LSCId, nodeId, EnumState.fromValue(state), alarmState);
    }

    public static byte[] toBytes(final AlarmId alarmId) {
        byte[] bytes = new byte[32];
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putLong(alarmId.serialNo);
        buf.putInt(alarmId.LSCId);
        buf.putLong(alarmId.alarmTime.getTime());
        buf.putInt(alarmId.nodeId);
        buf.putInt(alarmId.state.getValue());
        buf.putInt(alarmId.AlarmStatus);
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmId alarmId = (AlarmId) o;
        return serialNo == alarmId.serialNo
                && LSCId == alarmId.LSCId
                && nodeId == alarmId.nodeId
                && AlarmStatus == alarmId.AlarmStatus
                && Objects.equals(alarmTime, alarmId.alarmTime)
                && state == alarmId.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNo, alarmTime, LSCId, nodeId, state, AlarmStatus);
    }

    @Override
    public String toString() {
        return "AlarmId{" +
                "serialNo=" + serialNo +
                ", alarmTime=" + Util.formatDate(alarmTime) +
                ", LSCId=" + LSCId +
                ", nodeId=" + nodeId +
                "(BCD=" + BCD.format(nodeId) +
                "), state=" + state +
                ", AlarmStatus=" + AlarmStatus +
                '}';
    }

    final public long serialNo; // 流水
    final public Date alarmTime; // 告警时间
    final public int LSCId; // LSC ID
    final public int nodeId; // 数据标识ID
    final public EnumState state; // 数值的状态
    final public int AlarmStatus;
}

package com.github.apuex.cmcc.cint3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlarmId {
    public AlarmId( final long serialNo
            , final int nodeId
            , final Date timestamp
            , final EnumState state
    ) {
        this.serialNo = serialNo;
        this.nodeId = nodeId;
        this.timestamp = timestamp;
        this.state = state;
    }

    public AlarmId( final long serialNo
                  , final int nodeId
                  , final long timestamp
                  , final EnumState state
    ) {
        this.serialNo = serialNo;
        this.nodeId = nodeId;
        this.timestamp = new Date(timestamp);
        this.state = state;
    }

    public static AlarmId fromTAlarm(final TAlarm alarm) {
        Pattern pattern = Pattern.compile(TAlarm.alarmDescPattern());
        Matcher matched = pattern.matcher(alarm.AlarmDesc);
        if(matched.find()) {
            final int nodeId = alarm.Id;
            final EnumState state = alarm.State;
            long serialNo = Long.parseLong(matched.group(2));
            String alarmTime = matched.group(10);
            Date timestamp = Util.parseDate(alarmTime);
            return new AlarmId(serialNo, nodeId, timestamp, state);
        } else {
            throw new RuntimeException(String.format("Failed to convert AlarmId from TAlarm %s", alarm));
        }
    }

    public static AlarmId fromBytes(byte[] bytes) {
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        final long serialNo = buf.getLong();
        final int nodeId = buf.getInt();
        final long timestamp = buf.getLong();
        final int state = buf.getInt();
        return new AlarmId(serialNo, nodeId, timestamp, EnumState.fromValue(state));
    }

    public static byte[] toBytes(final AlarmId alarmId) {
        byte[] bytes = new byte[24];
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putLong(alarmId.serialNo);
        buf.putInt(alarmId.nodeId);
        buf.putLong(alarmId.timestamp.getTime());
        buf.putInt(alarmId.state.getValue());
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmId alarmId = (AlarmId) o;
        return nodeId == alarmId.nodeId && timestamp == alarmId.timestamp && state == alarmId.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, timestamp, state);
    }

    @Override
    public String toString() {
        return "AlarmId{" +
                "serialNo=" + serialNo +
                ", nodeId=" + nodeId +
                ", timestamp=" + Util.formatDate(timestamp) +
                ", state=" + state +
                '}';
    }

    final public long serialNo; // 流水
    final public int nodeId; // 数据标识ID
    final public Date timestamp; // LSC ID
    final public EnumState state; // 数值的状态
}

package com.github.apuex.cmcc.cint3;

import java.util.Comparator;

public final class AlarmIdComparator implements Comparator<AlarmId> {
    @Override
    public int compare(final AlarmId l, final AlarmId r) {
        int serialNoCompareResult = Long.compare(l.serialNo, r.serialNo);
        if(0 != serialNoCompareResult) return serialNoCompareResult;
        int timestampCompareResult = Long.compare(l.alarmTime.getTime(), r.alarmTime.getTime());
        if(0 != timestampCompareResult) return timestampCompareResult;
        int LSCIdCompareResult = Integer.compare(l.LSCId, r.LSCId);
        if(0 != LSCIdCompareResult ) return LSCIdCompareResult;
        int nodeIdCompareResult = Integer.compare(l.nodeId, r.nodeId);
        if(0 != nodeIdCompareResult ) return nodeIdCompareResult;
        int stateCompareResult = Integer.compare(l.state.getValue(), r.state.getValue());
        if(0 != stateCompareResult) return stateCompareResult;
        return Integer.compare(l.AlarmStatus, r.AlarmStatus);
    }
}

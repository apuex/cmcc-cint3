package com.github.apuex.cmcc.cint3;

import java.util.Comparator;

public final class AlarmIdComparator implements Comparator<AlarmId> {
    @Override
    public int compare(final AlarmId l, final AlarmId r) {
        int serialNoCompareResult = Long.compare(l.serialNo, r.serialNo);
        if(0 != serialNoCompareResult) return serialNoCompareResult;
        int nodeIdCompareResult = Integer.compare(l.nodeId, r.nodeId);
        if(0 != nodeIdCompareResult ) return nodeIdCompareResult;
        int timestampCompareResult = Long.compare(l.timestamp.getTime(), r.timestamp.getTime());
        if(0 != timestampCompareResult) return timestampCompareResult;
        return Integer.compare(l.state.getValue(), r.state.getValue());
    }
}

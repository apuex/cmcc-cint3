package com.github.apuex.cmcc.cint3;

import java.util.Comparator;

public class AlarmIdComparator implements Comparator<AlarmId> {
    @Override
    public int compare(final AlarmId o1, final AlarmId o2) {
        int nodeIdCompareResult = Integer.compare(o1.nodeId, o2.nodeId);
        if(0 != nodeIdCompareResult ) return nodeIdCompareResult;
        int timestampCompareResult = Long.compare(o1.timestamp.getTime(), o2.timestamp.getTime());
        if(0 != timestampCompareResult) return timestampCompareResult;
        return Integer.compare(o1.state.getValue(), o2.state.getValue());
    }
}

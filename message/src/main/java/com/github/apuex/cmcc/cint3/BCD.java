package com.github.apuex.cmcc.cint3;

public class BCD {
    public BCD(int b, int c, int d) {
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public BCD(int NodeId) {
        this.b = (0x1FFF & (NodeId >> 19));
        this.c = (0xFF & (NodeId >> 11));
        this.d = (0x7FF & NodeId);
    }
    public int toNodeId() {
        return ((0x1FFF & this.b) << 19)
                | ((0xFF & this.c) << 11)
                | (0x7FF & this.d);
    }
    public static BCD fromBCD(int b, int c, int d) {
        return new BCD(b, c, d);
    }
    public static BCD fromNodeId(int NodeId) {
        return new BCD(NodeId);
    }
    public static int toNodeId(int b, int c, int d) {
        return ((0x1FFF & b) << 19)
                | ((0xFF & c) << 11)
                | (0x7FF & d);
    }
    @Override
    public String toString() {
        return "BCD{" +
                "b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", NodeId=" + toNodeId() +
                '}';
    }

    public final int b;
    public final int c;
    public final int d;
}

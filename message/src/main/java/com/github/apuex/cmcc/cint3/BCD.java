package com.github.apuex.cmcc.cint3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BCD implements Comparable<BCD> {
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

    public static int fromBytes(byte[] bytes) {
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        return buf.getInt();
    }

    public static byte[] toBytes(int nodeId) {
        byte[] bytes = new byte[4];
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putInt(nodeId);
        return bytes;
    }

    public static boolean isStation(int b, int c, int d) {
        return ((0 != b) && (0 == c) && (0 == d));
    }
    public static boolean isDevice(int b, int c, int d) {
        return ((0 != b) && (0 != c) && (0 == d));
    }
    public static boolean isSignal(int b, int c, int d) {
        return ((0 != b) && (0 != c) && (0 != d));
    }
    public static boolean isValid(int b, int c, int d) {
        return (isSignal(b, c, d)
                || isDevice(b, c, d)
                || isStation(b, c, d));
    }

    public boolean isStation() {
        return isStation(this.b, this.c, this.d);
    }
    public boolean isDevice() {
        return isDevice(this.b, this.c, this.d);
    }
    public boolean isSignal() {
        return isSignal(this.b, this.c, this.d);
    }
    public boolean isValid() {
        return isValid(this.b, this.c, this.d);
    }

    @Override
    public int compareTo(BCD o) {
        return Integer.compare(toNodeId(), o.toNodeId());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BCD bcd = (BCD) o;
        return toNodeId() == bcd.toNodeId();
    }

    @Override
    public int hashCode() {
        return toNodeId();
    }

    public final int b;
    public final int c;
    public final int d;
}

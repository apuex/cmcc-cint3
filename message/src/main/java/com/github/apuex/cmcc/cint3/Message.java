package com.github.apuex.cmcc.cint3;

import java.io.Serializable;

/**
 * Parent class for all PKType of messages.
 *
 * @author Wangxy
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int MESSAGE_HEADER = 0x7E7C6B5A;

    public Message
    ( EnumPKType PKType
    ) {
        this.Header = MESSAGE_HEADER;
        this.Length = 0;
        this.SerialNo = 0;
        this.PKType = PKType;
        this.CRC16 = 0;
    }

    public Message
    ( int SerialNo
    , EnumPKType PKType
    ) {
        this.Header = MESSAGE_HEADER;
        this.Length = 0;
        this.SerialNo = SerialNo;
        this.PKType = PKType;
        this.CRC16 = 0;
    }

    public int Header; // Message header, 0x7E7C6B5A.
    public int Length; // Message length.
    public int SerialNo; // Message serial number.
    public EnumPKType PKType; // 报文定义
    public short CRC16; // Message CRC16.
}



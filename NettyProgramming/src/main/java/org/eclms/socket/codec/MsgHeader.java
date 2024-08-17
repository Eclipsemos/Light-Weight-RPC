package org.eclms.socket.codec;

import java.io.Serializable;

public class MsgHeader implements Serializable {
    private short magic; //magic number for safety issue
    private byte version; //protocol version
    private byte msgType; //data type
    private byte status; // status
    private long requestId; // request id
    private int serializationLen; // len of serialization

    private byte[] serialization; // content of serialization
    private int msgLen;
    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getSerializationLen() {
        return serializationLen;
    }

    public void setSerializationLen(int serializationLen) {
        this.serializationLen = serializationLen;
    }

    public byte[] getSerialization() {
        return serialization;
    }

    public void setSerialization(byte[] serialization) {
        this.serialization = serialization;
    }

    public int getMsgLen() {
        return msgLen;
    }

    public void setMsgLen(int msgLen) {
        this.msgLen = msgLen;
    }




}

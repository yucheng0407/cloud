package net.ruixin.udp;

import org.jboss.netty.buffer.ChannelBuffer;

import java.io.Serializable;
import java.util.Arrays;

public class Message implements Serializable{
    private static final long serialVersionUID = 4398559115325723920L;
    private int msgSn;
    private static int internalMsgNo = 0;
    private int msgVer;//版本号
    private int msgId;//命令标志
    private int msgHead;//标志头
    private int msgSize;//包体大小
    private ChannelBuffer VehicleNo;//标志头
    private ChannelBuffer msgBody;
    private ChannelBuffer msg;
    private byte[] versionFlag = {0,0,1};

    //下行报文标识，值为1时，代表发送的数据；默认为0，代表接收的报文
//	private int downFlag = 0;

    public Message(){}


    public Message(int msgId){
        //下行报文需要填充报文序列号
        synchronized((Integer)internalMsgNo) {
            if(internalMsgNo == Integer.MAX_VALUE){
                internalMsgNo = 0;
            }
        }
        this.msgSn = ++internalMsgNo;
        this.msgId = msgId;
        //this.downFlag = 1;
    }


    public static int getInternalMsgNo() {
        return internalMsgNo;
    }


    public static void setInternalMsgNo(int internalMsgNo) {
        Message.internalMsgNo = internalMsgNo;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getMsgVer() {
        return msgVer;
    }

    public void setMsgVer(int msgVer) {
        this.msgVer = msgVer;
    }

    public int getMsgHead() {
        return msgHead;
    }

    public void setMsgHead(int msgHead) {
        this.msgHead = msgHead;
    }

    public int getMsgSize() {
        return msgSize;
    }

    public void setMsgSize(long msgSize) {
        this.msgSize = (int)msgSize;
    }

    public int getMsgId() {
        return msgId;
    }


    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }


    public int getMsgSn() {
        return msgSn;
    }

    public ChannelBuffer getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(ChannelBuffer vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public void setMsgSn(int msgSn) {
        this.msgSn = msgSn;
    }


    public ChannelBuffer getMsgBody() {
        return msgBody;
    }


    public void setMsgBody(ChannelBuffer msgBody) {
        this.msgBody = msgBody;
    }


    public byte[] getVersionFlag() {
        return versionFlag;
    }


    public void setVersionFlag(byte[] versionFlag) {
        this.versionFlag = versionFlag;
    }

    public ChannelBuffer getMsg() {
        return msg;
    }

    public void setMsg(ChannelBuffer msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msgSn=" + msgSn +
                ", msgVer=" + msgVer +
                ", msgId=" + msgId +
                ", msgHead=" + msgHead +
                ", msgSize=" + msgSize +
                ", msgBody=" + msgBody +
                ", msg=" + msg +
                ", versionFlag=" + Arrays.toString(versionFlag) +
                '}';
    }
}

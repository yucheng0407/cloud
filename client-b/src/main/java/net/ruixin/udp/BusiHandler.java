package net.ruixin.udp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import net.ruixin.domain.entity.DeviceRealtime;
import net.ruixin.service.IDeviceRealtimeService;
import net.ruixin.util.constant.Sfyx_st;
import net.ruixin.util.json.JacksonUtil;
import net.ruixin.util.tools.SpringContextUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import static java.net.SocketOptions.SO_RCVBUF;

/**
 * @author gai
 */
public class BusiHandler extends SimpleChannelHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Integer send=1;
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Message msg = (Message) e.getMessage();
        if (msg.getMsgHead() == 0xAAAA) this.readProject(msg, ctx, e);
    }

    private void readProject(Message msg, ChannelHandlerContext ctx, MessageEvent e) throws ParseException {
        DeviceRealtime deviceRealtime = new DeviceRealtime();
        String cph = msg.getMsgBody().readBytes(20).toString(Charset.forName("GBK")).trim();
        deviceRealtime.setVehicleNo(cph);
        System.out.println("终端编号:"+cph);
//        logger.info("终端编号:" + 123);
        double x = this.hexToDouble(msg.getMsgBody());
        double y = this.hexToDouble(msg.getMsgBody());
        deviceRealtime.setLon(x);
        deviceRealtime.setLat(y);
//        //logger.info("经度：" + x);
//        //logger.info("纬度：" + y);
        int speed = this.hexToShortInt(msg.getMsgBody());
        deviceRealtime.setSpeed(speed);
        //logger.info("卫星定位速度：" + speed);
        int direct = this.hexToShortInt(msg.getMsgBody());
        deviceRealtime.setDirection(direct);
        //logger.info("方向：" + direct);
        int height = this.hexToShortInt(msg.getMsgBody());
        deviceRealtime.setAltitude(height);
        //logger.info("海拔：" + height);
        int jd = this.hexToShortInt(msg.getMsgBody());
        deviceRealtime.setJd(jd);
        //logger.info("精度：" + jd);
        int year = msg.getMsgBody().readUnsignedShort();
        byte month = msg.getMsgBody().readByte();
        byte day = msg.getMsgBody().readByte();
        byte hour = msg.getMsgBody().readByte();
        byte min = msg.getMsgBody().readByte();
        byte second = msg.getMsgBody().readByte();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String gxsj = year + "-" + month + "-" + day + "-" + hour + ":" + min + ":" + second;
        System.out.println("日期：" + gxsj);
        deviceRealtime.setGxsj(sdf.parse(gxsj));
        deviceRealtime.setSfyxSt(Sfyx_st.VALID);
        JmsMessagingTemplate jmsTemplate = SpringContextUtils.getBean(JmsMessagingTemplate.class);
        //发送消息
        jmsTemplate.convertAndSend(DeviceRealtime.ACTIVEMQ_TAG, JacksonUtil.toJson(deviceRealtime));
        //转发消息
        IDeviceRealtimeService service = SpringContextUtils.getBean(IDeviceRealtimeService.class);
        //转发缓存消息
        SendCache sendCache = SpringContextUtils.getBean(SendCache.class);
        Map inMap = sendCache.getInformation(deviceRealtime.getVehicleNo());
        if (inMap != null) {
            List<Map<String, Object>> list = sendCache.getServer(inMap.get("ID").toString());
            this.informationTo16(e, inMap);
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    this.sendServer(ctx, e, this.setAddress(map));
                }
            }
        }
    }

    private InetSocketAddress setAddress(Map map) {
        return new InetSocketAddress(map.get("IP").toString(), Integer.parseInt(map.get("PORT").toString()));
    }

    private void informationTo16(MessageEvent e, Map map) {
        ChannelBuffer buffer = ChannelBuffers.buffer(81);
        buffer.writeBytes(((Message) e.getMessage()).getMsg());
        buffer.writeBytes(this.serBuffer(map.get("DEVICE_ORGAN").toString().getBytes(Charset.forName("GBK")), 20));
        ((Message) e.getMessage()).setMsg(buffer);
    }

    private void sendServer(ChannelHandlerContext ctx, MessageEvent e, InetSocketAddress inetSocketAddress) {
        ChannelBuffer buffer = ((Message) e.getMessage()).getMsg();
        e.getChannel().write(buffer, inetSocketAddress);
    }

    public double hexToDouble(ChannelBuffer buffer) {
        byte[] bytes = new byte[8];
        byte[] b = buffer.readBytes(8).array();

        for (int i = 7; i >= 0; --i) {
            bytes[7 - i] = b[i];
        }

        ChannelBuffer cb = ChannelBuffers.buffer(bytes.length);
        cb.writeBytes(bytes);
        return cb.readDouble();
    }

    public int hexToShortInt(ChannelBuffer buffer) {
        byte[] bytes = new byte[2];
        byte[] b = buffer.readBytes(2).array();

        for (int i = 1; i >= 0; --i) {
            bytes[1 - i] = b[i];
        }

        ChannelBuffer cb = ChannelBuffers.buffer(bytes.length);
        cb.writeBytes(bytes);
        return cb.readUnsignedShort();
    }

    private byte[] serBuffer(byte[] bytes, int size) {
        ChannelBuffer buffer = ChannelBuffers.buffer(size);
        buffer.writeBytes(bytes);
        return buffer.array();
    }

    private void heartBeat(Message msg, ChannelHandlerContext ctx, MessageEvent e) {
        System.out.println("收到心跳请求，发送应答消息……");
        Message msgRep = new Message(JT809Constants.UP_LINKTEST_RSP);
        ChannelBuffer buffer = ChannelBuffers.buffer(0);
        msgRep.setMsgBody(buffer);
        e.getChannel().write(msgRep);
    }
}

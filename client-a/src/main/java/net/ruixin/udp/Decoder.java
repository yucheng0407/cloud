package net.ruixin.udp;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.socket.nio.NioDatagramChannel;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gai
 * 解码
 */
public class Decoder extends FrameDecoder {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    int count=0;
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
//        count++;
//        System.out.println(((NioDatagramChannel) channel).getLocalAddress().getPort()+"接到了"+count);
        Message message = null;
        byte[] b = buffer.array();
        ChannelBuffer cb = ChannelBuffers.buffer(b.length);
        cb.writeBytes(b);
        message = this.buildMessage(buffer.readBytes(buffer.readableBytes()));
        message.setMsg(cb.readBytes(61));
        return message;

    }

    private Message buildMessage(ChannelBuffer buffer) {
        Message msg = new Message();
        msg.setMsgHead(buffer.readUnsignedShort());
        msg.setMsgId(buffer.readUnsignedShort());
        msg.setMsgVer(buffer.readUnsignedShort());
        msg.setMsgSize(buffer.readUnsignedInt());
        ChannelBuffer bodyBytes = buffer.readBytes(51);
        msg.setMsgBody(bodyBytes);
//        logger.info(" readable bytes:" + buffer.readableBytes());
//        logger.info(" readable bytes:" + bodyBytes);
        return msg;
    }
}

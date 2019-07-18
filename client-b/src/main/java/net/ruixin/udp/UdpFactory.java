package net.ruixin.udp;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author gai
 * udp监听生成
 */
//@Component
public class UdpFactory implements DisposableBean {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private DatagramChannelFactory udpChannelFactory;
    private ConnectionlessBootstrap bootstrap;

    public UdpFactory(@Value("${netty.ports}") String ports) {
        String[] ports0 = ports.split(",");
        udpChannelFactory = new NioDatagramChannelFactory(
                new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                        60L, TimeUnit.SECONDS,
                        new SynchronousQueue<Runnable>()));
//        this.bootstrap = new ConnectionlessBootstrap(udpChannelFactory);
        this.bootstrap = new ConnectionlessBootstrap(udpChannelFactory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new Decoder());
                pipeline.addLast("busiHandler", new BusiHandler());
                return pipeline;
            }
        });
        //缓冲区10M(提高吞吐量)
        bootstrap.setOption("receiveBufferSize", 1048576*10);
//        bootstrap.setOption("sendBufferSize", 1048576*100);
        for (int i = 0; i < ports0.length; i++) {
            SocketAddress serverAddress = new InetSocketAddress(Integer.parseInt(ports0[i]));
            bootstrap.bind(serverAddress);
            logger.info(" Server is starting with port:"+ports0[i]);
        }
    }

    @Override
    public void destroy() throws Exception {
        udpChannelFactory.shutdown();
    }
}

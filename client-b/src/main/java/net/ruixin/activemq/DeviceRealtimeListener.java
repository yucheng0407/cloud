package net.ruixin.activemq;

import net.ruixin.domain.entity.DeviceRealtime;
import net.ruixin.domain.entity.DeviceStatus;
import net.ruixin.service.IDeviceRealtimeService;
import net.ruixin.udp.UdpThreadPool;
import net.ruixin.util.json.JacksonUtil;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gai
 *         消息消费者
 */
@Component
public class DeviceRealtimeListener implements InitializingBean, DisposableBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IDeviceRealtimeService deviceRealtimeService;
    @Autowired
    private UdpThreadPool udpThreadPool;

    @Value("${cacherun.maxcount}")
    private int maxCount;
    @Value("${cacherun.maxwait}")
    private int maxWait;
    private boolean exit = false;

    // 锁
    private final Lock lock = new ReentrantLock();

    // 缓存满的条件变量
    private final Condition full = lock.newCondition();
    // 缓存空的条件变量
    private final Condition empty = lock.newCondition();
    private CacheRun cacheRun;

    @Override
    public void afterPropertiesSet() {
        cacheRun = new CacheRun();
        new Thread(cacheRun).start();
    }

    @JmsListener(destination = DeviceRealtime.ACTIVEMQ_TAG, containerFactory = "jmsListenerContainerQueue")
    public void onMessage(ActiveMQTextMessage message) {
        try {
            lock.lock();
            while (udpThreadPool.getThreadCurCount() >= udpThreadPool.getThreadMaxCount()) {
//                System.out.println("爆线程了");
                message.getConnection().stop();
            }
            message.getConnection().start();
            String json = message.getText();
            DeviceRealtime deviceRealtime = JacksonUtil.readValue(json, DeviceRealtime.class);
            cacheRun.deviceRealtimes.add(deviceRealtime);
            if (cacheRun.deviceRealtimes.size() >= maxCount) {
                empty.signal();
                full.await();
            }
        } catch (Exception e) {
            logger.error("插入信息失败", e);
        } finally {
            lock.unlock();

        }
    }

    @Override
    public void destroy() throws Exception {
        exit = true;
        empty.signal();
    }

    class CacheRun implements Runnable {

        private List<DeviceRealtime> deviceRealtimes;
        private Long createTime;

        public CacheRun() {
            createTime = System.currentTimeMillis();
            deviceRealtimes = new ArrayList<>(DeviceRealtimeListener.this.maxCount + 100);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    DeviceRealtimeListener.this.lock.lock();
                    if (deviceRealtimes.size() >= DeviceRealtimeListener.this.maxCount
                            || (System.currentTimeMillis() - createTime >= DeviceRealtimeListener.this.maxWait && deviceRealtimes.size() > 0)) {
                        List<DeviceRealtime> deviceRealtimesCopy = new ArrayList<>(deviceRealtimes);
//                        Map<String, DeviceStatus> updateDeviceStatusCopy = new HashMap<>();
//                        updateDeviceStatusCopy.putAll(deviceRealtimeService.getUpdateDeviceStatus());
//                        List<DeviceStatus> insertDeviceStatusCopy = new ArrayList<>(deviceRealtimeService.getInsertDeviceStatus());
                        // DeviceRealtimeListener.this.deviceRealtimeService.saveDeviceRealtimes(deviceRealtimes);
                        // DeviceRealtimeListener.this.deviceRealtimeService.openSaveTread(deviceRealtimesCopy,updateDeviceStatusCopy,insertDeviceStatusCopy);
                        DeviceRealtimeListener.this.deviceRealtimeService.openSaveTread(deviceRealtimesCopy);
                        deviceRealtimes.clear();
//                        deviceRealtimeService.getUpdateDeviceStatus().clear();
//                        deviceRealtimeService.getInsertDeviceStatus().clear();
                        createTime = System.currentTimeMillis();
                    }
                    full.signal();
                    empty.await(System.currentTimeMillis() - createTime, TimeUnit.MILLISECONDS);
                    if (DeviceRealtimeListener.this.exit) {
                        return;
                    }
                } catch (Exception e) {
                    logger.error("插入信息失败", e);
                } finally {
                    DeviceRealtimeListener.this.lock.unlock();
                }
            }
        }
    }
//"0 0 1 15 * ?" 每月15日上午10:15触发
//  @Scheduled(fixedDelay = 10000)
    @Scheduled(cron="0 0 1 15 * ?") //每月15日凌晨1点触发
    private void job() {
        try {
            logger.info("job开始执行");
            deviceRealtimeService.deleteJob();
//            lock.lock();
//            connection.stop();
//            boolean a = true;
//            Date kssj = new Date();
//            while (a) {
//                if (udpThreadPool.getThreadCurCount() == 0) {
//                    while ((new Date().getTime() - kssj.getTime()) < 1000 * 60 * 15) {
//                        System.out.println("停留时间:" + (new Date().getTime() - kssj.getTime()));
//                    }
////                    deviceRealtimeService.deleteJob();
//                    a = false;
//                }
//            }
//            connection.start();
            logger.info("job执行完毕");
        } catch (Exception e) {
            logger.error("插入信息失败", e);
        }
    }
}

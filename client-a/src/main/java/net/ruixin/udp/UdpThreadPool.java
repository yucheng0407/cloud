package net.ruixin.udp;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yucheng on 2019/7/3.
 */
@Component
public class UdpThreadPool implements InitializingBean {
    private ThreadPoolExecutor threadPool;
    @Value("${threadMaxCount}")
    private int threadMaxCount;
    @Override
    public void afterPropertiesSet() {
        threadPool = new ThreadPoolExecutor(threadMaxCount, threadMaxCount,
                0L, TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>());
    }

    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }
    public int getThreadMaxCount() {
        return threadMaxCount;
    }
    public int getThreadCurCount() {
        return threadPool.getActiveCount();
    }
    public void setThreadPool(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }
}

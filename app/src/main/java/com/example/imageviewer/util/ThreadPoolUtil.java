package com.example.imageviewer.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @decription: 封装线程池为一个工具类
 */
public class ThreadPoolUtil {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE_TIME = 10L;
    private static final LinkedBlockingQueue<Runnable> S_WORK_QUEUE = new LinkedBlockingQueue<>(128);
    private static final ThreadFactory S_THREAD_FACTORY = new ThreadFactory() {
        // AtomicInteger用于原子递增计数器等应用程序，不能用作Integer的替代品。
        // 但是，此类确实扩展了Number以允许处理基于数字的类的工具和实用程序进行统一访问
        private final AtomicInteger mInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            //getAndIncrement():以原子方式将当前值加一,返回前一个值
            return new Thread(r, "ImageLoader#" + mInteger.getAndIncrement());
        }
    };

    public static final ThreadPoolExecutor S_THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, S_WORK_QUEUE, S_THREAD_FACTORY);
}

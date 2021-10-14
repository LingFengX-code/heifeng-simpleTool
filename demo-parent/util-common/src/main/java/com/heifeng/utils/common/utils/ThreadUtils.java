package com.heifeng.utils.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程工具包，待完善
 * @author  XLF
 */
public class ThreadUtils {

    private static final ExecutorService es = Executors.newFixedThreadPool(10);

    public static void execute(Runnable runnable) {
        es.submit(runnable);
    }
}

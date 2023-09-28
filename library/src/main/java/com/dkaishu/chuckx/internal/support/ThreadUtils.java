package com.dkaishu.chuckx.internal.support;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public final class ThreadUtils {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static ExecutorService sThreadExecutor = null;

    public static void run(Runnable runnable) {
        getThreadExecutor().execute(runnable);
    }

    public static void runOnUIThread(Runnable runnable) {
        getMainHandler().post(runnable);
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static Handler getMainHandler() {
        return HANDLER;
    }

    private static synchronized ExecutorService getThreadExecutor() {
        if (sThreadExecutor == null) {
            sThreadExecutor = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors());
        }
        return sThreadExecutor;
    }

}

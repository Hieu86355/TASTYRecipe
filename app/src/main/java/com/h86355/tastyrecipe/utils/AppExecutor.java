package com.h86355.tastyrecipe.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutor {
    private static AppExecutor instance;

    public static AppExecutor getInstance() {
        if (instance == null) {
            instance = new AppExecutor();
        }
        return instance;
    }

    private final ScheduledExecutorService netWorkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService netWorkIO() {
        return netWorkIO;
    }
}

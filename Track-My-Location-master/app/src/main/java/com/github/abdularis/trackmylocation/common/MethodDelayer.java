package com.github.abdularis.trackmylocation.common;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Runs a method at a future time
 */
public class MethodDelayer {
    private static final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();

    public static ScheduledFuture<?> run(Runnable runnable, long delay, TimeUnit timeUnit) {
        return worker.schedule(runnable, delay, timeUnit);
    }
}

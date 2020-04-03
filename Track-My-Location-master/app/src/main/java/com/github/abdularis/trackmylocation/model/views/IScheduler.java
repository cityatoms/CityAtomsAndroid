package com.github.abdularis.trackmylocation.model.views;

import io.reactivex.Scheduler;

public interface IScheduler {
    Scheduler mainThread();

    Scheduler backgroundThread();
}


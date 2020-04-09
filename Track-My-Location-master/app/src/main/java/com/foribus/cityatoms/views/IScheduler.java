package com.foribus.cityatoms.views;

import io.reactivex.Scheduler;

public interface IScheduler {
    Scheduler mainThread();

    Scheduler backgroundThread();
}


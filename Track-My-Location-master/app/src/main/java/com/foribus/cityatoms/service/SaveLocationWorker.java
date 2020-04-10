package com.foribus.cityatoms.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.foribus.cityatoms.database.DatabaseRepositoryManager;

public class SaveLocationWorker extends Worker {

    public SaveLocationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        Data data = getInputData();
        if (data != null) {
            DatabaseRepositoryManager.getInstance(getApplicationContext()).dataPointRepository()
                    .saveDataPoint(data.getDouble("lat", 0), data.getDouble("lon", 0));
            return Result.success();
        }

        return Result.failure();
    }
}

package com.foribus.cityatoms.database.datapoints;

import android.content.Context;

import com.foribus.cityatoms.common.SyncService;

import java.util.concurrent.Executors;

public class DataPointRepository extends SyncService<DataPoint> {

    // 1 minute
    private static final long SYNC_INTERVAL = 1000 * 60;

    private DataPointDao dataPointDao;

    public DataPointRepository(Context context) {
        super(SYNC_INTERVAL, Executors.newCachedThreadPool());

        dataPointDao = DataPointDatabase.getDatabase(context).dataPointDao();
    }

    @Override
    protected void add(DataPoint entity) {
        super.add(entity);
    }

    @Override
    protected void onEntityAdd(DataPoint entity) {
        dataPointDao.insert(entity);
    }

    @Override
    protected void sync() {

    }
}

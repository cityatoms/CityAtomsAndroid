package com.github.abdularis.trackmylocation.database.symptoms;

import android.content.Context;

import com.github.abdularis.trackmylocation.common.SyncService;

import java.util.concurrent.Executors;

public class SymptomsScoreRepository extends SyncService<SymptomsScore> {

    // 1 minute
    private static final long SYNC_INTERVAL = 1000 * 60;

    private SymptomsScoreDao symptomsScoreDao;

    public SymptomsScoreRepository(Context context) {
        super(SYNC_INTERVAL, Executors.newCachedThreadPool());

        symptomsScoreDao = SymptomsScoreDatabase.getDatabase(context).symptomsScoreDao();
    }

    @Override
    protected void add(SymptomsScore entity) {
        super.add(entity);
    }

    @Override
    protected void onEntityAdd(SymptomsScore entity) {
        symptomsScoreDao.insert(entity);
    }

    @Override
    protected void sync() {

    }
}

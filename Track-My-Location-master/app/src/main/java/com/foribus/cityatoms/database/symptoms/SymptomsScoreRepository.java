package com.foribus.cityatoms.database.symptoms;

import android.content.Context;

import com.foribus.cityatoms.common.SyncService;
import com.foribus.cityatoms.network.model.SymptomScores;

import java.util.concurrent.Executors;

public class SymptomsScoreRepository extends SyncService<SymptomsScoreEntity> {

    // 1 minute
    private static final long SYNC_INTERVAL = 1000 * 60;

    private SymptomsScoreDao symptomsScoreDao;

    public SymptomsScoreRepository(Context context) {
        super(SYNC_INTERVAL, Executors.newCachedThreadPool());

        symptomsScoreDao = SymptomsScoreDatabase.getDatabase(context).symptomsScoreDao();
    }

    public SymptomsScoreEntity getLatestSymptomScoreInfo() {
        return symptomsScoreDao.getLatestSymptomScoreInfo();
    }

    public SymptomsScoreEntity getSymptomScoreInfo(int id) {
        return symptomsScoreDao.getSymptomScoreInfo(id);
    }

    public void saveDataPoint(SymptomScores symptomScores) {
        add(symptomScores.toEntity());
    }

    @Override
    protected void add(SymptomsScoreEntity entity) {
        super.add(entity);
    }

    @Override
    protected void onEntityAdd(SymptomsScoreEntity entity) {
        symptomsScoreDao.insert(entity);
    }

    @Override
    protected void sync() {

    }
}

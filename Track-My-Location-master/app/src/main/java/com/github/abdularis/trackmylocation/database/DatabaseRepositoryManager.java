package com.github.abdularis.trackmylocation.database;

import android.content.Context;

import com.github.abdularis.trackmylocation.database.datapoints.DataPointRepository;
import com.github.abdularis.trackmylocation.database.symptoms.SymptomsScoreRepository;

public class DatabaseRepositoryManager {

    private static DatabaseRepositoryManager instance;
    private Context mContext;

    private DataPointRepository dataPointRepository;
    private SymptomsScoreRepository symptomsScoreRepository;

    private DatabaseRepositoryManager(Context context) {
        mContext = context;
    }

    public static DatabaseRepositoryManager getInstance(final Context context) {
        if (instance == null) {
            synchronized (DatabaseRepositoryManager.class) {
                if (instance == null) {
                    instance = new DatabaseRepositoryManager(context);
                }
            }
        }
        return instance;
    }

    public DataPointRepository dataPointRepository() {
        if (dataPointRepository == null) {
            dataPointRepository = new DataPointRepository(mContext);
        }
        return dataPointRepository;
    }

    public SymptomsScoreRepository symptomsScoreRepository() {
        if (symptomsScoreRepository == null) {
            symptomsScoreRepository = new SymptomsScoreRepository(mContext);
        }
        return symptomsScoreRepository;
    }
}

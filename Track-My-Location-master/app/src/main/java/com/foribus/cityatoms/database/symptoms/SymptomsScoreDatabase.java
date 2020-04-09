package com.foribus.cityatoms.database.symptoms;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SymptomsScore.class}, version = 1, exportSchema = false)
abstract class SymptomsScoreDatabase extends RoomDatabase {

    private static final String DB_NAME = "symptoms_score_database";

    private static SymptomsScoreDatabase instance;

    public static SymptomsScoreDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (SymptomsScoreDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), SymptomsScoreDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract SymptomsScoreDao symptomsScoreDao();
}

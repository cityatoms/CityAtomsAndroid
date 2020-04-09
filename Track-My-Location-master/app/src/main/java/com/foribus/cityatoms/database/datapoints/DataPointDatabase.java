package com.foribus.cityatoms.database.datapoints;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DataPoint.class}, version = 1, exportSchema = false)
abstract class DataPointDatabase extends RoomDatabase {

    private static final String DB_NAME = "data_points_database";

    private static DataPointDatabase instance;

    public static DataPointDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (DataPointDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), DataPointDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract DataPointDao dataPointDao();
}

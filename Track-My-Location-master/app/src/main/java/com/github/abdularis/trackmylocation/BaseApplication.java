package com.github.abdularis.trackmylocation;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.github.abdularis.trackmylocation.entity.DaoMaster;
import com.github.abdularis.trackmylocation.entity.DaoSession;

import lombok.Getter;

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication baseApplication;
    @Getter
    private SharedPreferences preferences;
    @Getter
    private DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = (BaseApplication) getApplicationContext();
        preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "track-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public BaseApplication() {
        super();
    }

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }
}

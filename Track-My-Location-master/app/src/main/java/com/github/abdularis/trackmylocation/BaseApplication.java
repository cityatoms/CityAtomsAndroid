package com.github.abdularis.trackmylocation;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.github.abdularis.trackmylocation.dagger.ApiComponent;
import com.github.abdularis.trackmylocation.dagger.DaggerApiComponent;
import com.github.abdularis.trackmylocation.entity.DaoMaster;
import com.github.abdularis.trackmylocation.entity.DaoSession;
import com.github.abdularis.trackmylocation.network.ApiClientModule;

import lombok.Getter;

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication baseApplication;
    @Getter
    private SharedPreferences preferences;
    @Getter
    private DaoSession daoSession;

    private ApiComponent mApiComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = (BaseApplication) getApplicationContext();
        preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "track-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        mApiComponent = DaggerApiComponent.builder()
                .apiClientModule(new ApiClientModule("http://ec2-3-12-160-215.us-east-2.compute.amazonaws.com:3000/api/v1/me/"))
                .build();
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

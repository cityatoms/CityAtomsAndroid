package com.foribus.cityatoms;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import timber.log.Timber;

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication baseApplication;

    private SharedPreferences preferences;

    public BaseApplication() {
        super();
    }

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        baseApplication = (BaseApplication) getApplicationContext();
        preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }
}

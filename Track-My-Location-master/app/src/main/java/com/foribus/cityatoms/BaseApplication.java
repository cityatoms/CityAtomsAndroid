package com.foribus.cityatoms;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import lombok.Getter;

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication baseApplication;

    @Getter
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
        baseApplication = (BaseApplication) getApplicationContext();
        preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        Log.i("test", "onCreate: "+getPackageName());
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

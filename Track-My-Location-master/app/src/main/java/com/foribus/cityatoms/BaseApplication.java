package com.foribus.cityatoms;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.foribus.cityatoms.common.IPreferencesKeys;

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

    public String getFirebaseInstanceId() {
        return preferences.getString(IPreferencesKeys.USER_ID, null);
    }
}

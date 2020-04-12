package com.foribus.cityatoms;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.foribus.cityatoms.common.IPreferencesKeys;
import com.mapbox.mapboxsdk.Mapbox;

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

        Mapbox.getInstance(this, "pk.eyJ1IjoiY2l0eWF0b21zIiwiYSI6ImNrOHI3cnJibTAwanAzbHFic2V5dWt6NTEifQ.0qXroHLjGoQq-MWxr_Uu7Q");
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

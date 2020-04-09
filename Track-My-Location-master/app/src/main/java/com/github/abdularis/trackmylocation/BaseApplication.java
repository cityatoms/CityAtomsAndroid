package com.github.abdularis.trackmylocation;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.github.abdularis.trackmylocation.dagger.ApiComponent;
import com.github.abdularis.trackmylocation.dagger.DaggerApiComponent;
import com.github.abdularis.trackmylocation.network.ApiClientModule;

import lombok.Getter;

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication baseApplication;
    @Getter
    private SharedPreferences preferences;
    @Getter
    private ApiComponent ApiComponent;

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

        ApiComponent = DaggerApiComponent.builder()
                .apiClientModule(new ApiClientModule("http://ec2-3-12-160-215.us-east-2.compute.amazonaws.com:3000/me/"))
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public ApiComponent getApiComponent() {
        return ApiComponent;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }
}

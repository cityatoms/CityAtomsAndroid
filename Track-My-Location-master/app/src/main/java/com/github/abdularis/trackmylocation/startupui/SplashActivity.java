package com.github.abdularis.trackmylocation.startupui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.github.abdularis.trackmylocation.common.IPreferencesKeys;
import com.github.abdularis.trackmylocation.dashboard.BaseActivity;
import com.github.abdularis.trackmylocation.dashboard.MainActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences preferences =
                getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        Class classObj = preferences.contains(IPreferencesKeys.USER_ID)
                ? MainActivity.class : StartupActivity.class;
        Intent i = new Intent(SplashActivity.this, classObj);
        startActivity(i);
        finish();
    }
}

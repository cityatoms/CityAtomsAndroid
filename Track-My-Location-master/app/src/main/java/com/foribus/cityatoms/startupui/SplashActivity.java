package com.foribus.cityatoms.startupui;

import android.content.Intent;
import android.os.Bundle;

import com.foribus.cityatoms.BaseApplication;
import com.foribus.cityatoms.common.IPreferencesKeys;
import com.foribus.cityatoms.dashboard.BaseActivity;
import com.foribus.cityatoms.dashboard.MainActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (BaseApplication.getBaseApplication().getPreferences().contains(IPreferencesKeys.USER_ID)) {
            if (BaseApplication.getBaseApplication().getPreferences().getBoolean(IPreferencesKeys.FIRST_TIME_SYMPTOMS_SAVED, false)) {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
            } else {
                Intent i = new Intent(SplashActivity.this, GetSymptoms.class);
                startActivity(i);
            }
        } else {
            Intent i = new Intent(SplashActivity.this, AnonymousLogin.class);
            startActivity(i);
        }
        finish();
    }
}

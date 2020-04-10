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

        Class classObj = BaseApplication.getBaseApplication().getPreferences().contains(IPreferencesKeys.USER_ID)
                ? MainActivity.class : AnonymousLogin.class;
        Intent i = new Intent(SplashActivity.this, classObj);
        startActivity(i);
        finish();
    }
}

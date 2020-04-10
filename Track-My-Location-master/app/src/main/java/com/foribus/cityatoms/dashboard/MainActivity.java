package com.foribus.cityatoms.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.foribus.cityatoms.R;
import com.foribus.cityatoms.common.IPreferencesKeys;
import com.foribus.cityatoms.permission.LocationPermissionManager;
import com.foribus.cityatoms.startupui.StartupActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    HomeFragment homeFragment;
    PersonalInfoFragment personalInfoFragment;

    LocationPermissionManager locationPermissionManager;

    private BottomNavigationView.OnNavigationItemSelectedListener navListner = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.nav_home:
                selectedFragment = getHomeFragment();
                break;
            case R.id.nav_daily:
                selectedFragment = getPersonalInfoFragment();
                break;
        }
        initFragment(selectedFragment);
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if it goes here user is already logged in
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
        initFragment(getHomeFragment());

        locationPermissionManager = new LocationPermissionManager(this);
        locationPermissionManager.requestPermissions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void goToStartupActivity() {
        Intent i = new Intent(this, StartupActivity.class);
        startActivity(i);
        finish();
    }

    private void initFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (fragment != null && !supportFragmentManager.isDestroyed()) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.basic_fragment, fragment).commitAllowingStateLoss();
        }
    }

    public void addFragment(Fragment fragment, String TAG) {
        if (fragment == null) return;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (!supportFragmentManager.isDestroyed()) {
            FragmentTransaction fragTransaction = supportFragmentManager.beginTransaction();
            fragTransaction.replace(R.id.basic_fragment, fragment, TAG);
            fragTransaction.addToBackStack(TAG);
            fragTransaction.commitAllowingStateLoss();
        }
    }

    private Fragment getHomeFragment() {
        if (homeFragment == null) {
            final SharedPreferences preferences =
                    getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            String country = preferences.
                    getString(IPreferencesKeys.COUNTRY, "");
            String utc = preferences.
                    getString(IPreferencesKeys.TIME_ZONE, "");
            homeFragment = new HomeFragment();
            homeFragment.setArguments(getIntent().getExtras());
        }
        return homeFragment;
    }

    private Fragment getPersonalInfoFragment() {
        if (personalInfoFragment == null) {
            personalInfoFragment = new PersonalInfoFragment();
            personalInfoFragment.setArguments(getIntent().getExtras());
        }
        return personalInfoFragment;
    }
}

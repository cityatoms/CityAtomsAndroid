package com.foribus.cityatoms.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.foribus.cityatoms.R;
import com.foribus.cityatoms.common.IPreferencesKeys;
import com.foribus.cityatoms.permission.LocationPermissionManager;
import com.foribus.cityatoms.startupui.StartupActivity;
import com.foribus.cityatoms.startupui.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    HomeFragment homeFragment;
    PersonalInfoFragment personalInfoFragment;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    LocationPermissionManager locationPermissionManager;
    private TabLayout mTabLayout;
    private ViewPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Context context;
    AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if it goes here user is already logged in
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.app_bar_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_home, R.id.health, R.id.heat_n,R.id.about,R.id.service,R.id.cokies,R.id.action_out)
                        .setDrawerLayout(drawer)
                        .build();
                NavController navController;
                navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController(MainActivity.this, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);

            }
        });
        locationPermissionManager = new LocationPermissionManager(this);
        locationPermissionManager.requestPermissions();
        ButterKnife.bind(this);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);

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

    public void initFragment(Fragment fragment) {
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

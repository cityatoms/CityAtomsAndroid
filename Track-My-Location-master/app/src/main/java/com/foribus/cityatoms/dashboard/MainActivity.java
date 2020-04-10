package com.foribus.cityatoms.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.foribus.cityatoms.R;
import com.foribus.cityatoms.permission.LocationPermissionManager;
import com.google.android.material.navigation.NavigationView;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    LocationPermissionManager locationPermissionManager;

    AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if it goes here user is already logged in
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.app_bar_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_heat_map, R.id.nav_health_monitor, R.id.nav_about, R.id.nav_privacy, R.id.nav_services, R.id.nav_cookies, R.id.nav_sign_out)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(MainActivity.this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        locationPermissionManager = new LocationPermissionManager(this);
        locationPermissionManager.requestPermissions();
        ButterKnife.bind(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

package com.foribus.cityatoms.dashboard;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.foribus.cityatoms.BaseApplication;
import com.foribus.cityatoms.R;
import com.foribus.cityatoms.database.DatabaseRepositoryManager;
import com.foribus.cityatoms.firebase.FirebaseAuthHelper;
import com.foribus.cityatoms.permission.LocationPermissionManager;
import com.foribus.cityatoms.service.LocationForegroundService;
import com.foribus.cityatoms.startupui.GetSymptoms;
import com.foribus.cityatoms.startupui.SplashActivity;
import com.google.android.material.navigation.NavigationView;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    LocationPermissionManager locationPermissionManager;
    AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    boolean checkSignin = false;

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
        navigationView.getMenu().findItem(R.id.nav_sign_out).
                setOnMenuItemClickListener(menuItem -> {
                    confirmAndDoSignOut();
                    return true;
                });
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_heat_map, R.id.nav_health_monitor, R.id.nav_about, R.id.nav_privacy, R.id.nav_services, R.id.nav_cookies, R.id.nav_sign_out)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
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

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_sign_out) {
            confirmAndDoSignOut();
            return false;
        }
        return true;
    }

    private void confirmAndDoSignOut() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Sure want to sign out?");
        dialog.setPositiveButton("Sign out", (paramDialogInterface, paramInt) -> {
            showProgressBar("Please wait");
            AsyncTask.execute(() -> {
                BaseApplication.getBaseApplication().getPreferences().edit().clear().apply();
                DatabaseRepositoryManager.getInstance(getApplicationContext()).wipeData();
                FirebaseAuthHelper.doAnonymouslySignOut();
                runOnUiThread(() -> {
                    hideProgressBar();
                    Intent intent = new Intent(MainActivity.this, LocationForegroundService.class);
                    stopService(intent);
                    intent = new Intent(this, SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    checkSignin = false;
                    finish();
                });
            });
        });
        dialog.setNegativeButton("Cancel", (paramDialogInterface, paramInt) -> {
        });
        dialog.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (navController.getCurrentDestination().getId() == R.id.nav_health_monitor) {
            getMenuInflater().inflate(R.menu.menu_option_main_nav, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_edit) {
            Intent intent = new Intent(this, GetSymptoms.class);
            intent.putExtra(GetSymptoms.SOURCE_CALLER, true);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
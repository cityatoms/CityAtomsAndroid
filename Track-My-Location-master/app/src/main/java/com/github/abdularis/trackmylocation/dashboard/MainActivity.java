package com.github.abdularis.trackmylocation.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.common.IPreferencesKeys;
import com.github.abdularis.trackmylocation.startupui.StartupActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    public static final String MESSENGER_INTENT_KEY = "msg-intent-key";
    public static final String MESSAGE_STATUS = "Sync_User_data";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    HealthMonitorFragment healthMonitorFragment;
    DailySymptomsFragment dailySymptomsFragment;
    PersonalInfoFragment personalInfoFragment;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private BottomNavigationView.OnNavigationItemSelectedListener navListner = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new DailySymptomsFragment();
                break;

        }
        initFragment(selectedFragment);
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check for authentication
       /* mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            goToStartupActivity();
            return;
        }
        mAuth.addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() == null) {
                goToStartupActivity();
            }
        });*/

        // if it goes here user is already logged in
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawerlayout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        NavigationView navigationView=findViewById(R.id.nav_View);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id =item.getItemId();
                if (id==R.id.heat)
                {
                    Toast.makeText(MainActivity.this, "heat", Toast.LENGTH_SHORT).show();
                }
                else if (id==R.id.health)
                {
                    Toast.makeText(MainActivity.this, "heat", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.heat)
                {
                    Toast.makeText(MainActivity.this, "heat", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.heat)
                {
                    Toast.makeText(MainActivity.this, "heat", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.heat)
                {
                    Toast.makeText(MainActivity.this, "heat", Toast.LENGTH_SHORT).show();
                }else if (id==R.id.heat)
                {
                    Toast.makeText(MainActivity.this, "heat", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        ButterKnife.bind(this);

        initFragment(new GetSymptomsFirstFragment());
        requestPermissions();
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            boolean permissionAccessCoarseLocationApproved =
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED;

            if (permissionAccessCoarseLocationApproved) {
                boolean backgroundLocationPermissionApproved =
                        false;

                backgroundLocationPermissionApproved = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

                if (backgroundLocationPermissionApproved) {
                    // start location foreground service
                } else {
                    // App can only access location in the foreground. Display a dialog
                    // warning the user that your app must have all-the-time access to
                    // location in order to function properly. Then, request background
                    // location.
                    ActivityCompat.requestPermissions(this, new String[]{
                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                            REQUEST_PERMISSIONS_REQUEST_CODE);
                }
            } else {
                // App doesn't have access to the device's location at all. Make full request
                // for permission.
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        },
                        REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        } else if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                finish();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // start location foreground service
            } else {
                finish();
            }
        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    },
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
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

    private Fragment getHealthMonitorFragment() {
        if (healthMonitorFragment == null) {
            final SharedPreferences preferences =
                    getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            String country = preferences.
                    getString(IPreferencesKeys.COUNTRY, "");
            String utc = preferences.
                    getString(IPreferencesKeys.TIME_ZONE, "");
            healthMonitorFragment = new HealthMonitorFragment();
            healthMonitorFragment.setArguments(getIntent().getExtras());
        }
        return healthMonitorFragment;
    }

    private Fragment getPersonalInfoFragment() {
        if (personalInfoFragment == null) {
            personalInfoFragment = new PersonalInfoFragment();
            personalInfoFragment.setArguments(getIntent().getExtras());
        }
        return personalInfoFragment;
    }

}

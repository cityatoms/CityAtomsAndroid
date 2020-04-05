package com.github.abdularis.trackmylocation.dashboard;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.abdularis.trackmylocation.BaseApplication;
import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.common.IPreferencesKeys;
import com.github.abdularis.trackmylocation.entity.DaoSession;
import com.github.abdularis.trackmylocation.entity.LocationData;
import com.github.abdularis.trackmylocation.entity.LocationDataDao;
import com.github.abdularis.trackmylocation.sharelocation.LocationUpdatesService;
import com.github.abdularis.trackmylocation.startupui.StartupActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import freemarker.template.utility.Constants;
import lombok.Getter;

public class MainActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    HomeFragment homeFragment;
    DailySymptomsFragment dailySymptomsFragment;
    PersonalInfoFragment personalInfoFragment;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    public static final String MESSENGER_INTENT_KEY = "msg-intent-key";
    private IncomingMessageHandler mHandler;
    private LocationDataDao locationDataDao;
    private List<LocationData> locationDataList;
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
        ButterKnife.bind(this);
        DaoSession daoSession = BaseApplication.getBaseApplication().getDaoSession();
        locationDataDao = daoSession.getLocationDataDao();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
        mHandler = new IncomingMessageHandler();
        getLocationData();
        initFragment(getHomeFragment());
        requestPermissions();
    }

    class IncomingMessageHandler extends Handler {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            Log.i("TAG", "handleMessage..." + msg.toString());

            super.handleMessage(msg);

            switch (msg.what) {
                case LocationUpdatesService.LOCATION_MESSAGE:
                    Location obj = (Location) msg.obj;
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    double lat = obj.getLatitude();
                    double lon = obj.getLongitude();
                    System.out.println(lat+" "+lon+ " "+currentDateTimeString);
                    break;
            }
        }
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
                    // App can access location both in the foreground and in the background.
                    // Start your service that doesn't have a foreground service type
                    // defined.
                   // if (!isMyServiceRunning("LocationUpdatesService.class")) {

                        Intent startServiceIntent = new Intent(this, LocationUpdatesService.class);
                        Messenger messengerIncoming = new Messenger(mHandler);
                        startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming);
                        getApplicationContext().startForegroundService(startServiceIntent);
                  //  }
                } else {
                    // App can only access location in the foreground. Display a dialog
                    // warning the user that your app must have all-the-time access to
                    // location in order to function properly. Then, request background
                    // location.
                    ActivityCompat.requestPermissions(this, new String[] {
                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                            REQUEST_PERMISSIONS_REQUEST_CODE);
                }
            } else {
                // App doesn't have access to the device's location at all. Make full request
                // for permission.
                ActivityCompat.requestPermissions(this, new String[] {
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        },
                        REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        } else if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                finish();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //if (!isMyServiceRunning("LocationUpdatesService.class")) {
                    Intent startServiceIntent = new Intent(this, LocationUpdatesService.class);
                    Messenger messengerIncoming = new Messenger(mHandler);
                    startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming);
                    startService(startServiceIntent);
                //}
            } else {
                finish();
            }
        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(this, new String[] {
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
                    getString(IPreferencesKeys.COUNTRY, String.valueOf(Constants.EMPTY_STRING));
            String utc = preferences.
                    getString(IPreferencesKeys.TIME_ZONE, String.valueOf(Constants.EMPTY_STRING));
            homeFragment = new HomeFragment();
            homeFragment.setArguments(getIntent().getExtras());
            homeFragment.setLocationDataList(locationDataList);
            homeFragment.setUserData(country + "\n" + utc );
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

    private void getLocationData() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        String hour = format.format(date).substring(0,2);
            locationDataList = locationDataDao
                    .queryBuilder().where(LocationDataDao.Properties.Hour.eq(hour)).list();
            if (locationDataList.size() > 0) {
                System.out.println(locationDataList);
            }
    }

    public boolean isMyServiceRunning(String serviceClassName){
        final ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)){
                return true;
            }
        }
        return false;
    }
}

package com.foribus.cityatoms.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.foribus.cityatoms.permission.AndroidPermissionChecker;
import com.foribus.cityatoms.permission.PermissionChecker;
import com.foribus.cityatoms.permission.PermissionType;

import timber.log.Timber;

/**
 * Check permission on activity resume and let observers know when changed
 * Mainly for alert box
 */
public class LocationStatus {

    private Context context;
    private PermissionChecker permissionChecker;
    private LocationLiveData locationLiveData;

    private MutableLiveData<Boolean> GpsLocationServiceStatus;

    private boolean locationUpdateRequested = false;
    private BroadcastReceiver gpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {

                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (isGpsEnabled || isNetworkEnabled) {
                    // Handle Location turned ON
                    Timber.d("GPS TURNED ON");
                    GpsLocationServiceStatus.postValue(true);
                } else {
                    // Handle Location turned OFF
                    Timber.d("GPS TURNED OFF");
                    GpsLocationServiceStatus.postValue(false);
                }
            }
        }
    };

    public LocationStatus(Context context, Looper looper) {
        this.context = context;
        permissionChecker = new AndroidPermissionChecker(context);
        locationLiveData = new LocationLiveData(context, permissionChecker, looper);
        GpsLocationServiceStatus = new MutableLiveData<>();
        GpsLocationServiceStatus.postValue(false);

        IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        context.registerReceiver(gpsSwitchStateReceiver, filter);
    }

    public void unregisterReceiver() {
        context.unregisterReceiver(gpsSwitchStateReceiver);
    }

    public LocationLiveData getLocationLiveData() {
        return locationLiveData;
    }

    public LiveData<Boolean> getGpsLocationServiceStatus() {
        return GpsLocationServiceStatus;
    }

    public void onActivityResume() {
        // checkLocationPermission();
        // checkLocationService();

        boolean granted = permissionChecker.hasPermission(PermissionType.LOCATION);
        boolean isOn = locationLiveData.isLocationServiceOn();
        if (granted && isOn && !locationUpdateRequested) {
            locationUpdateRequested = true;
            locationLiveData.requestLocationChangeUpdate();
        } else if (locationUpdateRequested && (!granted || !isOn)) {
            locationUpdateRequested = false;
        }
    }

    public boolean hasLocationBackgroundPermission() {
        return permissionChecker.hasPermission(PermissionType.LOCATION_BACKGROUND);
    }
}
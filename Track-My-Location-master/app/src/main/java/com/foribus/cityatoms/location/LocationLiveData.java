package com.foribus.cityatoms.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Looper;
import android.provider.Settings;

import androidx.lifecycle.LiveData;

import com.foribus.cityatoms.permission.PermissionChecker;
import com.foribus.cityatoms.permission.PermissionType;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import timber.log.Timber;

public class LocationLiveData extends LiveData<Location> {
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     * 5 minutes default
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    /**
     * Minimum amount of meters between location updates
     */
    private static final float SMALLEST_DISPLACEMENT = 25.0f;

    private Context context;
    private FusedLocationProviderClient locationClient;
    private LocationCallback locationCallback;
    private boolean active;
    private PermissionChecker permissionChecker;

    private Looper looper;

    public LocationLiveData(Context context, PermissionChecker permissionChecker, Looper looper) {
        this.looper = looper;
        this.context = context.getApplicationContext();
        this.permissionChecker = permissionChecker;
        locationClient = LocationServices.getFusedLocationProviderClient(context);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Timber.d("New Location: %s", locationResult.getLastLocation().toString());
                postValue(locationResult.getLastLocation());
            }
        };
    }

    public void requestLocationChangeUpdate() {
        Timber.i("Requesting location updates");
        requestLastLocation();
        requestLocationUpdates();
    }

    public boolean isLocationServiceOn() {
        int locationMode = 0;

        try {
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException ignored) {
            Timber.w("Location mode setting not found");
        }

        return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }

    public void enableLocationSettings(Activity activity) {
        LocationRequest locationRequest = createLocationRequest();
        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build();
        LocationServices.getSettingsClient(activity)
                .checkLocationSettings(locationSettingsRequest)
                .addOnFailureListener(activity, e -> {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException rae = (ResolvableApiException) e;
                                // Show settings dialog
                                rae.startResolutionForResult(activity, 0);
                            } catch (IntentSender.SendIntentException sie) {
                                Timber.e(sie);
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            activity.startActivity(myIntent);
                    }
                });
    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();

        if (active) {
            stopLocationUpdates();
        }
    }

    private void requestLocationUpdates() {
        if (permissionChecker.hasPermission(PermissionType.LOCATION)) {
            active = true;
            LocationRequest locationRequest = createLocationRequest();
            locationClient.requestLocationUpdates(locationRequest, locationCallback, looper);
        }
    }

    private void stopLocationUpdates() {
        active = false;
        locationClient.removeLocationUpdates(locationCallback);
    }

    private void requestLastLocation() {
        if (permissionChecker.hasPermission(PermissionType.LOCATION)) {
            active = true;
            locationClient.getLastLocation().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    Timber.d("Last Location: %s", task.getResult().toString());
                    postValue(task.getResult());
                }
            });
        }
    }

    private LocationRequest createLocationRequest() {
        return LocationRequest.create()
                .setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
                .setSmallestDisplacement(SMALLEST_DISPLACEMENT)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
}

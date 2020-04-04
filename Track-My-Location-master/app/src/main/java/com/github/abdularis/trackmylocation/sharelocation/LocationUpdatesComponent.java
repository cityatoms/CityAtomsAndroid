package com.github.abdularis.trackmylocation.sharelocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.github.abdularis.trackmylocation.BaseApplication;
import com.github.abdularis.trackmylocation.entity.DaoSession;
import com.github.abdularis.trackmylocation.entity.LocationData;
import com.github.abdularis.trackmylocation.entity.LocationDataDao;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.security.acl.LastOwnerException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.Getter;


/**
 * stand alone component for location updates
 */
public class LocationUpdatesComponent {

    @Getter
    private LocationDataDao locationDataDao;
    private static final String TAG = LocationUpdatesComponent.class.getSimpleName();

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 60 * 1000;

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private LocationRequest mLocationRequest;

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Callback for changes in location.
     */
    private LocationCallback mLocationCallback;

    /**
     * The current location.
     */
    private Location mLocation;

    public ILocationProvider iLocationProvider;
    Context context;

    public LocationUpdatesComponent(ILocationProvider iLocationProvider) {
        this.iLocationProvider = iLocationProvider;
    }

    /**
     * create first time to initialize the location components
     *
     * @param context
     */
    public void onCreate(Context context) {
        this.context = context;
        Log.i(TAG, "created...............");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        DaoSession daoSession = BaseApplication.getBaseApplication().getDaoSession();
        locationDataDao = daoSession.getLocationDataDao();
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.i(TAG, "onCreate...onLocationResult...............loc " + locationResult.getLastLocation());
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                double lat = locationResult.getLastLocation().getLatitude();
                double lon = locationResult.getLastLocation().getLongitude();
                saveLocationData(currentDateTimeString,Double.toString(lon),Double.toString(lat));
                onNewLocation(locationResult.getLastLocation());
            }
        };
        // create location request
        createLocationRequest();
        getLastLocation();

    }

    private void saveLocationData(String timestamp, String lon, String lat) {
        LocationData locationData = new LocationData();
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        locationData.setHour(format.format(date).substring(0,2));
        locationData.setTimestamp(timestamp);
        locationData.setLatitude(lat);
        locationData.setLongitude(lon);
        locationDataDao.insert(locationData);
    }

    /**
     * start location updates
     */
    public void onStart() {
        Log.i(TAG, "onStart ");
        //hey request for location updates
        requestLocationUpdates();
    }

    /**
     * remove location updates
     */
    public void onStop() {
        Log.i(TAG, "onStop....");
        removeLocationUpdates();
    }

    /**
     * Makes a request for location updates. Note that in this sample we merely log the
     * {@link SecurityException}.
     */
    public void requestLocationUpdates() {
        Log.i(TAG, "Requesting location updates");
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, Looper.getMainLooper());
        } catch (SecurityException unlikely) {
            Log.e(TAG, "Lost location permission. Could not request updates. " + unlikely);
        }
    }

    /**
     * Removes location updates. Note that in this sample we merely log the
     * {@link SecurityException}.
     */
    public void removeLocationUpdates() {
        Log.i(TAG, "Removing location updates");
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
//            Utils.setRequestingLocationUpdates(this, false);
//            stopSelf();
        } catch (SecurityException unlikely) {
//            Utils.setRequestingLocationUpdates(this, true);
            Log.e(TAG, "Lost location permission. Could not remove updates. " + unlikely);
        }
    }

    /**
     * get last location
     */
    private void getLastLocation() {
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLocation = task.getResult();
                                Log.i(TAG, "getLastLocation " + mLocation);
                                onNewLocation(mLocation);
                            } else {
                                //requestLocationUpdates();
                                Log.w(TAG, "Failed to get location.");
                            }
                        }
                    });
        } catch (SecurityException unlikely) {
            Log.e(TAG, "Lost location permission." + unlikely);
        }
    }

    private void onNewLocation(Location location) {
        Log.i(TAG, "New location: " + location);
//        Toast.makeText(getApplicationContext(), "onNewLocation " + location, Toast.LENGTH_LONG).show();

        mLocation = location;
        if (this.iLocationProvider != null) {
            this.iLocationProvider.onLocationUpdate(mLocation);
        }
    }

    /**
     * Sets the location request parameters.
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    /**
     * implements this interface to get call back of location changes
     */
    public interface ILocationProvider {
        void onLocationUpdate(Location location);
    }
}
package com.github.abdularis.trackmylocation.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;

import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.location.LocationStatus;
import com.github.abdularis.trackmylocation.startupui.SplashActivity;

import timber.log.Timber;

public class LocationForegroundService extends LifecycleService {

    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    private LocationStatus locationStatus;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);


        locationStatus = new LocationStatus(getApplicationContext(), Looper.myLooper());

        locationStatus.getGpsLocationServiceStatus().observe(this, aBoolean -> {
            if (aBoolean) {
                if (locationStatus.hasLocationBackgroundPermission()) {
                    locationStatus.onActivityResume();
                } else {
                    Timber.d("timer task with location background permission false");
                }
            }
        });

        locationStatus.getLocationLiveData().observe(this, location -> {
            Timber.i("GETTING LOCATION IN BACKGROUND: %s", location.toString());
        });

        if (locationStatus.hasLocationBackgroundPermission()) {
            locationStatus.onActivityResume();
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationStatus.unregisterReceiver();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
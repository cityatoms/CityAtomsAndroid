package com.foribus.cityatoms.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.foribus.cityatoms.service.LocationForegroundService;

public class LocationPermissionManager {

    private PermissionChecker permissionChecker;
    private PermissionRequester permissionRequester;

    private Activity activity;

    public LocationPermissionManager(Activity activity) {
        this.activity = activity;
        permissionChecker = new AndroidPermissionChecker(this.activity);
        permissionRequester = new AndroidPermissionRequester(this.activity);
    }

    public void requestPermissions() {
        if (!permissionChecker.hasPermissionBeenShown(PermissionType.LOCATION)) {
            // Request location only if never requested before
            permissionRequester.requestPermission(PermissionType.LOCATION);
        } else if (!permissionRequester.shouldShowPermissionRationale(PermissionType.LOCATION)) {
            permissionRequester.requestPermission(PermissionType.LOCATION);
        } else {
            permissionRequester.openPermissionSettings();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0) {
            boolean granted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

            PermissionType permissionType = null;
            switch (requestCode) {
                case PermissionRequester.LOCATION_REQUEST_CODE:
                    permissionType = PermissionType.LOCATION;
                    startService();
                    break;
                case PermissionRequester.WRITE_STORAGE_REQUEST_CODE:
                    permissionType = PermissionType.STORAGE;
                    break;
            }
            permissionChecker.updatePermissionState(permissionType);

            if (!granted) {
                showSettingAlertPermission();
            }
        }

        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
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
        }*/
    }

    private void showSettingAlertPermission() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setMessage("To use app, the app needs to access location of the device. Do you want to enable location permission?");
        dialog.setPositiveButton("Settings", (paramDialogInterface, paramInt) -> {
            permissionRequester.openPermissionSettings();
        });
        dialog.setNegativeButton("Cancel", (paramDialogInterface, paramInt) -> {
        });
        dialog.show();
    }

    private void startService() {
        Intent serviceIntent = new Intent(activity, LocationForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");

        ContextCompat.startForegroundService(activity, serviceIntent);
    }
}

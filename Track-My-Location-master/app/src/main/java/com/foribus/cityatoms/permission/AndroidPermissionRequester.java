package com.foribus.cityatoms.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.foribus.cityatoms.common.DialogViewModel;

public class AndroidPermissionRequester implements PermissionRequester {
    private Activity activity;
    // Value false = have not been requested, true = requested already
    private SharedPreferences preferences;

    public AndroidPermissionRequester(Activity activity) {
        this.activity = activity;
        preferences = activity.getSharedPreferences(PermissionPreferenceKeys.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void requestPermission(PermissionType permissionType) {
        switch (permissionType) {
            case LOCATION:
                if (!preferences.getBoolean(PermissionPreferenceKeys.LOCATION_PERMISSION, false)) {
                    preferences.edit().putBoolean(PermissionPreferenceKeys.LOCATION_PERMISSION, true).apply();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                            PermissionRequester.LOCATION_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PermissionRequester.LOCATION_REQUEST_CODE);
                }
                break;
            case STORAGE:
                if (!preferences.getBoolean(PermissionPreferenceKeys.STORAGE_PERMISSION, false)) {
                    preferences.edit().putBoolean(PermissionPreferenceKeys.STORAGE_PERMISSION, true).apply();
                }
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionRequester.WRITE_STORAGE_REQUEST_CODE);
                break;
        }
    }

    @Override
    public boolean shouldShowPermissionRationale(PermissionType permissionType) {
        switch (permissionType) {
            case STORAGE:
                return ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            case LOCATION:
                return ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION);
            default:
                return false;
        }
    }

    @Override
    public void showCustomDialog(DialogViewModel dialogViewModel) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.setMessage(dialogViewModel.getMessage());
        for (DialogViewModel.Button button : dialogViewModel.getButtons()) {
            dialog.setButton(button.getWhichButton(), button.getText(), button.getOnClickListener());
        }
        dialog.setCancelable(dialogViewModel.isCancelable());
        dialog.show();
    }

    @Override
    public void openPermissionSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }

    @Override
    public void openLocationSettings() {
        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivity(myIntent);
    }

    @Override
    public void openSettings() {
        Intent myIntent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(myIntent);
    }
}

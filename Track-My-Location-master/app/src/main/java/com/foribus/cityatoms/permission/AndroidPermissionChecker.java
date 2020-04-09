package com.foribus.cityatoms.permission;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class AndroidPermissionChecker implements PermissionChecker {
    private Context context;
    private SharedPreferences preferences;

    public AndroidPermissionChecker(Context context) {
        this.context = context.getApplicationContext();
        preferences = context.getSharedPreferences(PermissionPreferenceKeys.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean hasPermission(PermissionType permissionType) {
        switch (permissionType) {
            case LOCATION:
                return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            case LOCATION_BACKGROUND:
                return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            case STORAGE:
                return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            default:
                return false;
        }
    }

    @Override
    public boolean hasPermissionBeenShown(PermissionType permissionType) {
        switch (permissionType) {
            case LOCATION:
                return preferences.getBoolean(PermissionPreferenceKeys.LOCATION_PERMISSION, false);
            case STORAGE:
                return preferences.getBoolean(PermissionPreferenceKeys.STORAGE_PERMISSION, false);
            default:
                return false;
        }
    }

    @Override
    public boolean hasPermissionChanged(PermissionType permissionType) {
        // -1 = unset, 0 = denied, 1 = granted
        int status = -1;

        switch (permissionType) {
            case LOCATION:
                status = preferences.getInt(PermissionPreferenceKeys.LOCATION_STATE, -1);
                break;
            case STORAGE:
                status = preferences.getInt(PermissionPreferenceKeys.STORAGE_STATE, -1);
                break;
        }

        if (status == -1) {
            return false;
        } else {
            boolean granted = status == 1;
            return granted != hasPermission(permissionType);
        }
    }

    @Override
    public void updatePermissionState(PermissionType permissionType) {
        boolean granted = hasPermission(permissionType);
        int newState = granted ? 1 : 0;

        switch (permissionType) {
            case LOCATION:
                preferences.edit().putInt(PermissionPreferenceKeys.LOCATION_STATE, newState).apply();
                break;
            case STORAGE:
                preferences.edit().putInt(PermissionPreferenceKeys.STORAGE_STATE, newState).apply();
                break;
        }
    }
}

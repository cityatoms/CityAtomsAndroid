package com.github.abdularis.trackmylocation.permission;

import com.github.abdularis.trackmylocation.common.DialogViewModel;

/**
 * Convenience methods to show a dialog requesting permissions and opening settings
 */
public interface PermissionRequester {
    int LOCATION_REQUEST_CODE = 2;
    int WRITE_STORAGE_REQUEST_CODE = 5;

    void requestPermission(PermissionType permissionType);

    boolean shouldShowPermissionRationale(PermissionType permissionType);

    void showCustomDialog(DialogViewModel dialogViewModel);

    void openPermissionSettings();

    void openLocationSettings();

    void openSettings();
}

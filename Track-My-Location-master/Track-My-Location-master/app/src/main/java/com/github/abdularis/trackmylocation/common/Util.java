package com.github.abdularis.trackmylocation.common;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public final class Util {

    private static Util instance;
    private static final int REQ_PERM_CODE = 9874;

    public static Util getInstance() {
        if (instance == null) instance = new Util();
        return instance;
    }

    public static boolean checkLocationPermission(Activity activity) {
        // check permission untuk lokasi (fine/coarse)
        // runtime permission checking ini digunakan untuk android 6.0 (marshmellow) ke atas
        int fineLoc = ContextCompat
                .checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLoc = ContextCompat
                .checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (fineLoc != PackageManager.PERMISSION_GRANTED ||
                coarseLoc != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQ_PERM_CODE);
            return false;
        }

        return true;
    }

    public static boolean checkLocationPermissionsResult(int requestCode,
                                                         @NonNull String[] permissions,
                                                         @NonNull int[] grantResults) {
        return requestCode == REQ_PERM_CODE &&
                grantResults.length >= 2 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkGooglePlayServicesAvailability(Activity activity) {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int resultCode = api.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (api.isUserResolvableError(resultCode)) {
                Dialog dialog = api.getErrorDialog(activity, resultCode, 1234);
                dialog.setCancelable(false);
                dialog.setOnCancelListener(dialogInterface -> activity.finish());
                dialog.show();
            } else {
                Toast.makeText(activity, "Device unsupported", Toast.LENGTH_LONG).show();
                activity.finish();
            }

            return false;
        }

        return true;
    }

    public void hideKeyboard(Context ctx) {
        try {
            InputMethodManager inputManager =
                    (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            View v = ((Activity) ctx).getCurrentFocus();
            if (v == null || inputManager == null) return;
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

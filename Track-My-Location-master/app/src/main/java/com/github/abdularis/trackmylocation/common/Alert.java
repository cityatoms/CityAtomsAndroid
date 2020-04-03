package com.github.abdularis.trackmylocation.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.abdularis.trackmylocation.R;

public class Alert {
    private static AlertDialog mAlertDialog;
    public static void showProgressBar(Activity activity, String progressText) {
        hideProgressBar(activity);
        if (activity != null && !activity.isFinishing()) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.layout_progress_bar, null);
            if (progressText != null) {
                TextView txtProgress = dialogView.findViewById(R.id.progress_text);
                txtProgress.setText(progressText);
            }
            dialogBuilder.setView(dialogView);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.setCancelable(false);
            if (!activity.isFinishing())
                alertDialog.show();
            if (alertDialog.getWindow() != null) {
                Resources r = activity.getResources();
                alertDialog.getWindow().setLayout((int) (r.getDisplayMetrics().widthPixels * 0.5),
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            mAlertDialog = alertDialog;
        }
    }

    public static void hideProgressBar(Activity activity) {
        if (mAlertDialog != null && activity!= null && !activity.isFinishing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }
}

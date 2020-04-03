package com.github.abdularis.trackmylocation.model.views;

import android.view.View;

public interface Presenter<T> {
    void onCreate();

    void onStart();

    void onStop();

    void onDestroy();

    void attachView(View v);
}
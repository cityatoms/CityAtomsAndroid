package com.github.abdularis.trackmylocation.dashboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.abdularis.trackmylocation.common.Alert;
import com.github.abdularis.trackmylocation.model.views.AppScheduler;
import com.github.abdularis.trackmylocation.model.views.IScheduler;
import com.github.abdularis.trackmylocation.model.views.Presenter;

public class BaseActivity extends AppCompatActivity {
    protected Presenter presenter;
    protected IScheduler scheduler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduler = new AppScheduler();
        initializePresenter();
        if(presenter != null) presenter.onCreate();
    }

    public void showProgressBar(String progressText) {
        if (!this.isFinishing()) {
            Alert.showProgressBar(this, progressText);
        }
    }

    public void hideProgressBar() {
        if (!this.isFinishing()) {
            Alert.hideProgressBar(this);
        }
    }

    protected void initializePresenter() {

    }
}

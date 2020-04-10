package com.github.abdularis.trackmylocation.startupui;

import android.content.Intent;
import android.os.Bundle;

import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.dashboard.HealthMonitorFragment;
import com.github.abdularis.trackmylocation.startupui.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.widget.ImageView;


public class SimpleFeeling extends AppCompatActivity implements View.OnClickListener {
CardView covidcard,symptomscard,testcard,feelinggreatcard;
ImageView     backToMain;
    private int feelingType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_feeling);

        covidcard=findViewById(R.id.covidcard);
        symptomscard=findViewById(R.id.symptomscard);
        testcard=findViewById(R.id.testcard);
        feelinggreatcard=findViewById(R.id.feelinggreatcard);
        backToMain=findViewById(R.id.    backMain);
        feelinggreatcard.setOnClickListener(this);
        symptomscard.setOnClickListener(this);
        testcard.setOnClickListener(this);
        covidcard.setOnClickListener(this);
        backToMain.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch ( v.getId())
        {
            case R.id.feelinggreatcard:
                feelingType = 1;
                initFragment(new HealthMonitorFragment());
                break;
            case R.id.symptomscard:
                feelingType = 2;
                Intent i =new Intent(this,SimpleSymptoms.class);
                startActivity(i);
                break;
            case R.id.testcard:
                feelingType = 3;
                break;
            case R.id.covidcard:
                feelingType = 4;
                break;

                case R.id.    backMain:
                    finish();
                break;

        }

    }
    private void initFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (fragment != null && !supportFragmentManager.isDestroyed()) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.basic_fragment, fragment).commitAllowingStateLoss();
        }
    }
}

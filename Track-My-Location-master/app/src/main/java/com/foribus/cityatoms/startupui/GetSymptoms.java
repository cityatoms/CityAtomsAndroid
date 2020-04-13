package com.foribus.cityatoms.startupui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.foribus.cityatoms.BaseApplication;
import com.foribus.cityatoms.R;
import com.foribus.cityatoms.common.IPreferencesKeys;
import com.foribus.cityatoms.dashboard.BaseActivity;
import com.foribus.cityatoms.dashboard.MainActivity;
import com.foribus.cityatoms.database.DatabaseRepositoryManager;
import com.foribus.cityatoms.network.model.SymptomScores;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class GetSymptoms extends BaseActivity {

    public static final String SOURCE_CALLER = "source_caller";

    private int feelingType;
    private SharedPreferences preferences;
    private boolean sourceCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_feeling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sourceCaller = getIntent().getBooleanExtra(SOURCE_CALLER, false);


        ButterKnife.bind(this);
        preferences = BaseApplication.getBaseApplication().getPreferences();
    }

    @OnClick({R.id.card_feel1, R.id.card_feel2, R.id.card_feel3, R.id.card_feel4})
    public void OnClickFeelings(View view) {
        switch (view.getId()) {
            case R.id.card_feel1:
                feelingType = 1;
                break;
            case R.id.card_feel2:
                feelingType = 2;
                break;
            case R.id.card_feel3:
                feelingType = 3;
                break;
            case R.id.card_feel4:
                feelingType = 4;
                break;
        }
        if (preferences.getAll() != null) {
            preferences.getAll().clear();
            preferences.edit().putInt(IPreferencesKeys.COUNTER, 0).apply();

        }
            if (feelingType == 1) {

                preferences.edit().putInt(IPreferencesKeys.FEELING_TYPE, feelingType).apply();

                DatabaseRepositoryManager.getInstance(this).symptomsScoreRepository().saveDataPoint(SymptomScores.NORMAL());
                toMainActivity();
            } else if (feelingType == 2) {
                Intent i = new Intent(this, SimpleSymptoms.class);
                startActivityForResult(i, 1);
            } else if (feelingType == 3) {
                preferences.edit().putInt(IPreferencesKeys.FEELING_TYPE, feelingType).apply();
                DatabaseRepositoryManager.getInstance(this).symptomsScoreRepository().saveDataPoint(SymptomScores.HOSPITAL());
                toMainActivity();
            } else if (feelingType == 4) {
                preferences.edit().putInt(IPreferencesKeys.FEELING_TYPE, feelingType).apply();
                DatabaseRepositoryManager.getInstance(this).symptomsScoreRepository().saveDataPoint(SymptomScores.C19());
                toMainActivity();
            }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                toMainActivity();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        toMainActivity();
    }

    private void toMainActivity() {
        if (sourceCaller)
            finish();
        else {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    }
}

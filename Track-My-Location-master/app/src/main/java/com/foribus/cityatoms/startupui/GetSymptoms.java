package com.foribus.cityatoms.startupui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.foribus.cityatoms.BaseApplication;
import com.foribus.cityatoms.R;
import com.foribus.cityatoms.common.IPreferencesKeys;
import com.foribus.cityatoms.dashboard.BaseActivity;
import com.foribus.cityatoms.dashboard.BaseFragment;
import com.foribus.cityatoms.dashboard.DailySymptomsFragment;
import com.foribus.cityatoms.dashboard.HealthMonitorFragment;
import com.foribus.cityatoms.dashboard.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class GetSymptoms extends BaseActivity {
    DailySymptomsFragment dailySymptomsFragment;
    private int feelingType;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_feeling);
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
        Toast.makeText(getApplicationContext(), feelingType + "", Toast.LENGTH_SHORT).show();

            if (dailySymptomsFragment == null)
                dailySymptomsFragment = new DailySymptomsFragment();

            addFragment(dailySymptomsFragment, "daily");
            if (feelingType == 1) {
                preferences.edit().putInt(IPreferencesKeys.FEELING_TYPE, feelingType ).apply();

                Intent i = new Intent(this, MainActivity.class);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(i);
            } else if (feelingType == 2) {
                Intent i = new Intent(this, SimpleSymptoms.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else if (feelingType == 3) {
                preferences.edit().putInt(IPreferencesKeys.FEELING_TYPE, feelingType ).apply();
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("feelingType",feelingType);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else if (feelingType == 4) {
                preferences.edit().putInt(IPreferencesKeys.FEELING_TYPE, feelingType ).apply();
                Intent i = new Intent(this, MainActivity.class);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
    }


    @OnClick(R.id.feelBack)
    public void OnCLickBack() {
        onBackPressed();
    }

    public void addFragment(Fragment fragment, String TAG) {
        if (fragment == null) return;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (!supportFragmentManager.isDestroyed()) {
            FragmentTransaction fragTransaction = supportFragmentManager.beginTransaction();
            fragTransaction.replace(R.id.basic_fragment, fragment, TAG);
            fragTransaction.addToBackStack(TAG);
            fragTransaction.commitAllowingStateLoss();
        }
    }
}

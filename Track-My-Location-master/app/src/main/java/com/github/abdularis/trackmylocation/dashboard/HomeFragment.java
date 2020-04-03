package com.github.abdularis.trackmylocation.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.abdularis.trackmylocation.BaseApplication;
import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.sharelocation.LocationUpdatesService;
import com.github.abdularis.trackmylocation.startupui.StartupActivity;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    private SharedPreferences preferences;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setUpUI() {
        preferences = BaseApplication.getBaseApplication().getPreferences();
    }

    @Override
    protected void initializePresenter() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(HomeFragment.this, rootView);
        return rootView;
    }

    @OnClick(R.id.btnLogout)
    public void OnCLickLogout() {
        FirebaseAuth.getInstance().signOut();
        preferences.edit().clear().apply();
        Intent intent = new Intent(mainActivity, StartupActivity.class);
        startActivity(intent);
        mainActivity.stopService(new Intent(mainActivity, LocationUpdatesService.class));
        mainActivity.finish();

    }


}

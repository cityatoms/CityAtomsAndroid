package com.github.abdularis.trackmylocation.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.abdularis.trackmylocation.BaseApplication;
import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.entity.LocationData;
import com.github.abdularis.trackmylocation.sharelocation.LocationUpdatesService;
import com.github.abdularis.trackmylocation.startupui.AnonymousLogin;
import com.github.abdularis.trackmylocation.startupui.StartupActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.textView2)
    TextView txt2;
    @BindView(R.id.textView3)
    TextView txt3;
    @Setter
    List<LocationData> locationDataList;
    @Setter
    String userData;
    private SharedPreferences preferences;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setUpUI() {
        preferences = BaseApplication.getBaseApplication().getPreferences();
        txt3.setText(userData);
        StringBuilder stringBuilder = new StringBuilder();
        for (LocationData locationData: locationDataList) {
            stringBuilder.append("time:"+locationData.getTimestamp()+"   lat:"+locationData.getLatitude()+"   long:"+locationData.getLongitude());
            stringBuilder.append("\n");
        }
        txt2.setText(stringBuilder.toString());
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
        Intent intent = new Intent(mainActivity, AnonymousLogin.class);
        startActivity(intent);
        mainActivity.stopService(new Intent(mainActivity, LocationUpdatesService.class));
        mainActivity.finish();

    }


}

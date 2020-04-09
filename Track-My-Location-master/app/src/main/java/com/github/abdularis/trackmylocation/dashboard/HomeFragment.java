package com.github.abdularis.trackmylocation.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.abdularis.trackmylocation.BaseApplication;
import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.startupui.AnonymousLogin;

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
    String userData;
    private SharedPreferences preferences;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setUpUI() {
        preferences = BaseApplication.getBaseApplication().getPreferences();
        txt3.setText(userData);
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
        preferences.edit().clear().apply();
        Intent intent = new Intent(mainActivity, AnonymousLogin.class);
        startActivity(intent);
        mainActivity.finish();
    }


}

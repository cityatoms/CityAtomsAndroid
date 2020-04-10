package com.github.abdularis.trackmylocation.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.github.abdularis.trackmylocation.BaseApplication;
import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.startupui.AnonymousLogin;
import com.github.abdularis.trackmylocation.startupui.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthMonitorFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Context context;
    private SharedPreferences preferences;

    public HealthMonitorFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initComponents(rootView);
        setUpPageAdapter(rootView);
        setupTabs();


        return rootView;
    }
    private void initComponents(View rootView) {

        mTabLayout          =     rootView.findViewById(R.id.mTablayout);
        mViewPager          =     rootView.findViewById(R.id.mViewPager);
        context             =       getContext();

    }
    private void setUpPageAdapter(View View) {
        mPagerAdapter = new ViewPagerAdapter(context,getFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
    private void setupTabs() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
//    @OnClick(R.id.btnLogout)
//    public void OnCLickLogout() {
//        preferences.edit().clear().apply();
//        Intent intent = new Intent(mainActivity, AnonymousLogin.class);
//        startActivity(intent);
//        mainActivity.finish();
//    }


}

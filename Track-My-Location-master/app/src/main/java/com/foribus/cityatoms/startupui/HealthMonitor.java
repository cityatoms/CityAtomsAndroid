package com.foribus.cityatoms.startupui;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.foribus.cityatoms.R;
import com.google.android.material.tabs.TabLayout;

public class HealthMonitor extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_monitor);

        initComponents();
        setUpPageAdapter();
        setupTabs();
    }

    private void initComponents() {

        mTabLayout = findViewById(R.id.mTablayout);
        mViewPager = findViewById(R.id.mViewPager);
        context = this;

    }

    private void setUpPageAdapter() {
        mPagerAdapter = new ViewPagerAdapter(context, getSupportFragmentManager());
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
}

package com.foribus.cityatoms.startupui;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.foribus.cityatoms.fragments.CurrentFragment;
import com.foribus.cityatoms.fragments.ForteenDaysFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> _fragments;
    private Context mContext;

    public ViewPagerAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this._fragments = new ArrayList<Fragment>();
        this.mContext = mContext;
    }
    public void add(Fragment fragment) {
        this._fragments.add(fragment);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CurrentFragment();
            case 1:
                return new ForteenDaysFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "CURRENT ";
            case 1:
                return "14 DAYS";
            default:
                return null;
        }
    }
}

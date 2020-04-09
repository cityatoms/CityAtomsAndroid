package com.github.abdularis.trackmylocation.startupui;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.fragmentss.CurrentFragment;
import com.github.abdularis.trackmylocation.fragmentss.ForteenDaysFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context       mContext;

    public ViewPagerAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext   =   mContext;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new CurrentFragment();
            case 1 :
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
                return "Current ";
            case 1:
                return "14 Days";
            default:
                return null;
        }
    }
}

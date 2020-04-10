package com.foribus.cityatoms.fragmentss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.foribus.cityatoms.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForteenDaysFragment extends Fragment {

    public ForteenDaysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forteen_days, container, false);
    }
}

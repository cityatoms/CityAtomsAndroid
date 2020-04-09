package com.github.abdularis.trackmylocation.fragmentss;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.abdularis.trackmylocation.R;


public class CurrentFragment extends Fragment {

    View view;
    TextView text;
    SweepGradient sweepGradient;
    Paint mPaint = new Paint();
    Paint mPaint2 = new Paint();
    Canvas canvas = new Canvas();
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_current, container, false);


        // Create background track

        return view;
    }

    protected float getDimension(float base) {
        // get the proper scaled dimensions for the display
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, base, getResources().getDisplayMetrics());
    }
}

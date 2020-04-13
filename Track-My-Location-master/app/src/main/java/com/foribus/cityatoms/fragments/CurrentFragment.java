package com.foribus.cityatoms.fragments;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foribus.cityatoms.BaseApplication;
import com.foribus.cityatoms.R;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class CurrentFragment extends Fragment {

    View view;
    TextView text;
    SweepGradient sweepGradient;
    Paint mPaint = new Paint();
    Paint mPaint2 = new Paint();
    Canvas canvas = new Canvas();
    ImageView imageView;
    TextView symptcount;
    int number;
    int counter;
    SharedPreferences sharedPreferences;
    String st;
    GradientDrawable gd;
    View iv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_current, container, false);
        iv= (View) view.findViewById(R.id.iv);
        symptcount = view.findViewById(R.id.symptcount);
        gd = new GradientDrawable();
        sharedPreferences = BaseApplication.getBaseApplication().getPreferences();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        createViews();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void createViews() {
        number = sharedPreferences.getInt("feelingType", 0);
        st = sharedPreferences.getString("st", "st");
        Log.d(TAG, "call from simplesymptoms: " + st);
        number = sharedPreferences.getInt("feelingType", 0);
        counter = sharedPreferences.getInt("counter", 0);
        Log.d(TAG, "call from getsymptoms: " + number);
        symptcount.setText("" + counter);
        if (number == 1) {
            gd.setColors(new int[]{
                    getResources().getColor(R.color.lightishGreen), getResources().getColor(R.color.lightishGreen),
                    getResources().getColor(R.color.lightishGreen)});
        } else if (number == 3) {
            gd.setColors(new int[]{
                    getResources().getColor(R.color.peach), getResources().getColor(R.color.peach),
                    getResources().getColor(R.color.peach)});
        } else if (number == 4) {
            gd.setColors(new int[]{
                    getResources().getColor(R.color.salmon), getResources().getColor(R.color.salmon),
                    getResources().getColor(R.color.salmon)});
        }

        // Set the color array to draw gradient.
        else if (number==2) {
            gd.setColors(new int[]{
                    getResources().getColor(R.color.periwinkle),
                    getResources().getColor(R.color.lightGreenishBlue),
                    getResources().getColor(R.color.lightishGreen),
                    getResources().getColor(R.color.peach),
                    getResources().getColor(R.color.desert),
                    getResources().getColor(R.color.salmon)

            });
        }
        // Set the GradientDrawable gradient type linear gradient
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        // Set GradientDrawable shape is a rectangle
        gd.setShape(GradientDrawable.OVAL);

        // Set 3 pixels width solid blue color border
//        gd.setStroke(3, Color.BLUE);

        // Set GradientDrawable width and in pixels
        gd.setSize(450, 150); // Width 450 pixels and height 150 pixels

        // Set GradientDrawable as ImageView source image
        iv.setBackground(gd);
        // Create background track
    }

    protected float getDimension(float base) {
        // get the proper scaled dimensions for the display
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, base, getResources().getDisplayMetrics());
    }
}

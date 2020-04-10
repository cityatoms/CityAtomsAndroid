package com.foribus.cityatoms.fragmentss;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.foribus.cityatoms.BaseApplication;
import com.foribus.cityatoms.R;
import com.foribus.cityatoms.dashboard.CircularProgressBarDrawable;
import com.foribus.cityatoms.dashboard.MainActivity;


public class CurrentFragment extends Fragment {

    View view;
    TextView text;
    SweepGradient sweepGradient;
    Paint mPaint = new Paint();
    Paint mPaint2 = new Paint();
    Canvas canvas = new Canvas();
    ImageView imageView;
    MainActivity mainActivity;
    int number;
     SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_current, container, false);
        final View iv = (View) view.findViewById(R.id.iv);
        GradientDrawable gd = new GradientDrawable();
        sharedPreferences = BaseApplication.getBaseApplication().getPreferences();
        number=sharedPreferences.getInt("feelingType",1);

        if (number==1){
            gd.setColors(new int[]{
                    getResources().getColor(R.color.lightishGreen),getResources().getColor(R.color.lightishGreen),
            getResources().getColor(R.color.lightishGreen)});
        }else if (number==3){
            gd.setColors(new int[]{
                    getResources().getColor(R.color.peach),getResources().getColor(R.color.peach),
                    getResources().getColor(R.color.peach)});
        }
        else if (number==4){
            gd.setColors(new int[]{
                    getResources().getColor(R.color.salmon), getResources().getColor(R.color.salmon),
                    getResources().getColor(R.color.salmon)});
        }
        // Set the color array to draw gradient
//        gd.setColors(new int[]{
//                getResources().getColor(R.color.salmon),
//                getResources().getColor(R.color.periwinkle),
//                getResources().getColor(R.color.greyishBrown),
//                getResources().getColor(R.color.lightGreenishBlue),
//                getResources().getColor(R.color.greyishBrown),
//                getResources().getColor(R.color.lightishGreen)
//
//        });

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

        return view;
    }

    protected float getDimension(float base) {
        // get the proper scaled dimensions for the display
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, base, getResources().getDisplayMetrics());
    }
}

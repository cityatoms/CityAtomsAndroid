package com.foribus.cityatoms.fragments;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foribus.cityatoms.R;
import com.foribus.cityatoms.database.DatabaseRepositoryManager;
import com.foribus.cityatoms.database.symptoms.SymptomsScoreEntity;
import com.foribus.cityatoms.model.FeelingType;
import com.foribus.cityatoms.network.model.SymptomScores;

import java.util.concurrent.Executors;


public class CurrentFragment extends Fragment {

	View view;
	TextView text;
	SweepGradient sweepGradient;
	Paint mPaint = new Paint();
	Paint mPaint2 = new Paint();
	Canvas canvas = new Canvas();
	ImageView imageView;
	TextView symptcount;

	GradientDrawable gd;
	View iv;
	private FeelingType feelingType;
	private SymptomScores symptomScores;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_current, container, false);
		iv = (View) view.findViewById(R.id.iv);
		symptcount = view.findViewById(R.id.symptcount);
		gd = new GradientDrawable();
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
		Executors.newSingleThreadExecutor().execute(() -> {
			SymptomsScoreEntity entity = DatabaseRepositoryManager.getInstance(getContext()).symptomsScoreRepository().getLatestSymptomScoreInfo();
			symptomScores = SymptomScores.instance(entity);
			feelingType = symptomScores.getFeeling();

			getActivity().runOnUiThread(this::updateChartInfo);
		});
	}

	private void updateChartInfo() {
		symptcount.setText("" + symptomScores.getScore());
		if (feelingType == FeelingType.NORMAL) {
			gd.setColors(new int[]{
					getResources().getColor(R.color.lightishGreen), getResources().getColor(R.color.lightishGreen),
					getResources().getColor(R.color.lightishGreen)});
		} else if (feelingType == FeelingType.HOSPITAL) {
			gd.setColors(new int[]{
					getResources().getColor(R.color.peach), getResources().getColor(R.color.peach),
					getResources().getColor(R.color.peach)});
		} else if (feelingType == FeelingType.C19) {
			gd.setColors(new int[]{
					getResources().getColor(R.color.salmon), getResources().getColor(R.color.salmon),
					getResources().getColor(R.color.salmon)});
		}

		// Set the color array to draw gradient.
		else if (feelingType == FeelingType.HAVING_SYMPTOMS) {
			gd.setColors(new int[]{
					getResources().getColor(R.color.periwinkle),
					getResources().getColor(R.color.lightGreenishBlue),
					getResources().getColor(R.color.peach),
					getResources().getColor(R.color.desert),
					getResources().getColor(R.color.salmon)

			});
		}
		gd.getOrientation().ordinal();
		// Set the GradientDrawable gradient type linear gradient
		gd.setGradientType(GradientDrawable.RADIAL_GRADIENT);

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

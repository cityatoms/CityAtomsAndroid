package com.foribus.cityatoms.fragments;

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

import com.foribus.cityatoms.R;
import com.foribus.cityatoms.database.DatabaseRepositoryManager;
import com.foribus.cityatoms.database.symptoms.SymptomsScoreEntity;
import com.foribus.cityatoms.model.FeelingType;
import com.foribus.cityatoms.network.model.SymptomScores;

import java.util.ArrayList;
import java.util.List;
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
	private static final String TAG = "CurrentFragment";
	GradientDrawable gd;
	View iv;
	private FeelingType feelingType;
	private SymptomScores symptomScores;
	List<Integer> myList = new ArrayList<Integer>();

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
			Log.d(TAG, "createViews:symptomScores "+symptomScores.getBreathing());
			Log.d(TAG, "createViews: "+feelingType.name());
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
		}
		else if (feelingType == FeelingType.C19) {
			gd.setColors(new int[]{
					getResources().getColor(R.color.salmon), getResources().getColor(R.color.salmon),
					getResources().getColor(R.color.salmon)});
		}

		// Set the color array to draw gradient.
		else if (feelingType == FeelingType.HAVING_SYMPTOMS) {
//			for (int i =0;i<=symptomScores.getScore();i++)
//			{
//			if (symptomScores.getBreathing()==1) {
//				myList.add(getResources().getColor(R.color.lightishGreen));
//			}else if (symptomScores.getCough()==1){
//					myList.add(getResources().getColor(R.color.lightGreenishBlue));
//			}else if (symptomScores.getFever()==1)
//			{
//				myList.add(getResources().getColor(R.color.peach));
//			}else if (symptomScores.getSmell()==1)
//			{
//				myList.add(getResources().getColor(R.color.desert));
//			}else if (symptomScores.getThroat()==1)
//			{
//				getResources().getColor(R.color.periwinkle);
//			}else if (symptomScores.getPneumonia()==1)
//			{
//				getResources().getColor(R.color.salmon);
//			}
//			}
//			int size = myList.size();
//			int[] result = new int[size];
//			Integer[] temp = myList.toArray(new Integer[size]);
//			for (int n = 0; n < size; ++n) {
//				result[n] = temp[n];
//			}
//			Log.d(TAG, "updateChartInfo: "+result);
//			gd.setColors (result);
				gd.setColors(new int[]{
//					getResources().getColor(R.color.salmon),
//					getResources().getColor(R.color.salmon),
//					getResources().getColor(R.color.salmon),
//					getResources().getColor(R.color.periwinkle),
//					getResources().getColor(R.color.periwinkle),
//					getResources().getColor(R.color.periwinkle)
						getResources().getColor(R.color.periwinkle),
						getResources().getColor(R.color.lightGreenishBlue),
						getResources().getColor(R.color.peach),
						getResources().getColor(R.color.desert),
						getResources().getColor(R.color.salmon),
						getResources().getColor(R.color.lightishGreen)


				});


		}
		// Set the GradientDrawable gradient type linear gradient
		gd.setGradientType(GradientDrawable.RADIAL_GRADIENT);

		// Set GradientDrawable shape is a rectangle
		gd.setShape(GradientDrawable.OVAL);
		gd.setGradientRadius(250);
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

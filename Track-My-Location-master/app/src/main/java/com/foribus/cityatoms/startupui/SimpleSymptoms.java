package com.foribus.cityatoms.startupui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.foribus.cityatoms.R;
import com.foribus.cityatoms.dashboard.BaseActivity;
import com.foribus.cityatoms.database.DatabaseRepositoryManager;
import com.foribus.cityatoms.database.symptoms.SymptomsScoreEntity;
import com.foribus.cityatoms.model.FeelingType;
import com.foribus.cityatoms.network.model.SymptomScores;

import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SimpleSymptoms extends BaseActivity {

	protected Unbinder unbinder;
	@BindView(R.id.layout_sym_1)
	LinearLayout layoutSym1;
	@BindView(R.id.layout_sym_2)
	LinearLayout layoutSym2;
	@BindView(R.id.layout_sym_3)
	LinearLayout layoutSym3;
	@BindView(R.id.layout_sym_4)
	LinearLayout layoutSym4;
	@BindView(R.id.layout_sym_5)
	LinearLayout layoutSym5;
	@BindView(R.id.layout_sym_6)
	LinearLayout layoutSym6;
	@BindView(R.id.txtSym1)
	TextView txtSym1;
	@BindView(R.id.txtSym2)
	TextView txtSym2;
	@BindView(R.id.txtSym3)
	TextView txtSym3;
	@BindView(R.id.txtSym4)
	TextView txtSym4;
	@BindView(R.id.txtSym5)
	TextView txtSym5;
	@BindView(R.id.txtSym6)
	TextView txtSym6;
	@BindView(R.id.btn_save_symptoms)
	Button btnSaveSymptoms;
	@BindView(R.id.sore_throat)
	ImageView sore_throat;
	@BindView(R.id.dry)
	ImageView dry;
	@BindView(R.id.fever)
	ImageView fever;
	@BindView(R.id.shortness_breath)
	ImageView shortness_breath;
	@BindView(R.id.loss_taste)
	ImageView loss_taste;
	@BindView(R.id.pneumonia_feelin)
	ImageView pneumonia_feeling;

	SymptomScores symptomScores;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_symptoms);

		Toolbar toolbar = findViewById(R.id.toolbar);
		toolbar.setTitle("");
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		ButterKnife.bind(this);

		loadSymptomsIfAny();
	}

	private void loadSymptomsIfAny() {
		Executors.newSingleThreadExecutor().execute(() -> {
			SymptomsScoreEntity entity = DatabaseRepositoryManager.getInstance(this).symptomsScoreRepository().getLatestSymptomScoreInfo();
			symptomScores = SymptomScores.instance(entity);
			if (symptomScores == null) {
				symptomScores = new SymptomScores();

				if (getIntent().hasExtra("feeling_type")) {
					FeelingType feelingType = (FeelingType) getIntent().getSerializableExtra("feeling_type");
					if (feelingType == FeelingType.HOSPITAL)
						symptomScores.setHospital(1);
					else if (feelingType == FeelingType.C19)
						symptomScores.setC19(1);
				}
			}

			runOnUiThread(this::refreshUI);
		});
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@OnClick({R.id.layout_sym_1, R.id.layout_sym_2, R.id.layout_sym_3, R.id.layout_sym_4,
			R.id.layout_sym_5, R.id.layout_sym_6})
	public void onItemClicked(View view) {
		switch (view.getId()) {
			case R.id.layout_sym_1:
				symptomScores.setThroat(symptomScores.getThroat() == 0 ? 1 : 0);
				break;
			case R.id.layout_sym_2:
				symptomScores.setCough(symptomScores.getCough() == 0 ? 1 : 0);
				break;
			case R.id.layout_sym_3:
				symptomScores.setFever(symptomScores.getFever() == 0 ? 1 : 0);
				break;
			case R.id.layout_sym_4:
				symptomScores.setBreathing(symptomScores.getBreathing() == 0 ? 1 : 0);
				break;
			case R.id.layout_sym_5:
				symptomScores.setSmell(symptomScores.getSmell() == 0 ? 1 : 0);
				break;
			case R.id.layout_sym_6:
				symptomScores.setPneumonia(symptomScores.getPneumonia() == 0 ? 1 : 0);
				break;
		}
		refreshUI();
	}

	private void refreshUI() {
		// Throat
		if (symptomScores.getThroat() == 1) {
			sore_throat.setBackgroundResource(R.drawable.srtb48);
			layoutSym1.setBackgroundResource(R.drawable.bg_select_sym);
			txtSym1.setTextColor(getResources().getColor(R.color.colorCenter));
		} else {
			layoutSym1.setBackgroundResource(R.drawable.bg_non_select_sym);
			txtSym1.setTextColor(getResources().getColor(R.color.subSubText));
		}

		// Cough
		if (symptomScores.getCough() == 1) {
			dry.setBackgroundResource(R.drawable.dcblu48);
			layoutSym2.setBackgroundResource(R.drawable.bg_select_sym);
			txtSym2.setTextColor(getResources().getColor(R.color.colorCenter));
		} else {
			layoutSym2.setBackgroundResource(R.drawable.bg_non_select_sym);
			txtSym2.setTextColor(getResources().getColor(R.color.subSubText));
		}

		// Fever
		if (symptomScores.getFever() == 1) {
			fever.setBackgroundResource(R.drawable.feverbl48);
			layoutSym3.setBackgroundResource(R.drawable.bg_select_sym);
			txtSym3.setTextColor(getResources().getColor(R.color.colorCenter));
		} else {
			layoutSym3.setBackgroundResource(R.drawable.bg_non_select_sym);
			txtSym3.setTextColor(getResources().getColor(R.color.subSubText));
		}

		// Breathing
		if (symptomScores.getBreathing() == 1) {
			shortness_breath.setBackgroundResource(R.drawable.breathblu48);
			layoutSym4.setBackgroundResource(R.drawable.bg_select_sym);
			txtSym4.setTextColor(getResources().getColor(R.color.colorCenter));
		} else {
			layoutSym4.setBackgroundResource(R.drawable.bg_non_select_sym);
			txtSym4.setTextColor(getResources().getColor(R.color.subSubText));
		}

		// Smell
		if (symptomScores.getSmell() == 1) {
			loss_taste.setBackgroundResource(R.drawable.toungeblu48);
			layoutSym5.setBackgroundResource(R.drawable.bg_select_sym);
			txtSym5.setTextColor(getResources().getColor(R.color.colorCenter));
		} else {
			layoutSym5.setBackgroundResource(R.drawable.bg_non_select_sym);
			txtSym5.setTextColor(getResources().getColor(R.color.subSubText));
		}

		// Pneumonia
		if (symptomScores.getPneumonia() == 1) {
			pneumonia_feeling.setBackgroundResource(R.drawable.heartblu48);
			layoutSym6.setBackgroundResource(R.drawable.bg_select_sym);
			txtSym6.setTextColor(getResources().getColor(R.color.colorCenter));
		} else {
			layoutSym6.setBackgroundResource(R.drawable.bg_non_select_sym);
			txtSym6.setTextColor(getResources().getColor(R.color.subSubText));
		}

		if (symptomScores.calculateScore() > 0) {
			btnSaveSymptoms.setEnabled(true);
			btnSaveSymptoms.setBackgroundResource(R.drawable.bg_login_button);
		} else {
			btnSaveSymptoms.setEnabled(false);
			btnSaveSymptoms.setBackgroundResource(R.drawable.bg_btn_disable);
		}
	}

	@OnClick(R.id.btn_save_symptoms)
	public void OnClickSave() {
		DatabaseRepositoryManager.getInstance(this).symptomsScoreRepository().saveDataPoint(symptomScores);
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		super.onBackPressed();
		finish();
	}
}

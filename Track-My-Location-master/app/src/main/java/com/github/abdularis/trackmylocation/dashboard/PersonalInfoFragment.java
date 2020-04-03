package com.github.abdularis.trackmylocation.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.abdularis.trackmylocation.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalInfoFragment extends BaseFragment {
    @BindView(R.id.layMale)
    LinearLayout layoutMale;
    @BindView(R.id.txtMale)
    TextView txtMale;
    @BindView(R.id.layFemale)
    LinearLayout layoutFemale;
    @BindView(R.id.txtFemale)
    TextView txtFemale;

    @BindView(R.id.layAge1)
    LinearLayout layoutAge1;
    @BindView(R.id.layAge2)
    LinearLayout layoutAge2;
    @BindView(R.id.layAge3)
    LinearLayout layoutAge3;
    @BindView(R.id.layAge4)
    LinearLayout layoutAge4;
    @BindView(R.id.layAge5)
    LinearLayout layoutAge5;
    @BindView(R.id.layAge6)
    LinearLayout layoutAge6;

    @BindView(R.id.txtAge1)
    TextView txtAge1;
    @BindView(R.id.txtAge2)
    TextView txtAge2;
    @BindView(R.id.txtAge3)
    TextView txtAge3;
    @BindView(R.id.txtAge4)
    TextView txtAge4;
    @BindView(R.id.txtAge5)
    TextView txtAge5;
    @BindView(R.id.txtAge6)
    TextView txtAge6;

    @BindView(R.id.layYes)
    LinearLayout layoutYes;
    @BindView(R.id.txtYes)
    TextView txtYes;
    @BindView(R.id.layNo)
    LinearLayout layoutNo;
    @BindView(R.id.txtNo)
    TextView txtNo;

    private String gender;
    private int age = 0;
    private String longTermIll;

    GetSymptomsFirstFragment getSymptomsFirstFragment;

    public PersonalInfoFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setUpUI() {

    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_personal_info, container,
                false);
        unbinder = ButterKnife.bind(PersonalInfoFragment.this, rootView);
        return rootView;
    }

    @OnClick({R.id.layMale, R.id.layFemale})
    public void OnClickGender(View view) {
        switch (view.getId()) {
            case R.id.layMale:
                gender = "Male";
                updateGender(true);
                break;
            case R.id.layFemale:
                gender = "Female";
                updateGender(false);
                break;
        }
    }

    private void updateGender(boolean isMale) {
        if (isMale) {
            layoutMale.setBackgroundResource(R.drawable.bg_select_sym);
            txtMale.setTextColor(getResources().getColor(R.color.colorCenter));
            layoutFemale.setBackgroundResource(R.drawable.bg_non_select_sym);
            txtFemale.setTextColor(getResources().getColor(R.color.subSubText));
        } else {
            layoutFemale.setBackgroundResource(R.drawable.bg_select_sym);
            txtFemale.setTextColor(getResources().getColor(R.color.colorCenter));
            layoutMale.setBackgroundResource(R.drawable.bg_non_select_sym);
            txtMale.setTextColor(getResources().getColor(R.color.subSubText));
        }
    }

    @OnClick({R.id.layAge1, R.id.layAge2, R.id.layAge3, R.id.layAge4, R.id.layAge5,
            R.id.layAge6})
    public void OnClickAge(View view) {
        switch (view.getId()) {
            case R.id.layAge1:
                 age = 1;
                break;
            case R.id.layAge2:
                age = 2;
                break;
            case R.id.layAge3:
                age = 3;
                break;
            case R.id.layAge4:
                age = 4;
                break;
            case R.id.layAge5:
                age = 5;
                break;
            case R.id.layAge6:
                age = 6;
                break;
        }
        updateAge(age);
    }

    @OnClick(R.id.btn_continue)
    public void OnClickContinue() {
        if (gender != null && age != 0 && longTermIll != null) {
            if (mainActivity != null && isAdded()) {
                if (getSymptomsFirstFragment == null)
                    getSymptomsFirstFragment = new GetSymptomsFirstFragment();
                mainActivity.addFragment(getSymptomsFirstFragment, "fst");
            }
        } else
            Toast.makeText(mainActivity, "Select Inputs", Toast.LENGTH_SHORT).show();
    }

    private void updateAge(int age) {
        layoutAge1.setBackgroundResource(R.drawable.bg_non_select_sym);
        layoutAge2.setBackgroundResource(R.drawable.bg_non_select_sym);
        layoutAge3.setBackgroundResource(R.drawable.bg_non_select_sym);
        layoutAge4.setBackgroundResource(R.drawable.bg_non_select_sym);
        layoutAge5.setBackgroundResource(R.drawable.bg_non_select_sym);
        layoutAge6.setBackgroundResource(R.drawable.bg_non_select_sym);
        txtAge1.setTextColor(getResources().getColor(R.color.subSubText));
        txtAge2.setTextColor(getResources().getColor(R.color.subSubText));
        txtAge3.setTextColor(getResources().getColor(R.color.subSubText));
        txtAge4.setTextColor(getResources().getColor(R.color.subSubText));
        txtAge5.setTextColor(getResources().getColor(R.color.subSubText));
        txtAge6.setTextColor(getResources().getColor(R.color.subSubText));
        switch (age) {
            case 1:
                layoutAge1.setBackgroundResource(R.drawable.bg_select_sym);
                txtAge1.setTextColor(getResources().getColor(R.color.colorCenter));
                break;
            case 2:
                layoutAge2.setBackgroundResource(R.drawable.bg_select_sym);
                txtAge2.setTextColor(getResources().getColor(R.color.colorCenter));
                break;
            case 3:
                layoutAge3.setBackgroundResource(R.drawable.bg_select_sym);
                txtAge3.setTextColor(getResources().getColor(R.color.colorCenter));
                break;
            case 4:
                layoutAge4.setBackgroundResource(R.drawable.bg_select_sym);
                txtAge4.setTextColor(getResources().getColor(R.color.colorCenter));
                break;
            case 5:
                layoutAge5.setBackgroundResource(R.drawable.bg_select_sym);
                txtAge5.setTextColor(getResources().getColor(R.color.colorCenter));
                break;
            case 6:
                layoutAge6.setBackgroundResource(R.drawable.bg_select_sym);
                txtAge6.setTextColor(getResources().getColor(R.color.colorCenter));
                break;
        }
    }

    @OnClick({R.id.layYes, R.id.layNo})
    public void OnClickIll(View view) {
        switch (view.getId()) {
            case R.id.layYes:
                longTermIll = "True";
                selectLongTermIll(true);
                break;
            case R.id.layNo:
                longTermIll = "False";
                selectLongTermIll(false);
                break;
        }
    }

    private void selectLongTermIll(boolean isTrue) {
        if (isTrue) {
            layoutYes.setBackgroundResource(R.drawable.bg_select_sym);
            txtYes.setTextColor(getResources().getColor(R.color.colorCenter));
            layoutNo.setBackgroundResource(R.drawable.bg_non_select_sym);
            txtNo.setTextColor(getResources().getColor(R.color.subSubText));
        } else {
            layoutNo.setBackgroundResource(R.drawable.bg_select_sym);
            txtNo.setTextColor(getResources().getColor(R.color.colorCenter));
            layoutYes.setBackgroundResource(R.drawable.bg_non_select_sym);
            txtYes.setTextColor(getResources().getColor(R.color.subSubText));
        }
    }

}

package com.foribus.cityatoms.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.foribus.cityatoms.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
//
//public class GetSymptomsFirstFragment extends BaseFragment {
//    DailySymptomsFragment dailySymptomsFragment;
//    private int feelingType;
//    com.github.abdularis.trackmylocation.dashboard.HealthMonitorFragment healthMonitorFragment;
//    public GetSymptomsFirstFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    protected void setUpUI() {
//
//    }
//
//    @Override
//    protected void initializePresenter() {
//
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_get_symptoms_first, container, false);
//        unbinder = ButterKnife.bind(GetSymptomsFirstFragment.this, rootView);
//        return rootView;
//    }
//
//    @OnClick({R.id.card_feel1, R.id.card_feel2, R.id.card_feel3, R.id.card_feel4})
//    public void OnClickFeelings(View view) {
//        switch (view.getId()) {
//            case R.id.card_feel1:
//                feelingType = 1;
//                break;
//            case R.id.card_feel2:
//                feelingType = 2;
//                break;
//            case R.id.card_feel3:
//                feelingType = 3;
//                break;
//            case R.id.card_feel4:
//                feelingType = 4;
//                break;
//        }
//        Toast.makeText(mainActivity, feelingType + "", Toast.LENGTH_SHORT).show();
//        if (mainActivity != null && isAdded()) {
//            if (dailySymptomsFragment == null)
//                dailySymptomsFragment = new DailySymptomsFragment();
//
//            mainActivity.addFragment(dailySymptomsFragment, "daily");
//            if (feelingType==1)
//            {
//                healthMonitorFragment = new com.github.abdularis.trackmylocation.dashboard.HealthMonitorFragment();
//                mainActivity.addFragment(healthMonitorFragment, "health");
//            }else if (feelingType==2){
//                if (dailySymptomsFragment == null)
//                    dailySymptomsFragment = new DailySymptomsFragment();
//                mainActivity.addFragment(dailySymptomsFragment, "daily");
//            }else if(feelingType==3){
//                healthMonitorFragment = new com.github.abdularis.trackmylocation.dashboard.HealthMonitorFragment();
//                mainActivity.addFragment(healthMonitorFragment, "health");
//            }else if(feelingType==4){
//                healthMonitorFragment = new com.github.abdularis.trackmylocation.dashboard.HealthMonitorFragment();
//                mainActivity.addFragment(healthMonitorFragment, "health");
//            }
//
//        }
//    }
//
//    @OnClick(R.id.feelBack)
//    public void OnCLickBack() {
//        mainActivity.onBackPressed();
//    }
//}

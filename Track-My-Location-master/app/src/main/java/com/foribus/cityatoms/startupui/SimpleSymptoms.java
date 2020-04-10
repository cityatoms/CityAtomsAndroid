package com.foribus.cityatoms.startupui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foribus.cityatoms.R;
import com.foribus.cityatoms.adapter.GridAdapter;
import com.foribus.cityatoms.adapter.models.Model;
import com.foribus.cityatoms.dashboard.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SimpleSymptoms extends BaseActivity {

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

    private boolean isCard1Selected = false;
    private boolean isCard2Selected = false;
    private boolean isCard3Selected = false;
    private boolean isCard4Selected = false;
    private boolean isCard5Selected = false;
    private boolean isCard6Selected = false;
    protected Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_symptoms);

        ButterKnife.bind(this);

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.layout_sym_1, R.id.layout_sym_2, R.id.layout_sym_3, R.id.layout_sym_4,
            R.id.layout_sym_5, R.id.layout_sym_6})
    public void onItemClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_sym_1:
                isCard1Selected = !isCard1Selected;
                if (isCard1Selected) {

                    layoutSym1.setBackgroundResource(R.drawable.bg_select_sym);
                    txtSym1.setTextColor(getResources().getColor(R.color.colorCenter));
                } else {
                    layoutSym1.setBackgroundResource(R.drawable.bg_non_select_sym);
                    txtSym1.setTextColor(getResources().getColor(R.color.subSubText));
                }
                break;
            case R.id.layout_sym_2:
                isCard2Selected = !isCard2Selected;
                if (isCard2Selected) {
                    layoutSym2.setBackgroundResource(R.drawable.bg_select_sym);
                    txtSym2.setTextColor(getResources().getColor(R.color.colorCenter));
                } else {
                    layoutSym2.setBackgroundResource(R.drawable.bg_non_select_sym);
                    txtSym2.setTextColor(getResources().getColor(R.color.subSubText));
                }
                break;
            case R.id.layout_sym_3:
                isCard3Selected = !isCard3Selected;
                if (isCard3Selected) {
                    layoutSym3.setBackgroundResource(R.drawable.bg_select_sym);
                    txtSym3.setTextColor(getResources().getColor(R.color.colorCenter));
                } else {
                    layoutSym3.setBackgroundResource(R.drawable.bg_non_select_sym);
                    txtSym3.setTextColor(getResources().getColor(R.color.subSubText));
                }
                break;
            case R.id.layout_sym_4:
                isCard4Selected = !isCard4Selected;
                if (isCard4Selected) {
                    layoutSym4.setBackgroundResource(R.drawable.bg_select_sym);
                    txtSym4.setTextColor(getResources().getColor(R.color.colorCenter));
                } else {
                    layoutSym4.setBackgroundResource(R.drawable.bg_non_select_sym);
                    txtSym4.setTextColor(getResources().getColor(R.color.subSubText));
                }
                break;
            case R.id.layout_sym_5:
                isCard5Selected = !isCard5Selected;
                if (isCard5Selected) {
                    layoutSym5.setBackgroundResource(R.drawable.bg_select_sym);
                    txtSym5.setTextColor(getResources().getColor(R.color.colorCenter));
                } else {
                    layoutSym5.setBackgroundResource(R.drawable.bg_non_select_sym);
                    txtSym5.setTextColor(getResources().getColor(R.color.subSubText));
                }
                break;
            case R.id.layout_sym_6:
                isCard6Selected = !isCard6Selected;
                if (isCard6Selected) {
                    layoutSym6.setBackgroundResource(R.drawable.bg_select_sym);
                    txtSym6.setTextColor(getResources().getColor(R.color.colorCenter));
                } else {
                    layoutSym6.setBackgroundResource(R.drawable.bg_non_select_sym);
                    txtSym6.setTextColor(getResources().getColor(R.color.subSubText));
                }
                break;
        }
        if (isCard1Selected || isCard2Selected || isCard3Selected || isCard4Selected ||
                isCard5Selected || isCard6Selected) {
            btnSaveSymptoms.setEnabled(true);
            btnSaveSymptoms.setBackgroundResource(R.drawable.bg_login_button);
        } else {
            btnSaveSymptoms.setEnabled(false);
            btnSaveSymptoms.setBackgroundResource(R.drawable.bg_btn_disable);
        }
    }

    @OnClick(R.id.btn_save_symptoms)
    public void OnClickSave() {
        StringBuilder st = new StringBuilder();
        if (isCard1Selected)
            st.append("1");
        if (isCard2Selected) {
            if (st.length() > 0)
                st.append(",");
            st.append("2");
        }
        if (isCard3Selected) {
            if (st.length() > 0)
                st.append(",");
            st.append("3");
        }
        if (isCard4Selected) {
            if (st.length() > 0)
                st.append(",");
            st.append("4");
        }
        if (isCard5Selected) {
            if (st.length() > 0)
                st.append(",");
            st.append("5");
        }
        if (isCard6Selected) {
            if (st.length() > 0)
                st.append(",");
            st.append("6");
        }
//        Toast.makeText(getContext(), st, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.close_sym)
    public void ButtonClose() {
//        getActivity().onBackPressed();
    }
}

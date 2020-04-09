package com.foribus.cityatoms.startupui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foribus.cityatoms.R;
import com.foribus.cityatoms.adapter.GridAdapter;
import com.foribus.cityatoms.adapter.models.Model;
import com.foribus.cityatoms.dashboard.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class SimpleSymptoms extends BaseActivity {
    GridAdapter gridAdapter;
    RecyclerView recyclerView;
    Model model;
    Button btnsave;
    List<Model> models = new ArrayList<>();
    int[] img = {R.drawable.srtb96, R.drawable.dcblu96, R.drawable.feverebl96, R.drawable.breathblu96,
            R.drawable.toungeblu96, R.drawable.heartblu96};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_symptoms);
        insetData();
        btnsave = findViewById(R.id.btn_save_symptom);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        gridAdapter = new GridAdapter(this, models);
        recyclerView.setAdapter(gridAdapter);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    void insetData() {
        models.add(new Model(R.drawable.srtbl96, "Sore throat"));

        models.add(new Model(R.drawable.dcbl96, "Dry Cough \n no mucus"));

        models.add(new Model(R.drawable.fever98, "Fever"));

        models.add(new Model(R.drawable.breathbl96, "Shortness of \n breath"));

        models.add(new Model(R.drawable.tounge96, "Loss of smell and taste"));

        models.add(new Model(R.drawable.heartbl96, "Pnemonia like feeling"));


    }
}

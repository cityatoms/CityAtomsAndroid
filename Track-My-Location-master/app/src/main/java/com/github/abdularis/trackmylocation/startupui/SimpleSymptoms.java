package com.github.abdularis.trackmylocation.startupui;

import android.graphics.Color;
import android.os.Bundle;

import com.github.abdularis.trackmylocation.Model;
import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.adapter.GridAdapter;
import com.github.abdularis.trackmylocation.dashboard.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class SimpleSymptoms extends BaseActivity {
    GridAdapter gridAdapter;
    RecyclerView recyclerView;
    Model model;
    Button btnsave;
    List<Model> models=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_symptoms);
        insetData();
        btnsave=findViewById(R.id.btn_save_symptom);
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

    int[] img={R.drawable.srtb96,R.drawable.dcblu96,R.drawable.feverebl96,R.drawable.breathblu96,
            R.drawable.toungeblu96,R.drawable.heartblu96};

    void insetData(){
       models.add(new Model(R.drawable.srtbl96,"Sore throat"));

        models.add(new Model(R.drawable.dcbl96,"Dry Cough \n no mucus"));

        models.add(new Model(R.drawable.fever98,"Fever"));

        models.add(new Model(R.drawable.breathbl96,"Shortness of \n breath"));

        models.add(new Model(R.drawable.tounge96,"Loss of smell and taste"));

        models.add(new Model(R.drawable.heartbl96,"Pnemonia like feeling"));



    }
}

package com.foribus.cityatoms.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.foribus.cityatoms.R;
import com.foribus.cityatoms.adapter.models.Model;
import com.foribus.cityatoms.startupui.SimpleSymptoms;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {
    private static final String TAG = "GridAdapter";
    Context context;
    List<Model> models = new ArrayList();
    int[] img = {R.drawable.srtb96, R.drawable.dcblu96, R.drawable.feverebl96, R.drawable.breathblu96,
            R.drawable.toungeblu96, R.drawable.heartblu96};
    private LayoutInflater mInflater;

    public GridAdapter(SimpleSymptoms context, List<Model> models) {
        this.context = context;
        this.models = models;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        return new GridViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        Model model = models.get(position);
        holder.myTextView.setText(model.getText());
        Log.d(TAG, "onBindViewHolder: " + model.getText());
        holder.imageView.setImageResource(model.getImage());

        holder.linear_l.setBackgroundColor(model.isSelected() ? Color.BLUE : Color.WHITE);
        holder.linear_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isSelected()) {
                    model.setSelected(!model.isSelected());
                    Log.d(TAG, "onClick: " + model.getText());
                    holder.linear_l.setBackgroundColor(model.isSelected() ? Color.BLUE : Color.WHITE);
                    holder.imageView.setImageResource(img[position]);
                    holder.myTextView.setTextColor(Color.BLUE);
                } else {
                    holder.myTextView.setTextColor(Color.BLACK);
                }
            }
        });

    }

    // total number of cells
    @Override
    public int getItemCount() {
        Log.d("getItemCount", "getItemCount" + models.size());
        return models.size();
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView imageView;
        CardView cardView;
        LinearLayout linear_l;
        private View view;

        GridViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            linear_l = itemView.findViewById(R.id.linear_l);
            myTextView = itemView.findViewById(R.id.textview);
            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardview);
            cardView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
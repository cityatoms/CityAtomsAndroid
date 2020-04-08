package com.github.abdularis.trackmylocation.adapter;

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

import com.github.abdularis.trackmylocation.Model;
import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.startupui.SimpleSymptoms;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {
    Context context;
    private static final String TAG = "GridAdapter";
    List<Model> models = new ArrayList();
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
                model.setSelected(!model.isSelected());
                Log.d(TAG, "onClick: "+model.getText());
                holder.linear_l.setBackgroundColor(model.isSelected() ? Color.BLUE : Color.WHITE);

            }
        });

    }

    // total number of cells
    @Override
    public int getItemCount() {
        Log.d("getItemCount", "getItemCount" + models.size());
        return models.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView imageView;
        CardView cardView;
        private View view;
        LinearLayout linear_l;

        GridViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            linear_l=itemView.findViewById(R.id.linear_l);
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

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
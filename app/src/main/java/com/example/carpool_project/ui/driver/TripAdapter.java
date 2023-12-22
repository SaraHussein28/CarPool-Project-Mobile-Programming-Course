package com.example.carpool_project.ui.driver;//package com.example.project_draft1;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool_project.R;
import com.example.carpool_project.ui.helpers.RouteHelperClass;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripViewHolder> {
    private ArrayList<RouteHelperClass> characters;


    public TripAdapter(ArrayList<RouteHelperClass> characters){
        this.characters = characters;
    }
    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from (parent.getContext()).inflate(R.layout.trip_status, parent, false);

        return new TripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
    RouteHelperClass currentCharacter = characters.get(position);
    holder.populateCharacterInfo(currentCharacter);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateItems(ArrayList<RouteHelperClass> trips) {
        characters.clear();
        characters.addAll(trips);
        notifyDataSetChanged();
    }
}

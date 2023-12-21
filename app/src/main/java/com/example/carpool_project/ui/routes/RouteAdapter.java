package com.example.carpool_project.ui.routes;//package com.example.project_draft1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool_project.R;
import com.example.carpool_project.ui.helpers.RouteHelperClass;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteViewHolder> {
//    private final List<Character> characters;

    private final ArrayList<RouteHelperClass> characters;


    public RouteAdapter(ArrayList<RouteHelperClass> characters){
        this.characters = characters;
    }
    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from (parent.getContext()).inflate(R.layout.item_route, parent, false);

        return new RouteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
    RouteHelperClass currentCharacter = characters.get(position);
    holder.populateCharacterInfo(currentCharacter);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }
}

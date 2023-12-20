package com.example.carpool_project;//package com.example.project_draft1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterViewHolder> {
//    private final List<Character> characters;

    private final ArrayList<RouteHelperClass> characters;


    public  CharacterAdapter(ArrayList<RouteHelperClass> characters){
        this.characters = characters;
    }
    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from (parent.getContext()).inflate(R.layout.item_route, parent, false);

        return new CharacterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
    RouteHelperClass currentCharacter = characters.get(position);
    holder.populateCharacterInfo(currentCharacter);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }
}

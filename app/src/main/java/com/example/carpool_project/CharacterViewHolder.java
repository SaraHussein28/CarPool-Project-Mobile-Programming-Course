package com.example.carpool_project;//package com.example.project_draft1;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final TextView pickUpTimeTextView;
    private final TextView dropOffTimeTextView;
    private final TextView sourcePointTextView;
    private final TextView destinationPointTextView;

    public CharacterViewHolder(@NonNull View itemView) {
        super(itemView);

        pickUpTimeTextView = itemView.findViewById(R.id.textView00);
        dropOffTimeTextView = itemView.findViewById(R.id.textView01);
        sourcePointTextView = itemView.findViewById(R.id.textView03);
        destinationPointTextView = itemView.findViewById(R.id.textView04);
        itemView.setOnClickListener(this);
    }

    public void populateCharacterInfo(Character character){
        pickUpTimeTextView.setText(character.getPickUpTime());
        dropOffTimeTextView.setText(character.getDropOffTime());
        sourcePointTextView.setText(character.getSourcePoint());
        destinationPointTextView.setText(character.getDestinationPoint());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == itemView.getId()){

            Intent intent = new Intent(itemView.getContext(), TripActivity.class);
            intent.putExtra("source", sourcePointTextView.getText());
            intent.putExtra("destination", destinationPointTextView.getText());
            intent.putExtra("drop_off_time", dropOffTimeTextView.getText());
            intent.putExtra("pick_up_time", pickUpTimeTextView.getText());
            itemView.getContext().startActivity(intent);

        }
    }
}

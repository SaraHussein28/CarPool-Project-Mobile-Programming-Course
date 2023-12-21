package com.example.carpool_project.ui.driver;//package com.example.project_draft1;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool_project.R;
import com.example.carpool_project.ui.helpers.RouteHelperClass;
import com.example.carpool_project.ui.trip_details.TripActivity;

public class TripViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final TextView pickUpTimeTextView;
    private final TextView dropOffTimeTextView;
    private final TextView sourcePointTextView;
    private final TextView destinationPointTextView;
    private final TextView tripStatus;
    private final TextView tripFare;
    private final TextView date;

    public TripViewHolder(@NonNull View itemView) {
        super(itemView);

        pickUpTimeTextView = itemView.findViewById(R.id.textView09);
        dropOffTimeTextView = itemView.findViewById(R.id.textView06);
        sourcePointTextView = itemView.findViewById(R.id.textView01);
        destinationPointTextView = itemView.findViewById(R.id.textView02);
        tripStatus = itemView.findViewById(R.id.textView05);
        tripFare = itemView.findViewById(R.id.textView04);
        date = itemView.findViewById(R.id.textView07);

        itemView.setOnClickListener(this);
    }

    public void populateCharacterInfo(RouteHelperClass character){
        pickUpTimeTextView.setText(character.getPickupTime());
        dropOffTimeTextView.setText(character.getDropOffTime());
        sourcePointTextView.setText(character.getSource());
        destinationPointTextView.setText(character.getDestination());
        tripStatus.setText(character.getStatus());
        tripFare.setText(character.getPrice());
        date.setText(character.getDay());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == itemView.getId()){

            //TODO: change the navigation here.
            Intent intent = new Intent(itemView.getContext(), TripActivity.class);
            intent.putExtra("source", sourcePointTextView.getText());
            intent.putExtra("destination", destinationPointTextView.getText());
            intent.putExtra("drop_off_time", dropOffTimeTextView.getText());
            intent.putExtra("pick_up_time", pickUpTimeTextView.getText());
            intent.putExtra("status", tripStatus.getText());
            intent.putExtra("price", tripFare.getText());
            intent.putExtra("date", date.getText());
            itemView.getContext().startActivity(intent);

        }
    }
}

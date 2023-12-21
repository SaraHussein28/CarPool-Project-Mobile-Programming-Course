package com.example.carpool_project.ui.routes;//package com.example.project_draft1;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool_project.R;
import com.example.carpool_project.ui.helpers.RouteHelperClass;
import com.example.carpool_project.ui.trip_details.TripActivity;

public class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final TextView pickUpTimeTextView;
    private final TextView dropOffTimeTextView;
    private final TextView sourcePointTextView;
    private final TextView destinationPointTextView;
    private String tripFare;
    private String date;
    private String routeId;
    private String status;

    public RouteViewHolder(@NonNull View itemView) {
        super(itemView);

        pickUpTimeTextView = itemView.findViewById(R.id.textView00);
        dropOffTimeTextView = itemView.findViewById(R.id.textView01);
        sourcePointTextView = itemView.findViewById(R.id.textView03);
        destinationPointTextView = itemView.findViewById(R.id.textView04);
        itemView.setOnClickListener(this);
    }

    public void populateCharacterInfo(RouteHelperClass character){
        pickUpTimeTextView.setText(character.getPickupTime());
        dropOffTimeTextView.setText(character.getDropOffTime());
        sourcePointTextView.setText(character.getSource());
        destinationPointTextView.setText(character.getDestination());
        status = character.getStatus();
        tripFare = character.getPrice();
        date = character.getDay();
        routeId = character.getRouteId();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == itemView.getId()){

            Intent intent = new Intent(itemView.getContext(), TripActivity.class);
            intent.putExtra("source", sourcePointTextView.getText());
            intent.putExtra("destination", destinationPointTextView.getText());
            intent.putExtra("drop_off_time", dropOffTimeTextView.getText());
            intent.putExtra("pick_up_time", pickUpTimeTextView.getText());
            intent.putExtra("price", tripFare);
            intent.putExtra("date", date);
            intent.putExtra("route_id", routeId);
            intent.putExtra("status", status);
            itemView.getContext().startActivity(intent);

        }
    }
}

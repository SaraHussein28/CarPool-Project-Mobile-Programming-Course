package com.example.carpool_project.ui.routes;

public class Character {
    private final String sourcePoint;
    private final String destinationPoint ;
    //TODO: consider using LocalTime and DateTimeFormatter to handle the dates.
    private final String pickUpTime;
    private final String dropOffTime;

    public Character(String sourcePoint, String destinationPoint, String pickUpTime, String dropOffTime) {
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;
        this.pickUpTime = pickUpTime;
        this.dropOffTime = dropOffTime;
    }

    public String getSourcePoint() {
        return sourcePoint;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public String getDropOffTime() {
        return dropOffTime;
    }

}

package com.example.carpool_project.ui.helpers;

import androidx.annotation.NonNull;

public class TripHelperClass {
    String source, destination, day, pickupTime, dropOffTime, price, status;

    public TripHelperClass() {

    }

    public TripHelperClass(String source, String destination, String day, String pickupTime, String dropOffTime, String price, String status) {
        this.source = source;
        this.destination = destination;
        this.day = day;
        this.pickupTime = pickupTime;
        this.dropOffTime = dropOffTime;
        this.price = price;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getDropOffTime() {
        return dropOffTime;
    }

    public void setDropOffTime(String dropOffTime) {
        this.dropOffTime = dropOffTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return (source.toString() + " " + destination.toString());
    }
}

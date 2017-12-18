package com.netcracker.unc.model.impl;


import com.netcracker.unc.model.Location;

public class Shark extends Fish {

    private int hungerTime;

    public Shark(Location location, int lifetime, int progenyPeriod, int searchRadius, int hungerTime) {
        super(location, lifetime, progenyPeriod, searchRadius);
        this.hungerTime = hungerTime;
    }

    @Override
    public void action() {

    }

    @Override
    public void move() {

    }

    @Override
    public void searchTarget() {

    }

    public void eat(){

    }

    public int getHungerTime() {
        return hungerTime;
    }

    public void setHungerTime(int hungerTime) {
        this.hungerTime = hungerTime;
    }
}

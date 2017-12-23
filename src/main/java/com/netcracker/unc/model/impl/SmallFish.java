package com.netcracker.unc.model.impl;

import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;

public class SmallFish extends Fish {

    public SmallFish() {
    }

    public SmallFish(Location location, int lifetime, int progenyPeriod, int searchRadius) {
        super(location, lifetime, progenyPeriod, searchRadius);
    }

    @Override
    public void action() {

    }

    @Override
    public void move() {
        Ocean ocean = Ocean.getInstanse();
        int x = this.getLocation().getX();
        int y = this.getLocation().getY();
        switch (ocean.getFlowList().get(x)) {
            case LEFT:

                break;
            case RIGHT:
                break;
            default:
                break;
        }
    }

    @Override
    public void searchTarget() {

    }
}

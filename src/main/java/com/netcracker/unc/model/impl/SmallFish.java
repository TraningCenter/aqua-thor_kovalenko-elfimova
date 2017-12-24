package com.netcracker.unc.model.impl;

import com.netcracker.unc.model.Direction;
import com.netcracker.unc.model.Flow;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.utils.CommonUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
        Location location = this.getLocation();
        Location newLocation;
        List<Direction> directions = new ArrayList(Arrays.asList(Direction.values()));
        if (this.getTarget() != null) {
            return;
        }
        switch (ocean.getFlowList().get(location.getX())) {
            case LEFT:
                newLocation = ocean.getEmptyLocation(Direction.LEFT, location);
                if (newLocation != null) {
                    ocean.moveFish(this, newLocation);
                    return;
                }
                directions.remove(Direction.LEFT);
                break;
            case RIGHT:
                newLocation = ocean.getEmptyLocation(Direction.RIGHT, location);
                if (newLocation != null) {
                    ocean.moveFish(this, newLocation);
                    return;
                }
                directions.remove(Direction.RIGHT);
                break;
        }
        Random rnd = new Random();
        int i;
        while (!directions.isEmpty()) {
            i = rnd.nextInt(directions.size());
            newLocation = ocean.getEmptyLocation(directions.get(i), location);
            if (newLocation != null) {
                ocean.moveFish(this, newLocation);
                return;
            }
            directions.remove(i);
        }
    }

    @Override
    protected boolean isEnemyPresent(Location currentLocation) {
        Ocean ocean = Ocean.getInstanse();
        return ocean.getFishByLocation(currentLocation) instanceof Shark;
    }
}

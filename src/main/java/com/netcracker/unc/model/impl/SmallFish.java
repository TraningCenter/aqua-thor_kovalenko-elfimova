package com.netcracker.unc.model.impl;

import com.netcracker.unc.model.Direction;
import com.netcracker.unc.model.FishType;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Realization of Fish - SmallFish
 */
public class SmallFish extends Fish {

    public SmallFish() {
    }

    /**
     * constructor
     *
     * @param location current location
     * @param lifetime max age
     * @param progenyPeriod number of births
     * @param searchRadius maximum number of cells to search
     */
    public SmallFish(Location location, int lifetime, int progenyPeriod, int searchRadius) {
        super(location, lifetime, progenyPeriod, searchRadius);
    }

    /**
     * one fish action (move, give birth, die)
     */
    @Override
    public void action() {
        Ocean ocean = Ocean.getInstanse();
        if (age >= lifetime) {
            die();
            return;
        }
        searchTarget();
        if (age % (lifetime / progenyPeriod - 1) == 0 && age != 0) {
            giveBirth();
        } else {
            move();
        }
        age++;
    }

    /**
     * move to next cell (random or target)
     */
    @Override
    public void move() {
        Ocean ocean = Ocean.getInstanse();
        Location newLocation;
        List<Direction> directions = new ArrayList(Arrays.asList(Direction.values()));
        if (target != null) {
            List<Direction> ds = Direction.getDirectionByLocations(location, target);
            if (ds != null) {
                directions.removeAll(ds);
            }
        } else {
            switch (ocean.getFlowList().get(location.getX())) {
                case LEFT:
                    newLocation = ocean.getNextLocation(Direction.LEFT, location);
                    if (newLocation != null) {
                        ocean.moveFish(this, newLocation);
                        return;
                    }
                    directions.remove(Direction.LEFT);
                    break;
                case RIGHT:
                    newLocation = ocean.getNextLocation(Direction.RIGHT, location);
                    if (newLocation != null) {
                        ocean.moveFish(this, newLocation);
                        return;
                    }
                    directions.remove(Direction.RIGHT);
                    break;
            }
        }
        Random rnd = new Random();
        int i;
        while (!directions.isEmpty()) {
            i = rnd.nextInt(directions.size());
            newLocation = ocean.getNextLocation(directions.get(i), location);
            if (newLocation != null) {
                ocean.moveFish(this, newLocation);
                return;
            }
            directions.remove(i);
        }
    }

    /**
     * check the presence of the enemy
     *
     * @param currentLocation location to check
     * @return true if shark is in this location
     */
    @Override
    protected boolean isEnemyPresent(Location currentLocation) {
        Ocean ocean = Ocean.getInstanse();
        return ocean.getFishByLocation(currentLocation) instanceof Shark;
    }

    /**
     * get fish type (SHARK/SMALL)
     *
     * @return SMALL
     */
    @Override
    public FishType getType() {
        return FishType.SMALL;
    }
}

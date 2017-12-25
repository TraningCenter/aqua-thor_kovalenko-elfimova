package com.netcracker.unc.model.impl;

import com.netcracker.unc.model.Direction;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Shark extends Fish {

    @XmlElement
    private int hungerTime;
    private int lifeTime;

    public Shark() {

    }

    public Shark(Fish fish, int hungerTime) {
        super(fish.getLocation(), fish.getLifetime(), fish.getProgenyPeriod(), fish.getSearchRadius());
        this.hungerTime = hungerTime;
    }


    public Shark(Location location, int lifetime, int progenyPeriod, int searchRadius, int hungerTime) {
        super(location, lifetime, progenyPeriod, searchRadius);
        this.hungerTime = hungerTime;
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
            List<Direction> ds = Direction.getDirectionByLocations(location, this.getTarget());
            for (Direction d : ds) {
                newLocation = ocean.getEmptyLocation(d, location);
                if (newLocation != null) {
                    ocean.moveFish(this, newLocation);
                    return;
                }
            }
            directions.removeAll(ds);
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
        return ocean.getFishByLocation(currentLocation) instanceof SmallFish;
    }

    public void eat() {

    }

    public int getHungerTime() {
        return hungerTime;
    }

    public void setHungerTime(int hungerTime) {
        this.hungerTime = hungerTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.hungerTime;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        final Shark other = (Shark) obj;
        return this.hungerTime == other.hungerTime;
    }

    public void setLifeTime(Integer lifeTime) {
        this.lifeTime = lifeTime;
    }
}

package com.netcracker.unc.model.impl;

import com.netcracker.unc.model.Direction;
import com.netcracker.unc.model.FishType;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.interfaces.IFish;
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
    private int hungerCounter;
    private boolean isAte;

    public Shark() {

    }

    public Shark(Location location, int lifetime, int progenyPeriod, int searchRadius, int hungerTime) {
        super(location, lifetime, progenyPeriod, searchRadius);
        this.hungerTime = hungerTime;
    }

    public Shark(Fish fish, int hungerTime) {
        super(fish.getLocation(), fish.getLifetime(), fish.getProgenyPeriod(), fish.getSearchRadius());
        this.hungerTime = hungerTime;
    }

    @Override
    public void action() {
        Ocean ocean = Ocean.getInstanse();
        isAte = false;
        if (age >= lifetime || (hungerCounter >= hungerTime)) {
            die();
            return;
        }
        searchTarget();
        if (progenyPeriod == 1 && age % (lifetime / 2 + 1) == 0 && age != 0 || age != 1 && age % (lifetime / progenyPeriod - 1) == 0 && age != 0) {
            giveBirth();
        } else {
            move();
        }
        age++;
        if (!isAte) {
            hungerCounter++;
        }
    }

    @Override
    public void move() {
        Ocean ocean = Ocean.getInstanse();
        Location newLocation;
        List<Direction> directions = new ArrayList(Arrays.asList(Direction.values()));
        if (target != null) {
            List<Direction> ds = Direction.getDirectionByLocations(location, target);
            if (ds != null) {
                for (Direction d : ds) {
                    newLocation = ocean.getNextLocation(d, location);
                    if (newLocation != null) {
                        if (newLocation.equals(target)) {
                            eat(newLocation);
                            return;
                        }
                        ocean.moveFish(this, newLocation);
                        return;
                    }
                    directions.remove(d);
                }
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

    @Override
    protected boolean isEnemyPresent(Location currentLocation) {
        Ocean ocean = Ocean.getInstanse();
        return ocean.getFishByLocation(currentLocation) instanceof SmallFish;
    }

    @Override
    public FishType getType() {
        return FishType.SHARK;
    }

    public void eat(Location newLocation) {
        Ocean ocean = Ocean.getInstanse();
        IFish fish = ocean.getFishByLocation(newLocation);
        fish.die();
        ocean.moveFish(this, newLocation);
        hungerCounter = 0;
        isAte = true;
    }

    public int getHungerTime() {
        return hungerTime;
    }

    public void setHungerTime(int hungerTime) {
        this.hungerTime = hungerTime;
    }

    public int getHungerCounter() {
        return hungerCounter;
    }

    public void setHungerCounter(int hungerCounter) {
        this.hungerCounter = hungerCounter;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.hungerTime;
        hash = 11 * hash + this.hungerCounter;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Shark other = (Shark) obj;
        if (this.hungerTime != other.hungerTime) {
            return false;
        }
        return this.hungerCounter == other.hungerCounter;
    }
}

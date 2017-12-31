package com.netcracker.unc.model.impl;

import com.netcracker.unc.creator.FishCreator;
import com.netcracker.unc.model.FishType;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.interfaces.IFish;
import com.netcracker.unc.utils.CommonUtils;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Fish implements IFish {

    @XmlElement
    protected Location location;
    @XmlElement
    protected int lifetime;
    @XmlElement
    protected int progenyPeriod;
    protected int age;
    @XmlElement
    protected int searchRadius;
    protected Location target;

    public Fish() {

    }

    public Fish(Location location, int lifetime, int progenyPeriod, int searchRadius) {
        this.location = location;
        this.lifetime = lifetime;
        this.progenyPeriod = progenyPeriod;
        this.searchRadius = searchRadius;
    }

    protected abstract boolean isEnemyPresent(Location currentLocation);

    @Override
    public abstract void move();

    @Override
    public abstract FishType getType();

    @Override
    public void searchTarget() {
        Ocean ocean = Ocean.getInstanse();
        int x = location.getX();
        int y = location.getY();
        Location nearestLocation = null;
        double nearestDistance = 0;
        Location currentLocation;
        double currentDistance;
        for (int i = x - searchRadius, currx = i; i <= x + searchRadius; currx = ++i) {
            for (int j = y - searchRadius, curry = j; j <= y + searchRadius; curry = ++j) {
                if (i < 0 || i >= ocean.getHeight()) {
                    currx = CommonUtils.getCorrectX(i, ocean.getHeight(), ocean.isTor());
                    if (currx < 0) {
                        continue;
                    }
                }
                if (j < 0 || j >= ocean.getWidth()) {
                    curry = CommonUtils.getCorrectY(j, ocean.getWidth(), ocean.isTor());
                    if (curry < 0) {
                        continue;
                    }
                }
                currentLocation = new Location(currx, curry);
                currentDistance = CommonUtils.getDistanceBetweenLocations(location, new Location(i, j));
                if (currentDistance <= searchRadius && !ocean.isEmptyLocation(currentLocation) && isEnemyPresent(currentLocation)) {
                    if (nearestLocation == null || currentDistance < nearestDistance) {
                        nearestLocation = currentLocation;
                        nearestDistance = currentDistance;
                    }
                }
            }
        }
        this.setTarget(nearestLocation);
    }

    @Override
    public void giveBirth() {
        Ocean ocean = Ocean.getInstanse();
        if (ocean.getAllPopulation() > ocean.getMaxPopulation()) {
            move();
            return;
        }
        Location oldLocation = location;
        move();
        if (location.equals(oldLocation)) {
            die();
        }
        IFish fish = FishCreator.createSuccessorFish(this, oldLocation);
        ocean.addFish(fish);
    }

    @Override
    public void die() {
        Ocean ocean = Ocean.getInstanse();
        ocean.getMatrix()[location.getX()][location.getY()] = null;
        if (getType() == FishType.SHARK) {
            ocean.getSharks().remove(this);
        } else if (getType() == FishType.SMALL) {
            ocean.getSmallFishes().remove(this);
        }
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public int getProgenyPeriod() {
        return progenyPeriod;
    }

    public void setProgenyPeriod(int progenyPeriod) {
        this.progenyPeriod = progenyPeriod;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSearchRadius() {
        return searchRadius;
    }

    public void setSearchRadius(int searchRadius) {
        this.searchRadius = searchRadius;
    }

    @Override
    public Location getTarget() {
        return target;
    }

    @Override
    public void setTarget(Location target) {
        this.target = target;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.location);
        hash = 29 * hash + this.lifetime;
        hash = 29 * hash + this.progenyPeriod;
        hash = 29 * hash + this.age;
        hash = 29 * hash + this.searchRadius;
        hash = 29 * hash + Objects.hashCode(this.target);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fish other = (Fish) obj;
        if (this.lifetime != other.lifetime) {
            return false;
        }
        if (this.progenyPeriod != other.progenyPeriod) {
            return false;
        }
        if (this.age != other.age) {
            return false;
        }
        if (this.searchRadius != other.searchRadius) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        return Objects.equals(this.target, other.target);
    }

}

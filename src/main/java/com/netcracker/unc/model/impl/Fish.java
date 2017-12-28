package com.netcracker.unc.model.impl;

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
    private Location location;
    @XmlElement
    private int lifetime;
    @XmlElement
    private int progenyPeriod;
    private int age;
    @XmlElement
    private int searchRadius;
    private Location target;

    public Fish() {

    }

    public Fish(Location location, int lifetime, int progenyPeriod, int searchRadius) {
        this.location = location;
        this.lifetime = lifetime;
        this.progenyPeriod = progenyPeriod;
        this.searchRadius = searchRadius;
    }

    public Fish(Location location, int lifetime, int progenyPeriod, int age, int searchRadius) {
        this.location = location;
        this.lifetime = lifetime;
        this.progenyPeriod = progenyPeriod;
        this.age = age;
        this.searchRadius = searchRadius;
    }

    @Override
    public void searchTarget() {
        Ocean ocean = Ocean.getInstanse();
        int x = location.getX();
        int y = location.getY();
        Location nearestLocation = this.getTarget();
        double nearestDistance = 0;
        if (nearestLocation != null) {
            nearestDistance = CommonUtils.getDistanceBetweenLocations(this.getLocation(), nearestLocation);
        }
        Location currentLocation = null;
        double currentDistance = 0;
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
                currentDistance = CommonUtils.getDistanceBetweenLocations(this.getLocation(), new Location(i, j));
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

    protected abstract boolean isEnemyPresent(Location currentLocation);

    @Override
    public void giveBirth() {

    }

    @Override
    public void die() {

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

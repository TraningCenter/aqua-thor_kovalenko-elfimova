package com.netcracker.unc.model.impl;

import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.interfaces.IFish;
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
        age = lifetime;
    }

    @Override
    public void giveBirth() {

    }

    @Override
    public void die() {

    }

    public Location getLocation() {
        return location;
    }

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

    public Location getTarget() {
        return target;
    }

    public void setTarget(Location target) {
        this.target = target;
    }
}

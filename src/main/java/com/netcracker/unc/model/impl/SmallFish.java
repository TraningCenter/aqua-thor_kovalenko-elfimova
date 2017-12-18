package com.netcracker.unc.model.impl;

import com.netcracker.unc.model.Location;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

public class SmallFish extends Fish {

    public SmallFish() {
    }

    public SmallFish(Location location, int lifetime, int progenyPeriod, int searchRadius) {
        super(location, lifetime, progenyPeriod, searchRadius);
    }

    public SmallFish(Location location, int lifetime, int progenyPeriod, int age, int searchRadius) {
        super(location, lifetime, progenyPeriod, age, searchRadius);
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
}

package com.netcracker.unc.model.interfaces;

import com.netcracker.unc.model.FishType;
import com.netcracker.unc.model.Location;

public interface IFish {

    void action();

    void move();

    void giveBirth();

    void searchTarget();

    void die();

    Location getLocation();

    void setLocation(Location location);

    Location getTarget();

    void setTarget(Location location);

    FishType getType();

    int getAge();
}

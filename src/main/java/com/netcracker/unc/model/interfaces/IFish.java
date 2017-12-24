package com.netcracker.unc.model.interfaces;

import com.netcracker.unc.model.Location;

public interface IFish {

    void action();

    void move();

    void giveBirth();

    void searchTarget();

    void die();
    
    Location getLocation();
}

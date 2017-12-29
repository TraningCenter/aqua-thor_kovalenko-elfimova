package com.netcracker.unc.creator;

import com.netcracker.unc.model.FishType;
import static com.netcracker.unc.model.FishType.SHARK;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;

public class FishCreator {

    public static IFish createFish(FishType fishType, Location location) {
        if (fishType == SHARK) {
            return new Shark(location, 3, 1, 5, 2);
        } else {
            return new SmallFish(location, 2, 1, 2);
        }
    }

}

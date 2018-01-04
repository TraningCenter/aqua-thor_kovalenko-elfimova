package com.netcracker.unc.creator;

import com.netcracker.unc.model.FishType;
import static com.netcracker.unc.model.FishType.SHARK;
import static com.netcracker.unc.model.FishType.SMALL;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import com.netcracker.unc.utils.CommonUtils;
import java.util.Random;

public class FishCreator {

    public static IFish createFish(FishType fishType) {
        Location emptyLocation = getEmptyLocation();
        if (fishType == SHARK) {
            return createShark(emptyLocation, null);
        } else if (fishType == SMALL) {
            return createSmallFish(emptyLocation, null);
        }
        return null;
    }

    public static IFish createSuccessorFish(IFish fish, Location location) {
        if (fish.getType() == SHARK) {
            return createShark(location, (Shark) fish);
        } else if (fish.getType() == SMALL) {
            return createSmallFish(location, (SmallFish) fish);
        }
        return null;
    }

    private static Location getEmptyLocation() {
        int i, j;
        Ocean ocean = Ocean.getInstanse();
        while (true) {
            i = CommonUtils.randInt(0, ocean.getHeight() - 1);
            j = CommonUtils.randInt(0, ocean.getWidth() - 1);
            if (ocean.getMatrix()[i][j] == null) {
                return new Location(i, j);
            }
        }
    }

    private static IFish createShark(Location location, Shark fish) {
        if (fish != null) {
            return new Shark(location, fish.getLifetime(), fish.getProgenyPeriod(), fish.getSearchRadius(), fish.getHungerTime());
        }
        int lifetime = CommonUtils.randInt(5, 100);
        int searchRadius = CommonUtils.randInt(1, 10);
        int hungerTime = CommonUtils.randInt(lifetime / 3, lifetime - 1);
        return new Shark(location, lifetime, 1, searchRadius, hungerTime);
    }

    private static IFish createSmallFish(Location location, SmallFish fish) {
        if (fish != null) {
            return new SmallFish(location, fish.getLifetime(), fish.getProgenyPeriod(), fish.getSearchRadius());
        }
        int lifetime = CommonUtils.randInt(5, 100);
        int progenyPeriod = CommonUtils.randInt(1, lifetime / 5);
        int searchRadius = CommonUtils.randInt(1, 5);
        return new SmallFish(location, lifetime, progenyPeriod, searchRadius);
    }

}

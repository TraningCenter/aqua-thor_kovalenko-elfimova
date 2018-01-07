package com.netcracker.unc.creator;

import com.netcracker.unc.model.FishType;
import static com.netcracker.unc.model.FishType.SHARK;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import com.netcracker.unc.utils.CommonUtils;

/**
 * Class to create new object of fish (smallfish/shark)
 */
public class FishCreator {

    /**
     * create new random fish by type (SHARK/SMALL)
     *
     * @param fishType type of fish
     * @return new random fish.
     */
    public static IFish createFish(FishType fishType) {
        Location emptyLocation = getEmptyLocation();
        if (fishType == SHARK) {
            return createShark(null, emptyLocation);
        } else {
            return createSmallFish(null, emptyLocation);
        }
    }

    /**
     * create new fish with parent parameters in specific location
     *
     * @param fish parent fish
     * @param location specific location
     * @return new successor fish.
     */
    public static IFish createSuccessorFish(IFish fish, Location location) {
        if (fish.getType() == SHARK) {
            return createShark((Shark) fish, location);
        } else {
            return createSmallFish((SmallFish) fish, location);
        }
    }

    /**
     * get random empty location
     *
     * @return empty location
     */
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

    /**
     * create new shark with parent parameters (or random shark) in specific
     * location
     *
     * @param fish parent fish
     * @param location specific location
     * @return new successor shark. If parent fish is null returns random shark
     */
    private static IFish createShark(Shark fish, Location location) {
        if (fish != null) {
            return new Shark(location, fish.getLifetime(), fish.getProgenyPeriod(), fish.getSearchRadius(), fish.getHungerTime());
        }
        int lifetime = CommonUtils.randInt(5, 100);
        int searchRadius = CommonUtils.randInt(1, 10);
        int hungerTime = CommonUtils.randInt(lifetime / 3, lifetime - 1);
        return new Shark(location, lifetime, 1, searchRadius, hungerTime);
    }

    /**
     * create new smallfish with parent parameters (or random smallfish) in
     * specific location
     *
     * @param fish parent fish
     * @param location specific location
     * @return new successor smallfish. If parent fish is null returns random
     * smallfish
     */
    private static IFish createSmallFish(SmallFish fish, Location location) {
        if (fish != null) {
            return new SmallFish(location, fish.getLifetime(), fish.getProgenyPeriod(), fish.getSearchRadius());
        }
        int lifetime = CommonUtils.randInt(5, 100);
        int progenyPeriod = CommonUtils.randInt(1, lifetime / 5);
        int searchRadius = CommonUtils.randInt(1, 5);
        return new SmallFish(location, lifetime, progenyPeriod, searchRadius);
    }
}

package com.netcracker.unc.creator;

import com.netcracker.unc.model.Flow;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to create new random ocean
 */
public class OceanCreator {

    /**
     * get default ocean configuration (10*50)
     *
     * @return ocean configuration
     */
    public static OceanConfig getDefaultOceanConfig() {
        List<Flow> flows = new ArrayList();
        flows.add(Flow.LEFT);
        flows.add(Flow.RIGHT);
        flows.add(Flow.NONE);
        flows.add(Flow.NONE);
        flows.add(Flow.RIGHT);
        flows.add(Flow.LEFT);
        flows.add(Flow.RIGHT);
        flows.add(Flow.NONE);
        flows.add(Flow.NONE);
        List<IFish> sharks = new ArrayList();
        sharks.add(new Shark(new Location(0, 1), 40, 1, 4, 25));
        sharks.add(new Shark(new Location(9, 49), 45, 1, 4, 25));
        sharks.add(new Shark(new Location(1, 20), 45, 1, 4, 25));
        List<IFish> smallFishes = new ArrayList();
        smallFishes.add(new SmallFish(new Location(0, 7), 30, 2, 1));
        smallFishes.add(new SmallFish(new Location(1, 9), 30, 2, 1));
        smallFishes.add(new SmallFish(new Location(4, 40), 30, 2, 1));
        smallFishes.add(new SmallFish(new Location(5, 29), 30, 2, 1));
        return new OceanConfig(true, 10, 50, flows, 10, sharks, smallFishes);
    }
}

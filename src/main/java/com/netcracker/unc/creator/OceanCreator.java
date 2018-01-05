package com.netcracker.unc.creator;

import com.netcracker.unc.model.Flow;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import java.util.ArrayList;
import java.util.List;

public class OceanCreator {

    public static void createOcean() {

    }

    public static OceanConfig getDefaultOceanConfig() {
        List<Flow> flows = new ArrayList();
        flows.add(Flow.LEFT);
        flows.add(Flow.RIGHT);
        List<IFish> sharks = new ArrayList();
        sharks.add(new Shark(new Location(0, 1), 40, 1, 4, 20));
        List<IFish> smallFishes = new ArrayList();
        smallFishes.add(new SmallFish(new Location(0, 7), 30, 3, 2));
        smallFishes.add(new SmallFish(new Location(1, 9), 30, 3, 2));
        return new OceanConfig(true, 2, 20, flows, 10, sharks, smallFishes);
    }
}

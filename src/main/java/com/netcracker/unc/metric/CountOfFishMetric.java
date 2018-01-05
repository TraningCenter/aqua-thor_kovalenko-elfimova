package com.netcracker.unc.metric;

import com.netcracker.unc.model.Ocean;


public class CountOfFishMetric implements IMetric{

    private String name;
    private double value;

    @Override
    public void calculateValue(Ocean o) {
        value=o.getSmallFishes().size();
    }

    @Override
    public String getName() {
        name=String.valueOf(value);
        return name;
    }
}

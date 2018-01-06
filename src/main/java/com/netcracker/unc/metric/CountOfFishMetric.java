package com.netcracker.unc.metric;

import com.netcracker.unc.model.Ocean;

public class CountOfFishMetric implements IMetric {

    private String name;
    private double value;

    @Override
    public void calculateValue(Ocean o) {
    }

    @Override
    public String getName() {
        return name;
    }
}

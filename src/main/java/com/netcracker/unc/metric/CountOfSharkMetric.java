package com.netcracker.unc.metric;

import com.netcracker.unc.model.Ocean;

import java.util.ArrayList;

public class CountOfSharkMetric implements IMetric{

    private ArrayList<Integer> countOfSharks;
    private String name;
    private double value;

    @Override
    public void calculateValue(Ocean o) {
        value=o.getSharks().size();
    }

    @Override
    public String getName() {
        name=String.valueOf(value);
        return name;
    }
}


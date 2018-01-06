package com.netcracker.unc.metric;

import com.netcracker.unc.model.Ocean;


public interface IMetric {
    public void calculateValue(Ocean o);
    public String getName();
    public double getValue();
}

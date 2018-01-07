package com.netcracker.unc.metric;


public class Metric implements IMetric{

    private String name;
    private double value;

    public Metric(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

}

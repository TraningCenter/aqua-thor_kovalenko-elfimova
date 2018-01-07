package com.netcracker.unc.metric;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Metric class
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Metric implements IMetric{

    @XmlElement(name="name")    
    private String name;
    @XmlElement(name="value")
    private double value;

    /**
     * metric constructor
     *
     * @param name current name of metric
     * @param value current value of metric
     *
     */

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

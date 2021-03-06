package com.netcracker.unc.metric;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * Snapshot class list of metrics in a certain step
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Snapshot {

    @XmlElement(name = "step")
    private int step;
    @XmlElementWrapper(name = "metrics")
    @XmlElement(name = "metric", type = Metric.class)
    private List<IMetric> metricList;

    /**
     * Snapshot constructor
     *
     * @param step contains the current step number of the ocean
     * @param metricList contains a list of metrics on the current step of the
     * ocean
     *
     */
    public Snapshot(int step, List<IMetric> metricList) {
        this.step = step;
        this.metricList = metricList;
    }

    /**
     * Empty snapshot constructor
     */
    public Snapshot() {
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public List<IMetric> getMetricList() {
        return metricList;
    }

    public void setMetricList(List<IMetric> metricList) {
        this.metricList = metricList;
    }
}

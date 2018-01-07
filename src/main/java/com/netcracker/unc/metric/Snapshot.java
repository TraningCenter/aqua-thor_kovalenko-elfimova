package com.netcracker.unc.metric;

import java.util.List;

/**
 * Snapshot class
 * list of metrics in a certain step
 */
public class Snapshot {
    private int step;
    private List<IMetric> metricList;

    /**
     * Snapshot constructor
     *
     * @param step contains the current step number of the ocean
     * @param metricList contains a list of metrics on the current step of the ocean
     *
     */

    public Snapshot(int step, List<IMetric> metricList) {
        this.step = step;
        this.metricList = metricList;
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

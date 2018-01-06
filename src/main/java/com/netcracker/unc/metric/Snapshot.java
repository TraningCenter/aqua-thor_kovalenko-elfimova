package com.netcracker.unc.metric;

import java.util.List;

/**
 * Created on 06.01.2018.
 */
public class Snapshot {
    private int step;
    private List<IMetric> metricList;

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

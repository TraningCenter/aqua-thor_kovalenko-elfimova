package com.netcracker.unc.metric;

import com.netcracker.unc.model.Ocean;

import java.util.ArrayList;
import java.util.List;

public class MetricsWriter {

    public static List<Snapshot> snapshots=new ArrayList();

    public MetricsWriter(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }

    public MetricsWriter() {
    }

    public static void writeMetric(Ocean ocean){
        List<IMetric> metricList=new ArrayList();
        metricList.add(new Metric("FishCount",ocean.getSmallFishes().size()));
        metricList.add(new Metric("SharkCount",ocean.getSharks().size()));
        snapshots.add(new Snapshot (ocean.getStep(),  metricList));
    }

    public List<Snapshot> getSnapshots() {
        return snapshots;
    }
}

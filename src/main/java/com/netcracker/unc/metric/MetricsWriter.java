package com.netcracker.unc.metric;

import com.netcracker.unc.model.Ocean;

import java.util.ArrayList;
import java.util.List;

/**
 * MetricsWriter class
 */
public class MetricsWriter {

    public static List<Snapshot> snapshots=new ArrayList();

    /**
     * MetricsWriter constructor
     *
     * @param snapshots contains list of snapshots at each step
     *
     */
    public MetricsWriter(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }

    /**
     * empty MetricsWriter constructor
     */
    public MetricsWriter() {
    }

    /**
     * add a snapshot to the snapshot list at each step
     *
     * @param ocean ocean
     */
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

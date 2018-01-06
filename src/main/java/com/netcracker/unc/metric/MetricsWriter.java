package com.netcracker.unc.metric;

import java.util.List;

public class MetricsWriter {

    public List<Snapshot> snapshots;

    public MetricsWriter(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }

    public static void writeMetric(IMetric m){
    }
}

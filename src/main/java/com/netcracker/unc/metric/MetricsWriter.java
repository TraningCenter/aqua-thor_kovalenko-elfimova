package com.netcracker.unc.metric;

import com.netcracker.unc.model.Ocean;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * MetricsWriter class
 */
@XmlRootElement(name = "snapshots")
@XmlAccessorType(XmlAccessType.FIELD)
public class MetricsWriter {

    @XmlElement(name = "snapshot")
    public List<Snapshot> snapshots = new ArrayList();

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
     */
    public void writeMetric() {
        Ocean ocean = Ocean.getInstanse();
        List<IMetric> metricList = new ArrayList();
        metricList.add(new Metric("FishCount", ocean.getSmallFishes().size()));
        metricList.add(new Metric("SharkCount", ocean.getSharks().size()));
        snapshots.add(new Snapshot(ocean.getStep(), metricList));
    }

    public List<Snapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }

}

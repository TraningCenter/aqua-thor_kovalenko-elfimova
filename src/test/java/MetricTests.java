
import com.netcracker.unc.metric.IMetric;
import com.netcracker.unc.metric.Metric;
import com.netcracker.unc.metric.MetricsWriter;
import com.netcracker.unc.metric.Snapshot;
import com.netcracker.unc.model.Ocean;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import tools.ParsersTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for testing the MetricWriter class
 */
public class MetricTests {

    Ocean ocean;

    @Before
    public void init() {
        ocean = new Ocean(ParsersTools.getOceanConfig());
    }

    @Test
    public void metricsWriterTest() {
        MetricsWriter metricsWriter = new MetricsWriter();
        metricsWriter.writeMetric();
        List<IMetric> metricList = new ArrayList();
        Metric metric = new Metric();
        metric.setName("FishCount");
        metric.setValue(1);
        metricList.add(metric);
        metricList.add(new Metric("SharkCount", 4));
        List<Snapshot> snapshotslist = new ArrayList();
        Snapshot snapshot = new Snapshot();
        snapshot.setStep(10);
        snapshot.setMetricList(metricList);
        snapshotslist.add(snapshot);
        snapshotslist.add(new Snapshot(20, metricList));
        metricsWriter.setSnapshots(snapshotslist);
        Assert.assertEquals(2, metricsWriter.getSnapshots().size());
    }
}

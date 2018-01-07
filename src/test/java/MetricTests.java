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

import static com.netcracker.unc.metric.MetricsWriter.snapshots;
import static com.netcracker.unc.metric.MetricsWriter.writeMetric;

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
        writeMetric(ocean);
        List<IMetric> metricList= new ArrayList();
        metricList.add(new Metric("FishCount", 1));
        metricList.add(new Metric("SharkCount", 4));
        List<Snapshot> snapshotslist = new ArrayList();
        snapshotslist.add(new Snapshot (0,  metricList));
        MetricsWriter metricsWriter=new MetricsWriter(snapshotslist);
        ocean.changeFlow();
        Assert.assertEquals(metricsWriter.getSnapshots(), snapshots);
    }
}

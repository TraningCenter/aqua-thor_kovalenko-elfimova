package tools;


import com.netcracker.unc.metric.*;
import com.netcracker.unc.model.Flow;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import java.util.ArrayList;
import java.util.List;

public class ParsersTools {

    public static String XMLString = "<ocean><tor>true</tor><height>2</height><width>20</width><flows><flow>LEFT</flow><flow>RIGHT</flow></flows><changeFlow>10</changeFlow>"
            + "<sharks><shark><lifetime>40</lifetime><progenyPeriod>1</progenyPeriod><searchRadius>4</searchRadius><hungerTime>20</hungerTime><location><x>0</x><y>1</y></location></shark></sharks>"
            + "<smallFishes><fish><lifetime>30</lifetime><progenyPeriod>3</progenyPeriod><searchRadius>2</searchRadius><location><x>0</x><y>7</y></location></fish>"
            + "<fish><lifetime>30</lifetime><progenyPeriod>3</progenyPeriod><searchRadius>2</searchRadius><location><x>1</x><y>9</y></location></fish></smallFishes></ocean>";

    public static String XMLStringWithoutOceanTag = "<tor>false</tor><height>2</height><width>20</width><flows><flow>LEFT</flow></flows><changeFlow>10</changeFlow>"
            + "<sharks><shark><lifetime>40</lifetime><progenyPeriod>1</progenyPeriod><searchRadius>4</searchRadius><hungerTime>20</hungerTime><location><x>0</x><y>1</y></location></shark></sharks>"
            + "<smallFishes><fish><lifetime>30</lifetime><progenyPeriod>3</progenyPeriod><searchRadius>2</searchRadius><location><x>0</x><y>7</y></location></fish></smallFishes>";

    public static String XMLStringWrongTags = "<ocean><wrongtag>wrong</wrongtag><tor>true</tor><height>2</height><width>20</width><flows><flow>LEFT</flow><flow>RIGHT</flow></flows><changeFlow>10</changeFlow>"
            + "<sharks><shark><lifetime>40</lifetime><progenyPeriod>1</progenyPeriod><searchRadius>4</searchRadius><hungerTime>20</hungerTime><location><x>0</x><y>1</y></location></shark></sharks>"
            + "<smallFishes><fish><lifetime>30</lifetime><progenyPeriod>3</progenyPeriod><searchRadius>2</searchRadius><location><x>0</x><y>7</y></location></fish></smallFishes></ocean>";

    public static String XMLStringWithoutFishes = "<ocean><tor>true</tor><height>2</height><width>20</width><flows><flow>LEFT</flow><flow>RIGHT</flow></flows><changeFlow>10</changeFlow></ocean>";

    public static String XMLStringMonitoring = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><snapshots><snapshot><step>4</step><metrics><metric><name>FishCount</name><value>1.0</value></metric><metric><name>SharkCount</name><value>4.0</value></metric></metrics></snapshot></snapshots>";

    public static OceanConfig getOceanConfig() {
        List<Flow> flows = new ArrayList();
        flows.add(Flow.LEFT);
        flows.add(Flow.RIGHT);
        List<IFish> sharks = new ArrayList();
        sharks.add(new Shark(new Location(0, 1), 40, 1, 4, 20));
        List<IFish> smallFishes = new ArrayList();
        smallFishes.add(new SmallFish(new Location(0, 7), 30, 3, 2));
        smallFishes.add(new SmallFish(new Location(1, 9), 30, 3, 2));
        return new OceanConfig(true, 2, 20, flows, 10, sharks, smallFishes);
    }

    public static MetricsWriter getMetricsWriter() {
        List<IMetric> metricList= new ArrayList();
        metricList.add(new Metric("FishCount", 1));
        metricList.add(new Metric("SharkCount", 4));
        List<Snapshot> snapshots = new ArrayList();
        snapshots.add(new Snapshot (4,  metricList));
        return new MetricsWriter(snapshots);
    }
}

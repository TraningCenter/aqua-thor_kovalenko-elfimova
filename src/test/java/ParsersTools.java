
import com.netcracker.unc.model.Flow;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import java.util.ArrayList;
import java.util.List;

public class ParsersTools {

    public static final String XMLStringEx1 = "<ocean><tor>true</tor><height>2</height><weight>20</weight><flows><flow>LEFT</flow><flow>RIGHT</flow></flows><changeFlow>10</changeFlow>"
            + "<sharks><shark><lifetime>40</lifetime><progenyPeriod>21</progenyPeriod><searchRadius>4</searchRadius><hungerTime>20</hungerTime><location><x>0</x><y>1</y></location></shark></sharks>"
            + "<smallFishes><fish><lifetime>30</lifetime><progenyPeriod>9</progenyPeriod><searchRadius>2</searchRadius><location><x>0</x><y>7</y></location></fish>"
            + "<fish><lifetime>30</lifetime><progenyPeriod>9</progenyPeriod><searchRadius>2</searchRadius><location><x>1</x><y>9</y></location></fish></smallFishes></ocean>";

    public static OceanConfig getOceanConfigEx1() {
        List<Flow> flows = new ArrayList();
        flows.add(Flow.LEFT);
        flows.add(Flow.RIGHT);
        List<IFish> sharks = new ArrayList();
        sharks.add(new Shark(new Location(0, 1), 40, 21, 0, 4, 20));
        List<IFish> smallFishes = new ArrayList();
        smallFishes.add(new SmallFish(new Location(0, 7), 30, 9, 0, 2));
        smallFishes.add(new SmallFish(new Location(1, 9), 30, 9, 0, 2));
        return new OceanConfig(true, 2, 20, flows, 10, sharks, smallFishes);
    }
}
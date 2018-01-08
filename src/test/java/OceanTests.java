
import tools.ParsersTools;
import com.netcracker.unc.model.Direction;
import com.netcracker.unc.model.Flow;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.OceanConfig;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class OceanTests {

    Ocean ocean;

    @Before
    public void init() {
        ocean = new Ocean(ParsersTools.getOceanConfig());
    }

    @Test
    public void getLocationsTest() {
        Location location = new Location(1, 0);
        //LEFT
        //Tor - true
        Assert.assertEquals(ocean.getNextLocation(Direction.LEFT, location), new Location(1, 19));
        //Tor - false
        ocean.setIsTor(false);
        Assert.assertNull(ocean.getNextLocation(Direction.LEFT, location));
        location = new Location(1, 5);
        Assert.assertEquals(ocean.getNextLocation(Direction.LEFT, location), new Location(1, 4));

        //RIGHT
        Assert.assertEquals(ocean.getNextLocation(Direction.RIGHT, location), new Location(1, 6));
        //Tor - false
        location = new Location(1, 19);
        Assert.assertNull(ocean.getNextLocation(Direction.RIGHT, location));
        //Tor - true
        ocean.setIsTor(true);
        Assert.assertEquals(ocean.getNextLocation(Direction.RIGHT, location), new Location(1, 0));

        //UP
        Assert.assertEquals(ocean.getNextLocation(Direction.UP, location), new Location(0, 19));
        //Tor - true
        location = new Location(0, 1);
        Assert.assertEquals(ocean.getNextLocation(Direction.UP, location), new Location(1, 1));
        //Tor - false
        ocean.setIsTor(false);
        Assert.assertNull(ocean.getNextLocation(Direction.UP, location));

        //DOWN
        Assert.assertEquals(ocean.getNextLocation(Direction.DOWN, location), new Location(1, 1));
        //Tor - false
        location = new Location(1, 5);
        Assert.assertNull(ocean.getNextLocation(Direction.DOWN, location));
        //Tor - true
        ocean.setIsTor(true);
        Assert.assertEquals(ocean.getNextLocation(Direction.DOWN, location), new Location(0, 5));
    }

    @Test
    public void getDirectionByLocationsTest() {
        Location location1 = new Location(1, 2);
        Location location2 = new Location(1, 3);
        Assert.assertEquals(Direction.RIGHT, Direction.getDirectionByLocations(location1, location2).get(0));
        Assert.assertEquals(1, Direction.getDirectionByLocations(location1, location2).size());
        location2 = new Location(2, 2);
        Assert.assertEquals(Direction.DOWN, Direction.getDirectionByLocations(location1, location2).get(0));
        Assert.assertEquals(1, Direction.getDirectionByLocations(location1, location2).size());
        location2 = new Location(0, 2);
        Assert.assertEquals(Direction.UP, Direction.getDirectionByLocations(location1, location2).get(0));
        Assert.assertEquals(1, Direction.getDirectionByLocations(location1, location2).size());
        location2 = new Location(1, 1);
        Assert.assertEquals(Direction.LEFT, Direction.getDirectionByLocations(location1, location2).get(0));
        Assert.assertEquals(1, Direction.getDirectionByLocations(location1, location2).size());
        location2 = new Location(3, 3);
        Assert.assertEquals(2, Direction.getDirectionByLocations(location1, location2).size());
        Assert.assertTrue(Direction.getDirectionByLocations(location1, location2).contains(Direction.DOWN));
        Assert.assertTrue(Direction.getDirectionByLocations(location1, location2).contains(Direction.RIGHT));
        location2 = new Location(1, 2);
        Assert.assertNull(Direction.getDirectionByLocations(location1, location2));
    }

    @Test
    public void changeFlowTest() {
        ocean.changeFlow();
        Assert.assertEquals(2, ocean.getFlowList().size());
    }

    @Test
    public void oneStepTest() {
        ocean.oneStep();
        Assert.assertEquals(1, ocean.getSharks().get(0).getAge());
        Assert.assertEquals(1, ocean.getSmallFishes().get(0).getAge());
        Assert.assertEquals(1, ocean.getStep());
    }

    @Test
    public void getFlowByIndexTest() {
        Flow flow = Flow.fromIndex(0);
        Assert.assertEquals(flow, Flow.RIGHT);
        flow = Flow.fromIndex(5);
        Assert.assertNull(flow);
    }

    @Test
    public void locationEqualsTest() {
        Location l1 = new Location(3, 2);
        Location l2 = null;
        Assert.assertFalse(l1.equals(l2));
        l2 = new Location(3, 2);
        Assert.assertEquals(l1, l2);
        Assert.assertEquals(l1.hashCode(), l2.hashCode());
    }

    @Test
    public void oceanConfigEqualsTest() {
        OceanConfig oc1 = new OceanConfig(true, 10, 10, null, 10, null, null);
        OceanConfig oc2 = null;
        Assert.assertFalse(oc1.equals(oc2));
        oc2 = new OceanConfig(true, 10, 10, null, 10, null, null);
        Assert.assertEquals(oc1, oc2);
        Assert.assertEquals(oc1.hashCode(), oc2.hashCode());
    }
}

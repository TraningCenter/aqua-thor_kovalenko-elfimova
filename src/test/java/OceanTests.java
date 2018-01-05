
import tools.ParsersTools;
import com.netcracker.unc.model.Direction;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
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
}

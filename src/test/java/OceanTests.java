
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
        Assert.assertEquals(ocean.getEmptyLocation(Direction.LEFT, location), new Location(1, 19));
        //Tor - false
        ocean.setIsTor(false);
        Assert.assertNull(ocean.getEmptyLocation(Direction.LEFT, location));
        location = new Location(1, 5);
        Assert.assertEquals(ocean.getEmptyLocation(Direction.LEFT, location), new Location(1, 4));
        
        //RIGHT
        Assert.assertEquals(ocean.getEmptyLocation(Direction.RIGHT, location), new Location(1, 6));
        //Tor - false
        location = new Location(1, 19);
        Assert.assertNull(ocean.getEmptyLocation(Direction.RIGHT, location));
        //Tor - true
        ocean.setIsTor(true);
        Assert.assertEquals(ocean.getEmptyLocation(Direction.RIGHT, location), new Location(1, 0));

        //UP
        Assert.assertEquals(ocean.getEmptyLocation(Direction.UP, location), new Location(0, 19));
        //Tor - true
        location = new Location(0, 1);
        Assert.assertEquals(ocean.getEmptyLocation(Direction.UP, location), new Location(1, 1));
        //Tor - false
        ocean.setIsTor(false);
        Assert.assertNull(ocean.getEmptyLocation(Direction.UP, location));

        //DOWN
        Assert.assertEquals(ocean.getEmptyLocation(Direction.DOWN, location), new Location(1, 1));
        //Tor - false
        location = new Location(1, 5);
        Assert.assertNull(ocean.getEmptyLocation(Direction.DOWN, location));
        //Tor - true
        ocean.setIsTor(true);
        Assert.assertEquals(ocean.getEmptyLocation(Direction.DOWN, location), new Location(0, 5));
        
        //not free (1;9)
        location = new Location(0, 9);
        Assert.assertNull(ocean.getEmptyLocation(Direction.DOWN, location));
        location = new Location(1, 8);
        Assert.assertNull(ocean.getEmptyLocation(Direction.RIGHT, location));
        location = new Location(1, 10);
        Assert.assertNull(ocean.getEmptyLocation(Direction.LEFT, location));
        location = new Location(0, 9);
        Assert.assertNull(ocean.getEmptyLocation(Direction.UP, location));
    }
}

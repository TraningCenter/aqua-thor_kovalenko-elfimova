
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class FishesTests {

    Ocean ocean;

    @Before
    public void init() {
        ocean = new Ocean(ParsersTools.getOceanConfig());
    }

    @Test
    public void FishMoveTest() {
        // Tor - true
        // 0 - LEFT flow
        // 1 - RIGHT flow
        // shark - (0;1)
        // smallfish - (0;7), (1;9)
        ocean.getSmallFishes().get(0).move();
        Assert.assertEquals(new Location(0, 6), ocean.getSmallFishes().get(0).getLocation());
        ocean.getSmallFishes().get(1).move();
        Assert.assertEquals(new Location(1, 10), ocean.getSmallFishes().get(1).getLocation());

        ocean.moveFish(ocean.getSmallFishes().get(0), new Location(1, 9));
        Assert.assertEquals(new Location(1, 9), ocean.getSmallFishes().get(0).getLocation());
        //smallfish - (1;9), (1;10)
        ocean.getSmallFishes().get(0).move();
        Assert.assertTrue(!ocean.getSmallFishes().get(0).getLocation().equals(new Location(1, 9)));

        //ocean.getSharks().get(0).move();
        //Assert.assertNotSame(new Location(0, 1), ocean.getSharks().get(0).getLocation());
    }
}

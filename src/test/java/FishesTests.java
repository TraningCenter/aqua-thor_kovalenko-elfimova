
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.interfaces.IFish;
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

    @Test
    public void SearchTargetTest() {
        //tor - false
        ocean.setIsTor(false);
        ocean.moveFish(ocean.getSmallFishes().get(0), new Location(1, 3));
        // shark - (0;1)
        // smallfish - (1;3), (1;9)
        IFish shark = ocean.getSharks().get(0);
        shark.searchTarget();
        Assert.assertEquals(new Location(1, 3), shark.getTarget());
        IFish fish = ocean.getSmallFishes().get(0);
        fish.searchTarget();
        Assert.assertNull(fish.getTarget());
        // shark - (0;1)
        // smallfish - (1;2), (1;9)
        ocean.moveFish(fish, new Location(1, 2));
        fish.searchTarget();
        Assert.assertEquals(shark.getLocation(), fish.getTarget());

        //tor - true
        ocean.setIsTor(true);
        fish.setTarget(null);
        shark.setTarget(null);
        fish.searchTarget();
        Assert.assertEquals(shark.getLocation(), fish.getTarget());
        // shark - (0;1)
        // smallfish - (0;19), (1;9)
        ocean.moveFish(fish, new Location(0, 19));
        fish.searchTarget();
        Assert.assertEquals(shark.getLocation(), fish.getTarget());
        shark.searchTarget();
        Assert.assertEquals(shark.getTarget(), fish.getLocation());
        // shark - (0;1)
        // smallfish - (0;19), (0;2)
        shark.setTarget(null);
        ocean.moveFish(ocean.getSmallFishes().get(1), new Location(0, 2));
        shark.searchTarget();
        Assert.assertEquals(shark.getTarget(), ocean.getSmallFishes().get(1).getLocation());
    }
}

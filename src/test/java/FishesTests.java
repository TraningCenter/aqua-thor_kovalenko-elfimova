
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
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
        IFish fish1 = ocean.getSmallFishes().get(0);
        IFish fish2 = ocean.getSmallFishes().get(1);
        IFish shark = ocean.getSharks().get(0);
        fish1.move();
        Assert.assertEquals(new Location(0, 6), fish1.getLocation());
        ocean.getSmallFishes().get(1).move();
        Assert.assertEquals(new Location(1, 10), fish2.getLocation());

        ocean.moveFish(fish1, new Location(1, 9));
        Assert.assertEquals(new Location(1, 9), fish1.getLocation());
        //smallfish - (1;9), (1;10)
        fish1.move();
        Assert.assertTrue(!fish1.getLocation().equals(new Location(1, 9)));

        shark.move();
        Assert.assertNotSame(new Location(0, 1), ocean.getSharks().get(0).getLocation());

        //with target
        ocean.moveFish(fish1, new Location(0, 3));
        fish1.setTarget(new Location(0, 1));
        fish1.move();
        Assert.assertFalse(fish1.getLocation().equals(new Location(0, 3)));
        Assert.assertFalse(fish1.getLocation().equals(new Location(0, 2)));

        ocean.moveFish(fish1, new Location(1, 3));
        fish1.move();
        Assert.assertFalse(fish1.getLocation().equals(new Location(1, 3)));
        Assert.assertFalse(fish1.getLocation().equals(new Location(1, 2)));

        ocean.moveFish(fish1, new Location(1, 3));
        ocean.moveFish(fish2, new Location(0, 3));
        fish1.move();
        Assert.assertEquals(new Location(1, 4), fish1.getLocation());

        ocean.moveFish(shark, new Location(0, 1));
        shark.setTarget(new Location(0, 4));
        shark.move();
        Assert.assertEquals(new Location(0, 2), shark.getLocation());

        ocean.moveFish(shark, new Location(0, 1));
        shark.setTarget(new Location(1, 4));
        shark.move();
        Assert.assertFalse(shark.getLocation().equals(new Location(0, 0)));

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

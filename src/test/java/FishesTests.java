
import tools.ParsersTools;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.impl.Fish;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
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
    public void moveTest() {
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
    public void searchTargetTest() {
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

    @Test
    public void dieTest() {
        IFish fish = ocean.getSmallFishes().get(0);
        fish.die();
        Assert.assertEquals(1, ocean.getSmallFishes().size());
        Assert.assertNull(ocean.getMatrix()[fish.getLocation().getX()][fish.getLocation().getY()]);

        IFish shark = ocean.getSharks().get(0);
        shark.die();
        Assert.assertEquals(0, ocean.getSharks().size());
        Assert.assertNull(ocean.getMatrix()[shark.getLocation().getX()][shark.getLocation().getY()]);
    }

    @Test
    public void actionTest() {
        //smallfishes
        // shark - (0;1)
        // smallfish - (0;7), (0;9), (0,8), (1,8)
        ocean.moveFish(ocean.getSmallFishes().get(1), new Location(0, 9));
        ocean.addFish(new SmallFish(new Location(0, 8), 2, 1, 2));
        ocean.addFish(new SmallFish(new Location(1, 8), 2, 1, 2));
        IFish fish3 = ocean.getSmallFishes().get(2);
        IFish fish4 = ocean.getSmallFishes().get(3);
        fish3.action();
        Assert.assertEquals(1, fish3.getAge());
        Assert.assertEquals(new Location(0, 8), fish3.getLocation());
        Assert.assertNull(fish3.getTarget());
        fish3.action();
        Assert.assertNotSame(fish3, ocean.getFishByLocation(new Location(0, 8)));
        Assert.assertEquals(4, ocean.getSmallFishes().size());
        fish4.action();
        Assert.assertEquals(1, fish4.getAge());
        Assert.assertEquals(4, ocean.getSmallFishes().size());
        fish4.action();
        Assert.assertEquals(5, ocean.getSmallFishes().size());
        fish4.action();
        Assert.assertEquals(4, ocean.getSmallFishes().size());

        //sharks
        // shark - (0;1), (0,11)
        // smallfish - (0;7), (0;9), (0,8), (1,8)
        ocean.addFish(new Shark(new Location(0, 11), 3, 1, 5, 2));
        Shark shark = (Shark) ocean.getSharks().get(1);
        shark.action();
        Assert.assertEquals(new Location(0, 10), shark.getLocation());
        Assert.assertEquals(new Location(0, 9), shark.getTarget());
        Assert.assertEquals(1, shark.getAge());
        Assert.assertEquals(1, shark.getHungerCounter());
        Assert.assertEquals(2, ocean.getSharks().size());
        shark.action();
        Assert.assertEquals(new Location(0, 9), shark.getLocation());
        Assert.assertEquals(new Location(0, 9), shark.getTarget());
        Assert.assertEquals(2, shark.getAge());
        Assert.assertEquals(0, shark.getHungerCounter());
        Assert.assertEquals(3, ocean.getSmallFishes().size());
        shark.action();
        Assert.assertEquals(3, shark.getAge());
        Assert.assertEquals(0, shark.getHungerCounter());
        Assert.assertEquals(2, ocean.getSmallFishes().size());
        Assert.assertEquals(3, ocean.getSharks().size());
        shark.action();
        Assert.assertEquals(2, ocean.getSharks().size());

        ocean.addFish(new Shark(new Location(1, 15), 3, 1, 2, 2));
        shark = (Shark) ocean.getSharks().get(2);
        shark.action();
        shark.action();
        Assert.assertEquals(2, shark.getAge());
        Assert.assertEquals(2, shark.getHungerCounter());
        shark.action();
        Assert.assertEquals(2, ocean.getSharks().size());
    }

    @Test
    public void fishEqualsTest() {
        Fish sh1 = new Shark(new Location(3, 4), 10, 1, 4, 5);
        Shark sh2 = new Shark(new Location(3, 4), 10, 1, 4, 5);
        Assert.assertEquals(sh1, sh2);
        Assert.assertEquals(sh1.hashCode(), sh2.hashCode());
        sh2.setHungerTime(10);
        Assert.assertNotSame(sh1, sh2);
        IFish fish = new SmallFish(new Location(3, 4), 10, 1, 4);
        Assert.assertNotSame(sh1, fish);
        sh2 = new Shark(new Location(3, 4), 10, 1, 5, 5);
        Assert.assertNotSame(sh1, sh2);
        sh2.setProgenyPeriod(2);
        Assert.assertNotSame(sh1, sh2);
    }
}

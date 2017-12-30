
import com.netcracker.unc.creator.FishCreator;
import com.netcracker.unc.model.FishType;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreatorTests {

    Ocean ocean;

    @Before
    public void init() {
        ocean = new Ocean(ParsersTools.getOceanConfig());
    }

    @Test
    public void createSharkTest() {
        Shark shark = (Shark) FishCreator.createFish(FishType.SHARK);
        Assert.assertTrue(shark.getLifetime() >= 5 && shark.getLifetime() <= 100);
        Assert.assertTrue(shark.getProgenyPeriod() == 1);
        Assert.assertTrue(shark.getSearchRadius() >= 1 && shark.getSearchRadius() <= 10);
        Assert.assertTrue(shark.getHungerTime() >= 1 && shark.getHungerTime() <= 99);
        Assert.assertNotSame(new Location(0, 1), shark.getLocation());
        Assert.assertNotSame(new Location(0, 7), shark.getLocation());
        Assert.assertNotSame(new Location(1, 9), shark.getLocation());

        shark = (Shark) FishCreator.createSuccessorFish(ocean.getSharks().get(0), new Location(0, 2));
        Assert.assertEquals(40, shark.getLifetime());
        Assert.assertEquals(1, shark.getProgenyPeriod());
        Assert.assertEquals(4, shark.getSearchRadius());
        Assert.assertEquals(20, shark.getHungerTime());
        Assert.assertEquals(new Location(0, 2), shark.getLocation());
    }

    @Test
    public void createSmallFishTest() {
        SmallFish fish = (SmallFish) FishCreator.createFish(FishType.SMALL);
        Assert.assertTrue(fish.getLifetime() >= 5 && fish.getLifetime() <= 100);
        Assert.assertTrue(fish.getProgenyPeriod() >= 1 && fish.getProgenyPeriod() <= 20);
        Assert.assertTrue(fish.getSearchRadius() >= 1 && fish.getSearchRadius() <= 5);
        Assert.assertNotSame(new Location(0, 1), fish.getLocation());
        Assert.assertNotSame(new Location(0, 7), fish.getLocation());
        Assert.assertNotSame(new Location(1, 9), fish.getLocation());

        fish = (SmallFish) FishCreator.createSuccessorFish(ocean.getSmallFishes().get(0), new Location(0, 8));
        Assert.assertEquals(30, fish.getLifetime());
        Assert.assertEquals(3, fish.getProgenyPeriod());
        Assert.assertEquals(2, fish.getSearchRadius());
        Assert.assertEquals(new Location(0, 8), fish.getLocation());
    }

    @Test
    public void createOcean() {

    }
}

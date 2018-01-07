package com.netcracker.unc.model;

import static com.netcracker.unc.metric.MetricsWriter.writeMetric;
import static com.netcracker.unc.model.FishType.SHARK;
import static com.netcracker.unc.model.FishType.SMALL;

import com.netcracker.unc.model.interfaces.IFish;
import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Ocean class with matrix of cells, flows and fishes
 */
public class Ocean {

    private static Ocean ocean;
    private IFish[][] matrix;
    private int height;
    private int width;
    private boolean isTor;
    private List<Flow> flowList;
    private int changeFlow;
    private List<IFish> sharks;
    private List<IFish> smallFishes;
    private int step;
    private int paramWriteMetric=10;

    /**
     * ocean constructor based on ocean configuration
     *
     * @param oceanConfig ocean configuration
     */
    public Ocean(OceanConfig oceanConfig) {
        matrix = new IFish[oceanConfig.getHeight()][oceanConfig.getWidth()];
        this.height = oceanConfig.getHeight();
        this.width = oceanConfig.getWidth();
        this.isTor = oceanConfig.isTor();
        this.flowList = oceanConfig.getFlowList();
        if (flowList.size() != height) {
            for (int i = flowList.size(); i < height; i++) {
                flowList.add(Flow.NONE);
            }
        }
        this.changeFlow = oceanConfig.getChangeFlow();
        this.sharks = oceanConfig.getSharks();
        fillMatrix(sharks);
        this.smallFishes = oceanConfig.getSmallFishes();
        fillMatrix(smallFishes);
        ocean = this;
    }

    /**
     * one step of all fishes
     */
    public void oneStep() {
        if (step % changeFlow == 0 && step != 0) {
            changeFlow();
        }
        List<IFish> sharkList = new ArrayList(ocean.getSharks());
        sharkList.forEach(IFish::action);
        List<IFish> smallFishList = new ArrayList(ocean.getSmallFishes());
        smallFishList.forEach(IFish::action);
        if (step % paramWriteMetric == 0 && step != 0) {
           writeMetric(ocean);
       }
        step++;
    }

    /**
     * random flows change
     */
    public void changeFlow() {
        Random rnd = new Random();
        int num;
        for (int i = 0; i < flowList.size(); i++) {
            num = rnd.nextInt(Flow.size());
            flowList.set(i, Flow.fromIndex(num));
        }
    }

    /**
     * move fish in matrix
     *
     * @param fish fish for movement
     * @param newLocation new location
     */
    public void moveFish(IFish fish, Location newLocation) {
        Location oldLocation = fish.getLocation();
        matrix[newLocation.getX()][newLocation.getY()] = fish;
        matrix[oldLocation.getX()][oldLocation.getY()] = null;
        fish.setLocation(newLocation);
    }

    /**
     * get next location in matrix by direction
     *
     * @param direction direction
     * @param location current location
     * @return next location. If next cell is busy returns null
     */
    public Location getNextLocation(Direction direction, @NotNull Location location) {
        Location newLocation;
        int x = location.getX();
        int y = location.getY();
        switch (direction) {
            case LEFT:
                if (y == 0) {
                    if (isTor) {
                        y = width - 1;
                    } else {
                        return null;
                    }
                } else {
                    y -= 1;
                }
                break;
            case RIGHT:
                if (y == width - 1) {
                    if (isTor) {
                        y = 0;
                    } else {
                        return null;
                    }
                } else {
                    y += 1;
                }
                break;
            case UP:
                if (x == 0) {
                    if (isTor) {
                        x = height - 1;
                    } else {
                        return null;
                    }
                } else {
                    x -= 1;
                }
                break;
            case DOWN:
                if (x == height - 1) {
                    if (isTor) {
                        x = 0;
                    } else {
                        return null;
                    }
                } else {
                    x += 1;
                }
                break;
        }
        newLocation = new Location(x, y);
        if (!isEmptyLocation(newLocation)) {
            try {
                if (ocean.getFishByLocation(location).getType() == SHARK && ocean.getFishByLocation(newLocation).getType() == SMALL) {
                    return newLocation;
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        } else {
            return newLocation;
        }
    }

    /**
     * add fish to matrix
     *
     * @param fish fish
     */
    private void addFishInMatrix(IFish fish) {
        Location location = fish.getLocation();
        matrix[location.getX()][location.getY()] = fish;
    }

    /**
     * add fish to ocean
     *
     * @param fish fish
     */
    public void addFish(IFish fish) {
        addFishInMatrix(fish);
        if (fish.getType() == FishType.SHARK) {
            sharks.add(fish);
        } else if (fish.getType() == FishType.SMALL) {
            smallFishes.add(fish);
        }
    }

    /**
     * fill matrix with fishes
     *
     * @param fishes list of fishes
     */
    private void fillMatrix(List<IFish> fishes) {
        fishes.forEach(this::addFishInMatrix);
    }

    /**
     * get fish by matrix location
     *
     * @param location location
     * @return fish
     */
    public IFish getFishByLocation(Location location) {
        return matrix[location.getX()][location.getY()];
    }

    /**
     * check location to emptiness
     *
     * @param location location
     * @return true - if location is empty
     */
    public boolean isEmptyLocation(Location location) {
        return matrix[location.getX()][location.getY()] == null;
    }

    /**
     * get maximum population of all fishes
     *
     * @return max population
     */
    public int getMaxPopulation() {
        return height * width / 3;
    }

    /**
     * get current population of all fishes in the ocean
     *
     * @return current population
     */
    public int getAllPopulation() {
        return getSharks().size() + getSmallFishes().size();
    }

    public static Ocean getInstanse() {
        return ocean;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isTor() {
        return isTor;
    }

    public List<Flow> getFlowList() {
        return flowList;
    }

    public int getChangeFlow() {
        return changeFlow;
    }

    public List<IFish> getSharks() {
        return sharks;
    }

    public List<IFish> getSmallFishes() {
        return smallFishes;
    }

    public IFish[][] getMatrix() {
        return matrix;
    }

    public int getStep() {
        return step;
    }

    public void setIsTor(boolean isTor) {
        this.isTor = isTor;
    }
}

package com.netcracker.unc.model;

import com.netcracker.unc.model.interfaces.IFish;

import java.util.List;

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

    public Ocean(int height, int width, boolean isTor, List<Flow> flowList, int changeFlow, List<IFish> sharks, List<IFish> smallFishes) {
        matrix = new IFish[height][width];
        this.height = height;
        this.width = width;
        this.isTor = isTor;
        this.flowList = flowList;
        this.changeFlow = changeFlow;
        this.sharks = sharks;
        fillMatrix(sharks);
        this.smallFishes = smallFishes;
        fillMatrix(smallFishes);
        ocean = this;
    }

    public Ocean(OceanConfig oceanConfig) {
        matrix = new IFish[oceanConfig.getHeight()][oceanConfig.getWidth()];
        this.height = oceanConfig.getHeight();
        this.width = oceanConfig.getWidth();
        this.isTor = oceanConfig.isTor();
        this.flowList = oceanConfig.getFlowList();
        this.changeFlow = oceanConfig.getChangeFlow();
        this.sharks = oceanConfig.getSharks();
        fillMatrix(sharks);
        this.smallFishes = oceanConfig.getSmallFishes();
        fillMatrix(smallFishes);
        ocean = this;
    }

    private void addFishInMatrix(IFish fish) {
        Location location = fish.getLocation();
        matrix[location.getX()][location.getY()] = fish;
    }

    private void fillMatrix(List<IFish> fishes) {
        fishes.forEach(this::addFishInMatrix);
    }

    public IFish getFishByLocation(Location location) {
        return matrix[location.getX()][location.getY()];
    }

    public void moveFish(IFish fish, Location newLocation) {
        Location oldLocation = fish.getLocation();
        matrix[newLocation.getX()][newLocation.getY()] = fish;
        matrix[oldLocation.getX()][oldLocation.getY()] = null;
        fish.setLocation(newLocation);
    }

    public boolean isEmptyLocation(Location location) {
        return matrix[location.getX()][location.getY()] == null;
    }

    public Location getEmptyLocation(Direction direction, Location location) {
        Location newLocation = null;
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
        return (isEmptyLocation(newLocation)) ? newLocation : null;
    }

    public void oneStep() {

    }

    public void changeFlow() {

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

    public static void setOcean(Ocean ocean) {
        Ocean.ocean = ocean;
    }

    public void setMatrix(IFish[][] matrix) {
        this.matrix = matrix;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setIsTor(boolean isTor) {
        this.isTor = isTor;
    }

    public void setFlowList(List<Flow> flowList) {
        this.flowList = flowList;
    }

    public void setChangeFlow(int changeFlow) {
        this.changeFlow = changeFlow;
    }

    public void setSharks(List<IFish> sharks) {
        this.sharks = sharks;
    }

    public void setSmallFishes(List<IFish> smallFishes) {
        this.smallFishes = smallFishes;
    }

}

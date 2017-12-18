package com.netcracker.unc.model;


import com.netcracker.unc.model.interfaces.IFish;

import java.util.List;

public class Ocean {

    private static volatile Ocean ocean;
    private int height;
    private int weight;
    private boolean isTor;
    private List<Flow> flowList;
    private int changeFlow;
    private List<IFish> sharks;
    private List<IFish> smallFishes;

    public Ocean(int height, int weight, boolean isTor, List<Flow> flowList, int changeFlow, List<IFish> sharks, List<IFish> smallFishes) {
        this.height = height;
        this.weight = weight;
        this.isTor = isTor;
        this.flowList = flowList;
        this.changeFlow = changeFlow;
        this.sharks = sharks;
        this.smallFishes = smallFishes;
    }

    public void oneStep() {

    }

    public void changeFlow() {

    }

    public static Ocean getInstanse() {
        return ocean;
    }

    public static Ocean getOcean() {
        return ocean;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
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
}

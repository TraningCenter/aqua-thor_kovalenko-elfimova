package com.netcracker.unc.model;

import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ocean")
@XmlAccessorType(XmlAccessType.FIELD)
public class OceanConfig {
    
    @XmlElement(name = "tor")
    private boolean isTor;

    @XmlElement
    private int height;

    @XmlElement
    private int weight;

    @XmlElementWrapper(name = "flows")
    @XmlElement(name = "flow")
    private List<Flow> flowList;

    @XmlElement
    private int changeFlow;

    @XmlElementWrapper(name = "sharks")
    @XmlElement(name = "shark", type = Shark.class)
    private List<IFish> sharks;

    @XmlElementWrapper(name = "smallFishes")
    @XmlElement(name = "fish", type = SmallFish.class)
    private List<IFish> smallFishes;

    public OceanConfig(){
        
    }
    
    public OceanConfig(boolean isTor, int height, int weight, List<Flow> flowList, int changeFlow, List<IFish> sharks, List<IFish> smallFishes) {
        this.isTor = isTor;
        this.height = height;
        this.weight = weight;
        this.flowList = flowList;
        this.changeFlow = changeFlow;
        this.sharks = sharks;
        this.smallFishes = smallFishes;
    }

    
    
    public boolean isIsTor() {
        return isTor;
    }

    public void setIsTor(boolean isTor) {
        this.isTor = isTor;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Flow> getFlowList() {
        return flowList;
    }

    public void setFlowList(List<Flow> flowList) {
        this.flowList = flowList;
    }

    public int getChangeFlow() {
        return changeFlow;
    }

    public void setChangeFlow(int changeFlow) {
        this.changeFlow = changeFlow;
    }

    public List<IFish> getSharks() {
        return sharks;
    }

    public void setSharks(List<IFish> sharks) {
        this.sharks = sharks;
    }

    public List<IFish> getSmallFishes() {
        return smallFishes;
    }

    public void setSmallFishes(List<IFish> smallFishes) {
        this.smallFishes = smallFishes;
    }

}

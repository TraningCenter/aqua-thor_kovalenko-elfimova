package com.netcracker.unc.model;

import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Ocean configuration for ocean creation
 */
@XmlRootElement(name = "ocean")
@XmlAccessorType(XmlAccessType.FIELD)
public class OceanConfig {

    @XmlElement(name = "tor")
    private boolean isTor;
    @XmlElement
    private int height;
    @XmlElement
    private int width;
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

    public OceanConfig() {
    }

    /**
     * ocean configuration constructor
     *
     * @param isTor closed system
     * @param height count of rows
     * @param width count of columns
     * @param flowList flow for each row
     * @param changeFlow step to change flows
     * @param sharks list of sharks
     * @param smallFishes list of smallFishes
     */
    public OceanConfig(boolean isTor, int height, int width, List<Flow> flowList, int changeFlow, List<IFish> sharks, List<IFish> smallFishes) {
        this.isTor = isTor;
        this.height = height;
        this.width = width;
        this.flowList = flowList;
        this.changeFlow = changeFlow;
        this.sharks = sharks;
        this.smallFishes = smallFishes;
    }

    public boolean isTor() {
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.isTor ? 1 : 0);
        hash = 41 * hash + this.height;
        hash = 41 * hash + this.width;
        hash = 41 * hash + Objects.hashCode(this.flowList);
        hash = 41 * hash + this.changeFlow;
        hash = 41 * hash + Objects.hashCode(this.sharks);
        hash = 41 * hash + Objects.hashCode(this.smallFishes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OceanConfig other = (OceanConfig) obj;
        if (this.isTor != other.isTor) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if (this.width != other.width) {
            return false;
        }
        if (this.changeFlow != other.changeFlow) {
            return false;
        }
        if (!Objects.equals(this.flowList, other.flowList)) {
            return false;
        }
        if (!Objects.equals(this.sharks, other.sharks)) {
            return false;
        }
        return Objects.equals(this.smallFishes, other.smallFishes);
    }
}

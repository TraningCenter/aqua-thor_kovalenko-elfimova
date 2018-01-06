package com.netcracker.unc.parsers;

import com.netcracker.unc.model.Flow;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.model.impl.Fish;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import com.netcracker.unc.utils.CommonUtils;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StAXParserXML implements IXMLParser {

    @Override
    public OceanConfig read(InputStream config) {
        OceanConfig oceanConfig = new OceanConfig();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        boolean torFlag = false, heightFlag = false, widthFlag = false, changeFlowFlag = false;
        String name;
        try {
            XMLStreamReader reader = factory.createXMLStreamReader(config);
            int event = reader.getEventType();
            while (true) {
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        name = reader.getLocalName();
                        if (name.equals("ocean")) {
                        } else if (name.equals("tor")) {
                            torFlag = true;
                        } else if (name.equals("height")) {
                            heightFlag = true;
                        } else if (name.equals("width")) {
                            widthFlag = true;
                        } else if (name.equals("changeFlow")) {
                            changeFlowFlag = true;
                        } else if (reader.getLocalName().equals("flows")) {
                            oceanConfig.setFlowList(getFlows(reader));
                            if (oceanConfig.getFlowList() == null) {
                                return null;
                            }
                        } else if (reader.getLocalName().equals("sharks")) {
                            oceanConfig.setSharks(getFishes(reader, true));
                            if (oceanConfig.getSharks() == null) {
                                return null;
                            }
                        } else if (reader.getLocalName().equals("smallFishes")) {
                            oceanConfig.setSmallFishes(getFishes(reader, false));
                            if (oceanConfig.getSmallFishes() == null) {
                                return null;
                            }
                        } else {
                            return null;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (torFlag) {
                            oceanConfig.setIsTor(reader.getText().equals("true"));
                            torFlag = false;
                        } else if (heightFlag) {
                            oceanConfig.setHeight(Integer.parseInt(reader.getText()));
                            heightFlag = false;
                        } else if (widthFlag) {
                            oceanConfig.setWidth(Integer.parseInt(reader.getText()));
                            widthFlag = false;
                        } else if (changeFlowFlag) {
                            oceanConfig.setChangeFlow(Integer.parseInt(reader.getText()));
                            changeFlowFlag = false;
                        } else if (changeFlowFlag) {
                            oceanConfig.setChangeFlow(Integer.parseInt(reader.getText()));
                            changeFlowFlag = false;
                        }
                        break;
                }
                if (!reader.hasNext()) {
                    break;
                }
                event = reader.next();
            }
        } catch (XMLStreamException ex) {
            return null;
        }
        if (CommonUtils.checkEmpty(oceanConfig.getSharks()) && CommonUtils.checkEmpty(oceanConfig.getSmallFishes())) {
            return null;
        }
        return oceanConfig;
    }

    private List<Flow> getFlows(XMLStreamReader reader) throws XMLStreamException, IllegalArgumentException {
        List<Flow> flows = new ArrayList<>();
        boolean flowFlag = false;
        int event = reader.getEventType();
        while (reader.hasNext()) {
            event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if (reader.getLocalName().equals("flow")) {
                        flowFlag = true;
                    } else {
                        return null;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (flowFlag) {
                        flows.add(Flow.fromValue(reader.getText()));
                        flowFlag = false;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equals("flows")) {
                        return flows;
                    }
                    break;
            }
        }
        return null;
    }

    private List<IFish> getFishes(XMLStreamReader reader, boolean sharks) throws XMLStreamException {
        List<IFish> fishes = new ArrayList<>();
        boolean lifetimeFlag = false, progenyPeriodFlag = false, searchRadiusFlag = false, hungerTimeFlag = false;
        Fish fish = new SmallFish();
        int hungerTime = 0;
        String name;

        int event = reader.getEventType();
        while (reader.hasNext()) {
            event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals("shark") || name.equals("fish")) {
                    } else if (name.equals("lifetime")) {
                        lifetimeFlag = true;
                    } else if (name.equals("progenyPeriod")) {
                        progenyPeriodFlag = true;
                    } else if (name.equals("searchRadius")) {
                        searchRadiusFlag = true;
                    } else if (sharks && name.equals("hungerTime")) {
                        hungerTimeFlag = true;
                    } else if (reader.getLocalName().equals("location")) {
                        fish.setLocation(getLocation(reader));
                        if (fish.getLocation() == null) {
                            return null;
                        }
                    } else {
                        return null;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (lifetimeFlag) {
                        fish.setLifetime(Integer.parseInt(reader.getText()));
                        lifetimeFlag = false;
                    } else if (progenyPeriodFlag) {
                        fish.setProgenyPeriod(Integer.parseInt(reader.getText()));
                        progenyPeriodFlag = false;
                    } else if (searchRadiusFlag) {
                        fish.setSearchRadius(Integer.parseInt(reader.getText()));
                        searchRadiusFlag = false;
                    } else if (hungerTimeFlag) {
                        hungerTime = Integer.parseInt(reader.getText());
                        hungerTimeFlag = false;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (sharks && name.equals("shark")) {
                        fish = new Shark(fish, hungerTime);
                        fishes.add(fish);
                        fish = new SmallFish();
                    } else if (name.equals("fish")) {
                        fishes.add(fish);
                        fish = new SmallFish();
                    } else if (name.equals("sharks") || name.equals("smallFishes")) {
                        return fishes;
                    }
                    break;
            }
        }
        return null;
    }

    private Location getLocation(XMLStreamReader reader) throws XMLStreamException {
        Location location = new Location();
        boolean xFlag = false, yFlag = false;
        int event = reader.getEventType();
        while (reader.hasNext()) {
            event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "x":
                            xFlag = true;
                            break;
                        case "y":
                            yFlag = true;
                            break;
                        default:
                            return null;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (xFlag) {
                        location.setX(Integer.parseInt(reader.getText()));
                        xFlag = false;
                    } else if (yFlag) {
                        location.setY(Integer.parseInt(reader.getText()));
                        yFlag = false;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equals("location")) {
                        return location;
                    }
                    break;
            }
        }
        return null;
    }

    @Override
    public void write(OceanConfig oceanConfig, OutputStream outputStream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

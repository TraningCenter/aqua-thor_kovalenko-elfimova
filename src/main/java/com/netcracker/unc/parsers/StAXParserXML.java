package com.netcracker.unc.parsers;

import com.netcracker.unc.metric.MetricsWriter;
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
import javax.xml.stream.*;

/**
 * StAX parser
 */
public class StAXParserXML implements IXMLParser {

    /**
     * read ocean configuration from input stream
     *
     * @param config input stream
     * @return ocean configuration
     */
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

    /**
     * parse flows
     *
     * @param reader XMLStreamReader
     * @return list of flows
     * @throws XMLStreamException
     * @throws IllegalArgumentException
     */
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

    /**
     * parse fishes
     *
     * @param reader XMLStreamReader
     * @param isSharks
     * @return list of fishes (Sharks/SmallFishes)
     * @throws XMLStreamException
     */
    private List<IFish> getFishes(XMLStreamReader reader, boolean isSharks) throws XMLStreamException {
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
                    } else if (isSharks && name.equals("hungerTime")) {
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
                    if (isSharks && name.equals("shark")) {
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

    /**
     * parse location
     *
     * @param reader XMLStreamReader
     * @return location
     * @throws XMLStreamException
     */
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

    /**
     * write snapshots metrics at a certain step in the Output Stream
     *
     * @param outputStream Output Stream
     * @param metricsWriter MetricsWriter
     *
     */

    @Override
    public void write(MetricsWriter metricsWriter, OutputStream outputStream) {
        try {
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter staxWriter = factory.createXMLStreamWriter(outputStream);
            staxWriter.writeStartDocument();
            staxWriter.writeStartElement("snapshots");
            for (int i = 0; i < metricsWriter.snapshots.size(); i++) {
                staxWriter.writeStartElement("snapshot");
                staxWriter.writeStartElement("step");
                char[] tempStep = String.valueOf(metricsWriter.snapshots.get(i).getStep()).toCharArray();
                staxWriter.writeCharacters(tempStep, 0, tempStep.length);
                staxWriter.writeEndElement();
                staxWriter.writeStartElement("metrics");
                for (int j = 0; j < metricsWriter.snapshots.get(i).getMetricList().size(); j++) {
                    staxWriter.writeStartElement("metric");
                    staxWriter.writeStartElement("name");
                    char[] tempName = String.valueOf(metricsWriter.snapshots.get(i).getMetricList().get(j).getName()).toCharArray();
                    staxWriter.writeCharacters(tempName, 0, tempName.length);
                    staxWriter.writeEndElement();
                    staxWriter.writeStartElement("value");
                    char[] tempValue = String.valueOf(metricsWriter.snapshots.get(i).getMetricList().get(j).getValue()).toCharArray();
                    staxWriter.writeCharacters(tempValue, 0, tempValue.length);
                    staxWriter.writeEndElement();
                    staxWriter.writeEndElement();
                }
                staxWriter.writeEndElement();
                staxWriter.writeEndElement();
            }
            staxWriter.writeEndElement();
            staxWriter.writeEndDocument();
            staxWriter.flush();
            staxWriter.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}



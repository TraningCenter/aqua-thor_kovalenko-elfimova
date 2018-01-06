package com.netcracker.unc.parsers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import com.netcracker.unc.model.*;
import com.netcracker.unc.model.impl.Fish;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import java.io.IOException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMParserXML implements IXMLParser {

    private OceanConfig oceanConfig;

    @Override
    public OceanConfig read(InputStream config) {
        try {
            domParse(createDocumentBuilder().parse(config));
            return oceanConfig;
        } catch (IOException | NullPointerException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    private DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        return dbFactory.newDocumentBuilder();
    }

    private void domParse(Document document) throws NullPointerException {
        oceanConfig = new OceanConfig();
        Element documentElement = document.getDocumentElement();

        parseOceanType(getFirstElementByTagName(documentElement, "tor"));
        parseOceanHeight(getFirstElementByTagName(documentElement, "height"));
        parseOceanWeight(getFirstElementByTagName(documentElement, "width"));
        parseOceanFlowList(getFirstElementByTagName(documentElement, "flows"));
        parseOceanChangeFlow(getFirstElementByTagName(documentElement, "changeFlow"));
        parseFish(getFirstElementByTagName(documentElement, "sharks"));
        parseFish(getFirstElementByTagName(documentElement, "smallFishes"));
    }

    private void parseFish(Element sameFishes) throws NullPointerException {
        int hungerTime = 0;
        List<IFish> smallfishes = new ArrayList<>();
        List<IFish> sharkfishes = new ArrayList<>();
        String name = null;
        if (sameFishes.getTagName().equals("sharks")) {
            name = "shark";
        } else if (sameFishes.getTagName().equals("smallFishes")) {
            name = "fish";
        }
        NodeList fishNodeList = sameFishes.getElementsByTagName(name);
        for (int i = 0; i < fishNodeList.getLength(); i++) {
            if (fishNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element fishElement = (Element) fishNodeList.item(i);
                Fish fish = new SmallFish();
                NodeList childNodes = fishElement.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNodes.item(j);
                        switch (childElement.getNodeName()) {
                            case "lifetime":
                                fish.setLifetime(parseInteger(childElement));
                                break;
                            case "progenyPeriod":
                                fish.setProgenyPeriod(parseInteger(childElement));
                                break;
                            case "searchRadius":
                                fish.setSearchRadius(parseInteger(childElement));
                                break;
                            case "location":
                                fish.setLocation(parseLocation(childElement));
                                break;
                            case "hungerTime":
                                hungerTime = parseInteger(childElement);
                                break;
                        }
                    }
                }
                if (name.equals("shark")) {
                    fish = new Shark(fish, hungerTime);
                    sharkfishes.add(fish);
                    oceanConfig.setSharks(sharkfishes);
                } else if (name.equals("fish")) {
                    smallfishes.add(fish);
                    oceanConfig.setSmallFishes(smallfishes);
                }
            }
        }
    }

    private void parseOceanChangeFlow(Element changeFlow) {
        oceanConfig.setChangeFlow(parseInteger(changeFlow));
    }

    private Element getFirstElementByTagName(Element elementToSearch, String tagName) {
        return (Element) elementToSearch.getElementsByTagName(tagName).item(0);
    }

    private void parseOceanHeight(Element oceanHeightElement) {
        oceanConfig.setHeight(parseInteger(oceanHeightElement));
    }

    private void parseOceanWeight(Element oceanWeightElement) {
        oceanConfig.setWidth(parseInteger(oceanWeightElement));
    }

    private void parseOceanType(Node oceanTypeNode) {
        switch (oceanTypeNode.getTextContent()) {
            case "true":
                oceanConfig.setIsTor(true);
                break;
            case "false":
                oceanConfig.setIsTor(false);
                break;
        }
    }

    private void parseOceanFlowList(Element flowElement) {
        List<Flow> flows = new ArrayList<>();
        NodeList oceanFlowList = flowElement.getElementsByTagName("flow");
        for (int i = 0; i < oceanFlowList.getLength(); i++) {
            if (oceanFlowList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element oceanFlowElement = (Element) oceanFlowList.item(i);
                flows.add(Flow.fromValue(oceanFlowElement.getTextContent()));
            }
        }
        oceanConfig.setFlowList(flows);
    }

    private Location parseLocation(Element locationElement) {
        Location location = new Location();
        NodeList locationChildNodes = locationElement.getChildNodes();
        for (int j = 0; j < locationChildNodes.getLength(); j++) {
            if (locationChildNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) locationChildNodes.item(j);
                switch (childElement.getNodeName()) {
                    case "x":
                        location.setX(parseInteger(childElement));
                        break;
                    case "y":
                        location.setY(parseInteger(childElement));
                        break;
                }
            }
        }
        return location;
    }

    private Integer parseInteger(Node integerNode) {
        return Integer.parseInt(integerNode.getTextContent());
    }

    @Override
    public void write(OceanConfig oceanConfig, OutputStream outputStream) {
        try {
            this.oceanConfig = oceanConfig;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            document.setXmlStandalone(true);
            writeElements(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(outputStream));
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void writeElements(Document document) {
        Element oceanMonitoring = document.createElement("oceanMonitoring");
        document.appendChild(oceanMonitoring);
        Element sharksCount = document.createElement("sharksCount");
        sharksCount.appendChild(document.createTextNode(String.valueOf(oceanConfig.getSharks().size())));
        Element smallFishesCount = document.createElement("smallFishesCount");
        smallFishesCount.appendChild(document.createTextNode(String.valueOf(oceanConfig.getSmallFishes().size())));
        oceanMonitoring.appendChild(sharksCount);
        oceanMonitoring.appendChild(smallFishesCount);
    }
}

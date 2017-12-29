package com.netcracker.unc.parsers;

import com.netcracker.unc.model.Flow;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.model.impl.Fish;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 24.12.2017.
 */

public class SAXParserXML extends DefaultHandler implements IXMLParser  {

    private int hungerTime = 0;
    private OceanConfig oceanConfig;
    private List<Flow> flows;
    private Fish fish;
    private List<IFish> smallfishes;
    private List<IFish> sharkfishes;
    private Location location;
    private String currentElement;

    private TransformerFactory factory;
    private SAXTransformerFactory saxTransFactory;
    private TransformerHandler transHandler;

    public OceanConfig read(InputStream config) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SAXParserXML handler = new SAXParserXML();
            saxParser.parse(config,handler);
            OceanConfig oceanConfig = handler.getOceanConfig();
            return oceanConfig;
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    OceanConfig getOceanConfig(){
        return oceanConfig;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        switch (currentElement) {
            case "ocean":
                oceanConfig = new OceanConfig();
                break;
            case "shark":
                fish= new Shark();
                break;
            case "fish":
                fish= new SmallFish();
                break;
            case "flows":
                flows = new ArrayList<>();
                break;
            case "location":
                location = new Location();
                break;
            case "sharks":
                sharkfishes = new ArrayList<>();
                break;
            case "smallFishes":
                smallfishes = new ArrayList<>();
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(ch, start, length);
        if (text.contains("<") || currentElement == null) {
            return;
        }
        switch (currentElement) {
            case "tor":
                switch (text) {
                    case "true":
                        oceanConfig.setIsTor(true);
                        break;
                    case "false":
                        oceanConfig.setIsTor(false);
                        break;
                    default:
                }
                break;
            case "height":
                oceanConfig.setHeight(Integer.valueOf(text));
                break;
            case "width":
                oceanConfig.setWidth(Integer.valueOf(text));
                break;
            case "flow":
                flows.add(Flow.fromValue(text));
                break;
            case "changeFlow":
                oceanConfig.setChangeFlow(Integer.valueOf(text));
                break;
            case "lifetime":
                fish.setLifetime(Integer.valueOf(text));
                break;
            case "progenyPeriod":
                fish.setProgenyPeriod(Integer.valueOf(text));
                break;
            case "searchRadius":
                fish.setSearchRadius(Integer.valueOf(text));
                break;
            case "hungerTime":
                hungerTime = Integer.valueOf(text);
                break;
            case "x":
                location.setX(Integer.valueOf(text));
                break;
            case "y":
                location.setY(Integer.valueOf(text));
                break;
            default:
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "flows":
                oceanConfig.setFlowList(flows);
                break;
            case "location":
                fish.setLocation(location);
                break;
            case "shark":
                fish = new Shark(fish, hungerTime);
                sharkfishes.add(fish);
                break;
            case "fish":
                smallfishes.add(fish);
                break;
            case "sharks":
                oceanConfig.setSharks(sharkfishes);
                break;
            case "smallFishes":
                oceanConfig.setSmallFishes(smallfishes);
                break;
            default:
        }
        currentElement=null;
    }

    public void write(OceanConfig oceanConfig, OutputStream outputStream) {
        try {
            this.oceanConfig=oceanConfig;
            factory = TransformerFactory.newInstance().newInstance();
            saxTransFactory=(SAXTransformerFactory) factory;
            transHandler= saxTransFactory.newTransformerHandler();
            transHandler.setResult(new StreamResult(outputStream));
            writeData();
        } catch (TransformerConfigurationException| SAXException e) {
            e.printStackTrace();
        }
    }

    private void writeData() throws SAXException {
        transHandler.startDocument();
        transHandler.startElement("", "", "oceanMonitoring", null);
        writeSharKsCount();
        writeSmallFishesCount();
        transHandler.endElement("", "", "oceanMonitoring");
        transHandler.endDocument();
    }

    private void writeSharKsCount() throws SAXException {
        transHandler.startElement("", "", "sharksCount", null);
        char[] tempSharcs = String.valueOf(oceanConfig.getSharks().size()).toCharArray();
        transHandler.characters(tempSharcs, 0, tempSharcs.length);
        transHandler.endElement("", "", "sharksCount");
    }
    private void writeSmallFishesCount() throws SAXException {
        transHandler.startElement("", "", "smallFishesCount", null);
        char[] tempSmallFishes = String.valueOf(oceanConfig.getSmallFishes().size()).toCharArray();
        transHandler.characters(tempSmallFishes, 0, tempSmallFishes.length);
        transHandler.endElement("", "", "smallFishesCount");
    }
}

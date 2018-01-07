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
import java.io.IOException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

/**
 * SAX parser
 */

public class SAXParserXML extends DefaultHandler implements IXMLParser {

    private int hungerTime = 0;
    private OceanConfig oceanConfig=new OceanConfig();
    private List<Flow> flows;
    private Fish fish;
    private List<IFish> smallfishes;
    private List<IFish> sharkfishes;
    private Location location;
    private String currentElement;
    private TransformerFactory factory;
    private SAXTransformerFactory saxTransFactory;
    private TransformerHandler transHandler;

    /**
     * read ocean config from input stream
     *
     * @param config input stream
     * @return ocean configuration
     */
    @Override
    public OceanConfig read(InputStream config) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SAXParserXML handler = new SAXParserXML();
            saxParser.parse(config, handler);
            OceanConfig oceanConfig = handler.getOceanConfig();
            if (CommonUtils.checkEmpty(oceanConfig.getSharks()) && CommonUtils.checkEmpty(oceanConfig.getSmallFishes())) {
                return null;
            }
            return oceanConfig;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    OceanConfig getOceanConfig() {
        return oceanConfig;
    }

    /**
     * method start element is called at the beginning of an XML element
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        switch (currentElement) {
            case "ocean":
                oceanConfig = new OceanConfig();
                break;
            case "shark":
                fish = new Shark();
                break;
            case "fish":
                fish = new SmallFish();
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

    /**
     * method characters - reads text between tags,
     * parse ocean config
     *
     * @param ch chars
     * @param start start position
     * @param length length
     * @throws SAXException
     */
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

    /**
     * method end element is called at the end of an XML element,
     * parse fishes, flows
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
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
        currentElement = null;
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
            factory = TransformerFactory.newInstance().newInstance();
            saxTransFactory = (SAXTransformerFactory) factory;
            transHandler = saxTransFactory.newTransformerHandler();
            transHandler.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
            transHandler.setResult(new StreamResult(outputStream));
            writeRoot(metricsWriter);
        } catch (TransformerConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * create the document root element
     *
     * @param metricsWriter MetricsWriter
     *
     */

    private void writeRoot(MetricsWriter metricsWriter) throws SAXException {
        transHandler.startDocument();
        transHandler.startElement("", "", "snapshots", null);
        writeSnapshots(metricsWriter);
        transHandler.endElement("", "", "snapshots");
        transHandler.endDocument();
    }

    /**
     * create document elements and write metrics for some step
     *
     * @param metricsWriter MetricsWriter
     *
     */

    private void writeSnapshots(MetricsWriter metricsWriter) throws SAXException {
        for (int i = 0; i < metricsWriter.snapshots.size(); i++) {
            transHandler.startElement("", "", "snapshot", null);
            transHandler.startElement("", "", "step", null);
            char[] tempStep = String.valueOf(metricsWriter.snapshots.get(i).getStep()).toCharArray();
            transHandler.characters(tempStep, 0, tempStep.length);
            transHandler.endElement("", "", "step");
            transHandler.startElement("", "", "metrics", null);
            for (int j = 0; j < metricsWriter.snapshots.get(i).getMetricList().size(); j++) {
                transHandler.startElement("", "", "metric", null);
                transHandler.startElement("", "", "name", null);
                char[] tempName = String.valueOf(metricsWriter.snapshots.get(i).getMetricList().get(j).getName()).toCharArray();
                transHandler.characters(tempName, 0, tempName.length);
                transHandler.endElement("", "", "name");
                transHandler.startElement("", "", "value", null);
                char[] tempValue = String.valueOf(metricsWriter.snapshots.get(i).getMetricList().get(j).getValue()).toCharArray();
                transHandler.characters(tempValue, 0, tempValue.length);
                transHandler.endElement("", "", "value");
                transHandler.endElement("", "", "metric");
            }
            transHandler.endElement("", "", "metrics");
            transHandler.endElement("", "", "snapshot");
        }
    }
}

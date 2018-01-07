package parsers;

import com.netcracker.unc.metric.MetricsWriter;
;
import tools.ParsersTools;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.parsers.IXMLParser;
import com.netcracker.unc.parsers.StAXParserXML;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import junit.framework.Assert;
import org.junit.Test;



public class StAXTests {

    String XMLString;

    @Test
    public void readXMLTest() {
        XMLString = ParsersTools.XMLString;
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser staxReader = new StAXParserXML();
        OceanConfig oceanConfig = staxReader.read(inputStream);
        Assert.assertTrue(oceanConfig.equals(ParsersTools.getOceanConfig()));
    }

    @Test
    public void readXMLWithoutOceanTag() {
        XMLString = ParsersTools.XMLStringWithoutOceanTag;
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser staxReader = new StAXParserXML();
        OceanConfig oceanConfig = staxReader.read(inputStream);
        Assert.assertNull(oceanConfig);
    }

    @Test
    public void readXMLWithWrongTags() {
        XMLString = ParsersTools.XMLStringWrongTags;
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser staxReader = new StAXParserXML();
        OceanConfig oceanConfig = staxReader.read(inputStream);
        Assert.assertNull(oceanConfig);
    }

    @Test
    public void readXMLWithoutFishes() {
        XMLString = ParsersTools.XMLStringWithoutFishes;
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser staxReader = new StAXParserXML();
        OceanConfig oceanConfig = staxReader.read(inputStream);
        Assert.assertNull(oceanConfig);
    }

    @Test
    public void readDifferentXML() {
        IXMLParser staxReader = new StAXParserXML();
        String XMLString = "<ocean><tor>true</tor><height>2</height><width>20</width><flows><flowElement>LEFT</flowElement><flow>RIGHT</flow></flows><changeFlow>10</changeFlow>"
                + "<sharks><shark><lifetime>40</lifetime><progenyPeriod>1</progenyPeriod><searchRadius>4</searchRadius><hungerTime>20</hungerTime><location><x>0</x><y>1</y></location></shark></sharks>"
                + "<smallFishes><fish><lifetime>30</lifetime><progenyPeriod>3</progenyPeriod><searchRadius>2</searchRadius><location><x>0</x><y>7</y></location></fish>"
                + "<fish><lifetime>30</lifetime><progenyPeriod>3</progenyPeriod><searchRadius>2</searchRadius><location><x>1</x><y>9</y></location></fish></smallFishes></ocean>";
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        OceanConfig oceanConfig = staxReader.read(inputStream);
        Assert.assertNull(oceanConfig);
        XMLString = "<ocean><tor>true</tor><height>2</height><width>20</width><flows><flow>LEFT</flow><flow>RIGHT</flow></flows><changeFlow>10</changeFlow>"
                + "<sharks><shark><lifetime>40</lifetime><progenyPeriod>1</progenyPeriod><searchRadius>4</searchRadius><hungerTime>20</hungerTime><location><x>0</x><y>1</y></location></shark></sharks>"
                + "<smallFishes><fish><lifetime>30</lifetime><progenyPeriod>3</progenyPeriod><searchRadius>2</searchRadius><location><x>0</x><y>7</y><z>5></z></location></fish>"
                + "<fish><lifetime>30</lifetime><progenyPeriod>3</progenyPeriod><searchRadius>2</searchRadius><location><x>1</x><y>9</y></location></fish></smallFishes></ocean>";
        inputStream = new ByteArrayInputStream(XMLString.getBytes());
        oceanConfig = staxReader.read(inputStream);
        Assert.assertNull(oceanConfig);
                XMLString = "<ocean><tor>true</tor><height>2</height><width>20</width><flows><flow>LEFT</flow><flow>RIGHT</flow></flows><changeFlow>10</changeFlow>"
                + "<sharks><shark><element>10</element><lifetime>40</lifetime><progenyPeriod>1</progenyPeriod><searchRadius>4</searchRadius><hungerTime>20</hungerTime><location><x>0</x><y>1</y></location></shark></sharks>"
                + "<smallFishes><fish><lifetime>30</lifetime><progenyPeriod>3</progenyPeriod><searchRadius>2</searchRadius><location><x>0</x><y>7</y></location></fish>"
                + "<fish><lifetime>30</lifetime><progenyPeriod>3</progenyPeriod><searchRadius>2</searchRadius><location><x>1</x><y>9</y></location></fish></smallFishes></ocean>";
        inputStream = new ByteArrayInputStream(XMLString.getBytes());
        oceanConfig = staxReader.read(inputStream);
        Assert.assertNull(oceanConfig);
    }

    @Test
    public void writeXMLTest() throws UnsupportedEncodingException {
        XMLString = ParsersTools.XMLStringMonitoringStax;
        MetricsWriter metricsWriter = ParsersTools.getMetricsWriter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IXMLParser staxWriter = new StAXParserXML();
        staxWriter.write(metricsWriter, outputStream);
        String XMLStringRes = new String(outputStream.toByteArray(), "UTF-8");
        Assert.assertTrue(XMLString.equals(XMLStringRes));
    }
}

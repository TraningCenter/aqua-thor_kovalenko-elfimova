package parsers;

import com.netcracker.unc.metric.MetricsWriter;
import tools.ParsersTools;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.parsers.IXMLParser;
import com.netcracker.unc.parsers.SAXParserXML;
import junit.framework.Assert;
import org.junit.Test;

import java.io.*;

public class SAXTests {

    String XMLString;

    @Test
    public void readXMLTest() {
        XMLString = ParsersTools.XMLString;
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser saxParser = new SAXParserXML();
        OceanConfig oceanConfig = saxParser.read(inputStream);
        Assert.assertTrue(oceanConfig.equals(ParsersTools.getOceanConfig()));
    }

    @Test
    public void readXMLWithoutOceanTag() {
        XMLString = ParsersTools.XMLStringWithoutOceanTag;
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser saxParser = new SAXParserXML();
        OceanConfig oceanConfig = saxParser.read(inputStream);
        Assert.assertNull(oceanConfig);
    }

    @Test
    public void readXMLWithoutFishes() {
        XMLString = ParsersTools.XMLStringWithoutFishes;
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser saxParser = new SAXParserXML();
        OceanConfig oceanConfig = saxParser.read(inputStream);
        Assert.assertNull(oceanConfig);
    }

    @Test
    public void writeXMLTest() throws UnsupportedEncodingException {
        XMLString = ParsersTools.XMLStringMonitoring;
        MetricsWriter metricsWriter = ParsersTools.getMetricsWriter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IXMLParser saxParser = new SAXParserXML();
        saxParser.write(metricsWriter, outputStream);
        String XMLStringRes = new String(outputStream.toByteArray(), "UTF-8");
        Assert.assertTrue(XMLString.equals(XMLStringRes));
    }
}

package parsers;


import tools.ParsersTools;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.parsers.IXMLParser;
import com.netcracker.unc.parsers.JAXBParserXML;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import junit.framework.Assert;
import org.junit.Test;

public class JAXBTests {

    String XMLString;

    @Test
    public void readXMLTest() {
        XMLString = ParsersTools.XMLString;
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser jaxbParser = new JAXBParserXML();
        OceanConfig oceanConfig = jaxbParser.read(inputStream);
        Assert.assertTrue(oceanConfig.equals(ParsersTools.getOceanConfig()));
    }

    @Test
    public void readXMLWithoutOceanTag() {
        XMLString = ParsersTools.XMLStringWithoutOceanTag;
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser jaxbParser = new JAXBParserXML();
        OceanConfig oceanConfig = jaxbParser.read(inputStream);
        Assert.assertNull(oceanConfig);
    }

    @Test
    public void readXMLWithWrongTags() {
        XMLString = ParsersTools.XMLStringWrongTags;
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser jaxbParser = new JAXBParserXML();
        OceanConfig oceanConfig = jaxbParser.read(inputStream);
        Assert.assertNull(oceanConfig);
    }

    @Test
    public void readXMLWithoutFishes() {
        XMLString = ParsersTools.XMLStringWithoutFishes;
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser jaxbParser = new JAXBParserXML();
        OceanConfig oceanConfig = jaxbParser.read(inputStream);
        Assert.assertNull(oceanConfig);
    }
}

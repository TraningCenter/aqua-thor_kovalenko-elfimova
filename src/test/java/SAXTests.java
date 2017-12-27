import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.parsers.IXMLParser;
import com.netcracker.unc.parsers.SAXParserXML;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created on 27.12.2017.
 */
public class SAXTests {
    String XMLString;

    @Before
    public void init() {
        XMLString = ParsersTools.XMLString;
    }

    @Test
    public void readXMLTest() {
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser saxParser = new SAXParserXML();
        OceanConfig oceanConfig = saxParser.read(inputStream);
        Assert.assertTrue(oceanConfig.equals(ParsersTools.getOceanConfig()));
    }
}

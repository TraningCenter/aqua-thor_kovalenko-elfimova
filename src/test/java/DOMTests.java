import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.parsers.DOMParser;
import com.netcracker.unc.parsers.IXMLParser;
import com.netcracker.unc.parsers.JAXBParser;
import com.netcracker.unc.parsers.StAXParser;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created on 19.12.2017.
 */
public class DOMTests {
    String XMLString;

    @Before
    public void init() {
        XMLString = ParsersTools.XMLString;
    }

    @Test
    public void readXMLTest() {
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser domParser = new DOMParser();
        OceanConfig oceanConfig = domParser.read(inputStream);
        Assert.assertTrue(oceanConfig.equals(ParsersTools.getOceanConfig()));
    }
}

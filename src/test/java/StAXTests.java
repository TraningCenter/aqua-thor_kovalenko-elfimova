
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.parsers.IXMLParser;
import com.netcracker.unc.parsers.JAXBParser;
import com.netcracker.unc.parsers.StAXParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class StAXTests {

    String XMLString;

    @Before
    public void init() {
        XMLString = ParsersTools.XMLStringEx1;
    }

    @Test
    public void readXMLTest() {
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser staxReader = new StAXParser();
        OceanConfig oceanConfig = staxReader.read(inputStream);
        Assert.assertTrue(oceanConfig.equals(ParsersTools.getOceanConfigEx1()));
    }
}

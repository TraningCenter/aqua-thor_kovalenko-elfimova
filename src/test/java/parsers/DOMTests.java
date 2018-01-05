package parsers;

import tools.ParsersTools;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.parsers.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created on 19.12.2017.
 */
public class DOMTests {
    String XMLString;

    @Test
    public void readXMLTest() {
        XMLString = ParsersTools.XMLString;
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser domParser = new DOMParser();
        OceanConfig oceanConfig = domParser.read(inputStream);
        Assert.assertTrue(oceanConfig.equals(ParsersTools.getOceanConfig()));
    }

    @Test
    public void writeXMLTest()throws UnsupportedEncodingException {
        XMLString = ParsersTools.XMLStringMonitoring;
        OceanConfig oceanConfig=ParsersTools.getOceanConfig();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IXMLParser domParser = new DOMParser();
        domParser.write(oceanConfig,outputStream);
        String XMLStringRes = new String(outputStream.toByteArray(), "UTF-8");
        Assert.assertTrue(XMLString.equals(XMLStringRes));
    }
}

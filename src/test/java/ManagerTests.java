
import com.netcracker.unc.OceanManager;
import com.netcracker.unc.utils.CommonUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.Test;

public class ManagerTests {

    OceanManager manager = new OceanManager();

    @Test
    public void getsetParserPropertyTest() {
        String value = null;
        try {
            CommonUtils.setParserProperty("config.properties", "inputparser", "JAXB");
            value = CommonUtils.getParserProperty("config.properties", "jaxb");
        } catch (IOException ex) {
            Logger.getLogger(ManagerTests.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Test
    public void setParserWrongPropertyTest() {
        try {
            CommonUtils.setParserProperty("config2.properties", "inputparser", "DOM");
        } catch (IOException ex) {
        }

    }

    @Test
    public void getParserWrongPropertyTest() {
        String value = null;
        try {
            value = CommonUtils.getParserProperty("config2.properties", "inputparser");
        } catch (IOException ex) {
        }
        Assert.assertNull(value);
    }
}

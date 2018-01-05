
import com.netcracker.unc.OceanManager;
import com.netcracker.unc.utils.CommonUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import junit.framework.Assert;
import org.junit.Test;

public class ManagerTests {

    OceanManager manager = new OceanManager();

    @Test
    public void setParserPropertyTest() {
        CommonUtils.setParserProperty("inputparser", "JAXB");
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            Assert.assertEquals("jaxb", prop.getProperty("inputparser"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

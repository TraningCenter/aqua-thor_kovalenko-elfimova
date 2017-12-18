
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.parsers.IXMLParser;
import com.netcracker.unc.parsers.JAXBParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;

public class JAXBTests {

    String XMLString;

    @Before
    public void init() {
        XMLString = "<ocean><tor>true</tor><height>2</height><weight>20</weight><flows><flow>LEFT</flow><flow>RIGHT</flow></flows><changeFlow>10</changeFlow>"
                + "<sharks><shark><lifetime>40</lifetime><progenyPeriod>21</progenyPeriod><searchRadius>4</searchRadius><hungerTime>20</hungerTime><location><x>0</x><y>1</y></location></shark></sharks>"
                + "<smallFishes><fish><lifetime>30</lifetime><progenyPeriod>9</progenyPeriod><searchRadius>2</searchRadius><location><x>0</x><y>7</y></location></fish>"
                + "<fish><lifetime>30</lifetime><progenyPeriod>9</progenyPeriod><searchRadius>2</searchRadius><location><x>1</x><y>9</y></location></fish></smallFishes></ocean>";
    }

    @Test
    public void readXMLTest() {
        InputStream inputStream = new ByteArrayInputStream(XMLString.getBytes());
        IXMLParser jaxbParser = new JAXBParser();
        OceanConfig oceanConfig = jaxbParser.read(inputStream);
    }
}

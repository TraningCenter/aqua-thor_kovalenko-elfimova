
import com.netcracker.unc.OceanManager;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.visualizer.OceanVisualizer;
import java.io.IOException;
import static junit.framework.Assert.fail;
import org.junit.Test;
import tools.ParsersTools;

public class VisualizerTests {

    Ocean ocean;
    OceanVisualizer visualizer;
    OceanManager manager;

    public VisualizerTests() {
        ocean = new Ocean(ParsersTools.getOceanConfig());
        manager = new OceanManager(false);
        try {
            this.visualizer = new OceanVisualizer();
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void visualize() {
        try {
            visualizer.visualize();
        } catch (IOException | InterruptedException ex) {
            fail();
        }
    }

    @Test
    public void visualizerMethodsTest() {
        try {
            visualizer.startScreen();
            visualizer.refresh();
            visualizer.clear();
            visualizer.stopScreen();
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void menuTest() {
        try {
            manager.setVisualizer(visualizer);
            manager.mainMenu();
            manager.configMenu();
            manager.parserSettingsMenu("Test1", true);
            manager.parserSettingsMenu("Test2", false);
            manager.concreteParserSettingsMenu(true);
            manager.inputWritePeriod();
        } catch (Exception ex) {
            fail();
        }
    }
}

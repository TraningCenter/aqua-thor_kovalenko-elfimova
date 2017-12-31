package com.netcracker.unc;

import com.netcracker.unc.model.Flow;
import com.netcracker.unc.model.Location;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import com.netcracker.unc.parsers.IXMLParser;
import com.netcracker.unc.parsers.JAXBParser;
import com.netcracker.unc.visualizer.OceanVisualizer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OceanManager {

    Ocean ocean;
    OceanVisualizer visualizer;

    public void run() {
        try {
            ocean = new Ocean(readConfig());
        } catch (IOException ex) {
            System.out.println("Ошибка чтения файла!");
        }
        visualizer = new OceanVisualizer();
        visualizer.startScreen();
        while (true) {
            visualizer.visualize();
            ocean.oneStep();
        }
    }

    public OceanConfig readConfig() throws IOException {
        InputStream inputStream = new FileInputStream("config.xml");
        IXMLParser jaxbParser = new JAXBParser();
        OceanConfig config = jaxbParser.read(inputStream);
        inputStream.close();
        return config;
    }

    public OceanConfig getOceanConfig() {
        List<Flow> flows = new ArrayList();
        flows.add(Flow.LEFT);
        flows.add(Flow.RIGHT);
        List<IFish> sharks = new ArrayList();
        sharks.add(new Shark(new Location(0, 1), 40, 1, 4, 20));
        List<IFish> smallFishes = new ArrayList();
        smallFishes.add(new SmallFish(new Location(0, 7), 30, 3, 2));
        smallFishes.add(new SmallFish(new Location(1, 9), 30, 3, 2));
        return new OceanConfig(true, 2, 20, flows, 10, sharks, smallFishes);
    }
}

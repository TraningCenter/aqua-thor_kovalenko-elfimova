package com.netcracker.unc;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OceanManager {

    Ocean ocean;
    OceanVisualizer visualizer;
    ExecutorService executor;
    boolean isStop = false;

    public void run() {
        visualizer = new OceanVisualizer();
        visualizer.startScreen();
        mainMenu();
    }

    private void mainMenu() {
        executor = Executors.newFixedThreadPool(1);
        configMenu();
        executor.submit(() -> {
            isStop = false;
            while (!isStop) {
                visualizer.visualize();
                ocean.oneStep();
            }
        });
        while (true) {
            KeyStroke keyStroke;
            try {
                keyStroke = visualizer.getScreen().readInput();
                if (keyStroke.getKeyType() == KeyType.Escape || keyStroke.getKeyType() == KeyType.EOF) {
                    isStop = true;
                    mainMenu();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

    public void configMenu() {
        visualizer.clear();
        TextGraphics textGraphics = visualizer.getScreen().newTextGraphics();
        try {
            while (true) {
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.putString(new TerminalPosition(0, 1), "Выберите конфигурацию: ");
                textGraphics.putString(new TerminalPosition(0, 2), "1. Из файла config.xml");
                textGraphics.putString(new TerminalPosition(0, 3), "2. Пример по умолчанию");
                textGraphics.putString(new TerminalPosition(0, 4), "3. Настройка парсера");
                textGraphics.putString(new TerminalPosition(0, 5), "Esq. Выход");
                visualizer.refresh();
                KeyStroke keyStroke = visualizer.getScreen().readInput();
                while (keyStroke == null) {
                    keyStroke = visualizer.getScreen().readInput();
                }
                System.out.println(keyStroke.getKeyType().toString());
                switch (keyStroke.getKeyType()) {
                    case Escape:
                        visualizer.stopScreen();
                        System.exit(0);
                        break;
                    case EOF:
                        visualizer.stopScreen();
                        System.exit(0);
                        break;
                    case Character:
                        switch (keyStroke.getCharacter()) {
                            case '1':
                                ocean = new Ocean(readConfig());
                                return;
                            case '2':
                                ocean = new Ocean(getOceanConfig());
                                return;
                            case '3':
                                inputParserSettings();
                                break;
                            default:
                                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                                textGraphics.putString(new TerminalPosition(0, 0), "Ошибка! Введите число от 1 до 3!");
                                break;
                        }
                }
            }
        } catch (IOException ex) {
            System.out.println("Ошибка отображения меню!");
        }
    }

    private void inputParserSettings() {

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

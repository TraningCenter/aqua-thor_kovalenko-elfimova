package com.netcracker.unc;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.netcracker.unc.creator.OceanCreator;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.parsers.DOMParserXML;
import com.netcracker.unc.parsers.IXMLParser;
import com.netcracker.unc.parsers.JAXBParserXML;
import com.netcracker.unc.parsers.SAXParserXML;
import com.netcracker.unc.parsers.StAXParserXML;
import com.netcracker.unc.utils.CommonUtils;
import com.netcracker.unc.visualizer.OceanVisualizer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.xml.parsers.SAXParser;

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
                ex.printStackTrace();
            }

        }
    }

    private void configMenu() {
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
                        exit();
                        break;
                    case EOF:
                        exit();
                        break;
                    case Character:
                        switch (keyStroke.getCharacter()) {
                            case '1':
                                ocean = new Ocean(readConfig());
                                return;
                            case '2':
                                ocean = new Ocean(OceanCreator.getDefaultOceanConfig());
                                return;
                            case '3':
                                parserSettingsMenu("");
                                break;
                            default:
                                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                                textGraphics.putString(new TerminalPosition(0, 0), "Ошибка! Введите число от 1 до 3!");
                                break;
                        }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void parserSettingsMenu(String message) {
        visualizer.clear();
        TextGraphics textGraphics = visualizer.getScreen().newTextGraphics();
        if (message != null && !message.isEmpty()) {
            textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
            textGraphics.putString(new TerminalPosition(0, 0), message);
        }
        try {
            while (true) {
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.putString(new TerminalPosition(0, 1), "Выбор парсеров: ");
                textGraphics.putString(new TerminalPosition(0, 2), "1. Парсер ввода");
                textGraphics.putString(new TerminalPosition(0, 3), "2. Парсер вывода");
                textGraphics.putString(new TerminalPosition(0, 4), "Esq. Назад");
                visualizer.refresh();
                KeyStroke keyStroke = visualizer.getScreen().readInput();
                while (keyStroke == null) {
                    keyStroke = visualizer.getScreen().readInput();
                }
                System.out.println(keyStroke.getKeyType().toString());
                switch (keyStroke.getKeyType()) {
                    case Escape:
                        mainMenu();
                        break;
                    case EOF:
                        exit();
                        break;
                    case Character:
                        switch (keyStroke.getCharacter()) {
                            case '1':
                                concreteParserSettingsMenu(true);
                                return;
                            case '2':
                                concreteParserSettingsMenu(false);
                                return;
                            default:
                                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                                textGraphics.putString(new TerminalPosition(0, 0), "Ошибка! Введите число от 1 до 2!");
                                break;
                        }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void concreteParserSettingsMenu(boolean isInput) {
        visualizer.clear();
        TextGraphics textGraphics = visualizer.getScreen().newTextGraphics();
        String key = isInput ? "inputparser" : "outputparser";
        try {
            while (true) {
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.putString(new TerminalPosition(0, 1), "Типы парсеров: ");
                textGraphics.putString(new TerminalPosition(0, 2), "1. DOM");
                textGraphics.putString(new TerminalPosition(0, 3), "2. SAX");
                textGraphics.putString(new TerminalPosition(0, 4), "3. StAX");
                textGraphics.putString(new TerminalPosition(0, 5), "4. JAXB");
                textGraphics.putString(new TerminalPosition(0, 6), "Esq. Назад");
                visualizer.refresh();
                KeyStroke keyStroke = visualizer.getScreen().readInput();
                while (keyStroke == null) {
                    keyStroke = visualizer.getScreen().readInput();
                }
                System.out.println(keyStroke.getKeyType().toString());
                switch (keyStroke.getKeyType()) {
                    case Escape:
                        parserSettingsMenu("");
                        return;
                    case EOF:
                        exit();
                        break;
                    case Character:
                        switch (keyStroke.getCharacter()) {
                            case '1':
                                CommonUtils.setParserProperty(key, "dom");
                                parserSettingsMenu(String.format("%s -> dom. Настройки сохранены", key));
                                return;
                            case '2':
                                CommonUtils.setParserProperty(key, "sax");
                                parserSettingsMenu(String.format("%s -> sax. Настройки сохранены", key));
                                return;
                            case '3':
                                CommonUtils.setParserProperty(key, "stax");
                                parserSettingsMenu(String.format("%s -> stax. Настройки сохранены", key));
                                return;
                            case '4':
                                CommonUtils.setParserProperty(key, "jaxb");
                                parserSettingsMenu(String.format("%s -> jaxb. Настройки сохранены", key));
                                return;
                            default:
                                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                                textGraphics.putString(new TerminalPosition(0, 0), "Ошибка! Введите число от 1 до 4!");
                                break;
                        }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void exit() {
        visualizer.stopScreen();
        System.exit(0);
    }

    public OceanConfig readConfig() throws IOException {
        InputStream inputStream = new FileInputStream("config.xml");
        String value = CommonUtils.getParserProperty("inputparser").toLowerCase().trim();
        IXMLParser parser;
        switch (value) {
            case "dom":
               parser = new DOMParserXML();
                break;
            case "sax":
                parser = new SAXParserXML();
                break;
            case "stax":
                parser = new StAXParserXML();
                break;
            case "jaxb":
                parser = new JAXBParserXML();
                break;
            default:
                parserSettingsMenu("Ошибка! Выбранный в настройках парсер не найден");
                return null;
        }
        
        OceanConfig config = parser.read(inputStream);
        inputStream.close();
        return config;
    }
}

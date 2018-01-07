package com.netcracker.unc;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.netcracker.unc.creator.OceanCreator;
import com.netcracker.unc.metric.MetricsWriter;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.parsers.DOMParserXML;
import com.netcracker.unc.parsers.IXMLParser;
import com.netcracker.unc.parsers.JAXBParserXML;
import com.netcracker.unc.parsers.SAXParserXML;
import com.netcracker.unc.parsers.StAXParserXML;
import com.netcracker.unc.utils.CommonUtils;
import com.netcracker.unc.visualizer.OceanVisualizer;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.netcracker.unc.metric.MetricsWriter.snapshots;

/**
 * Ocean system manager
 */
public class OceanManager {

    Ocean ocean;
    OceanVisualizer visualizer;
    ExecutorService executor;
    boolean isStop = false;
    MetricsWriter metricsWriter;
    int writePeriod = 10;
    String propertiesFilename = "config.properties";

    /**
     * start the ocean system
     */
    public void run() {
        visualizer = new OceanVisualizer();
        visualizer.startScreen();
        mainMenu();
    }

    /**
     * start user menu
     */
    private void mainMenu() {
        executor = Executors.newFixedThreadPool(2);
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
                    executor.submit(() -> {
                        try {
                            writeMetrics();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    executor.shutdown();
                    mainMenu();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * configuration menu
     */
    private void configMenu() {
        visualizer.clear();
        TextGraphics textGraphics = visualizer.getTextGraphics();
        try {
            while (true) {
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.putString(new TerminalPosition(0, 1), "Выберите конфигурацию: ");
                textGraphics.putString(new TerminalPosition(0, 2), "1. Из файла config.xml");
                textGraphics.putString(new TerminalPosition(0, 3), "2. Пример по умолчанию");
                textGraphics.putString(new TerminalPosition(0, 4), "3. Настройка парсеров");
                textGraphics.putString(new TerminalPosition(0, 5), "Esq. Выход");
                visualizer.refresh();
                KeyStroke keyStroke = visualizer.getScreen().readInput();
                while (keyStroke == null) {
                    keyStroke = visualizer.getScreen().readInput();
                }
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
                                parserSettingsMenu("", false);
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

    /**
     * parser settings menu
     *
     * @param message message to user
     * @param isError
     */
    private void parserSettingsMenu(String message, boolean isError) {
        visualizer.clear();
        TextGraphics textGraphics = visualizer.getTextGraphics();
        if (message != null && !message.isEmpty()) {
            if (isError) {
                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                textGraphics.putString(new TerminalPosition(0, 0), message);
            } else {
                textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
                textGraphics.putString(new TerminalPosition(0, 0), message);
            }
        }
        try {
            while (true) {
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.putString(new TerminalPosition(0, 1), "Настройка парсеров: ");
                textGraphics.putString(new TerminalPosition(0, 2), "1. Парсер ввода");
                textGraphics.putString(new TerminalPosition(0, 3), "2. Парсер вывода");
                textGraphics.putString(new TerminalPosition(0, 4), "3. Периодичность сохранения в файл");
                textGraphics.putString(new TerminalPosition(0, 5), "Esq. Назад");
                visualizer.refresh();
                KeyStroke keyStroke = visualizer.getScreen().readInput();
                while (keyStroke == null) {
                    keyStroke = visualizer.getScreen().readInput();
                }
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
                            case '3':
                                inputWritePeriod();
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

    /**
     * concrete parser settings menu
     *
     * @param isInput true if parse is input
     */
    private void concreteParserSettingsMenu(boolean isInput) {
        visualizer.clear();
        TextGraphics textGraphics = visualizer.getTextGraphics();
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
                switch (keyStroke.getKeyType()) {
                    case Escape:
                        parserSettingsMenu("", false);
                        return;
                    case EOF:
                        exit();
                        break;
                    case Character:
                        switch (keyStroke.getCharacter()) {
                            case '1':
                                CommonUtils.setParserProperty(propertiesFilename, key, "dom");
                                parserSettingsMenu(String.format("%s -> dom. Настройки сохранены", key), false);
                                return;
                            case '2':
                                CommonUtils.setParserProperty(propertiesFilename, key, "sax");
                                parserSettingsMenu(String.format("%s -> sax. Настройки сохранены", key), false);
                                return;
                            case '3':
                                CommonUtils.setParserProperty(propertiesFilename, key, "stax");
                                parserSettingsMenu(String.format("%s -> stax. Настройки сохранены", key), false);
                                return;
                            case '4':
                                CommonUtils.setParserProperty(propertiesFilename, key, "jaxb");
                                parserSettingsMenu(String.format("%s -> jaxb. Настройки сохранены", key), false);
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

    /**
     * input positive integer (writePeriod)
     */
    private void inputWritePeriod() {
        visualizer.clear();
        TextGraphics textGraphics = visualizer.getTextGraphics();
        StringBuilder sb = new StringBuilder();
        try {
            while (true) {
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.putString(new TerminalPosition(0, 1), String.format("Текушая периодичность: %s", writePeriod));
                textGraphics.putString(new TerminalPosition(0, 2), "Введите положительное число: " + sb.toString());
                visualizer.refresh();
                KeyStroke keyStroke = visualizer.getScreen().readInput();
                while (keyStroke == null) {
                    keyStroke = visualizer.getScreen().readInput();
                }
                switch (keyStroke.getKeyType()) {
                    case Escape:
                        parserSettingsMenu("", false);
                        return;
                    case EOF:
                        exit();
                        break;
                    case Enter:
                        int i;
                        try {
                            i = Integer.parseInt(sb.toString());
                        } catch (NumberFormatException ex) {
                            parserSettingsMenu(String.format("Ошибка! Введено некорректное число. Текущая периодичность: %s", writePeriod), true);
                            return;
                        }
                        if (i <= 0) {
                            parserSettingsMenu(String.format("Ошибка! Введено отрицательное число. Текущая периодичность: %s", writePeriod), true);
                            return;
                        }
                        writePeriod = i;
                        parserSettingsMenu(String.format("Число было сохранено. Текущая периодичность: %s", writePeriod), false);
                        return;
                    case Character:
                        sb.append(keyStroke.getCharacter());
                        break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * exit of the system
     */
    private void exit() {
        visualizer.stopScreen();
        System.exit(0);
    }

    /**
     * read configuration from file
     *
     * @return ocean configuration
     * @throws IOException
     */
    private OceanConfig readConfig() throws IOException {
        InputStream inputStream = new FileInputStream("config.xml");
        String value = CommonUtils.getParserProperty(propertiesFilename, "inputparser").toLowerCase().trim();
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
                parserSettingsMenu("Ошибка! Выбранный в настройках парсер не найден", true);
                return null;
        }
        OceanConfig config = parser.read(inputStream);
        inputStream.close();
        return config;
    }

    /**
     * write metrics to a file
     *
     * @throws IOException
     */

    private void writeMetrics() throws IOException {
        OutputStream outputStream = new FileOutputStream("metric.xml");
        String value = CommonUtils.getParserProperty("outputparser").toLowerCase().trim();
        IXMLParser parser = null;
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
                break;
        }
        //IXMLParser parser = new DOMParserXML();
        parser.write(new MetricsWriter(snapshots), outputStream);
        outputStream.close();
    }
}

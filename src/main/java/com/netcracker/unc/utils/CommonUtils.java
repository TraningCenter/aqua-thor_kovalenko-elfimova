package com.netcracker.unc.utils;

import com.netcracker.unc.model.Location;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Properties;
import java.util.Random;

public class CommonUtils {

    public static boolean checkEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static double getDistanceBetweenLocations(Location location1, Location location2) {
        double d = Math.pow(location2.getX() - location1.getX(), 2) + Math.pow(location2.getY() - location1.getY(), 2);
        return Math.sqrt(d);
    }

    public static int getCorrectX(int i, int height, boolean isTor) {
        int currx = i;
        if (isTor) {
            if (i < 0) {
                currx = height + i;
            } else if (i >= height) {
                currx = i - height;
            }
        }
        return (currx >= height) ? -1 : currx;
    }

    public static int getCorrectY(int j, int width, boolean isTor) {
        int curry = j;
        if (isTor) {
            if (j < 0) {
                curry = width + j;
            } else if (j >= width) {
                curry = j - width;
            }
        }
        return (curry >= width) ? -1 : curry;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static void setParserProperty(String key, String value) {
        String propertiesFilename = "config.properties";
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(propertiesFilename));
            prop.setProperty(key.toLowerCase().trim(), value.toLowerCase().trim());
            prop.store(new FileOutputStream(propertiesFilename), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getParserProperty(String key) {
        String propertiesFilename = "config.properties";
        Properties prop = new Properties();
        String value;
        try {
            prop.load(new FileInputStream(propertiesFilename));
            value = prop.getProperty(key);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return value;
    }
}

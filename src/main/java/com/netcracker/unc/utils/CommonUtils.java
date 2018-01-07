package com.netcracker.unc.utils;

import com.netcracker.unc.model.Location;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Random;

/**
 * Class with common utils
 */
public class CommonUtils {

    /**
     * check collection if is empty
     *
     * @param collection
     * @return true if this collection contains no elements
     */
    public static boolean checkEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * get distance between locations
     *
     * @param location1 the first location
     * @param location2 the second location
     * @return distance
     */
    public static double getDistanceBetweenLocations(Location location1, Location location2) {
        double d = Math.pow(location2.getX() - location1.getX(), 2) + Math.pow(location2.getY() - location1.getY(), 2);
        return Math.sqrt(d);
    }

    /**
     * get correct x position in matrix
     *
     * @param i row
     * @param height count of rows
     * @param isTor closed system
     * @return x position
     */
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

    /**
     * get correct y position in matrix
     *
     * @param j row
     * @param width count of columns
     * @param isTor closed system
     * @return y position
     */
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

    /**
     * generate random integer value in range
     *
     * @param min minimum value
     * @param max maximimum value
     * @return random integer value
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /**
     * set property in properties file
     *
     * @param key
     * @param value
     */
    public static void setParserProperty(String propertiesFilename, String key, String value) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(propertiesFilename));
        prop.setProperty(key.toLowerCase().trim(), value.toLowerCase().trim());
        prop.store(new FileOutputStream(propertiesFilename), null);

    }

    /**
     * get property from properties file
     *
     * @param key
     * @return value
     */
    public static String getParserProperty(String propertiesFilename, String key) throws IOException {
        Properties prop = new Properties();
        String value;
        prop.load(new FileInputStream(propertiesFilename));
        value = prop.getProperty(key);
        return value;
    }
}

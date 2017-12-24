package com.netcracker.unc.utils;

import com.netcracker.unc.model.Location;
import java.util.Collection;

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
}
package com.netcracker.unc.model;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public static List<Direction> getDirectionByLocations(Location location1, Location location2) {
        List directions = new ArrayList();
        int x = location1.getX() - location2.getX();
        int y = location1.getY() - location2.getY();
        if (location1.equals(location2)) {
            return null;
        }
        if (x > 0) {
            directions.add(UP);
        } else if (x < 0) {
            directions.add(DOWN);
        }
        if (y > 0) {
            directions.add(LEFT);
        } else if (y < 0) {
            directions.add(RIGHT);
        }
        return directions;
    }

}

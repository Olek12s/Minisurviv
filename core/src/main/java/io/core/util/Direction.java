package io.core.util;

public enum Direction {
    DOWN, LEFT, RIGHT, UP;

    public static Direction getDirection(float xd, float yd) {
        if (Math.abs(xd) > Math.abs(yd)) {
            if (xd < 0) return Direction.LEFT;
            else return Direction.RIGHT;
        }
        else {
            if (yd < 0) return Direction.DOWN;
            else return Direction.UP;
        }
    }
}


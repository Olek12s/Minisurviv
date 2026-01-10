package io.core.util;

import java.util.Random;

public enum Direction {
    DOWN, LEFT, RIGHT, UP;
    private static Random random = new Random();

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

    public static Direction randomDirection() {
        int r = random.nextInt(4);

        switch (r) {
            case 0: return UP;
            case 1: return LEFT;
            case 2: return RIGHT;
            default: return DOWN;
        }
    }
}


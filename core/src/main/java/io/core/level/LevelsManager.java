package io.core.level;

import java.util.NavigableMap;
import java.util.TreeMap;

public class LevelsManager
{
    private final int seed;     // seed that is used to generate levels
    private static final Level[] levels = new Level[4];

    private static int currentLevel = 0;

    public LevelsManager(int seed) {
        this.seed = seed;
    }

    public static Level getCurrentLevel() {
        int idx = currentLevel - levelNames.firstKey();

        if (idx < 0 || idx >= levels.length) {
            throw new IllegalStateException("Invalid level depth: " + currentLevel);
        }
        return levels[idx];
    }

    private static final NavigableMap<Integer, String> levelNames = new TreeMap<>() {{
        put(-3, "Ruins");
        put(-2, "Deep Cave");
        put(-1, "Cave");
        put(0, "Surface");
    }};

    public static String getLevelName(int depth) {return levelNames.get(depth);}
}

package io.core.level;

import io.core.entity.mob.Player;
import io.core.util.FloatConsumer;

import java.util.NavigableMap;
import java.util.TreeMap;

public class LevelsManager
{
    private static final Level[] levels = new Level[4];

    private static String worldName;
    private static int worldSize;
    private static int worldSeed;
    public static Player player;

    private static int currentLevel = 0;

    public static final int SURFACE = 0, CAVE = -1, DEEP_CAVE = -2, RUINS = -3;

    public static void init(String worldName, int worldSize, int worldSeed) {
        LevelsManager.worldName = worldName;
        LevelsManager.worldSize = worldSize;
        LevelsManager.worldSeed = worldSeed;
    }

    /**
     * Generates all levels in order with parent references while reporting current progress.
     */
    public static void generateAllLevels(FloatConsumer overallProgress, FloatConsumer stepProgress, FloatConsumer stepNameProgress)
    {
        Level parent = null;
        int index = 0;
        int totalLevels = levelNames.size();

        for (int depth : levelNames.descendingKeySet()) {
            String name = levelNames.get(depth);
            Level lvl = new Level(worldSize, worldSize, parent, depth, worldSeed);
            levels[index] = lvl;

            int finalIndex = index;
            lvl.generate(p -> {
                float overall = (finalIndex + p) / totalLevels;
                overallProgress.accept(overall);
                stepProgress.accept(p);
                stepNameProgress.accept(finalIndex / (float) totalLevels);
            });

            parent = lvl;
            index++;
        }
    }

    public static Level getCurrentLevel() {
        return levels[currentLevel];
    }

    public static void setCurrentLevel(int level) {
        if (!levelNames.containsKey(level)) throw new IllegalArgumentException("Level does not exist");
        LevelsManager.currentLevel = level;
    }

    public static String getLevelName(int depth) { return levelNames.get(depth); }

    public static Level[] getLevels() { return levels; }

    private static final NavigableMap<Integer, String> levelNames = new TreeMap<>() {{
        put(RUINS, "Ruins");
        put(DEEP_CAVE, "Deep Cave");
        put(CAVE, "Cave");
        put(SURFACE, "Surface");
    }};
}

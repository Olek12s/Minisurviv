package io.core.level;

import io.core.entity.Player;

import java.util.NavigableMap;
import java.util.TreeMap;

public class LevelsManager
{
    private final int seed;     // seed that is used to generate levels
    private int worldSize = 256;    // TODO: 256 is temporary. worldSize shall be loadedfrom settings or loaded file
    private static final Level[] levels = new Level[4];
    private Player player;

    private static int currentLevel = 0;

    public static void setCurrentLevel(int level) {
        if (!levelNames.containsKey(level)) throw new IllegalArgumentException("Level does not exist");
        LevelsManager.currentLevel = level;
    }

    public LevelsManager() {

        // this.worldSize = settingsScreen.getWorldSize();
        // this.seed = settingsScreen.getSeed);
        this.seed = 750;
        this.player = new Player();


        Level parent = null;
        int index = 0;
        // Levels are loaded in reverse order for proper Parent References
        for (int lvl : levelNames.descendingKeySet()) {
            levels[index] = new Level(
                    worldSize,
                    worldSize,
                    parent,   // parentLevel
                    lvl,
                    seed
            );

            parent = levels[index];
            index++;
        }
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

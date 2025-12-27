package io.core.level;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Level
{
    private static final HashMap<Integer, String> levels = new HashMap<>() {{
        put(-3, "Ruins");
        put(-2, "Deep Cave");
        put(-1, "Cave");
        put(0, "Surface");
    }};

    private int width, height;  // size of the Level map
    private final int seed;     // seed that was used to generate this level
    private Level parentLevel; // reference to parent level

    private Set<Object> entities = new HashSet<>();         // TODO : change to entity
    private Set<Object> players = new HashSet<>();          // TODO : change to player
    private Set<Object> entitiesToAdd = new HashSet<>();    // TODO : change to entity
    private Set<Object> entitiesToRemove = new HashSet<>(); // TODO : change to entity

    public static String getLevelName(int depth) {return levels.get(depth);}


    public Level(int width, int height, int seed, Level parentLevel) {
        this.width = width;
        this.height = height;
        this.seed = seed;
        this.parentLevel = parentLevel;
    }
}

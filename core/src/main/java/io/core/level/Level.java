package io.core.level;

import io.core.entity.Entity;
import io.core.entity.Player;

import java.util.*;

public class Level
{
    private static final NavigableMap<Integer, String> levelNames = new TreeMap<>() {{
        put(-3, "Ruins");
        put(-2, "Deep Cave");
        put(-1, "Cave");
        put(0, "Surface");
    }};

    private static final Level[] levels = new Level[4];

    private int width, height;  // size of the Level map
    private final int seed;     // seed that was used to generate this level
    private Level parentLevel; // reference to parent level

    private Set<Entity> entities = new HashSet<>();         // TODO : change to entity
    private Set<Player> players = new HashSet<>();          // TODO : change to player
    private Set<Entity> entitiesToAdd = new HashSet<>();    // TODO : change to entity
    private Set<Entity> entitiesToRemove = new HashSet<>(); // TODO : change to entity

    public Set<Entity> getEntities() {
        return entities;
    }

    private static int currentLevel = 0;

    public static Level getCurrentLevel() {
        int idx = currentLevel - levelNames.firstKey();

        if (idx < 0 || idx >= levels.length) {
            throw new IllegalStateException("Invalid level depth: " + currentLevel);
        }
        return levels[idx];
    }


    public static String getLevelName(int depth) {return levelNames.get(depth);}


    public Level(int width, int height, int seed, Level parentLevel) {
        this.width = width;
        this.height = height;
        this.seed = seed;
        this.parentLevel = parentLevel;
    }





}

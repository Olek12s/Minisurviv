package io.core.level;

import io.core.level.tile.Tile;

import java.util.Map;

public class MapManager
{
    public static final int CHUNK_SIZE = 8;

    public Map<Integer, Map<Integer, Chunk>> chunks;

    public Tile getTile(int x, int y) { // TODO: finish
        return null;
    }

    public void setTile(int x, int y, Tile tile) {  // TODO: finish

    }

    public void createMap(int seed, int width, int height, int level) { // TODO: finish

    }


    private static class Chunk {
        Chunk() {}
    }
}

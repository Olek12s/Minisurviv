package io.core.level;

import io.core.level.tile.TileData;

/*
Every level has its own MapManager. List of all levels is stored in LevelsManager ckass.
 */
public class Chunk
{
    public static final int CHUNK_SIZE = 8;
    public TileData[][] tileDats = new TileData[CHUNK_SIZE][CHUNK_SIZE];


}

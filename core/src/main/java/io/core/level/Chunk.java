package io.core.level;

import io.core.level.tile.TileData;
import io.core.level.tile.TileId;
import io.core.level.tile.tiles.FloorType;
import io.core.util.Noise;

/*
Every level has its own MapManager. List of all levels is stored in LevelsManager ckass.
 */
public class Chunk
{
    public static final int CHUNK_SIZE = 8;
    public TileData[][] tileDats = new TileData[CHUNK_SIZE][CHUNK_SIZE];
    private Noise noise;


    public void setNoise(Noise noise) {
        this.noise = noise;
    }

    public Noise getNoise() {

        return noise;
    }


    public void setTile(int x, int y, TileId tileId, FloorType floorType) {
        switch (floorType) {
            case FEATURE -> tileDats[x][y].setFeature(tileId);
            case GROUND_SHALLOW -> tileDats[x][y].setGround1(tileId);
            case GROUND_DEEP -> tileDats[x][y].setGround2(tileId);
        }
    }
}

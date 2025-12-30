package io.core.level;

import io.core.entity.Entity;
import io.core.entity.Player;
import io.core.level.tile.TileData;

import java.util.*;

public class Level
{
    public Chunk[][] chunks;

    private int width, height;  // size of the Level map
    private Level parentLevel; // reference to parent level

    private Set<Entity> entities = new HashSet<>();         // TODO : change to entity
    private Set<Player> players = new HashSet<>();          // TODO : change to player
    private Set<Entity> entitiesToAdd = new HashSet<>();    // TODO : change to entity
    private Set<Entity> entitiesToRemove = new HashSet<>(); // TODO : change to entity


    public Level(int width, int height, Level parentLevel) {
        this.chunks = new Chunk[width/Chunk.CHUNK_SIZE][height/Chunk.CHUNK_SIZE];
        this.width = width;
        this.height = height;
        this.parentLevel = parentLevel;
    }

    private int toMapX(int worldX) {
        return worldX + width / 2;
    }

    private int toMapY(int worldY) {
        return worldY + height / 2;
    }

    private boolean inBounds(int mapX, int mapY) {
        return mapX >= 0 && mapX < width && mapY >= 0 && mapY < height;
    }

    private int chunkX(int mapX) { return mapX / Chunk.CHUNK_SIZE; }
    private int tileX(int mapX)  { return mapX % Chunk.CHUNK_SIZE; }
    private int chunkY(int mapY) { return mapY / Chunk.CHUNK_SIZE; }
    private int tileY(int mapY)  { return mapY % Chunk.CHUNK_SIZE; }


    public Set<Entity> getEntities() {
        return entities;
    }


    public TileData getTileData(int x, int y) {
        int mapX = toMapX(x);
        int mapY = toMapY(y);

        if (!inBounds(mapX, mapY)) {
            return null;
        }

        Chunk chunk = chunks[chunkX(mapX)][chunkY(mapY)];
        if (chunk == null) return null;

        return chunk.tileDats[tileX(mapX)][tileY(mapY)];
    }


    public void setTileData(int x, int y, TileData tileData) {
        int mapX = toMapX(x);
        int mapY = toMapY(y);

        if (!inBounds(mapX, mapY)) {
            throw new IndexOutOfBoundsException(
                    "Tile outside map: " + x + "," + y
            );
        }

        Chunk chunk = chunks[chunkX(mapX)][chunkY(mapY)];
        if (chunk == null) {
            chunk = new Chunk();
            chunks[chunkX(mapX)][chunkY(mapY)] = chunk;
        }

        chunk.tileDats[tileX(mapX)][tileY(mapY)] = tileData;
    }



    /*
    Main render method for level. Here rendering of Level is managed.
     */
    public void render() {

        for (Entity e : entities) {
            e.render();
        }
    }

    public void renderTiles() {

    }

    public void renderEntities() {

    }

}

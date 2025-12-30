package io.core.level;

import com.badlogic.gdx.math.Rectangle;
import io.core.entity.Entity;
import io.core.entity.Player;
import io.core.level.tile.TileData;
import io.core.level.tile.Tiles;

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

    /**
     * Returns list of entities at given chunk found by world position on the current level
     * @param x - world X
     * @param y - world Y
     * @return
     */
    public List<Entity> getEntitiesInChunk(int x, int y) {
        int mapX = toMapX(x);
        int mapY = toMapY(y);

        int cx = chunkX(mapX);
        int cy = chunkY(mapY);

        int chunkLeft   = cx * Chunk.CHUNK_SIZE - width / 2;
        int chunkTop    = cy * Chunk.CHUNK_SIZE - height / 2;
        int chunkRight  = chunkLeft + Chunk.CHUNK_SIZE;
        int chunkBottom = chunkTop + Chunk.CHUNK_SIZE;

        return getEntitiesInBox(chunkLeft, chunkTop, chunkRight, chunkBottom);
    }



    /**
     * Get entities in a certain area on the current level
     * @param x0 - Left
     * @param y0 - Top
     * @param x1 - Right
     * @param y1 - Bottom
     * @return - List of entities whose hitboxes are intersecting box given in params
     */
    public List<Entity> getEntitiesInBox(int x0, int y0, int x1, int y1) {
        List<Entity> result = new ArrayList<>();

        int left = Math.min(x0, x1);
        int right = Math.max(x0, x1);
        int top = Math.min(y0, y1);
        int bottom = Math.max(y0, y1);

        Rectangle box = new Rectangle(left, top, right - left, bottom - top);

        for (Entity e : entities) {
            if (e.getHitbox().overlaps(box)) {
                result.add(e);
            }
        }

        return result;
    }




    /**
    Main render method for level. Here rendering of Level is managed.
     */
    public void render() {
        renderTiles();
        renderEntities();
    }

    /**
     * Renders current level's tiles in stack order defined inside tileData.
     * Tiles out of screen are also drawn
     */
    @Deprecated
    public void renderTiles() {
        for (int i = 0; i < chunks[0].length; i++) {
            for (int j = 0; j < chunks.length; j++) {
                for (TileData[] tileDataX : chunks[i][j].tileDats) {
                    for (TileData tileData : tileDataX) {
                        tileData.render();
                    }
                }
            }
        }
    }

    /**
     * Level's entities before rendering are being sorted by Y coordinate.
     * Entities, with higher Y (lower on the screen) are drawn first.
     * Only entities within given box are rendered
     * @param xb0 - Left
     * @param yb0 - Top
     * @param xb1 - Right
     * @param yb1 - Bottom
     */
    public void renderEntities(int xb0, int yb0, int xb1, int yb1) {
        List<Entity> entityList = getEntitiesInBox(xb0, yb0, xb1, yb1);
        entityList.sort((e1, e2) -> Float.compare(e2.getHitbox().y, e1.getHitbox().y));

        for (Entity e : entityList) {
            e.render();
        }
    }

    /**
     * Level's entities before rendering are being sorted by Y coordinate.
     * Entities, with higher Y (lower on the screen) are drawn first.
     * Entities out of screen are also drawn
     */
    @Deprecated
    public void renderEntities() {
        List<Entity> entityList = new ArrayList<>(entities);
        entityList.sort((e1, e2) -> Float.compare(e2.getHitbox().y, e1.getHitbox().y));

        for (Entity e : entityList) {
            e.render();
        }
    }
}

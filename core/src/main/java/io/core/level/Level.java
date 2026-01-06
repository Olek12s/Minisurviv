package io.core.level;

import com.badlogic.gdx.math.Rectangle;
import io.core.entity.mob.Entity;
import io.core.entity.mob.Player;
import io.core.level.tile.TileData;
import io.core.util.Box;
import io.core.util.FloatConsumer;

import java.util.*;

public class Level
{
    public Chunk[][] chunks;

    protected int width, height;  // size of the Level map
    protected int level;
    protected Level parentLevel; // reference to parent level
    protected int seed;

    public Set<Entity> entities = new HashSet<>();
    public Set<Player> players = new HashSet<>();
    private Set<Entity> entitiesToAdd = new HashSet<>();
    private Set<Entity> entitiesToRemove = new HashSet<>();

    public Player getFirstPlayer() {
        if (players.isEmpty()) return null;
        return players.iterator().next();
    }

    public int getLevelNumber() {return level;}

    public Level(int width, int height, Level parentLevel, int level, int seed) {
        this.chunks = new Chunk[width/Chunk.CHUNK_SIZE][height/Chunk.CHUNK_SIZE];
        this.width = width;
        this.height = height;
        this.parentLevel = parentLevel;
        this.level = level;
        this.seed = seed;
    }

    public void generate(FloatConsumer progress) {
        LevelGenerator.generateMapLevel(this, progress);
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
     * @param includePlayers - should include players into the list
     * @return
     */
    public List<Entity> getEntitiesInChunk(int x, int y, boolean includePlayers) {
        int mapX = toMapX(x);
        int mapY = toMapY(y);

        int cx = chunkX(mapX);
        int cy = chunkY(mapY);

        int chunkLeft   = cx * Chunk.CHUNK_SIZE - width / 2;
        int chunkTop    = cy * Chunk.CHUNK_SIZE - height / 2;
        int chunkRight  = chunkLeft + Chunk.CHUNK_SIZE;
        int chunkBottom = chunkTop + Chunk.CHUNK_SIZE;

        return getEntitiesInBox(chunkLeft, chunkTop, chunkRight, chunkBottom, includePlayers);
    }



    /**
     * Get entities in a certain area on the current level
     * @param x0 - Left
     * @param y0 - Top
     * @param x1 - Right
     * @param y1 - Bottom
     * @param includePlayers - should include players into the list
     * @return - List of entities whose hitboxes are intersecting box given in params
     */
    public List<Entity> getEntitiesInBox(int x0, int y0, int x1, int y1, boolean includePlayers) {
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
        if (includePlayers) {
            for (Entity p : players) {
                if (p.getHitbox().overlaps(box)) {
                    result.add(p);
                }
            }
        }

        return result;
    }

    public boolean intersectsWithAnyCollidableInBox(Rectangle rect, Entity ignore) {
        return intersectsWithAnyCollidableInBox(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, ignore);
    }

    public boolean intersectsWithAnyCollidableInBox(float x0, float y0, float x1, float y1, Entity ignore) {
        int mapLeft   = toMapX((int)Math.floor(x0));
        int mapRight  = toMapX((int)Math.ceil (x1));
        int mapTop    = toMapY((int)Math.floor(y0));
        int mapBottom = toMapY((int)Math.ceil (y1));

        int chunkX0 = chunkX(mapLeft);
        int chunkX1 = chunkX(mapRight);
        int chunkY0 = chunkY(mapTop);
        int chunkY1 = chunkY(mapBottom);

        // check if any collidable tile intersects with box
        for (int cx = chunkX0; cx <= chunkX1; cx++) {
            for (int cy = chunkY0; cy <= chunkY1; cy++) {

                if (cx < 0 || cx >= chunks.length) continue;
                if (cy < 0 || cy >= chunks[cx].length) continue;

                Chunk chunk = chunks[cx][cy];
                if (chunk == null) continue;

                int tileLeft   = (int)Math.floor(x0);
                int tileRight  = (int)Math.floor(x1 - 1e-6f);
                int tileTop    = (int)Math.floor(y0);
                int tileBottom = (int)Math.floor(y1 - 1e-6f);

                for (int tx = tileLeft; tx <= tileRight; tx++) {
                    for (int ty = tileTop; ty <= tileBottom; ty++) {
                        TileData t = getTileData(tx, ty);
                        if (t != null && t.isSolid()) {
                            return true;
                        }
                    }
                }

            }
        }

        // check if any entity's hitbox at the current map intersects with box
        for (Entity e : entities) {
            if (e == ignore) continue;
            Rectangle hb = e.getHitbox();

            if (Box.overlaps(
                    hb.x, hb.y, hb.x + hb.width, hb.y + hb.height,
                    x0, y0, x1, y1
            )) {
                return true;
            }

        }

        return false;
    }

    public void tick() {

        for (Player p : players) {
            p.tick(this);
        }

        for (Entity e : entities) {
            e.tick(this);
        }
    }


    /**
    Main render method for level. Here rendering of Level is managed.
     */
    public void render(int xb0, int yb0, int xb1, int yb1) {
        renderTiles(xb0, yb0, xb1, yb1);
        renderEntities(xb0, yb0, xb1, yb1);
    }

    /**
     * Debug method using shaperenderer
     */
    public void renderShapes(int xb0, int yb0, int xb1, int yb1) {
        renderEntityHitboxes(xb0, yb0, xb1, yb1);
    }

    public void renderEntityHitboxes(int xb0, int yb0, int xb1, int yb1) {
        List<Entity> en = getEntitiesInBox(xb0, yb0, xb1, yb1, true);
        for (Entity e : en) {
            e.renderShape();
        }
    }


    /**
     * Renders current level's tiles in stack order defined inside tileData.
     * Tiles out of screen are also drawn
     */
    @Deprecated
    public void renderTiles() {
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; j < chunks[0].length; j++) {
                for (TileData[] tileDataX : chunks[i][j].tileDats) {
                    for (TileData tileData : tileDataX) {
                        tileData.render();
                    }
                }
            }
        }
    }

    /**
     * Renders current level's tiles in stack order defined inside tileData.
     * Only entities within given box are rendered
     * @param xb0 - Left
     * @param yb0 - Top
     * @param xb1 - Right
     * @param yb1 - Bottom
     */
    public void renderTiles(int xb0, int yb0, int xb1, int yb1) {

        int mapLeft   = toMapX(xb0);
        int mapRight  = toMapX(xb1);
        int mapTop    = toMapY(yb0);
        int mapBottom = toMapY(yb1);


        int chunkX0 = chunkX(mapLeft);
        int chunkX1 = chunkX(mapRight);
        int chunkY0 = chunkY(mapTop);
        int chunkY1 = chunkY(mapBottom);

        for (int cx = chunkX0; cx <= chunkX1; cx++) {
            for (int cy = chunkY0; cy <= chunkY1; cy++) {
                if (cx < 0 || cx >= chunks.length) continue;                    // Don't render tiles if out ouf map
                if (cy < 0 || cy >= chunks[cx].length) continue;                // Don't render tiles if out ouf map
                Chunk chunk = chunks[cx][cy];

                for (int tx = 0; tx < Chunk.CHUNK_SIZE; tx++) {
                    for (int ty = 0; ty < Chunk.CHUNK_SIZE; ty++) {
                        TileData tileData = chunk.tileDats[tx][ty];

                        int worldX = cx * Chunk.CHUNK_SIZE + tx - width / 2;
                        int worldY = cy * Chunk.CHUNK_SIZE + ty - height / 2;

                        if (worldX >= xb0 && worldX <= xb1 &&
                                worldY >= yb0 && worldY <= yb1) {
                            tileData.render();
                        }
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
        List<Entity> entityList = getEntitiesInBox(xb0, yb0, xb1, yb1, true);
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
        entityList.add((Entity) players);
        entityList.sort((e1, e2) -> Float.compare(e2.getHitbox().y, e1.getHitbox().y));

        for (Entity e : entityList) {
            e.render();
        }
    }

    public void addEntity(Entity entity, int x, int y) {
        entity.setLevel(this);
        entity.setX(x);
        entity.setY(y);

        if (entity instanceof Player p) {
            players.add(p);
        } else {
            entities.add(entity);
        }
    }

    @Override
    public String toString() {
        return "level=" + LevelsManager.getLevelName(level);
    }
}

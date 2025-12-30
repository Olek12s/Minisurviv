package io.core.level.tile;


import io.core.level.tile.tiles.ground.*;
import io.core.level.tile.tiles.features.*;

import java.util.EnumMap;
import java.util.Map;

/*
This one is tricky. Tile placing system has few rules:
1) First layer of every tile is undestructable bedrock tile. No exception. It is achieved by replacing destroyed ground tile with bedrock.
2) There are 2 ground layers. They can be stacked ONLY like that:
*****   BEDROCK - NON_SOLID - NON_SOLID
*****   BEDROCK - NON_SOLID - SOLID
3) Feature layer can be placed on bedrock or non-solid layer
4) Liquids such as water or lava cannot be placed above solid ground layers
5) Liquids cannot be stacked
 */
public class Tiles
{
    private static final Map<TileId, Tile> REGISTRY = new EnumMap<>(TileId.class);

    static {
        // FOUNDATION LAYER - THERE'S NOTHING DEEPER THAN BEDROCK
        register(new BedrockTile(TileId.BEDROCK));

        // GROUND LAYER - there are 2 such layers in 1 tile
        register(new DirtTile(TileId.DIRT));
        register(new GrassTile(TileId.GRASS));
        register(new SandTile(TileId.SAND));
        register(new ClayTile(TileId.CLAY));
        register(new WaterTile(TileId.WATER));
        register(new StoneTile(TileId.STONE));



        // FEATURES
        register(new DantelionTile(TileId.DANTELION));
        register(new RoseTile(TileId.ROSE));
        register(new SnowTile(TileId.SNOW));
        register(new CactusTile(TileId.CACTUS));
        register(new TreeTile(TileId.TREE));
    }

    private static void register(Tile tile) {
        REGISTRY.put(tile.id, tile);
    }


    public static Tile getTileFromID(TileId id) {
        Tile tile = REGISTRY.get(id);
        if (tile == null) {
            throw new IllegalArgumentException("Unknown TileId: " + id);
        }
        return tile;
    }
}

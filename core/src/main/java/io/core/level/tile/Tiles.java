package io.core.level.tile;


import io.core.level.tile.tiles.ground.*;
import io.core.level.tile.tiles.features.*;

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
    // FOUNDATION LAYER - THERE'S NOTHING DEEPER THAN BEDROCK
    public static final Tile BEDROCK = new BedrockTile(TileId.BEDROCK);

    // GROUND LAYER - there are 2 such layers in 1 tile
    public static final Tile DIRT   = new DirtTile(TileId.DIRT);
    public static final Tile GRASS  = new GrassTile(TileId.GRASS);
    public static final Tile SAND   = new SandTile(TileId.SAND);
    public static final Tile CLAY   = new ClayTile(TileId.CLAY);
    public static final Tile WATER  = new WaterTile(TileId.WATER);
    public static final Tile STONE  = new StoneTile(TileId.STONE);


    // FEATURES
    public static final Tile DANTELION = new DantelionTile(TileId.DANTELION);
    public static final Tile ROSE      = new RoseTile(TileId.ROSE);
    public static final Tile SNOW      = new SnowTile(TileId.SNOW);
    public static final Tile CACTUS    = new CactusTile(TileId.CACTUS);
    public static final Tile TREE      = new TreeTile(TileId.TREE);


}

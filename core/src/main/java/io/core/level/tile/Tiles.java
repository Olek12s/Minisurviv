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
    public static Tile BEDROCK = new BedrockTile("BEDROCK");

    // GROUND LAYER - there are 2 such layers in 1 tile
    public static Tile DIRT = new DirtTile("DIRT");
    public static Tile GRASS = new GrassTile("GRASS");
    public static Tile SAND = new SandTile("SAND");
    public static Tile CLAY = new ClayTile("CLAY");
    public static Tile WATER = new WaterTile("WATER");
    public static Tile STONE = new StoneTile("STONE");


    // FEATURE TILES - can connect to all other layers
    public static Tile DANTELION = new DantelionTile("DANTELION");
    public static Tile ROSE = new RoseTile("ROSE");
    public static Tile SNOW = new SnowTile("SNOW");
    public static Tile CACTUS = new CactusTile("CACTUS");
    public static Tile TREE = new TreeTile("TREE");


}

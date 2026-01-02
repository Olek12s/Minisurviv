package io.core.level.biome.biomes;

import io.core.level.Chunk;
import io.core.level.biome.Biome;
import io.core.level.tile.TileId;
import io.core.level.tile.tiles.FloorType;

public class WinterBiome extends Biome
{
    public WinterBiome() {
        super(-0.67f, 0.0f, -0.6f, 1.f);
    }

    @Override
    public void generate(Chunk map, int x, int y) {
        map.setTile(x, y, TileId.DIRT, FloorType.GROUND_DEEP);
        map.setTile(x, y, TileId.SNOW, FloorType.GROUND_SHALLOW);
    }

//    @Override
//    public float getGenerationWeight(Noise noise, int x, int y) {
//       return 0f;
//    }
}

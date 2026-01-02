package io.core.level.biome.biomes;

import io.core.level.Chunk;
import io.core.level.biome.Biome;
import io.core.level.tile.TileId;
import io.core.level.tile.tiles.FloorType;
import io.core.util.Noise;

public class OceanBiome extends Biome
{
    public OceanBiome() {
        super(0, -0.6f, 0.2f, 1.f);
    }

    @Override
    public void generate(Chunk map, int x, int y) {
        map.setTile(x, y, TileId.CLAY, FloorType.GROUND_DEEP);
        map.setTile(x, y, TileId.WATER, FloorType.GROUND_SHALLOW);
    }

    @Override
    public float getGenerationWeight(Noise noise, int x, int y) {
        return (float)Math.pow(-noise.getHeight(x, y)*4, 5);
    }
}

package io.core.level.biome.biomes;

import io.core.level.Chunk;
import io.core.level.biome.Biome;
import io.core.level.tile.TileId;
import io.core.level.tile.tiles.FloorType;
import io.core.util.Noise;

public class RiverBiome extends Biome
{
    public RiverBiome() {
        super(0, -0.1f, 0.1f, 0.2f);
    }

    @Override
    public void generate(Chunk map, int x, int y) {
        map.setTile(x, y, TileId.SAND, FloorType.GROUND_DEEP);
        map.setTile(x, y, TileId.WATER, FloorType.GROUND_SHALLOW);
    }

    @Override
    public float getGenerationWeight(Noise noise, int x, int y) {
        int l = noise.getLayers();
        return Math.min(0.5f / (float) Math.abs(noise.getScale128Noise(x, y, l - 1) - noise.getScale128Noise(x, y, l - 2)), 10.f);
    }
}

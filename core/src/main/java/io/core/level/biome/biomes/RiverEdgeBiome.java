package io.core.level.biome.biomes;

import io.core.level.Chunk;
import io.core.level.biome.Biome;
import io.core.util.Noise;

public class RiverEdgeBiome extends Biome
{
    public RiverEdgeBiome() {
        super(0, -0.1f, 0.1f, 0.3f);
    }

    @Override
    public void generate(Chunk map, int x, int y) {

    }

    @Override
    public float getGenerationWeight(Noise noise, int x, int y) {
        int l = noise.getLayers();
        return Math.min(0.55f / (float) Math.abs(noise.getScale128Noise(x, y, l - 1) - noise.getScale128Noise(x, y, l - 2)), 3.0f);
    }
}

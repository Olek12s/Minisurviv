package io.core.level.biome.biomes;

import io.core.level.MapManager;
import io.core.level.biome.Biome;
import io.core.util.Noise;

public class OceanBiome extends Biome
{
    public OceanBiome() {
        super(0, -0.6f, 0.2f, 1.f);
    }

    @Override
    public void generate(MapManager map, int x, int y) {

    }

    @Override
    public float getGenerationWeight(Noise noise, int x, int y) {
        return (float)Math.pow(-noise.getHeight(x, y)*4, 5);
    }
}

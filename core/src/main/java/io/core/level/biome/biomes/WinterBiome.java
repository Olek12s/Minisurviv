package io.core.level.biome.biomes;

import io.core.level.MapManager;
import io.core.level.biome.Biome;
import io.core.util.Noise;

public class WinterBiome extends Biome
{
    public WinterBiome() {
        super(-0.67f, 0.0f, -0.6f, 1.f);
    }

    @Override
    public void generate(MapManager map, int x, int y) {

    }

//    @Override
//    public float getGenerationWeight(Noise noise, int x, int y) {
//       return 0f;
//    }
}

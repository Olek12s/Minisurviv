package io.core.level.biome.biomes;

import io.core.level.MapManager;
import io.core.level.biome.Biome;
import io.core.util.Noise;

public class RockyBiome extends Biome
{
    public RockyBiome() {
        super(0, 9.4f, 0, 1.f);
    }

    @Override
    public void generate(MapManager map, int x, int y) {
    }

    @Override
    public float getGenerationWeight(Noise noise, int x, int y) {

        return 2f;
    }



}

package io.core.level.biome.biomes;

import io.core.level.MapManager;
import io.core.level.biome.Biome;

public class ForestBiome extends Biome
{


    public ForestBiome() {
        super(-0.3f, 0.0f, 0.3f);
    }

    @Override
    public void generate(MapManager map, int x, int y) {

    }

    @Override
    public int getHexARGBColor() {
        return 0xFF003000;
    }
}

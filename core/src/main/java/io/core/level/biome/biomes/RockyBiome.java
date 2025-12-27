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
        double val = Math.abs(noise.getScale64Noise(x, y, 0) - noise.getScale64Noise(x, y, 1));
        double mval = Math.abs(Math.abs(noise.getScale16Noise(x, y, 0) - noise.getScale16Noise(x, y, 1)) - noise.getScale16Noise(x, y, 2));

        // TODO: heuristic for this biome is not perfect, but it works for now...
        if(val > 0.75 && mval < 0.35) {
            return 9f;
        }
        else return Float.MIN_VALUE;
    }



}

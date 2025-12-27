package io.core.level.biome;

import io.core.level.biome.biomes.DesertBiome;
import io.core.level.biome.biomes.ForestBiome;
import io.core.level.biome.biomes.OceanBiome;
import io.core.level.biome.biomes.PlainsBiome;

import java.util.HashSet;
import java.util.Set;

public class Biomes
{
    public static final Biome DESERT = new DesertBiome();
    public static final Biome FOREST = new ForestBiome();
    public static final Biome OCEAN = new OceanBiome();
    public static final Biome PLAINS = new PlainsBiome();

    public static int getBiomeColor(Biome biome) {
        return biome.getHexARGBColor();
    }
}

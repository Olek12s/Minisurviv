package io.core.level.biome;

import io.core.level.biome.biomes.*;
import io.core.util.Noise;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Biomes
{
    public static final Biome DESERT = new DesertBiome();
    public static final Biome FOREST = new ForestBiome();
    public static final Biome OCEAN = new OceanBiome();
    public static final Biome PLAINS = new PlainsBiome();
    public static final Biome ROCKY = new RockyBiome();
    public static final Biome RIVER = new RiverBiome();
    public static final Biome RIVER_EDGE = new RiverEdgeBiome();
    public static final Biome WINTER = new WinterBiome();

    public static HashMap<Biome, Integer> biomesARGB = new HashMap<>() {{
        put(DESERT, 0xFFFFFF64);
        put(FOREST, 0xFF007200);
        put(OCEAN, 0xFF0033FF);
        put(PLAINS, 0xFF00A900);
        put(ROCKY, 0xFFB4B4B4);
        put(RIVER, 0xFF0033CC);
        put(RIVER_EDGE, 0xFFEEEE00);
        put(WINTER, 0xFFEEEEEE);
    }};

    public static Set<Biome> getAllBiomes() {
        return biomesARGB.keySet();
    }

    public static BiomeId getBiomeId(Biome biome) {
        if (biome == DESERT) return BiomeId.DESERT;
        if (biome == FOREST) return BiomeId.FOREST;
        if (biome == OCEAN) return BiomeId.OCEAN;
        if (biome == PLAINS) return BiomeId.PLAINS;
        if (biome == ROCKY) return BiomeId.ROCKY;
        if (biome == RIVER) return BiomeId.RIVER;
        if (biome == RIVER_EDGE) return BiomeId.RIVER_EDGE;
        if (biome == WINTER) return BiomeId.WINTER;

        throw new IllegalArgumentException("Unknown biome: " + biome);
    }


    public static int getBiomeColor(Biome biome) {
        return biomesARGB.get(biome);
    }


    /**
     * Selects the best matching biome for given world coordinates
     * based on temperature, height and humidity noise values.
     * @param noise source of procedural world data
     * @param x world x-coordinate
     * @param y world y-coordinate
     * @return
     */
    public static Biome matchBiome(Noise noise, int x, int y) {
        Biome closest = null;
        //float maxWeight = -Float.MAX_VALUE;
        float maxWeight = Float.NEGATIVE_INFINITY;
        for (Biome biome : biomesARGB.keySet()) {
            float weight = biome.getGenerationWeight(noise, x, y);
            if (weight > maxWeight) {
                maxWeight = weight;
                closest = biome;
            }
        }
        return closest;
    }


}

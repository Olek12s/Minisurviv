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

    private static HashMap<Biome, Integer> biomesARGB = new HashMap<>() {{
        put(DESERT, 0xFFFFFF64);
        put(FOREST, 0xFF007200);
        put(OCEAN, 0xFF0088FF);
        put(PLAINS, 0xFF00A900);
        put(ROCKY, 0xFFB4B4B4);
        put(RIVER, 0xFF0088BB);
        put(RIVER_EDGE, 0xEEEEEE32);
    }};

    public static Set<Biome> getAllBiomes() {
        return biomesARGB.keySet();
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

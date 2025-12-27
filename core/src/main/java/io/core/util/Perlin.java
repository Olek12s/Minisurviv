package io.core.util;

import java.util.Random;

public class Perlin {

    private final int seed;
    private final int octaves;
    private final double persistence;
    private final double lacunarity;

    private static final int PERM_SIZE = 256;   // Standard table size by Ken Perlin
    private final int[] perm = new int[PERM_SIZE * 2];

    public Perlin(int seed, int octaves, double persistence, double lacunarity) {
        this.seed = seed;
        this.octaves = octaves;
        this.persistence = persistence;
        this.lacunarity = lacunarity;

        initPermutation();
    }

    private void initPermutation() {
        int[] p = new int[PERM_SIZE];
        for (int i = 0; i < PERM_SIZE; i++) {
            p[i] = i;
        }

        Random random = new Random(seed);
        for (int i = PERM_SIZE - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = p[i];
            p[i] = p[j];
            p[j] = tmp;
        }

        for (int i = 0; i < PERM_SIZE * 2; i++) {
            perm[i] = p[i % PERM_SIZE];
        }
    }


    /**
     * 1 dimentional noise
     * @param x X axis
     * @return
     */
    public double noise1D(double x) {
        return fbm(x, 0, 0);
    }

    /**
     * 2 dimentional noise
     * @param x X axis
     * @param y Y axis
     * @return
     */
    public double noise2D(double x, double y) {
        return fbm(x, y, 0);
    }

    /**
     * 3 dimentional noise
     * @param x X axis
     * @param y Y axis
     * @param z Z axis
     * @return
     */
    public double noise3D(double x, double y, double z) {
        return fbm(x, y, z);
    }


    /**
     * Fractal brownian motion (FBM) for noise roughness
     * @param x - x axis
     * @param y - y axis
     * @param z - z axis
     * @return Value in range [-1, 1]
     */
    private double fbm(double x, double y, double z) {
        double value = 0.0;
        double amplitude = 1.0;
        double frequency = 1.0;
        double max = 0.0;

        for (int i = 0; i < octaves; i++) {
            value += singleNoise(
                    x * frequency,
                    y * frequency,
                    z * frequency
            ) * amplitude;

            max += amplitude;
            amplitude *= persistence;
            frequency *= lacunarity;
        }
        return (value / max);
    }

    private double singleNoise(double x, double y, double z) {
        int X = fastFloor(x) & 255;
        int Y = fastFloor(y) & 255;
        int Z = fastFloor(z) & 255;

        x -= fastFloor(x);
        y -= fastFloor(y);
        z -= fastFloor(z);

        double u = fade(x);
        double v = fade(y);
        double w = fade(z);

        int aaa = perm[perm[perm[X] + Y] + Z];
        int aba = perm[perm[perm[X] + Y + 1] + Z];
        int aab = perm[perm[perm[X] + Y] + Z + 1];
        int abb = perm[perm[perm[X] + Y + 1] + Z + 1];
        int baa = perm[perm[perm[X + 1] + Y] + Z];
        int bba = perm[perm[perm[X + 1] + Y + 1] + Z];
        int bab = perm[perm[perm[X + 1] + Y] + Z + 1];
        int bbb = perm[perm[perm[X + 1] + Y + 1] + Z + 1];

        return lerp(w,
                lerp(v,
                        lerp(u, grad(aaa, x, y, z), grad(baa, x - 1, y, z)),
                        lerp(u, grad(aba, x, y - 1, z), grad(bba, x - 1, y - 1, z))
                ),
                lerp(v,
                        lerp(u, grad(aab, x, y, z - 1), grad(bab, x - 1, y, z - 1)),
                        lerp(u, grad(abb, x, y - 1, z - 1), grad(bbb, x - 1, y - 1, z - 1))
                )
        );
    }



    private static int fastFloor(double x) {
        return x >= 0 ? (int) x : (int) x - 1;
    }

    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private static double grad(int hash, double x, double y, double z) {
        int h = hash & 15;
        double u = h < 8 ? x : y;
        double v = h < 4 ? y : (h == 12 || h == 14 ? x : z);
        return ((h & 1) == 0 ? u : -u) +
                ((h & 2) == 0 ? v : -v);
    }
}

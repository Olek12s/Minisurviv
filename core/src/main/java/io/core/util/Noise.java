package io.core.util;

import io.core.util.Perlin;

public class Noise
{
    private static final int NOISE_LAYER_DIFF = 100;    // changing correlation between perlin layers

    private int width, height;  // should match chunk size in typical use case
    private int layers = 4;
    private Perlin perlin;

    // same noise generated at different scales
    private float[][][] noise1;
    private float[][][] noise2;
    private float[][][] noise4;
    private float[][][] noise8;
    private float[][][] noise16;
    private float[][][] noise32;
    private float[][][] noise64;
    private float[][][] noise128;
    private float[][][] noise256;
    private float[][][] noise512;
    private float[][][] noise1024;
    private float[][][] noise2048;
    private float[][][] noise4096;

    public Noise(int seed, int width, int height, int octaves, double persistence, double lacunarity, int xOffset, int yOffset, int zOffset) {
        this.width = width;
        this.height = height;
        this.perlin = new Perlin(seed, octaves, persistence, lacunarity);

        noise1 = new float[width][height][layers];
        noise2 = new float[width][height][layers];
        noise4 = new float[width][height][layers];
        noise8 = new float[width][height][layers];
        noise16 = new float[width][height][layers];
        noise32 = new float[width][height][layers];
        noise64 = new float[width][height][layers];
        noise128 = new float[width][height][layers];
        noise256 = new float[width][height][layers];
        noise512 = new float[width][height][layers];
        noise1024 = new float[width][height][layers];
        noise2048 = new float[width][height][layers];
        noise4096 = new float[width][height][layers];

        generateNoise(xOffset, yOffset, zOffset);
    }

    private void generateNoise(int xOffset, int yOffset, int zOffset) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int l = 0; l < layers; l++) {

                    double z = (zOffset + l) * NOISE_LAYER_DIFF;

                    noise1[x][y][l] = sample(x + xOffset, y + yOffset, z, 1);
                    noise2[x][y][l] = sample(x + xOffset, y + yOffset, z, 2);
                    noise4[x][y][l] = sample(x + xOffset, y + yOffset, z, 4);
                    noise8[x][y][l] = sample(x + xOffset, y + yOffset, z, 8);
                    noise16[x][y][l] = sample(x + xOffset, y + yOffset, z, 16);
                    noise32[x][y][l] = sample(x + xOffset, y + yOffset, z, 32);
                    noise64[x][y][l] = sample(x + xOffset, y + yOffset, z, 64);
                    noise128[x][y][l] = sample(x + xOffset, y + yOffset, z, 128);
                    noise256[x][y][l] = sample(x + xOffset, y + yOffset, z, 256);
                    noise512[x][y][l] = sample(x + xOffset, y + yOffset, z, 512);
                    noise1024[x][y][l] = sample(x + xOffset, y + yOffset, z, 1024);
                    noise2048[x][y][l] = sample(x + xOffset, y + yOffset, z, 2048);
                    noise4096[x][y][l] = sample(x + xOffset, y + yOffset, z, 4096);
                }
            }
        }
    }

    private float sample(int x, int y, double z, double scale) {
        return (float) perlin.noise3D(
                x / scale,
                y / scale,
                z
        );
    }

    private double octave(float[][][] noiseArray, int x, int y, double... weights) {
        double sum = 0;
        for (int l = 0; l < layers; l++) {
            sum += noiseArray[x][y][l] * weights[l];
        }
        return sum;
    }


    public double getTemperature(int x, int y) {
        return octave(noise512, x, y, 1,0.01, 0.02, 0.04, 0.08, 0.16, 0.32, 0.64, 0.05, 0.04, 0.01);
    }

    public double getHeight(int x, int y) {
        return octave(noise512, x, y,  2,0.005, 0.01, 0.02, 0.01, 0.02, 0.05, 0.1, 0.2, 0.7, 0.2);
    }

    public double getHumidity(int x, int y) {
        return octave(noise512, x, y, 3,0.02, 0.04, 0.07, 0.1, 0.4, 0.3, 0.1, 0.05, 0.02, 0.01);
    }
}

package io.core.level.biome;

import io.core.level.MapManager;
import io.core.util.Noise;

public abstract class Biome
{
    private float temperature;  // [-1, 1] Cold - Hot
    private float height;       // [-1, 1] Deep ocean - high mountains
    private float humidity;     // [-1, 1] extremely dry - extremely wet
    private float rarity;       // [-1, 1] very rare - very common

    public Biome(float temperature, float height, float humidity, float rarity) {
        this.temperature = temperature;
        this.height = height;
        this.humidity = humidity;
        this.rarity = rarity;
    }

    public Biome(float temperature, float height, float humidity) {
        this(temperature, height, humidity, 1.f);
    }

    public float getGenerationWeight(Noise noise, int tx, int ty) {
        float x = (float)noise.getTemperature(tx, ty) - temperature;
        float y = (float)noise.getHeight(tx, ty) - height;
        float z = (float)noise.getHumidity(tx, ty) - humidity;

        return - (x*x + y*y + z*z) + rarity;
    }

    public abstract void generate(MapManager map, int x, int y);
}

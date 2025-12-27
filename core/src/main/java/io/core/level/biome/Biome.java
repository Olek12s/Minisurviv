package io.core.level.biome;

import io.core.level.MapManager;

public abstract class Biome
{
    private float temperature;
    private float height;
    private float humidity;
    private float rarity;

    public Biome(float temperature, float height, float humidity, float rarity) {
        this.temperature = temperature;
        this.height = height;
        this.humidity = humidity;
        this.rarity = rarity;
    }

    public Biome(float temperature, float height, float humidity) {
        this(temperature, height, humidity, 1.f);
    }

    public abstract void generate(MapManager map, int x, int y);
    public abstract int getHexARGBColor();
}

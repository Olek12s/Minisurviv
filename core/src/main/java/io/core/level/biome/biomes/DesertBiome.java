package io.core.level.biome.biomes;

import io.core.level.Chunk;
import io.core.level.Level;
import io.core.level.LevelsManager;
import io.core.level.biome.Biome;
import io.core.level.biome.Biomes;
import io.core.level.tile.TileData;
import io.core.level.tile.TileId;
import io.core.level.tile.tiles.FloorType;
import io.core.level.tile.tiles.features.CactusTile;
import io.core.util.Noise;

import java.util.Random;

public class DesertBiome extends Biome
{

    public DesertBiome() {
        super(0.3f, 0.0f, -0.6f, 1.f);
    }

    @Override
    public void generate(Chunk map, int x, int y) {
        Noise noise = map.getNoise();
        double val = Math.abs(noise.getScale64Noise(x, y, 0) - noise.getScale64Noise(x, y, 1));
        double mval = Math.abs(Math.abs(noise.getScale16Noise(x, y, 0) - noise.getScale16Noise(x, y, 1)) - noise.getScale16Noise(x, y, 2));

        map.setTile(x, y, TileId.DIRT, FloorType.GROUND_DEEP);
        if (val > 0.5 && mval < 0.5) {
            map.setTile(x, y, TileId.STONE, FloorType.GROUND_SHALLOW);
        }
        else {
            map.setTile(x, y, TileId.SAND, FloorType.GROUND_SHALLOW);
        }
        if(noise.getScale8Noise(x, y, 0) < -0.25 && noise.getTileNoise(x, y, 1) < -0.4) {
            map.setTile(x, y, TileId.CACTUS, FloorType.FEATURE);
        }
    }
}

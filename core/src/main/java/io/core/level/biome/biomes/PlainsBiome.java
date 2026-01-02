package io.core.level.biome.biomes;

import io.core.level.Chunk;
import io.core.level.biome.Biome;
import io.core.level.tile.TileId;
import io.core.level.tile.tiles.FloorType;
import io.core.util.Noise;

import java.util.Random;

public class PlainsBiome extends Biome
{
    public PlainsBiome() {
        super(-0.25f, 0, 0.3f, 1.f);
    }

    @Override
    public void generate(Chunk map, int x, int y) {
        Noise noise = map.getNoise();

        double val = Math.abs(noise.getScale64Noise(x, y, 0) - noise.getScale64Noise(x, y, 1));
        double mval = Math.abs(Math.abs(noise.getScale16Noise(x, y, 0) - noise.getScale16Noise(x, y, 1)) - noise.getScale16Noise(x, y, 2));


        map.setTile(x, y, TileId.DIRT, FloorType.GROUND_DEEP);
        if (val > 0.75 && mval < 0.35) {
            map.setTile(x, y, TileId.STONE, FloorType.GROUND_SHALLOW);
        }
        else {
            map.setTile(x, y, TileId.GRASS, FloorType.GROUND_SHALLOW);

            if (noise.getScale16Noise(x, y, 2) < -0.6 && noise.getTileNoise(x, y, 0) < 0.4) {
                map.setTile(x, y, TileId.TREE, FloorType.FEATURE);
            }
            if (noise.getScale32Noise(x, y, 2) + noise.getScale8Noise(x, y, 0) < -0.6 && noise.getTileNoise(x, y, 1) < -0.4) {
                map.setTile(x, y, TileId.DANTELION, FloorType.FEATURE);
            }
        }
    }
}

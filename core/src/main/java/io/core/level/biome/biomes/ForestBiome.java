package io.core.level.biome.biomes;

import io.core.level.Chunk;
import io.core.level.biome.Biome;
import io.core.level.tile.TileId;
import io.core.level.tile.tiles.FloorType;
import io.core.util.Noise;

public class ForestBiome extends Biome
{


    public ForestBiome() {
        super(-0.1f, 0, 0.5f, 1.f);
    }

    @Override
    public void generate(Chunk map, int x, int y) {
        Noise noise = map.getNoise();

        map.setTile(x, y, TileId.DIRT, FloorType.GROUND_DEEP);
        map.setTile(x, y, TileId.GRASS, FloorType.GROUND_SHALLOW);
        if (noise.getTileNoise(x, y, 0) < 0.6) {
            map.setTile(x, y, TileId.TREE, FloorType.FEATURE);
        }

    }
}

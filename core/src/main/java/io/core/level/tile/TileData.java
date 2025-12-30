package io.core.level.tile;

import io.core.level.tile.tiles.ground.BedrockTile;
import io.core.level.tile.tiles.ground.WaterTile;

public class TileData
{
    public final int x;
    public final int y;

    private GroundTile ground1;
    private GroundTile ground2;
    private FeatureTile feature;

    public TileData(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * TileData is made of 3 layers stacked as:
     * -Ground1
     * -Ground2
     * -Feature
     *
     * Feature might exist if both ground1 and ground2 are nulls (bedrock is rendered)
     */
    public void render() {
        if (ground1 != null &&
                (ground2 == null || ground2 instanceof WaterTile)) {
            ground1.render(x, y);
        }

        if (ground2 != null &&
                (ground1 == null || ground1 instanceof BedrockTile)) {
            ground2.render(x, y);
        }

        if (feature != null) {
            feature.render(x, y);
        }
    }
}

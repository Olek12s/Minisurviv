package io.core.level.tile;

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

    }
}

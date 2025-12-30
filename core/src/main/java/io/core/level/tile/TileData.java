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
}

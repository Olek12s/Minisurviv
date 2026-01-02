package io.core.level.tile;

import io.core.level.biome.Biome;
import io.core.level.biome.Biomes;
import io.core.level.tile.tiles.ground.BedrockTile;
import io.core.level.tile.tiles.ground.WaterTile;

public class TileData
{
    public final int x;
    public final int y;

    private Biomes.TileBiome biome;     // TODO: tidy it
    private GroundTile ground1;
    private GroundTile ground2;
    private FeatureTile feature;


    /*
    public void setGround1(GroundTile ground1) {
        this.ground1 = ground1;
    }
    public void setGround2(GroundTile ground2) {
        this.ground2 = ground2;
    }
    public void setFeature(FeatureTile feature) {
        this.feature = feature;
    }
     */


    public void setGround1(TileId tileId) {
        Tile tile = Tiles.getTileFromID(tileId);

        if (!(tile instanceof GroundTile)) {
            throw new IllegalArgumentException(
                    "Tile " + tileId + " is not a GroundTile"
            );
        }

        if (tile instanceof WaterTile && ground2 instanceof WaterTile) {
            throw new IllegalArgumentException(
                    "Water tiles cannot be stacked!"
            );
        }

        this.ground1 = (GroundTile) tile;
    }


    public void setGround2(TileId tileId) {
        Tile tile = Tiles.getTileFromID(tileId);

        if (!(tile instanceof GroundTile)) {
            throw new IllegalArgumentException(
                    "Tile " + tileId + " is not a GroundTile"
            );
        }

        if (tile instanceof WaterTile && ground1 instanceof WaterTile) {
            throw new IllegalArgumentException(
                    "Water tiles cannot be stacked!"
            );
        }

        if (tile instanceof  WaterTile && ground1.isSolid()) {
            throw new IllegalArgumentException(
                    "Water tiles cannot be stacked above solid tiles!"
            );
        }

        this.ground2 = (GroundTile) tile;
    }

    public void setFeature(TileId tileId) {
        Tile tile = Tiles.getTileFromID(tileId);

        if (!(tile instanceof FeatureTile)) {
            throw new IllegalArgumentException(
                    "Tile " + tileId + " is not a FeatureTile"
            );
        }

        if (ground2 != null && ground2.isSolid() || ground2 == null && ground1.isSolid()) {
            throw new IllegalArgumentException(
                    "Feature tile " + tileId + " cannot be stacked above solid tile"
            );
        }

        this.feature = (FeatureTile) tile;
    }



    public TileData(Biomes.TileBiome biome, int x, int y) {
        this.biome = biome;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Biome: " + biome + " ");
        sb.append("Ground1: " + ground1 + " ");
        sb.append("Ground2: " + ground2 + " ");
        sb.append("Feature: " + feature + " ");

        return sb.toString();
    }
}

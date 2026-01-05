package io.core.level.tile;

import io.core.level.biome.BiomeId;
import io.core.level.tile.tiles.ground.BedrockTile;
import io.core.level.tile.tiles.ground.WaterTile;

public class TileData
{
    public final int x;
    public final int y;

    private BiomeId biome;
    private GroundTile groundDeep;
    private GroundTile groundShallow;
    private FeatureTile feature;

    public boolean isSolid() {
        return (feature != null && feature.isSolid())
                || (groundShallow != null && groundShallow.isSolid())
                || (groundDeep != null && groundDeep.isSolid());
    }


    public TileData(BiomeId biome, int x, int y) {
        this.biome = biome;
        this.x = x;
        this.y = y;
    }


    /*
    public void setGroundDeep(GroundTile groundDeep) {
        this.groundDeep = groundDeep;
    }
    public void setGroundShallow(GroundTile groundShallow) {
        this.groundShallow = groundShallow;
    }
    public void setFeature(FeatureTile feature) {
        this.feature = feature;
    }
     */


    public void setGroundDeep(TileId tileId) {
        Tile tile = Tiles.getTileFromID(tileId);

        if (!(tile instanceof GroundTile)) {
            throw new IllegalArgumentException(
                    "Tile " + tileId + " is not a GroundTile"
            );
        }

        if (tile instanceof WaterTile && groundShallow instanceof WaterTile) {
            throw new IllegalArgumentException(
                    "Water tiles cannot be stacked!"
            );
        }

        this.groundDeep = (GroundTile) tile;
    }


    public void setGroundShallow(TileId tileId) {
        Tile tile = Tiles.getTileFromID(tileId);

        if (!(tile instanceof GroundTile)) {
            throw new IllegalArgumentException(
                    "Tile " + tileId + " is not a GroundTile"
            );
        }

        if (tile instanceof WaterTile && groundDeep instanceof WaterTile) {
            throw new IllegalArgumentException(
                    "Water tiles cannot be stacked!"
            );
        }

        if (tile instanceof  WaterTile && groundDeep.isSolid()) {
            throw new IllegalArgumentException(
                    "Water tiles cannot be stacked above solid tiles!"
            );
        }

        this.groundShallow = (GroundTile) tile;
    }

    public void setFeature(TileId tileId) {
        Tile tile = Tiles.getTileFromID(tileId);

        if (!(tile instanceof FeatureTile)) {
            throw new IllegalArgumentException(
                    "Tile " + tileId + " is not a FeatureTile"
            );
        }

        if (groundShallow != null && groundShallow.isSolid() || groundShallow == null && groundDeep.isSolid()) {
            throw new IllegalArgumentException(
                    "Feature tile " + tileId + " cannot be stacked above solid tile"
            );
        }

        this.feature = (FeatureTile) tile;
    }



    /**
     * TileData is made of 3 layers stacked as:
     * -GroundShallow
     * -GroundDeep
     * -Feature
     *
     * Feature might exist if both groundDeep and groundShallow are nulls (bedrock is rendered)
     * Only visible parts are rendered for sake of performance
     */
    public void render() {
        if (groundDeep != null) groundDeep.render(x, y);
        if (groundShallow != null) groundShallow.render(x, y);
        if (feature != null) {
            feature.render(x, y);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Biome: " + biome + " ");
        sb.append("Ground1: " + groundDeep + " ");
        sb.append("Ground2: " + groundShallow + " ");
        sb.append("Feature: " + feature + " ");

        return sb.toString();
    }
}

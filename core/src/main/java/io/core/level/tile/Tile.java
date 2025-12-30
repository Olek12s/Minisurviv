package io.core.level.tile;

import io.core.core.Renderer;
import io.core.level.tile.tiles.features.TreeTile;

public abstract class Tile
{
    protected final TileId id;

    protected Tile(TileId id) {
        this.id = id;
    }

    public void render(int tileX, int tileY) {
        Renderer.renderTile(id.name(), tileX, tileY);
    }
}

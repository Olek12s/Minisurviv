package io.core.level.tile.tiles.ground;

import io.core.core.Renderer;
import io.core.level.tile.GroundTile;
import io.core.level.tile.TileId;

public class WaterTile extends GroundTile
{

    public WaterTile(TileId id) {
        super(id);
    }

    @Override
    public void render(int tileX, int tileY) {
        Renderer.spriteBatch.setColor(1f, 1f, 1f, 0.75f);    // 0.75 opacity for water tile
        Renderer.renderTile(id.name(), tileX, tileY);
        Renderer.spriteBatch.setColor(1f, 1f, 1f, 1f);
    }
}

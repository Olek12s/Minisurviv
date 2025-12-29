package io.core.level.tile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.core.core.Renderer;

public abstract class Tile
{
    protected final String name;

    protected Tile(String name) {
        this.name = name;
    }

    public void render(int tileX, int tileY) {
        Renderer.drawTile(name, tileX, tileY);
    }
}

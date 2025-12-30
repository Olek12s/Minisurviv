package io.core.level.tile;

import io.core.core.Renderer;

public abstract class Tile
{
    protected final String name;

    protected Tile(String name) {
        this.name = name;
    }

    public void render(int tileX, int tileY) {
        Renderer.renderTile(name, tileX, tileY);
    }
}

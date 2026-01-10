package io.core.entity.item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.core.core.Renderer;
import io.core.entity.Entity;
import io.core.level.tile.TileId;
import io.core.util.Direction;

import java.util.EnumMap;

public abstract class Item
{
    protected final ItemId id;

    public ItemId getId() {
        return id;
    }

    public Item(ItemId id)
    {
        this.id = id;
    }

    // coordinates should be given in HUD coordinate system
    public void renderOnHUD(float x, float y) {
        Renderer.renderItem(id.name(), x, y);
    }
}

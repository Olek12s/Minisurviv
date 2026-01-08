package io.core.entity.item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.core.core.Renderer;
import io.core.entity.Entity;
import io.core.level.tile.TileId;
import io.core.util.Direction;

import java.util.EnumMap;

public abstract class Item extends Entity
{
    protected final ItemId id;

    public Item(ItemId id)
    {
        this.id = id;
        collidabe = false;

        // default hitbox values for item
        this.hitboxWidth = 8f;
        this.hitboxHeight = 8f;
        this.hitboxOffsetX = (24f - hitboxWidth) /2;
        this.hitboxOffsetY = (24f - hitboxHeight) /2;
    }

    // coordinates should be given in tile coordinates system
    public void render(float x, float y) {
        Renderer.renderItem(id.name(), x, y);
    }
}

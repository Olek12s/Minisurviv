package io.core.entity;

import io.core.core.Renderer;
import io.core.entity.item.Item;

public class ItemEntity extends Entity
{
    protected Item item;    // reference to the item

    public ItemEntity(float x, float y, Item item) {
        this.x = x;
        this.y = y;
        this.item = item;

        collidabe = false;

        // default hitbox values for itemEntity
        this.hitboxWidth = 8f;
        this.hitboxHeight = 8f;
        this.hitboxOffsetX = (24f - hitboxWidth) /2;
        this.hitboxOffsetY = (24f - hitboxHeight) /2;
    }

    // coordinates should be given in Tile coordinate system
    @Override
    public void render() {
        Renderer.renderItem(item.getId().getItemName(), x, y);
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "item=" + item +
                '}';
    }
}

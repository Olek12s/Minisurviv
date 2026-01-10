package io.core.entity;

import io.core.entity.item.Item;

public class ItemEntity extends Entity
{
    protected Item item;    // reference to the item

    public ItemEntity(float x, float y, Item item) {
        this.x = x;
        this.y = y;
        this.item = item;
    }

    @Override
    public void render() {

    }
}

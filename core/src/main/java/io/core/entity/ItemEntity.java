package io.core.entity;

import com.badlogic.gdx.graphics.Color;
import io.core.core.Renderer;
import io.core.entity.item.Item;
import io.core.level.Level;

public class ItemEntity extends Entity
{
    protected Item item;    // reference to the item
    protected int ticksToBePickable = 40 + random.nextInt(41);  // [40, 80]

    public Item getItem() {return item;}
    public boolean canBePicked() {return ticksToBePickable == 0;}

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

    @Override
    public void tick(Level level) {

        if (ticksToBePickable != 0) ticksToBePickable--;
    }

    /**
     * Color of the shape depends on the state of {@link #ticksToBePickable}.
     * 0 - GREEN
     * BETWEEN - gradient
     * 80 - RED
     */
    @Override
    public void renderShape() {
        float r = ticksToBePickable / 80f;
        float g = 1 - (ticksToBePickable / 80f);
        Color color = new Color(r,g,0f,1f);
        Renderer.renderHitboxShape(hitbox, color);
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

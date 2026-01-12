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
    protected int maxStack = 1; // by default - item stacks up to 1.
    protected int amount = 1;   // default amount is 1

    public int getMaxStack() {return maxStack;}

    public int getAmount() {return amount;}
    public void setAmount(int count) {this.amount = count;}

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

    //TODO: This might be bad copy() method, perhaps abstract copy() would be better
    public Item copy() {
        try {
            Item copy = this.getClass().getDeclaredConstructor().newInstance();
            copy.setAmount(this.amount);
            return copy;
        } catch (Exception e) {
            throw new RuntimeException("Cannot copy item " + this, e);
        }
    }




    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", maxStack=" + maxStack +
                ", amount=" + amount +
                '}';
    }
}

package io.core.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import io.core.Tickable;
import io.core.core.Renderer;
import io.core.level.Level;

public abstract class Entity implements Tickable
{
    protected int x, y; // world coordinates
    protected Rectangle hitbox = new Rectangle();
    protected Level level; // level that entity is at

    // Hitbox config - needed during hitbox updates
    protected int hitboxOffsetX = 0;
    protected int hitboxOffsetY = 0;
    protected int hitboxWidth;
    protected int hitboxHeight;

    public void setX(int x)
    {
        this.x = x;
        updateHitbox();
    }
    public void setY(int y)
    {
        this.y = y;
        updateHitbox();
    }
    public int getX() {return x;}
    public int getY() {return y;}
    public Level getLevel() {return level;}
    public void setLevel(Level level) {this.level = level;}

    public Rectangle getHitbox() {return hitbox;}

    public abstract void render();



    /**
     * Moves the entity by vector(x, y).
     * @param x - X axis
     * @param y - Y axis
     * @return True if entity has moved, otherwise false
     */
    public boolean move(int x, int y) {



        return false;
    }

    protected void updateHitbox() {
        hitbox.set(
                x + hitboxOffsetX,
                y + hitboxOffsetY,
                hitboxWidth,
                hitboxHeight
        );
    }
}

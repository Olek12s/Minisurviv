package io.core.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import io.core.Tickable;
import io.core.core.Renderer;
import io.core.level.Level;

public abstract class Entity implements Tickable
{
    // world coordinates. Notice how they are float values - It's because entity can appear between "tile pixels"
    // Discrete values are tile coordinates.
    // USE SETTERS IF YOU WANT TO MODIFY THESE VALUES
    public float x, y;

    protected Rectangle hitbox = new Rectangle();
    protected Level level; // level that entity is at

    // Hitbox config - needed during hitbox updates. Values needs to be divided by / 24 (1 tile contains 24 pixels)
    // - which is already done in updateHitbox()
    protected float hitboxOffsetX = 0;
    protected float hitboxOffsetY = 0;
    protected float hitboxWidth;
    protected float hitboxHeight;

    public void setX(float x)
    {
        this.x = x;
        updateHitbox();
    }
    public void setY(float y)
    {
        this.y = y;
        updateHitbox();
    }
    public float getX() {return x;}
    public float getY() {return y;}
    public Level getLevel() {return level;}
    public void setLevel(Level level) {this.level = level;}

    public Rectangle getHitbox() {return hitbox;}

    public abstract void render();

    public void renderShape() {
        Renderer.renderHitboxShape(hitbox);
    }


    /**
     * Moves the entity by vector(x, y) distance. distance equals to 1 is relative to the length of 1 tile.
     * Moving at both directions at the same time results in diagonal movement, which is NOT rooted (this is a faster way of travel)
     *
     * Method works as:
     * New Rectangle (hitbox) is created at predicted position. If it doesn't collides with Level's collidable tiles or
     * other entity's hitboxes then entity is being moved to that position. At very high speeds entity can shoot through slim hitboxes.
     *
     * Otherwise, if there is a collision at the expected position - entity is moved by a fraction (not smaller than 1/32th of tile length)
     * of given X or Y until collision happens.
     * At first, movement by X axis is done, then by Y axis so entity can "slide" when moving diagonally.
     * There's a check if entity will collide with a new position, if not, entity is moved
     * @param x - X axis
     * @param y - Y axis
     * @return True if entity has moved, otherwise false
     */
    public boolean move(float x, float y) {



        return false;
    }

    /**
     * Moves the entity by x distance. distance equals to 1 is relative to the length of 1 tile.
     * @param x - X axis
     * @return True if entity has moved, otherwise false
     */
    private boolean moveX(float x) {

        return false;
    }

    /**
     * Moves the entity by y distance. distance equals to 1 is relative to the length of 1 tile.
     * @param y - Y axis
     * @return True if entity has moved, otherwise false
     */
    private boolean moveY(float y) {

        return false;
    }

    // div by /24 so hitbox is in proper world coordinate system
    protected void updateHitbox() {
        hitbox.set(
                x + (hitboxOffsetX / 24f),
                y + (hitboxOffsetX / 24f),
                (hitboxWidth) / 24f,
                (hitboxHeight) / 24f
        );
    }
}

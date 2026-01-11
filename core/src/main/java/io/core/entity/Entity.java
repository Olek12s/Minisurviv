package io.core.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import io.core.Tickable;
import io.core.core.Renderer;
import io.core.level.Level;
import io.core.level.LevelsManager;

import java.util.Random;

public abstract class Entity implements Tickable
{
    // world coordinates. Notice how they are float values - It's because entity can appear between "tile pixels"
    // Discrete values are tile coordinates.
    // USE SETTERS IF YOU WANT TO MODIFY THESE VALUES
    public float x, y;

    protected Random random = new Random();
    protected Rectangle hitbox = new Rectangle();
    protected Level level; // level that entity is at
    protected Inventory inventory = new Inventory();
    private boolean removed;    // should entity by removed on the next tick    // TODO: implement rmeoving



    // Hitbox config - needed during hitbox updates. Values needs to be divided by / 24 (1 tile contains 24 pixels)
    // - which is already done in updateHitbox()
    protected float hitboxOffsetX = 0;
    protected float hitboxOffsetY = 0;
    protected float hitboxWidth;
    protected float hitboxHeight;
    protected boolean collidabe;  // Entity items should not be collidable like mobs

    public boolean isCollidabe() {
        return collidabe;
    }

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
     * @param xd - X axis
     * @param yd - Y axis
     * @return True if entity has moved, otherwise false
     */
    public boolean move(float xd, float yd) {
        if (xd == 0 && yd == 0) return false;

        if (!collidesAt(x + xd, y + yd)) {
            setX(x + xd);
            setY(y + yd);
            return true;
        }

        boolean moved = false;

        if (xd != 0) {
            moved |= moveX(xd);
        }

        if (yd != 0) {
            moved |= moveY(yd);
        }

        return moved;
    }

    /**
     * Moves the entity by x distance. distance equals to 1 is relative to the length of 1 tile.
     * @param xd - X axis
     * @return True if entity has moved, otherwise false
     */
    private boolean moveX(float xd) {
        float step = (xd > 0 ? 1f : -1f) * (1f / 32f);
        float moved = 0f;

        while (Math.abs(moved + step) <= Math.abs(xd)) {
            float nextX = x + moved + step;

            if (collidesAt(nextX, y)) {
                break;
            }

            moved += step;
        }

        if (moved != 0f) {
            setX(x + moved);
            return true;
        }

        return false;
    }

    /**
     * Moves the entity by y distance. distance equals to 1 is relative to the length of 1 tile.
     * @param yd - Y axis
     * @return True if entity has moved, otherwise false
     */
    private boolean moveY(float yd) {
        float step = (yd > 0 ? 1f : -1f) * (1f / 32f);
        float moved = 0f;

        while (Math.abs(moved + step) <= Math.abs(yd)) {
            float nextY = y + moved + step;

            if (collidesAt(x, nextY)) {
                break;
            }

            moved += step;
        }

        if (moved != 0f) {
            setY(y + moved);
            return true;
        }

        return false;
    }

    /**
     * Method checking if entity at nx ny coordinates would collide with terrain or other entities
     * @param nx
     * @param ny
     * @return
     */
    private boolean collidesAt(float nx, float ny) {
        Rectangle hb = new Rectangle(
                nx + hitboxOffsetX / 24f,
                ny + hitboxOffsetY / 24f,
                hitboxWidth  / 24f,
                hitboxHeight / 24f
        );

        return level.intersectsWithAnyCollidableInBox(hb, this);
    }


    // div by /24 so hitbox is in proper world coordinate system
    protected void updateHitbox() {
        hitbox.set(
                x + (hitboxOffsetX / 24f),
                y + (hitboxOffsetY / 24f),
                (hitboxWidth) / 24f,
                (hitboxHeight) / 24f
        );
    }

    // should only be called by level class, eg. level.add(entity)
    public void setLevel(Level level, float x, float y) {
        if (level == null) {
            throw new RuntimeException("Tried to set level of entity " + this + " to a null level.");
        }

        this.level = level;
        this.removed = false;
        setX(x);
        setY(y);
    }
}

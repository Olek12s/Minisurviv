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
    protected Rectangle hitbox;
    protected Level level; // level that entity is at
    protected Animation<TextureRegion> sprites;

    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public int getX() {return x;}
    public int getY() {return y;}
    public Level getLevel() {return level;}

    public Rectangle getHitbox() {return hitbox;}

    public void render() {
        TextureRegion frame = sprites.getKeyFrame(0);

        Renderer.renderEntity(frame, x, y);
    }


    /**
     * Moves the entity by vector(x, y).
     * @param x - X axis
     * @param y - Y axis
     * @return True if entity has moved, otherwise false
     */
    public boolean move(int x, int y) {



        return false;
    }
}

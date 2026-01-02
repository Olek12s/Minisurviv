package io.core.entity;

import com.badlogic.gdx.math.Rectangle;
import io.core.Tickable;
import io.core.level.Level;

public abstract class Entity implements Tickable
{
    private Rectangle hitbox;
    protected Level level; // level that entity is at

    public Rectangle getHitbox() {return hitbox;}

    public void render() {

    }
}

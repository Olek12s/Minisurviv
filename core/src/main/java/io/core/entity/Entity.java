package io.core.entity;

import com.badlogic.gdx.math.Rectangle;
import io.core.level.Level;

public abstract class Entity
{
    private Rectangle hitbox;
    protected Level level; // level that entity is at

    public Rectangle getHitbox() {return hitbox;}

    public void render() {

    }
}

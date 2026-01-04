package io.core.entity;

import com.badlogic.gdx.math.Rectangle;
import io.core.level.Level;

public abstract class Mob extends Entity
{

    public Mob() {
        this.hitboxWidth = 16;
        this.hitboxHeight = 16;
    }

    public void tick(Level level) {

    }
}

package io.core.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectFloatMap;
import io.core.core.Input;
import io.core.level.Level;



public class Player extends Mob
{
    @Override
    public void tick(Level level) {
        Vector2 vec = new Vector2(0, 0);    // movement vector

        if (Input.isHeld(Input.Keys.W)) vec.y++;    // up
        if (Input.isHeld(Input.Keys.S)) vec.y--;    // down
        if (Input.isHeld(Input.Keys.A)) vec.x--;    // left
        if (Input.isHeld(Input.Keys.D)) vec.x++;    // right
    }
}

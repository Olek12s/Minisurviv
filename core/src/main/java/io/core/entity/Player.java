package io.core.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectFloatMap;
import io.core.core.Input;
import io.core.level.Level;
import io.core.level.LevelsManager;


public class Player extends Mob
{
    public Player() {
        // starting coordinates are set to 0
        int x = 0;
        int y = 0;
        LevelsManager.getCurrentLevel().addEntity(this, x, y);
        System.out.println("[Player] Initialized at x: " + x + ", " + "y: " + y + " At level: " + LevelsManager.getCurrentLevel());
    }




    @Override
    public void tick(Level level) {
        Vector2 vec = new Vector2(0, 0);    // movement vector

        if (Input.isHeld(Input.Keys.W)) vec.y++;    // up
        if (Input.isHeld(Input.Keys.S)) vec.y--;    // down
        if (Input.isHeld(Input.Keys.A)) vec.x--;    // left
        if (Input.isHeld(Input.Keys.D)) vec.x++;    // right

        // ...
        // ...
        // ...

        int speed = 1;
        // Move the player
        int xd = (int) (vec.x * speed);
        int yd = (int) (vec.y * speed);
        boolean moved = move(xd, yd);
    }
}

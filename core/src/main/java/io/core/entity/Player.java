package io.core.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectFloatMap;
import io.core.core.CameraController;
import io.core.core.Input;
import io.core.core.Renderer;
import io.core.level.Level;
import io.core.level.LevelsManager;
import io.core.util.Direction;


public class Player extends Mob
{

    public Player() {
        TextureRegion sheet = Renderer.getEntitiesTextureAtlas().findRegion("player");
        loadAnimations(sheet, 3, 4);

        // starting coordinates are set to 0
        int x = 0;
        int y = 0;
        LevelsManager.getCurrentLevel().addEntity(this, x, y);
        CameraController.snapTo(this);  // TODO: It's not really good place for snapping
        System.out.println("[Player] Initialized at x: " + x + ", " + "y: " + y + " At level: " + LevelsManager.getCurrentLevel());
    }




    @Override
    public void tick(Level level) {

        Vector2 vec = new Vector2(0, 0);    // movement vector
        float dt = Gdx.graphics.getDeltaTime();

        // up
        if (Input.isHeld(Input.Keys.W)) {
            vec.y++;
            facingDirection = Direction.UP;
        }
        // down
        if (Input.isHeld(Input.Keys.S)) {
            vec.y--;
            facingDirection = Direction.DOWN;
        }
        // left
        if (Input.isHeld(Input.Keys.A)) {
            vec.x--;
            facingDirection = Direction.LEFT;
        }
        // right
        if (Input.isHeld(Input.Keys.D)) {
            vec.x++;
            facingDirection = Direction.RIGHT;
        }

        move(vec.x * movSpeed, vec.y * movSpeed);

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

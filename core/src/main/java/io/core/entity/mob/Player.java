package io.core.entity.mob;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.core.core.CameraController;
import io.core.core.Input;
import io.core.core.Renderer;
import io.core.entity.Entity;
import io.core.entity.ItemEntity;
import io.core.entity.item.Item;
import io.core.level.Level;
import io.core.level.LevelsManager;

import java.util.List;


public class Player extends Mob
{
    private static final int MAX_HEALTH = 20;
    public static final int MAX_ENERGY = 20;
    private int energy;

    public int getEnergy() {return energy;}
    public void setEnergy(int energy) {this.energy = energy;}

    public Player() {
        super(MAX_HEALTH);
        TextureRegion sheet = Renderer.getEntitiesTextureAtlas().findRegion("player");
        loadAnimations(sheet, 3, 4);

        this.energy = MAX_ENERGY;

        // starting coordinates are set to 0
        int x = 0;
        int y = 0;
        LevelsManager.getCurrentLevel().addEntity(this, x, y);
        CameraController.snapTo(this);  // TODO: It's not really good place for snapping
        System.out.println("[Player] Initialized at x: " + x + ", " + "y: " + y + " At level: " + LevelsManager.getCurrentLevel());
    }




    @Override
    public void tick(Level level) {


        // ITEMS WITHIN HITBOX
        List<Entity> nearbyItems = level.getEntitiesInBox(
                hitbox.x, hitbox.y,
                hitbox.x + hitbox.width, hitbox.y + hitbox.height,
                false
        );

        // PICKING UP ITEMS WITHIN HITBOX IF ITEM'S COUNTDOWN IS 0
        for (Entity e : nearbyItems) {
            if (e instanceof ItemEntity itemEntity) {
                if (itemEntity.canBePicked()) {
                    Item item = itemEntity.getItem();

                    boolean added = inventory.addItemIfPossible(item);
                    if (added) {
                        level.entities.remove(itemEntity);
                    }
                }
            }
        }

        // PLAYER MOVEMENT

        Vector2 vec = new Vector2(0, 0);    // movement vector

        if (Input.isHeld(Input.Keys.W)) vec.y++;            // up
        if (Input.isHeld(Input.Keys.S)) vec.y--;            // down
        if (Input.isHeld(Input.Keys.A)) vec.x--;            // left
        if (Input.isHeld(Input.Keys.D)) vec.x++;            // right

        // Move the player
        float xd = vec.x * movSpeed;
        float yd = vec.y * movSpeed;
        boolean moved = move(xd, yd, true); // Player's moved in this method

        super.tick(level);
    }
}

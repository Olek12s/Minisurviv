package io.core.entity.mob;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.core.core.CameraController;
import io.core.core.Renderer;
import io.core.entity.item.Egg;
import io.core.level.Level;
import io.core.level.LevelsManager;

public class Chicken extends Mob
{
    public Chicken () {
        TextureRegion sheet = Renderer.getEntitiesTextureAtlas().findRegion("chicken");
        loadAnimations(sheet, 3, 4);


        int x = 4;
        int y = 4;
        LevelsManager.getCurrentLevel().addEntity(this, x, y);
        CameraController.snapTo(this);  // TODO: It's not really good place for snapping
        System.out.println("[Chicken] Initialized at x: " + x + ", " + "y: " + y + " At level: " + LevelsManager.getCurrentLevel());
    }


    @Override
    public void tick(Level level) {



        // Chicken can randomly lay eggs over time once per x minutes
        if (random.nextInt(1 * 60 * 3) == 0) {
            level.addEntity(new Chicken(), x, y);
            System.out.println("laid egg!");
        }
    }
}

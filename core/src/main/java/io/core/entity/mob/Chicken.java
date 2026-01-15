package io.core.entity.mob;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.core.core.CameraController;
import io.core.core.Renderer;
import io.core.entity.item.Egg;
import io.core.entity.item.ItemId;
import io.core.entity.item.Items;
import io.core.level.Level;
import io.core.level.LevelsManager;

public class Chicken extends PassiveMob
{
    static int a = 0;
    public Chicken () {
        super(4);

        TextureRegion sheet = Renderer.getEntitiesTextureAtlas().findRegion("chicken");
        loadAnimations(sheet, 3, 4);

        setHitboxSize(8, 8);
        int x = 4;
        int y = 4;
        if (a == 0)LevelsManager.getCurrentLevel().addEntity(this, x, y);  //TODO: temporary, spawning should be determined in world generation
        a++;
        System.out.println("[Chicken] Initialized at x: " + x + ", " + "y: " + y + " At level: " + LevelsManager.getCurrentLevel());
    }


    @Override
    public void tick(Level level) {
        super.tick(level);
        // Chicken can randomly lay eggs over time once per x minutes
        if (random.nextInt(6 * 6 * 4) == 0) {
            //level.addEntity(new Egg(), x, y); depracted
            level.dropItem(x, y, 1, 1, Items.get(ItemId.EGG));
        }
    }
}

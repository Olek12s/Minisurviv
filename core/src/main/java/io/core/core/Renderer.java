package io.core.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.core.entity.Entity;
import io.core.level.Level;
import io.core.level.LevelsManager;

public class Renderer {
    private static int TILE_TXT_SIZE = 24;  // leave it private, no class except Renderer should know what TXT size is
    private static int ENTITY_TXT_SIZE = 24;  // leave it private, no class except Renderer should know what TXT size is

    public static SpriteBatch spriteBatch;
    private static OrthographicCamera camera;

    private static final AssetManager assetManager = new AssetManager();
    private static TextureAtlas TILES_TEXTURE_ATLAS;

    public static boolean renderGame = false;

    public static void init(Viewport viewport) {
        Renderer.spriteBatch = new SpriteBatch();

        Renderer.camera = (OrthographicCamera) viewport.getCamera();
        //Renderer.camera.setToOrtho(false);
        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);

        loadTileTextures();
    }

    private static void loadTileTextures() {
        assetManager.load("tiles.atlas", TextureAtlas.class);
        assetManager.finishLoading();
        TILES_TEXTURE_ATLAS = assetManager.get("tiles.atlas", TextureAtlas.class);
    }

    /**
     * Main rendering method, here every render() method is being called and managed
     */
    public static void render() {
        // camera's visible AABB
        int startX = (int)(camera.position.x - camera.viewportWidth / 2) / TILE_TXT_SIZE;
        int endX   = (int)(camera.position.x + camera.viewportWidth / 2) / TILE_TXT_SIZE;
        int startY = (int)(camera.position.y - camera.viewportHeight / 2) / TILE_TXT_SIZE;
        int endY   = (int)(camera.position.y + camera.viewportHeight / 2) / TILE_TXT_SIZE;

        if (renderGame) {
            spriteBatch.begin();
            LevelsManager.getCurrentLevel().render(startX, startY, endX, endY);
            //renderGUI();
            spriteBatch.end();
        }
    }

    public static void renderTile(String tileName, int x, int y) {

        spriteBatch.draw(
                TILES_TEXTURE_ATLAS.findRegion(tileName.toLowerCase()),
                x * TILE_TXT_SIZE,
                y * TILE_TXT_SIZE,
                TILE_TXT_SIZE,
                TILE_TXT_SIZE
        );
    }

    public static void renderEntity(TextureRegion frame, int x, int y) {
        spriteBatch.draw(
                frame,
                x * ENTITY_TXT_SIZE,
                y * ENTITY_TXT_SIZE,
                ENTITY_TXT_SIZE,
                ENTITY_TXT_SIZE
        );
    }
}

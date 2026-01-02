package io.core.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.core.entity.Entity;
import io.core.level.Level;
import io.core.level.LevelsManager;

public class Renderer {
    private static int WORLD_WIDTH = 432;   // 18 * 24
    private static int WORLD_HEIGHT = 288;  // 12 * 24

    private static int TILE_TXT_SIZE = 24;

    public static SpriteBatch spriteBatch;
    private static OrthographicCamera camera;

    private static final AssetManager assetManager = new AssetManager();
    private static TextureAtlas TILES_TEXTURE_ATLAS;

    public static boolean renderGame = false;

    public static void init(Viewport viewport) {
        Renderer.spriteBatch = new SpriteBatch();

        Renderer.camera = (OrthographicCamera) viewport.getCamera();
        Renderer.camera.setToOrtho(false);
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
        if (renderGame) {
            spriteBatch.begin();
            renderLevel();
            //renderGUI();
            spriteBatch.end();
        }
    }


    /**
     * Renders current level content
     */
    public static void renderLevel() {
        Level currentLevel = LevelsManager.getCurrentLevel();
        currentLevel.render();
    }

    public static void renderTile(String tileName, int x, int y) {
        spriteBatch.draw(TILES_TEXTURE_ATLAS.findRegion(tileName.toLowerCase()), x * TILE_TXT_SIZE, y * TILE_TXT_SIZE);
    }
}

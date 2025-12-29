package io.core.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Renderer {
    private static int WORLD_WIDTH = 432;   // 18 * 24
    private static int WORLD_HEIGHT = 288;  // 12 * 24

    private static int TILE_TXT_SIZE = 16;

    public static SpriteBatch spriteBatch;
    private static OrthographicCamera camera;

    private static final AssetManager assetManager = new AssetManager();
    private static TextureAtlas TILES_TEXTURE_ATLAS;

    public static void init(OrthographicCamera camera, Viewport viewport) {
        Renderer.spriteBatch = new SpriteBatch();
        Renderer.camera = camera;

        loadTileTextures();


        camera.setToOrtho(true);   // (0,0) coordinate is at top left !!!
        viewport.setCamera(camera);
        viewport.getCamera();
    }

    private static void loadTileTextures() {
        assetManager.load("tiles.atlas", TextureAtlas.class);
        assetManager.finishLoading();
        TILES_TEXTURE_ATLAS = assetManager.get("tiles.atlas", TextureAtlas.class);
    }

    public static void render() {

    }


    public static void drawTile(String tileName, int x, int y) {
        spriteBatch.draw(TILES_TEXTURE_ATLAS.findRegion(tileName.toLowerCase()), x * TILE_TXT_SIZE, y * TILE_TXT_SIZE);
    }
}

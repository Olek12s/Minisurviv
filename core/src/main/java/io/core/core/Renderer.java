package io.core.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.core.level.LevelsManager;

public class Renderer {
    private static int TILE_TXT_SIZE = 24;  // leave it private, no class except Renderer should know what TXT size is
    private static int ENTITY_TXT_SIZE = 24;  // leave it private, no class except Renderer should know what TXT size is
    private static int ITEM_TXT_SIZE = 24;

    public static SpriteBatch spriteBatch;
    private static ShapeRenderer shapeRenderer;
    private static CameraController cameraController;

    private static final AssetManager assetManager = new AssetManager();
    private static TextureAtlas TILES_TEXTURE_ATLAS;
    public static TextureAtlas getEntitiesTextureAtlas() {
        return ENTITIES_TEXTURE_ATLAS;
    }
    private static TextureAtlas ENTITIES_TEXTURE_ATLAS;
    private static TextureAtlas ITEMS_TEXTURE_ATLAS;


    public static boolean renderGame = false;

    public static void init(Viewport viewport) {
        Renderer.spriteBatch = new SpriteBatch();
        Renderer.shapeRenderer = new ShapeRenderer();

        cameraController = new CameraController((OrthographicCamera) viewport.getCamera());
        //Renderer.camera.setToOrtho(false);
        CameraController.camera.update();

        spriteBatch.setProjectionMatrix(cameraController.camera.combined);

        loadTileTextures();
        loadEntitiesTextures();
        loadItemTextures();
    }

    private static void loadTileTextures() {
        assetManager.load("tiles.atlas", TextureAtlas.class);
        assetManager.finishLoading();
        TILES_TEXTURE_ATLAS = assetManager.get("tiles.atlas", TextureAtlas.class);
    }

    private static void loadEntitiesTextures() {
        assetManager.load("entities.atlas", TextureAtlas.class);
        assetManager.finishLoading();
        ENTITIES_TEXTURE_ATLAS = assetManager.get("entities.atlas", TextureAtlas.class);
    }

    private static void loadItemTextures() {
        assetManager.load("items.atlas", TextureAtlas.class);
        assetManager.finishLoading();
        ITEMS_TEXTURE_ATLAS = assetManager.get("items.atlas", TextureAtlas.class);
    }

    /**
     * Main rendering method, here every render() method is being called and managed
     */
    public static void render() {
        // camera's visible AABB. floor/ceil so we don't see any unordered areas.
        float camX = CameraController.camera.position.x;
        float camY = CameraController.camera.position.y;
        float halfW = CameraController.camera.viewportWidth / 2f;
        float halfH = CameraController.camera.viewportHeight / 2f;

        int startX = (int) Math.floor((camX - halfW) / TILE_TXT_SIZE);
        int endX   = (int) Math.ceil ((camX + halfW) / TILE_TXT_SIZE);
        int startY = (int) Math.floor((camY - halfH) / TILE_TXT_SIZE);
        int endY   = (int) Math.ceil ((camY + halfH) / TILE_TXT_SIZE);


        if (renderGame) {

            spriteBatch.setProjectionMatrix(CameraController.camera.combined);
            spriteBatch.begin();
            LevelsManager.getCurrentLevel().render(startX, startY, endX, endY);
            //renderGUI();
            spriteBatch.end();


            // Shape renderer's DEBUG render
            shapeRenderer.setProjectionMatrix(CameraController.camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            if (Minisurviv.DEBUG_MODE) {
                LevelsManager.getCurrentLevel().renderShapes(startX, startY, endX, endY);
            }
            shapeRenderer.end();
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

    public static void renderItem(String itemName, float x, float y) {
        spriteBatch.draw(
                ITEMS_TEXTURE_ATLAS.findRegion(itemName.toLowerCase()),
                x * ITEM_TXT_SIZE,
                y * ITEM_TXT_SIZE,
                ITEM_TXT_SIZE,
                ITEM_TXT_SIZE
        );
    }

    public static void renderEntity(TextureRegion region, float x, float y) {
        spriteBatch.draw(
                region,
                x * TILE_TXT_SIZE,
                y * TILE_TXT_SIZE,
                ENTITY_TXT_SIZE,
                ENTITY_TXT_SIZE
        );
    }

    public static void renderHitboxShape(Rectangle hitbox) {
        float x = hitbox.x;
        float y = hitbox.y;
        float w = hitbox.width;
        float h = hitbox.height;

        shapeRenderer.rect(
                x * ENTITY_TXT_SIZE,
                y * ENTITY_TXT_SIZE,
                w * TILE_TXT_SIZE,
                h * TILE_TXT_SIZE
        );
    }

    public static void renderHitboxShape(Rectangle hitbox, Color color) {
        Color originalColor = shapeRenderer.getColor().cpy();
        shapeRenderer.setColor(color);
        renderHitboxShape(hitbox);
        shapeRenderer.setColor(originalColor);
    }
}

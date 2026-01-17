package io.core.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import io.core.entity.Entity;

public class CameraController {

    protected static OrthographicCamera camera;
    private static final int PIXELS_PER_TILE = 24;
    private static final int ENTITY_SIZE = 24;

    // 1.0 or above - instant (no smoothness)
    // above 0 - smooth
    private static final float FOLLOW_FACTOR = 0.1f;

    public CameraController(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * Camera changes its position instantly above entity. Methods needs to be called every tick
     * @param e - entity to follow
     */
    protected static void follow(Entity e) {
        camera.position.set(
                centerXPx(e),
                centerYPx(e),
                0
        );
        camera.update();
    }

    /**
     * Instantly moves the camera to the given entity's position.
     *
     * This method "snaps" the camera to the target without any smoothing.
     * It should be called once during initialization (e.g. when a level
     * starts or the player is spawned) to synchronize the camera position
     * with the entity before smooth following is applied.
     *
     * Using snapTo prevents an initial camera jump caused by smoothing
     * from a previously set camera position.
     */
    public static void snapTo(Entity e) {
        camera.position.set(
                centerXPx(e),
                centerYPx(e),
                0
        );
        camera.update();
    }


    /**
     * Camera changes its position smoothly (how smoothly it depends on FOLLOW_FACTOR) above entity.
     * Methods needs to be called every tick
     * @param e - entity to follow
     */
    public static void followSmooth(Entity e) {
        float targetX = centerXPx(e);
        float targetY = centerYPx(e);

        camera.position.x += (targetX - camera.position.x) * FOLLOW_FACTOR;
        camera.position.y += (targetY - camera.position.y) * FOLLOW_FACTOR;
        camera.update();
    }


    private static float centerXPx(Entity e) {
        return (ENTITY_SIZE + ENTITY_SIZE / 2f) * PIXELS_PER_TILE;
    }

    private static float centerYPx(Entity e) {
        return (ENTITY_SIZE + ENTITY_SIZE / 2f) * PIXELS_PER_TILE;
    }


}

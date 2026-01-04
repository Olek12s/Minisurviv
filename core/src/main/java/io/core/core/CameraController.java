package io.core.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import io.core.entity.Entity;

public class CameraController {

    protected static OrthographicCamera camera;
    private static final int PIXELS_PER_TILE = 24;

    public CameraController(OrthographicCamera camera) {
        this.camera = camera;
    }

    protected static void follow(Entity e) {
        camera.position.set(
                e.getX() * PIXELS_PER_TILE,
                e.getY() * PIXELS_PER_TILE,
                0
        );
        update();
    }

    public static void update() {
        camera.update();
    }
}

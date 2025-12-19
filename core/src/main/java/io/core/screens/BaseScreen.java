package io.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.core.core.Renderer;

abstract public class BaseScreen implements Screen
{

    protected final Viewport viewport;

    protected BaseScreen(Viewport viewport) {
        this.viewport = viewport;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        Renderer.WORLD_WIDTH = (int)viewport.getWorldWidth();
        Renderer.WORLD_HEIGHT = (int)viewport.getWorldHeight();
        System.out.println("resize");
    }

    @Override
    public void show() {
        viewport.apply();
    }


    /*
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
    */
}

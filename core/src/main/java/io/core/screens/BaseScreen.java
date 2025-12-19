package io.core.screens;

import com.badlogic.gdx.Screen;
import io.core.Minisurviv;
import io.core.Renderer;
import io.core.Updater;

public class BaseScreen implements Screen
{
    //protected final Minisurviv game;
    protected final Renderer renderer;
    protected final Updater updater;

    protected BaseScreen() {
        this.renderer = new Renderer();
        this.updater = new Updater();
    }

    @Override
    public void render(float delta) {
        //updater.tick();
        //renderer.render();
        System.out.println("ti re");
    }

    @Override
    public void show() {}

    @Override
    public void resize(int i, int i1) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}

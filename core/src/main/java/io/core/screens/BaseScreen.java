package io.core.screens;

import com.badlogic.gdx.Screen;
import io.core.core.Input;
import io.core.core.Renderer;
import io.core.core.Updater;

public class BaseScreen implements Screen
{
    //protected final Minisurviv game;
    protected final Input input;
    protected final Updater updater;
    protected final Renderer renderer;

    protected BaseScreen() {
        this.input = new Input();
        this.updater = new Updater();
        this.renderer = new Renderer();
    }

    @Override
    public void render(float delta) {

        input.listen();
        updater.tick();
        renderer.render();

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

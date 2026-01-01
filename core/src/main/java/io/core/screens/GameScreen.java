package io.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.core.core.Minisurviv;

public class GameScreen implements Screen {
    private final Minisurviv game;
    private final Stage hudStage;
    
    protected GameScreen(Minisurviv game) {
        this.game = game;
        this.hudStage = new Stage(game.getViewport());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

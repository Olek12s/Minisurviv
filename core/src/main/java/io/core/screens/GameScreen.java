package io.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.core.core.Minisurviv;
import io.core.core.Renderer;
import io.core.core.Updater;

public class GameScreen implements Screen {
    private final Minisurviv game;
    private final Stage hudStage;
    
    protected GameScreen(Minisurviv game) {
        this.game = game;
        this.hudStage = new Stage(game.getViewport());

        Renderer.renderGame = true; // gameplay is pending, setting flag to true so game can be rendered in main loop
        Updater.tickGame = true; // gameplay is pending, setting flag to true so game can be ticked in main loop
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(hudStage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hudStage.act(dt);
        hudStage.draw();
    }

    @Override
    public void resize(int width, int height) { game.viewport.update(width, height, true); }

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

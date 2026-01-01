package io.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.core.core.Minisurviv;
import io.core.level.Level;
import io.core.level.LevelGenerator;
import io.core.level.LevelsManager;

public class LoadingScreen implements Screen
{

    enum Mode { NEW_WORLD, LOAD_WORLD }

    private final Minisurviv game;
    private final Mode mode;


    private Stage stage;
    private Label progressLabel;
    private Label stepLabel;


    private float progress = 0f;
    private String step = "initializing...";
    private boolean finished = false;


    public LoadingScreen(Minisurviv game, Mode mode) {
        this.game = game;
        this.mode = mode;
    }

    @Override
    public void show() {
        stage = new Stage(game.getViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        progressLabel = new Label("Loading: 0%", skin);
        stepLabel = new Label("initializing...", skin);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(stepLabel).padBottom(10);
        table.row();
        table.add(progressLabel);

        stage.addActor(table);

        new Thread(this::load).start();
    }

    private void load() {
        if (mode == Mode.NEW_WORLD) {
            Level level = new Level(512, 512, null, 0, 781);    // TODO : pass seed from newWorldsettings

            level.generate(p -> Gdx.app.postRunnable(() -> {
                progress = p;
                progressLabel.setText("Loading... " + (int)(p*100) + "%");
            }));

            LevelsManager.setCurrentLevel(level.getLevelNumber());
        }
        finished = true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        if (finished) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}

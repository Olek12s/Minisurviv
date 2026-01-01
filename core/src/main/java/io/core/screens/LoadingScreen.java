package io.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.core.core.Minisurviv;
import io.core.level.LevelsManager;
import io.core.util.FloatConsumer;

public class LoadingScreen implements Screen {

    private final Minisurviv game;
    private Stage stage;
    private Label overallLabel;
    private Label stepLabel;

    private boolean finished = false;

    public LoadingScreen(Minisurviv game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(game.getViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        overallLabel = new Label("Overall Progress: 0%", skin);
        stepLabel = new Label("Current Level: 0%", skin);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(stepLabel).padBottom(10);
        table.row();
        table.add(overallLabel);
        stage.addActor(table);

        // generating thread
        new Thread(this::generateWorld).start();
    }

    private void generateWorld() {
        LevelsManager manager = new LevelsManager(750, 512);    // TODO : pass params from worldoptions

        FloatConsumer overall = p -> Gdx.app.postRunnable(() ->
                overallLabel.setText(String.format("Overall Progress: %d%%", (int)(p * 100)))
        );

        FloatConsumer step = p -> Gdx.app.postRunnable(() ->
                stepLabel.setText(String.format("Current Level: %d%%", (int)(p * 100)))
        );

        FloatConsumer stepName = p -> {};

        manager.generateAllLevels(overall, step, stepName);
        LevelsManager.setCurrentLevel(0);

        finished = true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        if (finished) {
            System.out.println("[Main Menu] generating world finished");
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) { game.viewport.update(width, height, true); }

    @Override
    public void dispose() { stage.dispose(); }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
}

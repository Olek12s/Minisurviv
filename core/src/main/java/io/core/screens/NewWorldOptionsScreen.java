package io.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.sun.tools.javac.Main;
import io.core.core.Minisurviv;

import java.util.Random;

public class NewWorldOptionsScreen implements Screen {

    private final Minisurviv game;
    private Stage stage;

    private Label headerLabel;
    private Label worldNameLabel;
    private Label worldSeedLabel;
    private Label worldSizeLabel;
    private Label createWorldLabel;
    private Actor[] labelActors;


    private String worldName;
    private int seed;
    private int worldSize = 256;    // default value

    private int focusIndex = 0;

    private final int[] allowedSizes = {128, 256, 512, 1024, 2048};

    public NewWorldOptionsScreen(Minisurviv game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(game.getViewport());
        Gdx.input.setInputProcessor(stage);

        // FONT
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecrafty/Regular.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter buttonParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        buttonParam.size = 16;
        BitmapFont buttonFont = generator.generateFont(buttonParam);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = buttonFont;
        labelStyle.fontColor = Color.WHITE;

        headerLabel = new Label("Create new world: ", labelStyle);
        worldNameLabel = new Label("Name: ", labelStyle);
        worldSizeLabel = new Label("Size: ", labelStyle);
        worldSeedLabel = new Label("Seed: ", labelStyle);
        createWorldLabel = new Label("Create World", labelStyle);

        labelActors = new Actor[4];
        labelActors[0] = worldNameLabel;
        labelActors[1] = worldSizeLabel;
        labelActors[2] = worldSeedLabel;
        labelActors[3] = createWorldLabel;





        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.pad(20);
        table.add(headerLabel).padBottom(30);
        table.row();
        for (Actor actor : labelActors) {
            if (actor == createWorldLabel) {
                table.add(actor).padTop(20);
            }
            else table.add(actor).padBottom(10);
            table.row();
        }

        stage.addActor(table);


        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                // CHANGE SCREEN TO MAIN MENU
                if (keycode == Input.Keys.ESCAPE) {
                 //   game.setScreen(new MainMenuScreen(game));
                    return true;
                }

                // DOWN
                if (keycode == Input.Keys.DOWN) {
                    focusIndex = (focusIndex + 1) % labelActors.length;
                    return true;
                }

                // UP
                if (keycode == Input.Keys.UP) {
                    focusIndex = (focusIndex - 1 + labelActors.length) % labelActors.length;
                    return true;
                }

                // CREATE NEW WORLD
                if ((keycode == Input.Keys.ENTER || keycode == Input.Keys.SPACE)
                        && LabelIndices.fromIndex(focusIndex) == LabelIndices.WORLD_GENERATE) {

                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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


    private enum LabelIndices {
        WORLD_NAME(0),
        WORLD_SIZE(1),
        WORLD_SEED(2),
        WORLD_GENERATE(3);

        public final int index;

        LabelIndices(int index) {
            this.index = index;
        }

        public static LabelIndices fromIndex(int i) {
            for (LabelIndices li : values()) {
                if (li.index == i) return li;
            }
            return null;
        }
    }
}

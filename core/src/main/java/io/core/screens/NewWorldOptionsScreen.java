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

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

public class NewWorldOptionsScreen implements Screen {

    private final Minisurviv game;
    private Stage stage;

    private Label headerLabel;
    private Label worldNameLabel;
    private Label worldSeedLabel;
    private Label worldSizeLabel;
    private Label createWorldLabel;
    private Label[] labelActors;


    private String worldNameText = "";
    private int seed;
    private String seedText = "";

    private int focusIndex = 0;

    private int worldSizeIndex = 1;
    private final int[] allowedSizes = {128, 256, 512, 1024, 2048};
    private int worldSize = allowedSizes[worldSizeIndex];

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

        labelActors = new Label[4];
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
                    game.setScreen(new MainMenuScreen(game));
                    return true;
                }

                // DOWN
                if (keycode == Input.Keys.DOWN) {
                    focusIndex = (focusIndex + 1) % labelActors.length;
                    updateFocus();
                    return true;
                }

                // UP
                if (keycode == Input.Keys.UP) {
                    focusIndex = (focusIndex - 1 + labelActors.length) % labelActors.length;
                    updateFocus();
                    return true;
                }

                // LEFT
                if (keycode == Input.Keys.LEFT) {
                    if (focusIndex == LabelIndices.WORLD_SIZE.index) {
                        if (worldSizeIndex > 0) {
                            worldSizeIndex--;

                            updateFocus();
                        }
                    }
                    return true;
                }

                // RIGHT
                if (keycode == Input.Keys.RIGHT) {
                    if (focusIndex == LabelIndices.WORLD_SIZE.index) {
                        if (worldSizeIndex < allowedSizes.length - 1) {
                            worldSizeIndex++;

                            updateFocus();
                        }
                    }
                    return true;
                }

                // CREATE NEW WORLD
                if ((keycode == Input.Keys.ENTER || keycode == Input.Keys.SPACE)
                        && LabelIndices.fromIndex(focusIndex) == LabelIndices.WORLD_GENERATE) {

                    String finalWorldName = worldNameText.trim();
                    if (finalWorldName.isEmpty()) return false;

                    // if seed empty - generate random seed
                    int finalSeed;
                    if (seedText.isEmpty() || seedText.equals("-")) {
                        finalSeed = new Random().nextInt();
                    } else {
                        try {
                            finalSeed = Integer.parseInt(seedText);
                        } catch (NumberFormatException e) {
                            finalSeed = new Random().nextInt();
                        }
                    }

                    game.setScreen(new LoadingScreen(
                            game,
                            finalWorldName,
                            worldSize,
                            finalSeed
                    ));
                    return true;
                }

                return false;
            }

            // KeyTyped for both seed & world name
            @Override
            public boolean keyTyped(InputEvent event, char character) {

                if (character == '\r' || character == '\n') return true;

                // BACKSPACE
                if (character == '\b') {
                    if (focusIndex == LabelIndices.WORLD_NAME.index && worldNameText.length() > 0) {
                        worldNameText = worldNameText.substring(0, worldNameText.length() - 1);
                    }

                    if (focusIndex == LabelIndices.WORLD_SEED.index && seedText.length() > 0) {
                        seedText = seedText.substring(0, seedText.length() - 1);
                    }

                    updateFocus();
                    return true;
                }

                if (focusIndex == LabelIndices.WORLD_NAME.index) {
                    if (worldNameText.length() < 32 && character >= 32) {
                        worldNameText += character;
                    }
                }

                if (focusIndex == LabelIndices.WORLD_SEED.index) {
                    if (Character.isDigit(character) || character == '-') {
                        seedText += character;
                    }
                }

                updateFocus();
                return true;
            }
        });
        updateFocus();
    }   // show()

    private void updateFocus() {


        worldSize = allowedSizes[worldSizeIndex];
        worldSizeLabel.setText("Size: " + worldSize);

        for (int i = 0; i < labelActors.length; i++) {

            labelActors[i].clearActions();
            labelActors[i].getColor().a = 1f;

            String baseText;

            if (i == LabelIndices.WORLD_NAME.index) {
                baseText = "Name: " + worldNameText;
            }
            else if (i == LabelIndices.WORLD_SIZE.index) {
                baseText = "Size: " + worldSize;
            }
            else if (i == LabelIndices.WORLD_SEED.index) {
                baseText = "Seed: " + seedText;
            }
            else {
                baseText = "Create World";
            }

            if (i == focusIndex) {
                labelActors[i].setText("> " + baseText + " <");
                stage.setKeyboardFocus(labelActors[i]);
                labelActors[i].addAction(forever(sequence(
                        fadeOut(0.5f),
                        fadeIn(0.5f)
                )));

            } else {
                labelActors[i].setText(baseText);
            }
        }
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

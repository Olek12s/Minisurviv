package io.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.core.core.Minisurviv;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

public class MainMenuScreen implements Screen
{
    private Minisurviv game;
    private Stage stage;
    private Skin skin;

    private Texture logoTexture;
    private Image logo;

    private TextButton[] sceneButtons;
    private int focusIndex = 0;

    public MainMenuScreen(Minisurviv game) {
        this.game = game;
        this.stage = new Stage(game.getViewport());
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        Gdx.input.setInputProcessor(this.stage);

        // LOGO
        this.logoTexture = new Texture(Gdx.files.internal("logo.png"));
        this.logo = new Image(logoTexture);


        // FONT
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecrafty/Regular.otf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        //parameter.size = 64;
        //parameter.color = Color.WHITE;

        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();


        // BARE BUTTON STYLE
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = null;
        style.down = null;
        style.checked = null;
        style.font = font;

        // BUTTONS DEFINITONS
        TextButton startnewWorldButton = new TextButton("start new world", style);
        TextButton loadWorldButton = new TextButton("load world", style);
        TextButton settingsButton = new TextButton("settings", style);
        TextButton achievementsButton = new TextButton("achievements", style);
        TextButton exitButton = new TextButton("exit", style);


        sceneButtons = new TextButton[] {
                startnewWorldButton, loadWorldButton, settingsButton, achievementsButton, exitButton
        };

        // DISABLE TOUCHABLE BY MOUSE
        for (TextButton b : sceneButtons) {
            b.setTouchable(Touchable.disabled);
        }

        // DEFINE LISTENERS FOR THE BUTTONS:

        // new world button
        startnewWorldButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("New World");
            }
        });

        // load world button
        loadWorldButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Load World");
            }
        });

        // settings button
        settingsButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Settings");
            }
        });

        // achievements button
        achievementsButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Achievements");
            }
        });

        // exit game button
        exitButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Exit");
                dispose();
                System.exit(0);
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.top();

        table.add(logo).padTop(20).padBottom(20).width(104*3).height(16*3);
        table.row();
        for (TextButton b : sceneButtons) {
            table.add(b).padBottom(20);
            table.row();
        }

        stage.addActor(table);


        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.DOWN) {
                    focusIndex = (focusIndex + 1) % sceneButtons.length;
                    updateFocus();
                    return true;
                }

                if (keycode == Input.Keys.UP) {
                    focusIndex = (focusIndex - 1 + sceneButtons.length) % sceneButtons.length;
                    updateFocus();
                    return true;
                }

                if (keycode == Input.Keys.ENTER || keycode == Input.Keys.SPACE) {
                    fireFocusedButton();
                    return true;
                }

                return false;
            }
        });
        updateFocus();
    }

    private void fireFocusedButton() {
        TextButton focused = sceneButtons[focusIndex];
        focused.fire(new ChangeListener.ChangeEvent());
    }


    private void updateFocus() {
        for (int i = 0; i < sceneButtons.length; i++) {

            sceneButtons[i].clearActions();
            sceneButtons[i].getColor().a = 1f;

            String baseText = sceneButtons[i].getText().toString()
                    .replace("> ", "")
                    .replace(" <", "");

            if (i == focusIndex) {
                sceneButtons[i].setText("> " + baseText + " <");
                stage.setKeyboardFocus(sceneButtons[i]);
                sceneButtons[i].addAction(forever(sequence(
                        fadeOut(0.5f),
                        fadeIn(0.5f)
                )));

            } else {
                sceneButtons[i].setText(baseText);
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(dt);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        logoTexture.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}

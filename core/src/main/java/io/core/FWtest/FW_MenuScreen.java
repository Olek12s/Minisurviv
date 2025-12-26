package io.core.FWtest;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class FW_MenuScreen implements Screen {

    private final FW_Main game;
    private Stage stage;
    private Skin skin;

    private Texture logoTexture;
    private Image logo;

    private TextButton[] buttons;
    private int focusIndex = 0;

    public FW_MenuScreen(FW_Main game) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        Gdx.input.setInputProcessor(stage);

        // LOGO
        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        logo = new Image(logoTexture);

        // FONT
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2.5f);

        // STYLE
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = null;
        style.down = null;
        style.checked = null;
        style.font = font;

        TextButton button1 = new TextButton("start new world", style);
        TextButton button2 = new TextButton("load world", style);
        TextButton button3 = new TextButton("settings", style);
        TextButton button4 = new TextButton("achievements", style);
        TextButton button5 = new TextButton("exit", style);

        buttons = new TextButton[] {
                button1, button2, button3, button4, button5
        };


        button1.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                System.out.println("NEW WORLD");
            }

        });

        button5.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        Table table = new Table();
        table.setFillParent(true);
        table.top();

        table.add(logo).padTop(40).padBottom(80);
        table.row();
        for (TextButton b : buttons) {
            table.add(b).padBottom(20);
            table.row();
        }

        stage.addActor(table);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.DOWN) {
                    focusIndex = (focusIndex + 1) % buttons.length;
                    updateFocus();
                    return true;
                }

                if (keycode == Input.Keys.UP) {
                    focusIndex = (focusIndex - 1 + buttons.length) % buttons.length;
                    updateFocus();
                    return true;
                }

                if (keycode == Input.Keys.ENTER || keycode == Input.Keys.SPACE) {
                    activateFocusedButton();
                    return true;
                }

                return false;
            }

        });

        updateFocus();
    }

    private void activateFocusedButton() {
        if (buttons[focusIndex] == buttons[0]) {
            System.out.println("NEW WORLD");
        }
        else if (buttons[focusIndex] == buttons[4]) {
            Gdx.app.exit();
        }
    }


    private void updateFocus() {
        for (int i = 0; i < buttons.length; i++) {

            buttons[i].clearActions();
            buttons[i].getColor().a = 1f;

            String baseText = buttons[i].getText().toString()
                    .replace("> ", "")
                    .replace(" <", "");

            if (i == focusIndex) {
                buttons[i].setText("> " + baseText + " <");
                stage.setKeyboardFocus(buttons[i]);
                buttons[i].addAction(forever(sequence(
                        fadeOut(0.5f),
                        fadeIn(0.5f)
                )));

            } else {
                buttons[i].setText(baseText);
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
        skin.dispose();
        logoTexture.dispose();
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}

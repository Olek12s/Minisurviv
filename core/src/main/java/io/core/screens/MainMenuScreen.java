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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.core.core.Minisurviv;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Random;

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
    private Label versionLabel;
    private Label funTextLabel;

    private String[] randomFunTexts = {
            "Tiley!", "Also try Minecraft!", "Also try Terraria!", "Welcome 2026!",
            "Don't forget to drink", "Praise to the cats!", "Don't try it at home!",
            "Don't test dynamite at home!", "Also try Hardcore mode!", "GL & HF!",
    };

    public MainMenuScreen(Minisurviv game) {
        this.game = game;
        this.stage = new Stage(game.getViewport());
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.logoTexture = new Texture(Gdx.files.internal("logo.png"));
        this.logo = new Image(logoTexture);
        this.versionLabel = new Label(Minisurviv.VERSION, skin);
        this.funTextLabel = new Label(randomFuntext(), skin);

        Gdx.input.setInputProcessor(this.stage);


        // FONT (buttons)
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecrafty/Regular.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter buttonParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        buttonParam.size = 16;
        BitmapFont buttonFont = generator.generateFont(buttonParam);

        // FONT (version label)
        FreeTypeFontGenerator.FreeTypeFontParameter labelParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        labelParam.size = 12;
        BitmapFont labelFont = generator.generateFont(labelParam);

        // FONT (fun text label)
        FreeTypeFontGenerator.FreeTypeFontParameter funTextParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        funTextParam.size = 14;
        BitmapFont funTextFont = generator.generateFont(funTextParam);


        // VERSION LABEL STYLE
        Label.LabelStyle versionStyle = new Label.LabelStyle();
        versionStyle.font = labelFont;
        versionStyle.fontColor = Color.GRAY;
        versionLabel = new Label(Minisurviv.VERSION, versionStyle);

        // BARE BUTTON STYLE
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = null;
        buttonStyle.down = null;
        buttonStyle.checked = null;
        buttonStyle.font = buttonFont;

        // FUN TEXT LABEL STYLE
        Label.LabelStyle funTextStyle = new Label.LabelStyle();
        funTextStyle.font = funTextFont;
        funTextStyle.fontColor = Color.GOLD;
        funTextLabel = new Label(randomFuntext(), funTextStyle);
        funTextLabel.addAction(forever(
                sequence(
                fadeOut(1.4f),
                fadeIn(1.4f)
        )));

        // BUTTONS DEFINITONS
        TextButton startnewWorldButton = new TextButton("start new world", buttonStyle);
        TextButton loadWorldButton = new TextButton("load world", buttonStyle);
        TextButton settingsButton = new TextButton("settings", buttonStyle);
        TextButton achievementsButton = new TextButton("achievements", buttonStyle);
        TextButton exitButton = new TextButton("exit", buttonStyle);


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

        table.add(logo).padTop(10).padBottom(2).width(104*3).height(16*3);
        table.row();
        table.add(funTextLabel).padTop(2).padBottom(20);
        table.row();
        for (TextButton b : sceneButtons) {
            table.add(b).padBottom(10);
            table.row();
        }

        stage.addActor(table);
        //table.debug();

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

        // VERSION LABEL
        versionLabel.setColor(Color.GRAY);

        Table bottomLeftTable = new Table();
        bottomLeftTable.setFillParent(true);
        bottomLeftTable.left().bottom();
        bottomLeftTable.add(versionLabel).pad(3);
        stage.addActor(bottomLeftTable);

        // FUN TEXT LABEL

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

    private String randomFuntext() {
        Random random = new Random();
        return randomFunTexts[random.nextInt(randomFunTexts.length)];
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

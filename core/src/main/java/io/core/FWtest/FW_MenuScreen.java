package io.core.FWtest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FW_MenuScreen implements Screen {
    private final FW_Main game;
    Stage stage;
    private Skin skin;
    Texture logoTexture;
    Image logo;


    public FW_MenuScreen(FW_Main game) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        this.logoTexture = new Texture(Gdx.files.internal("logo.png"));
        this.logo = new Image(logoTexture);

        Table table = new Table();

        table.top();
        table.setFillParent(true);

        TextButton button1 = new TextButton("start new world", skin);
        TextButton button2 = new TextButton("load world", skin);
        TextButton button3 = new TextButton("settings", skin);
        TextButton button4 = new TextButton("achievements", skin);
        TextButton button5 = new TextButton("exit", skin);

button2.addListener(new ClickListener() {
    @Override
    public void clicked(InputEvent event, float x, float y) {
        System.out.println("click!");

    }
});



        BitmapFont font = new BitmapFont();
        font.getData().setScale(3f);


        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("default-round");
        style.down = skin.getDrawable("default-round-down");
        style.font = font;


       button1 = new TextButton("Start", style);


        // LOGO
        table.add(logo)
                .padTop(50)
                .width(600)
                .height(100);
        table.row();

        // 1. Start
        table.add(button1)
                .padTop(100)
                .width(300)
                .height(60);
        table.row();

        // 2. Load
        table.add(button2)
                .padTop(20)
                .width(300)
                .height(60);
        table.row();

        // 3. Settings + Achievements
        Table rowTable = new Table();
        rowTable.add(button3)
                .width(140)
                .height(60)
                .padTop(20)
                .padRight(20);


        rowTable.add(button4)
                .width(140)
                .height(60)
                .padTop(20);
        table.add(rowTable);
        table.row();

        // 4. Exit
        table.add(button5)
                .padTop(20)
                .width(300)
                .height(60);
        table.row();

        stage.addActor(table);


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1)
    {
        game.viewport.update(i, i1);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}

    @Override
    public void show() {}
    @Override
    public void hide() {}
}

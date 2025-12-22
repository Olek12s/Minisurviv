package io.core.FWtest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class FW_Main extends Game
{
    public Viewport viewport;

    @Override
    public void create() {
        viewport = new FitViewport(800, 600);
        setScreen(new FW_MenuScreen(this));
    }

    @Override
    public void resize(int i, int i1) {
       // viewport.update(i, i1);
        super.resize(i, i1);
    }


   public void render() {
       Gdx.gl.glClearColor(0.0f, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        super.render();
   }

    @Override
    public void dispose() {
        super.dispose();
    }
}

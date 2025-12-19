package io.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.core.screens.MainMenuScreen;

public class Minisurviv extends Game
{
    private AssetManager assetManager;
    private SpriteBatch spriteBatch;

    @Override
    public void create() {
        this.assetManager = new AssetManager();
        this.spriteBatch = new SpriteBatch();

        setScreen(new MainMenuScreen());
    }

    @Override
    public void render() {
        super.render();
        System.out.println("ren");
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        spriteBatch.dispose();
    }
}

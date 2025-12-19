package io.core.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.core.screens.MainMenuScreen;

public abstract class Minisurviv extends Game
{
    private AssetManager assetManager;
    private SpriteBatch spriteBatch;
    private Input input;
    private Updater updater;
    private Renderer renderer;


    @Override
    public void create() {
        this.assetManager = new AssetManager();
        this.spriteBatch = new SpriteBatch();

        this.input = new Input();
        this.updater = new Updater();
        this.renderer = new Renderer();

        setScreen(new MainMenuScreen());
    }


    /**
     * Main loop method. Main-loop IUR pipeline stands as:
     * 1) calling user I/O operations
     * 2) calling all available update ticks
     * 3) rendering content on the current screen
     *
     * render() method is called by Lib-GDX framework "configuration.setForegroundFPS" times per second.
     */
    @Override
    public void render() {
        super.render();
        input.listen();
        updater.tick();
        renderer.render();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        spriteBatch.dispose();
    }
}

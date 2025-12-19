package io.core.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.core.screens.MainMenuScreen;

public abstract class Minisurviv extends Game
{
    private AssetManager assetManager;
    private SpriteBatch spriteBatch;
    private Input input;
    private Updater updater;
    private Renderer renderer;

    // Debug counters
    private int ticksThisSecond = 0;
    private int framesThisSecond = 0;
    private int currentTPS = 0;
    private int currentFPS = 0;
    private long lastSecondTime = 0;

    // Target ticks per second
    public static final int TARGET_TPS = 60;  // how many ticks are performed per 1 second

    // Target frames per second
    public static final int TARGET_FPS = 300;  // how many ticks are performed per 1 second

    // counts how many ticks should be processed based on elapsed time (in ticks)
    private double accumulator = 0;

    // when was last tick call in (nano secs)
    long lastTickTime = 0;


    @Override
    public void create() {
        this.assetManager = new AssetManager();
        this.spriteBatch = new SpriteBatch();

        this.input = new Input();
        this.updater = new Updater();
        this.renderer = new Renderer();

        setScreen(new MainMenuScreen(new FitViewport(432, 288)));


        lastSecondTime = System.currentTimeMillis();
    }


    /**
     * Main loop method. Main-loop IUR pipeline stands as:
     * 1) calling user I/O operations   - dependent on {@link #TARGET_TPS}
     * 2) calling all available update ticks - dependent on {@link #TARGET_TPS}
     * 3) rendering content on the current screen - dependent on Lib-GDX frames per second
     *
     * render() method is called by Lib-GDX framework "configuration.setForegroundFPS" times per second.
     *
     */
    @Override
    public void render() {
        // LibGDX render pipeline
        super.render();

        long now = System.nanoTime();
        double nsPerTick = 1_000_000_000.0 / TARGET_TPS;

        // input.listen(); // Listen to user input  <--- move input.listen() here to make it TARGET_FPS dependent

        // Accumulate unprocessed time
        accumulator += (now - (lastTickTime == 0 ? now : lastTickTime)) / nsPerTick;
        lastTickTime = now;

        // Process ticks
        while (accumulator >= 1) {
            input.listen(); // Listen to user input
            updater.tick();
            ticksThisSecond++;
            accumulator--;
        }

        // Render current frame
        renderer.render();
        framesThisSecond++;

        // Update FPS/TPS counters every second
        if (System.currentTimeMillis() - lastSecondTime >= 1000) {
            currentTPS = ticksThisSecond;
            currentFPS = framesThisSecond;
            ticksThisSecond = 0;
            framesThisSecond = 0;
            lastSecondTime += 1000;
        }
    }

    @Override
    public void resize(int x, int y) {
        super.resize(x, y);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        spriteBatch.dispose();
    }
}

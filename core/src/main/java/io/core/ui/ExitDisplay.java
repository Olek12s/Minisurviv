package io.core.ui;

import com.badlogic.gdx.Gdx;
import io.core.core.Flow;
import io.core.core.Renderer;

public class ExitDisplay extends Display
{
    public ExitDisplay() {
        new Display.Builder(this)
                .addEntry(new TextEntry("Exit", Flow::exitToMainMenu, true)).centered()
                .addEntry(new TextEntry("Return", this::close, true)).centered()
                .build(true);
    }


    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render() {
        super.render();
    }

    public void close() {
        UIManager.closeTopDisplay();
    }

    @Override
    public String toString() {
        return "ExitDisplay";
    }
}

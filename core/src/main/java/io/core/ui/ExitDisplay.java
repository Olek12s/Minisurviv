package io.core.ui;

import com.badlogic.gdx.Gdx;
import io.core.core.Flow;
import io.core.core.Renderer;

public class ExitDisplay extends Display
{
    public ExitDisplay() {

        Menu menu = new Menu();
        menu.addEntry(new TextEntry("Exit", Flow::exitToMainMenu, true));
        menu.addEntry(new TextEntry("Return", this::close, true));

        addMenu(menu);

        layoutToContent();
       // setMinimumSize(80, 0);
        centerOnScreen();
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

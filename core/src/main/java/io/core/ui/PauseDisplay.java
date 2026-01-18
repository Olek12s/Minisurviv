package io.core.ui;

import io.core.core.Flow;

public class PauseDisplay extends Display
{
    public PauseDisplay() {
        Menu menu = new Menu();
        menu.addEntry(new TextEntry("Game is Paused"));

        addMenu(menu);
        layoutToContent();
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
        return "PauseDisplay";
    }
}

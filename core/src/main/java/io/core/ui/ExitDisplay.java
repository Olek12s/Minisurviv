package io.core.ui;

import com.badlogic.gdx.Gdx;
import io.core.core.Flow;
import io.core.core.Renderer;

public class ExitDisplay extends Display
{
    public ExitDisplay() {
        Menu menu = new Menu();

        menu.addEntry(new TextEntry("Exit", Flow::exitToMainMenu));
        menu.addEntry(new TextEntry("Return", this::close));

        addMenu(menu);

        layoutToContent();
        centerOnScreen();
    }

    private void centerOnScreen() {
        float w = Renderer.getVWWidth();
        float h = Renderer.getVWHeight();

        this.x = (int)(w / 2f - width / 2f);
        this.y = (int)(h / 2f - height / 2f);
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

package io.core.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import io.core.core.Renderer;

public class TextEntry extends Entry
{
    private final String text;
    private final Runnable action;
    private final GlyphLayout layout = new GlyphLayout();

    public TextEntry(String text, Runnable action) {
        this.text = text;
        this.action = action;

        layout.setText(Display.font, text);
    }

    @Override
    public void render(int x, int y, boolean selected) {
        if (selected) Renderer.drawText(Display.font, "> " + text + " <", x, y);
        else Renderer.drawText(Display.font, text, x, y);
    }

    @Override
    public int getWidth() {
        return (int) layout.width;
    }

    @Override
    public int getHeight() {
        return (int) layout.height;
        //return font.getLineHeight()
    }

    @Override
    public void activate() {
        if (action != null)
            action.run();
    }
}

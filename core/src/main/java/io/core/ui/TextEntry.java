package io.core.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import io.core.core.Renderer;

public class TextEntry extends Entry
{
    private final String text;
    private Runnable action;
    private final GlyphLayout layout = new GlyphLayout();

    public TextEntry(String text, Runnable action, boolean selectable) {
        this.selectable = selectable;
        this.text = text;
        this.action = action;

        layout.setText(Display.font, text);
    }

    public TextEntry(String text) {
        this.selectable = false;
        this.text = text;

        layout.setText(Display.font, text);
    }

    @Override
    public void render(int x, int y, boolean selected) {
        if (selected && selectable) Renderer.drawText(Display.font, "> " + text + " <", x, y);
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

package io.core.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import io.core.core.Renderer;

public class TextEntry extends Entry
{
    private final String text;
    private final Runnable action;
    private final BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();

    public TextEntry(BitmapFont font, String text, Runnable action) {
        this.font = font;
        this.text = text;
        this.action = action;

        layout.setText(font, text);
    }

    @Override
    public void render(int x, int y, boolean selected) {
        if (selected) Renderer.drawText(font, "> " + text + " <", x, y);
        else Renderer.drawText(font, text, x, y);
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

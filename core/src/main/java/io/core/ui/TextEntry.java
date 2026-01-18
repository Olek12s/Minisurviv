package io.core.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import io.core.core.Renderer;

public class TextEntry extends Entry {
    private final String text;
    private Runnable action;

    private final GlyphLayout normal = new GlyphLayout();
    private final GlyphLayout selected = new GlyphLayout();

    public TextEntry(String text, Runnable action, boolean selectable) {
        this.selectable = selectable;
        this.text = text;
        this.action = action;

        normal.setText(Display.font, text);

        if (selectable) selected.setText(Display.font, "> " + text + " <");
        else selected.setText(Display.font, text);
    }

    public TextEntry(String text) {
        this.selectable = false;
        this.text = text;

        normal.setText(Display.font, text);
        selected.setText(Display.font, text);
    }

    @Override
    public void render(int x, int y, boolean selected) {
        if (selected && selectable) Renderer.drawText(Display.font, "> " + text + " <", x, y);
        else Renderer.drawText(Display.font, text, x, y);
    }

    @Override
    public int getWidth() {
        return (int) Math.max(normal.width, selected.width);
    }

    @Override
    public int getDrawOffset(int maxWidth, boolean isSelected) {
        float currentWidth = (isSelected && selectable) ? selected.width : normal.width;
        return (int) ((maxWidth - currentWidth) / 2f);
    }

    @Override
    public int getHeight() {
        return (int) normal.height;
    }

    @Override
    public void activate() {
        if (action != null) action.run();
    }
}


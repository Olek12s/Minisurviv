package io.core.ui;

public abstract class Entry {
    protected boolean selectable;
    protected boolean centered;

    public boolean isSelectable() { return selectable; }
    public boolean isCentered() { return centered; }

    public abstract void render(int x, int y, boolean selected);
    public abstract int getWidth();
    public abstract int getHeight();

    public int getDrawOffset(int maxWidth, boolean selected) {
        return 0;
    }

    public void activate() {}
}

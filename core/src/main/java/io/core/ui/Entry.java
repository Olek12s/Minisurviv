package io.core.ui;

public abstract class Entry
{
    protected boolean selectable;



    public boolean isSelectable() {
        return selectable;
    }


    public abstract void render(int x, int y, boolean selected);


    public abstract int getWidth();


    public abstract int getHeight();


    public void activate() {
    }
}

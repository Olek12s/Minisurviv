package io.core.ui;

import io.core.core.Input;

import java.util.List;

public class Display
{
    protected int x, y; // window position
    protected int width, height;

    List<Menu> menus;
    Display parent;
    boolean canExit;
    boolean visible;

    public void tick() {
        if (canExit && Input.isJustPressed(Input.Keys.ESCAPE)) {
            close();
            return;
        }

        for (Menu m : menus)
            m.tick(input);
    }

}

package io.core.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.core.core.Input;
import io.core.core.Renderer;

import java.util.List;


public class Display
{
    protected static TextureRegion windowRegion = Renderer.getHudTextureAtlas().findRegion("window");
    protected static NinePatch windowPatch = new NinePatch(windowRegion, 24, 24, 24, 24);


    protected int x, y; // window position
    protected int width, height;

    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public void setWidth(int width) {this.width = width;}
    public void setHeight(int height) {this.height = height;}

    List<Menu> menus;
    Display parent;
    boolean canExit;
    boolean visible;

    public void render() {
        Renderer.renderWindowNinePatch(windowPatch, width, height, x, y);
        System.out.println("rendering window: x: " + x + ", y: " + y + " width: " + width + " height: " + height);
    }

    public void tick() {
    }

}

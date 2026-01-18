package io.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import io.core.core.Input;
import io.core.core.Renderer;

import java.util.ArrayList;
import java.util.List;


public class Display
{
    protected static TextureRegion windowRegion = Renderer.getHudTextureAtlas().findRegion("window");
    protected static NinePatch windowPatch = new NinePatch(windowRegion, 8,8,8,8);
    protected static BitmapFont font;

    private int padding = 4;
    private boolean selected;
    private final List<Menu> menus = new ArrayList<>();   // menu is a container for UI entries such as text or UI inventory slots
    private int activeMenu = 0;

    protected int x, y; // window position
    protected int width, height;


    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecrafty/Regular.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter ftf = new FreeTypeFontGenerator.FreeTypeFontParameter();
        ftf.size = 10;
        font = generator.generateFont(ftf);
    }

    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public void setWidth(int width) {this.width = width;}
    public void setHeight(int height) {this.height = height;}

    public void addMenu(Menu menu) {
        menus.add(menu);
    }

    public Menu getActiveMenu() {
        return menus.get(activeMenu);
    }

    protected void centerOnScreen() {
        float w = Renderer.getVWWidth();
        float h = Renderer.getVWHeight();

        this.x = (int)(w / 2f - width / 2f);
        this.y = (int)(h / 2f - height / 2f);
    }

    public void layoutToContent() {
        int maxW = 0;
        int maxH = 0;

        for (Menu m : menus) {
            maxW = Math.max(maxW, m.getRequiredWidth());
            maxH = Math.max(maxH, m.getRequiredHeight());
        }

        width  = Math.max(width, maxW + padding * 2);
        height = Math.max(height, maxH + padding * 2);
    }


    public void tick() {
        if (menus.isEmpty()) return;
        getActiveMenu().tick();
    }

    public void setMinimumSize(int minWidth, int minHeight) {
        width = Math.max(width, minWidth);
        height = Math.max(height, minHeight);
    }


    public void render() {
        Renderer.renderWindowNinePatch(windowPatch, width, height, x, y);

        if (menus.isEmpty()) return;

        Menu menu = getActiveMenu();

        int menuX = x + (width - menu.getRequiredWidth()) / 2;
        int menuY = y + (height + menu.getRequiredHeight()) / 2;

        menu.render(menuX, menuY);
    }

    public static class Builder {
        private final Display display;
        private final Menu menu = new Menu();
        private Entry lastEntry;

        public Builder(Display display) {
            this.display = display;
        }

        public Builder addEntry(Entry entry) {
            menu.addEntry(entry);
            lastEntry = entry;
            return this;
        }

        public Builder centered() {
            if (lastEntry != null)
                lastEntry.centered = true;
            return this;
        }

        public Builder selectable(boolean value) {
            if (lastEntry != null)
                lastEntry.selectable = value;
            return this;
        }

        public Display build(boolean centerOnScreen) {
            display.addMenu(menu);
            display.layoutToContent();
            if (centerOnScreen) display.centerOnScreen();
            return display;
        }
    }


//    public void render() {
//        Renderer.renderWindowNinePatch(windowPatch, width, height, x, y);
//
//        if (menus.isEmpty()) return;
//
//        getActiveMenu().render(
//                x + padding,
//                y + height - padding
//        );
//    }
}

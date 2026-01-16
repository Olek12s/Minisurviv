package io.core.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.core.core.Renderer;

public abstract class UIDisplay
{
    protected Stage stage;
    protected UIDisplay parent;
    protected boolean blocksGameInput = true;

    public UIDisplay(Viewport viewport, UIDisplay parent) {
        this.stage = new Stage(viewport, Renderer.spriteBatch);
        this.parent = parent;
        build();
    }

    public void show() {}
    public void hide() {}

    // creating windows and other elements [...]
    protected abstract void build();

    public void tick() {
        stage.act();
    }

    public void render() {
        stage.draw();
    }

    public boolean blocksGameInput() {
        return blocksGameInput;
    }

    public UIDisplay getParent() {
        return parent;
    }

    public void dispose() {
        stage.dispose();
    }
}

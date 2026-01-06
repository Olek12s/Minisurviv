package io.core.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import io.core.core.Renderer;
import io.core.level.Level;
import io.core.util.Direction;

import java.util.EnumMap;

public abstract class Mob extends Entity
{
    protected Direction facingDirection = Direction.UP;
    protected EnumMap<Direction, Animation<TextureRegion>> animations;
    protected float animStateTime = 0f;
    private float animSpeed = 0.15f;


    protected float movSpeed = 3f / 60; // TODO: change it for better format. now it moves 3 tiles / sec

    public TextureRegion getCurrentFrame() {
        return animations.get(facingDirection).getKeyFrame(animStateTime);
    }


    public Mob() {

        // default hitbox values for mob
        this.hitboxWidth = 16f;
        this.hitboxHeight = 16f;
        this.hitboxOffsetX = (24f - hitboxWidth) /2;
        this.hitboxOffsetY = (24f - hitboxHeight) /2;
    }

    protected boolean move(float xd, float yd, boolean changeDirection) {
        if (level == null) return false;

        if (changeDirection) facingDirection = Direction.getDirection(xd, yd);

        boolean moved;
        moved = super.move(xd, yd);
        return moved;
    }

    public void render() {
        TextureRegion frame = getCurrentFrame();

        Renderer.renderEntity(frame, x, y);
    }

    public void tick(Level level) {

    }

    /**
     * Loads mob's animation spritesheet. Spritesheet should be ordered as:
     * -DOWN
     * -LEFT
     * -RIGHT
     * -UP
     *
     * Standard values form frameCols and frameRows are 3 and 4.
     */
    protected void loadAnimations(TextureRegion sheet, int frameCols, int frameRows) {

        int frameWidth = sheet.getRegionWidth() / frameCols;
        int frameHeight = sheet.getRegionHeight() / frameRows;

        TextureRegion[][] tmp = sheet.split(frameWidth, frameHeight);

        animations = new EnumMap<>(Direction.class);

        animations.put(Direction.DOWN,
                new Animation<>(animSpeed, tmp[0]));
        animations.put(Direction.LEFT,
                new Animation<>(animSpeed, tmp[1]));
        animations.put(Direction.RIGHT,
                new Animation<>(animSpeed, tmp[2]));
        animations.put(Direction.UP,
                new Animation<>(animSpeed, tmp[3]));

        for (Animation<TextureRegion> a : animations.values()) {
            a.setPlayMode(Animation.PlayMode.LOOP);
        }
    }
}

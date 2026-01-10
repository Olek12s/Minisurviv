package io.core.entity.mob;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.core.core.Renderer;
import io.core.entity.Entity;
import io.core.level.Level;
import io.core.util.Direction;

import java.util.EnumMap;

public abstract class Mob extends Entity
{
    protected Direction facingDirection = Direction.UP;
    protected EnumMap<Direction, Animation<TextureRegion>> animations;
    protected int animTick = 0;
    protected int animFrame = 0;
    private static final float FRAME_CHANGE_INTERVAL = 0.1f;
    private static final int FRAME_TICKS = (int)(FRAME_CHANGE_INTERVAL * 60);
    protected boolean isWalking = false;



    protected float movSpeed = 3f / 60; // TODO: change it for better format. now it moves 3 tiles / sec

    public TextureRegion getCurrentFrame() {
        return animations.get(facingDirection).getKeyFrame(animFrame);
    }


    public Mob() {

        // default hitbox values for mob
        this.hitboxWidth = 16f;
        this.hitboxHeight = 16f;
        this.hitboxOffsetX = (24f - hitboxWidth) /2;
        this.hitboxOffsetY = (24f - hitboxHeight) /2;

        collidabe = true;
    }

    protected boolean move(float xd, float yd, boolean changeDirection) {
        if (level == null || (xd == 0 && yd == 0)) {
            isWalking= false;
            return false;
        }

        if (changeDirection) facingDirection = Direction.getDirection(xd, yd);

        boolean moved = super.move(xd, yd);
        isWalking = moved;
        return moved;
    }

    public void render() {
        TextureRegion frame = getCurrentFrame();

        Renderer.renderEntity(frame, x, y);
    }

    protected void updateWalkingAnimation() {
        if (!isWalking) {
            animFrame = 0; // idle = frame 0
            return;
        }

        animTick++;

        if (animTick >= FRAME_TICKS) {
            animTick = 0;
            animFrame++;

            // while walking - skip standing frame
            if (animFrame < 1 || animFrame >= 3) {
                animFrame = 1;
            }
        }
    }

    protected void randomWalk() {
        // entity can change direction randomly once per x seconds
        boolean directionChange = random.nextInt(6 * 60) == 0;


        if (directionChange) {
            facingDirection = Direction.randomDirection();

            // entity can also start walking randomly when changing direction
            if (!isWalking && random.nextInt(5) == 0) {
                isWalking = true;
                return;
            }
        }
        // entity can start walking randomly once per x seconds
        if (!isWalking) {
            if (random.nextInt(8 * 60) == 0) isWalking = true;
        }
        // entity can stop walking randomly once per x seconds
        else {
            if (random.nextInt(6 * 60) == 0) isWalking = false;
        }
    }



    public void tick(Level level) {


        updateWalkingAnimation();
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
                new Animation<>(1f, tmp[0]));
        animations.put(Direction.LEFT,
                new Animation<>(1f, tmp[1]));
        animations.put(Direction.RIGHT,
                new Animation<>(1f, tmp[2]));
        animations.put(Direction.UP,
                new Animation<>(1f, tmp[3]));

        for (Animation<TextureRegion> a : animations.values()) {
            a.setPlayMode(Animation.PlayMode.LOOP);
        }
    }
}

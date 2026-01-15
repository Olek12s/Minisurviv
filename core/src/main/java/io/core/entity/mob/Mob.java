package io.core.entity.mob;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.core.core.Input;
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
    protected boolean walking = false;              // is entity walking on its own
    protected boolean randomWalkingActive = true;   // can entity perform random walk (used for ticking randomWalk() method)
    protected boolean wantsToWalk = false;          // intention of walking, not physical state



    protected float movSpeed = 3f / 60; // TODO: change it for better format. now it moves 3 tiles / sec

    public TextureRegion getCurrentFrame() {
        return animations.get(facingDirection).getKeyFrame(animFrame);
    }


    public Mob() {

        // default hitbox values for mob
        setHitboxSize(16, 16);

        collidabe = true;
    }

    protected boolean move(float xd, float yd, boolean changeDirection) {
        if (level == null || (xd == 0 && yd == 0)) {
            walking = false;
            return false;
        }

        if (changeDirection) facingDirection = Direction.getDirection(xd, yd);

        boolean moved = super.move(xd, yd);
        walking = moved;
        return moved;
    }

    public void render() {
        TextureRegion frame = getCurrentFrame();

        Renderer.renderEntity(frame, x, y);
    }

    protected void updateWalkingAnimation() {
        if (!walking) {
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

    /**
     *  Performs random walk for Mob. Mob randomly changes direction and may start walking in that direction based on given parameters.
     * @param dirChangeDivider - How rare is direction changing
     * @param toStartWalkDivider - How rare should entity start having intention to walk
     * @param toStopWalkDivider - How rare should entity stop having intetion to walk
     */
    protected void randomWalk(int dirChangeDivider, int toStartWalkDivider, int toStopWalkDivider) {
        // entity can change direction randomly once per x seconds
        boolean directionChange = random.nextInt(6 * dirChangeDivider) == 0;

        if (directionChange) {
            facingDirection = Direction.randomDirection();
            // entity can also start walking randomly when changing direction
            if (!wantsToWalk && random.nextInt(3) == 0) {
                wantsToWalk = true;
                return;
            }
        }
        // entity can start walking randomly once per x seconds
        if (!wantsToWalk) {
            if (random.nextInt(toStartWalkDivider * 60) == 0) wantsToWalk = true;
        }
        // entity can stop walking randomly once per x seconds
        else {
            if (random.nextInt(4 * toStopWalkDivider) == 0) {
                wantsToWalk = false;
            }
        }
    }



    public void tick(Level level) {


        updateWalkingAnimation();

        // Behavior overriden (used) by both a player and NPCS
        // is above this line. Everything below SHOULD NOT be used by Player class
        if ((this instanceof Player))
        {
            return;
        }
        if (randomWalkingActive) {
            randomWalk(9, 7, 8);
        }

        int x = 0; // facing vector X
        int y = 0; // facing vector Y

        if (facingDirection == Direction.LEFT) x--;
        if (facingDirection == Direction.RIGHT) x++;
        if (facingDirection == Direction.UP) y++;
        if (facingDirection == Direction.DOWN) y--;

        float xd = 0;
        float yd = 0;

        if (wantsToWalk) {
            xd = x * movSpeed;
            yd = y * movSpeed;
        }

        boolean moved = move(xd, yd, true); // Mobs's moved in this method
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

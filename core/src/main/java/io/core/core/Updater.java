package io.core.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.core.entity.mob.Player;
import io.core.level.LevelsManager;

import static com.badlogic.gdx.Gdx.input;

public class Updater {

    public static boolean tickGame = false;

    /**
     *
     *  Executes single tick in the Level player is currently at. Second method in a main-loop IUR pipeline (Input-Update-Render)
     */
    public static void tick() {

        if (!tickGame) return;  // Don't call ticks unless game has started
        Player player = LevelsManager.getCurrentLevel().getFirstPlayer();
        if (player == null) return;




        if (input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            return; // preventing further ticks from game - After all, It's a Pause
        }

        // camera is linked to the player's character
        CameraController.followSmooth(player);
        LevelsManager.getCurrentLevel().tick();


    }
}

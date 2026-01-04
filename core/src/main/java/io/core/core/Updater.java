package io.core.core;

import com.badlogic.gdx.Gdx;
import io.core.entity.Player;
import io.core.level.LevelsManager;

import java.util.Optional;

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

        LevelsManager.getCurrentLevel().tick();

        // camera is linked to the player's character
        CameraController.followSmooth(player);


    }
}

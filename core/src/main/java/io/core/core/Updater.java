package io.core.core;

import com.badlogic.gdx.Gdx;
import io.core.entity.mob.Player;
import io.core.level.LevelsManager;
import io.core.ui.ExitDisplay;
import io.core.ui.PauseDisplay;
import io.core.ui.UIManager;

import static com.badlogic.gdx.Gdx.input;

public class Updater {

    public static boolean tickGame = false;

    /**
     *
     *  Executes single tick in the Level player is currently at. Second method in a main-loop IUR pipeline (Input-Update-Render)
     */
    public static void tick() {
        UIManager.tick();

        // ===== DISPLAYS =====
        if (Input.isJustPressed(Input.Keys.ESCAPE)) {
            if (UIManager.alreadyOpen(ExitDisplay.class)) {
                UIManager.close(ExitDisplay.class);
            } else {
                UIManager.open(ExitDisplay.class);
            }
        }

        if (Input.isJustPressed(Input.Keys.P)) {
            if (!Flow.PAUSED) {
                UIManager.open(PauseDisplay.class);
                Flow.pauseGame();  // this method pauses most of the game's ticks. Camera and some ticks for HUD should still be ticked tho
            } else {
                UIManager.close(PauseDisplay.class);
                Flow.resumeGame();  // renew game ticks
            }
        }

        if (!tickGame) return;  // Don't call further ticks unless game has started or unpaused

        Player player = LevelsManager.getCurrentLevel().getFirstPlayer();
        if (player == null) return;
        CameraController.followSmooth(player);
        LevelsManager.getCurrentLevel().tick();
    }
}

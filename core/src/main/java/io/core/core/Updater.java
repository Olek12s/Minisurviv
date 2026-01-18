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
                Flow.resumeGame();
            } else {
                UIManager.open(ExitDisplay.class);
                Flow.pauseGame();
            }
        }

        if (Input.isJustPressed(Input.Keys.P) && !UIManager.alreadyOpen(ExitDisplay.class)) {
            if (!Flow.PAUSED) {
                UIManager.open(PauseDisplay.class);
                Flow.pauseGame();  // this method pauses most of the game's ticks. Camera and some ticks for HUD should still be ticked tho
            } else {
                UIManager.close(PauseDisplay.class);
                Flow.resumeGame();  // renew game ticks
            }
        }
        syncPauseState();

        if (!tickGame) return;  // Don't call further ticks unless game has started or unpaused

        Player player = LevelsManager.getCurrentLevel().getFirstPlayer();
        if (player == null) return;
        CameraController.followSmooth(player);
        LevelsManager.getCurrentLevel().tick();
    }



    /**
     * Synchronizes the global game pause state with the current UI displays stack.
     *
     * This method ensures that the game logic pause/resume state (Flow.PAUSED, Updater.tickGame)
     * is always consistent with the current open UI displays that block gameplay.
     *
     * If at least one blocking Display is open, the game is forced into paused state.
     * If no blocking Displays are open, the game is automatically resumed.
     *
     * This prevents desynchronization bugs, for example when external events
     * (like window resize, focus loss, or display removal outside of input flow)
     * close or open UI displays without going through the standard pause/resume logic.
     *
     * Note: non-blocking Displays (e.g., HUD overlays) will not trigger pause.
     */
    private static void syncPauseState() {
        boolean blockingOpen = UIManager.alreadyOpen(PauseDisplay.class) || UIManager.alreadyOpen(ExitDisplay.class);

        if (blockingOpen) {
            if (!Flow.PAUSED) Flow.pauseGame();
        } else {
            if (Flow.PAUSED) Flow.resumeGame();
        }
    }
}

package io.core.core;

import io.core.level.LevelsManager;

public class Updater {

    public static boolean tickGame = false;

    /**
     *
     *  Executes single tick in the Level player is currently at. Second method in a main-loop IUR pipeline (Input-Update-Render)
     */
    public static void tick() {
        if (!tickGame) return;  // Don't call ticks unless game has started

        LevelsManager.getCurrentLevel().tick();
    }
}

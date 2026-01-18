package io.core.core;

import io.core.level.LevelsManager;
import io.core.screens.MainMenuScreen;
import io.core.ui.UIManager;

public class Flow {
    public static boolean PAUSED;

    public static void exitToMainMenu() {
        Minisurviv game = Minisurviv.get();
        if (game == null) return;

        // stop game
        Renderer.renderGame = false;
        Updater.tickGame = false;

        UIManager.dispose();

        LevelsManager.dispose();

        game.setScreen(new MainMenuScreen(game));           // switch game screen to the main menu
        PAUSED = false;
    }

    public static void pauseGame() {
        Updater.tickGame = false;
        PAUSED = true;
    }

    public static void resumeGame() {
        Updater.tickGame = true;
        PAUSED = false;
    }
}


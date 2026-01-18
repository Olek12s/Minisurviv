package io.core.core;

import io.core.level.LevelsManager;
import io.core.screens.MainMenuScreen;

public class Flow {

    public static void exitToMainMenu() {
        Minisurviv game = Minisurviv.get();
        if (game == null) return;

        // stop game
        Renderer.renderGame = false;
        Updater.tickGame = false;


        LevelsManager.dispose();

        game.setScreen(new MainMenuScreen(game));           // switch game screen to the main menu
    }
}


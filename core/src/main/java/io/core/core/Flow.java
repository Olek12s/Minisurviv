package io.core.core;

import io.core.screens.MainMenuScreen;

public class Flow {

    public static void goToMainMenu() {
        Minisurviv game = Minisurviv.get();

        if (game == null) return;

        Renderer.renderGame = false;
        game.setScreen(new MainMenuScreen(game));
    }
}


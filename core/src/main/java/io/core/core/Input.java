package io.core.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import java.util.HashMap;
import java.util.Map;

public class Input {
    public static final Map<Integer, Boolean> keysJustPressed = new HashMap<>();
    public static final Map<Integer, Boolean> keysHeld = new HashMap<>();



    public static class Keys {
        public static final int A = com.badlogic.gdx.Input.Keys.A;
        public static final int B = com.badlogic.gdx.Input.Keys.B;
        public static final int C = com.badlogic.gdx.Input.Keys.C;
        public static final int D = com.badlogic.gdx.Input.Keys.D;
        public static final int E = com.badlogic.gdx.Input.Keys.E;
        public static final int F = com.badlogic.gdx.Input.Keys.F;
        public static final int G = com.badlogic.gdx.Input.Keys.G;
        public static final int H = com.badlogic.gdx.Input.Keys.H;
        public static final int I = com.badlogic.gdx.Input.Keys.I;
        public static final int J = com.badlogic.gdx.Input.Keys.J;
        public static final int K = com.badlogic.gdx.Input.Keys.K;
        public static final int L = com.badlogic.gdx.Input.Keys.L;
        public static final int M = com.badlogic.gdx.Input.Keys.M;
        public static final int N = com.badlogic.gdx.Input.Keys.N;
        public static final int O = com.badlogic.gdx.Input.Keys.O;
        public static final int P = com.badlogic.gdx.Input.Keys.P;
        public static final int Q = com.badlogic.gdx.Input.Keys.Q;
        public static final int R = com.badlogic.gdx.Input.Keys.R;
        public static final int S = com.badlogic.gdx.Input.Keys.S;
        public static final int T = com.badlogic.gdx.Input.Keys.T;
        public static final int U = com.badlogic.gdx.Input.Keys.U;
        public static final int V = com.badlogic.gdx.Input.Keys.V;
        public static final int W = com.badlogic.gdx.Input.Keys.W;
        public static final int X = com.badlogic.gdx.Input.Keys.X;
        public static final int Y = com.badlogic.gdx.Input.Keys.Y;
        public static final int Z = com.badlogic.gdx.Input.Keys.Z;

        public static final int NUM_0 = com.badlogic.gdx.Input.Keys.NUM_0;
        public static final int NUM_1 = com.badlogic.gdx.Input.Keys.NUM_1;
        public static final int NUM_2 = com.badlogic.gdx.Input.Keys.NUM_2;
        public static final int NUM_3 = com.badlogic.gdx.Input.Keys.NUM_3;
        public static final int NUM_4 = com.badlogic.gdx.Input.Keys.NUM_4;
        public static final int NUM_5 = com.badlogic.gdx.Input.Keys.NUM_5;
        public static final int NUM_6 = com.badlogic.gdx.Input.Keys.NUM_6;
        public static final int NUM_7 = com.badlogic.gdx.Input.Keys.NUM_7;
        public static final int NUM_8 = com.badlogic.gdx.Input.Keys.NUM_8;
        public static final int NUM_9 = com.badlogic.gdx.Input.Keys.NUM_9;

        public static final int NUMPAD_0 = com.badlogic.gdx.Input.Keys.NUMPAD_0;
        public static final int NUMPAD_1 = com.badlogic.gdx.Input.Keys.NUMPAD_1;
        public static final int NUMPAD_2 = com.badlogic.gdx.Input.Keys.NUMPAD_2;
        public static final int NUMPAD_3 = com.badlogic.gdx.Input.Keys.NUMPAD_3;
        public static final int NUMPAD_4 = com.badlogic.gdx.Input.Keys.NUMPAD_4;
        public static final int NUMPAD_5 = com.badlogic.gdx.Input.Keys.NUMPAD_5;
        public static final int NUMPAD_6 = com.badlogic.gdx.Input.Keys.NUMPAD_6;
        public static final int NUMPAD_7 = com.badlogic.gdx.Input.Keys.NUMPAD_7;
        public static final int NUMPAD_8 = com.badlogic.gdx.Input.Keys.NUMPAD_8;
        public static final int NUMPAD_9 = com.badlogic.gdx.Input.Keys.NUMPAD_9;

        public static final int ENTER = com.badlogic.gdx.Input.Keys.ENTER;
        public static final int SPACE = com.badlogic.gdx.Input.Keys.SPACE;
        public static final int ESCAPE = com.badlogic.gdx.Input.Keys.ESCAPE;
        public static final int TAB = com.badlogic.gdx.Input.Keys.TAB;
        public static final int BACKSPACE = com.badlogic.gdx.Input.Keys.BACKSPACE;
        public static final int SHIFT_LEFT = com.badlogic.gdx.Input.Keys.SHIFT_LEFT;
        public static final int SHIFT_RIGHT = com.badlogic.gdx.Input.Keys.SHIFT_RIGHT;
        public static final int CONTROL_LEFT = com.badlogic.gdx.Input.Keys.CONTROL_LEFT;
        public static final int CONTROL_RIGHT = com.badlogic.gdx.Input.Keys.CONTROL_RIGHT;
        public static final int ALT_LEFT = com.badlogic.gdx.Input.Keys.ALT_LEFT;
        public static final int ALT_RIGHT = com.badlogic.gdx.Input.Keys.ALT_RIGHT;

        public static final int UP = com.badlogic.gdx.Input.Keys.UP;
        public static final int DOWN = com.badlogic.gdx.Input.Keys.DOWN;
        public static final int LEFT = com.badlogic.gdx.Input.Keys.LEFT;
        public static final int RIGHT = com.badlogic.gdx.Input.Keys.RIGHT;
    }

    private static final Integer[] allKeys = {
            Keys.A, Keys.B, Keys.C, Keys.D, Keys.E, Keys.F, Keys.G, Keys.H, Keys.I, Keys.J,
            Keys.K, Keys.L, Keys.M, Keys.N, Keys.O, Keys.P, Keys.Q, Keys.R, Keys.S, Keys.T,
            Keys.U, Keys.V, Keys.W, Keys.X, Keys.Y, Keys.Z,
            Keys.NUM_0, Keys.NUM_1, Keys.NUM_2, Keys.NUM_3, Keys.NUM_4, Keys.NUM_5,
            Keys.NUM_6, Keys.NUM_7, Keys.NUM_8, Keys.NUM_9,
            Keys.NUMPAD_0, Keys.NUMPAD_1, Keys.NUMPAD_2, Keys.NUMPAD_3, Keys.NUMPAD_4,
            Keys.NUMPAD_5, Keys.NUMPAD_6, Keys.NUMPAD_7, Keys.NUMPAD_8, Keys.NUMPAD_9,
            Keys.ENTER, Keys.SPACE, Keys.ESCAPE, Keys.TAB, Keys.BACKSPACE,
            Keys.SHIFT_LEFT, Keys.SHIFT_RIGHT, Keys.CONTROL_LEFT, Keys.CONTROL_RIGHT,
            Keys.ALT_LEFT, Keys.ALT_RIGHT, Keys.UP, Keys.DOWN, Keys.LEFT, Keys.RIGHT
    };

    /**
     *
     *  Listens user's controller inputs. First method in a main-loop IUR pipeline (Input-Update-Render)
     */
    public static void listen() {
        for (int key : allKeys) {
            boolean currentlyPressed = Gdx.input.isKeyPressed(key);
            boolean wasHeld = keysHeld.getOrDefault(key, false);

            if (currentlyPressed && !wasHeld) {
                keysJustPressed.put(key, true);
            }

            keysHeld.put(key, currentlyPressed);
        }
    }

    /**
     * Sets every just-clicked button from true to false every tick.
     * This method should be called ONLY after performing all the ticks in the main game loop
     */
    protected static void resetJustClickedFlag() {
        for (int key : allKeys) {
            keysJustPressed.put(key, false);
        }
    }

    public static boolean isJustPressed(int key) {
        return keysJustPressed.getOrDefault(key, false);
    }

    public static boolean isHeld(int key) {
        return keysHeld.getOrDefault(key, false);
    }
}

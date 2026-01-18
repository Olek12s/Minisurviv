package io.core.ui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class UIManager
{
    //private static final ArrayDeque<Display> displayStack = new ArrayDeque<>();
    private static final List<Display> displays = new ArrayList<>();

    public static void open(Display display) {
        if (display == null) return;
        if (alreadyOpen(display.getClass())) return;

        displays.add(display);
        System.out.println("[Display] Opened: " + display.toString());
    }

    public static boolean alreadyOpen(Class<? extends Display> cls) {
        for (Display d : displays) {
            if (d.getClass() == cls) return true;
        }
        return false;
    }

    public static void closeTopDisplay() {
        if (!displays.isEmpty()) {
            System.out.println("[Display] Closed: " + displays.getLast().toString());
            displays.removeLast();
        }
    }

    // null if empty, otherwise get last element of dequeue
    public static Display getTopDisplay() {
        return displays.isEmpty() ? null : displays.getLast();
    }

    public static void tick() {
        if (getTopDisplay() != null) {
            getTopDisplay().tick();
        }
    }

    public static void render() {
        for (Display display : displays) {
            display.render();
        }
    }
}

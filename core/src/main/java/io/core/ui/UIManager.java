package io.core.ui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class UIManager
{
    //private static final ArrayDeque<Display> displayStack = new ArrayDeque<>();
    protected static final List<Display> displays = new ArrayList<>();

    public static <T extends Display> T open(Class<T> cls) {
        for (Display d : displays) {
            if (d.getClass() == cls) {
                return cls.cast(d);
            }
        }

        try {
            T display = cls.getDeclaredConstructor().newInstance();
            displays.add(display);
            System.out.println("[Display] Opened: " + display.toString());
            return display;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean alreadyOpen(Class<? extends Display> cls) {
        for (Display d : displays) {
            if (d.getClass() == cls) return true;
        }
        return false;
    }

    public static <T extends Display> void close(Class<T> cls) {
        for (int i = displays.size() - 1; i >= 0; i--) {
            Display d = displays.get(i);
            if (d.getClass() == cls) {
                System.out.println("[Display] Closed: " + d.toString());
                displays.remove(i);
                return;
            }
        }
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

    public static void dispose() {
        displays.clear();
    }
}

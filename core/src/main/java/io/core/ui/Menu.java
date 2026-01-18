package io.core.ui;

import io.core.core.Input;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final List<Entry> entries = new ArrayList<>();
    private int selectedIndex = 0;
    private int spacing = 8;

    public void addEntry(Entry e) {
        entries.add(e);
    }

    public void render(int x, int y) {
        int offsetY = 0;
        int maxWidth = getRequiredWidth();

        for (int i = 0; i < entries.size(); i++) {
            Entry e = entries.get(i);
            boolean selected = (i == selectedIndex);

            int drawX = x;

            if (e.isCentered()) {
                drawX += e.getDrawOffset(maxWidth, selected);
            }

            e.render(drawX, y - offsetY, selected);
            offsetY += e.getHeight() + spacing;
        }
    }

    public void tick() {
        if (Input.isJustPressed(Input.Keys.W) || Input.isJustPressed(Input.Keys.UP))
            move(-1);

        if (Input.isJustPressed(Input.Keys.S) || Input.isJustPressed(Input.Keys.DOWN))
            move(1);

        if (Input.isJustPressed(Input.Keys.SPACE) || Input.isJustPressed(Input.Keys.ENTER))
            entries.get(selectedIndex).activate();
    }

    private void move(int dir) {
        if (entries.isEmpty()) return;

        int startIndex = selectedIndex;
        do {
            selectedIndex = (selectedIndex + dir + entries.size()) % entries.size();

            if (selectedIndex == startIndex && !entries.get(selectedIndex).isSelectable()) {
                return;
            }

        } while (!entries.get(selectedIndex).isSelectable());
    }

    public int getRequiredWidth() {
        int max = 0;
        for (Entry e : entries)
            max = Math.max(max, e.getWidth());
        return max;
    }

    public int getRequiredHeight() {
        if (entries.isEmpty()) return 0;

        int sum = -spacing;
        for (Entry e : entries)
            sum += e.getHeight() + spacing;

        return sum;
    }
}


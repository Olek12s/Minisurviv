package io.core.entity.item;

import java.util.ArrayList;

public class Items
{
    private static final ArrayList<Item> items = new ArrayList<>();

    private static void add(Item item) {
        items.add(item);
    }

    public static Item get(ItemId itemId) {
        for (Item item : items) {
            if (item.getId() == itemId) {
                return item;
            }
        }
        return null; // if not found
    }
}

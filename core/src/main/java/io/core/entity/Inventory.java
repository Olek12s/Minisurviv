package io.core.entity;

import io.core.entity.item.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory
{
    private List<Item> items = new ArrayList<>();
    public static int MAX_SIZE = 16;

    public List<Item> getItems() {return items;}


     /**
     * Removes one unit of the specified item from the inventory.
     * If the item's amount reaches 0 or less, the item is removed completely.
     * @param item - item to be removed
     */
    public void removeItem(Item item) {
        forceRemoveItem(item, 1);
    }

     /**
     * Removes the specified quantity of the given item from the inventory.
     * If the item's amount reaches 0 or less, the item is removed completely.
     * @param item - item to be removed
     * @param quantity - amount of item to remove
     */
    public void forceRemoveItem(Item item, int quantity) {
        removeIfAvailable(item, quantity, false);
    }

    public void removeIfAvailable(Item item, int amount) {
        removeIfAvailable(item, amount, true);
    }

    /**
     *  Safe method to remove specified quantity of the given item from the inventory.
     *  If the amount in inventory is smaller than amount to be removed - nothing happens and method returns false.
     * @param item - item to be removed
     * @param amount - amount of item to remove
     * @return - returns true if removed items, false otherwise.
     */
    boolean removeIfAvailable(Item item, int amount, boolean check) {
        int total = 0;

        if (check) {
            // count total
            for (Item i : items) {
                if (i.equals(item)) {
                    total += i.getAmount();
                }
            }
            if (total < amount) return false;
        }

        int toRemove = amount;
        for (Iterator<Item> it = items.iterator(); it.hasNext() && toRemove > 0; ) {
            Item i = it.next();
            if (i.equals(item)) {
                int available = i.getAmount();

                if (available <= toRemove) {
                    toRemove -= available;
                    it.remove(); // remove stack
                }
                else {
                    i.setAmount(available - toRemove);
                    toRemove = 0;
                }
            }
        }
        return true;
    }

    public boolean addItemIfPossible(Item item) {
        int amountToAdd = item.getAmount();

        // add to the existing stacks
        for (Item i : items) {
            if (i.getId() == item.getId() && i.getAmount() < i.getMaxStack()) {
                int space = i.getMaxStack() - i.getAmount();
                int added = Math.min(space, amountToAdd);
                i.setAmount(i.getAmount() + added);
                amountToAdd -= added;
                if (amountToAdd == 0) return true; // everything was distributed
            }
        }

        // create new stack in inventory
        if (amountToAdd > 0) {
            if (items.size() < MAX_SIZE) {
                item.setAmount(amountToAdd);
                items.add(item);
                return true;
            } else {
                return false; // no space
            }
        }
        return true;
    }


}

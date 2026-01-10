package io.core.entity.item;

public enum ItemId {
    EGG("Egg"),
    APPLE("Apple");

    private final String itemName;

    ItemId(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}

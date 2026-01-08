package io.core.entity.item;

public class Egg extends Item
{

    public Egg() {
        super(ItemId.EGG);
    }

    @Override
    public void render() {
        super.render(x, y);
    }
}

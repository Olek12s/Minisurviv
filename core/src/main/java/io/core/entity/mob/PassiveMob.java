package io.core.entity.mob;

import io.core.level.Level;

public abstract class PassiveMob extends Mob
{
    public PassiveMob(int maxHealth) {
        super(maxHealth);
    }

    @Override
    public void tick(Level level) {
        super.tick(level);
    }
}

package io.core.entity.mob;

import io.core.level.Level;

public class HostileMob extends Mob
{
    public HostileMob(int maxHealth) {
        super(maxHealth);
    }

    @Override
    public void tick(Level level) {
        super.tick(level);
    }
}

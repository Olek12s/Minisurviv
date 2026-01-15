package io.core.entity.mob;

import io.core.level.Level;

public class NeutralMob extends Mob
{
    public NeutralMob(int maxHealth) {
        super(maxHealth);
    }

    @Override
    public void tick(Level level) {
        super.tick(level);
    }
}

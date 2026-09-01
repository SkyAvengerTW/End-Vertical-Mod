package com.endvertical;

import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class VoidWalker extends Monster {
    private int rangedCooldown = 0;

    public VoidWalker(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public void serverTick() {
        if (rangedCooldown > 0) rangedCooldown--;
        if (rangedCooldown == 0 && !level().isClientSide && getTarget() instanceof Player p) {
            double d = distanceTo(p);
            if (d >= 3.5 && d <= 25.0) {
                // Placeholder projectile hook: the ranged attack is intentionally isolated
                // so it can be implemented with the target version's projectile API.
                rangedCooldown = 20;
            }
        }
    }

    @Override
    public boolean hurtServer(DamageSource source, float amount) {
        // 2% chance to block an incoming hit completely.
        if (getRandom().nextFloat() < 0.02f) return false;
        return super.hurtServer(source, amount);
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            if (getRandom().nextBoolean()) {
                living.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100, 1));
            } else {
                living.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1));
            }
        }
        return hit;
    }
}

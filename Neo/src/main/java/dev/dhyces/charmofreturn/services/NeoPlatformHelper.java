package dev.dhyces.charmofreturn.services;

import dev.dhyces.charmofreturn.NeoCharmOfReturn;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.objecthunter.exp4j.Expression;

public class NeoPlatformHelper implements PlatformHelper {
    @Override
    public Expression getLevelCostExpression() {
        return NeoCharmOfReturn.CONFIG.levelCostExpression;
    }

    @Override
    public int getDurability() {
        return NeoCharmOfReturn.CONFIG.durability.get();
    }

    @Override
    public int getChargeTicks() {
        return NeoCharmOfReturn.CONFIG.chargeTime.get();
    }

    @Override
    public int getCooldownTicks() {
        return NeoCharmOfReturn.CONFIG.cooldownTime.get();
    }

    @Override
    public boolean isClientParticles() {
        return NeoCharmOfReturn.CONFIG.isClientParticles.get();
    }

    @Override
    public void teleportToDimension(Entity teleported, ServerLevel destination, PortalInfo target) {
        teleported.changeDimension(destination);
        teleported.teleportTo(target.pos.x, target.pos.y, target.pos.z);
    }
}

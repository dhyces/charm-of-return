package dhyces.ringofreturn.services;

import dhyces.ringofreturn.ForgeRingOfReturn;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.objecthunter.exp4j.Expression;

public class ForgePlatformHelper implements PlatformHelper {
    @Override
    public Expression getLevelCostExpression() {
        return ForgeRingOfReturn.CONFIG.levelCostExpression;
    }

    @Override
    public int getDurability() {
        return ForgeRingOfReturn.CONFIG.durability.get();
    }

    @Override
    public int getChargeTicks() {
        return ForgeRingOfReturn.CONFIG.chargeTime.get();
    }

    @Override
    public int getCooldownTicks() {
        return ForgeRingOfReturn.CONFIG.cooldownTime.get();
    }

    @Override
    public boolean isClientParticles() {
        return ForgeRingOfReturn.CONFIG.isClientParticles.get();
    }

    @Override
    public void teleportToDimension(Entity teleported, ServerLevel destination, PortalInfo target) {
        teleported.changeDimension(destination);
        teleported.teleportTo(target.pos.x, target.pos.y, target.pos.z);
    }
}

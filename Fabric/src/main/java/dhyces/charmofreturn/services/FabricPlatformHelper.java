package dhyces.charmofreturn.services;

import dhyces.charmofreturn.FabricCharmOfReturn;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.objecthunter.exp4j.Expression;

public class FabricPlatformHelper implements PlatformHelper {
    @Override
    public Expression getLevelCostExpression() {
        return FabricCharmOfReturn.config.getLevelCostExpression();
    }

    @Override
    public int getDurability() {
        return FabricCharmOfReturn.config.getDurability();
    }

    @Override
    public int getChargeTicks() {
        return FabricCharmOfReturn.config.getCharge();
    }

    @Override
    public int getCooldownTicks() {
        return FabricCharmOfReturn.config.getCooldown();
    }

    @Override
    public boolean isClientParticles() {
        return FabricCharmOfReturn.config.isClientParticles();
    }

    @Override
    public void teleportToDimension(Entity teleported, ServerLevel destination, PortalInfo target) {
        FabricDimensions.teleport(teleported, destination, target);
    }
}

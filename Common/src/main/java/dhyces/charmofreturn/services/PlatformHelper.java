package dhyces.charmofreturn.services;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.objecthunter.exp4j.Expression;

public interface PlatformHelper {

    Expression getLevelCostExpression();
    int getDurability();

    int getChargeTicks();

    int getCooldownTicks();

    boolean isClientParticles();

    void teleportToDimension(Entity teleported, ServerLevel destination, PortalInfo target);
}

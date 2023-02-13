package dhyces.ringofreturn.services;

import dhyces.ringofreturn.FabricRingOfReturn;

public class FabricConfigHelper implements ConfigHelper {
    @Override
    public String getExpressionString() {
        return FabricRingOfReturn.config.getLevelCostStr();
    }

    @Override
    public int getDurability() {
        return FabricRingOfReturn.config.getDurability();
    }

    @Override
    public int getCharge() {
        return FabricRingOfReturn.config.charge;
    }

    @Override
    public int getCooldown() {
        return FabricRingOfReturn.config.cooldown;
    }

    @Override
    public boolean isClientParticles() {
        return FabricRingOfReturn.config.isClientParticles;
    }

    @Override
    public void setExpressionString(String expression) {
        FabricRingOfReturn.config.setLevelCostStr(expression);
    }

    @Override
    public void setDurability(int durability) {
        FabricRingOfReturn.config.durability = durability;
    }

    @Override
    public void setCharge(int charge) {
        FabricRingOfReturn.config.charge = charge;
    }

    @Override
    public void setCooldown(int cooldown) {
        FabricRingOfReturn.config.cooldown = cooldown;
    }

    @Override
    public void setClientParticles(boolean bool) {
        FabricRingOfReturn.config.isClientParticles = bool;
    }

    @Override
    public void save() {
        FabricRingOfReturn.saveConfig();
    }
}

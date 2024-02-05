package dev.dhyces.charmofreturn.services;


import dev.dhyces.charmofreturn.FabricCharmOfReturn;

public class FabricConfigHelper implements ConfigHelper {
    @Override
    public String getExpressionString() {
        return FabricCharmOfReturn.config.getLevelCostStr();
    }

    @Override
    public int getDurability() {
        return FabricCharmOfReturn.config.getDurability();
    }

    @Override
    public int getCharge() {
        return FabricCharmOfReturn.config.charge;
    }

    @Override
    public int getCooldown() {
        return FabricCharmOfReturn.config.cooldown;
    }

    @Override
    public boolean isClientParticles() {
        return FabricCharmOfReturn.config.isClientParticles;
    }

    @Override
    public boolean isUseAnchorCharge() {
        return FabricCharmOfReturn.config.isUseAnchorCharge;
    }

    @Override
    public void setExpressionString(String expression) {
        FabricCharmOfReturn.config.setLevelCostStr(expression);
    }

    @Override
    public void setDurability(int durability) {
        FabricCharmOfReturn.config.durability = durability;
    }

    @Override
    public void setCharge(int charge) {
        FabricCharmOfReturn.config.charge = charge;
    }

    @Override
    public void setCooldown(int cooldown) {
        FabricCharmOfReturn.config.cooldown = cooldown;
    }

    @Override
    public void setClientParticles(boolean bool) {
        FabricCharmOfReturn.config.isClientParticles = bool;
    }

    @Override
    public void setUseAnchorCharge(boolean value) {
        FabricCharmOfReturn.config.isUseAnchorCharge = value;
    }

    @Override
    public void save() {
        FabricCharmOfReturn.saveConfig();
    }
}

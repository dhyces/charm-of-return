package dev.dhyces.charmofreturn.services;

import dev.dhyces.charmofreturn.NeoCharmOfReturn;

public class NeoConfigHelper implements ConfigHelper {
    @Override
    public String getExpressionString() {
        return NeoCharmOfReturn.CONFIG.levelCost.get();
    }

    @Override
    public int getDurability() {
        return NeoCharmOfReturn.CONFIG.durability.get();
    }

    @Override
    public int getCharge() {
        return NeoCharmOfReturn.CONFIG.chargeTime.get();
    }

    @Override
    public int getCooldown() {
        return NeoCharmOfReturn.CONFIG.cooldownTime.get();
    }

    @Override
    public boolean isClientParticles() {
        return NeoCharmOfReturn.CONFIG.isClientParticles.get();
    }

    @Override
    public boolean isUseAnchorCharge() {
        return NeoCharmOfReturn.CONFIG.isUseAnchorCharge.get();
    }

    @Override
    public void setExpressionString(String expression) {
        NeoCharmOfReturn.CONFIG.levelCost.set(expression);
        NeoCharmOfReturn.CONFIG.refreshExpression();
    }

    @Override
    public void setDurability(int durability) {
        NeoCharmOfReturn.CONFIG.durability.set(durability);
    }

    @Override
    public void setCharge(int charge) {
        NeoCharmOfReturn.CONFIG.chargeTime.set(charge);
    }

    @Override
    public void setCooldown(int cooldown) {
        NeoCharmOfReturn.CONFIG.cooldownTime.set(cooldown);
    }

    @Override
    public void setClientParticles(boolean value) {
        NeoCharmOfReturn.CONFIG.isClientParticles.set(value);
    }

    @Override
    public void setUseAnchorCharge(boolean value) {
        NeoCharmOfReturn.CONFIG.isUseAnchorCharge.set(value);
    }

    @Override
    public void save() {
        // NO-OP. Already dealt with by forge
    }
}

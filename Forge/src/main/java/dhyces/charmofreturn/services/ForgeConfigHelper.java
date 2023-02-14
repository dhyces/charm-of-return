package dhyces.charmofreturn.services;

import dhyces.charmofreturn.ForgeCharmOfReturn;

public class ForgeConfigHelper implements ConfigHelper {
    @Override
    public String getExpressionString() {
        return ForgeCharmOfReturn.CONFIG.levelCost.get();
    }

    @Override
    public int getDurability() {
        return ForgeCharmOfReturn.CONFIG.durability.get();
    }

    @Override
    public int getCharge() {
        return ForgeCharmOfReturn.CONFIG.chargeTime.get();
    }

    @Override
    public int getCooldown() {
        return ForgeCharmOfReturn.CONFIG.cooldownTime.get();
    }

    @Override
    public boolean isClientParticles() {
        return ForgeCharmOfReturn.CONFIG.isClientParticles.get();
    }

    @Override
    public boolean isUseAnchorCharge() {
        return ForgeCharmOfReturn.CONFIG.isUseAnchorCharge.get();
    }

    @Override
    public void setExpressionString(String expression) {
        ForgeCharmOfReturn.CONFIG.levelCost.set(expression);
        ForgeCharmOfReturn.CONFIG.refreshExpression();
    }

    @Override
    public void setDurability(int durability) {
        ForgeCharmOfReturn.CONFIG.durability.set(durability);
    }

    @Override
    public void setCharge(int charge) {
        ForgeCharmOfReturn.CONFIG.chargeTime.set(charge);
    }

    @Override
    public void setCooldown(int cooldown) {
        ForgeCharmOfReturn.CONFIG.cooldownTime.set(cooldown);
    }

    @Override
    public void setClientParticles(boolean value) {
        ForgeCharmOfReturn.CONFIG.isClientParticles.set(value);
    }

    @Override
    public void setUseAnchorCharge(boolean value) {
        ForgeCharmOfReturn.CONFIG.isUseAnchorCharge.set(value);
    }

    @Override
    public void save() {
        // NO-OP. Already dealt with by forge
    }
}

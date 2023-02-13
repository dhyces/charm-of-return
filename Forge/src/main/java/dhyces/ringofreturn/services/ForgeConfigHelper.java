package dhyces.ringofreturn.services;

import dhyces.ringofreturn.ForgeRingOfReturn;

public class ForgeConfigHelper implements ConfigHelper {
    @Override
    public String getExpressionString() {
        return ForgeRingOfReturn.CONFIG.levelCost.get();
    }

    @Override
    public int getDurability() {
        return ForgeRingOfReturn.CONFIG.durability.get();
    }

    @Override
    public int getCharge() {
        return ForgeRingOfReturn.CONFIG.chargeTime.get();
    }

    @Override
    public int getCooldown() {
        return ForgeRingOfReturn.CONFIG.cooldownTime.get();
    }

    @Override
    public boolean isClientParticles() {
        return ForgeRingOfReturn.CONFIG.isClientParticles.get();
    }

    @Override
    public void setExpressionString(String expression) {
        ForgeRingOfReturn.CONFIG.levelCost.set(expression);
        ForgeRingOfReturn.CONFIG.refreshExpression();
    }

    @Override
    public void setDurability(int durability) {
        ForgeRingOfReturn.CONFIG.durability.set(durability);
    }

    @Override
    public void setCharge(int charge) {
        ForgeRingOfReturn.CONFIG.chargeTime.set(charge);
    }

    @Override
    public void setCooldown(int cooldown) {
        ForgeRingOfReturn.CONFIG.cooldownTime.set(cooldown);
    }

    @Override
    public void setClientParticles(boolean bool) {
        ForgeRingOfReturn.CONFIG.isClientParticles.set(bool);
    }

    @Override
    public void save() {
        // NO-OP. Already dealt with by forge
    }
}

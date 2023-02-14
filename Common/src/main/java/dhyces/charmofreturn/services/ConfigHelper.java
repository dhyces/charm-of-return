package dhyces.charmofreturn.services;

public interface ConfigHelper {

    String getExpressionString();

    int getDurability();

    int getCharge();

    int getCooldown();

    boolean isClientParticles();

    boolean isUseAnchorCharge();

    void setExpressionString(String expression);

    void setDurability(int durability);

    void setCharge(int charge);

    void setCooldown(int cooldown);

    void setClientParticles(boolean value);

    void setUseAnchorCharge(boolean value);

    void save();
}

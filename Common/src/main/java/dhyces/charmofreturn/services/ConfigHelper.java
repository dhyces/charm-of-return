package dhyces.charmofreturn.services;

public interface ConfigHelper {

    String getExpressionString();

    int getDurability();

    int getCharge();

    int getCooldown();

    boolean isClientParticles();

    void setExpressionString(String expression);

    void setDurability(int durability);

    void setCharge(int charge);

    void setCooldown(int cooldown);

    void setClientParticles(boolean bool);

    void save();
}

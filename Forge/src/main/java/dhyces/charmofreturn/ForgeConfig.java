package dhyces.charmofreturn;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import java.util.EmptyStackException;

public class ForgeConfig {
    public ForgeConfigSpec.ConfigValue<String> levelCost;
    public ForgeConfigSpec.IntValue durability;
    public ForgeConfigSpec.IntValue chargeTime;
    public ForgeConfigSpec.IntValue cooldownTime;
    public ForgeConfigSpec.BooleanValue isClientParticles;
    public ForgeConfigSpec.BooleanValue isUseAnchorCharge;

    public Expression levelCostExpression;

    public void refreshExpression() {
        String cost = levelCost.get();
        try {
            if (!cost.isBlank()) {
                levelCostExpression = new ExpressionBuilder(cost).variable("x").build();
            } else {
                levelCostExpression = new ExpressionBuilder("0").build();
            }
        } catch (UnknownFunctionOrVariableException | NumberFormatException | EmptyStackException ignored) {
        }
    }

    public void onConfigChanged(ModConfigEvent.Reloading event) {
        if (event.getConfig().getModId().equals(CharmOfReturn.MODID)) {
            refreshExpression();
        }
    }

    public void onConfigLoaded(ModConfigEvent.Loading event) {
        if (event.getConfig().getModId().equals(CharmOfReturn.MODID)) {
            refreshExpression();
        }
    }
}

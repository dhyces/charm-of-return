package dev.dhyces.charmofreturn;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import java.util.EmptyStackException;

public class NeoConfig {
    public ModConfigSpec.ConfigValue<String> levelCost;
    public ModConfigSpec.IntValue durability;
    public ModConfigSpec.IntValue chargeTime;
    public ModConfigSpec.IntValue cooldownTime;
    public ModConfigSpec.BooleanValue isClientParticles;
    public ModConfigSpec.BooleanValue isUseAnchorCharge;

    public Expression levelCostExpression;

    public ModConfigSpec constructSpec() {
        ModConfigSpec.Builder spec = new ModConfigSpec.Builder()
                .push("Common");
        levelCost = spec.comment("Levels required to use (can input an equation with current level as 'x')").define("levelCost", "0.8x");
        durability = spec.comment("Durability of the charm").defineInRange("durability", () -> 0, 0, Integer.MAX_VALUE);
        chargeTime = spec.comment("Number of ticks it takes to use the charm").defineInRange("chargeTicks", () -> 200, 0, Integer.MAX_VALUE);
        cooldownTime = spec.comment("Number of ticks for the cooldown").defineInRange("cooldownTicks", () -> 1200, 0, Integer.MAX_VALUE);
        isClientParticles = spec.comment("If warp particles are only visible on the user's client. Default is false").define("clientOnlyParticles", false);
        isUseAnchorCharge = spec.comment("If teleporting to a respawn anchor should use up a charge. Default is false.").define("useAnchorCharge", false);
        spec.pop();
        return spec.build();
    }

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

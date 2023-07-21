package dhyces.charmofreturn.integration;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dhyces.charmofreturn.ForgeCharmOfReturn;
import net.minecraft.network.chat.Component;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.EmptyStackException;

public class YaclCompat {

    private static final double DEMO_VAL = 10;

    public static YetAnotherConfigLib create() {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config_screen.charmofreturn.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config_screen.charmofreturn.server.title"))
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config_screen.charmofreturn.server.gameplay.title"))
                                .option(Option.<String>createBuilder()
                                        .name(Component.translatable("config_screen.charmofreturn.server.gameplay.level_cost"))
                                        .description(s ->
                                                OptionDescription.of(
                                                        Component.literal("%s=%s".formatted(s.isBlank() ? "0" : s, demoExpression(s))),
                                                        Component.empty(),
                                                        Component.translatable("config_screen.charmofreturn.server.gameplay.level_cost.desc", DEMO_VAL)
                                                )
                                        )
                                        .binding(
                                                "0.8x",
                                                ForgeCharmOfReturn.CONFIG.levelCost,
                                                s -> {
                                                    ForgeCharmOfReturn.CONFIG.levelCost.set(s);
                                                    ForgeCharmOfReturn.CONFIG.refreshExpression();
                                                }
                                        )
                                        .controller(StringControllerBuilder::create)
                                        .build()
                                )
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config_screen.charmofreturn.server.gameplay.durability"))
                                        .description(
                                                OptionDescription.of(Component.translatable("config_screen.charmofreturn.server.gameplay.durability.desc"))
                                        )
                                        .binding(
                                                0,
                                                ForgeCharmOfReturn.CONFIG.durability,
                                                ForgeCharmOfReturn.CONFIG.durability::set
                                        )
                                        .controller(integerOption -> IntegerFieldControllerBuilder.create(integerOption).range(0, Integer.MAX_VALUE))
                                        .build()
                                )
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config_screen.charmofreturn.server.gameplay.charge_time"))
                                        .description(
                                                OptionDescription.of(Component.translatable("config_screen.charmofreturn.server.gameplay.charge_time.desc"))
                                        )
                                        .binding(
                                                200,
                                                ForgeCharmOfReturn.CONFIG.chargeTime,
                                                ForgeCharmOfReturn.CONFIG.chargeTime::set
                                        )
                                        .controller(integerOption -> IntegerFieldControllerBuilder.create(integerOption).range(0, Integer.MAX_VALUE))
                                        .build()
                                )
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config_screen.charmofreturn.server.gameplay.cooldown_time"))
                                        .description(
                                                OptionDescription.of(Component.translatable("config_screen.charmofreturn.server.gameplay.cooldown_time.desc"))
                                        )
                                        .binding(
                                                200,
                                                ForgeCharmOfReturn.CONFIG.cooldownTime,
                                                ForgeCharmOfReturn.CONFIG.cooldownTime::set
                                        )
                                        .controller(integerOption -> IntegerFieldControllerBuilder.create(integerOption).range(0, Integer.MAX_VALUE))
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config_screen.charmofreturn.server.gameplay.use_anchor"))
                                        .description(
                                                OptionDescription.of(Component.translatable("config_screen.charmofreturn.server.gameplay.use_anchor.desc"))
                                        )
                                        .binding(
                                                false,
                                                ForgeCharmOfReturn.CONFIG.isUseAnchorCharge,
                                                ForgeCharmOfReturn.CONFIG.isUseAnchorCharge::set
                                        )
                                        .controller(booleanOption -> BooleanControllerBuilder.create(booleanOption).trueFalseFormatter().coloured(true))
                                        .build()
                                )
                                .build()
                        )
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config_screen.charmofreturn.server.misc.title"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config_screen.charmofreturn.server.misc.client_particles"))
                                        .description(
                                                OptionDescription.of(Component.translatable("config_screen.charmofreturn.server.misc.client_particles.desc"))
                                        )
                                        .binding(
                                                false,
                                                ForgeCharmOfReturn.CONFIG.isClientParticles,
                                                ForgeCharmOfReturn.CONFIG.isClientParticles::set
                                        )
                                        .controller(booleanOption -> BooleanControllerBuilder.create(booleanOption).trueFalseFormatter().coloured(true))
                                        .build()
                                )
                                .build()
                        )
                        .build()
                ).build();
    }

    private static String demoExpression(String rawExpression) {
        if (rawExpression.isBlank()) {
            return "0";
        }

        try {
            return String.valueOf(new ExpressionBuilder(rawExpression).variable("x").build()
                    .setVariable("x", DEMO_VAL).evaluate());
        } catch (EmptyStackException | IllegalArgumentException e) {
            return "???";
        }
    }
}

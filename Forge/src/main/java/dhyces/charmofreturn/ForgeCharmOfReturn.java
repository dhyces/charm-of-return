package dhyces.charmofreturn;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(CharmOfReturn.MODID)
public class ForgeCharmOfReturn {

    private static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(Registry.ITEM_REGISTRY, CharmOfReturn.MODID);
    private static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_REGISTER = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, CharmOfReturn.MODID);

    public static final ForgeConfig CONFIG = new ForgeConfig();

    public ForgeCharmOfReturn() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEM_REGISTER.register(modBus);
        LOOT_MODIFIER_REGISTER.register(modBus);

        ITEM_REGISTER.register("charm_of_return", Register.CHARM);
        LOOT_MODIFIER_REGISTER.register("loot_table_inject", LootTableLootModifier.Serializer::new);

        ForgeConfigSpec.Builder spec = new ForgeConfigSpec.Builder()
                .push("Common");
        CONFIG.levelCost = spec.comment("Levels required to use (can input an equation with current level as 'x')").define("levelCost", "0.8x");
        CONFIG.durability = spec.comment("Durability of the charm").defineInRange("durability", () -> 0, 0, Integer.MAX_VALUE);
        CONFIG.chargeTime = spec.comment("Number of ticks it takes to use the charm").defineInRange("chargeTicks", () -> 200, 0, Integer.MAX_VALUE);
        CONFIG.cooldownTime = spec.comment("Number of ticks for the cooldown").defineInRange("cooldownTicks", () -> 1200, 0, Integer.MAX_VALUE);
        CONFIG.isClientParticles = spec.comment("If warp particles are only visible on the user's client. Default is false").define("clientOnlyParticles", false);
        spec.pop();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec.build());

        modBus.addListener(CONFIG::onConfigLoaded);
        modBus.addListener(CONFIG::onConfigChanged);
    }
}
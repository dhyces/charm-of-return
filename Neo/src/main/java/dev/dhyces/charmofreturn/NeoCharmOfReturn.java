package dev.dhyces.charmofreturn;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(CharmOfReturn.MODID)
public class NeoCharmOfReturn {
    private static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(Registries.ITEM, CharmOfReturn.MODID);

    public static final NeoConfig CONFIG = new NeoConfig();

    public NeoCharmOfReturn(IEventBus modBus, Dist dist) {
        ITEM_REGISTER.register(modBus);

        ITEM_REGISTER.register("charm_of_return", Register.CHARM);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG.constructSpec());

        modBus.addListener(CONFIG::onConfigLoaded);
        modBus.addListener(CONFIG::onConfigChanged);
        modBus.addListener(this::addToTab);

        if (dist.isClient()) {
            NeoCharmOfReturnClient.init(modBus);
        }
    }

    private void addToTab(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(Register.CHARM.get());
        }
    }
}
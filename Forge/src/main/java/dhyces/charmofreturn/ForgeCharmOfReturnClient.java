package dhyces.charmofreturn;

import dhyces.charmofreturn.integration.YaclCompat;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ForgeCharmOfReturnClient {
    public static void init(IEventBus bus) {
//        ModLoadingContext.get()
//                .registerExtensionPoint(
//                        ConfigGuiHandler.ConfigGuiFactory.class,
//                        () -> new ConfigGuiHandler.ConfigGuiFactory((minecraft, screen) ->
//                                new ConfigScreen(screen)
//                        )
//                );
        bus.addListener(ForgeCharmOfReturnClient::addToTab);
        bus.addListener(ForgeCharmOfReturnClient::clientSetup);
    }

    private static void addToTab(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(Register.CHARM);
        }
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("yet_another_config_lib_v3")) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                    new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> YaclCompat.create().generateScreen(screen))
            );
        }
    }
}

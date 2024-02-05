package dev.dhyces.charmofreturn;

import dev.dhyces.charmofreturn.integration.YaclCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ConfigScreenHandler;

public class NeoCharmOfReturnClient {
    public static void init(IEventBus modBus) {
        modBus.addListener(NeoCharmOfReturnClient::clientSetup);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("yet_another_config_lib_v3")) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                    new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> YaclCompat.create().generateScreen(screen))
            );
        }
    }
}

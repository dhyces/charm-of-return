package dhyces.charmofreturn;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;

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
    }

    private static void addToTab(final CreativeModeTabEvent.BuildContents event) {
        if (event.getTab().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            event.accept(Register.CHARM);
        }
    }
}

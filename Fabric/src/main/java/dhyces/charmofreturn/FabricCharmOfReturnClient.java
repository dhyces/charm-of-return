package dhyces.charmofreturn;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FabricCharmOfReturnClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(CharmOfReturn.id("sync_config"), (client, handler, buf, responseSender) -> {
            FabricConfig newConfig = FabricConfig.readFromBuf(buf);
            client.execute(() -> FabricCharmOfReturn.config = newConfig);
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            client.execute(FabricCharmOfReturn::setupConfig);
        });
    }
}

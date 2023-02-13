package dhyces.ringofreturn;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FabricRingOfReturnClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(RingOfReturn.id("sync_config"), (client, handler, buf, responseSender) -> {
            FabricConfig newConfig = FabricConfig.readFromBuf(buf);
            client.execute(() -> FabricRingOfReturn.config = newConfig);
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            client.execute(FabricRingOfReturn::setupConfig);
        });
    }
}

package dhyces.charmofreturn;

import dhyces.charmofreturn.networking.FabricNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FabricCharmOfReturnClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(FabricNetworking.CONFIG_SYNC, (packet, player, responseSender) -> {
            FabricCharmOfReturn.config = packet.config;
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            client.execute(FabricCharmOfReturn::setupConfig);
        });
    }
}

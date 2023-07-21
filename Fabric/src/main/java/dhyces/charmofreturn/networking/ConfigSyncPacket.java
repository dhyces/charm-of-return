package dhyces.charmofreturn.networking;

import dhyces.charmofreturn.FabricCharmOfReturn;
import dhyces.charmofreturn.FabricConfig;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;

public class ConfigSyncPacket implements FabricPacket {

    public FabricConfig config;

    public ConfigSyncPacket() {
        config = FabricCharmOfReturn.config;
    }

    public ConfigSyncPacket(FriendlyByteBuf buf) {
        config = FabricConfig.readFromBuf(buf);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        FabricConfig.writeToBuf(buf, config);
    }

    @Override
    public PacketType<?> getType() {
        return FabricNetworking.CONFIG_SYNC;
    }
}

package dhyces.charmofreturn.networking;

import dhyces.charmofreturn.CharmOfReturn;
import net.fabricmc.fabric.api.networking.v1.PacketType;

public class FabricNetworking {

    public static final PacketType<ConfigSyncPacket> CONFIG_SYNC = PacketType.create(CharmOfReturn.id("config_sync"), ConfigSyncPacket::new);

    public static void init() {}
}

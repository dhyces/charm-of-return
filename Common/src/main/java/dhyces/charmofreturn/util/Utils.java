package dhyces.charmofreturn.util;

import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class Utils {
    // Pretty interesting alternate impl here
    // <https://github.com/Shadows-of-Fire/Placebo/blob/1.18/src/main/java/shadows/placebo/util/EnchantmentUtils.java>
    public static double totalExperience(Player player) {
        double total = 0;
        int level = player.experienceLevel;
        if (level < 17) {
            total = (level * level) + 6 * level;
        } else if (level < 32) {
            total = 2.5 * (level * level) - 40.5 * level + 360;
        } else {
            total = 4.5 * (level * level) - 162.5 * level + 2220;
        }
        return total + player.experienceProgress * player.getXpNeededForNextLevel();
    }

    public static void sendSimpleSoundEvent(ServerPlayer player, SoundEvent pSound, SoundSource pSource, double pX, double pY, double pZ, float pVolume, float pPitch) {
        sendSimpleSoundEvent(player, BuiltInRegistries.SOUND_EVENT.wrapAsHolder(pSound), pSource, pX, pY, pZ, pVolume, pPitch);
    }

    public static void sendSimpleSoundEvent(ServerPlayer player, Holder<SoundEvent> pSound, SoundSource pSource, double pX, double pY, double pZ, float pVolume, float pPitch) {
        player.connection.send(new ClientboundSoundPacket(pSound, pSource, pX, pY, pZ, pVolume, pPitch, player.getRandom().nextLong()));
    }
}

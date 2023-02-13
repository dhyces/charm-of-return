package dhyces.charmofreturn.util;

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
}

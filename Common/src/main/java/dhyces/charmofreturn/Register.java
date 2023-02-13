package dhyces.charmofreturn;

import com.google.common.base.Suppliers;
import dhyces.charmofreturn.items.CharmItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

public class Register {
    public static final Supplier<CharmItem> CHARM = Suppliers.memoize(() -> new CharmItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).tab(CreativeModeTab.TAB_TOOLS)));
}

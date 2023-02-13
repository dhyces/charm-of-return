package dhyces.ringofreturn;

import com.google.common.base.Suppliers;
import dhyces.ringofreturn.items.RingItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

public class Register {
    public static final Supplier<RingItem> RING = Suppliers.memoize(() -> new RingItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
}

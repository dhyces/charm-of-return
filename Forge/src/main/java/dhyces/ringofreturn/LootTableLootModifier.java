package dhyces.ringofreturn;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class LootTableLootModifier extends LootModifier {
    public static final Codec<LootTableLootModifier> CODEC = RecordCodecBuilder.create(instance ->
            codecStart(instance).and(
                    ResourceLocation.CODEC.fieldOf("loot_table").forGetter(lootTableLootModifier -> lootTableLootModifier.lootTableId)
            ).apply(instance, LootTableLootModifier::new)
    );

    private final ResourceLocation lootTableId;

    public LootTableLootModifier(LootItemCondition[] conditionsIn, ResourceLocation lootTableId) {
        super(conditionsIn);
        this.lootTableId = lootTableId;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootContext.Builder lootContext = new LootContext.Builder(context).withQueriedLootTableId(lootTableId);
        generatedLoot.addAll(context.getLootTable(lootTableId).getRandomItems(lootContext.create(LootContextParamSets.CHEST)));
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}

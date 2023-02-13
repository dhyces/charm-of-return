package dhyces.ringofreturn;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.List;

public class LootTableLootModifier extends LootModifier {
//    public static final Codec<LootTableLootModifier> CODEC = RecordCodecBuilder.create(instance ->
//            instance.group(
//
//            )
//    );

    private static final VarHandle QUERIED_LOOT_TABLE_ID;

    static {
        try {
            QUERIED_LOOT_TABLE_ID = MethodHandles.privateLookupIn(LootContext.Builder.class, MethodHandles.lookup()).findVarHandle(LootContext.Builder.class, "queriedLootTableId", ResourceLocation.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final ResourceLocation lootTableId;

    public LootTableLootModifier(LootItemCondition[] conditionsIn, ResourceLocation lootTableId) {
        super(conditionsIn);
        this.lootTableId = lootTableId;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        LootContext.Builder lootContext = setQueriedLootTableId(new LootContext.Builder(context), lootTableId);
        generatedLoot.addAll(context.getLootTable(lootTableId).getRandomItems(lootContext.create(LootContextParamSets.CHEST)));
        return generatedLoot;
    }

    private static LootContext.Builder setQueriedLootTableId(final LootContext.Builder builder, final ResourceLocation rl) {
        QUERIED_LOOT_TABLE_ID.set(builder, rl);
        return builder;
    }

    public static class Serializer extends GlobalLootModifierSerializer<LootTableLootModifier> {

        @Override
        public LootTableLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] lootItemConditions) {
            ResourceLocation lootTableId = ResourceLocation.tryParse(object.get("loot_table").getAsString());
            return new LootTableLootModifier(lootItemConditions, lootTableId);
        }

        @Override
        public JsonObject write(LootTableLootModifier instance) {
            return null;
        }
    }
}

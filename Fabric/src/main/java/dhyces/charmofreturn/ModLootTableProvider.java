package dhyces.charmofreturn;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

import java.util.function.BiConsumer;

public class ModLootTableProvider extends SimpleFabricLootTableProvider {
    public ModLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, LootContextParamSets.CHEST);
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> resourceLocationBuilderBiConsumer) {
        resourceLocationBuilderBiConsumer.accept(CharmOfReturn.id("chests/injected/charm_loot_low_chance"), ringLoot(0.1f));
        resourceLocationBuilderBiConsumer.accept(CharmOfReturn.id("chests/injected/charm_loot_higher_chance"), ringLoot(0.35f));
    }

    private LootTable.Builder ringLoot(float chance) {
        return new LootTable.Builder().withPool(
                LootPool.lootPool().add(
                        LootItem.lootTableItem(Register.CHARM.get())
                ).conditionally(LootItemRandomChanceCondition.randomChance(chance).build())
        );
    }
}

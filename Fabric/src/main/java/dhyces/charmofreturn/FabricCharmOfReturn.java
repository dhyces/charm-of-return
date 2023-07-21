package dhyces.charmofreturn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import dhyces.charmofreturn.networking.ConfigSyncPacket;
import dhyces.charmofreturn.networking.FabricNetworking;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FabricCharmOfReturn implements ModInitializer {
    private static final Gson GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("charmofreturn.json");
    public static FabricConfig config;

    @Override
    public void onInitialize() {
        setupConfig();
        Registry.register(BuiltInRegistries.ITEM, CharmOfReturn.id("charm_of_return"), Register.CHARM.get());

        FabricNetworking.init();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            sender.sendPacket(new ConfigSyncPacket());
        });

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
            PlayerLookup.all(server).forEach(serverPlayer -> ServerPlayNetworking.send(serverPlayer, new ConfigSyncPacket()));
        });

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(new ResourceLocation("chests/end_city_treasure"))) {
                tableBuilder.withPool(
                        new LootPool.Builder().add(LootTableReference.lootTableReference(CharmOfReturn.id("chests/injected/charm_loot_higher_chance")))
                );
            } else if (id.equals(new ResourceLocation("chests/stronghold_corridor")) || id.equals(new ResourceLocation("chests/stronghold_crossing"))) {
                tableBuilder.withPool(
                        new LootPool.Builder().add(LootTableReference.lootTableReference(CharmOfReturn.id("chests/injected/charm_loot_low_chance")))
                );
            }
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(new ItemStack(Register.CHARM.get()));
        });
    }

    static void setupConfig() {
        if (!Files.exists(CONFIG_PATH)) {
            try {
                Files.createFile(CONFIG_PATH);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        JsonObject obj;
        try (BufferedReader reader = Files.newBufferedReader(CONFIG_PATH)) {
            obj = GSON.fromJson(reader, JsonObject.class);
        } catch (JsonSyntaxException e) {
            CharmOfReturn.LOGGER.error(e.getLocalizedMessage() + ". Resetting config");
            obj = null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (obj == null) {
            resetConfig();
        }
        DataResult<FabricConfig> result = FabricConfig.CODEC.parse(JsonOps.INSTANCE, obj);
        if (result.result().isPresent()) {
            config = result.getOrThrow(false, CharmOfReturn.LOGGER::error);
        } else {
            result.error().ifPresent(CharmOfReturn.LOGGER::error);
            resetConfig();
        }
    }

    static void resetConfig() {
        config = new FabricConfig();
        saveConfig();
        CharmOfReturn.LOGGER.info("Config has been reset");
    }

    public static void saveConfig() {
        config.saveToFile(GSON, CONFIG_PATH);
    }
}

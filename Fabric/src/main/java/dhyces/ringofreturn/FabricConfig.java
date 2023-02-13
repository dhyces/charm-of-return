package dhyces.ringofreturn;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FabricConfig {
    public static final Codec<FabricConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.comapFlatMap(
                            s -> {
                                Expression exp;
                                try {
                                    exp = new ExpressionBuilder(s).variable("x").build();
                                } catch (UnknownFunctionOrVariableException e) {
                                    return DataResult.error(e.getMessage());
                                }
                                return DataResult.success(Pair.of(s, exp));
                            },
                            Pair::getFirst
                    ).fieldOf("levelCost").forGetter(config -> Pair.of(config.levelCostStr, config.levelCostExpression)),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("durability").forGetter(FabricConfig::getDurability),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("chargeTicks").forGetter(FabricConfig::getCharge),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("cooldownTicks").forGetter(FabricConfig::getCooldown),
                    Codec.BOOL.fieldOf("clientOnlyParticles").forGetter(FabricConfig::isClientParticles)
            ).apply(instance, FabricConfig::new)
    );

    private String levelCostStr;
    public Expression levelCostExpression;
    public int durability;
    public int charge;
    public int cooldown;
    public boolean isClientParticles;

    public FabricConfig() {
        this(Pair.of("0.8x", null), 0, 200, 1200, false);
        this.levelCostExpression = new ExpressionBuilder(levelCostStr).variable("x").build();
    }

    public FabricConfig(Pair<String, Expression> levelCost, int durability, int charge, int cooldown, boolean isClientParticles) {
        this.levelCostStr = levelCost.getFirst();
        this.levelCostExpression = levelCost.getSecond();
        this.durability = durability;
        this.charge = charge;
        this.cooldown = cooldown;
        this.isClientParticles = isClientParticles;
    }

    public String getLevelCostStr() {
        return levelCostStr;
    }

    public void setLevelCostStr(String levelCostStr) {
        try {
            this.levelCostExpression = new ExpressionBuilder(levelCostStr).variable("x").build();
        } catch (UnknownFunctionOrVariableException e) {
            return;
        }
        this.levelCostStr = levelCostStr;
    }

    public Expression getLevelCostExpression() {
        return levelCostExpression;
    }

    public int getDurability() {
        return durability;
    }

    public int getCharge() {
        return charge;
    }

    public int getCooldown() {
        return cooldown;
    }

    public boolean isClientParticles() {
        return isClientParticles;
    }

    public static FabricConfig readFromBuf(FriendlyByteBuf buf) {
        return buf.readWithCodec(CODEC);
    }

    public static void writeToBuf(FriendlyByteBuf buf, FabricConfig config) {
        buf.writeWithCodec(CODEC, config);
    }

    public void saveToFile(Gson gson, Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            DataResult<JsonElement> result = CODEC.encodeStart(JsonOps.INSTANCE, this);
            if (result.result().isPresent()) {
                gson.toJson(result.getOrThrow(false, RingOfReturn.LOGGER::error), gson.newJsonWriter(writer));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

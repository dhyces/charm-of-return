package dhyces.charmofreturn.items;

import com.mojang.math.Vector3f;
import dhyces.charmofreturn.services.Services;
import dhyces.charmofreturn.util.Utils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CharmItem extends Item {

    private static final Map<ResourceKey<Level>, TeleportFunction> TELEPORT_FUNCTION_MAP = Util.make(new HashMap<>(), map -> {
        map.put(Level.OVERWORLD, (stack, level, user) -> {
            if (user.getRespawnDimension().equals(Level.OVERWORLD) && user.getRespawnPosition() != null) {
                Optional<Vec3> potentialBedPos = BedBlock.findStandUpPosition(user.getType(), level, user.getRespawnPosition(), user.getYRot());
                if (potentialBedPos.isPresent()) {
                    return Optional.of(new TeleportPosition(Level.OVERWORLD, new BlockPos(potentialBedPos.get())));
                } else {
                    user.displayClientMessage(new TranslatableComponent("block.minecraft.spawn.not_valid"), false);
                }
            }
            BlockPos pos = level.getSharedSpawnPos();
            return Optional.of(new TeleportPosition(Level.OVERWORLD, pos));
        });
        map.put(Level.NETHER, (stack, level, user) -> {
            boolean usesCharge = Services.CONFIG_HELPER.isUseAnchorCharge();
            if (user.getRespawnDimension().equals(Level.NETHER) && user.getRespawnPosition() != null && (!usesCharge || level.getBlockState(user.getRespawnPosition()).getValue(RespawnAnchorBlock.CHARGE) > 0)) {
                BlockPos blockPos = user.getRespawnPosition();
                Optional<Vec3> potentialAnchorPos = RespawnAnchorBlock.findStandUpPosition(user.getType(), level, blockPos);
                if (potentialAnchorPos.isPresent()) {
                    Vec3 pos = potentialAnchorPos.get();
                    if (usesCharge) {
                        BlockState state = level.getBlockState(blockPos);
                        level.setBlock(blockPos, state.setValue(RespawnAnchorBlock.CHARGE, state.getValue(RespawnAnchorBlock.CHARGE)-1), Block.UPDATE_ALL);
                        Utils.sendSimpleSoundEvent(user, SoundEvents.RESPAWN_ANCHOR_DEPLETE, SoundSource.BLOCKS, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0f, 1.0f);
                    }
                    return Optional.of(new TeleportPosition(Level.NETHER, new BlockPos(pos)));
                } else {
                    user.displayClientMessage(new TranslatableComponent("block.minecraft.spawn.not_valid"), false);
                }
            }
            return Optional.empty();
        });
        map.put(Level.END, (stack, level, user) -> {
            BlockPos pos;
            if (user.getRespawnDimension().equals(Level.END)) {
                pos = user.getRespawnPosition();
            } else {
                pos = ServerLevel.END_SPAWN_POINT;
            }
            return Optional.of(new TeleportPosition(Level.END, pos));
        });
    });

    public CharmItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, components, tooltipFlag);
        components.add(new TranslatableComponent("item.charmofreturn.charm_of_return.desc"));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getUses(stack) > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0f - getUses(stack) * 13.0f / Services.PLATFORM_HELPER.getDurability());
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return Services.PLATFORM_HELPER.getChargeTicks();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pPlayer.getCooldowns().isOnCooldown(this)) {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pHand), pLevel.isClientSide);
        }
        return super.use(pLevel, pPlayer, pHand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int tick) {
        if (tick % 5 == 0) {
            Vec3 lookVec = calcParticlePos(livingEntity).normalize().scale(1.5);
            double x = livingEntity.getX() + lookVec.x;
            double y = livingEntity.getY() + livingEntity.getEyeHeight(livingEntity.getPose()) + lookVec.y;
            double z = livingEntity.getZ() + lookVec.z;
            float size = 1 - ((float)tick / (float)Services.PLATFORM_HELPER.getChargeTicks());
            int numOfParticles = (Services.PLATFORM_HELPER.getChargeTicks()*2) / (tick == 0 ? 1 : tick);
            if (Services.PLATFORM_HELPER.isClientParticles() && level.isClientSide) {
                Random rand = new Random();
                for (int i = 0; i < numOfParticles; i++) {
                    double offsetX = x + (size == 0 ? 0 : rand.nextFloat(size)) * (rand.nextBoolean() ? -1 : 1);
                    double offsetY = y + (size == 0 ? 0 : rand.nextFloat(size)) * (rand.nextBoolean() ? -1 : 1);
                    double offsetZ = z + (size == 0 ? 0 : rand.nextFloat(size)) * (rand.nextBoolean() ? -1 : 1);
                    level.addParticle(new DustParticleOptions(new Vector3f(0.0f, 0.612f, 0.627f), 6f * size), offsetX, offsetY, offsetZ, 0, 0, 0);
                }
            } else if (!Services.PLATFORM_HELPER.isClientParticles() && !level.isClientSide) {
                ((ServerLevel)level).sendParticles(new DustParticleOptions(new Vector3f(0.0f, 0.612f, 0.627f), 6f * size), x, y, z, numOfParticles, size, size, size, 0);
            }
        }
    }

    private Vec3 calcParticlePos(LivingEntity livingEntity) {
        float xRot = livingEntity.getXRot();
        float negativeYRot = -livingEntity.getYRot();
        double sinX = Math.sin(xRot * Mth.DEG_TO_RAD);
        double cosX = Math.cos(xRot * Mth.DEG_TO_RAD);
        double sinY = Math.sin(negativeYRot * Mth.DEG_TO_RAD);
        double cosY = Math.cos(negativeYRot * Mth.DEG_TO_RAD);
        return new Vec3(cosX * sinY, -sinX, cosX * cosY);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLevel instanceof ServerLevel serverLevel && pLivingEntity instanceof ServerPlayer player) {
            int exp = (int)Utils.totalExperience(player);
            double xpToTake = Services.PLATFORM_HELPER.getLevelCostExpression().setVariable("x", exp).evaluate();
            if (exp >= xpToTake || player.getAbilities().instabuild) {
                TeleportFunction function = TELEPORT_FUNCTION_MAP.get(pLevel.dimension());
                if (function != null) {
                    Optional<TeleportPosition> pos = function.onTeleport(pStack, serverLevel, player);
                    if (pos.isPresent()) {
                        TeleportPosition teleportPosition = pos.get();
                        boolean broken = false;
                        player.giveExperiencePoints(-(int)xpToTake);
                        player.getCooldowns().addCooldown(this, Services.PLATFORM_HELPER.getCooldownTicks());
                        if (Services.PLATFORM_HELPER.getDurability() > 0) {
                            setUses(pStack, getUses(pStack)+1);
                            if (getUses(pStack) >= Services.PLATFORM_HELPER.getDurability()) {
                                broken = true;
                            }
                        }
                        player.awardStat(Stats.ITEM_USED.get(this), 1);
                        if (teleportPosition.isSameDimension(pLevel)) {
                            BlockPos position = teleportPosition.position;
                            player.connection.teleport(position.getX(), position.getY(), position.getZ(), player.getYRot(), player.getXRot());
                            if (broken) {
                                player.connection.send(new ClientboundSoundPacket(SoundEvents.GLASS_BREAK, SoundSource.MASTER, position.getX(), position.getY(), position.getZ(), 1.0f, 1.0f));
                                player.connection.send(new ClientboundSoundPacket(SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.MASTER, position.getX(), position.getY(), position.getZ(), 1.0f, 1.0f));
                            }
                            player.connection.send(new ClientboundSoundPacket(SoundEvents.ENDERMAN_TELEPORT, SoundSource.MASTER, position.getX(), position.getY(), position.getZ(), 1.0f, 1.0f));
                        } else {
                            Services.PLATFORM_HELPER.teleportToDimension(player, serverLevel, new PortalInfo(Vec3.atCenterOf(teleportPosition.position), Vec3.ZERO, player.getYRot(), player.getXRot()));
                        }
                        return broken ? ItemStack.EMPTY : super.finishUsingItem(pStack, pLevel, pLivingEntity);
                    }
                }
            }
            player.playNotifySound(SoundEvents.LAVA_EXTINGUISH, SoundSource.NEUTRAL, 1.0f, 1.0f);
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    public int getUses(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt("Uses") : 0;
    }

    public void setUses(ItemStack stack, int uses) {
        stack.getOrCreateTag().putInt("Uses", uses);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    /**
     * TEMPORARY API: PRONE TO REMOVAL IN ANY MINOR OR MAJOR UPDATE.
     * Only one teleport function for any given level. This activates when the player finishes using the item, after
     * confirming requirements are met to determine if the player will teleport or not.
     *
     * @param levelKey ResourceKey of the level to set the teleport function for
     * @param function TeleportFunction to activate if user is in this level and passes requirements
     */
    public static void addTeleportFunction(ResourceKey<Level> levelKey, TeleportFunction function) {
        TELEPORT_FUNCTION_MAP.put(levelKey, function);
    }

    public record TeleportPosition(ResourceKey<Level> levelKey, BlockPos position) {
        public boolean isSameDimension(Level level) {
            return this.levelKey.equals(level.dimension());
        }
    }

    public interface TeleportFunction {
        Optional<TeleportPosition> onTeleport(ItemStack stack, ServerLevel level, ServerPlayer user);
    }
}

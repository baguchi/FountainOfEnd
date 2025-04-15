package baguchi.fountain_of_end.blockentity;

import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.extensions.IOwnedSpawner;
import net.neoforged.neoforge.event.EventHooks;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public abstract class FountainBaseSpawner implements IOwnedSpawner {
    public static final String SPAWN_DATA_TAG = "SpawnData";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int EVENT_SPAWN = 1;
    private int spawnDelay = 20;
    private SimpleWeightedRandomList<SpawnData> spawnPotentials = SimpleWeightedRandomList.empty();
    @Nullable
    private SpawnData nextSpawnData;
    private double spin;
    private double oSpin;
    private int minSpawnDelay = 400;
    private int maxSpawnDelay = 600;
    private int spawnCount = 24;
    @Nullable
    private Entity displayEntity;
    private int maxNearbyEntities = 6;
    private int requiredPlayerRange = 20;
    private int spawnRange = 10;

    public FountainBaseSpawner() {
    }

    public void setEntityIds(List<SpawnDataWeight> types) {
        SimpleWeightedRandomList.Builder<SpawnData> list = SimpleWeightedRandomList.builder();

        for (int i = 0; i < types.size(); i++) {
            list.add(types.get(i).spawnData(), types.get(i).weight());
        }
        this.setSpawnPotentials(list.build());
    }

    public SimpleWeightedRandomList<SpawnData> getSpawnPotentials() {
        return spawnPotentials;
    }

    private void setSpawnPotentials(SimpleWeightedRandomList<SpawnData> spawnPotentials) {
        this.spawnPotentials = spawnPotentials;
    }

    private boolean isNearPlayer(Level level, BlockPos pos) {
        return level.hasNearbyAlivePlayer((double) pos.getX() + (double) 0.5F, (double) pos.getY() + (double) 0.5F, (double) pos.getZ() + (double) 0.5F, (double) this.requiredPlayerRange);
    }

    public void clientTick(Level level, BlockPos pos) {
        if (!this.isNearPlayer(level, pos)) {
            this.oSpin = this.spin;
        } else if (this.displayEntity != null) {
            RandomSource randomsource = level.getRandom();
            double d0 = (double) pos.getX() + randomsource.nextDouble();
            double d1 = (double) pos.getY() + randomsource.nextDouble();
            double d2 = (double) pos.getZ() + randomsource.nextDouble();
            //level.addParticle(ParticleTypes.PORTAL, d0, d1, d2, (double)0.0F, (double)0.0F, (double)0.0F);
            level.addParticle(ParticleTypes.PORTAL, d0, d1, d2, (double) 0.0F, (double) 0.0F, (double) 0.0F);
            if (this.spawnDelay > 0) {
                --this.spawnDelay;
            }

            this.oSpin = this.spin;
            this.spin = (this.spin + (double) (1000.0F / ((float) this.spawnDelay + 200.0F))) % (double) 360.0F;
        }

    }

    public void serverTick(ServerLevel serverLevel, BlockPos pos) {
        if (this.isNearPlayer(serverLevel, pos)) {
            if (this.spawnDelay == -1) {
                this.delay(serverLevel, pos);
            }

            if (this.spawnDelay > 0) {
                --this.spawnDelay;
            } else {
                boolean flag = false;
                RandomSource randomsource = serverLevel.getRandom();
                SpawnData spawndata = this.getOrCreateNextSpawnData(serverLevel, randomsource, pos);

                for (int i = 0; i < this.spawnCount; ++i) {
                    CompoundTag compoundtag = spawndata.getEntityToSpawn();
                    Optional<EntityType<?>> optional = EntityType.by(compoundtag);
                    if (optional.isEmpty()) {
                        this.delay(serverLevel, pos);
                        return;
                    }

                    ListTag listtag = compoundtag.getList("Pos", 6);
                    int j = listtag.size();
                    double d0 = j >= 1 ? listtag.getDouble(0) : (double) pos.getX() + (randomsource.nextDouble() - randomsource.nextDouble()) * (double) this.spawnRange + (double) 0.5F;
                    double d1 = j >= 2 ? listtag.getDouble(1) : (double) (pos.getY() + randomsource.nextInt(3) - 1);
                    double d2 = j >= 3 ? listtag.getDouble(2) : (double) pos.getZ() + (randomsource.nextDouble() - randomsource.nextDouble()) * (double) this.spawnRange + (double) 0.5F;
                    if (serverLevel.noCollision(((EntityType) optional.get()).getSpawnAABB(d0, d1, d2))) {
                        BlockPos blockpos = BlockPos.containing(d0, d1, d2);
                        if (spawndata.getCustomSpawnRules().isPresent()) {
                            if (!((EntityType) optional.get()).getCategory().isFriendly() && serverLevel.getDifficulty() == Difficulty.PEACEFUL) {
                                continue;
                            }

                            SpawnData.CustomSpawnRules spawndata$customspawnrules = (SpawnData.CustomSpawnRules) spawndata.getCustomSpawnRules().get();
                            if (!spawndata$customspawnrules.isValidPosition(blockpos, serverLevel)) {
                                continue;
                            }
                        }/* else if (!SpawnPlacements.checkSpawnRules((EntityType)optional.get(), serverLevel, MobSpawnType.SPAWNER, blockpos, serverLevel.getRandom())) {
                            continue;
                        }*/

                        Entity entity = EntityType.loadEntityRecursive(compoundtag, serverLevel, (p_151310_) -> {
                            p_151310_.moveTo(d0, d1, d2, p_151310_.getYRot(), p_151310_.getXRot());
                            return p_151310_;
                        });
                        if (entity == null) {
                            this.delay(serverLevel, pos);
                            return;
                        }

                        int k = serverLevel.getEntities(EntityTypeTest.forExactClass(entity.getClass()), (new AABB((double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), (double) (pos.getX() + 1), (double) (pos.getY() + 1), (double) (pos.getZ() + 1))).inflate((double) this.spawnRange), EntitySelector.NO_SPECTATORS).size();
                        if (k >= this.maxNearbyEntities) {
                            this.delay(serverLevel, pos);
                            return;
                        }

                        entity.moveTo(entity.getX(), entity.getY(), entity.getZ(), randomsource.nextFloat() * 360.0F, 0.0F);
                        if (entity instanceof Mob) {
                            Mob mob = (Mob) entity;
                            if (mob.checkSpawnObstruction(serverLevel)) {
                                continue;
                            }

                            boolean flag1 = spawndata.getEntityToSpawn().size() == 1 && spawndata.getEntityToSpawn().contains("id", 8);
                            EventHooks.finalizeMobSpawnSpawner(mob, serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, (SpawnGroupData) null, this, flag1);
                            Optional<EquipmentTable> var10000 = spawndata.getEquipment();
                            Objects.requireNonNull(mob);
                            var10000.ifPresent(mob::equip);
                        }

                        if (!serverLevel.tryAddFreshEntityWithPassengers(entity)) {
                            this.delay(serverLevel, pos);
                            return;
                        }

                        Vec3 vec3 = pos.getCenter();

                        serverLevel.sendParticles(ParticleTypes.PORTAL, vec3.x + randomsource.nextFloat() - randomsource.nextFloat(), vec3.y + randomsource.nextFloat() - randomsource.nextFloat(), vec3.z + randomsource.nextFloat() - randomsource.nextFloat(), 10, 0F, 0F, 0F, 0);
                        serverLevel.playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0F, 1.0F);
                        serverLevel.gameEvent(entity, GameEvent.ENTITY_PLACE, blockpos);
                        if (entity instanceof Mob) {
                            ((Mob) entity).spawnAnim();
                        }

                        flag = true;
                    }
                }

                if (flag) {
                    this.delay(serverLevel, pos);
                }
            }
        }

    }

    private void delay(Level level, BlockPos pos) {
        RandomSource randomsource = level.random;
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.spawnDelay = this.minSpawnDelay;
        } else {
            this.spawnDelay = this.minSpawnDelay + randomsource.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
        }

        this.spawnPotentials.getRandom(randomsource).ifPresent((p_337965_) -> this.setNextSpawnData(level, pos, (SpawnData) p_337965_.data()));
        this.broadcastEvent(level, pos, 1);
    }

    public void load(@Nullable Level level, BlockPos pos, CompoundTag tag) {
        this.spawnDelay = tag.getShort("Delay");
        boolean flag = tag.contains("SpawnData", 10);
        if (flag) {
            SpawnData spawndata = (SpawnData) SpawnData.CODEC.parse(NbtOps.INSTANCE, tag.getCompound("SpawnData")).resultOrPartial((p_186391_) -> LOGGER.warn("Invalid SpawnData: {}", p_186391_)).orElseGet(SpawnData::new);
            this.setNextSpawnData(level, pos, spawndata);
        }

        boolean flag1 = tag.contains("SpawnPotentials", 9);
        if (flag1) {
            ListTag listtag = tag.getList("SpawnPotentials", 10);
            this.spawnPotentials = (SimpleWeightedRandomList) SpawnData.LIST_CODEC.parse(NbtOps.INSTANCE, listtag).resultOrPartial((p_186388_) -> LOGGER.warn("Invalid SpawnPotentials list: {}", p_186388_)).orElseGet(SimpleWeightedRandomList::empty);
        } else {
            this.spawnPotentials = SimpleWeightedRandomList.single(this.nextSpawnData != null ? this.nextSpawnData : new SpawnData());
        }

        if (tag.contains("MinSpawnDelay", 99)) {
            this.minSpawnDelay = tag.getShort("MinSpawnDelay");
            this.maxSpawnDelay = tag.getShort("MaxSpawnDelay");
            this.spawnCount = tag.getShort("SpawnCount");
        }

        if (tag.contains("MaxNearbyEntities", 99)) {
            this.maxNearbyEntities = tag.getShort("MaxNearbyEntities");
            this.requiredPlayerRange = tag.getShort("RequiredPlayerRange");
        }

        if (tag.contains("SpawnRange", 99)) {
            this.spawnRange = tag.getShort("SpawnRange");
        }

        this.displayEntity = null;
    }

    public CompoundTag save(CompoundTag tag) {
        tag.putShort("Delay", (short) this.spawnDelay);
        tag.putShort("MinSpawnDelay", (short) this.minSpawnDelay);
        tag.putShort("MaxSpawnDelay", (short) this.maxSpawnDelay);
        tag.putShort("SpawnCount", (short) this.spawnCount);
        tag.putShort("MaxNearbyEntities", (short) this.maxNearbyEntities);
        tag.putShort("RequiredPlayerRange", (short) this.requiredPlayerRange);
        tag.putShort("SpawnRange", (short) this.spawnRange);
        if (this.nextSpawnData != null) {
            tag.put("SpawnData", (Tag) SpawnData.CODEC.encodeStart(NbtOps.INSTANCE, this.nextSpawnData).getOrThrow((p_337966_) -> new IllegalStateException("Invalid SpawnData: " + p_337966_)));
        }

        tag.put("SpawnPotentials", (Tag) SpawnData.LIST_CODEC.encodeStart(NbtOps.INSTANCE, this.spawnPotentials).getOrThrow());
        return tag;
    }

    @Nullable
    public Entity getOrCreateDisplayEntity(Level level, BlockPos pos) {
        if (this.displayEntity == null) {
            CompoundTag compoundtag = this.getOrCreateNextSpawnData(level, level.getRandom(), pos).getEntityToSpawn();
            if (!compoundtag.contains("id", 8)) {
                return null;
            }

            this.displayEntity = EntityType.loadEntityRecursive(compoundtag, level, Function.identity());
            if (compoundtag.size() == 1 && this.displayEntity instanceof Mob) {
            }
        }

        return this.displayEntity;
    }

    public boolean onEventTriggered(Level level, int id) {
        if (id == 1) {
            if (level.isClientSide) {
                this.spawnDelay = this.minSpawnDelay;
            }

            return true;
        } else {
            return false;
        }
    }

    protected void setNextSpawnData(@Nullable Level level, BlockPos pos, SpawnData nextSpawnData) {
        this.nextSpawnData = nextSpawnData;
    }

    public SpawnData getOrCreateNextSpawnData(@Nullable Level level, RandomSource random, BlockPos pos) {
        if (this.nextSpawnData != null) {
            return this.nextSpawnData;
        } else {
            this.setNextSpawnData(level, pos, (SpawnData) this.spawnPotentials.getRandom(random).map(WeightedEntry.Wrapper::data).orElseGet(SpawnData::new));
            return this.nextSpawnData;
        }
    }

    public abstract void broadcastEvent(Level var1, BlockPos var2, int var3);

    public double getSpin() {
        return this.spin;
    }

    public double getoSpin() {
        return this.oSpin;
    }

    public @org.jetbrains.annotations.Nullable Either<BlockEntity, Entity> getOwner() {
        return null;
    }
}

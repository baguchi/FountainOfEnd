package baguchi.end_fountain.blockentity;

import baguchi.end_fountain.register.ModBlockEntitys;
import baguchi.end_fountain.register.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static baguchi.end_fountain.block.FountainOfEndBlock.putEntity;

public class FountainOfEndBlockEntity extends BlockEntity implements Spawner {
    private final FountainBaseSpawner spawner = new FountainBaseSpawner() {
        @Override
        public void broadcastEvent(Level p_155767_, BlockPos p_155768_, int p_155769_) {
            p_155767_.blockEvent(p_155768_, ModBlocks.FOUNTAIN_OF_END.get(), p_155769_, 0);
        }

        @Override
        public void setNextSpawnData(@Nullable Level p_155771_, BlockPos p_155772_, SpawnData p_155773_) {
            super.setNextSpawnData(p_155771_, p_155772_, p_155773_);
            if (p_155771_ != null) {
                BlockState blockstate = p_155771_.getBlockState(p_155772_);
                p_155771_.sendBlockUpdated(p_155772_, blockstate, blockstate, 4);
            }
        }

        @Override
        public com.mojang.datafixers.util.Either<net.minecraft.world.level.block.entity.BlockEntity, net.minecraft.world.entity.Entity> getOwner() {
            return com.mojang.datafixers.util.Either.left(FountainOfEndBlockEntity.this);
        }
    };


    public int tick;
    public FountainOfEndBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntitys.FOUNTAIN_OF_END.get(), pos, blockState);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, FountainOfEndBlockEntity blockEntity) {
        boolean flag = false;

        blockEntity.tick++;

        if (flag) {
            setChanged(level, pos, state);
        }

        if (blockEntity.spawner.getSpawnPotentials().isEmpty()) {
            blockEntity.setEntityId(EntityType.ENDERMITE, level.getRandom());
        }

        if (level.isClientSide()) {
            blockEntity.spawner.clientTick(level, pos);
        } else {
            blockEntity.spawner.serverTick((ServerLevel) level, pos);
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag compoundtag = this.saveCustomOnly(registries);
        compoundtag.remove("SpawnPotentials");
        return compoundtag;
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        return this.spawner.onEventTriggered(this.level, id) || super.triggerEvent(id, type);
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.spawner.load(this.level, this.worldPosition, tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        this.spawner.save(tag);
    }

    @Override
    public void setEntityId(EntityType<?> type, RandomSource random) {
        List<SpawnDataWeight> list = new ArrayList<>();

        putEntity(list, type, 1);

        this.getSpawner().setEntityIds(list);
        this.setChanged();
    }

    public FountainBaseSpawner getSpawner() {
        return this.spawner;
    }
}

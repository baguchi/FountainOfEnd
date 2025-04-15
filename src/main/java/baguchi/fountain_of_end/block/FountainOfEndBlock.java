package baguchi.fountain_of_end.block;

import baguchi.fountain_of_end.blockentity.FountainOfEndBlockEntity;
import baguchi.fountain_of_end.blockentity.SpawnDataWeight;
import baguchi.fountain_of_end.register.ModBlockEntitys;
import baguchi.fountain_of_end.register.ModEntities;
import baguchi.fountain_of_end.util.EnderlingUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FountainOfEndBlock extends BaseEntityBlock {
    private final IntProvider xpRange;
    public static final MapCodec<FountainOfEndBlock> CODEC = simpleCodec(FountainOfEndBlock::new);

    @Override
    public MapCodec<FountainOfEndBlock> codec() {
        return CODEC;
    }

    public FountainOfEndBlock(Properties properties) {
        super(properties);
        this.xpRange = UniformInt.of(100, 120);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FountainOfEndBlockEntity(blockPos, blockState);
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntitys.FOUNTAIN_OF_END.get(), FountainOfEndBlockEntity::tick);

    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (level.getBlockEntity(pos) instanceof FountainOfEndBlockEntity spawnerblockentity) {
            List<SpawnDataWeight> list = new ArrayList<>();

            putEntity(list, EntityType.ENDERMITE, 1);
            putEntity(list, ModEntities.WATCHLING.get(), 5);
            putEntity(list, ModEntities.SNARELING.get(), 3);

            spawnerblockentity.getSpawner().setEntityIds(list);
            spawnerblockentity.setChanged();
        }
    }

    public static List<SpawnDataWeight> putEntity(List<SpawnDataWeight> spawnDataWeights, EntityType<?> type, int weight) {
        CompoundTag tag = new CompoundTag();

        tag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(type).toString());

        spawnDataWeights.add(new SpawnDataWeight(new SpawnData(tag, Optional.empty(), Optional.empty()), weight));

        return spawnDataWeights;
    }

    @Override
    protected void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        EnderlingUtil.angerNearbyEnderling(player, false);
        return super.playerWillDestroy(level, pos, state, player);
    }


    public int getExpDrop(BlockState state, LevelAccessor level, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity breaker, ItemStack tool) {
        return this.xpRange.sample(level.getRandom());
    }
}

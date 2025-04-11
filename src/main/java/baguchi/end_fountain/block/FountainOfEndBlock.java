package baguchi.end_fountain.block;

import baguchi.end_fountain.blockentity.FountainOfEndBlockEntity;
import baguchi.end_fountain.register.ModBlockEntitys;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BeaconBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FountainOfEndBlock extends BaseEntityBlock {

    public static final MapCodec<FountainOfEndBlock> CODEC = simpleCodec(FountainOfEndBlock::new);

    @Override
    public MapCodec<FountainOfEndBlock> codec() {
        return CODEC;
    }

    public FountainOfEndBlock(Properties properties) {
        super(properties);
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
}

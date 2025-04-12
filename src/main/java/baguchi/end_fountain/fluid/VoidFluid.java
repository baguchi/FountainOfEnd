package baguchi.end_fountain.fluid;

import baguchi.end_fountain.register.ModBlocks;
import baguchi.end_fountain.register.ModFluidTypes;
import baguchi.end_fountain.register.ModFluids;
import baguchi.end_fountain.register.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.Optional;

public abstract class VoidFluid extends FlowingFluid {

    protected VoidFluid() {
        super();
    }

    @Override
    protected boolean isRandomlyTicking() {
        return true;
    }

    public FluidType getFluidType() {
        return ModFluidTypes.VOID.get();
    }

    public Fluid getFlowing() {
        return ModFluids.VOID_FLOW.get();
    }

    public Fluid getSource() {
        return ModFluids.VOID.get();
    }

    public boolean canConvertToSource(FluidState state, Level level, BlockPos pos) {
        return this.getFluidType().canConvertToSource(state, level, pos);
    }

    protected void beforeDestroyingBlock(LevelAccessor worldIn, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? worldIn.getBlockEntity(pos) : null;
        Block.dropResources(state, worldIn, pos, blockEntity);
    }

    protected int getSlopeFindDistance(LevelReader worldIn) {
        return 4;
    }

    protected int getDropOff(LevelReader worldIn) {
        return 2;
    }

    public Item getBucket() {
        return ModItems.VOID_BUCKET.get();
    }

    protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluidIn, Direction direction) {
        return direction == Direction.DOWN && !this.isSame(fluidIn);
    }

    public int getTickDelay(LevelReader level) {
        return 30;
    }

    protected BlockState createLegacyBlock(FluidState state) {
        return (BlockState) (ModBlocks.VOID.get()).defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    public Optional<SoundEvent> getPickupSound() {
        return Optional.ofNullable(this.getFluidType().getSound(SoundActions.BUCKET_FILL));
    }

    @Override
    public boolean isSame(Fluid p_207187_1_) {
        return p_207187_1_ == ModFluids.VOID.get() || p_207187_1_ == ModFluids.VOID_FLOW.get();
    }

    protected float getExplosionResistance() {
        return 1000.0F;
    }

    @Override
    protected boolean canConvertToSource(Level p_256670_) {
        return false;
    }

    public static class Source extends VoidFluid {
        public Source() {
            super();
        }

        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends VoidFluid {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> p_207184_1_) {
            super.createFluidStateDefinition(p_207184_1_);
            p_207184_1_.add(LEVEL);
        }

        public int getAmount(FluidState p_207192_1_) {
            return p_207192_1_.getValue(LEVEL);
        }

        public boolean isSource(FluidState p_207193_1_) {
            return false;
        }

        @Override
        protected boolean isRandomlyTicking() {
            return false;
        }
    }
}
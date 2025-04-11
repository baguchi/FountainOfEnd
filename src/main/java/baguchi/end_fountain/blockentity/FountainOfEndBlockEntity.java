package baguchi.end_fountain.blockentity;

import baguchi.end_fountain.register.ModBlockEntitys;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class FountainOfEndBlockEntity extends BlockEntity {
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
    }
}

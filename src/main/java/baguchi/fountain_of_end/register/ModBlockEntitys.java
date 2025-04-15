package baguchi.fountain_of_end.register;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.blockentity.FountainOfEndBlockEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntitys {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FountainOfEnd.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FountainOfEndBlockEntity>> FOUNTAIN_OF_END = BLOCK_ENTITIES.register("fountain_of_end", () -> register(FountainOfEnd.MODID + ":fountain_of_end", BlockEntityType.Builder.of(FountainOfEndBlockEntity::new, ModBlocks.FOUNTAIN_OF_END.get())));

    private static <T extends BlockEntity> BlockEntityType<T> register(String p_200966_0_, BlockEntityType.Builder<T> p_200966_1_) {
        Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, p_200966_0_);
        return p_200966_1_.build(type);
    }
}

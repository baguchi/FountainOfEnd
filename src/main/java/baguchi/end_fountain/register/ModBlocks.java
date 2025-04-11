package baguchi.end_fountain.register;

import baguchi.end_fountain.EndFountain;
import baguchi.end_fountain.block.FountainOfEndBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EndFountain.MODID);

    public static final DeferredBlock<Block> FOUNTAIN_OF_END = noItemRegister("fountain_of_end", () -> new FountainOfEndBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).replaceable().noCollission().strength(100.0F).noLootTable().sound(SoundType.NETHERRACK)));


    private static <T extends Block> DeferredBlock<T> baseRegister(String name, Supplier<? extends T> block, Function<DeferredBlock<T>, Supplier<? extends Item>> item) {
        DeferredBlock<T> register = BLOCKS.register(name, block);
        Supplier<? extends Item> itemSupplier = item.apply(register);
        ModItems.ITEMS.register(name, itemSupplier);
        return register;
    }

    private static <T extends Block> DeferredBlock<T> noItemRegister(String name, Supplier<? extends T> block) {
        DeferredBlock<T> register = BLOCKS.register(name, block);
        return register;
    }

    private static <B extends Block> DeferredBlock<B> register(String name, Supplier<? extends Block> block) {
        return (DeferredBlock<B>) baseRegister(name, block, (object) -> ModBlocks.registerBlockItem(object));
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final Supplier<T> block) {
        return () -> {

            return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties());

        };
    }
}

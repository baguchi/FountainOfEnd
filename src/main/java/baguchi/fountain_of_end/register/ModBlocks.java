package baguchi.fountain_of_end.register;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.block.FountainOfEndBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FountainOfEnd.MODID);

    public static final DeferredBlock<LiquidBlock> VOID = noItemRegister("void", () -> new LiquidBlock(ModFluids.VOID.value(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).replaceable().noCollission().strength(1000.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().lightLevel(p_152605_ -> 12).sound(SoundType.EMPTY)));

    public static final DeferredBlock<Block> FOUNTAIN_OF_END = register("fountain_of_end", () -> new FountainOfEndBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(30F, 100.0F).noLootTable().sound(SoundType.NETHERRACK)));


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

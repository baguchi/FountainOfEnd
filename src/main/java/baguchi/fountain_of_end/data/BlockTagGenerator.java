package baguchi.fountain_of_end.data;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.register.ModBlocks;
import baguchi.fountain_of_end.register.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends BlockTagsProvider {
    public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, FountainOfEnd.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.FOUNTAIN_OF_END.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.FOUNTAIN_OF_END.get());
        this.tag(ModTags.Blocks.MOVE_SPREAD).add(ModBlocks.VOID.get()).add(Blocks.END_STONE).addTag(ModTags.Blocks.REPLACEABLE_TO_END);
        this.tag(ModTags.Blocks.REPLACEABLE_TO_END).add(Blocks.WATER).addTag(BlockTags.ENDERMAN_HOLDABLE);
    }
}
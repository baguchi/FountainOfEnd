package baguchi.fountain_of_end.data;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.register.ModBiomes;
import baguchi.fountain_of_end.register.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BiomeTagGenerator extends BiomeTagsProvider {
    public BiomeTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, FountainOfEnd.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        tag(Tags.Biomes.IS_END).add(ModBiomes.OVERWORLD_END);
        tag(ModTags.Biomes.END_FOUNTAIN).addTag(Tags.Biomes.IS_OVERWORLD).remove(Tags.Biomes.IS_UNDERGROUND).remove(Tags.Biomes.IS_OCEAN).remove(Tags.Biomes.IS_RIVER);
    }
}

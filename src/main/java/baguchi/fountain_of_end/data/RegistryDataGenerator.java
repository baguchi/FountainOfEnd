package baguchi.fountain_of_end.data;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.register.ModBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()

            .add(Registries.PROCESSOR_LIST, (context) -> {
            })
            .add(Registries.STRUCTURE, ModStructures::bootstrapStructures)
            .add(Registries.STRUCTURE_SET, ModStructures::bootstrapSets)
            .add(Registries.TEMPLATE_POOL, ModStructures::bootstrapPools)
            .add(Registries.BIOME, ModBiomes::bootstrap);


    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of("minecraft", FountainOfEnd.MODID));
    }
}
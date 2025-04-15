package baguchi.fountain_of_end.data;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.register.ModTags;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class ModStructures {
    public static final ResourceKey<Structure> END_FOUNTAIN = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, "end_fountain"));

    public static final ResourceKey<StructureSet> END_FOUNTAIN_SET = ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, "end_fountain"));

    public static final ResourceKey<StructureTemplatePool> END_FOUNTAIN_MAIN = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, "end_fountain/main"));

    public static void bootstrapStructures(BootstrapContext<Structure> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> pools = context.lookup(Registries.TEMPLATE_POOL);
        context.register(
                END_FOUNTAIN,
                new JigsawStructure(
                        new Structure.StructureSettings.Builder(biomes.getOrThrow(ModTags.Biomes.END_FOUNTAIN))
                                .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                                .build(),
                        pools.getOrThrow(ModStructures.END_FOUNTAIN_MAIN),
                        6,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
    }

    public static void bootstrapSets(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        context.register(END_FOUNTAIN_SET, new StructureSet(structures.getOrThrow(END_FOUNTAIN), new RandomSpreadStructurePlacement(32, 6, RandomSpreadType.LINEAR, 142326620)));
    }

    public static void bootstrapPools(BootstrapContext<StructureTemplatePool> context) {
        Holder<StructureTemplatePool> emptyPool = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);
        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);

        context.register(END_FOUNTAIN_MAIN, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.legacy(name("end_fountain/fountain_1")), 3),
                Pair.of(StructurePoolElement.legacy(name("end_fountain/fountain_2")), 2),
                Pair.of(StructurePoolElement.legacy(name("end_fountain/fountain_3")), 2)
        ), StructureTemplatePool.Projection.RIGID));
    }

    private static String name(String name) {
        return ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, name).toString();
    }
}
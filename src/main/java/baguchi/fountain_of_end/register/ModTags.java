package baguchi.fountain_of_end.register;

import baguchi.fountain_of_end.FountainOfEnd;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Biomes {
        public static final TagKey<Biome> END_FOUNTAIN = tag("has_structure/end_fountain");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> MOVE_SPREAD = create("move_spread");
        public static final TagKey<Block> REPLACEABLE_TO_END = create("replaceable_to_end");

        private static TagKey<Block> create(String p_203847_) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, p_203847_));
        }
    }

}

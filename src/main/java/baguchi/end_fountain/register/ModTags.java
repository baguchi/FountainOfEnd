package baguchi.end_fountain.register;

import baguchi.end_fountain.FountainOfEnd;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModTags {
    public static class Biomes {
        public static final TagKey<Biome> END_FOUNTAIN = tag("has_structure/end_fountain");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, name));
        }
    }
}

package baguchi.fountain_of_end.register;

import baguchi.fountain_of_end.FountainOfEnd;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomes {
    public static final ResourceKey<Biome> OVERWORLD_END = register("overworld_end");

    public static Biome crimsonForest(HolderGetter<PlacedFeature> p_256350_, HolderGetter<ConfiguredWorldCarver<?>> p_256386_) {
        MobSpawnSettings mobspawnsettings = new MobSpawnSettings.Builder()
                .build();
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(p_256350_, p_256386_);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(2.0F)
                .downfall(0.0F)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .waterColor(4159204)
                                .waterFogColor(329011)
                                .fogColor(10518688)
                                .skyColor(calculateSkyColor(2.0F))
                                .ambientParticle(new AmbientParticleSettings(ParticleTypes.WARPED_SPORE, 0.025F))
                                .ambientLoopSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
                                .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0))
                                .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS, 0.0111))
                                .build()
                )
                .mobSpawnSettings(mobspawnsettings)
                .generationSettings(biomegenerationsettings$builder.build())
                .build();
    }

    protected static int calculateSkyColor(float temperature) {
        float $$1 = temperature / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }


    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> vanillaConfiguredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(OVERWORLD_END, crimsonForest(placedFeatures, vanillaConfiguredCarvers));
    }

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, name));
    }
}
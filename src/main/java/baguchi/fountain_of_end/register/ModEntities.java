package baguchi.fountain_of_end.register;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.entity.Snareling;
import baguchi.fountain_of_end.entity.Watchling;
import baguchi.fountain_of_end.entity.projectile.SnareBall;
import baguchi.fountain_of_end.entity.projectile.SnareWebEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(modid = FountainOfEnd.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, FountainOfEnd.MODID);

    public static final Supplier<EntityType<Watchling>> WATCHLING =
            ENTITY_TYPES.register("watchling", () -> EntityType.Builder.of(Watchling::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.8F)
                    .eyeHeight(1.65F)
                    .ridingOffset(-0.7F)
                    .build(FountainOfEnd.MODID + ":watchling"));
    public static final Supplier<EntityType<Snareling>> SNARELING =
            ENTITY_TYPES.register("snareling", () -> EntityType.Builder.of(Snareling::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.9F)
                    .eyeHeight(1.8F)
                    .ridingOffset(-0.7F)
                    .build(FountainOfEnd.MODID + ":snareling"));

    public static final Supplier<EntityType<SnareBall>> SNARE_BALL = ENTITY_TYPES.register("snare_ball", () -> EntityType.Builder.<SnareBall>of(SnareBall::new, MobCategory.MISC)
            .sized(0.3F, 0.3F).updateInterval(30).build(FountainOfEnd.MODID + ":snare_ball"));
    public static final Supplier<EntityType<SnareWebEntity>> SNARE_WEB = ENTITY_TYPES.register("snare_web", () -> EntityType.Builder.<SnareWebEntity>of(SnareWebEntity::new, MobCategory.MISC)
            .sized(3F, 0.1F).updateInterval(10).build(FountainOfEnd.MODID + ":snare_web"));




    @SubscribeEvent
    public static void registerEntity(EntityAttributeCreationEvent event) {
        event.put(WATCHLING.get(), Watchling.createAttributes().build());
        event.put(SNARELING.get(), Snareling.createAttributes().build());
        event.put(SNARE_WEB.get(), SnareWebEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacement(RegisterSpawnPlacementsEvent event) {
        event.register(WATCHLING.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(SNARELING.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}

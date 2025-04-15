package baguchi.end_fountain.register;

import baguchi.end_fountain.FountainOfEnd;
import baguchi.end_fountain.entity.Watchling;
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

    @SubscribeEvent
    public static void registerEntity(EntityAttributeCreationEvent event) {
        event.put(WATCHLING.get(), Watchling.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacement(RegisterSpawnPlacementsEvent event) {
        event.register(WATCHLING.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}

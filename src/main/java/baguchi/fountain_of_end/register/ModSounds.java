package baguchi.fountain_of_end.register;

import baguchi.fountain_of_end.FountainOfEnd;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, FountainOfEnd.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> WATCHLING_IDLE = createEvent("entity.watchling.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> WATCHLING_HURT = createEvent("entity.watchling.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> WATCHLING_DEATH = createEvent("entity.watchling.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> WATCHLING_ATTACK = createEvent("entity.watchling.attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> SNARELING_IDLE = createEvent("entity.snareling.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNARELING_HURT = createEvent("entity.snareling.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNARELING_DEATH = createEvent("entity.snareling.death");

    private static DeferredHolder<SoundEvent, SoundEvent> createEvent(String sound) {
        ResourceLocation name = ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, sound);
        return SOUND_EVENTS.register(sound, () -> SoundEvent.createVariableRangeEvent(name));
    }

}

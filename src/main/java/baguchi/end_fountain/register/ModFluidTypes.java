package baguchi.end_fountain.register;

import baguchi.end_fountain.FountainOfEnd;
import baguchi.end_fountain.fluid.VoidFluidType;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, FountainOfEnd.MODID);

    public static final Supplier<FluidType> VOID = FLUID_TYPES.register("void", () -> new VoidFluidType(FluidType.Properties.create().canExtinguish(true).motionScale(0.005F).viscosity(1400).fallDistanceModifier(0F).supportsBoating(true).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)));
}
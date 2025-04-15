package baguchi.fountain_of_end.register;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.fluid.VoidFluid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, FountainOfEnd.MODID);

    public static final DeferredHolder<Fluid, VoidFluid.Source> VOID = FLUIDS.register("void", () -> new VoidFluid.Source());
    public static final DeferredHolder<Fluid, VoidFluid.Flowing> VOID_FLOW = FLUIDS.register("void_flow", () -> new VoidFluid.Flowing());

}
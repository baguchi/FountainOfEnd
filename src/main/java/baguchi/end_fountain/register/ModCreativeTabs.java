package baguchi.end_fountain.register;

import baguchi.end_fountain.FountainOfEnd;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FountainOfEnd.MODID);


    public static final Supplier<CreativeModeTab> FOUNTAIN_OF_END = CREATIVE_MODE_TABS.register("fountain_of_end", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup." + FountainOfEnd.MODID))
            .icon(() -> ModBlocks.FOUNTAIN_OF_END.toStack())
            .displayItems((parameters, output) -> {
                output.acceptAll(Stream.of(
                        ModBlocks.FOUNTAIN_OF_END.asItem(),
                        ModItems.VOID_BUCKET.get(),
                        ModItems.WATCHLING_SPAWNEGG.get()
                ).map(sup -> {
                    return sup.getDefaultInstance();
                }).toList());
            }).build());
}

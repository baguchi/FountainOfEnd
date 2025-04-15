package baguchi.end_fountain.register;

import baguchi.end_fountain.FountainOfEnd;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, FountainOfEnd.MODID);
    public static final Supplier<Item> VOID_BUCKET = ITEMS.register("void_bucket", () -> new BucketItem(ModFluids.VOID.value(), (new Item.Properties()).stacksTo(1).craftRemainder(Items.BUCKET)));
    public static final Supplier<Item> WATCHLING_SPAWNEGG = ITEMS.register("watchling_spawn_egg", () -> new DeferredSpawnEggItem(ModEntities.WATCHLING, 1447446, 0, (new Item.Properties())));
    public static final Supplier<Item> SNARELING_SPAWNEGG = ITEMS.register("snareling_spawn_egg", () -> new DeferredSpawnEggItem(ModEntities.SNARELING, 1447446, 0xB8D731, (new Item.Properties())));

}

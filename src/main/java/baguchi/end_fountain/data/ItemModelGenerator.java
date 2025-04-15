package baguchi.end_fountain.data;

import baguchi.end_fountain.FountainOfEnd;
import baguchi.end_fountain.register.ModBlocks;
import baguchi.end_fountain.register.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static baguchi.end_fountain.FountainOfEnd.prefix;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, FountainOfEnd.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.basicItem(ModBlocks.FOUNTAIN_OF_END.get().asItem());
        this.basicItem(ModItems.VOID_BUCKET.get());
        this.spawnEgg(ModItems.WATCHLING_SPAWNEGG.get());
        this.spawnEgg(ModItems.SNARELING_SPAWNEGG.get());
    }

    public ItemModelBuilder itemBlockFlat(Block block) {
        return itemBlockFlat(block, blockName(block));
    }

    public ItemModelBuilder itemBlockFlat(Block block, String name) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + name));
    }

    private void toBlock(Block b) {
        toBlockModel(b, BuiltInRegistries.BLOCK.getKey(b).getPath());
    }

    private void toBlockModel(Block b, String model) {
        toBlockModel(b, prefix("block/" + model));
    }

    private void toBlockModel(Block b, ResourceLocation model) {
        withExistingParent(BuiltInRegistries.BLOCK.getKey(b).getPath(), model);
    }

    public String blockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    public ItemModelBuilder spawnEgg(Item item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item).getPath(), mcLoc("item/template_spawn_egg"));
    }
}
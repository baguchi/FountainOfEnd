package baguchi.end_fountain.client;

import baguchi.end_fountain.EndFountain;
import baguchi.end_fountain.blockentity.FountainOfEndBlockEntity;
import baguchi.end_fountain.client.render.blockentity.FountainOfEndRenderer;
import baguchi.end_fountain.register.ModBlockEntitys;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = EndFountain.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRenderer {
    @SubscribeEvent
    public static void setupRendering(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockEntitys.FOUNTAIN_OF_END.get(), context -> new FountainOfEndRenderer());
    }
}

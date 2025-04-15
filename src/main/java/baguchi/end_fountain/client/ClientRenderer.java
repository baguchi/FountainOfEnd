package baguchi.end_fountain.client;

import baguchi.end_fountain.FountainOfEnd;
import baguchi.end_fountain.client.model.SnareBallModel;
import baguchi.end_fountain.client.model.SnareWebModel;
import baguchi.end_fountain.client.model.SnarelingModel;
import baguchi.end_fountain.client.model.WatchlingModel;
import baguchi.end_fountain.client.render.blockentity.FountainOfEndRenderer;
import baguchi.end_fountain.client.render.entity.SnareBallRenderer;
import baguchi.end_fountain.client.render.entity.SnareWebRenderer;
import baguchi.end_fountain.client.render.entity.SnarelingRenderer;
import baguchi.end_fountain.client.render.entity.WatchlingRenderer;
import baguchi.end_fountain.fluid.VoidFluidType;
import baguchi.end_fountain.register.ModBlockEntitys;
import baguchi.end_fountain.register.ModEntities;
import baguchi.end_fountain.register.ModFluidTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = FountainOfEnd.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRenderer {
    @SubscribeEvent
    public static void setupRendering(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntities.WATCHLING.get(), WatchlingRenderer::new);
        event.registerEntityRenderer(ModEntities.SNARELING.get(), SnarelingRenderer::new);
        event.registerEntityRenderer(ModEntities.SNARE_BALL.get(), SnareBallRenderer::new);
        event.registerEntityRenderer(ModEntities.SNARE_WEB.get(), SnareWebRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntitys.FOUNTAIN_OF_END.get(), context -> new FountainOfEndRenderer());
    }

    @SubscribeEvent
    public static void setupRendering(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WatchlingModel.LAYER_LOCATION, WatchlingModel::createBodyLayer);
        event.registerLayerDefinition(SnarelingModel.LAYER_LOCATION, SnarelingModel::createBodyLayer);
        event.registerLayerDefinition(SnareBallModel.LAYER_LOCATION, SnareBallModel::createBodyLayer);
        event.registerLayerDefinition(SnareWebModel.LAYER_LOCATION, SnareWebModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new VoidFluidType.VoidRender(), ModFluidTypes.VOID.get());
    }


}

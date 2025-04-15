package baguchi.end_fountain.client.render.entity;

import baguchi.end_fountain.FountainOfEnd;
import baguchi.end_fountain.client.model.WatchlingModel;
import baguchi.end_fountain.client.render.entity.layer.WatchlingEyeLayer;
import baguchi.end_fountain.entity.Watchling;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WatchlingRenderer extends MobRenderer<Watchling, WatchlingModel<Watchling>> {
    private static final ResourceLocation ENDERMAN_LOCATION = ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, "textures/entity/enderling/watchling/watchling.png");

    public WatchlingRenderer(EntityRendererProvider.Context context) {
        super(context, new WatchlingModel<>(context.bakeLayer(WatchlingModel.LAYER_LOCATION)), 0.3F);
        this.addLayer(new WatchlingEyeLayer<>(this));

    }


    @Override
    public ResourceLocation getTextureLocation(Watchling watchling) {
        return ENDERMAN_LOCATION;
    }
}

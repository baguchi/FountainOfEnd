package baguchi.fountain_of_end.client.render.entity;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.client.model.WatchlingModel;
import baguchi.fountain_of_end.client.render.entity.layer.WatchlingEyeLayer;
import baguchi.fountain_of_end.entity.Watchling;
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

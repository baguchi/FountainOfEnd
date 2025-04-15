package baguchi.fountain_of_end.client.render.entity;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.client.model.SnarelingModel;
import baguchi.fountain_of_end.client.render.entity.layer.SnarelingEyeLayer;
import baguchi.fountain_of_end.entity.Snareling;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnarelingRenderer extends MobRenderer<Snareling, SnarelingModel<Snareling>> {
    private static final ResourceLocation ENDERMAN_LOCATION = ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, "textures/entity/enderling/snareling/snareling.png");

    public SnarelingRenderer(EntityRendererProvider.Context context) {
        super(context, new SnarelingModel<>(context.bakeLayer(SnarelingModel.LAYER_LOCATION)), 0.3F);
        this.addLayer(new SnarelingEyeLayer<>(this));
    }


    @Override
    public ResourceLocation getTextureLocation(Snareling snareling) {
        return ENDERMAN_LOCATION;
    }
}

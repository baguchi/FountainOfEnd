package baguchi.end_fountain.client.render.entity;

import baguchi.end_fountain.FountainOfEnd;
import baguchi.end_fountain.client.model.SnarelingModel;
import baguchi.end_fountain.client.render.entity.layer.SnarelingEyeLayer;
import baguchi.end_fountain.entity.Snareling;
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

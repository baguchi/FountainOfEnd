package baguchi.fountain_of_end.client.render.entity.layer;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.client.model.SnarelingModel;
import baguchi.fountain_of_end.entity.Snareling;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class SnarelingEyeLayer<T extends Snareling> extends EyesLayer<T, SnarelingModel<T>> {
    private static final RenderType EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, "textures/entity/enderling/snareling/snareling_eye.png"));

    public SnarelingEyeLayer(RenderLayerParent<T, SnarelingModel<T>> p_116964_) {
        super(p_116964_);
    }

    public RenderType renderType() {
        return EYES;
    }
}

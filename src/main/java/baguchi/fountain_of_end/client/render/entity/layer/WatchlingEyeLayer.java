package baguchi.fountain_of_end.client.render.entity.layer;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.client.model.WatchlingModel;
import baguchi.fountain_of_end.entity.Watchling;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class WatchlingEyeLayer<T extends Watchling> extends EyesLayer<T, WatchlingModel<T>> {
    private static final RenderType EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, "textures/entity/enderling/watchling/watchling_eye.png"));

    public WatchlingEyeLayer(RenderLayerParent<T, WatchlingModel<T>> p_116964_) {
        super(p_116964_);
    }

    public RenderType renderType() {
        return EYES;
    }
}

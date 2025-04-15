package baguchi.end_fountain.client.render.layer;

import baguchi.end_fountain.FountainOfEnd;
import baguchi.end_fountain.client.model.WatchlingModel;
import baguchi.end_fountain.entity.Watchling;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class WatchlingEyeLayer<T extends Watchling> extends EyesLayer<T, WatchlingModel<T>> {
    private static final RenderType EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, "textures/entity/enderling/watchling_eye.png"));

    public WatchlingEyeLayer(RenderLayerParent<T, WatchlingModel<T>> p_116964_) {
        super(p_116964_);
    }

    public RenderType renderType() {
        return EYES;
    }
}

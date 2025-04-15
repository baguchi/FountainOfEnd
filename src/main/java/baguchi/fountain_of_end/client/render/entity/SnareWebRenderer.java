package baguchi.fountain_of_end.client.render.entity;

import baguchi.fountain_of_end.FountainOfEnd;
import baguchi.fountain_of_end.client.model.SnareWebModel;
import baguchi.fountain_of_end.entity.projectile.SnareWebEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SnareWebRenderer<T extends SnareWebEntity> extends EntityRenderer<T> {
    private static final ResourceLocation LLAMA_SPIT_LOCATION = ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, "textures/entity/enderling/snareling/snare_web.png");
    private final SnareWebModel<T> model;

    public SnareWebRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SnareWebModel<>(context.bakeLayer(SnareWebModel.LAYER_LOCATION));
    }

    @Override
    public void render(T llamaSpit, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();

        poseStack.scale(3, 3, 3);
        poseStack.rotateAround(llamaSpit.getAttachFace().getOpposite().getRotation(), 0.0F, 0.0F, 0.0F);
        poseStack.mulPose(Axis.XP.rotationDegrees(0));
        poseStack.translate(0.0F, -1.501F, 0);

        this.model.setupAnim(llamaSpit, g, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.eyes(LLAMA_SPIT_LOCATION));
        this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(llamaSpit, f, g, poseStack, multiBufferSource, i);
    }

    public ResourceLocation getTextureLocation(T llamaSpit) {
        return LLAMA_SPIT_LOCATION;
    }
}
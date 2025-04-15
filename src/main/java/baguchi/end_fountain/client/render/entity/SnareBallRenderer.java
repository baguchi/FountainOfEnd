package baguchi.end_fountain.client.render.entity;

import baguchi.end_fountain.FountainOfEnd;
import baguchi.end_fountain.client.model.SnareBallModel;
import baguchi.end_fountain.entity.projectile.SnareBall;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SnareBallRenderer<T extends SnareBall> extends EntityRenderer<T> {
    private static final ResourceLocation LLAMA_SPIT_LOCATION = ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, "textures/entity/enderling/snareling/snare_ball.png");
    private final SnareBallModel<T> model;

    public SnareBallRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SnareBallModel<>(context.bakeLayer(SnareBallModel.LAYER_LOCATION));
    }

    public void render(T llamaSpit, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 4F / 16F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(g, llamaSpit.yRotO, llamaSpit.getYRot()) - 180F));
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(g, llamaSpit.xRotO, llamaSpit.getXRot())));
        poseStack.translate(0.0F, -1.501F + 3F / 16F, -2.5F / 16F);
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
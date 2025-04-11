package baguchi.end_fountain.client.render.blockentity;

import baguchi.end_fountain.EndFountain;
import baguchi.end_fountain.blockentity.FountainOfEndBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FountainOfEndRenderer implements BlockEntityRenderer<FountainOfEndBlockEntity> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EndFountain.MODID, "textures/environment/fountain_of_end.png");
    @Override
    public void render(FountainOfEndBlockEntity fountainOfEndBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        int px = Mth.floor(camera.getPosition().x());
        int py = Mth.floor(camera.getPosition().y());
        int pz = Mth.floor(camera.getPosition().z());

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = null;

        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        int range = 5;
        if (Minecraft.useFancyGraphics()) {
            range = 10;
        }

        RenderSystem.depthMask(Minecraft.useShaderTransparency());

        float combinedTicks = fountainOfEndBlockEntity.tick + v;
        RenderSystem.setShader(GameRenderer::getParticleShader);

        int fullbright = 15 << 20 | 15 << 4;
        RenderSystem.setShaderTexture(0, TEXTURE);
        bufferBuilder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        float countFactor = -((fountainOfEndBlockEntity.tick & 511) + v) / 512.0F;
        float uFactor = 0; //no moving horizontally
        float vFactor = combinedTicks * 0.005F;
        renderEffect(bufferBuilder, 0, 0, 0, 1024, 5, 5, countFactor, uFactor, vFactor, new float[]{1.0F, 1.0F, 1.0F, 0.8F}, fullbright);

        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());


        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static void renderEffect(BufferBuilder bufferBuilder, double fountainX, double fountainZ, int minY, int maxY, int dx, int dz, float countFactor, float uFactor, float vFactor, float[] color, int light) {
        int blockLight = light >> 16 & 65535;
        int skyLight = light & 65535;
        bufferBuilder
                .addVertex((float) (dx - fountainX + 0.5F), (float) (minY), (float) (dz - fountainZ + 0.5F))
                .setUv(0.0F + uFactor, minY * 0.25F + countFactor + vFactor)
                .setColor(color[0], color[1], color[2], color[3])
                .setUv2(blockLight, skyLight);
        bufferBuilder
                .addVertex((float) (dx + fountainX + 0.5F), (float) (minY), (float) (dz + fountainZ + 0.5F))
                .setUv(1.0F + uFactor, minY * 0.25F + countFactor + vFactor)
                .setColor(color[0], color[1], color[2], color[3])
                .setUv2(blockLight, skyLight);
        bufferBuilder
                .addVertex((float) (dx + fountainX + 0.5F), (float) (maxY), (float) (dz + fountainZ + 0.5F))
                .setUv(1.0F + uFactor, maxY * 0.25F + countFactor + vFactor)
                .setColor(color[0], color[1], color[2], color[3])
                .setUv2(blockLight, skyLight);
        bufferBuilder
                .addVertex((float) (dx - fountainX + 0.5F), (float) (maxY), (float) (dz - fountainZ + 0.5F))
                .setUv(0.0F + uFactor, maxY * 0.25F + countFactor + vFactor)
                .setColor(color[0], color[1], color[2], color[3])
                .setUv2(blockLight, skyLight);
    }
    public boolean shouldRenderOffScreen(FountainOfEndBlockEntity blockEntity) {
        return true;
    }

    public int getViewDistance() {
        return 256;
    }

    public boolean shouldRender(FountainOfEndBlockEntity blockEntity, Vec3 cameraPos) {
        return Vec3.atCenterOf(blockEntity.getBlockPos()).multiply((double)1.0F, (double)0.0F, (double)1.0F).closerThan(cameraPos.multiply((double)1.0F, (double)0.0F, (double)1.0F), (double)this.getViewDistance());
    }

    public AABB getRenderBoundingBox(FountainOfEndBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return new AABB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)pos.getX() + (double)1.0F, (double)1024.0F, (double)pos.getZ() + (double)1.0F);
    }
}

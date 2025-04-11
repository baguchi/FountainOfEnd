package baguchi.end_fountain.client.render.blockentity;

import baguchi.end_fountain.FountainOfEnd;
import baguchi.end_fountain.blockentity.FountainOfEndBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FountainOfEndRenderer implements BlockEntityRenderer<FountainOfEndBlockEntity> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, "textures/environment/fountain_of_end.png");
    @Override
    public void render(FountainOfEndBlockEntity fountainOfEndBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        int px = Mth.floor(fountainOfEndBlockEntity.getBlockPos().getCenter().x());
        int py = Mth.floor(fountainOfEndBlockEntity.getBlockPos().getCenter().y());
        int pz = Mth.floor(fountainOfEndBlockEntity.getBlockPos().getCenter().z());

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = null;
        Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();


        RenderSystem.depthMask(Minecraft.useShaderTransparency());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        float combinedTicks = fountainOfEndBlockEntity.tick + v;
        RenderSystem.setShader(GameRenderer::getParticleShader);
        int range = 3;
        for (int dz = pz - range; dz <= pz + range; ++dz) {
            for (int dx = px - range; dx <= px + range; ++dx) {
                int fullbright = 15 << 20 | 15 << 4;
                RenderSystem.setShaderTexture(0, TEXTURE);
                bufferBuilder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                float countFactor = -((fountainOfEndBlockEntity.tick & 511) + v) / 512.0F;
                float uFactor = 0; //no moving horizontally
                float vFactor = -combinedTicks * 0.0065F;
                double xRange = 3;
                double zRange = 0;
                renderEffect(bufferBuilder, xRange, zRange, fountainOfEndBlockEntity.getBlockPos().getY(), 1024, camera.getPosition(), dx, dz, countFactor, uFactor, vFactor, new float[]{1.0F, 1.0F, 1.0F, 0.8F}, fullbright);
            }
        }
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());

        for (int dz = pz - range; dz <= pz + range; ++dz) {
            for (int dx = px - range; dx <= px + range; ++dx) {
                int fullbright = 15 << 20 | 15 << 4;
                RenderSystem.setShaderTexture(0, TEXTURE);
                bufferBuilder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                float countFactor = -((fountainOfEndBlockEntity.tick & 511) + v) / 512.0F;
                float uFactor = 0; //no moving horizontally
                float vFactor = -combinedTicks * 0.0065F;
                double xRange = 0;
                double zRange = 3;
                renderEffect(bufferBuilder, xRange, zRange, fountainOfEndBlockEntity.getBlockPos().getY(), 1024, camera.getPosition(), dx, dz, countFactor, uFactor, vFactor, new float[]{1.0F, 1.0F, 1.0F, 0.8F}, fullbright);
            }
        }
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
        Minecraft.getInstance().gameRenderer.lightTexture().turnOffLightLayer();

        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static void renderEffect(BufferBuilder bufferBuilder, double renderX, double renderZ, int minY, int maxY, Vec3 camera, double dx, double dz, float countFactor, float uFactor, float vFactor, float[] color, int light) {
        int blockLight = light >> 16 & 65535;
        int skyLight = light & 65535;
        bufferBuilder
                .addVertex((float) (dx - camera.x() - renderX + 3F), (float) (minY - camera.y()), (float) (dz - camera.z() - renderZ + 3F))
                .setUv(0.0F + uFactor, minY * 0.05F + countFactor + vFactor)
                .setColor(color[0], color[1], color[2], color[3])
                .setUv2(blockLight, skyLight);
        bufferBuilder
                .addVertex((float) (dx - camera.x() + renderX + 3F), (float) (minY - camera.y()), (float) (dz - camera.z() + renderZ + 3F))
                .setUv(1.0F + uFactor, minY * 0.05F + countFactor + vFactor)
                .setColor(color[0], color[1], color[2], color[3])
                .setUv2(blockLight, skyLight);
        bufferBuilder
                .addVertex((float) (dx - camera.x() + renderX + 3F), (float) (maxY - camera.y()), (float) (dz - camera.z() + renderZ + 3F))
                .setUv(1.0F + uFactor, maxY * 0.05F + countFactor + vFactor)
                .setColor(color[0], color[1], color[2], color[3])
                .setUv2(blockLight, skyLight);
        bufferBuilder
                .addVertex((float) (dx - camera.x() - renderX + 3F), (float) (maxY - camera.y()), (float) (dz - camera.z() - renderZ + 3F))
                .setUv(0.0F + uFactor, maxY * 0.05F + countFactor + vFactor)
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
        return net.minecraft.world.phys.AABB.INFINITE;
    }
}

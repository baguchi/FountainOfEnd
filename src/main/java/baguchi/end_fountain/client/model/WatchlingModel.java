package baguchi.end_fountain.client.model;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchi.end_fountain.FountainOfEnd;
import baguchi.end_fountain.client.animation.WatchlingAnimations;
import baguchi.end_fountain.entity.Watchling;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WatchlingModel<T extends Watchling> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(FountainOfEnd.MODID, "watchling"), "main");
    private final ModelPart root;
    private final ModelPart Head;
    private final ModelPart eye_r;
    private final ModelPart eye_lid_r;
    private final ModelPart eye_l;
    private final ModelPart eye_lid_l;
    private final ModelPart Body;
    private final ModelPart eye_r2;
    private final ModelPart eye_lid_r2;
    private final ModelPart eye_l2;
    private final ModelPart eye_lid_l2;
    private final ModelPart eye_r3;
    private final ModelPart eye_lid_r3;
    private final ModelPart eye_r4;
    private final ModelPart eye_lid_r4;
    private final ModelPart eye_l3;
    private final ModelPart eye_lid_l5;
    private final ModelPart RightArm;
    private final ModelPart LeftArm;
    private final ModelPart RightLeg;
    private final ModelPart LeftLeg;

    public WatchlingModel(ModelPart root) {
        this.root = root;
        this.Head = root.getChild("Head");
        this.eye_r = this.Head.getChild("eye_r");
        this.eye_lid_r = this.eye_r.getChild("eye_lid_r");
        this.eye_l = this.Head.getChild("eye_l");
        this.eye_lid_l = this.eye_l.getChild("eye_lid_l");
        this.Body = root.getChild("Body");
        this.eye_r2 = this.Body.getChild("eye_r2");
        this.eye_lid_r2 = this.eye_r2.getChild("eye_lid_r2");
        this.eye_l2 = this.Body.getChild("eye_l2");
        this.eye_lid_l2 = this.eye_l2.getChild("eye_lid_l2");
        this.eye_r3 = this.Body.getChild("eye_r3");
        this.eye_lid_r3 = this.eye_r3.getChild("eye_lid_r3");
        this.eye_r4 = this.Body.getChild("eye_r4");
        this.eye_lid_r4 = this.eye_r4.getChild("eye_lid_r4");
        this.eye_l3 = this.Body.getChild("eye_l3");
        this.eye_lid_l5 = this.eye_l3.getChild("eye_lid_l5");
        this.RightArm = root.getChild("RightArm");
        this.LeftArm = root.getChild("LeftArm");
        this.RightLeg = root.getChild("RightLeg");
        this.LeftLeg = root.getChild("LeftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eye_r = Head.addOrReplaceChild("eye_r", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -3.0F, -3.6F));

        PartDefinition eye_lid_r = eye_r.addOrReplaceChild("eye_lid_r", CubeListBuilder.create().texOffs(0, 1).addBox(-0.5F, -1.0F, -0.1F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eye_l = Head.addOrReplaceChild("eye_l", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.0F, -3.6F));

        PartDefinition eye_lid_l = eye_l.addOrReplaceChild("eye_lid_l", CubeListBuilder.create().texOffs(0, 1).addBox(-0.5F, -1.0F, -0.1F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(32, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eye_r2 = Body.addOrReplaceChild("eye_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 3.0F, -2.1F));

        PartDefinition eye_lid_r2 = eye_r2.addOrReplaceChild("eye_lid_r2", CubeListBuilder.create().texOffs(0, 1).addBox(-0.5F, -1.0F, -0.1F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eye_l2 = Body.addOrReplaceChild("eye_l2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 7.0F, -2.1F));

        PartDefinition eye_lid_l2 = eye_l2.addOrReplaceChild("eye_lid_l2", CubeListBuilder.create().texOffs(0, 1).addBox(-0.5F, -1.0F, -0.1F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eye_r3 = Body.addOrReplaceChild("eye_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 9.0F, -2.1F));

        PartDefinition eye_lid_r3 = eye_r3.addOrReplaceChild("eye_lid_r3", CubeListBuilder.create().texOffs(0, 1).addBox(-0.5F, -1.0F, -0.1F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eye_r4 = Body.addOrReplaceChild("eye_r4", CubeListBuilder.create().texOffs(0, 2).mirror().addBox(-1.5F, -1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 9.0F, 2.1F));

        PartDefinition eye_lid_r4 = eye_r4.addOrReplaceChild("eye_lid_r4", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.05F));

        PartDefinition eye_l3 = Body.addOrReplaceChild("eye_l3", CubeListBuilder.create().texOffs(0, 2).mirror().addBox(-1.5F, -1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 5.0F, 2.1F));

        PartDefinition eye_lid_l5 = eye_l3.addOrReplaceChild("eye_lid_l5", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-0.5F, -1.0F, -0.05F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.1F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(56, 0).addBox(-3.0F, -2.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 2.0F, 0.0F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.Head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.Head.xRot = headPitch * ((float) Math.PI / 180F);
        this.RightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.LeftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.RightArm.zRot = 0.0F;
        this.RightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.LeftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        float f = Mth.rotLerp(ageInTicks - entity.tickCount, entity.yBodyRotO, entity.yBodyRot);
        this.eye_lid_r.setPos(Mth.clamp(((netHeadYaw - f + 180) % 360 - 180) / 90, -0.75F, 0.75F), 0, 0);
        this.eye_lid_l.setPos(Mth.clamp(((netHeadYaw - f + 180) % 360 - 180) / 90, -0.75F, 0.75F), 0, 0);
        this.eye_lid_r2.setPos(Mth.clamp(((netHeadYaw - f + 180) % 360 - 180) / 90, -0.75F, 0.75F), 0, 0);
        this.eye_lid_l2.setPos(Mth.clamp(((netHeadYaw - f + 180) % 360 - 180) / 90, -0.75F, 0.75F), 0, 0);
        this.eye_lid_r3.setPos(Mth.clamp(((netHeadYaw - f + 180) % 360 - 180) / 90, -0.75F, 0.75F), 0, 0);
        this.eye_lid_l5.setPos(Mth.clamp(-((netHeadYaw - f + 180) % 360 - 180) / 90, -0.75F, 0.75F), 0, 0.11F);
        this.eye_lid_r4.setPos(Mth.clamp(-((netHeadYaw - f + 180) % 360 - 180) / 90, -0.75F, 0.75F), 0, 0.11F);


        if (entity.attackAnimationState.isStarted()) {
            this.RightArm.xRot = 0;
            this.LeftArm.xRot = 0;
        }
        this.animate(entity.attackAnimationState, WatchlingAnimations.attack, ageInTicks);
        this.animate(entity.idleAnimationState, WatchlingAnimations.idle, ageInTicks);

    }


    @Override
    public ModelPart root() {
        return this.root;
    }
}
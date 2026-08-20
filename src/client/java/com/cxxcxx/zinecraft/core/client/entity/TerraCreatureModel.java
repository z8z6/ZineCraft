package com.cxxcxx.zinecraft.core.client.entity;

import com.cxxcxx.zinecraft.core.entity.TerraBeastEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Vanilla renderer model generated from the four editable Blockbench skeletons.
 */
public final class TerraCreatureModel<T extends TerraBeastEntity> extends HierarchicalModel<T> {
  private static final String[] BONES = {
      "body", "head", "jaw", "tail", "front_left", "front_right",
      "middle_left", "middle_right", "back_left", "back_right"
  };
  private final ModelPart root;
  private final ModelPart head;

  public TerraCreatureModel(ModelPart bakedRoot) {
    root = bakedRoot.getChild("root");
    head = root.getChild("head");
  }

  public static LayerDefinition sandbeastLayer() {
    MeshDefinition mesh = mesh();
    PartDefinition root = mesh.getRoot().getChild("root");
    root.addOrReplaceChild("body", CubeListBuilder.create()
        .texOffs(0, 0).addBox(-8.0F, -5.0F, -9.0F, 16.0F, 10.0F, 18.0F)
        .texOffs(0, 36).addBox(-9.0F, -5.0F, -11.0F, 18.0F, 11.0F, 8.0F)
        .texOffs(72, 32).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 4.0F, 13.0F)
        .texOffs(128, 16).addBox(-5.0F, -14.0F, -4.0F, 10.0F, 7.0F, 8.0F), PartPose.offset(0.0F, 13.0F, 0.0F));
    PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
        .texOffs(0, 64).addBox(-5.0F, -5.0F, -8.0F, 10.0F, 8.0F, 8.0F)
        .texOffs(40, 64).addBox(-4.0F, -2.0F, -12.0F, 8.0F, 5.0F, 5.0F)
        .texOffs(88, 64).addBox(-1.0F, -5.0F, -15.0F, 2.0F, 2.0F, 5.0F), PartPose.offset(0.0F, 12.0F, -9.0F));
    head.addOrReplaceChild("ears", CubeListBuilder.create().texOffs(64, 64)
        .addBox(3.0F, -8.0F, -3.0F, 3.0F, 5.0F, 3.0F)
        .addBox(-6.0F, -8.0F, -3.0F, 3.0F, 5.0F, 3.0F), PartPose.ZERO);
    fillCommonBones(root, true, false);
    return LayerDefinition.create(mesh, 256, 256);
  }

  public static LayerDefinition rivenbeastLayer() {
    MeshDefinition mesh = mesh();
    PartDefinition root = mesh.getRoot().getChild("root");
    root.addOrReplaceChild("body", CubeListBuilder.create()
        .texOffs(0, 0).addBox(-7.0F, -5.0F, -8.0F, 14.0F, 10.0F, 17.0F)
        .texOffs(48, 40).addBox(-8.0F, -6.0F, -9.0F, 16.0F, 12.0F, 7.0F)
        .texOffs(64, 48).addBox(-6.0F, -8.0F, -7.0F, 12.0F, 3.0F, 14.0F), PartPose.offset(0.0F, 13.0F, 0.0F));
    PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
        .texOffs(0, 32).addBox(-5.0F, -5.0F, -8.0F, 10.0F, 8.0F, 8.0F)
        .texOffs(40, 32).addBox(-3.5F, -2.0F, -12.0F, 7.0F, 5.0F, 5.0F), PartPose.offset(0.0F, 12.0F, -9.0F));
    head.addOrReplaceChild("ears", CubeListBuilder.create().texOffs(96, 0)
        .addBox(2.5F, -8.0F, -5.0F, 3.0F, 4.0F, 3.0F)
        .addBox(-5.5F, -8.0F, -5.0F, 3.0F, 4.0F, 3.0F), PartPose.ZERO);
    fillCommonBones(root, true, false);
    return LayerDefinition.create(mesh, 128, 128);
  }

  public static LayerDefinition clampbeastLayer() {
    MeshDefinition mesh = mesh();
    PartDefinition root = mesh.getRoot().getChild("root");
    root.addOrReplaceChild("body", CubeListBuilder.create()
        .texOffs(0, 0).addBox(-7.0F, -4.0F, -7.0F, 14.0F, 7.0F, 15.0F)
        .texOffs(48, 40).addBox(-6.0F, -7.0F, -5.0F, 12.0F, 5.0F, 12.0F), PartPose.offset(0.0F, 16.0F, 0.0F));
    root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 32)
        .addBox(-6.0F, -4.0F, -7.0F, 12.0F, 7.0F, 7.0F), PartPose.offset(0.0F, 16.0F, -7.0F));
    root.addOrReplaceChild("front_left", CubeListBuilder.create().texOffs(64, 0)
        .addBox(0.0F, -2.0F, -3.0F, 8.0F, 4.0F, 4.0F).texOffs(96, 32)
        .addBox(5.0F, -4.0F, -8.0F, 6.0F, 6.0F, 6.0F), PartPose.offset(5.0F, 16.0F, -8.0F));
    root.addOrReplaceChild("front_right", CubeListBuilder.create().texOffs(64, 0)
        .addBox(-8.0F, -2.0F, -3.0F, 8.0F, 4.0F, 4.0F).texOffs(96, 32)
        .addBox(-11.0F, -4.0F, -8.0F, 6.0F, 6.0F, 6.0F), PartPose.offset(-5.0F, 16.0F, -8.0F));
    fillCommonBones(root, false, true);
    return LayerDefinition.create(mesh, 128, 128);
  }

  public static LayerDefinition packbeastLayer() {
    MeshDefinition mesh = mesh();
    PartDefinition root = mesh.getRoot().getChild("root");
    root.addOrReplaceChild("body", CubeListBuilder.create()
        .texOffs(0, 0).addBox(-8.0F, -5.0F, -9.0F, 16.0F, 11.0F, 19.0F)
        .texOffs(48, 40).addBox(-7.0F, -11.0F, -5.0F, 14.0F, 8.0F, 12.0F)
        .texOffs(64, 48).addBox(-8.5F, -7.0F, -10.0F, 17.0F, 7.0F, 6.0F), PartPose.offset(0.0F, 13.0F, 0.0F));
    PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
        .texOffs(0, 32).addBox(-5.5F, -5.0F, -9.0F, 11.0F, 8.0F, 9.0F)
        .texOffs(40, 32).addBox(-4.5F, -2.0F, -14.0F, 9.0F, 6.0F, 7.0F)
        .texOffs(96, 32).addBox(-1.5F, -5.0F, -18.0F, 3.0F, 4.0F, 6.0F), PartPose.offset(0.0F, 12.0F, -9.0F));
    head.addOrReplaceChild("ears", CubeListBuilder.create().texOffs(96, 0)
        .addBox(3.5F, -7.0F, -5.0F, 2.5F, 4.0F, 3.0F)
        .addBox(-6.0F, -7.0F, -5.0F, 2.5F, 4.0F, 3.0F), PartPose.ZERO);
    fillCommonBones(root, true, false);
    return LayerDefinition.create(mesh, 128, 128);
  }

  private static MeshDefinition mesh() {
    MeshDefinition mesh = new MeshDefinition();
    PartDefinition root = mesh.getRoot().addOrReplaceChild("root", CubeListBuilder.create(), PartPose.ZERO);
    for (String bone : BONES) root.addOrReplaceChild(bone, CubeListBuilder.create(), PartPose.ZERO);
    return mesh;
  }

  private static void fillCommonBones(PartDefinition root, boolean quadruped, boolean arthropod) {
    root.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(96, 32)
        .addBox(-3.0F, -1.0F, -4.0F, 6.0F, 2.0F, 5.0F), PartPose.offset(0.0F, 15.0F, -18.0F));
    root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(72, 0)
        .addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, arthropod ? 5.0F : 11.0F), PartPose.offset(0.0F, 13.0F, 7.0F));
    if (quadruped) {
      addLeg(root, "front_left", 5.0F, -7.0F);
      addLeg(root, "front_right", -5.0F, -7.0F);
      addLeg(root, "back_left", 5.0F, 7.0F);
      addLeg(root, "back_right", -5.0F, 7.0F);
    }
    if (arthropod) {
      addWideLeg(root, "middle_left", 6.0F, -2.0F, false);
      addWideLeg(root, "middle_right", -6.0F, -2.0F, true);
      addWideLeg(root, "back_left", 6.0F, 5.0F, false);
      addWideLeg(root, "back_right", -6.0F, 5.0F, true);
    }
  }

  private static void addLeg(PartDefinition root, String name, float x, float z) {
    root.addOrReplaceChild(name, CubeListBuilder.create().texOffs(64, 0)
        .addBox(-1.8F, 0.0F, -2.0F, 3.6F, 8.0F, 4.0F), PartPose.offset(x, 16.0F, z));
  }

  private static void addWideLeg(PartDefinition root, String name, float x, float z, boolean mirror) {
    root.addOrReplaceChild(name, CubeListBuilder.create().texOffs(64, 0).mirror(mirror)
            .addBox(mirror ? -7.0F : 0.0F, -1.0F, -1.5F, 7.0F, 3.0F, 3.0F)
            .texOffs(80, 0).addBox(mirror ? -8.0F : 6.0F, 1.0F, -1.25F, 2.0F, 8.0F, 2.5F),
        PartPose.offset(x, 16.0F, z));
  }

  @Override
  public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    root.getAllParts().forEach(ModelPart::resetPose);
    head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
    head.xRot = headPitch * Mth.DEG_TO_RAD;
    animateWalk(TerraCreatureAnimations.WALK, limbSwing, limbSwingAmount, 2.0F, 2.5F);
    animate(entity.idleAnimationState, TerraCreatureAnimations.IDLE, ageInTicks);
    animate(entity.attackAnimationState, TerraCreatureAnimations.ATTACK, ageInTicks);
    animate(entity.hurtAnimationState, TerraCreatureAnimations.HURT, ageInTicks);
  }

  @Override
  public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, int color) {
    root.render(poseStack, consumer, packedLight, packedOverlay, color);
  }

  @Override
  public ModelPart root() {
    return root;
  }
}

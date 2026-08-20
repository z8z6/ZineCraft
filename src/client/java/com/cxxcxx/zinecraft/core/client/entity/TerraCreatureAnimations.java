package com.cxxcxx.zinecraft.core.client.entity;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Animation definitions mirrored by the editable Blockbench timelines.
 */
public final class TerraCreatureAnimations {
  public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(2.0F)
      .looping()
      .addAnimation("body", rotation(
          frame(0.0F, 0.0F, 0.0F, 0.0F),
          frame(1.0F, 1.5F, 0.0F, 0.0F),
          frame(2.0F, 0.0F, 0.0F, 0.0F)))
      .addAnimation("head", rotation(
          frame(0.0F, 0.0F, -3.0F, 0.0F),
          frame(1.0F, 2.0F, 3.0F, 0.0F),
          frame(2.0F, 0.0F, -3.0F, 0.0F)))
      .build();

  public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(1.0F)
      .looping()
      .addAnimation("front_left", leg(28.0F))
      .addAnimation("back_right", leg(28.0F))
      .addAnimation("front_right", leg(-28.0F))
      .addAnimation("back_left", leg(-28.0F))
      .addAnimation("middle_left", leg(20.0F))
      .addAnimation("middle_right", leg(-20.0F))
      .addAnimation("body", position(
          positionFrame(0.0F, 0.0F, 0.0F, 0.0F),
          positionFrame(0.5F, 0.0F, -0.6F, 0.0F),
          positionFrame(1.0F, 0.0F, 0.0F, 0.0F)))
      .build();

  public static final AnimationDefinition ATTACK = AnimationDefinition.Builder.withLength(0.65F)
      .addAnimation("body", rotation(
          frame(0.0F, 0.0F, 0.0F, 0.0F),
          frame(0.25F, 8.0F, 0.0F, 0.0F),
          frame(0.65F, 0.0F, 0.0F, 0.0F)))
      .addAnimation("head", rotation(
          frame(0.0F, 0.0F, 0.0F, 0.0F),
          frame(0.25F, -25.0F, 0.0F, 0.0F),
          frame(0.65F, 0.0F, 0.0F, 0.0F)))
      .addAnimation("jaw", rotation(
          frame(0.0F, 0.0F, 0.0F, 0.0F),
          frame(0.25F, 24.0F, 0.0F, 0.0F),
          frame(0.65F, 0.0F, 0.0F, 0.0F)))
      .build();

  public static final AnimationDefinition HURT = AnimationDefinition.Builder.withLength(0.45F)
      .addAnimation("body", rotation(
          frame(0.0F, 0.0F, 0.0F, 0.0F),
          frame(0.15F, -8.0F, 0.0F, 7.0F),
          frame(0.45F, 0.0F, 0.0F, 0.0F)))
      .addAnimation("head", rotation(
          frame(0.0F, 0.0F, 0.0F, 0.0F),
          frame(0.15F, 12.0F, 0.0F, -7.0F),
          frame(0.45F, 0.0F, 0.0F, 0.0F)))
      .build();

  private TerraCreatureAnimations() {
  }

  private static AnimationChannel leg(float angle) {
    return rotation(frame(0.0F, angle, 0.0F, 0.0F), frame(0.5F, -angle, 0.0F, 0.0F), frame(1.0F, angle, 0.0F, 0.0F));
  }

  private static AnimationChannel rotation(Keyframe... frames) {
    return new AnimationChannel(AnimationChannel.Targets.ROTATION, frames);
  }

  private static AnimationChannel position(Keyframe... frames) {
    return new AnimationChannel(AnimationChannel.Targets.POSITION, frames);
  }

  private static Keyframe frame(float time, float x, float y, float z) {
    return new Keyframe(time, KeyframeAnimations.degreeVec(x, y, z), AnimationChannel.Interpolations.CATMULLROM);
  }

  private static Keyframe positionFrame(float time, float x, float y, float z) {
    return new Keyframe(time, KeyframeAnimations.posVec(x, y, z), AnimationChannel.Interpolations.CATMULLROM);
  }
}

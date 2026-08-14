package com.cxxcxx.zinecraft.core.client.weapon.tacz

import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczAmmoSpec
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunPacks
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunSpec
import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.weapon.ModTaczWeapons
import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import org.joml.Vector3f
import java.util.IdentityHashMap
import java.util.concurrent.ConcurrentHashMap

object TaczItemRenderer {
  private val models = ConcurrentHashMap<String, TaczBedrockModel>()
  private val animations = ConcurrentHashMap<String, Map<String, TaczAnimationClip>>()
  private val textures = ConcurrentHashMap<String, ResourceLocation>()
  private val runtimes = java.util.Collections.synchronizedMap(IdentityHashMap<ItemStack, TaczLuaAnimationRuntime>())

  fun initialize() {
    BuiltinItemRendererRegistry.INSTANCE.register(ModTaczWeapons.GUN_ITEM.item, ::renderGun)
    BuiltinItemRendererRegistry.INSTANCE.register(ModTaczWeapons.AMMO_ITEM.item, ::renderAmmo)
    if (FabricLoader.getInstance().isDevelopmentEnvironment) {
      ClientLifecycleEvents.CLIENT_STARTED.register { validateExternalAssets() }
    }
  }

  private fun validateExternalAssets() {
    val snapshot = TaczGunPacks.snapshot
    val modelPaths = buildSet {
      snapshot.guns.values.mapNotNullTo(this) { it.assets.modelPath }
      snapshot.ammunition.values.mapNotNullTo(this) { it.modelPath }
    }
    val animationPaths = snapshot.guns.values.mapNotNull { it.assets.animationPath }.toSet()
    val playerAnimationPaths = snapshot.guns.values.mapNotNull { it.assets.playerAnimationPath }.toSet()
    val invalidModels = modelPaths.count { model(it) == null }
    val invalidAnimations = animationPaths.count { path ->
      runCatching {
        animations.computeIfAbsent(path) {
          snapshot.open(path)?.use(TaczBedrockParser::animations) ?: emptyMap()
        }
      }.isFailure
    }
    val invalidPlayerAnimations = playerAnimationPaths.count { path ->
      runCatching { snapshot.open(path)?.use(TaczBedrockParser::animations) ?: error("missing") }.isFailure
    }
    val invalidStateMachines = snapshot.guns.values.count { gun ->
      runCatching {
        val clips = linkedMapOf<String, TaczAnimationClip>()
        gun.assets.defaultAnimationPath?.let { path ->
          snapshot.open(path)?.use(TaczBedrockParser::animations)?.let(clips::putAll)
        }
        gun.assets.animationPath?.let { path ->
          snapshot.open(path)?.use(TaczBedrockParser::animations)?.let(clips::putAll)
        }
        TaczLuaAnimationRuntime(ModTaczWeapons.gunStack(gun), gun, clips, Minecraft.getInstance().player).also {
          it.sample(Minecraft.getInstance().player)
          it.stop()
        }
      }.isFailure
    }
    Zinecraft.logger.info(
      "Validated {} TaCZ model(s), {} gun animation(s), {} player animation(s), and {} Lua state machine(s); failures: {}/{}/{}/{}",
      modelPaths.size,
      animationPaths.size,
      playerAnimationPaths.size,
      snapshot.guns.size,
      invalidModels,
      invalidAnimations,
      invalidPlayerAnimations,
      invalidStateMachines
    )
  }

  fun play(entity: net.minecraft.world.entity.LivingEntity, stack: ItemStack, animation: ResourceLocation) {
    if (stack.item !== ModTaczWeapons.GUN_ITEM.item) return
    val input = when (animation) {
      ModTaczWeapons.FIRE_ANIMATION_ID -> "shoot"
      ModTaczWeapons.RELOAD_ANIMATION_ID -> "reload"
      ModTaczWeapons.AIM_ANIMATION_ID -> if (stack.getOrDefault(WeaponStateComponents.AIMING, false)) "aim" else "idle"
      ModTaczWeapons.INSPECT_ANIMATION_ID -> "inspect"
      ModTaczWeapons.FIRE_SELECT_ANIMATION_ID -> "fire_select"
      ModTaczWeapons.MELEE_ANIMATION_ID -> "bayonet_push"
      ModTaczWeapons.BOLT_ANIMATION_ID -> "blot"
      else -> return
    }
    runtime(stack, entity)?.trigger(input, entity)
  }

  fun stop(stack: ItemStack, animation: ResourceLocation) {
    // TaCZ state machines own clip lifetime; presentation completion must not truncate transitions.
  }

  private fun renderGun(
    stack: ItemStack,
    context: ItemDisplayContext,
    poses: PoseStack,
    buffers: MultiBufferSource,
    light: Int,
    overlay: Int
  ) {
    val gun = stack.get(WeaponStateComponents.TACZ_GUN_ID)?.let(TaczGunPacks::gun) ?: return
    val slotTexture = gun.assets.slotTexturePath
    if (context == ItemDisplayContext.GUI && slotTexture != null) {
      renderIcon(slotTexture, poses, buffers, light, overlay)
      return
    }
    val modelPath = gun.assets.modelPath
    val texturePath = gun.assets.texturePath
    if (modelPath == null || texturePath == null) {
      gun.assets.slotTexturePath?.let { renderIcon(it, poses, buffers, light, overlay) }
      return
    }
    val model = model(modelPath) ?: return
    val texture = texture(texturePath) ?: return
    val transforms = animationTransforms(stack, gun)
    poses.pushPose()
    applyContextTransform(context, poses)
    renderModel(model, transforms, texture, poses, buffers, light, overlay)
    poses.popPose()
  }

  private fun renderAmmo(
    stack: ItemStack,
    context: ItemDisplayContext,
    poses: PoseStack,
    buffers: MultiBufferSource,
    light: Int,
    overlay: Int
  ) {
    val ammo = stack.get(WeaponStateComponents.TACZ_AMMO_ID)?.let(TaczGunPacks::ammo) ?: return
    val slotTexture = ammo.slotTexturePath
    if (context == ItemDisplayContext.GUI && slotTexture != null) {
      renderIcon(slotTexture, poses, buffers, light, overlay)
      return
    }
    renderModelAsset(ammo, context, poses, buffers, light, overlay)
  }

  private fun renderModelAsset(
    ammo: TaczAmmoSpec,
    context: ItemDisplayContext,
    poses: PoseStack,
    buffers: MultiBufferSource,
    light: Int,
    overlay: Int
  ) {
    val modelPath =
      ammo.modelPath ?: return ammo.slotTexturePath?.let { renderIcon(it, poses, buffers, light, overlay) } ?: Unit
    val texturePath = ammo.texturePath ?: return
    val model = model(modelPath) ?: return
    val texture = texture(texturePath) ?: return
    poses.pushPose()
    applyContextTransform(context, poses)
    poses.scale(1.6f, 1.6f, 1.6f)
    renderModel(model, emptyMap(), texture, poses, buffers, light, overlay)
    poses.popPose()
  }

  private fun applyContextTransform(context: ItemDisplayContext, poses: PoseStack) {
    poses.translate(0.5, 0.35, 0.5)
    when (context) {
      ItemDisplayContext.FIRST_PERSON_LEFT_HAND, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND -> {
        poses.mulPose(Axis.YP.rotationDegrees(90f))
        poses.mulPose(Axis.ZP.rotationDegrees(-8f))
        poses.scale(0.72f, 0.72f, 0.72f)
      }

      ItemDisplayContext.THIRD_PERSON_LEFT_HAND, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND -> {
        poses.mulPose(Axis.YP.rotationDegrees(90f))
        poses.mulPose(Axis.ZP.rotationDegrees(-35f))
        poses.scale(0.55f, 0.55f, 0.55f)
      }

      ItemDisplayContext.GROUND -> poses.scale(0.45f, 0.45f, 0.45f)
      ItemDisplayContext.FIXED -> {
        poses.mulPose(Axis.YP.rotationDegrees(90f))
        poses.scale(0.7f, 0.7f, 0.7f)
      }

      else -> poses.scale(0.65f, 0.65f, 0.65f)
    }
  }

  private fun renderIcon(
    path: String,
    poses: PoseStack,
    buffers: MultiBufferSource,
    light: Int,
    overlay: Int
  ) {
    val texture = texture(path) ?: return
    val consumer = buffers.getBuffer(RenderType.entityCutoutNoCull(texture))
    val pose = poses.last()
    vertex(consumer, pose, 0f, 0f, 0f, 0f, 1f, light, overlay, 0f, 0f, 1f)
    vertex(consumer, pose, 1f, 0f, 0f, 1f, 1f, light, overlay, 0f, 0f, 1f)
    vertex(consumer, pose, 1f, 1f, 0f, 1f, 0f, light, overlay, 0f, 0f, 1f)
    vertex(consumer, pose, 0f, 1f, 0f, 0f, 0f, light, overlay, 0f, 0f, 1f)
  }

  private fun renderModel(
    model: TaczBedrockModel,
    transforms: Map<String, TaczBoneTransform>,
    texture: ResourceLocation,
    poses: PoseStack,
    buffers: MultiBufferSource,
    light: Int,
    overlay: Int
  ) {
    val children = model.bones.groupBy { it.parent }
    val consumer = buffers.getBuffer(RenderType.entityCutoutNoCull(texture))
    children[null].orEmpty()
      .forEach { renderBone(it, TaczVector.ZERO, children, model, transforms, poses, consumer, light, overlay) }
  }

  private fun renderBone(
    bone: TaczBone,
    parentPivot: TaczVector,
    children: Map<String?, List<TaczBone>>,
    model: TaczBedrockModel,
    transforms: Map<String, TaczBoneTransform>,
    poses: PoseStack,
    consumer: VertexConsumer,
    light: Int,
    overlay: Int
  ) {
    poses.pushPose()
    val relative = bone.pivot - parentPivot
    poses.translate(relative.x / 16.0, relative.y / 16.0, relative.z / 16.0)
    val animated = transforms[bone.name] ?: TaczBoneTransform()
    poses.translate(animated.position.x / 16.0, animated.position.y / 16.0, animated.position.z / 16.0)
    rotate(poses, bone.rotation + animated.rotation)
    poses.scale(animated.scale.x, animated.scale.y, animated.scale.z)
    bone.cubes.forEach { renderCube(it, bone.pivot, model, poses, consumer, light, overlay) }
    children[bone.name].orEmpty().forEach { child ->
      renderBone(child, bone.pivot, children, model, transforms, poses, consumer, light, overlay)
    }
    poses.popPose()
  }

  private fun renderCube(
    cube: TaczCube,
    bonePivot: TaczVector,
    model: TaczBedrockModel,
    poses: PoseStack,
    consumer: VertexConsumer,
    light: Int,
    overlay: Int
  ) {
    poses.pushPose()
    val relativePivot = cube.pivot - bonePivot
    poses.translate(relativePivot.x / 16.0, relativePivot.y / 16.0, relativePivot.z / 16.0)
    rotate(poses, cube.rotation)
    val min = cube.origin - cube.pivot - TaczVector(cube.inflate, cube.inflate, cube.inflate)
    val max = cube.origin - cube.pivot + cube.size + TaczVector(cube.inflate, cube.inflate, cube.inflate)
    cube.faces.forEach { (face, uv) -> renderFace(face, uv, min, max, model, poses.last(), consumer, light, overlay) }
    poses.popPose()
  }

  private fun renderFace(
    face: String,
    uv: TaczFaceUv,
    min: TaczVector,
    max: TaczVector,
    model: TaczBedrockModel,
    pose: PoseStack.Pose,
    consumer: VertexConsumer,
    light: Int,
    overlay: Int
  ) {
    val x0 = min.x / 16f;
    val y0 = min.y / 16f;
    val z0 = min.z / 16f
    val x1 = max.x / 16f;
    val y1 = max.y / 16f;
    val z1 = max.z / 16f
    val u0 = uv.u / model.textureWidth;
    val v0 = uv.v / model.textureHeight
    val u1 = (uv.u + uv.width) / model.textureWidth;
    val v1 = (uv.v + uv.height) / model.textureHeight
    val data = when (face) {
      "north" -> FaceData(arrayOf(v(x1, y0, z0), v(x0, y0, z0), v(x0, y1, z0), v(x1, y1, z0)), v(0f, 0f, -1f))
      "south" -> FaceData(arrayOf(v(x0, y0, z1), v(x1, y0, z1), v(x1, y1, z1), v(x0, y1, z1)), v(0f, 0f, 1f))
      "west" -> FaceData(arrayOf(v(x0, y0, z0), v(x0, y0, z1), v(x0, y1, z1), v(x0, y1, z0)), v(-1f, 0f, 0f))
      "east" -> FaceData(arrayOf(v(x1, y0, z1), v(x1, y0, z0), v(x1, y1, z0), v(x1, y1, z1)), v(1f, 0f, 0f))
      "up" -> FaceData(arrayOf(v(x0, y1, z1), v(x1, y1, z1), v(x1, y1, z0), v(x0, y1, z0)), v(0f, 1f, 0f))
      "down" -> FaceData(arrayOf(v(x0, y0, z0), v(x1, y0, z0), v(x1, y0, z1), v(x0, y0, z1)), v(0f, -1f, 0f))
      else -> return
    }
    val coordinates = arrayOf(u0 to v1, u1 to v1, u1 to v0, u0 to v0)
    data.vertices.forEachIndexed { index, point ->
      val textureUv = coordinates[index]
      vertex(
        consumer,
        pose,
        point.x,
        point.y,
        point.z,
        textureUv.first,
        textureUv.second,
        light,
        overlay,
        data.normal.x,
        data.normal.y,
        data.normal.z
      )
    }
  }

  private data class FaceData(val vertices: Array<Vector3f>, val normal: Vector3f)

  private fun v(x: Float, y: Float, z: Float) = Vector3f(x, y, z)

  private fun vertex(
    consumer: VertexConsumer,
    pose: PoseStack.Pose,
    x: Float, y: Float, z: Float,
    u: Float, v: Float,
    light: Int, overlay: Int,
    nx: Float, ny: Float, nz: Float
  ) {
    consumer.addVertex(pose, x, y, z).setColor(255, 255, 255, 255).setUv(u, v)
      .setOverlay(overlay).setLight(light).setNormal(pose, nx, ny, nz)
  }

  private fun rotate(poses: PoseStack, rotation: TaczVector) {
    if (rotation.z != 0f) poses.mulPose(Axis.ZP.rotationDegrees(rotation.z))
    if (rotation.y != 0f) poses.mulPose(Axis.YP.rotationDegrees(rotation.y))
    if (rotation.x != 0f) poses.mulPose(Axis.XP.rotationDegrees(rotation.x))
  }

  private fun animationTransforms(stack: ItemStack, gun: TaczGunSpec): Map<String, TaczBoneTransform> {
    val entity = Minecraft.getInstance().player?.takeIf { it.mainHandItem === stack }
    return runtime(stack, entity)?.sample(entity) ?: emptyMap()
  }

  private fun runtime(stack: ItemStack, entity: net.minecraft.world.entity.LivingEntity?): TaczLuaAnimationRuntime? {
    runtimes[stack]?.let { runtime -> entity?.let(runtime::bind); return runtime }
    val gun = stack.get(WeaponStateComponents.TACZ_GUN_ID)?.let(TaczGunPacks::gun) ?: return null
    val clips = linkedMapOf<String, TaczAnimationClip>()
    gun.assets.defaultAnimationPath?.let { path ->
      clips += animations.computeIfAbsent(path) {
        TaczGunPacks.snapshot.open(path)?.use(TaczBedrockParser::animations) ?: emptyMap()
      }
    }
    gun.assets.animationPath?.let { path ->
      clips += animations.computeIfAbsent(path) {
        TaczGunPacks.snapshot.open(path)?.use(TaczBedrockParser::animations) ?: emptyMap()
      }
    }
    if (clips.isEmpty()) return null
    return TaczLuaAnimationRuntime(stack, gun, clips, entity).also { runtimes[stack] = it }
  }

  private fun model(path: String): TaczBedrockModel? = models[path] ?: runCatching {
    TaczGunPacks.snapshot.open(path)?.use(TaczBedrockParser::model) ?: return null
  }.onFailure { Zinecraft.logger.warn("Failed to load TaCZ Bedrock model {}", path, it) }
    .getOrNull()?.also { models[path] = it }

  private fun texture(path: String): ResourceLocation? = textures[path] ?: runCatching {
    val image = TaczGunPacks.snapshot.open(path)?.use(NativeImage::read) ?: return null
    val id =
      ResourceLocation.fromNamespaceAndPath(Zinecraft.MOD_ID, "dynamic_tacz/${path.hashCode().toUInt().toString(16)}")
    Minecraft.getInstance().textureManager.register(id, DynamicTexture(image))
    id
  }.onFailure { Zinecraft.logger.warn("Failed to load TaCZ texture {}", path, it) }
    .getOrNull()?.also { textures[path] = it }
}

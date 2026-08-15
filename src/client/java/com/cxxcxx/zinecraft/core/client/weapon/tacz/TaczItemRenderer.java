package com.cxxcxx.zinecraft.core.client.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczAmmoSpec;
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunPacks;
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunSpec;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.weapon.ModTaczWeapons;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TaczItemRenderer extends BlockEntityWithoutLevelRenderer {
  private static final Map<String, TaczBedrockModel> MODELS = new ConcurrentHashMap<>();
  private static final Map<String, ResourceLocation> TEXTURES = new ConcurrentHashMap<>();

  private TaczItemRenderer() {
    super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
  }

  private static TaczItemRenderer instance() {
    return Holder.INSTANCE;
  }

  private static void applyContextTransform(ItemDisplayContext context, PoseStack poses) {
    poses.translate(0.5, 0.35, 0.5);
    switch (context) {
      case FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND -> {
        poses.mulPose(Axis.YP.rotationDegrees(90));
        poses.mulPose(Axis.ZP.rotationDegrees(-8));
        poses.scale(.72f, .72f, .72f);
      }
      case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
        poses.mulPose(Axis.YP.rotationDegrees(90));
        poses.mulPose(Axis.ZP.rotationDegrees(-35));
        poses.scale(.55f, .55f, .55f);
      }
      case GROUND -> poses.scale(.45f, .45f, .45f);
      case FIXED -> {
        poses.mulPose(Axis.YP.rotationDegrees(90));
        poses.scale(.7f, .7f, .7f);
      }
      default -> poses.scale(.65f, .65f, .65f);
    }
  }

  private static void renderIcon(String path, PoseStack poses, MultiBufferSource buffers, int light, int overlay) {
    ResourceLocation texture = texture(path);
    if (texture == null) return;
    VertexConsumer consumer = buffers.getBuffer(RenderType.entityCutoutNoCull(texture));
    PoseStack.Pose pose = poses.last();
    vertex(consumer, pose, 0, 0, 0, 0, 1, light, overlay, 0, 0, 1);
    vertex(consumer, pose, 1, 0, 0, 1, 1, light, overlay, 0, 0, 1);
    vertex(consumer, pose, 1, 1, 0, 1, 0, light, overlay, 0, 0, 1);
    vertex(consumer, pose, 0, 1, 0, 0, 0, light, overlay, 0, 0, 1);
  }

  private static void renderModel(TaczBedrockModel model, Map<String, TaczBoneTransform> transforms,
                                  ResourceLocation texture, PoseStack poses, MultiBufferSource buffers, int light, int overlay) {
    Map<String, List<TaczBone>> children = new HashMap<>();
    for (TaczBone bone : model.bones()) children.computeIfAbsent(bone.parent(), ignored -> new ArrayList<>()).add(bone);
    VertexConsumer consumer = buffers.getBuffer(RenderType.entityCutoutNoCull(texture));
    for (TaczBone root : children.getOrDefault(null, List.of()))
      renderBone(root, TaczVector.ZERO, children, model, transforms, poses, consumer, light, overlay);
  }

  private static void renderBone(TaczBone bone, TaczVector parentPivot, Map<String, List<TaczBone>> children,
                                 TaczBedrockModel model, Map<String, TaczBoneTransform> transforms, PoseStack poses,
                                 VertexConsumer consumer, int light, int overlay) {
    poses.pushPose();
    TaczVector relative = bone.pivot().subtract(parentPivot);
    poses.translate(relative.x() / 16.0, relative.y() / 16.0, relative.z() / 16.0);
    TaczBoneTransform animated = transforms.getOrDefault(bone.name(), TaczBoneTransform.IDENTITY);
    poses.translate(animated.position().x() / 16.0, animated.position().y() / 16.0, animated.position().z() / 16.0);
    rotate(poses, bone.rotation().add(animated.rotation()));
    poses.scale(animated.scale().x(), animated.scale().y(), animated.scale().z());
    for (TaczCube cube : bone.cubes()) renderCube(cube, bone.pivot(), model, poses, consumer, light, overlay);
    for (TaczBone child : children.getOrDefault(bone.name(), List.of()))
      renderBone(child, bone.pivot(), children, model, transforms, poses, consumer, light, overlay);
    poses.popPose();
  }

  private static void renderCube(TaczCube cube, TaczVector bonePivot, TaczBedrockModel model, PoseStack poses,
                                 VertexConsumer consumer, int light, int overlay) {
    poses.pushPose();
    TaczVector relative = cube.pivot().subtract(bonePivot);
    poses.translate(relative.x() / 16.0, relative.y() / 16.0, relative.z() / 16.0);
    rotate(poses, cube.rotation());
    TaczVector inflation = new TaczVector(cube.inflate(), cube.inflate(), cube.inflate());
    TaczVector min = cube.origin().subtract(cube.pivot()).subtract(inflation);
    TaczVector max = cube.origin().subtract(cube.pivot()).add(cube.size()).add(inflation);
    cube.faces().forEach((face, uv) -> renderFace(face, uv, min, max, model, poses.last(), consumer, light, overlay));
    poses.popPose();
  }

  private static void renderFace(String face, TaczFaceUv uv, TaczVector min, TaczVector max, TaczBedrockModel model,
                                 PoseStack.Pose pose, VertexConsumer consumer, int light, int overlay) {
    float x0 = min.x() / 16, y0 = min.y() / 16, z0 = min.z() / 16, x1 = max.x() / 16, y1 = max.y() / 16, z1 = max.z() / 16;
    FaceData data = switch (face) {
      case "north" ->
          new FaceData(new Vector3f[]{v(x1, y0, z0), v(x0, y0, z0), v(x0, y1, z0), v(x1, y1, z0)}, v(0, 0, -1));
      case "south" ->
          new FaceData(new Vector3f[]{v(x0, y0, z1), v(x1, y0, z1), v(x1, y1, z1), v(x0, y1, z1)}, v(0, 0, 1));
      case "west" ->
          new FaceData(new Vector3f[]{v(x0, y0, z0), v(x0, y0, z1), v(x0, y1, z1), v(x0, y1, z0)}, v(-1, 0, 0));
      case "east" ->
          new FaceData(new Vector3f[]{v(x1, y0, z1), v(x1, y0, z0), v(x1, y1, z0), v(x1, y1, z1)}, v(1, 0, 0));
      case "up" -> new FaceData(new Vector3f[]{v(x0, y1, z1), v(x1, y1, z1), v(x1, y1, z0), v(x0, y1, z0)}, v(0, 1, 0));
      case "down" ->
          new FaceData(new Vector3f[]{v(x0, y0, z0), v(x1, y0, z0), v(x1, y0, z1), v(x0, y0, z1)}, v(0, -1, 0));
      default -> null;
    };
    if (data == null) return;
    float u0 = uv.u() / model.textureWidth(), v0 = uv.v() / model.textureHeight();
    float u1 = (uv.u() + uv.width()) / model.textureWidth(), v1 = (uv.v() + uv.height()) / model.textureHeight();
    float[][] coordinates = {{u0, v1}, {u1, v1}, {u1, v0}, {u0, v0}};
    for (int i = 0; i < 4; i++) {
      Vector3f point = data.vertices()[i], normal = data.normal();
      vertex(consumer, pose, point.x, point.y, point.z, coordinates[i][0], coordinates[i][1], light, overlay, normal.x, normal.y, normal.z);
    }
  }

  private static Vector3f v(float x, float y, float z) {
    return new Vector3f(x, y, z);
  }

  private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, float z, float u, float v,
                             int light, int overlay, float nx, float ny, float nz) {
    consumer.addVertex(pose, x, y, z).setColor(255, 255, 255, 255).setUv(u, v).setOverlay(overlay).setLight(light).setNormal(pose, nx, ny, nz);
  }

  private static void rotate(PoseStack poses, TaczVector rotation) {
    if (rotation.z() != 0) poses.mulPose(Axis.ZP.rotationDegrees(rotation.z()));
    if (rotation.y() != 0) poses.mulPose(Axis.YP.rotationDegrees(rotation.y()));
    if (rotation.x() != 0) poses.mulPose(Axis.XP.rotationDegrees(rotation.x()));
  }

  private static void clearCaches() {
    MODELS.clear();
    var manager = Minecraft.getInstance().getTextureManager();
    TEXTURES.values().forEach(manager::release);
    TEXTURES.clear();
  }

  private static TaczBedrockModel model(String path) {
    TaczBedrockModel cached = MODELS.get(path);
    if (cached != null) return cached;
    try (var input = TaczGunPacks.INSTANCE.getSnapshot().open(path)) {
      if (input == null) return null;
      TaczBedrockModel model = TaczBedrockParser.model(input);
      MODELS.put(path, model);
      return model;
    } catch (Exception exception) {
      Zinecraft.INSTANCE.getLogger().warn("Failed to load TaCZ Bedrock model {}", path, exception);
      return null;
    }
  }

  private static ResourceLocation texture(String path) {
    ResourceLocation cached = TEXTURES.get(path);
    if (cached != null) return cached;
    try (var input = TaczGunPacks.INSTANCE.getSnapshot().open(path)) {
      if (input == null) return null;
      NativeImage image = NativeImage.read(input);
      ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Zinecraft.MOD_ID, "dynamic_tacz/" + Integer.toUnsignedString(path.hashCode(), 16));
      Minecraft.getInstance().getTextureManager().register(id, new DynamicTexture(image));
      TEXTURES.put(path, id);
      return id;
    } catch (Exception exception) {
      Zinecraft.INSTANCE.getLogger().warn("Failed to load TaCZ texture {}", path, exception);
      return null;
    }
  }

  @Override
  public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poses, MultiBufferSource buffers,
                           int light, int overlay) {
    if (stack.getItem() == ModTaczWeapons.INSTANCE.getGUN_ITEM().getItem())
      renderGun(stack, context, poses, buffers, light, overlay);
    else if (stack.getItem() == ModTaczWeapons.INSTANCE.getAMMO_ITEM().getItem())
      renderAmmo(stack, context, poses, buffers, light, overlay);
  }

  private void renderGun(ItemStack stack, ItemDisplayContext context, PoseStack poses, MultiBufferSource buffers, int light, int overlay) {
    ResourceLocation id = stack.get(WeaponStateComponents.INSTANCE.getTACZ_GUN_ID());
    TaczGunSpec gun = id == null ? null : TaczGunPacks.INSTANCE.gun(id);
    if (gun == null) return;
    String slotTexture = gun.getAssets().getSlotTexturePath();
    if (context == ItemDisplayContext.GUI && slotTexture != null) {
      renderIcon(slotTexture, poses, buffers, light, overlay);
      return;
    }
    String modelPath = gun.getAssets().getModelPath();
    String texturePath = gun.getAssets().getTexturePath();
    if (modelPath == null || texturePath == null) {
      if (slotTexture != null) renderIcon(slotTexture, poses, buffers, light, overlay);
      return;
    }
    TaczBedrockModel model = model(modelPath);
    ResourceLocation texture = texture(texturePath);
    if (model == null || texture == null) return;
    poses.pushPose();
    applyContextTransform(context, poses);
    renderModel(model, Map.of(), texture, poses, buffers, light, overlay);
    poses.popPose();
  }

  private void renderAmmo(ItemStack stack, ItemDisplayContext context, PoseStack poses, MultiBufferSource buffers, int light, int overlay) {
    ResourceLocation id = stack.get(WeaponStateComponents.INSTANCE.getTACZ_AMMO_ID());
    TaczAmmoSpec ammo = id == null ? null : TaczGunPacks.INSTANCE.ammo(id);
    if (ammo == null) return;
    if (context == ItemDisplayContext.GUI && ammo.getSlotTexturePath() != null) {
      renderIcon(ammo.getSlotTexturePath(), poses, buffers, light, overlay);
      return;
    }
    String modelPath = ammo.getModelPath();
    String texturePath = ammo.getTexturePath();
    if (modelPath == null) {
      if (ammo.getSlotTexturePath() != null) renderIcon(ammo.getSlotTexturePath(), poses, buffers, light, overlay);
      return;
    }
    if (texturePath == null) return;
    TaczBedrockModel model = model(modelPath);
    ResourceLocation texture = texture(texturePath);
    if (model == null || texture == null) return;
    poses.pushPose();
    applyContextTransform(context, poses);
    poses.scale(1.6f, 1.6f, 1.6f);
    renderModel(model, Map.of(), texture, poses, buffers, light, overlay);
    poses.popPose();
  }

  @EventBusSubscriber(modid = Zinecraft.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
  public static final class ModEvents {
    private ModEvents() {
    }

    @SubscribeEvent
    public static void registerExtensions(RegisterClientExtensionsEvent event) {
      IClientItemExtensions extensions = new IClientItemExtensions() {
        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
          return instance();
        }
      };
      event.registerItem(extensions, ModTaczWeapons.INSTANCE.getGUN_ITEM().getItem(), ModTaczWeapons.INSTANCE.getAMMO_ITEM().getItem());
    }

    @SubscribeEvent
    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
      event.registerReloadListener((ResourceManagerReloadListener) resourceManager -> clearCaches());
    }
  }

  private record FaceData(Vector3f[] vertices, Vector3f normal) {
  }

  private static final class Holder {
    private static final TaczItemRenderer INSTANCE = new TaczItemRenderer();
  }

}

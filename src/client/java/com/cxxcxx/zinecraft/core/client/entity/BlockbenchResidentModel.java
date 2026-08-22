package com.cxxcxx.zinecraft.core.client.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;

/** Loads native humanoid geometry directly from the residents' editable Blockbench projects. */
public final class BlockbenchResidentModel<T extends PathfinderMob> extends HumanoidModel<T> {
  private static final int TEXTURE_SIZE = 256;
  private final ModelPart rightWing;
  private final ModelPart leftWing;
  private final ModelPart tail;
  private final ModelPart tail1;
  private final ModelPart tail2;
  private final ModelPart tail3;

  public BlockbenchResidentModel(ModelPart root) {
    super(root);
    ModelPart wings = body.hasChild("wings") ? body.getChild("wings") : null;
    rightWing = wings != null && wings.hasChild("right_wing") ? wings.getChild("right_wing") : null;
    leftWing = wings != null && wings.hasChild("left_wing") ? wings.getChild("left_wing") : null;
    tail = body.hasChild("tail") ? body.getChild("tail") : null;
    tail1 = child(tail, "tail1");
    tail2 = child(tail1, "tail2");
    tail3 = child(tail2, "tail3");
  }

  public static LayerDefinition layer(String name) {
    JsonObject model = readModel(name);
    Map<String, JsonObject> groups = byUuid(model.getAsJsonArray("groups"));
    Map<String, JsonObject> elements = byUuid(model.getAsJsonArray("elements"));
    Map<String, Uv> textureOffsets = allocateTextureOffsets(model.getAsJsonArray("elements"));
    MeshDefinition mesh = new MeshDefinition();
    PartDefinition root = mesh.getRoot();
    for (JsonElement outlinerElement : model.getAsJsonArray("outliner")) {
      JsonObject node = outlinerElement.getAsJsonObject();
      JsonObject group = groups.get(node.get("uuid").getAsString());
      if (group != null && "Root".equals(group.get("name").getAsString())) {
        addNodeChildren(root, root, node, groups, elements, textureOffsets, vector(group, "origin"), true);
      } else {
        addGroup(root, root, node, groups, elements, textureOffsets,
            new float[] {0.0F, 0.0F, 0.0F}, true);
      }
    }
    root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
    return LayerDefinition.create(mesh, TEXTURE_SIZE, TEXTURE_SIZE);
  }

  private static void addNodeChildren(
      PartDefinition modelRoot,
      PartDefinition parent,
      JsonObject node,
      Map<String, JsonObject> groups,
      Map<String, JsonObject> elements,
      Map<String, Uv> textureOffsets,
      float[] parentPivot,
      boolean topLevel
  ) {
    for (JsonElement child : node.getAsJsonArray("children")) {
      if (child.isJsonObject()) {
        addGroup(modelRoot, parent, child.getAsJsonObject(), groups, elements, textureOffsets,
            parentPivot, topLevel);
      }
    }
  }

  private static void addGroup(
      PartDefinition modelRoot,
      PartDefinition parent,
      JsonObject node,
      Map<String, JsonObject> groups,
      Map<String, JsonObject> elements,
      Map<String, Uv> textureOffsets,
      float[] parentPivot,
      boolean topLevel
  ) {
    JsonObject group = groups.get(node.get("uuid").getAsString());
    if (group == null) throw new IllegalStateException("Blockbench outliner references an unknown group");
    float[] pivot = vector(group, "origin");
    float[] rotation = vectorOrZero(group, "rotation");
    CubeListBuilder cubes = CubeListBuilder.create();
    JsonArray children = node.getAsJsonArray("children");
    for (JsonElement child : children) {
      if (!child.isJsonPrimitive()) continue;
      JsonObject cube = elements.get(child.getAsString());
      if (cube != null && !isRotated(cube)) addCube(cubes, cube, pivot, textureOffsets.get(child.getAsString()));
    }

    float offsetX = pivot[0] - parentPivot[0];
    float offsetY = topLevel ? 24.0F - pivot[1] : -(pivot[1] - parentPivot[1]);
    float offsetZ = pivot[2] - parentPivot[2];
    PartPose pose = PartPose.offsetAndRotation(
        offsetX, offsetY, offsetZ,
        -rotation[0] * Mth.DEG_TO_RAD,
        rotation[1] * Mth.DEG_TO_RAD,
        -rotation[2] * Mth.DEG_TO_RAD);
    PartDefinition part = parent.addOrReplaceChild(partName(group.get("name").getAsString()), cubes, pose);

    for (JsonElement child : children) {
      if (child.isJsonObject()) {
        JsonObject childNode = child.getAsJsonObject();
        JsonObject childGroup = groups.get(childNode.get("uuid").getAsString());
        boolean flatten = childGroup != null
            && isHumanoidRootPart(childGroup.get("name").getAsString());
        addGroup(
            modelRoot,
            flatten ? modelRoot : part,
            childNode,
            groups,
            elements,
            textureOffsets,
            flatten ? new float[] {0.0F, 0.0F, 0.0F} : pivot,
            flatten);
      } else if (child.isJsonPrimitive()) {
        JsonObject cube = elements.get(child.getAsString());
        if (cube != null && isRotated(cube)) {
          addRotatedCube(part, cube, pivot, textureOffsets.get(child.getAsString()));
        }
      }
    }
  }

  private static void addCube(CubeListBuilder builder, JsonObject cube, float[] pivot, Uv uv) {
    float[] from = vector(cube, "from");
    float[] to = vector(cube, "to");
    builder.texOffs(uv.u(), uv.v()).addBox(
        from[0] - pivot[0],
        -(to[1] - pivot[1]),
        from[2] - pivot[2],
        to[0] - from[0],
        to[1] - from[1],
        to[2] - from[2],
        new CubeDeformation(numberOrZero(cube, "inflate")));
  }

  private static void addRotatedCube(PartDefinition parent, JsonObject cube, float[] bonePivot, Uv uv) {
    float[] from = vector(cube, "from");
    float[] to = vector(cube, "to");
    float[] pivot = vector(cube, "origin");
    float[] rotation = vectorOrZero(cube, "rotation");
    CubeListBuilder builder = CubeListBuilder.create().texOffs(uv.u(), uv.v()).addBox(
        from[0] - pivot[0],
        -(to[1] - pivot[1]),
        from[2] - pivot[2],
        to[0] - from[0],
        to[1] - from[1],
        to[2] - from[2],
        new CubeDeformation(numberOrZero(cube, "inflate")));
    parent.addOrReplaceChild(
        "cube_" + cube.get("uuid").getAsString().substring(0, 8),
        builder,
        PartPose.offsetAndRotation(
            pivot[0] - bonePivot[0],
            -(pivot[1] - bonePivot[1]),
            pivot[2] - bonePivot[2],
            -rotation[0] * Mth.DEG_TO_RAD,
            rotation[1] * Mth.DEG_TO_RAD,
            -rotation[2] * Mth.DEG_TO_RAD));
  }

  private static Map<String, Uv> allocateTextureOffsets(JsonArray cubes) {
    Map<String, Uv> offsets = new HashMap<>();
    int cursorX = 0;
    int cursorY = 0;
    int rowHeight = 0;
    for (JsonElement element : cubes) {
      JsonObject cube = element.getAsJsonObject();
      float[] from = vector(cube, "from");
      float[] to = vector(cube, "to");
      float sizeX = to[0] - from[0];
      float sizeY = to[1] - from[1];
      float sizeZ = to[2] - from[2];
      int regionWidth = Mth.ceil(2.0F * (sizeX + sizeZ)) + 2;
      int regionHeight = Mth.ceil(sizeY + sizeZ) + 2;
      if (cursorX + regionWidth > TEXTURE_SIZE) {
        cursorX = 0;
        cursorY += rowHeight;
        rowHeight = 0;
      }
      if (cursorY + regionHeight > TEXTURE_SIZE) {
        throw new IllegalStateException("Resident texture atlas exceeds " + TEXTURE_SIZE + " pixels");
      }
      offsets.put(cube.get("uuid").getAsString(), new Uv(cursorX + 1, cursorY + 1));
      cursorX += regionWidth;
      rowHeight = Math.max(rowHeight, regionHeight);
    }
    return offsets;
  }

  private static Map<String, JsonObject> byUuid(JsonArray values) {
    Map<String, JsonObject> result = new HashMap<>();
    for (JsonElement value : values) {
      JsonObject object = value.getAsJsonObject();
      result.put(object.get("uuid").getAsString(), object);
    }
    return result;
  }

  private static JsonObject readModel(String name) {
    String path = "/assets/zinecraft/blockbench/entity/" + name + ".bbmodel";
    try (InputStream stream = BlockbenchResidentModel.class.getResourceAsStream(path)) {
      if (stream == null) throw new IllegalStateException("Missing Blockbench resident model: " + path);
      try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
        return JsonParser.parseReader(reader).getAsJsonObject();
      }
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to read Blockbench resident model: " + path, exception);
    }
  }

  private static String partName(String name) {
    return switch (name) {
      case "Head" -> "head";
      case "Body" -> "body";
      case "RightArm" -> "right_arm";
      case "LeftArm" -> "left_arm";
      case "RightLeg" -> "right_leg";
      case "LeftLeg" -> "left_leg";
      default -> name.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase(Locale.ROOT);
    };
  }

  private static boolean isHumanoidRootPart(String name) {
    return switch (name) {
      case "Head", "Body", "RightArm", "LeftArm", "RightLeg", "LeftLeg" -> true;
      default -> false;
    };
  }

  private static float[] vector(JsonObject object, String key) {
    JsonArray values = object.getAsJsonArray(key);
    return new float[] {values.get(0).getAsFloat(), values.get(1).getAsFloat(), values.get(2).getAsFloat()};
  }

  private static float[] vectorOrZero(JsonObject object, String key) {
    return object.has(key) ? vector(object, key) : new float[] {0.0F, 0.0F, 0.0F};
  }

  private static float numberOrZero(JsonObject object, String key) {
    return object.has(key) ? object.get(key).getAsFloat() : 0.0F;
  }

  private static boolean isRotated(JsonObject cube) {
    float[] rotation = vectorOrZero(cube, "rotation");
    return rotation[0] != 0.0F || rotation[1] != 0.0F || rotation[2] != 0.0F;
  }

  private static ModelPart child(ModelPart parent, String name) {
    return parent != null && parent.hasChild(name) ? parent.getChild(name) : null;
  }

  @Override
  public void setupAnim(
      T entity,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
  ) {
    resetPose(rightWing);
    resetPose(leftWing);
    resetPose(tail);
    resetPose(tail1);
    resetPose(tail2);
    resetPose(tail3);
    super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    float idle = Mth.sin(ageInTicks * 0.08F);
    if (rightWing != null) rightWing.zRot += idle * 0.04F;
    if (leftWing != null) leftWing.zRot -= idle * 0.04F;
    if (tail != null) tail.yRot += Mth.sin(ageInTicks * 0.10F) * 0.12F;
    if (tail1 != null) tail1.yRot += Mth.sin(ageInTicks * 0.10F - 0.35F) * 0.10F;
    if (tail2 != null) tail2.yRot += Mth.sin(ageInTicks * 0.10F - 0.70F) * 0.08F;
    if (tail3 != null) tail3.yRot += Mth.sin(ageInTicks * 0.10F - 1.05F) * 0.06F;
  }

  private static void resetPose(ModelPart part) {
    if (part != null) part.resetPose();
  }

  private record Uv(int u, int v) {
  }
}

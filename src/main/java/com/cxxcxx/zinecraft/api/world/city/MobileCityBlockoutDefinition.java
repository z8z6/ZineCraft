package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.nation.TerraPlace;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * 一座移动城市的临时方块占位契约。
 *
 * <p>该契约只描述可替换的 Blockout 尺寸，不把占位体块声明为官方建筑还原。</p>
 */
public record MobileCityBlockoutDefinition(
    ResourceLocation structureId,
    TerraPlace place,
    ResourceLocation templateRoot,
    int plateWidth,
    int plateDepth,
    int layerCount,
    int layerHeight,
    MobileCityProgram program,
    ResourceLocation placeholderBlock
) {
  public static final int REQUIRED_LAYER_COUNT = 4;
  public static final int MIN_PLATE_AREA = 900;
  public static final int MAX_PLATE_AREA = 1_200;
  public static final int MIN_LANDMARK_AREA = 200;
  public static final int MAX_LANDMARK_AREA = 300;

  public MobileCityBlockoutDefinition {
    Objects.requireNonNull(structureId, "移动城市结构 ID 不能为空");
    Objects.requireNonNull(place, "移动城市地点不能为空");
    Objects.requireNonNull(templateRoot, "移动城市模板目录不能为空");
    Objects.requireNonNull(program, "逐城建筑方案不能为空");
    Objects.requireNonNull(placeholderBlock, "移动城市占位方块不能为空");
    if (!place.type().isUrban()) {
      throw new IllegalArgumentException("移动城市 Blockout 只能绑定城市、聚落或城区：" + place.id());
    }
    if (plateWidth <= 0 || plateDepth <= 0 || layerHeight <= 0) {
      throw new IllegalArgumentException("移动地块尺寸必须为正数：" + place.id());
    }
    int plateArea = Math.multiplyExact(plateWidth, plateDepth);
    if (plateArea < MIN_PLATE_AREA || plateArea > MAX_PLATE_AREA) {
      throw new IllegalArgumentException("移动地块水平占地必须约为 1000 方块：" + place.id() + " / " + plateArea);
    }
    if (layerCount != REQUIRED_LAYER_COUNT) {
      throw new IllegalArgumentException("移动地块必须恰好包含四层：" + place.id());
    }
    if (!program.placeId().equals(place.id())) {
      throw new IllegalArgumentException("逐城建筑方案与地点不一致：" + place.id());
    }
    for (MobileCityProgram.Landmark landmark : program.landmarks()) {
      int landmarkArea = landmark.area();
      if (landmarkArea < MIN_LANDMARK_AREA || landmarkArea > MAX_LANDMARK_AREA) {
        throw new IllegalArgumentException("城市地标水平占地必须为 200—300 方块：" + place.id() + " / " + landmarkArea);
      }
    }
    if (plateWidth > 48 || plateDepth > 48 || layerCount * layerHeight > 48) {
      throw new IllegalArgumentException("移动地块不能超过单个结构模板的 48 格轴长限制：" + place.id());
    }
  }

  public int plateArea() {
    return plateWidth * plateDepth;
  }

  public int landmarkCount() {
    return program.landmarks().size();
  }
}

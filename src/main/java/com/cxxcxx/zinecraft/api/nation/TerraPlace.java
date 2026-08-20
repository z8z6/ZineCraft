package com.cxxcxx.zinecraft.api.nation;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 泰拉固定地图上的城市或地区。
 *
 * <p>名称和国家归属来自资料，坐标及椭圆范围是 Zinecraft 的游戏化布局，不表示官方坐标。</p>
 */
public record TerraPlace(
    String id,
    TerraNation nation,
    TerraPlaceType type,
    String zhCn,
    String enUs,
    int x,
    int z,
    int radiusX,
    int radiusZ
) {
  private static final Pattern ID_PATTERN = Pattern.compile("[a-z0-9_]+(?:/[a-z0-9_]+)*");

  public TerraPlace {
    id = requireText(id, "地点 ID");
    nation = Objects.requireNonNull(nation, "地点所属国家不能为空");
    type = Objects.requireNonNull(type, "地点类型不能为空");
    zhCn = requireText(zhCn, "地点中文名");
    enUs = requireText(enUs, "地点英文名");
    if (!ID_PATTERN.matcher(id).matches()) throw new IllegalArgumentException("地点 ID 非法：" + id);
    if (!id.startsWith(nation.getId() + "/")) {
      throw new IllegalArgumentException("地点 ID 必须以所属国家开头：" + id);
    }
    if (radiusX < 128 || radiusZ < 128) throw new IllegalArgumentException("地点地图半径不能小于 128 格：" + id);
  }

  private static String requireText(String value, String field) {
    String text = Objects.requireNonNull(value, field + "不能为空").strip();
    if (text.isEmpty()) throw new IllegalArgumentException(field + "不能为空");
    return text;
  }

  public String translationKey() {
    return "journeymap.zinecraft.place." + id.replace('/', '.');
  }
}

"""Generate fixed, replaceable four-layer mobile-city Blockout templates.

City and place names are read from TerraGeography, whose catalogue is sourced
from PRTS.  This exporter does not invent canonical landmark names.  Every
unknown landmark is emitted under the blockout namespace and recorded as
UNKNOWN in the manifest so it can be replaced by an evidence-backed build.
"""

from __future__ import annotations

import re
import shutil
import sys
from dataclasses import dataclass
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "script"))

from nation_settlements.common import AIR, JIGSAW, Template  # noqa: E402
from nation_landmarks.base import NATION_MATERIALS  # noqa: E402
SOURCE = ROOT / "src/main/java/com/cxxcxx/zinecraft/core/nation/TerraGeography.java"
CITY_PROGRAMS_SOURCE = ROOT / "src/main/java/com/cxxcxx/zinecraft/core/registry/ModCityStructure.java"
OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/blockout/terra_cities"
REGION_OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/blockout/terra_regions"
ASSET_OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/blockout/terra_city_assets"
PLATE_WIDTH = 32
PLATE_DEPTH = 32
LAYER_COUNT = 4
LAYER_HEIGHT = 8
TEMPLATE_HEIGHT = 48
LANDMARK_WIDTH = 15
LANDMARK_DEPTH = 17
URBAN_TYPES = frozenset({"city", "settlement", "district"})
PLACE_TYPES = URBAN_TYPES | {"region", "natural_feature"}


@dataclass(frozen=True)
class Place:
    nation: str
    kind: str
    name: str
    local_id: str

    @property
    def place_id(self) -> str:
        return f"{self.nation}/{self.local_id}"


@dataclass(frozen=True)
class NationBlockoutPalette:
    trim: str


NATION_BLOCKOUT_PALETTES: dict[str, NationBlockoutPalette] = {
    "aegir": NationBlockoutPalette("minecraft:oxidized_copper"),
    "bolivar": NationBlockoutPalette("minecraft:cut_copper"),
    "higashi": NationBlockoutPalette("minecraft:dark_oak_planks"),
    "durin": NationBlockoutPalette("minecraft:yellow_terracotta"),
    "columbia": NationBlockoutPalette("minecraft:iron_block"),
    "kazimierz": NationBlockoutPalette("minecraft:gold_block"),
    "kazdel": NationBlockoutPalette("minecraft:polished_blackstone"),
    "laterano": NationBlockoutPalette("minecraft:gold_block"),
    "leithanien": NationBlockoutPalette("minecraft:amethyst_block"),
    "rim_billiton": NationBlockoutPalette("minecraft:cut_copper"),
    "minos": NationBlockoutPalette("minecraft:chiseled_sandstone"),
    "sargon": NationBlockoutPalette("minecraft:cut_sandstone"),
    "sami": NationBlockoutPalette("minecraft:spruce_planks"),
    "victoria": NationBlockoutPalette("create:industrial_iron_block"),
    "ursus": NationBlockoutPalette("minecraft:iron_block"),
    "kjerag": NationBlockoutPalette("minecraft:spruce_planks"),
    "siracusa": NationBlockoutPalette("minecraft:dark_oak_planks"),
    "yan": NationBlockoutPalette("minecraft:polished_tuff"),
    "iberia": NationBlockoutPalette("minecraft:oxidized_copper"),
}


def _base36(value: int) -> str:
    digits = "0123456789abcdefghijklmnopqrstuvwxyz"
    if value == 0:
        return "0"
    result = ""
    while value:
        value, remainder = divmod(value, 36)
        result = digits[remainder] + result
    return result


def _java_unsigned_hash(value: str) -> str:
    result = 0
    for character in value:
        result = (31 * result + ord(character)) & 0xFFFFFFFF
    return _base36(result)


def read_places() -> list[Place]:
    source = SOURCE.read_text(encoding="utf-8")
    start = source.index("private static List<NationDefinition> definitions()")
    end = source.index("private static NationDefinition nation", start)
    current_nation: str | None = None
    places: list[Place] = []
    for line in source[start:end].splitlines():
        nation_match = re.search(r"nation\(TerraNation\.([A-Z_]+),", line)
        if nation_match:
            current_nation = nation_match.group(1).lower()
        if current_nation is None:
            continue
        for kind, name in re.findall(r'(city|settlement|district|region|feature)\("([^"]+)"\)', line):
            if kind == "feature":
                kind = "natural_feature"
            local_id = f"{kind}_{_java_unsigned_hash(name)}"
            places.append(Place(current_nation, kind, name, local_id))
    if len(places) != 187 or {place.nation for place in places} != set(NATION_BLOCKOUT_PALETTES):
        raise ValueError("TerraGeography 城市目录变化后必须重新审查移动城市 Blockout")
    if len({place.place_id for place in places}) != len(places):
        raise ValueError("移动城市地点 ID 重复")
    return places


def read_city_programs(urban_places: list[Place]) -> dict[str, dict[str, object]]:
    source = CITY_PROGRAMS_SOURCE.read_text(encoding="utf-8")
    city_pattern = re.compile(
        r'city\("([^"]+)", ([A-Za-z0-9_.]+), List\.of\((.*?)\), (.+)\),?$'
    )
    landmark_pattern = re.compile(
        r'landmark\("([a-z0-9_]+)", "([^"]+)", (\d+), (\d+), (\d+), (\d+), (\d+), (\d+)\)'
    )
    building_pattern = re.compile(
        r'building\("([a-z0-9_]+)", "([^"]+)", (\d+), (\d+), (\d+), (\d+)\)'
    )
    programs: dict[str, dict[str, object]] = {}
    for raw_line in source.splitlines():
        match = city_pattern.fullmatch(raw_line.strip())
        if match is None:
            continue
        place_id, layout, raw_landmarks, raw_buildings = match.groups()
        if place_id in programs:
            raise ValueError(f"{place_id}: Java 逐城建筑声明重复")
        landmarks = []
        for landmark_id, zh_cn, width, depth, height, tower_x, tower_z, tower_height in landmark_pattern.findall(raw_landmarks):
            width_value = int(width)
            depth_value = int(depth)
            landmarks.append({
                "id": landmark_id,
                "zh_cn": zh_cn,
                "structure": f"zinecraft:city_asset/{place_id}/{landmark_id}",
                "template": f"zinecraft:blockout/terra_city_assets/{place_id}/{landmark_id}",
                "area": width_value * depth_value,
                "width": width_value,
                "depth": depth_value,
                "height": int(height),
                "tower_x": int(tower_x),
                "tower_z": int(tower_z),
                "tower_height": int(tower_height),
            })
        buildings = [
            {
                "id": building_id,
                "zh_cn": zh_cn,
                "structure": f"zinecraft:city_asset/{place_id}/{building_id}",
                "template": f"zinecraft:blockout/terra_city_assets/{place_id}/{building_id}",
                "width": int(width),
                "depth": int(depth),
                "height": int(building_height),
                "weight": int(weight),
                "status": "NATION_FUNCTION_BLOCKOUT",
            }
            for building_id, zh_cn, width, depth, building_height, weight in building_pattern.findall(raw_buildings)
        ]
        programs[place_id] = {
            "nation": place_id.split("/", 1)[0],
            "place_type": place_id.split("/", 1)[1].split("_", 1)[0],
            "layout": layout,
            "landmarks": landmarks,
            "buildings": buildings,
        }
    expected_ids = {place.place_id for place in urban_places}
    if set(programs) != expected_ids:
        missing = sorted(expected_ids - set(programs))
        unknown = sorted(set(programs) - expected_ids)
        raise ValueError(f"逐城建筑声明未与 TerraGeography 一一对应：缺少={missing}，未知={unknown}")
    for place in urban_places:
        program = programs[place.place_id]
        if not isinstance(program, dict):
            raise ValueError(f"{place.place_id}: 城市声明必须为对象")
        if program.get("nation") != place.nation or program.get("place_type") != place.kind:
            raise ValueError(f"{place.place_id}: 国家或地点类型与 TerraGeography 不一致")
        if program.get("layout") != "GridCityLayout.DEFAULT":
            raise ValueError(f"{place.place_id}: 当前 Blockout 生成器只支持默认棋盘格布局")
        landmarks = program.get("landmarks")
        if not isinstance(landmarks, list) or not landmarks or len(landmarks) > 5:
            raise ValueError(f"{place.place_id}: 必须显式声明 1—5 个城市地标")
        if any(not 200 <= int(landmark.get("area", 0)) <= 300 for landmark in landmarks):
            raise ValueError(f"{place.place_id}: 每个地标必须占地 200—300 方块")
        buildings = program.get("buildings")
        if not isinstance(buildings, list) or not buildings:
            raise ValueError(f"{place.place_id}: 必须显式声明普通建筑目录")
        for building in buildings:
            building_id = building.get("id")
            height = building.get("height")
            if not isinstance(building_id, str) or not re.fullmatch(r"[a-z0-9_]+", building_id):
                raise ValueError(f"{place.place_id}: 建筑 ID 非法")
            if not isinstance(height, int) or not 6 <= height <= 22:
                raise ValueError(f"{place.place_id}/{building_id}: 建筑高度必须为 6—22 格")
            if int(building.get("width", 0)) > 12 or int(building.get("depth", 0)) > 12:
                raise ValueError(f"{place.place_id}/{building_id}: 普通建筑不能越出默认棋盘格地块")
    return programs


def build_city_assets(city: Place, city_program: dict[str, object]) -> list[Template]:
    """Create city-owned NBTs; blockout geometry may repeat, registry identity may not."""
    nation_palette = NATION_BLOCKOUT_PALETTES[city.nation]
    ground, wall, _national_trim = NATION_MATERIALS[city.nation]
    assets: list[Template] = []
    for landmark in city_program["landmarks"]:
        spec = dict(landmark)
        template = Template(city.nation, city.place_id, str(spec["id"]), (32, TEMPLATE_HEIGHT, 32), "city_asset")
        _fill_layer_shell(template, ground, wall, nation_palette.trim)
        _place_landmark(template, wall, nation_palette.trim, spec)
        assets.append(template)
    for role_index, building in enumerate(city_program["buildings"]):
        spec = dict(building)
        template = Template(city.nation, city.place_id, str(spec["id"]), (32, TEMPLATE_HEIGHT, 32), "city_asset")
        _fill_layer_shell(template, ground, wall, nation_palette.trim)
        _place_building(template, wall, nation_palette.trim, spec, 10, 10, role_index)
        assets.append(template)
    return assets


def _fill_layer_shell(template: Template, ground: str, wall: str, trim: str) -> None:
    for layer in range(LAYER_COUNT):
        floor_y = layer * LAYER_HEIGHT
        template.cuboid((0, floor_y, 0), (31, floor_y, 31), ground if layer == 0 else trim)
        if layer == LAYER_COUNT - 1:
            continue
        ceiling_y = floor_y + LAYER_HEIGHT - 1
        template.cuboid((0, floor_y + 1, 0), (0, ceiling_y, 31), wall)
        template.cuboid((31, floor_y + 1, 0), (31, ceiling_y, 31), wall)
        template.cuboid((1, floor_y + 1, 0), (30, ceiling_y, 0), wall)
        template.cuboid((1, floor_y + 1, 31), (30, ceiling_y, 31), wall)
        for support_x, support_z in ((7, 7), (7, 24), (24, 7), (24, 24)):
            template.cuboid((support_x, floor_y + 1, support_z), (support_x + 1, ceiling_y, support_z + 1), wall)
        template.cuboid((15, floor_y + 1, 1), (16, floor_y + 3, 30), AIR)
        template.cuboid((1, floor_y + 1, 15), (30, floor_y + 3, 16), AIR)
    # One continuous service ladder connects all four layers.
    for y in range(1, LAYER_HEIGHT * (LAYER_COUNT - 1) + 2):
        template.block(2, y, 2, "minecraft:ladder", {"facing": "south", "waterlogged": "false"})
        template.block(2, y, 1, wall)
    for floor_y in (8, 16, 24):
        template.block(2, floor_y, 2, "minecraft:ladder", {"facing": "south", "waterlogged": "false"})
        template.block(2, floor_y + 1, 2, "minecraft:ladder", {"facing": "south", "waterlogged": "false"})


def _city_connector(template: Template, city: Place, module: str) -> None:
    seam = f"zinecraft:blockout/terra_city/{city.place_id}/{module}"
    pool = seam
    if module == "east":
        position, direction = (31, 25, 15), "east"
    elif module == "west":
        position, direction = (0, 25, 15), "west"
    elif module == "north":
        position, direction = (15, 25, 0), "north"
    else:
        position, direction = (15, 25, 31), "south"
    template.connector(*position, direction, seam, seam, pool, AIR)
    x, y, z = position
    template.block(x, y + 1, z, AIR)


def _child_connector(template: Template, city: Place, module: str) -> None:
    seam = f"zinecraft:blockout/terra_city/{city.place_id}/{module}"
    if module == "east":
        position, direction = (0, 25, 15), "west"
    elif module == "west":
        position, direction = (31, 25, 15), "east"
    elif module == "north":
        position, direction = (15, 25, 31), "south"
    else:
        position, direction = (15, 25, 0), "north"
    template.connector(*position, direction, seam, "minecraft:empty", "minecraft:empty", AIR)
    x, y, z = position
    template.block(x, y + 1, z, AIR)


def _landmark_spec(index: int) -> dict[str, int]:
    # These values are persisted in the manifest as explicit Blockout geometry,
    # not interpreted as canonical city facts.
    return {
        "width": LANDMARK_WIDTH,
        "depth": LANDMARK_DEPTH,
        "height": 10 + index % 12,
        "tower_x": 1 + (index // 12) % 6,
        "tower_z": 2 + (index // 72) * 7 + index % 3,
        "tower_height": 3 + (index // 6) % 6,
    }


def _place_landmark(template: Template, wall: str, trim: str, spec: dict[str, int]) -> None:
    x0 = (PLATE_WIDTH - spec["width"]) // 2
    z0 = (PLATE_DEPTH - spec["depth"]) // 2
    x1 = x0 + spec["width"] - 1
    z1 = z0 + spec["depth"] - 1
    base_y = 25
    top_y = min(45, base_y + spec["height"] - 1)
    template.cuboid((x0, base_y, z0), (x1, top_y, z1), wall)
    template.cuboid((x0 + 2, base_y + 1, z0 + 2), (x1 - 2, top_y - 1, z1 - 2), AIR)
    door_x = (x0 + x1) // 2
    template.cuboid((door_x - 1, base_y, z0), (door_x + 1, base_y + 2, z0), AIR)
    template.cuboid((x0, top_y, z0), (x1, top_y, z1), trim)
    tower_x = x0 + spec["tower_x"]
    tower_z = z0 + min(spec["depth"] - 3, spec["tower_z"])
    tower_top = min(47, top_y + spec["tower_height"])
    template.cuboid((tower_x, top_y + 1, tower_z), (tower_x + 1, tower_top, tower_z + 1), trim)


def _place_building(
    template: Template,
    wall: str,
    trim: str,
    building: dict[str, object],
    lot_x: int,
    lot_z: int,
    role_index: int,
) -> None:
    width = int(building["width"])
    depth = int(building["depth"])
    x0 = lot_x + (12 - width) // 2
    z0 = lot_z + (12 - depth) // 2
    x1, z1 = x0 + width - 1, z0 + depth - 1
    y0, y1 = 25, min(46, 25 + int(building["height"]) - 1)
    template.cuboid((x0, y0, z0), (x1, y1, z1), wall)
    template.cuboid((x0 + 1, y0 + 1, z0 + 1), (x1 - 1, y1 - 1, z1 - 1), AIR)
    door_x = (x0 + x1) // 2
    template.cuboid((door_x, y0, z0), (door_x, y0 + 2, z0), AIR)
    if role_index == 0:
        template.cuboid((x0, y1, z0), (x1, y1, z1), trim)
    elif role_index == 1:
        template.cuboid((x0 - 1, y0 + 3, z0 - 1), (x1 + 1, y0 + 3, z0), trim)
    elif role_index == 2:
        template.cuboid((x1 + 1, y0, z0 + 2), (x1 + 2, y1 - 2, z1 - 2), trim)
    else:
        template.cuboid((x0 + 2, y1 + 1, z0 + 2), (x1 - 2, min(47, y1 + 3), z1 - 2), trim)


def _place_grid_buildings(
    template: Template,
    wall: str,
    trim: str,
    buildings: list[dict[str, object]],
) -> None:
    # 12x12 lots separated by four-block roads; catalog entries are selected
    # deterministically and are not tied to plate compass directions.
    for lot_index, (lot_x, lot_z) in enumerate(((2, 2), (18, 2), (2, 18), (18, 18))):
        building = buildings[lot_index % len(buildings)]
        _place_building(template, wall, trim, building, lot_x, lot_z, lot_index)


def build_city(city: Place, city_program: dict[str, object]) -> tuple[list[Template], dict[str, object]]:
    nation_palette = NATION_BLOCKOUT_PALETTES[city.nation]
    ground, wall, _national_trim = NATION_MATERIALS[city.nation]
    modules_by_name = {
        name: Template(city.nation, city.place_id, name, (32, TEMPLATE_HEIGHT, 32), "city_blockout")
        for name in ("center", "north", "south", "east", "west")
    }
    for module in modules_by_name.values():
        _fill_layer_shell(module, ground, wall, nation_palette.trim)
    center = modules_by_name["center"]
    for direction in ("north", "south", "east", "west"):
        _city_connector(center, city, direction)
    for direction in ("north", "south", "east", "west"):
        module = modules_by_name[direction]
        _child_connector(module, city, direction)
    landmarks = [dict(landmark) for landmark in city_program["landmarks"]]
    landmark_modules = ("center", "north", "east", "south", "west")
    occupied_modules = set()
    for landmark, module_name in zip(landmarks, landmark_modules, strict=False):
        _place_landmark(modules_by_name[module_name], wall, nation_palette.trim, landmark)
        occupied_modules.add(module_name)
    buildings = [dict(building) for building in city_program["buildings"]]
    for module_name in ("north", "south", "east", "west"):
        if module_name not in occupied_modules:
            _place_grid_buildings(modules_by_name[module_name], wall, nation_palette.trim, buildings)
    modules = [modules_by_name[name] for name in ("center", "north", "south", "east", "west")]
    manifest = {
        "place_id": city.place_id,
        "name": city.name,
        "nation": city.nation,
        "place_type": city.kind,
        "structure": f"zinecraft:blockout/terra_city/{city.place_id}",
        "template_root": f"zinecraft:blockout/terra_cities/{city.place_id}",
        "status": "BLOCKOUT",
        "source_status": "UNKNOWN",
        "plate": {"width": 32, "depth": 32, "area": 1024, "layers": 4, "layer_height": 8, "pieces": 5},
        "layout": city_program["layout"],
        "landmarks": landmarks,
        "buildings": buildings,
    }
    return modules, manifest


def build_region(place: Place, index: int) -> tuple[Template, dict[str, object]]:
    palette = NATION_BLOCKOUT_PALETTES[place.nation]
    ground, wall, _national_trim = NATION_MATERIALS[place.nation]
    template = Template(place.nation, place.place_id, "landmark", (32, TEMPLATE_HEIGHT, 32), "region_blockout")
    template.cuboid((0, 0, 0), (31, 0, 31), ground)
    spec = _landmark_spec(index)
    _place_landmark(template, wall, palette.trim, spec)
    # Region markers start directly above their terrain plinth instead of a mobile deck.
    shifted: dict[tuple[int, int, int], tuple[int, dict[str, str | int] | None]] = {}
    for (x, y, z), value in template.blocks.items():
        shifted[(x, y - 24 if y >= 25 else y, z)] = value
    template.blocks = shifted
    manifest = {
        "place_id": place.place_id,
        "name": place.name,
        "nation": place.nation,
        "place_type": place.kind,
        "structure": f"zinecraft:blockout/terra_region/{place.place_id}",
        "template": f"zinecraft:blockout/terra_regions/{place.place_id}/landmark",
        "status": "BLOCKOUT",
        "source_status": "UNKNOWN",
        "landmark": {"id": f"{place.local_id}_region_marker_placeholder", "area": spec["width"] * spec["depth"], **spec},
    }
    return template, manifest


def validate(city_modules: list[tuple[Place, list[Template], dict[str, object]]]) -> None:
    if len(city_modules) != 112:
        raise ValueError("必须覆盖 112 个城市、聚落和城区")
    shapes: set[frozenset[tuple[int, int, int]]] = set()
    for city, modules, manifest in city_modules:
        if {module.name for module in modules} != {"center", "north", "south", "east", "west"}:
            raise ValueError(f"{city.place_id}: 必须包含五块移动地块")
        if any(module.size != (32, 48, 32) for module in modules):
            raise ValueError(f"{city.place_id}: 移动地块模板尺寸错误")
        for module in modules:
            for floor_y in (0, 8, 16, 24):
                if any(module.block_name((x, floor_y, z)) in {None, AIR} for x in range(32) for z in range(32)):
                    raise ValueError(f"{city.place_id}/{module.name}: 四层楼板不连续")
        connector_counts = [
            sum(module.block_name(position) == JIGSAW for position in module.blocks)
            for module in modules
        ]
        if connector_counts != [4, 1, 1, 1, 1]:
            raise ValueError(f"{city.place_id}: Jigsaw 地块接口数量错误 {connector_counts}")
        landmarks = manifest["landmarks"]
        assert isinstance(landmarks, list)
        if not landmarks or any(not 200 <= int(landmark["area"]) <= 300 for landmark in landmarks):
            raise ValueError(f"{city.place_id}: 地标占地越界")
        center = modules[0]
        shape = frozenset(
            position for position, (state, _nbt) in center.blocks.items()
            if center.palette[state][0] not in {AIR, JIGSAW} and position[1] >= 25
        )
        if shape in shapes:
            raise ValueError(f"{city.place_id}: 城市核心 Blockout 形体重复")
        shapes.add(shape)


def validate_regions(regions: list[tuple[Place, Template, dict[str, object]]]) -> None:
    if len(regions) != 75:
        raise ValueError("必须覆盖 75 个重要地区或自然地貌")
    shapes: set[frozenset[tuple[int, int, int]]] = set()
    for place, template, manifest in regions:
        landmark = manifest["landmark"]
        assert isinstance(landmark, dict)
        if not 200 <= int(landmark["area"]) <= 300:
            raise ValueError(f"{place.place_id}: 地区地标占地越界")
        shape = frozenset(
            position for position, (state, _nbt) in template.blocks.items()
            if template.palette[state][0] not in {AIR, JIGSAW} and position[1] > 0
        )
        if shape in shapes:
            raise ValueError(f"{place.place_id}: 地区地标 Blockout 形体重复")
        shapes.add(shape)


def main() -> None:
    places = read_places()
    urban_places = [place for place in places if place.kind in URBAN_TYPES]
    region_places = [place for place in places if place.kind not in URBAN_TYPES]
    city_programs = read_city_programs(urban_places)
    built = [(place, *build_city(place, city_programs[place.place_id])) for place in urban_places]
    city_assets = [
        asset
        for place in urban_places
        for asset in build_city_assets(place, city_programs[place.place_id])
    ]
    regions = [
        (place, *build_region(place, index + len(urban_places)))
        for index, place in enumerate(region_places)
    ]
    validate(built)
    validate_regions(regions)
    expected = ROOT / "src/main/resources/data/zinecraft/structure/blockout/terra_cities"
    if OUTPUT.resolve() != expected.resolve() or OUTPUT.name != "terra_cities":
        raise RuntimeError("拒绝清理非移动城市 Blockout 目录")
    if OUTPUT.exists():
        shutil.rmtree(OUTPUT)
    OUTPUT.mkdir(parents=True)
    expected_region = ROOT / "src/main/resources/data/zinecraft/structure/blockout/terra_regions"
    if REGION_OUTPUT.resolve() != expected_region.resolve() or REGION_OUTPUT.name != "terra_regions":
        raise RuntimeError("拒绝清理非地区地标 Blockout 目录")
    if REGION_OUTPUT.exists():
        shutil.rmtree(REGION_OUTPUT)
    REGION_OUTPUT.mkdir(parents=True)
    expected_assets = ROOT / "src/main/resources/data/zinecraft/structure/blockout/terra_city_assets"
    if ASSET_OUTPUT.resolve() != expected_assets.resolve() or ASSET_OUTPUT.name != "terra_city_assets":
        raise RuntimeError("拒绝清理非逐城结构 Blockout 目录")
    if ASSET_OUTPUT.exists():
        shutil.rmtree(ASSET_OUTPUT)
    ASSET_OUTPUT.mkdir(parents=True)
    written = [module.write(OUTPUT) for _place, modules, _manifest in built for module in modules]
    if len(written) != 560 or not all(path.is_file() for path in written):
        raise RuntimeError("移动城市 NBT 写出数量错误")
    region_written = [template.write(REGION_OUTPUT) for _place, template, _manifest in regions]
    if len(region_written) != 75 or not all(path.is_file() for path in region_written):
        raise RuntimeError("地区地标 NBT 写出数量错误")
    asset_written = [template.write(ASSET_OUTPUT) for template in city_assets]
    expected_asset_count = sum(
        len(program["landmarks"]) + len(program["buildings"])
        for program in city_programs.values()
    )
    if len(asset_written) != expected_asset_count or not all(path.is_file() for path in asset_written):
        raise RuntimeError("逐城注册结构 NBT 写出数量错误")
    print(f"Loaded {len(city_programs)} explicit city programs; no nation-derived building catalog")
    print(f"Generated 560 city NBT templates, {expected_asset_count} city-owned asset NBTs, and 75 region landmark templates")
    print("Validated 1024-block plates, four layers, 200-300-block landmarks, 112 unique city cores, and 75 unique region markers")


if __name__ == "__main__":
    main()

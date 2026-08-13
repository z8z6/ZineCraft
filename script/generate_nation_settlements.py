"""确定性生成十九个泰拉国家大型 Jigsaw 聚落的结构模板。"""

from __future__ import annotations

import gzip
import struct
from dataclasses import dataclass
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/nation_settlements"
DATA_VERSION = 3955


def utf(value: str) -> bytes:
    encoded = value.encode("utf-8")
    return struct.pack(">H", len(encoded)) + encoded


def named(tag_type: int, name: str, payload: bytes) -> bytes:
    return bytes([tag_type]) + utf(name) + payload


def string_payload(value: str) -> bytes:
    return utf(value)


def int_payload(value: int) -> bytes:
    return struct.pack(">i", value)


def list_payload(tag_type: int, values: list[bytes]) -> bytes:
    return bytes([tag_type]) + struct.pack(">i", len(values)) + b"".join(values)


def compound_payload(tags: list[bytes]) -> bytes:
    return b"".join(tags) + b"\x00"


def palette_entry(name: str, properties: dict[str, str] | None = None) -> bytes:
    tags = [named(8, "Name", string_payload(name))]
    if properties:
        tags.append(named(10, "Properties", compound_payload([
            named(8, key, string_payload(value)) for key, value in properties.items()
        ])))
    return compound_payload(tags)


def block_entry(position: tuple[int, int, int], state: int, nbt: dict[str, str | int] | None = None) -> bytes:
    tags = [
        named(9, "pos", list_payload(3, [int_payload(value) for value in position])),
        named(3, "state", int_payload(state)),
    ]
    if nbt:
        nbt_tags = [
            named(3 if isinstance(value, int) else 8, key, int_payload(value) if isinstance(value, int) else string_payload(value))
            for key, value in nbt.items()
        ]
        tags.append(named(10, "nbt", compound_payload(nbt_tags)))
    return compound_payload(tags)


def jigsaw_nbt(name: str, target: str, pool: str, final_state: str) -> dict[str, str | int]:
    return {
        "id": "minecraft:jigsaw",
        "name": name,
        "target": target,
        "pool": pool,
        "final_state": final_state,
        "joint": "rollable",
        "selection_priority": 0,
        "placement_priority": 0,
    }


ORIENTATION = {
    "north": "north_up",
    "south": "south_up",
    "east": "east_up",
    "west": "west_up",
}


@dataclass(frozen=True)
class NationStyle:
    path: str
    buildings: tuple[str, str, str, str]
    foundation: str
    wall: str
    roof: str
    accent: str
    road: str


STYLES = (
    NationStyle("aegir_subsea_enclave", ("pressure_residence", "hydroponics_lab", "bathysphere_dock", "current_archive"), "dark_prismarine", "prismarine_bricks", "cyan_stained_glass", "sea_lantern", "prismarine"),
    NationStyle("bolivar_dossoles_district", ("canal_house", "beach_market", "race_workshop", "festival_hall"), "smooth_sandstone", "orange_terracotta", "white_concrete", "yellow_concrete", "cut_sandstone"),
    NationStyle("higashi_sokogawa_town", ("machiya", "swordsmith", "tea_house", "magistrate_house"), "stone_bricks", "dark_oak_planks", "red_nether_bricks", "lantern", "cobblestone"),
    NationStyle("durin_ideal_city_block", ("dome_apartment", "machine_shop", "arcade", "transit_station"), "cut_copper", "cyan_concrete", "light_blue_stained_glass", "sea_lantern", "smooth_stone"),
    NationStyle("columbia_frontier_town", ("prefab_house", "pioneer_lab", "logistics_depot", "sheriff_office"), "smooth_stone", "white_concrete", "cut_copper", "redstone_lamp", "gravel"),
    NationStyle("kazimierz_knight_borough", ("tenement", "armor_workshop", "sponsor_shop", "tournament_inn"), "polished_andesite", "smooth_quartz", "red_concrete", "gold_block", "stone_bricks"),
    NationStyle("kazdel_sarkaz_settlement", ("canvas_house", "forge", "mercenary_lodge", "provision_store"), "blackstone", "dark_oak_planks", "black_wool", "soul_lantern", "polished_blackstone"),
    NationStyle("laterano_monastery_town", ("white_residence", "confectionery", "notary_office", "bell_chapel"), "calcite", "smooth_quartz", "yellow_stained_glass", "gold_block", "quartz_bricks"),
    NationStyle("leithanien_music_town", ("twilight_house", "instrument_workshop", "rehearsal_hall", "arts_academy"), "polished_deepslate", "dark_oak_planks", "black_concrete", "amethyst_block", "deepslate_tiles"),
    NationStyle("rim_billiton_mining_camp", ("miner_bunkhouse", "ore_workshop", "freight_depot", "canteen"), "deepslate_bricks", "spruce_planks", "cut_copper", "redstone_lamp", "cobbled_deepslate"),
    NationStyle("minos_heroic_polis", ("courtyard_house", "olive_market", "training_hall", "council_house"), "sandstone", "smooth_sandstone", "red_terracotta", "gold_block", "cut_sandstone"),
    NationStyle("sargon_oasis_town", ("adobe_house", "spice_market", "caravanserai", "well_house"), "sandstone", "orange_terracotta", "yellow_terracotta", "emerald_block", "smooth_sandstone"),
    NationStyle("sami_snowpriest_village", ("snow_lodge", "hunter_camp", "ritual_house", "supply_shed"), "packed_ice", "spruce_planks", "snow_block", "soul_lantern", "blue_ice"),
    NationStyle("victoria_industrial_borough", ("brick_tenement", "steam_workshop", "rail_warehouse", "council_hall"), "stone_bricks", "bricks", "copper_block", "iron_block", "polished_andesite"),
    NationStyle("ursus_northern_town", ("heated_house", "military_storehouse", "mine_office", "communal_hall"), "polished_diorite", "spruce_planks", "packed_ice", "redstone_lamp", "stone_bricks"),
    NationStyle("kjerag_mountain_village", ("stone_chalet", "tea_workshop", "caravan_post", "shrine_house"), "stone_bricks", "spruce_planks", "snow_block", "blue_ice", "cobblestone"),
    NationStyle("siracusa_family_town", ("family_house", "trattoria", "tailor_shop", "meeting_hall"), "mossy_stone_bricks", "bricks", "dark_oak_planks", "red_wool", "stone_bricks"),
    NationStyle("yan_shangshu_town", ("courtyard_residence", "tea_house", "artisan_workshop", "relay_office"), "tuff_bricks", "dark_oak_planks", "red_terracotta", "lantern", "polished_tuff"),
    NationStyle("iberia_coastal_town", ("saltstone_house", "shipwright", "fish_market", "inquisitor_office"), "stone_bricks", "calcite", "oxidized_copper", "sea_lantern", "gravel"),
)


class Template:
    def __init__(self, style: NationStyle, name: str, size: tuple[int, int, int]):
        self.style = style
        self.name = name
        self.size = size
        materials = (style.foundation, style.wall, style.roof, style.accent, style.road)
        self.palette = [f"minecraft:{material}" for material in materials] + ["minecraft:air"] + [
            "minecraft:jigsaw" for _ in range(4)
        ]
        self.blocks: dict[tuple[int, int, int], tuple[int, dict[str, str | int] | None]] = {}

    def block(self, x: int, y: int, z: int, state: int) -> None:
        sx, sy, sz = self.size
        if not (0 <= x < sx and 0 <= y < sy and 0 <= z < sz):
            raise ValueError(f"{self.style.path}/{self.name}: 方块坐标越界 {(x, y, z)}")
        self.blocks[(x, y, z)] = (state, None)

    def cuboid(self, start: tuple[int, int, int], end: tuple[int, int, int], state: int) -> None:
        for x in range(start[0], end[0] + 1):
            for y in range(start[1], end[1] + 1):
                for z in range(start[2], end[2] + 1):
                    self.block(x, y, z, state)

    def connector(self, x: int, y: int, z: int, direction: str, name: str, target: str, pool: str) -> None:
        state = 6 + ("north", "south", "east", "west").index(direction)
        final_state = f"minecraft:{self.style.road}"
        self.blocks[(x, y, z)] = (state, jigsaw_nbt(name, target, pool, final_state))

    def write(self) -> None:
        palette = [palette_entry(name) for name in self.palette[:6]]
        palette.extend(palette_entry("minecraft:jigsaw", {"orientation": ORIENTATION[direction]}) for direction in ("north", "south", "east", "west"))
        blocks = [block_entry(pos, state, nbt) for pos, (state, nbt) in sorted(self.blocks.items())]
        root = compound_payload([
            named(3, "DataVersion", int_payload(DATA_VERSION)),
            named(9, "size", list_payload(3, [int_payload(value) for value in self.size])),
            named(9, "palette", list_payload(10, palette)),
            named(9, "blocks", list_payload(10, blocks)),
            named(9, "entities", list_payload(10, [])),
        ])
        target = OUTPUT / self.style.path / f"{self.name}.nbt"
        target.parent.mkdir(parents=True, exist_ok=True)
        with gzip.GzipFile(filename=str(target), mode="wb", mtime=0) as stream:
            stream.write(bytes([10]) + utf("") + root)


def road_connector(template: Template, direction: str, target: str = "street") -> None:
    positions = {
        "north": (4, 1, 0), "south": (4, 1, 8), "east": (8, 1, 4), "west": (0, 1, 4),
    }
    x, y, z = positions[direction]
    template.connector(x, y, z, direction, f"zinecraft:{template.style.path}/street", f"zinecraft:{template.style.path}/{target}", f"zinecraft:{template.style.path}/streets")


def building_connector(template: Template, direction: str) -> None:
    positions = {"north": (2, 1, 0), "south": (6, 1, 8), "east": (8, 1, 6), "west": (0, 1, 2)}
    x, y, z = positions[direction]
    template.connector(x, y, z, direction, f"zinecraft:{template.style.path}/building_exit", f"zinecraft:{template.style.path}/building", f"zinecraft:{template.style.path}/buildings")


def center(style: NationStyle) -> Template:
    t = Template(style, "center", (15, 5, 15))
    t.cuboid((0, 0, 5), (14, 0, 9), 4); t.cuboid((5, 0, 0), (9, 0, 14), 4)
    t.cuboid((4, 0, 4), (10, 0, 10), 0); t.cuboid((6, 1, 6), (8, 2, 8), 3)
    for direction, position in {"north": (7, 1, 0), "south": (7, 1, 14), "east": (14, 1, 7), "west": (0, 1, 7)}.items():
        t.connector(*position, direction, f"zinecraft:{style.path}/center", f"zinecraft:{style.path}/street", f"zinecraft:{style.path}/streets")
    return t


def street(style: NationStyle, name: str, exits: tuple[str, ...], buildings: tuple[str, ...]) -> Template:
    t = Template(style, name, (9, 3, 9))
    t.cuboid((3, 0, 0), (5, 0, 8), 4); t.cuboid((0, 0, 3), (8, 0, 5), 4)
    for direction in exits: road_connector(t, direction)
    for direction in buildings: building_connector(t, direction)
    return t


def building(style: NationStyle, name: str, variant: int) -> Template:
    width = 9 + (variant % 2) * 2
    depth = 9 + ((variant + 1) % 2) * 2
    height = 7 + variant
    t = Template(style, name, (width, height, depth))
    t.cuboid((1, 0, 1), (width - 2, 0, depth - 2), 0)
    t.cuboid((2, 1, 2), (width - 3, height - 3, depth - 3), 5)
    for y in range(1, height - 2):
        for x in range(2, width - 2):
            if not (x in (width // 2 - 1, width // 2, width // 2 + 1) and y <= 3):
                t.block(x, y, 1, 1)
            t.block(x, y, depth - 2, 1)
        for z in range(2, depth - 2):
            t.block(1, y, z, 1)
            t.block(width - 2, y, z, 1)
    t.cuboid((1, height - 2, 1), (width - 2, height - 1, depth - 2), 2)
    for x in range(width // 2 - 1, width // 2 + 2):
        for y in range(1, 4):
            t.block(x, y, 1, 5)
    for x, z in ((2, 2), (width - 3, 2), (2, depth - 3), (width - 3, depth - 3)):
        t.block(x, 1, z, 3)
    t.block(1, 2, depth // 2, 3)
    t.block(width - 2, 2, depth // 2, 3)
    t.connector(width // 2, 1, 0, "north", f"zinecraft:{style.path}/building", "minecraft:empty", "minecraft:empty")
    return t


def generate(style: NationStyle) -> list[Template]:
    return [
        center(style),
        street(style, "street_straight", ("north", "south"), ("east", "west")),
        street(style, "street_corner", ("north", "east"), ("south", "west")),
        street(style, "street_cross", ("north", "south", "east", "west"), ()),
        street(style, "street_end", ("north",), ("south", "east", "west")),
        *[building(style, name, index) for index, name in enumerate(style.buildings)],
    ]


if __name__ == "__main__":
    if len(STYLES) != 19 or len({style.path for style in STYLES}) != 19:
        raise ValueError("国家聚落必须恰好有十九种且 ID 不重复")
    templates = [template for style in STYLES for template in generate(style)]
    for template in templates:
        template.write()
    print(f"Generated {len(templates)} Jigsaw settlement templates in {OUTPUT}")

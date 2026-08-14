"""生成十九个泰拉国家共三十八座唯一地标的 Minecraft structure NBT。"""

from __future__ import annotations

import gzip
import struct
from dataclasses import dataclass, field
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/nation_landmarks"
DATA_VERSION = 3955

NATION_MATERIALS = {
    "aegir_": ("zinecraft:aegir_abyssal_slate", "zinecraft:aegir_pressure_tile"),
    "bolivar_": ("zinecraft:bolivar_war_scoured_soil", "zinecraft:bolivar_dossoles_stucco"),
    "higashi_": ("zinecraft:higashi_shadow_loam", "zinecraft:higashi_machiya_plaster"),
    "durin_": ("zinecraft:durin_garden_moss", "zinecraft:durin_ideal_city_panel"),
    "columbia_": ("zinecraft:columbia_canyon_soil", "zinecraft:columbia_frontier_panel"),
    "kazimierz_": ("zinecraft:kazimierz_steppe_turf", "zinecraft:kazimierz_arena_masonry"),
    "kazdel_": ("zinecraft:kazdel_scarred_ash", "zinecraft:kazdel_fortress_plate"),
    "laterano_": ("zinecraft:laterano_alluvial_chalk", "zinecraft:laterano_basilica_marble"),
    "leithanien_": ("zinecraft:leithanien_twilight_humus", "zinecraft:leithanien_resonant_brick"),
    "rim_billiton_": ("zinecraft:rim_billiton_mine_tailings", "zinecraft:rim_billiton_corrugated_steel"),
    "minos_": ("zinecraft:minos_sunbaked_earth", "zinecraft:minos_heroic_masonry"),
    "sargon_": ("zinecraft:sargon_desert_crust", "zinecraft:sargon_oasis_adobe"),
    "sami_": ("zinecraft:sami_frost_moss", "zinecraft:sami_ritual_stone"),
    "victoria_": ("zinecraft:victoria_moorland_soil", "zinecraft:victoria_industrial_brick"),
    "ursus_": ("zinecraft:ursus_permafrost", "zinecraft:ursus_imperial_masonry"),
    "kjerag_": ("zinecraft:kjerag_sacred_snowstone", "zinecraft:kjerag_monastery_stone"),
    "siracusa_": ("zinecraft:siracusa_rain_darkened_soil", "zinecraft:siracusa_family_masonry"),
    "yan_": ("zinecraft:yan_mountain_soil", "zinecraft:yan_courtyard_brick"),
    "iberia_": ("zinecraft:iberia_salt_crusted_gravel", "zinecraft:iberia_coastal_masonry"),
}


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


def palette_entry(name: str) -> bytes:
    return compound_payload([named(8, "Name", string_payload(name))])


def block_entry(position: tuple[int, int, int], state: int) -> bytes:
    return compound_payload(
        [
            named(9, "pos", list_payload(3, [int_payload(value) for value in position])),
            named(3, "state", int_payload(state)),
        ]
    )


@dataclass
class Landmark:
    path: str
    size: tuple[int, int, int]
    palette: list[str]
    blocks: dict[tuple[int, int, int], int] = field(default_factory=dict)

    def __post_init__(self) -> None:
        for prefix, materials in NATION_MATERIALS.items():
            if self.path.startswith(prefix):
                self.palette[:2] = materials
                return
        raise ValueError(f"{self.path}: 无法匹配国家专属材质")

    def block(self, x: int, y: int, z: int, material: int) -> None:
        sx, sy, sz = self.size
        if not (0 <= x < sx and 0 <= y < sy and 0 <= z < sz):
            raise ValueError(f"{self.path}: 方块坐标越界 {(x, y, z)}")
        self.blocks[(x, y, z)] = material

    def cuboid(self, start: tuple[int, int, int], end: tuple[int, int, int], material: int) -> None:
        for x in range(start[0], end[0] + 1):
            for y in range(start[1], end[1] + 1):
                for z in range(start[2], end[2] + 1):
                    self.block(x, y, z, material)

    def column(self, x: int, z: int, bottom: int, top: int, material: int) -> None:
        self.cuboid((x, bottom, z), (x, top, z), material)

    def frame(self, y: int, inset: int, material: int) -> None:
        sx, _, sz = self.size
        for x in range(inset, sx - inset):
            self.block(x, y, inset, material)
            self.block(x, y, sz - inset - 1, material)
        for z in range(inset, sz - inset):
            self.block(inset, y, z, material)
            self.block(sx - inset - 1, y, z, material)

    def write(self) -> None:
        palette = [palette_entry(name) for name in self.palette]
        blocks = [block_entry(pos, state) for pos, state in sorted(self.blocks.items())]
        root = compound_payload(
            [
                named(3, "DataVersion", int_payload(DATA_VERSION)),
                named(9, "size", list_payload(3, [int_payload(value) for value in self.size])),
                named(9, "palette", list_payload(10, palette)),
                named(9, "blocks", list_payload(10, blocks)),
                named(9, "entities", list_payload(10, [])),
            ]
        )
        OUTPUT.mkdir(parents=True, exist_ok=True)
        with gzip.GzipFile(filename=str(OUTPUT / f"{self.path}.nbt"), mode="wb", mtime=0) as stream:
            stream.write(bytes([10]) + utf("") + root)


def aegir() -> Landmark:
    b = Landmark("aegir_volcanic_beacon", (11, 14, 11), ["minecraft:dark_prismarine", "minecraft:prismarine_bricks", "minecraft:magma_block", "minecraft:sea_lantern"])
    b.cuboid((1, 0, 1), (9, 1, 9), 0); b.frame(2, 2, 1); b.cuboid((4, 2, 4), (6, 10, 6), 1); b.column(5, 5, 2, 12, 2); b.block(5, 13, 5, 3)
    return b


def bolivar() -> Landmark:
    b = Landmark("bolivar_dossoles_yacht", (15, 9, 7), ["minecraft:smooth_quartz", "minecraft:light_blue_concrete", "minecraft:dark_oak_planks", "minecraft:white_wool"])
    for x in range(1, 14):
        width = 1 if x in (1, 13) else 2
        b.cuboid((x, 0, 3 - width), (x, 1, 3 + width), 1)
    b.cuboid((4, 2, 1), (11, 3, 5), 0); b.cuboid((6, 4, 2), (10, 5, 4), 2); b.column(5, 3, 3, 7, 2); b.cuboid((6, 5, 3), (9, 7, 3), 3)
    return b


def higashi() -> Landmark:
    b = Landmark("higashi_rift_shrine", (11, 10, 9), ["minecraft:stone_bricks", "minecraft:red_nether_bricks", "minecraft:dark_oak_planks", "minecraft:shroomlight"])
    b.cuboid((1, 0, 1), (9, 0, 7), 0); b.column(2, 3, 1, 7, 1); b.column(8, 3, 1, 7, 1); b.cuboid((1, 7, 3), (9, 8, 3), 1); b.cuboid((3, 1, 4), (7, 5, 7), 2); b.cuboid((2, 6, 3), (8, 6, 8), 2); b.block(5, 5, 3, 3)
    return b


def durin() -> Landmark:
    b = Landmark("durin_dome_station", (13, 9, 9), ["minecraft:cut_copper", "minecraft:oxidized_copper", "minecraft:tinted_glass", "minecraft:sea_lantern"])
    b.cuboid((1, 0, 1), (11, 0, 7), 0); b.cuboid((1, 1, 3), (11, 1, 5), 1)
    for x in range(1, 12):
        height = 7 - abs(6 - x) // 2
        b.column(x, 1, 1, height, 0); b.column(x, 7, 1, height, 0); b.block(x, height, 4, 2)
    b.cuboid((3, 1, 4), (9, 1, 4), 3)
    return b


def columbia() -> Landmark:
    b = Landmark("columbia_frontier_lab", (13, 9, 11), ["minecraft:white_concrete", "minecraft:iron_block", "minecraft:light_blue_stained_glass", "minecraft:copper_block"])
    b.cuboid((1, 0, 1), (11, 0, 9), 1); b.cuboid((2, 1, 2), (10, 5, 8), 0); b.cuboid((3, 2, 1), (9, 4, 1), 2); b.cuboid((4, 6, 4), (8, 7, 7), 3); b.column(6, 5, 6, 8, 1)
    return b


def kazimierz() -> Landmark:
    b = Landmark("kazimierz_arena_gate", (15, 11, 7), ["minecraft:polished_andesite", "minecraft:smooth_quartz", "minecraft:gold_block", "minecraft:red_concrete"])
    b.cuboid((0, 0, 1), (14, 0, 5), 0); b.cuboid((1, 1, 1), (4, 8, 5), 1); b.cuboid((10, 1, 1), (13, 8, 5), 1); b.cuboid((4, 7, 1), (10, 9, 5), 1); b.cuboid((5, 8, 0), (9, 8, 6), 3); b.block(7, 10, 3, 2)
    return b


def kazdel() -> Landmark:
    b = Landmark("kazdel_babel_ruins", (11, 14, 11), ["minecraft:blackstone", "minecraft:polished_blackstone_bricks", "minecraft:crying_obsidian", "minecraft:soul_lantern"])
    b.cuboid((1, 0, 1), (9, 0, 9), 0); b.column(2, 2, 1, 10, 1); b.column(8, 8, 1, 12, 1); b.column(2, 8, 1, 6, 1); b.column(8, 2, 1, 8, 1); b.cuboid((4, 1, 4), (6, 9, 6), 2); b.block(5, 10, 5, 3)
    return b


def laterano() -> Landmark:
    b = Landmark("laterano_revelation_tower", (9, 15, 9), ["minecraft:calcite", "minecraft:smooth_quartz", "minecraft:gold_block", "minecraft:glowstone"])
    b.cuboid((1, 0, 1), (7, 1, 7), 0); b.cuboid((2, 2, 2), (6, 11, 6), 1); b.frame(12, 1, 2); b.column(4, 4, 12, 14, 2); b.block(4, 11, 4, 3)
    return b


def leithanien() -> Landmark:
    b = Landmark("leithanien_twin_spires", (13, 15, 9), ["minecraft:white_concrete", "minecraft:black_concrete", "minecraft:amethyst_block", "minecraft:polished_deepslate"])
    b.cuboid((1, 0, 1), (11, 0, 7), 3); b.cuboid((2, 1, 2), (5, 11, 6), 0); b.cuboid((7, 1, 2), (10, 11, 6), 1); b.cuboid((4, 5, 3), (8, 6, 5), 2); b.column(3, 4, 12, 14, 0); b.column(9, 4, 12, 14, 1)
    return b


def rim_billiton() -> Landmark:
    b = Landmark("rim_billiton_mining_derrick", (13, 13, 11), ["minecraft:deepslate_bricks", "minecraft:iron_block", "minecraft:cut_copper", "minecraft:redstone_lamp"])
    b.cuboid((1, 0, 1), (11, 0, 9), 0)
    for x, z in ((3, 3), (9, 3), (3, 8), (9, 8)): b.column(x, z, 1, 10, 1)
    for y in (3, 6, 9): b.cuboid((3, y, 3), (9, y, 3), 2); b.cuboid((3, y, 8), (9, y, 8), 2)
    b.column(6, 5, 1, 12, 0); b.block(6, 11, 5, 3)
    return b


def minos() -> Landmark:
    b = Landmark("minos_heroes_temple", (15, 10, 11), ["minecraft:sandstone", "minecraft:quartz_pillar", "minecraft:chiseled_sandstone", "minecraft:gold_block"])
    b.cuboid((1, 0, 1), (13, 1, 9), 0)
    for x in (2, 5, 9, 12): b.column(x, 2, 2, 7, 1); b.column(x, 8, 2, 7, 1)
    b.cuboid((1, 8, 1), (13, 9, 9), 2); b.block(7, 7, 5, 3)
    return b


def sargon() -> Landmark:
    b = Landmark("sargon_golden_bazaar", (15, 10, 13), ["minecraft:smooth_sandstone", "minecraft:orange_terracotta", "minecraft:gold_block", "minecraft:emerald_block"])
    b.cuboid((1, 0, 1), (13, 0, 11), 0); b.cuboid((2, 1, 2), (12, 4, 10), 1); b.cuboid((4, 5, 3), (10, 7, 9), 0); b.frame(8, 5, 2); b.column(7, 6, 8, 9, 2); b.block(7, 5, 6, 3)
    return b


def sami() -> Landmark:
    b = Landmark("sami_cyclops_altar", (11, 11, 11), ["minecraft:packed_ice", "minecraft:blue_ice", "minecraft:amethyst_block", "minecraft:soul_soil"])
    b.cuboid((1, 0, 1), (9, 0, 9), 3); b.frame(1, 2, 0); b.frame(2, 3, 0); b.cuboid((4, 1, 4), (6, 7, 6), 1); b.block(5, 8, 5, 2); b.column(1, 1, 1, 6, 0); b.column(9, 9, 1, 6, 0)
    return b


def victoria() -> Landmark:
    b = Landmark("victoria_defence_cannon", (15, 10, 9), ["minecraft:stone_bricks", "minecraft:iron_block", "minecraft:polished_blackstone", "minecraft:redstone_block"])
    b.cuboid((1, 0, 1), (13, 2, 7), 0); b.cuboid((3, 3, 2), (10, 6, 6), 1); b.cuboid((9, 5, 3), (14, 7, 5), 2); b.cuboid((2, 3, 3), (3, 5, 5), 3)
    return b


def ursus() -> Landmark:
    b = Landmark("ursus_sarcophagus_station", (13, 9, 11), ["minecraft:polished_diorite", "minecraft:iron_block", "minecraft:packed_ice", "minecraft:redstone_lamp"])
    b.cuboid((1, 0, 1), (11, 0, 9), 2); b.cuboid((2, 1, 2), (10, 5, 8), 0); b.cuboid((4, 2, 3), (8, 4, 7), 1); b.cuboid((3, 6, 3), (9, 7, 7), 1); b.block(6, 8, 5, 3)
    return b


def kjerag() -> Landmark:
    b = Landmark("kjerag_karlan_monastery", (15, 11, 13), ["minecraft:stone_bricks", "minecraft:spruce_planks", "minecraft:snow_block", "minecraft:blue_ice"])
    b.cuboid((1, 0, 1), (13, 0, 11), 0); b.cuboid((2, 1, 2), (12, 6, 10), 1); b.cuboid((1, 7, 1), (13, 7, 11), 2); b.cuboid((4, 8, 3), (10, 9, 9), 1); b.column(7, 6, 8, 10, 3)
    return b


def siracusa() -> Landmark:
    b = Landmark("siracusa_family_court", (15, 11, 11), ["minecraft:bricks", "minecraft:polished_deepslate", "minecraft:iron_bars", "minecraft:mossy_stone_bricks"])
    b.cuboid((1, 0, 1), (13, 0, 9), 1); b.cuboid((2, 1, 2), (12, 7, 8), 0); b.cuboid((4, 2, 1), (10, 6, 1), 2); b.cuboid((1, 8, 1), (13, 9, 9), 1); b.column(7, 5, 1, 10, 3)
    return b


def yan() -> Landmark:
    b = Landmark("yan_yumen_beacon", (11, 15, 11), ["minecraft:tuff_bricks", "minecraft:dark_oak_planks", "minecraft:red_terracotta", "minecraft:shroomlight"])
    b.cuboid((1, 0, 1), (9, 1, 9), 0); b.cuboid((3, 2, 3), (7, 11, 7), 0); b.cuboid((2, 5, 2), (8, 6, 8), 1); b.cuboid((1, 11, 1), (9, 12, 9), 2); b.block(5, 13, 5, 3); b.block(5, 14, 5, 3)
    return b


def iberia() -> Landmark:
    b = Landmark("iberia_eye_lighthouse", (11, 16, 11), ["minecraft:stone_bricks", "minecraft:smooth_quartz", "minecraft:red_concrete", "minecraft:sea_lantern"])
    b.cuboid((1, 0, 1), (9, 1, 9), 0); b.cuboid((3, 2, 3), (7, 12, 7), 1); b.cuboid((2, 6, 2), (8, 7, 8), 2); b.frame(13, 2, 0); b.cuboid((4, 13, 4), (6, 14, 6), 3); b.block(5, 15, 5, 2)
    return b


def aegir_observatory() -> Landmark:
    b = Landmark("aegir_abyssal_observatory", (13, 9, 13), ["minecraft:prismarine_bricks", "minecraft:cyan_stained_glass", "minecraft:sea_lantern", "minecraft:copper_block"])
    b.cuboid((1, 0, 1), (11, 1, 11), 0); b.frame(2, 2, 0); b.frame(3, 3, 1); b.frame(4, 4, 1); b.frame(5, 5, 1); b.cuboid((5, 2, 5), (7, 7, 7), 3); b.block(6, 8, 6, 2)
    return b


def bolivar_checkpoint() -> Landmark:
    b = Landmark("bolivar_race_checkpoint", (15, 8, 7), ["minecraft:orange_concrete", "minecraft:white_concrete", "minecraft:yellow_concrete", "minecraft:redstone_lamp"])
    b.cuboid((0, 0, 1), (14, 0, 5), 1); b.column(2, 1, 1, 6, 0); b.column(12, 5, 1, 6, 0); b.cuboid((2, 5, 1), (12, 6, 5), 2); b.cuboid((5, 1, 1), (9, 1, 5), 0); b.block(7, 7, 3, 3)
    return b


def higashi_watchtower() -> Landmark:
    b = Landmark("higashi_sokogawa_watchtower", (9, 14, 9), ["minecraft:deepslate_bricks", "minecraft:dark_oak_planks", "minecraft:red_nether_bricks", "minecraft:soul_lantern"])
    b.cuboid((1, 0, 1), (7, 1, 7), 0); b.cuboid((3, 2, 3), (5, 10, 5), 1); b.cuboid((1, 10, 1), (7, 11, 7), 2); b.column(4, 4, 12, 13, 0); b.block(4, 9, 2, 3)
    return b


def durin_water_park() -> Landmark:
    b = Landmark("durin_water_park", (15, 8, 13), ["minecraft:cyan_concrete", "minecraft:yellow_concrete", "minecraft:light_blue_stained_glass", "minecraft:sea_lantern"])
    b.cuboid((1, 0, 1), (13, 1, 11), 0); b.cuboid((3, 2, 3), (11, 2, 9), 2); b.column(2, 2, 2, 6, 1); b.column(12, 10, 2, 6, 1)
    for i in range(8): b.block(3 + i, 6 - i // 2, 6, 1)
    b.cuboid((5, 2, 5), (9, 2, 7), 3)
    return b


def columbia_outpost() -> Landmark:
    b = Landmark("columbia_prison_outpost", (13, 10, 13), ["minecraft:iron_block", "minecraft:iron_bars", "minecraft:smooth_stone", "minecraft:redstone_lamp"])
    b.cuboid((1, 0, 1), (11, 0, 11), 2); b.frame(1, 1, 0); b.frame(2, 1, 1); b.frame(3, 1, 1); b.cuboid((4, 1, 4), (8, 5, 8), 0); b.cuboid((5, 2, 3), (7, 4, 3), 1); b.column(2, 2, 4, 9, 0); b.block(2, 8, 2, 3)
    return b


def kazimierz_monument() -> Landmark:
    b = Landmark("kazimierz_knight_monument", (11, 13, 11), ["minecraft:stone_bricks", "minecraft:polished_andesite", "minecraft:gold_block", "minecraft:iron_block"])
    b.cuboid((1, 0, 1), (9, 1, 9), 0); b.cuboid((3, 2, 3), (7, 4, 7), 1); b.column(5, 5, 5, 10, 3); b.cuboid((3, 8, 5), (7, 8, 5), 3); b.block(5, 11, 5, 2); b.block(5, 12, 5, 2)
    return b


def kazdel_camp() -> Landmark:
    b = Landmark("kazdel_sarkaz_camp", (15, 8, 13), ["minecraft:black_wool", "minecraft:spruce_planks", "minecraft:soul_soil", "minecraft:crying_obsidian"])
    b.cuboid((1, 0, 1), (13, 0, 11), 2)
    for cx, cz in ((4, 4), (10, 4), (7, 9)):
        b.cuboid((cx - 2, 1, cz - 2), (cx + 2, 1, cz + 2), 1); b.column(cx, cz, 2, 6, 1)
        for offset in range(-2, 3): b.block(cx + offset, 4 - abs(offset) // 2, cz, 0)
    b.block(7, 1, 6, 3)
    return b


def laterano_chapel() -> Landmark:
    b = Landmark("laterano_ambrosius_chapel", (13, 12, 11), ["minecraft:smooth_quartz", "minecraft:calcite", "minecraft:yellow_stained_glass", "minecraft:gold_block"])
    b.cuboid((1, 0, 1), (11, 1, 9), 1); b.cuboid((2, 2, 2), (10, 7, 8), 0); b.cuboid((1, 8, 1), (11, 9, 9), 0); b.cuboid((5, 3, 1), (7, 6, 1), 2); b.column(6, 5, 9, 11, 3)
    return b


def leithanien_hall() -> Landmark:
    b = Landmark("leithanien_concert_hall", (15, 11, 13), ["minecraft:dark_oak_planks", "minecraft:amethyst_block", "minecraft:polished_blackstone_bricks", "minecraft:white_concrete"])
    b.cuboid((1, 0, 1), (13, 1, 11), 2); b.cuboid((2, 2, 2), (12, 7, 10), 0); b.cuboid((1, 8, 1), (13, 9, 11), 3)
    for x in (3, 6, 9, 12): b.column(x, 1, 2, 7, 1)
    b.cuboid((4, 2, 8), (10, 3, 10), 1)
    return b


def rim_rail_depot() -> Landmark:
    b = Landmark("rim_billiton_rail_depot", (17, 8, 9), ["minecraft:deepslate_tiles", "minecraft:iron_block", "minecraft:cut_copper", "minecraft:redstone_block"])
    b.cuboid((0, 0, 1), (16, 0, 7), 0); b.cuboid((1, 1, 2), (15, 1, 2), 1); b.cuboid((1, 1, 6), (15, 1, 6), 1); b.cuboid((4, 2, 2), (12, 5, 6), 2); b.cuboid((2, 2, 3), (3, 4, 5), 3); b.column(14, 4, 2, 7, 1)
    return b


def minos_plaza() -> Landmark:
    b = Landmark("minos_heroes_plaza", (15, 8, 15), ["minecraft:smooth_sandstone", "minecraft:cut_sandstone", "minecraft:quartz_pillar", "minecraft:gold_block"])
    b.cuboid((1, 0, 1), (13, 0, 13), 0); b.frame(1, 2, 1)
    for x, z in ((3, 3), (11, 3), (3, 11), (11, 11)): b.column(x, z, 1, 6, 2)
    b.cuboid((5, 1, 5), (9, 2, 9), 1); b.column(7, 7, 3, 7, 3)
    return b


def sargon_well() -> Landmark:
    b = Landmark("sargon_long_spring_well", (11, 9, 11), ["minecraft:sandstone", "minecraft:cut_sandstone", "minecraft:dark_oak_planks", "minecraft:lapis_block"])
    b.cuboid((1, 0, 1), (9, 0, 9), 0); b.frame(1, 3, 1); b.cuboid((4, 1, 4), (6, 3, 6), 3); b.column(2, 5, 2, 7, 2); b.column(8, 5, 2, 7, 2); b.cuboid((2, 7, 3), (8, 8, 7), 2)
    return b


def sami_lodge() -> Landmark:
    b = Landmark("sami_snowpriest_lodge", (13, 10, 11), ["minecraft:spruce_log", "minecraft:spruce_planks", "minecraft:snow_block", "minecraft:soul_lantern"])
    b.cuboid((1, 0, 1), (11, 0, 9), 2); b.cuboid((2, 1, 2), (10, 5, 8), 1)
    for x in range(1, 12):
        height = 8 - abs(6 - x) // 2
        b.block(x, height, 3, 0); b.block(x, height, 7, 0)
    b.column(3, 3, 1, 7, 0); b.column(9, 7, 1, 7, 0); b.block(6, 5, 1, 3)
    return b


def victoria_station() -> Landmark:
    b = Landmark("victoria_steam_station", (17, 10, 11), ["minecraft:bricks", "minecraft:iron_block", "minecraft:glass", "minecraft:copper_block"])
    b.cuboid((1, 0, 1), (15, 0, 9), 1); b.cuboid((2, 1, 2), (14, 6, 8), 0); b.cuboid((1, 7, 1), (15, 8, 9), 3); b.cuboid((6, 2, 1), (10, 5, 1), 2); b.column(13, 7, 7, 9, 3)
    return b


def ursus_mine_tower() -> Landmark:
    b = Landmark("ursus_northern_mine_tower", (11, 14, 11), ["minecraft:stone_bricks", "minecraft:iron_block", "minecraft:spruce_planks", "minecraft:redstone_lamp"])
    b.cuboid((1, 0, 1), (9, 0, 9), 0)
    for x, z in ((2, 2), (8, 2), (2, 8), (8, 8)): b.column(x, z, 1, 11, 1)
    for y in (3, 6, 9): b.frame(y, 2, 2)
    b.cuboid((2, 11, 2), (8, 12, 8), 2); b.block(5, 13, 5, 3)
    return b


def kjerag_plaza() -> Landmark:
    b = Landmark("kjerag_sacred_plaza", (15, 9, 15), ["minecraft:stone_bricks", "minecraft:snow_block", "minecraft:blue_ice", "minecraft:spruce_planks"])
    b.cuboid((1, 0, 1), (13, 0, 13), 1); b.frame(1, 2, 0); b.cuboid((5, 1, 5), (9, 3, 9), 0); b.column(7, 7, 4, 8, 2)
    for x, z in ((3, 3), (11, 3), (3, 11), (11, 11)): b.column(x, z, 1, 5, 3)
    return b


def siracusa_theatre() -> Landmark:
    b = Landmark("siracusa_family_theatre", (15, 12, 13), ["minecraft:bricks", "minecraft:dark_oak_planks", "minecraft:red_wool", "minecraft:gold_block"])
    b.cuboid((1, 0, 1), (13, 1, 11), 0); b.cuboid((2, 2, 2), (12, 8, 10), 1); b.cuboid((1, 9, 1), (13, 10, 11), 0); b.cuboid((4, 2, 8), (10, 4, 10), 2); b.cuboid((4, 5, 8), (10, 7, 8), 2); b.block(7, 11, 6, 3)
    return b


def yan_pavilion() -> Landmark:
    b = Landmark("yan_shangshu_pavilion", (13, 11, 13), ["minecraft:tuff_bricks", "minecraft:dark_oak_log", "minecraft:red_terracotta", "minecraft:lantern"])
    b.cuboid((1, 0, 1), (11, 0, 11), 0)
    for x, z in ((2, 2), (10, 2), (2, 10), (10, 10)): b.column(x, z, 1, 7, 1)
    b.cuboid((1, 7, 1), (11, 8, 11), 2); b.cuboid((3, 9, 3), (9, 9, 9), 2); b.block(6, 6, 6, 3); b.block(6, 10, 6, 3)
    return b


def iberia_chapel() -> Landmark:
    b = Landmark("iberia_saltwind_chapel", (13, 13, 11), ["minecraft:stone_bricks", "minecraft:calcite", "minecraft:cyan_stained_glass", "minecraft:oxidized_copper"])
    b.cuboid((1, 0, 1), (11, 1, 9), 0); b.cuboid((2, 2, 2), (10, 8, 8), 1); b.cuboid((1, 9, 1), (11, 10, 9), 3); b.cuboid((5, 3, 1), (7, 7, 1), 2); b.column(6, 5, 10, 12, 3)
    return b


BUILDERS = [
    aegir, aegir_observatory, bolivar, bolivar_checkpoint, higashi, higashi_watchtower,
    durin, durin_water_park, columbia, columbia_outpost, kazimierz, kazimierz_monument,
    kazdel, kazdel_camp, laterano, laterano_chapel, leithanien, leithanien_hall,
    rim_billiton, rim_rail_depot, minos, minos_plaza, sargon, sargon_well, sami, sami_lodge,
    victoria, victoria_station, ursus, ursus_mine_tower, kjerag, kjerag_plaza,
    siracusa, siracusa_theatre, yan, yan_pavilion, iberia, iberia_chapel,
]


if __name__ == "__main__":
    landmarks = [builder() for builder in BUILDERS]
    if len({landmark.path for landmark in landmarks}) != 38:
        raise ValueError("建筑 ID 必须恰好包含三十八个且不能重复")
    for landmark in landmarks:
        landmark.write()
    print(f"Generated {len(landmarks)} nation landmark templates in {OUTPUT}")

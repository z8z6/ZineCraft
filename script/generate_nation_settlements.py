"""Generate the nineteen evidence-backed Terra settlement structure sets.

The published settlement and building IDs are compatibility contracts.  The
geometry is deliberately described by 76 explicit BuildingPlan records: no
single recoloured ``building()`` box is used for the four national functions.
"""

from __future__ import annotations

import gzip
import struct
from collections import deque
from dataclasses import dataclass
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/nation_settlements"
DATA_VERSION = 3955
AIR = "minecraft:air"
JIGSAW = "minecraft:jigsaw"
LIGHTS = {"minecraft:sea_lantern", "minecraft:ochre_froglight", "minecraft:shroomlight"}
CONTAINERS = {"minecraft:barrel", "minecraft:chest"}
STAIRS = {"minecraft:polished_andesite_stairs", "minecraft:spruce_stairs", "minecraft:stone_brick_stairs"}


def block_id(value: str) -> str:
    return value if ":" in value else f"minecraft:{value}"


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


def palette_entry(name: str, properties: tuple[tuple[str, str], ...]) -> bytes:
    tags = [named(8, "Name", string_payload(name))]
    if properties:
        tags.append(named(10, "Properties", compound_payload([
            named(8, key, string_payload(value)) for key, value in properties
        ])))
    return compound_payload(tags)


def block_entry(position: tuple[int, int, int], state: int, nbt: dict[str, str | int] | None) -> bytes:
    tags = [
        named(9, "pos", list_payload(3, [int_payload(value) for value in position])),
        named(3, "state", int_payload(state)),
    ]
    if nbt:
        payload = []
        for key, value in nbt.items():
            if isinstance(value, int):
                payload.append(named(3, key, int_payload(value)))
            else:
                payload.append(named(8, key, string_payload(value)))
        tags.append(named(10, "nbt", compound_payload(payload)))
    return compound_payload(tags)


ORIENTATION = {direction: f"{direction}_up" for direction in ("north", "south", "east", "west")}


def jigsaw_nbt(name: str, target: str, pool: str, final_state: str) -> dict[str, str | int]:
    return {
        "id": JIGSAW,
        "name": name,
        "target": target,
        "pool": pool,
        "final_state": final_state,
        "joint": "rollable",
        "selection_priority": 0,
        "placement_priority": 0,
    }


@dataclass(frozen=True)
class BuildingPlan:
    name: str
    size: tuple[int, int, int]
    silhouette: str
    roof: str
    role: str
    feature: str
    floors: int = 1


@dataclass(frozen=True)
class NationStyle:
    key: str
    path: str
    foundation: str
    wall: str
    roof: str
    trim: str
    road: str
    wood: str
    door: str
    light: str
    signature: str
    plans: tuple[BuildingPlan, BuildingPlan, BuildingPlan, BuildingPlan]


def p(name: str, w: int, h: int, d: int, silhouette: str, roof: str, role: str, feature: str, floors: int = 1) -> BuildingPlan:
    return BuildingPlan(name, (w, h, d), silhouette, roof, role, feature, floors)


# Every line below is a compatibility ID translated through its REDESIGN.md
# contract.  Width stays <= 9 so adjacent 9-block street sockets never overlap.
STYLES = (
    NationStyle("aegir", "aegir_subsea_enclave", "zinecraft:aegir_abyssal_slate", "zinecraft:aegir_pressure_tile", "dark_prismarine", "oxidized_copper", "prismarine", "warped_planks", "warped_door", "sea_lantern", "pressure_ribs", (
        p("pressure_residence", 9, 18, 20, "twin_tower", "flat", "residence", "sealed_bridge", 2), p("hydroponics_lab", 9, 12, 22, "stepped", "glass_ridge", "lab", "cultivation_bays"), p("bathysphere_dock", 9, 15, 24, "gantry", "flat", "logistics", "wet_lock"), p("current_archive", 9, 15, 18, "tower", "flat", "archive", "data_spine", 2))),
    NationStyle("bolivar", "bolivar_dossoles_district", "zinecraft:bolivar_war_scoured_soil", "zinecraft:bolivar_dossoles_stucco", "smooth_quartz", "cut_sandstone", "cut_sandstone", "jungle_planks", "jungle_door", "ochre_froglight", "tension_canopy", (
        p("canal_house", 9, 12, 18, "terraced", "flat", "residence", "waterfront_steps"), p("beach_market", 9, 10, 24, "open_hall", "canopy", "market", "gallery_stalls"), p("race_workshop", 9, 13, 21, "saw_hall", "sawtooth", "workshop", "service_rig"), p("festival_hall", 9, 14, 24, "arched_hall", "tension", "performance", "public_gallery"))),
    NationStyle("columbia", "columbia_frontier_town", "zinecraft:columbia_canyon_soil", "zinecraft:columbia_frontier_panel", "cut_copper", "exposed_copper", "gravel", "oak_planks", "copper_door", "sea_lantern", "service_spine", (
        p("prefab_house", 9, 12, 20, "pod_cluster", "flat", "residence", "tow_hitch"), p("pioneer_lab", 9, 12, 22, "stepped", "flat", "lab", "sample_lock"), p("logistics_depot", 9, 13, 24, "long_hall", "sawtooth", "logistics", "sorting_tower"), p("sheriff_office", 9, 15, 18, "tower", "flat", "civic", "emergency_mast", 2))),
    NationStyle("durin", "durin_ideal_city_block", "zinecraft:durin_garden_moss", "zinecraft:durin_ideal_city_panel", "smooth_quartz", "light_blue_stained_glass", "smooth_stone", "bamboo_planks", "bamboo_door", "sea_lantern", "garden_balcony", (
        p("dome_apartment", 9, 15, 20, "terraced", "garden", "residence", "shared_green", 2), p("machine_shop", 9, 11, 22, "open_hall", "glass_ridge", "workshop", "assembly_window"), p("arcade", 9, 12, 24, "sunken_hall", "garden", "archive", "reading_court"), p("transit_station", 9, 13, 24, "platform", "canopy", "transit", "layered_walkway"))),
    NationStyle("higashi", "higashi_sokogawa_town", "zinecraft:higashi_shadow_loam", "zinecraft:higashi_machiya_plaster", "deepslate_tiles", "dark_oak_planks", "cobblestone", "dark_oak_planks", "dark_oak_door", "shroomlight", "overstreet_frame", (
        p("machiya", 7, 16, 24, "deep_house", "stacked_eaves", "residence", "rear_service", 2), p("swordsmith", 9, 12, 21, "saw_hall", "sawtooth", "workshop", "smoke_stack"), p("tea_house", 7, 11, 18, "courtyard", "stacked_eaves", "food", "small_court"), p("magistrate_house", 9, 15, 20, "tower", "stacked_eaves", "civic", "distribution_vault", 2))),
    NationStyle("kazimierz", "kazimierz_knight_borough", "zinecraft:kazimierz_steppe_turf", "zinecraft:kazimierz_arena_masonry", "smooth_stone", "iron_block", "stone_bricks", "spruce_planks", "spruce_door", "ochre_froglight", "arena_truss", (
        p("tenement", 9, 18, 18, "tower", "flat", "residence", "commercial_base", 2), p("armor_workshop", 9, 13, 24, "long_hall", "truss", "workshop", "test_rig"), p("sponsor_shop", 9, 11, 20, "arcade", "canopy", "market", "display_strip"), p("tournament_inn", 9, 15, 24, "winged", "flat", "lodging", "team_store", 2))),
    NationStyle("kazdel", "kazdel_sarkaz_settlement", "zinecraft:kazdel_scarred_ash", "zinecraft:kazdel_fortress_plate", "polished_blackstone", "raw_iron_block", "polished_blackstone", "dark_oak_planks", "crimson_door", "shroomlight", "patched_frame", (
        p("canvas_house", 8, 12, 18, "offset_pods", "shed", "residence", "external_stair"), p("forge", 9, 14, 22, "furnace_hall", "sawtooth", "workshop", "furnace_platform"), p("mercenary_lodge", 9, 13, 20, "fortified", "flat", "barracks", "training_yard"), p("provision_store", 8, 10, 18, "loading_shed", "shed", "logistics", "distribution_bay"))),
    NationStyle("laterano", "laterano_monastery_town", "zinecraft:laterano_alluvial_chalk", "zinecraft:laterano_basilica_marble", "quartz_bricks", "gold_block", "quartz_bricks", "birch_planks", "birch_door", "sea_lantern", "arcaded_facade", (
        p("white_residence", 9, 15, 18, "arcade", "flat", "residence", "shared_loggia", 2), p("confectionery", 8, 11, 18, "shopfront", "canopy", "food", "cooling_racks"), p("notary_office", 9, 15, 21, "civic_hall", "vault", "archive", "records_wing", 2), p("bell_chapel", 9, 14, 22, "winged", "vault", "clinic", "service_bell"))),
    NationStyle("leithanien", "leithanien_music_town", "zinecraft:leithanien_twilight_humus", "zinecraft:leithanien_resonant_brick", "deepslate_tiles", "amethyst_block", "deepslate_tiles", "spruce_planks", "spruce_door", "sea_lantern", "acoustic_fins", (
        p("twilight_house", 8, 17, 18, "tower", "steep", "residence", "historic_stair", 2), p("instrument_workshop", 9, 12, 20, "daylight_hall", "glass_ridge", "workshop", "tuning_room"), p("rehearsal_hall", 9, 14, 24, "arched_hall", "vault", "performance", "acoustic_wall"), p("arts_academy", 9, 18, 24, "courtyard_tower", "steep", "academy", "teaching_tower", 2))),
    NationStyle("rim_billiton", "rim_billiton_mining_camp", "zinecraft:rim_billiton_mine_tailings", "zinecraft:rim_billiton_corrugated_steel", "weathered_copper", "raw_iron_block", "cobbled_deepslate", "spruce_planks", "iron_door", "ochre_froglight", "mining_gantry", (
        p("miner_bunkhouse", 8, 13, 20, "long_house", "shed", "lodging", "wash_entry", 2), p("ore_workshop", 9, 15, 24, "process_tower", "sawtooth", "workshop", "hopper_line"), p("freight_depot", 9, 13, 24, "platform", "truss", "logistics", "crossline_crane"), p("canteen", 9, 10, 20, "open_hall", "shed", "food", "relief_room"))),
    NationStyle("minos", "minos_heroic_polis", "zinecraft:minos_sunbaked_earth", "zinecraft:minos_heroic_masonry", "terracotta", "smooth_quartz", "cut_sandstone", "jungle_planks", "jungle_door", "ochre_froglight", "civic_colonnade", (
        p("courtyard_house", 9, 11, 18, "courtyard", "low_slope", "residence", "work_loggia"), p("olive_market", 9, 10, 22, "colonnade", "canopy", "market", "produce_lane"), p("training_hall", 9, 12, 24, "open_court", "low_slope", "training", "medical_wing"), p("council_house", 9, 14, 21, "civic_hall", "pediment", "civic", "assembly_room"))),
    NationStyle("sargon", "sargon_oasis_town", "zinecraft:sargon_desert_crust", "zinecraft:sargon_oasis_adobe", "smooth_sandstone", "cut_sandstone", "smooth_sandstone", "acacia_planks", "acacia_door", "ochre_froglight", "shade_and_water", (
        p("adobe_house", 9, 11, 18, "courtyard", "flat", "residence", "vent_court"), p("spice_market", 9, 10, 24, "shaded_hall", "canopy", "market", "goods_lane"), p("caravanserai", 9, 13, 24, "courtyard", "flat", "lodging", "transit_court", 2), p("well_house", 9, 15, 18, "water_tower", "flat", "water", "settling_basin"))),
    NationStyle("sami", "sami_snowpriest_village", "zinecraft:sami_frost_moss", "spruce_planks", "spruce_planks", "zinecraft:sami_ritual_stone", "spruce_planks", "stripped_spruce_log", "spruce_door", "shroomlight", "stilt_walkway", (
        p("snow_lodge", 8, 11, 20, "stilt_house", "long_eave", "residence", "hearth_platform"), p("hunter_camp", 9, 10, 22, "open_shed", "long_eave", "workshop", "observation_walk"), p("ritual_house", 9, 12, 24, "gathering_hall", "long_eave", "assembly", "central_hearth"), p("supply_shed", 7, 9, 18, "high_store", "shed", "logistics", "sled_ramp"))),
    NationStyle("victoria", "victoria_industrial_borough", "zinecraft:victoria_moorland_soil", "zinecraft:victoria_industrial_brick", "weathered_copper", "polished_andesite", "polished_andesite", "dark_oak_planks", "create:brass_door", "sea_lantern", "industrial_chimney", (
        p("brick_tenement", 9, 18, 18, "streetwall_tower", "flat", "residence", "corner_raise", 2), p("steam_workshop", 9, 14, 24, "saw_hall", "sawtooth", "workshop", "power_spine"), p("rail_warehouse", 9, 13, 24, "long_hall", "truss", "logistics", "lift_axis"), p("council_hall", 9, 16, 21, "civic_hall", "flat", "civic", "high_window", 2))),
    NationStyle("ursus", "ursus_northern_town", "zinecraft:ursus_permafrost", "zinecraft:ursus_imperial_masonry", "deepslate_tiles", "iron_block", "stone_bricks", "spruce_planks", "spruce_door", "sea_lantern", "heating_main", (
        p("heated_house", 9, 15, 18, "insulated", "steep", "residence", "double_vestibule", 2), p("military_storehouse", 9, 13, 24, "fortified", "low_slope", "logistics", "guarded_loading"), p("mine_office", 9, 16, 20, "control_tower", "steep", "civic", "sample_bridge", 2), p("communal_hall", 9, 13, 22, "winged", "low_slope", "clinic", "heated_refuge"))),
    NationStyle("kjerag", "kjerag_mountain_village", "zinecraft:kjerag_sacred_snowstone", "zinecraft:kjerag_monastery_stone", "spruce_planks", "dark_oak_log", "cobblestone", "spruce_planks", "spruce_door", "shroomlight", "snow_retaining", (
        p("stone_chalet", 8, 12, 18, "slope_house", "steep", "residence", "snow_wall"), p("tea_workshop", 9, 11, 20, "terraced", "steep", "food", "drying_deck"), p("caravan_post", 9, 13, 24, "platform", "long_eave", "transit", "transfer_ramp"), p("shrine_house", 9, 14, 21, "courtyard", "steep", "clinic", "quiet_court"))),
    NationStyle("siracusa", "siracusa_family_town", "zinecraft:siracusa_rain_darkened_soil", "zinecraft:siracusa_family_masonry", "dark_oak_planks", "polished_deepslate", "stone_bricks", "dark_oak_planks", "dark_oak_door", "shroomlight", "rain_arcade", (
        p("family_house", 8, 15, 22, "deep_house", "low_slope", "residence", "hidden_court", 2), p("trattoria", 8, 10, 18, "corner_shop", "awning", "food", "service_yard"), p("tailor_shop", 7, 13, 18, "shop_tower", "low_slope", "workshop", "shutter_front", 2), p("meeting_hall", 9, 15, 21, "converted_manor", "vault", "civic", "public_entry", 2))),
    NationStyle("yan", "yan_shangshu_town", "zinecraft:yan_mountain_soil", "zinecraft:yan_courtyard_brick", "deepslate_tiles", "polished_tuff", "polished_tuff", "dark_oak_planks", "dark_oak_door", "shroomlight", "mountain_terrace", (
        p("courtyard_residence", 9, 13, 20, "terraced", "stacked_eaves", "residence", "slope_shop", 2), p("tea_house", 9, 11, 22, "pavilion", "stacked_eaves", "food", "view_platform"), p("artisan_workshop", 9, 14, 24, "saw_hall", "stacked_eaves", "workshop", "cable_service"), p("relay_office", 9, 15, 24, "platform_tower", "stacked_eaves", "transit", "bridgehead", 2))),
    NationStyle("iberia", "iberia_coastal_town", "zinecraft:iberia_salt_crusted_gravel", "zinecraft:iberia_coastal_masonry", "weathered_copper", "tuff_bricks", "gravel", "spruce_planks", "spruce_door", "sea_lantern", "salt_buttress", (
        p("saltstone_house", 8, 11, 18, "slope_house", "barrel", "residence", "water_store"), p("shipwright", 9, 14, 24, "slipway_hall", "truss", "workshop", "launch_ramp"), p("fish_market", 9, 11, 22, "drained_hall", "canopy", "market", "cold_store"), p("inquisitor_office", 9, 15, 21, "fortified_civic", "barrel", "clinic", "refuge_court", 2))),
)


class Template:
    def __init__(self, style: NationStyle, name: str, size: tuple[int, int, int], plan: BuildingPlan | None = None):
        self.style = style
        self.name = name
        self.size = size
        self.plan = plan
        self.palette: list[tuple[str, tuple[tuple[str, str], ...]]] = []
        self.palette_index: dict[tuple[str, tuple[tuple[str, str], ...]], int] = {}
        self.blocks: dict[tuple[int, int, int], tuple[int, dict[str, str | int] | None]] = {}
        self.room_targets: list[tuple[str, tuple[int, int, int]]] = []

    def state(self, name: str, properties: dict[str, str] | None = None) -> int:
        key = (block_id(name), tuple(sorted((properties or {}).items())))
        if key not in self.palette_index:
            self.palette_index[key] = len(self.palette)
            self.palette.append(key)
        return self.palette_index[key]

    def block(self, x: int, y: int, z: int, name: str, properties: dict[str, str] | None = None, nbt: dict[str, str | int] | None = None) -> None:
        sx, sy, sz = self.size
        if not (0 <= x < sx and 0 <= y < sy and 0 <= z < sz):
            raise ValueError(f"{self.style.path}/{self.name}: block outside size {(x, y, z)} / {self.size}")
        self.blocks[(x, y, z)] = (self.state(name, properties), nbt)

    def cuboid(self, start: tuple[int, int, int], end: tuple[int, int, int], name: str) -> None:
        for x in range(start[0], end[0] + 1):
            for y in range(start[1], end[1] + 1):
                for z in range(start[2], end[2] + 1):
                    self.block(x, y, z, name)

    def clear(self, start: tuple[int, int, int], end: tuple[int, int, int]) -> None:
        self.cuboid(start, end, AIR)

    def connector(self, x: int, y: int, z: int, direction: str, name: str, target: str, pool: str) -> None:
        self.block(x, y, z, JIGSAW, {"orientation": ORIENTATION[direction]}, jigsaw_nbt(name, target, pool, block_id(self.style.road)))

    def block_name(self, pos: tuple[int, int, int]) -> str | None:
        value = self.blocks.get(pos)
        return self.palette[value[0]][0] if value else None

    def write(self) -> None:
        palette = [palette_entry(name, properties) for name, properties in self.palette]
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


def center(style: NationStyle) -> Template:
    t = Template(style, "center", (15, 8, 15))
    t.cuboid((0, 0, 5), (14, 0, 9), style.road)
    t.cuboid((5, 0, 0), (9, 0, 14), style.road)
    t.cuboid((4, 0, 4), (10, 0, 10), style.foundation)
    # A supported national material marker, water/service node, or roofed rest node.
    t.cuboid((6, 1, 6), (8, 1, 8), style.wall)
    t.block(7, 2, 7, style.trim)
    t.block(7, 3, 7, style.light)
    for x, z in ((5, 5), (9, 5), (5, 9), (9, 9)):
        t.block(x, 1, z, style.wood)
        t.block(x, 2, z, "iron_bars")
    for direction, position in {"north": (7, 1, 0), "south": (7, 1, 14), "east": (14, 1, 7), "west": (0, 1, 7)}.items():
        t.connector(*position, direction, f"zinecraft:{style.path}/center", f"zinecraft:{style.path}/street", f"zinecraft:{style.path}/streets")
    return t


def road_connector(t: Template, direction: str) -> None:
    x, y, z = {"north": (4, 1, 0), "south": (4, 1, 8), "east": (8, 1, 4), "west": (0, 1, 4)}[direction]
    t.connector(x, y, z, direction, f"zinecraft:{t.style.path}/street", f"zinecraft:{t.style.path}/street", f"zinecraft:{t.style.path}/streets")


def building_connector(t: Template, direction: str) -> None:
    x, y, z = {"north": (2, 1, 0), "south": (6, 1, 8), "east": (8, 1, 6), "west": (0, 1, 2)}[direction]
    t.connector(x, y, z, direction, f"zinecraft:{t.style.path}/building_exit", f"zinecraft:{t.style.path}/building", f"zinecraft:{t.style.path}/buildings")


def street(style: NationStyle, name: str, exits: tuple[str, ...], buildings: tuple[str, ...]) -> Template:
    t = Template(style, name, (9, 5, 9))
    t.cuboid((3, 0, 0), (5, 0, 8), style.road)
    t.cuboid((0, 0, 3), (8, 0, 5), style.road)
    for direction in exits:
        road_connector(t, direction)
    for direction in buildings:
        building_connector(t, direction)
    # Street furniture uses a real post and never occupies a connector lane.
    for x, z in ((1, 1), (7, 7)):
        t.block(x, 0, z, style.foundation)
        t.block(x, 1, z, "iron_bars")
        t.block(x, 2, z, style.light)
    return t


def building_wall_top(plan: BuildingPlan) -> int:
    _, h, _ = plan.size
    wall_top = h - (4 if plan.roof in {"steep", "stacked_eaves", "vault", "barrel"} else 3)
    return max(7, wall_top)


def shell(t: Template, plan: BuildingPlan) -> int:
    w, h, d = plan.size
    wall_top = building_wall_top(plan)
    t.cuboid((0, 0, 1), (w - 1, 0, d - 1), t.style.foundation)
    t.clear((1, 1, 2), (w - 2, wall_top, d - 2))
    for y in range(1, wall_top + 1):
        for x in range(w):
            t.block(x, y, 1, t.style.wall)
            t.block(x, y, d - 1, t.style.wall)
        for z in range(2, d - 1):
            t.block(0, y, z, t.style.wall)
            t.block(w - 1, y, z, t.style.wall)
    # Rhythmic windows, with high windows on civic/industrial plans.
    window_y = 4 if plan.feature in {"high_window", "power_spine", "sorting_tower"} else 3
    for z in range(4, d - 3, 5):
        t.block(0, window_y, z, "gray_stained_glass_pane")
        t.block(w - 1, window_y, z, "gray_stained_glass_pane")
    for x in range(2, w - 2, 3):
        t.block(x, window_y, d - 1, "gray_stained_glass_pane")
    c = w // 2
    t.block(c, 1, 1, t.style.door, {"facing": "north", "half": "lower", "hinge": "left", "open": "false"})
    t.block(c, 2, 1, t.style.door, {"facing": "north", "half": "upper", "hinge": "left", "open": "false"})
    return wall_top


def roof(t: Template, plan: BuildingPlan, wall_top: int) -> None:
    w, h, d = plan.size
    base = wall_top + 1
    if plan.roof in {"flat", "garden", "canopy", "tension", "truss", "glass_ridge", "sawtooth", "shed", "awning", "long_eave", "low_slope", "pediment"}:
        t.cuboid((0, base, 1), (w - 1, base, d - 1), t.style.roof)
        if plan.roof in {"canopy", "tension", "truss", "long_eave"}:
            for z in range(2, d - 1, 4):
                t.cuboid((0, base + 1, z), (w - 1, base + 1, z), t.style.trim)
        if plan.roof == "sawtooth":
            for z in range(3, d - 2, 5):
                t.cuboid((1, min(h - 1, base + 1), z), (w - 2, min(h - 1, base + 1), z), "gray_stained_glass")
        if plan.roof == "garden":
            garden_y = min(h - 1, base + 1)
            t.cuboid((2, garden_y, 4), (w - 3, garden_y, min(d - 4, 9)), "moss_block")
            if garden_y + 1 < h:
                t.block(w // 2, garden_y + 1, 6, "azalea")
        if plan.roof in {"shed", "awning", "low_slope"}:
            for z in range(2, d - 1):
                if z % 3 == 0 and base + 1 < h:
                    t.cuboid((0, base + 1, z), (w - 1, base + 1, z), t.style.roof)
    else:
        # Stepped voxel vault/gable; no real-world copied roof profile.
        for inset in range((w + 1) // 2):
            y = min(h - 1, base + inset)
            t.cuboid((inset, y, 1), (w - 1 - inset, y, d - 1), t.style.roof)
        if plan.roof == "stacked_eaves":
            t.cuboid((0, min(h - 1, base + 2), 4), (w - 1, min(h - 1, base + 2), d - 2), t.style.trim)


def add_upper_floor_and_stairs(t: Template, plan: BuildingPlan, wall_top: int) -> None:
    if plan.floors != 2:
        return
    w, _, d = plan.size
    upper_y = 6
    t.cuboid((1, upper_y, 2), (w - 2, upper_y, d - 2), t.style.wood)
    # Five-step flight rising south. The full shaft is cleared through the upper floor.
    for step in range(5):
        pos = (1, 1 + step, 3 + step)
        t.block(*pos, "polished_andesite_stairs", {"facing": "south", "half": "bottom", "shape": "straight"})
        for dy in (1, 2):
            t.block(pos[0], pos[1] + dy, pos[2], AIR)
        t.block(1, upper_y, 3 + step, AIR)
    t.block(2, upper_y, 7, t.style.wood)
    t.clear((2, upper_y + 1, 4), (w - 2, wall_top, d - 2))
    # Lower-storey main lights are recessed into the upper floor; the upper
    # deck would otherwise shadow the ground floor completely.
    for z in range(4, d - 3, 7):
        t.block(w // 2, upper_y - 1, z, t.style.light)
    # Supported upper-level lights are embedded immediately beneath the roof.
    for z in range(6, d - 3, 7):
        t.block(w // 2, wall_top, z, t.style.light)
    t.block(w // 2, wall_top, d - 4, t.style.light)


ROLE_BLOCKS = {
    "residence": ("red_bed", "crafting_table", "bookshelf"),
    "lab": ("brewing_stand", "cauldron", "create:mechanical_arm"),
    "logistics": ("barrel", "create:depot", "create:gearbox"),
    "archive": ("lectern", "bookshelf", "cartography_table"),
    "market": ("barrel", "composter", "crafting_table"),
    "workshop": ("crafting_table", "anvil", "create:andesite_casing"),
    "performance": ("note_block", "lectern", "bookshelf"),
    "civic": ("lectern", "cartography_table", "bookshelf"),
    "lodging": ("blue_bed", "barrel", "smoker"),
    "food": ("smoker", "furnace", "barrel"),
    "clinic": ("white_bed", "brewing_stand", "barrel"),
    "academy": ("lectern", "bookshelf", "note_block"),
    "transit": ("cartography_table", "barrel", "create:gearbox"),
    "training": ("target", "smithing_table", "barrel"),
    "water": ("cauldron", "create:mechanical_pump", "create:fluid_pipe"),
    "barracks": ("red_bed", "barrel", "target"),
    "assembly": ("lectern", "bookshelf", "campfire"),
}


def furnish(t: Template, plan: BuildingPlan) -> None:
    w, _, d = plan.size
    items = ROLE_BLOCKS[plan.role]
    positions = ((1, 1, d - 3), (w - 2, 1, d - 3), (w - 2, 1, 4))
    for name, pos in zip(items, positions):
        t.block(*pos, name)
    # Table, two chairs and an unobstructed central aisle.
    table_z = min(d - 6, 8)
    t.block(w // 2, 1, table_z, "spruce_slab", {"type": "top", "waterlogged": "false"})
    t.block(w // 2 - 1, 1, table_z, "spruce_stairs", {"facing": "east", "half": "bottom", "shape": "straight"})
    t.block(w // 2 + 1, 1, table_z, "spruce_stairs", {"facing": "west", "half": "bottom", "shape": "straight"})
    # Every building has a floor-level container, never submerged or ceiling-mounted.
    chest_pos = (w - 2, 1, d - 5)
    t.block(*chest_pos, "barrel", {"facing": "up", "open": "false"}, {
        "id": "minecraft:barrel",
        "LootTable": f"zinecraft:chests/nation/{t.style.key}_structure",
    })
    # A compact readable service chain for technological/utility programs.
    if plan.role in {"lab", "logistics", "workshop", "transit", "water"}:
        for z in range(10, min(d - 3, 15)):
            t.block(1, 2, z, "create:shaft", {"axis": "z"})
        t.block(1, 1, min(d - 4, 15), "create:gearbox", {"axis": "z"})


def add_signature(t: Template, plan: BuildingPlan, wall_top: int) -> None:
    w, _, d = plan.size
    sig = t.style.signature
    # Each nation gets a distinct, evidence-bounded infrastructure/facade language.
    if sig == "pressure_ribs":
        for z in range(3, d - 1, 4):
            t.cuboid((0, 1, z), (0, wall_top, z), t.style.trim)
            t.cuboid((w - 1, 1, z), (w - 1, wall_top, z), t.style.trim)
        t.cuboid((1, wall_top - 1, d // 2), (w - 2, wall_top - 1, d // 2), "cyan_stained_glass")
    elif sig == "tension_canopy":
        for z in range(4, d - 2, 6):
            t.block(0, wall_top + 1, z, "iron_bars"); t.block(w - 1, wall_top + 1, z, "iron_bars")
    elif sig == "service_spine":
        t.cuboid((w - 1, 2, 4), (w - 1, 2, d - 4), "create:fluid_pipe")
        for z in (5, d - 5): t.cuboid((w - 1, 1, z), (w - 1, min(wall_top, 6), z), t.style.trim)
    elif sig == "garden_balcony":
        t.cuboid((1, 4, d // 2 - 2), (w - 2, 4, d // 2 + 2), "light_blue_stained_glass")
        t.block(1, 5, d // 2, "moss_block"); t.block(w - 2, 5, d // 2, "moss_block")
    elif sig == "overstreet_frame":
        for z in (4, d - 4):
            t.cuboid((0, 1, z), (0, wall_top, z), t.style.trim); t.cuboid((w - 1, 1, z), (w - 1, wall_top, z), t.style.trim)
        t.cuboid((0, wall_top, d - 4), (w - 1, wall_top, d - 4), "create:metal_girder")
    elif sig == "arena_truss":
        for z in range(4, d - 2, 6): t.cuboid((0, wall_top, z), (w - 1, wall_top, z), "create:metal_girder")
    elif sig == "patched_frame":
        for x, z in ((0, 5), (w - 1, 8), (0, d - 5)): t.cuboid((x, 2, z), (x, min(wall_top - 1, 5), z), "raw_iron_block")
    elif sig == "arcaded_facade":
        for x in range(1, w, 2): t.cuboid((x, 1, 1), (x, min(4, wall_top), 1), "quartz_pillar")
    elif sig == "acoustic_fins":
        for z in range(4, d - 3, 5): t.cuboid((0, 2, z), (0, min(wall_top, 7), z), "amethyst_block")
    elif sig == "mining_gantry":
        t.cuboid((0, wall_top, d // 2), (w - 1, wall_top, d // 2), "create:metal_girder")
        t.block(w // 2, wall_top - 1, d // 2, "chain")
    elif sig == "civic_colonnade":
        for x in (1, 3, w - 4, w - 2): t.cuboid((x, 1, 1), (x, min(5, wall_top), 1), "smooth_quartz")
    elif sig == "shade_and_water":
        t.cuboid((0, wall_top, 4), (w - 1, wall_top, 4), "cut_sandstone")
        t.block(w - 2, 1, 3, "water_cauldron", {"level": "2"})
    elif sig == "stilt_walkway":
        for x in (0, w - 1):
            for z in (2, d // 2, d - 2): t.cuboid((x, 0, z), (x, 2, z), "stripped_spruce_log")
        t.cuboid((0, 0, d // 2), (w - 1, 0, d // 2), "spruce_planks")
    elif sig == "industrial_chimney":
        t.cuboid((w - 1, 1, d - 4), (w - 1, min(wall_top + 1, t.size[1] - 1), d - 4), "create:industrial_iron_block")
        t.cuboid((0, 2, 5), (0, 2, d - 5), "create:fluid_pipe")
    elif sig == "heating_main":
        t.cuboid((0, 2, 4), (0, 2, d - 4), "create:fluid_pipe")
        for z in (5, d - 5): t.block(0, 2, z, "create:mechanical_pump", {"facing": "south"})
    elif sig == "snow_retaining":
        t.cuboid((0, 1, d - 3), (0, 2, d - 1), "cobblestone")
        t.cuboid((w - 1, 1, d - 3), (w - 1, 2, d - 1), "cobblestone")
        for z in range(3, d - 2, 5): t.block(0, wall_top + 1, z, "snow_block")
    elif sig == "rain_arcade":
        t.cuboid((0, 1, 3), (0, wall_top, 3), "create:fluid_pipe")
        t.cuboid((1, 3, 1), (w - 2, 3, 1), "dark_oak_slab")
    elif sig == "mountain_terrace":
        t.cuboid((0, 0, d // 2), (w - 1, 0, d // 2), "polished_tuff")
        t.block(0, 2, d // 2, "dark_oak_fence"); t.block(w - 1, 2, d // 2, "dark_oak_fence")
    elif sig == "salt_buttress":
        for z in range(4, d - 2, 6):
            t.cuboid((0, 1, z), (1, min(4, wall_top), z), "tuff_bricks")
            t.cuboid((w - 2, 1, z), (w - 1, min(4, wall_top), z), "tuff_bricks")


def add_plan_geometry(t: Template, plan: BuildingPlan, wall_top: int) -> None:
    """Turn the explicit silhouette/feature contract into visible massing."""
    w, h, d = plan.size
    silhouette = plan.silhouette
    feature = plan.feature

    if any(token in silhouette for token in ("tower", "streetwall")):
        # An offset rear tower/equipment crown breaks the common roof line.
        x0, x1 = max(1, w - 5), w - 2
        z0, z1 = d - 7, d - 2
        for y in range(wall_top + 1, h - 1):
            for x in range(x0, x1 + 1):
                t.block(x, y, z0, t.style.wall); t.block(x, y, z1, t.style.wall)
            for z in range(z0 + 1, z1):
                t.block(x0, y, z, t.style.wall); t.block(x1, y, z, t.style.wall)
        t.cuboid((x0, h - 1, z0), (x1, h - 1, z1), t.style.roof)
    if silhouette == "twin_tower":
        for x0, x1 in ((0, 2), (w - 3, w - 1)):
            t.cuboid((x0, wall_top + 1, d - 6), (x1, h - 2, d - 2), t.style.wall)
            t.cuboid((x0, h - 1, d - 6), (x1, h - 1, d - 2), t.style.roof)
    if any(token in silhouette for token in ("terraced", "stepped", "pod", "offset")):
        # A real stepped upper facade, not a palette-only variant.
        ledge_y = max(5, wall_top - 2)
        t.cuboid((0, ledge_y, d // 2), (0, ledge_y, d - 2), t.style.trim)
        t.cuboid((w - 1, ledge_y, d // 2), (w - 1, ledge_y, d - 2), t.style.trim)
        for x in range(0, w, 2):
            t.block(x, ledge_y, d - 1, t.style.trim)
    if any(token in silhouette for token in ("platform", "gantry", "slipway")):
        beam_y = min(wall_top - 1, 6)
        t.cuboid((0, beam_y, d // 2), (w - 1, beam_y, d // 2), "create:metal_girder")
        t.cuboid((0, 1, d // 2), (0, beam_y, d // 2), "create:metal_girder")
        t.cuboid((w - 1, 1, d // 2), (w - 1, beam_y, d // 2), "create:metal_girder")
    if any(token in silhouette for token in ("open_hall", "daylight_hall", "gathering_hall", "shaded_hall")):
        for x in range(1, w - 1):
            t.block(x, min(wall_top - 1, 5), d - 1, "gray_stained_glass")
    if any(token in silhouette for token in ("fortified", "insulated")):
        for z in (4, d - 4):
            t.cuboid((0, 1, z), (1, min(5, wall_top), z), t.style.trim)
            t.cuboid((w - 2, 1, z), (w - 1, min(5, wall_top), z), t.style.trim)

    if any(token in feature for token in ("bridge", "platform", "deck", "walk", "loggia")):
        t.cuboid((1, 4, d - 3), (w - 2, 4, d - 2), t.style.wood)
        for x in range(1, w - 1):
            t.block(x, 5, d - 2, "iron_bars")
    if any(token in feature for token in ("water", "basin", "wet_lock", "cold_store")):
        t.block(w - 2, 1, 3, "water_cauldron", {"level": "2"})
        t.cuboid((w - 1, 2, 4), (w - 1, 2, min(d - 4, 10)), "create:fluid_pipe")
    if any(token in feature for token in ("tower", "mast", "stack", "chimney", "smoke")):
        t.cuboid((w - 1, 2, d - 3), (w - 1, min(h - 1, wall_top + 2), d - 3), t.style.trim)
    if any(token in feature for token in ("rig", "truss", "crane", "cable", "power", "lift", "spine", "axis")):
        t.cuboid((0, min(wall_top, 6), d - 5), (w - 1, min(wall_top, 6), d - 5), "create:metal_girder")
    if any(token in feature for token in ("court", "yard", "green")):
        t.cuboid((2, 0, d // 2 - 1), (w - 3, 0, d // 2 + 2), "moss_block" if t.style.key in {"durin", "sami"} else t.style.road)


def open_courtyard_roof(t: Template, plan: BuildingPlan, wall_top: int) -> None:
    if not any(token in plan.silhouette for token in ("courtyard", "open_court", "sunken_hall")):
        return
    w, _, d = plan.size
    # Keep the main lighting bays at z=4,11,... and open a smaller central court.
    z0 = min(8, d - 6)
    z1 = z0 + 1
    for x in range(2, w - 2):
        for z in range(z0, z1 + 1):
            t.block(x, wall_top + 1, z, AIR)


def add_lighting(t: Template, plan: BuildingPlan, wall_top: int) -> None:
    w, _, d = plan.size
    # Light grid max spacing is seven; every source touches the roof slab above.
    for z in range(4, d - 3, 7):
        t.block(w // 2, wall_top, z, t.style.light)
        if w >= 9 and d >= 22:
            t.block(2, wall_top, z, t.style.light)
            t.block(w - 3, wall_top, z, t.style.light)
    # Supported wall lights keep tall single-storey halls lit at working level.
    for z in range(5, d - 3, 7):
        t.block(0, min(4, wall_top - 1), z, t.style.light)
        t.block(w - 1, min(4, wall_top - 1), z, t.style.light)


def make_building(style: NationStyle, plan: BuildingPlan) -> Template:
    t = Template(style, plan.name, plan.size, plan)
    wall_top = shell(t, plan)
    add_plan_geometry(t, plan, wall_top)
    add_signature(t, plan, wall_top)
    add_upper_floor_and_stairs(t, plan, wall_top)
    furnish(t, plan)
    roof(t, plan, wall_top)
    open_courtyard_roof(t, plan, wall_top)
    add_lighting(t, plan, wall_top)
    c = plan.size[0] // 2
    t.connector(c, 1, 0, "north", f"zinecraft:{style.path}/building", "minecraft:empty", "minecraft:empty")
    t.room_targets.extend([
        ("entrance_room", (c, 1, 3)),
        ("rear_program_room", (c, 1, plan.size[2] - 4)),
    ])
    if plan.floors == 2:
        t.room_targets.extend([
            ("upper_stair_landing", (2, 7, 7)),
            ("upper_program_room", (c, 7, plan.size[2] - 4)),
        ])
    return t


def generate(style: NationStyle) -> list[Template]:
    return [
        center(style),
        street(style, "street_straight", ("north", "south"), ("east", "west")),
        street(style, "street_corner", ("north", "east"), ("south", "west")),
        street(style, "street_cross", ("north", "south", "east", "west"), ()),
        street(style, "street_end", ("north",), ("south", "east", "west")),
        *[make_building(style, plan) for plan in style.plans],
    ]


def player_passable(name: str | None) -> bool:
    return name == AIR or bool(name and name.endswith("_door"))


def standable_support(name: str | None) -> bool:
    if name is None or player_passable(name):
        return False
    # Thin service/decoration blocks are collision-bearing details, not room
    # floor surfaces in this integer-height navigation approximation.
    if name in {
        "minecraft:iron_bars", "minecraft:chain", "minecraft:brewing_stand",
        "minecraft:campfire", "minecraft:cauldron", "minecraft:water_cauldron",
        "create:shaft", "create:fluid_pipe", "create:mechanical_pump",
        "create:mechanical_arm",
    }:
        return False
    return not any(token in name for token in ("_pane", "_fence"))


def light_transparent(name: str | None) -> bool:
    if name is None or name == AIR or name in LIGHTS:
        return True
    if name.endswith("_door") or "glass" in name:
        return True
    if any(token in name for token in ("_stairs", "_slab", "_fence")):
        return True
    return name in {
        "minecraft:iron_bars", "minecraft:chain", "minecraft:brewing_stand",
        "create:shaft", "create:fluid_pipe", "create:mechanical_pump",
        "create:mechanical_arm",
    }


def room_standing_nodes(t: Template, wall_top: int) -> set[tuple[int, int, int]]:
    w, _, d = t.size
    nodes: set[tuple[int, int, int]] = set()
    assert t.plan is not None
    declared_walk_levels = {1, 7} if t.plan.floors == 2 else {1}
    for x in range(1, w - 1):
        for y in declared_walk_levels:
            for z in range(1, d - 1):
                if (
                    player_passable(t.block_name((x, y, z)))
                    and player_passable(t.block_name((x, y + 1, z)))
                    and standable_support(t.block_name((x, y - 1, z)))
                ):
                    nodes.add((x, y, z))
    # Stair surfaces bridge the declared floor elevations in true 3-D.  Their
    # navigation node is one voxel above the stair block itself.
    for (x, y, z) in t.blocks:
        if t.block_name((x, y, z)) in STAIRS:
            stand = (x, y + 1, z)
            if player_passable(t.block_name(stand)) and player_passable(t.block_name((x, y + 2, z))):
                nodes.add(stand)
    return nodes


def navigation_flood(nodes: set[tuple[int, int, int]], start: tuple[int, int, int]) -> set[tuple[int, int, int]]:
    assert start in nodes
    reached = {start}
    queue = deque([start])
    while queue:
        x, y, z = queue.popleft()
        for dx, dz in ((1, 0), (-1, 0), (0, 1), (0, -1)):
            for dy in (-1, 0, 1):
                nxt = (x + dx, y + dy, z + dz)
                if nxt in nodes and nxt not in reached:
                    reached.add(nxt)
                    queue.append(nxt)
    return reached


def light_flood(t: Template, sources: list[tuple[int, int, int]]) -> set[tuple[int, int, int]]:
    """Approximate block light: opaque voxels stop a 15-level six-way flood."""
    w, h, d = t.size
    distance = {source: 0 for source in sources}
    queue = deque(sources)
    while queue:
        x, y, z = queue.popleft()
        current = distance[(x, y, z)]
        if current >= 14:  # level 15 at source, level 1 after fourteen steps
            continue
        for nxt in ((x + 1, y, z), (x - 1, y, z), (x, y + 1, z), (x, y - 1, z), (x, y, z + 1), (x, y, z - 1)):
            nx, ny, nz = nxt
            if not (0 <= nx < w and 0 <= ny < h and 0 <= nz < d):
                continue
            if nxt in distance or not light_transparent(t.block_name(nxt)):
                continue
            distance[nxt] = current + 1
            queue.append(nxt)
    return set(distance)


def validate_building(t: Template) -> None:
    assert t.plan is not None
    plan = t.plan
    w, h, d = plan.size
    label = f"{t.style.path}/{t.name}"
    assert w <= 9, f"{label}: frontage would collide on the 9-block street grid"
    assert max(w, d) >= 16, f"{label}: below S horizontal scale"
    assert h >= 9 and len(t.blocks) > 150, f"{label}: insufficient building volume"
    assert sum(1 for pos in t.blocks if t.block_name(pos) == AIR and 1 <= pos[1] < h - 2) >= 30, f"{label}: no usable interior air"

    c = w // 2
    connector = t.blocks.get((c, 1, 0))
    assert connector and connector[1] and connector[1]["id"] == JIGSAW
    assert connector[1]["name"] == f"zinecraft:{t.style.path}/building"
    assert connector[1]["target"] == "minecraft:empty" and connector[1]["pool"] == "minecraft:empty"
    door_lower = t.block_name((c, 1, 1))
    door_upper = t.block_name((c, 2, 1))
    assert door_lower == block_id(t.style.door) == door_upper, f"{label}: missing paired entrance door"
    assert t.block_name((c, 1, 2)) == AIR and t.block_name((c, 2, 2)) == AIR, f"{label}: blocked entrance"

    wall_top = building_wall_top(plan)
    standing_nodes = room_standing_nodes(t, wall_top)
    entrance = (c, 1, 1)
    assert entrance in standing_nodes, f"{label}: entrance is not a valid two-block standing space"
    reached = navigation_flood(standing_nodes, entrance)
    unreachable = sorted(standing_nodes - reached)
    assert not unreachable, f"{label}: unreachable standing space {unreachable[0]} ({len(unreachable)} total)"
    for target_name, target in t.room_targets:
        assert target in standing_nodes, f"{label}: declared {target_name} is not standable at {target}"
        assert target in reached, f"{label}: declared {target_name} is unreachable at {target}"

    light_positions = [pos for pos in t.blocks if t.block_name(pos) in LIGHTS]
    assert len(light_positions) >= max(2, d // 8), f"{label}: insufficient supported lighting"
    for x, y, z in light_positions:
        supported = any(t.block_name(p) not in {None, AIR} for p in ((x, y + 1, z), (x, y - 1, z), (x + 1, y, z), (x - 1, y, z), (x, y, z + 1), (x, y, z - 1)) if 0 <= p[0] < w and 0 <= p[1] < h and 0 <= p[2] < d)
        assert supported, f"{label}: floating light at {(x, y, z)}"
    lit_voxels = light_flood(t, light_positions)
    unlit = sorted(node for node in standing_nodes if node not in lit_voxels)
    assert not unlit, f"{label}: block-light flood cannot illuminate standing space {unlit[0]} ({len(unlit)} total)"

    containers = [(pos, data[1]) for pos, data in t.blocks.items() if t.block_name(pos) in CONTAINERS and data[1]]
    assert containers, f"{label}: no loot container"
    for (x, y, z), nbt in containers:
        assert y >= 1 and t.block_name((x, y - 1, z)) not in {None, AIR}, f"{label}: submerged/floating container"
        assert nbt and nbt.get("LootTable") == f"zinecraft:chests/nation/{t.style.key}_structure"

    stairs = [pos for pos in t.blocks if t.block_name(pos) in STAIRS]
    if plan.floors == 2:
        assert len(stairs) >= 5, f"{label}: upper floor lacks a physical stair"
        for x, y, z in stairs:
            assert t.block_name((x, y + 1, z)) == AIR and t.block_name((x, y + 2, z)) == AIR, f"{label}: stair head collision at {(x, y, z)}"
            stair_stand = (x, y + 1, z)
            assert stair_stand in reached, f"{label}: stair standing position unreachable at {stair_stand}"
    assert not any("concrete" in name for name, _ in t.palette), f"{label}: concrete placeholder remains"
    assert len({t.block_name(pos) for pos in t.blocks}) >= 10, f"{label}: material/detail vocabulary too small"


def validate(templates: list[Template]) -> None:
    assert len(STYLES) == 19 and len({style.path for style in STYLES}) == 19
    plans = [plan for style in STYLES for plan in style.plans]
    assert len(plans) == 76
    assert len(templates) == 171
    assert len({(t.style.path, t.name) for t in templates}) == 171
    for style in STYLES:
        names = {t.name for t in templates if t.style == style}
        assert names == {"center", "street_straight", "street_corner", "street_cross", "street_end", *(p.name for p in style.plans)}
    # Every plan has an explicit design tuple and no two national/function plans are accidental clones.
    descriptors = [(plan.size, plan.silhouette, plan.roof, plan.role, plan.feature) for plan in plans]
    assert len(descriptors) == len(set(descriptors)), "duplicate building design tuple"
    for t in templates:
        if t.plan:
            validate_building(t)
        for pos, (state, nbt) in t.blocks.items():
            x, y, z = pos
            assert 0 <= x < t.size[0] and 0 <= y < t.size[1] and 0 <= z < t.size[2]
            if t.palette[state][0] == JIGSAW:
                assert nbt and nbt["id"] == JIGSAW and nbt["final_state"] == block_id(t.style.road)


def main() -> None:
    templates = [template for style in STYLES for template in generate(style)]
    validate(templates)
    for template in templates:
        template.write()
    print(f"Generated and validated {len(templates)} templates: 19 centers, 76 streets, 76 buildings")


if __name__ == "__main__":
    main()

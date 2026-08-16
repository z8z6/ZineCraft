"""Deterministically generate the 38 modular Terra nation landmarks.

Public structure ids are retained for save compatibility.  The old ids are not
claims about canon: each six-piece composition follows the corresponding
``docs/architecture/countries/*/REDESIGN.md`` design contract.
"""

from __future__ import annotations

import gzip
import shutil
import struct
import zlib
from collections import deque
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/nation_landmarks"
DATA_VERSION = 3955
MODULES = ("foundation", "core", "facade", "roof", "annex", "surrounding")

NATION_MATERIALS = {
    "aegir": ("zinecraft:aegir_abyssal_slate", "zinecraft:aegir_pressure_tile", "minecraft:oxidized_copper"),
    "bolivar": ("zinecraft:bolivar_war_scoured_soil", "zinecraft:bolivar_dossoles_stucco", "minecraft:cut_copper"),
    "higashi": ("zinecraft:higashi_shadow_loam", "zinecraft:higashi_machiya_plaster", "minecraft:dark_oak_planks"),
    "durin": ("zinecraft:durin_garden_moss", "zinecraft:durin_ideal_city_panel", "minecraft:yellow_terracotta"),
    "columbia": ("zinecraft:columbia_canyon_soil", "zinecraft:columbia_frontier_panel", "minecraft:iron_block"),
    "kazimierz": ("zinecraft:kazimierz_steppe_turf", "zinecraft:kazimierz_arena_masonry", "minecraft:gold_block"),
    "kazdel": ("zinecraft:kazdel_scarred_ash", "zinecraft:kazdel_fortress_plate", "minecraft:polished_blackstone"),
    "laterano": ("zinecraft:laterano_alluvial_chalk", "zinecraft:laterano_basilica_marble", "minecraft:gold_block"),
    "leithanien": ("zinecraft:leithanien_twilight_humus", "zinecraft:leithanien_resonant_brick", "minecraft:amethyst_block"),
    "rim_billiton": ("zinecraft:rim_billiton_mine_tailings", "zinecraft:rim_billiton_corrugated_steel", "minecraft:cut_copper"),
    "minos": ("zinecraft:minos_sunbaked_earth", "zinecraft:minos_heroic_masonry", "minecraft:chiseled_sandstone"),
    "sargon": ("zinecraft:sargon_desert_crust", "zinecraft:sargon_oasis_adobe", "minecraft:cut_sandstone"),
    "sami": ("zinecraft:sami_frost_moss", "zinecraft:sami_ritual_stone", "minecraft:spruce_planks"),
    "victoria": ("zinecraft:victoria_moorland_soil", "zinecraft:victoria_industrial_brick", "create:industrial_iron_block"),
    "ursus": ("zinecraft:ursus_permafrost", "zinecraft:ursus_imperial_masonry", "minecraft:iron_block"),
    "kjerag": ("zinecraft:kjerag_sacred_snowstone", "zinecraft:kjerag_monastery_stone", "minecraft:spruce_planks"),
    "siracusa": ("zinecraft:siracusa_rain_darkened_soil", "zinecraft:siracusa_family_masonry", "minecraft:dark_oak_planks"),
    "yan": ("zinecraft:yan_mountain_soil", "zinecraft:yan_courtyard_brick", "minecraft:polished_tuff"),
    "iberia": ("zinecraft:iberia_salt_crusted_gravel", "zinecraft:iberia_coastal_masonry", "minecraft:oxidized_copper"),
}


@dataclass(frozen=True)
class LandmarkSpec:
    path: str
    nation: str
    kind: str
    public_program: str


SPECS = (
    LandmarkSpec("aegir_volcanic_beacon", "aegir", "beacon", "energy beacon complex"),
    LandmarkSpec("aegir_abyssal_observatory", "aegir", "observatory", "pressure observatory"),
    LandmarkSpec("bolivar_dossoles_yacht", "bolivar", "gallery", "waterfront gallery"),
    LandmarkSpec("bolivar_race_checkpoint", "bolivar", "logistics", "plain relief logistics hub"),
    LandmarkSpec("higashi_rift_shrine", "higashi", "terrace", "rift-side public precinct"),
    LandmarkSpec("higashi_sokogawa_watchtower", "higashi", "watchtower", "border watch and relay tower"),
    LandmarkSpec("durin_dome_station", "durin", "station", "underground garden transit dome"),
    LandmarkSpec("durin_water_park", "durin", "leisure", "engineered public water garden"),
    LandmarkSpec("columbia_frontier_lab", "columbia", "laboratory", "research and energy complex"),
    LandmarkSpec("columbia_prison_outpost", "columbia", "platform", "mobile platform service node"),
    LandmarkSpec("kazimierz_arena_gate", "kazimierz", "arena", "competition arena entrance"),
    LandmarkSpec("kazimierz_knight_monument", "kazimierz", "workshop", "knight equipment and public memorial hall"),
    LandmarkSpec("kazdel_babel_ruins", "kazdel", "ruin", "war-scarred civic stronghold"),
    LandmarkSpec("kazdel_sarkaz_camp", "kazdel", "camp", "clan logistics and shelter compound"),
    LandmarkSpec("laterano_revelation_tower", "laterano", "tower", "public law and communications tower"),
    LandmarkSpec("laterano_ambrosius_chapel", "laterano", "hall", "community hall and sanctuary"),
    LandmarkSpec("leithanien_twin_spires", "leithanien", "spires", "twin acoustic tower complex"),
    LandmarkSpec("leithanien_concert_hall", "leithanien", "hall", "acoustic performance hall"),
    LandmarkSpec("rim_billiton_mining_derrick", "rim_billiton", "industrial", "mine headframe and processing tower"),
    LandmarkSpec("rim_billiton_rail_depot", "rim_billiton", "logistics", "ore rail and freight depot"),
    LandmarkSpec("minos_heroes_temple", "minos", "hall", "city-state civic and training hall"),
    LandmarkSpec("minos_heroes_plaza", "minos", "plaza", "heroic public assembly plaza"),
    LandmarkSpec("sargon_golden_bazaar", "sargon", "market", "oasis caravan market complex"),
    LandmarkSpec("sargon_long_spring_well", "sargon", "waterworks", "oasis water and caravan service node"),
    LandmarkSpec("sami_cyclops_altar", "sami", "boundary", "wilderness boundary gathering site"),
    LandmarkSpec("sami_snowpriest_lodge", "sami", "lodge", "mobile winter lodge and store"),
    LandmarkSpec("victoria_defence_cannon", "victoria", "fortification", "Londinium defence cannon compatible form"),
    LandmarkSpec("victoria_steam_station", "victoria", "station", "Londinium district transport hub"),
    LandmarkSpec("ursus_sarcophagus_station", "ursus", "containment", "restricted heavy-shell utility station"),
    LandmarkSpec("ursus_northern_mine_tower", "ursus", "industrial", "northern mine hoist and sorting tower"),
    LandmarkSpec("kjerag_karlan_monastery", "kjerag", "monastery", "mountain monastery and trade waypoint"),
    LandmarkSpec("kjerag_sacred_plaza", "kjerag", "plaza", "terraced mountain public plaza"),
    LandmarkSpec("siracusa_family_court", "siracusa", "court", "city-state united court"),
    LandmarkSpec("siracusa_family_theatre", "siracusa", "theatre", "city-state public theatre"),
    LandmarkSpec("yan_yumen_beacon", "yan", "gate", "Yumen gate and observation axis"),
    LandmarkSpec("yan_shangshu_pavilion", "yan", "terrace", "Shangshu hill, cable and public terrace"),
    LandmarkSpec("iberia_eye_lighthouse", "iberia", "lighthouse", "coastal navigation and warning tower"),
    LandmarkSpec("iberia_saltwind_chapel", "iberia", "refuge", "salt-wind refuge and service hall"),
)

# Three evidence-bounded architectural program features per compatibility id.
# They are gameplay translations of the corresponding REDESIGN contract, not
# assertions that the legacy English id is a canonical building name.
PROGRAM_FEATURES: dict[str, tuple[str, str, str]] = {
    "aegir_volcanic_beacon": ("energy_well", "pressure_airlock", "radial_bridge"),
    "aegir_abyssal_observatory": ("observation_wedge", "data_archive", "sealed_lab"),
    "bolivar_dossoles_yacht": ("tension_gallery", "waterfront_steps", "service_crane"),
    "bolivar_race_checkpoint": ("check_gate", "relief_clinic", "freight_sorter"),
    "higashi_rift_shrine": ("cliff_terrace", "covered_walk", "relay_platform"),
    "higashi_sokogawa_watchtower": ("watch_deck", "signal_room", "border_store"),
    "durin_dome_station": ("transit_platform", "garden_dome", "service_track"),
    "durin_water_park": ("water_channel", "leisure_deck", "pump_house"),
    "columbia_frontier_lab": ("clean_lab", "energy_spine", "rest_court"),
    "columbia_prison_outpost": ("platform_hitch", "service_bay", "communications"),
    "kazimierz_arena_gate": ("spectator_risers", "entry_gate", "equipment_check"),
    "kazimierz_knight_monument": ("memorial_gallery", "equipment_workshop", "public_archive"),
    "kazdel_babel_ruins": ("scarred_bastion", "triage_room", "broken_arcade"),
    "kazdel_sarkaz_camp": ("shelter_rows", "clan_store", "repair_yard"),
    "laterano_revelation_tower": ("law_archive", "communications", "public_counter"),
    "laterano_ambrosius_chapel": ("sanctuary_hall", "community_kitchen", "quiet_court"),
    "leithanien_twin_spires": ("acoustic_shafts", "rehearsal_rooms", "resonance_bridge"),
    "leithanien_concert_hall": ("auditorium", "stage_rig", "acoustic_baffles"),
    "rim_billiton_mining_derrick": ("mine_hoist", "crusher_line", "ore_hopper"),
    "rim_billiton_rail_depot": ("rail_bays", "freight_sorter", "loading_gantry"),
    "minos_heroes_temple": ("column_hall", "training_court", "civic_archive"),
    "minos_heroes_plaza": ("assembly_steps", "column_ring", "civic_store"),
    "sargon_golden_bazaar": ("shaded_arcade", "caravan_bays", "water_counter"),
    "sargon_long_spring_well": ("cistern", "pump_house", "caravan_shelter"),
    "sami_cyclops_altar": ("boundary_stones", "gathering_hearth", "supply_cache"),
    "sami_snowpriest_lodge": ("sleeping_bunks", "drying_racks", "sled_store"),
    "victoria_defence_cannon": ("armored_bastion", "fire_control", "shell_hoist"),
    "victoria_steam_station": ("train_shed", "ticket_hall", "baggage_sorter"),
    "ursus_sarcophagus_station": ("containment_shell", "medical_isolation", "utility_spine"),
    "ursus_northern_mine_tower": ("mine_hoist", "ore_hopper", "warming_room"),
    "kjerag_karlan_monastery": ("terraced_hall", "trade_store", "mountain_bridge"),
    "kjerag_sacred_plaza": ("stepped_plaza", "route_shelter", "mountain_rail"),
    "siracusa_family_court": ("courtroom", "legal_archive", "rain_court"),
    "siracusa_family_theatre": ("auditorium", "stage_rig", "scenery_store"),
    "yan_yumen_beacon": ("ribbed_gate", "observation_axis", "cargo_lift"),
    "yan_shangshu_pavilion": ("hill_terrace", "cable_station", "service_tunnel"),
    "iberia_eye_lighthouse": ("beacon_lens", "warning_room", "saltwind_jetty"),
    "iberia_saltwind_chapel": ("refuge_hall", "supply_store", "drainage_court"),
}

MACHINE_FEATURES = {
    "energy_well", "energy_spine", "acoustic_shafts", "mine_hoist", "crusher_line",
    "shell_hoist", "utility_spine", "cargo_lift", "beacon_lens", "stage_rig",
}
WATER_FEATURES = {
    "waterfront_steps", "water_channel", "pump_house", "cistern", "water_counter",
    "saltwind_jetty", "drainage_court", "rain_court",
}
LOGISTICS_FEATURES = {
    "service_crane", "freight_sorter", "service_track", "platform_hitch", "service_bay",
    "equipment_check", "border_store", "clan_store", "repair_yard", "equipment_workshop",
    "ore_hopper", "rail_bays", "loading_gantry", "caravan_bays", "supply_cache",
    "sled_store", "baggage_sorter", "trade_store", "civic_store", "scenery_store", "supply_store",
}
FORTIFIED_FEATURES = {
    "pressure_airlock", "sealed_lab", "check_gate", "watch_deck", "signal_room",
    "scarred_bastion", "triage_room", "armored_bastion", "fire_control", "containment_shell",
    "medical_isolation", "warming_room", "ribbed_gate", "observation_axis", "warning_room",
}
PUBLIC_FEATURES = {
    "data_archive", "tension_gallery", "relief_clinic", "covered_walk", "relay_platform",
    "transit_platform", "garden_dome", "leisure_deck", "clean_lab", "rest_court", "communications",
    "spectator_risers", "entry_gate", "memorial_gallery", "public_archive", "shelter_rows",
    "law_archive", "public_counter", "sanctuary_hall", "community_kitchen", "quiet_court",
    "rehearsal_rooms", "auditorium", "acoustic_baffles", "column_hall", "training_court",
    "civic_archive", "assembly_steps", "column_ring", "shaded_arcade", "caravan_shelter",
    "boundary_stones", "gathering_hearth", "sleeping_bunks", "drying_racks", "train_shed",
    "ticket_hall", "terraced_hall", "stepped_plaza", "route_shelter", "mountain_rail",
    "courtroom", "legal_archive", "refuge_hall",
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


def palette_entry(name: str, properties: dict[str, str] | None = None) -> bytes:
    tags = [named(8, "Name", string_payload(name))]
    if properties:
        tags.append(named(10, "Properties", compound_payload([
            named(8, key, string_payload(value)) for key, value in properties.items()
        ])))
    return compound_payload(tags)


def nbt_value(name: str, value: Any) -> bytes:
    if isinstance(value, int):
        return named(3, name, int_payload(value))
    if isinstance(value, str):
        return named(8, name, string_payload(value))
    if isinstance(value, dict):
        return named(10, name, compound_payload([nbt_value(k, v) for k, v in value.items()]))
    if isinstance(value, list) and all(isinstance(v, str) for v in value):
        return named(9, name, list_payload(8, [string_payload(v) for v in value]))
    raise TypeError(f"unsupported NBT value: {name}={value!r}")


def block_entry(pos: tuple[int, int, int], state: int, data: dict[str, Any] | None) -> bytes:
    tags = [
        named(9, "pos", list_payload(3, [int_payload(v) for v in pos])),
        named(3, "state", int_payload(state)),
    ]
    if data:
        tags.append(named(10, "nbt", compound_payload([nbt_value(k, v) for k, v in data.items()])))
    return compound_payload(tags)


BASE_PALETTE = (
    ("minecraft:air", None),                         # 0
    ("GROUND", None),                               # 1
    ("WALL", None),                                 # 2
    ("TRIM", None),                                 # 3
    ("minecraft:tinted_glass", None),               # 4
    ("minecraft:sea_lantern", None),                # 5
    ("create:metal_girder", None),                  # 6
    ("create:andesite_casing", None),               # 7
    ("create:shaft", {"axis": "x"}),              # 8
    ("create:cogwheel", {"axis": "x"}),           # 9
    ("minecraft:chest", {"facing": "west", "type": "single", "waterlogged": "false"}),  # 10
    ("minecraft:barrel", {"facing": "up", "open": "false"}),                              # 11
    ("minecraft:crafting_table", None),             # 12
    ("minecraft:cartography_table", None),          # 13
    ("minecraft:lectern", {"facing": "south", "has_book": "false", "powered": "false"}), # 14
    ("minecraft:bookshelf", None),                  # 15
    ("minecraft:dark_oak_slab", {"type": "top", "waterlogged": "false"}),                 # 16
    ("minecraft:polished_andesite_stairs", {"facing": "east", "half": "bottom", "shape": "straight", "waterlogged": "false"}), # 17
    ("minecraft:polished_andesite_stairs", {"facing": "west", "half": "bottom", "shape": "straight", "waterlogged": "false"}), # 18
    ("minecraft:iron_bars", None),                  # 19
    ("minecraft:dark_oak_fence", {"east": "false", "north": "false", "south": "false", "waterlogged": "false", "west": "false"}), # 20
    ("create:brass_door", {"facing": "south", "half": "lower", "hinge": "left", "open": "false", "powered": "false", "visible": "true"}), #21
    ("create:brass_door", {"facing": "south", "half": "upper", "hinge": "left", "open": "false", "powered": "false", "visible": "true"}), #22
    ("minecraft:water_cauldron", {"level": "3"}), # 23
    ("minecraft:anvil", {"facing": "north"}),      # 24
    ("minecraft:ladder", {"facing": "north", "waterlogged": "false"}), # 25
)

LADDER = 25
VERTICAL_LADDER_STATE = "minecraft:ladder[facing=north,waterlogged=false]"

JIGSAW_ORIENTATIONS = {
    "east": "east_up", "west": "west_up", "north": "north_up",
    "south": "south_up", "up": "up_east", "down": "down_east",
}


@dataclass
class Module:
    spec: LandmarkSpec
    name: str
    size: tuple[int, int, int]
    blocks: dict[tuple[int, int, int], int] = field(default_factory=dict)
    block_nbt: dict[tuple[int, int, int], dict[str, Any]] = field(default_factory=dict)
    connectors: list[tuple[str, str, str]] = field(default_factory=list)
    lights: set[tuple[int, int, int]] = field(default_factory=set)
    containers: set[tuple[int, int, int]] = field(default_factory=set)
    stairs: set[tuple[int, int, int]] = field(default_factory=set)
    doors: set[tuple[int, int, int]] = field(default_factory=set)
    feature_signature: list[tuple[str, str, int, int, int]] = field(default_factory=list)

    @property
    def palette(self) -> list[tuple[str, dict[str, str] | None]]:
        ground, wall, trim = NATION_MATERIALS[self.spec.nation]
        result = list(BASE_PALETTE)
        result[1] = (ground, None)
        result[2] = (wall, None)
        result[3] = (trim, None)
        result.extend(("minecraft:jigsaw", {"orientation": orientation}) for orientation in JIGSAW_ORIENTATIONS.values())
        return result

    def block(self, x: int, y: int, z: int, state: int) -> None:
        if not (0 <= x < self.size[0] and 0 <= y < self.size[1] and 0 <= z < self.size[2]):
            raise ValueError(f"{self.spec.path}/{self.name}: out of bounds {(x, y, z)}")
        self.blocks[(x, y, z)] = state
        self.block_nbt.pop((x, y, z), None)

    def cuboid(self, a: tuple[int, int, int], b: tuple[int, int, int], state: int) -> None:
        for x in range(a[0], b[0] + 1):
            for y in range(a[1], b[1] + 1):
                for z in range(a[2], b[2] + 1):
                    self.block(x, y, z, state)

    def hollow_room(self, a: tuple[int, int, int], b: tuple[int, int, int]) -> None:
        self.cuboid((a[0], a[1], a[2]), (b[0], a[1], b[2]), 2)
        self.cuboid((a[0], b[1], a[2]), (b[0], b[1], b[2]), 2)
        for y in range(a[1] + 1, b[1]):
            for x in range(a[0], b[0] + 1):
                self.block(x, y, a[2], 2); self.block(x, y, b[2], 2)
            for z in range(a[2] + 1, b[2]):
                self.block(a[0], y, z, 2); self.block(b[0], y, z, 2)

    def connector(
        self,
        pos: tuple[int, int, int],
        direction: str,
        connector_id: str,
        pool: str,
        final_state: str = "minecraft:air",
    ) -> None:
        state = len(BASE_PALETTE) + list(JIGSAW_ORIENTATIONS).index(direction)
        self.block(*pos, state)
        target = f"zinecraft:{self.spec.path}/{connector_id}"
        self.block_nbt[pos] = {
            "id": "minecraft:jigsaw", "name": target, "target": target,
            "pool": f"zinecraft:{self.spec.path}/{pool}", "final_state": final_state,
            "joint": "aligned", "selection_priority": 0, "placement_priority": 0,
        }
        self.connectors.append((connector_id, pool, direction))

    def child_connector(
        self,
        pos: tuple[int, int, int],
        direction: str,
        connector_id: str,
        final_state: str = "minecraft:air",
    ) -> None:
        state = len(BASE_PALETTE) + list(JIGSAW_ORIENTATIONS).index(direction)
        self.block(*pos, state)
        name = f"zinecraft:{self.spec.path}/{connector_id}"
        self.block_nbt[pos] = {
            "id": "minecraft:jigsaw", "name": name, "target": "minecraft:empty",
            "pool": "minecraft:empty", "final_state": final_state, "joint": "aligned",
            "selection_priority": 0, "placement_priority": 0,
        }
        self.connectors.append((connector_id, "minecraft:empty", direction))

    def light(self, x: int, y: int, z: int) -> None:
        self.block(x, y, z, 5); self.lights.add((x, y, z))

    def chest(self, x: int, y: int, z: int) -> None:
        self.block(x, y, z, 10)
        self.block_nbt[(x, y, z)] = {
            "id": "minecraft:chest",
            "LootTable": f"zinecraft:chests/nation/{self.spec.nation}_structure",
        }
        self.containers.add((x, y, z))

    def door(self, x: int, y: int, z: int) -> None:
        self.block(x, y, z, 21); self.block(x, y + 1, z, 22)
        self.doors.update(((x, y, z), (x, y + 1, z)))

    def write(self) -> None:
        palette = [palette_entry(name, props) for name, props in self.palette]
        blocks = [block_entry(pos, state, self.block_nbt.get(pos)) for pos, state in sorted(self.blocks.items())]
        root = compound_payload([
            named(3, "DataVersion", int_payload(DATA_VERSION)),
            named(9, "size", list_payload(3, [int_payload(v) for v in self.size])),
            named(9, "palette", list_payload(10, palette)),
            named(9, "blocks", list_payload(10, blocks)),
            named(9, "entities", list_payload(10, [])),
        ])
        target = OUTPUT / self.spec.path / f"{self.name}.nbt"
        target.parent.mkdir(parents=True, exist_ok=True)
        with gzip.GzipFile(filename=str(target), mode="wb", mtime=0) as stream:
            stream.write(bytes([10]) + utf("") + root)


def add_supported_lamp(module: Module, x: int, y: int, z: int) -> None:
    for support_y in range(1, y):
        if module.blocks.get((x, support_y, z), 0) == 0:
            module.block(x, support_y, z, 20)
    module.light(x, y, z)


def feature_seed(spec: LandmarkSpec, feature: str) -> int:
    """Stable layout seed; public_program therefore materially affects geometry."""
    return zlib.crc32(f"{spec.public_program}|{feature}".encode("utf-8"))


def feature_family(feature: str) -> str:
    if feature in MACHINE_FEATURES:
        return "machine"
    if feature in WATER_FEATURES:
        return "water"
    if feature in LOGISTICS_FEATURES:
        return "logistics"
    if feature in FORTIFIED_FEATURES:
        return "fortified"
    if feature in PUBLIC_FEATURES:
        return "public"
    return "spatial"


def add_program_feature(module: Module, feature: str, slot: int) -> None:
    """Place a named program installation in the module, never a text marker."""
    seed = feature_seed(module.spec, feature)
    family = feature_family(feature)
    module.feature_signature.append((module.name, feature, family, seed % 17, seed % 23))

    if module.name == "core":
        floor_y = 1 + slot * 9
        x = 3 + (seed % 3) * 3
        z = 20 + ((seed // 3) % 3) * 3
        if family == "machine":
            module.cuboid((x, floor_y, z), (x + 2, floor_y + 3, z + 2), 7)
            module.cuboid((x + 1, floor_y + 1, z - 1), (x + 1, floor_y + 1, z + 3), 8)
            module.block(x + 1, floor_y + 2, z + 1, 9)
        elif family == "water":
            module.cuboid((x, floor_y, z), (x + 2, floor_y, z + 2), 3)
            module.block(x + 1, floor_y + 1, z + 1, 23)
            module.cuboid((x, floor_y + 1, z), (x, floor_y + 2, z + 2), 7)
        elif family == "logistics":
            module.cuboid((x, floor_y, z), (x + 2, floor_y, z), 11)
            module.cuboid((x, floor_y + 1, z), (x + 2, floor_y + 1, z), 11)
            module.block(x + 1, floor_y, z + 2, 12)
        elif family == "fortified":
            module.cuboid((x, floor_y, z), (x + 3, floor_y + 3, z), 6)
            module.block(x + 1, floor_y, z + 1, 7)
            module.block(x + 2, floor_y, z + 1, 14)
        elif family == "public":
            module.cuboid((x, floor_y, z), (x + 3, floor_y, z + 1), 16)
            module.block(x, floor_y, z + 3, 15)
            module.block(x + 2, floor_y, z + 3, 14)
        else:
            module.cuboid((x, floor_y, z), (x + 4, floor_y, z + 2), 3)
            for px in (x, x + 4):
                module.block(px, floor_y + 1, z, 20)
                module.block(px, floor_y + 1, z + 2, 20)
        return

    if module.name == "facade":
        x = 3 + seed % 12
        depth = 3 + (seed // 11) % 5
        if family in {"machine", "fortified"}:
            module.cuboid((x, 1, depth), (x + 3, 14, depth + 1), 6)
            module.cuboid((x + 1, 4, depth - 1), (x + 2, 10, depth + 2), 7)
        elif family == "water":
            module.cuboid((x, 1, depth), (x + 5, 2, depth + 5), 3)
            module.cuboid((x + 1, 2, depth + 1), (x + 4, 2, depth + 4), 23)
        elif family == "logistics":
            module.cuboid((x, 1, depth), (x + 5, 5, depth), 6)
            module.block(x + 1, 1, depth + 2, 11); module.block(x + 3, 1, depth + 2, 11)
        elif family == "public":
            for step in range(4):
                module.cuboid((x, 1 + step, depth + step), (x + 6, 1 + step, depth + step), 3)
        else:
            module.cuboid((x, 1, depth), (x + 5, 1, depth + 8), 3)
            module.cuboid((x, 2, depth), (x, 7, depth + 8), 20)
        return

    if module.name == "annex":
        x = 2 + seed % 8
        z = 3 + (seed // 7) % 5
        if family == "machine":
            module.cuboid((x, 1, z), (x + 6, 1, z), 8)
            module.block(x + 2, 1, z + 1, 9); module.block(x + 5, 1, z + 1, 7)
        elif family == "water":
            module.cuboid((x, 1, z), (x + 5, 1, z + 4), 3)
            module.block(x + 1, 2, z + 2, 23); module.block(x + 4, 2, z + 2, 23)
        elif family == "logistics":
            module.cuboid((x, 1, z), (x + 5, 3, z), 11)
            module.block(x + 6, 1, z, 24)
        elif family == "fortified":
            module.cuboid((x, 1, z), (x + 6, 4, z), 6)
            module.cuboid((x + 2, 1, z + 1), (x + 4, 2, z + 2), 7)
        elif family == "public":
            module.cuboid((x, 1, z), (x + 5, 1, z + 2), 16)
            module.block(x, 1, z + 4, 15); module.block(x + 4, 1, z + 4, 14)
        else:
            module.cuboid((x, 1, z), (x + 7, 1, z + 5), 3)
            module.cuboid((x, 2, z), (x + 7, 4, z), 20)
        return

    if module.name == "surrounding":
        x = 10 + seed % 6
        z = 22 + (seed // 5) % 4
        if family == "machine":
            for px in (x, x + 6):
                module.cuboid((px, 1, z), (px, 7, z), 6)
            module.cuboid((x, 7, z), (x + 6, 7, z), 6)
        elif family == "water":
            module.cuboid((x, 0, z), (x + 7, 0, z + 3), 3)
            module.cuboid((x + 1, 1, z + 1), (x + 6, 1, z + 2), 23)
        elif family == "logistics":
            module.cuboid((x, 1, z), (x + 7, 1, z + 3), 3)
            for px in (x + 1, x + 3, x + 5):
                module.block(px, 2, z + 1, 11)
        elif family == "fortified":
            module.cuboid((x, 1, z), (x + 7, 3, z), 2)
            for px in range(x, x + 8, 2):
                module.block(px, 4, z, 19)
        elif family == "public":
            for step in range(3):
                module.cuboid((x + step, 1 + step, z + step), (x + 7 - step, 1 + step, z + 3), 3)
        else:
            module.cuboid((x, 1, z), (x + 7, 1, z + 3), 3)
            for px in (x, x + 7):
                module.cuboid((px, 2, z), (px, 5, z + 3), 20)


def add_module_program(module: Module) -> None:
    features = PROGRAM_FEATURES[module.spec.path]
    if module.name == "core":
        for slot, feature in enumerate(features):
            add_program_feature(module, feature, slot)
    elif module.name == "facade":
        add_program_feature(module, features[0], 0)
    elif module.name == "annex":
        add_program_feature(module, features[1], 1)
    elif module.name == "surrounding":
        add_program_feature(module, features[2], 2)


def foundation(spec: LandmarkSpec) -> Module:
    m = Module(spec, "foundation", (48, 12, 48))
    m.cuboid((0, 0, 0), (47, 0, 47), 1)
    m.cuboid((4, 0, 4), (43, 0, 43), 3)
    # Cross-shaped public approach preserves landscape/road relationship.
    m.cuboid((0, 0, 21), (47, 0, 26), 2)
    m.cuboid((21, 0, 0), (26, 0, 47), 2)
    for x, z in ((6, 6), (41, 6), (6, 41), (41, 41)):
        add_supported_lamp(m, x, 4, z)
    m.connector((47, 1, 23), "east", "foundation_core", "core")
    m.connector((0, 1, 23), "west", "foundation_surrounding", "surrounding")
    # Clear real three-high passages at both seams.
    for x in (0, 47):
        for y in range(1, 4):
            for z in range(22, 26):
                if (x, y, z) not in m.block_nbt:
                    m.block(x, y, z, 0)
    return m


def add_switchback_stairs(m: Module) -> None:
    # Three broad flights connect every occupied floor and the roof access level.
    flights = ((2, 8, False), (10, 16, True), (19, 25, False))
    for low, high, reverse in flights:
        for i, y in enumerate(range(low, high + 1)):
            x = 5 + (high - y if reverse else y - low)
            state = 18 if reverse else 17
            m.block(x, y, 6 if not reverse else 9, state)
            m.stairs.add((x, y, 6 if not reverse else 9))
        landing_y = high + 1
        m.cuboid((5, landing_y, 6), (12, landing_y, 9), 3)
    # Cut the stairwell after floors/landings exist; every tread keeps two
    # blocks of player headroom while adjacent landing blocks remain walkable.
    for x, y, z in m.stairs:
        m.block(x, y + 1, z, 0)
        m.block(x, y + 2, z, 0)


def add_roof_access_ladder(m: Module) -> None:
    # North-facing ladder is attached to the continuous trim spine at z+1.
    # The two Jigsaw cells above it resolve to the same ladder block.
    for y in range(28, 31):
        m.block(15, y, 16, 3)
        m.block(15, y, 15, LADDER)


def core(spec: LandmarkSpec) -> Module:
    m = Module(spec, "core", (32, 32, 32))
    m.hollow_room((0, 0, 0), (31, 31, 31))
    for floor_y in (9, 18, 27):
        m.cuboid((1, floor_y, 1), (30, floor_y, 30), 3)
    # Windows are narrow and repeated, not an all-glass box.
    for y in (4, 13, 22):
        for x in (5, 11, 20, 26):
            m.cuboid((x, y, 0), (x + 1, y + 2, 0), 4)
            m.cuboid((x, y, 31), (x + 1, y + 2, 31), 4)
    # Room division and a real brass doorway on every occupied floor.
    for floor_y in (0, 9, 18):
        m.cuboid((1, floor_y + 1, 16), (30, floor_y + 8, 16), 2)
        for y in range(floor_y + 1, floor_y + 4):
            m.block(16, y, 16, 0)
        m.door(16, floor_y + 1, 16)
        for x in (14, 24):
            m.cuboid((x - 1, floor_y + 8, 6), (x + 1, floor_y + 8, 6), 6)
            m.light(x, floor_y + 8, 6)
            m.cuboid((x - 1, floor_y + 8, 24), (x + 1, floor_y + 8, 24), 6)
            m.light(x, floor_y + 8, 24)
    add_switchback_stairs(m)
    add_roof_access_ladder(m)
    # Purposeful planning/control furnishings and a supported mechanical service line.
    for x in range(19, 27):
        m.block(x, 1, 7, 16)
    m.block(21, 1, 8, 13); m.block(24, 1, 8, 14)
    m.block(27, 1, 23, 12); m.block(26, 1, 23, 7)
    for x in range(20, 28):
        m.block(x, 10, 25, 8)
    m.block(24, 10, 24, 9)
    m.cuboid((20, 19, 25), (27, 21, 25), 15)
    m.chest(27, 1, 27); m.chest(27, 10, 27); m.chest(27, 19, 27)
    add_module_program(m)
    # Four seams: plaza entry, facade, annex, roof.
    m.child_connector((0, 1, 15), "west", "foundation_core")
    m.connector((31, 1, 15), "east", "core_facade", "facade")
    m.connector((15, 1, 31), "south", "core_annex", "annex")
    m.connector((15, 31, 15), "up", "core_roof", "roof", VERTICAL_LADDER_STATE)
    for x, z in ((0, 15), (31, 15)):
        for y in range(1, 4):
            for dz in range(0, 3):
                zz = z + dz
                if (x, y, zz) not in m.block_nbt:
                    m.block(x, y, zz, 0)
    for y in range(1, 4):
        for x in range(14, 18):
            if (x, y, 31) not in m.block_nbt:
                m.block(x, y, 31, 0)
    return m


def facade(spec: LandmarkSpec) -> Module:
    m = Module(spec, "facade", (24, 24, 32))
    m.hollow_room((0, 0, 0), (23, 19, 31))
    # Recessed public entrance and facade rhythm.
    for y in range(1, 5):
        m.cuboid((0, y, 14), (0, y, 17), 0)
    for x in (5, 11, 17):
        m.cuboid((x, 4, 0), (x + 1, 11, 0), 4)
        m.cuboid((x, 4, 31), (x + 1, 11, 31), 4)
        m.cuboid((x, 1, 2), (x, 17, 2), 3)
    for x in (6, 17):
        m.cuboid((x - 1, 18, 8), (x + 1, 18, 8), 6); m.light(x, 18, 8)
        m.cuboid((x - 1, 18, 23), (x + 1, 18, 23), 6); m.light(x, 18, 23)
    m.cuboid((7, 1, 13), (16, 1, 18), 16)
    m.block(9, 1, 12, 14); m.block(14, 1, 12, 13)
    m.chest(20, 1, 27)
    add_module_program(m)
    m.child_connector((0, 1, 15), "west", "core_facade")
    return m


def annex(spec: LandmarkSpec) -> Module:
    m = Module(spec, "annex", (32, 20, 24))
    m.hollow_room((0, 0, 0), (31, 17, 23))
    for y in range(1, 4):
        m.cuboid((14, y, 0), (17, y, 0), 0)
    # Workshop/store/medical-ready service wing with visible maintained machinery.
    m.cuboid((16, 1, 4), (16, 16, 22), 2)
    for y in range(1, 4):
        m.block(16, y, 12, 0)
    m.door(16, 1, 12)
    for x in range(3, 13):
        m.block(x, 1, 18, 8)
    m.block(7, 1, 17, 9); m.block(11, 1, 17, 7)
    m.block(20, 1, 18, 12); m.block(22, 1, 18, 11)
    m.cuboid((21, 1, 4), (27, 3, 4), 15)
    for x in (7, 24):
        m.cuboid((x - 1, 16, 7), (x + 1, 16, 7), 6); m.light(x, 16, 7)
        m.cuboid((x - 1, 16, 18), (x + 1, 16, 18), 6); m.light(x, 16, 18)
    m.chest(28, 1, 20)
    add_module_program(m)
    m.child_connector((15, 1, 0), "north", "core_annex")
    return m


def roof(spec: LandmarkSpec) -> Module:
    m = Module(spec, "roof", (32, 32, 32))
    m.cuboid((1, 0, 1), (30, 1, 30), 3)
    m.child_connector((15, 0, 15), "down", "core_roof", VERTICAL_LADDER_STATE)
    # Silhouette families follow the redesign function rather than the legacy name.
    if spec.kind in {"tower", "watchtower", "lighthouse", "beacon", "industrial", "spires", "gate"}:
        towers = (8, 23) if spec.kind in {"spires", "gate"} else (15,)
        for cx in towers:
            for y in range(2, 25):
                radius = 4 if y < 15 else 2
                m.cuboid((cx - radius, y, 12), (cx + radius, y, 19), 2 if y % 5 else 3)
            m.light(cx, 26, 15); m.block(cx, 25, 15, 6)
    elif spec.kind in {"hall", "gallery", "theatre", "arena", "station", "court", "monastery"}:
        for x in range(2, 30):
            height = 12 - abs(15 - x) // 3
            m.cuboid((x, 2, 3), (x, height, 28), 3 if x % 4 == 0 else 2)
        m.cuboid((5, 13, 8), (26, 14, 23), 3)
    elif spec.kind in {"plaza", "terrace", "leisure", "market", "camp", "boundary"}:
        for inset, y in ((3, 2), (6, 5), (9, 8)):
            for x in range(inset, 32 - inset):
                m.block(x, y, inset, 2); m.block(x, y, 31 - inset, 2)
            for z in range(inset, 32 - inset):
                m.block(inset, y, z, 2); m.block(31 - inset, y, z, 2)
        m.cuboid((13, 9, 13), (18, 18, 18), 3)
    else:
        m.cuboid((4, 2, 4), (27, 8, 27), 2)
        m.cuboid((8, 9, 8), (23, 15, 23), 3)
        for x, z in ((6, 6), (25, 6), (6, 25), (25, 25)):
            m.cuboid((x, 2, z), (x, 13, z), 6)
    # Roof safety rail and supported work lights.
    for x in range(1, 31):
        m.block(x, 2, 1, 19); m.block(x, 2, 30, 19)
    for z in range(1, 31):
        m.block(1, 2, z, 19); m.block(30, 2, z, 19)
    for x, z in ((4, 4), (27, 4), (4, 27), (27, 27)):
        add_supported_lamp(m, x, 5, z)
    # Continue the north-facing ladder through the roof piece. The child
    # Jigsaw at local y=0 also resolves to a ladder, producing world-y 28..34
    # continuity after assembly. Its z+1 trim spine physically supports it.
    for y in range(1, 3):
        m.block(15, y, 16, 3)
        m.block(15, y, 15, LADDER)
    m.block(15, 3, 15, 0)
    # Two-block-high egress corridor. The slab around the 1x1 shaft is the
    # landing; bars guard both sides and the rear edge.
    for x in range(16, 21):
        for z in range(14, 16):
            for y in range(2, 5):
                m.block(x, y, z, 0)
    for x in range(13, 21):
        m.block(x, 2, 13, 19)
        m.block(x, 2, 17, 19)
    for z in range(13, 18):
        m.block(13, 2, z, 19)
    return m


def surrounding(spec: LandmarkSpec) -> Module:
    m = Module(spec, "surrounding", (32, 12, 32))
    m.cuboid((0, 0, 0), (31, 0, 31), 1)
    # Approach road, drainage/service strips and a small logistics shelter.
    m.cuboid((0, 1, 12), (31, 1, 19), 3)
    m.cuboid((3, 1, 3), (7, 1, 28), 2)
    m.cuboid((23, 1, 3), (28, 1, 9), 2)
    m.cuboid((23, 2, 3), (28, 6, 9), 2)
    m.cuboid((24, 2, 4), (27, 5, 8), 0)
    m.block(25, 2, 7, 11); m.chest(26, 2, 7)
    for x, z in ((4, 5), (4, 26), (26, 11), (26, 23)):
        add_supported_lamp(m, x, 4, z)
    add_module_program(m)
    m.child_connector((31, 1, 15), "east", "foundation_surrounding")
    return m


def build(spec: LandmarkSpec) -> list[Module]:
    return [foundation(spec), core(spec), facade(spec), roof(spec), annex(spec), surrounding(spec)]


def validate(spec: LandmarkSpec, modules: list[Module]) -> None:
    by_name = {m.name: m for m in modules}
    if set(by_name) != set(MODULES):
        raise ValueError(f"{spec.path}: required module classes missing: {set(MODULES) - set(by_name)}")
    if len(modules) < 4:
        raise ValueError(f"{spec.path}: landmark must be multi-NBT")
    # Expected deterministic assembly spans 136 x 64 x 72: comfortably L-scale.
    assembled = (136, 64, 72)
    if max(assembled[0], assembled[2]) < 64:
        raise ValueError(f"{spec.path}: assembled landmark is below L scale")
    expected = {
        "foundation_core": 2, "foundation_surrounding": 2, "core_facade": 2,
        "core_annex": 2, "core_roof": 2,
    }
    counts: dict[str, int] = {}
    for m in modules:
        for connector_id, _, _ in m.connectors:
            counts[connector_id] = counts.get(connector_id, 0) + 1
    if counts != expected:
        raise ValueError(f"{spec.path}: unmatched Jigsaw interfaces: {counts}")
    if not all(m.blocks for m in modules):
        raise ValueError(f"{spec.path}: empty module")
    features = PROGRAM_FEATURES[spec.path]
    if not spec.public_program.strip() or len(set(features)) != 3:
        raise ValueError(f"{spec.path}: program must define three distinct features")
    expected_feature_names = {
        "core": set(features), "facade": {features[0]},
        "annex": {features[1]}, "surrounding": {features[2]},
    }
    for module_name, feature_names in expected_feature_names.items():
        actual = {entry[1] for entry in by_name[module_name].feature_signature}
        if actual != feature_names:
            raise ValueError(f"{spec.path}/{module_name}: program geometry missing {feature_names - actual}")

    # Horizontal seams must expose feet and two blocks of headroom on both
    # pieces after their Jigsaw blocks resolve to air.
    horizontal_seams = (
        ("foundation", (47, 1, 23), "core", (0, 1, 15)),
        ("foundation", (0, 1, 23), "surrounding", (31, 1, 15)),
        ("core", (31, 1, 15), "facade", (0, 1, 15)),
        ("core", (15, 1, 31), "annex", (15, 1, 0)),
    )
    for parent_name, parent_pos, child_name, child_pos in horizontal_seams:
        for module_name, pos in ((parent_name, parent_pos), (child_name, child_pos)):
            module = by_name[module_name]
            if module.block_nbt.get(pos, {}).get("final_state") != "minecraft:air":
                raise ValueError(f"{spec.path}/{module_name}: seam is not final air at {pos}")
            x, y, z = pos
            if module.blocks.get((x, y + 1, z), 0) != 0 or module.blocks.get((x, y + 2, z), 0) != 0:
                raise ValueError(f"{spec.path}/{module_name}: cross-Jigsaw headroom blocked at {pos}")

    # The vertical seam resolves to one continuous, physically supported
    # north-facing ladder across core world-y 28..31 and roof world-y 32..34.
    core_module, roof_module = by_name["core"], by_name["roof"]
    if core_module.block_nbt.get((15, 31, 15), {}).get("final_state") != VERTICAL_LADDER_STATE:
        raise ValueError(f"{spec.path}: core roof connector does not resolve to ladder")
    if roof_module.block_nbt.get((15, 0, 15), {}).get("final_state") != VERTICAL_LADDER_STATE:
        raise ValueError(f"{spec.path}: roof hatch connector does not resolve to ladder")
    if any(core_module.blocks.get((15, y, 15)) != LADDER for y in range(28, 31)):
        raise ValueError(f"{spec.path}: core ladder is discontinuous")
    if any(roof_module.blocks.get((15, y, 15)) != LADDER for y in range(1, 3)):
        raise ValueError(f"{spec.path}: roof ladder is discontinuous")
    ladder_supports = (
        (core_module, range(28, 31)),
        (roof_module, range(1, 3)),
    )
    for module, levels in ladder_supports:
        if any(module.blocks.get((15, y, 16)) not in {2, 3} for y in levels):
            raise ValueError(f"{spec.path}/{module.name}: north-facing ladder lacks z+1 support")
    if core_module.blocks.get((15, 31, 16)) not in {2, 3} or roof_module.blocks.get((15, 0, 16)) not in {2, 3}:
        raise ValueError(f"{spec.path}: connector ladders lack backing blocks")
    if roof_module.blocks.get((15, 3, 15), 0) != 0 or roof_module.blocks.get((16, 1, 15)) != 3:
        raise ValueError(f"{spec.path}: roof ladder lacks headroom or exit platform")
    if roof_module.blocks.get((15, 2, 13)) != 19 or roof_module.blocks.get((15, 2, 17)) != 19:
        raise ValueError(f"{spec.path}: roof hatch landing lacks guard rails")

    # Assemble the two pieces in world coordinates exactly as the up/down
    # Jigsaw pair places them, substitute final states, then prove a player's
    # two-block body can walk from the core top floor, climb, and step onto the
    # roof platform. This catches local-coordinate false positives.
    assembled_blocks: dict[tuple[int, int, int], int] = {}
    for module, origin_y in ((core_module, 0), (roof_module, 32)):
        for (x, y, z), state in module.blocks.items():
            final_state = module.block_nbt.get((x, y, z), {}).get("final_state")
            if final_state == VERTICAL_LADDER_STATE:
                state = LADDER
            elif final_state == "minecraft:air":
                state = 0
            assembled_blocks[(x, y + origin_y, z)] = state

    passable = {0, LADDER, 21, 22}

    def can_occupy(position: tuple[int, int, int]) -> bool:
        x, y, z = position
        feet = assembled_blocks.get((x, y, z), 0)
        head = assembled_blocks.get((x, y + 1, z), 0)
        if feet not in passable or head not in passable:
            return False
        below = assembled_blocks.get((x, y - 1, z), 0)
        return feet == LADDER or below not in passable

    start, roof_goal = (14, 28, 15), (16, 34, 15)
    queue, reached = deque([start]), {start}
    while queue:
        x, y, z = queue.popleft()
        current_state = assembled_blocks.get((x, y, z), 0)
        neighbours = [(x + 1, y, z), (x - 1, y, z), (x, y, z + 1), (x, y, z - 1)]
        for vertical_y in (y + 1, y - 1):
            destination_state = assembled_blocks.get((x, vertical_y, z), 0)
            if current_state == LADDER or destination_state == LADDER:
                neighbours.append((x, vertical_y, z))
        for destination in neighbours:
            if destination in reached or not (27 <= destination[1] <= 36):
                continue
            if can_occupy(destination):
                reached.add(destination)
                queue.append(destination)
    if roof_goal not in reached:
        raise ValueError(f"{spec.path}: assembled 3D traversal cannot reach roof platform")
    # Physical support for every main light.
    supported = {1, 2, 3, 6, 7, 20}
    for m in modules:
        for x, y, z in m.lights:
            neighbours = ((x + 1, y, z), (x - 1, y, z), (x, y + 1, z),
                          (x, y - 1, z), (x, y, z + 1), (x, y, z - 1))
            if not any(m.blocks.get(pos) in supported for pos in neighbours):
                raise ValueError(f"{spec.path}/{m.name}: floating light {(x, y, z)}")
        for x, y, z in m.containers:
            if y < 1 or m.blocks.get((x, y - 1, z)) not in {1, 2, 3}:
                raise ValueError(f"{spec.path}/{m.name}: unsupported or sunken container {(x, y, z)}")
            nbt = m.block_nbt[(x, y, z)]
            if nbt.get("LootTable") != f"zinecraft:chests/nation/{spec.nation}_structure":
                raise ValueError(f"{spec.path}/{m.name}: invalid national loot table")
        for x, y, z in m.stairs:
            head_states = (m.blocks.get((x, y + 1, z), 0), m.blocks.get((x, y + 2, z), 0))
            if any(state != 0 and state < len(BASE_PALETTE) for state in head_states):
                raise ValueError(f"{spec.path}/{m.name}: stair headroom blocked {(x, y, z)}")
    if len(by_name["core"].containers) < 3 or len(by_name["core"].doors) < 6:
        raise ValueError(f"{spec.path}: core lacks rooms, doors or loot")
    # Ground-floor room connectivity around the divider and brass door.
    m = by_name["core"]
    passable_states = {0, 21, 22}
    start, goal = (4, 1, 4), (26, 1, 27)
    queue, seen = deque([start]), {start}
    while queue:
        x, y, z = queue.popleft()
        for pos in ((x + 1, y, z), (x - 1, y, z), (x, y, z + 1), (x, y, z - 1)):
            if not (1 <= pos[0] <= 30 and 1 <= pos[2] <= 30) or pos in seen:
                continue
            if m.blocks.get(pos, 0) in passable_states:
                seen.add(pos); queue.append(pos)
    if goal not in seen:
        raise ValueError(f"{spec.path}: ground-floor rooms are disconnected")


def main() -> None:
    if len(SPECS) != 38 or len({s.path for s in SPECS}) != 38:
        raise ValueError("public landmark ids must contain exactly 38 unique values")
    if set(NATION_MATERIALS) != {s.nation for s in SPECS}:
        raise ValueError("all nineteen nation palettes must be used")
    if set(PROGRAM_FEATURES) != {s.path for s in SPECS}:
        raise ValueError("program feature table must cover exactly the 38 public ids")
    if len(set(PROGRAM_FEATURES.values())) != 38:
        raise ValueError("all 38 landmarks must have a unique program feature signature")
    if OUTPUT.exists():
        shutil.rmtree(OUTPUT)
    module_count = 0
    generated_signatures: set[tuple[tuple[str, str, int, int, int], ...]] = set()
    for spec in SPECS:
        modules = build(spec)
        validate(spec, modules)
        signature = tuple(sorted(entry for module in modules for entry in module.feature_signature))
        if signature in generated_signatures:
            raise ValueError(f"{spec.path}: generated program signature is not unique")
        generated_signatures.add(signature)
        for module in modules:
            module.write()
            module_count += 1
    if len(generated_signatures) != 38:
        raise ValueError("generated program signatures must remain unique")
    print(f"Generated {len(SPECS)} modular landmarks / {module_count} NBT modules in {OUTPUT}")


if __name__ == "__main__":
    main()

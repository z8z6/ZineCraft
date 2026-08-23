#!/usr/bin/env python3
"""Overwrite every Region building template with a deterministic, south-door matchbox."""

from __future__ import annotations

import gzip
import struct
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
STRUCTURE_ROOT = ROOT / "src/main/resources/data/zinecraft/structure"
DATA_VERSION = 3955
SHOP_IDS = (
    "aegir_shop", "bolivar_shop", "higashi_shop", "durin_shop",
    "columbia_shop", "kazimierz_shop", "kazdel_shop", "laterano_shop",
    "leithanien_shop", "rim_billiton_shop", "minos_shop", "sargon_shop",
    "sami_shop", "victoria_shop", "ursus_shop", "kjerag_shop",
    "siracusa_shop", "siesta_shop", "yan_shop", "iberia_shop",
)
MEDIUM_SHOP_IDS = tuple(shop.removesuffix("_shop") + "_medium_shop" for shop in SHOP_IDS)
LARGE_IDS = (
    "aegir_volcanic_beacon", "aegir_abyssal_observatory",
    "bolivar_dossoles_yacht", "bolivar_race_checkpoint",
    "higashi_rift_shrine", "higashi_sokogawa_watchtower",
    "durin_dome_station", "durin_water_park",
    "columbia_frontier_lab", "columbia_prison_outpost",
    "kazimierz_arena_gate", "kazimierz_knight_monument",
    "kazdel_babel_ruins", "kazdel_sarkaz_camp",
    "laterano_revelation_tower", "laterano_ambrosius_chapel",
    "leithanien_twin_spires", "leithanien_concert_hall",
    "rim_billiton_mining_derrick", "rim_billiton_rail_depot",
    "minos_heroes_temple", "minos_heroes_plaza",
    "sargon_golden_bazaar", "sargon_long_spring_well",
    "sami_cyclops_altar", "sami_snowpriest_lodge",
    "victoria_defence_cannon", "victoria_steam_station",
    "ursus_sarcophagus_station", "ursus_northern_mine_tower",
    "kjerag_karlan_monastery", "kjerag_sacred_plaza",
    "siracusa_family_court", "siracusa_family_theatre",
    "yan_yumen_beacon", "yan_shangshu_pavilion",
    "iberia_eye_lighthouse", "iberia_saltwind_chapel",
)


def utf(value: str) -> bytes:
    encoded = value.encode("utf-8")
    return struct.pack(">H", len(encoded)) + encoded


def named(tag_type: int, name: str, payload: bytes) -> bytes:
    return bytes([tag_type]) + utf(name) + payload


def integer(value: int) -> bytes:
    return struct.pack(">i", value)


def string(value: str) -> bytes:
    return utf(value)


def list_payload(tag_type: int, values: list[bytes]) -> bytes:
    return bytes([tag_type]) + struct.pack(">i", len(values)) + b"".join(values)


def compound(tags: list[bytes]) -> bytes:
    return b"".join(tags) + b"\x00"


def palette_entry(name: str, properties: dict[str, str] | None = None) -> bytes:
    tags = [named(8, "Name", string(name))]
    if properties:
        tags.append(named(10, "Properties", compound([
            named(8, key, string(value)) for key, value in properties.items()
        ])))
    return compound(tags)


def block_entry(x: int, y: int, z: int, state: int) -> bytes:
    return compound([
        named(9, "pos", list_payload(3, [integer(x), integer(y), integer(z)])),
        named(3, "state", integer(state)),
    ])


def write_matchbox(path: Path, chunks_x: int, chunks_z: int, height: int = 8) -> None:
    width = chunks_x * 16
    depth = chunks_z * 16
    door_x = width // 2
    blocks: list[bytes] = []
    for x in range(width):
        for y in range(height):
            for z in range(depth):
                boundary = (
                    x in {0, width - 1}
                    or y in {0, height - 1}
                    or z in {0, depth - 1}
                )
                if x == door_x and z == depth - 1 and y == 1:
                    state = 2
                elif x == door_x and z == depth - 1 and y == 2:
                    state = 3
                else:
                    state = 1 if boundary else 0
                blocks.append(block_entry(x, y, z, state))
    root = compound([
        named(3, "DataVersion", integer(DATA_VERSION)),
        named(9, "size", list_payload(3, [integer(width), integer(height), integer(depth)])),
        named(9, "palette", list_payload(10, [
            palette_entry("minecraft:air"),
            palette_entry("minecraft:stone_bricks"),
            palette_entry("minecraft:oak_door", {
                "facing": "south", "half": "lower", "hinge": "left",
                "open": "false", "powered": "false",
            }),
            palette_entry("minecraft:oak_door", {
                "facing": "south", "half": "upper", "hinge": "left",
                "open": "false", "powered": "false",
            }),
        ])),
        named(9, "blocks", list_payload(10, blocks)),
        named(9, "entities", list_payload(10, [])),
    ])
    path.parent.mkdir(parents=True, exist_ok=True)
    with gzip.GzipFile(filename=str(path), mode="wb", mtime=0) as stream:
        stream.write(bytes([10]) + utf("") + root)


def main() -> None:
    targets: list[tuple[Path, int, int]] = [
        *((STRUCTURE_ROOT / f"{shop}.nbt", 1, 1) for shop in SHOP_IDS),
        *((STRUCTURE_ROOT / f"{shop}.nbt", 1, 2) for shop in MEDIUM_SHOP_IDS),
        (STRUCTURE_ROOT / "stargate.nbt", 2, 2),
        (STRUCTURE_ROOT / "laterano_host/core.nbt", 2, 2),
    ]
    for building in LARGE_IDS:
        directory = STRUCTURE_ROOT / building
        templates = sorted(directory.glob("*.nbt"))
        if not templates:
            raise FileNotFoundError(f"No templates found for {building}")
        targets.extend((template, 2, 2) for template in templates)
    for target, chunks_x, chunks_z in targets:
        write_matchbox(target, chunks_x, chunks_z)
    expected = len(SHOP_IDS) + len(MEDIUM_SHOP_IDS) + 2 + len(LARGE_IDS) * 6
    if len(targets) != expected:
        raise RuntimeError(f"Expected {expected} templates, got {len(targets)}")
    print(f"Generated {len(targets)} south-door, Chunk-aligned building matchboxes")


if __name__ == "__main__":
    main()

"""生成三段式 Jigsaw 建筑示例的 Minecraft structure NBT。"""

from __future__ import annotations

import gzip
import struct
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/resources/data/zinecraft-core/structure/jigsaw_example"
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
        property_tags = [named(8, key, string_payload(value)) for key, value in properties.items()]
        tags.append(named(10, "Properties", compound_payload(property_tags)))
    return compound_payload(tags)


def block_entry(
    position: tuple[int, int, int],
    state: int,
    block_entity: dict[str, str | int] | None = None,
) -> bytes:
    tags = [
        named(9, "pos", list_payload(3, [int_payload(value) for value in position])),
        named(3, "state", int_payload(state)),
    ]
    if block_entity:
        entity_tags = []
        for key, value in block_entity.items():
            if isinstance(value, int):
                entity_tags.append(named(3, key, int_payload(value)))
            else:
                entity_tags.append(named(8, key, string_payload(value)))
        tags.append(named(10, "nbt", compound_payload(entity_tags)))
    return compound_payload(tags)


def jigsaw_nbt(name: str, target: str, pool: str) -> dict[str, str | int]:
    return {
        "id": "minecraft:jigsaw",
        "name": name,
        "target": target,
        "pool": pool,
        "final_state": "minecraft:stone_bricks",
        "joint": "aligned",
        "selection_priority": 0,
        "placement_priority": 0,
    }


def write_structure(
    name: str,
    jigsaws: list[tuple[tuple[int, int, int], str, str, str, str]],
) -> None:
    palette = [
        palette_entry("minecraft:stone_bricks"),
        palette_entry("minecraft:jigsaw", {"orientation": "east_up"}),
        palette_entry("minecraft:jigsaw", {"orientation": "west_up"}),
    ]
    blocks = [block_entry((x, 0, z), 0) for x in range(5) for z in range(5)]
    for position, orientation, connector_name, target, pool in jigsaws:
        state = 1 if orientation == "east_up" else 2
        blocks.append(block_entry(position, state, jigsaw_nbt(connector_name, target, pool)))

    root = compound_payload(
        [
            named(3, "DataVersion", int_payload(DATA_VERSION)),
            named(9, "size", list_payload(3, [int_payload(5), int_payload(2), int_payload(5)])),
            named(9, "palette", list_payload(10, palette)),
            named(9, "blocks", list_payload(10, blocks)),
            named(9, "entities", list_payload(10, [])),
        ]
    )
    data = bytes([10]) + utf("") + root
    OUTPUT.mkdir(parents=True, exist_ok=True)
    with gzip.GzipFile(filename=str(OUTPUT / f"{name}.nbt"), mode="wb", mtime=0) as stream:
        stream.write(data)


write_structure(
    "start",
    [
        (
            (4, 1, 2),
            "east_up",
            "zinecraft-core:jigsaw_example/start_exit",
            "zinecraft-core:jigsaw_example/middle_in",
            "zinecraft-core:jigsaw_example/middle",
        )
    ],
)

write_structure(
    "middle",
    [
        (
            (0, 1, 2),
            "west_up",
            "zinecraft-core:jigsaw_example/middle_in",
            "minecraft:empty",
            "minecraft:empty",
        ),
        (
            (4, 1, 2),
            "east_up",
            "zinecraft-core:jigsaw_example/middle_exit",
            "zinecraft-core:jigsaw_example/end_in",
            "zinecraft-core:jigsaw_example/end",
        ),
    ],
)

write_structure(
    "end",
    [
        (
            (0, 1, 2),
            "west_up",
            "zinecraft-core:jigsaw_example/end_in",
            "minecraft:empty",
            "minecraft:empty",
        )
    ],
)

print(f"Generated Jigsaw example templates in {OUTPUT}")

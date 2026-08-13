"""生成技能系统共用的 Ponder 训练场景 NBT。"""

from __future__ import annotations

import gzip
import struct
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/resources/assets/zinecraft/ponder/skill_demo/training_ground.nbt"
DATA_VERSION = 3955


def utf(value: str) -> bytes:
    encoded = value.encode("utf-8")
    return struct.pack(">H", len(encoded)) + encoded


def named(tag_type: int, name: str, payload: bytes) -> bytes:
    return bytes([tag_type]) + utf(name) + payload


def int_payload(value: int) -> bytes:
    return struct.pack(">i", value)


def list_payload(tag_type: int, values: list[bytes]) -> bytes:
    return bytes([tag_type]) + struct.pack(">i", len(values)) + b"".join(values)


def compound_payload(tags: list[bytes]) -> bytes:
    return b"".join(tags) + b"\x00"


def palette_entry(name: str) -> bytes:
    return compound_payload([named(8, "Name", utf(name))])


def block_entry(position: tuple[int, int, int], state: int) -> bytes:
    return compound_payload([
        named(9, "pos", list_payload(3, [int_payload(value) for value in position])),
        named(3, "state", int_payload(state)),
    ])


palette = [
    palette_entry("minecraft:polished_deepslate"),
    palette_entry("minecraft:smooth_quartz"),
    palette_entry("minecraft:red_concrete"),
    palette_entry("minecraft:gold_block"),
    palette_entry("minecraft:sea_lantern"),
]

blocks: dict[tuple[int, int, int], int] = {}
for x in range(7):
    for z in range(7):
        blocks[(x, 0, z)] = 0

for x in range(1, 6):
    for z in range(1, 6):
        blocks[(x, 1, z)] = 1

blocks[(3, 1, 3)] = 3
blocks[(3, 2, 3)] = 4
for position in ((1, 1, 1), (1, 1, 5), (5, 1, 1), (5, 1, 5)):
    blocks[position] = 2

root = compound_payload([
    named(3, "DataVersion", int_payload(DATA_VERSION)),
    named(9, "size", list_payload(3, [int_payload(7), int_payload(3), int_payload(7)])),
    named(9, "palette", list_payload(10, palette)),
    named(9, "blocks", list_payload(10, [
        block_entry(position, state) for position, state in sorted(blocks.items())
    ])),
    named(9, "entities", list_payload(10, [])),
])

OUTPUT.parent.mkdir(parents=True, exist_ok=True)
with gzip.GzipFile(filename=str(OUTPUT), mode="wb", mtime=0) as stream:
    stream.write(bytes([10]) + utf("") + root)

print(f"Generated skill Ponder scene in {OUTPUT}")

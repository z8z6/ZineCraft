"""Generate the inactive snowfield stargate template used by worldgen/structure."""

from __future__ import annotations

import gzip
import math
import struct
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/stargate.nbt"
DATA_VERSION = 3955
SIZE = (31, 26, 10)
AIR = 0
ARCH = 1
CONTROLLER = 2
PALETTE = (
    ("minecraft:air", None),
    ("zinecraft:stargate_arch", None),
    ("zinecraft:stargate_controller", {"active": "false", "axis": "x"}),
)


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


def palette_entry(name: str, properties: dict[str, str] | None) -> bytes:
    tags = [named(8, "Name", string_payload(name))]
    if properties:
        tags.append(named(10, "Properties", compound_payload([
            named(8, key, string_payload(value)) for key, value in properties.items()
        ])))
    return compound_payload(tags)


def block_entry(position: tuple[int, int, int], state: int) -> bytes:
    return compound_payload([
        named(9, "pos", list_payload(3, [int_payload(value) for value in position])),
        named(3, "state", int_payload(state)),
    ])


def shifted(horizontal: int, vertical: int, depth: int) -> tuple[int, int, int]:
    return horizontal + 15, vertical + 1, depth + 3


def generate_blocks() -> dict[tuple[int, int, int], int]:
    blocks = {
        (x, y, z): AIR
        for x in range(SIZE[0])
        for y in range(SIZE[1])
        for z in range(SIZE[2])
    }

    # Foundation slab.
    for horizontal in range(-14, 15):
        for depth in range(-3, 7):
            blocks[shifted(horizontal, -1, depth)] = ARCH

    # Four stepped buttresses on either side of the gate.
    for side in (-1, 1):
        for radius in range(12, 16):
            top = 6 - (radius - 12)
            for vertical in range(top + 1):
                for depth in range(-2, 3):
                    blocks[shifted(side * radius, vertical, depth)] = ARCH

    # Twenty-four-block-high circular arch; the inactive interior remains air.
    half_widths = []
    for vertical in range(1, 25):
        curve_height = max(vertical - 12, 0)
        half_widths.append(max(1, round(math.sqrt(12 * 12 - curve_height * curve_height))))
    for vertical, half_width in enumerate(half_widths, start=1):
        interior_half_width = half_width - 2
        for horizontal in range(-half_width, half_width + 1):
            if interior_half_width < 0 or abs(horizontal) > interior_half_width:
                for depth in range(-1, 2):
                    blocks[shifted(horizontal, vertical, depth)] = ARCH

    blocks[shifted(0, 0, 5)] = CONTROLLER
    return blocks


def write_structure(blocks: dict[tuple[int, int, int], int]) -> None:
    root = compound_payload([
        named(3, "DataVersion", int_payload(DATA_VERSION)),
        named(9, "size", list_payload(3, [int_payload(value) for value in SIZE])),
        named(9, "palette", list_payload(10, [palette_entry(*entry) for entry in PALETTE])),
        named(9, "blocks", list_payload(10, [
            block_entry(position, state) for position, state in sorted(blocks.items())
        ])),
        named(9, "entities", list_payload(10, [])),
    ])
    OUTPUT.parent.mkdir(parents=True, exist_ok=True)
    with gzip.GzipFile(filename=str(OUTPUT), mode="wb", mtime=0) as stream:
        stream.write(bytes([10]) + utf("") + root)


if __name__ == "__main__":
    structure_blocks = generate_blocks()
    if set(structure_blocks.values()) != {AIR, ARCH, CONTROLLER}:
        raise ValueError("stargate palette coverage is incomplete")
    write_structure(structure_blocks)
    solid = sum(state != AIR for state in structure_blocks.values())
    print(f"stargate: size={SIZE}, entries={len(structure_blocks)}, solid={solid}")

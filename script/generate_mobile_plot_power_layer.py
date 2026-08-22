"""生成 16×16、三层、层高 10 的移动地块动力层结构 NBT。"""

from __future__ import annotations

import gzip
import struct
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/mobile_plot_power_layer.nbt"
DATA_VERSION = 3955
SIZE = 16
FLOOR_LEVELS = (0, 10, 20, 30)
BRASS_CASING = "create:brass_casing"
INDUSTRIAL_IRON = "create:industrial_iron_block"


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


def is_perimeter(x: int, z: int) -> bool:
    return x == 0 or x == SIZE - 1 or z == 0 or z == SIZE - 1


def build_structure() -> bytes:
    perimeter_blocks = SIZE * 4 - 4
    assert len(FLOOR_LEVELS) == 4
    assert FLOOR_LEVELS == (0, 10, 20, 30)
    assert perimeter_blocks * len(FLOOR_LEVELS) == 240
    assert (SIZE * SIZE - perimeter_blocks) * len(FLOOR_LEVELS) == 784
    palette = [palette_entry(BRASS_CASING), palette_entry(INDUSTRIAL_IRON)]
    blocks = [
        block_entry((x, y, z), 1 if is_perimeter(x, z) else 0)
        for y in FLOOR_LEVELS
        for x in range(SIZE)
        for z in range(SIZE)
    ]
    root = compound_payload(
        [
            named(3, "DataVersion", int_payload(DATA_VERSION)),
            named(9, "size", list_payload(3, [int_payload(SIZE), int_payload(31), int_payload(SIZE)])),
            named(9, "palette", list_payload(10, palette)),
            named(9, "blocks", list_payload(10, blocks)),
            named(9, "entities", list_payload(10, [])),
        ]
    )
    return bytes([10]) + utf("") + root


def main() -> None:
    OUTPUT.parent.mkdir(parents=True, exist_ok=True)
    with gzip.GzipFile(filename=str(OUTPUT), mode="wb", mtime=0) as stream:
        stream.write(build_structure())
    print(f"Generated {OUTPUT} (16x31x16, 784 brass casing, 240 industrial iron)")


if __name__ == "__main__":
    main()

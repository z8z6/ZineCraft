"""Generate temporary block-box templates for all registered nation shops."""

from __future__ import annotations

import gzip
import struct
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
STRUCTURE_ROOT = ROOT / "src/main/resources/data/zinecraft/structure"
NATIONS = (
    "aegir",
    "bolivar",
    "higashi",
    "durin",
    "columbia",
    "kazimierz",
    "kazdel",
    "laterano",
    "leithanien",
    "rim_billiton",
    "minos",
    "sargon",
    "sami",
    "victoria",
    "ursus",
    "kjerag",
    "siracusa",
    "siesta",
    "yan",
    "iberia",
)
DATA_VERSION = 3955


def utf(value: str) -> bytes:
    encoded = value.encode("utf-8")
    return struct.pack(">H", len(encoded)) + encoded


def named(tag_type: int, name: str, payload: bytes) -> bytes:
    return bytes([tag_type]) + utf(name) + payload


def integer(value: int) -> bytes:
    return struct.pack(">i", value)


def list_payload(tag_type: int, values: list[bytes]) -> bytes:
    return bytes([tag_type]) + struct.pack(">i", len(values)) + b"".join(values)


def compound(tags: list[bytes]) -> bytes:
    return b"".join(tags) + b"\x00"


def palette_entry(name: str) -> bytes:
    return compound([named(8, "Name", utf(name))])


def block_entry(x: int, y: int, z: int, state: int) -> bytes:
    return compound([
        named(9, "pos", list_payload(3, [integer(x), integer(y), integer(z)])),
        named(3, "state", integer(state)),
    ])


def write_shop(nation: str) -> Path:
    width, height, depth = 11, 6, 9
    blocks = []
    for x in range(width):
        for y in range(height):
            for z in range(depth):
                boundary = x in {0, width - 1} or y in {0, height - 1} or z in {0, depth - 1}
                doorway = x == width // 2 and z == 0 and y in {1, 2}
                blocks.append(block_entry(x, y, z, 0 if boundary and not doorway else 1))
    root = compound([
        named(3, "DataVersion", integer(DATA_VERSION)),
        named(9, "size", list_payload(3, [integer(width), integer(height), integer(depth)])),
        named(9, "palette", list_payload(10, [
            palette_entry("minecraft:stone_bricks"),
            palette_entry("minecraft:air"),
        ])),
        named(9, "blocks", list_payload(10, blocks)),
        named(9, "entities", list_payload(10, [])),
    ])
    target = STRUCTURE_ROOT / f"{nation}_shop.nbt"
    with gzip.GzipFile(filename=str(target), mode="wb", mtime=0) as stream:
        stream.write(bytes([10]) + utf("") + root)
    return target


def main() -> None:
    written = [write_shop(nation) for nation in NATIONS]
    assert len(written) == len(NATIONS) and all(path.is_file() for path in written)
    print(f"Generated {len(written)} temporary nation shop blockouts")


if __name__ == "__main__":
    main()

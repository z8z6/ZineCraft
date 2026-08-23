"""Generate temporary block-box templates for all registered nation shops."""

from __future__ import annotations

import gzip
import struct
from pathlib import Path

from generate_city_building_matchboxes import write_matchbox


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
    target = STRUCTURE_ROOT / f"{nation}_shop.nbt"
    write_matchbox(target, 1, 1)
    return target


def main() -> None:
    written = [write_shop(nation) for nation in NATIONS]
    assert len(written) == len(NATIONS) and all(path.is_file() for path in written)
    print(f"Generated {len(written)} temporary nation shop blockouts")


if __name__ == "__main__":
    main()

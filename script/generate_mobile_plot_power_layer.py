"""生成三个独立的 16×16×16 移动地块层级模板和道路模板。"""

from __future__ import annotations

import gzip
import struct
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
LAYER_OUTPUTS = {
    "power": ROOT / "src/main/resources/data/zinecraft/structure/mobile_plot_power_layer.nbt",
    "support": ROOT / "src/main/resources/data/zinecraft/structure/mobile_plot_support_layer.nbt",
    "life": ROOT / "src/main/resources/data/zinecraft/structure/mobile_plot_life_layer.nbt",
}
ROAD_OUTPUT_DIR = ROOT / "src/main/resources/data/zinecraft/structure/mobile_plot_road"
DATA_VERSION = 3955
SIZE = 16
LAYER_HEIGHT = 16
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


def build_layer() -> bytes:
    palette = [palette_entry(BRASS_CASING), palette_entry(INDUSTRIAL_IRON)]
    blocks: list[bytes] = []
    for y in range(LAYER_HEIGHT):
        for x in range(SIZE):
            for z in range(SIZE):
                floor = y == 0
                horizontal_frame = y in {0, LAYER_HEIGHT - 1} and is_perimeter(x, z)
                vertical_frame = x in {0, SIZE - 1} and z in {0, SIZE - 1}
                if horizontal_frame or vertical_frame:
                    blocks.append(block_entry((x, y, z), 1))
                elif floor:
                    blocks.append(block_entry((x, y, z), 0))
    root = compound_payload(
        [
            named(3, "DataVersion", int_payload(DATA_VERSION)),
            named(9, "size", list_payload(3, [int_payload(SIZE), int_payload(LAYER_HEIGHT), int_payload(SIZE)])),
            named(9, "palette", list_payload(10, palette)),
            named(9, "blocks", list_payload(10, blocks)),
            named(9, "entities", list_payload(10, [])),
        ]
    )
    return bytes([10]) + utf("") + root


def build_road_surface(connections: frozenset[str]) -> bytes:
    palette = [
        palette_entry("minecraft:smooth_stone"),
        palette_entry("minecraft:gray_concrete"),
    ]
    road_min = 3
    road_max = 12

    def is_road(x: int, z: int) -> bool:
        center = road_min <= x <= road_max and road_min <= z <= road_max
        north = "north" in connections and road_min <= x <= road_max and z < road_min
        south = "south" in connections and road_min <= x <= road_max and z > road_max
        west = "west" in connections and x < road_min and road_min <= z <= road_max
        east = "east" in connections and x > road_max and road_min <= z <= road_max
        return center or north or south or west or east

    blocks = [block_entry((x, 0, z), 1 if is_road(x, z) else 0) for x in range(SIZE) for z in range(SIZE)]
    root = compound_payload(
        [
            named(3, "DataVersion", int_payload(DATA_VERSION)),
            named(9, "size", list_payload(3, [int_payload(SIZE), int_payload(1), int_payload(SIZE)])),
            named(9, "palette", list_payload(10, palette)),
            named(9, "blocks", list_payload(10, blocks)),
            named(9, "entities", list_payload(10, [])),
        ]
    )
    return bytes([10]) + utf("") + root


def main() -> None:
    layer_data = build_layer()
    for output in LAYER_OUTPUTS.values():
        output.parent.mkdir(parents=True, exist_ok=True)
        with gzip.GzipFile(filename=str(output), mode="wb", mtime=0) as stream:
            stream.write(layer_data)
    road_templates = {
        "isolated": frozenset(),
        "end": frozenset({"north"}),
        "straight": frozenset({"north", "south"}),
        "corner": frozenset({"north", "east"}),
        "tee": frozenset({"north", "east", "west"}),
        "cross": frozenset({"north", "east", "south", "west"}),
    }
    ROAD_OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
    for name, connections in road_templates.items():
        target = ROAD_OUTPUT_DIR / f"{name}.nbt"
        with gzip.GzipFile(filename=str(target), mode="wb", mtime=0) as stream:
            stream.write(build_road_surface(connections))
    print(f"Generated {len(LAYER_OUTPUTS)} independent 16x16x16 mobile-plot layer NBT templates")
    print(f"Generated {len(road_templates)} road NBT templates in {ROAD_OUTPUT_DIR}")


if __name__ == "__main__":
    main()

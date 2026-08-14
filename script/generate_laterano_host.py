"""生成固定于泰拉原点地下的拉特兰 PCS 主机结构模板。

造型依据 PRTS 对“大教堂地下最深处”“银色山脉”和人格与认知同步系统的文字描述进行
Minecraft 化转译：外部是逐层收束的银色机械山体，内部是同心环与垂直同步核心。
脚本不复制官方关卡贴图、标志或客户端模型。
"""

from __future__ import annotations

import gzip
import struct
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/laterano_host/core.nbt"
DATA_VERSION = 3955
SIZE = (33, 29, 33)

AIR = 0
CASING = 1
CONDUIT = 2
MARBLE = 3
TINTED_GLASS = 4
SYNC_GLASS = 5
GOLD = 6

PALETTE = [
    "minecraft:air",
    "zinecraft:laterano_host_casing",
    "zinecraft:laterano_host_conduit",
    "zinecraft:laterano_basilica_marble",
    "minecraft:tinted_glass",
    "minecraft:light_blue_stained_glass",
    "minecraft:gold_block",
]


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


class Template:
    def __init__(self) -> None:
        self.blocks: dict[tuple[int, int, int], int] = {}

    def block(self, x: int, y: int, z: int, state: int) -> None:
        sx, sy, sz = SIZE
        if not (0 <= x < sx and 0 <= y < sy and 0 <= z < sz):
            raise ValueError(f"方块坐标越界: {(x, y, z)}")
        self.blocks[(x, y, z)] = state

    def cuboid(
        self,
        start: tuple[int, int, int],
        end: tuple[int, int, int],
        state: int,
    ) -> None:
        for x in range(start[0], end[0] + 1):
            for y in range(start[1], end[1] + 1):
                for z in range(start[2], end[2] + 1):
                    self.block(x, y, z, state)

    def write(self) -> None:
        palette = [palette_entry(name) for name in PALETTE]
        blocks = [block_entry(pos, state) for pos, state in sorted(self.blocks.items())]
        root = compound_payload(
            [
                named(3, "DataVersion", int_payload(DATA_VERSION)),
                named(9, "size", list_payload(3, [int_payload(value) for value in SIZE])),
                named(9, "palette", list_payload(10, palette)),
                named(9, "blocks", list_payload(10, blocks)),
                named(9, "entities", list_payload(10, [])),
            ]
        )
        OUTPUT.parent.mkdir(parents=True, exist_ok=True)
        with gzip.GzipFile(filename=str(OUTPUT), mode="wb", mtime=0) as stream:
            stream.write(bytes([10]) + utf("") + root)


def octagonal_distance(x: int, z: int) -> int:
    dx = abs(x - 16)
    dz = abs(z - 16)
    return max(dx, dz) + min(dx, dz) // 2


def shell_radius(y: int) -> int:
    if y <= 3:
        return 16
    if y <= 7:
        return 15
    if y <= 11:
        return 14
    if y <= 15:
        return 13
    if y <= 19:
        return 11
    if y <= 23:
        return 9
    if y <= 26:
        return 6
    return 3


def add_halo(template: Template, y: int, radius: int) -> None:
    """用离散圆环表达 PCS 的同步波与萨科塔光环意象。"""
    for x in range(16 - radius, 16 + radius + 1):
        for z in range(16 - radius, 16 + radius + 1):
            distance = (x - 16) ** 2 + (z - 16) ** 2
            if radius * radius - radius <= distance <= radius * radius + radius:
                template.block(x, y, z, CONDUIT)


def build() -> Template:
    template = Template()

    # 银色山脉外壳：逐层收束的八边形机械穹体，同时用空气模板清出内部大厅。
    for y in range(SIZE[1]):
        outer = shell_radius(y)
        for x in range(SIZE[0]):
            for z in range(SIZE[2]):
                distance = octagonal_distance(x, z)
                if distance <= outer:
                    if y == 0 or distance >= outer - 1:
                        state = CASING
                    else:
                        state = AIR
                    template.block(x, y, z, state)

    # 地下圣堂式基座和从南侧进入核心的中轴。
    template.cuboid((3, 1, 3), (29, 1, 29), CASING)
    for inset in (4, 8, 12):
        for x in range(inset, 33 - inset):
            template.block(x, 2, inset, MARBLE)
            template.block(x, 2, 32 - inset, MARBLE)
        for z in range(inset, 33 - inset):
            template.block(inset, 2, z, MARBLE)
            template.block(32 - inset, 2, z, MARBLE)
    template.cuboid((14, 2, 20), (18, 2, 31), MARBLE)

    # 四组记忆柱围绕中心排列，青色玻璃表现同步数据流。
    for x, z in ((8, 8), (24, 8), (8, 24), (24, 24)):
        template.cuboid((x - 1, 2, z - 1), (x + 1, 10, z + 1), CASING)
        template.cuboid((x, 3, z), (x, 9, z), SYNC_GLASS)
        template.block(x, 11, z, CONDUIT)

    # PCS 垂直主核心：黑色隔离层包覆发光同步导管，上下由金色节点锁定。
    template.cuboid((13, 2, 13), (19, 23, 19), TINTED_GLASS)
    template.cuboid((14, 3, 14), (18, 22, 18), AIR)
    template.cuboid((15, 3, 15), (17, 22, 17), SYNC_GLASS)
    template.cuboid((16, 2, 16), (16, 25, 16), CONDUIT)
    template.cuboid((15, 2, 15), (17, 2, 17), GOLD)
    template.cuboid((15, 23, 15), (17, 23, 17), GOLD)

    add_halo(template, 7, 5)
    add_halo(template, 13, 8)
    add_halo(template, 20, 10)

    # 四向银色承力鳍连接主机与“山体”，营造前文明设备被宗教建筑包裹的反差。
    for offset in range(4, 13):
        height = 4 + (12 - offset) // 2
        for y in range(3, height + 1):
            template.block(16 + offset, y, 16, CASING)
            template.block(16 - offset, y, 16, CASING)
            template.block(16, y, 16 + offset, CASING)
            template.block(16, y, 16 - offset, CASING)
        template.block(16 + offset, height + 1, 16, CONDUIT)
        template.block(16 - offset, height + 1, 16, CONDUIT)
        template.block(16, height + 1, 16 + offset, CONDUIT)
        template.block(16, height + 1, 16 - offset, CONDUIT)

    # 南侧检修入口；显式空气会在地下地形中清出通道。
    template.cuboid((13, 2, 27), (19, 8, 32), CASING)
    template.cuboid((15, 3, 26), (17, 6, 32), AIR)
    template.cuboid((15, 2, 26), (17, 2, 32), MARBLE)
    template.block(14, 6, 30, CONDUIT)
    template.block(18, 6, 30, CONDUIT)

    return template


if __name__ == "__main__":
    result = build()
    result.write()
    print(f"Generated Laterano host template with {len(result.blocks)} blocks at {OUTPUT}")

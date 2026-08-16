"""Low-level NBT primitives and neutral validation for isolated builders.

This module deliberately contains no house, room, facade, roof, road, or
silhouette generator.  Nation modules must place their own geometry explicitly;
only serialization, single-block/cuboid mutation, connectors, and generic
quality gates are shared here.
"""

from __future__ import annotations

import argparse
import gzip
import hashlib
import struct
from collections import deque
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
PREVIEW_ROOT = ROOT / "build/structure_previews/nation_settlements_batch_1"
DATA_VERSION = 3955
AIR = "minecraft:air"
JIGSAW = "minecraft:jigsaw"
LIGHT_BLOCKS = {
    "minecraft:sea_lantern",
    "minecraft:ochre_froglight",
    "minecraft:shroomlight",
    "minecraft:end_rod",
    "minecraft:light",
}
CONTAINERS = {"minecraft:barrel", "minecraft:chest"}
THIN_SUPPORT_EXCLUSIONS = {
    "minecraft:chain",
    "minecraft:iron_bars",
    "create:shaft",
    "create:fluid_pipe",
    "create:mechanical_pump",
}
ORIENTATION = {direction: f"{direction}_up" for direction in ("north", "south", "east", "west")}


def block_id(value: str) -> str:
    return value if ":" in value else f"minecraft:{value}"


def _utf(value: str) -> bytes:
    encoded = value.encode("utf-8")
    return struct.pack(">H", len(encoded)) + encoded


def _named(tag_type: int, name: str, payload: bytes) -> bytes:
    return bytes([tag_type]) + _utf(name) + payload


def _string(value: str) -> bytes:
    return _utf(value)


def _integer(value: int) -> bytes:
    return struct.pack(">i", value)


def _list(tag_type: int, values: list[bytes]) -> bytes:
    return bytes([tag_type]) + struct.pack(">i", len(values)) + b"".join(values)


def _compound(tags: list[bytes]) -> bytes:
    return b"".join(tags) + b"\x00"


def _palette_entry(name: str, properties: tuple[tuple[str, str], ...]) -> bytes:
    tags = [_named(8, "Name", _string(name))]
    if properties:
        tags.append(_named(10, "Properties", _compound([
            _named(8, key, _string(value)) for key, value in properties
        ])))
    return _compound(tags)


def _block_entry(position: tuple[int, int, int], state: int, nbt: dict[str, str | int] | None) -> bytes:
    tags = [
        _named(9, "pos", _list(3, [_integer(value) for value in position])),
        _named(3, "state", _integer(state)),
    ]
    if nbt:
        payload = []
        for key, value in nbt.items():
            if isinstance(value, int):
                payload.append(_named(3, key, _integer(value)))
            else:
                payload.append(_named(8, key, _string(value)))
        tags.append(_named(10, "nbt", _compound(payload)))
    return _compound(tags)


class Template:
    """Mutable low-level Structure Template representation.

    Validation annotations describe observable requirements only.  They do not
    create geometry and therefore cannot turn into a cross-national design
    profile.
    """

    def __init__(self, nation: str, settlement: str, name: str, size: tuple[int, int, int], category: str):
        self.nation = nation
        self.settlement = settlement
        self.name = name
        self.size = size
        self.category = category
        self.palette: list[tuple[str, tuple[tuple[str, str], ...]]] = []
        self._palette_index: dict[tuple[str, tuple[tuple[str, str], ...]], int] = {}
        self.blocks: dict[tuple[int, int, int], tuple[int, dict[str, str | int] | None]] = {}
        self.walk_targets: list[tuple[str, tuple[int, int, int]]] = []
        self.walk_regions: list[tuple[str, tuple[int, int, int], tuple[int, int, int]]] = []

    @property
    def label(self) -> str:
        return f"{self.settlement}/{self.name}"

    def state(self, name: str, properties: dict[str, str] | None = None) -> int:
        key = (block_id(name), tuple(sorted((properties or {}).items())))
        if key not in self._palette_index:
            self._palette_index[key] = len(self.palette)
            self.palette.append(key)
        return self._palette_index[key]

    def block(
        self,
        x: int,
        y: int,
        z: int,
        name: str,
        properties: dict[str, str] | None = None,
        nbt: dict[str, str | int] | None = None,
    ) -> None:
        sx, sy, sz = self.size
        if not (0 <= x < sx and 0 <= y < sy and 0 <= z < sz):
            raise ValueError(f"{self.label}: block outside size {(x, y, z)} / {self.size}")
        self.blocks[(x, y, z)] = (self.state(name, properties), nbt)

    def cuboid(self, start: tuple[int, int, int], end: tuple[int, int, int], name: str) -> None:
        for x in range(start[0], end[0] + 1):
            for y in range(start[1], end[1] + 1):
                for z in range(start[2], end[2] + 1):
                    self.block(x, y, z, name)

    def clear(self, start: tuple[int, int, int], end: tuple[int, int, int]) -> None:
        self.cuboid(start, end, AIR)

    def connector(
        self,
        x: int,
        y: int,
        z: int,
        direction: str,
        name: str,
        target: str,
        pool: str,
        final_state: str,
    ) -> None:
        # Street-side building sockets are parent connectors.  Nation builders
        # still choose every socket position explicitly; this shared method
        # only enforces the Jigsaw protocol that attaches the nation's
        # ``buildings`` pool.  Building templates use the same connector name
        # as terminal children and therefore keep minecraft:empty.
        building_socket = f"zinecraft:{self.settlement}/building"
        if self.category == "street" and name == building_socket:
            target = building_socket
            pool = f"zinecraft:{self.settlement}/buildings"
            road_value = self.blocks.get((15, 0, 15))
            if road_value is None or self.palette[road_value[0]][0] in {AIR, JIGSAW}:
                raise ValueError(f"{self.label}: cannot resolve national road surface for building socket")
            inward = {
                "west": (1, 0), "east": (-1, 0),
                "north": (0, 1), "south": (0, -1),
            }[direction]
            px, pz = x, z
            while not (12 <= px <= 19 and 12 <= pz <= 19):
                self.blocks[(px, y - 1, pz)] = (road_value[0], None)
                self.block(px, y, pz, AIR)
                self.block(px, y + 1, pz, AIR)
                px += inward[0]
                pz += inward[1]
        elif self.category == "building" and name == building_socket:
            inward = {
                "west": (1, 0), "east": (-1, 0),
                "north": (0, 1), "south": (0, -1),
            }[direction]
            support = self.blocks.get((x + inward[0], y - 1, z + inward[1]))
            if support is None or self.palette[support[0]][0] in {AIR, JIGSAW}:
                raise ValueError(f"{self.label}: building connector has no inward foundation support")
            self.blocks[(x, y - 1, z)] = (support[0], None)
        nbt: dict[str, str | int] = {
            "id": JIGSAW,
            "name": name,
            "target": target,
            "pool": pool,
            "final_state": block_id(final_state),
            "joint": "rollable",
            "selection_priority": 0,
            "placement_priority": 0,
        }
        self.block(x, y, z, JIGSAW, {"orientation": ORIENTATION[direction]}, nbt)

    def require_reachable(self, label: str, position: tuple[int, int, int]) -> None:
        self.walk_targets.append((label, position))

    def require_walk_region(
        self,
        label: str,
        start: tuple[int, int, int],
        end: tuple[int, int, int],
    ) -> None:
        self.walk_regions.append((label, start, end))

    def block_name(self, position: tuple[int, int, int]) -> str | None:
        value = self.blocks.get(position)
        return self.palette[value[0]][0] if value else None

    def block_properties(self, position: tuple[int, int, int]) -> dict[str, str]:
        value = self.blocks.get(position)
        if value is None:
            return {}
        return dict(self.palette[value[0]][1])

    def resolved_block_name(self, position: tuple[int, int, int]) -> str | None:
        """Return the post-Jigsaw block used by player-space validation."""
        value = self.blocks.get(position)
        if value is None:
            return None
        name = self.palette[value[0]][0]
        if name == JIGSAW and value[1]:
            return block_id(str(value[1].get("final_state", AIR)).split("[", 1)[0])
        return name

    def write(self, output_root: Path) -> Path:
        palette = [_palette_entry(name, properties) for name, properties in self.palette]
        blocks = [_block_entry(pos, state, nbt) for pos, (state, nbt) in sorted(self.blocks.items())]
        root = _compound([
            _named(3, "DataVersion", _integer(DATA_VERSION)),
            _named(9, "size", _list(3, [_integer(value) for value in self.size])),
            _named(9, "palette", _list(10, palette)),
            _named(9, "blocks", _list(10, blocks)),
            _named(9, "entities", _list(10, [])),
        ])
        target = output_root / self.settlement / f"{self.name}.nbt"
        target.parent.mkdir(parents=True, exist_ok=True)
        with gzip.GzipFile(filename=str(target), mode="wb", mtime=0) as stream:
            stream.write(bytes([10]) + _utf("") + root)
        return target


def _player_passable(name: str | None) -> bool:
    return name in {None, AIR} or bool(name and (
        name.endswith(("_door", ":ladder", ":scaffolding")) or "trapdoor" in name
    ))


def _light_transparent(name: str | None) -> bool:
    if _player_passable(name) or name in LIGHT_BLOCKS:
        return True
    assert name is not None
    return "glass" in name or name.endswith(("_pane", "_fence", "_stairs", "_slab")) or name == "minecraft:iron_bars"


def _support(name: str | None) -> bool:
    return bool(name and not _player_passable(name) and name not in THIN_SUPPORT_EXCLUSIONS and not name.endswith(("_pane", "_fence")))


def _standing_nodes(template: Template) -> set[tuple[int, int, int]]:
    w, h, d = template.size
    nodes = set()
    for x in range(w):
        for y in range(1, h - 1):
            for z in range(d):
                foot = template.resolved_block_name((x, y, z))
                head = template.resolved_block_name((x, y + 1, z))
                climbable = bool(foot and foot.endswith((":ladder", ":scaffolding")))
                if _player_passable(foot) and _player_passable(head) and (
                    climbable or _support(template.resolved_block_name((x, y - 1, z)))
                ):
                    nodes.add((x, y, z))
    return nodes


def _navigation(
    template: Template,
    nodes: set[tuple[int, int, int]],
    start: tuple[int, int, int],
) -> set[tuple[int, int, int]]:
    reached = {start}
    queue = deque([start])
    while queue:
        x, y, z = queue.popleft()
        current_foot = template.resolved_block_name((x, y, z))
        current_floor = template.resolved_block_name((x, y - 1, z))
        current_climbable = bool(current_foot and current_foot.endswith((":ladder", ":scaffolding")))
        for dx, dz in ((1, 0), (-1, 0), (0, 1), (0, -1)):
            for dy in (-1, 0, 1):
                nxt = (x + dx, y + dy, z + dz)
                target_foot = template.resolved_block_name(nxt)
                target_floor = template.resolved_block_name((nxt[0], nxt[1] - 1, nxt[2]))
                target_climbable = bool(target_foot and target_foot.endswith((":ladder", ":scaffolding")))
                ascending = dy == 1 and bool(target_floor and target_floor.endswith(("_stairs", "_slab")))
                descending = dy == -1 and bool(current_floor and current_floor.endswith(("_stairs", "_slab")))
                if nxt in nodes and nxt not in reached and (
                    dy == 0 or ascending or descending or current_climbable or target_climbable
                ):
                    reached.add(nxt)
                    queue.append(nxt)
        if current_climbable:
            for nxt in ((x, y + 1, z), (x, y - 1, z)):
                if nxt in nodes and nxt not in reached:
                    reached.add(nxt)
                    queue.append(nxt)
    return reached


def _lit_voxels(template: Template, sources: list[tuple[int, int, int]]) -> set[tuple[int, int, int]]:
    w, h, d = template.size
    distance = {source: 0 for source in sources}
    queue = deque(sources)
    while queue:
        x, y, z = queue.popleft()
        current = distance[(x, y, z)]
        if current >= 14:
            continue
        for nxt in ((x + 1, y, z), (x - 1, y, z), (x, y + 1, z), (x, y - 1, z), (x, y, z + 1), (x, y, z - 1)):
            nx, ny, nz = nxt
            if not (0 <= nx < w and 0 <= ny < h and 0 <= nz < d):
                continue
            if nxt in distance or not _light_transparent(template.resolved_block_name(nxt)):
                continue
            distance[nxt] = current + 1
            queue.append(nxt)
    return set(distance)


def _validate_connectors(template: Template) -> None:
    connectors = []
    for pos, (state, nbt) in template.blocks.items():
        if template.palette[state][0] == JIGSAW:
            assert nbt and nbt.get("id") == JIGSAW, f"{template.label}: malformed connector at {pos}"
            assert nbt.get("name") and nbt.get("target") and nbt.get("pool") and nbt.get("final_state")
            connectors.append(pos)
            building_socket = f"zinecraft:{template.settlement}/building"
            if nbt.get("name") == building_socket:
                if template.category == "street":
                    assert nbt.get("target") == building_socket, (
                        f"{template.label}: street building socket does not target its child name at {pos}"
                    )
                    assert nbt.get("pool") == f"zinecraft:{template.settlement}/buildings", (
                        f"{template.label}: street building socket does not reference the buildings pool at {pos}"
                    )
                    x, y, z = pos
                    inward = {
                        0: (1, 0), template.size[0] - 1: (-1, 0),
                    }.get(x)
                    if inward is None:
                        inward = {
                            0: (0, 1), template.size[2] - 1: (0, -1),
                        }.get(z)
                    assert inward is not None, f"{template.label}: building socket is not on an edge at {pos}"
                    px, pz = x, z
                    while not (12 <= px <= 19 and 12 <= pz <= 19):
                        assert _support(template.block_name((px, y - 1, pz))), (
                            f"{template.label}: building spur lacks floor at {(px, y - 1, pz)}"
                        )
                        foot_name = template.block_name((px, y, pz))
                        assert (foot_name == JIGSAW and (px, pz) == (x, z) or _player_passable(foot_name)) \
                            and _player_passable(template.block_name((px, y + 1, pz))), (
                                f"{template.label}: building spur lacks two-block clearance at {(px, y, pz)}"
                            )
                        px += inward[0]
                        pz += inward[1]
                elif template.category == "building":
                    assert nbt.get("target") == "minecraft:empty" and nbt.get("pool") == "minecraft:empty", (
                        f"{template.label}: building entrance must remain a terminal child connector at {pos}"
                    )
                    x, y, z = pos
                    assert _support(template.block_name((x, y - 1, z))), (
                        f"{template.label}: building connector has no floor at {(x, y - 1, z)}"
                    )
            if str(nbt.get("target", "")).endswith("/street"):
                x, _, z = pos
                final_state = str(nbt["final_state"])
                if z in (0, template.size[2] - 1):
                    edge = [template.block_name((edge_x, 0, z)) for edge_x in range(template.size[0])]
                elif x in (0, template.size[0] - 1):
                    edge = [template.block_name((x, 0, edge_z)) for edge_z in range(template.size[2])]
                else:
                    raise AssertionError(f"{template.label}: street connector is not on a module edge at {pos}")
                assert edge.count(final_state) == 8, f"{template.label}: connected road edge is not an 8-block section at {pos}"
    assert connectors, f"{template.label}: missing Jigsaw connector"


def _validate_building(template: Template) -> None:
    w, h, d = template.size
    assert 17 <= w <= 31, f"{template.label}: frontage {w} outside 17..31"
    assert 24 <= d <= 46, f"{template.label}: depth {d} outside 24..46"
    assert 14 <= h <= 34, f"{template.label}: height {h} outside 14..34"
    solid = [pos for pos in template.blocks if template.block_name(pos) not in {None, AIR, JIGSAW}]
    assert solid, f"{template.label}: empty building"
    min_x, max_x = min(p[0] for p in solid), max(p[0] for p in solid)
    min_y, max_y = min(p[1] for p in solid), max(p[1] for p in solid)
    min_z, max_z = min(p[2] for p in solid), max(p[2] for p in solid)
    hull = (max_x - min_x + 1) * (max_y - min_y + 1) * (max_z - min_z + 1)
    void_ratio = 1.0 - len(solid) / hull
    assert void_ratio >= 0.10, f"{template.label}: occupied hull leaves only {void_ratio:.1%} void"

    # A rectangular extrusion has one roof height and occupies all four upper
    # corners.  Require stepped/cut/open massing observable from block data.
    roofline: dict[tuple[int, int], int] = {}
    for x, y, z in solid:
        roofline[(x, z)] = max(roofline.get((x, z), 0), y)
    assert len(set(roofline.values())) >= 3, f"{template.label}: insufficient roofline variation"
    upper = max_y - max(2, (max_y - min_y) // 4)
    upper_corners = sum(
        1 for x, z in ((min_x, min_z), (min_x, max_z), (max_x, min_z), (max_x, max_z))
        if any(px == x and pz == z and py >= upper for px, py, pz in solid)
    )
    assert upper_corners <= 3, f"{template.label}: four-square upper mass remains"

    nodes = _standing_nodes(template)
    assert template.walk_targets, f"{template.label}: no declared player route"
    entrance = template.walk_targets[0][1]
    assert entrance in nodes, f"{template.label}: entrance target is not standable at {entrance}"
    building_socket = f"zinecraft:{template.settlement}/building"
    sockets = [
        pos for pos, (state, nbt) in template.blocks.items()
        if template.palette[state][0] == JIGSAW and nbt and nbt.get("name") == building_socket
    ]
    assert len(sockets) == 1, f"{template.label}: expected one terminal building socket"
    socket = sockets[0]
    assert template.resolved_block_name(socket) == AIR, f"{template.label}: socket final state is not air"
    assert socket in nodes, f"{template.label}: resolved building socket is not standable at {socket}"
    reached = _navigation(template, nodes, socket)
    assert entrance in reached, f"{template.label}: socket cannot reach first room through the outer door"
    required = list(template.walk_targets)
    for label, start, end in template.walk_regions:
        for x in range(start[0], end[0] + 1):
            for y in range(start[1], end[1] + 1):
                for z in range(start[2], end[2] + 1):
                    pos = (x, y, z)
                    if pos in nodes:
                        required.append((label, pos))
    for label, pos in required:
        assert pos in nodes, f"{template.label}: {label} is not standable at {pos}"
        assert pos in reached, f"{template.label}: {label} is unreachable at {pos}"

    lights = [pos for pos in template.blocks if template.block_name(pos) in LIGHT_BLOCKS]
    assert lights, f"{template.label}: no primary lighting"
    for x, y, z in lights:
        neighbours = ((x + 1, y, z), (x - 1, y, z), (x, y + 1, z), (x, y - 1, z), (x, y, z + 1), (x, y, z - 1))
        assert any(_support(template.block_name(p)) for p in neighbours), f"{template.label}: floating light at {(x, y, z)}"
    lit = _lit_voxels(template, lights)
    for label, pos in required:
        assert pos in lit, f"{template.label}: {label} is outside block-light flood at {pos}"

    # Validate the whole entrance-connected interior, not only hand-picked
    # annotations.  A node is indoors when any non-air block roofs its column.
    roofed_reached = {
        (x, y, z) for x, y, z in reached
        if any(
            template.resolved_block_name((x, roof_y, z)) not in {None, AIR}
            for roof_y in range(y + 2, h)
        )
    }
    dark = sorted(roofed_reached - lit)
    assert not dark, f"{template.label}: {len(dark)} reachable roofed nodes are dark; first={dark[:5]}"

    lower_doors = []
    for pos in template.blocks:
        name = template.block_name(pos)
        properties = template.block_properties(pos)
        if name and name.endswith("_door") and properties.get("half") == "lower":
            lower_doors.append(pos)
            upper = (pos[0], pos[1] + 1, pos[2])
            assert template.block_name(upper) == name and template.block_properties(upper).get("half") == "upper", (
                f"{template.label}: door pair is incomplete at {pos}"
            )
            facing = properties.get("facing")
            axis = (0, 1) if facing in {"north", "south"} else (1, 0)
            sides = ((pos[0] - axis[0], pos[1], pos[2] - axis[1]),
                     (pos[0] + axis[0], pos[1], pos[2] + axis[1]))
            assert all(side in nodes for side in sides), f"{template.label}: door lacks two-sided clearance at {pos}"
            assert any(side in reached for side in sides), f"{template.label}: door is disconnected at {pos}"
    assert len(lower_doors) >= 2, f"{template.label}: requires an outer door and at least one explicit internal door"

    containers = [(pos, nbt) for pos, (_, nbt) in template.blocks.items() if template.block_name(pos) in CONTAINERS]
    assert containers, f"{template.label}: no loot container"
    for (x, y, z), nbt in containers:
        assert _support(template.block_name((x, y - 1, z))), f"{template.label}: unsupported container at {(x, y, z)}"
        assert nbt and nbt.get("LootTable") == f"zinecraft:chests/nation/{template.nation}_structure"
        assert _player_passable(template.resolved_block_name((x, y + 1, z))), (
            f"{template.label}: container cannot open at {(x, y, z)}"
        )
        adjacent = ((x + 1, y, z), (x - 1, y, z), (x, y, z + 1), (x, y, z - 1))
        assert any(pos in reached for pos in adjacent), (
            f"{template.label}: container has no reachable adjacent standing position at {(x, y, z)}"
        )
    assert not any("concrete" in name for name, _ in template.palette), f"{template.label}: concrete placeholder remains"


def validate_nation(templates: list[Template], nation: str, settlement: str) -> None:
    expected = {"center", "street_straight", "street_corner", "street_cross", "street_end"}
    assert len(templates) == 9, f"{settlement}: expected 9 templates, got {len(templates)}"
    assert all(template.nation == nation and template.settlement == settlement for template in templates)
    assert len({template.name for template in templates}) == 9
    assert expected <= {template.name for template in templates}
    buildings = [template for template in templates if template.category == "building"]
    assert len(buildings) == 4
    assert all(template.size[0] == 32 and template.size[2] == 32 for template in templates if template.category != "building")
    for template in templates:
        _validate_connectors(template)
        for position in (pos for pos in template.blocks if template.block_name(pos) in LIGHT_BLOCKS):
            x, y, z = position
            neighbours = (
                (x + 1, y, z), (x - 1, y, z), (x, y + 1, z),
                (x, y - 1, z), (x, y, z + 1), (x, y, z - 1),
            )
            assert any(_support(template.block_name(pos)) for pos in neighbours), (
                f"{template.label}: floating light at {position}"
            )
        if template.category == "building":
            _validate_building(template)


def validate_batch(nation_builds: list[list[Template]]) -> list[Template]:
    templates = [template for group in nation_builds for template in group]
    expected = len(nation_builds) * 9
    assert nation_builds and len(templates) == expected
    assert len({(template.settlement, template.name) for template in templates}) == expected
    assert len({template.nation for template in templates}) == len(nation_builds)
    signatures = []
    for template in templates:
        digest = hashlib.sha256(str(template.size).encode("ascii"))
        for pos in sorted(template.blocks):
            name = template.block_name(pos)
            if name != AIR:
                digest.update(f"{pos}:{name}\n".encode("utf-8"))
        signatures.append(digest.hexdigest())
    assert len(signatures) == len(set(signatures)), "batch contains duplicate block geometry"
    return templates


def write_preview(templates: list[Template], output_root: Path = PREVIEW_ROOT) -> list[Path]:
    return [template.write(output_root) for template in templates]


def output_argument(description: str) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=description)
    parser.add_argument("--output", type=Path, default=PREVIEW_ROOT)
    parser.add_argument("--validate-only", action="store_true")
    return parser.parse_args()

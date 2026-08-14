#!/usr/bin/env python3
"""Import all Phantom & Crimson Solitaire collectibles and their PRTS PNGs.

The textual fields come from the extracted Arknights game-data table. Images are
downloaded unchanged from PRTS's asset host. The generated catalog is consumed by
ModCollectibles at runtime and also drives the Trinkets tag and the source ledger.
"""

from __future__ import annotations

import argparse
import json
import struct
import sys
import time
import urllib.error
import urllib.request
from dataclasses import dataclass
from pathlib import Path
from typing import Any


REPOSITORY_ROOT = Path(__file__).resolve().parents[1]
GAME_DATA_URL = (
    "https://raw.githubusercontent.com/Kengxxiao/ArknightsGameData/master/"
    "zh_CN/gamedata/excel/roguelike_topic_table.json"
)
PRTS_PAGE_URL = "https://prts.wiki/w/傀影与猩红孤钻/长生者宝盒"
PRTS_IMAGE_ROOT = "https://torappu.prts.wiki/assets/roguelike_topic_itempic"
USER_AGENT = "Zinecraft collectible importer/1.0 (+PRTS source ledger)"
EXPECTED_COUNT = 245

CATALOG_PATH = REPOSITORY_ROOT / (
    "src/main/resources/zinecraft/collectibles/phantom_crimson_solitaire.json"
)
TEXTURE_DIRECTORY = REPOSITORY_ROOT / "src/main/resources/assets/zinecraft/textures/item"
TRINKETS_TAG_PATH = REPOSITORY_ROOT / (
    "src/main/resources/data/trinkets/tags/item/chest/relic.json"
)
SOURCE_LEDGER_PATH = REPOSITORY_ROOT / "docs/item/PRTS_COLLECTIBLES.md"
DEFAULT_CACHE_PATH = REPOSITORY_ROOT / "build/prts-cache/roguelike_topic_table.json"

# Keep the public IDs from the first implementation stable for existing worlds.
LEGACY_PATHS = {
    "rogue_1_relic_a11": "collectible_oriron_buckler",
    "rogue_1_relic_a12": "collectible_legion_breastplate",
    "rogue_1_relic_a13": "collectible_old_steam_armor",
    "rogue_1_relic_a14": "collectible_emperors_favor",
    "rogue_1_relic_a15": "collectible_noble_rapier",
    "rogue_1_relic_a16": "collectible_old_guard_edge",
    "rogue_1_relic_a20": "collectible_foul_hemostatic",
    "rogue_1_relic_a21": "collectible_first_aid_kit",
    "rogue_1_relic_a22": "collectible_unknown_instrument",
    "rogue_1_relic_a31": "collectible_stage_perfume",
    "rogue_1_relic_p05": "collectible_bluntclaw_hundred_battles",
    "rogue_1_relic_p07": "collectible_broken_halberd_edge",
    "rogue_1_relic_p10": "collectible_broken_halberd_desperate",
    "rogue_1_relic_p12": "collectible_iron_guard_aggression",
    "rogue_1_relic_p13": "collectible_iron_guard_immovable",
    "rogue_1_relic_p20": "collectible_broken_bow_godspeed",
    "rogue_1_relic_p23": "collectible_broken_staff_chant",
    "rogue_1_relic_p38": "collectible_rusted_blade_lone_soldier",
}

RARITY_MAP = {
    "NORMAL": "UNCOMMON",
    "RARE": "RARE",
    "SUPER_RARE": "EPIC",
}


@dataclass(frozen=True)
class ImportRecord:
    path: str
    order_id: str
    source_id: str
    icon_id: str
    name: str
    original_effect: str
    description: str
    rarity: str

    def to_json(self) -> dict[str, str]:
        return {
            "path": self.path,
            "orderId": self.order_id,
            "sourceId": self.source_id,
            "iconId": self.icon_id,
            "zhCn": self.name,
            # PRTS does not supply an official English localization. Chinese is
            # intentionally retained as the fallback instead of inventing text.
            "enUs": self.name,
            "originalEffectZhCn": self.original_effect,
            "originalEffectEnUs": self.original_effect,
            "descriptionZhCn": self.description,
            "descriptionEnUs": self.description,
            "rarity": self.rarity,
        }


def parse_arguments() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--game-data",
        type=Path,
        help="Use an existing rogue-like topic table instead of downloading it.",
    )
    parser.add_argument(
        "--refresh",
        action="store_true",
        help="Redownload the game-data cache and existing PRTS PNG files.",
    )
    parser.add_argument(
        "--skip-images",
        action="store_true",
        help="Generate metadata only; useful for an offline consistency check.",
    )
    return parser.parse_args()


def download(url: str, destination: Path, refresh: bool = False) -> bool:
    if destination.exists() and not refresh:
        return False
    destination.parent.mkdir(parents=True, exist_ok=True)
    temporary = destination.with_suffix(destination.suffix + ".part")
    request = urllib.request.Request(url, headers={"User-Agent": USER_AGENT})
    last_error: Exception | None = None
    for attempt in range(1, 4):
        try:
            with urllib.request.urlopen(request, timeout=30) as response:
                temporary.write_bytes(response.read())
            temporary.replace(destination)
            return True
        except (OSError, urllib.error.URLError) as error:
            last_error = error
            temporary.unlink(missing_ok=True)
            if attempt < 3:
                time.sleep(attempt)
    raise RuntimeError(f"下载失败：{url}") from last_error


def load_game_data(arguments: argparse.Namespace) -> dict[str, Any]:
    if arguments.game_data:
        source = arguments.game_data.resolve()
        if not source.is_file():
            raise FileNotFoundError(f"找不到游戏数据：{source}")
    else:
        source = DEFAULT_CACHE_PATH
        download(GAME_DATA_URL, source, arguments.refresh)
    return json.loads(source.read_text(encoding="utf-8"))


def build_records(game_data: dict[str, Any]) -> list[ImportRecord]:
    detail = game_data["details"]["rogue_1"]
    items = detail["items"]
    if isinstance(items, dict):
        items = items.values()
    relics = {item["id"]: item for item in items if item["type"] == "RELIC"}
    archive = detail["archiveComp"]["relic"]["relic"]

    if len(relics) != EXPECTED_COUNT or len(archive) != EXPECTED_COUNT:
        raise ValueError(
            f"藏品数量异常：items={len(relics)}, archive={len(archive)}, "
            f"expected={EXPECTED_COUNT}"
        )
    if relics.keys() != archive.keys():
        missing_items = sorted(archive.keys() - relics.keys())
        missing_archive = sorted(relics.keys() - archive.keys())
        raise ValueError(
            f"藏品与档案表不一致：missing_items={missing_items}, "
            f"missing_archive={missing_archive}"
        )

    records: list[ImportRecord] = []
    for source_id, item in relics.items():
        order_id = archive[source_id]["orderId"]
        path = LEGACY_PATHS.get(source_id, f"collectible_is2_{order_id.lower()}")
        required = ("name", "usage", "description", "iconId", "rarity")
        missing = [key for key in required if not item.get(key)]
        if missing:
            raise ValueError(f"{source_id} 缺少字段：{', '.join(missing)}")
        records.append(
            ImportRecord(
                path=path,
                order_id=order_id,
                source_id=source_id,
                icon_id=item["iconId"],
                name=item["name"],
                original_effect=item["usage"],
                description=item["description"],
                rarity=RARITY_MAP[item["rarity"]],
            )
        )

    records.sort(key=lambda record: (record.order_id.startswith("PCS"), record.order_id))
    paths = [record.path for record in records]
    if len(paths) != len(set(paths)):
        raise ValueError("生成的物品 ID 存在重复")
    return records


def write_json(path: Path, value: Any) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(
        json.dumps(value, ensure_ascii=False, indent=2) + "\n",
        encoding="utf-8",
        newline="\n",
    )


def png_size(path: Path) -> tuple[int, int]:
    data = path.read_bytes()
    if len(data) < 24 or data[:8] != b"\x89PNG\r\n\x1a\n" or data[12:16] != b"IHDR":
        raise ValueError(f"不是有效 PNG：{path}")
    return struct.unpack(">II", data[16:24])


def import_images(records: list[ImportRecord], refresh: bool) -> tuple[int, set[tuple[int, int]]]:
    downloaded = 0
    sizes: set[tuple[int, int]] = set()
    for index, record in enumerate(records, start=1):
        target = TEXTURE_DIRECTORY / f"{record.path}.png"
        url = f"{PRTS_IMAGE_ROOT}/{record.icon_id}.png"
        if download(url, target, refresh):
            downloaded += 1
            print(f"[{index:03d}/{EXPECTED_COUNT}] 下载 {record.order_id} {record.name}")
        sizes.add(png_size(target))
    return downloaded, sizes


def markdown_escape(value: str) -> str:
    return value.replace("|", "\\|").replace("\n", "<br>")


def write_source_ledger(records: list[ImportRecord]) -> None:
    lines = [
        "# 集成战略藏品素材来源",
        "",
        "本模块收录 PRTS《傀影与猩红孤钻》“长生者宝盒”的全部藏品：",
        "No.001–238 与 PCS01–PCS07，共 245 件。中文名、编号、原效果和描述来自",
        "明日方舟游戏数据，PNG 直接下载自 PRTS 图片资源域，未重绘、未生成或替换。",
        "",
        f"- PRTS 资料页：<{PRTS_PAGE_URL}>",
        f"- PRTS 图片资源域：<{PRTS_IMAGE_ROOT}/>",
        f"- 游戏数据镜像：<{GAME_DATA_URL}>",
        "- 导入脚本：`script/import_prts_is2_collectibles.py`",
        "",
        "## 逐文件来源",
        "",
        "| 本地文件 | PRTS 原文件 | 藏品 |",
        "| --- | --- | --- |",
    ]
    for record in records:
        lines.append(
            f"| `{record.path}.png` | `{record.icon_id}.png` | "
            f"No.{record.order_id} {markdown_escape(record.name)} |"
        )
    lines.extend(
        [
            "",
            "下载/核对日期：2026-08-14。脚本会校验藏品总数、字段完整性、ID 唯一性和 PNG 文件头。",
            "",
            "## 权利说明",
            "",
            "这些游戏图片与文本原文的权利属于上海鹰角网络科技有限公司及其关联公司；",
            "PRTS 用于资料整理与展示。本项目根目录许可证不应被解释为对这些第三方素材重新授权。",
            "Minecraft 适配效果与项目代码不属于 PRTS 原文。",
            "",
        ]
    )
    SOURCE_LEDGER_PATH.parent.mkdir(parents=True, exist_ok=True)
    SOURCE_LEDGER_PATH.write_text("\n".join(lines), encoding="utf-8", newline="\n")


def main() -> int:
    arguments = parse_arguments()
    records = build_records(load_game_data(arguments))
    write_json(CATALOG_PATH, [record.to_json() for record in records])
    write_json(
        TRINKETS_TAG_PATH,
        {"replace": False, "values": [f"zinecraft:{record.path}" for record in records]},
    )
    write_source_ledger(records)

    downloaded = 0
    sizes: set[tuple[int, int]] = set()
    if not arguments.skip_images:
        downloaded, sizes = import_images(records, arguments.refresh)

    print(f"已生成 {len(records)} 件藏品；本次下载 {downloaded} 张 PNG。")
    if sizes:
        print("PNG 尺寸：" + ", ".join(f"{width}x{height}" for width, height in sorted(sizes)))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (KeyError, ValueError, FileNotFoundError, RuntimeError) as error:
        print(f"错误：{error}", file=sys.stderr)
        raise SystemExit(1) from error

#!/usr/bin/env python3
"""Audit Phantom & Crimson Solitaire source data and import its PRTS PNGs.

The textual fields come from the extracted Arknights game-data table. Images are
downloaded unchanged from PRTS's asset host. Runtime registration lives directly in
ModCollectible.java; the generated JSON is an ignored audit snapshot only.
"""

from __future__ import annotations

import argparse
import hashlib
import json
import os
import re
import shutil
import struct
import sys
import time
import urllib.error
import urllib.request
import tempfile
import unicodedata
from dataclasses import dataclass
from pathlib import Path
from typing import Any


REPOSITORY_ROOT = Path(__file__).resolve().parents[1]
GAME_DATA_URL = (
    "https://raw.githubusercontent.com/Kengxxiao/ArknightsGameData/master/"
    "zh_CN/gamedata/excel/roguelike_topic_table.json"
)
# The upstream mirror's master branch is mutable. Refuse any other snapshot so
# repeated imports cannot silently change committed text.
GAME_DATA_SHA256 = "2d3a34926fc4c71c105e5d5eb2541b81ce52e832393b476761a5001604b1b1f4"
ENGLISH_GAME_DATA_URL = (
    "https://raw.githubusercontent.com/ArknightsAssets/ArknightsGamedata/master/"
    "en/gamedata/excel/roguelike_topic_table.json"
)
ENGLISH_GAME_DATA_SHA256 = "341d50068bc3301e0c14f33e9b23b0b88bea4ad7b82e1fd075de93778c1a4e22"
PRTS_PAGE_URL = "https://prts.wiki/w/傀影与猩红孤钻/长生者宝盒"
PRTS_IMAGE_ROOT = "https://torappu.prts.wiki/assets/roguelike_topic_itempic"
USER_AGENT = "Zinecraft collectible importer/1.0 (+PRTS source ledger)"
EXPECTED_COUNT = 245

AUDIT_CATALOG_PATH = REPOSITORY_ROOT / (
    "build/prts-cache/phantom_crimson_solitaire.json"
)
TEXTURE_DIRECTORY = REPOSITORY_ROOT / "src/main/resources/assets/zinecraft/textures/item"
CURIOS_TAG_PATH = REPOSITORY_ROOT / (
    "src/main/resources/data/curios/tags/item/relic.json"
)
SOURCE_LEDGER_PATH = REPOSITORY_ROOT / "docs/item/PRTS_COLLECTIBLES.md"
IMAGE_DIGEST_MANIFEST_PATH = REPOSITORY_ROOT / "script/data/prts_is2_image_sha256.json"
DEFAULT_CACHE_PATH = REPOSITORY_ROOT / "build/prts-cache/roguelike_topic_table.json"
DEFAULT_ENGLISH_CACHE_PATH = REPOSITORY_ROOT / "build/prts-cache/roguelike_topic_table_en.json"

RARITY_MAP = {
    "NORMAL": "UNCOMMON",
    "RARE": "RARE",
    "SUPER_RARE": "EPIC",
}
ORDER_ID_PATTERN = re.compile(r"(?:[0-9]{3}|PCS[0-9]{2})\Z")
SOURCE_ID_PATTERN = re.compile(r"rogue_1_relic_[a-z0-9_]+\Z")
ITEM_PATH_PATTERN = re.compile(r"[a-z0-9_]+\Z")


@dataclass(frozen=True)
class ImportRecord:
    path: str
    order_id: str
    source_id: str
    icon_id: str
    name: str
    english_name: str
    original_effect: str
    english_original_effect: str
    description: str
    english_description: str
    rarity: str

    def to_json(self) -> dict[str, str]:
        return {
            "path": self.path,
            "orderId": self.order_id,
            "sourceId": self.source_id,
            "iconId": self.icon_id,
            "zhCn": self.name,
            "enUs": self.english_name,
            "originalEffectZhCn": self.original_effect,
            "originalEffectEnUs": self.english_original_effect,
            "descriptionZhCn": self.description,
            "descriptionEnUs": self.english_description,
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
        "--english-game-data",
        type=Path,
        help="Use an existing English rogue-like topic table instead of downloading it.",
    )
    parser.add_argument(
        "--refresh",
        action="store_true",
        help="Redownload the game-data cache and existing PRTS PNG files.",
    )
    parser.add_argument(
        "--skip-images",
        action="store_true",
        help="Do not download images; existing PNGs and their digests are still validated.",
    )
    parser.add_argument(
        "--update-image-digests",
        action="store_true",
        help="Explicitly accept current validated PNG bytes and rewrite their SHA-256 manifest.",
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


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as source:
        for chunk in iter(lambda: source.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def require_expected_game_data(path: Path, expected_digest: str) -> str:
    digest = sha256(path)
    if digest != expected_digest:
        raise ValueError(
            "游戏数据快照 SHA-256 不匹配；请先审查并显式更新脚本中的固定摘要："
            f"expected={expected_digest}, actual={digest}"
        )
    return digest


def load_pinned_game_data(
    explicit_path: Path | None,
    cache_path: Path,
    url: str,
    expected_digest: str,
    refresh: bool,
) -> tuple[dict[str, Any], str]:
    if explicit_path:
        source = explicit_path.resolve()
        if not source.is_file():
            raise FileNotFoundError(f"找不到游戏数据：{source}")
    else:
        source = cache_path
        if refresh or not source.exists():
            candidate = source.with_suffix(source.suffix + ".candidate")
            candidate.unlink(missing_ok=True)
            download(url, candidate, refresh=True)
            try:
                require_expected_game_data(candidate, expected_digest)
                source.parent.mkdir(parents=True, exist_ok=True)
                os.replace(candidate, source)
            finally:
                candidate.unlink(missing_ok=True)
    digest = require_expected_game_data(source, expected_digest)
    return json.loads(source.read_text(encoding="utf-8")), digest


def load_game_data(arguments: argparse.Namespace) -> tuple[dict[str, Any], str, dict[str, Any], str]:
    chinese, chinese_digest = load_pinned_game_data(
        arguments.game_data,
        DEFAULT_CACHE_PATH,
        GAME_DATA_URL,
        GAME_DATA_SHA256,
        arguments.refresh,
    )
    english, english_digest = load_pinned_game_data(
        arguments.english_game_data,
        DEFAULT_ENGLISH_CACHE_PATH,
        ENGLISH_GAME_DATA_URL,
        ENGLISH_GAME_DATA_SHA256,
        arguments.refresh,
    )
    return chinese, chinese_digest, english, english_digest


def english_name_to_path(name: str) -> str:
    ascii_name = unicodedata.normalize("NFKD", name).encode("ascii", "ignore").decode("ascii")
    ascii_name = ascii_name.lower().replace("'", "")
    slug = re.sub(r"_+", "_", re.sub(r"[^a-z0-9]+", "_", ascii_name)).strip("_")
    if not slug:
        raise ValueError(f"英文藏品名无法生成物品 ID：{name!r}")
    return slug


def build_records(game_data: dict[str, Any], english_game_data: dict[str, Any]) -> list[ImportRecord]:
    detail = game_data["details"]["rogue_1"]
    items = detail["items"]
    if isinstance(items, dict):
        items = items.values()
    relics = {item["id"]: item for item in items if item["type"] == "RELIC"}
    archive = detail["archiveComp"]["relic"]["relic"]
    english_detail = english_game_data["details"]["rogue_1"]
    english_items_source = english_detail["items"]
    if isinstance(english_items_source, dict):
        english_items_source = english_items_source.values()
    english_relics = {
        item["id"]: item for item in english_items_source if item["type"] == "RELIC"
    }
    english_archive = english_detail["archiveComp"]["relic"]["relic"]

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
    if relics.keys() != english_relics.keys() or archive.keys() != english_archive.keys():
        raise ValueError("中英文藏品来源 ID 集合不一致")

    records: list[ImportRecord] = []
    for source_id, item in relics.items():
        order_id = archive[source_id]["orderId"]
        english_item = english_relics[source_id]
        english_order_id = english_archive[source_id]["orderId"]
        if english_order_id != order_id:
            raise ValueError(
                f"中英文档案编号不一致：{source_id}={order_id!r}/{english_order_id!r}"
            )
        required = ("name", "usage", "description", "iconId", "rarity")
        missing = [key for key in required if not item.get(key)]
        if missing:
            raise ValueError(f"{source_id} 缺少字段：{', '.join(missing)}")
        invalid_types = [key for key in required if not isinstance(item[key], str)]
        if invalid_types:
            raise ValueError(f"{source_id} 字段类型错误：{', '.join(invalid_types)}")
        english_missing = [key for key in required if not english_item.get(key)]
        if english_missing:
            raise ValueError(f"{source_id} 英文字段缺失：{', '.join(english_missing)}")
        path = english_name_to_path(english_item["name"])
        icon_id = item["iconId"]
        if not ORDER_ID_PATTERN.fullmatch(order_id):
            raise ValueError(f"档案编号格式无效：{source_id}={order_id!r}")
        if not SOURCE_ID_PATTERN.fullmatch(source_id):
            raise ValueError(f"来源 ID 格式无效：{source_id!r}")
        if not SOURCE_ID_PATTERN.fullmatch(icon_id) or icon_id != source_id:
            raise ValueError(f"图片 ID 格式无效或与来源不一致：{source_id}={icon_id!r}")
        if not ITEM_PATH_PATTERN.fullmatch(path):
            raise ValueError(f"物品 ID 路径格式无效：{source_id}={path!r}")
        rarity = RARITY_MAP.get(item["rarity"])
        if rarity is None:
            raise ValueError(f"未知藏品稀有度：{source_id}={item['rarity']!r}")
        records.append(
            ImportRecord(
                path=path,
                order_id=order_id,
                source_id=source_id,
                icon_id=icon_id,
                name=item["name"],
                english_name=english_item["name"],
                original_effect=item["usage"],
                english_original_effect=english_item["usage"],
                description=item["description"],
                english_description=english_item["description"],
                rarity=rarity,
            )
        )

    records.sort(key=lambda record: (record.order_id.startswith("PCS"), record.order_id))
    paths = [record.path for record in records]
    if len(paths) != len(set(paths)):
        raise ValueError("生成的物品 ID 存在重复")
    return records


def require_within(path: Path, root: Path) -> Path:
    resolved_path = path.resolve()
    resolved_root = root.resolve()
    if resolved_path != resolved_root and resolved_root not in resolved_path.parents:
        raise ValueError(f"输出路径越过允许目录：{resolved_path}（root={resolved_root}）")
    return resolved_path


def staged_path(staging_root: Path, destination: Path) -> Path:
    safe_destination = require_within(destination, REPOSITORY_ROOT)
    relative = safe_destination.relative_to(REPOSITORY_ROOT.resolve())
    return require_within(staging_root / relative, staging_root)


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


def stage_images(
    records: list[ImportRecord],
    staging_root: Path,
    refresh: bool,
    download_images: bool,
    expected_digests: dict[str, str],
    update_digests: bool,
    previous_paths: dict[str, str],
) -> tuple[int, set[tuple[int, int]], list[tuple[Path, Path]], dict[str, str]]:
    downloaded = 0
    sizes: set[tuple[int, int]] = set()
    replacements: list[tuple[Path, Path]] = []
    actual_digests: dict[str, str] = {}
    for index, record in enumerate(records, start=1):
        target = require_within(TEXTURE_DIRECTORY / f"{record.path}.png", TEXTURE_DIRECTORY)
        staged = staged_path(staging_root, target)
        url = f"{PRTS_IMAGE_ROOT}/{record.icon_id}.png"
        previous_path = previous_paths.get(record.source_id)
        previous_target = (
            require_within(TEXTURE_DIRECTORY / f"{previous_path}.png", TEXTURE_DIRECTORY)
            if previous_path
            else None
        )
        if not refresh and not target.exists() and previous_target and previous_target.is_file():
            staged.parent.mkdir(parents=True, exist_ok=True)
            shutil.copyfile(previous_target, staged)
            replacements.append((staged, target))
        elif download_images and (refresh or not target.exists()):
            download(url, staged, refresh=True)
            replacements.append((staged, target))
            downloaded += 1
            print(f"[{index:03d}/{EXPECTED_COUNT}] 下载 {record.order_id} {record.name}")
        validated = staged if staged.exists() else target
        if not validated.is_file():
            raise FileNotFoundError(f"缺少藏品 PNG：{target}")
        sizes.add(png_size(validated))
        digest = sha256(validated)
        actual_digests[record.icon_id] = digest
        if not update_digests and expected_digests.get(record.icon_id) != digest:
            raise ValueError(
                f"PNG SHA-256 不匹配：{record.icon_id}；"
                "若已人工核对 PRTS 资源变化，请显式使用 --update-image-digests"
            )
    return downloaded, sizes, replacements, actual_digests


def load_previous_paths() -> dict[str, str]:
    if not AUDIT_CATALOG_PATH.is_file():
        return {}
    catalog = json.loads(AUDIT_CATALOG_PATH.read_text(encoding="utf-8"))
    previous_paths: dict[str, str] = {}
    for entry in catalog:
        source_id = entry.get("sourceId")
        path = entry.get("path")
        if not isinstance(source_id, str) or not SOURCE_ID_PATTERN.fullmatch(source_id):
            raise ValueError(f"旧目录来源 ID 无效：{source_id!r}")
        if not isinstance(path, str) or not ITEM_PATH_PATTERN.fullmatch(path):
            raise ValueError(f"旧目录物品 ID 无效：{path!r}")
        if source_id in previous_paths:
            raise ValueError(f"旧目录来源 ID 重复：{source_id}")
        previous_paths[source_id] = path
    return previous_paths


def remove_obsolete_textures(records: list[ImportRecord], previous_paths: dict[str, str]) -> int:
    current_paths = {record.path for record in records}
    obsolete_paths = set(previous_paths.values()) - current_paths
    removed = 0
    for path in sorted(obsolete_paths):
        target = require_within(TEXTURE_DIRECTORY / f"{path}.png", TEXTURE_DIRECTORY)
        if target.is_file():
            target.unlink()
            removed += 1
    return removed


def load_image_digests(records: list[ImportRecord], allow_missing: bool) -> dict[str, str]:
    expected_ids = {record.icon_id for record in records}
    if not IMAGE_DIGEST_MANIFEST_PATH.is_file():
        if allow_missing:
            return {}
        raise FileNotFoundError(f"缺少 PNG SHA-256 清单：{IMAGE_DIGEST_MANIFEST_PATH}")
    data = json.loads(IMAGE_DIGEST_MANIFEST_PATH.read_text(encoding="utf-8"))
    if data.get("algorithm") != "SHA-256" or not isinstance(data.get("images"), dict):
        raise ValueError("PNG SHA-256 清单结构无效")
    images = data["images"]
    if set(images) != expected_ids and not allow_missing:
        raise ValueError("PNG SHA-256 清单与藏品 iconId 集合不一致")
    for icon_id, digest in images.items():
        if not SOURCE_ID_PATTERN.fullmatch(icon_id) or not isinstance(digest, str) or not re.fullmatch(r"[0-9a-f]{64}", digest):
            raise ValueError(f"PNG SHA-256 清单条目无效：{icon_id}={digest!r}")
    return images


def markdown_escape(value: str) -> str:
    return value.replace("|", "\\|").replace("\n", "<br>")


def write_source_ledger(
    records: list[ImportRecord],
    destination: Path,
    game_data_digest: str,
    english_game_data_digest: str,
) -> None:
    lines = [
        "# 集成战略藏品素材来源",
        "",
        "本模块收录 PRTS《傀影与猩红孤钻》“长生者宝盒”的全部藏品：",
        "",
        "已适配的攻击、防御、生命和攻击速度藏品按 PRTS 数值原样进入统一战斗属性层：百分比藏品同类相加，攻击速度 `+N`",
        "按点数而非百分比处理。攻击力会同时作用于近战、原生枪械、法术、治疗和 TaCZ 枪械。公式及后续适配规则见",
        "[战斗数值机制](../combat/combat-stats.md)。",
        "",
        "全部 245 件藏品均已建立能力声明，不再以 `ArchiveOnly` 作为默认值：",
        "",
        "- 无条件的生命、攻击、防御、法抗、攻速和每秒回复按 PRTS 数值直接进入服务端运行时；职业限定效果在 Minecraft 中明确适配为“装备者”。",
        "- 希望、源石锭、招募、部署、关卡生命、节点和指定首领等规则保存为 `SourceRule`，逐字保留原始触发条件，供对应的集成战略子系统消费；在该子系统存在前不会伪装成幸运、经验或其他无关效果。",
        "- 245 个 Java Builder 声明逐项写明原效果、描述和最终 `CollectiblePower`，构建验证保证每件藏品都生成物品、翻译、模型和纹理引用。",
        "",
        "## L2 Library 页面",
        "",
        "项目要求 L2 Library 3.0.8。玩家物品栏中的 L2 `Curios` 标签显示为“饰品”；藏品只要装备在任意 Curios 饰品槽中就会生效，不再限定为 `relic` 槽。L2 属性标签显示为“能力”。能力页右侧额外显示 Zinecraft 的生命、攻击、防御、法抗、攻击速度、攻击间隔倍率、已装备饰品数量，以及明日方舟属性、物理、法术和攻速公式。",
        "",
        "页面只读取客户端已同步的实体属性和 Curios 内容，伤害、回复与藏品触发仍由服务器结算。",
        "",
        "## 泰拉维度战利品",
        "",
        "泰拉维度已生成的结构箱通过 `curios:relic` 物品标签抽取全部 245 件藏品。当前维多利亚防御炮的控制、维修、弹药、规划和补给箱均有 8% 的独立藏品掉落概率；房间原有的主题藏品池保留。后续泰拉结构箱应复用同一标签池。",
        "",
        "No.001–238 与 PCS01–PCS07，共 245 件。中文名、编号、原效果和描述来自",
        "明日方舟游戏数据，PNG 直接下载自 PRTS 图片资源域，未重绘、未生成或替换。",
        "",
        f"- PRTS 资料页：<{PRTS_PAGE_URL}>",
        f"- PRTS 图片资源域：<{PRTS_IMAGE_ROOT}/>",
        f"- 游戏数据镜像：<{GAME_DATA_URL}>",
        f"- 中文数据固定 SHA-256：`{game_data_digest}`",
        f"- 英文游戏数据镜像：<{ENGLISH_GAME_DATA_URL}>",
        f"- 英文数据固定 SHA-256：`{english_game_data_digest}`",
        "- PNG SHA-256 清单：`script/data/prts_is2_image_sha256.json`",
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
            "脚本每次运行都会核对固定输入摘要、藏品总数、字段完整性、ID 唯一性、PNG 文件头和逐图 SHA-256；JSON 仅写入",
            "`build/prts-cache` 作为审计快照，运行时注册以 `ModCollectible.java` 为唯一数据源。",
            "",
            "## 权利说明",
            "",
            "这些游戏图片与文本原文的权利属于上海鹰角网络科技有限公司及其关联公司；",
            "PRTS 用于资料整理与展示。本项目根目录许可证不应被解释为对这些第三方素材重新授权。",
            "Minecraft 适配效果与项目代码不属于 PRTS 原文。",
            "",
        ]
    )
    destination.parent.mkdir(parents=True, exist_ok=True)
    destination.write_text("\n".join(lines), encoding="utf-8", newline="\n")


def atomic_replace_from(source: Path, destination: Path) -> None:
    """Copy to a same-directory temporary file, fsync it, then atomically replace."""
    destination.parent.mkdir(parents=True, exist_ok=True)
    descriptor, temporary_name = tempfile.mkstemp(
        prefix=f".{destination.name}.", suffix=".publish", dir=destination.parent
    )
    temporary = Path(temporary_name)
    try:
        with os.fdopen(descriptor, "wb") as output, source.open("rb") as input_file:
            shutil.copyfileobj(input_file, output)
            output.flush()
            os.fsync(output.fileno())
        os.replace(temporary, destination)
    finally:
        temporary.unlink(missing_ok=True)


def publish_staged(
    staging_root: Path,
    outputs: list[tuple[Path, Path]],
    *,
    allowed_root: Path = REPOSITORY_ROOT,
    failure_after: int | None = None,
) -> None:
    """Atomically publish a validated batch and roll the whole batch back on failure."""
    checked: list[tuple[Path, Path]] = []
    destinations: set[Path] = set()
    for staged, destination in outputs:
        staged = require_within(staged, staging_root)
        destination = require_within(destination, allowed_root)
        if not staged.is_file():
            raise FileNotFoundError(f"缺少暂存输出：{staged}")
        if destination in destinations:
            raise ValueError(f"重复发布目标：{destination}")
        destinations.add(destination)
        checked.append((staged, destination))

    backup_root = require_within(staging_root / "publish-backups", staging_root)
    backup_root.mkdir(parents=True, exist_ok=True)
    backups: list[tuple[Path, Path, bool]] = []
    for index, (_, destination) in enumerate(checked):
        existed = destination.is_file()
        backup = backup_root / f"{index:04d}.backup"
        if existed:
            shutil.copyfile(destination, backup)
        backups.append((destination, backup, existed))

    published = 0
    try:
        for staged, destination in checked:
            atomic_replace_from(staged, destination)
            published += 1
            if failure_after is not None and published >= failure_after:
                raise RuntimeError(f"injected publish failure after {published} replacement(s)")
    except BaseException as publish_error:
        rollback_errors: list[str] = []
        for destination, backup, existed in reversed(backups[:published]):
            try:
                if existed:
                    atomic_replace_from(backup, destination)
                else:
                    destination.unlink(missing_ok=True)
            except OSError as rollback_error:
                rollback_errors.append(f"{destination}: {rollback_error}")
        if rollback_errors:
            raise RuntimeError("发布失败且回滚不完整：" + "; ".join(rollback_errors)) from publish_error
        raise


def main() -> int:
    arguments = parse_arguments()
    if arguments.refresh and arguments.skip_images:
        raise ValueError("--refresh 与 --skip-images 不能同时使用")
    game_data, game_data_digest, english_game_data, english_game_data_digest = load_game_data(arguments)
    records = build_records(game_data, english_game_data)
    previous_paths = load_previous_paths()
    expected_image_digests = load_image_digests(records, allow_missing=arguments.update_image_digests)
    staging_parent = REPOSITORY_ROOT / "build" / "prts-staging"
    staging_parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix="collectibles-", dir=staging_parent) as temporary:
        staging_root = Path(temporary).resolve()
        outputs: list[tuple[Path, Path]] = []

        staged_catalog = staged_path(staging_root, AUDIT_CATALOG_PATH)
        write_json(staged_catalog, [record.to_json() for record in records])
        # Parse the staged JSON once more so serialization failures cannot publish partial metadata.
        json.loads(staged_catalog.read_text(encoding="utf-8"))
        outputs.append((staged_catalog, AUDIT_CATALOG_PATH))

        staged_tag = staged_path(staging_root, CURIOS_TAG_PATH)
        write_json(staged_tag, {"replace": False, "values": [f"zinecraft:{r.path}" for r in records]})
        json.loads(staged_tag.read_text(encoding="utf-8"))
        outputs.append((staged_tag, CURIOS_TAG_PATH))

        staged_ledger = staged_path(staging_root, SOURCE_LEDGER_PATH)
        write_source_ledger(records, staged_ledger, game_data_digest, english_game_data_digest)
        outputs.append((staged_ledger, SOURCE_LEDGER_PATH))

        downloaded, sizes, image_outputs, actual_image_digests = stage_images(
            records,
            staging_root,
            arguments.refresh,
            download_images=not arguments.skip_images,
            expected_digests=expected_image_digests,
            update_digests=arguments.update_image_digests,
            previous_paths=previous_paths,
        )
        outputs.extend(image_outputs)

        if arguments.update_image_digests:
            staged_manifest = staged_path(staging_root, IMAGE_DIGEST_MANIFEST_PATH)
            write_json(
                staged_manifest,
                {"algorithm": "SHA-256", "images": dict(sorted(actual_image_digests.items()))},
            )
            json.loads(staged_manifest.read_text(encoding="utf-8"))
            outputs.append((staged_manifest, IMAGE_DIGEST_MANIFEST_PATH))

        publish_staged(staging_root, outputs)
    removed = remove_obsolete_textures(records, previous_paths)

    print(f"已生成 {len(records)} 件藏品；本次下载 {downloaded} 张 PNG。")
    print(f"已移除 {removed} 张旧 ID 藏品 PNG。")
    if sizes:
        print(f"已校验 {len(records)} 张 PNG，共 {len(sizes)} 种尺寸。")
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (KeyError, ValueError, FileNotFoundError, RuntimeError) as error:
        print(f"错误：{error}", file=sys.stderr)
        raise SystemExit(1) from error

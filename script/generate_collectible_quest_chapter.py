#!/usr/bin/env python3
"""Generate the FTB Quests collectible encyclopedia from Java Builder declarations."""

from __future__ import annotations

import json
import re
from dataclasses import dataclass
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
QUEST_ROOT = ROOT / "src/main/resources/zinecraft/ftbquests/quests"
CATALOG_PATH = ROOT / "src/main/java/com/cxxcxx/zinecraft/core/item/ModCollectible.java"
CHAPTER_PATH = QUEST_ROOT / "chapters/collectibles.snbt"
CHAPTER_ID = "434F4C4C45435449"
QUEST_PREFIX = "C3"
TASK_PREFIX = "C4"
CATEGORY_QUEST_PREFIX = "C1"
CATEGORY_TASK_PREFIX = "C2"
EXPECTED_COUNT = 245
ITEM_COLUMNS = 12


@dataclass(frozen=True)
class Category:
    key: str
    zh: str
    en: str
    zh_description: str
    en_description: str


CATEGORIES = (
    Category("attack", "攻击类", "Offense", "主要提高输出、攻击速度或削弱敌方防御。", "Primarily improves damage or attack speed, or reduces enemy defenses."),
    Category("healing", "治疗类", "Healing", "主要强化治疗、生命回复或持续恢复。", "Primarily improves healing, health recovery, or regeneration."),
    Category("defense", "防御类", "Defense", "主要提高防御、法抗、减伤，或削弱敌方攻击。", "Primarily improves defenses, resistance, or damage reduction, or weakens enemy attacks."),
    Category("survival", "生存类", "Survival", "主要影响生命上限、目标生命或闪避。", "Primarily affects maximum health, objective life, or dodge."),
    Category("hybrid", "综合战斗类", "Hybrid Combat", "同时影响两种及以上战斗属性。", "Affects two or more combat attributes at once."),
    Category("exploration", "探索类", "Exploration", "主要影响节点、战斗结算、路线或探索失败规则。", "Primarily affects nodes, battle results, routes, or exploration failure rules."),
    Category("recruitment", "编队与招募类", "Squad and Recruitment", "主要影响希望、招募、部署或编队。", "Primarily affects Hope, recruitment, deployment, or squad building."),
    Category("economy", "资源经营类", "Resources and Economy", "主要影响源石锭、商店、商品或藏品获取。", "Primarily affects Originium Ingots, shops, goods, or collectible acquisition."),
    Category("special", "特殊规则类", "Special Rules", "不属于以上类别的特殊集成战略规则。", "Special Integrated Strategies rules outside the categories above."),
)
CATEGORY_BY_KEY = {category.key: category for category in CATEGORIES}


def quest_id(index: int) -> str:
    return f"{QUEST_PREFIX}{index:014X}"


def task_id(index: int) -> str:
    return f"{TASK_PREFIX}{index:014X}"


def category_quest_id(index: int) -> str:
    return f"{CATEGORY_QUEST_PREFIX}{index:014X}"


def category_task_id(index: int) -> str:
    return f"{CATEGORY_TASK_PREFIX}{index:014X}"


def combat_flags(effect: str) -> set[str]:
    flags: set[str] = set()
    if re.search(r"攻击速度|攻击间隔|造成.{0,16}伤害|伤害提高|暴击|技力|防御力-", effect):
        flags.add("attack")
    if re.search(r"攻击力\+|攻击力提高", effect) and not re.search(r"敌方.{0,8}攻击力\+", effect):
        flags.add("attack")
    if re.search(r"治疗|回复.{0,16}生命|生命回复|每秒回复", effect):
        flags.add("healing")
    if re.search(r"防御力\+|法术抗性|受到.{0,16}伤害|伤害减免|护盾|抵抗|敌方.{0,8}攻击力-", effect):
        flags.add("defense")
    if re.search(r"(?:最大生命值|生命上限|生命值)(?:\+|-|提高|降低)|目标生命|闪避", effect):
        flags.add("survival")
    return flags


def classify(effect: str) -> str:
    flags = combat_flags(effect)
    if len(flags) >= 2:
        return "hybrid"
    if flags:
        return next(iter(flags))
    if re.search(r"节点|关卡|非战斗|战斗后|区域最终战斗|探索|路线|钥匙|灯火", effect):
        return "exploration"
    if re.search(r"招募|干员|部署|再部署|希望|编队", effect):
        return "recruitment"
    if re.search(r"源石锭|商店|商品|购买|售价|道具|藏品|收藏品", effect):
        return "economy"
    return "special"


def snbt_text(value: str) -> str:
    value = re.sub(r"</?color(?:=[^>]+)?>", "", value)
    return value.replace("\\", "\\\\").replace('"', '\\"').replace("\r", "").replace("\n", "\\n")


def to_display_name(path: str) -> str:
    """Mirror TranslationCatalog.toDisplayName for generated English quest text."""
    return " ".join(word[0].upper() + word[1:] for word in re.split(r"[_.]", path) if word)


def load_catalog() -> list[dict[str, str]]:
    source = CATALOG_PATH.read_text(encoding="utf-8")
    declaration = re.compile(
        r"public static final CollectibleBuilder\s+[A-Z0-9_]+\s*=\s*collectible\((.*?)\n\s*\);",
        re.DOTALL,
    )
    java_string = re.compile(r'"(?:\\.|[^"\\])*"')
    rarity = re.compile(r"Rarity\.(UNCOMMON|RARE|EPIC)")
    catalog: list[dict[str, str]] = []
    for match in declaration.finditer(source):
        arguments = match.group(1)
        strings = java_string.findall(arguments)
        rarity_match = rarity.search(arguments)
        if len(strings) < 7 or rarity_match is None:
            raise ValueError("Malformed collectible Builder declaration")
        values = [json.loads(value) for value in strings[:7]]
        catalog.append(
            {
                "path": values[0],
                "orderId": values[1],
                "zhCn": values[2],
                "enUs": to_display_name(values[0]),
                "originalEffectZhCn": values[3],
                "originalEffectEnUs": values[4],
                "descriptionZhCn": values[5],
                "descriptionEnUs": values[6],
                "rarity": rarity_match.group(1),
            }
        )
    if len(catalog) != EXPECTED_COUNT:
        raise ValueError(f"Expected {EXPECTED_COUNT} Java collectibles, found {len(catalog)}")
    paths = [entry["path"] for entry in catalog]
    if len(paths) != len(set(paths)):
        raise ValueError("Collectible paths must be unique")
    return catalog


def grouped_catalog(catalog: list[dict[str, str]]) -> dict[str, list[dict[str, str]]]:
    grouped = {category.key: [] for category in CATEGORIES}
    for entry in catalog:
        grouped[classify(entry["originalEffectZhCn"])].append(entry)
    if any(not entries for entries in grouped.values()):
        empty = [key for key, entries in grouped.items() if not entries]
        raise ValueError(f"Collectible categories must not be empty: {empty}")
    return grouped


def write_chapter(grouped: dict[str, list[dict[str, str]]]) -> None:
    quests: list[str] = []
    y = 0.0
    for category_index, category in enumerate(CATEGORIES, 1):
        entries = grouped[category.key]
        quests.append(
            "\n".join(
                (
                    "\t\t{",
                    f'\t\t\tid: "{category_quest_id(category_index)}"',
                    f'\t\t\ticon: {{ id: "zinecraft:{entries[0]["path"]}" }}',
                    "\t\t\toptional: true",
                    '\t\t\tshape: "gear"',
                    "\t\t\tsize: 2.0d",
                    f'\t\t\ttasks: [{{ id: "{category_task_id(category_index)}", type: "checkmark" }}]',
                    "\t\t\tx: -3.0d",
                    f"\t\t\ty: {y:.1f}d",
                    "\t\t}",
                )
            )
        )
        for local_index, entry in enumerate(entries):
            index = int(entry["orderId"][3:]) + 238 if entry["orderId"].startswith("PCS") else int(entry["orderId"])
            x = float((local_index % ITEM_COLUMNS) * 2)
            item_y = y + float((local_index // ITEM_COLUMNS) * 2)
            quests.append(
                "\n".join(
                    (
                        "\t\t{",
                        f'\t\t\tid: "{quest_id(index)}"',
                        f'\t\t\ticon: {{ id: "zinecraft:{entry["path"]}" }}',
                        "\t\t\toptional: true",
                        f'\t\t\ttasks: [{{ id: "{task_id(index)}", item: {{ count: 1, id: "zinecraft:{entry["path"]}" }}, type: "item" }}]',
                        f"\t\t\tx: {x:.1f}d",
                        f"\t\t\ty: {item_y:.1f}d",
                        "\t\t}",
                    )
                )
            )
        y += float(((len(entries) - 1) // ITEM_COLUMNS + 1) * 2 + 4)

    content = "\n".join(
        (
            "{",
            "\tdefault_hide_dependency_lines: true",
            '\tdefault_quest_shape: "circle"',
            '\tfilename: "collectibles"',
            '\ticon: { id: "zinecraft:hot_water_kettle" }',
            f'\tid: "{CHAPTER_ID}"',
            "\torder_index: 2",
            '\tprogression_mode: "flexible"',
            "\tquest_links: [ ]",
            "\tquests: [",
            "\n".join(quests),
            "\t]",
            "}",
            "",
        )
    )
    CHAPTER_PATH.write_text(content, encoding="utf-8", newline="\n")


def translation_lines(locale: str, grouped: dict[str, list[dict[str, str]]]) -> list[str]:
    zh = locale == "zh_cn"
    lines = [f'\tchapter.{CHAPTER_ID}.title: "{"藏品图鉴" if zh else "Collectible Encyclopedia"}"']
    for category_index, category in enumerate(CATEGORIES, 1):
        entries = grouped[category.key]
        qid = category_quest_id(category_index)
        tid = category_task_id(category_index)
        title = category.zh if zh else category.en
        description = category.zh_description if zh else category.en_description
        count_text = f"共 {len(entries)} 件藏品。" if zh else f"Contains {len(entries)} collectibles."
        lines.extend(
            (
                f'\tquest.{qid}.title: "{snbt_text(title)}"',
                f'\tquest.{qid}.quest_subtitle: "{snbt_text(count_text)}"',
                f'\tquest.{qid}.quest_desc: ["{snbt_text(description)}", "{snbt_text(count_text)}"]',
                f'\ttask.{tid}.title: "{"阅读分类说明" if zh else "Read category guide"}"',
            )
        )
        for entry in entries:
            index = int(entry["orderId"][3:]) + 238 if entry["orderId"].startswith("PCS") else int(entry["orderId"])
            qid = quest_id(index)
            tid = task_id(index)
            name = entry["zhCn"] if zh else entry["enUs"]
            effect = entry["originalEffectZhCn"] if zh else entry["originalEffectEnUs"]
            description = entry["descriptionZhCn"] if zh else entry["descriptionEnUs"]
            number = entry["orderId"]
            category_name = category.zh if zh else category.en
            subtitle = f"No.{number} · {category_name}"
            equip_note = (
                "装备到任意饰品栏即可生效；Minecraft 实际效果请查看物品提示和能力面板。"
                if zh
                else "Equip in any accessory slot; see the item tooltip and Abilities panel for the Minecraft effect."
            )
            task_title = f"持有{name}" if zh else f"Obtain {name}"
            lines.extend(
                (
                    f'\tquest.{qid}.title: "{snbt_text(name)}"',
                    f'\tquest.{qid}.quest_subtitle: "{snbt_text(subtitle)}"',
                    f'\tquest.{qid}.quest_desc: ["{snbt_text("PRTS 原效果：" if zh else "Original effect: ")}{snbt_text(effect)}", "{snbt_text("藏品介绍：" if zh else "Description: ")}{snbt_text(description)}", "{snbt_text(equip_note)}"]',
                    f'\ttask.{tid}.title: "{snbt_text(task_title)}"',
                )
            )
    return lines


def update_language(locale: str, grouped: dict[str, list[dict[str, str]]]) -> None:
    path = QUEST_ROOT / "lang" / f"{locale}.snbt"
    lines = path.read_text(encoding="utf-8").splitlines()
    generated_key = re.compile(r"^\s*(?:chapter|quest|task)\.(?:434F4C4C45435449|C[1-4][0-9A-F]{14})\.")
    lines = [line for line in lines if not generated_key.match(line)]
    while lines and not lines[-1].strip():
        lines.pop()
    if not lines or lines[-1].strip() != "}":
        raise ValueError(f"Unexpected language SNBT ending: {path}")
    lines.pop()
    while lines and not lines[-1].strip():
        lines.pop()
    lines.extend(("", *translation_lines(locale, grouped), "}"))
    path.write_text("\n".join(lines) + "\n", encoding="utf-8", newline="\n")


def main() -> None:
    catalog = load_catalog()
    grouped = grouped_catalog(catalog)
    write_chapter(grouped)
    update_language("zh_cn", grouped)
    update_language("en_us", grouped)
    summary = ", ".join(f"{category.key}={len(grouped[category.key])}" for category in CATEGORIES)
    print(f"Generated {len(catalog)} collectible quests: {summary}")


if __name__ == "__main__":
    main()

#!/usr/bin/env python3
"""Import the supported post-IS2 Integrated Strategies collectibles recorded by PRTS.

The PRTS MediaWiki source is the authoritative Chinese metadata source.  The
official English game-data mirror is used only when it contains the same icon
ID; untranslated records deliberately retain the Chinese source text instead
of inventing a translation.  Existing ModCollectible declarations win name
deduplication so their hand-written Minecraft adaptations are preserved.
"""

from __future__ import annotations

import argparse
import functools
import hashlib
import html
import json
import re
import time
import unicodedata
import urllib.error
import urllib.request
from dataclasses import dataclass
from pathlib import Path
from typing import Iterable


ROOT = Path(__file__).resolve().parents[1]
JAVA_PATH = ROOT / "src/main/java/com/cxxcxx/zinecraft/core/registry/ModCollectible.java"
TAG_PATH = ROOT / "src/main/resources/data/curios/tags/item/relic.json"
TEXTURE_ROOT = ROOT / "src/main/resources/assets/zinecraft/textures/item"
AUDIT_PATH = ROOT / "build/prts-cache/all_integrated_strategies_collectibles.json"
MANIFEST_PATH = ROOT / "script/data/prts_additional_collectible_image_sha256.json"
LEDGER_PATH = ROOT / "docs/item/PRTS_ADDITIONAL_COLLECTIBLES.md"
UNIMPLEMENTED_PATH = ROOT / "docs/item/unimplemented-collectible-effects.md"
ENGLISH_GAME_DATA_PATH = ROOT / "build/prts-cache/roguelike_topic_table_en.current.json"
TEMPORARY_ENGLISH_NAMES_PATH = ROOT / "script/data/prts_is7_temporary_en_names.json"
PRTS_IMAGE_ROOT = "https://torappu.prts.wiki/assets/roguelike_topic_itempic"
USER_AGENT = "Zinecraft collectible importer/1.0 (+PRTS source ledger)"

START_MARKER = "  // BEGIN GENERATED PRTS ADDITIONAL COLLECTIBLES"
END_MARKER = "  // END GENERATED PRTS ADDITIONAL COLLECTIBLES"
FIELD_NAME_OVERRIDES = {
    # ArkData reuses an older English name for a distinct Chinese collectible.
    # Add the Chinese distinguishing sense rather than falling back to an order ID.
    "rogue_2_relic_fight_77": "RUSTED_BLADE_EXECUTION_GRINDING",
    "rogue_2_relic_fight_84": "HAND_OF_DIFFUSION_EXPLOSIVE",
}


@dataclass(frozen=True)
class Theme:
    key: str
    prefix: str
    zh_cn: str
    en_us: str
    page: str


THEMES = (
    Theme("rogue_2", "IS3", "水月与深蓝之树", "Mizuki & Caerula Arbor", "水月与深蓝之树/生物制品陈设"),
    Theme("rogue_3", "IS4", "探索者的银凇止境", "Expeditioner's Jǫklumarkar", "探索者的银凇止境/仪式用品索引"),
    Theme("rogue_4", "IS5", "萨卡兹的无终奇语", "Sarkaz's Furnaceside Fables", "萨卡兹的无终奇语/想象实体图鉴"),
    Theme("rogue_5", "IS6", "岁的界园志异", "Sui's Garden of Grotesqueries", "岁的界园志异/珍玩集册"),
    Theme("rogue_6", "IS7", "沉沦者的黑流树海", "沉沦者的黑流树海", "沉沦者的黑流树海/拟造物质编目"),
)


@dataclass(frozen=True)
class Record:
    theme: Theme
    source_order_id: str
    field_name: str
    path: str
    icon_id: str
    name: str
    english_name: str
    # PRTS 中完整列出的全部档位文本。
    effect: str
    strongest_effect: str
    english_effect: str
    description: str
    english_description: str
    english_name_source: str
    rarity: str
    effect_conditions: tuple[str, ...]
    effect_variants: tuple[str, ...]

    def audit_json(self) -> dict[str, object]:
        adaptations = [adapt_effect(effect) for effect in self.effect_variants]
        status = aggregate_status(adaptations)
        return {
            "theme": self.theme.zh_cn,
            "themeKey": self.theme.key,
            "sourcePage": f"https://prts.wiki/w/{self.theme.page}",
            "sourceOrderId": self.source_order_id,
            "field": self.field_name,
            "path": self.path,
            "iconId": self.icon_id,
            "zhCn": self.name,
            "enUs": self.english_name,
            "englishNameSource": self.english_name_source,
            "originalEffectZhCn": self.effect,
            "originalEffectEnUs": self.english_effect,
            "strongestEffectZhCn": self.strongest_effect,
            "descriptionZhCn": self.description,
            "descriptionEnUs": self.english_description,
            "rarity": self.rarity,
            "effectVariantsZhCn": [
                {"condition": condition, "effect": effect}
                for condition, effect in zip(self.effect_conditions, self.effect_variants)
            ],
            "minecraftAdaptationStatus": status,
            "minecraftAdaptation": [adaptation.summary for adaptation in adaptations],
        }


def arguments() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--write", action="store_true", help="Write Java, tag, audit, ledger, and new PNGs.")
    parser.add_argument("--skip-images", action="store_true", help="Generate metadata without downloading missing PNGs.")
    return parser.parse_args()


def cache_path(theme: Theme) -> Path:
    return ROOT / f"build/prts-cache/prts_{theme.key}_wikitext.json"


def extract_root_templates(source: str, template_name: str) -> Iterable[str]:
    marker = "{{" + template_name + "\n"
    cursor = 0
    while True:
        start = source.find(marker, cursor)
        if start < 0:
            return
        depth = 0
        index = start
        while index < len(source) - 1:
            pair = source[index:index + 2]
            if pair == "{{":
                depth += 1
                index += 2
                continue
            if pair == "}}":
                depth -= 1
                index += 2
                if depth == 0:
                    yield source[start:index]
                    cursor = index
                    break
                continue
            index += 1
        else:
            raise ValueError(f"Unclosed {template_name} template")


def parse_fields(template: str) -> dict[str, str]:
    fields: dict[str, str] = {}
    depth = 0
    starts: list[tuple[int, int, str]] = []
    index = 0
    while index < len(template) - 1:
        pair = template[index:index + 2]
        if pair == "{{":
            depth += 1
            index += 2
            continue
        if pair == "}}":
            depth -= 1
            index += 2
            continue
        if depth == 1 and template[index] == "\n" and index + 1 < len(template) and template[index + 1] == "|":
            equals = template.find("=", index + 2, template.find("\n", index + 2))
            if equals >= 0:
                key = template[index + 2:equals].strip()
                starts.append((index, equals + 1, key))
        index += 1
    for position, (_, value_start, key) in enumerate(starts):
        value_end = starts[position + 1][0] if position + 1 < len(starts) else template.rfind("\n}}")
        fields[key] = template[value_start:value_end].strip()
    return fields


def split_template(value: str) -> list[str]:
    if not (value.startswith("{{") and value.endswith("}}")):
        return [value]
    body = value[2:-2]
    result: list[str] = []
    start = 0
    depth = 0
    index = 0
    while index < len(body) - 1:
        pair = body[index:index + 2]
        if pair in ("{{", "[["):
            depth += 1
            index += 2
            continue
        if pair in ("}}", "]]" ):
            depth -= 1
            index += 2
            continue
        if body[index] == "|" and depth == 0:
            result.append(body[start:index])
            start = index + 1
        index += 1
    result.append(body[start:])
    return result


def clean_wikitext(value: str) -> str:
    value = re.sub(r"<!--.*?-->", "", value, flags=re.DOTALL)
    value = re.sub(r"<ref\b.*?</ref>|<ref\b[^>]*/>", "", value, flags=re.DOTALL | re.IGNORECASE)
    value = re.sub(r"<br\s*/?>", "\n", value, flags=re.IGNORECASE)
    value = re.sub(r"\[\[[^\]|]+\|([^\]]+)]]", r"\1", value)
    value = re.sub(r"\[\[([^\]]+)]]", r"\1", value)

    template_pattern = re.compile(r"\{\{([^{}]*)}}")
    while template_pattern.search(value):
        def replace(match: re.Match[str]) -> str:
            parts = split_template("{{" + match.group(1) + "}}")
            name = parts[0].strip().lower()
            positional = [part.strip() for part in parts[1:] if "=" not in part and part.strip()]
            if not positional:
                return ""
            if name in {"color", "c", "字体颜色"}:
                return positional[-1]
            if name.startswith("#var:") or name.startswith("#vardefine:"):
                return ""
            return positional[-1] if len(positional) > 1 else positional[0]
        value = template_pattern.sub(replace, value)

    value = re.sub(r"<[^>]+>", "", value)
    value = value.replace("'''", "").replace("''", "")
    value = html.unescape(value)
    lines = [re.sub(r"[ \t\u00a0]+", " ", line).strip() for line in value.splitlines()]
    return "\n".join(line for line in lines if line).strip()


def strongest_effect(raw_effect: str) -> tuple[str, str, tuple[str, ...], tuple[str, ...]]:
    cleaned = clean_wikitext(raw_effect)
    lines = [line for line in cleaned.splitlines() if line]
    groups: list[tuple[str, list[str]]] = []
    for line in lines:
        label = re.match(r"^[【〖]([^】〗]+)[】〗]", line)
        if label:
            groups.append((label.group(1), [line[label.end():].strip()]))
        elif groups:
            groups[-1][1].append(line)
    if len(groups) >= 2:
        conditions = tuple(condition for condition, _ in groups)
        variants = tuple("\n".join(group).strip() for _, group in groups)
        return cleaned, variants[-1], conditions, variants
    return cleaned, cleaned, ("基础",), (cleaned,)


def english_items() -> dict[str, dict[str, object]]:
    if not ENGLISH_GAME_DATA_PATH.is_file():
        return {}
    data = json.loads(ENGLISH_GAME_DATA_PATH.read_text(encoding="utf-8"))
    result: dict[str, dict[str, object]] = {}
    for detail in data.get("details", {}).values():
        items = detail.get("items", {})
        iterable = items.values() if isinstance(items, dict) else items
        # ACTIVE_TOOL and EXPLORE_TOOL entries are also listed as collectibles by
        # PRTS and carry their official English names in the same ArkData table.
        result.update({item["id"]: item for item in iterable if item.get("id")})
    return result


def temporary_english_names() -> dict[str, str]:
    if not TEMPORARY_ENGLISH_NAMES_PATH.is_file():
        return {}
    return json.loads(TEMPORARY_ENGLISH_NAMES_PATH.read_text(encoding="utf-8"))


def java_constant_name(english_name: str) -> str:
    value = english_name.replace("&", " AND ").replace("α", " ALPHA ").replace("β", " BETA ")
    value = unicodedata.normalize("NFKD", value).encode("ascii", "ignore").decode("ascii")
    value = re.sub(r"[^A-Za-z0-9]+", "_", value).strip("_").upper()
    if not value or not re.search(r"[A-Z]", value):
        raise ValueError(f"English collectible name cannot form a Java field: {english_name!r}")
    if value[0].isdigit():
        value = "COLLECTIBLE_" + value
    return value


def rarity_name(value: str) -> str:
    return {"0": "UNCOMMON", "1": "RARE", "2": "EPIC", "3": "EPIC"}.get(value.strip(), "UNCOMMON")


def load_records() -> list[Record]:
    english = english_items()
    temporary = temporary_english_names()
    result: list[Record] = []
    for theme in THEMES:
        data = json.loads(cache_path(theme).read_text(encoding="utf-8"))
        source = data["parse"]["wikitext"]
        templates = list(extract_root_templates(source, "收藏品"))
        for template in templates:
            fields = parse_fields(template)
            required = ("ID", "名称", "效果", "描述")
            missing = [key for key in required if not fields.get(key)]
            if missing:
                raise ValueError(f"{theme.key} collectible missing fields {missing}: {fields.get('ID')}")
            source_order = fields["ID"].strip().zfill(3)
            if not re.fullmatch(r"[0-9]{3}", source_order):
                raise ValueError(f"Unsupported PRTS order ID: {theme.key}/{fields['ID']}")
            name = clean_wikitext(fields["名称"])
            icon_id = fields.get("iconId", f"fungimist_{source_order}").strip()
            effect, strongest, conditions, variants = strongest_effect(fields["效果"])
            description = clean_wikitext(fields["描述"])
            official = english.get(icon_id, {})
            official_name = official.get("name")
            temporary_name = temporary.get(icon_id)
            english_name = str(official_name or temporary_name or name)
            english_name_source = (
                "ArkData"
                if official_name
                else "temporary Chinese translation"
                if temporary_name
                else "unavailable"
            )
            field_name = FIELD_NAME_OVERRIDES.get(
                icon_id,
                java_constant_name(english_name)
                if english_name_source != "unavailable"
                else java_constant_name(f"untranslated {icon_id}"),
            )
            english_description = str(official.get("description") or description)
            english_effect = str(official.get("usage") or effect)
            if len(variants) > 1:
                # The official data table stores only the base tier.  Do not label
                # that weaker text as the complete multi-tier PRTS effect.
                english_effect = effect
            result.append(Record(
                theme=theme,
                source_order_id=source_order,
                field_name=field_name,
                path=field_name.lower(),
                icon_id=icon_id,
                name=name,
                english_name=english_name,
                effect=effect,
                strongest_effect=strongest,
                english_effect=english_effect,
                description=description,
                english_description=english_description,
                english_name_source=english_name_source,
                rarity=rarity_name(fields.get("稀有度", "0")),
                effect_conditions=conditions,
                effect_variants=variants,
            ))
        if len(templates) != len({record.source_order_id for record in result if record.theme == theme}):
            raise ValueError(f"Duplicate PRTS order ID in {theme.key}")
    return result


JAVA_STRING = re.compile(r'"(?:\\.|[^"\\])*"')
DECLARATION = re.compile(
    r"public static final CollectibleBuilder\s+[A-Z0-9_]+\s*=\s*collectible\((.*?)\n\s*\);",
    re.DOTALL,
)


def existing_names(java_source: str) -> set[str]:
    prefix = java_source.split(START_MARKER, 1)[0]
    names: set[str] = set()
    for match in DECLARATION.finditer(prefix):
        strings = JAVA_STRING.findall(match.group(1))
        if len(strings) >= 2:
            values = [json.loads(value) for value in strings]
            name_index = 2 if re.fullmatch(r"(?:[0-9]{3}|PCS[0-9]{2})", values[1]) else 1
            names.add(values[name_index])
    return names


def deduplicate(records: list[Record], java_source: str) -> tuple[list[Record], list[dict[str, str]]]:
    existing = existing_names(java_source)
    groups: dict[str, list[Record]] = {}
    for record in records:
        groups.setdefault(record.name, []).append(record)
    selected: list[Record] = []
    skipped: list[dict[str, str]] = []
    for name, candidates in groups.items():
        if name in existing:
            skipped.extend(
                {"name": record.name, "theme": record.theme.zh_cn, "sourceOrderId": record.source_order_id}
                for record in candidates
            )
            continue
        maximum_tiers = max(len(record.effect_variants) for record in candidates)
        if maximum_tiers > 1:
            # Later themes usually carry the newest high-difficulty ceiling.  Among
            # equally complete multi-tier records, retain that later ceiling.
            chosen = next(
                record for record in reversed(candidates)
                if len(record.effect_variants) == maximum_tiers
            )
        else:
            chosen = candidates[0]
        selected.append(chosen)
        skipped.extend(
            {"name": record.name, "theme": record.theme.zh_cn, "sourceOrderId": record.source_order_id}
            for record in candidates if record is not chosen
        )
    untranslated = [record for record in selected if record.english_name_source == "unavailable"]
    if untranslated:
        details = ", ".join(f"{record.theme.key}/{record.icon_id}/{record.name}" for record in untranslated)
        raise ValueError(f"Selected collectibles missing English names: {details}")
    return selected, skipped


def validate_field_names(records: list[Record], java_source: str) -> None:
    generated: dict[str, list[Record]] = {}
    for record in records:
        generated.setdefault(record.field_name, []).append(record)
    duplicates = {name: values for name, values in generated.items() if len(values) > 1}
    if duplicates:
        raise ValueError(f"Duplicate generated collectible fields: {sorted(duplicates)}")
    existing_source = java_source.split(START_MARKER, 1)[0]
    existing = set(re.findall(
        r"public static final CollectibleBuilder\s+([A-Z0-9_]+)",
        existing_source,
    ))
    collisions = existing.intersection(generated)
    if collisions:
        raise ValueError(f"Generated collectible fields collide with existing fields: {sorted(collisions)}")
    paths = [record.path for record in records]
    if len(paths) != len(set(paths)):
        raise ValueError("Generated collectible resource paths are not unique")
    invalid_paths = [path for path in paths if not re.fullmatch(r"[a-z0-9_]+", path)]
    if invalid_paths:
        raise ValueError(f"Invalid collectible resource paths: {invalid_paths}")


def java_string(value: str) -> str:
    return json.dumps(value, ensure_ascii=False).replace(" ", "\\u2028").replace(" ", "\\u2029")


@dataclass(frozen=True)
class Adaptation:
    java: str
    status: str
    summary: str


PROFESSIONS = {
    "先锋": "VANGUARD",
    "近卫": "GUARD",
    "狙击": "SNIPER",
    "术师": "CASTER",
    "重装": "DEFENDER",
    "医疗": "MEDIC",
    "辅助": "SUPPORTER",
    "特种": "SPECIALIST",
}

RESOURCE_FIELDS = (
    (r"(?:立即获得)?希望([+-]\d+)", "hope"),
    (r"(?:立即获得)?源石锭([+-]\d+)", "originiumIngots"),
    (r"(?:初始)?行动力([+-]\d+)", "actionPoints"),
    (r"抗干扰指数([+-]\d+)", "antiInterferenceIndex"),
    (r"坍缩值([+-]\d+)", "collapseValue"),
    (r"负荷临界点([+-]\d+)", "mentalBurdenLimit"),
    (r"思绪([+-]\d+)", "thoughts"),
    (r"烛火([+-]\d+)", "candles"),
    (r"灯火([+-]\d+)", "light"),
    (r"钥匙([+-]\d+)", "keys"),
    (r"(?:掷骰次数|骰子)([+-]\d+)", "dice"),
    (r"可携带干员([+-]\d+)", "squadCapacity"),
    (r"可同时部署人数([+-]\d+)", "deploymentLimit"),
    (r"初始部署费用([+-]\d+)", "initialDeploymentPoints"),
)


def power_set(powers: list[str]) -> str:
    if len(powers) == 1:
        return powers[0]
    return "statSet(" + ", ".join(powers) + ")"


def percent_value(sign: str, value: str) -> float:
    return (-1.0 if sign == "-" else 1.0) * int(value) / 100.0


def decimal(value: float) -> str:
    return f"{value:.10g}"


def adapt_clause(clause: str) -> tuple[str, str] | None:
    match = re.fullmatch(r"(?:立即获得)?(?:临时)?目标生命(?:值|上限)?([+-]\d+)", clause)
    if match:
        return f"stats -> stats.addMaxHealth({int(match.group(1))})", "implemented"
    match = re.fullmatch(r"(?:立即)?(?:获得|回复)([0-9]+)(?:点)?(?:临时)?目标生命(?:值|上限)?", clause)
    if match:
        return f"stats -> stats.addMaxHealth({int(match.group(1))})", "implemented"
    match = re.fullmatch(r"护盾值?([+-]\d+)", clause)
    if match:
        return f"stats -> stats.addMaxHealth({int(match.group(1))})", "implemented"
    match = re.fullmatch(r"(?:立即)?获得([0-9]+)(?:点)?护盾", clause)
    if match:
        return f"stats -> stats.addMaxHealth({int(match.group(1))})", "implemented"

    for pattern, field in RESOURCE_FIELDS:
        match = re.fullmatch(pattern, clause)
        if match:
            return f"stats -> stats.{field}({int(match.group(1))})", "registered"

    match = re.fullmatch(r"所有我方单位每秒回复([0-9]+)点生命", clause)
    if match:
        return f"regenerationFlat({int(match.group(1))})", "implemented"

    ally = r"所有(?:我方单位|我方干员|友方单位|干员)"
    match = re.fullmatch(ally + r"(?:的)?攻击力、防御力和生命(?:值)?\+([0-9]+)%", clause)
    if match:
        bonus = decimal(int(match.group(1)) / 100.0)
        return (
            "stats -> stats.multiplyAttack(" + bonus + ").multiplyDefense(" + bonus
            + ").multiplyMaxHealth(" + bonus + ")",
            "implemented",
        )
    match = re.fullmatch(ally + r"(?:的)?攻击力和生命(?:值)?\+([0-9]+)%", clause)
    if match:
        bonus = decimal(int(match.group(1)) / 100.0)
        return f"stats -> stats.multiplyAttack({bonus}).multiplyMaxHealth({bonus})", "implemented"
    match = re.fullmatch(ally + r"(?:的)?防御力和生命(?:值)?\+([0-9]+)%", clause)
    if match:
        bonus = decimal(int(match.group(1)) / 100.0)
        return f"stats -> stats.multiplyDefense({bonus}).multiplyMaxHealth({bonus})", "implemented"
    match = re.fullmatch(ally + r"(?:的)?生命(?:值)?、攻击力、防御力([+-])([0-9]+)%", clause)
    if match:
        bonus = decimal(percent_value(match.group(1), match.group(2)))
        return (
            "stats -> stats.multiplyMaxHealth(" + bonus + ").multiplyAttack(" + bonus
            + ").multiplyDefense(" + bonus + ")",
            "implemented",
        )
    match = re.fullmatch(ally + r"(?:的)?(?:攻击力、(?:最大)?生命值|(?:最大)?生命值、攻击力)([+-])([0-9]+)%", clause)
    if match:
        bonus = decimal(percent_value(match.group(1), match.group(2)))
        return f"stats -> stats.multiplyAttack({bonus}).multiplyMaxHealth({bonus})", "implemented"

    ally_stats = {
        "攻击力": "multiplyAttack",
        "防御力": "multiplyDefense",
        "生命": "multiplyMaxHealth",
        "生命值": "multiplyMaxHealth",
        "最大生命值": "multiplyMaxHealth",
    }
    match = re.fullmatch(ally + r"(?:的)?(攻击力|防御力|生命|生命值|最大生命值)([+-])([0-9]+)%", clause)
    if match:
        return (
            f"stats -> stats.{ally_stats[match.group(1)]}("
            f"{decimal(percent_value(match.group(2), match.group(3)))})",
            "implemented",
        )
    match = re.fullmatch(ally + r"(?:的)?攻击速度([+-])([0-9]+)", clause)
    if match:
        value = int(match.group(2)) * (-1 if match.group(1) == "-" else 1)
        return f"stats -> stats.addAttackSpeed({value})", "implemented"
    match = re.fullmatch(ally + r"(?:的)?法术抗性([+-])([0-9]+)", clause)
    if match:
        value = int(match.group(2)) * (-1 if match.group(1) == "-" else 1)
        return f"stats -> stats.addResistance({value})", "implemented"
    match = re.fullmatch(ally + r"(?:的)?受到的治疗(?:与|和)生命回复效果\+([0-9]+)%", clause)
    if match:
        return (
            "stats -> stats.addHealingAndHealthRegenerationBonus("
            f"{decimal(int(match.group(1)) / 100.0)})",
            "implemented",
        )
    match = re.fullmatch(ally + r"(?:的)?造成的真实伤害\+([0-9]+)%", clause)
    if match:
        return f"stats -> stats.addTrueDamageBonus({decimal(int(match.group(1)) / 100.0)})", "implemented"
    match = re.fullmatch(ally + r"(?:的)?造成的元素(?:损伤|伤害)\+([0-9]+)%", clause)
    if match:
        return f"stats -> stats.addElementalDamageBonus({decimal(int(match.group(1)) / 100.0)})", "implemented"
    match = re.fullmatch(ally + r"(?:的)?受到的元素损伤(?:减少|-)([0-9]+)%", clause)
    if match:
        return f"stats -> stats.addElementalDamageReduction({decimal(int(match.group(1)) / 100.0)})", "implemented"
    match = re.fullmatch(ally + r"(?:的)?获得([0-9]+)%的物理和法术闪避", clause)
    if match:
        bonus = decimal(int(match.group(1)) / 100.0)
        return (
            f"stats -> stats.addPhysicalDamageEvasionRate({bonus})"
            f".addMagicDamageEvasionRate({bonus})",
            "implemented",
        )

    match = re.fullmatch(
        r"所有【(先锋|近卫|狙击|术师|重装|医疗|辅助|特种)】干员(?:的)?"
        r"(攻击力|防御力|生命|生命值|最大生命值)([+-])([0-9]+)%",
        clause,
    )
    if match:
        method = ally_stats[match.group(2)]
        bonus = decimal(percent_value(match.group(3), match.group(4)))
        return (
            f"forProfession(SkillProfession.{PROFESSIONS[match.group(1)]}, "
            f"stats -> stats.{method}({bonus}))",
            "implemented",
        )
    match = re.fullmatch(
        r"所有【(先锋|近卫|狙击|术师|重装|医疗|辅助|特种)】干员(?:的)?"
        r"(攻击速度|法术抗性)([+-])([0-9]+)",
        clause,
    )
    if match:
        method = "addAttackSpeed" if match.group(2) == "攻击速度" else "addResistance"
        value = int(match.group(4)) * (-1 if match.group(3) == "-" else 1)
        return (
            f"forProfession(SkillProfession.{PROFESSIONS[match.group(1)]}, "
            f"stats -> stats.{method}({value}))",
            "implemented",
        )

    enemy = r"所有敌(?:方单位|人)"
    match = re.fullmatch(
        enemy + r"(?:的)?攻击力、防御力(?:和|、)生命(?:值)?\+([0-9]+)%",
        clause,
    )
    if match:
        bonus = decimal(int(match.group(1)) / 100.0)
        return (
            "stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> "
            f"enemyStats.multiplyAttack({bonus}).multiplyDefense({bonus}).multiplyMaxHealth({bonus}))",
            "implemented",
        )
    match = re.fullmatch(enemy + r"(?:的)?(?:攻击力和生命|攻击力、生命值)([+-])([0-9]+)%", clause)
    if match:
        bonus = decimal(percent_value(match.group(1), match.group(2)))
        return (
            "stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> "
            f"enemyStats.multiplyAttack({bonus}).multiplyMaxHealth({bonus}))",
            "implemented",
        )
    match = re.fullmatch(enemy + r"(?:的)?生命(?:值)?、防御力\+([0-9]+)%", clause)
    if match:
        bonus = decimal(int(match.group(1)) / 100.0)
        return (
            "stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> "
            f"enemyStats.multiplyMaxHealth({bonus}).multiplyDefense({bonus}))",
            "implemented",
        )
    match = re.fullmatch(enemy + r"(?:的)?生命(?:值)?([+-])([0-9]+)%", clause)
    if match:
        bonus = decimal(percent_value(match.group(1), match.group(2)))
        return (
            "stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> "
            f"enemyStats.multiplyMaxHealth({bonus}))",
            "implemented",
        )
    match = re.fullmatch(enemy + r"(?:的)?攻击力([+-])([0-9]+)%", clause)
    if match and match.group(1) == "-":
        return f"stats -> stats.addDamageReduction({decimal(int(match.group(2)) / 100.0)})", "implemented"
    if match:
        bonus = decimal(int(match.group(2)) / 100.0)
        return (
            "stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> "
            f"enemyStats.multiplyAttack({bonus}))",
            "implemented",
        )
    match = re.fullmatch(enemy + r"(?:的)?防御力([+-])([0-9]+)%", clause)
    if match and match.group(1) == "-":
        return f"stats -> stats.addDefenseIgnore({decimal(int(match.group(2)) / 100.0)})", "implemented"
    if match:
        bonus = decimal(int(match.group(2)) / 100.0)
        return (
            "stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> "
            f"enemyStats.multiplyDefense({bonus}))",
            "implemented",
        )
    match = re.fullmatch(enemy + r"(?:的)?攻击速度([+-])([0-9]+)", clause)
    if match:
        value = int(match.group(2)) * (-1 if match.group(1) == "-" else 1)
        return (
            "stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> "
            f"enemyStats.addAttackSpeed({value}))",
            "implemented",
        )
    match = re.fullmatch(enemy + r"受到的物理与法术伤害\+([0-9]+)%", clause)
    if match:
        bonus = decimal(int(match.group(1)) / 100.0)
        return (
            f"stats -> stats.addEnemyPhysicalDamageTakenBonus({bonus})"
            f".addEnemyMagicDamageTakenBonus({bonus})",
            "implemented",
        )
    match = re.fullmatch(enemy + r"受到的(物理|法术)伤害\+([0-9]+)%", clause)
    if match:
        method = "addEnemyPhysicalDamageTakenBonus" if match.group(1) == "物理" else "addEnemyMagicDamageTakenBonus"
        return f"stats -> stats.{method}({decimal(int(match.group(2)) / 100.0)})", "implemented"
    match = re.fullmatch(enemy + r"(?:的)?移动速度-([0-9]+)%", clause)
    if match:
        return f"stats -> stats.addEnemyMovementSpeedReduction({decimal(int(match.group(1)) / 100.0)})", "implemented"
    match = re.fullmatch(enemy + r"(?:的)?重量-([0-9]+)(?:个)?(?:等级|级)?", clause)
    if match:
        return f"stats -> stats.addEnemyWeightIgnore({int(match.group(1))})", "implemented"
    match = re.fullmatch(enemy + r"受到的异常状态持续时间\+([0-9]+)%", clause)
    if match:
        return f"stats -> stats.addEnemyStatusDurationBonus({decimal(int(match.group(1)) / 100.0)})", "implemented"
    return None


def adapt_effect(original_rule: str) -> Adaptation:
    normalized = original_rule.replace("％", "%").replace("；", "，").replace("。", "，")
    clauses = [clause.strip() for clause in re.split(r"[，\n]", normalized) if clause.strip()]
    powers: list[str] = []
    kinds: list[str] = []
    unresolved: list[str] = []
    carried_subject = ""
    for clause in clauses:
        candidate = clause
        if carried_subject and re.match(
            r"(?:攻击力|防御力|生命|生命值|最大生命值|攻击速度|攻速|法术抗性)[+-]",
            clause,
        ):
            candidate = carried_subject + clause
        adapted = adapt_clause(candidate)
        if adapted is None:
            unresolved.append(clause)
            carried_subject = ""
        else:
            # A later bare value after an unresolved condition must not be applied
            # unconditionally. Keep the whole source rule pending instead.
            if unresolved and candidate == clause:
                unresolved.append(clause)
                carried_subject = ""
                continue
            power, kind = adapted
            powers.append(power)
            kinds.append(kind)
            subject = re.match(
                r"(所有【(?:先锋|近卫|狙击|术师|重装|医疗|辅助|特种)】干员(?:的)?|"
                r"所有(?:我方单位|我方干员|友方单位|干员)(?:的)?)",
                candidate,
            )
            carried_subject = subject.group(1) if subject else ""
    quoted = java_string(original_rule)
    if not powers:
        return Adaptation(f"sourceRule({quoted})", "unimplemented", "等待专用运行时")
    power = power_set(powers)
    if unresolved:
        return Adaptation(
            f"partialRule({quoted}, {power})",
            "partial",
            f"已实现 {len(powers)} 项；待实现 {len(unresolved)} 项",
        )
    if "registered" in kinds:
        registered_count = kinds.count("registered")
        implemented_count = kinds.count("implemented")
        return Adaptation(
            f"registeredRule({quoted}, {power})",
            "implemented",
            f"已实现 {registered_count + implemented_count} 项实际效果",
        )
    return Adaptation(
        f"implementedRule({quoted}, {power})",
        "implemented",
        f"已实现 {len(powers)} 项运行时效果",
    )


def aggregate_status(adaptations: list[Adaptation]) -> str:
    statuses = {adaptation.status for adaptation in adaptations}
    if statuses == {"unimplemented"}:
        return "unimplemented"
    if "unimplemented" in statuses or "partial" in statuses:
        return "partial"
    return "implemented"


def factory_definition(record: Record) -> str:
    indent = "    "
    values = (
        java_string(record.path),
        java_string(record.name),
        java_string(record.effect),
        java_string(record.english_effect),
        java_string(record.description),
        java_string(record.english_description),
    )
    if len(record.effect_variants) > 1:
        tier_lines = [f"{indent}    tieredRule("]
        for index, (condition, effect) in enumerate(zip(record.effect_conditions, record.effect_variants)):
            comma = "," if index + 1 < len(record.effect_variants) else ""
            tier_lines.append(
                f"{indent}        tier({java_string(condition)}, {adapt_effect(effect).java}){comma}"
            )
        tier_lines.append(f"{indent}    ),")
        power_lines = "\n".join(tier_lines)
    else:
        power_lines = f"{indent}    {adapt_effect(record.effect_variants[0]).java},"
    return "\n".join((
        f"  private static CollectibleBuilder create_{record.field_name}() {{",
        "    return collectible(",
        *(f"        {value}," for value in values),
        power_lines,
        f"        Rarity.{record.rarity}",
        "    );",
        "  }",
    ))


def generated_java(records: list[Record]) -> str:
    lines = [START_MARKER]
    for record in records:
        lines.append(
            f"  public static final CollectibleBuilder {record.field_name} = "
            f"create_{record.field_name}();"
        )
    lines.append("")
    for record in records:
        lines.append(factory_definition(record))
        lines.append("")
    lines.append(END_MARKER)
    return "\n".join(lines)


RUNTIME_ORDER_ARGUMENT = re.compile(
    r'(\bcollectible\(\r?\n\s*"[^"\r\n]+",\r?\n)\s*'
    r'"(?:[0-9]{3}|PCS[0-9]{2})",\r?\n'
)


def remove_runtime_order_ids(java_source: str) -> str:
    return RUNTIME_ORDER_ARGUMENT.sub(r"\1", java_source)


def replace_generated_region(java_source: str, records: list[Record]) -> str:
    generated = generated_java(records)
    if START_MARKER in java_source and END_MARKER in java_source:
        before, remainder = java_source.split(START_MARKER, 1)
        _, after = remainder.split(END_MARKER, 1)
        return before + generated + after
    anchor = "  public static final List<CollectibleBuilder> ALL ="
    if anchor not in java_source:
        raise ValueError("Could not locate ModCollectible ALL declaration")
    return java_source.replace(anchor, generated + "\n\n" + anchor, 1)


@functools.cache
def fungimist_image_urls() -> dict[str, str]:
    page = ROOT / "build/prts-cache/prts_rogue_0.html"
    source = page.read_text(encoding="utf-8")
    anchors = list(re.finditer(r'<p id="([0-9]+)"[^>]*></p>', source))
    urls: dict[str, str] = {}
    for index, anchor in enumerate(anchors):
        end = anchors[index + 1].start() if index + 1 < len(anchors) else len(source)
        image = re.search(r"<img\b[^>]*>", source[anchor.end():end])
        if image is None:
            raise ValueError(f"Missing Fungimist image tag for No.{anchor.group(1)}")
        tag = image.group(0)
        srcset = re.search(r'\bsrcset="([^"]+)"', tag)
        if srcset:
            candidates = [candidate.strip().split()[0] for candidate in srcset.group(1).split(",")]
            url = candidates[-1]
        else:
            source_url = re.search(r'\bsrc="([^"]+)"', tag)
            if source_url is None:
                raise ValueError(f"Missing Fungimist image URL for No.{anchor.group(1)}")
            url = source_url.group(1)
        urls[anchor.group(1).zfill(3)] = html.unescape(url)
    return urls


def download_image(record: Record) -> bool:
    destination = TEXTURE_ROOT / f"{record.path}.png"
    if destination.is_file():
        return False
    destination.parent.mkdir(parents=True, exist_ok=True)
    image_url = (
        fungimist_image_urls()[record.source_order_id]
        if record.theme.key == "rogue_0"
        else f"{PRTS_IMAGE_ROOT}/{record.icon_id}.png"
    )
    request = urllib.request.Request(
        image_url,
        headers={"User-Agent": USER_AGENT},
    )
    temporary = destination.with_suffix(".png.part")
    last_error: Exception | None = None
    for attempt in range(1, 5):
        try:
            with urllib.request.urlopen(request, timeout=30) as response:
                data = response.read()
            if len(data) < 24 or data[:8] != b"\x89PNG\r\n\x1a\n":
                raise ValueError(f"PRTS response is not PNG: {record.icon_id}")
            temporary.write_bytes(data)
            temporary.replace(destination)
            return True
        except (OSError, urllib.error.URLError) as error:
            last_error = error
            temporary.unlink(missing_ok=True)
            if attempt < 4:
                time.sleep(attempt)
    raise RuntimeError(f"Failed to download PRTS PNG: {record.icon_id}") from last_error


def digest(path: Path) -> str:
    return hashlib.sha256(path.read_bytes()).hexdigest()


EFFECT_AUDIT_START = "<!-- BEGIN GENERATED ADDITIONAL COLLECTIBLE EFFECT AUDIT -->"
EFFECT_AUDIT_END = "<!-- END GENERATED ADDITIONAL COLLECTIBLE EFFECT AUDIT -->"


def write_effect_audit(records: list[Record]) -> None:
    grouped: dict[str, list[tuple[Record, list[Adaptation]]]] = {
        "implemented": [],
        "partial": [],
        "unimplemented": [],
    }
    for record in records:
        adaptations = [adapt_effect(effect) for effect in record.effect_variants]
        grouped[aggregate_status(adaptations)].append((record, adaptations))
    lines = [
        EFFECT_AUDIT_START,
        "",
        "## 其余集成战略藏品效果审计",
        "",
        "本节由 script/import_prts_all_collectibles.py 生成，覆盖灰蕈秘境以外新增的 497 件藏品。",
        "implemented 表示原规则已完全实现；partial 与 unimplemented 的原规则继续保存在 sourceRules。",
        "",
        "| 状态 | 数量 |",
        "| --- | ---: |",
        f"| 完全实现 | {len(grouped['implemented'])} |",
        f"| 部分实现 | {len(grouped['partial'])} |",
        f"| 未实现 | {len(grouped['unimplemented'])} |",
        "",
    ]
    labels = {
        "partial": "部分实现",
        "unimplemented": "未实现",
    }
    for status in ("partial", "unimplemented"):
        entries = grouped[status]
        lines.extend((
            f"### {labels[status]}（{len(entries)} 件）",
            "",
            "| 物品 ID | 藏品 | 适配摘要 | PRTS 原效果 |",
            "| --- | --- | --- | --- |",
        ))
        for record, adaptations in entries:
            summary = "；".join(dict.fromkeys(adaptation.summary for adaptation in adaptations))
            effect = record.effect.replace("|", "\\|").replace("\n", "<br>")
            lines.append(
                f"| {record.path} | {record.name.replace('|', '\\|')} | "
                f"{summary.replace('|', '\\|')} | {effect} |"
            )
        lines.append("")
    lines.append(EFFECT_AUDIT_END)
    generated = "\n".join(lines)
    source = UNIMPLEMENTED_PATH.read_text(encoding="utf-8")
    if EFFECT_AUDIT_START in source and EFFECT_AUDIT_END in source:
        before, remainder = source.split(EFFECT_AUDIT_START, 1)
        _, after = remainder.split(EFFECT_AUDIT_END, 1)
        updated = before.rstrip() + "\n\n" + generated + after
    else:
        updated = source.rstrip() + "\n\n" + generated + "\n"
    UNIMPLEMENTED_PATH.write_text(updated, encoding="utf-8", newline="\n")


def write_outputs(records: list[Record], skipped: list[dict[str, str]], skip_images: bool) -> None:
    java_source = JAVA_PATH.read_text(encoding="utf-8")
    previous_paths: set[str] = set()
    previous_paths_by_icon: dict[str, str] = {}
    if AUDIT_PATH.is_file():
        previous_audit = json.loads(AUDIT_PATH.read_text(encoding="utf-8"))
        previous_paths = {entry["path"] for entry in previous_audit.get("records", [])}
        previous_paths_by_icon = {
            entry["iconId"]: entry["path"]
            for entry in previous_audit.get("records", [])
        }

    texture_root = TEXTURE_ROOT.resolve()
    migrations: list[tuple[Path, Path]] = []
    for record in records:
        previous_path = previous_paths_by_icon.get(record.icon_id)
        if not previous_path or previous_path == record.path:
            continue
        source = (TEXTURE_ROOT / f"{previous_path}.png").resolve()
        destination = (TEXTURE_ROOT / f"{record.path}.png").resolve()
        if source.parent != texture_root or destination.parent != texture_root:
            raise ValueError(f"Texture migration escaped texture root: {source} -> {destination}")
        if source.is_file() and destination.is_file():
            if digest(source) != digest(destination):
                raise ValueError(f"Texture migration collision: {source} -> {destination}")
        elif source.is_file():
            migrations.append((source, destination))
    for source, destination in migrations:
        source.replace(destination)

    generated_source = replace_generated_region(java_source, records)
    JAVA_PATH.write_text(remove_runtime_order_ids(generated_source), encoding="utf-8", newline="\n")

    tag = json.loads(TAG_PATH.read_text(encoding="utf-8")) if TAG_PATH.is_file() else {"replace": False, "values": []}
    previous_values = {f"zinecraft:{path}" for path in previous_paths}
    retained_values = [value for value in tag.get("values", []) if value not in previous_values]
    values = list(dict.fromkeys([*retained_values, *(f"zinecraft:{record.path}" for record in records)]))
    TAG_PATH.write_text(json.dumps({"replace": False, "values": values}, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")

    downloaded = 0
    image_hashes: dict[str, str] = {}
    for index, record in enumerate(records, 1):
        if not skip_images and download_image(record):
            downloaded += 1
            print(f"[{index:04d}/{len(records):04d}] {record.theme.prefix} No.{record.source_order_id} {record.name}")
        image = TEXTURE_ROOT / f"{record.path}.png"
        if image.is_file():
            image_hashes[record.icon_id] = digest(image)
        elif not skip_images:
            raise FileNotFoundError(image)

    AUDIT_PATH.parent.mkdir(parents=True, exist_ok=True)
    AUDIT_PATH.write_text(json.dumps({
        "records": [record.audit_json() for record in records],
        "skippedDuplicateNames": skipped,
    }, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    if not skip_images:
        if MANIFEST_PATH.is_file():
            pinned = json.loads(MANIFEST_PATH.read_text(encoding="utf-8")).get("images", {})
            changed = {
                icon_id: (expected, image_hashes.get(icon_id))
                for icon_id, expected in pinned.items()
                if image_hashes.get(icon_id) != expected
            }
            if changed:
                raise ValueError(f"PRTS PNG digest changed during path migration: {changed}")
        MANIFEST_PATH.parent.mkdir(parents=True, exist_ok=True)
        MANIFEST_PATH.write_text(json.dumps({"algorithm": "SHA-256", "images": dict(sorted(image_hashes.items()))}, indent=2) + "\n", encoding="utf-8")
        current_paths = {record.path for record in records}
        for obsolete in sorted(previous_paths - current_paths):
            obsolete_file = TEXTURE_ROOT / f"{obsolete}.png"
            if obsolete_file.is_file():
                obsolete_file.unlink()

    lines = [
        "# PRTS 其余集成战略藏品来源",
        "",
        f"新增 {len(records)} 件同名去重后的藏品；跳过 {len(skipped)} 条同名记录。",
        "同一 PRTS 藏品模板列出多档效果时，原效果完整保留全部档位，由运行时特殊条件档位选择对应效果。",
        "字段名、注册 ID 与 PNG 路径采用英文名；IS7 暂缺的 101 个英文名记录在 script/data/prts_is7_temporary_en_names.json。",
        "",
        "| 字段 | 资源路径 | 主题 | 原编号 | 藏品 | PRTS iconId |",
        "| --- | --- | --- | --- | --- | --- |",
    ]
    lines.extend(
        f"| `{record.field_name}` | `{record.path}` | {record.theme.zh_cn} | No.{record.source_order_id} | "
        f"{record.name.replace('|', '\\|')} | `{record.icon_id}` |"
        for record in records
    )
    lines.extend(("", "## 资料页", ""))
    lines.extend(f"- [{theme.zh_cn}](https://prts.wiki/w/{theme.page})" for theme in THEMES)
    lines.extend(("", "PNG 均直接来自 PRTS 图片资源域，未重绘或生成。", ""))
    LEDGER_PATH.write_text("\n".join(lines), encoding="utf-8", newline="\n")
    write_effect_audit(records)
    print(
        f"Wrote {len(records)} new collectibles; skipped {len(skipped)} duplicate-name records; "
        f"migrated {len(migrations)} PNGs; downloaded {downloaded} PNGs."
    )


def main() -> int:
    args = arguments()
    java_source = JAVA_PATH.read_text(encoding="utf-8")
    records, skipped = deduplicate(load_records(), java_source)
    validate_field_names(records, java_source)
    print(f"PRTS records: {len(records) + len(skipped)}; selected new names: {len(records)}; skipped duplicates: {len(skipped)}")
    for theme in THEMES:
        print(f"{theme.prefix}: {sum(record.theme == theme for record in records)} selected")
    if args.write:
        write_outputs(records, skipped, args.skip_images)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())

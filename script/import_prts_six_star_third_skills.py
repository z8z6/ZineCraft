"""从 PRTS 导入国服六星干员的三技能（专精三）资料与图标。"""

from __future__ import annotations

import argparse
import html
import json
import re
import time
import urllib.error
import urllib.parse
import urllib.request
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/java/com/cxxcxx/zinecraft/core/skill/ModSkill.java"
TEXTURE_DIR = ROOT / "src/main/resources/assets/zinecraft/textures/item"
API = "https://prts.wiki/api.php"
USER_AGENT = "Zinecraft-PRTS-Skill-Importer/1.0"
MAX_ATTEMPTS = 4
BEGIN_MARKER = "  // BEGIN GENERATED PRTS SIX-STAR THIRD SKILLS"
END_MARKER = "  // END GENERATED PRTS SIX-STAR THIRD SKILLS"

PROFESSIONS = {
    "先锋": "VANGUARD",
    "近卫": "GUARD",
    "重装": "DEFENDER",
    "狙击": "SNIPER",
    "术师": "CASTER",
    "医疗": "MEDIC",
    "辅助": "SUPPORTER",
    "特种": "SPECIALIST",
}

THEMES = {
    "VANGUARD": "COST_RECOVERY",
    "GUARD": "AREA_SLASH",
    "DEFENDER": "HEAL_AND_SLOW",
    "SNIPER": "RAPID_FIRE",
    "CASTER": "VOLCANIC_BURST",
    "MEDIC": "SANCTUARY",
    "SUPPORTER": "SLOWING_FIELD",
    "SPECIALIST": "DEPLOYMENT_STUN",
}

RECOVERY_TYPES = {
    "自动回复": "AUTO_RECOVERY",
    "攻击回复": "OFFENSIVE_RECOVERY",
    "受击回复": "DEFENSIVE_RECOVERY",
    "被动": "PASSIVE",
}

TRIGGER_TYPES = {
    "手动触发": "MANUAL",
    "自动触发": "AUTO",
}

# 这些三技能已经在 ModSkill 中手工登记；导入器保留现有常量和专用展示配置。
EXISTING = {
    ("银灰", "真银斩"),
    ("能天使", "过载模式"),
    ("维什戴尔", "爆裂黎明"),
    ("艾雅法拉", "火山"),
    ("塞雷娅", "钙质化"),
    ("夜莺", "圣域"),
    ("铃兰", "狐火渺然"),
}


def api_request(**params: str) -> dict:
    query = urllib.parse.urlencode({"format": "json", **params})
    request = urllib.request.Request(f"{API}?{query}", headers={"User-Agent": USER_AGENT})
    for attempt in range(1, MAX_ATTEMPTS + 1):
        try:
            with urllib.request.urlopen(request, timeout=60) as response:
                return json.load(response)
        except (urllib.error.HTTPError, urllib.error.URLError, TimeoutError):
            if attempt == MAX_ATTEMPTS:
                raise
            time.sleep(attempt)
    raise AssertionError("unreachable")


def download(url: str, destination: Path) -> None:
    request = urllib.request.Request(url, headers={"User-Agent": USER_AGENT})
    for attempt in range(1, MAX_ATTEMPTS + 1):
        try:
            with urllib.request.urlopen(request, timeout=60) as response:
                payload = response.read()
            break
        except (urllib.error.HTTPError, urllib.error.URLError, TimeoutError):
            if attempt == MAX_ATTEMPTS:
                raise
            time.sleep(attempt)
    destination.parent.mkdir(parents=True, exist_ok=True)
    destination.write_bytes(payload)


def six_star_operators() -> list[dict[str, str]]:
    result = api_request(
        action="cargoquery",
        tables="chara",
        fields="_pageName=operator,en,profession,rarity,charIndex",
        where="rarity=5 AND charIndex>0",
        order_by="charIndex",
        limit="500",
    )
    return [entry["title"] for entry in result["cargoquery"]]


def page_source(operator: str) -> str:
    result = api_request(action="parse", page=operator, prop="wikitext")
    return result["parse"]["wikitext"]["*"]


def template_value(block: str, key: str) -> str:
    match = re.search(rf"(?m)^\|{re.escape(key)}=(.*)$", block)
    return match.group(1).strip() if match else ""


def third_skill_block(source: str) -> str:
    heading = re.search(r"'''技能3（[^\n]*'''", source)
    if not heading:
        return ""
    start = source.find("{{技能", heading.end())
    if start < 0:
        return ""
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
                return source[start:index]
            continue
        index += 1
    return ""


def collapse_templates(value: str) -> str:
    pattern = re.compile(r"\{\{([^{}]*)}}")
    while "{{" in value:
        def replace(match: re.Match[str]) -> str:
            parts = match.group(1).split("|")
            name = parts[0].strip()
            args = [part.strip() for part in parts[1:] if "=" not in part]
            if name in {"color", "术语", "修正", "注", "黑幕"}:
                return args[-1] if args else ""
            if name in {"*", "±"}:
                return args[-1] if args else ""
            return args[-1] if args else name

        updated = pattern.sub(replace, value)
        if updated == value:
            break
        value = updated
    return value


def clean_wikitext(value: str) -> str:
    value = re.sub(r"<br\s*/?>", "；", value, flags=re.IGNORECASE)
    value = re.sub(r"<ref[^>]*>.*?</ref>", "", value, flags=re.DOTALL | re.IGNORECASE)
    value = re.sub(r"<ref[^>]*/>", "", value, flags=re.IGNORECASE)
    value = re.sub(
        r"\[\[([^]]+)]]",
        lambda match: match.group(1).split("|")[-1],
        value,
    )
    value = collapse_templates(value)
    value = re.sub(
        r"\[\[([^]]+)]]",
        lambda match: match.group(1).split("|")[-1],
        value,
    )
    value = re.sub(r"'''?", "", value)
    value = re.sub(r"<[^>]+>", "", value)
    value = html.unescape(value)
    value = re.sub(r"\s+", " ", value)
    return value.strip(" ；")


def integer(value: str, *, nullable: bool = False) -> int | None:
    cleaned = clean_wikitext(value)
    match = re.search(r"-?\d+", cleaned)
    if match:
        return int(match.group())
    if nullable:
        return None
    return 0


def slug(value: str) -> str:
    value = value.lower().replace("š", "s").replace("'", "")
    value = re.sub(r"[^a-z0-9]+", "_", value).strip("_")
    return value or "unknown"


def icon_url(skill_name: str) -> str:
    result = api_request(
        action="query",
        titles=f"File:技能_{skill_name}.png",
        prop="imageinfo",
        iiprop="url",
    )
    page = next(iter(result["query"]["pages"].values()))
    image_info = page.get("imageinfo")
    if not image_info:
        raise ValueError(f"PRTS 未找到技能图标：{skill_name}")
    return image_info[0]["url"]


def build_entry(operator: dict[str, str], used_paths: set[str]) -> dict[str, object]:
    source = page_source(operator["operator"])
    block = third_skill_block(source)
    if not block:
        raise ValueError("未找到第三技能模板")

    skill_name = clean_wikitext(template_value(block, "技能名"))
    skill_name_en_source = clean_wikitext(template_value(block, "技能名en"))
    skill_name_en = skill_name_en_source or skill_name
    recovery_zh = clean_wikitext(template_value(block, "技能类型1"))
    trigger_zh = clean_wikitext(template_value(block, "技能类型2"))
    profession = PROFESSIONS[operator["profession"]]
    operator_path = slug(operator["en"])
    if operator_path == "unknown":
        operator_path = f"operator_{operator['charIndex']}"
    path_source = skill_name_en_source or f"{operator_path}_third_skill"
    base_path = f"skill_{slug(path_source)}"
    path = base_path
    if path in used_paths:
        path = f"{base_path}_{operator['charIndex']}"
    used_paths.add(path)

    return {
        "path": path,
        "operatorZhCn": operator["operator"],
        "operatorEnUs": operator["en"] or operator["operator"],
        "profession": profession,
        "skillZhCn": skill_name,
        "skillEnUs": skill_name_en,
        "recoveryType": RECOVERY_TYPES[recovery_zh],
        "triggerType": (
            "ON_DEPLOYMENT"
            if not trigger_zh and RECOVERY_TYPES[recovery_zh] == "PASSIVE"
            else TRIGGER_TYPES[trigger_zh]
        ),
        "initialSp": integer(template_value(block, "技能专精3初始")),
        "spCost": integer(template_value(block, "技能专精3消耗")),
        "durationSeconds": integer(template_value(block, "技能专精3持续"), nullable=True),
        "descriptionZhCn": clean_wikitext(template_value(block, "技能专精3描述")),
        "theme": THEMES[profession],
        "sourceUrl": "https://prts.wiki/w/" + urllib.parse.quote(operator["operator"]),
    }


def java_string(value: str) -> str:
    return json.dumps(value, ensure_ascii=False)


def java_field(entry: dict[str, object]) -> str:
    path = str(entry["path"])
    field_name = path.removeprefix("skill_").upper()
    duration = "null" if entry["durationSeconds"] is None else str(entry["durationSeconds"])
    source_url = str(entry["sourceUrl"])
    return f"""  // PRTS: {source_url}
  public static final SkillBuilder {field_name} = skill(
      {java_string(path)},
      {java_string(str(entry["skillZhCn"]))}
  )
      .enUs({java_string(str(entry["skillEnUs"]))})
      .operator(
          {java_string(str(entry["operatorZhCn"]))},
          {java_string(str(entry["operatorEnUs"]))},
          SkillProfession.{entry["profession"]}
      )
      .activation(
          SkillSpRecoveryType.{entry["recoveryType"]},
          SkillTriggerType.{entry["triggerType"]}
      )
      .stats({entry["initialSp"]}, {entry["spCost"]}, {duration})
      .description(
          {java_string(str(entry["descriptionZhCn"]))},
          {java_string(str(entry["descriptionZhCn"]))}
      )
      .effect(reusedEffect(SkillDemoTheme.{entry["theme"]}))
      .theme(SkillDemoTheme.{entry["theme"]})
      .build();"""


def write_mod_skill(entries: list[dict[str, object]]) -> None:
    source = OUTPUT.read_text(encoding="utf-8")
    begin = source.find(BEGIN_MARKER)
    end = source.find(END_MARKER)
    if begin < 0 or end < 0 or end < begin:
        raise ValueError("ModSkill 缺少 PRTS 技能生成区段标记")
    generated = "\n\n".join(java_field(entry) for entry in entries)
    replacement = BEGIN_MARKER + "\n" + generated + "\n" + END_MARKER
    source = source[:begin] + replacement + source[end + len(END_MARKER):]
    OUTPUT.write_text(source, encoding="utf-8")


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--skip-icons", action="store_true", help="仅刷新 ModSkill，不下载 PRTS 图标")
    args = parser.parse_args()

    operators = six_star_operators()
    entries: list[dict[str, object]] = []
    skipped_existing: list[str] = []
    errors: list[str] = []
    used_paths = {
        "skill_truesilver_slash",
        "skill_overloading_mode",
        "skill_explosive_dawn",
        "skill_volcano",
        "skill_calcification",
        "skill_sanctuary",
        "skill_foxfire_haze",
    }

    for index, operator in enumerate(operators, start=1):
        try:
            entry = build_entry(operator, used_paths)
            key = (str(entry["operatorZhCn"]), str(entry["skillZhCn"]))
            if key in EXISTING:
                skipped_existing.append(f"{key[0]}：{key[1]}")
                continue
            entries.append(entry)
            if not args.skip_icons:
                destination = TEXTURE_DIR / f"{entry['path']}.png"
                if not destination.exists():
                    download(icon_url(str(entry["skillZhCn"])), destination)
            print(f"[{index}/{len(operators)}] {entry['operatorZhCn']}：{entry['skillZhCn']}")
            time.sleep(0.05)
        except Exception as exc:  # noqa: BLE001 - 汇总全部远端数据问题后统一失败
            errors.append(f"{operator['operator']}：{exc}")

    if errors:
        raise SystemExit("PRTS 导入失败：\n- " + "\n- ".join(errors))

    if len(operators) != len(entries) + len(skipped_existing):
        raise SystemExit("六星干员数量与生成结果不一致")
    write_mod_skill(entries)
    print(f"写入 {OUTPUT.relative_to(ROOT)}：新增 {len(entries)}，复用现有 {len(skipped_existing)}")


if __name__ == "__main__":
    main()

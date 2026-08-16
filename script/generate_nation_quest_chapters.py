#!/usr/bin/env python3
"""Generate one FTB Quests structure-exploration chapter for each Terra nation."""

from __future__ import annotations

from dataclasses import dataclass
from pathlib import Path
import re


ROOT = Path(__file__).resolve().parents[1]
QUEST_ROOT = ROOT / "src/main/resources/zinecraft/ftbquests/quests"
CHAPTER_ROOT = QUEST_ROOT / "chapters"
GROUP_ID = "4E4154494F4E5347"


@dataclass(frozen=True)
class Structure:
    path: str
    zh: str
    en: str


@dataclass(frozen=True)
class Nation:
    path: str
    zh: str
    en: str
    icon: str
    structures: tuple[Structure, ...]


NATIONS = (
    Nation("aegir", "阿戈尔", "Aegir", "zinecraft:aegir_abyssal_slate", (Structure("aegir_subsea_enclave", "阿戈尔海底聚居地", "Aegir Subsea Enclave"), Structure("aegir_volcanic_beacon", "阿戈尔火山信标", "Aegir Volcanic Beacon"), Structure("aegir_abyssal_observatory", "阿戈尔深渊观测站", "Aegir Abyssal Observatory"))),
    Nation("bolivar", "玻利瓦尔", "Bolivar", "zinecraft:bolivar_war_scoured_soil", (Structure("bolivar_dossoles_district", "玻利瓦尔多索雷斯城区", "Bolivar Dossoles District"), Structure("bolivar_dossoles_yacht", "玻利瓦尔多索雷斯游艇", "Bolivar Dossoles Yacht"), Structure("bolivar_race_checkpoint", "玻利瓦尔竞速检查站", "Bolivar Race Checkpoint"))),
    Nation("higashi", "东国", "Higashi", "zinecraft:higashi_shadow_loam", (Structure("higashi_sokogawa_town", "东国索谷川町", "Higashi Sokogawa Town"), Structure("higashi_rift_shrine", "东国裂谷神社", "Higashi Rift Shrine"), Structure("higashi_sokogawa_watchtower", "东国索谷川瞭望塔", "Higashi Sokogawa Watchtower"))),
    Nation("durin", "杜林", "Durin", "zinecraft:durin_garden_moss", (Structure("durin_ideal_city_block", "杜林理想城街区", "Durin Ideal City Block"), Structure("durin_dome_station", "杜林穹顶车站", "Durin Dome Station"), Structure("durin_water_park", "杜林水上乐园", "Durin Water Park"))),
    Nation("columbia", "哥伦比亚", "Columbia", "zinecraft:columbia_canyon_soil", (Structure("columbia_frontier_town", "哥伦比亚边疆城镇", "Columbia Frontier Town"), Structure("columbia_frontier_lab", "哥伦比亚边疆实验室", "Columbia Frontier Lab"), Structure("columbia_prison_outpost", "哥伦比亚监狱哨站", "Columbia Prison Outpost"))),
    Nation("kazimierz", "卡西米尔", "Kazimierz", "zinecraft:kazimierz_steppe_turf", (Structure("kazimierz_knight_borough", "卡西米尔骑士城区", "Kazimierz Knight Borough"), Structure("kazimierz_arena_gate", "卡西米尔竞技场大门", "Kazimierz Arena Gate"), Structure("kazimierz_knight_monument", "卡西米尔骑士纪念碑", "Kazimierz Knight Monument"))),
    Nation("kazdel", "卡兹戴尔", "Kazdel", "zinecraft:kazdel_scarred_ash", (Structure("kazdel_sarkaz_settlement", "卡兹戴尔萨卡兹聚落", "Kazdel Sarkaz Settlement"), Structure("kazdel_babel_ruins", "卡兹戴尔巴别塔遗迹", "Kazdel Babel Ruins"), Structure("kazdel_sarkaz_camp", "卡兹戴尔萨卡兹营地", "Kazdel Sarkaz Camp"))),
    Nation("laterano", "拉特兰", "Laterano", "zinecraft:laterano_alluvial_chalk", (Structure("laterano_monastery_town", "拉特兰修道院城镇", "Laterano Monastery Town"), Structure("laterano_revelation_tower", "拉特兰启示石塔", "Laterano Revelation Tower"), Structure("laterano_ambrosius_chapel", "拉特兰安布罗修礼拜堂", "Laterano Ambrosius Chapel"), Structure("laterano_host", "拉特兰主机", "Laterano Host"))),
    Nation("leithanien", "莱塔尼亚", "Leithanien", "zinecraft:leithanien_twilight_humus", (Structure("leithanien_music_town", "莱塔尼亚音乐城镇", "Leithanien Music Town"), Structure("leithanien_twin_spires", "莱塔尼亚双塔", "Leithanien Twin Spires"), Structure("leithanien_concert_hall", "莱塔尼亚音乐厅", "Leithanien Concert Hall"))),
    Nation("rim_billiton", "雷姆必拓", "Rim Billiton", "zinecraft:rim_billiton_mine_tailings", (Structure("rim_billiton_mining_camp", "雷姆必拓采矿营地", "Rim Billiton Mining Camp"), Structure("rim_billiton_mining_derrick", "雷姆必拓采矿井架", "Rim Billiton Mining Derrick"), Structure("rim_billiton_rail_depot", "雷姆必拓铁路货站", "Rim Billiton Rail Depot"))),
    Nation("minos", "米诺斯", "Minos", "zinecraft:minos_sunbaked_earth", (Structure("minos_heroic_polis", "米诺斯英雄城邦", "Minos Heroic Polis"), Structure("minos_heroes_temple", "米诺斯英雄神殿", "Minos Heroes Temple"), Structure("minos_heroes_plaza", "米诺斯英雄广场", "Minos Heroes Plaza"))),
    Nation("sargon", "萨尔贡", "Sargon", "zinecraft:sargon_desert_crust", (Structure("sargon_oasis_town", "萨尔贡绿洲城镇", "Sargon Oasis Town"), Structure("sargon_golden_bazaar", "萨尔贡黄金集市", "Sargon Golden Bazaar"), Structure("sargon_long_spring_well", "萨尔贡长泉水井", "Sargon Long Spring Well"))),
    Nation("sami", "萨米", "Sami", "zinecraft:sami_frost_moss", (Structure("sami_snowpriest_village", "萨米雪祀村落", "Sami Snowpriest Village"), Structure("sami_cyclops_altar", "萨米独眼巨人祭坛", "Sami Cyclops Altar"), Structure("sami_snowpriest_lodge", "萨米雪祀居所", "Sami Snowpriest Lodge"))),
    Nation("victoria", "维多利亚", "Victoria", "zinecraft:victoria_moorland_soil", (Structure("victoria_industrial_borough", "维多利亚工业城区", "Victoria Industrial Borough"), Structure("victoria_defence_cannon", "维多利亚防御炮台", "Victoria Defence Cannon"), Structure("victoria_steam_station", "维多利亚蒸汽车站", "Victoria Steam Station"))),
    Nation("ursus", "乌萨斯", "Ursus", "zinecraft:ursus_permafrost", (Structure("ursus_northern_town", "乌萨斯北方城镇", "Ursus Northern Town"), Structure("ursus_sarcophagus_station", "乌萨斯石棺站", "Ursus Sarcophagus Station"), Structure("ursus_northern_mine_tower", "乌萨斯北方矿塔", "Ursus Northern Mine Tower"))),
    Nation("kjerag", "谢拉格", "Kjerag", "zinecraft:kjerag_sacred_snowstone", (Structure("kjerag_mountain_village", "谢拉格山地村落", "Kjerag Mountain Village"), Structure("kjerag_karlan_monastery", "谢拉格喀兰修道院", "Kjerag Karlan Monastery"), Structure("kjerag_sacred_plaza", "谢拉格圣洁广场", "Kjerag Sacred Plaza"))),
    Nation("siracusa", "叙拉古", "Siracusa", "zinecraft:siracusa_rain_darkened_soil", (Structure("siracusa_family_town", "叙拉古家族城镇", "Siracusa Family Town"), Structure("siracusa_family_court", "叙拉古家族法庭", "Siracusa Family Court"), Structure("siracusa_family_theatre", "叙拉古家族剧院", "Siracusa Family Theatre"))),
    Nation("yan", "炎国", "Yan", "zinecraft:yan_mountain_soil", (Structure("yan_shangshu_town", "炎国尚蜀城镇", "Yan Shangshu Town"), Structure("yan_yumen_beacon", "炎国玉门烽台", "Yan Yumen Beacon"), Structure("yan_shangshu_pavilion", "炎国尚蜀亭阁", "Yan Shangshu Pavilion"))),
    Nation("iberia", "伊比利亚", "Iberia", "zinecraft:iberia_salt_crusted_gravel", (Structure("iberia_coastal_town", "伊比利亚滨海城镇", "Iberia Coastal Town"), Structure("iberia_eye_lighthouse", "伊比利亚之眼灯塔", "Iberia Eye Lighthouse"), Structure("iberia_saltwind_chapel", "伊比利亚盐风礼拜堂", "Iberia Saltwind Chapel"))),
)


def chapter_id(index: int) -> str:
    return f"7A000000000000{index:02X}"


def quest_id(nation_index: int, structure_index: int) -> str:
    return f"7B{nation_index:02X}0000000000{structure_index:02X}"


def task_id(nation_index: int, structure_index: int) -> str:
    return f"7C{nation_index:02X}0000000000{structure_index:02X}"


def write_chapter(nation: Nation, nation_index: int) -> None:
    count = len(nation.structures)
    start_x = -1.5 * (count - 1)
    quests = []
    for structure_index, structure in enumerate(nation.structures, 1):
        x = start_x + 3.0 * (structure_index - 1)
        quests.append(
            "\n".join(
                (
                    "\t\t{",
                    f'\t\t\tid: "{quest_id(nation_index, structure_index)}"',
                    '\t\t\ticon: { id: "minecraft:compass" }',
                    f'\t\t\ttasks: [{{ id: "{task_id(nation_index, structure_index)}", structure: "zinecraft:{structure.path}", type: "structure" }}]',
                    f"\t\t\tx: {x:.1f}d",
                    "\t\t\ty: 0.0d",
                    "\t\t}",
                )
            )
        )

    content = "\n".join(
        (
            "{",
            "\tdefault_hide_dependency_lines: false",
            '\tdefault_quest_shape: "circle"',
            f'\tfilename: "nation_{nation.path}"',
            f'\tgroup: "{GROUP_ID}"',
            f'\ticon: {{ id: "{nation.icon}" }}',
            f'\tid: "{chapter_id(nation_index)}"',
            f"\torder_index: {nation_index}",
            '\tprogression_mode: "flexible"',
            "\tquest_links: [ ]",
            "\tquests: [",
            "\n".join(quests),
            "\t]",
            "}",
            "",
        )
    )
    (CHAPTER_ROOT / f"nation_{nation.path}.snbt").write_text(content, encoding="utf-8", newline="\n")


def generated_translation_lines(locale: str) -> list[str]:
    lines: list[str] = []
    for nation_index, nation in enumerate(NATIONS, 1):
        nation_name = nation.zh if locale == "zh_cn" else nation.en
        chapter_title = f"{nation_name}：结构探索" if locale == "zh_cn" else f"{nation_name}: Structure Exploration"
        lines.append(f'\tchapter.{chapter_id(nation_index)}.title: "{chapter_title}"')
        for structure_index, structure in enumerate(nation.structures, 1):
            name = structure.zh if locale == "zh_cn" else structure.en
            qid = quest_id(nation_index, structure_index)
            tid = task_id(nation_index, structure_index)
            if locale == "zh_cn":
                subtitle = "进入结构范围后自动完成"
                description = f"前往并进入 zinecraft:{structure.path} 的有效结构范围。FTB Quests 会在服务器端自动检测访问状态。"
                task_title = f"访问{name}"
            else:
                subtitle = "Completes automatically inside the structure"
                description = f"Travel to and enter the valid bounds of zinecraft:{structure.path}. FTB Quests checks the visit on the server."
                task_title = f"Visit {name}"
            lines.extend(
                (
                    f'\tquest.{qid}.title: "{name}"',
                    f'\tquest.{qid}.quest_subtitle: "{subtitle}"',
                    f'\tquest.{qid}.quest_desc: ["{description}"]',
                    f'\ttask.{tid}.title: "{task_title}"',
                )
            )
    return lines


def update_language(locale: str) -> None:
    path = QUEST_ROOT / "lang" / f"{locale}.snbt"
    lines = path.read_text(encoding="utf-8").splitlines()
    generated_key = re.compile(r"^\s*(?:chapter|quest|task)\.7[ABC][0-9A-F]{14}\.")
    lines = [line for line in lines if not generated_key.match(line)]
    while lines and not lines[-1].strip():
        lines.pop()
    if not lines or lines[-1].strip() != "}":
        raise ValueError(f"Unexpected language SNBT ending: {path}")
    closing_brace = lines.pop()
    while lines and not lines[-1].strip():
        lines.pop()
    lines.extend(("", *generated_translation_lines(locale), closing_brace))
    path.write_text("\n".join(lines) + "\n", encoding="utf-8", newline="\n")


def main() -> None:
    CHAPTER_ROOT.mkdir(parents=True, exist_ok=True)
    for index, nation in enumerate(NATIONS, 1):
        write_chapter(nation, index)
    update_language("zh_cn")
    update_language("en_us")
    print(f"Generated {len(NATIONS)} nation chapters with {sum(len(n.structures) for n in NATIONS)} structure tasks.")


if __name__ == "__main__":
    main()

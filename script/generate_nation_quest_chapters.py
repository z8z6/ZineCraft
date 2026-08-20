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


@dataclass(frozen=True)
class Dossier:
    setting_zh: str
    setting_en: str
    cities: str
    regions: str


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


DOSSIERS = {
    "aegir": Dossier(
        "地处深海、对外相对封锁的国家。国家权力掌握在科学执政官与技术执政官手中，并以深海猎人、海巡队等力量对抗海嗣。",
        "A deep-sea nation that remains relatively closed to the outside world. Scientific and technical consuls hold state power, while forces including the Abyssal Hunters and maritime patrols oppose the Seaborn.",
        "弥利亚留姆",
        "现有 PRTS 地理总目录未列出更多可确认地名",
    ),
    "bolivar": Dossier(
        "位于泰拉大陆西北部。结晶时代因矿石开采范围与哥伦比亚重叠而发生冲突，国内长期处于辛嘉斯王朝、联合政府与真正玻利瓦尔人三方内战之中。",
        "A northwestern Terra nation. Overlapping Originium mining claims brought conflict with Columbia, while the Sinjas Dynasty, the Coalition Government, and the True Bolivarians remain locked in civil war.",
        "多索雷斯、拉乌尼达、特科马",
        "黑流树海、内河",
    ),
    "higashi": Dossier(
        "位于泰拉大陆东北角，常年处于南北朝内战状态：北方由光严氏当家，南方由光元氏当家，八大家族掌握全国军权。",
        "A nation in Terra's northeast, long divided by civil war between northern and southern courts. Eight great families hold the country's military power.",
        "南院行在御机大社、北院镇守锁川城、姬户城、后川城、二户城、新安芸市、露华村；城市辖区：皇居大社、仁田街、锻冶町、都厅街",
        "常暗裂谷",
    ),
    "durin": Dossier(
        "杜林人的社会以城市为单位，称为城邦的居住地分布在泰拉各处的地表之下；杜林人拥有先进科技。",
        "Durin society is organized around city-states scattered beneath Terra's surface, and Durin civilization possesses advanced technology.",
        "际崖城、天际城",
        "现有 PRTS 地理总目录未列出更多可确认地名",
    ),
    "columbia": Dossier(
        "由维多利亚西北开拓区的城邦联盟发展而来的年轻联邦，独立后迅速成为具有影响力的政治经济实体，并持续寻求更高的国际地位。",
        "A young federation formed from city-states in Victoria's northwestern frontier. It developed rapidly after independence into an influential political and economic power seeking a greater international role.",
        "特里蒙、提卡伦多、堡垒山城、纽莱堡市、圣苏菲城、铸铁城、北诺斯维尔、新曼法斯特、蓝卡坞、吉沃吉亚、铁驮镇、达维镇",
        "麦克斯特区、螺旋桨天堂、加斯帕荒原、骸骨荒原、苏里根湖",
    ),
    "kazimierz": Dossier(
        "卡西米尔联合领是骑士与商业之国，举办骑士特别锦标赛；传统骑士精神与利益至上的商业思想正激烈交锋。",
        "The Kazimierz Major is a land of knights and commerce that hosts the Kazimierz Major tournament, where traditional chivalry clashes with profit-driven commercial interests.",
        "卡瓦莱利亚基（大骑士领）、茨沃涅克、奥格尼斯科、滴水村、垒石村、沥泉村",
        "黄金平原",
    ),
    "kazdel": Dossier(
        "萨卡兹人的国家，常年内战且一度处于无政府状态。卡兹戴尔城由军事委员会直接管理，各部族受对应王庭管辖。",
        "The homeland of the Sarkaz, scarred by prolonged civil war and periods of anarchy. Kazdel City is directly administered by the Military Commission, while Sarkaz tribes answer to their respective Royal Courts.",
        "贝罗尼村、卡兹戴尔城（今卡兹戴尔）",
        "现有 PRTS 地理总目录未列出更多可确认地名",
    ),
    "laterano": Dossier(
        "位于泰拉中部的神权国家。公共权力以践行律法为依据，由教宗与枢机团构成统治核心；长期中立和枢纽位置使其成为重要自由贸易港。",
        "A theocratic state in central Terra. Public authority is exercised in the name of the Law by the Pope and Curia, while long-standing neutrality and a central location make Laterano an important free-trade hub.",
        "司提望区、安布罗修区、法柏尔区、格芬区、米迦莱昂区、圣马尔索区、伊卡莱西亚区",
        "现有 PRTS 地理总目录未列出更多可确认地名",
    ),
    "leithanien": Dossier(
        "实行选举君主制与邦联制，九个选帝侯大区保有各自政府、法律与军队。源石技艺、音乐和艺术教育深刻影响其政治与社会。",
        "An elective monarchy and confederation whose nine electoral districts retain their own governments, laws, and armies. Originium Arts, music, and arts education profoundly shape its politics and society.",
        "崔林特尔梅、沃伦姆德、维谢海姆、格林登；崔林特尔梅辖区：博登区、约瑟夫加滕区、维恩区、卢滕区、双角区、新利奥波德区、巴赫区、精进区；维谢海姆辖区：高庭区、夕照区",
        "海登施威尔大区、瓦瑟领大区、福特冈大区、奥施登海姆大区、凯普拉尼大区、恩瓦德大区、鲁珀坎大区、施彤领大区（费尔斯）、厄登赫尔大区、乌提卡领",
    ),
    "rim_billiton": Dossier(
        "以矿业为主要产业，并参与与谢拉格的贸易；历史上曾与维多利亚围绕矿业问题发生冲突。",
        "A nation whose principal industry is mining and which trades with Kjerag. It has historically clashed with Victoria over mining interests.",
        "终极大铁屯、钢铁萝卜城、铁腕城、大涌泉镇、洋蓟村、芜菁镇、太阳谷、红砂镇、锈渣子镇",
        "格林梅多自治州、比格皮勒自治州、双倍黑尔梅特矿区、零号公路、南境、大风滩、咧嘴谷",
    ),
    "minos": Dossier(
        "前身为古阿加门王国。民众曾在十二英雄领导下驱逐萨尔贡军队，英雄信仰在米诺斯广为盛行。",
        "The successor to ancient Agamem. Its people expelled Sargonian forces under the leadership of the Twelve Heroes, and reverence for heroes remains widespread.",
        "科林尼亚、雅赛努斯、拉刻代蒙、阿克罗蒂村、爱琴",
        "阿涅斯河、荷谟伊山、特尔斐运河、赫里亚山",
    ),
    "sargon": Dossier(
        "位于泰拉大陆西南角的广阔帝国，境内交织着荒漠、绿洲与雨林。土地多由王酋分管，许多居民生活在环境严苛的荒野和密林之中。",
        "A vast empire in southwestern Terra where deserts, oases, and rainforests meet. Much of its territory is governed by Ameers, while many inhabitants live in harsh wilderness and dense forests.",
        "长泉镇、费坤城",
        "阿卡胡拉、伊巴特地区、凯尔图恩谷地；其他已知地名：瓦伊凡、米纳特哈玛仪",
    ),
    "sami": Dossier(
        "位居泰拉北方，由诸多部族构成，拥有独特的人与自然相处模式。",
        "A northern land composed of many tribes, distinguished by a unique relationship between its people and nature.",
        "察帕特",
        "原初之地、冬牙群山",
    ),
    "victoria": Dossier(
        "占据泰拉富饶中央谷地的君主制帝国，领土广袤、资源丰富，并拥有多个开拓区与飞地；国家由多层贵族领地构成。",
        "A monarchic empire occupying Terra's fertile Central Valley, with vast territory, abundant resources, frontiers, and exclaves organized through layered noble domains.",
        "伦蒂尼姆、红脊镇、切特雷镇、布伦特伍德镇、吉布森镇、格瑞威治、丽茵卡登、卡拉顿、博森德尔；伦蒂尼姆辖区：奥特克里格区、萨迪恩区、海布里区、诺伯特区、玛格纳区、卡登区",
        "多伦郡、小丘郡、阿斯卡拉郡、林顿郡、半岛郡、塞克郡、开夏郡、橡林郡、特伦特郡；石高原野、克拉斯德内海、赤鬃山脉、银石崖、暮辉河",
    ),
    "ursus": Dossier(
        "雄踞泰拉大陆北部的君主制帝国，幅员辽阔、自然环境严酷，拥有雄厚军事资本，并长期以军事扩张立国。",
        "A vast northern monarchic empire with a harsh natural environment and formidable military resources, historically shaped by military expansion.",
        "圣骏堡、泽尔格勒（卫星城）、切尔诺伯格、图利斯卡亚、新彼得罗夫斯克、塔曼格勒德、维亚特诺、扎莫列斯、彼得达诺尔、布列洁诺伊、维罗比斯科镇；圣骏堡辖区：萨列夫格勒区、卡托加区、奥多耶夫区、涅瓦湖；布列洁诺伊辖区：布列斯克、中心矿区、卫星城矿区、奥洛涅茨矿区、沃尔格勒矿区、乌拉尔矿区、克拉斯矿区、奥涅加矿区",
        "格里高利省",
    ),
    "kjerag": Dossier(
        "位于泰拉中北部、终年落雪的雪山国家。世俗事务由三大家族组成的三族议会掌控，蔓珠院主持宗教传统，同时国内正经历对外开放与现代化。",
        "A perpetually snowy mountain nation in north-central Terra. Secular affairs are governed by the Tri-Clan Council, a religious institution maintains sacred tradition, and the country is undergoing opening and modernization.",
        "图里卡姆",
        "少女峰、马特洪峰、喀兰峰、银心湖",
    ),
    "siracusa": Dossier(
        "由移动城邦和家族势力构成的国家。十二家族组成灰厅并遵循铳与秩序，中央行政机关为城邦联合议事会。",
        "A nation of mobile city-states and powerful families. The Twelve Families form the Hall of Gray under the order of guns and law, while the Union Council of City-States serves as the central administration.",
        "蒙特卢佩、七丘城、怀特城、拉克玛蒂瓦城、帕勒莫、沃尔西尼、新沃尔西尼",
        "现有 PRTS 地理总目录未列出更多可确认地名",
    ),
    "yan": Dossier(
        "位于泰拉大陆最东边、疆土广阔的国家，拥有悠久的东方文明传统，并在北方边境长期承担抵御威胁的职责。",
        "A vast nation at Terra's eastern edge with a long-established eastern civilization, whose northern frontier has long borne responsibility for resisting external threats.",
        "百灶、龙门、姜齐城、夕城、黄城、春都、花郡、勾吴城、丹燕城、尚蜀、玉门、大荒城、邙山镇、婆山镇",
        "尚蜀辖区与山地：新峦区、流云区、数舟峰、取江峰（攥江峰）、忘水坪、居奇山、泥泥峰、昏谭峰、别离峰、梓云峰、青銮峰、寻日峰；邙山",
    ),
    "iberia": Dossier(
        "位于泰拉大陆南端的沿海国家，曾借助阿戈尔技术成为强盛海洋国家；大静谧后严重衰落，如今受审判庭统治并趋于保守封闭。",
        "A southern coastal nation that once became a great maritime power with Aegirian technology. It declined severely after the Profound Silence and is now governed by the Inquisition under an increasingly conservative isolation.",
        "佩尔多尼朵拉（佩尔多尼）、盐风城、潮石镇、格兰法洛、颂圣棱堡、港都、雅隆镇",
        "盐漠",
    ),
}


def chapter_id(index: int) -> str:
    return f"7A000000000000{index:02X}"


def quest_id(nation_index: int, structure_index: int) -> str:
    return f"7B{nation_index:02X}0000000000{structure_index:02X}"


def task_id(nation_index: int, structure_index: int) -> str:
    return f"7C{nation_index:02X}0000000000{structure_index:02X}"


def archive_quest_id(nation_index: int, archive_index: int) -> str:
    return f"7D{nation_index:02X}0000000000{archive_index:02X}"


def archive_task_id(nation_index: int, archive_index: int) -> str:
    return f"7E{nation_index:02X}0000000000{archive_index:02X}"


def english_place_catalog(value: str) -> str:
    if value == "现有 PRTS 地理总目录未列出更多可确认地名":
        return "The current PRTS geography index lists no further confirmed place names"
    replacements = {
        "城市辖区：": "Urban districts: ",
        "崔林特尔梅辖区：": "崔林特尔梅 districts: ",
        "维谢海姆辖区：": "维谢海姆 districts: ",
        "圣骏堡辖区：": "圣骏堡 districts: ",
        "布列洁诺伊辖区：": "布列洁诺伊 districts: ",
        "尚蜀辖区与山地：": "尚蜀 districts and mountains: ",
        "其他已知地名：": "Other known place names: ",
        "；": "; ",
        "、": ", ",
    }
    for source, target in replacements.items():
        value = value.replace(source, target)
    return value


def write_chapter(nation: Nation, nation_index: int) -> None:
    count = len(nation.structures)
    start_x = -1.5 * (count - 1)
    archive_icons = ("minecraft:writable_book", "minecraft:filled_map", "minecraft:compass", "minecraft:bricks")
    quests = [
        "\n".join(
            (
                "\t\t{",
                f'\t\t\tid: "{archive_quest_id(nation_index, archive_index)}"',
                f'\t\t\ticon: {{ id: "{icon}" }}',
                "\t\t\toptional: true",
                f'\t\t\ttasks: [{{ id: "{archive_task_id(nation_index, archive_index)}", type: "checkmark" }}]',
                f"\t\t\tx: {-4.5 + 3.0 * (archive_index - 1):.1f}d",
                "\t\t\ty: 0.0d",
                "\t\t}",
            )
        )
        for archive_index, icon in enumerate(archive_icons, 1)
    ]
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
                    "\t\t\ty: 4.0d",
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
        dossier = DOSSIERS[nation.path]
        chapter_title = f"{nation_name}：国家档案" if locale == "zh_cn" else f"{nation_name}: National Dossier"
        lines.append(f'\tchapter.{chapter_id(nation_index)}.title: "{chapter_title}"')
        building_names = "、".join(structure.zh for structure in nation.structures)
        building_names_en = ", ".join(structure.en for structure in nation.structures)
        if locale == "zh_cn":
            archive_text = (
                ("国家设定", "PRTS 国家资料摘要", dossier.setting_zh + "\\n\\n资料口径：PRTS 泰拉大典国家条目；本节点不补写未公开设定。", "阅读国家设定"),
                ("城市与聚落", "PRTS 已确认地名目录", "城市、聚落与明确城市辖区：" + dossier.cities + "。\\n\\n地图坐标与边界属于 Zinecraft 游戏化布局，不代表官方精确位置。", "阅读城市目录"),
                ("重要地区", "行政区与自然地貌", "重要地区：" + dossier.regions + "。\\n\\n国家、城市和群系是不同数据层；地区边界不会被群系边界替代。", "阅读地区目录"),
                ("建筑与探索", "当前可访问的国家结构", "本章可探索建筑：" + building_names + "。\\n\\n这些注册结构是依据资料制作的 Minecraft 表达；每座城市另有独立注册的城市中心、住宅、商店、工坊和公共建筑 Blockout，未知外观不视为官方复原。", "阅读建筑目录"),
            )
        else:
            archive_text = (
                ("National Setting", "Summary of PRTS nation records", dossier.setting_en + "\\n\\nSource boundary: PRTS Terra geography records; this entry does not invent undisclosed lore.", "Read the national setting"),
                ("Cities and Settlements", "Place names confirmed by PRTS", "Cities, settlements, and explicit urban districts: " + english_place_catalog(dossier.cities) + ".\\n\\nPRTS Chinese place names are retained where no verified English form is recorded. Map coordinates and boundaries are Zinecraft gameplay layouts, not claimed official positions.", "Read the city directory"),
                ("Important Regions", "Administrative and natural regions", "Important regions: " + english_place_catalog(dossier.regions) + ".\\n\\nPRTS Chinese place names are retained where no verified English form is recorded. Nations, cities, and biomes are separate data layers; biome borders do not replace regional borders.", "Read the region directory"),
                ("Buildings and Exploration", "Currently visitable national structures", "Structures available in this chapter: " + building_names_en + ".\\n\\nThese registered structures are evidence-based Minecraft interpretations. Each city also owns separately registered blockout structures for its center, residences, shops, workshops, and public buildings; unknown appearances are not presented as official reconstructions.", "Read the building directory"),
            )
        for archive_index, (title, subtitle, description, task_title) in enumerate(archive_text, 1):
            qid = archive_quest_id(nation_index, archive_index)
            tid = archive_task_id(nation_index, archive_index)
            lines.extend(
                (
                    f'\tquest.{qid}.title: "{title}"',
                    f'\tquest.{qid}.quest_subtitle: "{subtitle}"',
                    f'\tquest.{qid}.quest_desc: ["{description}"]',
                    f'\ttask.{tid}.title: "{task_title}"',
                )
            )
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
    generated_key = re.compile(r"^\s*(?:chapter|quest|task)\.7[ABCDE][0-9A-F]{14}\.")
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
    if set(DOSSIERS) != {nation.path for nation in NATIONS}:
        raise ValueError("国家档案必须与十九国章节一一对应")
    CHAPTER_ROOT.mkdir(parents=True, exist_ok=True)
    for index, nation in enumerate(NATIONS, 1):
        write_chapter(nation, index)
    update_language("zh_cn")
    update_language("en_us")
    print(
        f"Generated {len(NATIONS)} nation chapters with "
        f"{len(NATIONS) * 4} dossier tasks and {sum(len(n.structures) for n in NATIONS)} structure tasks."
    )


if __name__ == "__main__":
    main()

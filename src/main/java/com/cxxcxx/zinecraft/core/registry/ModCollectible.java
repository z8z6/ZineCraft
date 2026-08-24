package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.collection.CollectiblePower;
import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.registry.builder.CollectibleBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.world.item.Rarity;

import java.util.List;
import java.util.function.BiFunction;

/**
 * 直接以 Java Builder 声明并注册《傀影与猩红孤钻》的全部 245 件藏品。
 */
public final class ModCollectible {
  private static final int EXPECTED_COUNT = 245;

  public static final CollectibleBuilder HOT_WATER_KETTLE = collectible(
      "hot_water_kettle",
      "001",
      "热水壶",
      "立即获得目标生命+2，希望+1",
      "Immediately gain +2 Life Points and +1 Hope",
      "罗德岛办公室里的同款热水壶，有人经常大半夜用热水壶煮速食面吃，这种生活习惯不是很健康......",
      "Some people often use the hot water kettle in the Rhodes Island Office to cook instant noodles in the middle of the night. That's not a very healthy lifestyle...",
      explorationRule("立即获得目标生命+2，希望+1", power -> power.addMaxHealth(2).hope(1)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder SPECIAL_SUPPRESSOR = collectible(
      "special_suppressor",
      "002",
      "特殊抑制器",
      "立即获得目标生命+6",
      "Immediately gain +6 Life Points.",
      "罗德岛改良款抑制器，能够有效遏制感染造成的源石技艺失控。",
      "An enhanced suppressor developed by Rhodes Island that can better keep the infected from losing control of their Originium Arts.",
      explorationRule("立即获得目标生命+6", power -> power.addMaxHealth(6)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder STONE_GARGOYLE = collectible(
      "stone_gargoyle",
      "003",
      "石像鬼塑像",
      "立即获得目标生命+6",
      "Immediately gain +6 Life Points.",
      "一个手掌大小的雕像，听说是石像鬼们表示友好的造物。",
      "A palm-sized sculpture. Supposedly they are handmade gifts given out by the Gargoyles as a sign of friendship.",
      explorationRule("立即获得目标生命+6", power -> power.addMaxHealth(6)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder VAMPIRES_BED = collectible(
      "vampires_bed",
      "004",
      "血魔的寝床",
      "立即获得目标生命+8",
      "Immediately gain +8 Life Points.",
      "一副奢华的棺木，和古堡十分般配，华法琳说这就是刻板印象。",
      "A lavish-looking coffin that matches the castle's aesthetics well. However, Warfarin says this is merely a stereotype.",
      explorationRule("立即获得目标生命+8", power -> power.addMaxHealth(8)),
      Rarity.RARE
  );
  public static final CollectibleBuilder PROOF_OF_LONGEVITY = collectible(
      "proof_of_longevity",
      "005",
      "长生者之证",
      "立即获得目标生命+10",
      "Immediately gain +10 Life Points.",
      "一段树枝，枯荣一体；\n从枯萎处生长，从繁盛处枯萎。",
      "A tree branch that has withered and flourished. \nWhen it is withered, it grows anew. When it is thriving in its fullest, it fades away yet again.",
      explorationRule("立即获得目标生命+10", power -> power.addMaxHealth(10)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder PRIED_OPEN_TOOLBOX = collectible(
      "pried_open_toolbox",
      "006",
      "被撬开的道具箱",
      "希望+4",
      "Hope +4",
      "剧团道具师的道具箱，从顶针到道具假人，一应物品应有尽有。",
      "The tool box of the troupe's prop master. From nails to dummy props, it has everything.",
      explorationRule("希望+4", power -> power.hope(4)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder ANTIQUATED_SHEET_MUSIC = collectible(
      "antiquated_sheet_music",
      "007",
      "古旧乐谱残章",
      "希望+4",
      "Hope +4",
      "一张古旧的乐谱残章，上面的音符已经模糊不清，不知这首曲子讲述着怎样的故事。",
      "A tattered piece of sheet music. The notes can no longer be clearly read. No one knows for certain what story this piece was meant to tell.",
      explorationRule("希望+4", power -> power.hope(4)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder LETTER_OF_TERMINATION_CONTRACT = collectible(
      "letter_of_termination_contract",
      "008",
      "解约协议",
      "希望+4",
      "Hope +4",
      "只要拿到一个签名，就可以彻底摆脱这个剧团了。",
      "You can break free of this troupe with just one autograph.",
      explorationRule("希望+4", power -> power.hope(4)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder UNIVERSAL_KEY = collectible(
      "universal_key",
      "009",
      "万能钥匙",
      "希望+6",
      "Hope +6",
      "银质的老旧钥匙，可以用它打开任何一扇门。作为管理者，剧团管家自然要手握开启所有秘密的银钥。",
      "An old, silver key. It can open any door. As the keeper, the troupe's butler naturally needs a key that unlocks all the secrets here.",
      explorationRule("希望+6", power -> power.hope(6)),
      Rarity.RARE
  );
  public static final CollectibleBuilder BANSHEES_KISS = collectible(
      "banshees_kiss",
      "010",
      "女妖之吻",
      "希望+8",
      "Hope +8",
      "外族人获得它时常常会展现出超越自身的勇气，但作为稀少的男性女妖，Logos对于这种东西已经见怪不怪了。",
      "Outsiders make extraordinary displays of bravery that far surpass their normal abilities. However, as one of the few male Banshees out there, this has become a far too common sight to Logos.",
      explorationRule("希望+8", power -> power.hope(8)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder ANTIQUE_COINS = collectible(
      "antique_coins",
      "011",
      "古旧钱币",
      "立即获得源石锭+10",
      "Immediately adds +10 Originium Ingots.",
      "生锈的古代钱币，已经无法辨认年代，这东西在泰拉荒地上随处可见。",
      "An ancient, rusty coin, weathered to the point where one can no longer identify its age. Things like these are all too common throughout Terra's barrens.",
      explorationRule("立即获得源石锭+10", power -> power.originiumIngots(10)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder ACTORS_JEWELRY_BOX = collectible(
      "actors_jewelry_box",
      "012",
      "演员的首饰盒",
      "立即获得源石锭+18",
      "Immediately adds +18 Originium Ingots.",
      "装满了各种碎裂首饰的盒子，年轻的主演佩戴着这些珠宝登台，然后从最高处纵身跃下。",
      "A box containing all kinds of shattered jewelries. The young lead actress wore these jewelries and stepped onto the stage, in the end falling from its highest point.",
      explorationRule("立即获得源石锭+18", power -> power.originiumIngots(18)),
      Rarity.RARE
  );
  public static final CollectibleBuilder FLAWLESS_JADESTONE = collectible(
      "flawless_jadestone",
      "013",
      "无瑕宝玉",
      "立即获得源石锭+25",
      "Immediately adds +25 Originium Ingots.",
      "即便城池三度因其而毁，它也依然光洁无瑕，无可挑剔。持有它的萨尔贡皇帝直至今日都没想好用什么珠宝与之相衬，只有争斗的硝烟永伴。",
      "Even though it thrice brought the city to ruins, it yet shines radiantly in its flawless form. Even today, the Sargon emperor in possession of the gem has yet to find another piece of jewelry matching its brilliance, leaving it with naught but gunsmoke for a companion.",
      explorationRule("立即获得源石锭+25", power -> power.originiumIngots(25)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder LAUGHING_JOKER = collectible(
      "laughing_joker",
      "014",
      "尖笑鬼牌",
      "立即进阶一个干员（不消耗希望）",
      "Immediately Promote an Operator (Does not require Hope).",
      "这是给予你的特权。",
      "You are granted this privilege.",
      sourceRule("立即进阶一个干员（不消耗希望）"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder MISS_CHRISTINE_PETTING_TICKET = collectible(
      "miss_christine_petting_ticket",
      "015",
      "Miss.Christine摸摸券",
      "立即进阶两个干员（不消耗希望）",
      "Immediately Promote 2 Operators (Does not require Hope).",
      "你已经获得了女士的准许！",
      "You have the lady's permission!",
      sourceRule("立即进阶两个干员（不消耗希望）"),
      Rarity.RARE
  );
  public static final CollectibleBuilder ANTIQUE_CASTING = collectible(
      "antique_casting",
      "016",
      "古旧铸物",
      "立即进阶三个干员（不消耗希望）",
      "Immediately Promote 3 Operators (Does not require Hope).",
      "“天有洪炉，地生五金”......虽然可以用来成就各种事业，却没人知道具体原理。",
      "'Harken the forge of the sky Awaits five earthborn metals' ...Even though it can be used for all kinds of work, nobody understands its exact mechanism.",
      sourceRule("立即进阶三个干员（不消耗希望）"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BLUNT_CLAWS_TRAINING = collectible(
      "blunt_claws_training",
      "017",
      "钝爪-典训",
      "立即进阶一个【先锋】干员（不消耗希望）",
      "Immediately Promote a Vanguard Operator (Does not require Hope).",
      "一套强有力的装备，一颗勇往直前的心。",
      "A formidable set of equipment to provide strength, an indomitable heart to press forward bravely.",
      sourceRule("立即进阶一个【先锋】干员（不消耗希望）"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BEND_SPEARS_TRAINING = collectible(
      "bend_spears_training",
      "018",
      "折戟-典训",
      "立即进阶一个【近卫】干员（不消耗希望）",
      "Immediately Promote a Guard Operator (Does not require Hope).",
      "有的人用剑是因为他们不会用弩也不会用法术。而有的人用剑，是因为只有用剑的必要。",
      "Some people use swords because they don't know how to use crossbows or Arts. Others use swords because that's all they need.",
      sourceRule("立即进阶一个【近卫】干员（不消耗希望）"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder IRON_GUARD_TRAINING = collectible(
      "iron_guard_training",
      "019",
      "铁卫-典训",
      "立即进阶一个【重装】干员（不消耗希望）",
      "Immediately Promote a Defender Operator (Does not require Hope).",
      "划痕是荣耀，但那些碎如齑粉的战士，谁来铭记？",
      "Scars symbolize glory, but who will remember the soldiers who were cut a bit too deeply?",
      sourceRule("立即进阶一个【重装】干员（不消耗希望）"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder FATAL_BOLTS_TRAINING = collectible(
      "fatal_bolts_training",
      "020",
      "残弩-典训",
      "立即进阶一个【狙击】干员（不消耗希望）",
      "Immediately Promote a Sniper Operator (Does not require Hope).",
      "“要么，你们控制点火力。要么，你们搞对目标，好吗？”",
      "'Either you learn to control your ignition, or you learn to discern your target. Got it?'",
      sourceRule("立即进阶一个【狙击】干员（不消耗希望）"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BROKEN_WAND_TRAINING = collectible(
      "broken_wand_training",
      "021",
      "断杖-典训",
      "立即进阶一个【术师】干员（不消耗希望）",
      "Immediately Promote a Caster Operator (Does not require Hope).",
      "“看过某些术师干员的‘表演’后，我选择默默收起那本莱塔尼亚教材。”",
      "'After watching the 'performance' of certain casters, I decided to quietly put away the Leithanian textbook.'",
      sourceRule("立即进阶一个【术师】干员（不消耗希望）"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder STALWART_AID_TRAINING = collectible(
      "stalwart_aid_training",
      "022",
      "支柱-典训",
      "立即进阶一个【辅助】干员（不消耗希望）",
      "Immediately Promote a Supporter Operator (Does not require Hope).",
      "他们行动的时候总是五花八门的。",
      "Their tactics are always diverse and colorful.",
      sourceRule("立即进阶一个【辅助】干员（不消耗希望）"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder HEALERS_PATH_TRAINING = collectible(
      "healers_path_training",
      "023",
      "医者-典训",
      "立即进阶一个【医疗】干员（不消耗希望）",
      "Immediately Promote a Medic Operator (Does not require Hope).",
      "直到所有人都是医生，都能根治名为生活的病灶，直到那一刻我们才不需要医生。",
      "When the day comes when everyone is a doctor and can even cure the disease known as life, we will no longer need doctors.",
      sourceRule("立即进阶一个【医疗】干员（不消耗希望）"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder RUSTED_BLADE_TRAINING = collectible(
      "rusted_blade_training",
      "024",
      "锈刃-典训",
      "立即进阶一个【特种】干员（不消耗希望）",
      "Immediately Promote a Specialist Operator (Does not require Hope).",
      "在与特种干员们进行实战练习的时候，请做好充足的“没做好准备就被打了”的准备。",
      "When conducting live combat drills with Specialist Operators, please be prepared to be 'beaten up thoroughly before you are prepared.'",
      sourceRule("立即进阶一个【特种】干员（不消耗希望）"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder REGIONAL_ACTION_PLAN = collectible(
      "regional_action_plan",
      "025",
      "地区行动方案",
      "招募4星干员的希望消耗-2",
      "4-star Operators cost -2 Hope to recruit.",
      "罗德岛在各个重要的枢纽区域都设有专门的办事处。信使们努力在这片大地上勉强织了一张易碎的网。",
      "Rhodes Island has established dedicated offices in every important hub area. The messengers are doing what they can to weave a fragile web across this land.",
      sourceRule("招募4星干员的希望消耗-2"),
      Rarity.RARE
  );
  public static final CollectibleBuilder COMPREHENSIVE_OPERATION_FILE = collectible(
      "comprehensive_operation_file",
      "026",
      "全局作战文件",
      "招募5星干员的希望消耗-2",
      "5-star Operators cost -2 Hope to recruit.",
      "陆上行舟，如履薄冰。记得要培养自己的大局观。",
      "A vessel sailing upon the land, like treading upon thin ice. Remember to always expand your worldview.",
      sourceRule("招募5星干员的希望消耗-2"),
      Rarity.RARE
  );
  public static final CollectibleBuilder SECRET_HR_LETTER = collectible(
      "secret_hr_letter",
      "027",
      "人事部密信",
      "招募6星干员的希望消耗-2",
      "6-star Operators cost -2 Hope to recruit",
      "“■■■给你的？哦那没事了。”",
      "'■■■ gave this to you? Guess there's no problem then.'",
      sourceRule("招募6星干员的希望消耗-2"),
      Rarity.RARE
  );
  public static final CollectibleBuilder DRAFT_OF_A_SPEECH = collectible(
      "draft_of_a_speech",
      "028",
      "一份演讲稿",
      "招募所有干员的希望消耗-2",
      "All Operators cost -2 Hope to recruit",
      "她有点紧张，有点担心，却一直都很坚定。话筒打开时，她两只长长的耳朵稍稍晃动了一下。",
      "She was a little nervous, a little worried, but still remained resolute. When the microphone was turned on, her long ears quivered slightly.",
      sourceRule("招募所有干员的希望消耗-2"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder CRIMSON_TROUPE_TICKET_STUB = collectible(
      "crimson_troupe_ticket_stub",
      "029",
      "猩红剧团票根",
      "战斗后有25%的几率额外掉落一张招募券",
      "25% chance to drop an additional Recruitment Voucher after battle",
      "猩红剧团的门票，没有写演出和表演者的名字。究竟是怎样的演出在等待着你？",
      "A Crimson Troupe ticket stub. None of the cast are listed. What kind of performance do they have in store for you?",
      sourceRule("战斗后有25%的几率额外掉落一张招募券"),
      Rarity.RARE
  );
  public static final CollectibleBuilder LUCKY_COIN = collectible(
      "lucky_coin",
      "030",
      "幸运硬币",
      "每进入一个非战斗节点，获得源石锭+2",
      "Gain +2 Originium Ingots upon entering a noncombat node.",
      "在罗德岛捡到的硬币，捡到一枚就能捡到很多枚。",
      "A coin picked up from Rhodes Island. If you find one, you're sure to be able to find many more.",
      explorationRule("每进入一个非战斗节点，获得源石锭+2", power -> power.originiumIngotsPerNonCombatNode(2)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder MASQUERADE_MASK = collectible(
      "masquerade_mask",
      "031",
      "假面舞会面具",
      "每进入一个非战斗节点，获得源石锭+3",
      "Gain +3 Originium Ingots upon entering a noncombat node.",
      "尊敬的客人，欢迎来到猩红剧团，接下来的演出一定会令您印象深刻。",
      "Esteemed guests, welcome to the Crimson Troupe. The performance we have in store for you tonight will be one to remember.",
      explorationRule("每进入一个非战斗节点，获得源石锭+3", power -> power.originiumIngotsPerNonCombatNode(3)),
      Rarity.RARE
  );
  public static final CollectibleBuilder SUPREME_RING = collectible(
      "supreme_ring",
      "032",
      "至宝指环",
      "战斗掉落的源石锭+50%",
      "+50% Originium Ingot drops from battle.",
      "在莱塔尼亚的神话中，持戒者享有整片大地的财富，但也终将因贪婪毁灭。",
      "In Leithanian mythology, he who holds the ring enjoys the vast riches of the entire world, but greed will also be his undoing.",
      explorationRule("战斗掉落的源石锭+50%", power -> power.battleOriginiumIngotMultiplier(1.5)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder VICTORIAN_SCRAP_MEDAL = collectible(
      "victorian_scrap_medal",
      "033",
      "维多利亚“废铁”勋章",
      "战斗获得的指挥经验+20%",
      "Gain +20% Command EXP from battles",
      "推进之王临时设计并颁发给达格达的骑士勋章。材料很烂，但对达格达来说很有意义。",
      "A knight's medal that Siege threw together and awarded to Dagda on the spot. The materials used are pitiful, but it holds a lot of meaning to Dagda.",
      explorationRule("战斗获得的指挥经验+20%", power -> power.commandExperienceMultiplier(1.2)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder LEITHANIAN_MEDAL_OF_HONOR = collectible(
      "leithanian_medal_of_honor",
      "034",
      "莱塔尼亚荣誉勋章",
      "战斗获得的指挥经验+30%",
      "Gain +30% Command EXP from battles",
      "两位女皇很少同做一件事，为创造艺术的人授勋，这是其中一件。",
      "The twin empresses rarely do anything together, but conferring awards on those who create art is one such thing.",
      explorationRule("战斗获得的指挥经验+30%", power -> power.commandExperienceMultiplier(1.3)),
      Rarity.RARE
  );
  public static final CollectibleBuilder RUSTED_IRON_HAMMER = collectible(
      "rusted_iron_hammer",
      "035",
      "锈蚀的铁锤",
      "商店中购买道具所需源石锭-50%",
      "Shop vendors charge -50% Originium Ingots for their goods.",
      "“变革是必须的，而我们应当让暴风雨来得更快一点。”",
      "'Change is necessary, and we must do all we can to hasten the oncoming storm.'",
      sourceRule("商店中购买道具所需源石锭-50%"),
      Rarity.RARE
  );
  public static final CollectibleBuilder FOUR_LEAF_CLOVER_FOSSIL = collectible(
      "four_leaf_clover_fossil",
      "036",
      "四叶草化石",
      "战斗后掉落收藏品时，增加一个可选项",
      "Gain an additional choice when collectibles are dropped after battle",
      "麦哲伦在生命绝迹之所找到的幸运象征。这块石头无疑经历过大地的剧变，但它还是幸存下来，将残留的小小希望留给了发现它的人。 \n",
      "A symbol of luck that Magallan found in a place all but devoid of life. The rock has no doubt experienced many violent changes of the lands, but it survived, giving those who come across it a small bit of lingering hope.\n",
      sourceRule("战斗后掉落收藏品时，增加一个可选项"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder ASSAULT_CO_OP_EXPANSION = collectible(
      "assault_co_op_expansion",
      "037",
      "突击协议扩充",
      "商店和战斗掉落的先锋招募券都被升级成高级人事调度函",
      "Vanguard Rec. Vouchers obtained from Shops or as a drop will be upgraded into Elite HR Dispatch Letters.",
      "单一的干员组成难以在前线战斗中取得优势，通过协议补充战力也是有效的手段。",
      "It is difficult to gain the upper hand in frontline combat with only a single squad of Operators. Supplementing combat capabilities through Co-ops is an effective solution.",
      sourceRule("商店和战斗掉落的先锋招募券都被升级成高级人事调度函"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder ASSAULT_CO_OP_REINFORCEMENTS = collectible(
      "assault_co_op_reinforcements",
      "038",
      "突击协议增援",
      "商店和战斗掉落的近卫招募券都被升级成高级人事调度函",
      "Guard Rec. Vouchers obtained from Shops or as a drop will be upgraded into Elite HR Dispatch Letters.",
      "单一的干员组成难以在前线战斗中取得优势，通过协议补充战力也是有效的手段。",
      "It is difficult to gain the upper hand in frontline combat with only a single squad of Operators. Supplementing combat capabilities through Co-ops is an effective solution.",
      sourceRule("商店和战斗掉落的近卫招募券都被升级成高级人事调度函"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder FORTIFICATION_CO_OP_EXPANSION = collectible(
      "fortification_co_op_expansion",
      "039",
      "堡垒协议扩充",
      "商店和战斗掉落的重装招募券都被升级成高级人事调度函",
      "Defender Rec. Vouchers obtained from Shops or as a drop will be upgraded into Elite HR Dispatch Letters.",
      "单一的干员组成难以在前线战斗中取得优势，通过协议补充战力也是有效的手段。",
      "It is difficult to gain the upper hand in frontline combat with only a single squad of Operators. Supplementing combat capabilities through Co-ops is an effective solution.",
      sourceRule("商店和战斗掉落的重装招募券都被升级成高级人事调度函"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder FORTIFICATION_CO_OP_REINFORCEMENTS = collectible(
      "fortification_co_op_reinforcements",
      "040",
      "堡垒协议增援",
      "商店和战斗掉落的辅助招募券都被升级成高级人事调度函",
      "Supporter Rec. Vouchers obtained from Shops or as a drop will be upgraded into Elite HR Dispatch Letters.",
      "单一的干员组成难以在前线战斗中取得优势，通过协议补充战力也是有效的手段。",
      "It is difficult to gain the upper hand in frontline combat with only a single squad of Operators. Supplementing combat capabilities through Co-ops is an effective solution.",
      sourceRule("商店和战斗掉落的辅助招募券都被升级成高级人事调度函"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder RANGED_CO_OP_EXPANSION = collectible(
      "ranged_co_op_expansion",
      "041",
      "远程协议扩充",
      "商店和战斗掉落的医疗招募券都被升级成高级人事调度函",
      "Medic Rec. Vouchers obtained from Shops or as a drop will be upgraded into Elite HR Dispatch Letters.",
      "单一的干员组成难以在前线战斗中取得优势，通过协议补充战力也是有效的手段。",
      "It is difficult to gain the upper hand in frontline combat with only a single squad of Operators. Supplementing combat capabilities through Co-ops is an effective solution.",
      sourceRule("商店和战斗掉落的医疗招募券都被升级成高级人事调度函"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder RANGED_CO_OP_REINFORCEMENTS = collectible(
      "ranged_co_op_reinforcements",
      "042",
      "远程协议增援",
      "商店和战斗掉落的狙击招募券都被升级成高级人事调度函",
      "Sniper Rec. Vouchers obtained from Shops or as a drop will be upgraded into Elite HR Dispatch Letters.",
      "单一的干员组成难以在前线战斗中取得优势，通过协议补充战力也是有效的手段。",
      "It is difficult to gain the upper hand in frontline combat with only a single squad of Operators. Supplementing combat capabilities through Co-ops is an effective solution.",
      sourceRule("商店和战斗掉落的狙击招募券都被升级成高级人事调度函"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder SABOTAGE_CO_OP_EXPANSION = collectible(
      "sabotage_co_op_expansion",
      "043",
      "破坏协议扩充",
      "商店和战斗掉落的术师招募券都被升级成高级人事调度函",
      "Caster Rec. Vouchers obtained from Shops or as a drop will be upgraded into Elite HR Dispatch Letters.",
      "单一的干员组成难以在前线战斗中取得优势，通过协议补充战力也是有效的手段。",
      "It is difficult to gain the upper hand in frontline combat with only a single squad of Operators. Supplementing combat capabilities through Co-ops is an effective solution.",
      sourceRule("商店和战斗掉落的术师招募券都被升级成高级人事调度函"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder SABOTAGE_CO_OP_REINFORCEMENTS = collectible(
      "sabotage_co_op_reinforcements",
      "044",
      "破坏协议增援",
      "商店和战斗掉落的特种招募券都被升级成高级人事调度函",
      "Specialist Rec. Vouchers obtained from Shops or as a drop will be upgraded into Elite HR Dispatch Letters.",
      "单一的干员组成难以在前线战斗中取得优势，通过协议补充战力也是有效的手段。",
      "It is difficult to gain the upper hand in frontline combat with only a single squad of Operators. Supplementing combat capabilities through Co-ops is an effective solution.",
      sourceRule("商店和战斗掉落的特种招募券都被升级成高级人事调度函"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder SILENT_SQUAD = collectible(
      "silent_squad",
      "045",
      "“静音小队”",
      "所有我方单位的攻击力和防御力+35%，生命+45%",
      "All friendly units have +35% ATK, +35% DEF, and +45% Max HP",
      "由罗德岛工程部统一操控的探索用无人机，更高效，更安全，为外勤干员们提供全方位的支援与保障。",
      "A set of exploration drones remotely controlled by the Rhodes Island Engineering Department. More efficient and safer than ever, they provide operators on field missions the omnidirectional support and defense they need.",
      runtime(statSet(percent(CombatStat::multiplyAttack, 0.35), percent(CombatStat::multiplyDefense, 0.35), percent(CombatStat::multiplyMaxHealth, 0.45))),
      Rarity.EPIC
  );
  public static final CollectibleBuilder FISSURED_RESTRAINTS = collectible(
      "fissured_restraints",
      "046",
      "开裂的束缚带",
      "所有敌方单位的攻击力-7%",
      "All enemy units have -7% ATK.",
      "这根坚固的带子似乎绑过什么恐怖的东西......救命......",
      "It seems this sturdy band was once used to restrain something terrifying... Help...",
      statFlat("伤害减免+7%", "+7% damage reduction", CombatStat::addDamageReduction, 0.07),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder ABYSSAL_WYRDMASK = collectible(
      "abyssal_wyrdmask",
      "047",
      "奇渊面具",
      "所有敌方单位的攻击力-12%",
      "All enemy units have -12% ATK.",
      "不属于所知的任何一个文化的古怪面具，会让人丧失战意......并不自觉地陷入长久的沉思。",
      "An eldritch mask not belonging to any culture you're familiar with that is capable of making people lose their will to fight... and unconsciously fall into a state of deep contemplation.",
      statFlat("伤害减免+12%", "+12% damage reduction", CombatStat::addDamageReduction, 0.12),
      Rarity.RARE
  );
  public static final CollectibleBuilder GODMOTHERS_TOKEN = collectible(
      "godmothers_token",
      "048",
      "教母的信物",
      "所有敌方单位的攻击力-17%",
      "All enemy units have -17% ATK.",
      "骨质的桂冠，西西里夫人的信物。秩序的象征将铲平起伏的欲望，斗争不被允许。无论真假，跪下。",
      "A laurel of bones, the token of a Sicilian noblewoman. This symbol of order will smooth the turbulance of all desires, for conflict is not allowed. Genuine or counterfeit, it still demands you to kneel.",
      statFlat("伤害减免+17%", "+17% damage reduction", CombatStat::addDamageReduction, 0.17),
      Rarity.EPIC
  );
  public static final CollectibleBuilder WORN_OUT_GROUP_PHOTO = collectible(
      "worn_out_group_photo",
      "049",
      "残破合影",
      "所有敌方单位的防御力-12%",
      "All enemy units have -12% DEF.",
      "里面有你认识的人吗？",
      "Do you know someone in the photo?",
      statFlat("无视防御+12%", "+12% DEF ignore", CombatStat::addDefenseIgnore, 0.12),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder WRITERS_TONGUE = collectible(
      "writers_tongue",
      "050",
      "作者的喉舌",
      "所有敌方单位的防御力-21%",
      "All enemy units have -21% DEF.",
      "剧作家用笔蘸着自己的血创作。他为自己设计了丧身火海的最后一幕。",
      "The playwright penned his creations with a quill and his own blood, and for himself he devised an ending in which he perishes in a sea of flames.",
      statFlat("无视防御+21%", "+21% DEF ignore", CombatStat::addDefenseIgnore, 0.21),
      Rarity.RARE
  );
  public static final CollectibleBuilder ROSMONTISS_EMBRACE = collectible(
      "rosmontiss_embrace",
      "051",
      "迷迭香之拥",
      "所有敌方单位的防御力-30%",
      "All enemy units have -30% DEF.",
      "“谁来审判？”",
      "'Who will be the judge?'",
      statFlat("无视防御+30%", "+30% DEF ignore", CombatStat::addDefenseIgnore, 0.30),
      Rarity.EPIC
  );
  public static final CollectibleBuilder THE_WHISPERER_IN_DARKNIGHT = collectible(
      "the_whisperer_in_darknight",
      "052",
      "“黑夜呢喃”",
      "所有敌方单位的生命-10%",
      "All enemy units have -10% HP.",
      "一张黑色唱片。不。准。碰。它。",
      "A black vinyl. DO. NOT. TOUCH. IT.",
      enemySpawnStat("敌方生成时最大生命-10%", "-10% enemy maximum HP on spawn", (enemy, stats) -> stats.multiplyMaxHealth(-0.10)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder GOLD_PLATED_DIE = collectible(
      "gold_plated_die",
      "053",
      "镶金骨骰",
      "所有敌方单位的生命-15%",
      "All enemy units have -15% HP.",
      "镶金的骨骰只有一面刻着代表生存的印记，至于其他十九面......",
      "The gold-plated die has one side representing life. As for the other nineteen sides...",
      enemySpawnStat("敌方生成时最大生命-15%", "-15% enemy maximum HP on spawn", (enemy, stats) -> stats.multiplyMaxHealth(-0.15)),
      Rarity.RARE
  );
  public static final CollectibleBuilder PROFOUND_SILENCE = collectible(
      "profound_silence",
      "054",
      "《大静谧》",
      "所有敌方单位的生命-20%",
      "All enemy units have -20% HP.",
      "描绘伊比利亚史上最大灾难的画作，笔触毫无意义，色彩毫无意义，意象毫无意义。“阿戈尔知道，阿戈尔知道，阿戈尔知道。”",
      "A painting that depicts the greatest disaster in Iberian history. Its brushstrokes, colors, and images are all meaningless. 'The Ægir know. The Ægir know. The Ægir know.'",
      enemySpawnStat("敌方生成时最大生命-20%", "-20% enemy maximum HP on spawn", (enemy, stats) -> stats.multiplyMaxHealth(-0.20)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder ORIRON_ROUND_SHIELD = collectible(
      "oriron_round_shield",
      "055",
      "异铁小圆盾",
      "所有我方单位的防御力+15%",
      "All friendly units have +15% DEF.",
      "在异铁被广泛应用于现代工业领域前，这种材料时常被用来制造军用制式武器。",
      "Before the use of Oriron became widespread in today's industrial sector, the material was frequently used to forge military weapons like this one.",
      statPercent("防御力+15%", "+15% DEF", CombatStat::multiplyDefense, 0.15),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder MILITARY_MIRROR_ARMOR = collectible(
      "military_mirror_armor",
      "056",
      "军团护心镜",
      "所有我方单位的防御力+25%",
      "All friendly units have +25% DEF.",
      "这种甲片曾经能够防护住针对心脏的致命伤害，但随着高卢没落，护心镜这种过时的护甲样式也退出了历史舞台。",
      "This mirror armor used to be able to withstand most attacks on the chest, but with the fall of Gaul, such archaic forms of armor have become museum pieces.",
      statPercent("防御力+25%", "+25% DEF", CombatStat::multiplyDefense, 0.25),
      Rarity.RARE
  );
  public static final CollectibleBuilder OLD_STEAM_ARMOR = collectible(
      "old_steam_armor",
      "057",
      "古旧的蒸汽甲胄",
      "所有我方单位的防御力+35%",
      "All friendly units have +35% DEF.",
      "哪怕是这种旧型号的蒸汽甲胄，也仿佛承载着维多利亚君主们照耀着半片大地的荣耀辉光。",
      "Even this old, outdated steam armor seems to carry with it the resplendence of when the Victorian monarchs cast their glory across half the world.",
      statPercent("防御力+35%", "+35% DEF", CombatStat::multiplyDefense, 0.35),
      Rarity.EPIC
  );
  public static final CollectibleBuilder EMPERORS_FAVOR = collectible(
      "emperors_favor",
      "058",
      "皇帝的恩宠",
      "所有我方近战单位的攻击力+15%",
      "All friendly melee units have +15% ATK.",
      "十分锋利的拆信刀。前任乌萨斯皇帝喜爱的日用品。大多数时候并不用来拆信。",
      "A very sharp letter opener. It was one of the favorite possessions of the last Ursus emperor, and as such it was very rarely used to actually open letters.",
      statPercent("攻击力+15%", "+15% ATK", CombatStat::multiplyAttack, 0.15),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder ROYAL_RAPIER = collectible(
      "royal_rapier",
      "059",
      "贵族刺剑",
      "所有我方近战单位的攻击力+25%",
      "All friendly melee units have +25% ATK.",
      "装饰性强的贵族刺剑。高卢贵族首先使用，作为军事武器还是太脆了。",
      "A highly decorative rapier for nobles. Used by Gaulish nobles first and foremost, though perhaps a little fragile for a military weapon.",
      statPercent("攻击力+25%", "+25% ATK", CombatStat::multiplyAttack, 0.25),
      Rarity.RARE
  );
  public static final CollectibleBuilder VIEUX_VANGUARDS_BLADE = collectible(
      "vieux_vanguards_blade",
      "060",
      "老近卫军之锋",
      "所有我方近战单位的攻击力+35%",
      "All friendly melee units have +35% ATK.",
      "高卢将见证老近卫军的牺牲，随后才迎来自己的覆灭。",
      "Prior to its downfall, Gaul first witnessed the sacrifices of its Vieux Vanguards.",
      statPercent("攻击力+35%", "+35% ATK", CombatStat::multiplyAttack, 0.35),
      Rarity.EPIC
  );
  public static final CollectibleBuilder NECKLACE_OF_THE_PRESENCE = collectible(
      "necklace_of_the_presence",
      "061",
      "显圣吊坠",
      "所有我方远程单位的攻击力+15%",
      "All friendly ranged units have +15% ATK.",
      "信仰悬在你的心口上方十公分处，语言流入你的血液，子弹滑进你的弹仓。",
      "Faith hangs ten centimeters above your heart, spoken words flow into your blood, and bullets slide into your magazine.",
      runtime(percent(CombatStat::multiplyAttack, 0.15)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder SILVER_FORKS = collectible(
      "silver_forks",
      "062",
      "银餐叉",
      "所有我方远程单位的攻击力+25%",
      "All friendly ranged units have +25% ATK.",
      "一把用来处理失败的演员，一把用来收拾蹩脚的剧作者，还有一把留给有需要的人。厨师长不能容忍任何人在餐桌上出错。",
      "One for dealing with actors who botch their performances, one for dealing with incompetent playwrights, and one for anyone who needs it. The Chief will not tolerate any mistakes on the table.",
      runtime(percent(CombatStat::multiplyAttack, 0.25)),
      Rarity.RARE
  );
  public static final CollectibleBuilder DAMAGED_REVOLVER_CYLINDER = collectible(
      "damaged_revolver_cylinder",
      "063",
      "损坏的左轮弹巢",
      "所有我方远程单位的攻击力+35%",
      "All friendly ranged units have +35% ATK.",
      "她行过刀山火海也不曾有一刻向奸邪低头，她枪口火舌焦灼好似怒阳，她头顶光芒炽烈几胜白昼。此处安葬着Outcast，我们的朋友。",
      "She never capitulated, even after enduring countless trials and tribulations. Her muzzle belched tongues of flame that burned like an angry sun, and the glow above her head was brighter than the daylight itself. Buried here is Outcast, our friend.",
      runtime(percent(CombatStat::multiplyAttack, 0.35)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder NOXIOUS_HEMOSTATIC_AGENT = collectible(
      "noxious_hemostatic_agent",
      "064",
      "难闻的止血剂",
      "所有我方单位的生命+20%",
      "All friendly units have +20% HP.",
      "“这种源石虫的体液经过发酵处理之后可以用于止血”——《罗德岛野外生存指南》",
      "'After fermentation, the body fluids of this Originium slug can be used to stop bleeding.' —'Rhodes Island Wilderness Survival Guide'",
      statPercent("最大生命值+20%", "+20% maximum HP", CombatStat::multiplyMaxHealth, 0.2),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder FIRST_AID_KIT = collectible(
      "first_aid_kit",
      "065",
      "急救药箱",
      "所有我方单位的生命+35%",
      "All friendly units have +35% HP.",
      "“…抗生素与乙醇添加物，请在专业人士指导下使用…”——雷姆必拓安全生产指南",
      "'...Please use antibiotics and ethanol additives under the supervision of professionals...' —Rim Billiton Production Safety Manual",
      statPercent("最大生命值+35%", "+35% maximum HP", CombatStat::multiplyMaxHealth, 0.35),
      Rarity.RARE
  );
  public static final CollectibleBuilder UNKNOWN_INSTRUMENT = collectible(
      "unknown_instrument",
      "066",
      "未知仪器",
      "所有我方单位的生命+50%",
      "All friendly units have +50% HP.",
      "不要问。就当为了你自己好。",
      "Don't ask. It's for your own good.",
      statPercent("最大生命值+50%", "+50% maximum HP", CombatStat::multiplyMaxHealth, 0.5),
      Rarity.EPIC
  );
  public static final CollectibleBuilder RUSTED_RAZOR = collectible(
      "rusted_razor",
      "067",
      "锈蚀刀片",
      "所有敌方单位受到的物理伤害+15%",
      "All enemies take +15% Physical damage.",
      "如果它割破了皮肤，你知道会发生什么。",
      "If you cut yourself with this, you know what will happen.",
      sourceRule("所有敌方单位受到的物理伤害+15%"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder CARRIAGE_DRIVERS_WHIP = collectible(
      "carriage_drivers_whip",
      "068",
      "赶车夫的长鞭",
      "所有敌方单位受到的物理伤害+25%",
      "All enemies take +25% Physical damage.",
      "作为剧团的赶车人，他没有名字，没有过往。唯有挥鞭驱赶驮兽，他的生命才有意义。",
      "As the troupe's carriage driver, he has no name and no past. His life is meaningful only when he whips his burdenbeasts.",
      sourceRule("所有敌方单位受到的物理伤害+25%"),
      Rarity.RARE
  );
  public static final CollectibleBuilder AVENGER = collectible(
      "avenger",
      "069",
      "“复仇者”",
      "所有敌方单位受到的物理伤害+35%",
      "All enemies take +35% Physical damage.",
      "在阿斯卡纶第一次为军事委员会完成任务后，由特雷西斯亲手赠送，特蕾西娅为她安装的第一把武器。",
      "A gift from Theresis and set up by Theresa, this is the very first weapon that Ascalon received after her inaugural mission as part of the Military Council.",
      sourceRule("所有敌方单位受到的物理伤害+35%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder STANDARD_ANTI_RIOT_INSTRUMENT = collectible(
      "standard_anti_riot_instrument",
      "070",
      "制式防暴用具",
      "所有敌方单位受到的法术伤害+20%",
      "All enemies take +20% Arts damage.",
      "乌萨斯军警的制式装备，自带施放间歇性致盲源石技艺的功能。可惜关键时刻想得起来这一功能的军警寥寥无几。",
      "Standard equipment of the Ursus Guard. Comes with an intermittent blinding Originium Arts effect. Unfortunately, not many Guards out there remember this when push comes to shove.",
      sourceRule("所有敌方单位受到的法术伤害+20%"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder EMPERORS_COLLECTION = collectible(
      "emperors_collection",
      "071",
      "皇帝的收藏",
      "所有敌方单位受到的法术伤害+30%",
      "All enemies take +30% Arts damage.",
      "萨米人对他们的荒野尊崇无比，而现在这片荒野的碎片正静静躺在乌萨斯皇帝的私库中。",
      "The Sami revere their wild lands more than anything, and this fragment of the vast wilderness sleeps in the personal collection of the Emperor of Ursus.",
      sourceRule("所有敌方单位受到的法术伤害+30%"),
      Rarity.RARE
  );
  public static final CollectibleBuilder BRILLIANT_LAMENT = collectible(
      "brilliant_lament",
      "072",
      "“璀璨悲泣”",
      "所有敌方单位受到的法术伤害+40%",
      "All enemies take +40% Arts damage.",
      "他的血液仍在流淌，他从不曾真的自这里离开。",
      "His blood yet flows. He has never left this place.",
      sourceRule("所有敌方单位受到的法术伤害+40%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder LIVE_ROSE = collectible(
      "live_rose",
      "073",
      "活玫瑰",
      "所有我方单位受到的治疗和生命回复效果+20%",
      "All friendly units have +20% healing effectiveness",
      "是你滋养她，还是她守护你？",
      "Is it you who nourish her, or is it her who protects you?",
      statFlat("受到的治疗与生命回复效果+20%", "+20% healing and health regeneration received", CombatStat::addHealingAndHealthRegenerationBonus, 0.20),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder WHITE_FLOWER_CROWN = collectible(
      "white_flower_crown",
      "074",
      "苍白花冠",
      "所有我方单位受到的治疗和生命回复效果+30%",
      "All friendly units have +30% healing effectiveness",
      "血魔仪式使用的花冠头纱，曾经鲜红的生命被吮吸殆尽，现在只余苍白。",
      "A floral veil used in Vampire rituals. Its once vibrant redness has been sucked empty, leaving nothing only a pale white color.",
      statFlat("受到的治疗与生命回复效果+30%", "+30% healing and health regeneration received", CombatStat::addHealingAndHealthRegenerationBonus, 0.30),
      Rarity.RARE
  );
  public static final CollectibleBuilder ACTORS_PERFUME = collectible(
      "actors_perfume",
      "075",
      "演出用香水",
      "所有我方单位每秒回复1%的最大生命值",
      "All friendly units recover 1% of Max HP per second",
      "剧团演员们上台前会喷一些在身上，这种稳定且芬芳的香气能为演员持续舒缓压力，至少，表面上，看上去是这样的。",
      "Troupe actors put this on before going onstage. Stable yet aromatic fragrances like this can help them ease pressure. That's how it seems on the surface, at least.",
      effect("每秒回复1%的最大生命值", "Recover 1% of maximum HP every second",
          regenerationPercentage(0.01)),
      Rarity.RARE
  );
  public static final CollectibleBuilder DESIGNERS_RULER = collectible(
      "designers_ruler",
      "076",
      "设计师量尺",
      "所有我方单位获得15%物理闪避",
      "All friendly units have +15% Physical Dodge",
      "在数据、经验以及精确测量下，身着铠甲的人多了几分幸运。",
      "Carefully measured with data and experience, those who wear this armor are known to be several magnitudes luckier.",
      statFlat("物理伤害闪避率+15%", "+15% Physical damage evasion", CombatStat::addPhysicalDamageEvasionRate, 0.15),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder ARTS_KILLER = collectible(
      "arts_killer",
      "077",
      "“法术杀手”",
      "所有我方单位获得15%法术闪避",
      "All friendly units have +15% Arts Dodge",
      "由于莱塔尼亚人的源石技艺与音乐息息相关，有人竟想出了用噪音对抗法术的蠢主意。",
      "Considering how Originium Arts and music go hand in hand in Leithanien, it's quite amazing someone came up with the bright idea of combating Arts with noise like this.",
      statFlat("法术伤害闪避率+15%", "+15% Arts damage evasion", CombatStat::addMagicDamageEvasionRate, 0.15),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder DANCERS_BRACELETS = collectible(
      "dancers_bracelets",
      "078",
      "舞者手链",
      "所有我方单位获得10%物理与法术闪避",
      "All friendly units have +10% Physical and Arts Dodge",
      "训练有素的舞者在舞台上翩翩起舞躲过陷阱，观众们鼓掌喝彩，全然不知有人刚刚与死亡擦肩而过。",
      "The audience breaks into applause as the well-trained dancers waltz around the traps on stage. None of them are aware of the brushes with death the performers have just been through.",
      effect("物理与法术伤害闪避率+10%", "+10% Physical and Arts damage evasion", statSet(flat(CombatStat::addPhysicalDamageEvasionRate, 0.10), flat(CombatStat::addMagicDamageEvasionRate, 0.10))),
      Rarity.RARE
  );
  public static final CollectibleBuilder URSUS_BIG_BREAD = collectible(
      "ursus_big_bread",
      "079",
      "乌萨斯列巴",
      "可携带干员+1",
      "Squad Size Limit +1",
      "一种来自乌萨斯的食物，吃起来有一股子浓烈的发酵味儿。就是酸。",
      "Food from Ursus with a strong, fermented taste. Quite sour.",
      explorationRule("可携带干员+1", power -> power.squadCapacity(1)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder CRUCIBLE_CREAM_PUFF = collectible(
      "crucible_cream_puff",
      "080",
      "苦行者泡芙",
      "可携带干员+1",
      "Squad Size Limit +1",
      "行在苦路上的人们常常用这甘甜来抚慰心灵。",
      "Those who walk treacherous paths feast on this dessert to soothe their souls.",
      explorationRule("可携带干员+1", power -> power.squadCapacity(1)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder IRON_BAGUETTE = collectible(
      "iron_baguette",
      "081",
      "铁棍面包",
      "可携带干员+1",
      "Squad Size Limit +1",
      "坚硬的棍状面包，随身带上一根，既可以饱腹，又能够防身。",
      "A hard, rod-shaped bread. Always keep one on you. Not only does it fill you up, you can use it as a weapon to defend yourself.",
      explorationRule("可携带干员+1", power -> power.squadCapacity(1)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder TEST_RUN_CHOCOLATE = collectible(
      "test_run_chocolate",
      "082",
      "试制巧克力",
      "可携带干员+2",
      "Squad Size Limit +2",
      "只是将研发员个人偏爱的咸蛋黄与巧克力进行简单混合后的试制品，尚未达到能作为商品面世的水准。“还缺少酥脆的灵魂……”",
      "Just a test product a researcher threw together by tossing together their personal favorites, salted egg yolks and chocolate. Not yet suitable for the mass market. 'Still don't have that crispiness to them...'",
      explorationRule("可携带干员+2", power -> power.squadCapacity(2)),
      Rarity.RARE
  );
  public static final CollectibleBuilder GAULISH_MACARONS = collectible(
      "gaulish_macarons",
      "083",
      "高卢小圆饼",
      "可携带干员+2",
      "Squad Size Limit +2",
      "由于高卢圆饼的糖分高得吓人，部分店家推出了微缩版本以供顾客享用。",
      "With how much sugar there is in Gaulish macarons, some bakeries concocted these miniature treats for their customers' guilt-free enjoyment.",
      explorationRule("可携带干员+2", power -> power.squadCapacity(2)),
      Rarity.RARE
  );
  public static final CollectibleBuilder VICTORIAN_CAKE = collectible(
      "victorian_cake",
      "084",
      "维多利亚蛋糕",
      "可携带干员+3",
      "Squad Size Limit +3",
      "纪念我们常胜不败的皇帝！",
      "For our indomitable emperor!",
      explorationRule("可携带干员+3", power -> power.squadCapacity(3)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder ROUTE_DIAGRAM = collectible(
      "route_diagram",
      "085",
      "路线说明图",
      "可同时部署人数+1",
      "Deployment Limit +1",
      "一张干员亲手绘制的地图，附赠路线说明。除了书写者本人，没人看得懂。",
      "A hand-drawn map sketched out by an operator. Comes with explanations on the route, too. No one except its creator can decipher the map.",
      explorationRule("可同时部署人数+1", power -> power.deploymentLimit(1)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder DIM_LANTERN = collectible(
      "dim_lantern",
      "086",
      "昏暗的提灯",
      "可同时部署人数+1",
      "Deployment Limit +1",
      "干员探险用的提灯，可以手提，也可以挂在背包上。\n明明在外面很亮，但在这里就不好使。",
      "A lantern used during explorations. Operators can either hold it or hang it from their bags.\nIt's very bright outside, but it's hardly usable in here.",
      explorationRule("可同时部署人数+1", power -> power.deploymentLimit(1)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BRASS_COMPASS = collectible(
      "brass_compass",
      "087",
      "黄铜指南针",
      "可同时部署人数+2",
      "Deployment Limit +2",
      "它总是指向同一个方向。但那到底是哪儿？",
      "It's always pointing at the same direction, but where is it pointing exactly?",
      explorationRule("可同时部署人数+2", power -> power.deploymentLimit(2)),
      Rarity.RARE
  );
  public static final CollectibleBuilder BROKE_MASK = collectible(
      "broke_mask",
      "088",
      "破损的面具",
      "可同时部署人数+2",
      "Deployment Limit +2",
      "他戴上了面具。\n是他在说话还是面具在说话？",
      "He's wearing the mask.\nIs he the one who's speaking or is it the mask?",
      explorationRule("可同时部署人数+2", power -> power.deploymentLimit(2)),
      Rarity.RARE
  );
  public static final CollectibleBuilder BLANK_BUSINESS_CARD = collectible(
      "blank_business_card",
      "089",
      "空白名片",
      "可携带干员+1，可同时部署人数+1",
      "+1 Squad Size Limit, +1 Deployment Limit.",
      "神秘的名片，以神秘方式记录着神秘杀手的联系方式。",
      "A mysterious card with the mysterious assassin's contact information written in most mysterious ways.",
      explorationRule("可携带干员+1，可同时部署人数+1", power -> power.squadCapacity(1).deploymentLimit(1)),
      Rarity.RARE
  );
  public static final CollectibleBuilder DOLL_HOUSE = collectible(
      "doll_house",
      "090",
      "人偶之家",
      "初始部署费用+10",
      "+10 Starting DP",
      "每个孩子都该有一套。",
      "All kids need at least one set.",
      explorationRule("初始部署费用+10", power -> power.initialDeploymentPoints(10)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder MINIATURE_STAGE_MODEL = collectible(
      "miniature_stage_model",
      "091",
      "微缩舞台模型",
      "初始部署费用+20",
      "+20 Starting DP",
      "红色的颜料未干。小心，别染红了手。",
      "The red paint has not dried yet. Careful. Don't get that paint on your hand.",
      explorationRule("初始部署费用+20", power -> power.initialDeploymentPoints(20)),
      Rarity.RARE
  );
  public static final CollectibleBuilder DREAMBIND_CASTLE_MODEL = collectible(
      "dreambind_castle_model",
      "092",
      "缠梦古堡模型",
      "初始部署费用+30",
      "+30 Starting DP",
      "你见过这里，你曾在古堡中游荡，但你现在将其捧在掌心。这一切到底都是真的，还是只是一场梦？",
      "You've seen this place, and you once wandered the castle, yet you now hold it in your hand. Is this real, or is it all a dream?",
      explorationRule("初始部署费用+30", power -> power.initialDeploymentPoints(30)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BLUNT_CLAWS_ADVANCEMENT = collectible(
      "blunt_claws_advancement",
      "093",
      "钝爪-突破",
      "所有【先锋】干员的部署费用-2，生命+60%",
      "Vanguard Operators have -2 DP Cost and +60% Max HP",
      "在波涛中插下顽石，分割巨浪，破开迷雾。",
      "Plant a great stone within the waters. Split the waves and part the fog.",
      runtime(percent(CombatStat::multiplyMaxHealth, 0.6)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BLUNT_CLAWS_BURST = collectible(
      "blunt_claws_burst",
      "094",
      "钝爪-爆发",
      "所有【先锋】干员的初始技力+15",
      "Vanguard Operators start with +15 SP.",
      "他已经把所有后续部队的事情都干完了，还要别人做什么？",
      "He's already done all the reserve squad's work for them. What else do you expect?",
      sourceRule("所有【先锋】干员的初始技力+15"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BLUNT_CLAWS_PROFICIENCY = collectible(
      "blunt_claws_proficiency",
      "095",
      "钝爪-熟稔",
      "所有【先锋】干员的技力消耗-35%",
      "The SP Cost of Vanguard Operators' skills is decreased by -35%.",
      "作为大部分时候最先进入战场的干员，你无法想象他们经历了多少。",
      "As an operator who often enters the battlefield before anyone else, you cannot fathom what they have been through.",
      sourceRule("所有【先锋】干员的技力消耗-35%"),
      Rarity.RARE
  );
  public static final CollectibleBuilder BLUNT_CLAWS_INSPIRATION = collectible(
      "blunt_claws_inspiration",
      "096",
      "钝爪-振奋",
      "所有【先锋】干员的再部署时间-50%",
      "The Redeployment Time of Vanguard Operators -50%.",
      "只是战略性撤退而已，这样才好在贯穿敌人前看清他们长什么样。",
      "It's just a strategic retreat. That way, you can see what they look like before penetrating the enemy.",
      sourceRule("所有【先锋】干员的再部署时间-50%"),
      Rarity.RARE
  );
  public static final CollectibleBuilder BLUNT_CLAWS_MASTERY = collectible(
      "blunt_claws_mastery",
      "097",
      "钝爪-百战",
      "所有【先锋】干员的攻击力+50%，防御力+50%",
      "Vanguard Operators have +50% ATK and DEF",
      "“当黑漆漆的敌人向我涌来时，我脑海里只想到四个字——它们完了。”",
      "'When the darkly-clad enemies rushed towards me, there were only four words on my mind — they are done for.'",
      effect("攻击力+50%，防御力+50%", "+50% ATK and +50% DEF", statSet(percent(CombatStat::multiplyAttack, 0.5), percent(CombatStat::multiplyDefense, 0.5))),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BEND_SPEARS_ADVANCEMENT = collectible(
      "bend_spears_advancement",
      "098",
      "折戟-突破",
      "所有【近卫】干员的部署费用-3，生命+40%",
      "Guard Operators have -3 DP Cost and +40% Max HP",
      "战士走入战场，战士拔出战刃，战士迎接战斗。",
      "The soldiers stepped onto the battlefield, drew their blades, and met in battle.",
      runtime(percent(CombatStat::multiplyMaxHealth, 0.4)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BEND_SPEARS_ACUITY = collectible(
      "bend_spears_acuity",
      "099",
      "折戟-锋刃",
      "所有【近卫】干员的攻击力+25%",
      "Guard Operators have +25% ATK.",
      "“这是你这个月砍坏的第七把刀了。”“但是我这个月砍了八个敌人。”",
      "'This is the 7th blade you've broken this month.' 'But I cut down eight enemies this month.'",
      statPercent("攻击力+25%", "+25% ATK", CombatStat::multiplyAttack, 0.25),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BEND_SPEARS_BLOODBATH = collectible(
      "bend_spears_bloodbath",
      "100",
      "折戟-浴血",
      "所有【近卫】干员在攻击后获得2点技力",
      "Guard Operators restore 2 SP after each attack.",
      "愈战愈勇的战士十分少见，我们常需要安排一些心理辅导来缓和战后的情绪。",
      "Soldiers who get bolder as time passes are rare. Most need some form of psychological counseling to alleviate the trauma of war.",
      sourceRule("所有【近卫】干员在攻击后获得2点技力"),
      Rarity.RARE
  );
  public static final CollectibleBuilder BEND_SPEARS_ARMY_OF_ONE = collectible(
      "bend_spears_army_of_one",
      "101",
      "折戟-一夫当关",
      "所有【近卫】干员的阻挡数+1",
      "Guard Operators have +1 Block.",
      "并不是只有身穿重甲或是手持盾牌的人才擅长抵御敌人的攻击。迅速击溃有可能对你造成伤害的敌人才是最好的防守。",
      "One does not need to don heavy armor or hold up a shield to resist enemy attacks. Oftentimes, the best defense is to quickly eliminate those that would harm you.",
      sourceRule("所有【近卫】干员的阻挡数+1"),
      Rarity.RARE
  );
  public static final CollectibleBuilder BEND_SPEARS_DEATHMATCH = collectible(
      "bend_spears_deathmatch",
      "102",
      "折戟-破釜沉舟",
      "所有【近卫】干员的防御力-40%，但攻击力+40%，攻击速度+30",
      "Guard Operators have -40% DEF, but gain +40% ATK and +30 ASPD.",
      "不太建议向煌学习那些技巧。",
      "I wouldn't really recommend learning those skills from Blaze.",
      effect("防御力-40%，攻击力+40%，攻击速度+30", "-40% DEF, +40% ATK and +30 ASPD", statSet(percent(CombatStat::multiplyDefense, -0.4), percent(CombatStat::multiplyAttack, 0.4), flat(CombatStat::addAttackSpeed, 30))),
      Rarity.EPIC
  );
  public static final CollectibleBuilder IRON_GUARD_ADVANCEMENT = collectible(
      "iron_guard_advancement",
      "103",
      "铁卫-突破",
      "所有【重装】干员的部署费用-3，生命+40%",
      "Defender Operators have -3 DP Cost and +40% Max HP",
      "持盾者连成山脉，连成土地，他们对抗的不是血肉之敌，他们对抗命运，对抗不公。",
      "The shieldbearers formed a mountain range and became the earth. What they fight against are not enemies of flesh and blood. They fight against fate and injustice.",
      runtime(percent(CombatStat::multiplyMaxHealth, 0.4)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder IRON_GUARD_INVASION = collectible(
      "iron_guard_invasion",
      "104",
      "铁卫-侵掠",
      "所有【重装】干员的攻击力+60%",
      "Defender Operators have +60% ATK.",
      "对队友和战略目的的保护行动往往会让人忽视他们原本的侵略性。",
      "Defensive actions for the sake of teammates or strategic purposes often make people forget about their original aggressiveness.",
      statPercent("攻击力+60%", "+60% ATK", CombatStat::multiplyAttack, 0.6),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder IRON_GUARD_TRANQUILITY = collectible(
      "iron_guard_tranquility",
      "105",
      "铁卫-不动",
      "所有【重装】干员的防御力+25%，生命+50%",
      "Defender Operators have +25% DEF and +50% Max HP",
      "“真有人能在那种规模的轰炸下一动不动？”“谁说一动不动的，他还往前挪了几步。”",
      "'Is there anyone who can remain in formation under a bombing of that scale?' 'Standing in formation? He moved a few steps forward.'",
      effect("防御力+25%，最大生命值+50%", "+25% DEF and +50% maximum HP", statSet(percent(CombatStat::multiplyDefense, 0.25), percent(CombatStat::multiplyMaxHealth, 0.5))),
      Rarity.RARE
  );
  public static final CollectibleBuilder IRON_GUARD_ADVANCE = collectible(
      "iron_guard_advance",
      "106",
      "铁卫-推进",
      "所有【重装】干员阻挡数-1（部署时不会低于1），但攻击力+40%，攻击速度+40",
      "Defender Operators have -1 Block (will not be reduced below 1), but gain +40% ATK and +40 ASPD.",
      "以放弃防守换取进攻的机会，毁灭的阵线向前迈进。",
      "In exchange for an opportunity to attack, the shattered ranks made one more advance.",
      sourceRule("所有【重装】干员阻挡数-1（部署时不会低于1），但攻击力+40%，攻击速度+40"),
      Rarity.RARE
  );
  public static final CollectibleBuilder IRON_GUARD_IMPENETRABLE = collectible(
      "iron_guard_impenetrable",
      "107",
      "铁卫-无锋",
      "所有【重装】干员在受到攻击后获得2点技力",
      "Defender Operators restore 2 SP after being attacked.",
      "他们承受的每一次攻击，都将化作怒火数倍返还。",
      "Every blow they endure will turn into anger and be returned severalfold.",
      sourceRule("所有【重装】干员在受到攻击后获得2点技力"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder FATAL_BOLTS_ADVANCEMENT = collectible(
      "fatal_bolts_advancement",
      "108",
      "残弩-突破",
      "所有【狙击】干员的部署费用-2，生命+60%",
      "Sniper Operators have -2 DP Cost and +60% Max HP",
      "上膛，瞄准，开火，毁灭如期而至。",
      "Load, aim, and fire. Destruction arrives as scheduled.",
      runtime(percent(CombatStat::multiplyMaxHealth, 0.6)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder FATAL_BOLTS_PRECISION = collectible(
      "fatal_bolts_precision",
      "109",
      "残弩-百步穿杨",
      "所有【狙击】干员的攻击力+20%",
      "Sniper Operators have +20% ATK.",
      "在源石技艺尚不如今天发达的时代，炎国曾以“百步穿杨”的典故来称赞他人箭术高超。",
      "In an era when Originium Arts were not as robust as they are now, Yan had a saying, 'a pierced willow leaf from a hundred paces,' to praise others for their mastery of archery.",
      runtime(percent(CombatStat::multiplyAttack, 0.2)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder FATAL_BOLTS_SYNERGY = collectible(
      "fatal_bolts_synergy",
      "110",
      "残弩-战场依存",
      "所有【狙击】干员的自然技力恢复+0.5/秒",
      "Increases the SP regen rate of Sniper Operators by +0.5/s",
      "和武器越发亲密的狙击手，越不容易遭到武器的背叛。",
      "The more intimate a sniper is with her weapon, the less likely she is to be betrayed by it.",
      statFlat("自然回复技能技力+0.5/秒", "+0.5 SP/s for Auto Recovery skills", CombatStat::addNaturalSkillPointRegeneration, 0.5),
      Rarity.RARE
  );
  public static final CollectibleBuilder FATAL_BOLTS_CROSSFIRE = collectible(
      "fatal_bolts_crossfire",
      "111",
      "残弩-交叉火力",
      "所有【狙击】干员的生命-40%，但攻击力+40%",
      "Sniper Operators have -40% HP, but gain +40% ATK.",
      "密集的火力网布置同时让狙击干员们暴露在危险之中，接下来是一场关于准星的博弈。",
      "The dense crossfire also exposes snipers to great danger. The next battle boils down to a game of vision.",
      runtime(statSet(percent(CombatStat::multiplyAttack, 0.4), percent(CombatStat::multiplyMaxHealth, -0.4))),
      Rarity.RARE
  );
  public static final CollectibleBuilder FATAL_BOLTS_DIVINE_SPEED = collectible(
      "fatal_bolts_divine_speed",
      "112",
      "残弩-神速",
      "所有【狙击】干员的攻击速度+70",
      "Sniper Operators have +70 ASPD.",
      "据说古维多利亚的传奇弓手可以让箭矢几乎连成一线。",
      "It is said that the legendary archer of ancient Victoria can almost shoot a continuous stream of arrows.",
      statFlat("攻击速度+70", "+70 ASPD", CombatStat::addAttackSpeed, 70),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BROKEN_WAND_ADVANCEMENT = collectible(
      "broken_wand_advancement",
      "113",
      "断杖-突破",
      "所有【术师】干员的部署费用-3，生命+60%",
      "Caster Operators have -3 DP Cost and +60% Max HP",
      "让技艺在指尖起舞，呼风唤雨，搅乱现实，达成你宏伟的目的。",
      "Let Arts dance around your fingertips. Call the wind and rain, unravel reality, and achieve your grand goals.",
      runtime(percent(CombatStat::multiplyMaxHealth, 0.6)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BROKEN_WAND_ARTS_WEAVING = collectible(
      "broken_wand_arts_weaving",
      "114",
      "断杖-织法者",
      "所有【术师】干员的攻击力+25%",
      "Caster Operators have +25% ATK.",
      "在莱塔尼亚仰望高塔的时候经常会看见一些奇怪的现象......甚至是天象。",
      "Those who gaze up at the great spires in Leithanien often see some strange or even celestial phenomena.",
      runtime(percent(CombatStat::multiplyAttack, 0.25)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BROKEN_WAND_CHANTING = collectible(
      "broken_wand_chanting",
      "115",
      "断杖-咏唱",
      "所有【术师】干员的攻击速度+40",
      "Caster Operators have +40 ASPD.",
      "虽然音乐与法术有着奇妙的联系，呃，但这不是某些人五音不全的借口。",
      "Though the relationship between music and Arts is marvelous indeed, umm, this is not an excuse for some peoples' tone-deafness.",
      statFlat("攻击速度+40", "+40 ASPD", CombatStat::addAttackSpeed, 40),
      Rarity.RARE
  );
  public static final CollectibleBuilder BROKEN_WAND_CONCENTRATION = collectible(
      "broken_wand_concentration",
      "116",
      "断杖-凝神",
      "所有【术师】干员的技力恢复+0.4/秒",
      "Increases the SP regen rate of Caster Operators by +0.4/s",
      "“当一个术师打算全力以赴，哪怕是天火小姐那种坏脾气术师，也会展露出截然不同的气质。”",
      "'When a caster intends to go all out, they will display a completely different temperament — even if we're talking about something like Miss Skyfire's grumpiness.'",
      statFlat("自然回复技能技力+0.4/秒", "+0.4 SP/s for Auto Recovery skills", CombatStat::addNaturalSkillPointRegeneration, 0.4),
      Rarity.RARE
  );
  public static final CollectibleBuilder BROKEN_WAND_MALEDICTION = collectible(
      "broken_wand_malediction",
      "117",
      "断杖-苦难巫咒",
      "所有【术师】干员生命-40%，但造成的法术伤害+70%",
      "Caster Operators have -40% HP, but deal +70% Arts damage",
      "萨卡兹接触源石的起源几乎无从考证，古老法术的起点早已脱离物质现实与逻辑常理。",
      "It is virtually impossible to identify when the Sarkaz first encountered Originium, and the origins of these ancient Arts have long been separated from actual reality and common sense.",
      runtime(percent(CombatStat::multiplyMaxHealth, -0.4)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder STALWART_AID_ADVANCEMENT = collectible(
      "stalwart_aid_advancement",
      "118",
      "支柱-突破",
      "所有【辅助】干员的部署费用-2，生命+60%",
      "Supporter Operators have -2 DP Cost and +60% Max HP",
      "当“差一点完成任务”的时候，你需要的正是那个帮你补上“差一点”的人。",
      "Whenever you're 'almost done' with a task, what you need at that moment is someone to help you with the 'almost' part.",
      runtime(percent(CombatStat::multiplyMaxHealth, 0.6)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder STALWART_AID_SECONDARY_FRONT = collectible(
      "stalwart_aid_secondary_front",
      "119",
      "支柱-次要战场",
      "所有【辅助】干员的【召唤物】攻击力+50%",
      "Units summoned by Supporter Operators have +50% ATK.",
      "“呃，机械和源石技艺衍生物我尚且能理解，但是不是有些别的......”",
      "'Um, I can understand the derivatives of combining mechanics with Originium Arts, but there might be something else...'",
      runtime(percent(CombatStat::multiplyAttack, 0.5)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder STALWART_AID_DILIGENCE = collectible(
      "stalwart_aid_diligence",
      "120",
      "支柱-勤奋",
      "所有【辅助】干员的技力消耗-40%",
      "The SP Cost of Supporter Operators' skills is decreased by -40%.",
      "即使是看似最微小的工作，干员们也会全力以赴。何况事实上，一点也不微小。",
      "Operators tend to give it their all, even to tasks that might seem trivial. The reality is, those tasks are not trivial at all.",
      sourceRule("所有【辅助】干员的技力消耗-40%"),
      Rarity.RARE
  );
  public static final CollectibleBuilder STALWART_AID_DEMORALIZE = collectible(
      "stalwart_aid_demoralize",
      "121",
      "支柱-破兵",
      "所有【辅助】干员攻击范围内的敌方单位攻击力-15%",
      "Enemies within the attack range of Supporter Operators have -15% ATK.",
      "削弱敌人的伤害有很多手段：腐蚀兵器，摧残精神，加大重力，以及......让他们肚子疼。",
      "There are many ways to weaken an enemy's offensive capabilities: corroding their weapons, psychological warfare, increasing their weight, and... giving them a stomachache.",
      sourceRule("所有【辅助】干员攻击范围内的敌方单位攻击力-15%"),
      Rarity.RARE
  );
  public static final CollectibleBuilder STALWART_AID_COUNTER_ARTS = collectible(
      "stalwart_aid_counter_arts",
      "122",
      "支柱-枯法",
      "所有【辅助】干员攻击范围内的敌方单位防御力-20%，法术抗性-20%",
      "Enemies within the attack range of Supporter Operators have -20% DEF and -20% RES.",
      "弱化敌人的防御有很多手段：立场装置，法术彻甲，弱点标记，以及......让他们肚子疼。",
      "There are many ways to weaken an enemy's defensive capabilities: positional devices, armor-stripping Arts, marking weak points, and... giving them a stomachache.",
      sourceRule("所有【辅助】干员攻击范围内的敌方单位防御力-20%，法术抗性-20%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HEALERS_PATH_ADVANCEMENT = collectible(
      "healers_path_advancement",
      "123",
      "医者-突破",
      "所有【医疗】干员的部署费用-2，生命+60%",
      "Medic Operators have -2 DP Cost and +60% Max HP",
      "拯救是人类必须赞许的美德，是这一切得以存续的仰仗。",
      "The desire to save lives is a virtue that mankind must applaud, for that is the support that keeps us alive.",
      runtime(percent(CombatStat::multiplyMaxHealth, 0.6)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder HEALERS_PATH_SELF_TREATING = collectible(
      "healers_path_self_treating",
      "124",
      "医者-自医",
      "所有【医疗】干员的技力恢复+0.3/秒",
      "Increases the SP regen rate of Medic Operators by +0.3/s",
      "谁说医者不能自医，他们只是选择给自己来一片提神药然后继续帮助别人而已。",
      "Who says healers can't heal themselves? They simply pop some pills before continuing to help others.",
      statFlat("自然回复技能技力+0.3/秒", "+0.3 SP/s for Auto Recovery skills", CombatStat::addNaturalSkillPointRegeneration, 0.3),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder HEALERS_PATH_POTENCY = collectible(
      "healers_path_potency",
      "125",
      "医者-强效试剂",
      "所有【医疗】干员的攻击力+40%",
      "Medic Operators have +40% ATK.",
      "除了华法琳，没人敢采取这么激进的医疗手段，不过她从没失误过一次。",
      "Nobody else dared to attempt such a radical treatment — except Warfarin. She never made a single mistake.",
      runtime(percent(CombatStat::multiplyAttack, 0.4)),
      Rarity.RARE
  );
  public static final CollectibleBuilder HEALERS_PATH_KEEN_HANDS = collectible(
      "healers_path_keen_hands",
      "126",
      "医者-妙手",
      "所有【医疗】干员的攻击速度+50",
      "Medic Operators have +50 ASPD.",
      "比起从死亡手里多抢回一条命的伟业，事后昏睡个三天算什么？",
      "Compared to the feat of snatching a life from the grip of death, what is three days of coma afterwards?",
      runtime(flat(CombatStat::addAttackSpeed, 50)),
      Rarity.RARE
  );
  public static final CollectibleBuilder HEALERS_PATH_RESTORE_SANITY = collectible(
      "healers_path_restore_sanity",
      "127",
      "医者-理智固剂",
      "所有【医疗】干员攻击范围内的我方单位获得抵抗",
      "Allied units within the attack range of Medic Operators gain Resistance.",
      "加固你的思维，让你脑中帝国疆域上的每株杂草都无懈可击。",
      "Reinforce your thinking. In the domain of your mind, make even every weed impregnable.",
      statFlat("异常状态持续时间-50%", "-50% negative status duration", CombatStat::addFriendlyStatusDurationReduction, 0.50),
      Rarity.EPIC
  );
  public static final CollectibleBuilder RUSTED_BLADE_ADVANCEMENT = collectible(
      "rusted_blade_advancement",
      "128",
      "锈刃-突破",
      "所有【特种】干员的部署费用-2，生命+60%",
      "Specialist Operators have -2 DP Cost and +60% Max HP",
      "手段有很多，结局却只有一种。",
      "There may be many means, but only one end.",
      runtime(percent(CombatStat::multiplyMaxHealth, 0.6)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder RUSTED_BLADE_EXECUTION = collectible(
      "rusted_blade_execution",
      "129",
      "锈刃-处决",
      "所有【特种】干员的攻击速度+30",
      "Specialist Operators have +30 ASPD.",
      "红有一把小刀。她有一把小刀。",
      "Projekt Red has a knife. She has a knife.",
      runtime(flat(CombatStat::addAttackSpeed, 30)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder RUSTED_BLADE_ISOLATION = collectible(
      "rusted_blade_isolation",
      "130",
      "锈刃-单兵",
      "所有【特种】干员的攻击力+40%，防御力+40%",
      "Specialist Operators have +40% ATK and DEF",
      "S.W.E.E.P.的总管没有任何任务参与记录。*没有*。",
      "The director of S.W.E.E.P. does not have any mission participation records. *None*.",
      effect("攻击力+40%，防御力+40%", "+40% ATK and +40% DEF", statSet(percent(CombatStat::multiplyAttack, 0.4), percent(CombatStat::multiplyDefense, 0.4))),
      Rarity.RARE
  );
  public static final CollectibleBuilder RUSTED_BLADE_NO_MANS_LAND = collectible(
      "rusted_blade_no_mans_land",
      "131",
      "锈刃-无人之境",
      "所有【特种】干员的再部署时间-35%",
      "The Redeployment Time of Specialist Operators -35%.",
      "部分干员的作战方式很特殊，无论见到他们“好几次”还是“好几个”，都不要大惊小怪。",
      "Some operators have very peculiar combat methods. Don't make a fuss over how many times you see them, or how many of them you see.",
      sourceRule("所有【特种】干员的再部署时间-35%"),
      Rarity.RARE
  );
  public static final CollectibleBuilder RUSTED_BLADE_OVERWHELM = collectible(
      "rusted_blade_overwhelm",
      "132",
      "锈刃-神力",
      "所有【特种】干员的力度+2",
      "Specialist Operators have +2 shift strength.",
      "地形是作战的一环......只能说这么多了。",
      "Terrain is one aspect of battle... That's all I can say.",
      sourceRule("所有【特种】干员的力度+2"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HAND_OF_SPIKES = collectible(
      "hand_of_spikes",
      "133",
      "尖刺之手",
      "【铁卫】、【驭法铁卫】和【决战者】对自身阻挡的敌人每秒造成自身防御力100%的法术伤害",
      "Protector, Arts Protector, and Duelist Operators deal Arts damage equal to 100% of their own DEF on enemy units they block",
      "让敌人动手，自己打个头破血流。",
      "Let the enemy make his move. He will be his own downfall.",
      sourceRule("【铁卫】、【驭法铁卫】和【决战者】对自身阻挡的敌人每秒造成自身防御力100%的法术伤害"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HAND_OF_CHOKER = collectible(
      "hand_of_choker",
      "134",
      "扼喉之手",
      "【重射手】、【神射手】和【攻城手】的攻击会将生命值30%以下非领袖敌人强制击败",
      "Heavyshooter, Deadeye, and Besieger Operators' attacks will defeat all non-Boss enemy units at 30% or less HP",
      "最后一份气力，掐断最后一点生机。",
      "With your last strength, break open a last bit of hope for yourself.",
      sourceRule("【重射手】、【神射手】和【攻城手】的攻击会将生命值30%以下非领袖敌人强制击败"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HAND_OF_BUCKLER = collectible(
      "hand_of_buckler",
      "135",
      "扣挠之手",
      "【凝滞师】、【削弱者】和【护佑者】攻击时额外造成目标当前生命值3%的法术伤害",
      "Decel Binder, Hexer, and Abjurer Operators' attacks deal extra Arts damage equal to 3% of the target's HP",
      "点点滴滴，挡无可挡。",
      "Drop by drop, bit by bit. It is unstoppable.",
      sourceRule("【凝滞师】、【削弱者】和【护佑者】攻击时额外造成目标当前生命值3%的法术伤害"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HAND_OF_DIFFUSION = collectible(
      "hand_of_diffusion",
      "136",
      "扩散之手",
      "【扩散术师】、【链术师】和【轰击术师】每对一个单位造成伤害就回复2点技力值",
      "Splash Caster, Chain Caster, and Blast Caster Operators recover 2 SP every time they attack an enemy unit",
      "多揍一个敌人，多攒一份力量。",
      "The more enemies you strike, the more power you take hold of.",
      sourceRule("【扩散术师】、【链术师】和【轰击术师】每对一个单位造成伤害就回复2点技力值"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HAND_OF_SHREDDER = collectible(
      "hand_of_shredder",
      "137",
      "撕扯之手",
      "【无畏者】、【剑豪】和【教官】攻击时无视目标70%的防御力",
      "Dreadnought, Swordmaster, and Instructor Operators' attacks ignore 70% of target's DEF",
      "东撕西扯，铁甲如布匹。",
      "Tear it all apart. Their armor is nothing but cloth in your hands.",
      sourceRule("【无畏者】、【剑豪】和【教官】攻击时无视目标70%的防御力"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HAND_OF_SUPERSPEED = collectible(
      "hand_of_superspeed",
      "138",
      "极速之手",
      "【处决者】、【伏击客】和【傀儡师】周围八格内没有我方单位时，攻击速度+100",
      "Executor, Ambusher, and Dollkeeper Operators gain +100 ASPD when there are no friendly units in the eight adjacent tiles",
      "振臂一挥，只留残影。",
      "You swing your hand, leaving not even an afterimage.",
      sourceRule("【处决者】、【伏击客】和【傀儡师】周围八格内没有我方单位时，攻击速度+100"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HAND_OF_SNATCHER = collectible(
      "hand_of_snatcher",
      "139",
      "积攒之手",
      "【冲锋手】、【尖兵】和【战术家】的部署费用-12，且击败敌人后额外获得6点部署费用",
      "Charger, Pioneer, and Tactician Operators' DP Cost -12 and gain 6 additional DP after defeating an enemy",
      "缺了什么，就从敌人身上拿，别客气。",
      "Whatever it is you do not have, snatch it away from your enemy. There is no need to hesitate.",
      sourceRule("【冲锋手】、【尖兵】和【战术家】的部署费用-12，且击败敌人后额外获得6点部署费用"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BLUE_SILK_SCARF = collectible(
      "blue_silk_scarf",
      "140",
      "蓝色丝巾",
      "希望+2，可携带干员+1",
      "Hope +2, Squad Size Limit +1",
      "女士优雅的丝巾，她的出现代表安全。",
      "A lady's elegant scarf. Her appearance is a sign that it is safe here.",
      explorationRule("希望+2，可携带干员+1", power -> power.hope(2).squadCapacity(1)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder RED_BOW_TIE = collectible(
      "red_bow_tie",
      "141",
      "红色蝴蝶结",
      "希望+2，可同时部署人数+1",
      "Hope +2, Deployment Limit +1",
      "傀影送出的第一份礼物，Miss.Christine非常中意。",
      "The first present Phantom gave Miss.Christine. She is really fond of it.",
      explorationRule("希望+2，可同时部署人数+1", power -> power.hope(2).deploymentLimit(1)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder WEIRD_FLUTE = collectible(
      "weird_flute",
      "142",
      "古怪的长笛",
      "希望+4，可携带干员+1",
      "Hope +4, Squad Size Limit +1",
      "外形古怪的长笛，有人日夜将其吹响。",
      "A weird-looking flute. Someone plays it day in and day out.",
      explorationRule("希望+4，可携带干员+1", power -> power.hope(4).squadCapacity(1)),
      Rarity.RARE
  );
  public static final CollectibleBuilder GLASS_BIRD = collectible(
      "glass_bird",
      "143",
      "玻璃小鸟",
      "希望+4，可携带干员+1",
      "Hope +4, Squad Size Limit +1",
      "“在我的乐园里，这只小鸟会永远地歌唱下去。”",
      "'In this paradise of mine, the bird will sing forever and ever.'",
      explorationRule("希望+4，可携带干员+1", power -> power.hope(4).squadCapacity(1)),
      Rarity.RARE
  );
  public static final CollectibleBuilder SOLO_MUSIC_BOX = collectible(
      "solo_music_box",
      "144",
      "独奏八音盒",
      "希望+4，可同时部署人数+1",
      "Hope +4, Deployment Limit +1",
      "老旧的八音盒，打开之后音乐没有响起，盒子中央的小人也没有动。没有任何响动，无人跳舞。在与傀影对戏之前，女主演总是在后台痴痴地看着它。",
      "An old music box. The music does not play when the box is opened, nor does the figure inside show any signs it is about to move. There is not a peep, and no one is breaking out in dance. Before she begins her performances with Phantom, the lead actress stares blankly into the music box.",
      explorationRule("希望+4，可同时部署人数+1", power -> power.hope(4).deploymentLimit(1)),
      Rarity.RARE
  );
  public static final CollectibleBuilder ORIGINIUM_IRIS = collectible(
      "originium_iris",
      "145",
      "源石鸢尾花",
      "希望+6，可携带干员+2",
      "Hope +6, Squad Size Limit +2",
      "极尽奢华的花朵，剧团长在谢幕时向主演献花的次数寥寥无几。",
      "A most extravagant bouquet of flowers. The troupe leader has handed flowers like these to the leads during countless curtain calls.",
      explorationRule("希望+6，可携带干员+2", power -> power.hope(6).squadCapacity(2)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder PURE_GOLD_EXPEDITION = collectible(
      "pure_gold_expedition",
      "146",
      "赤金的远征",
      "希望+4，可携带干员+1，可同时部署人数+1",
      "Hope +4, Squad Size Limit +1, Deployment Limit +1",
      "君王自荒地出发，集市在她的裙下如春芽萌发，财富之路连通高山密林，直到她消失在西方浪涛的边崖。——萨尔贡古老童话",
      "The regent departed from the wastelands, and markets rose from beneath her dress like sprouts budding in the spring. The road of prosperity connected high mountains and dense forests, following her until she disappeared beyond the cliffs overlooking the western waves. —Ancient Sargon Fairy Tale",
      explorationRule("希望+4，可携带干员+1，可同时部署人数+1", power -> power.hope(4).squadCapacity(1).deploymentLimit(1)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder DURIN_OVERGROUND = collectible(
      "durin_overground",
      "147",
      "《杜林地上环游记》",
      "每场战斗获得1点临时目标生命值",
      "Gain 1 Temporary Life Point at the beginning of each battle.",
      "从这本游记中，我们可以一窥地底人对于地表人的奇异见解。",
      "In this expedition account, we get a glimpse of how those who live underground see those who live on the surface.",
      explorationRule("每场战斗获得1点临时目标生命值", power -> power.addMaxHealth(1)),
      Rarity.RARE
  );
  public static final CollectibleBuilder GAULISH_TOPONYM_ORIGINS = collectible(
      "gaulish_toponym_origins",
      "148",
      "《旧高卢地名源流考》",
      "每场战斗获得2点临时目标生命值",
      "Gain 2 Temporary Life Points at the beginning of each battle.",
      "薄绿从博士办公室里翻出来的古地名历史书，里面记载有克莱布拉松。",
      "A book of historical place names that Mint dug out of the Doctor's office. Calais-Blason is mentioned in the book.",
      explorationRule("每场战斗获得2点临时目标生命值", power -> power.addMaxHealth(2)),
      Rarity.RARE
  );
  public static final CollectibleBuilder ANCIENT_GAULISH_SILVER_COIN = collectible(
      "ancient_gaulish_silver_coin",
      "149",
      "古高卢银币",
      "所有干员的初始技力+6",
      "All Operators start with +6 SP.",
      "已灭亡国家“高卢”的流通货币，保存良好，收藏价值很高。",
      "A coin once circulated in the now fallen nation 'Gaul.' It is in very good shape, making it highly valuable as a collector's item.",
      sourceRule("所有干员的初始技力+6"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder ELYSEE_PURSE = collectible(
      "elysee_purse",
      "150",
      "爱丽舍钱袋",
      "所有干员的初始技力+8",
      "All Operators start with +8 SP.",
      "逛爱丽舍大街的贵妇们用的钱袋，慕斯有个同款。",
      "A purse used by high-class ladies who shop on the Élysée. Mousse has a purse like this.",
      sourceRule("所有干员的初始技力+8"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BANK_OF_GAUL_CHECK = collectible(
      "bank_of_gaul_check",
      "151",
      "高卢银行支票",
      "所有干员的初始技力+12",
      "All Operators start with +12 SP.",
      "镶金嵌银、奢华无比的支票本，现在已经无从兑现。",
      "An extravagant checkbook plated in gold and silver. Can no longer be cashed.",
      sourceRule("所有干员的初始技力+12"),
      Rarity.RARE
  );
  public static final CollectibleBuilder SECOND_ECONOMIC_REFORM_ACT = collectible(
      "second_economic_reform_act",
      "152",
      "《第二经济改革法》",
      "所有干员的初始技力+18",
      "All Operators start with +18 SP.",
      "以此法案为起点，高卢正式迈向了霸权帝国的道路。",
      "With this bill as the starting point, Gaul formally began its path to become a hegemonic empire.",
      sourceRule("所有干员的初始技力+18"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder VANILLA_SODA = collectible(
      "vanilla_soda",
      "153",
      "香草沙士汽水",
      "所有自然回复技能的技力恢复+0.2/秒",
      "Increases the SP regen rate of Auto Recovery skills by +0.2/s",
      "玻利瓦尔本地的廉价饮料，拥有上百年历史，混合多种香料，特点是不好喝。",
      "Bolívar's cheap specialty drink dates back hundreds of years. It is mixed with a variety of spices, and is known for not being very good.",
      statFlat("自然回复技能技力+0.2/秒", "+0.2 SP/s for Auto Recovery skills", CombatStat::addNaturalSkillPointRegeneration, 0.2),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BALL_JUICE = collectible(
      "ball_juice",
      "154",
      "球球果汁",
      "所有自然回复技能的技力恢复+0.25/秒",
      "Increases the SP regen rate of Auto Recovery skills by +0.25/s",
      "说是果汁其实是酒，瓶身上标注未成年禁止饮用。原产地曾在高卢版图内，现属于维多利亚。",
      "They call it fruit juice, but it's actually alcohol. The bottle itself states that minors are forbidden from drinking it. Its Victorian place of origin once fell within Gaul's territory.",
      statFlat("自然回复技能技力+0.25/秒", "+0.25 SP/s for Auto Recovery skills", CombatStat::addNaturalSkillPointRegeneration, 0.25),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder FOWLBEAST_LIVER_PATE = collectible(
      "fowlbeast_liver_pate",
      "155",
      "羽兽肝酱",
      "所有自然回复技能的技力恢复+0.35/秒",
      "Increases the SP regen rate of Auto Recovery skills by +0.35/s",
      "小瓶高档羽兽肝酱，颜色鲜艳，贵族特供。",
      "A small jar of fowlbeast liver pâté. Bright and vividly colorful, it is eaten only by nobles.",
      statFlat("自然回复技能技力+0.35/秒", "+0.35 SP/s for Auto Recovery skills", CombatStat::addNaturalSkillPointRegeneration, 0.35),
      Rarity.RARE
  );
  public static final CollectibleBuilder DREAMING_ESSENCE = collectible(
      "dreaming_essence",
      "156",
      "迷梦香精",
      "所有自然回复技能的技力恢复+0.5/秒",
      "Increases the SP regen rate of Auto Recovery skills by +0.5/s",
      "据说女妖们制作的香氛只会送给心上之人，只需一滴，就能令人如痴如醉，而艺术家们的灵感与创意也随之而来。",
      "Supposedly the Banshees make their aromatics only for their beloved. A single drop is enough to enchant just about anyone, bringing out any artist's inspiration and creativity.",
      statFlat("自然回复技能技力+0.5/秒", "+0.5 SP/s for Auto Recovery skills", CombatStat::addNaturalSkillPointRegeneration, 0.5),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BARRENS_TEQUILA = collectible(
      "barrens_tequila",
      "157",
      "荒地龙舌兰",
      "所有攻击和受击回复的技能每3.5秒回复1点技力",
      "Increases the SP regen rate of Offensive and Defensive Recovery skills by 1 SP per 3.5s.",
      "原产自玻利瓦尔的廉价蒸馏酒，味道很冲，广受哥伦比亚拓荒者的好评。",
      "A cheap distilled spirit originally produced in Bolívar. It has a strong taste and is widely praised by Columbia's explorers.",
      statFlat("攻击与受击回复技能每3.5秒回复1点技力", "+1 SP per 3.5s for Offensive and Defensive Recovery skills", CombatStat::addOffensiveDefensiveSkillPointRegeneration, 1.0 / 3.5),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder CAPTAIN_MORGANS_WINE = collectible(
      "captain_morgans_wine",
      "158",
      "摩根队长佳酿",
      "所有攻击和受击回复的技能每3秒回复1点技力",
      "Increases the SP regen rate of Offensive and Defensive Recovery skills by 1 SP per 3s.",
      "一种产自伊比利亚地区的酒，在维多利亚中部城市大受欢迎，现已停产。",
      "A wine produced in the Iberian region. Despite having gained popularity in the heart of Victoria's bustling cities, it is now discontinued.",
      statFlat("攻击与受击回复技能每3秒回复1点技力", "+1 SP per 3s for Offensive and Defensive Recovery skills", CombatStat::addOffensiveDefensiveSkillPointRegeneration, 1.0 / 3.0),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder WATER_OF_LIFE = collectible(
      "water_of_life",
      "159",
      "生命之水",
      "所有攻击和受击回复的技能每2.5秒回复1点技力",
      "Increases the SP regen rate of Offensive and Defensive Recovery skills by 1 SP per 2.5s.",
      "度数奇高的乌萨斯烈酒，上头，据说一个健康的乌萨斯人能干掉好几瓶。",
      "The strongest Ursine liquors go straight to one's head, but it's said that the healthy Ursine adult can down several bottles.",
      statFlat("攻击与受击回复技能每2.5秒回复1点技力", "+1 SP per 2.5s for Offensive and Defensive Recovery skills", CombatStat::addOffensiveDefensiveSkillPointRegeneration, 1.0 / 2.5),
      Rarity.RARE
  );
  public static final CollectibleBuilder ROYAL_LIQUEUR = collectible(
      "royal_liqueur",
      "160",
      "皇家利口酒",
      "所有攻击和受击回复的技能每1.5秒回复1点技力",
      "Increases the SP regen rate of Offensive and Defensive Recovery skills by 1 SP per 1.5s.",
      "已灭亡国家“高卢”的好酒，现在成为了收藏家手中的珍惜奇货，价格不菲。产地现处莱塔尼亚境内，工艺则已经失传。",
      "The fine wines of the extinct country, Gaul, have now become expensive, prime treasures for collectors. The place of production is now a part of Leithanien, but the technique has been lost.",
      statFlat("攻击与受击回复技能每1.5秒回复1点技力", "+1 SP per 1.5s for Offensive and Defensive Recovery skills", CombatStat::addOffensiveDefensiveSkillPointRegeneration, 1.0 / 1.5),
      Rarity.EPIC
  );
  public static final CollectibleBuilder UNLEASHINGS = collectible(
      "unleashings",
      "161",
      "“绽放”",
      "所有干员每次受到伤害时回复1点技力，每次受到元素损伤时回复1点技力",
      "All Operators recover 1 SP every time they take damage or Elemental Injury",
      "在Touch的尝试下，自然暂时向你敞开心扉。",
      "After several attempts by Touch, nature briefly opens up to you.",
      sourceRule("所有干员每次受到伤害时回复1点技力，每次受到元素损伤时回复1点技力"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder RENOWNED_SINGER = collectible(
      "renowned_singer",
      "162",
      "“当红歌手”",
      "敌人被晕眩、冰冻、束缚时，每秒受到600点法术伤害",
      "Enemies receive 600 Arts Damage per second when stunned, frozen, or bound",
      "别管它唱什么，赶紧让它闭嘴，吵死了！",
      "Who cares what it's singing? Make it stop! It's hurting my ears!",
      sourceRule("敌人被晕眩、冰冻、束缚时，每秒受到600点法术伤害"),
      Rarity.RARE
  );
  public static final CollectibleBuilder STRING_PUPPET = collectible(
      "string_puppet",
      "163",
      "悬丝傀儡",
      "敌人被晕眩、冰冻、束缚时，每秒受到700点法术伤害",
      "Enemies receive 700 Arts Damage per second when stunned, frozen, or bound",
      "越是用力想要挣脱，丝线就缠得越紧。你真的认为自己仍在思考？",
      "The more you struggle and try to break free, the tighter the strings get. Do you really think you are still thinking rationally?",
      sourceRule("敌人被晕眩、冰冻、束缚时，每秒受到700点法术伤害"),
      Rarity.RARE
  );
  public static final CollectibleBuilder CHILDRENS_PUPPET = collectible(
      "childrens_puppet",
      "164",
      "“童趣玩偶”",
      "敌人被晕眩、冰冻、束缚时，每秒受到1000点法术伤害",
      "Enemies receive 1000 Arts Damage per second when stunned, frozen, or bound",
      "伊比利亚的孩童们将海岸边的奇物做成了简陋玩偶，听说只要抱着它，梦乡就会变得充实起来。",
      "A simple puppet made by Iberian children using trinkets found on the seacoast. Supposedly just holding it tight will give you much richer dreams.",
      sourceRule("敌人被晕眩、冰冻、束缚时，每秒受到1000点法术伤害"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder COIN_OPERATED_TOY = collectible(
      "coin_operated_toy",
      "165",
      "投币玩具",
      "每有5源石锭，所有我方单位的攻击速度+3",
      "For each 5 Originium Ingots in possession, all friendly units have +3 ASPD",
      "不知道为什么会有人喜欢这种造型，但整体的设计理念确实可圈可点——只要投币，就连石像都会动起来，千真万确。",
      "No idea why anyone would like a design like this, but its overall concept is indeed praiseworthy—Just insert a coin, and even a statue will start moving. There is nothing truer in this world.",
      sourceRule("每有5源石锭，所有我方单位的攻击速度+3"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder CHIVALRIC_COMMANDMENTS_NEW_EDITION = collectible(
      "chivalric_commandments_new_edition",
      "166",
      "骑士戒律·新编",
      "每有5源石锭，所有我方单位的攻击速度+5",
      "For each 5 Originium Ingots in possession, all friendly units have +5 ASPD",
      "庄严宣誓那是过去的事儿了，现代骑士精神需要更加灵活的展现方式。在联络装置上输入你的编号，你便是名光荣的骑士。",
      "Taking solemn vows is a thing of the past. The spirit of modern knighthood requires more flexible displays. Go ahead and input your number in the communications device, and you too will be an honorary knight.",
      sourceRule("每有5源石锭，所有我方单位的攻击速度+5"),
      Rarity.RARE
  );
  public static final CollectibleBuilder GOLDEN_CHALICE = collectible(
      "golden_chalice",
      "167",
      "金酒之杯",
      "每有5源石锭，所有我方单位的攻击速度+7",
      "For each 5 Originium Ingots in possession, all friendly units have +7 ASPD",
      "圣人、骑士、虔信之徒，无人不为之疯狂。老天啊，连杯子本身都是纯金的！",
      "Saints, knights, and pietists. All of them are madmen. Dear God, even the chalice itself is made of pure gold!",
      sourceRule("每有5源石锭，所有我方单位的攻击速度+7"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder SPINACH_PACK = collectible(
      "spinach_pack",
      "168",
      "绿叶菜罐头",
      "所有干员技能触发后1秒内攻击力+60%",
      "All friendly units have +60% ATK for one second after their skill becomes active",
      "很多卡特斯相信吃下这盒罐头，他们就会变得力大无穷。出色的广告效果，业界经典成功案例。",
      "Many Cautus out there believe that popping the contents of this can into their mouths will grant them immense strength. The marketing's effectiveness is a classic success story in the industry.",
      sourceRule("所有干员技能触发后1秒内攻击力+60%"),
      Rarity.RARE
  );
  public static final CollectibleBuilder NO_073_SAFETY_REAGENT = collectible(
      "no_073_safety_reagent",
      "169",
      "073号安全试剂",
      "所有干员技能触发后1秒内攻击力+65%",
      "All friendly units have +65% ATK for one second after their skill becomes active",
      "严谨实验下的安全产物，绝对安全，不会爆炸，令人安心。",
      "A perfectly safe product of a cautiously controlled experiment. Perfectly safe, definitely will not explode, and keeps your mind at ease.",
      sourceRule("所有干员技能触发后1秒内攻击力+65%"),
      Rarity.RARE
  );
  public static final CollectibleBuilder WRATH_OF_SIRACUSANS = collectible(
      "wrath_of_siracusans",
      "170",
      "叙拉古人的愤怒",
      "所有干员技能触发后1秒内攻击力+100%",
      "All friendly units have +100% ATK for one second after their skill becomes active",
      "曾经开设在高卢首都的一家“正宗叙拉古餐厅”的招牌面食，用浓郁的巧克力酱与源石虫肝制成的酱汁以其独特风味备受高卢贵族好评。但是据说差一点引起叙拉古与高卢的外交冲突。",
      "The signature pasta dish of an 'authentic Siracusan restaurant' that once operated in the capital of Gaul. The dish's unique taste, and particularly its sauce, a blend of thick chocolate sauce and Originium Slug liver, earned the praise of many Gaulish nobles. Supposedly, though, this dish nearly caused a diplomatic crisis between Siracusa and Gaul.",
      sourceRule("所有干员技能触发后1秒内攻击力+100%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder CIVILIGHT_ETERNA = collectible(
      "civilight_eterna",
      "171",
      "“文明的存续”",
      "所有敌方单位受到的真实伤害+150%",
      "All enemies take +150% True damage.",
      "小小的黑色王冠。",
      "A small, black crown.",
      statFlat("造成的真实伤害+150%", "+150% True damage dealt", CombatStat::addTrueDamageBonus, 1.50),
      Rarity.EPIC
  );
  public static final CollectibleBuilder RIBBON_OF_HONOR = collectible(
      "ribbon_of_honor",
      "172",
      "荣耀绶带",
      "所有我方单位仅阻挡1名敌人时，攻击力+100%",
      "All friendly units have +100% ATK when blocking only one enemy",
      "曾有无数位演员出演这个角色，但永远救不了这个角色心目中的挚友。",
      "Numerous actors have played this character, yet none of them were able to save the character's dearest friend.",
      sourceRule("所有我方单位仅阻挡1名敌人时，攻击力+100%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder LOYALITY = collectible(
      "loyality",
      "173",
      "“忠义”",
      "所有我方单位阻挡3名及以上敌人时，每秒回复150点生命",
      "All friendly units recover 150 HP per second when blocking three or more enemies",
      "何为忠，何为义。有一群人早在数十年前就已经给出答案。",
      "What is loyalty, and what is righteousness? A group of people found the answer several decades ago.",
      sourceRule("所有我方单位阻挡3名及以上敌人时，每秒回复150点生命"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder END_OF_TIMES = collectible(
      "end_of_times",
      "174",
      "“时光之末”",
      "仅一次，在非区域最终战斗中失败时不结束探索，目标生命+1并继续下一步行动",
      "For one time only during any battle prior to the final battle of the area, gain 1 Life Point and continue onto the next step instead of concluding the exploration upon mission failure",
      "只要到达不了新的循环，过错就还有机会挽回。",
      "As long as a new cycle has not been reached, there is still time to fix your mistakes.",
      explorationRule("仅一次，在非区域最终战斗中失败时不结束探索，目标生命+1并继续下一步行动",
          power -> power.oneTimeFailureRecoveryObjectiveLife(1)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder ROYAL_ALLIANCE_TREATY = collectible(
      "royal_alliance_treaty",
      "175",
      "王庭盟约",
      "所有干员不再受到来自【萨卡兹】敌人的伤害",
      "All friendly units no longer receive any damage from Sarkaz enemies",
      "古老的萨卡兹王庭牺牲自己，获得了力量，由此物始，诸王庭止戈言和，同仇敌忾。",
      "The old Sarkaz courts sacrificed themselves to obtain their power. Ever since, the courts have come to terms with one another, banding together against their common enemy.",
      sourceRule("所有干员不再受到来自【萨卡兹】敌人的伤害"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder CONDENSED_SUPPRESSANT = collectible(
      "condensed_suppressant",
      "176",
      "浓缩抑制剂",
      "所有干员攻击【感染生物】时攻击力提升至150%并使其失去特殊能力3秒",
      "All friendly units increase their ATK to 150% and silence the enemy for 3 seconds when attacking [Infected Creatures]",
      "罗德岛用于抑制急性矿石病的医疗试剂，但在浓缩提炼后，反而会对感染部位造成不可逆转的伤害。",
      "A medical reagent developed by Rhodes Island to suppress acute Oripathy infections. However, further enrichment of the reagent's concentration will cause irreversible damage to the infection site.",
      sourceRule("所有干员攻击【感染生物】时攻击力提升至150%并使其失去特殊能力3秒"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder URSUS_CHACHEK = collectible(
      "ursus_chachek",
      "177",
      "乌萨斯弯刀",
      "所有敌方单位的攻击力、防御力、生命+40%",
      "All enemy units have +40% ATK, DEF, and Max HP",
      "钢铁的洪流在荒芜的土地上疾驰，远方传来不容忤逆的绝对意志，乌萨斯，是我双手的延伸。",
      "The currents of steel and iron gush forth on the barren plains. From afar surge an inviolable, absolute will. Wheresoever my hands reach, there stands Ursus.",
      sourceRule("所有敌方单位的攻击力、防御力、生命+40%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder URSUS_CHACHEK_REFORGED = collectible(
      "ursus_chachek_reforged",
      "178",
      "乌萨斯弯刀（重铸）",
      "所有敌方单位的攻击力、防御力、生命+35%，每进入新的一层额外+10%，完成紧急作战时-5%（最低35%）",
      "All enemy units gain +35% ATK, DEF, and Max HP, and gain an additional +10% to these three stats upon entering a new floor, but clearing an Emergency Op reduces these stats by 5% (minimum 35%)",
      "钢铁的洪流在荒芜的土地上疾驰，远方传来不容忤逆的绝对意志，乌萨斯，是我双手的延伸。",
      "The currents of steel and iron gush forth on the barren plains. From afar surge an inviolable, absolute will. Wheresoever my hands reach, there stands Ursus.",
      sourceRule("所有敌方单位的攻击力、防御力、生命+35%，每进入新的一层额外+10%，完成紧急作战时-5%（最低35%）"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder VICTORIA_CROWN = collectible(
      "victoria_crown",
      "179",
      "维多利亚王冠",
      "所有敌方单位的攻击力、防御力、生命+30%，且所有领袖单位还会攻击力、防御力+15%，生命+30%",
      "All enemy units have +30% ATK, DEF, and Max HP; Boss units gain an additional +15% ATK, +15% DEF, and +30% Max HP",
      "当最后一位统治维多利亚的阿斯兰被推上断头台时，愤怒的群众一拥而上，踩踏着过去的君权。",
      "When the last Aslan to rule Victoria was sent to the guillotine, the angry masses rushed forward to trample his reign into dust.",
      sourceRule("所有敌方单位的攻击力、防御力、生命+30%，且所有领袖单位还会攻击力、防御力+15%，生命+30%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder VICTORIA_CROWN_REFORGED = collectible(
      "victoria_crown_reforged",
      "180",
      "维多利亚王冠（重铸）",
      "所有敌方单位的攻击力、防御力、生命+35%，所有领袖单位还会攻击力、防御力+20%，生命+50%，编入所有职业的干员时领袖加成减半",
      "All enemy units have +35% ATK, DEF, and Max HP; Leader enemies gain an additional +20% ATK, DEF and +50% Max HP, but these leader bonuses are halved when your squad has Operators from all 8 Classes",
      "当最后一位统治维多利亚的阿斯兰被推上断头台时，愤怒的群众一拥而上，踩踏着过去的君权。",
      "When the last Aslan to rule Victoria was sent to the guillotine, the angry masses rushed forward to trample his reign into dust.",
      sourceRule("所有敌方单位的攻击力、防御力、生命+35%，所有领袖单位还会攻击力、防御力+20%，生命+50%，编入所有职业的干员时领袖加成减半"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder LEITHANIEN_SCEPTRE = collectible(
      "leithanien_sceptre",
      "181",
      "莱塔尼亚权杖",
      "所有敌方单位的攻击力、防御力、生命+30%，且每进入一个新节点后，失去1目标生命（最多降至1）",
      "All enemy units have +30% ATK, DEF, and Max HP; Entering a new node decreases Life Point by 1 (cannot be reduced beneath 1 this way)",
      "曾几何时，巫王的权杖上流淌着白天与黑夜。",
      "It was not long ago that the Witch King's scepter flowed with daylight and the night sky.",
      sourceRule("所有敌方单位的攻击力、防御力、生命+30%，且每进入一个新节点后，失去1目标生命（最多降至1）"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder LEITHANIEN_SCEPTRE_REFORGED = collectible(
      "leithanien_sceptre_reforged",
      "182",
      "莱塔尼亚权杖（重铸）",
      "所有敌方单位的攻击力、防御力、生命+35%，进入节点时目标生命-1（最低降至1），关卡生命低于3时我方部署费用+2，技力回复速度-20%",
      "All enemy units have +35% ATK, DEF, and Max HP; Entering a new node decreases Life Point by 1 (cannot be reduced beneath 1 this way); When Life Point is below 3, all units have DP Cost + 2 and SP recovery rate -20%",
      "曾几何时，巫王的权杖上流淌着白天与黑夜。",
      "It was not long ago that the Witch King's scepter flowed with daylight and the night sky.",
      sourceRule("所有敌方单位的攻击力、防御力、生命+35%，进入节点时目标生命-1（最低降至1），关卡生命低于3时我方部署费用+2，技力回复速度-20%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder GAUL_MANTLE = collectible(
      "gaul_mantle",
      "183",
      "高卢长袍",
      "所有敌方单位的攻击力、防御力、生命+25%，且招募4星及以上干员时希望消耗+1",
      "All enemy units have +25% ATK, DEF, and Max HP; 4-star and above Operators cost +1 Hope when recruited",
      "高卢皇帝的遗物，威权与盛世的象征。",
      "An artifact left behind by the late Gaulish emperor. A symbol of power and glory.",
      sourceRule("所有敌方单位的攻击力、防御力、生命+25%，且招募4星及以上干员时希望消耗+1"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder GAUL_MANTLE_REFORGED = collectible(
      "gaul_mantle_reforged",
      "184",
      "高卢长袍（重铸）",
      "所有敌方单位的攻击力、防御力、生命+30%，在奇数层招募4星以上干员时希望消耗+2，偶数层晋升干员希望消耗+2",
      "All enemy units have +30% ATK, DEF, and Max HP; 4-star and above Operators cost +2 Hope to recruit on odd-numbered floors, and +2 Hope to promote on even-numbered floors",
      "高卢皇帝的遗物，威权与盛世的象征。",
      "An artifact left behind by the late Gaulish emperor. A symbol of power and glory.",
      sourceRule("所有敌方单位的攻击力、防御力、生命+30%，在奇数层招募4星以上干员时希望消耗+2，偶数层晋升干员希望消耗+2"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HALF_REFINED_DIAMOND = collectible(
      "half_refined_diamond",
      "185",
      "半洗孤钻",
      "所有敌方单位的攻击力、防御力、生命+25%，且战斗获得的指挥经验-50%",
      "All enemy units have +25% ATK, DEF, and Max HP; Gain -50% Command EXP from battles",
      "一半闪耀着艺术的光辉，一半浸染着猩红的疯癫，这才是剧团长梦想中的“傀影”。",
      "Half of it shines radiantly in the name of art, while the other half is stained in a madder red color that signifies madness. This is the 'Phantom' that the troupe leader has always dreamt of.",
      sourceRule("所有敌方单位的攻击力、防御力、生命+25%，且战斗获得的指挥经验-50%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HALF_REFINED_DIAMOND_REFORGED = collectible(
      "half_refined_diamond_reforged",
      "186",
      "半洗孤钻（重铸）",
      "所有敌方单位的攻击力、防御力、生命+30%，战斗获得的指挥经验-70%；险路恶敌中获得的指挥经验变为15倍",
      "All enemy units have +30% ATK, DEF, and Max HP; Gain -70% Command EXP from battles; Dreadful Foe nodes grant 15x Command EXP",
      "一半闪耀着艺术的光辉，一半浸染着猩红的疯癫，这才是剧团长梦想中的“傀影”。",
      "Half of it shines radiantly in the name of art, while the other half is stained in a madder red color that signifies madness. This is the 'Phantom' that the troupe leader has always dreamt of.",
      sourceRule("所有敌方单位的攻击力、防御力、生命+30%，战斗获得的指挥经验-70%；险路恶敌中获得的指挥经验变为15倍"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder SIGIL_OF_TRAGODIA = collectible(
      "sigil_of_tragodia",
      "187",
      "酒神的印记",
      "所有敌方单位的攻击力、防御力、生命+30%，且可同时部署人数-2",
      "All enemy units have +30% ATK, DEF, and Max HP; Deployment Limit -2",
      "猩红剧团内随处可见的图案，将剧团长和他对艺术的渴求深深烙印进每个人的脑海中。",
      "A symbol that can be seen everywhere within the Crimson Troupe. It burns the troupe leader and his pursuit for the arts into the eyes of all those who lay their eyes upon it.",
      sourceRule("所有敌方单位的攻击力、防御力、生命+30%，且可同时部署人数-2"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder SIGIL_OF_TRAGODIA_REFORGED = collectible(
      "sigil_of_tragodia_reforged",
      "188",
      "酒神的印记（重铸）",
      "所有敌方单位的攻击力、防御力、生命+35%，可同时部署人数-3，每次进入幕间余兴时可同时部署人数+2",
      "All enemy units have +35% ATK, DEF, and Max HP; Deployment Limit -3, but gain Deployment Limit +2 whenever you enter the Downtime Recreation node",
      "猩红剧团内随处可见的图案，将剧团长和他对艺术的渴求深深烙印进每个人的脑海中。",
      "A symbol that can be seen everywhere within the Crimson Troupe. It burns the troupe leader and his pursuit for the arts into the eyes of all those who lay their eyes upon it.",
      explorationRule("所有敌方单位的攻击力、防御力、生命+35%，可同时部署人数-3，每次进入幕间余兴时可同时部署人数+2", power -> power.deploymentLimit(2)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder PLAYWRIGHTS_MANUSCRIPT_VICTORIA = collectible(
      "playwrights_manuscript_victoria",
      "189",
      "剧作家手稿：维多利亚",
      "所有敌方单位的攻击力、防御力和生命+10%，下次招募干员时希望消耗+1（招募后效果消失）",
      "All enemy units have +10% ATK, DEF, and Max HP; Operators cost +1 Hope to recruit (effect lasts for 1 recruit)",
      "“菲林的王冠四分五裂——”",
      "'The Feline's crown shatters into pieces—'",
      sourceRule("所有敌方单位的攻击力、防御力和生命+10%，下次招募干员时希望消耗+1（招募后效果消失）"),
      Rarity.RARE
  );
  public static final CollectibleBuilder PLAYWRIGHTS_MANUSCRIPT_URSUS = collectible(
      "playwrights_manuscript_ursus",
      "190",
      "剧作家手稿：乌萨斯",
      "所有敌方单位的攻击力、防御力和生命+10%，每次进入非战斗节点时失去1源石锭",
      "All enemy units have +10% ATK, DEF, and Max HP; Lose 1 Originium Ingot every time you enter a non-combat node",
      "“乌萨斯的宴会戛然而止——”\n",
      "'The Ursus's feast comes to an abrupt stop—'\n",
      sourceRule("所有敌方单位的攻击力、防御力和生命+10%，每次进入非战斗节点时失去1源石锭"),
      Rarity.RARE
  );
  public static final CollectibleBuilder PLAYWRIGHTS_MANUSCRIPT_LEITHANIEN = collectible(
      "playwrights_manuscript_leithanien",
      "191",
      "剧作家手稿：莱塔尼亚",
      "所有敌方单位的攻击力、防御力和生命+10%，部署费用低于99时我方攻击力-10%",
      "All enemy units have +10% ATK, DEF, and Max HP; All allied units have -10% ATK when DP is below 99",
      "“卡普里尼的高塔轰然倒塌——”",
      "'The Caprinae's spire collapses—'",
      sourceRule("所有敌方单位的攻击力、防御力和生命+10%，部署费用低于99时我方攻击力-10%"),
      Rarity.RARE
  );
  public static final CollectibleBuilder PLAYWRIGHTS_MANUSCRIPT_GAUL = collectible(
      "playwrights_manuscript_gaul",
      "192",
      "剧作家手稿：高卢",
      "所有敌方单位的攻击力、防御力和生命+10%，每场战斗首次损失关卡生命时变为2倍",
      "All enemy units have +10% ATK, DEF, and Max HP and the bonus is doubled when you lose Life Points for the first time in each battle",
      "“黎博利的砖石化为齑粉——”",
      "'The Liberi's bricks turn into dust—'",
      sourceRule("所有敌方单位的攻击力、防御力和生命+10%，每场战斗首次损失关卡生命时变为2倍"),
      Rarity.RARE
  );
  public static final CollectibleBuilder RIGHT_EYE_OF_THE_NATATOR = collectible(
      "right_eye_of_the_natator",
      "193",
      "“游禽的右眼”",
      "所有近卫和狙击干员的攻击力+15%，所有辅助和术师干员的攻击力-5%",
      "Guard and Sniper Operators have +15% ATK, but Supporter and Caster Operators have -5% ATK",
      "高卢皇后冠冕的一部分，现存于伦蒂尼姆皇家博物馆。在得知皇帝逝世的消息后，这位皇后决定同敌人战至最后一刻——为了国家，也为了她离世的爱人。",
      "One half of the Gaulish empress's crown is now housed in the Royal Museum of Londinium. When she learned of the death of the emperor, the empress consort was determined to fight her enemies to the end—for her country, and for her departed beloved.",
      runtime(percent(CombatStat::multiplyAttack, 0.15)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder LEFT_EYE_OF_THE_NATATOR = collectible(
      "left_eye_of_the_natator",
      "194",
      "“游禽的左眼”",
      "所有辅助和术师干员的攻击力+15%，所有近卫和狙击干员的攻击力-5%",
      "Supporter and Caster Operators have +15% ATK, but Guard and Sniper Operators have -5% ATK",
      "高卢皇后冠冕的一部分，现存于莱塔尼亚女皇图书馆。皇后遣走所有侍从，宽恕一切罪犯，随后独自坐在曾属于丈夫的宝座上，等待着入侵者的到来——她决心死于源石结晶，而非敌人的剑刃。",
      "One half of the Gaulish empress's crown is now stored housed in the Leithanian Queen Library. The empress dismissed all her attendants, granted clemency to all criminals, and sat solemnly alone on the throne that was once her husband's, awaiting the invaders—She was determined to die not by at hands of her enemies, but under the effects of Originium crystals.",
      runtime(percent(CombatStat::multiplyAttack, 0.15)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder MAGNIFICENT_VISAGE = collectible(
      "magnificent_visage",
      "195",
      "“华美容貌”",
      "每进入一个非战斗节点，获得希望+1",
      "Gain +1 Hope upon entering a noncombat node.",
      "只见其面容便能充满希望的确是件幸事——即使知晓其灾祸本质，亦是如此。",
      "It's a good thing that a glimpse of her face feels you with hope—and especially so if you knew of the disaster.",
      explorationRule("每进入一个非战斗节点，获得希望+1", power -> power.hopePerNonCombatNode(1)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BLADEMACE = collectible(
      "blademace",
      "196",
      "“剑锤”",
      "所有干员的部署费用+5，但攻击力、防御力和生命+10%",
      "All Operators have +5 DP Cost, but gain +10% ATK, DEF, and Max HP",
      "插在巨大石头上的剑，到底是谁这么无聊啊。",
      "A sword lodged in a stone. Was someone bored enough to do this?",
      runtime(percent(CombatStat::multiplyMaxHealth, 0.1)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BROKENBLADE = collectible(
      "brokenblade",
      "197",
      "“断剑”",
      "所有干员的生命-30%，但再部署时间-50%",
      "All Operators have -30% Max HP, but have -50% Redeployment Time",
      "一把折断的剑，姑且能用……大概吧……",
      "A broken sword. It's usable... probably...",
      sourceRule("所有干员的生命-30%，但再部署时间-50%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder FAMILIAR_SCULPTURE = collectible(
      "familiar_sculpture",
      "198",
      "眼熟的雕像",
      "所有敌方单位的攻击速度-15",
      "All enemy units have -15 ASPD.",
      "容貌相近，气质相异，你或许见过这个人？",
      "A familiar face but a different vibe. Perhaps you have met this person?",
      enemySpawnStat("敌方生成时攻击速度-15", "-15 enemy ASPD on spawn", (enemy, stats) -> stats.addAttackSpeed(-15.0)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder PROOF_OF_FRIENDSHIP = collectible(
      "proof_of_friendship",
      "199",
      "友谊之证",
      "战斗掉落的源石锭+30%",
      "+30% Originium Ingot drops from battle.",
      "一些古老的血魔家族还沿用旧时的繁琐礼节。他们交付出高昂的友谊，并不允许他人随意冒犯。",
      "Some old Vampire families are still concerned with formalities and etiquette. When they extend to others their exalted friendship, they do not allow it to be sullied.",
      explorationRule("战斗掉落的源石锭+30%", power -> power.battleOriginiumIngotMultiplier(1.3)),
      Rarity.RARE
  );
  public static final CollectibleBuilder JET_BLACK_DANCE_SHOES = collectible(
      "jet_black_dance_shoes",
      "200",
      "漆黑的舞鞋",
      "可以在高台部署近战位干员",
      "Can deploy Melee Operators on High Ground tiles",
      "哥伦比亚的舞者踩踏大地，铿锵之声响亮悦耳。",
      "The Columbian dancer's steps are firm and strong, her taps reverberating loudly in the air as she strikes the ground.",
      sourceRule("可以在高台部署近战位干员"),
      Rarity.RARE
  );
  public static final CollectibleBuilder PURE_WHITE_DANCE_SHOES = collectible(
      "pure_white_dance_shoes",
      "201",
      "洁白的舞鞋",
      "可以在低地部署远程位干员",
      "Can deploy Ranged Operators on Low Ground tiles",
      "乌萨斯的舞者轻盈灵动，于天地间尽情舒展身姿。",
      "The Ursus dancer's steps are swift and elegant, her body moving as she wills.",
      sourceRule("可以在低地部署远程位干员"),
      Rarity.RARE
  );
  public static final CollectibleBuilder BLADEDANCE = collectible(
      "bladedance",
      "202",
      "“刀舞”",
      "战斗开始时，使随机一名干员在本局内攻击力+100%，生命+100%",
      "When the battle begins, a random Operator will have +100% ATK and +100% Max HP within the operation",
      "“猩红剧团”成员的遗物。“刀舞”是一位沉默寡言的萨尔贡舞者，也是一位武艺高超的战士，她对剧团无比忠诚，即便是在猩红剧团内也少有人知晓她的黑暗过去。",
      "An artifact left behind by a 'Crimson Troupe' member. 'Bladedance' was a quiet Sargon dancer as well as a warrior of spectacular martial prowess, and her loyalty to the troupe is unparalleled. Even among the members of the troupe, there are few people who know of her dark past.",
      sourceRule("战斗开始时，使随机一名干员在本局内攻击力+100%，生命+100%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder WHITEFLOWER = collectible(
      "whiteflower",
      "203",
      "“白英花”",
      "战斗开始时，打乱所有干员的部署费用",
      "When the battle begins, randomize all allied operators' Deployment Cost",
      "“猩红剧团”成员的遗物。“白英花”这个艺名属于一位著名的莱塔尼亚歌剧演员，据说这位被称作“莱塔尼亚高塔上的白英花”的女演员与多位高塔贵族保持着微妙的私人关系，甚至尝试以此挑起贵族之间的争斗。",
      "An artifact left behind by a 'Crimson Troupe' member. The stage name 'White Solanum' belonged to a famous Leithanian opera actress. Supposedly, the actress revered as the 'Whiteflower that stands atop the high tower' had complex relationships with many of the tower's nobles, and she even tried to spark a few scuffles among them.",
      sourceRule("战斗开始时，打乱所有干员的部署费用"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder SHADOW = collectible(
      "shadow",
      "204",
      "“影子”",
      "提高招募券中临时招募出现的概率",
      "Increase the probability of Temporary Recruitment when using Recruitment Voucher",
      "“猩红剧团”成员的遗物。据说在加入剧团之前，“影子”是一位著名的叙拉古舞台魔术师，以自己高超的幻术而出名，然而他被指控在表演中使用源石技艺暗杀了多位家族成员。",
      "An artifact left behind by a 'Crimson Troupe' member. The rumor has it that, before he joined the troupe, the 'Shadow' was a Siracusan stage caster famous for his superb illusionary techniques. It is also said that he killed many members of the family during his performances with his Originium Arts.",
      sourceRule("提高招募券中临时招募出现的概率"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder FLOWERS_OF_QUINDE = collectible(
      "flowers_of_quinde",
      "205",
      "《坎德之花》",
      "所有我方单位每秒回复10点生命",
      "All friendly units recover 10 HP every second",
      "雕刻在石板上的叙事诗，是八百年前高卢一位著名的诗人留下的巨作。尽管后世的文学家对这首诗有着相当高的评价，但是依据历史记载，这位诗人却因为自己身形的臃肿与长相的丑陋而被人嘲笑。",
      "An epic narrative poem etched on a stone tablet. It was written eight hundred years ago by a famous Gaulish poet, but although modern literary scholars hold the poem in extremely high regard, historical records show that he was often ridiculed by his contemporaries for his obese figure and unattractive facial features.",
      runtime(regenerationFlat(10.0)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder NUMBNESS_AND_OBSCENITY = collectible(
      "numbness_and_obscenity",
      "206",
      "《麻木与庸俗》",
      "所有我方单位法术抗性+5",
      "All friendly units have +5 RES",
      "一件著名的艺术品，两百年前莱塔尼亚疯狂雕塑家的遗作。这位雕塑家以才华横溢与参与多起残忍的命案而为人所知。但是依据历史记载，他生前并没有多少人关注他的作品，而是因为自己秀美的长相而被人追捧。",
      "A famous art piece and the final piece created by an insane Leithanian sculptor two hundred years ago. The sculptor was known for his extraordinary talent and his participation in multiple cruel homicides. However, historical records show that few people took notice of his work. He was instead known for his features and beauty.",
      runtime(flat(CombatStat::addResistance, 5)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BEAUTY_AND_UGLINESS_IN_THE_AGE_OF_TERRA = collectible(
      "beauty_and_ugliness_in_the_age_of_terra",
      "207",
      "《世间的美与丑》",
      "仅一次，在非区域最终战斗中失败时不结束探索，目标生命+10并继续下一步行动",
      "For one time only during any battle prior to the final battle of the area, gain 10 Life Points and continue onto the next step instead of concluding the exploration upon mission failure",
      "十五年前出版于维多利亚的传奇书籍，这本书简单地记述了作者对审美的理解，记载了大量文学、艺术、音乐相关的知识与历史。该书的内容涵盖古今，通俗易懂，被誉为“本世代的艺术大典”。然而据说该书的作者只是某个小剧团的剧团长，数年前被一个代号为“血钻”的刺客谋害。",
      "A legendary book published in Victoria fifteen years ago. The book describes the author's appreciation of aesthetics in simple terms, and it contains a comprehensive record of literary, artistic and musical knowledge and history. The book covers both the past and present and is an easy read, with many honoring it as the 'great art encyclopedia of our generation.' Yet it is rumored that the book's author was the leader of a certain small troupe and was murdered a few years ago by a hitman codenamed 'Blood Diamond.'",
      explorationRule("仅一次，在非区域最终战斗中失败时不结束探索，目标生命+10并继续下一步行动",
          power -> power.oneTimeFailureRecoveryObjectiveLife(10)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder WORN_OUT_PUPPET = collectible(
      "worn_out_puppet",
      "208",
      "残破的玩偶",
      "让探索走向不同的结局",
      "The adventure will head towards a different ending",
      "“他说，他受够了，他再也不愿做他人手中的牵线木偶。”",
      "'He said he has had enough. He will be a puppet no longer.'",
      sourceRule("让探索走向不同的结局"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder USELESS_SCISSORS = collectible(
      "useless_scissors",
      "209",
      "无用的剪刀",
      "立即获得源石锭+11，希望+3",
      "Immediately adds +11 Originium Ingots and +3 Hope",
      "这把剪刀被使用了太久太久，久到已经没有办法再剪断任何有形之物，久到能将许多无形束缚全部剪碎。",
      "This pair of scissors has been used for far, far too long. So long that it can no longer cut anything tangible, and so long that it can cut all things intangible to shreds.",
      explorationRule("立即获得源石锭+11，希望+3", power -> power.originiumIngots(11)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BLANK_SUICIDE_NOTE = collectible(
      "blank_suicide_note",
      "210",
      "空白遗书",
      "使【伤心的大锁】自愈能力失效",
      "Neutralizes Big Sad Lock's Self-Healing abilities",
      "摆脱那个试图操纵他的疯子，就是他唯一的心愿。现在，他已经如愿了。",
      "His only wish was to escape the madman who wanted control over him. Now, he got his wish.",
      sourceRule("使【伤心的大锁】自愈能力失效"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder MANTLE_OF_THE_WRONGLY_CONDEMNED = collectible(
      "mantle_of_the_wrongly_condemned",
      "211",
      "替罪领巾",
      "所有我方单位的部署费用+2",
      "All friendly units have +2 DP Cost.",
      "疯癫的高塔术师主办了一场特殊的宴会，所有碍于体面而出现在会客大厅的宾客，都是幕布揭开那一瞬间的见证者。",
      "The crazed Caster of the tower organized a special banquet. All the guests who were present at the reception hall out of courtesy became witnesses when the curtain rose.",
      sourceRule("所有我方单位的部署费用+2"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder STAND_IN_ACTOR = collectible(
      "stand_in_actor",
      "212",
      "替补演员",
      "所有我方单位的攻击力、防御力、生命+3%",
      "All friendly units have +3% ATK, DEF, and Max HP",
      "剧作家始终未认可由他揭幕的这几场戏。但是没关系，报幕人并不气馁，他的手中从不缺少替补演员。",
      "The playwright never approved of his inauguration of these shows. No matter, though. The master of ceremonies is not discouraged. He has as many understudies at his disposal.",
      runtime(statSet(percent(CombatStat::multiplyAttack, 0.03), percent(CombatStat::multiplyDefense, 0.03), percent(CombatStat::multiplyMaxHealth, 0.03))),
      Rarity.EPIC
  );
  public static final CollectibleBuilder ABRUPT_REALIZATION = collectible(
      "abrupt_realization",
      "213",
      "恍悟",
      "所有我方单位的部署费用-3，攻击力、防御力、生命+5%，让探索走向不同的结局",
      "All friendly units have -3 DP Cost and gain +5% ATK, DEF, and Max HP. The adventure will head towards a different ending",
      "巫王曾经的追随者已向艺术献出其全部。血肉、骨骼、信仰、心灵。",
      "The Witch King's followers have given everything for the arts. Their flesh, their blood, their beliefs, and their souls.",
      runtime(statSet(percent(CombatStat::multiplyAttack, 0.05), percent(CombatStat::multiplyDefense, 0.05), percent(CombatStat::multiplyMaxHealth, 0.05))),
      Rarity.EPIC
  );
  public static final CollectibleBuilder DANCE_OF_THE_CONDEMNED = collectible(
      "dance_of_the_condemned",
      "214",
      "死囚之舞",
      "所有干员的再部署时间-10%",
      "Operator redeployment time -10%",
      "是他们自己将自己关在这里。啊，多么安静，再也没有人能打扰这场创作了。",
      "They have trapped themselves in this place. Ah, peace and quiet. No one can interrupt this creation now.",
      sourceRule("所有干员的再部署时间-10%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder ACT_1 = collectible(
      "act_1",
      "215",
      "初幕",
      "希望+3，让探索走向不同的结局",
      "Hope +3, leads the exploration toward a different conclusion",
      "这是第一场戏的落幕，是年轻人曾经为自己设计的第一个结局。在焚毁所有珍视之物的火焰之中，落魄的主人公蘸着仇敌的鲜血，写下复仇的最后一幕。",
      "The end of the first act, the first ending that the young man wrote for himself. In the midst of the fire that consumed all that he treasured, the hero sips his foe's blood and writes the last scene of his vendetta.",
      explorationRule("希望+3，让探索走向不同的结局", power -> power.hope(3)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder TODAYS_MENU = collectible(
      "todays_menu",
      "216",
      "今日菜谱",
      "所有我方单位受到的治疗和生命回复效果+40%",
      "All allied units have +40% healing effectiveness",
      "剧团厨师长每顿都做不同的菜品，所有人被剧团长严令不准打听食物来源。",
      "The troupe's chef never serves the same dish twice. The members of the troupe have been warned to never ask where the ingredients came from.",
      statFlat("受到的治疗与生命回复效果+40%", "+40% healing and health regeneration received", CombatStat::addHealingAndHealthRegenerationBonus, 0.40),
      Rarity.EPIC
  );
  public static final CollectibleBuilder INTOXICATED_HYMNOI = collectible(
      "intoxicated_hymnoi",
      "217",
      "“迷醉荷谟伊”",
      "所有我方单位每秒回复2%的最大生命值",
      "All allied units recover 2% of Max HP per second",
      "只要闻到一点，帕拉斯的眼角便会湿润，也不知道莱娜在香水里加了什么魔法......",
      "One sniff brings tears to Pallas's eyes. What spell has Lena cast on this perfume?",
      runtime(regenerationPercentage(0.02)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder EMPRESSES_WISH = collectible(
      "empresses_wish",
      "218",
      "女皇之愿",
      "所有我方单位获得15%物理与法术闪避",
      "All allied units have +15% Physical and Arts Dodge",
      "两位女皇如同朝夜悬于高塔之上，当晨昏相通，意见一致，莱塔尼亚便再无异议。",
      "The Twin Empresses tower above. When dawn and dusk are one, there shall be no more dissent in Leithanien.",
      effect("物理与法术伤害闪避率+15%", "+15% Physical and Arts damage evasion", statSet(flat(CombatStat::addPhysicalDamageEvasionRate, 0.15), flat(CombatStat::addMagicDamageEvasionRate, 0.15))),
      Rarity.EPIC
  );
  public static final CollectibleBuilder VICTORY_HORN = collectible(
      "victory_horn",
      "219",
      "凯旋号角",
      "敌人受到的一次性晕眩、寒冷、冰冻等异常效果影响时间提高100%",
      "Duration of single instance status effects such as stun, cold or freeze is increased by 100%",
      "吹响我们的号角，唤起我们的呼号，让敌人在震天咆哮中落荒而逃！",
      "Blow the horn, cry out, and scatter our foes with its roar!",
      statFlat("对敌方施加的异常状态持续时间+100%", "+100% negative status duration applied to enemies", CombatStat::addEnemyStatusDurationBonus, 1.00),
      Rarity.RARE
  );
  public static final CollectibleBuilder FLASH_CAMERA = collectible(
      "flash_camera",
      "220",
      "高闪相机",
      "敌人受到的一次性晕眩、寒冷、冰冻等异常效果影响时间提高110%",
      "Duration of single instance status effects such as stun, cold or freeze is increased by 110%",
      "用这部相机拍摄的照片冲洗出来后基本上只有一片花白，隐约能够看到受害者扭曲的表情。",
      "Every photo shot by this camera is washed out in white, with only the victim's twisted expression faintly visible.",
      statFlat("对敌方施加的异常状态持续时间+110%", "+110% negative status duration applied to enemies", CombatStat::addEnemyStatusDurationBonus, 1.10),
      Rarity.RARE
  );
  public static final CollectibleBuilder THERAPY_TAPE = collectible(
      "therapy_tape",
      "221",
      "精神治疗录像带",
      "敌人受到的一次性晕眩、寒冷、冰冻等异常效果影响时间提高150%",
      "Duration of single instance status effects such as stun, cold or freeze is increased by 150%",
      "这是他曾经多次阻止你听的，他说这很危险，他说你不该逗留——现在你明白了吗？你在听他唱歌。",
      "He tried to stop you from listening. He said it was dangerous. He said you should not stay. Do you understand now? You are listening to his song.",
      statFlat("对敌方施加的异常状态持续时间+150%", "+150% negative status duration applied to enemies", CombatStat::addEnemyStatusDurationBonus, 1.50),
      Rarity.EPIC
  );
  public static final CollectibleBuilder ROYAL_BROOCH = collectible(
      "royal_brooch",
      "222",
      "皇族金胸针",
      "所有我方单位的法术抗性+15，且部署后抵挡一次伤害",
      "All allied units have RES +15, and gain one-time dodge after deployment",
      "高卢皇族用于彰显身份的装饰品，由黄金打造，镶嵌了大量昂贵的固化源石结晶。实际上这种胸针不单纯是装饰，许多胸针安装了搭载防御性法术的通用源石回路。",
      "An ornament worn by Gaulish royalty, made from gold and inlaid with expensive Originium crystals. They are more than decoration: many of these brooches hide Originium circuits loaded with defensive Arts.",
      sourceRule("所有我方单位的法术抗性+15，且部署后抵挡一次伤害"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder TEAR_OF_THE_DEPARTED = collectible(
      "tear_of_the_departed",
      "223",
      "“逝者垂泪”",
      "所有干员受到晕眩、寒冷、冰冻等负面状态影响时，获得50%庇护",
      "All Operators gain 50% Sanctuary when affected by a negative status such as stun, cold or freeze",
      "这枚古典挂坠以真诚和毅力铸就，在此之后，Whitesmith的锻炉再也没能燃起火焰。",
      "Forged with honesty and perseverance. No flame has burned in Whitesmith's furnace ever since.",
      sourceRule("所有干员受到晕眩、寒冷、冰冻等负面状态影响时，获得50%庇护"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder OLD_FAN = collectible(
      "old_fan",
      "224",
      "老蒲扇",
      "战斗编队中每有一个职业不同的干员，所有干员的攻击力+10%",
      "All Operators gain ATK +10% for every different type of Operator class in the squad",
      "遇上连天阴雨，大炎的老天师便会向亲友抱怨自己膝盖受不得湿寒，然后，随手扇动他的蒲扇......云开雾散，雨水退避，天气就此放晴。这一手，下回还得教教他那在大理寺当差的小徒弟。",
      "When it rains for several days on end, the old Yanese Tianshi complains to his friends about the pain in his knees, before casually waving his fan and driving away the rainclouds. It is about time he taught this trick to his young apprentice, currently working at the Central Judicial Office.",
      sourceRule("战斗编队中每有一个职业不同的干员，所有干员的攻击力+10%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder NACHZEHRERS_CANE = collectible(
      "nachzehrers_cane",
      "225",
      "食腐者手杖",
      "所有我方单位受到的治疗溢出时，周围敌人会受到相当于该次溢出治疗量的法术伤害",
      "When an allied unit is overhealed, nearby enemies take Arts damage equal to the overheal amount",
      "食腐者吞吃生命，也利用生命。",
      "Nachzehrer devours life and uses it.",
      sourceRule("所有我方单位受到的治疗溢出时，周围敌人会受到相当于该次溢出治疗量的法术伤害"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder ANGELINAS_INSPIRATION = collectible(
      "angelinas_inspiration",
      "226",
      "安洁莉娜的创想",
      "所有敌方单位移动速度-15%，且重量下降一个等级",
      "All enemy units have Movement Speed -15%, and their weight is reduced by one rank",
      "少女并不介意分享自己的源石技艺，她相信你会将之用在正确的地方。",
      "The girl has no qualms about sharing her Originium Arts. She believes that you will use it for the right reasons.",
      effect("敌方移动速度-15%，推拉结算时忽略1级重量", "Enemy Movement Speed -15%; ignore 1 weight rank for push/pull", stats -> stats.addEnemyMovementSpeedReduction(0.15).addEnemyWeightIgnore(1)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder SCOUTS_SCOPE = collectible(
      "scouts_scope",
      "227",
      "Scout的狙击镜",
      "所有敌人受到我方单位伤害时，距离伤害来源越远受到的伤害越高（最高100%）",
      "Enemies take increased damage from allied units as range increases (max 100%)",
      "锁定目标、维持精度、扣下扳机、摧毁敌人抵抗的意志，这一切都是为了那个更远大的未来。Scout已经没有机会看到了，或许你可以帮忙见证。",
      "Lock on, aim, fire, and destroy the enemy's will to resist, all in the name of a better future that Scout will never see. Maybe you can do it for him.",
      sourceRule("所有敌人受到我方单位伤害时，距离伤害来源越远受到的伤害越高（最高100%）"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder SILENCE = collectible(
      "silence",
      "228",
      "“噤声”",
      "所有我方干员技能未开启时60秒内攻击力逐渐提升至最高+60%，每次技能结束时失去该加成",
      "Allied Operators gradually gain ATK when not using a skill, up to a maximum of +60% after 60 seconds; resets when a skill expires",
      "在等候那即将到来的至高一刻时，演员捂住自己刺痛不止的喉咙，按住胸腔中翻滚的火焰，闭口不言。",
      "When the climax arrives, the actor grabs his stinging throat and holds back the roiling fire in his chest, all without a word.",
      sourceRule("所有我方干员技能未开启时60秒内攻击力逐渐提升至最高+60%，每次技能结束时失去该加成"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder FOCUS = collectible(
      "focus",
      "229",
      "“聚焦”",
      "每次部署我方单位时，对场上随机一个敌人及其周围造成3000点法术伤害",
      "Whenever you deploy a unit, deal 3000 Arts damage to a random enemy and the area around it",
      "聚光灯交汇于主角身上，演员发出最后的呼号，死亡突至，戏剧落幕。",
      "The stage lights focus on the hero. The actor gives one last dying cry, before the curtains are brought down by Death.",
      sourceRule("每次部署我方单位时，对场上随机一个敌人及其周围造成3000点法术伤害"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder RHODES_ISLAND_TACTICAL_TRANSCEIVER = collectible(
      "rhodes_island_tactical_transceiver",
      "230",
      "罗德岛战术电台",
      "战斗后掉落招募券时，增加一个可选项",
      "Gain an additional choice when Recruitment Vouchers are dropped after battle",
      "保持联络，支援马上抵达。",
      "Hang in there, help's on the way.",
      sourceRule("战斗后掉落招募券时，增加一个可选项"),
      Rarity.RARE
  );
  public static final CollectibleBuilder ASSAULT_CO_OP_SHARP_BLADE = collectible(
      "assault_co_op_sharp_blade",
      "231",
      "突击协议-利刃",
      "战斗编队中每有一名先锋和近卫干员，所有先锋和近卫干员的攻击力+8%",
      "Increases the ATK of Vanguard and Guard Operators by +8% for each Vanguard or Guard Operator in the combat squad.",
      "用最锋利的矛攻击最坚硬的盾，最后的结果肯定是盾碎了，相信我。",
      "Use the sharpest spear to strike the hardest shield. I guarantee you that the shield will break first, trust me.",
      sourceRule("战斗编队中每有一名先锋和近卫干员，所有先锋和近卫干员的攻击力+8%"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder ASSAULT_CO_OP_SKIRMISH = collectible(
      "assault_co_op_skirmish",
      "232",
      "突击协议-散兵",
      "战斗编队中每有一名先锋和近卫干员，所有先锋和近卫干员的防御力+10%",
      "Increases the DEF of Vanguard and Guard Operators by +10% for each Vanguard or Guard Operator in the combat squad.",
      "精巧的格挡技术才是保护自己身体的真髓。",
      "A sophisticated blocking technique is essential to protecting one's body.",
      sourceRule("战斗编队中每有一名先锋和近卫干员，所有先锋和近卫干员的防御力+10%"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder FORTIFICATION_CONTRACT_PHALANX = collectible(
      "fortification_contract_phalanx",
      "233",
      "堡垒协议-方阵",
      "战斗编队中每有一名重装和辅助干员，所有重装和辅助干员的攻击力+8%",
      "Increases the ATK of Defender and Supporter Operators by +8% for each Defender or Supporter Operator in the combat squad.",
      "如同巨舰碾过碎石。",
      "Like a huge ship running over rubble.",
      sourceRule("战斗编队中每有一名重装和辅助干员，所有重装和辅助干员的攻击力+8%"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder FORTIFICATION_CONTRACT_RESOLUTION = collectible(
      "fortification_contract_resolution",
      "234",
      "堡垒协议-固守",
      "战斗编队中每有一名重装和辅助干员，所有重装和辅助干员的防御力+10%",
      "Increases the DEF of Defender and Supporter Operators by +10% for each Defender or Supporter Operator in the combat squad.",
      "用最锋利的矛攻击最坚硬的盾，最后的结果肯定是矛断了，相信我。",
      "Use the hardest shield to block the sharpest spear. I guarantee you that the spear will break first, trust me.",
      sourceRule("战斗编队中每有一名重装和辅助干员，所有重装和辅助干员的防御力+10%"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder LONG_RANGE_CONTRACT_REMOTE_STRIKE = collectible(
      "long_range_contract_remote_strike",
      "235",
      "远程协议-遥击",
      "战斗编队中每有一名医疗和狙击干员，所有医疗和狙击干员的攻击力+8%",
      "Increases the ATK of Medic and Sniper Operators by +8% for each Medic or Sniper Operator in the combat squad.",
      "能保持距离解决的问题，为什么要凑脸上去？",
      "If you can solve the problem from a reasonable distance, why do you have to literally butt heads?",
      sourceRule("战斗编队中每有一名医疗和狙击干员，所有医疗和狙击干员的攻击力+8%"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder LONG_RANGE_CONTRACT_ASSASSINATION = collectible(
      "long_range_contract_assassination",
      "236",
      "远程协议-克敌",
      "战斗编队中每有一名医疗和狙击干员，所有医疗和狙击干员的攻击速度+6",
      "Increases the ASPD of Medic and Sniper Operators by +6 for each Medic or Sniper Operator in the combat squad.",
      "距离太远分不清敌我？那就先射一轮，没躲开的就是敌人。",
      "Can't tell friends from foes at this distance? Unload a volley at them then. The foes are the ones that fail to avoid it.",
      sourceRule("战斗编队中每有一名医疗和狙击干员，所有医疗和狙击干员的攻击速度+6"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder SABOTAGE_CO_OP_ELIMINATION = collectible(
      "sabotage_co_op_elimination",
      "237",
      "破坏协议-消除",
      "战斗编队中每有一名术师和特种干员，所有术师和特种干员的攻击力+8%",
      "Increases the ATK of Caster and Specialist Operators by +8% for each Caster or Specialist Operator in the combat squad.",
      "外部的毁灭与内部的瓦解。",
      "External destruction, internal disintegration.",
      sourceRule("战斗编队中每有一名术师和特种干员，所有术师和特种干员的攻击力+8%"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder SABOTAGE_CO_OP_SUPPRESSION = collectible(
      "sabotage_co_op_suppression",
      "238",
      "破坏协议-压制",
      "战斗编队中每有一名术师和特种干员，所有术师和特种干员的攻击速度+6",
      "Increases the ASPD of Caster and Specialist Operators by +6 for each Caster or Specialist Operator in the combat squad.",
      "不要在乎打得准不准，只要打得多，就是打得准！",
      "Don't worry about accuracy. Take enough shots, and you're guaranteed to land something!",
      sourceRule("战斗编队中每有一名术师和特种干员，所有术师和特种干员的攻击速度+6"),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder KINGS_NEW_LANCE = collectible(
      "kings_new_lance",
      "PCS01",
      "国王的新枪",
      "目标生命值为1时，所有我方单位的攻击速度+50",
      "When Life Point is 1, all friendly units have +50 ASPD",
      "绝境下的美好祈愿能带给人希望，即使那并不是真相。",
      "Beautiful wishes made in dire straits can bring hope, even if they are not necessarily the truth.",
      effect("每秒判断目标生命；为1时攻击速度+50", "Check Life Points every second; +50 ASPD while at 1", stats -> stats.addPerSecondConditionalEffect(current -> Double.compare(current.maxHealth(), 1.0) == 0 ? current.addAttackSpeed(50.0) : current)),
      Rarity.RARE
  );
  public static final CollectibleBuilder GUARD_CAP = collectible(
      "guard_cap",
      "PCS02",
      "近卫军帽",
      "在一局战斗中，所有干员每部署过一次就生命+25%",
      "For a single battle, all friendly units have +25% HP each time they are deployed",
      "“你我终会离开，但我们的国家，将长盛不衰。”",
      "'You and I will cease one day, but our country is everlasting.'",
      sourceRule("在一局战斗中，所有干员每部署过一次就生命+25%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder PAINFUL_HAPPINESS = collectible(
      "painful_happiness",
      "PCS03",
      "“苦痛的快乐”",
      "所有敌方单位的生命-15%，移动速度-15%",
      "All enemy units have -15% HP and Movement Speed",
      "矿石病在剧团内蔓延，有人倒下，有人哭泣，有人乐不可支，有人一心书写。年轻人在这时收到他的成年礼物，他即将唱响身为演员的最后一支歌。",
      "Oripathy is spreading among the troupe members. Some collapsed, some broke out crying, some were overjoyed, and some turned their attention to writing. It was at such a time that the young man received his coming-of-age present. He will sing his very last song as an actor.",
      enemySpawnStat("敌方生成时最大生命-15%；移动速度规则尚未实现", "-15% enemy maximum HP on spawn; movement speed rule pending", (enemy, stats) -> stats.multiplyMaxHealth(-0.15), "所有敌方单位移动速度-15%"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder CASTLES_OFFSPRING = collectible(
      "castles_offspring",
      "PCS04",
      "古堡的子嗣",
      "所有干员在场上停留100秒后防御力+300，法术抗性+30",
      "All friendly units have +300 DEF and +30 RES 100 seconds after deployment",
      "只要有足够时间，这个盒子便能成为另一座崭新的舞台。",
      "With enough time, this box will become a whole new stage.",
      sourceRule("所有干员在场上停留100秒后防御力+300，法术抗性+30"),
      Rarity.EPIC
  );
  public static final CollectibleBuilder SURGING_FEAST = collectible(
      "surging_feast",
      "PCS05",
      "涌动之餐",
      "所有我方单位受到的治疗和生命回复效果+30%，且受到的元素损伤-30%",
      "All friendly units have +30% healing effects and receive -30% Elemental Injury",
      "我们生来便学会摄取。别违抗欲求，顺从它。",
      "We've known how to indulge since birth. Do not resist your desires. Submit to them instead.",
      effect("受到的治疗与生命回复效果+30%，元素损伤减免30%", "+30% healing and health regeneration received and 30% Elemental Injury reduction", statSet(flat(CombatStat::addHealingAndHealthRegenerationBonus, 0.30), flat(CombatStat::addElementalDamageReduction, 0.30))),
      Rarity.EPIC
  );
  public static final CollectibleBuilder EVENTIDE_TERROR = collectible(
      "eventide_terror",
      "PCS06",
      "“夜骇”",
      "每次敌人进入保护目标点时，立刻对全场所有敌人造成5秒晕眩",
      "Deals Stun on all enemy units for 5 seconds every time an enemy unit enters the Protection Objective",
      "总有一个黑暗的日子，连光芒都无法带来救赎。而剧团长早已备好美酒，准备欣赏他人的绝望时刻。",
      "There will come a day of darkness that not even light can save. The leader of the troupe has prepared a fine wine to properly appreciate human despair.",
      sourceRule("每次敌人进入保护目标点时，立刻对全场所有敌人造成5秒晕眩"),
      Rarity.RARE
  );
  public static final CollectibleBuilder EXCESSIVENESS = collectible(
      "excessiveness",
      "PCS07",
      "“无度”",
      "每次敌人进入保护目标点时，立刻对全场所有敌人造成3000点真实伤害",
      "Deals 3000 True Damage on all enemy units every time an enemy unit enters the Protection Objective",
      "无光的宝石，如同剧团长本人永无止境的欲望。",
      "A gemstone without luster, just like the endless desires of the troupe's leader.",
      sourceRule("每次敌人进入保护目标点时，立刻对全场所有敌人造成3000点真实伤害"),
      Rarity.EPIC
  );
  public static final List<CollectibleBuilder> ALL = List.copyOf(Zinecraft.COLLECTIBLES.entries);

  static {
  }

  static {
    if (ALL.size() != EXPECTED_COUNT) {
      throw new IllegalStateException(
          "《傀影与猩红孤钻》藏品应注册 " + EXPECTED_COUNT + " 件，实际为 " + ALL.size() + " 件"
      );
    }
  }

  private ModCollectible() {
  }

  private static CollectibleBuilder collectible(
      String path,
      String orderId,
      String zhCn,
      String originalEffectZhCn,
      String originalEffectEnUs,
      String descriptionZhCn,
      String descriptionEnUs,
      PowerDefinition effect,
      Rarity rarity
  ) {
    return new CollectibleBuilder(Zinecraft.COLLECTIBLES, path, orderId, zhCn)
        .originalEffect(originalEffectZhCn, originalEffectEnUs)
        .description(descriptionZhCn, descriptionEnUs)
        .minecraftEffect(effect.zhCn(), effect.enUs(), effect.power())
        .sourceRules(effect.sourceRules())
        .rarity(rarity)
        .build();
  }

  private static PowerDefinition statPercent(
      String zhCn,
      String enUs,
      BiFunction<CombatStat, Double, CombatStat> field,
      double amount
  ) {
    return effect(zhCn, enUs, percent(field, amount));
  }

  private static PowerDefinition statFlat(
      String zhCn,
      String enUs,
      BiFunction<CombatStat, Double, CombatStat> field,
      double amount
  ) {
    return effect(zhCn, enUs, flat(field, amount));
  }

  private static PowerDefinition runtime(CollectiblePower power) {
    return effect(
        "按 PRTS 原数值应用于装备者",
        "Applied to the wearer using the original PRTS value",
        power
    );
  }

  private static PowerDefinition enemySpawnStat(
      String zhCn,
      String enUs,
      CombatStat.EnemySpawnStatEffect effect
  ) {
    return effect(zhCn, enUs, stats -> stats.addEnemySpawnStatEffect(effect));
  }

  private static PowerDefinition enemySpawnStat(
      String zhCn,
      String enUs,
      CombatStat.EnemySpawnStatEffect effect,
      String pendingSourceRule
  ) {
    return effect(
        zhCn,
        enUs,
        stats -> stats.addEnemySpawnStatEffect(effect),
        List.of(pendingSourceRule)
    );
  }

  private static PowerDefinition sourceRule(String originalRule) {
    return effect(
        "已登记原始探索规则；等待对应的节点、招募或部署系统触发",
        "Original exploration rule registered; requires its matching node, recruitment or deployment system",
        CollectiblePower.NONE,
        List.of(originalRule)
    );
  }

  private static PowerDefinition explorationRule(String originalRule, CollectiblePower power) {
    return effect(
        "已登记集成战略资源效果；由探索运行时消费",
        "Integrated Strategies resource effect registered for the exploration runtime",
        power,
        List.of(originalRule)
    );
  }

  private static PowerDefinition effect(String zhCn, String enUs, CollectiblePower power) {
    return effect(zhCn, enUs, power, List.of());
  }

  private static PowerDefinition effect(
      String zhCn,
      String enUs,
      CollectiblePower power,
      List<String> sourceRules
  ) {
    return new PowerDefinition(zhCn, enUs, power, List.copyOf(sourceRules));
  }

  private static CollectiblePower statSet(CollectiblePower... effects) {
    return CollectiblePower.combine(effects);
  }

  private static CollectiblePower percent(
      BiFunction<CombatStat, Double, CombatStat> field,
      double amount
  ) {
    return stats -> field.apply(stats, amount);
  }

  private static CollectiblePower flat(
      BiFunction<CombatStat, Double, CombatStat> field,
      double amount
  ) {
    return stats -> field.apply(stats, amount);
  }

  private static CollectiblePower regenerationFlat(double healthPerSecond) {
    return stats -> stats.addPerSecondEffect(entity -> entity.heal((float) healthPerSecond));
  }

  private static CollectiblePower regenerationPercentage(double fractionPerSecond) {
    return stats -> stats.addPerSecondEffect(
        entity -> entity.heal((float) (entity.getMaxHealth() * fractionPerSecond))
    );
  }
  private record PowerDefinition(String zhCn, String enUs, CollectiblePower power, List<String> sourceRules) {
  }

  public static void bootstrap() {
  }
}

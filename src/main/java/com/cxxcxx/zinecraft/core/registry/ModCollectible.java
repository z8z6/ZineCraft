package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.collection.CollectiblePower;
import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.registry.builder.CollectibleBuilder;
import com.cxxcxx.zinecraft.api.skill.SkillProfession;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.world.item.Rarity;

import java.util.List;
import java.util.function.BiFunction;

/**
 * 直接以 Java Builder 声明并注册集成战略各主题中按中文名去重后的全部藏品。
 */
public final class ModCollectible {
  private static final int EXPECTED_COUNT = 742;

  public static final CollectibleBuilder HOT_WATER_KETTLE = collectible(
      "hot_water_kettle",
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
      "锈蚀刀片",
      "所有敌方单位受到的物理伤害+15%",
      "All enemies take +15% Physical damage.",
      "如果它割破了皮肤，你知道会发生什么。",
      "If you cut yourself with this, you know what will happen.",
      statFlat(
          "敌方受到的物理伤害独立乘区+15%",
          "+15% independent Physical damage taken multiplier for enemies",
          CombatStat::addEnemyPhysicalDamageTakenBonus,
          0.15
      ),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder CARRIAGE_DRIVERS_WHIP = collectible(
      "carriage_drivers_whip",
      "赶车夫的长鞭",
      "所有敌方单位受到的物理伤害+25%",
      "All enemies take +25% Physical damage.",
      "作为剧团的赶车人，他没有名字，没有过往。唯有挥鞭驱赶驮兽，他的生命才有意义。",
      "As the troupe's carriage driver, he has no name and no past. His life is meaningful only when he whips his burdenbeasts.",
      statFlat(
          "敌方受到的物理伤害独立乘区+25%",
          "+25% independent Physical damage taken multiplier for enemies",
          CombatStat::addEnemyPhysicalDamageTakenBonus,
          0.25
      ),
      Rarity.RARE
  );
  public static final CollectibleBuilder AVENGER = collectible(
      "avenger",
      "“复仇者”",
      "所有敌方单位受到的物理伤害+35%",
      "All enemies take +35% Physical damage.",
      "在阿斯卡纶第一次为军事委员会完成任务后，由特雷西斯亲手赠送，特蕾西娅为她安装的第一把武器。",
      "A gift from Theresis and set up by Theresa, this is the very first weapon that Ascalon received after her inaugural mission as part of the Military Council.",
      statFlat(
          "敌方受到的物理伤害独立乘区+35%",
          "+35% independent Physical damage taken multiplier for enemies",
          CombatStat::addEnemyPhysicalDamageTakenBonus,
          0.35
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder STANDARD_ANTI_RIOT_INSTRUMENT = collectible(
      "standard_anti_riot_instrument",
      "制式防暴用具",
      "所有敌方单位受到的法术伤害+20%",
      "All enemies take +20% Arts damage.",
      "乌萨斯军警的制式装备，自带施放间歇性致盲源石技艺的功能。可惜关键时刻想得起来这一功能的军警寥寥无几。",
      "Standard equipment of the Ursus Guard. Comes with an intermittent blinding Originium Arts effect. Unfortunately, not many Guards out there remember this when push comes to shove.",
      statFlat(
          "敌方受到的法术伤害独立乘区+20%",
          "+20% independent Arts damage taken multiplier for enemies",
          CombatStat::addEnemyMagicDamageTakenBonus,
          0.20
      ),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder EMPERORS_COLLECTION = collectible(
      "emperors_collection",
      "皇帝的收藏",
      "所有敌方单位受到的法术伤害+30%",
      "All enemies take +30% Arts damage.",
      "萨米人对他们的荒野尊崇无比，而现在这片荒野的碎片正静静躺在乌萨斯皇帝的私库中。",
      "The Sami revere their wild lands more than anything, and this fragment of the vast wilderness sleeps in the personal collection of the Emperor of Ursus.",
      statFlat(
          "敌方受到的法术伤害独立乘区+30%",
          "+30% independent Arts damage taken multiplier for enemies",
          CombatStat::addEnemyMagicDamageTakenBonus,
          0.30
      ),
      Rarity.RARE
  );
  public static final CollectibleBuilder BRILLIANT_LAMENT = collectible(
      "brilliant_lament",
      "“璀璨悲泣”",
      "所有敌方单位受到的法术伤害+40%",
      "All enemies take +40% Arts damage.",
      "他的血液仍在流淌，他从不曾真的自这里离开。",
      "His blood yet flows. He has never left this place.",
      statFlat(
          "敌方受到的法术伤害独立乘区+40%",
          "+40% independent Arts damage taken multiplier for enemies",
          CombatStat::addEnemyMagicDamageTakenBonus,
          0.40
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder LIVE_ROSE = collectible(
      "live_rose",
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
      "钝爪-突破",
      "所有【先锋】干员的部署费用-2，生命+60%",
      "Vanguard Operators have -2 DP Cost and +60% Max HP",
      "在波涛中插下顽石，分割巨浪，破开迷雾。",
      "Plant a great stone within the waters. Split the waves and part the fog.",
      professionRule(SkillProfession.VANGUARD, runtime(percent(CombatStat::multiplyMaxHealth, 0.6))),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BLUNT_CLAWS_BURST = collectible(
      "blunt_claws_burst",
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
      "钝爪-百战",
      "所有【先锋】干员的攻击力+50%，防御力+50%",
      "Vanguard Operators have +50% ATK and DEF",
      "“当黑漆漆的敌人向我涌来时，我脑海里只想到四个字——它们完了。”",
      "'When the darkly-clad enemies rushed towards me, there were only four words on my mind — they are done for.'",
      professionRule(SkillProfession.VANGUARD, effect("攻击力+50%，防御力+50%", "+50% ATK and +50% DEF", statSet(percent(CombatStat::multiplyAttack, 0.5), percent(CombatStat::multiplyDefense, 0.5)))),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BEND_SPEARS_ADVANCEMENT = collectible(
      "bend_spears_advancement",
      "折戟-突破",
      "所有【近卫】干员的部署费用-3，生命+40%",
      "Guard Operators have -3 DP Cost and +40% Max HP",
      "战士走入战场，战士拔出战刃，战士迎接战斗。",
      "The soldiers stepped onto the battlefield, drew their blades, and met in battle.",
      professionRule(SkillProfession.GUARD, runtime(percent(CombatStat::multiplyMaxHealth, 0.4))),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BEND_SPEARS_ACUITY = collectible(
      "bend_spears_acuity",
      "折戟-锋刃",
      "所有【近卫】干员的攻击力+25%",
      "Guard Operators have +25% ATK.",
      "“这是你这个月砍坏的第七把刀了。”“但是我这个月砍了八个敌人。”",
      "'This is the 7th blade you've broken this month.' 'But I cut down eight enemies this month.'",
      professionRule(SkillProfession.GUARD, statPercent("攻击力+25%", "+25% ATK", CombatStat::multiplyAttack, 0.25)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BEND_SPEARS_BLOODBATH = collectible(
      "bend_spears_bloodbath",
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
      "折戟-破釜沉舟",
      "所有【近卫】干员的防御力-40%，但攻击力+40%，攻击速度+30",
      "Guard Operators have -40% DEF, but gain +40% ATK and +30 ASPD.",
      "不太建议向煌学习那些技巧。",
      "I wouldn't really recommend learning those skills from Blaze.",
      professionRule(SkillProfession.GUARD, effect("防御力-40%，攻击力+40%，攻击速度+30", "-40% DEF, +40% ATK and +30 ASPD", statSet(percent(CombatStat::multiplyDefense, -0.4), percent(CombatStat::multiplyAttack, 0.4), flat(CombatStat::addAttackSpeed, 30)))),
      Rarity.EPIC
  );
  public static final CollectibleBuilder IRON_GUARD_ADVANCEMENT = collectible(
      "iron_guard_advancement",
      "铁卫-突破",
      "所有【重装】干员的部署费用-3，生命+40%",
      "Defender Operators have -3 DP Cost and +40% Max HP",
      "持盾者连成山脉，连成土地，他们对抗的不是血肉之敌，他们对抗命运，对抗不公。",
      "The shieldbearers formed a mountain range and became the earth. What they fight against are not enemies of flesh and blood. They fight against fate and injustice.",
      professionRule(SkillProfession.DEFENDER, runtime(percent(CombatStat::multiplyMaxHealth, 0.4))),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder IRON_GUARD_INVASION = collectible(
      "iron_guard_invasion",
      "铁卫-侵掠",
      "所有【重装】干员的攻击力+60%",
      "Defender Operators have +60% ATK.",
      "对队友和战略目的的保护行动往往会让人忽视他们原本的侵略性。",
      "Defensive actions for the sake of teammates or strategic purposes often make people forget about their original aggressiveness.",
      professionRule(SkillProfession.DEFENDER, statPercent("攻击力+60%", "+60% ATK", CombatStat::multiplyAttack, 0.6)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder IRON_GUARD_TRANQUILITY = collectible(
      "iron_guard_tranquility",
      "铁卫-不动",
      "所有【重装】干员的防御力+25%，生命+50%",
      "Defender Operators have +25% DEF and +50% Max HP",
      "“真有人能在那种规模的轰炸下一动不动？”“谁说一动不动的，他还往前挪了几步。”",
      "'Is there anyone who can remain in formation under a bombing of that scale?' 'Standing in formation? He moved a few steps forward.'",
      professionRule(SkillProfession.DEFENDER, effect("防御力+25%，最大生命值+50%", "+25% DEF and +50% maximum HP", statSet(percent(CombatStat::multiplyDefense, 0.25), percent(CombatStat::multiplyMaxHealth, 0.5)))),
      Rarity.RARE
  );
  public static final CollectibleBuilder IRON_GUARD_ADVANCE = collectible(
      "iron_guard_advance",
      "铁卫-推进",
      "所有【重装】干员阻挡数-1（部署时不会低于1），但攻击力+40%，攻击速度+40",
      "Defender Operators have -1 Block (will not be reduced below 1), but gain +40% ATK and +40 ASPD.",
      "以放弃防守换取进攻的机会，毁灭的阵线向前迈进。",
      "In exchange for an opportunity to attack, the shattered ranks made one more advance.",
      effect(
          "重装技能攻击力+40%、攻击速度+40；阻挡数规则尚未实现",
          "+40% ATK and +40 ASPD for Defender skills; Block rule pending",
          forProfession(SkillProfession.DEFENDER, statSet(
              percent(CombatStat::multiplyAttack, 0.4),
              flat(CombatStat::addAttackSpeed, 40)
          )),
          List.of("所有【重装】干员阻挡数-1（部署时不会低于1）")
      ),
      Rarity.RARE
  );
  public static final CollectibleBuilder IRON_GUARD_IMPENETRABLE = collectible(
      "iron_guard_impenetrable",
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
      "残弩-突破",
      "所有【狙击】干员的部署费用-2，生命+60%",
      "Sniper Operators have -2 DP Cost and +60% Max HP",
      "上膛，瞄准，开火，毁灭如期而至。",
      "Load, aim, and fire. Destruction arrives as scheduled.",
      professionRule(SkillProfession.SNIPER, runtime(percent(CombatStat::multiplyMaxHealth, 0.6))),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder FATAL_BOLTS_PRECISION = collectible(
      "fatal_bolts_precision",
      "残弩-百步穿杨",
      "所有【狙击】干员的攻击力+20%",
      "Sniper Operators have +20% ATK.",
      "在源石技艺尚不如今天发达的时代，炎国曾以“百步穿杨”的典故来称赞他人箭术高超。",
      "In an era when Originium Arts were not as robust as they are now, Yan had a saying, 'a pierced willow leaf from a hundred paces,' to praise others for their mastery of archery.",
      professionRule(SkillProfession.SNIPER, runtime(percent(CombatStat::multiplyAttack, 0.2))),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder FATAL_BOLTS_SYNERGY = collectible(
      "fatal_bolts_synergy",
      "残弩-战场依存",
      "所有【狙击】干员的自然技力恢复+0.5/秒",
      "Increases the SP regen rate of Sniper Operators by +0.5/s",
      "和武器越发亲密的狙击手，越不容易遭到武器的背叛。",
      "The more intimate a sniper is with her weapon, the less likely she is to be betrayed by it.",
      professionRule(SkillProfession.SNIPER, statFlat("自然回复技能技力+0.5/秒", "+0.5 SP/s for Auto Recovery skills", CombatStat::addNaturalSkillPointRegeneration, 0.5)),
      Rarity.RARE
  );
  public static final CollectibleBuilder FATAL_BOLTS_CROSSFIRE = collectible(
      "fatal_bolts_crossfire",
      "残弩-交叉火力",
      "所有【狙击】干员的生命-40%，但攻击力+40%",
      "Sniper Operators have -40% HP, but gain +40% ATK.",
      "密集的火力网布置同时让狙击干员们暴露在危险之中，接下来是一场关于准星的博弈。",
      "The dense crossfire also exposes snipers to great danger. The next battle boils down to a game of vision.",
      professionRule(SkillProfession.SNIPER, runtime(statSet(percent(CombatStat::multiplyAttack, 0.4), percent(CombatStat::multiplyMaxHealth, -0.4)))),
      Rarity.RARE
  );
  public static final CollectibleBuilder FATAL_BOLTS_DIVINE_SPEED = collectible(
      "fatal_bolts_divine_speed",
      "残弩-神速",
      "所有【狙击】干员的攻击速度+70",
      "Sniper Operators have +70 ASPD.",
      "据说古维多利亚的传奇弓手可以让箭矢几乎连成一线。",
      "It is said that the legendary archer of ancient Victoria can almost shoot a continuous stream of arrows.",
      professionRule(SkillProfession.SNIPER, statFlat("攻击速度+70", "+70 ASPD", CombatStat::addAttackSpeed, 70)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BROKEN_WAND_ADVANCEMENT = collectible(
      "broken_wand_advancement",
      "断杖-突破",
      "所有【术师】干员的部署费用-3，生命+60%",
      "Caster Operators have -3 DP Cost and +60% Max HP",
      "让技艺在指尖起舞，呼风唤雨，搅乱现实，达成你宏伟的目的。",
      "Let Arts dance around your fingertips. Call the wind and rain, unravel reality, and achieve your grand goals.",
      professionRule(SkillProfession.CASTER, runtime(percent(CombatStat::multiplyMaxHealth, 0.6))),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BROKEN_WAND_ARTS_WEAVING = collectible(
      "broken_wand_arts_weaving",
      "断杖-织法者",
      "所有【术师】干员的攻击力+25%",
      "Caster Operators have +25% ATK.",
      "在莱塔尼亚仰望高塔的时候经常会看见一些奇怪的现象......甚至是天象。",
      "Those who gaze up at the great spires in Leithanien often see some strange or even celestial phenomena.",
      professionRule(SkillProfession.CASTER, runtime(percent(CombatStat::multiplyAttack, 0.25))),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder BROKEN_WAND_CHANTING = collectible(
      "broken_wand_chanting",
      "断杖-咏唱",
      "所有【术师】干员的攻击速度+40",
      "Caster Operators have +40 ASPD.",
      "虽然音乐与法术有着奇妙的联系，呃，但这不是某些人五音不全的借口。",
      "Though the relationship between music and Arts is marvelous indeed, umm, this is not an excuse for some peoples' tone-deafness.",
      professionRule(SkillProfession.CASTER, statFlat("攻击速度+40", "+40 ASPD", CombatStat::addAttackSpeed, 40)),
      Rarity.RARE
  );
  public static final CollectibleBuilder BROKEN_WAND_CONCENTRATION = collectible(
      "broken_wand_concentration",
      "断杖-凝神",
      "所有【术师】干员的技力恢复+0.4/秒",
      "Increases the SP regen rate of Caster Operators by +0.4/s",
      "“当一个术师打算全力以赴，哪怕是天火小姐那种坏脾气术师，也会展露出截然不同的气质。”",
      "'When a caster intends to go all out, they will display a completely different temperament — even if we're talking about something like Miss Skyfire's grumpiness.'",
      professionRule(SkillProfession.CASTER, statFlat("自然回复技能技力+0.4/秒", "+0.4 SP/s for Auto Recovery skills", CombatStat::addNaturalSkillPointRegeneration, 0.4)),
      Rarity.RARE
  );
  public static final CollectibleBuilder BROKEN_WAND_MALEDICTION = collectible(
      "broken_wand_malediction",
      "断杖-苦难巫咒",
      "所有【术师】干员生命-40%，但造成的法术伤害+70%",
      "Caster Operators have -40% HP, but deal +70% Arts damage",
      "萨卡兹接触源石的起源几乎无从考证，古老法术的起点早已脱离物质现实与逻辑常理。",
      "It is virtually impossible to identify when the Sarkaz first encountered Originium, and the origins of these ancient Arts have long been separated from actual reality and common sense.",
      professionRule(SkillProfession.CASTER, runtime(percent(CombatStat::multiplyMaxHealth, -0.4))),
      Rarity.EPIC
  );
  public static final CollectibleBuilder STALWART_AID_ADVANCEMENT = collectible(
      "stalwart_aid_advancement",
      "支柱-突破",
      "所有【辅助】干员的部署费用-2，生命+60%",
      "Supporter Operators have -2 DP Cost and +60% Max HP",
      "当“差一点完成任务”的时候，你需要的正是那个帮你补上“差一点”的人。",
      "Whenever you're 'almost done' with a task, what you need at that moment is someone to help you with the 'almost' part.",
      professionRule(SkillProfession.SUPPORTER, runtime(percent(CombatStat::multiplyMaxHealth, 0.6))),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder STALWART_AID_SECONDARY_FRONT = collectible(
      "stalwart_aid_secondary_front",
      "支柱-次要战场",
      "所有【辅助】干员的【召唤物】攻击力+50%",
      "Units summoned by Supporter Operators have +50% ATK.",
      "“呃，机械和源石技艺衍生物我尚且能理解，但是不是有些别的......”",
      "'Um, I can understand the derivatives of combining mechanics with Originium Arts, but there might be something else...'",
      professionRule(SkillProfession.SUPPORTER, runtime(percent(CombatStat::multiplyAttack, 0.5))),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder STALWART_AID_DILIGENCE = collectible(
      "stalwart_aid_diligence",
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
      "医者-突破",
      "所有【医疗】干员的部署费用-2，生命+60%",
      "Medic Operators have -2 DP Cost and +60% Max HP",
      "拯救是人类必须赞许的美德，是这一切得以存续的仰仗。",
      "The desire to save lives is a virtue that mankind must applaud, for that is the support that keeps us alive.",
      professionRule(SkillProfession.MEDIC, runtime(percent(CombatStat::multiplyMaxHealth, 0.6))),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder HEALERS_PATH_SELF_TREATING = collectible(
      "healers_path_self_treating",
      "医者-自医",
      "所有【医疗】干员的技力恢复+0.3/秒",
      "Increases the SP regen rate of Medic Operators by +0.3/s",
      "谁说医者不能自医，他们只是选择给自己来一片提神药然后继续帮助别人而已。",
      "Who says healers can't heal themselves? They simply pop some pills before continuing to help others.",
      professionRule(SkillProfession.MEDIC, statFlat("自然回复技能技力+0.3/秒", "+0.3 SP/s for Auto Recovery skills", CombatStat::addNaturalSkillPointRegeneration, 0.3)),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder HEALERS_PATH_POTENCY = collectible(
      "healers_path_potency",
      "医者-强效试剂",
      "所有【医疗】干员的攻击力+40%",
      "Medic Operators have +40% ATK.",
      "除了华法琳，没人敢采取这么激进的医疗手段，不过她从没失误过一次。",
      "Nobody else dared to attempt such a radical treatment — except Warfarin. She never made a single mistake.",
      professionRule(SkillProfession.MEDIC, runtime(percent(CombatStat::multiplyAttack, 0.4))),
      Rarity.RARE
  );
  public static final CollectibleBuilder HEALERS_PATH_KEEN_HANDS = collectible(
      "healers_path_keen_hands",
      "医者-妙手",
      "所有【医疗】干员的攻击速度+50",
      "Medic Operators have +50 ASPD.",
      "比起从死亡手里多抢回一条命的伟业，事后昏睡个三天算什么？",
      "Compared to the feat of snatching a life from the grip of death, what is three days of coma afterwards?",
      professionRule(SkillProfession.MEDIC, runtime(flat(CombatStat::addAttackSpeed, 50))),
      Rarity.RARE
  );
  public static final CollectibleBuilder HEALERS_PATH_RESTORE_SANITY = collectible(
      "healers_path_restore_sanity",
      "医者-理智固剂",
      "所有【医疗】干员攻击范围内的我方单位获得抵抗",
      "Allied units within the attack range of Medic Operators gain Resistance.",
      "加固你的思维，让你脑中帝国疆域上的每株杂草都无懈可击。",
      "Reinforce your thinking. In the domain of your mind, make even every weed impregnable.",
      professionRule(SkillProfession.MEDIC, statFlat("异常状态持续时间-50%", "-50% negative status duration", CombatStat::addFriendlyStatusDurationReduction, 0.50)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder RUSTED_BLADE_ADVANCEMENT = collectible(
      "rusted_blade_advancement",
      "锈刃-突破",
      "所有【特种】干员的部署费用-2，生命+60%",
      "Specialist Operators have -2 DP Cost and +60% Max HP",
      "手段有很多，结局却只有一种。",
      "There may be many means, but only one end.",
      professionRule(SkillProfession.SPECIALIST, runtime(percent(CombatStat::multiplyMaxHealth, 0.6))),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder RUSTED_BLADE_EXECUTION = collectible(
      "rusted_blade_execution",
      "锈刃-处决",
      "所有【特种】干员的攻击速度+30",
      "Specialist Operators have +30 ASPD.",
      "红有一把小刀。她有一把小刀。",
      "Projekt Red has a knife. She has a knife.",
      professionRule(SkillProfession.SPECIALIST, runtime(flat(CombatStat::addAttackSpeed, 30))),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder RUSTED_BLADE_ISOLATION = collectible(
      "rusted_blade_isolation",
      "锈刃-单兵",
      "所有【特种】干员的攻击力+40%，防御力+40%",
      "Specialist Operators have +40% ATK and DEF",
      "S.W.E.E.P.的总管没有任何任务参与记录。*没有*。",
      "The director of S.W.E.E.P. does not have any mission participation records. *None*.",
      professionRule(SkillProfession.SPECIALIST, effect("攻击力+40%，防御力+40%", "+40% ATK and +40% DEF", statSet(percent(CombatStat::multiplyAttack, 0.4), percent(CombatStat::multiplyDefense, 0.4)))),
      Rarity.RARE
  );
  public static final CollectibleBuilder RUSTED_BLADE_NO_MANS_LAND = collectible(
      "rusted_blade_no_mans_land",
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
      "撕扯之手",
      "【无畏者】、【剑豪】和【教官】攻击时无视目标70%的防御力",
      "Dreadnought, Swordmaster, and Instructor Operators' attacks ignore 70% of target's DEF",
      "东撕西扯，铁甲如布匹。",
      "Tear it all apart. Their armor is nothing but cloth in your hands.",
      professionRule(
          SkillProfession.GUARD,
          statFlat("近卫技能无视目标70%防御力", "Guard skills ignore 70% DEF", CombatStat::addDefenseIgnore, 0.70)
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HAND_OF_SUPERSPEED = collectible(
      "hand_of_superspeed",
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
      "投币玩具",
      "每有5源石锭，所有我方单位的攻击速度+3",
      "For each 5 Originium Ingots in possession, all friendly units have +3 ASPD",
      "不知道为什么会有人喜欢这种造型，但整体的设计理念确实可圈可点——只要投币，就连石像都会动起来，千真万确。",
      "No idea why anyone would like a design like this, but its overall concept is indeed praiseworthy—Just insert a coin, and even a statue will start moving. There is nothing truer in this world.",
      ingotAttackSpeed(3),
      Rarity.UNCOMMON
  );
  public static final CollectibleBuilder CHIVALRIC_COMMANDMENTS_NEW_EDITION = collectible(
      "chivalric_commandments_new_edition",
      "骑士戒律·新编",
      "每有5源石锭，所有我方单位的攻击速度+5",
      "For each 5 Originium Ingots in possession, all friendly units have +5 ASPD",
      "庄严宣誓那是过去的事儿了，现代骑士精神需要更加灵活的展现方式。在联络装置上输入你的编号，你便是名光荣的骑士。",
      "Taking solemn vows is a thing of the past. The spirit of modern knighthood requires more flexible displays. Go ahead and input your number in the communications device, and you too will be an honorary knight.",
      ingotAttackSpeed(5),
      Rarity.RARE
  );
  public static final CollectibleBuilder GOLDEN_CHALICE = collectible(
      "golden_chalice",
      "金酒之杯",
      "每有5源石锭，所有我方单位的攻击速度+7",
      "For each 5 Originium Ingots in possession, all friendly units have +7 ASPD",
      "圣人、骑士、虔信之徒，无人不为之疯狂。老天啊，连杯子本身都是纯金的！",
      "Saints, knights, and pietists. All of them are madmen. Dear God, even the chalice itself is made of pure gold!",
      ingotAttackSpeed(7),
      Rarity.EPIC
  );
  public static final CollectibleBuilder SPINACH_PACK = collectible(
      "spinach_pack",
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
      "乌萨斯弯刀",
      "所有敌方单位的攻击力、防御力、生命+40%",
      "All enemy units have +40% ATK, DEF, and Max HP",
      "钢铁的洪流在荒芜的土地上疾驰，远方传来不容忤逆的绝对意志，乌萨斯，是我双手的延伸。",
      "The currents of steel and iron gush forth on the barren plains. From afar surge an inviolable, absolute will. Wheresoever my hands reach, there stands Ursus.",
      enemySpawnStat(
          "敌方生成时攻击力、防御力、最大生命+40%",
          "+40% enemy ATK, DEF and maximum HP on spawn",
          (enemy, stats) -> enemyCoreStats(stats, 0.40)
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder URSUS_CHACHEK_REFORGED = collectible(
      "ursus_chachek_reforged",
      "乌萨斯弯刀（重铸）",
      "所有敌方单位的攻击力、防御力、生命+35%，每进入新的一层额外+10%，完成紧急作战时-5%（最低35%）",
      "All enemy units gain +35% ATK, DEF, and Max HP, and gain an additional +10% to these three stats upon entering a new floor, but clearing an Emergency Op reduces these stats by 5% (minimum 35%)",
      "钢铁的洪流在荒芜的土地上疾驰，远方传来不容忤逆的绝对意志，乌萨斯，是我双手的延伸。",
      "The currents of steel and iron gush forth on the barren plains. From afar surge an inviolable, absolute will. Wheresoever my hands reach, there stands Ursus.",
      enemySpawnStat(
          "敌方生成时攻击力、防御力、最大生命至少+35%；楼层与紧急作战变化尚未实现",
          "At least +35% enemy ATK, DEF and maximum HP on spawn; floor and Emergency Op changes pending",
          (enemy, stats) -> enemyCoreStats(stats, 0.35),
          "每进入新的一层额外+10%，完成紧急作战时-5%（最低35%）"
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder VICTORIA_CROWN = collectible(
      "victoria_crown",
      "维多利亚王冠",
      "所有敌方单位的攻击力、防御力、生命+30%，且所有领袖单位还会攻击力、防御力+15%，生命+30%",
      "All enemy units have +30% ATK, DEF, and Max HP; Boss units gain an additional +15% ATK, +15% DEF, and +30% Max HP",
      "当最后一位统治维多利亚的阿斯兰被推上断头台时，愤怒的群众一拥而上，踩踏着过去的君权。",
      "When the last Aslan to rule Victoria was sent to the guillotine, the angry masses rushed forward to trample his reign into dust.",
      enemySpawnStat(
          "敌方生成时攻击力、防御力、最大生命+30%；领袖额外强化尚未实现",
          "+30% enemy ATK, DEF and maximum HP on spawn; additional boss bonuses pending",
          (enemy, stats) -> enemyCoreStats(stats, 0.30),
          "所有领袖单位还会攻击力、防御力+15%，生命+30%"
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder VICTORIA_CROWN_REFORGED = collectible(
      "victoria_crown_reforged",
      "维多利亚王冠（重铸）",
      "所有敌方单位的攻击力、防御力、生命+35%，所有领袖单位还会攻击力、防御力+20%，生命+50%，编入所有职业的干员时领袖加成减半",
      "All enemy units have +35% ATK, DEF, and Max HP; Leader enemies gain an additional +20% ATK, DEF and +50% Max HP, but these leader bonuses are halved when your squad has Operators from all 8 Classes",
      "当最后一位统治维多利亚的阿斯兰被推上断头台时，愤怒的群众一拥而上，踩踏着过去的君权。",
      "When the last Aslan to rule Victoria was sent to the guillotine, the angry masses rushed forward to trample his reign into dust.",
      enemySpawnStat(
          "敌方生成时攻击力、防御力、最大生命+35%；领袖与全职业编队条件尚未实现",
          "+35% enemy ATK, DEF and maximum HP on spawn; boss and full-squad conditions pending",
          (enemy, stats) -> enemyCoreStats(stats, 0.35),
          "所有领袖单位还会攻击力、防御力+20%，生命+50%，编入所有职业的干员时领袖加成减半"
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder LEITHANIEN_SCEPTRE = collectible(
      "leithanien_sceptre",
      "莱塔尼亚权杖",
      "所有敌方单位的攻击力、防御力、生命+30%，且每进入一个新节点后，失去1目标生命（最多降至1）",
      "All enemy units have +30% ATK, DEF, and Max HP; Entering a new node decreases Life Point by 1 (cannot be reduced beneath 1 this way)",
      "曾几何时，巫王的权杖上流淌着白天与黑夜。",
      "It was not long ago that the Witch King's scepter flowed with daylight and the night sky.",
      enemySpawnStat(
          "敌方生成时攻击力、防御力、最大生命+30%；节点目标生命规则尚未实现",
          "+30% enemy ATK, DEF and maximum HP on spawn; node Life Point rule pending",
          (enemy, stats) -> enemyCoreStats(stats, 0.30),
          "每进入一个新节点后，失去1目标生命（最多降至1）"
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder LEITHANIEN_SCEPTRE_REFORGED = collectible(
      "leithanien_sceptre_reforged",
      "莱塔尼亚权杖（重铸）",
      "所有敌方单位的攻击力、防御力、生命+35%，进入节点时目标生命-1（最低降至1），关卡生命低于3时我方部署费用+2，技力回复速度-20%",
      "All enemy units have +35% ATK, DEF, and Max HP; Entering a new node decreases Life Point by 1 (cannot be reduced beneath 1 this way); When Life Point is below 3, all units have DP Cost + 2 and SP recovery rate -20%",
      "曾几何时，巫王的权杖上流淌着白天与黑夜。",
      "It was not long ago that the Witch King's scepter flowed with daylight and the night sky.",
      enemySpawnStat(
          "敌方生成时攻击力、防御力、最大生命+35%；节点与低目标生命规则尚未实现",
          "+35% enemy ATK, DEF and maximum HP on spawn; node and low-Life rules pending",
          (enemy, stats) -> enemyCoreStats(stats, 0.35),
          "进入节点时目标生命-1（最低降至1），关卡生命低于3时我方部署费用+2，技力回复速度-20%"
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder GAUL_MANTLE = collectible(
      "gaul_mantle",
      "高卢长袍",
      "所有敌方单位的攻击力、防御力、生命+25%，且招募4星及以上干员时希望消耗+1",
      "All enemy units have +25% ATK, DEF, and Max HP; 4-star and above Operators cost +1 Hope when recruited",
      "高卢皇帝的遗物，威权与盛世的象征。",
      "An artifact left behind by the late Gaulish emperor. A symbol of power and glory.",
      enemySpawnStat(
          "敌方生成时攻击力、防御力、最大生命+25%；招募希望规则尚未实现",
          "+25% enemy ATK, DEF and maximum HP on spawn; recruitment Hope rule pending",
          (enemy, stats) -> enemyCoreStats(stats, 0.25),
          "招募4星及以上干员时希望消耗+1"
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder GAUL_MANTLE_REFORGED = collectible(
      "gaul_mantle_reforged",
      "高卢长袍（重铸）",
      "所有敌方单位的攻击力、防御力、生命+30%，在奇数层招募4星以上干员时希望消耗+2，偶数层晋升干员希望消耗+2",
      "All enemy units have +30% ATK, DEF, and Max HP; 4-star and above Operators cost +2 Hope to recruit on odd-numbered floors, and +2 Hope to promote on even-numbered floors",
      "高卢皇帝的遗物，威权与盛世的象征。",
      "An artifact left behind by the late Gaulish emperor. A symbol of power and glory.",
      enemySpawnStat(
          "敌方生成时攻击力、防御力、最大生命+30%；分层招募与晋升规则尚未实现",
          "+30% enemy ATK, DEF and maximum HP on spawn; floor recruitment and promotion rules pending",
          (enemy, stats) -> enemyCoreStats(stats, 0.30),
          "在奇数层招募4星以上干员时希望消耗+2，偶数层晋升干员希望消耗+2"
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HALF_REFINED_DIAMOND = collectible(
      "half_refined_diamond",
      "半洗孤钻",
      "所有敌方单位的攻击力、防御力、生命+25%，且战斗获得的指挥经验-50%",
      "All enemy units have +25% ATK, DEF, and Max HP; Gain -50% Command EXP from battles",
      "一半闪耀着艺术的光辉，一半浸染着猩红的疯癫，这才是剧团长梦想中的“傀影”。",
      "Half of it shines radiantly in the name of art, while the other half is stained in a madder red color that signifies madness. This is the 'Phantom' that the troupe leader has always dreamt of.",
      effect(
          "敌方生成时攻击力、防御力、最大生命+25%；已登记指挥经验倍率50%",
          "+25% enemy ATK, DEF and maximum HP on spawn; 50% Command EXP multiplier registered",
          stats -> stats
              .addEnemySpawnStatEffect((enemy, enemyStats) -> enemyCoreStats(enemyStats, 0.25))
              .commandExperienceMultiplier(0.50),
          List.of("战斗获得的指挥经验-50%尚无探索结算消费者")
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder HALF_REFINED_DIAMOND_REFORGED = collectible(
      "half_refined_diamond_reforged",
      "半洗孤钻（重铸）",
      "所有敌方单位的攻击力、防御力、生命+30%，战斗获得的指挥经验-70%；险路恶敌中获得的指挥经验变为15倍",
      "All enemy units have +30% ATK, DEF, and Max HP; Gain -70% Command EXP from battles; Dreadful Foe nodes grant 15x Command EXP",
      "一半闪耀着艺术的光辉，一半浸染着猩红的疯癫，这才是剧团长梦想中的“傀影”。",
      "Half of it shines radiantly in the name of art, while the other half is stained in a madder red color that signifies madness. This is the 'Phantom' that the troupe leader has always dreamt of.",
      effect(
          "敌方生成时攻击力、防御力、最大生命+30%；已登记普通指挥经验倍率30%",
          "+30% enemy ATK, DEF and maximum HP on spawn; 30% normal Command EXP multiplier registered",
          stats -> stats
              .addEnemySpawnStatEffect((enemy, enemyStats) -> enemyCoreStats(enemyStats, 0.30))
              .commandExperienceMultiplier(0.30),
          List.of("战斗获得的指挥经验-70%尚无探索结算消费者；险路恶敌中获得的指挥经验变为15倍尚未实现")
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder SIGIL_OF_TRAGODIA = collectible(
      "sigil_of_tragodia",
      "酒神的印记",
      "所有敌方单位的攻击力、防御力、生命+30%，且可同时部署人数-2",
      "All enemy units have +30% ATK, DEF, and Max HP; Deployment Limit -2",
      "猩红剧团内随处可见的图案，将剧团长和他对艺术的渴求深深烙印进每个人的脑海中。",
      "A symbol that can be seen everywhere within the Crimson Troupe. It burns the troupe leader and his pursuit for the arts into the eyes of all those who lay their eyes upon it.",
      effect(
          "敌方生成时攻击力、防御力、最大生命+30%；已登记部署上限-2",
          "+30% enemy ATK, DEF and maximum HP on spawn; Deployment Limit -2 registered",
          stats -> stats
              .addEnemySpawnStatEffect((enemy, enemyStats) -> enemyCoreStats(enemyStats, 0.30))
              .deploymentLimit(-2),
          List.of("可同时部署人数-2尚无探索结算消费者")
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder SIGIL_OF_TRAGODIA_REFORGED = collectible(
      "sigil_of_tragodia_reforged",
      "酒神的印记（重铸）",
      "所有敌方单位的攻击力、防御力、生命+35%，可同时部署人数-3，每次进入幕间余兴时可同时部署人数+2",
      "All enemy units have +35% ATK, DEF, and Max HP; Deployment Limit -3, but gain Deployment Limit +2 whenever you enter the Downtime Recreation node",
      "猩红剧团内随处可见的图案，将剧团长和他对艺术的渴求深深烙印进每个人的脑海中。",
      "A symbol that can be seen everywhere within the Crimson Troupe. It burns the troupe leader and his pursuit for the arts into the eyes of all those who lay their eyes upon it.",
      effect(
          "敌方生成时攻击力、防御力、最大生命+35%；已登记基础部署上限-3",
          "+35% enemy ATK, DEF and maximum HP on spawn; base Deployment Limit -3 registered",
          stats -> stats
              .addEnemySpawnStatEffect((enemy, enemyStats) -> enemyCoreStats(enemyStats, 0.35))
              .deploymentLimit(-3),
          List.of("可同时部署人数-3尚无探索结算消费者；每次进入幕间余兴时可同时部署人数+2尚未实现")
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder PLAYWRIGHTS_MANUSCRIPT_VICTORIA = collectible(
      "playwrights_manuscript_victoria",
      "剧作家手稿：维多利亚",
      "所有敌方单位的攻击力、防御力和生命+10%，下次招募干员时希望消耗+1（招募后效果消失）",
      "All enemy units have +10% ATK, DEF, and Max HP; Operators cost +1 Hope to recruit (effect lasts for 1 recruit)",
      "“菲林的王冠四分五裂——”",
      "'The Feline's crown shatters into pieces—'",
      enemySpawnStat(
          "敌方生成时攻击力、防御力、最大生命+10%；一次性招募希望规则尚未实现",
          "+10% enemy ATK, DEF and maximum HP on spawn; one-time recruitment Hope rule pending",
          (enemy, stats) -> enemyCoreStats(stats, 0.10),
          "下次招募干员时希望消耗+1（招募后效果消失）"
      ),
      Rarity.RARE
  );
  public static final CollectibleBuilder PLAYWRIGHTS_MANUSCRIPT_URSUS = collectible(
      "playwrights_manuscript_ursus",
      "剧作家手稿：乌萨斯",
      "所有敌方单位的攻击力、防御力和生命+10%，每次进入非战斗节点时失去1源石锭",
      "All enemy units have +10% ATK, DEF, and Max HP; Lose 1 Originium Ingot every time you enter a non-combat node",
      "“乌萨斯的宴会戛然而止——”\n",
      "'The Ursus's feast comes to an abrupt stop—'\n",
      effect(
          "敌方生成时攻击力、防御力、最大生命+10%；已登记非战斗节点源石锭-1",
          "+10% enemy ATK, DEF and maximum HP on spawn; -1 Ingot per non-combat node registered",
          stats -> stats
              .addEnemySpawnStatEffect((enemy, enemyStats) -> enemyCoreStats(enemyStats, 0.10))
              .originiumIngotsPerNonCombatNode(-1),
          List.of("每次进入非战斗节点时失去1源石锭尚无探索结算消费者")
      ),
      Rarity.RARE
  );
  public static final CollectibleBuilder PLAYWRIGHTS_MANUSCRIPT_LEITHANIEN = collectible(
      "playwrights_manuscript_leithanien",
      "剧作家手稿：莱塔尼亚",
      "所有敌方单位的攻击力、防御力和生命+10%，部署费用低于99时我方攻击力-10%",
      "All enemy units have +10% ATK, DEF, and Max HP; All allied units have -10% ATK when DP is below 99",
      "“卡普里尼的高塔轰然倒塌——”",
      "'The Caprinae's spire collapses—'",
      enemySpawnStat(
          "敌方生成时攻击力、防御力、最大生命+10%；部署费用条件减攻尚未实现",
          "+10% enemy ATK, DEF and maximum HP on spawn; conditional allied ATK reduction pending",
          (enemy, stats) -> enemyCoreStats(stats, 0.10),
          "部署费用低于99时我方攻击力-10%"
      ),
      Rarity.RARE
  );
  public static final CollectibleBuilder PLAYWRIGHTS_MANUSCRIPT_GAUL = collectible(
      "playwrights_manuscript_gaul",
      "剧作家手稿：高卢",
      "所有敌方单位的攻击力、防御力和生命+10%，每场战斗首次损失关卡生命时变为2倍",
      "All enemy units have +10% ATK, DEF, and Max HP and the bonus is doubled when you lose Life Points for the first time in each battle",
      "“黎博利的砖石化为齑粉——”",
      "'The Liberi's bricks turn into dust—'",
      enemySpawnStat(
          "敌方生成时攻击力、防御力、最大生命+10%；首次损失目标生命的倍率变化尚未实现",
          "+10% enemy ATK, DEF and maximum HP on spawn; first Life Point loss multiplier pending",
          (enemy, stats) -> enemyCoreStats(stats, 0.10),
          "每场战斗首次损失关卡生命时变为2倍"
      ),
      Rarity.RARE
  );
  public static final CollectibleBuilder RIGHT_EYE_OF_THE_NATATOR = collectible(
      "right_eye_of_the_natator",
      "“游禽的右眼”",
      "所有近卫和狙击干员的攻击力+15%，所有辅助和术师干员的攻击力-5%",
      "Guard and Sniper Operators have +15% ATK, but Supporter and Caster Operators have -5% ATK",
      "高卢皇后冠冕的一部分，现存于伦蒂尼姆皇家博物馆。在得知皇帝逝世的消息后，这位皇后决定同敌人战至最后一刻——为了国家，也为了她离世的爱人。",
      "One half of the Gaulish empress's crown is now housed in the Royal Museum of Londinium. When she learned of the death of the emperor, the empress consort was determined to fight her enemies to the end—for her country, and for her departed beloved.",
      effect(
          "近卫、狙击技能攻击力+15%；辅助、术师技能攻击力-5%",
          "+15% ATK for Guard and Sniper skills; -5% ATK for Supporter and Caster skills",
          statSet(
              forProfession(SkillProfession.GUARD, percent(CombatStat::multiplyAttack, 0.15)),
              forProfession(SkillProfession.SNIPER, percent(CombatStat::multiplyAttack, 0.15)),
              forProfession(SkillProfession.SUPPORTER, percent(CombatStat::multiplyAttack, -0.05)),
              forProfession(SkillProfession.CASTER, percent(CombatStat::multiplyAttack, -0.05))
          )
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder LEFT_EYE_OF_THE_NATATOR = collectible(
      "left_eye_of_the_natator",
      "“游禽的左眼”",
      "所有辅助和术师干员的攻击力+15%，所有近卫和狙击干员的攻击力-5%",
      "Supporter and Caster Operators have +15% ATK, but Guard and Sniper Operators have -5% ATK",
      "高卢皇后冠冕的一部分，现存于莱塔尼亚女皇图书馆。皇后遣走所有侍从，宽恕一切罪犯，随后独自坐在曾属于丈夫的宝座上，等待着入侵者的到来——她决心死于源石结晶，而非敌人的剑刃。",
      "One half of the Gaulish empress's crown is now stored housed in the Leithanian Queen Library. The empress dismissed all her attendants, granted clemency to all criminals, and sat solemnly alone on the throne that was once her husband's, awaiting the invaders—She was determined to die not by at hands of her enemies, but under the effects of Originium crystals.",
      effect(
          "辅助、术师技能攻击力+15%；近卫、狙击技能攻击力-5%",
          "+15% ATK for Supporter and Caster skills; -5% ATK for Guard and Sniper skills",
          statSet(
              forProfession(SkillProfession.SUPPORTER, percent(CombatStat::multiplyAttack, 0.15)),
              forProfession(SkillProfession.CASTER, percent(CombatStat::multiplyAttack, 0.15)),
              forProfession(SkillProfession.GUARD, percent(CombatStat::multiplyAttack, -0.05)),
              forProfession(SkillProfession.SNIPER, percent(CombatStat::multiplyAttack, -0.05))
          )
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder MAGNIFICENT_VISAGE = collectible(
      "magnificent_visage",
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
      "“剑锤”",
      "所有干员的部署费用+5，但攻击力、防御力和生命+10%",
      "All Operators have +5 DP Cost, but gain +10% ATK, DEF, and Max HP",
      "插在巨大石头上的剑，到底是谁这么无聊啊。",
      "A sword lodged in a stone. Was someone bored enough to do this?",
      effect(
          "攻击力、防御力、最大生命+10%；部署费用规则尚未实现",
          "+10% ATK, DEF and maximum HP; DP Cost rule pending",
          statSet(
              percent(CombatStat::multiplyAttack, 0.10),
              percent(CombatStat::multiplyDefense, 0.10),
              percent(CombatStat::multiplyMaxHealth, 0.10)
          ),
          List.of("所有干员的部署费用+5")
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BROKENBLADE = collectible(
      "brokenblade",
      "“断剑”",
      "所有干员的生命-30%，但再部署时间-50%",
      "All Operators have -30% Max HP, but have -50% Redeployment Time",
      "一把折断的剑，姑且能用……大概吧……",
      "A broken sword. It's usable... probably...",
      effect(
          "最大生命-30%；再部署时间规则尚未实现",
          "-30% maximum HP; Redeployment Time rule pending",
          percent(CombatStat::multiplyMaxHealth, -0.30),
          List.of("所有干员的再部署时间-50%")
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder FAMILIAR_SCULPTURE = collectible(
      "familiar_sculpture",
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
      "无用的剪刀",
      "立即获得源石锭+11，希望+3",
      "Immediately adds +11 Originium Ingots and +3 Hope",
      "这把剪刀被使用了太久太久，久到已经没有办法再剪断任何有形之物，久到能将许多无形束缚全部剪碎。",
      "This pair of scissors has been used for far, far too long. So long that it can no longer cut anything tangible, and so long that it can cut all things intangible to shreds.",
      explorationRule("立即获得源石锭+11，希望+3", power -> power.originiumIngots(11).hope(3)),
      Rarity.EPIC
  );
  public static final CollectibleBuilder BLANK_SUICIDE_NOTE = collectible(
      "blank_suicide_note",
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
      "皇族金胸针",
      "所有我方单位的法术抗性+15，且部署后抵挡一次伤害",
      "All allied units have RES +15, and gain one-time dodge after deployment",
      "高卢皇族用于彰显身份的装饰品，由黄金打造，镶嵌了大量昂贵的固化源石结晶。实际上这种胸针不单纯是装饰，许多胸针安装了搭载防御性法术的通用源石回路。",
      "An ornament worn by Gaulish royalty, made from gold and inlaid with expensive Originium crystals. They are more than decoration: many of these brooches hide Originium circuits loaded with defensive Arts.",
      effect(
          "法术抗性+15；部署后抵挡一次伤害尚未实现",
          "+15 RES; one-time post-deployment damage block pending",
          flat(CombatStat::addResistance, 15),
          List.of("部署后抵挡一次伤害")
      ),
      Rarity.EPIC
  );
  public static final CollectibleBuilder TEAR_OF_THE_DEPARTED = collectible(
      "tear_of_the_departed",
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
      "“无度”",
      "每次敌人进入保护目标点时，立刻对全场所有敌人造成3000点真实伤害",
      "Deals 3000 True Damage on all enemy units every time an enemy unit enters the Protection Objective",
      "无光的宝石，如同剧团长本人永无止境的欲望。",
      "A gemstone without luster, just like the endless desires of the troupe's leader.",
      sourceRule("每次敌人进入保护目标点时，立刻对全场所有敌人造成3000点真实伤害"),
      Rarity.EPIC
  );
  // BEGIN GENERATED PRTS ADDITIONAL COLLECTIBLES
  public static final CollectibleBuilder SELECT_CANNED_BEAST_MEAT = create_SELECT_CANNED_BEAST_MEAT();
  public static final CollectibleBuilder CHILLED_SEAWEED_SALAD = create_CHILLED_SEAWEED_SALAD();
  public static final CollectibleBuilder ORANGE_STORM = create_ORANGE_STORM();
  public static final CollectibleBuilder COFFEE_PLAINS_COFFEE_CANDY = create_COFFEE_PLAINS_COFFEE_CANDY();
  public static final CollectibleBuilder SCREAMING_CHERRY = create_SCREAMING_CHERRY();
  public static final CollectibleBuilder PETE_S_FRUIT_MEDLEY = create_PETE_S_FRUIT_MEDLEY();
  public static final CollectibleBuilder EXTRA_PUNGENT_COFFEE_BEANS = create_EXTRA_PUNGENT_COFFEE_BEANS();
  public static final CollectibleBuilder NIGHTSUN_FLOWER = create_NIGHTSUN_FLOWER();
  public static final CollectibleBuilder FLAMECALLER_ORIGINIUM_SLUG = create_FLAMECALLER_ORIGINIUM_SLUG();
  public static final CollectibleBuilder PATHFINDER_FIN = create_PATHFINDER_FIN();
  public static final CollectibleBuilder SEA_TERROR_JERKY = create_SEA_TERROR_JERKY();
  public static final CollectibleBuilder VORTEX_CONFLUENCE = create_VORTEX_CONFLUENCE();
  public static final CollectibleBuilder OCEAN_VOYAGE = create_OCEAN_VOYAGE();
  public static final CollectibleBuilder VIVIPAROUS_LILY = create_VIVIPAROUS_LILY();
  public static final CollectibleBuilder CHITINOUS_RIPPER = create_CHITINOUS_RIPPER();
  public static final CollectibleBuilder BLACK_TULIP = create_BLACK_TULIP();
  public static final CollectibleBuilder DISTANT_HOME_S_GUIDE = create_DISTANT_HOME_S_GUIDE();
  public static final CollectibleBuilder ABANDONED_BANNER = create_ABANDONED_BANNER();
  public static final CollectibleBuilder MEDICINE_STICKS = create_MEDICINE_STICKS();
  public static final CollectibleBuilder FINCATCHER_S_SHAWL = create_FINCATCHER_S_SHAWL();
  public static final CollectibleBuilder GLORIOUS_KAZIMIERZ = create_GLORIOUS_KAZIMIERZ();
  public static final CollectibleBuilder THE_RETURN = create_THE_RETURN();
  public static final CollectibleBuilder CONVALESCENCE_EXPERIENCE_CARD = create_CONVALESCENCE_EXPERIENCE_CARD();
  public static final CollectibleBuilder CONVALESCENCE_PRIME_MEMBERSHIP = create_CONVALESCENCE_PRIME_MEMBERSHIP();
  public static final CollectibleBuilder OLD_GEORGE_NUTRITIONAL_PASTE = create_OLD_GEORGE_NUTRITIONAL_PASTE();
  public static final CollectibleBuilder EMERGENCY_ACTIVE_AGENT = create_EMERGENCY_ACTIVE_AGENT();
  public static final CollectibleBuilder KING_S_BUCKLER = create_KING_S_BUCKLER();
  public static final CollectibleBuilder KING_S_STAFF = create_KING_S_STAFF();
  public static final CollectibleBuilder KING_S_CROWN = create_KING_S_CROWN();
  public static final CollectibleBuilder TIMEWORN_POETRY_STRIPS = create_TIMEWORN_POETRY_STRIPS();
  public static final CollectibleBuilder DERIVATIVE_TERMINAL_SUPERCHARGED_MOD = create_DERIVATIVE_TERMINAL_SUPERCHARGED_MOD();
  public static final CollectibleBuilder UNCLE_VOUGHT_TOTAL_CARE_KIT = create_UNCLE_VOUGHT_TOTAL_CARE_KIT();
  public static final CollectibleBuilder URSUS_FELT_RUG = create_URSUS_FELT_RUG();
  public static final CollectibleBuilder IRON_GUARD_MOVING_FORTRESS = create_IRON_GUARD_MOVING_FORTRESS();
  public static final CollectibleBuilder STALWART_AID_ASSISTANCE = create_STALWART_AID_ASSISTANCE();
  public static final CollectibleBuilder HEALER_S_PATH_RESTORE_SANITY = create_HEALER_S_PATH_RESTORE_SANITY();
  public static final CollectibleBuilder RUSTED_BLADE_EXECUTION_GRINDING = create_RUSTED_BLADE_EXECUTION_GRINDING();
  public static final CollectibleBuilder RUSTED_BLADE_NO_MAN_S_LAND = create_RUSTED_BLADE_NO_MAN_S_LAND();
  public static final CollectibleBuilder HAND_OF_DIFFUSION_EXPLOSIVE = create_HAND_OF_DIFFUSION_EXPLOSIVE();
  public static final CollectibleBuilder HAND_OF_PURIFICATION = create_HAND_OF_PURIFICATION();
  public static final CollectibleBuilder HAND_OF_PULVERIZATION = create_HAND_OF_PULVERIZATION();
  public static final CollectibleBuilder HAND_OF_FLOWING_WATER = create_HAND_OF_FLOWING_WATER();
  public static final CollectibleBuilder GAME_ROOM_ADMIN_ACCESS_CARD = create_GAME_ROOM_ADMIN_ACCESS_CARD();
  public static final CollectibleBuilder TERRAIN_MAP = create_TERRAIN_MAP();
  public static final CollectibleBuilder DIVINE_CONCH = create_DIVINE_CONCH();
  public static final CollectibleBuilder NIGHT_TALK_IN_THE_WOODS = create_NIGHT_TALK_IN_THE_WOODS();
  public static final CollectibleBuilder CHURCH_RELIEF_MEAL_VOUCHER = create_CHURCH_RELIEF_MEAL_VOUCHER();
  public static final CollectibleBuilder ACAHUALLAN_BOWL = create_ACAHUALLAN_BOWL();
  public static final CollectibleBuilder THREE_DIMENSIONAL_ART_DISPLAY = create_THREE_DIMENSIONAL_ART_DISPLAY();
  public static final CollectibleBuilder MULTIPURPOSE_HAIR_CLIP = create_MULTIPURPOSE_HAIR_CLIP();
  public static final CollectibleBuilder THREE_KEY_CONTRACT = create_THREE_KEY_CONTRACT();
  public static final CollectibleBuilder PORTABLE_SCRIPTURES = create_PORTABLE_SCRIPTURES();
  public static final CollectibleBuilder INQUISITOR_S_SCRIPTURE_CLOTH = create_INQUISITOR_S_SCRIPTURE_CLOTH();
  public static final CollectibleBuilder DEEP_SEA_SIRE_SCULPTURE = create_DEEP_SEA_SIRE_SCULPTURE();
  public static final CollectibleBuilder DUCK_LORD_S_GOLDEN_BRICK = create_DUCK_LORD_S_GOLDEN_BRICK();
  public static final CollectibleBuilder DEFENDER_2 = create_DEFENDER_2();
  public static final CollectibleBuilder EMBEDDED_ARMOR_PLATE = create_EMBEDDED_ARMOR_PLATE();
  public static final CollectibleBuilder DARIO_S_LANTERN = create_DARIO_S_LANTERN();
  public static final CollectibleBuilder KING_S_FELLOWSHIP = create_KING_S_FELLOWSHIP();
  public static final CollectibleBuilder TULIP_S_SECRET_FORMULA = create_TULIP_S_SECRET_FORMULA();
  public static final CollectibleBuilder PARAFFIN_AND_BALSAM = create_PARAFFIN_AND_BALSAM();
  public static final CollectibleBuilder NIGHTSUN_GRASS = create_NIGHTSUN_GRASS();
  public static final CollectibleBuilder CATHEDRAL_PUZZLE = create_CATHEDRAL_PUZZLE();
  public static final CollectibleBuilder SUPPORT_REPLENISHMENT_STATION = create_SUPPORT_REPLENISHMENT_STATION();
  public static final CollectibleBuilder SUPPORT_LANDMINE_SET = create_SUPPORT_LANDMINE_SET();
  public static final CollectibleBuilder SUPPORT_MR_BOOM = create_SUPPORT_MR_BOOM();
  public static final CollectibleBuilder SUPPORT_ESCAPE_CRANE = create_SUPPORT_ESCAPE_CRANE();
  public static final CollectibleBuilder SUPPORT_RIOT_PILE = create_SUPPORT_RIOT_PILE();
  public static final CollectibleBuilder SUPPORT_FIRST_AID_KIT = create_SUPPORT_FIRST_AID_KIT();
  public static final CollectibleBuilder PORTRAIT_OF_BREOGAN = create_PORTRAIT_OF_BREOGAN();
  public static final CollectibleBuilder LEVIATHAN_S_ANABOLISM = create_LEVIATHAN_S_ANABOLISM();
  public static final CollectibleBuilder FAILED_SPECIMEN = create_FAILED_SPECIMEN();
  public static final CollectibleBuilder EMERGENCY_LIGHT = create_EMERGENCY_LIGHT();
  public static final CollectibleBuilder LUMINOUS_CORPSE = create_LUMINOUS_CORPSE();
  public static final CollectibleBuilder BISHOP_S_RESEARCH = create_BISHOP_S_RESEARCH();
  public static final CollectibleBuilder CAERULA_ANIMUS = create_CAERULA_ANIMUS();
  public static final CollectibleBuilder BREATH_OF_THE_TIDE = create_BREATH_OF_THE_TIDE();
  public static final CollectibleBuilder REGRESSED_ROCINANTE = create_REGRESSED_ROCINANTE();
  public static final CollectibleBuilder ABSURD_FATE = create_ABSURD_FATE();
  public static final CollectibleBuilder THE_KNIGHT_S_CORPUS = create_THE_KNIGHT_S_CORPUS();
  public static final CollectibleBuilder LAST_REFRAIN = create_LAST_REFRAIN();
  public static final CollectibleBuilder FLAMES_OF_THE_INQUISITION = create_FLAMES_OF_THE_INQUISITION();
  public static final CollectibleBuilder LITTLE_GRAN_FARO = create_LITTLE_GRAN_FARO();
  public static final CollectibleBuilder MEMORIES_OF_THE_SHARD = create_MEMORIES_OF_THE_SHARD();
  public static final CollectibleBuilder GLORY_PACK = create_GLORY_PACK();
  public static final CollectibleBuilder SUI_S_WRATH = create_SUI_S_WRATH();
  public static final CollectibleBuilder BEDROCK = create_BEDROCK();
  public static final CollectibleBuilder WAVEBREAKER = create_WAVEBREAKER();
  public static final CollectibleBuilder PULSE_OF_THE_OCEAN = create_PULSE_OF_THE_OCEAN();
  public static final CollectibleBuilder DETERMINATION = create_DETERMINATION();
  public static final CollectibleBuilder VIGIL = create_VIGIL();
  public static final CollectibleBuilder HESITATION = create_HESITATION();
  public static final CollectibleBuilder ENDLESS_LIFE = create_ENDLESS_LIFE();
  public static final CollectibleBuilder CAERULA_MEMORY = create_CAERULA_MEMORY();
  public static final CollectibleBuilder CAERULA_ARBOR = create_CAERULA_ARBOR();
  public static final CollectibleBuilder HR_BRONZE_SEAL = create_HR_BRONZE_SEAL();
  public static final CollectibleBuilder DOCTOR_SILVER_SEAL = create_DOCTOR_SILVER_SEAL();
  public static final CollectibleBuilder WORDLESS_CORAL = create_WORDLESS_CORAL();
  public static final CollectibleBuilder UNDERGROUND_SCORCH = create_UNDERGROUND_SCORCH();
  public static final CollectibleBuilder GREAT_MACHINATIONS = create_GREAT_MACHINATIONS();
  public static final CollectibleBuilder SOLIDIFIED_LAMP_OIL = create_SOLIDIFIED_LAMP_OIL();
  public static final CollectibleBuilder SANCTIFIED_LAMP_OIL = create_SANCTIFIED_LAMP_OIL();
  public static final CollectibleBuilder KING_S_CRYSTAL = create_KING_S_CRYSTAL();
  public static final CollectibleBuilder HAND_OF_BRANDING = create_HAND_OF_BRANDING();
  public static final CollectibleBuilder WRANKWOOD_SAFETY_SUIT = create_WRANKWOOD_SAFETY_SUIT();
  public static final CollectibleBuilder FLASHING_SWORDS = create_FLASHING_SWORDS();
  public static final CollectibleBuilder MERCENARY_INSURANCE = create_MERCENARY_INSURANCE();
  public static final CollectibleBuilder BROKEN_WAND_LEARNING = create_BROKEN_WAND_LEARNING();
  public static final CollectibleBuilder BROKEN_WAND_LIGHT_OF_WISDOM = create_BROKEN_WAND_LIGHT_OF_WISDOM();
  public static final CollectibleBuilder BROKEN_WAND_SOLVING = create_BROKEN_WAND_SOLVING();
  public static final CollectibleBuilder IRON_GUARD_WALL = create_IRON_GUARD_WALL();
  public static final CollectibleBuilder IRON_GUARD_TOWER = create_IRON_GUARD_TOWER();
  public static final CollectibleBuilder SURVIVOR_CONTRACT = create_SURVIVOR_CONTRACT();
  public static final CollectibleBuilder FOSTERER_GENOTYPE = create_FOSTERER_GENOTYPE();
  public static final CollectibleBuilder SPECTRUM_ANALYZER = create_SPECTRUM_ANALYZER();
  public static final CollectibleBuilder LEISURE_TIME = create_LEISURE_TIME();
  public static final CollectibleBuilder TRANQUIL_SPRING_CURRENT = create_TRANQUIL_SPRING_CURRENT();
  public static final CollectibleBuilder DEVOTION = create_DEVOTION();
  public static final CollectibleBuilder CELESTIAL_DUST = create_CELESTIAL_DUST();
  public static final CollectibleBuilder FROZEN_HUSK = create_FROZEN_HUSK();
  public static final CollectibleBuilder RING_OF_THORNS = create_RING_OF_THORNS();
  public static final CollectibleBuilder PEEK_INTO_THE_ETERNAL_NIGHT = create_PEEK_INTO_THE_ETERNAL_NIGHT();
  public static final CollectibleBuilder DEAD_TREE_S_ECHO = create_DEAD_TREE_S_ECHO();
  public static final CollectibleBuilder FROZEN_WHETSTONE = create_FROZEN_WHETSTONE();
  public static final CollectibleBuilder CLAIRVOYANT_S_REVEAL = create_CLAIRVOYANT_S_REVEAL();
  public static final CollectibleBuilder WEAVE_OF_THORNS_AND_LEAVES = create_WEAVE_OF_THORNS_AND_LEAVES();
  public static final CollectibleBuilder LIVING_WOODPLATE = create_LIVING_WOODPLATE();
  public static final CollectibleBuilder FINSHELL_SHIELD = create_FINSHELL_SHIELD();
  public static final CollectibleBuilder FROST_BUCK_S_PAULDRON = create_FROST_BUCK_S_PAULDRON();
  public static final CollectibleBuilder SNOW_DOE_S_GLOVE = create_SNOW_DOE_S_GLOVE();
  public static final CollectibleBuilder EMPTY_FOWLBEAST = create_EMPTY_FOWLBEAST();
  public static final CollectibleBuilder ROCK_HORN = create_ROCK_HORN();
  public static final CollectibleBuilder INEXTINGUISHABLE_TORCH = create_INEXTINGUISHABLE_TORCH();
  public static final CollectibleBuilder LAKEBED_AEGIS = create_LAKEBED_AEGIS();
  public static final CollectibleBuilder ANCIENT_FRESCO = create_ANCIENT_FRESCO();
  public static final CollectibleBuilder GALLERIA_STELLARIA_S_SPLENDOR = create_GALLERIA_STELLARIA_S_SPLENDOR();
  public static final CollectibleBuilder CLOUD_MOVING_TOTEM = create_CLOUD_MOVING_TOTEM();
  public static final CollectibleBuilder IRON_GUARD_CONSOLIDATION = create_IRON_GUARD_CONSOLIDATION();
  public static final CollectibleBuilder RUSTED_BLADE_SPEARHEAD_SHARPENING = create_RUSTED_BLADE_SPEARHEAD_SHARPENING();
  public static final CollectibleBuilder HAND_OF_FISTICUFFS = create_HAND_OF_FISTICUFFS();
  public static final CollectibleBuilder HAND_OF_ADAPTABILITY = create_HAND_OF_ADAPTABILITY();
  public static final CollectibleBuilder HAND_OF_OUTBURST = create_HAND_OF_OUTBURST();
  public static final CollectibleBuilder KNIGHT_LANCE_PRO = create_KNIGHT_LANCE_PRO();
  public static final CollectibleBuilder FULL_AUTO_MAINTENANCE_STATION = create_FULL_AUTO_MAINTENANCE_STATION();
  public static final CollectibleBuilder WARNING_FENCE = create_WARNING_FENCE();
  public static final CollectibleBuilder HEAVY_MUSIC_ANTHOLOGY = create_HEAVY_MUSIC_ANTHOLOGY();
  public static final CollectibleBuilder WANDERING_CASTER_S_NOSTALGIA = create_WANDERING_CASTER_S_NOSTALGIA();
  public static final CollectibleBuilder CEREMONY_BELL = create_CEREMONY_BELL();
  public static final CollectibleBuilder NORTHERN_PIONEER_S_CRUTCH = create_NORTHERN_PIONEER_S_CRUTCH();
  public static final CollectibleBuilder HUNTERS_INSIGHT = create_HUNTERS_INSIGHT();
  public static final CollectibleBuilder WEAVE_OF_SNOW_AND_SOIL = create_WEAVE_OF_SNOW_AND_SOIL();
  public static final CollectibleBuilder ANCIENT_TREE_FRUIT = create_ANCIENT_TREE_FRUIT();
  public static final CollectibleBuilder POLAR_RETROFIT_PACKAGE = create_POLAR_RETROFIT_PACKAGE();
  public static final CollectibleBuilder ROUNDSTONE_ALTAR = create_ROUNDSTONE_ALTAR();
  public static final CollectibleBuilder RAINBOW_JAR = create_RAINBOW_JAR();
  public static final CollectibleBuilder SPROUTED_STICK = create_SPROUTED_STICK();
  public static final CollectibleBuilder CANNOT_S_MARK = create_CANNOT_S_MARK();
  public static final CollectibleBuilder GUIDING_VINEDOLL = create_GUIDING_VINEDOLL();
  public static final CollectibleBuilder BROKEN_URSUS_BLADE = create_BROKEN_URSUS_BLADE();
  public static final CollectibleBuilder SLYTOOTH_FOREST_DESTROYER = create_SLYTOOTH_FOREST_DESTROYER();
  public static final CollectibleBuilder SUPPORT_FOG_MACHINE = create_SUPPORT_FOG_MACHINE();
  public static final CollectibleBuilder ALTAR_TYPE_RADAR = create_ALTAR_TYPE_RADAR();
  public static final CollectibleBuilder R_45_PORTABLE_GRAMOPHONE = create_R_45_PORTABLE_GRAMOPHONE();
  public static final CollectibleBuilder ORNAMENTAL_GIANT_MUSHROOM = create_ORNAMENTAL_GIANT_MUSHROOM();
  public static final CollectibleBuilder FIELD_DEVICE_LUD_99X = create_FIELD_DEVICE_LUD_99X();
  public static final CollectibleBuilder TWO_STEP_FIRECRACKER_AND_DATA_COLLECTOR = create_TWO_STEP_FIRECRACKER_AND_DATA_COLLECTOR();
  public static final CollectibleBuilder SELF_DRIVING_RECON_CART = create_SELF_DRIVING_RECON_CART();
  public static final CollectibleBuilder TREESCAR_HELM = create_TREESCAR_HELM();
  public static final CollectibleBuilder BOUNDLESS_GIFT = create_BOUNDLESS_GIFT();
  public static final CollectibleBuilder ROUTEWEAVE_NET = create_ROUTEWEAVE_NET();
  public static final CollectibleBuilder BLINDNESS = create_BLINDNESS();
  public static final CollectibleBuilder SCARRED_AMBER = create_SCARRED_AMBER();
  public static final CollectibleBuilder AMMA_S_AFFECTION = create_AMMA_S_AFFECTION();
  public static final CollectibleBuilder HORIZON_INVITATION = create_HORIZON_INVITATION();
  public static final CollectibleBuilder DIMENSIONAL_FLUID = create_DIMENSIONAL_FLUID();
  public static final CollectibleBuilder COLLAPSAL_SEED = create_COLLAPSAL_SEED();
  public static final CollectibleBuilder FRAGMENT_OF_SPACE = create_FRAGMENT_OF_SPACE();
  public static final CollectibleBuilder PROFOUND_SCORCHMARKS = create_PROFOUND_SCORCHMARKS();
  public static final CollectibleBuilder HAND_OF_UNYIELDING = create_HAND_OF_UNYIELDING();
  public static final CollectibleBuilder HAND_OF_RETURN = create_HAND_OF_RETURN();
  public static final CollectibleBuilder BLUNT_CLAWS_FUEL = create_BLUNT_CLAWS_FUEL();
  public static final CollectibleBuilder BEND_SPEARS_BATTLECRY = create_BEND_SPEARS_BATTLECRY();
  public static final CollectibleBuilder IRON_GUARD_RAMPART = create_IRON_GUARD_RAMPART();
  public static final CollectibleBuilder FATAL_BOLTS_SUCCESSION = create_FATAL_BOLTS_SUCCESSION();
  public static final CollectibleBuilder BROKEN_WAND_HARMONY = create_BROKEN_WAND_HARMONY();
  public static final CollectibleBuilder STALWART_AID_LINKUP = create_STALWART_AID_LINKUP();
  public static final CollectibleBuilder HEALER_S_PATH_WELLSPRING = create_HEALER_S_PATH_WELLSPRING();
  public static final CollectibleBuilder RUSTED_BLADE_DISTURBANCE = create_RUSTED_BLADE_DISTURBANCE();
  public static final CollectibleBuilder PALMTOP_PAVILION = create_PALMTOP_PAVILION();
  public static final CollectibleBuilder MINOAN_ODE = create_MINOAN_ODE();
  public static final CollectibleBuilder FRISTON_P = create_FRISTON_P();
  public static final CollectibleBuilder VINECREEP_MORTAR_GUNNER = create_VINECREEP_MORTAR_GUNNER();
  public static final CollectibleBuilder NORTHWIND_CONSTRUCT = create_NORTHWIND_CONSTRUCT();
  public static final CollectibleBuilder SHATTERED_ALLIANCE_TREATY = create_SHATTERED_ALLIANCE_TREATY();
  public static final CollectibleBuilder PARADIGM_APPARATUS = create_PARADIGM_APPARATUS();
  public static final CollectibleBuilder EXPEDITIONER_S_FIELD_PACK = create_EXPEDITIONER_S_FIELD_PACK();
  public static final CollectibleBuilder BLACK_HOLE_PROTOCOL = create_BLACK_HOLE_PROTOCOL();
  public static final CollectibleBuilder HIBERNATING_KIN = create_HIBERNATING_KIN();
  public static final CollectibleBuilder BAG_OF_PLANS = create_BAG_OF_PLANS();
  public static final CollectibleBuilder GIFT_OF_REMEMBRANCE = create_GIFT_OF_REMEMBRANCE();
  public static final CollectibleBuilder CURSED_WAR_CHRONICLES = create_CURSED_WAR_CHRONICLES();
  public static final CollectibleBuilder LOST_KEY = create_LOST_KEY();
  public static final CollectibleBuilder NAMELESS_TOTEM = create_NAMELESS_TOTEM();
  public static final CollectibleBuilder REVENANT_REMNANT = create_REVENANT_REMNANT();
  public static final CollectibleBuilder MAGIC_BOX = create_MAGIC_BOX();
  public static final CollectibleBuilder HALF_EATEN_CANDY = create_HALF_EATEN_CANDY();
  public static final CollectibleBuilder DAWN_OF_LITERATURE = create_DAWN_OF_LITERATURE();
  public static final CollectibleBuilder GRAVITY_DEFYING_MACHINE = create_GRAVITY_DEFYING_MACHINE();
  public static final CollectibleBuilder HALO = create_HALO();
  public static final CollectibleBuilder BLUNT_CLAWS_PREDATION = create_BLUNT_CLAWS_PREDATION();
  public static final CollectibleBuilder BEND_SPEARS_RENDING = create_BEND_SPEARS_RENDING();
  public static final CollectibleBuilder BROKEN_WAND_UNDULATION = create_BROKEN_WAND_UNDULATION();
  public static final CollectibleBuilder RUSTED_BLADE_PROTRACTION = create_RUSTED_BLADE_PROTRACTION();
  public static final CollectibleBuilder RUSTED_BLADE_LONE_FORCE = create_RUSTED_BLADE_LONE_FORCE();
  public static final CollectibleBuilder HAND_OF_PREDATION = create_HAND_OF_PREDATION();
  public static final CollectibleBuilder HAND_OF_RENDING = create_HAND_OF_RENDING();
  public static final CollectibleBuilder HAND_OF_UNDULATION = create_HAND_OF_UNDULATION();
  public static final CollectibleBuilder HAND_OF_PROTRACTION = create_HAND_OF_PROTRACTION();
  public static final CollectibleBuilder HAND_OF_MYSTERY = create_HAND_OF_MYSTERY();
  public static final CollectibleBuilder HAND_OF_RUMBLE = create_HAND_OF_RUMBLE();
  public static final CollectibleBuilder BRIDGE_OF_KNOWLEDGE = create_BRIDGE_OF_KNOWLEDGE();
  public static final CollectibleBuilder MERCENARY_S_ACCESSORY = create_MERCENARY_S_ACCESSORY();
  public static final CollectibleBuilder CHILDREN_OF_THE_WALLS = create_CHILDREN_OF_THE_WALLS();
  public static final CollectibleBuilder KING_S_ARMOR = create_KING_S_ARMOR();
  public static final CollectibleBuilder KING_S_LEGACY = create_KING_S_LEGACY();
  public static final CollectibleBuilder PERSONAL_WITCHCRAFT_TERMINAL = create_PERSONAL_WITCHCRAFT_TERMINAL();
  public static final CollectibleBuilder PRIMORDIAL_VESTIGE = create_PRIMORDIAL_VESTIGE();
  public static final CollectibleBuilder DISASTER_S_ORIGIN = create_DISASTER_S_ORIGIN();
  public static final CollectibleBuilder SLAVE_HUNTER = create_SLAVE_HUNTER();
  public static final CollectibleBuilder SARKAZ_KING_S_REGAL_REST = create_SARKAZ_KING_S_REGAL_REST();
  public static final CollectibleBuilder SARKAZ_KING_S_TORN_BANNER = create_SARKAZ_KING_S_TORN_BANNER();
  public static final CollectibleBuilder COMMANDER_S_PORTRAIT = create_COMMANDER_S_PORTRAIT();
  public static final CollectibleBuilder SOUL_BINDING_BONE = create_SOUL_BINDING_BONE();
  public static final CollectibleBuilder SOUL_FURNACE_S_FUEL = create_SOUL_FURNACE_S_FUEL();
  public static final CollectibleBuilder MASTERLESS_MEMORIES = create_MASTERLESS_MEMORIES();
  public static final CollectibleBuilder HERR_HRAASELSUHER = create_HERR_HRAASELSUHER();
  public static final CollectibleBuilder SONG_TO_MAKE_A_TENACIOUS_MIND = create_SONG_TO_MAKE_A_TENACIOUS_MIND();
  public static final CollectibleBuilder ANCHOR_FOR_FIVE_SECONDS_AGO = create_ANCHOR_FOR_FIVE_SECONDS_AGO();
  public static final CollectibleBuilder PERSONAL_PAINTBRUSH = create_PERSONAL_PAINTBRUSH();
  public static final CollectibleBuilder SCALES_OF_AVARICE = create_SCALES_OF_AVARICE();
  public static final CollectibleBuilder WORD_CARVING_KNIFE = create_WORD_CARVING_KNIFE();
  public static final CollectibleBuilder THOUGHTS_CATCHER = create_THOUGHTS_CATCHER();
  public static final CollectibleBuilder DOODLE_OF_HOPE = create_DOODLE_OF_HOPE();
  public static final CollectibleBuilder TALONS_OF_HATRED = create_TALONS_OF_HATRED();
  public static final CollectibleBuilder BURDENHERD_BELL = create_BURDENHERD_BELL();
  public static final CollectibleBuilder LAMP_OF_WISHES = create_LAMP_OF_WISHES();
  public static final CollectibleBuilder ROLLING_ANCESTORS = create_ROLLING_ANCESTORS();
  public static final CollectibleBuilder BLOOD_TAX_MYSTERY = create_BLOOD_TAX_MYSTERY();
  public static final CollectibleBuilder CRYSTAL_MYSTERY = create_CRYSTAL_MYSTERY();
  public static final CollectibleBuilder SUSPICION_MYSTERY = create_SUSPICION_MYSTERY();
  public static final CollectibleBuilder FABRICATION_MYSTERY = create_FABRICATION_MYSTERY();
  public static final CollectibleBuilder FORESIGHT_MYSTERY = create_FORESIGHT_MYSTERY();
  public static final CollectibleBuilder SIGIL_MYSTERY = create_SIGIL_MYSTERY();
  public static final CollectibleBuilder TRANQUIL_MYSTERY = create_TRANQUIL_MYSTERY();
  public static final CollectibleBuilder SILK_BOND_MYSTERY = create_SILK_BOND_MYSTERY();
  public static final CollectibleBuilder PLEDGE_OF_BABEL = create_PLEDGE_OF_BABEL();
  public static final CollectibleBuilder PROPHET_HORN = create_PROPHET_HORN();
  public static final CollectibleBuilder TEN_RINGS = create_TEN_RINGS();
  public static final CollectibleBuilder TIME_AND_LIGHT = create_TIME_AND_LIGHT();
  public static final CollectibleBuilder PETAL = create_PETAL();
  public static final CollectibleBuilder ANASA_S_KARMA = create_ANASA_S_KARMA();
  public static final CollectibleBuilder PROPHETIC_IMAGE = create_PROPHETIC_IMAGE();
  public static final CollectibleBuilder ENDLESS_KEY = create_ENDLESS_KEY();
  public static final CollectibleBuilder FRAMEWORK_OF_THE_END = create_FRAMEWORK_OF_THE_END();
  public static final CollectibleBuilder BODY_OF_THE_END = create_BODY_OF_THE_END();
  public static final CollectibleBuilder REALITY_OF_THE_END = create_REALITY_OF_THE_END();
  public static final CollectibleBuilder DEVILBANE_BANNER = create_DEVILBANE_BANNER();
  public static final CollectibleBuilder HOLY_CITY_S_EMBRACE = create_HOLY_CITY_S_EMBRACE();
  public static final CollectibleBuilder LITTLE_CUBE = create_LITTLE_CUBE();
  public static final CollectibleBuilder GREAT_BANSHEE_S_VEIL = create_GREAT_BANSHEE_S_VEIL();
  public static final CollectibleBuilder FEARLESS_BLADE = create_FEARLESS_BLADE();
  public static final CollectibleBuilder ARCH_GLYPH = create_ARCH_GLYPH();
  public static final CollectibleBuilder GUL_DUL_S_SILENCE = create_GUL_DUL_S_SILENCE();
  public static final CollectibleBuilder QUI_SARTUSTAJ_S_PROMISE = create_QUI_SARTUSTAJ_S_PROMISE();
  public static final CollectibleBuilder BALOR_SACA_S_ARROGANCE = create_BALOR_SACA_S_ARROGANCE();
  public static final CollectibleBuilder YLIS_S_RAVINGS = create_YLIS_S_RAVINGS();
  public static final CollectibleBuilder SHARD_OF_THE_UNTOLD_KINGS = create_SHARD_OF_THE_UNTOLD_KINGS();
  public static final CollectibleBuilder JUDGEMENT_GUARD = create_JUDGEMENT_GUARD();
  public static final CollectibleBuilder JUDGEMENT_VANGUARD = create_JUDGEMENT_VANGUARD();
  public static final CollectibleBuilder JUDGEMENT_DEFENDER = create_JUDGEMENT_DEFENDER();
  public static final CollectibleBuilder JUDGEMENT_SNIPER = create_JUDGEMENT_SNIPER();
  public static final CollectibleBuilder JUDGEMENT_SPECIALIST = create_JUDGEMENT_SPECIALIST();
  public static final CollectibleBuilder JUDGEMENT_MEDIC = create_JUDGEMENT_MEDIC();
  public static final CollectibleBuilder JUDGEMENT_CASTER = create_JUDGEMENT_CASTER();
  public static final CollectibleBuilder JUDGEMENT_SUPPORTER = create_JUDGEMENT_SUPPORTER();
  public static final CollectibleBuilder HAND_OF_FIREWORKS = create_HAND_OF_FIREWORKS();
  public static final CollectibleBuilder CLOTHESKAZ = create_CLOTHESKAZ();
  public static final CollectibleBuilder UNRIPE_YEARNING = create_UNRIPE_YEARNING();
  public static final CollectibleBuilder CURSED_COUNTERBEAST = create_CURSED_COUNTERBEAST();
  public static final CollectibleBuilder RHODER_S_GATE = create_RHODER_S_GATE();
  public static final CollectibleBuilder VISAGE_OF_CROWNED_FATES = create_VISAGE_OF_CROWNED_FATES();
  public static final CollectibleBuilder KING_OF_SARKAZ_S_VESSEL = create_KING_OF_SARKAZ_S_VESSEL();
  public static final CollectibleBuilder EXPUNGER_OF_HATRED = create_EXPUNGER_OF_HATRED();
  public static final CollectibleBuilder BABY_DJALL = create_BABY_DJALL();
  public static final CollectibleBuilder EYE_OF_FORTUNE = create_EYE_OF_FORTUNE();
  public static final CollectibleBuilder WONDROUS_GRAFFITI = create_WONDROUS_GRAFFITI();
  public static final CollectibleBuilder OSMANTHUS_GOBLET = create_OSMANTHUS_GOBLET();
  public static final CollectibleBuilder ENLISTMENT_ORDER = create_ENLISTMENT_ORDER();
  public static final CollectibleBuilder GEIST_BINDING_ROPE = create_GEIST_BINDING_ROPE();
  public static final CollectibleBuilder AH_MENG = create_AH_MENG();
  public static final CollectibleBuilder EVALUATION_MINISTER = create_EVALUATION_MINISTER();
  public static final CollectibleBuilder BORDERBOUND_MIRROR = create_BORDERBOUND_MIRROR();
  public static final CollectibleBuilder EVERLASTING_GUIDING_LIGHT = create_EVERLASTING_GUIDING_LIGHT();
  public static final CollectibleBuilder FIREFLY_BOOKLIGHT = create_FIREFLY_BOOKLIGHT();
  public static final CollectibleBuilder TOY_SLINGSHOT = create_TOY_SLINGSHOT();
  public static final CollectibleBuilder SNACK_BASKET = create_SNACK_BASKET();
  public static final CollectibleBuilder REMNANT_SPRING = create_REMNANT_SPRING();
  public static final CollectibleBuilder WALLEYE = create_WALLEYE();
  public static final CollectibleBuilder WOODBLOCK_PRINTS = create_WOODBLOCK_PRINTS();
  public static final CollectibleBuilder THREE_FEET_TO_ALL = create_THREE_FEET_TO_ALL();
  public static final CollectibleBuilder YANESE_DICTIONARY = create_YANESE_DICTIONARY();
  public static final CollectibleBuilder COLLECTION_OF_ELEGANCE = create_COLLECTION_OF_ELEGANCE();
  public static final CollectibleBuilder A_JADE_JUE = create_A_JADE_JUE();
  public static final CollectibleBuilder OLD_SANDAL = create_OLD_SANDAL();
  public static final CollectibleBuilder YI_LOCK = create_YI_LOCK();
  public static final CollectibleBuilder COMPLEX_YI_LOCK = create_COMPLEX_YI_LOCK();
  public static final CollectibleBuilder MOTHER_CAULDRON = create_MOTHER_CAULDRON();
  public static final CollectibleBuilder DELICATE_BAMBOO = create_DELICATE_BAMBOO();
  public static final CollectibleBuilder INTRO_TO_WEIQI_OPENINGS = create_INTRO_TO_WEIQI_OPENINGS();
  public static final CollectibleBuilder BROCADE_BANNER = create_BROCADE_BANNER();
  public static final CollectibleBuilder DARK_RED_MASK = create_DARK_RED_MASK();
  public static final CollectibleBuilder ELDER_MASK = create_ELDER_MASK();
  public static final CollectibleBuilder CHARCOAL_MASK = create_CHARCOAL_MASK();
  public static final CollectibleBuilder MOON_LADY_MASK = create_MOON_LADY_MASK();
  public static final CollectibleBuilder FIST_CLASSICS_123 = create_FIST_CLASSICS_123();
  public static final CollectibleBuilder INK_OF_DAWN_AND_DUSK = create_INK_OF_DAWN_AND_DUSK();
  public static final CollectibleBuilder CHAMBER_OF_GEISTS_LEDGER = create_CHAMBER_OF_GEISTS_LEDGER();
  public static final CollectibleBuilder TRAINING_LIGHTNING_ROD = create_TRAINING_LIGHTNING_ROD();
  public static final CollectibleBuilder FIRE_POKING_STICK = create_FIRE_POKING_STICK();
  public static final CollectibleBuilder IGNITED_BEASTKITE = create_IGNITED_BEASTKITE();
  public static final CollectibleBuilder INTERDEPENDENT_LUCK = create_INTERDEPENDENT_LUCK();
  public static final CollectibleBuilder FAMILY_STIR_FRY = create_FAMILY_STIR_FRY();
  public static final CollectibleBuilder RHODES_ISLAND_EMERGENCY_RESCUE_VEHICLE = create_RHODES_ISLAND_EMERGENCY_RESCUE_VEHICLE();
  public static final CollectibleBuilder WOODEN_LUMP = create_WOODEN_LUMP();
  public static final CollectibleBuilder QUICKBEAST_TRANSPORT = create_QUICKBEAST_TRANSPORT();
  public static final CollectibleBuilder BIG_BOB_S_CERTIFICATE = create_BIG_BOB_S_CERTIFICATE();
  public static final CollectibleBuilder ROUND_WOODWHEEL = create_ROUND_WOODWHEEL();
  public static final CollectibleBuilder DUO_CARRIER_SEDAN = create_DUO_CARRIER_SEDAN();
  public static final CollectibleBuilder SCREECHING_BLOWGUN = create_SCREECHING_BLOWGUN();
  public static final CollectibleBuilder STRANGE_PICKY_STATUE = create_STRANGE_PICKY_STATUE();
  public static final CollectibleBuilder VARIED_FORTUNE = create_VARIED_FORTUNE();
  public static final CollectibleBuilder BREWED_REALM = create_BREWED_REALM();
  public static final CollectibleBuilder REMNANT_FLAMES_AND_DREAMS = create_REMNANT_FLAMES_AND_DREAMS();
  public static final CollectibleBuilder METAL_EXPEDITION_DRUM = create_METAL_EXPEDITION_DRUM();
  public static final CollectibleBuilder PLUCKED_BALANCE = create_PLUCKED_BALANCE();
  public static final CollectibleBuilder PLUCKED_FLOWER = create_PLUCKED_FLOWER();
  public static final CollectibleBuilder PLUCKED_RISK = create_PLUCKED_RISK();
  public static final CollectibleBuilder THROWN_BALANCE = create_THROWN_BALANCE();
  public static final CollectibleBuilder THROWN_FLOWER = create_THROWN_FLOWER();
  public static final CollectibleBuilder THROWN_RISK = create_THROWN_RISK();
  public static final CollectibleBuilder SEEN_BALANCE = create_SEEN_BALANCE();
  public static final CollectibleBuilder SEEN_FLOWER = create_SEEN_FLOWER();
  public static final CollectibleBuilder SEEN_RISK = create_SEEN_RISK();
  public static final CollectibleBuilder SUI_S_BALANCE = create_SUI_S_BALANCE();
  public static final CollectibleBuilder SUI_S_FLOWER = create_SUI_S_FLOWER();
  public static final CollectibleBuilder SUI_S_RISK = create_SUI_S_RISK();
  public static final CollectibleBuilder INK_OF_THE_HIDDEN_BUTTERFLY = create_INK_OF_THE_HIDDEN_BUTTERFLY();
  public static final CollectibleBuilder CARVED_INKSTICK_REMNANT = create_CARVED_INKSTICK_REMNANT();
  public static final CollectibleBuilder CLOUD_AND_PAINT = create_CLOUD_AND_PAINT();
  public static final CollectibleBuilder UNFLINCHING_MASTERPLAN = create_UNFLINCHING_MASTERPLAN();
  public static final CollectibleBuilder UNSEALED_CASE = create_UNSEALED_CASE();
  public static final CollectibleBuilder INKLESS_SCROLL = create_INKLESS_SCROLL();
  public static final CollectibleBuilder MEMORY_SCOPE = create_MEMORY_SCOPE();
  public static final CollectibleBuilder LITTLE_MOJI = create_LITTLE_MOJI();
  public static final CollectibleBuilder MERCILESS = create_MERCILESS();
  public static final CollectibleBuilder CEASELESS = create_CEASELESS();
  public static final CollectibleBuilder GALLOPBEAST_CHARIOT = create_GALLOPBEAST_CHARIOT();
  public static final CollectibleBuilder RAMPART_BURDENBEAST = create_RAMPART_BURDENBEAST();
  public static final CollectibleBuilder LITTLE_BAIZAO = create_LITTLE_BAIZAO();
  public static final CollectibleBuilder CANGTONG = create_CANGTONG();
  public static final CollectibleBuilder COIN_GEISTS = create_COIN_GEISTS();
  public static final CollectibleBuilder FOUR_CORNERS_PAINT = create_FOUR_CORNERS_PAINT();
  public static final CollectibleBuilder FOUR_SEASONS_BRUSH = create_FOUR_SEASONS_BRUSH();
  public static final CollectibleBuilder SOLDIER_SHADOW = create_SOLDIER_SHADOW();
  public static final CollectibleBuilder CRAFTSMAN_SHADOW = create_CRAFTSMAN_SHADOW();
  public static final CollectibleBuilder ARTIST_SHADOW = create_ARTIST_SHADOW();
  public static final CollectibleBuilder MERCHANT_SHADOW = create_MERCHANT_SHADOW();
  public static final CollectibleBuilder DEEPWATER_TALISMAN = create_DEEPWATER_TALISMAN();
  public static final CollectibleBuilder THREE_LEGGED_GOLDFOWL = create_THREE_LEGGED_GOLDFOWL();
  public static final CollectibleBuilder RAIDIAN_S_HANDHELD_CONSOLE = create_RAIDIAN_S_HANDHELD_CONSOLE();
  public static final CollectibleBuilder BELLS_OF_AGREEMENT = create_BELLS_OF_AGREEMENT();
  public static final CollectibleBuilder TZU_WU_BLADE = create_TZU_WU_BLADE();
  public static final CollectibleBuilder WU_SHANG_SHUTTLE = create_WU_SHANG_SHUTTLE();
  public static final CollectibleBuilder SHEN_CHU_WHEEL = create_SHEN_CHU_WHEEL();
  public static final CollectibleBuilder HAI_SHIH_STOVE = create_HAI_SHIH_STOVE();
  public static final CollectibleBuilder YIN_SHIH_FLASK = create_YIN_SHIH_FLASK();
  public static final CollectibleBuilder WEI_CHIEN_RULE = create_WEI_CHIEN_RULE();
  public static final CollectibleBuilder GREAT_TOMB_WHISTLE = create_GREAT_TOMB_WHISTLE();
  public static final CollectibleBuilder SMOKY_CANDLE = create_SMOKY_CANDLE();
  public static final CollectibleBuilder BROKEN_WAND_ABSORB = create_BROKEN_WAND_ABSORB();
  public static final CollectibleBuilder COURT_STRIKING_BLOCK = create_COURT_STRIKING_BLOCK();
  public static final CollectibleBuilder BLAZE_S_CHAINSAW = create_BLAZE_S_CHAINSAW();
  public static final CollectibleBuilder SAWBLADE_BANGLE = create_SAWBLADE_BANGLE();
  public static final CollectibleBuilder OLD_MILLSTONE = create_OLD_MILLSTONE();
  public static final CollectibleBuilder CROWING_ROOFTOP_FIGURE = create_CROWING_ROOFTOP_FIGURE();
  public static final CollectibleBuilder HEAVEN_AXE = create_HEAVEN_AXE();
  public static final CollectibleBuilder YARN_OF_REAL_AND_UNREAL = create_YARN_OF_REAL_AND_UNREAL();
  public static final CollectibleBuilder SILK_KNOT = create_SILK_KNOT();
  public static final CollectibleBuilder FRAGMENT_OF_JU_S_ORDER = create_FRAGMENT_OF_JU_S_ORDER();
  public static final CollectibleBuilder FRAGMENT_OF_HOU_S_SOIL = create_FRAGMENT_OF_HOU_S_SOIL();
  public static final CollectibleBuilder VICTORY_GUQIN = create_VICTORY_GUQIN();
  public static final CollectibleBuilder PANACEA = create_PANACEA();
  public static final CollectibleBuilder WAKING_TOWER = create_WAKING_TOWER();
  public static final CollectibleBuilder COIN_PLATTER = create_COIN_PLATTER();
  public static final CollectibleBuilder LITTLE_BA_JIE_STICKER = create_LITTLE_BA_JIE_STICKER();
  public static final CollectibleBuilder SOLDIER_S_EDGE = create_SOLDIER_S_EDGE();
  public static final CollectibleBuilder SWADDLED_PEGASUS = create_SWADDLED_PEGASUS();
  public static final CollectibleBuilder SWADDLED_EAGLE = create_SWADDLED_EAGLE();
  public static final CollectibleBuilder SWADDLED_DRAGON = create_SWADDLED_DRAGON();
  public static final CollectibleBuilder SWADDLED_HYDRA = create_SWADDLED_HYDRA();
  public static final CollectibleBuilder SWADDLED_BAI_ZE = create_SWADDLED_BAI_ZE();
  public static final CollectibleBuilder SWADDLED_GOLDEN_CROW = create_SWADDLED_GOLDEN_CROW();
  public static final CollectibleBuilder RED_SUN_CROWN = create_RED_SUN_CROWN();
  public static final CollectibleBuilder NIGHT_SHAWL = create_NIGHT_SHAWL();
  public static final CollectibleBuilder FALSE_WINGS = create_FALSE_WINGS();
  public static final CollectibleBuilder THE_DESIRE_TO_BITE = create_THE_DESIRE_TO_BITE();
  public static final CollectibleBuilder FRAGRANT_HORN = create_FRAGRANT_HORN();
  public static final CollectibleBuilder BLOODTHIRSTY_PINCERBEAST = create_BLOODTHIRSTY_PINCERBEAST();
  public static final CollectibleBuilder SANCTUARY = create_SANCTUARY();
  public static final CollectibleBuilder PLANTER_S_INSURANCE_POLICY = create_PLANTER_S_INSURANCE_POLICY();
  public static final CollectibleBuilder TACTICAL_VEST = create_TACTICAL_VEST();
  public static final CollectibleBuilder BLESSED_REBIRTH = create_BLESSED_REBIRTH();
  public static final CollectibleBuilder ANOTHER_S_BONDS = create_ANOTHER_S_BONDS();
  public static final CollectibleBuilder BLOODSTAINED_DICTIONARY = create_BLOODSTAINED_DICTIONARY();
  public static final CollectibleBuilder LIQUID_FERTILIZER = create_LIQUID_FERTILIZER();
  public static final CollectibleBuilder SPICY_COCOA = create_SPICY_COCOA();
  public static final CollectibleBuilder PAINKILLERS = create_PAINKILLERS();
  public static final CollectibleBuilder SKYROCKETING_ORIGINIUM_SLUG = create_SKYROCKETING_ORIGINIUM_SLUG();
  public static final CollectibleBuilder GROUND_HUGGING_BOMB = create_GROUND_HUGGING_BOMB();
  public static final CollectibleBuilder SOARING_WING = create_SOARING_WING();
  public static final CollectibleBuilder DRAGON_S_MOLT = create_DRAGON_S_MOLT();
  public static final CollectibleBuilder CELESTIAL_MASTER_S_BROCADE_POUCH = create_CELESTIAL_MASTER_S_BROCADE_POUCH();
  public static final CollectibleBuilder FIRE_IN_ICE = create_FIRE_IN_ICE();
  public static final CollectibleBuilder VALLEY_PRAYER = create_VALLEY_PRAYER();
  public static final CollectibleBuilder HOUND_DISEASE_CURE = create_HOUND_DISEASE_CURE();
  public static final CollectibleBuilder SPECIAL_OPERATIONS_RIOT_SHIELD = create_SPECIAL_OPERATIONS_RIOT_SHIELD();
  public static final CollectibleBuilder HOUND_COFFEE = create_HOUND_COFFEE();
  public static final CollectibleBuilder ARENA_VIP_VOUCHER = create_ARENA_VIP_VOUCHER();
  public static final CollectibleBuilder GOLDEN_PLAINS = create_GOLDEN_PLAINS();
  public static final CollectibleBuilder THE_FIRST_LAW = create_THE_FIRST_LAW();
  public static final CollectibleBuilder SPECIAL_OPERATIONS_FOOTAGE = create_SPECIAL_OPERATIONS_FOOTAGE();
  public static final CollectibleBuilder RECON_VANGUARD = create_RECON_VANGUARD();
  public static final CollectibleBuilder BLUNTCLAW_BATTLE_HARDENED = create_BLUNTCLAW_BATTLE_HARDENED();
  public static final CollectibleBuilder BLUNTCLAW_FIRST_MOVE = create_BLUNTCLAW_FIRST_MOVE();
  public static final CollectibleBuilder BROKEN_HALBERD_AVOID_THE_EDGE = create_BROKEN_HALBERD_AVOID_THE_EDGE();
  public static final CollectibleBuilder IRON_GUARD_LIGHT_STEP = create_IRON_GUARD_LIGHT_STEP();
  public static final CollectibleBuilder WORN_CROSSBOW_DIVINE_ACCURACY = create_WORN_CROSSBOW_DIVINE_ACCURACY();
  public static final CollectibleBuilder BROKEN_WAND_RESONANCE = create_BROKEN_WAND_RESONANCE();
  public static final CollectibleBuilder HEALER_TERRAIN_STRATEGY = create_HEALER_TERRAIN_STRATEGY();
  public static final CollectibleBuilder HAND_OF_TRAILBLAZING = create_HAND_OF_TRAILBLAZING();
  public static final CollectibleBuilder HAND_OF_SLAUGHTER = create_HAND_OF_SLAUGHTER();
  public static final CollectibleBuilder HAND_OF_FORTIFICATION = create_HAND_OF_FORTIFICATION();
  public static final CollectibleBuilder HAND_OF_ANTI_AIR = create_HAND_OF_ANTI_AIR();
  public static final CollectibleBuilder HAND_OF_HEALING = create_HAND_OF_HEALING();
  public static final CollectibleBuilder HAND_OF_PURGE_AND_ASSAULT = create_HAND_OF_PURGE_AND_ASSAULT();
  public static final CollectibleBuilder BLUNTCLAW_NEW_DOCTRINE = create_BLUNTCLAW_NEW_DOCTRINE();
  public static final CollectibleBuilder BROKEN_WAND_NEW_DOCTRINE = create_BROKEN_WAND_NEW_DOCTRINE();
  public static final CollectibleBuilder BROKEN_HALBERD_NEW_DOCTRINE = create_BROKEN_HALBERD_NEW_DOCTRINE();
  public static final CollectibleBuilder IRON_GUARD_NEW_DOCTRINE = create_IRON_GUARD_NEW_DOCTRINE();
  public static final CollectibleBuilder WORN_CROSSBOW_NEW_DOCTRINE = create_WORN_CROSSBOW_NEW_DOCTRINE();
  public static final CollectibleBuilder HEALER_NEW_DOCTRINE = create_HEALER_NEW_DOCTRINE();
  public static final CollectibleBuilder RUSTY_BLADE_NEW_DOCTRINE = create_RUSTY_BLADE_NEW_DOCTRINE();
  public static final CollectibleBuilder SUPPORT_NEW_DOCTRINE = create_SUPPORT_NEW_DOCTRINE();
  public static final CollectibleBuilder CONVALESCENCE_GIFT_CARD = create_CONVALESCENCE_GIFT_CARD();
  public static final CollectibleBuilder WOLF_BETWEEN_FINGERS = create_WOLF_BETWEEN_FINGERS();
  public static final CollectibleBuilder MOM_S_ENCOURAGEMENT = create_MOM_S_ENCOURAGEMENT();
  public static final CollectibleBuilder FOAM_SEALANT = create_FOAM_SEALANT();
  public static final CollectibleBuilder SNACK_BOX = create_SNACK_BOX();
  public static final CollectibleBuilder TRANQUIL_AROMA_STONE = create_TRANQUIL_AROMA_STONE();
  public static final CollectibleBuilder FORTUNE_COOKIE = create_FORTUNE_COOKIE();
  public static final CollectibleBuilder LIGHT_PACK = create_LIGHT_PACK();
  public static final CollectibleBuilder SORROWFUL_RED = create_SORROWFUL_RED();
  public static final CollectibleBuilder EMPTY_BED = create_EMPTY_BED();
  public static final CollectibleBuilder HIDE_AND_SEEK = create_HIDE_AND_SEEK();
  public static final CollectibleBuilder WHEEL_REGAINED = create_WHEEL_REGAINED();
  public static final CollectibleBuilder SUSTENANCE = create_SUSTENANCE();
  public static final CollectibleBuilder BONE_IN_THE_BAG = create_BONE_IN_THE_BAG();
  public static final CollectibleBuilder LITTLE_HAND_IN_THE_WOODS = create_LITTLE_HAND_IN_THE_WOODS();
  public static final CollectibleBuilder BLOOD_CHILD = create_BLOOD_CHILD();
  public static final CollectibleBuilder TRAVELING_COMPANION = create_TRAVELING_COMPANION();
  public static final CollectibleBuilder CALAMITY_FIRESTAFF = create_CALAMITY_FIRESTAFF();
  public static final CollectibleBuilder HUNTER_S_MARK = create_HUNTER_S_MARK();
  public static final CollectibleBuilder ORIGINIUM_PRIVATE_KEY = create_ORIGINIUM_PRIVATE_KEY();
  public static final CollectibleBuilder BLACK_CURRENT_TREE_SEA_NOTES = create_BLACK_CURRENT_TREE_SEA_NOTES();
  public static final CollectibleBuilder BASIC_IOT_TERMINAL = create_BASIC_IOT_TERMINAL();
  public static final CollectibleBuilder ADVANCED_IOT_TERMINAL = create_ADVANCED_IOT_TERMINAL();
  public static final CollectibleBuilder PLANT_PULP = create_PLANT_PULP();
  public static final CollectibleBuilder HOUND_PLANT_PULP = create_HOUND_PLANT_PULP();
  public static final CollectibleBuilder REMINISCENCE = create_REMINISCENCE();
  public static final CollectibleBuilder RAPPELLING_RAID_DEVICE = create_RAPPELLING_RAID_DEVICE();
  public static final CollectibleBuilder REGISTER_OF_HATED_NAMES = create_REGISTER_OF_HATED_NAMES();
  public static final CollectibleBuilder SAND_TABLE_ALPHA = create_SAND_TABLE_ALPHA();
  public static final CollectibleBuilder SAND_TABLE_BETA = create_SAND_TABLE_BETA();
  public static final CollectibleBuilder HEART_STIRRING_BEACON = create_HEART_STIRRING_BEACON();
  public static final CollectibleBuilder BRING_DOWN_THE_GOD = create_BRING_DOWN_THE_GOD();
  public static final CollectibleBuilder ANNIHILATE_THE_LIGHT = create_ANNIHILATE_THE_LIGHT();
  public static final CollectibleBuilder BURN_DOWN_CIVILIZATION = create_BURN_DOWN_CIVILIZATION();

  private static CollectibleBuilder create_SELECT_CANNED_BEAST_MEAT() {
    return collectible(
        "select_canned_beast_meat",
        "精选兽肉罐头",
        "【结构化】可携带干员+1，所有我方单位每秒回复3点生命\n【半结构化】可携带干员+1，所有我方单位每秒回复5点生命\n【非结构化】可携带干员+1，所有我方单位每秒回复8点生命\n【混沌化】可携带干员+1，所有我方单位每秒回复10点生命",
        "【结构化】可携带干员+1，所有我方单位每秒回复3点生命\n【半结构化】可携带干员+1，所有我方单位每秒回复5点生命\n【非结构化】可携带干员+1，所有我方单位每秒回复8点生命\n【混沌化】可携带干员+1，所有我方单位每秒回复10点生命",
        "对奔行于长路的旅人而言，“开罐即食”的便利，往往比味道更重要。",
        "对奔行于长路的旅人而言，“开罐即食”的便利，往往比味道更重要。",
        tieredRule(
            tier("结构化", registeredRule("可携带干员+1，所有我方单位每秒回复3点生命", statSet(stats -> stats.squadCapacity(1), regenerationFlat(3)))),
            tier("半结构化", registeredRule("可携带干员+1，所有我方单位每秒回复5点生命", statSet(stats -> stats.squadCapacity(1), regenerationFlat(5)))),
            tier("非结构化", registeredRule("可携带干员+1，所有我方单位每秒回复8点生命", statSet(stats -> stats.squadCapacity(1), regenerationFlat(8)))),
            tier("混沌化", registeredRule("可携带干员+1，所有我方单位每秒回复10点生命", statSet(stats -> stats.squadCapacity(1), regenerationFlat(10))))
        ),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_CHILLED_SEAWEED_SALAD() {
    return collectible(
        "chilled_seaweed_salad",
        "凉拌海草",
        "【结构化】可携带干员+1，所有我方单位的攻击速度+1\n【半结构化】可携带干员+1，所有我方单位的攻击速度+3\n【非结构化】可携带干员+1，所有我方单位的攻击速度+5\n【混沌化】可携带干员+1，所有我方单位的攻击速度+7",
        "【结构化】可携带干员+1，所有我方单位的攻击速度+1\n【半结构化】可携带干员+1，所有我方单位的攻击速度+3\n【非结构化】可携带干员+1，所有我方单位的攻击速度+5\n【混沌化】可携带干员+1，所有我方单位的攻击速度+7",
        "曾是令伊比利亚人自傲的民族美食，如今已因海嗣横行，渐渐离开了人们的记忆。",
        "曾是令伊比利亚人自傲的民族美食，如今已因海嗣横行，渐渐离开了人们的记忆。",
        tieredRule(
            tier("结构化", registeredRule("可携带干员+1，所有我方单位的攻击速度+1", statSet(stats -> stats.squadCapacity(1), stats -> stats.addAttackSpeed(1)))),
            tier("半结构化", registeredRule("可携带干员+1，所有我方单位的攻击速度+3", statSet(stats -> stats.squadCapacity(1), stats -> stats.addAttackSpeed(3)))),
            tier("非结构化", registeredRule("可携带干员+1，所有我方单位的攻击速度+5", statSet(stats -> stats.squadCapacity(1), stats -> stats.addAttackSpeed(5)))),
            tier("混沌化", registeredRule("可携带干员+1，所有我方单位的攻击速度+7", statSet(stats -> stats.squadCapacity(1), stats -> stats.addAttackSpeed(7))))
        ),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_ORANGE_STORM() {
    return collectible(
        "orange_storm",
        "橙味风暴",
        "【结构化】可携带干员+1，所有我方单位的攻击力+1%\n【半结构化】可携带干员+1，所有我方单位的攻击力+3%\n【非结构化】可携带干员+1，所有我方单位的攻击力+5%\n【混沌化】可携带干员+1，所有我方单位的攻击力+7%",
        "【结构化】可携带干员+1，所有我方单位的攻击力+1%\n【半结构化】可携带干员+1，所有我方单位的攻击力+3%\n【非结构化】可携带干员+1，所有我方单位的攻击力+5%\n【混沌化】可携带干员+1，所有我方单位的攻击力+7%",
        "约翰老妈糖果产品，畅销橙子味，以廉价美味为卖点，在孩子中很有人气。",
        "约翰老妈糖果产品，畅销橙子味，以廉价美味为卖点，在孩子中很有人气。",
        tieredRule(
            tier("结构化", registeredRule("可携带干员+1，所有我方单位的攻击力+1%", statSet(stats -> stats.squadCapacity(1), stats -> stats.multiplyAttack(0.01)))),
            tier("半结构化", registeredRule("可携带干员+1，所有我方单位的攻击力+3%", statSet(stats -> stats.squadCapacity(1), stats -> stats.multiplyAttack(0.03)))),
            tier("非结构化", registeredRule("可携带干员+1，所有我方单位的攻击力+5%", statSet(stats -> stats.squadCapacity(1), stats -> stats.multiplyAttack(0.05)))),
            tier("混沌化", registeredRule("可携带干员+1，所有我方单位的攻击力+7%", statSet(stats -> stats.squadCapacity(1), stats -> stats.multiplyAttack(0.07))))
        ),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_COFFEE_PLAINS_COFFEE_CANDY() {
    return collectible(
        "coffee_plains_coffee_candy",
        "咖啡平原咖啡糖",
        "【结构化】可携带干员+2，所有我方单位的攻击速度+2\n【半结构化】可携带干员+2，所有我方单位的攻击速度+4\n【非结构化】可携带干员+2，所有我方单位的攻击速度+6\n【混沌化】可携带干员+2，所有我方单位的攻击速度+8",
        "【结构化】可携带干员+2，所有我方单位的攻击速度+2\n【半结构化】可携带干员+2，所有我方单位的攻击速度+4\n【非结构化】可携带干员+2，所有我方单位的攻击速度+6\n【混沌化】可携带干员+2，所有我方单位的攻击速度+8",
        "多索雷斯咖啡品牌糖果产品，现已广销各地。添加真实咖啡因，让你从上班清醒到加班。",
        "多索雷斯咖啡品牌糖果产品，现已广销各地。添加真实咖啡因，让你从上班清醒到加班。",
        tieredRule(
            tier("结构化", registeredRule("可携带干员+2，所有我方单位的攻击速度+2", statSet(stats -> stats.squadCapacity(2), stats -> stats.addAttackSpeed(2)))),
            tier("半结构化", registeredRule("可携带干员+2，所有我方单位的攻击速度+4", statSet(stats -> stats.squadCapacity(2), stats -> stats.addAttackSpeed(4)))),
            tier("非结构化", registeredRule("可携带干员+2，所有我方单位的攻击速度+6", statSet(stats -> stats.squadCapacity(2), stats -> stats.addAttackSpeed(6)))),
            tier("混沌化", registeredRule("可携带干员+2，所有我方单位的攻击速度+8", statSet(stats -> stats.squadCapacity(2), stats -> stats.addAttackSpeed(8))))
        ),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SCREAMING_CHERRY() {
    return collectible(
        "screaming_cherry",
        "尖叫樱桃",
        "【结构化】可携带干员+2，所有我方单位的攻击力+2%\n【半结构化】可携带干员+2，所有我方单位的攻击力+4%\n【非结构化】可携带干员+2，所有我方单位的攻击力+6%\n【混沌化】可携带干员+2，所有我方单位的攻击力+8%",
        "【结构化】可携带干员+2，所有我方单位的攻击力+2%\n【半结构化】可携带干员+2，所有我方单位的攻击力+4%\n【非结构化】可携带干员+2，所有我方单位的攻击力+6%\n【混沌化】可携带干员+2，所有我方单位的攻击力+8%",
        "约翰老妈糖果产品，小众醋腌樱桃味，销量无法比肩畅销款，但一直有一小群忠实而稳定的消费者。听说某位约翰老妈董事就钟爱此口味。",
        "约翰老妈糖果产品，小众醋腌樱桃味，销量无法比肩畅销款，但一直有一小群忠实而稳定的消费者。听说某位约翰老妈董事就钟爱此口味。",
        tieredRule(
            tier("结构化", registeredRule("可携带干员+2，所有我方单位的攻击力+2%", statSet(stats -> stats.squadCapacity(2), stats -> stats.multiplyAttack(0.02)))),
            tier("半结构化", registeredRule("可携带干员+2，所有我方单位的攻击力+4%", statSet(stats -> stats.squadCapacity(2), stats -> stats.multiplyAttack(0.04)))),
            tier("非结构化", registeredRule("可携带干员+2，所有我方单位的攻击力+6%", statSet(stats -> stats.squadCapacity(2), stats -> stats.multiplyAttack(0.06)))),
            tier("混沌化", registeredRule("可携带干员+2，所有我方单位的攻击力+8%", statSet(stats -> stats.squadCapacity(2), stats -> stats.multiplyAttack(0.08))))
        ),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_PETE_S_FRUIT_MEDLEY() {
    return collectible(
        "pete_s_fruit_medley",
        "皮特水果什锦",
        "【结构化】可携带干员+3，所有我方单位的攻击力、防御力和生命值+3%\n【半结构化】可携带干员+3，所有我方单位的攻击力、防御力和生命值+4%\n【非结构化】可携带干员+3，所有我方单位的攻击力、防御力和生命值+5%\n【混沌化】可携带干员+3，所有我方单位的攻击力、防御力和生命值+7%",
        "【结构化】可携带干员+3，所有我方单位的攻击力、防御力和生命值+3%\n【半结构化】可携带干员+3，所有我方单位的攻击力、防御力和生命值+4%\n【非结构化】可携带干员+3，所有我方单位的攻击力、防御力和生命值+5%\n【混沌化】可携带干员+3，所有我方单位的攻击力、防御力和生命值+7%",
        "皮特牌什锦水果糖，以丰富的口味和超高性价比在糖果市场上与约翰老妈糖果系列相抗衡。缪尔赛思的最爱。",
        "皮特牌什锦水果糖，以丰富的口味和超高性价比在糖果市场上与约翰老妈糖果系列相抗衡。缪尔赛思的最爱。",
        tieredRule(
            tier("结构化", registeredRule("可携带干员+3，所有我方单位的攻击力、防御力和生命值+3%", statSet(stats -> stats.squadCapacity(3), stats -> stats.multiplyAttack(0.03).multiplyDefense(0.03).multiplyMaxHealth(0.03)))),
            tier("半结构化", registeredRule("可携带干员+3，所有我方单位的攻击力、防御力和生命值+4%", statSet(stats -> stats.squadCapacity(3), stats -> stats.multiplyAttack(0.04).multiplyDefense(0.04).multiplyMaxHealth(0.04)))),
            tier("非结构化", registeredRule("可携带干员+3，所有我方单位的攻击力、防御力和生命值+5%", statSet(stats -> stats.squadCapacity(3), stats -> stats.multiplyAttack(0.05).multiplyDefense(0.05).multiplyMaxHealth(0.05)))),
            tier("混沌化", registeredRule("可携带干员+3，所有我方单位的攻击力、防御力和生命值+7%", statSet(stats -> stats.squadCapacity(3), stats -> stats.multiplyAttack(0.07).multiplyDefense(0.07).multiplyMaxHealth(0.07))))
        ),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_EXTRA_PUNGENT_COFFEE_BEANS() {
    return collectible(
        "extra_pungent_coffee_beans",
        "特级馥郁咖啡豆",
        "可携带干员+1，可同时部署人数+1，护盾值+1",
        "+1 Squad Size Limit, +1 Deployment Limit, +1 Objective Shield",
        "咖啡农们喂某类体带异香的珍稀羽兽吃下咖啡豆，再从其排泄物中提取加工，经过胃酸发酵过程产生的异香，让咖啡豆的价格翻了三倍。",
        "Coffee growers feed their beans to a rare, unusual-smelling breed of fowlbeast before extracting and processing them from its excrement. The unique aroma produced by the beast's gastric acid fermentation triples the price of the coffee beans.",
        registeredRule("可携带干员+1，可同时部署人数+1，护盾值+1", statSet(stats -> stats.squadCapacity(1), stats -> stats.deploymentLimit(1), stats -> stats.addMaxHealth(1))),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_NIGHTSUN_FLOWER() {
    return collectible(
        "nightsun_flower",
        "夜阳花",
        "灯火+10",
        "+10 Light",
        "在夜晚发光且成群生长，能提供一定的光源，因此得名。",
        "It gives off light at night and forms groups to grow, able to provide a certain amount of lighting - hence the name.",
        registeredRule("灯火+10", stats -> stats.light(10)),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_FLAMECALLER_ORIGINIUM_SLUG() {
    return collectible(
        "flamecaller_originium_slug",
        "聚火源石虫",
        "灯火+15",
        "+15 Light",
        "一种野生源石虫，背上时不时会窜出暗淡的火光。经过现代科学研究，发现该生物的发火效应为化学反应，并非民间流传的至亲回魂。",
        "A species of wild Originium slug, with plumes of dim flame periodically bursting from its back. Through modern scientific research, it was determined that the creature's incendiary phenomenon was the result of a chemical reaction, not the resurrection of their kin's souls.",
        registeredRule("灯火+15", stats -> stats.light(15)),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_PATHFINDER_FIN() {
    return collectible(
        "pathfinder_fin",
        "指路鳞",
        "灯火+30",
        "+30 Light",
        "生息于伊比利亚沿岸的无性别海栖生物，时常为渔民指引回海岸的航向，因此得名。近期已不多见，被新生代伊比利亚人视作幸运的象征。",
        "An asexual marine creature endemic to the coasts of Iberia, named because they are often said to guide fincatchers back to shore. These days, they are rarely seen, and are now regarded as a symbol of luck by the new generation of Iberians.",
        registeredRule("灯火+30", stats -> stats.light(30)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SEA_TERROR_JERKY() {
    return collectible(
        "sea_terror_jerky",
        "恐鱼干",
        "所有敌方单位的防御力-21%",
        "All enemy units have -21% DEF",
        "当生存的欲望压过恐惧后，饥不择食的伊比利亚人会吞下这些“食物”，然后在某个平常的日子里成为恐鱼。",
        "Once their desire to survive overwhelms their terror, the famished Iberians will devour this 'food,' subsequently turning into Sea Terrors on some ordinary day.",
        implementedRule("所有敌方单位的防御力-21%", stats -> stats.addDefenseIgnore(0.21)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_VORTEX_CONFLUENCE() {
    return collectible(
        "vortex_confluence",
        "涡旋座",
        "所有【特种】干员的攻击力+30%，所有【术师】干员的攻击力+3%",
        "Specialist Operators have +30% ATK, and Caster Operators have +3% ATK",
        "驾驭风浪，驾驭洋流。曾经，阿戈尔与海洋如此默契交融。",
        "Rule the waves, rule the currents. Once, Ægir and the ocean were synonymous.",
        implementedRule("所有【特种】干员的攻击力+30%，所有【术师】干员的攻击力+3%", statSet(forProfession(SkillProfession.SPECIALIST, stats -> stats.multiplyAttack(0.3)), forProfession(SkillProfession.CASTER, stats -> stats.multiplyAttack(0.03)))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_OCEAN_VOYAGE() {
    return collectible(
        "ocean_voyage",
        "海程",
        "所有【术师】干员的攻击力+30%，所有【特种】干员的攻击力+3%",
        "Caster Operators have +30% ATK, and Specialist Operators have +3% ATK",
        "纵然两度逃上陆地，可他们依然自豪：阿戈尔曾丈量海洋。",
        "Twice they escaped to land, but in this they nevertheless still take pride: Ægir once measured the ocean.",
        implementedRule("所有【术师】干员的攻击力+30%，所有【特种】干员的攻击力+3%", statSet(forProfession(SkillProfession.CASTER, stats -> stats.multiplyAttack(0.3)), forProfession(SkillProfession.SPECIALIST, stats -> stats.multiplyAttack(0.03)))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_VIVIPAROUS_LILY() {
    return collectible(
        "viviparous_lily",
        "胎生百合",
        "战斗开始时，所有干员的部署费用-3并将部署费用打乱",
        "When a battle begins, reduces the DP Cost of all Operators by 3, then scrambles their DP Cost",
        "一种打破常理的生物形态。植物的繁殖能力，动物的繁殖欲望。",
        "An organism that defies common sense. A plant-like ability to reproduce, an animal-like desire to.",
        sourceRule("战斗开始时，所有干员的部署费用-3并将部署费用打乱"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_CHITINOUS_RIPPER() {
    return collectible(
        "chitinous_ripper",
        "几丁质刺刃",
        "战斗开始时，使随机一名干员在本局内攻击力+100%，生命+100%",
        "When a battle begins, a random Operator will have +100% ATK and +100% HP for that battle",
        "一种更有效的外骨骼生长方案。能够撕裂猎物，抵御打击，也可以化作同胞的美食。",
        "A more efficient way of growing an exoskeleton. It can tear prey apart, resist impact, and also be turned into a delicacy for its kin.",
        sourceRule("战斗开始时，使随机一名干员在本局内攻击力+100%，生命+100%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BLACK_TULIP() {
    return collectible(
        "black_tulip",
        "黑色郁金香",
        "所有我方干员技能未开启时60秒内攻击力逐渐提升至最高+60%，每次技能结束时失去该加成",
        "Friendly Operators gradually gain ATK when not using a skill, up to a maximum of +60% after 60 seconds; this bonus will be reset when a skill ends",
        "沉默压垮了伊比利亚人，所以她要发声，如果言语不够锐利，那就以剑代言。",
        "The Silence crushed the Iberians, so she wishes to speak up. If her words are not sharp enough, then her sword will speak for her.",
        sourceRule("所有我方干员技能未开启时60秒内攻击力逐渐提升至最高+60%，每次技能结束时失去该加成"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_DISTANT_HOME_S_GUIDE() {
    return collectible(
        "distant_home_s_guide",
        "遥乡之引",
        "每次战斗会随机一个可部署位置，该位置上的我方单位每秒回复1点技力和自身3%的血量",
        "Designates one deployable tile at the beginning of battle; friendly units deployed on that tile will recover 1 SP and 3% Max HP per second",
        "一枚古拙的戒指，实为特制的导航设备，凭借它可以找到阿戈尔曾在海洋各处秘密存储的应急物资。跟随洋流，跟随风，故乡将永远给予你馈赠。",
        "This ancient ring is actually said to be a special navigation device, used to find emergency supplies that the Ægir once secretly stashed in various spots across the ocean. Follow the ocean's currents, be guided by wind; your homeland will always continue to bestow gifts upon you.",
        sourceRule("每次战斗会随机一个可部署位置，该位置上的我方单位每秒回复1点技力和自身3%的血量"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_ABANDONED_BANNER() {
    return collectible(
        "abandoned_banner",
        "遗落之帜",
        "每次战斗会随机一个可部署位置，该位置上的我方单位攻击力+50%，攻击速度+50",
        "Designates one deployable tile at the beginning of battle; friendly units deployed on that tile have +50% ATK and +50 ASPD",
        "一面残破的桅旗，并无特殊之处，但深海猎人却能凭借它找到阿戈尔曾在海洋各处秘密存储的战备补给。海洋原谅了什么，她们并不清楚，但她们无需原谅。战斗未停。",
        "There is nothing special about this dismasted flag, but the Abyssal Hunters can use it to find combat supplies that the Ægir once secretly stashed in various spots across the ocean. They have no way of knowing what the ocean has forgiven, but there is no need for them to forgive. The fight never ends.",
        sourceRule("每次战斗会随机一个可部署位置，该位置上的我方单位攻击力+50%，攻击速度+50"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_MEDICINE_STICKS() {
    return collectible(
        "medicine_sticks",
        "药枚",
        "所有我方单位在部署时获得2层护盾",
        "All friendly units gain 2 layers of Shield when deployed",
        "古时炎国军队作战时，军士衔枚行军。桑葚从中得到灵感，制作了一款可食用的硬膏，非常锻炼咬肌，且醒神效果上佳。",
        "In ancient times, whenever Great Yan's armies fought, they would march forward carrying these. Mulberry drew inspiration from this and made an edible plaster, excellent for working out the masseter muscle and revitalizing one's spirit.",
        sourceRule("所有我方单位在部署时获得2层护盾"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FINCATCHER_S_SHAWL() {
    return collectible(
        "fincatcher_s_shawl",
        "捕鳞蓑",
        "远程干员获得【迷彩】",
        "Ranged Operators gain Camouflage",
        "伊比利亚海民在浅滩捕杀巨鳞时常穿的外衣，独特的造型与颜色让他们看起来像是普通的礁石。罗德岛工程部以此为原型设计了具有视觉隐匿效果的作战服，目前尚在测试阶段。",
        "A shawl often worn by Iberian seamen when they went to hunt giant fins in the shallows, the unique texture and color making them blend in with the reef. The Rhodes Island Engineering Department designed a combat uniform with an optical camouflage effect based on this prototype, which is currently in the testing stage.",
        sourceRule("远程干员获得【迷彩】"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_GLORIOUS_KAZIMIERZ() {
    return collectible(
        "glorious_kazimierz",
        "《光耀卡西米尔》",
        "干员触发闪避后6秒内攻击力+70%",
        "Operators gain +70% ATK for 6 seconds after dodging",
        "卡西米尔畅销书，以小说笔调讲述了自有骑士竞技以来那些闪耀竞技场的骑士事迹。惨遭放逐但重夺桂冠的临光占据了相当大的篇幅。",
        "A Kazimierzian best-seller that adapts the deeds of the arena's brightest-shining knights into a novel. Nearl, who was exiled but returned to reclaim her laurels, occupies a considerable portion of its pages.",
        sourceRule("干员触发闪避后6秒内攻击力+70%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_THE_RETURN() {
    return collectible(
        "the_return",
        "《归来》",
        "干员触发闪避后6秒内攻击力+130%",
        "Operators gain +130% ATK for 6 seconds after dodging",
        "卡西米尔畅销书，据说是粉丝的集体创作，“临光”已然成为糅杂了诸多幻想的形象复合体。“放逐，并非逃避。她归来，独自抵挡时代的洪流。”",
        "This Kazimierzian best-seller is said to be a collective creation by fans. 'Nearl' has already become an amalgamation of many ideals and fantasies. 'For her, exile was not an escape. She came back, and stood alone against the era's torrents.'",
        sourceRule("干员触发闪避后6秒内攻击力+130%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_CONVALESCENCE_EXPERIENCE_CARD() {
    return collectible(
        "convalescence_experience_card",
        "疗养体验卡",
        "所有干员部署后10秒内攻击速度+40",
        "All Operators gain +40 ASPD for 10 seconds after deployment",
        "疗养庭院特别服务！每位干员在执行外勤任务前，均可前往疗养庭院接受神经调理一次。名额不可转让。",
        "A special service from the Convalescent Garden! Each Operator is entitled to one physiotherapy session from the Convalescent Garden before each of their field operations. Non-transferrable.",
        sourceRule("所有干员部署后10秒内攻击速度+40"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_CONVALESCENCE_PRIME_MEMBERSHIP() {
    return collectible(
        "convalescence_prime_membership",
        "疗养特供卡",
        "所有干员部署后10秒内攻击速度+70",
        "All Operators gain +70 ASPD for 10 seconds after deployment",
        "疗养庭院联合医疗部特供！每位干员在执行外勤任务前，均可前往疗养庭院接受全面的身心状态评估，以及神经调理一次，结束后还可品尝波登可自制的花饼。",
        "A special service collaboratively provided by the Convalescent Garden and the Medical Department! Each Operator is entitled to one comprehensive physical and psychological assessment, as well as a physiotherapy session. Afterwards, they can also taste Podenco's homemade flower cakes.",
        sourceRule("所有干员部署后10秒内攻击速度+70"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_OLD_GEORGE_NUTRITIONAL_PASTE() {
    return collectible(
        "old_george_nutritional_paste",
        "古乔治营养原浆",
        "干员生命值越高，攻击力越高，100%生命值时达到最大可提升攻击力（+30%）",
        "Operators have increased ATK proportional to how high their HP is, up to a maximum of +30% ATK at 100% HP",
        "一种味道很差的强化营养剂，原本是哥伦比亚开发商定期向拓荒工人提供的“食物”补给，但在铺天盖地的营销后，它摇身一变成为了贵族钟爱的“美型良品”。",
        "This poor-tasting fortified nutritional supplement was originally a 'dietary supplement' periodically provided to Columbian Pioneers, but after extensive marketing, it suddenly became a 'beauty product' beloved by nobles.",
        sourceRule("干员生命值越高，攻击力越高，100%生命值时达到最大可提升攻击力（+30%）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_EMERGENCY_ACTIVE_AGENT() {
    return collectible(
        "emergency_active_agent",
        "紧急活性剂",
        "干员生命值越低，攻击速度越快，30%生命值时达到最大可提升攻击速度（+60）",
        "Operators have increased ASPD proportional to how low their HP is, up to a maximum of +60 ASPD at 30% HP",
        "雷姆必拓当地矿场常用的一种针剂，强活性成分可以刺激工人的感官，保证其在极端环境下的工作效率。生命和工程都有期限，对他们而言，这仅仅是个选择题。",
        "An injection commonly used in Rim Billiton's local mines. Its strong active ingredient can stimulate the workers' senses and ensure their work efficiency in extreme environments. Life and engineering both have deadlines, and for them, this is simply a multiple-choice question.",
        sourceRule("干员生命值越低，攻击速度越快，30%生命值时达到最大可提升攻击速度（+60）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_KING_S_BUCKLER() {
    return collectible(
        "king_s_buckler",
        "国王的圆饼",
        "目标生命为1时，所有干员的阻挡数+2",
        "When Life Point is at 1, all Operators have +2 Block",
        "直视前方，让坚韧的内心抗御万难。",
        "Keep your eyes straight ahead, and let your stalwart heart resist all hardship.",
        sourceRule("目标生命为1时，所有干员的阻挡数+2"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_KING_S_STAFF() {
    return collectible(
        "king_s_staff",
        "国王的枝条",
        "目标生命为1时，所有干员每2秒额外回复1点技力",
        "When Life Point is at 1, all Operators recover an additional 1 SP every 2 seconds",
        "放下无用的威仪，以恩慈待人。他们也会这样待你，他们会的。",
        "Let go of worthless pomp and treat others with kindness. They will no doubt return in kind.",
        sourceRule("目标生命为1时，所有干员每2秒额外回复1点技力"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_KING_S_CROWN() {
    return collectible(
        "king_s_crown",
        "诸王的冠冕",
        "目标生命为1时，所有我方单位的攻击力+50%，集齐3件及以上国王收藏品后变为+150%",
        "When Life Point is at 1, all friendly units have +50% ATK, increased to +150% ATK if you have collected 3 or more King's Collectibles",
        "无名的诸王啊，纵使王国毁灭，仍愿您护佑伊比利亚。",
        "O Nameless King, may you continue to bless Iberia, even should your kingdom fall to ruin.",
        sourceRule("目标生命为1时，所有我方单位的攻击力+50%，集齐3件及以上国王收藏品后变为+150%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_TIMEWORN_POETRY_STRIPS() {
    return collectible(
        "timeworn_poetry_strips",
        "散轶诗简",
        "【召唤物】不再消耗部署位",
        "Summons will no longer use Deployment slots",
        "“酣然失千山，万物得其灵。”",
        "'Drunk far beyond all mounts that list the realm, all beings take on souls as they demand.'",
        sourceRule("【召唤物】不再消耗部署位"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_DERIVATIVE_TERMINAL_SUPERCHARGED_MOD() {
    return collectible(
        "derivative_terminal_supercharged_mod",
        "衍生者终端-超级改",
        "战斗开始时【召唤物】生命值+30%，攻击力+30%，攻击速度+30",
        "Summons have +30% HP, +30% ATK, and +30 ASPD",
        "雷神工业出品的最新型电子操作终端，能够自行接驳现有多数遥控机械的信号，完成客制化与性能升级，目前尚未投入市场。",
        "A product of Raythean Industries. The latest remote-control operation terminal, capable of connecting to the signals of most existing remotely-operated units on its own, complete with customization options and performance upgrades. Not yet available on the market.",
        sourceRule("战斗开始时【召唤物】生命值+30%，攻击力+30%，攻击速度+30"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_UNCLE_VOUGHT_TOTAL_CARE_KIT() {
    return collectible(
        "uncle_vought_total_care_kit",
        "沃特大叔养护套装",
        "所有我方远程单位受到元素损伤减少40%",
        "All friendly ranged units take 40% less Elemental Injury",
        "哥伦比亚老牌毛发护理厂商“沃特大叔”生产的养护套装，普罗旺斯做过该产品的模特。“打理好尾巴和耳朵，它们是你的第二张脸。”",
        "A complete care kit produced by the Columbian old salon product manufacturer, 'Uncle Vought.' Provence has modeled for this product. 'Take good care of your tail and your ears - after all, they're your second face.'",
        sourceRule("所有我方远程单位受到元素损伤减少40%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_URSUS_FELT_RUG() {
    return collectible(
        "ursus_felt_rug",
        "乌萨斯毡毯",
        "所有我方近战单位受到元素损伤减少40%",
        "All friendly melee units take 40% less Elemental Injury",
        "乌萨斯人在储藏冬粮时铺在地窖底部的特制毡毯，吸水防潮。当地窖成为藏身之所时，它也可以为里面的人抵御片刻风雪。",
        "The people of Ursus will spread this special felt rug across their cellars when storing grains for the winter, in order to absorb water and prevent moisture. When the cellar becomes a hiding place, it also helps shelter the occupants from the wind and snow for a while.",
        sourceRule("所有我方近战单位受到元素损伤减少40%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_IRON_GUARD_MOVING_FORTRESS() {
    return collectible(
        "iron_guard_moving_fortress",
        "铁卫-临时要塞",
        "【重装】干员部署后15秒内攻击力和防御力+100%，且阻挡数+1",
        "Defender Operators have +100% ATK, +100% DEF, and +1 Block for 15 seconds after deployment",
        "无人能越过他们的防线，更无人能在来犯后离开。若说每个战士都有其使命，那他们的使命，便是在入场那一刻改变战局。",
        "Nobody can break through their defenses, and even fewer are able to escape from their assault. If every combatant has a mission, then that mission is to change the situation on the battlefield the moment they enter the fray.",
        sourceRule("【重装】干员部署后15秒内攻击力和防御力+100%，且阻挡数+1"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_STALWART_AID_ASSISTANCE() {
    return collectible(
        "stalwart_aid_assistance",
        "支柱-援护",
        "所有【辅助】干员使攻击范围内的我方单位攻击力+20%",
        "Friendly units within the attack range of Supporter Operators gain +20% ATK",
        "递上饭食、指明路线、甚至是促膝谈心，胜败的关键就在这点滴之间。",
        "Bringing meals, showing the right route to take, or even having a heart-to-heart. The key difference between victory and defeat often lies in these little things.",
        sourceRule("所有【辅助】干员使攻击范围内的我方单位攻击力+20%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HEALER_S_PATH_RESTORE_SANITY() {
    return collectible(
        "healer_s_path_restore_sanity",
        "医者-固化序列",
        "所有【医疗】干员攻击范围内的我方单位获得抵抗",
        "Friendly units within the attack range of Medic Operators gain Status Resistance",
        "让躯体如移动城市般坚实虽是一种理想化描述，医者们已经准备好将此创想付诸实践了。",
        "Making one's body as solid as a nomadic city is a bit of a romanticized description, but our medics are ready to put that idea into practice.",
        sourceRule("所有【医疗】干员攻击范围内的我方单位获得抵抗"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_RUSTED_BLADE_EXECUTION_GRINDING() {
    return collectible(
        "rusted_blade_execution_grinding",
        "锈刃-研磨",
        "所有【特种】干员的攻击速度+30",
        "Specialist Operators have +30 ASPD",
        "磨不完的锈迹，做不尽的工作。",
        "An endless amount of rust to scrape off, and endless amount of work.",
        implementedRule("所有【特种】干员的攻击速度+30", forProfession(SkillProfession.SPECIALIST, stats -> stats.addAttackSpeed(30))),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_RUSTED_BLADE_NO_MAN_S_LAND() {
    return collectible(
        "rusted_blade_no_man_s_land",
        "锈刃-可视静谧",
        "所有【特种】干员的再部署时间-35%",
        "The Redeployment Time of Specialist Operators is reduced by -35%",
        "声音在阴影掠过的瞬间遇害，倾听它们的人将是下一位受害者。",
        "Sound is eliminated the moment the shadows pass by, and whoever hears them is next.",
        sourceRule("所有【特种】干员的再部署时间-35%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_HAND_OF_DIFFUSION_EXPLOSIVE() {
    return collectible(
        "hand_of_diffusion_explosive",
        "炸裂之手",
        "【要塞】、【链术师】和【轰击术师】每对一个单位造成伤害就回复2点技力值",
        "Fortress, Chain Caster, and Blast Caster Operators recover 2 SP each time they deal damage to an enemy unit",
        "数量越多，力量越强。",
        "Strength in numbers.",
        sourceRule("【要塞】、【链术师】和【轰击术师】每对一个单位造成伤害就回复2点技力值"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_PURIFICATION() {
    return collectible(
        "hand_of_purification",
        "净尘之手",
        "【吟游者】【阵法术师】【疗养师】对攻击范围内敌人每秒造成50%攻击力的法术伤害",
        "Bard, Phalanx Caster, and Therapist Operators deal 50% ATK as Arts damage to all enemies in their attack range",
        "所在，即为无尘之地。",
        "Where it is, dust is not.",
        sourceRule("【吟游者】【阵法术师】【疗养师】对攻击范围内敌人每秒造成50%攻击力的法术伤害"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_PULVERIZATION() {
    return collectible(
        "hand_of_pulverization",
        "碎靶之手",
        "【炮手】【扩散术师】【投掷手】每对一个单位造成伤害就使自身攻击力+15%，最高+150%，5秒未造成伤害则使加成清空",
        "Artilleryman, Splash Caster, and Flinger Operators gain +15% ATK for every time they deal damage to the same unit, up to 150%; this bonus will reset if no damage is dealt for 5 seconds",
        "目标，可不仅仅只是连续命中靶心。",
        "Your goal isn't just to hit the bullseye over and over.",
        sourceRule("【炮手】【扩散术师】【投掷手】每对一个单位造成伤害就使自身攻击力+15%，最高+150%，5秒未造成伤害则使加成清空"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_FLOWING_WATER() {
    return collectible(
        "hand_of_flowing_water",
        "永流之手",
        "【中坚术师】【秘术师】【召唤师】每使用过一次技能，自身的技力自然回复速度+0.5/秒，最多叠加4层",
        "Increases the SP recovery rate of Core Caster, Mystic Caster, and Summoner Operators by +0.5/s whenever they use a skill, up to 4 stacks",
        "海洋从不断流，在于它永远奔涌。",
        "The ocean's flow never ceases, because it is always surging.",
        sourceRule("【中坚术师】【秘术师】【召唤师】每使用过一次技能，自身的技力自然回复速度+0.5/秒，最多叠加4层"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_GAME_ROOM_ADMIN_ACCESS_CARD() {
    return collectible(
        "game_room_admin_access_card",
        "游戏室管理员权限卡",
        "【实在的】立即进阶一个干员（不消耗希望）\n【巧思的】立即获得源石锭+1，并进阶一个干员（不消耗希望）\n【幻想的】立即获得源石锭+2，并进阶一个干员（不消耗希望）\n【架空的】立即获得源石锭+3，并进阶一个干员（不消耗希望）",
        "【实在的】立即进阶一个干员（不消耗希望）\n【巧思的】立即获得源石锭+1，并进阶一个干员（不消耗希望）\n【幻想的】立即获得源石锭+2，并进阶一个干员（不消耗希望）\n【架空的】立即获得源石锭+3，并进阶一个干员（不消耗希望）",
        "持有它，您就掌握了罗德岛的娱乐管理大权，请谨慎处理干员们种种奇怪的申请......以及每天关门。",
        "With this, you have become master over Rhodes Island's entertainment privileges. Please exercise caution when processing the various strange applications from the Operators... and remember to lock the door every day.",
        tieredRule(
            tier("实在的", sourceRule("立即进阶一个干员（不消耗希望）")),
            tier("巧思的", partialRule("立即获得源石锭+1，并进阶一个干员（不消耗希望）", stats -> stats.originiumIngots(1))),
            tier("幻想的", partialRule("立即获得源石锭+2，并进阶一个干员（不消耗希望）", stats -> stats.originiumIngots(2))),
            tier("架空的", partialRule("立即获得源石锭+3，并进阶一个干员（不消耗希望）", stats -> stats.originiumIngots(3)))
        ),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_TERRAIN_MAP() {
    return collectible(
        "terrain_map",
        "地形图",
        "【结构化】可同时部署人数+1，所有我方单位的防御力+4%\n【半结构化】可同时部署人数+1，所有我方单位的防御力+5%\n【非结构化】可同时部署人数+1，所有我方单位的防御力+6%\n【混沌化】可同时部署人数+1，所有我方单位的防御力+8%",
        "【结构化】可同时部署人数+1，所有我方单位的防御力+4%\n【半结构化】可同时部署人数+1，所有我方单位的防御力+5%\n【非结构化】可同时部署人数+1，所有我方单位的防御力+6%\n【混沌化】可同时部署人数+1，所有我方单位的防御力+8%",
        "一份颇有年代感的手绘地图，图上的名称和对应地形都有较大更易，比例尺似乎也有些问题。",
        "一份颇有年代感的手绘地图，图上的名称和对应地形都有较大更易，比例尺似乎也有些问题。",
        tieredRule(
            tier("结构化", registeredRule("可同时部署人数+1，所有我方单位的防御力+4%", statSet(stats -> stats.deploymentLimit(1), stats -> stats.multiplyDefense(0.04)))),
            tier("半结构化", registeredRule("可同时部署人数+1，所有我方单位的防御力+5%", statSet(stats -> stats.deploymentLimit(1), stats -> stats.multiplyDefense(0.05)))),
            tier("非结构化", registeredRule("可同时部署人数+1，所有我方单位的防御力+6%", statSet(stats -> stats.deploymentLimit(1), stats -> stats.multiplyDefense(0.06)))),
            tier("混沌化", registeredRule("可同时部署人数+1，所有我方单位的防御力+8%", statSet(stats -> stats.deploymentLimit(1), stats -> stats.multiplyDefense(0.08))))
        ),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_DIVINE_CONCH() {
    return collectible(
        "divine_conch",
        "神音海螺",
        "【结构化】可同时部署人数+1，所有我方单位的生命值+4%\n【半结构化】可同时部署人数+1，所有我方单位的生命值+5%\n【非结构化】可同时部署人数+1，所有我方单位的生命值+6%\n【混沌化】可同时部署人数+1，所有我方单位的生命值+8%",
        "【结构化】可同时部署人数+1，所有我方单位的生命值+4%\n【半结构化】可同时部署人数+1，所有我方单位的生命值+5%\n【非结构化】可同时部署人数+1，所有我方单位的生命值+6%\n【混沌化】可同时部署人数+1，所有我方单位的生命值+8%",
        "吹响它，那些你不曾见过、甚至不曾听闻的神明将回应你的声音。其实就是一个空心海螺。",
        "吹响它，那些你不曾见过、甚至不曾听闻的神明将回应你的声音。其实就是一个空心海螺。",
        tieredRule(
            tier("结构化", registeredRule("可同时部署人数+1，所有我方单位的生命值+4%", statSet(stats -> stats.deploymentLimit(1), stats -> stats.multiplyMaxHealth(0.04)))),
            tier("半结构化", registeredRule("可同时部署人数+1，所有我方单位的生命值+5%", statSet(stats -> stats.deploymentLimit(1), stats -> stats.multiplyMaxHealth(0.05)))),
            tier("非结构化", registeredRule("可同时部署人数+1，所有我方单位的生命值+6%", statSet(stats -> stats.deploymentLimit(1), stats -> stats.multiplyMaxHealth(0.06)))),
            tier("混沌化", registeredRule("可同时部署人数+1，所有我方单位的生命值+8%", statSet(stats -> stats.deploymentLimit(1), stats -> stats.multiplyMaxHealth(0.08))))
        ),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_NIGHT_TALK_IN_THE_WOODS() {
    return collectible(
        "night_talk_in_the_woods",
        "林间夜话",
        "【结构化】可同时部署人数+2，所有我方单位的防御力+6%\n【半结构化】可同时部署人数+2，所有我方单位的防御力+7%\n【非结构化】可同时部署人数+2，所有我方单位的防御力+8%\n【混沌化】可同时部署人数+2，所有我方单位的防御力+10%",
        "【结构化】可同时部署人数+2，所有我方单位的防御力+6%\n【半结构化】可同时部署人数+2，所有我方单位的防御力+7%\n【非结构化】可同时部署人数+2，所有我方单位的防御力+8%\n【混沌化】可同时部署人数+2，所有我方单位的防御力+10%",
        "疗养庭院为有外勤任务的干员特调的香薰套装，提炼十五种植物成分，尝试一百七十二种组合。风踏进山林，万物低语，人可好眠。",
        "疗养庭院为有外勤任务的干员特调的香薰套装，提炼十五种植物成分，尝试一百七十二种组合。风踏进山林，万物低语，人可好眠。",
        tieredRule(
            tier("结构化", registeredRule("可同时部署人数+2，所有我方单位的防御力+6%", statSet(stats -> stats.deploymentLimit(2), stats -> stats.multiplyDefense(0.06)))),
            tier("半结构化", registeredRule("可同时部署人数+2，所有我方单位的防御力+7%", statSet(stats -> stats.deploymentLimit(2), stats -> stats.multiplyDefense(0.07)))),
            tier("非结构化", registeredRule("可同时部署人数+2，所有我方单位的防御力+8%", statSet(stats -> stats.deploymentLimit(2), stats -> stats.multiplyDefense(0.08)))),
            tier("混沌化", registeredRule("可同时部署人数+2，所有我方单位的防御力+10%", statSet(stats -> stats.deploymentLimit(2), stats -> stats.multiplyDefense(0.1))))
        ),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_CHURCH_RELIEF_MEAL_VOUCHER() {
    return collectible(
        "church_relief_meal_voucher",
        "教堂救济餐券",
        "【结构化】可同时部署人数+2，所有我方单位的生命值+6%\n【半结构化】可同时部署人数+2，所有我方单位的生命值+7%\n【非结构化】可同时部署人数+2，所有我方单位的生命值+8%\n【混沌化】可同时部署人数+2，所有我方单位的生命值+10%",
        "【结构化】可同时部署人数+2，所有我方单位的生命值+6%\n【半结构化】可同时部署人数+2，所有我方单位的生命值+7%\n【非结构化】可同时部署人数+2，所有我方单位的生命值+8%\n【混沌化】可同时部署人数+2，所有我方单位的生命值+10%",
        "在没落的伊比利亚沿海城镇，某些教堂会定期提供救济餐，信徒们凭券领取。至于救济餐券的发放标准，是个秘密。",
        "在没落的伊比利亚沿海城镇，某些教堂会定期提供救济餐，信徒们凭券领取。至于救济餐券的发放标准，是个秘密。",
        tieredRule(
            tier("结构化", registeredRule("可同时部署人数+2，所有我方单位的生命值+6%", statSet(stats -> stats.deploymentLimit(2), stats -> stats.multiplyMaxHealth(0.06)))),
            tier("半结构化", registeredRule("可同时部署人数+2，所有我方单位的生命值+7%", statSet(stats -> stats.deploymentLimit(2), stats -> stats.multiplyMaxHealth(0.07)))),
            tier("非结构化", registeredRule("可同时部署人数+2，所有我方单位的生命值+8%", statSet(stats -> stats.deploymentLimit(2), stats -> stats.multiplyMaxHealth(0.08)))),
            tier("混沌化", registeredRule("可同时部署人数+2，所有我方单位的生命值+10%", statSet(stats -> stats.deploymentLimit(2), stats -> stats.multiplyMaxHealth(0.1))))
        ),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_ACAHUALLAN_BOWL() {
    return collectible(
        "acahuallan_bowl",
        "阿卡胡拉饭碗",
        "回复4目标生命，护盾值+2",
        "Restore 4 Life Point, +2 Objective Shield",
        "水果是当地人的主食之一，现采现食也是常有的事。",
        "Fruit is one of the locals' staples, often eaten fresh right off the tree.",
        implementedRule("回复4目标生命，护盾值+2", statSet(stats -> stats.addMaxHealth(4), stats -> stats.addMaxHealth(2))),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_THREE_DIMENSIONAL_ART_DISPLAY() {
    return collectible(
        "three_dimensional_art_display",
        "立体艺术装置",
        "回复7目标生命，每次战斗结束后额外回复1目标生命",
        "Restore 7 Life Point, restore an additional 1 Life Point after each battle",
        "莱茵生命有时给人的印象就如同这件艺术品：神秘莫测，层层叠叠，永远探不清虚实。",
        "The impression that Rhine Lab often gives is akin to this work of art: inscrutable, multi-faceted, impossible to sift fact from fiction.",
        partialRule("回复7目标生命，每次战斗结束后额外回复1目标生命", stats -> stats.addMaxHealth(7)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_MULTIPURPOSE_HAIR_CLIP() {
    return collectible(
        "multipurpose_hair_clip",
        "“多功能”发夹",
        "钥匙+1，掷骰次数+1，护盾值+1",
        "+1 Key, +1 Die Roll, +1 Objective Shield",
        "它的材质确实能够抵挡伤害，至于其他效果，应该也没有卡西米尔商人吹的那么神乎其神。话说回来，这只是个发夹，真的需要那么多额外用途吗？",
        "Its materials are clearly damage-resistant, but are its other functions as impressive as what the Kazimierzian merchant boasted? Come to think of it, why does a bobby pin need so many extra features anyway?",
        registeredRule("钥匙+1，掷骰次数+1，护盾值+1", statSet(stats -> stats.keys(1), stats -> stats.dice(1), stats -> stats.addMaxHealth(1))),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_THREE_KEY_CONTRACT() {
    return collectible(
        "three_key_contract",
        "三钥协定",
        "钥匙+3",
        "+3 Keys",
        "按照规定，灯塔守门人必须携带三把钥匙：一把用来打开大门，一把用来建立通讯，一把用来启动射灯。签字画押，协议已定，钥匙随身，无关生死。",
        "According to regulations, lighthouse keepers must always carry three keys: one to unlock the main door, another to establish communication, and one to activate the searchlight. Sign your name, and the contract is set in stone. Carry the keys with you at all times, regardless of life or death.",
        registeredRule("钥匙+3", stats -> stats.keys(3)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_PORTABLE_SCRIPTURES() {
    return collectible(
        "portable_scriptures",
        "便携经书",
        "掷骰次数+2",
        "+2 Die Rolls",
        "还能识字的伊比利亚教徒会随身携带这种口袋书，在生存的间隙取出来读上几段，内心便能升起些许宽慰。",
        "The few Iberian faithfuls who can still read often carry around a pocket-sized version of the Scriptures with them, taking it out to glance over a few lines during the brief lulls in their struggles for survival. It offers but a modicum of peace.",
        registeredRule("掷骰次数+2", stats -> stats.dice(2)),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_INQUISITOR_S_SCRIPTURE_CLOTH() {
    return collectible(
        "inquisitor_s_scripture_cloth",
        "判官经文布",
        "每次战斗结束后掷骰次数+1",
        "Gain +1 Die Roll after each battle",
        "作为部分审判官衣着装饰的飘带，上面刻满了经文。他们内心坚定，绝不轻易动摇，其配饰则加强了这种形象。",
        "As part of the decorations adorning an Inquisitor's clothing, this ribbon is inlaid with the Scriptures. Their hearts are firm and unwavering, and these accessories reinforce that image.",
        sourceRule("每次战斗结束后掷骰次数+1"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_DEEP_SEA_SIRE_SCULPTURE() {
    return collectible(
        "deep_sea_sire_sculpture",
        "深洋主宰刻像",
        "掷骰次数+6",
        "+6 Die Rolls",
        "深海教徒相信神灵就在大洋深处沉睡，而横行的恐鱼进一步加深了这一信念。既然真相如此，他们便再无疑虑。",
        "The Church of the Deep cultists believe that gods sleep in the depths of the ocean, and the teeming Sea Terrors seem to confirm their beliefs. Seeing as that is indeed the truth, they no longer have any reason to hesitate.",
        registeredRule("掷骰次数+6", stats -> stats.dice(6)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_DUCK_LORD_S_GOLDEN_BRICK() {
    return collectible(
        "duck_lord_s_golden_brick",
        "鸭爵金砖",
        "使你的骰子获得升级",
        "Upgrades your die",
        "“一个人到底怎样才能彻底摆脱霉运？”\n“你手头还有金砖吗，把它塞霉运嘴里。”",
        "'How can someone completely rid themselves of misfortune?' \n'You have a gold brick in your hand, don't you? Shove it up misfortune's mouth.'",
        sourceRule("使你的骰子获得升级"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_DEFENDER_2() {
    return collectible(
        "defender_2",
        "御2",
        "护盾值+5",
        "+5 Objective Shield",
        "这是个防御型量产无人机，没道理它只在敌人手里起作用。",
        "A mass-produced defensive-type drone. It doesn't make sense how it only seems to be useful in the hands of the enemy.",
        implementedRule("护盾值+5", stats -> stats.addMaxHealth(5)),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_EMBEDDED_ARMOR_PLATE() {
    return collectible(
        "embedded_armor_plate",
        "嵌体甲片",
        "目标生命上限-2，护盾值+8",
        "-2 Max Life Point, +8 Objective Shield",
        "“你什么都不懂，实验体。和乌萨斯那些怪物所遭受过的痛苦相比，镶嵌一块铠甲在你体内根本不值一提。”",
        "'You don't understand a thing, test subject. Compared to what those monsters of Ursus suffered, a measly plate of armor embedded into your body isn't even worth mentioning.'",
        implementedRule("目标生命上限-2，护盾值+8", statSet(stats -> stats.addMaxHealth(-2), stats -> stats.addMaxHealth(8))),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_DARIO_S_LANTERN() {
    return collectible(
        "dario_s_lantern",
        "达里奥的提灯",
        "获得时抑制指定一名干员的排异反应",
        "When obtained, suppresses a designated Operator's Rejection",
        "审判官达里奥曾经使用的提灯。即使早已身故，他的信念之火仍在大地上熊熊燃烧。",
        "A lantern once wielded by Inquisitor Dario. Even after his death, the flames of his conviction still burn brightly upon this land.",
        sourceRule("获得时抑制指定一名干员的排异反应"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_KING_S_FELLOWSHIP() {
    return collectible(
        "king_s_fellowship",
        "“国王的护戒”",
        "护盾值+2，将目标生命全部转化为等量护盾值（目标生命不会低于1）",
        "+2 Objective Shield, coverts all Life Point into an equivalent amount of Objective Shield (Cannot be reduced below 1 by this effect)",
        "深海教徒并非一蹴而就，他们在海洋与大地的交界处盘桓许久，为今后被称作“大静谧”的灾难做准备。",
        "The Cultists of the Deep did not achieve success overnight; they lingered at the junction between land and sea for ages, preparing for the calamity that would come to be known as the Profound Silence.",
        partialRule("护盾值+2，将目标生命全部转化为等量护盾值（目标生命不会低于1）", stats -> stats.addMaxHealth(2)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_TULIP_S_SECRET_FORMULA() {
    return collectible(
        "tulip_s_secret_formula",
        "郁金香的秘方",
        "获得时抑制指定一名干员的排异反应",
        "When obtained, suppresses a designated Operator's Rejection",
        "通过实情分析与重重实验后制作出的应急外用药膏，能够有效抑制人体内海嗣细胞产生的副作用。",
        "An emergency topical ointment produced through empirical analysis and numerous clinical trials, capable of effectively inhibiting the adverse side-effects Seaborn cells cause within the human body.",
        sourceRule("获得时抑制指定一名干员的排异反应"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_PARAFFIN_AND_BALSAM() {
    return collectible(
        "paraffin_and_balsam",
        "火油与药膏",
        "灯火-30，获得时抑制指定一名干员的排异反应",
        "-30 Light, when obtained, suppresses a designated Operator's Rejection",
        "焚烧过后污秽便被消除，你仍旧能成为纯净的人类。赞美火焰。",
        "Burn it and your impurities shall be shunned, allowing you to remain a pure human being. Praise the flame.",
        partialRule("灯火-30，获得时抑制指定一名干员的排异反应", stats -> stats.light(-30)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_NIGHTSUN_GRASS() {
    return collectible(
        "nightsun_grass",
        "夜阳草",
        "灯火+5",
        "+5 Light",
        "夜阳花的草叶，只有一点淡淡的光亮。含有一定的解毒成分，可以入药。",
        "Only a faint glimmer of light remains on the leaves of the nightsun flower. Contains certain detoxifying compounds and can be used as medicine.",
        registeredRule("灯火+5", stats -> stats.light(5)),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_CATHEDRAL_PUZZLE() {
    return collectible(
        "cathedral_puzzle",
        "大教堂拼图",
        "获得时随机获得1个启示",
        "When obtained, randomly gain 1 Revelation",
        "每当一块拼图落下，伊比利亚大教堂便愈发完整。在这破败的时代中，能够再次拼凑起历史造就的最为辉煌的奇观之一，已经足以令人感到慰藉。",
        "Every time a piece of the puzzle falls, the Iberian Cathedral becomes more complete. In these times of decay, there is ample comfort to be found in simply being able to piece together one of history's most glorious wonders.",
        sourceRule("获得时随机获得1个启示"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SUPPORT_REPLENISHMENT_STATION() {
    return collectible(
        "support_replenishment_station",
        "支援补给站",
        "每次战斗中携带1个便携式补给站",
        "Bring a Portable Supply Station along to each battle",
        "紧急提供给外勤干员的便携式补给站。",
        "An emergency Portable Supply Station provided to field operators.",
        sourceRule("每次战斗中携带1个便携式补给站"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SUPPORT_LANDMINE_SET() {
    return collectible(
        "support_landmine_set",
        "支援地雷组",
        "每次战斗中携带3个干扰地雷",
        "Bring 3 Interference Mines along to each battle",
        "紧急提供给外勤干员的干扰地雷组。",
        "An emergency set of Interference Mines provided to field operators.",
        sourceRule("每次战斗中携带3个干扰地雷"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SUPPORT_MR_BOOM() {
    return collectible(
        "support_mr_boom",
        "支援轰隆隆",
        "每次战斗中携带2个轰隆隆先生",
        "Bring 2 Mr. Booms along to each battle",
        "紧急提供给外勤干员的轰隆隆先生。",
        "An emergency Mr. Boom provided to field operators.",
        sourceRule("每次战斗中携带2个轰隆隆先生"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SUPPORT_ESCAPE_CRANE() {
    return collectible(
        "support_escape_crane",
        "支援起重机",
        "每次战斗中携带2个雪雉的安全起重机",
        "Bring 2 Snowsant's Safe Escape Cranes along to each battle",
        "紧急提供给外勤干员的雪雉的安全起重机。",
        "An emergency Snowsant's Safe Escape Crane provided to field operators.",
        sourceRule("每次战斗中携带2个雪雉的安全起重机"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SUPPORT_RIOT_PILE() {
    return collectible(
        "support_riot_pile",
        "支援防暴桩",
        "每次战斗中携带2个防暴桩",
        "Bring 2 Riot Piles along to each battle",
        "紧急提供给外勤干员的便携防暴桩。",
        "An emergency set of Riot Piles provided to field operators.",
        sourceRule("每次战斗中携带2个防暴桩"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SUPPORT_FIRST_AID_KIT() {
    return collectible(
        "support_first_aid_kit",
        "支援急救包",
        "每次战斗中携带3个急救包",
        "Bring 3 First-aid Kits to each battle",
        "紧急提供给外勤干员的便携急救包。",
        "An emergency First-aid Kit provided to field operators.",
        sourceRule("每次战斗中携带3个急救包"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_PORTRAIT_OF_BREOGAN() {
    return collectible(
        "portrait_of_breogan",
        "布雷奥甘肖像",
        "所有干员部署时失去15点技力",
        "All Operators lose 15 SP when deployed",
        "肖像的主人是一位在伊比利亚负有盛名的阿戈尔。他的发明与创举曾令伊比利亚闪闪发光，然而当浪潮淹没金光，他的死亡便已注定。",
        "The subject of the portrait is an Ægir who used to be well-known in Iberia. His inventions and feats once made the country shine, but when the tide drowned out the golden light, his fate was sealed.",
        sourceRule("所有干员部署时失去15点技力"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_LEVIATHAN_S_ANABOLISM() {
    return collectible(
        "leviathan_s_anabolism",
        "海神的代谢",
        "所有敌人生命值+25%",
        "All enemies gain +25% HP",
        "祂缓慢坠入海洋的最深处，血、肉、皮肤和骨骼分离溶解，被洋流冲卷，被所有生命摄取，滋养了整片海洋的繁荣。",
        "Slowly did He fall into the deepest depths of the ocean. His blood, flesh, skin, and bones separated and dissolved—they were swept up by the sea's currents, absorbed by all life, and became sustenance for the propagation of the entire ocean.",
        implementedRule("所有敌人生命值+25%", stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> enemyStats.multiplyMaxHealth(0.25))),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FAILED_SPECIMEN() {
    return collectible(
        "failed_specimen",
        "失败的标本",
        "获得时立即使随机一名干员产生排异反应",
        "When obtained, immediately afflicts a random Operator with a Rejection",
        "一份并未制作完成的壳衣标本。碎屑从微小的缝隙间掉落、附着、层叠，与里面已然死去的植物融为一体，直到将原本的容器彻底吞噬。",
        "An incomplete shelled specimen. Debris falls from small crevices, sticks together, accumulates, and merges with the dead plants inside, until the original husk is completely swallowed up.",
        sourceRule("获得时立即使随机一名干员产生排异反应"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_EMERGENCY_LIGHT() {
    return collectible(
        "emergency_light",
        "“应急灯”",
        "每次战斗结束后灯火额外-2",
        "Lose an additional 2 Light after each battle",
        "阿戈尔战士曾在紧急战斗中，将某类发光的小型恐鱼锁在透明罩具内充当照明工具。躁动的恐鱼在罩具内冲撞，濒死前更加疯狂地释放光亮，连同罩具本身化为难以名状的黑色胶质。",
        "Ægir warriors used to lock some kind of small luminescent Sea Terror inside a transparent contraption to serve as lighting during emergency situations. The restless Sea Terror would smash into its confines, releasing even more light in a frenzy until it died, staining its prison with an indescribable black glue.",
        sourceRule("每次战斗结束后灯火额外-2"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_LUMINOUS_CORPSE() {
    return collectible(
        "luminous_corpse",
        "“光明之躯”",
        "每次战斗结束后灯火额外-3",
        "Lose an additional 3 Light after each battle",
        "在漫长的迁徙中，为了大群的光明，族群中衰弱的个体脏器会自动溶解而发光，最终留下透明的皮囊。",
        "To provide light to we many during long migrations, the weakest organs among the collective would dissolve on their own and start glowing, leaving nothing behind but a transparent membrane.",
        sourceRule("每次战斗结束后灯火额外-3"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BISHOP_S_RESEARCH() {
    return collectible(
        "bishop_s_research",
        "主教的研究",
        "每次战斗结束后灯火额外-5",
        "Lose an additional 5 Light after each battle",
        "这位主教毕生的研究几乎都留在了这些文件和笔记里，其中涉及了诸多逾越伦理道德的实验。在他的观念中，接触海嗣只是手段，最终的愿景，是让人类通过与海嗣融合进化为更强大的生物。",
        "Almost all of the bishop's lifelong research can be found among these documents and notes, many of which involve experiments that transcended ethics and morality. In his eyes, making contact with the Seaborn was just a means to an end for his ultimate vision: allowing human beings to evolve into a more powerful existence through fusion with the Seaborn.",
        sourceRule("每次战斗结束后灯火额外-5"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_CAERULA_ANIMUS() {
    return collectible(
        "caerula_animus",
        "深蓝之心",
        "灯火-50，获得时立即使随机一名干员产生排异反应，让探索走向不同的结局",
        "-50 Light; when obtained, immediately afflicts a random Operator with a Rejection, and leads the exploration toward a different ending",
        "吞噬诞生之处随后死去的“初生”，其残存下来的核心器官。只有海嗣能够与之同化，却又只有人类能够理解蕴含其中的悲痛情感。啊啊，即使演化不息，它们，祂们的苦痛仍在。",
        "The surviving core organ of the 'Firstborn' that devoured Its birthplace and soon died afterwards. Only the Seaborn are able to assimilate it, but only humans are able to understand the grief contained within. Alas, even for those whose evolution never ends, their - Their - suffering will always persist.",
        partialRule("灯火-50，获得时立即使随机一名干员产生排异反应，让探索走向不同的结局", stats -> stats.light(-50)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BREATH_OF_THE_TIDE() {
    return collectible(
        "breath_of_the_tide",
        "海潮的气息",
        "可同时部署人数+1；获得后【猎潮的骑士】出现在战场，直至被击败",
        "+1 Deployment Limit; when obtained, [Tide-Hunt Knight] will appear on the battlefield until defeated",
        "他闻到了海潮的气味，于是出现在你近旁。你于他而言，没有意义。",
        "He appeared near you because he smelled the scent of the tides. You mean nothing to him.",
        partialRule("可同时部署人数+1；获得后【猎潮的骑士】出现在战场，直至被击败", stats -> stats.deploymentLimit(1)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_REGRESSED_ROCINANTE() {
    return collectible(
        "regressed_rocinante",
        "退行的罗辛南特",
        "【猎潮的骑士】不再出现在战场",
        "[Tide-Hunt Knight] will no longer appear on the battlefield",
        "只有一位英雄能够骑乘它，当英雄死去，它的生命也就到了尽头。",
        "Only a hero can ride it. When the hero dies, so too does its life end.",
        sourceRule("【猎潮的骑士】不再出现在战场"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_ABSURD_FATE() {
    return collectible(
        "absurd_fate",
        "“荒诞命运”",
        "源石锭+10，钥匙+1",
        "+10 Originium Ingots, +1 Key",
        "在卡西米尔，他的名字受众人传颂，他的遗族开设公司以其形象谋取利益，他以生命捍卫的事物，尽数化为了钱袋中的一枚枚金币。",
        "His name is widely known in Kazimierz, and his descendants set up companies to benefit from his renown. All the things he defended with his life have become nothing more than gold coins in a wallet.",
        registeredRule("源石锭+10，钥匙+1", statSet(stats -> stats.originiumIngots(10), stats -> stats.keys(1))),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_THE_KNIGHT_S_CORPUS() {
    return collectible(
        "the_knight_s_corpus",
        "骑士骨血",
        "【猎潮的骑士】的防御力-80%",
        "[Tide-Hunt Knight] has -80% DEF",
        "反抗不是毫无代价的。骑士早已遍体鳞伤，但他从不在意。纵使粉身碎骨，他也要抗争到底。",
        "Resisting does not come without a price. The knight's body had long since become riddled with wounds, but he paid no heed. He will fight until the bitter end, even should his body be smashed to pieces.",
        sourceRule("【猎潮的骑士】的防御力-80%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_LAST_REFRAIN() {
    return collectible(
        "last_refrain",
        "“绝唱”",
        "获得时随机获得2个启示",
        "When obtained, randomly gain 2 Revelations",
        "呜呼，呜呼，深海猎人今何在？\n可叹，可叹，海中只余涌潮声。",
        "Cry, cry, for where are the Abyssal Hunters now? \nAlas, alas, there is nothing in the ocean but the sound of the tide.",
        sourceRule("获得时随机获得2个启示"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_FLAMES_OF_THE_INQUISITION() {
    return collectible(
        "flames_of_the_inquisition",
        "“审判庭之火”",
        "所有干员对【海怪】造成伤害时回复2点技力",
        "Operators recover 2 SP when dealing damage to [Sea Monsters]",
        "正因为恶物们自海中诞生，所以它们畏惧，畏惧这与海水绝不相融的烈火。",
        "Because the evil creatures were born from the sea, they were afraid. Afraid of the flames that would never mix with the sea.",
        sourceRule("所有干员对【海怪】造成伤害时回复2点技力"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_LITTLE_GRAN_FARO() {
    return collectible(
        "little_gran_faro",
        "“小格兰法洛”",
        "每次敌人进入保护目标点时，立刻对全场所有敌人造成5秒晕眩",
        "Deals Stun on all enemy units for 5 seconds every time an enemy unit enters the Protection Objective",
        "使用逆向工程分析“伊比利亚之眼”后制作的照射装置，能够洞穿层层阴云，也可暂时驱退强敌。",
        "The illumination device produced after reverse engineering the Eye of Iberia. It is powerful enough to penetrate layers of dark clouds, as well as temporarily repel powerful enemies.",
        sourceRule("每次敌人进入保护目标点时，立刻对全场所有敌人造成5秒晕眩"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_MEMORIES_OF_THE_SHARD() {
    return collectible(
        "memories_of_the_shard",
        "“碎片大厦的回忆”",
        "每次敌人进入保护目标点时，立刻对全场所有敌人造成3000点真实伤害",
        "Deals 3000 True Damage on all enemy units every time an enemy unit enters the Protection Objective",
        "“它正扎在维多利亚的心脏上，眼看着这个帝国流尽鲜血。”",
        "'It has pierced Victoria's heart, and is watching the empire bleed.'",
        sourceRule("每次敌人进入保护目标点时，立刻对全场所有敌人造成3000点真实伤害"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_GLORY_PACK() {
    return collectible(
        "glory_pack",
        "“荣耀套餐”",
        "我方单位被击倒时，对周围1.25半径内的敌人造成6秒晕眩",
        "When an Operator is defeated, it will Stun all enemies in the four adjacent tiles for 6 seconds",
        "主办方在骑士竞赛期间推出的特别投注设备，优选多种叠加互补投注套餐，将单场比赛投注的损失降至最低。",
        "A special betting method set up by the organizers for the duration of the knight competition season. Choose from a variety of complementary betting packages to minimize your losses on any particular match.",
        sourceRule("我方单位被击倒时，对周围1.25半径内的敌人造成6秒晕眩"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SUI_S_WRATH() {
    return collectible(
        "sui_s_wrath",
        "“岁怒”",
        "每次部署我方单位时，对场上随机一个敌人及其周围造成3000点法术伤害",
        "Whenever you deploy a unit, deal 3000 Arts damage to a random enemy and the area around it",
        "巨兽昂首，天地惊。",
        "The Feranmut lifts its head, heaven and earth cower in fear.",
        sourceRule("每次部署我方单位时，对场上随机一个敌人及其周围造成3000点法术伤害"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BEDROCK() {
    return collectible(
        "bedrock",
        "“基岩”",
        "所有干员的部署费用+5，但攻击力、防御力和生命+15%",
        "All Operators have +5 DP Cost, but gain +15% ATK, DEF, and HP",
        "据说是专为深海猎人打造的武器。他的主人曾与斯卡蒂并肩作战，砸烂过海中巨物一根触须，但在抛出武器为斯卡蒂挡下骨质投射物后，消失在了恐鱼的大群中。",
        "A weapon said to have been made specifically for an Abyssal Hunter. His master once fought alongside Skadi, smashing the tentacles of a benthic behemoth. But when he cast aside his weapon to block a bony projectile for Skadi, he vanished into the midst of the Sea Terrors' swarm.",
        sourceRule("所有干员的部署费用+5，但攻击力、防御力和生命+15%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_WAVEBREAKER() {
    return collectible(
        "wavebreaker",
        "“分浪”",
        "所有干员的生命-25%，但再部署时间-50%",
        "All Operators have -25% HP, but have -50% Redeployment Time",
        "据说是专为深海猎人打造的武器。她的主人曾是歌蕾蒂娅的队员，被其收割的恐鱼不计其数。但为了让其他队伍能够顺利接近海中巨物，身负重伤的她自愿断后，没有人再见到过她的身影。",
        "A weapon said to have been made specifically for an Abyssal Hunter. Her master once was one of Gladiia's team members, responsible for culling countless Sea Terrors. But in order to allow the other teams to approach the benthic behemoth, she voluntarily stayed behind after being seriously wounded, and nobody ever saw her again.",
        partialRule("所有干员的生命-25%，但再部署时间-50%", stats -> stats.multiplyMaxHealth(-0.25)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_PULSE_OF_THE_OCEAN() {
    return collectible(
        "pulse_of_the_ocean",
        "“海洋的脉搏”",
        "所有我方单位的攻击速度+10，所有敌方单位的攻击速度-10",
        "All friendly units have +10 ASPD, all enemy units have -10 ASPD",
        "造型精巧的水脉仪，能够监测洋流的实时流向，在阿戈尔人的水下代步工具中比较常见。",
        "An exquisite water flow meter, capable of monitoring the flow of ocean currents in real time. A common sight among the Ægir people's underwater transportation tools.",
        implementedRule("所有我方单位的攻击速度+10，所有敌方单位的攻击速度-10", statSet(stats -> stats.addAttackSpeed(10), stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> enemyStats.addAttackSpeed(-10)))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_DETERMINATION() {
    return collectible(
        "determination",
        "“决心”",
        "所有我方干员阻挡数+2，防御力和生命+120%，法术抗性+20，让探索走向不同的结局",
        "All Operators gain Block +2, DEF and HP +120%, and RES +20; leads the exploration towards a different conclusion",
        "时至今日，水月的人性光辉仍未褪色，他作为人类接受了海嗣，也定能由海嗣重新化作人类。",
        "Mizuki's humanity has not faded. He has accepted the Seaborn as a human, and he can transform back from a Seaborn to a human.",
        sourceRule("所有我方干员阻挡数+2，防御力和生命+120%，法术抗性+20，让探索走向不同的结局"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_VIGIL() {
    return collectible(
        "vigil",
        "“观望”",
        "所有我方干员阻挡数+1，让探索走向不同的结局",
        "All Operators gain Block +1; leads the exploration towards a different conclusion",
        "水月对自己的认知并不偏向海嗣或是人类，他只是在做自己认为正确的事。",
        "Mizuki does not particularly identify with either humans or Seaborn. He only does what he thinks is right.",
        sourceRule("所有我方干员阻挡数+1，让探索走向不同的结局"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HESITATION() {
    return collectible(
        "hesitation",
        "“犹疑”",
        "所有我方干员阻挡数-1（不会低于1），让探索走向不同的结局",
        "All Operators gain Block -1 (will not go below 1); leads the exploration towards a different conclusion",
        "水月的海嗣本能压过人性后产生自我怀疑的具象表征。只要这种身份认知还不明晰，就仍会对水月造成不良影响。",
        "The manifestation of Mizuki's self-doubt, as his Seaborn instincts overcome his humanity. It will continue to have a negative impact, as long as his self-identity remains unclear.",
        sourceRule("所有我方干员阻挡数-1（不会低于1），让探索走向不同的结局"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ENDLESS_LIFE() {
    return collectible(
        "endless_life",
        "“未尽的生命”",
        "每进入新的一层时，使随机一名干员产生排异反应",
        "Afflicts a random Operator with a Rejection at the start of each new floor",
        "恐鱼体内的囊状组织，用以临时存储即将排出体外的幼卵，在主体死去后会自动脱落，在较长时间内仍能保持活性。当心，不要戳破它！",
        "Sac-like tissues found in Sea Terrors, used to store eggs prior to laying them. They detach from the host after death, maintaining activity for a period of time. Be careful not to break them!",
        sourceRule("每进入新的一层时，使随机一名干员产生排异反应"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_CAERULA_MEMORY() {
    return collectible(
        "caerula_memory",
        "深蓝回忆",
        "灯火-100，获得时立即使随机一名干员产生排异反应",
        "Light -100, immediately afflicts a random Operator with a Rejection",
        "即使你对实验室的研究内容只有丁点记忆 ，那也足以让你感到畏惧。或许在那个年代，为了生存，你，以及那些与你身份相同的人，都曾做过一些无法被饶恕的事。",
        "Even vague memories of the lab's research are enough to induce fear. Perhaps you and people like you have all done something unforgivable for the sake of survival.",
        partialRule("灯火-100，获得时立即使随机一名干员产生排异反应", stats -> stats.light(-100)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_CAERULA_ARBOR() {
    return collectible(
        "caerula_arbor",
        "深蓝之树",
        "所有敌人攻击速度+15；每次作战若未损失目标生命，战斗结束后灯火+15",
        "Enemy ASPD +15; at the end of operation, if no Life Point is lost, gain +15 Light",
        "通过祂的躯体，水月得以成为祂们的一员。祂的意志虽已消亡，祂的细胞仍在活动，熔岩已是祂的食粮，参天大树将向星球核心生长。若无人阻止，终有一日，祂将在星核中重生，并将泰拉邀为大群的一员。",
        "Through His body, Mizuki becomes one of Them. His consciousness is long gone, but His cells live on. The magma is His food, and the great tree grows towards the core of the planet. If left unchecked, He will become reborn in the planet's core, and invite Terra into we many.",
        partialRule("所有敌人攻击速度+15；每次作战若未损失目标生命，战斗结束后灯火+15", stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> enemyStats.addAttackSpeed(15))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HR_BRONZE_SEAL() {
    return collectible(
        "hr_bronze_seal",
        "人事部铜印",
        "随机临时招募1名5星干员，且直接就是已进阶的状态",
        "Recruit a random 5-star Operator that is automatically Promoted",
        "■■■收到紧急部署口令后派发给特定干员的印记，你得到它就代表着■■■已完成嘱托。",
        "Emblem assigned to specific Operators when ■■■ receives an emergency deployment command. Your receipt of it signifies that ■■■ has completed the assignment.",
        sourceRule("随机临时招募1名5星干员，且直接就是已进阶的状态"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_DOCTOR_SILVER_SEAL() {
    return collectible(
        "doctor_silver_seal",
        "博士银印",
        "随机临时招募1名6星干员，且直接就是已进阶的状态",
        "Recruit a random 6-star Operator that is automatically Promoted",
        "这块印记是你出发前嘱咐指挥中枢在合适时间送出的，现在你选定的干员已经赶到宿营处加入外勤任务，顺带把东西原封不动交还给了你。",
        "Emblem that you instructed to be sent out at the right time before you left. Your selected Operator has joined the field mission at the camp, while handing it back to you the way you left it.",
        sourceRule("随机临时招募1名6星干员，且直接就是已进阶的状态"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_WORDLESS_CORAL() {
    return collectible(
        "wordless_coral",
        "无字珊瑚",
        "每消耗1掷骰次数，回复5灯火",
        "Recover 5 Light for every die roll",
        "这物体上没有字迹，只有淡淡的香味。某种物质在空气中传播，为大群带去信息。",
        "There is no writing on it, only a faint scent. Chemicals are transmitted through the air, communicating information to we many.",
        sourceRule("每消耗1掷骰次数，回复5灯火"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_UNDERGROUND_SCORCH() {
    return collectible(
        "underground_scorch",
        "地底的灼痕",
        "每次作战若未损失目标生命，战斗结束后灯火+6",
        "At the end of operation, if no Life Point is lost, gain +6 Light",
        "海民们对陆上的事情毫不关心，自然也体会不到被阳光照耀时深入心灵的暖意。",
        "The people of the sea are unconcerned with what happens on land, and thus will never understand the warmth of the sun which reaches into the soul.",
        sourceRule("每次作战若未损失目标生命，战斗结束后灯火+6"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_GREAT_MACHINATIONS() {
    return collectible(
        "great_machinations",
        "铸阳巨械",
        "每次作战若未损失目标生命，战斗结束后灯火+9",
        "At the end of operation, if no Life Point is lost, gain +9 Light",
        "“别再贪恋陆上的阳光了，我们能创造出更好的。”",
        "'Do not envy the sun on the surface. We can do better.'",
        sourceRule("每次作战若未损失目标生命，战斗结束后灯火+9"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SOLIDIFIED_LAMP_OIL() {
    return collectible(
        "solidified_lamp_oil",
        "凝固灯油",
        "战斗损伤导致的灯火损失降低40%",
        "Light loss from battle damage reduced by 40%",
        "凝结成块状的固体灯油，可以随时取用，维持提灯的光亮。",
        "Solidified oil that can be added to a lamp to keep it burning bright.",
        sourceRule("战斗损伤导致的灯火损失降低40%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SANCTIFIED_LAMP_OIL() {
    return collectible(
        "sanctified_lamp_oil",
        "崇圣灯油",
        "战斗损伤导致的灯火损失降低60%",
        "Light loss from battle damage reduced by 60%",
        "审判庭经过处理后分发给审判官们的灯油，清淡的香味能够提神醒脑，令他们时刻保持警戒，不漏判任何一件恶事。",
        "Processed lamp oil that the Inquisition issues to its Inquisitors. The scent sharpens the mind, keeping them alert and aware of every evil.",
        sourceRule("战斗损伤导致的灯火损失降低60%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_KING_S_CRYSTAL() {
    return collectible(
        "king_s_crystal",
        "“国王的水晶”",
        "战斗结束后使目标生命-2（目标生命不会低于1），希望+1，源石锭+5",
        "At the end of battle, -2 Life Points (will not go lower than 1), +1 Hope, +5 Originium Ingots",
        "伊比利亚人与海嗣接触的历史远超他们想象，可惜他们那时并不知道，什么是海嗣。",
        "The Iberians' contact with the Seaborn goes back further than they can imagine, but they did not know what the Seaborn were back then.",
        sourceRule("战斗结束后使目标生命-2（目标生命不会低于1），希望+1，源石锭+5"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_HAND_OF_BRANDING() {
    return collectible(
        "hand_of_branding",
        "刻勋之手",
        "【无畏者】【决战者】【武者】每次击败敌人时，上述职业全体干员永久获得1%攻击力、1攻击速度和1%最大生命值（最多可获得99次）",
        "Whenever a [Dreadnought], [Duelist], or [Soloblade] defeats an enemy, all Operators of these classes gain 1% ATK, 1 ASPD and 1% max HP permanently (max 99)",
        "胜利本身，便是最好的补给。",
        "Victory is the best supply.",
        sourceRule("【无畏者】【决战者】【武者】每次击败敌人时，上述职业全体干员永久获得1%攻击力、1攻击速度和1%最大生命值（最多可获得99次）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_WRANKWOOD_SAFETY_SUIT() {
    return collectible(
        "wrankwood_safety_suit",
        "蓝卡坞安全衣",
        "每拥有一个遭诅古物，所有友方单位防御力+25%，法术抗性+10",
        "For each Cursed Curio you have, all allied units gain DEF +25%, RES +10",
        "蓝卡坞片场定制护具，用以帮助演员完成超高难度动作戏的拍摄，设计原型为某种军用器械。食铁兽曾在合作拍摄时拒绝使用该护具。",
        "Used at Wrankwood studios for high-difficulty shoots. Inspired by military gear. FEater once refused to wear it while shooting a collaborative project.",
        sourceRule("每拥有一个遭诅古物，所有友方单位防御力+25%，法术抗性+10"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FLASHING_SWORDS() {
    return collectible(
        "flashing_swords",
        "《刀光剑影》",
        "每拥有一个遭诅古物，所有友方单位攻击速度+35",
        "For each Cursed Curio you have, all allied units gain ASPD +35",
        "蓝卡坞限量发行的典藏影像合集，往届获奖动作电影的实拍花絮以及经典格斗戏的设计过程均在其中首度公开。",
        "Limited-edition montage from Wrankwood, showing the making-of and behind-the-scenes footage of classic action movies.",
        sourceRule("每拥有一个遭诅古物，所有友方单位攻击速度+35"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_MERCENARY_INSURANCE() {
    return collectible(
        "mercenary_insurance",
        "佣兵保单",
        "所有我方单位获得10%的庇护，灯火低于50时改为获得35%的庇护",
        "All friendly units gain 10% Sanctuary, increased to 35% if Light is below 50.",
        "为独立雇佣兵提供的特殊保险，当任务失败或损耗过大时，第三方机构将向佣兵提供一笔保障性收入。",
        "Special insurance offered to independent mercenaries, indemnifying against mission failure or excessive loss.",
        sourceRule("所有我方单位获得10%的庇护，灯火低于50时改为获得35%的庇护"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_BROKEN_WAND_LEARNING() {
    return collectible(
        "broken_wand_learning",
        "断杖-学识",
        "每拥有一个收藏品，【术师】干员的攻击力+2%，攻击速度+1",
        "For every collectible owned, [Caster] ATK +2%, ASPD +1",
        "遍览大地蕴藏之学识，无知便成为敌人的软肋。",
        "When you have taken in the wisdom of all the land, ignorance becomes the enemy's weakness.\n",
        sourceRule("每拥有一个收藏品，【术师】干员的攻击力+2%，攻击速度+1"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BROKEN_WAND_LIGHT_OF_WISDOM() {
    return collectible(
        "broken_wand_light_of_wisdom",
        "断杖-智慧之光",
        "每拥有一个收藏品，【术师】干员的攻击力+3%，攻击速度+2",
        "For every collectible owned, [Caster] ATK +3%, ASPD +2",
        "丰富的阅历不应只留在脑中，它应成为制胜的关键。",
        "Knowledge and experience should not remain untapped. They should be the key to victory.\n",
        sourceRule("每拥有一个收藏品，【术师】干员的攻击力+3%，攻击速度+2"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BROKEN_WAND_SOLVING() {
    return collectible(
        "broken_wand_solving",
        "断杖-破解",
        "场上每有1名【术师】干员，所有敌人的法术抗性-12",
        "For every [Caster] Operator on the field, enemy RES -12",
        "术师最大的敌人自然是术师。",
        "A caster's greatest enemy is another caster.\n",
        sourceRule("场上每有1名【术师】干员，所有敌人的法术抗性-12"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_IRON_GUARD_WALL() {
    return collectible(
        "iron_guard_wall",
        "铁卫-城墙",
        "所有【重装】干员防御力+40%，法术抗性+10",
        "[Defender] Operators gain DEF +40%, RES +10",
        "没有人会愚蠢到用身躯冲撞城墙，对吧？",
        "No one is stupid enough to ram a wall with their body, right?\n",
        implementedRule("所有【重装】干员防御力+40%，法术抗性+10", statSet(forProfession(SkillProfession.DEFENDER, stats -> stats.multiplyDefense(0.4)), forProfession(SkillProfession.DEFENDER, stats -> stats.addResistance(10)))),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_IRON_GUARD_TOWER() {
    return collectible(
        "iron_guard_tower",
        "铁卫-高塔",
        "所有【重装】干员防御力+70%，法术抗性+20，受到元素损伤减少60%",
        "[Defender] Operators gain DEF +70%, RES +20, Elemental Injury taken -60%",
        "它高耸入云，俯视着敌人在其脚下跳来蹦去。",
        "It rises into the clouds, looking down at the enemies jumping up and down by its feet.\n",
        partialRule("所有【重装】干员防御力+70%，法术抗性+20，受到元素损伤减少60%", statSet(forProfession(SkillProfession.DEFENDER, stats -> stats.multiplyDefense(0.7)), forProfession(SkillProfession.DEFENDER, stats -> stats.addResistance(20)))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SURVIVOR_CONTRACT() {
    return collectible(
        "survivor_contract",
        "生还者合约",
        "战斗开始时，使随机一名干员攻击力和防御力+20%，且每次战斗结束后额外+20%",
        "At the start of battle, a random Operator gains +20% ATK and DEF, plus an additional +20% at the end of each battle",
        "一本包含海量条目的合约书。书中记录的每一项条款都是抛弃感性与运气的经验结晶，逐一履行，便能成为幸存的最后一人。",
        "A very long contract, in which every term and condition is the result of experience, leaving no room for chance or sentiment. Satisfy them all to be the final survivor.\n",
        sourceRule("战斗开始时，使随机一名干员攻击力和防御力+20%，且每次战斗结束后额外+20%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_FOSTERER_GENOTYPE() {
    return collectible(
        "fosterer_genotype",
        "养育者基因种",
        "排异反应干员会同化周围2名正常干员，同化完成将临时获得【进化】：排异反应无效，获得35%的庇护，攻击无视目标35%的法术抗性和防御力",
        "An Operator with Rejection will assimilate 2 nearby normal Operators. When assimilation is complete, the Operator with Rejection temporarily gains [Evolution]: Rejection has no effect, gains 35% Sanctuary, attacks ignore 35% RES and DEF",
        "集体进化由个体突变推动，个体难题由集体智慧解决。陆对这一模型很有信心，如果养育者计划能有足够的时间，或许……",
        "Collective evolution is driven by individual mutation. Individual problems are solved by collective intelligence. Lu is highly confident in this model. If the Fosterer Project has enough time, perhaps...",
        sourceRule("排异反应干员会同化周围2名正常干员，同化完成将临时获得【进化】：排异反应无效，获得35%的庇护，攻击无视目标35%的法术抗性和防御力"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SPECTRUM_ANALYZER() {
    return collectible(
        "spectrum_analyzer",
        "光谱分析仪",
        "抗干扰指数+1，护盾值+2",
        "Anti-Interference Index +1, +2 Objective Shield",
        "请谨慎，关于萨米的异常现象，测定电磁辐射的结果和样本显微结晶分析的结果完全互斥。",
        "Please be careful regarding the abnormal phenomena in Sami. The results from measuring electromagnetic radiation and analyzing the specimen's micro-crystals are completely incompatible.",
        registeredRule("抗干扰指数+1，护盾值+2", statSet(stats -> stats.antiInterferenceIndex(1), stats -> stats.addMaxHealth(2))),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_LEISURE_TIME() {
    return collectible(
        "leisure_time",
        "闲暇时光",
        "可携带干员+1，抗干扰指数+1",
        "Squad Size Limit +1, Anti-Interference Index +1",
        "孩子们擅长捡回各种东西拼搭自己的游乐王国。那些外来者总是在半路上突然抛下他们的载具，泽地的居民对此已经见怪不怪。",
        "The children are experts at picking up all sorts of things to build their own play-kingdom. The wetland dwellers are accustomed to the outsiders always abruptly abandoning their vehicles halfway to their destination.",
        registeredRule("可携带干员+1，抗干扰指数+1", statSet(stats -> stats.squadCapacity(1), stats -> stats.antiInterferenceIndex(1))),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_TRANQUIL_SPRING_CURRENT() {
    return collectible(
        "tranquil_spring_current",
        "宁静泉流",
        "抗干扰指数+2，目标生命上限+3，希望+3",
        "Gain +2 Anti-Interference Index, +3 Max Life Points, and +3 Hope",
        "在跟随水滴声逃出风雪围困之后，这份标本成了科考队的护身符。",
        "After escaping a siege of wind and snow by following the sound of water drops, this specimen has become a protective charm for the research team.",
        registeredRule("抗干扰指数+2，目标生命上限+3，希望+3", statSet(stats -> stats.antiInterferenceIndex(2), stats -> stats.addMaxHealth(3), stats -> stats.hope(3))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_DEVOTION() {
    return collectible(
        "devotion",
        "“奉献”",
        "远程干员攻击或治疗时，对攻击范围内一名随机敌人造成150点法术伤害",
        "Ranged Operators deal 150 Arts damage to a random enemy within attack range with every attack or heal",
        "自远古以来，这一部族笃信自己掌握着有关萨米的真相：原本万物各有声音，直到“祂”代替万物言语。",
        "Since antiquity, this tribe has believed that they hold a truth about Sami: Everything originally had a voice, until 'It' spoke for all of them.",
        sourceRule("远程干员攻击或治疗时，对攻击范围内一名随机敌人造成150点法术伤害"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_CELESTIAL_DUST() {
    return collectible(
        "celestial_dust",
        "天穹尘埃",
        "远程干员在手动开启技能时，使攻击范围内的所有敌人浮空2秒",
        "Ranged Operators Levitate all enemies within attack range for 2 seconds when a skill is manually activated",
        "她轻轻吹动无数次日升月落、星辰更替的记录。她的生命轻易就被吹走。",
        "She gently blows away the countless records of sunrises, moonsets, and the changing of the stars. Her life is blown away that easily.",
        sourceRule("远程干员在手动开启技能时，使攻击范围内的所有敌人浮空2秒"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_FROZEN_HUSK() {
    return collectible(
        "frozen_husk",
        "冰结的躯壳",
        "近战干员在受到伤害后5秒内，攻击速度+40",
        "Melee Operators gain ASPD +40 for 5 seconds after taking damage",
        "驻守在冬牙群山的萨米战士经常被一种幻觉鼓舞：随着生命流向大地，他们原本被冻僵的身体变得越来越轻快。",
        "The warriors of Sami stationed in the Fjal Vetrtonn are often inspired by an illusion: As their life flows toward the earth, their originally frozen bodies become lighter and lighter.",
        sourceRule("近战干员在受到伤害后5秒内，攻击速度+40"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_RING_OF_THORNS() {
    return collectible(
        "ring_of_thorns",
        "荆棘环",
        "近战干员在受到治疗后，使自身和周围4格内的干员获得1点技力",
        "When Melee Operators are healed, they and Operators in the adjacent 4 tiles will recover 1 SP",
        "你得以从扭曲的恐惧中稳定自我。荆棘上残留着血渍，不知道是刺伤了你还是雪祀自己。他们使用任何手段时都不会眨一下眼睛。",
        "You were able to steady yourself while in a distorted fear. There is blood left on the thorns, and you don't know if it was you or the Snowpriest who was pricked by them. They will use whatever means they have, without batting an eye.",
        sourceRule("近战干员在受到治疗后，使自身和周围4格内的干员获得1点技力"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_PEEK_INTO_THE_ETERNAL_NIGHT() {
    return collectible(
        "peek_into_the_eternal_night",
        "“永夜的窥视”",
        "所有干员的攻击力+25%，攻击速度+25，但每秒流失25生命值",
        "All Operators gain ATK +25% and ASPD +25, but lose 25 HP every second",
        "她转身走向北地的那一刻，黑色的群山铺陈于眼前，如她多年前所见的预兆。",
        "The moment she turns around to head north, mountains in black lie before her eyes, just like the omen she saw many years ago.",
        partialRule("所有干员的攻击力+25%，攻击速度+25，但每秒流失25生命值", statSet(stats -> stats.multiplyAttack(0.25), stats -> stats.addAttackSpeed(25))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_DEAD_TREE_S_ECHO() {
    return collectible(
        "dead_tree_s_echo",
        "“枯木的回声”",
        "所有干员每秒恢复50生命值，但最大生命-25%",
        "All Operators recover 50 HP every second, but have Max HP -25%",
        "击鼓唤先灵，击鼓通草木，击鼓驱邪祟。一位又一位萨满巫医接过兽骨锤，其中有些人留下的回声比别人更长久。",
        "Drum to call their ancestors' souls, drum to communicate with the vegetation, drum to exorcise evil spirits. Shaman after shaman have received this beastbone hammer, some leaving a longer echo than the others.",
        sourceRule("所有干员每秒恢复50生命值，但最大生命-25%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_FROZEN_WHETSTONE() {
    return collectible(
        "frozen_whetstone",
        "凝冰砥石",
        "战斗开始时，使随机一名干员生命值、攻击力、防御力、法术抗性、阻挡数、初始技力、部署费用翻倍",
        "When a battle begins, the Max HP, ATK, DEF, RES, Block Count, Starting SP, and DP Cost of a random Operator is doubled",
        "石面光滑如冰，制作者请求了山岩的许可，将它过于沉重的力量复写在刀刃上。",
        "The stone's surface is smooth as ice. The maker asked the mountain cliffs for permission to replicate their overpowering strength in their blade.",
        sourceRule("战斗开始时，使随机一名干员生命值、攻击力、防御力、法术抗性、阻挡数、初始技力、部署费用翻倍"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_CLAIRVOYANT_S_REVEAL() {
    return collectible(
        "clairvoyant_s_reveal",
        "远见者之示",
        "所有干员部署费用和再部署时间随机变化",
        "The DP Cost and Redeployment Time of all Operators is randomly changed",
        "没有任何一个方向能避开厄运。",
        "There is no direction you can head to avoid misfortune.",
        sourceRule("所有干员部署费用和再部署时间随机变化"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_WEAVE_OF_THORNS_AND_LEAVES() {
    return collectible(
        "weave_of_thorns_and_leaves",
        "荆与叶的织带",
        "每次战斗会随机1个可部署位置，该位置和周围8格内的我方单位具有迷彩",
        "Designates one deployable tile at the beginning of battle; allied units deployed on that tile or its surrounding 8 tiles will gain Camouflage",
        "于此环中，得受荆棘草叶之偏爱。",
        "Those within this ring receive the favor of the thorns and leaves.",
        sourceRule("每次战斗会随机1个可部署位置，该位置和周围8格内的我方单位具有迷彩"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_LIVING_WOODPLATE() {
    return collectible(
        "living_woodplate",
        "活木甲",
        "所有我方近战单位部署时获得相当于最大生命50%的屏障",
        "All allied melee units gain a Barrier equal to 50% of their Max HP on deployment",
        "萨满们知晓树木对于生存的执着，所以当战士也拥有这种意志时，他们便会邀请树木成为战士的护甲。",
        "The shamans know how trees will cling to survival, so they invite the trees to become armor for a warrior possessing a similar will.",
        sourceRule("所有我方近战单位部署时获得相当于最大生命50%的屏障"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_FINSHELL_SHIELD() {
    return collectible(
        "finshell_shield",
        "鳞皮壳盾",
        "所有我方远程单位部署时获得3层护盾",
        "All allied ranged units gain 3 layers of Shield on deployment",
        "来自远方的层层创伤，越不过躯壳垒起的重重防护。",
        "The stacks of distant trauma cannot surpass the layers of protection built by the body.",
        sourceRule("所有我方远程单位部署时获得3层护盾"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_FROST_BUCK_S_PAULDRON() {
    return collectible(
        "frost_buck_s_pauldron",
        "霜牡的肩甲",
        "所有我方单位阻挡2名及以上敌人后，攻击力+40%，防御力+40%，持续30秒",
        "All allied units gain ATK +40% and DEF +40% for 30 seconds when blocking 2 or more enemies",
        "他的遗体上见不到伤痕，只有一双眼睛流干了血泪。",
        "There are no signs of injuries on his body, only a pair of eyes drained of blood and tears.",
        sourceRule("所有我方单位阻挡2名及以上敌人后，攻击力+40%，防御力+40%，持续30秒"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SNOW_DOE_S_GLOVE() {
    return collectible(
        "snow_doe_s_glove",
        "雪牝的护手",
        "所有我方单位阻挡3名及以上敌人后，攻击速度+80，获得50%的物理和法术闪避，持续30秒",
        "All allied units gain ASPD +80 and obtain 50% Physical and Arts Dodge for 30 seconds when blocking 3 or more enemies",
        "她向寒灾射去了箭袋中最后的三支箭，寒灾用它们结束了她的生命。",
        "She shoots the last three arrows in her quiver at the Frozen Monstrosity, who used them to end her life.",
        sourceRule("所有我方单位阻挡3名及以上敌人后，攻击速度+80，获得50%的物理和法术闪避，持续30秒"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_EMPTY_FOWLBEAST() {
    return collectible(
        "empty_fowlbeast",
        "空羽兽",
        "【召唤物】生命值+30%，攻击力+30%，且在场时使它所属的干员攻击力+60%",
        "Summons gain Max HP +30%, ATK +30%, and grant ATK +60% to their summoner",
        "肉体早已消逝，灵魂于壳中永驻。枝叶间的不融生灵，带着冰凌于林中展翅。",
        "The body is long since gone, but the soul remains in the shell for eternity. The unmelting creatures in the branches and leaves carry icicles as they spread their wings in the forest.",
        sourceRule("【召唤物】生命值+30%，攻击力+30%，且在场时使它所属的干员攻击力+60%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ROCK_HORN() {
    return collectible(
        "rock_horn",
        "岩角号",
        "所有干员周围4格每有一名干员，攻击力+20%",
        "All Operators gain ATK +20% for every Operator in the adjacent 4 tiles",
        "想要在萨米召集人手，吹号绝对是最为有效的措施。",
        "If you want to gather up manpower in Sami, blowing a trumpet is definitely the best way to do it.",
        sourceRule("所有干员周围4格每有一名干员，攻击力+20%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_INEXTINGUISHABLE_TORCH() {
    return collectible(
        "inextinguishable_torch",
        "不灭的火炬",
        "所有干员周围4格每有一名干员，攻击速度+30",
        "All Operators gain ASPD +30 for every Operator in the adjacent 4 tiles",
        "火焰代表着光芒与温暖，聚集在火焰下，绝望就成了希望。",
        "Flames represent radiance and warmth. Gathering round a fire turns despair into hope.",
        sourceRule("所有干员周围4格每有一名干员，攻击速度+30"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_LAKEBED_AEGIS() {
    return collectible(
        "lakebed_aegis",
        "湖中神盾",
        "护盾值+1；战斗开始时如果拥有护盾值，所有我方单位的最大生命+60%",
        "Objective Shield +1; all allied units gain Max HP +60% if you have any Objective Shield at the beginning of a battle",
        "三十年前，孩童的玩具木盾落入湖中。三十年后，他凿开冰封湖泊，取出盾牌，踏上英雄的旅途。",
        "Three decades ago, a child dropped his toy wooden shield into the lake. Three decades later, he bores through the frozen lake, retrieves his shield, and embarks on a hero's journey.",
        partialRule("护盾值+1；战斗开始时如果拥有护盾值，所有我方单位的最大生命+60%", stats -> stats.addMaxHealth(1)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_ANCIENT_FRESCO() {
    return collectible(
        "ancient_fresco",
        "古老壁画",
        "敌人进入和解除浮空、失重状态时，受到1000点法术伤害，并在10秒内失去特殊能力",
        "When an enemy enters or is freed from Levitation and/or Weightless, they take 1000 Arts damage and are Silenced for 10 seconds",
        "走出洞窟，抬头看看吧，自古以来，也不尽然。",
        "Walk out of the cave, and raise your head. This has never been right, even since antiquity.",
        sourceRule("敌人进入和解除浮空、失重状态时，受到1000点法术伤害，并在10秒内失去特殊能力"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_GALLERIA_STELLARIA_S_SPLENDOR() {
    return collectible(
        "galleria_stellaria_s_splendor",
        "万星园之辉",
        "敌人进入和解除浮空、失重状态时，受到2000点法术伤害，并在10秒内受到的伤害+30%",
        "When an enemy enters or is freed from Levitation and/or Weightless, they take 2000 Arts damage and take 30% more damage for 10 seconds",
        "在那一瞬，真实之光普照大地。",
        "At that moment, the light of truth shone onto the earth.",
        sourceRule("敌人进入和解除浮空、失重状态时，受到2000点法术伤害，并在10秒内受到的伤害+30%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_CLOUD_MOVING_TOTEM() {
    return collectible(
        "cloud_moving_totem",
        "移云者图腾",
        "所有敌方单位移动速度-15%，且失重时重量额外下降2个等级",
        "All enemies have -15% Movement Speed, and the Weightless effect further drops their Weight Level by 2",
        "信奉着羽兽之王的萨米部落制作了这个图腾，据说只要诚心呼唤，它就会鼓动双翼扬起风暴，让大地为之震颤。",
        "The Sami tribe that believes in the King of Fowlbeasts created this totem. It is said that a sincere call will stir its wings, raising a storm and making the earth tremble.",
        partialRule("所有敌方单位移动速度-15%，且失重时重量额外下降2个等级", stats -> stats.addEnemyMovementSpeedReduction(0.15)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_IRON_GUARD_CONSOLIDATION() {
    return collectible(
        "iron_guard_consolidation",
        "铁卫-整固",
        "所有【重装】干员生命值+40%，防御力+40%，法术抗性+20",
        "Defender Operators gain HP +40%, DEF +40%, and RES +20",
        "无论命运的打击多么沉重，扛下它。",
        "No matter how hard fate hits you, just take it in.",
        implementedRule("所有【重装】干员生命值+40%，防御力+40%，法术抗性+20", statSet(forProfession(SkillProfession.DEFENDER, stats -> stats.multiplyMaxHealth(0.4)), forProfession(SkillProfession.DEFENDER, stats -> stats.multiplyDefense(0.4)), forProfession(SkillProfession.DEFENDER, stats -> stats.addResistance(20)))),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_RUSTED_BLADE_SPEARHEAD_SHARPENING() {
    return collectible(
        "rusted_blade_spearhead_sharpening",
        "锈刃-锋芒发硎",
        "所有【特种】干员的攻击力+40%，力度+2",
        "Specialist Operators have +40% ATK and +2 shift strength",
        "“试试新武器——还是说，就在刚刚，你已经试过了？”",
        "'Try this new weapon out—or did you already try it out just now?",
        partialRule("所有【特种】干员的攻击力+40%，力度+2", forProfession(SkillProfession.SPECIALIST, stats -> stats.multiplyAttack(0.4))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_FISTICUFFS() {
    return collectible(
        "hand_of_fisticuffs",
        "缠斗之手",
        "【铁卫】、【驭法铁卫】和【决战者】每2秒束缚自身附近所有敌人1秒，并造成相当于攻击力100%的真实伤害",
        "Protector, Arts Protector, and Duelist Operators inflict Bind on all nearby enemies lasting 1 second every 2 seconds, and deal True damage equal to 100% of their ATK",
        "她站在那里的意思就是，不可越过。",
        "What she means by standing over there, is that you cannot cross.",
        sourceRule("【铁卫】、【驭法铁卫】和【决战者】每2秒束缚自身附近所有敌人1秒，并造成相当于攻击力100%的真实伤害"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_ADAPTABILITY() {
    return collectible(
        "hand_of_adaptability",
        "应机之手",
        "【冲锋手】、【尖兵】和【战术家】的部署费用-10，且击杀敌人后使所有未部署干员下次部署费用-1",
        "Charger, Pioneer, and Tactician Operators have -10 DP Cost, and after defeating an enemy grant -1 DP Cost to all undeployed Operators on their next deployment",
        "现在我们不缺战场情报，我们缺一个来得及读完情报的队长。",
        "What we lack now is not battlefield intelligence, but a captain who is able to finish reading said intelligence in time.",
        sourceRule("【冲锋手】、【尖兵】和【战术家】的部署费用-10，且击杀敌人后使所有未部署干员下次部署费用-1"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_OUTBURST() {
    return collectible(
        "hand_of_outburst",
        "溃决之手",
        "【重射手】、【神射手】和【攻城手】的攻击伤害随目标生命下降而提升最高50%，并对生命20%以下的敌人强制击杀",
        "Heavyshooter, Deadeye, and Besieger Operators deal increasing damage to enemies as the target's HP decreases, up to a maximum of 50%, and instantly defeat enemies below 20% HP",
        "砸开每一道裂痕，或者制造第一道裂痕。",
        "Smash open every single crack, or perhaps make the very first.",
        sourceRule("【重射手】、【神射手】和【攻城手】的攻击伤害随目标生命下降而提升最高50%，并对生命20%以下的敌人强制击杀"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_KNIGHT_LANCE_PRO() {
    return collectible(
        "knight_lance_pro",
        "骑士长枪专业版",
        "战场的宝箱会掉落更多的源石锭",
        "Treasure Chests on the battlefield will drop more Originium Ingots",
        "不用担心自身力量不足！利用小小的杠杆，在每一局中撬动更多，赢得更多！大奖将是萨米假日之旅！",
        "Don't worry if your own strength isn't enough! Use these tiny levers to move more, and win more in every round! The grand prize is a holiday trip to Sami!",
        sourceRule("战场的宝箱会掉落更多的源石锭"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FULL_AUTO_MAINTENANCE_STATION() {
    return collectible(
        "full_auto_maintenance_station",
        "全自动维修台",
        "抗干扰指数+2，但是坍缩值+2",
        "Anti-Interference Index +2, but +2 Collapse value",
        "它有点脏，但能完全自动运转，而且没人知道它为什么在自动运转，从早到晚，哪怕无人使用。雷神工业表示他们从未设计过这样一款产品。",
        "It's a little dirty, but can run on complete full-auto. And, for reasons no one knows, even runs from morning to night when no one's using it. Raythean Industries claims to have never designed such a product.",
        partialRule("抗干扰指数+2，但是坍缩值+2", stats -> stats.antiInterferenceIndex(2)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_WARNING_FENCE() {
    return collectible(
        "warning_fence",
        "警戒篱木",
        "坍缩值-2，目标生命上限+2",
        "-2 Collapse value, +2 Max Life Points",
        "雪地中显得十分醒目的彩色装饰，使人即使在做梦时也能清晰想象。土地没有边界，但漫游的心神需要知道恐惧在何处潜伏。",
        "The colorful decorations stand out in the snow, letting people imagine them clearly even in their dreams. The territory has no borders, but the roaming mind needs to know where fear is lurking.",
        registeredRule("坍缩值-2，目标生命上限+2", statSet(stats -> stats.collapseValue(-2), stats -> stats.addMaxHealth(2))),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_HEAVY_MUSIC_ANTHOLOGY() {
    return collectible(
        "heavy_music_anthology",
        "《重型音乐选集》",
        "坍缩值-2，希望+2",
        "-2 Collapse value, Hope +2",
        "驻留萨米南部的哥伦比亚救援人员每年冬天都有那么几次任务，是救助为了拍摄音乐影片而在积雪森林中迷路的乐队。",
        "Every winter, Columbian rescue crews stationed in southern Sami have several missions to rescue bands who get lost in the snowy woods while filming music videos.",
        registeredRule("坍缩值-2，希望+2", statSet(stats -> stats.collapseValue(-2), stats -> stats.hope(2))),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_WANDERING_CASTER_S_NOSTALGIA() {
    return collectible(
        "wandering_caster_s_nostalgia",
        "流浪术师的怀乡",
        "进入深埋迷境时，坍缩值-3",
        "When entering Secluded Passage, -3 Collapse value",
        "有人在绝望的追念中仿制了这些音符。刀刃划过空气时演奏的旋律令人感到诡异的和谐，但并不依循十二平均律。",
        "Someone has copied these notes down in a desperate attempt to remember them. The melody performed by the blade cutting through the air is strangely harmonious, but does not follow the equal temperament of music at all.",
        sourceRule("进入深埋迷境时，坍缩值-3"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_CEREMONY_BELL() {
    return collectible(
        "ceremony_bell",
        "仪式铃",
        "失去抗干扰指数时，回复1目标生命",
        "Recover 1 Life Point whenever you lose Anti-Interference Index",
        "闭上眼。你所渴望的，都要向内寻求。",
        "Close your eyes. Look inward for all that you crave.",
        sourceRule("失去抗干扰指数时，回复1目标生命"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_NORTHERN_PIONEER_S_CRUTCH() {
    return collectible(
        "northern_pioneer_s_crutch",
        "北方拓荒者拐杖",
        "抗干扰指数不低于4时，每进入一个非战斗节点，获得源石锭+2",
        "When Anti-Interference Index is no less than 4, gain +2 Originium Ingots whenever you enter a non-combat node",
        "能探测到一些危险，但更重要的是能探测到钱币。依据合同，只有当地建起符合哥伦比亚标准的医疗机构之后，公司才会承担拓荒者的医疗保险费用。",
        "It can detect some types of hazards, but more importantly, it can detect coins. According to the terms of the contract, the company will only bear the costs of the Pioneers' medical insurance after a local medical institute adhering to Columbian standards has been established.",
        sourceRule("抗干扰指数不低于4时，每进入一个非战斗节点，获得源石锭+2"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_HUNTERS_INSIGHT() {
    return collectible(
        "hunters_insight",
        "猎人的洞察",
        "所有干员攻击【坍缩体】时无视50%的防御力和法术抗性，并使其停顿0.5秒",
        "All Operators ignore 50% DEF and RES when attacking [Collapsals], and Slow them for 0.5 sec",
        "在意志完全被夺取之前，女孩向靠近的救援者恳求：“让我拉弓，让我战胜它，让我永不畏惧。”",
        "Before her will is completely wrested away, the girl begs the rescuer approaching her: 'Let me draw the bowstring, let me defeat it, let me never fear.'",
        sourceRule("所有干员攻击【坍缩体】时无视50%的防御力和法术抗性，并使其停顿0.5秒"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_WEAVE_OF_SNOW_AND_SOIL() {
    return collectible(
        "weave_of_snow_and_soil",
        "雪与土的织带",
        "每次战斗会随机1个可部署位置，该位置和周围8格的我方干员攻击和受击回复技力时，使范围内另一名干员同时回复",
        "Designates one deployable tile at the beginning of battle; allied Operators deployed on that tile or its surrounding 8 tiles will grant 1 SP to another Operator within those tiles whenever they gain SP from Offensive or Defensive Recovery",
        "于此环中，得受霜雪大地之青睐。",
        "Those within this ring receive the attention of the snow and earth.",
        sourceRule("每次战斗会随机1个可部署位置，该位置和周围8格的我方干员攻击和受击回复技力时，使范围内另一名干员同时回复"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ANCIENT_TREE_FRUIT() {
    return collectible(
        "ancient_tree_fruit",
        "古地树实",
        "立即获得三件随机的小玩意",
        "Immediately obtain 3 random trinkets",
        "萨米的孩子将愿望许进树种里埋入皑皑白雪。待到大树结实，取下前四个果实，将最小的那个留给后代许愿，余下的树果，便是向萨米祈求答案的贡品。",
        "The children of Sami made their wishes into tree seeds, and buried them in the snow. When the tree bears fruit, the first four are taken, and the smallest is left for future generations to make their wishes. The remaining fruits are tributes to Sami, praying for answers.",
        sourceRule("立即获得三件随机的小玩意"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_POLAR_RETROFIT_PACKAGE() {
    return collectible(
        "polar_retrofit_package",
        "极地改型包",
        "支援道具使用时会在周围4格同时使用",
        "When used, Support items will also be used simultaneously on the adjacent 4 tiles",
        "沃尔沃特科钦斯基极端环境设备测试部荣誉出品。能对各种通用型号装置进行极地适应性改修，令其在寒冷环境下发挥更大效用。",
        "Proudly produced by the Volvort Kochinski Extreme Environment Equipment Testing Department. It can modify a variety of devices, adapting them for polar regions and increasing their effectiveness in cold environments.",
        sourceRule("支援道具使用时会在周围4格同时使用"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_ROUNDSTONE_ALTAR() {
    return collectible(
        "roundstone_altar",
        "圆石祭坛",
        "战斗后有概率使所有我方单位的攻击力和防御力永久+5%（最多叠加10层）",
        "There is a chance after battle to permanently increase all allied units' ATK and DEF by 5% (Can stack up to 10 times)",
        "送上祭品，获得回馈。它是如此地便利，以至于人们都没有思考过，他们到底在向什么献祭。",
        "Offer a sacrifice, and receive a reward. It is so convenient that the people never think about what they are directing their sacrifices to.",
        sourceRule("战斗后有概率使所有我方单位的攻击力和防御力永久+5%（最多叠加10层）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_RAINBOW_JAR() {
    return collectible(
        "rainbow_jar",
        "彩虹瓮",
        "每次敌人进入保护目标点时，立即使随机一名再部署中的单位当前再部署时间减半",
        "Whenever an enemy enters the Objective Point, immediately selects a random Operator waiting to be re-deployable, and reduces their Redeployment Time by half",
        "它通常被萨米人放在彩虹落下的地方并精心保存，用以对抗踪迹不定的天灾与人祸",
        "It is usually placed by the Sami people wherever the rainbow falls. They are meticulously maintained in order to withstand the untraceable catastrophes, both natural and man-made.",
        sourceRule("每次敌人进入保护目标点时，立即使随机一名再部署中的单位当前再部署时间减半"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SPROUTED_STICK() {
    return collectible(
        "sprouted_stick",
        "芽棍",
        "每次进入树篱之途或失与得节点，护盾值+2",
        "Gain +2 Objective Shield when entering Bosky Passage or Lost and Found nodes",
        "它陪伴着一位健硕、黝黑的库兰塔走出黄沙之国，陪伴着成为莱茵生命科学考察科主任的他走入银白原野。即使在一场意外中与主人离散，它仍能为持有者带来生存的启示。",
        "It accompanied a sturdy and swarthy Kuranta out of the country of sand, accompanied him, the Director of Rhine Lab's Scientific Investigation Section, into the silverwhite plains. Even though it was separated from its owner in an accident, it can still bring its wielder a revelation about survival.",
        sourceRule("每次进入树篱之途或失与得节点，护盾值+2"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_CANNOT_S_MARK() {
    return collectible(
        "cannot_s_mark",
        "坎诺特印记",
        "每次在诡意行商购买一件道具，或在失与得交换一次，希望+1",
        "Gain 1 Hope whenever you buy an item at the Rogue Trader or make an exchange at Lost and Found",
        "有人愿意卖坎诺特一个面子，也有人值得坎诺特卖个“面子”。",
        "Some people are willing to do Cannot a favor, and some are worthy for Cannot to do a 'favor.'",
        sourceRule("每次在诡意行商购买一件道具，或在失与得交换一次，希望+1"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_GUIDING_VINEDOLL() {
    return collectible(
        "guiding_vinedoll",
        "指路藤偶",
        "通过纵向通路时有概率不降低抗干扰指数",
        "Anti-Interference Index has a chance to not decrease when traveling through a vertical path",
        "萨满们用藤蔓制作的小傀儡，时常被用于在林间寻路。虽然不是很准确，但要是找对了，也能为勘察机器节省大量能耗。",
        "The shamans often make these little puppets out of vines to navigate the forest. Even though they are not very accurate, striking true can conserve a lot of the survey equipment's energy.",
        sourceRule("通过纵向通路时有概率不降低抗干扰指数"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BROKEN_URSUS_BLADE() {
    return collectible(
        "broken_ursus_blade",
        "乌萨斯断刃",
        "每有1级坍缩范式，战斗获得的指挥经验+10%",
        "Gain Command EXP +10% for each level of Collapsal Paradigm",
        "他并非缺乏决心，其意志也无可置疑，只是在自决那一瞬间，身体的本能反应铸成了不可挽回的错误。",
        "It was not for a lack of determination; his willpower is beyond question. It was only that, at that moment of self-determination, his body instinctively committed an irreversible mistake.",
        sourceRule("每有1级坍缩范式，战斗获得的指挥经验+10%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SLYTOOTH_FOREST_DESTROYER() {
    return collectible(
        "slytooth_forest_destroyer",
        "伶牙，毁林者",
        "战斗有概率额外掉落1个密文板",
        "Battles have a chance to drop an extra 1 Foldartal",
        "当它发现人类愿意用树果和它交换木片后，这只鼷兽就开始肆无忌惮地在林间啃食树皮获取木片。它可能也没想到，有那么些碎片中，包含着萨米的无上旨意。",
        "When it discovered that humans were willing to exchange tree fruits for wood chips, this musbeast began to unscrupulously devour the forest bark for more chips. It may not have expected that many of those chips would embody Sami's supreme will.",
        sourceRule("战斗有概率额外掉落1个密文板"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SUPPORT_FOG_MACHINE() {
    return collectible(
        "support_fog_machine",
        "支援雾机",
        "每次战斗中携带1个失修舞台雾机",
        "Bring a Broken Fog Machine along to each battle",
        "紧急提供给外勤干员的舞台雾机，有些失修了。",
        "An emergency Fog Machine provided to field operators that appears to be broken.",
        sourceRule("每次战斗中携带1个失修舞台雾机"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_ALTAR_TYPE_RADAR() {
    return collectible(
        "altar_type_radar",
        "祭坛式雷达",
        "每次战斗中携带1个祭坛式雷达（定期扫描周围敌人）",
        "Bring 1 Altar-type Radar to each battle",
        "无线电定位装置，能够快速展开和收起，避免误伤。从切尔诺伯格回来后，工程部一些干员就致力于掌握这种源石装置的原理，战胜它们曾造成的伤害。",
        "Wireless locating device that can be quickly deployed and withdrawn to avoid friendly casualties. After returning from Chernobog, engineers have worked to understand the principles behind the device, overcoming the harm they once caused.",
        sourceRule("每次战斗中携带1个祭坛式雷达（定期扫描周围敌人）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_R_45_PORTABLE_GRAMOPHONE() {
    return collectible(
        "r_45_portable_gramophone",
        "R-45便携式“留声机”",
        "每次战斗中携带1个R-45便携式“留声机”（激活后攻击并调查敌人）",
        "Bring 1 R-45 Portable 'Gramophone' to each battle",
        "源石技艺传输装置，测试中版本。工程部尝试设计一种源石技艺适应性较低的干员也能使用的传输协议。一些莱塔尼亚人的评价是“不够优雅”。",
        "Originium Arts conductor prototype, part of Engineering's attempt to create a transmission protocol that operators with lower Originium Arts Assimilation can use. Criticized by some Leithanians as lacking in elegance.",
        sourceRule("每次战斗中携带1个R-45便携式“留声机”（激活后攻击并调查敌人）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ORNAMENTAL_GIANT_MUSHROOM() {
    return collectible(
        "ornamental_giant_mushroom",
        "景观巨蕈",
        "每次战斗中携带2个景观巨蕈（周围4格我方单位调查能力增强）",
        "Bring 2 Ornamental Giant Mushrooms to each battle",
        "人造遮蔽物，能够在恶劣天气里遮挡风雨，或是融入自然环境，为干员提供临时庇护。实际上它看起来总是和自然环境格格不入，哪怕在萨尔贡雨林也是。",
        "Manmade shelter that provides cover from the weather. Blatantly artificial, it fails to blend into the environment even in the jungles of Sargon.",
        sourceRule("每次战斗中携带2个景观巨蕈（周围4格我方单位调查能力增强）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_FIELD_DEVICE_LUD_99X() {
    return collectible(
        "field_device_lud_99x",
        "战场装置LUD-99X",
        "每次战斗会出现战场装置LUD-99X（对阻挡最高的我方单位附近触发随机正面或负面调查效果）",
        "Field Device LUD-99X appears in all battles",
        "见过它的干员众说纷纭，有人说自己看到的是一张折凳，有人说是一根撬棍。可露希尔和绝大多数工程部干员则反复强调，这个编号下只有一个理论模型，不存在实物。罗德岛绝不会制造它。",
        "Every operator seems to have a different idea about what this is. Some saw a stool, others a crowbar. Meanwhile, Closure and most engineering operators insist that its serial number is assigned to a conceptual model that has never been produced. Nor will it ever be.",
        sourceRule("每次战斗会出现战场装置LUD-99X（对阻挡最高的我方单位附近触发随机正面或负面调查效果）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_TWO_STEP_FIRECRACKER_AND_DATA_COLLECTOR() {
    return collectible(
        "two_step_firecracker_and_data_collector",
        "“二踢脚”型数据收集装置",
        "每次战斗中携带5个“二踢脚”型数据收集装置（部署后立即调查敌人）",
        "Bring 5 Two-step Firecracker and Data-collectors to each battle",
        "一次性数据收集装置，启动后会发出巨大的爆炸声。某位炎国干员使用后在说明书上增加了一条附注：它只会响一次。",
        "One-time data collection device that emits a loud popping sound when activated. A Yanese operator has added the following note to the manual: 'It only pops once.'",
        sourceRule("每次战斗中携带5个“二踢脚”型数据收集装置（部署后立即调查敌人）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SELF_DRIVING_RECON_CART() {
    return collectible(
        "self_driving_recon_cart",
        "勘察用自走车",
        "每次战斗中携带1个勘察用自走车出发点（发出自走车调查敌人）",
        "Bring 1 Recon Self-Driver Launchpoint to each battle",
        "自走型数据采集装置，能够代替干员进入危险环境获取数据、拍摄录像。和杜林工匠的大多数设计一样，在投入实战测试之前会首先供干员们游玩很长一段时间。",
        "Autonomous device that can collect data and record video in areas too dangerous for operators to enter. Like most Durin designs, it spent a large portion of its development cycle as a toy for operators before field testing was conducted.",
        sourceRule("每次战斗中携带1个勘察用自走车出发点（发出自走车调查敌人）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_TREESCAR_HELM() {
    return collectible(
        "treescar_helm",
        "树痕之盔",
        "希望+3，让探索开启不同的方向",
        "+3 Hope; leads expedition in a different direction",
        "地位与权能的载物，但于战士而言，只是懦弱者自保的人工制品。有的人丢弃它是为了去离职责，有的人丢弃它，只是为了坚定自己舍弃生命的决心。",
        "It carries status and power, but to the warrior, it is only an artificial object for the weak to protect themselves. Some discard it to abandon their duty, some discard it only to strengthen their resolve to give up their lives.",
        partialRule("希望+3，让探索开启不同的方向", stats -> stats.hope(3)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BOUNDLESS_GIFT() {
    return collectible(
        "boundless_gift",
        "无垠赠礼",
        "坍缩值+3，让探索开启不同的方向",
        "+3 Collapse value; leads expedition in a different direction",
        "在眼见不到的地方扎根，从想象不及的去处吸收养分，花朵绽放跨越无垠，成为边界之外的第一枚信标。",
        "Takes root in regions unperceivable, absorbs nutrients in places unimaginable. The flowers bloom across the vastness, becoming the very first signal beacon outside the border.",
        partialRule("坍缩值+3，让探索开启不同的方向", stats -> stats.collapseValue(3)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ROUTEWEAVE_NET() {
    return collectible(
        "routeweave_net",
        "路网",
        "战斗出现额外敌人，让探索开启不同的方向",
        "Additional enemies appear in battle; leads expedition in a different direction",
        "一张错综复杂的网织成了无人理解的地图。看上去是在显现四通八达的道路，但它所展示的，只有结果。",
        "An intricate network is woven into a map that no one understands. It shows what seems to be a path that extends in all directions, but what it actually displays is only the result.",
        sourceRule("战斗出现额外敌人，让探索开启不同的方向"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BLINDNESS() {
    return collectible(
        "blindness",
        "“盲目”",
        "抗干扰指数为0-1时，水平或垂直移动随机获得护盾值、源石锭、希望、抗干扰指数或密文板",
        "When Anti-Interference Index is at 0 to 1, moving horizontally or vertically will randomly grant you Objective Shield, Originium Ingot, Hope, Anti-Interference Index, or Foldartal",
        "他们是否还保留着萨卡兹王庭的建制，是否还有王庭之主，如今无人能够确定。众人所能追溯的最后记忆，停留在某位独眼巨人摘下头顶黑色王冠的那一刻。",
        "These days, there is no one capable of determining if they still retain the structure of a Sarkaz Royal Court, or if they still have a King within. The final memory that everyone can recall is the moment when a certain Cyclopes took off the black crown atop their head.",
        sourceRule("抗干扰指数为0-1时，水平或垂直移动随机获得护盾值、源石锭、希望、抗干扰指数或密文板"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SCARRED_AMBER() {
    return collectible(
        "scarred_amber",
        "琥珀伤痕",
        "抗干扰指数可以超出上限，且每超出1点使所有我方单位的生命值+8%，攻击力+8%",
        "Uncaps Anti-Interference Index Limit, and every surplus index point will grant all allied units HP +8% and ATK +8%",
        "精灵们如此孤独地遗留在泰拉的大地上，怀抱尘土，背靠异乡。",
        "The elves have been left so alone on Terra that they embrace the dust with their backs to a foreign land.",
        sourceRule("抗干扰指数可以超出上限，且每超出1点使所有我方单位的生命值+8%，攻击力+8%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_AMMA_S_AFFECTION() {
    return collectible(
        "amma_s_affection",
        "安玛的爱",
        "所有我方单位的生命、攻击力、防御力+1%；有时会发挥奇妙的效果",
        "All allied units gain +1% HP, ATK, and DEF; occasionally something wonderful will happen",
        "祂确是爱你的，口袋里那份重量便是佐证；可祂的爱飘忽又无常，你永远不知道能从口袋中取出的，是祂护佑的证明，还是一捧融雪。",
        "It does love you, as is evident from the weight in your pocket, but Its love is fleeting and fickle, and you never know if what you retrieve from your pocket will be proof of Its protection or a handful of melted snow.",
        partialRule("所有我方单位的生命、攻击力、防御力+1%；有时会发挥奇妙的效果", stats -> stats.multiplyMaxHealth(0.01).multiplyAttack(0.01).multiplyDefense(0.01)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HORIZON_INVITATION() {
    return collectible(
        "horizon_invitation",
        "视界邀约",
        "让探索开启不同的方向",
        "Leads expedition in a different direction",
        "一份进入冰原尽头的邀请函，通过秘密渠道分发或售卖给有资格的人。然而，拿到邀请函只是第一步，考验即将到来——",
        "An invitation to the end of the icefield, distributed or sold through secret channels to those deserving. However, obtaining this invitation is merely the first step, and the test is yet to come—",
        sourceRule("让探索开启不同的方向"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_DIMENSIONAL_FLUID() {
    return collectible(
        "dimensional_fluid",
        "维度流质",
        "让探索开启不同的方向",
        "Leads expedition in a different direction",
        "融合各国技术，对坍缩体和冰原巨构进行深入研究后研制的核心原料。在通过考验后，那位罗德岛干员把它正式交付给了你，还给你留了一句叮嘱：“把它安全送去冰原尽头，凯尔希在那里等你。”",
        "A raw material developed by integrating technology from multiple nations and results from deep-dives into Collapsal research. After passing the test, the Rhodes Island operator officially hands it to you along with a message: 'Deliver it safely to the end of the icefield, where Kal'tsit waits for you.'",
        sourceRule("让探索开启不同的方向"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_COLLAPSAL_SEED() {
    return collectible(
        "collapsal_seed",
        "坍缩之种",
        "所有我方单位的攻击力和生命值-15%",
        "All friendly units have -15% ATK and -15% HP",
        "坍缩的痕迹落在此处，拒绝被清除。",
        "Traces of Collapse remain here, refusing to be removed.",
        sourceRule("所有我方单位的攻击力和生命值-15%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_FRAGMENT_OF_SPACE() {
    return collectible(
        "fragment_of_space",
        "空间碎片",
        "可携带干员-4",
        "-4 Squad Size Limit",
        "所处的空间被乱流撕碎，只剩下小小一方。",
        "The space here has been indiscriminately torn to shreds, leaving only a tiny piece.",
        registeredRule("可携带干员-4", stats -> stats.squadCapacity(-4)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_PROFOUND_SCORCHMARKS() {
    return collectible(
        "profound_scorchmarks",
        "深度灼痕",
        "目标生命上限-12",
        "-12 Max Life Point",
        "磅礴能量烧尽了躯体与灵魂，它们再也无法复原。",
        "The boundless energy burned away their body and soul, and they will never recover.",
        implementedRule("目标生命上限-12", stats -> stats.addMaxHealth(-12)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_UNYIELDING() {
    return collectible(
        "hand_of_unyielding",
        "坚实之手",
        "【工匠】、【行商】和【怪杰】在场上停留30秒后，获得3点临时目标生命值（每名干员同一作战最多触发1次）",
        "Artificers, Merchants, and Geeks will grant 3 temporary Life Points after being deployed on the field for 30 seconds (Each Operator can only trigger this once per battle)",
        "锦上添花，险地妙用。",
        "Nice to have, even nicer when in danger.",
        sourceRule("【工匠】、【行商】和【怪杰】在场上停留30秒后，获得3点临时目标生命值（每名干员同一作战最多触发1次）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_RETURN() {
    return collectible(
        "hand_of_return",
        "复还之手",
        "【群愈师】、【链愈师】和【行医】攻击范围内干员受到致命伤时，回复所有生命（每名干员同一作战最多触发1次）",
        "Multi-target Medics, Chain Medics, and Wandering Medics will heal any Operator within their range to full HP when that Operator takes critical damage (Each Operator can only trigger this once per battle)",
        "抑制死亡，唤醒生命。",
        "Restrain death, and rouse life.",
        sourceRule("【群愈师】、【链愈师】和【行医】攻击范围内干员受到致命伤时，回复所有生命（每名干员同一作战最多触发1次）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BLUNT_CLAWS_FUEL() {
    return collectible(
        "blunt_claws_fuel",
        "钝爪-拾柴",
        "场上每有一名先锋干员，每3秒获得1点部署费用（最多叠加8次）",
        "For every Vanguard Operator on the field, gain 1 DP every 3 seconds (stacks up to 8 times)",
        "每加入一根薪柴，火焰就更加旺盛。",
        "The flames turn more intense with each additional firewood added to the pile.",
        sourceRule("场上每有一名先锋干员，每3秒获得1点部署费用（最多叠加8次）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_BEND_SPEARS_BATTLECRY() {
    return collectible(
        "bend_spears_battlecry",
        "折戟-战吼",
        "场上每有一名近卫干员，所有我方单位的攻击力+8%（最多叠加8次）",
        "For every Guard Operator on the field, all allies gain ATK +8% (stacks up to 8 times)",
        "作战也是项集体活动，人越多，底气越足，挥动武器时也就更有力量。",
        "Combat is also a group activity. The more people there are, the more confident they get, and the more powerful they become with weapons in hand.",
        sourceRule("场上每有一名近卫干员，所有我方单位的攻击力+8%（最多叠加8次）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_IRON_GUARD_RAMPART() {
    return collectible(
        "iron_guard_rampart",
        "铁卫-垒沙",
        "场上每有一名重装干员，所有我方单位受到的物理伤害-10%（最多叠加8次）",
        "For every Defender Operator on the field, all allies take 10% less physical damage (stacks up to 8 times)",
        "聚沙成塔，而后俯瞰群敌。",
        "Gather sand to form a tower, and gaze upon enemies from above.",
        sourceRule("场上每有一名重装干员，所有我方单位受到的物理伤害-10%（最多叠加8次）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_FATAL_BOLTS_SUCCESSION() {
    return collectible(
        "fatal_bolts_succession",
        "残弩-连珠",
        "场上每有一名狙击干员，所有我方单位的攻击速度+12（最多叠加8次）",
        "For every Sniper Operator on the field, all allies gain ASPD +12 (stacks up to 8 times)",
        "听到第一支箭的声响，被最后一支箭穿过胸膛。",
        "Hear the sound of the first arrow, as the last pierces the torso.",
        sourceRule("场上每有一名狙击干员，所有我方单位的攻击速度+12（最多叠加8次）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_BROKEN_WAND_HARMONY() {
    return collectible(
        "broken_wand_harmony",
        "断杖-和声",
        "场上每有一名术师干员，所有敌人受到的法术伤害+12%（最多叠加8次）",
        "For every Caster Operator on the field, all allies deal +12% Arts damage (stacks up to 8 times)",
        "独奏虽有可取之处，合唱才能毁灭一切。",
        "The solos have their merits, but a chorus can destroy everything.",
        sourceRule("场上每有一名术师干员，所有敌人受到的法术伤害+12%（最多叠加8次）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_STALWART_AID_LINKUP() {
    return collectible(
        "stalwart_aid_linkup",
        "支柱-联手",
        "场上每有一名辅助干员，所有自然回复技能的技力恢复+0.2/秒（最多叠加8次）",
        "For every Supporter Operator on the field, increases the SP regen rate of Auto Recovery skills by +0.2/s (stacks up to 8 times)",
        "手拉手，肩并肩，做好准备，直视前方。",
        "Hand in hand, shoulder to shoulder, brace yourselves, and look straight ahead.",
        sourceRule("场上每有一名辅助干员，所有自然回复技能的技力恢复+0.2/秒（最多叠加8次）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_HEALER_S_PATH_WELLSPRING() {
    return collectible(
        "healer_s_path_wellspring",
        "医者-涌泉",
        "场上每有一名医疗干员，所有我方单位的再部署时间-5%（最多叠加8次）",
        "For every Medic Operator on the field, all allies gain Redeployment Time -5% (stacks up to 8 times)",
        "他们是流水聚成的清泉，他们疗愈众生。",
        "They are clear springs formed from flowing water, and they heal all that live.",
        sourceRule("场上每有一名医疗干员，所有我方单位的再部署时间-5%（最多叠加8次）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_RUSTED_BLADE_DISTURBANCE() {
    return collectible(
        "rusted_blade_disturbance",
        "锈刃-扰袭",
        "场上每有一名特种干员，所有敌方单位的移动速度-5%（最多叠加8次）",
        "For every Specialist Operator on the field, all enemies have -5% Movement Speed (stacks up to 8 times)",
        "就像切香肠一样，每次只耽误敌人一点，等到他们回过神来，早已无计可施。",
        "Delay the enemy bit by bit, just like slicing a sausage, and by the time they realize it there will be nothing they can do.",
        sourceRule("场上每有一名特种干员，所有敌方单位的移动速度-5%（最多叠加8次）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_PALMTOP_PAVILION() {
    return collectible(
        "palmtop_pavilion",
        "掌上楼阁",
        "所有干员的阻挡数+1，在场上停留60秒后阻挡数额外+1",
        "All Operators gain +1 Block, and an additional +1 Block after being deployed for more than 60 seconds",
        "土木天师的杰作，一手可握，但掷于地面便是天机阁下楼外楼。原是天师府不传之秘，如今在量产后广泛运用于冰原驻守与探险任务中。",
        "The Tumu Tianshi's masterpiece. Fits in one hand, but throw it to the ground and it turns into the ground floor of the Chamber of Heaven's Designs' outer pavilion. Once a secret of the Tianshi Bureaus, now mass-produced and widely used in garrisons and expeditions on the icefield.",
        sourceRule("所有干员的阻挡数+1，在场上停留60秒后阻挡数额外+1"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_MINOAN_ODE() {
    return collectible(
        "minoan_ode",
        "米诺斯颂诗",
        "每通过一场紧急作战，所有我方单位的生命值永久+3%（最多叠加15层）",
        "All Allies permanently gain HP +3% for every Emergency Ops cleared (stacks up to 15 times)",
        "“可有比远征无人之境更值得赞颂的壮举？如今便有一项，前往北方吧，去谱写英雄的篇章！”",
        "'Is there a feat more worthy of praise than an expedition into no man's land? Now, yes! Go forth into the north, and write the next chapter of your saga!'",
        sourceRule("每通过一场紧急作战，所有我方单位的生命值永久+3%（最多叠加15层）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FRISTON_P() {
    return collectible(
        "friston_p",
        "Friston.P",
        "每通过一场紧急作战，所有我方单位的攻击速度永久+5（最多叠加15层）",
        "All Allies permanently gain ASPD +5 for every Emergency Ops cleared (stacks up to 15 times)",
        "弗里斯顿当然不会错过探寻另一个前人类文明秘密的机会。哦，备份子思维这件事可露希尔并不知情，千万不要告诉她！",
        "Friston would certainly not miss out on an opportunity to explore the secrets of another predecessor civilization. Oh, but Closure didn't even consider making backups, so DON'T tell her about this!",
        sourceRule("每通过一场紧急作战，所有我方单位的攻击速度永久+5（最多叠加15层）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_VINECREEP_MORTAR_GUNNER() {
    return collectible(
        "vinecreep_mortar_gunner",
        "藤蔓炮手",
        "敌方空中单位被击杀后，对周围其他敌人造成3000点物理伤害",
        "Enemies defeated while airborne will deal 3000 physical damage to surrounding enemies",
        "当不同文化的人放下戒备真心交流，出现这样带有奇思妙想的武器也就不足为奇了。",
        "It's no surprise such a fantastical weapon would appear when people from different cultures let down their guard and communicate with sincerity.",
        sourceRule("敌方空中单位被击杀后，对周围其他敌人造成3000点物理伤害"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_NORTHWIND_CONSTRUCT() {
    return collectible(
        "northwind_construct",
        "北风的造物",
        "【机械】和【法术造物】敌人首次攻击时，立即冻结15秒",
        "[Machine] and [Arts Creations] enemies will be immediately Frozen with their first attack for 15 seconds",
        "即使常常以失败告终，她仍热衷于为挑战悲惨命运之人提供帮助。",
        "Though she often fails, she is still committed to helping those who would challenge their tragic fates.",
        sourceRule("【机械】和【法术造物】敌人首次攻击时，立即冻结15秒"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SHATTERED_ALLIANCE_TREATY() {
    return collectible(
        "shattered_alliance_treaty",
        "破落盟约",
        "所有干员受到来自【萨卡兹】和【宿主】敌人的伤害减少50%",
        "All Operators receive 50% less damage from Sarkaz and Possessed enemies",
        "在他人眼中萨卡兹究竟是什么？或许这件物品就能说明一切。",
        "What are the Sarkaz in the eyes of others? Perhaps this object can explain everything.",
        sourceRule("所有干员受到来自【萨卡兹】和【宿主】敌人的伤害减少50%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_PARADIGM_APPARATUS() {
    return collectible(
        "paradigm_apparatus",
        "范式中和仪",
        "处于【国度】中的敌方单位攻击速度-30",
        "All enemies have ASPD -30 when on tiles affected by Dominion",
        "乌萨斯当然不愿向他国透露任何与内卫相关的研究，但毕竟事关对邪魔源头的探索，在多国施压下，乌萨斯最终还是分享了部分秘密技术。",
        "Naturally, Ursus was unwilling to divulge their research into the Royal Guard to other countries. But ultimately, they caved to international pressure and shared some of their technology—after all, this was all to find the source of the demons.",
        sourceRule("处于【国度】中的敌方单位攻击速度-30"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_EXPEDITIONER_S_FIELD_PACK() {
    return collectible(
        "expeditioner_s_field_pack",
        "探索者背包",
        "所有干员从异常状态恢复后，攻击力+60%并获得抵抗，持续15秒",
        "All Operators gain ATK +60% and Resist for 15 seconds after recovering from a negative status",
        "他们总是会把一切利于生存的物资塞进背包里，然后带上那一往无前的决心与意志，挑战认知的边界。",
        "They always stuff their backpacks with survival supplies, then press forward with the determination and will to challenge the boundaries of the known.",
        sourceRule("所有干员从异常状态恢复后，攻击力+60%并获得抵抗，持续15秒"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BLACK_HOLE_PROTOCOL() {
    return collectible(
        "black_hole_protocol",
        "黑洞协议",
        "部署费用达到上限时，所有干员的生命值+150%",
        "When DP is maxed out, all Operators gain Max HP +150%",
        "这是一场史无前例的联合行动。泰拉诸国都在极北的巨构前投入了大量资源，为了保证成果不被隐瞒或滥用，非政府组织罗德岛成了该行动的实际管理者。",
        "This collaborative operation is unprecedented in the history books. The nations of Terra have invested a great deal of resources into the megastructure in the far north, and the NGO Rhodes Island has become the actual managing party in order to prevent the results from being concealed or tampered with.",
        sourceRule("部署费用达到上限时，所有干员的生命值+150%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_HIBERNATING_KIN() {
    return collectible(
        "hibernating_kin",
        "沉眠同胞",
        "希望+5，此收藏品在失与得交换时额外获得1个收藏品",
        "Hope +5, obtain an extra Collectible when this Collectible is exchanged in Lost and Found",
        "迷失于银色山脉的萨卡兹以休眠自保。它沉睡了太久，以至于自我都消失殆尽，直到那命中注定的同胞将其唤醒，它将成为另一个他。",
        "Lost in the silver mountain range, the Sarkaz hibernated for self-preservation. It slept so long it nearly lost itself, until its fated kin awoke it. Now, it will follow his path.",
        partialRule("希望+5，此收藏品在失与得交换时额外获得1个收藏品", stats -> stats.hope(5)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BAG_OF_PLANS() {
    return collectible(
        "bag_of_plans",
        "一袋构想",
        "立即获得3缕构想",
        "Immediately obtain 3 Plans",
        "像“这里要盖一座桥”“那里要种上花”“该把这墙炸开”这类想法，就适合装进袋子里，这样当需要的时候就可以拿出来用。",
        "Ideas like 'build a bridge here', 'grow flowers there', or 'blow this wall open' are perfect for sitting inside bags so they can be easily retrieved when needed.",
        sourceRule("立即获得3缕构想"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_GIFT_OF_REMEMBRANCE() {
    return collectible(
        "gift_of_remembrance",
        "铭记的贺礼",
        "负荷临界点+5",
        "Toil Limit +5",
        "这是河谷中永远闪烁着波光的水，由菈玛莲·杜康珐丽丝在奇迹降临后的日子里亲手盛装。再之后，“编织泪水的河谷主母”喜笑颜开地把这些小瓶送给了许多亲朋姐妹。",
        "Water from the Convallis that never loses its shimmer, personally bottled by Laqeramaline Duqa Convalliss days after the miracle. The 'tear-weaving Convallis Hostess' happily gifted these vials to her dear ones.",
        registeredRule("负荷临界点+5", stats -> stats.mentalBurdenLimit(5)),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_CURSED_WAR_CHRONICLES() {
    return collectible(
        "cursed_war_chronicles",
        "遭诅战史集",
        "负荷临界点+2，每次完美作战后，负荷临界点+1",
        "Toil Limit +2; Toil Limit +1 after each perfectly cleared battle",
        "这本战史集被放在图书馆最深处，每个试图去阅读的人都会被卷入它所记载的无数战役，在徒劳的抵抗中精神崩溃。但死魂灵长者的手洞穿战场，直接将这位附身书中的同胞封入了自己的骨骸中。",
        "Hidden in the deepest corner of the library, this war chronicle ensnares hapless readers with its countless battles and campaigns, unraveling their minds as they resist. Eventually, the elder revenant tore out the rogue revenant in the book and sealed it in his bones.",
        partialRule("负荷临界点+2，每次完美作战后，负荷临界点+1", stats -> stats.mentalBurdenLimit(2)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_LOST_KEY() {
    return collectible(
        "lost_key",
        "失落之钥",
        "战斗获得的指挥经验+20%，指挥等级到达10级时，立刻获得50源石锭",
        "Gain +20% Command EXP from battles; obtain 50 Originium Ingots upon reaching Command Level 10",
        "“伙计们，这把钥匙能够打开失落王都卡兹戴尔的宝库。喂，绿发的菲林，看好你的兜帽奴隶，别让他碰我的地图！”",
        "'Fellas, this key can open the treasure trove lost within the royal city of Kazdel. Hey, green-haired Feline! Watch that hooded slave of yours and keep those hands away from my map!'",
        sourceRule("战斗获得的指挥经验+20%，指挥等级到达10级时，立刻获得50源石锭"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_NAMELESS_TOTEM() {
    return collectible(
        "nameless_totem",
        "无名图腾",
        "立刻获得3缕不同种类的思绪",
        "Obtain 3 different Thoughts",
        "这图腾竖立在路口，走近的人会用双脚在大地上一遍遍踩出死魂灵模糊的现实轮廓，直至鞋底磨穿，双脚见血，力竭而亡。死魂灵长者踩碎了同胞的附身物，将其封入了自己的骨骸中。",
        "This totem once stood at a crossroads, where those who approached would trample the vague outline of a revenant until their feet bled and they died from exhaustion. Eventually, the elder revenant shattered the token with a stomp and sealed the rogue revenant in his bones.",
        sourceRule("立刻获得3缕不同种类的思绪"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_REVENANT_REMNANT() {
    return collectible(
        "revenant_remnant",
        "死魂灵残躯",
        "希望+2，每个节点可刷新次数+1",
        "Hope +2, refresh count at each Node +1",
        "失去身体，成为彷徨的幽魂。这就是部分萨卡兹为了族群的未来自愿付出的代价。",
        "To be a wandering ghost without a body. This is the price some Sarkaz willingly paid to ensure the future of their race.",
        partialRule("希望+2，每个节点可刷新次数+1", stats -> stats.hope(2)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_MAGIC_BOX() {
    return collectible(
        "magic_box",
        "魔术匣",
        "源石锭+4，负荷临界点+2",
        "Originium Ingots +4 and Toil Limit +2",
        "这个外表平平无奇的匣子肆意摄取着人们的心魄，见到它的人会迫不及待杀死拥有者将之据为己有。人类死绝后，动物们成了争夺的主角。最终，死魂灵长者从一只巨型岩角兽的胃中取出同胞，封入了自己的骨骸中。",
        "This ordinary looking box devours souls, compelling those who see it to kill its owner and claim it. When all the humans had killed each other, animals became the new victims. Eventually, the elder revenant retrieved the box from a giant rockhorn's stomach and sealed the rogue revenant in his bones.",
        registeredRule("源石锭+4，负荷临界点+2", statSet(stats -> stats.originiumIngots(4), stats -> stats.mentalBurdenLimit(2))),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_HALF_EATEN_CANDY() {
    return collectible(
        "half_eaten_candy",
        "没吃完的糖果",
        "可携带干员+1，负荷临界点+2",
        "Squad Size Limit +1, Toil Limit +2",
        "“每吃下一颗糖，我就觉得自己身体的一部分不属于自己了。可我还想吃，还想吃......直到那个黑色的阴影出现，一把扯走了我身体里的怪物。但我精神里空缺的那部分，再也没能填上。”",
        "'With every piece of candy I eat, I feel like I'm losing a part of my body. But I still want to eat more, and more... until that black shadow showed up and ripped away the monster inside me. But that hollow in my mind will never be filled.'",
        registeredRule("可携带干员+1，负荷临界点+2", statSet(stats -> stats.squadCapacity(1), stats -> stats.mentalBurdenLimit(2))),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_DAWN_OF_LITERATURE() {
    return collectible(
        "dawn_of_literature",
        "文学的开端",
        "对【萨卡兹】敌人造成的物理与法术伤害提升50%",
        "Deals 50% more physical and Arts damage to [Sarkaz] enemies",
        "“阿卡姆·那坤·萨卡姆，那就是‘阿喃那’，是唯一的诅咒，是你未曾听见，便将要杀死你的语言。现在落泪吧！”",
        "'Aqqam Naqqun Sarqqam. That is 'Amnannam', the one and only curse, the uttering that kills even those who have yet to hear it. Now weep!'",
        sourceRule("对【萨卡兹】敌人造成的物理与法术伤害提升50%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_GRAVITY_DEFYING_MACHINE() {
    return collectible(
        "gravity_defying_machine",
        "重力蔑视机关",
        "所有敌人重量-2",
        "All enemies have -2 Weight",
        "娜斯提·鲁诺瑞伊的杰作将萨卡兹托离了残害他们千万年的大地，此后，萨卡兹虽缥缈于云端，却将自己的根系，牢牢扎在了天空之上。",
        "Nasti Lunorey's magnum opus lifted the Sarkaz from the land that had tormented them for thousands of years. Now, though they float in ethereal clouds, they remain firmly rooted in the sky.",
        implementedRule("所有敌人重量-2", stats -> stats.addEnemyWeightIgnore(2)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HALO() {
    return collectible(
        "halo",
        "光环",
        "【萨卡兹】敌人的移动速度-30%",
        "All [Sarkaz] enemies have -30% Movement Speed",
        "“当我进入那扇门时，我的兄长已经现出圆环长出光翼。他转过身来，向我问了好。传来的语言像是一把钥匙，解开了我心头的苦难，于是，当我高声回话时，圆环完成了链接，我也由此启明。”",
        "'When I entered that door, my elder brother had already sprouted a halo and wings of light. As he turned around and greeted me, his words were like a key, freeing me from the suffering in my heart. And then, I responded aloud, the halo completely connected, and I received my revelation.'",
        sourceRule("【萨卡兹】敌人的移动速度-30%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_BLUNT_CLAWS_PREDATION() {
    return collectible(
        "blunt_claws_predation",
        "钝爪-掠食",
        "场上每有一名先锋干员，每3秒获得1点部署费用（最多叠加8层）",
        "For every Vanguard Operator on the field, gain 1 DP every 3 seconds (Stacks up to 8 times)",
        "兽群饥肠辘辘，等待饱餐一顿。",
        "The pack rumbles with hunger, waiting for a feast.",
        sourceRule("场上每有一名先锋干员，每3秒获得1点部署费用（最多叠加8层）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BEND_SPEARS_RENDING() {
    return collectible(
        "bend_spears_rending",
        "折戟-裂岩",
        "场上每有一名近卫干员，所有我方单位的攻击力+8%（最多叠加8层）",
        "For every Guard Operator on the field, all allied units gain ATK +8% (Stacks up to 8 times)",
        "拥有力量的人总能互相成就。",
        "Those with power can always help each other to success.",
        sourceRule("场上每有一名近卫干员，所有我方单位的攻击力+8%（最多叠加8层）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BROKEN_WAND_UNDULATION() {
    return collectible(
        "broken_wand_undulation",
        "断杖-波纹",
        "场上每有一名术师干员，所有敌人受到的法术伤害+12%（最多叠加8层）",
        "For every Caster Operator on the field, all allied units deal +12% Arts damage (Stacks up to 8 times)",
        "调谐到同一频率，让我们奏起旋律。",
        "Tuned to the same frequency; together we'll play the melody.",
        sourceRule("场上每有一名术师干员，所有敌人受到的法术伤害+12%（最多叠加8层）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_RUSTED_BLADE_PROTRACTION() {
    return collectible(
        "rusted_blade_protraction",
        "锈刃-久居",
        "场上每有一名特种干员，所有敌方单位的移动速度-5%（最多叠加8层）",
        "For every Specialist Operator on the field, all enemies have -5% Movement Speed (Stacks up to 8 times)",
        "主人不想见客，客人便到不了门前。",
        "If the host does not want guests, they'll never reach the door.",
        sourceRule("场上每有一名特种干员，所有敌方单位的移动速度-5%（最多叠加8层）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_RUSTED_BLADE_LONE_FORCE() {
    return collectible(
        "rusted_blade_lone_force",
        "锈刃-遗世独立",
        "【特种】干员周围4格没有其他友方角色时，攻击力+70%",
        "When there are no allies in the surrounding 4 tiles of a Specialist operator, they gain +70% ATK",
        "孤独。",
        "Solitude.",
        sourceRule("【特种】干员周围4格没有其他友方角色时，攻击力+70%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_PREDATION() {
    return collectible(
        "hand_of_predation",
        "掠食之手",
        "【尖兵】【冲锋手】【战术家】每次开启技能时自身攻击力、防御力+10%（最多叠加10层），拥有钝爪-掠食时，技力恢复+0.5/秒",
        "Pioneer, Charger, and Tactician Operators gain +10% ATK and DEF on every skill activation (Stacks up to 10 times); If you have Blunt Claws - Predation, also increases their SP regen rate by +0.5/s",
        "跃入兽群，撕裂皮囊。",
        "Herds stir, flesh rend asunder.",
        sourceRule("【尖兵】【冲锋手】【战术家】每次开启技能时自身攻击力、防御力+10%（最多叠加10层），拥有钝爪-掠食时，技力恢复+0.5/秒"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_RENDING() {
    return collectible(
        "hand_of_rending",
        "裂岩之手",
        "【收割者】【武者】【斗士】攻击无视目标70%防御，拥有折戟-裂岩时，每秒恢复自身2.5%的生命值",
        "Reaper, Soloblade, and Fighter Operators ignore 70% of the target's DEF; If you have Bend Spears - Rending, they also recover 2.5% of Max HP per second",
        "开山碎地，噬土吞石。",
        "Mountains shatter, stones devoured.",
        sourceRule("【收割者】【武者】【斗士】攻击无视目标70%防御，拥有折戟-裂岩时，每秒恢复自身2.5%的生命值"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_UNDULATION() {
    return collectible(
        "hand_of_undulation",
        "波纹之手",
        "【扩散术师】【链术师】【驭械术师】每对一个单位造成伤害就回复2点技力值，拥有断杖-波纹时，攻击速度+20",
        "Splash Caster, Chain Caster, and Mech-accord Caster Operators recover 2 SP each time they deal damage to an enemy unit; If you have Broken Wand - Undulation, they also gain +20 ASPD",
        "一扫而过，震耳欲聋。",
        "Blasts conquer, the wake deafening.",
        sourceRule("【扩散术师】【链术师】【驭械术师】每对一个单位造成伤害就回复2点技力值，拥有断杖-波纹时，攻击速度+20"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_PROTRACTION() {
    return collectible(
        "hand_of_protraction",
        "久居之手",
        "【伏击客】【行商】【怪杰】每在场上存在30秒，攻击力+25%(最多叠加5层)，拥有锈刃-久居时，最多可以叠加8层",
        "Ambusher, Merchant, and Geek Operators gain +25% ATK for every 30 seconds they remain on the battlefield (Stacks up to 5 times); If you have Rusted Blade - Protraction, maximum stack increases to 8",
        "战场如家，久居常住。",
        "Protracted battles, a home of carnage.",
        sourceRule("【伏击客】【行商】【怪杰】每在场上存在30秒，攻击力+25%(最多叠加5层)，拥有锈刃-久居时，最多可以叠加8层"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_MYSTERY() {
    return collectible(
        "hand_of_mystery",
        "奥秘之手",
        "【中坚术师】【本源术师】【巫役】每使用过一次技能，自身的技力自然回复速度+0.5/秒，最多叠加4层",
        "Core Caster, Primal Caster, and Ritualist Operators gain +0.5/s SP recovery rate whenever they use a skill (Stacks up to 4 times)",
        "寻寻觅觅，接近真相。",
        "Mists part, the truth approaches.",
        sourceRule("【中坚术师】【本源术师】【巫役】每使用过一次技能，自身的技力自然回复速度+0.5/秒，最多叠加4层"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_RUMBLE() {
    return collectible(
        "hand_of_rumble",
        "轰鸣之手",
        "【炮手】【阵法术师】【投掷手】每对一个单位造成伤害就使自身攻击力+15%，最高+150%，5秒未造成伤害则使加成清空",
        "Artilleryman, Phalanx Caster, and Flinger Operators gain +15% ATK for every time they deal damage to the same unit, up to 150%; this bonus will reset if no damage is dealt for 5 seconds",
        "命中标的，撼动大地。",
        "Strikes land true, the earth trembles.",
        sourceRule("【炮手】【阵法术师】【投掷手】每对一个单位造成伤害就使自身攻击力+15%，最高+150%，5秒未造成伤害则使加成清空"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BRIDGE_OF_KNOWLEDGE() {
    return collectible(
        "bridge_of_knowledge",
        "“知识之桥”",
        "每使用一缕灵感，所有友方干员的最大生命值+5%（最多叠加10层）",
        "Every Inspirer used grants all allied units +5% HP (Stacks up to 10 times)",
        "据说只要把它握住，它就会通过振动提示巫妖知识圣殿所在的方向。真的能这样到达知识圣殿的大门吗？它缄口不言。而未曾如此质疑的人，确实从未到达过那扇门。",
        "It is said you need only hold it, and its vibrations will lead you to the Liches' Temple of Knowledge. Is that really true? It stays silent. Those who fail to question it will indeed never reach those doors.",
        sourceRule("每使用一缕灵感，所有友方干员的最大生命值+5%（最多叠加10层）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_MERCENARY_S_ACCESSORY() {
    return collectible(
        "mercenary_s_accessory",
        "佣兵的饰物",
        "使随机一名干员的攻击力、防御力、最大生命值+5%，且每次完美作战结束后额外+5%（最多叠加10层）",
        "Grants a random Operator +5% ATK, DEF, and Max HP, plus an additional +5% after each perfectly cleared battle (Stacks up to 10 times)",
        "那是他为了追求尽善尽美所付出的一点微小代价。说微小，是因为他的阅读和写作都未受多少影响。",
        "A small price to pay for pursuing perfection. Small because it hasn't really affected his reading and writing.",
        sourceRule("使随机一名干员的攻击力、防御力、最大生命值+5%，且每次完美作战结束后额外+5%（最多叠加10层）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_CHILDREN_OF_THE_WALLS() {
    return collectible(
        "children_of_the_walls",
        "城墙之子",
        "部署在蓝色目标点周围8格的干员阻挡数+2，最大生命值+50%",
        "Operators deployed on the 8 tiles surrounding the Objective Point gain +2 Block and +50% Max HP",
        "土石之子精心培育了卡兹戴尔的古老城垣，教会了它如何使自己变得刚韧，以保护身后的人民。随着长久和平的时代到来，它被拆分成了许多小块，作为护身符赠给了前往未知之地的探险家们。",
        "The Children of Soil and Stone carefully taught the ancient city of Kazdel how to toughen itself and protect its people behind its walls. With the advent of a protracted peace, it was broken into many little pieces and turned into amulets for adventurers heading into unexplored lands.",
        sourceRule("部署在蓝色目标点周围8格的干员阻挡数+2，最大生命值+50%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_KING_S_ARMOR() {
    return collectible(
        "king_s_armor",
        "国王的铠甲",
        "护盾值+3，将目标生命全部转化为等量护盾值且战斗结束后使目标生命-1，护盾值+1",
        "Shield +3; converts all Life Points into Shield; Life Point -1 and Shield +1 after each battle",
        "王宫的大门前、广场上，曾摆放着那么多金色的展柜，四方珍品尽供欣赏。而现在，仅仅是回想那个场面就会让人受伤。",
        "There used to be many golden display cases placed before the palace gates and in the square, displaying all sorts of treasures to admire. But now, it hurts just to think about such sights.",
        partialRule("护盾值+3，将目标生命全部转化为等量护盾值且战斗结束后使目标生命-1，护盾值+1", stats -> stats.addMaxHealth(3)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_KING_S_LEGACY() {
    return collectible(
        "king_s_legacy",
        "国王的延伸",
        "目标生命为1时，所有干员每2秒额外回复1点技力和3%的生命值",
        "When Life Point is at 1, all Operators recover an additional 1 SP and 3% of Max HP every 2 seconds",
        "成长，认清现实，用坚韧与笃定武装自己，用包容与慈恩武装朋友。",
        "Grow up and face reality. Arm yourself with tenacity and confidence, while showing your friends tolerance and grace.",
        sourceRule("目标生命为1时，所有干员每2秒额外回复1点技力和3%的生命值"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_PERSONAL_WITCHCRAFT_TERMINAL() {
    return collectible(
        "personal_witchcraft_terminal",
        "私人巫术终端",
        "进入战斗时，如果使用了灵感，所有干员攻击速度+35",
        "All Operators have +35 ASPD if you used an Inspirer as the battle started",
        "“我们沿用了‘提卡兹巫术’这个名字，以尊崇祖先在蒙昧时期的发现。但时至今日，它已是一门学科，渗入泰拉的方方面面，而我们对它的开发与探索，还远远没有到达尽头。”",
        "'We use the name 'Teekaz witchcraft' to honor what our ancestors discovered during the uncivilized parts of our history. But today it has become a discipline permeating all of Terra, and our explorations and developments in this field are far from over.'",
        sourceRule("进入战斗时，如果使用了灵感，所有干员攻击速度+35"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_PRIMORDIAL_VESTIGE() {
    return collectible(
        "primordial_vestige",
        "原始痕迹",
        "进入战斗时，如果未使用灵感，所有干员费用-6",
        "All Operators DP Cost -6 if you did not use an Inspirer as the battle started",
        "此类动物最为显著的变化是感染矿石病后产生的变异。除此以外，关于它们的记录在千百年间从未有过变化。",
        "The most significant change in these animals is the mutation they undergo after contracting Oripathy. Otherwise, their records have remained unchanged for countless years.",
        sourceRule("进入战斗时，如果未使用灵感，所有干员费用-6"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_DISASTER_S_ORIGIN() {
    return collectible(
        "disaster_s_origin",
        "灾难之源",
        "所有敌方单位受到的法术伤害+20%，受到的元素损伤+100%",
        "All enemies take +20% Arts damage and +100% Elemental Injury",
        "萨卡兹掘断了塔尔干主矿脉，源石结晶从卡兹戴尔喷涌而出。萨卡兹的败亡早已注定，于是他们要大地上的一切成为陪葬。",
        "The Sarkaz dug through the Targangils Prime Vein and Originium crystals spurted forth from Kazdel. Their fall was inevitable, so they wanted everything on this great land to be buried together with them.",
        partialRule("所有敌方单位受到的法术伤害+20%，受到的元素损伤+100%", stats -> stats.addEnemyMagicDamageTakenBonus(0.2)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SLAVE_HUNTER() {
    return collectible(
        "slave_hunter",
        "奴隶猎捕器",
        "进入浮空、失衡与失重状态的单位受到的伤害提升50%，持续10秒",
        "Units that become Levitated, Weightless, or are shifted will take 50% more damage for 10 seconds",
        "获取娜斯提的设计后，萨卡兹立刻将其运用到了战争之中，他们飞上天空，以绝对优势向着先民倾泻怒火。一场永不停歇的战争就此进入了新的阶段。",
        "After obtaining Nasti's designs, the Sarkaz put them to use in war right away. They flew into the sky, dominating the Ancients with waves of wrath, and the never-ending war entered a new stage.",
        sourceRule("进入浮空、失衡与失重状态的单位受到的伤害提升50%，持续10秒"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SARKAZ_KING_S_REGAL_REST() {
    return collectible(
        "sarkaz_king_s_regal_rest",
        "魔王的床榻",
        "目标生命值达到上限时，所有干员的防御力+20%，法术抗性+10",
        "All Operators gain +20% DEF and +10 RES at full Life Points",
        "身居此位者，安睡即为永眠。",
        "For those in this position, a peaceful sleep becomes eternal",
        sourceRule("目标生命值达到上限时，所有干员的防御力+20%，法术抗性+10"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SARKAZ_KING_S_TORN_BANNER() {
    return collectible(
        "sarkaz_king_s_torn_banner",
        "魔王的旗帜",
        "目标生命值达到上限时，所有干员的攻击速度+30",
        "All Operators gain +30 ASPD at full Life Points",
        "身居此位者，抗争便会陨落。",
        "For those in this position, to struggle is to inevitably fall.",
        sourceRule("目标生命值达到上限时，所有干员的攻击速度+30"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_COMMANDER_S_PORTRAIT() {
    return collectible(
        "commander_s_portrait",
        "统帅肖像",
        "所有我方单位的攻击力、最大生命值+10%，在险路恶敌中，攻击力、最大生命值额外+20%",
        "All allied units gain +10% ATK and Max HP, and an additional +20% in Dreadful Foe nodes",
        "画像中描绘了一位杰出之人，她让互相猜疑的君王握手言和，让语言不通的士兵并肩站立......如果她还活着，泰拉一统似乎也并非天方夜谭。",
        "The portrait depicts a remarkable individual who made distrustful kings shake hands and soldiers who could not understand each other stand shoulder to shoulder. If she were alive today, Terra's unification might seem surprisingly plausible.",
        partialRule("所有我方单位的攻击力、最大生命值+10%，在险路恶敌中，攻击力、最大生命值额外+20%", stats -> stats.multiplyAttack(0.1).multiplyMaxHealth(0.1)),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SOUL_BINDING_BONE() {
    return collectible(
        "soul_binding_bone",
        "束灵骨",
        "每携带一缕思绪，所有干员攻击力+3%",
        "For every Thought you have, all Operators gain +3% ATK",
        "死魂灵长者用自己的骨骸做成了封印同族的囚笼，之后，他背负着自己曾经的身躯，在大地上搜捕散落在各处的死魂灵同胞。",
        "The elder revenant made a cage with his own bones to seal his kin within, and then tracked down his revenant kin scattered across the land in this tattered husk.",
        sourceRule("每携带一缕思绪，所有干员攻击力+3%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SOUL_FURNACE_S_FUEL() {
    return collectible(
        "soul_furnace_s_fuel",
        "生命熔炉之薪",
        "每携带一缕思绪，所有干员攻击力+5%",
        "For every Thought you have, all Operators gain +5% ATK",
        "骨骸被掷入熔炉，火光瞬间从炉中迸发，死魂灵们纠缠在一起，将不甘、愤怒、平静、羞愧，尽数化作驱动卡兹戴尔的动力。",
        "The bones were thrown into the furnace, sending flames bursting forth. The revenants entangle into each other, transforming all their lament, fury, serenity, and shame into the energy that keeps Kazdel moving.",
        sourceRule("每携带一缕思绪，所有干员攻击力+5%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_MASTERLESS_MEMORIES() {
    return collectible(
        "masterless_memories",
        "无主的回忆",
        "立即获得上次探索遗留的最多5缕思绪",
        "Immediately obtain 5 Thoughts at most left behind from your last adventure",
        "在被长者投入大熔炉的那一刻，一些死魂灵终于想起了自己的出身和责任，用誓言重新树立了信念；另一些则发出恍然而悟的咒骂，令火焰在炉壁上的回声更有力了几分。",
        "As the elder tossed them into the furnace, some revenants finally remembered where they came from and their duties, and remade their oaths to their faith. Others unleashed a sudden slew of curses, magnifying the echoes of flames reverberating on the furnace walls.",
        sourceRule("立即获得上次探索遗留的最多5缕思绪"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HERR_HRAASELSUHER() {
    return collectible(
        "herr_hraaselsuher",
        "探灵伯爵",
        "负荷临界点+2，战斗后有概率掉落1缕构想",
        "Toil Limit +2 and chance to gain 1 Plan after combat",
        "阿尔布雷希特·冯·蹊兽，蔷花圆号之塔的伯爵，愿为您效劳，承诺给您带回失落的财宝、隐藏的智慧！他只偶尔要求分得其中的一部分作为报酬，称这是为了他的妻子和儿女。",
        "Albrecht Von Musbeast, Graf of the Spire of Rosen und Horns, is at your service, pledging to bring you lost treasures and hidden wisdom! He asks only for an occasional share of the haul, saying they're for his wife and daughter.",
        partialRule("负荷临界点+2，战斗后有概率掉落1缕构想", stats -> stats.mentalBurdenLimit(2)),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SONG_TO_MAKE_A_TENACIOUS_MIND() {
    return collectible(
        "song_to_make_a_tenacious_mind",
        "《神魂坚韧之歌》",
        "每使用一缕灵感，护盾+1",
        "Every Inspirer used grants +1 Shield",
        "歌里唱到，笞心魔天生能感应他人的恐惧，她索性把所感都诚实地讲了出来，别人却仍不肯放下那百般的戒备；后来，她练就了一身不用这个天赋克敌的好本领，却还是被冠上了“恶主”的名衔。",
        "The song tells of a Djall born with the ability to sense fear. She was candid with what she sensed, but everyone was still wary. She later honed her skills to defeat foes without this power, yet remained known as the 'Arch-liar'.",
        sourceRule("每使用一缕灵感，护盾+1"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_ANCHOR_FOR_FIVE_SECONDS_AGO() {
    return collectible(
        "anchor_for_five_seconds_ago",
        "为五秒前准备的锚",
        "进入战斗时，如果使用了灵感，战斗后会额外获得1缕灵感",
        "Gain 1 extra Inspirer after the battle if you used an Inspirer as the battle started",
        "年轻的巫妖时不时会为记不起五秒前乍现的灵感而感到烦恼，他于是制作了这个锚，好让自己以后可以把那些稍纵即逝的想法固定住。后来，他似乎没有再记起要使用这东西。",
        "A young Lich was fretting over occasionally forgetting an idea he thought of five seconds ago, so he created this anchor to set those fleeting inspirations in place. Afterwards, he seemed to have forgotten to use it.",
        sourceRule("进入战斗时，如果使用了灵感，战斗后会额外获得1缕灵感"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_PERSONAL_PAINTBRUSH() {
    return collectible(
        "personal_paintbrush",
        "随身画笔",
        "构想的负荷-1",
        "Plans have -1 Toil",
        "画家拿上这支笔，蘸着自己画下一幅杰作，让见到画的人心甘情愿再来添上一笔。死魂灵长者走过满是画像的长廊，用它画下封印的符咒，随后将同胞封入了自己的骨骸中。",
        "A haunted brush that compels its owners to paint with their own blood. The masterpiece mesmerizes beholders into adding another stroke the same way. Eventually, the elder revenant found the brush, along with many paintings, and sealed the rogue revenant in his bones.",
        sourceRule("构想的负荷-1"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SCALES_OF_AVARICE() {
    return collectible(
        "scales_of_avarice",
        "贪婪天平",
        "当前所有思绪的负荷大于20时，每进入一个非战斗节点，源石锭+3",
        "Gain +3 Originium Ingots for each non-combat node entered when Total Mental Toil exceeds 20",
        "商人们将金钱，将珍宝，甚至将自己的性命与灵魂奉上，都无法平衡天平，拿到那轻如鸿毛的光点。死魂灵长者用自己的灵魂压碎了天平，将同胞封入了自己的骨骸中。",
        "Merchants offered money, treasure, and even their lives and souls, but couldn't balance the scales to obtain the feather-light glow. The elder revenant crushed the scales with his soul and sealed the rogue revenant in his bones.",
        sourceRule("当前所有思绪的负荷大于20时，每进入一个非战斗节点，源石锭+3"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_WORD_CARVING_KNIFE() {
    return collectible(
        "word_carving_knife",
        "雕词錾刀",
        "地图中的与的防御力下降50%",
        "<Spines of Epoch> and <Spikes of Weeping> have -50% DEF",
        "曾经是萨卡兹石匠惯用的工具。在恸哭之刺上錾刻祷词，据说可以平息死魂灵的怨怒。錾刀尚算锋利，祷词却已无人知晓。",
        "Once the standard tool of Sarkaz stonemasons. It is said carving prayers on weeping spikes can calm the revenants' resentment. The knife is still sharp, but no one remembers the prayers.",
        sourceRule("地图中的与的防御力下降50%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_THOUGHTS_CATCHER() {
    return collectible(
        "thoughts_catcher",
        "思维捕手",
        "每次解读后源石锭+1",
        "Originium Ingot +1 after each interpretation",
        "去伪存真是个漫长的过程，总有一些想法会逸散出去。这时，这些经过专业训练的仆从就会立刻出击，将这些想法化作实在的财富。",
        "Sorting truth from falsehood takes time, and some ideas will invariably scatter. In such moments, professionally trained retainers will snatch those ideas up and turn them into true wealth.",
        sourceRule("每次解读后源石锭+1"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_DOODLE_OF_HOPE() {
    return collectible(
        "doodle_of_hope",
        "希望时代的涂鸦",
        "希望+3，刷新节点时不再会出现紧急作战，在不期而遇中会发挥奇妙的效果",
        "Hope +3; Emergency Ops will no longer appear when refreshing nodes; triggers wondrous effects in Encounter nodes",
        "“我是个提卡兹孤儿，爸爸妈妈死于天灾，是隔壁的菲林叔叔一家收养了我，把我当作家里的一分子。我可以问心无愧地说，我是先民的后代。”",
        "'I'm a Teekaz orphan. My parents died in a Catastrophe, and it was my Feline neighbor who raised me and treated me as one of his own. I can say with a clear conscience that I am a descendant of the Ancients.'",
        partialRule("希望+3，刷新节点时不再会出现紧急作战，在不期而遇中会发挥奇妙的效果", stats -> stats.hope(3)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_TALONS_OF_HATRED() {
    return collectible(
        "talons_of_hatred",
        "死仇时代的恨意",
        "所有友方单位和敌人攻击力+20%，刷新节点时更容易出现紧急作战，在不期而遇中会发挥奇妙的效果",
        "All allied units and enemies have +20% ATK; Emergency Ops will appear more often when refreshing nodes; triggers wondrous effects in Encounter nodes",
        "“我的妹妹被血魔咬死，爸爸被食腐者带走，妈妈带着我逃了出来。我绝不会向萨卡兹妥协，就算要忏悔，也得等到它们彻底消失再说。”",
        "'A vampire killed my sister, a Nachzehrer took my papa, and mama escaped with me. I'll never compromise with the Sarkaz. They can repent when they're all gone.'",
        sourceRule("所有友方单位和敌人攻击力+20%，刷新节点时更容易出现紧急作战，在不期而遇中会发挥奇妙的效果"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BURDENHERD_BELL() {
    return collectible(
        "burdenherd_bell",
        "牧驮人的摇铃",
        "驮兽旅行家加入队伍，在得偿所愿节点掉落战利品时，增加一个可选项",
        "Burdenbeast Traveler joins your squad; gains an additional choice when loot drops from a Wish Fulfilled node",
        "你用鞭子抽打，它会惊慌逃跑；你以缰绳拉扯，它会扭头抗拒。晃动摇铃，轻哼歌谣，它将随你走遍天涯。",
        "Whip it, and it will flee in terror; Pull on the reins, and it will resist. Ring the bell, hum a tune, and it will follow you to the ends of the land.",
        sourceRule("驮兽旅行家加入队伍，在得偿所愿节点掉落战利品时，增加一个可选项"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_LAMP_OF_WISHES() {
    return collectible(
        "lamp_of_wishes",
        "美愿时代的留恋",
        "源石锭+10，刷新节点时更容易出现得偿所愿，在得偿所愿中会发挥奇妙的效果",
        "Originium Ingots +10, Wish Fulfilled will appear more often when refreshing nodes; triggers wondrous effects in Wish Fulfilled",
        "“听说萨卡兹能够实现任何愿望，于是我执着地探求他们的秘密，如今看来，这成了他们苦难的开端。”",
        "'I heard the Sarkaz can grant any wish, so I dug deep to find their secrets. It seems this was the start of their suffering.'",
        partialRule("源石锭+10，刷新节点时更容易出现得偿所愿，在得偿所愿中会发挥奇妙的效果", stats -> stats.originiumIngots(10)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ROLLING_ANCESTORS() {
    return collectible(
        "rolling_ancestors",
        "滚动先祖",
        "所有敌方单位的攻击速度+30，移动速度+30%，最大生命值+30%，可在虚实疆界中探索不同的险路恶敌",
        "All enemy units have ASPD +30, Movement Speed +30%, and Max HP +30%; can encounter a different Dreadful Foe in Border of Truth",
        "哦不，先祖们来了，先祖们想要体验更刺激的……",
        "Oh great, the ancestors are here, and they want something more exciting...",
        partialRule("所有敌方单位的攻击速度+30，移动速度+30%，最大生命值+30%，可在虚实疆界中探索不同的险路恶敌", stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> enemyStats.addAttackSpeed(30))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BLOOD_TAX_MYSTERY() {
    return collectible(
        "blood_tax_mystery",
        "血税之谜",
        "所有友方单位和敌人的最大生命值减少35%，仅在诡谲断章中生效",
        "All allied and enemy units have -35% Max HP; only active in Bizarre Fragments",
        "“杜卡雷终究失败了，他挺身而出，仍没能阻止自己的兄弟丹索，殷红的锁链于是拴住了大地上的一切生灵。所以如今我们每走一步都会留下一道血的印痕，这血税就是为了还我们那时凭空欠下的债。”",
        "'Duq'arael ultimately failed. He stepped forward bravely, but did not manage to stop his brother Danso. The deep red chains then tethered all life on this land, so we now leave a trail of blood with every step we take. This blood tax is to repay the baseless debt we owe.'",
        sourceRule("所有友方单位和敌人的最大生命值减少35%，仅在诡谲断章中生效"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_CRYSTAL_MYSTERY() {
    return collectible(
        "crystal_mystery",
        "结晶之谜",
        "所有地面干员防御力+600，但部署后，冻结15秒，仅在诡谲断章中生效",
        "All low ground Operators have +600 DEF but are Frozen for 15 seconds after deployment; only active in Bizarre Fragments",
        "“人们仿照‘石翼魔’，将‘防腐师’的后代称作‘冰翼魔’。他们拥有坚冰的力量与耐心，总和炎魔共事，互相弥补天性的缺点。但后来，他们全被卡尔纳巫狄卡所骗，为阻绝黑流下陷而再未回到地面。”",
        "'The Rotproofers' descendants, called 'Ice Gargoyles,' complemented the Diαblos with their icy powers and cool heads, skillfully covering each other's flaws. But they were deceived by Kalnawdika and never resurfaced after going underground to stop the Black Flow.'",
        sourceRule("所有地面干员防御力+600，但部署后，冻结15秒，仅在诡谲断章中生效"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SUSPICION_MYSTERY() {
    return collectible(
        "suspicion_mystery",
        "猜疑之谜",
        "战斗后获得的源石锭翻倍，但失去目标生命值翻倍，仅在诡谲断章中生效",
        "Earn double the Originium Ingots post battle, but also lose double the Life Points; only active in Bizarre Fragments",
        "“杜拉喀姆公开质疑‘远逐者’对遗迹表现出的谨慎，带领子裔冲进了地下。预言说，他将崩解于‘万古不愈’的创伤，他的子裔将自称‘杜林’，他们的探井将深入大地之心，但将没有活人再能见到他们。”",
        "'Duq'hraqaam openly questioned the Farchaser's caution towards the ruins and led his descendants underground. Prophecy foretold his demise by an 'ancient wound that refuses to heal' and that his descendants would call themselves 'Durin.' Their test tunnels would reach deep into the land's core, but no living soul would ever see them again.'",
        sourceRule("战斗后获得的源石锭翻倍，但失去目标生命值翻倍，仅在诡谲断章中生效"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FABRICATION_MYSTERY() {
    return collectible(
        "fabrication_mystery",
        "惧傲之谜",
        "初始部署费用+50，费用自然回复速度降低50%，仅在诡谲断章中生效",
        "Starting DP +50, but natural DP regeneration rate is reduced by 50%; only active in Bizarre Fragments",
        "“‘大诳言者’曾造访此思维边界之地。她以多彩故事为此处风景增色，招待众萨卡兹来此讲古取乐，留下谜题若干，破解之即洞察历史。但时人都道‘笞心恶主所述皆为诳言’，故众萨卡兹离去后俱笃信其不真。”",
        "The 'Great Deceiver' once visited this Place of Possibility. She enriched the scenery with vivid tales, invited Sarkaz to hear ancient stories, and left puzzles revealing historical insights if solved. Yet, people insisted the Djall Arch-liar spoke only lies, leaving the Sarkaz convinced that what they heard was mere fabrications.",
        partialRule("初始部署费用+50，费用自然回复速度降低50%，仅在诡谲断章中生效", stats -> stats.initialDeploymentPoints(50)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FORESIGHT_MYSTERY() {
    return collectible(
        "foresight_mystery",
        "预示之谜",
        "战斗获得的指挥经验+20%，战斗时出现额外敌人，仅在诡谲断章中生效",
        "Gain +20% Command EXP from battles, extra enemies will appear in battle; only active in Bizarre Fragments",
        "“摩迩那剜去了自己的独眼，又请巫妖将自己放逐，试图让那偶尔瞥见的景象永久作废。自那以后，新降生的独眼巨人逐渐开始和其他萨卡兹一样，生有双眼。但那预言有人记录了下来，于是仍然应验。”",
        "'Lone eye, self-gouged; exile, a favor from the Liches. Mur'rannag endured this to prevent a future seen in visions. Since then, newborn Cyclopes started having two eyes like regular Sarkaz, but the prophecy was documented and would still come true.'",
        sourceRule("战斗获得的指挥经验+20%，战斗时出现额外敌人，仅在诡谲断章中生效"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SIGIL_MYSTERY() {
    return collectible(
        "sigil_mystery",
        "烙印之谜",
        "所有干员初始技力+30，技力自然回复速度下降50%，仅在诡谲断章中生效",
        "All Operators gain +30 Starting SP, but natural SP recovery rate is reduced by 50%; only active in Bizarre Fragments",
        "“在宗长的首肯之下，北风女巫卡莱莎开始教导食腐者们从永恒狩猎的恒律中汲取力量。而对孽茨雷来说，那是他臣民的理性战胜天性的开端。他超乎所有人预料的决定，让食腐者们在成为护灵者后仍继续尊他为宗长。”",
        "'With the sovereign's approval, the Northwind Witch Qalaiša taught the Nachzehrers how to draw energy from the Eternal Hunt's rigid laws. To Nezzsalem, that was the start of his subjects surpassing nature with reason. His decision exceeded everyone's expectations, and the Nachzehrers continue to revere him as their liege after becoming Nachzehrer Beschützers.'",
        sourceRule("所有干员初始技力+30，技力自然回复速度下降50%，仅在诡谲断章中生效"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_TRANQUIL_MYSTERY() {
    return collectible(
        "tranquil_mystery",
        "宁静之谜",
        "所有友方单位和敌人的攻击力减少25%，仅在诡谲断章中生效",
        "All allied and enemy units have -25% ATK, only effective in Bizarre Fragments",
        "“从阿尼尤斯山的山顶到海佩伦湖的湖心，遍布着无根之人的僧院。僧院依钟而作，亦依钟而息。那钟声仿佛出自万物之源，又回荡于亘古之中，使西图者罢返，令东掠者偃兵。维、卡两国由是至今无事。”",
        "'From the peak of Mount Anius to the heart of Lake Hyperion sit numerous monasteries of 'those without roots'. They work when their bells ring and rest otherwise. These bells seem to originate from the source of all things, and their rings resonate through eternity, beckoning all those seeking westward to turn back and ordering those sweeping eastward to lay down their arms. And thus, the nations of Victoria and Kazdel have enjoyed peace that lasts to this day.'",
        sourceRule("所有友方单位和敌人的攻击力减少25%，仅在诡谲断章中生效"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SILK_BOND_MYSTERY() {
    return collectible(
        "silk_bond_mystery",
        "丝契之谜",
        "干员周围8格存在其他干员时持续受到伤害，干员周围8格不存在其他干员时攻击速度+50，仅在诡谲断章中生效",
        "Operators take continuous damage if other Operators are within the 8 surrounding tiles; otherwise, gain +50 ASPD; only active in Bizarre Fragments",
        "“巫妖贝尔莎从小痴迷于古老知识中记载的‘其他种的萨卡兹’。在成为‘誊录’后次日，她便强行与实验动物签下巫术契约，开启其灵智，‘虫卡兹’真相如此。她的丝线现在还躲着，不敢见其他巫妖。”",
        "'Lich Bertha was obsessed with other kinds of Sarkaz since childhood. On the very next day that she became a 'First Scribe', she forced a witchcraft contract with a test subject animal to unlock its intelligence, creating the 'Slugkaz'. Her silk threads remain hidden, afraid to face the other Liches.'",
        sourceRule("干员周围8格存在其他干员时持续受到伤害，干员周围8格不存在其他干员时攻击速度+50，仅在诡谲断章中生效"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_PLEDGE_OF_BABEL() {
    return collectible(
        "pledge_of_babel",
        "巴别塔誓言",
        "所有干员的费用+3，让探索开启不同的方向",
        "All Operators +3 DP Cost; leads expedition in a different direction",
        "“巴别塔”由两位萨卡兹的魔王直接领导，在军事、科技、医疗等领域皆有建树。其最初，也是最终的目的，在于通过任何手段消灭战争本身。",
        "'Babel' is directly led by two Kings of Sarkaz who have contributed greatly to the advancement of the military, technology, and medicine. Its initial, and also ultimate, goal is to eliminate war itself by any means necessary.",
        sourceRule("所有干员的费用+3，让探索开启不同的方向"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_PROPHET_HORN() {
    return collectible(
        "prophet_horn",
        "先知长角",
        "刷新节点时出现命运所指，在诡谲断章中无法生效",
        "Nodes will turn into Prophecy when refreshed; not active in Bizarre Fragments",
        "征服神民与先民的并不是铳械，而是律法。很快，“提卡兹”这个词的意义超越了种族，而卡兹戴尔，也就此成为众生的家园。",
        "It was neither guns nor blades that conquered the Elders and Ancients, but the Law. Soon, the significance of the word 'teekaz' transcended race, and Kazdel became home to all life.",
        sourceRule("刷新节点时出现命运所指，在诡谲断章中无法生效"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_TEN_RINGS() {
    return collectible(
        "ten_rings",
        "十戒",
        "尊主的残影最大生命值下降50%",
        "Shadows of the Lord -50% Max HP",
        "比起借助魔王的力量，阿米娅更希望用自己的力量来改变大地。她拒绝了魔王的好意，带着她特有的坚韧与决心行走在泰拉诸国间，救死扶伤，呼吁和平，直到矿石病夺走她的生命。",
        "Amiya hoped to change the land with her own power rather than relying on the King of Sarkaz. She refused their kindness and journeyed through Terra's nations with unmatched resilience and resolution, saving lives and calling for peace until Oripathy claimed her life.",
        sourceRule("尊主的残影最大生命值下降50%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_TIME_AND_LIGHT() {
    return collectible(
        "time_and_light",
        "时与光",
        "年代会更频繁地出现，思绪的负荷+1，让探索开启不同的方向",
        "Epochs will occur more often, and Thoughts have +1 Toil; leads expedition in a different direction",
        "唤醒律法的第一声狂嚎。",
        "The first mad howl that awakened the Law.",
        sourceRule("年代会更频繁地出现，思绪的负荷+1，让探索开启不同的方向"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_PETAL() {
    return collectible(
        "petal",
        "片瓣",
        "战斗获得的指挥经验与源石锭-50%，在狭路相逢中会有奇妙的效果",
        "-50% Command EXP and Originium Ingots from battles; triggers wondrous effects in Face-Off",
        "了悟。",
        "Epiphany.",
        sourceRule("战斗获得的指挥经验与源石锭-50%，在狭路相逢中会有奇妙的效果"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ANASA_S_KARMA() {
    return collectible(
        "anasa_s_karma",
        "阿纳萨羯磨",
        "领袖与精英敌人最大生命值+50%，使虚实疆界中的险路恶敌更加艰难，让探索开启不同的方向",
        "Leader and Elite enemies have +50% max HP; Dreadful Foes in Border of Truth will be more difficult; leads expedition in a different direction",
        "他的心性已变，他的利剑已碎，但身上的结晶无时无刻不在提醒着他，修行尚未结束。",
        "His nature has changed and his sword lies shattered, yet the crystals on his body are an eternal reminder that his spirit still needs honing.",
        sourceRule("领袖与精英敌人最大生命值+50%，使虚实疆界中的险路恶敌更加艰难，让探索开启不同的方向"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_PROPHETIC_IMAGE() {
    return collectible(
        "prophetic_image",
        "预言显像",
        "终曲合声会在作战中出现",
        "Finale Cadence will appear in battles",
        "你一定听说过这样一段文字：“我见你，头顶黑冠，将千万生灵，熬成回忆......”",
        "You must have heard this before: 'I see you, black crown on your head, melting millions of lives, into nothing but memories.'",
        sourceRule("终曲合声会在作战中出现"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ENDLESS_KEY() {
    return collectible(
        "endless_key",
        "无终之钥",
        "让探索开启不同的方向",
        "Leads expedition in a different direction",
        "经由你的泉涌灵思，故事从虚无中诞生；经由你的枯竭想象，阿米娅将要终止万物。你的思想能够成为反抗她的武器吗？",
        "Through your torrent of inspirations, a story takes shape from nothingness. Through your exhausted imagination, Amiya will put an end to everything. Can your thoughts become a weapon to resist her?",
        sourceRule("让探索开启不同的方向"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_FRAMEWORK_OF_THE_END() {
    return collectible(
        "framework_of_the_end",
        "终结的骨架",
        "所有干员的部署费用+2，所有敌人受到的物理与法术伤害降低20%",
        "All Operators have +2 DP Cost; all enemies take 20% less Physical and Arts damage",
        "到处是天灾，到处是战争。当救助本身都阻止不了死亡，所有医疗行为都只是仇恨的帮凶时，阿米娅只能徒劳且机械地“挽救生命”——这是她唯一能做的事。",
        "Catastrophes and war, as far as the eye can see. When help cannot stop death, when medical treatment becomes only an accomplice to hatred, Amiya carries on with the only option left: the futile, mechanical act of 'saving lives'.",
        sourceRule("所有干员的部署费用+2，所有敌人受到的物理与法术伤害降低20%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BODY_OF_THE_END() {
    return collectible(
        "body_of_the_end",
        "终结的躯体",
        "所有干员的部署费用+2，所有敌人受到的物理与法术伤害降低20%，所有敌人攻击力+20%",
        "All Operators have +2 DP Cost; all enemies take 20% less Physical and Arts damage and gain 20% ATK",
        "阿米娅怀着悲伤看着眼前被封入“茧”中的人，他的一切在此定格，这样，他就再也不会疼痛，源石也不会夺走他的生命了。阿米娅不愿意去盲目使用这突然觉醒的力量，可现在......没有更好的选择了。",
        "Amiya looks sadly at the person before her, sealed within the 'cocoon'. Frozen in place, they will no longer feel pain, nor will Originium cut their life short. She was unwilling to recklessly use her newly awakened powers, but now... there is no other choice.",
        sourceRule("所有干员的部署费用+2，所有敌人受到的物理与法术伤害降低20%，所有敌人攻击力+20%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_REALITY_OF_THE_END() {
    return collectible(
        "reality_of_the_end",
        "终结的实相",
        "所有干员的部署费用+2，所有敌人受到的物理与法术伤害降低20%，所有敌人攻击力+20%，最终战场上出现额外敌人",
        "All Operators have +2 DP Cost; all enemies take 20% less Physical and Arts damage and gain 20% ATK; extra enemies will appear in the last battle",
        "随着一切被封入“茧”中，时间在这里只作用于阿米娅一人，她会去寻找一切病源的解药——无论是生理的，还是心理的，她都想要治愈。在那之前，将是一场绝对孤独且几无尽头的长旅。阿米娅已经出发了。",
        "Everything has been sealed into the 'cocoon', and now time flows only for Amiya. She will seek a panacea for all afflictions—physical or mental, she wants to cure them all. An absolutely lonely and endlessly long journey stands before her. She has already set off.",
        sourceRule("所有干员的部署费用+2，所有敌人受到的物理与法术伤害降低20%，所有敌人攻击力+20%，最终战场上出现额外敌人"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_DEVILBANE_BANNER() {
    return collectible(
        "devilbane_banner",
        "“讨魔义旗”",
        "所有干员对【萨卡兹】敌人造成伤害时回复2点技力",
        "Operators recover 2 SP when dealing damage to [Sarkaz] enemies",
        "效忠伊比利亚的萨卡兹雇佣兵制作了它，用来屠戮效忠莱塔尼亚的萨卡兹雇佣兵。它现在的名字则是很多年后效忠维多利亚的萨卡兹雇佣兵捡到它时起的。",
        "Made by Sarkaz mercenaries loyal to Iberia, and used to kill Sarkaz mercenaries loyal to Leithanien. Many years later, Sarkaz mercenaries loyal to Victoria picked it up and gave it its name.",
        sourceRule("所有干员对【萨卡兹】敌人造成伤害时回复2点技力"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HOLY_CITY_S_EMBRACE() {
    return collectible(
        "holy_city_s_embrace",
        "“圣城之拥”",
        "受到【萨卡兹】敌人的伤害降低35%",
        "Takes 35% less damage from [Sarkaz] enemies",
        "“哦，神圣的卡兹戴尔啊！愿你祝福我的子弹，如同你祝福长存的盟约一样；愿你连结我的铳口和目标，如同你连结你的萨科塔和萨卡兹一般！”",
        "'O holy Kazdel! May you bless my bullets as you have blessed our eternal covenant; may you bridge my gun to my target just as you have bridged the Sankta and the Sarkaz!'",
        sourceRule("受到【萨卡兹】敌人的伤害降低35%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_LITTLE_CUBE() {
    return collectible(
        "little_cube",
        "“小方块”",
        "所有我方单位最大生命值+10%，在狭路相逢中最大生命值额外+80%",
        "All Allied units gain +10% Max HP, and an additional +80% in Face-Off nodes",
        "巫妖们的超时空座驾，在非现实情境反而更加坚固。埃芒加德花了好久才修好它。",
        "The Liches' vessel, capable of transcending time and space, proves even more durable in fantasies. Ermengarde spent a lot of time fixing it up.",
        partialRule("所有我方单位最大生命值+10%，在狭路相逢中最大生命值额外+80%", stats -> stats.multiplyMaxHealth(0.1)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_GREAT_BANSHEE_S_VEIL() {
    return collectible(
        "great_banshee_s_veil",
        "讴歌者面纱",
        "所有敌方单位受到的法术伤害+10%，受到的元素损伤+50%",
        "All enemies take +10% Arts damage and +50% Elemental Injury",
        "前任女妖之主留给人一种亲切的印象，好似她从来都是如此......直到她拔出骨笔，轻声吟唱，将哀嚎钉入每一个入侵者的思维。",
        "The former Lord of the Banshees always seemed amicable, as if that were her only side... until she pulled out her bone pen, sang softly, and impaled agonizing wails into the minds of every intruder.",
        partialRule("所有敌方单位受到的法术伤害+10%，受到的元素损伤+50%", stats -> stats.addEnemyMagicDamageTakenBonus(0.1)),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_FEARLESS_BLADE() {
    return collectible(
        "fearless_blade",
        "无惧之刃",
        "作战开始时，我方部署费用最高的干员攻击力与防御力+50%",
        "When the battle begins, the Operator with the highest DP Cost gains +50% ATK and DEF",
        "他站在城门前，为卡兹戴尔守住这处门户。高卢火炮队、莱塔尼亚术师团、维多利亚骑士轮番上阵，想要突破这只有一名萨卡兹的防线。可直到城门被轰碎，直到联军决定撤退，这位歌利亚剑士都没有后退一步。",
        "Before Kazdel's gates he stood, an impregnable bulwark. Gaulish artilleries, Leithanian casters, and Victorian Knights all took their shots at him. Yet, even when the gates fell and the coalition retreated, the Goliath did not yield an inch.",
        sourceRule("作战开始时，我方部署费用最高的干员攻击力与防御力+50%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ARCH_GLYPH() {
    return collectible(
        "arch_glyph",
        "“拱门”与“呼救”",
        "仅一次，在非区域最终战斗和领袖战中失败时不结束探索，且若当前不处于诡谲断章时进入一个特殊的诡谲断章",
        "For one time only, if you would fail any battle other than the final battle of the area or a Leader battle and are currently not in a Bizarre Fragment, you will enter a special Bizarre Fragment",
        "自古以来，卡兹戴尔便隐藏于北方的风雪中。独眼巨人带领着其他萨卡兹制造了这种拥有通灵力量的密文印章，危急时刻把它盖在冰雪上，就能躲藏进脚下巨兽的内心，避开“敌人”。",
        "Since antiquity, Kazdel has been concealed within the northern wind and snow. Led by the Cyclopes, the Sarkaz jointly created this Foldartal seal with their psychic powers. In a pinch, place it on ice and snow to hide within the Feranmut beneath your feet, evading the 'enemy'.",
        sourceRule("仅一次，在非区域最终战斗和领袖战中失败时不结束探索，且若当前不处于诡谲断章时进入一个特殊的诡谲断章"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_GUL_DUL_S_SILENCE() {
    return collectible(
        "gul_dul_s_silence",
        "戈渎不语",
        "所有敌方单位防御力+3000，但每次受到伤害时防御力下降100（最多下降30次）",
        "All enemies gain +3000 DEF, but lose 100 DEF upon taking damage (Stacks up to 30 times)",
        "他站在卡兹戴尔的最高处，自此化为石雕。神民霸主们见到黑冠后退却，萨卡兹们见到黑冠后喜悦。这名为“魔王”的奇观存在了很久，直至有一天，萨卡兹们忘却了魔王，亲手将它从塔顶上推下。",
        "He stood at Kazdel's highest point and turned to stone. The Elder Hegemons retreated upon witnessing his black crown, while the Sarkaz rejoiced. This spectacle they named 'King of Sarkaz' endured for a long time—until the day the Sarkaz forgot their King, and pushed the statue down with their own hands.",
        sourceRule("所有敌方单位防御力+3000，但每次受到伤害时防御力下降100（最多下降30次）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_QUI_SARTUSTAJ_S_PROMISE() {
    return collectible(
        "qui_sartustaj_s_promise",
        "奎萨图什塔允诺",
        "招募及进阶5星及以上干员的所需要的希望+1",
        "All 5-star and above Operators cost +1 Hope to recruit and promote",
        "每更迭一代，他都向自己的载体与子民承诺，会为萨卡兹改变现状。在那层层累积的希望与信任中，他将王冠安放在了圆环巨构上，叩响了星空之门。",
        "With every changing generation, he promised his people and his carriers he would change the status quo for the Sarkaz. Within the layers of amassed hope and trust, he placed the crown on the ring-shaped structure, and knocked on the gates to the starry sky.",
        sourceRule("招募及进阶5星及以上干员的所需要的希望+1"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BALOR_SACA_S_ARROGANCE() {
    return collectible(
        "balor_saca_s_arrogance",
        "霸迩萨狂言",
        "精英及领袖敌人的生命值、防御力、攻击力+20%",
        "Elite and Leader enemies gain +20% HP, DEF, and ATK",
        "熔岩顺从地跟随着霸迩萨的军队在大地上蔓延。他们不在乎神民霸主，也不在乎其他萨卡兹。这片大地是炎魔的大地，不需要容下别人，而作为炎魔的王，霸迩萨也无法容忍王冠落在别人的脑袋上。",
        "Lava obediently followed Balor'sača's army and spread across the land. They cared not for the Elder Hegemons, nor for their fellow Sarkaz. This land belongs to the Diαblo, and there is no need for others. As their King, Balor'sača absolutely cannot allow the crown to fall on the head of another.",
        sourceRule("精英及领袖敌人的生命值、防御力、攻击力+20%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_YLIS_S_RAVINGS() {
    return collectible(
        "ylis_s_ravings",
        "以勒什呓语",
        "所有干员的初始技力-20",
        "All Operators start with -20 SP",
        "唉，可怜的人啊，他无法理解王冠的伟力，以至成了一个傀儡。那卑微的自我再无展现的可能，口中说出的话语也不再出于自己的意志。他成了王冠觉醒的第一个牺牲品。",
        "Alas, the poor man. He could not comprehend the crown's power, and became a puppet. He was no longer his humble self, and lost the will to speak his own words. He became the first sacrifice to the crown's awakening.",
        sourceRule("所有干员的初始技力-20"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SHARD_OF_THE_UNTOLD_KINGS() {
    return collectible(
        "shard_of_the_untold_kings",
        "未叙魔王残片",
        "负荷干员的攻击力-30%",
        "Loadbearing Operators have -30% ATK",
        "知识圣殿的看守人以命结穿破生命的奥秘、疯狂的萨卡兹佣兵骑乘魂灵播撒混乱、恼人的兽主用熔炉建立商业帝国、古老的菲林戴上王冠征服自己的造物主......仍有许多故事折叠在思绪中，等待着被讲述。",
        "A guardian of the Temple of Knowledge uses their phylactery to unveil the mysteries of life, a crazy Sarkaz mercenary saddles a revenant to sow chaos, a vexing Beast Aristocrat establishes a commercial empire with the furnace, an ancient Feline wears the crown and subdues her own creator... Many stories remain folded within thoughts, waiting to be told.",
        sourceRule("负荷干员的攻击力-30%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_JUDGEMENT_GUARD() {
    return collectible(
        "judgement_guard",
        "论断：近卫",
        "每拥有一个仅在诡谲断章生效的收藏品，【近卫】干员的攻击力+20%，攻速+20",
        "For every Bizarre Fragment Collectible owned, Guard Operators gain +20% ATK and +20 ASPD",
        "面对魂灵昭示的诸多谜题，近卫们挥舞武器将其剖开。",
        "Faced with the revenants' myriad mysteries, the Guards brandish their weapons and cut a path.",
        sourceRule("每拥有一个仅在诡谲断章生效的收藏品，【近卫】干员的攻击力+20%，攻速+20"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_JUDGEMENT_VANGUARD() {
    return collectible(
        "judgement_vanguard",
        "论断：先锋",
        "每拥有一个仅在诡谲断章生效的收藏品，【先锋】干员的攻击力+20%，最大生命值+30%",
        "For every Bizarre Fragment Collectible owned, Vanguard Operators gain +20% ATK and +30% Max HP",
        "面对魂灵昭示的诸多谜题，先锋们身先士卒突入其中。",
        "Faced with the revenants' myriad mysteries, the Vanguards take the lead and charge in.",
        sourceRule("每拥有一个仅在诡谲断章生效的收藏品，【先锋】干员的攻击力+20%，最大生命值+30%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_JUDGEMENT_DEFENDER() {
    return collectible(
        "judgement_defender",
        "论断：重装",
        "每拥有一个仅在诡谲断章生效的收藏品，【重装】干员的防御力+20%，最大生命值+30%",
        "For every Bizarre Fragment Collectible owned, Defender Operators gain +20% DEF and +30% Max HP",
        "面对魂灵昭示的诸多谜题，重装们做好防御稳步推进。",
        "Faced with the revenants' myriad mysteries, the Defenders prepare their defenses and steadily advance.",
        sourceRule("每拥有一个仅在诡谲断章生效的收藏品，【重装】干员的防御力+20%，最大生命值+30%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_JUDGEMENT_SNIPER() {
    return collectible(
        "judgement_sniper",
        "论断：狙击",
        "每拥有一个仅在诡谲断章生效的收藏品，【狙击】干员的攻击力+20%，攻速+20",
        "For every Bizarre Fragment Collectible owned, Sniper Operators gain +20% ATK and +20 ASPD",
        "面对魂灵昭示的诸多谜题，狙击们调整距离倾泻火力。",
        "Faced with the revenants' myriad mysteries, the Snipers adjust positions and unleash their firepower.",
        sourceRule("每拥有一个仅在诡谲断章生效的收藏品，【狙击】干员的攻击力+20%，攻速+20"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_JUDGEMENT_SPECIALIST() {
    return collectible(
        "judgement_specialist",
        "论断：特种",
        "每拥有一个仅在诡谲断章生效的收藏品，【特种】干员的攻击力+20%，最大生命值+30%",
        "For every Bizarre Fragment Collectible owned, Specialist Operators gain +20% ATK and +30% Max HP",
        "面对魂灵昭示的诸多谜题，特种们快速部署直冲要害。",
        "Faced with the revenants' myriad mysteries, the Specialists swiftly drop in on key targets.",
        sourceRule("每拥有一个仅在诡谲断章生效的收藏品，【特种】干员的攻击力+20%，最大生命值+30%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_JUDGEMENT_MEDIC() {
    return collectible(
        "judgement_medic",
        "论断：医疗",
        "每拥有一个仅在诡谲断章生效的收藏品，【医疗】干员的攻击力+50%",
        "For every Bizarre Fragment Collectible owned, Medic Operators gain +50% ATK",
        "面对魂灵昭示的诸多谜题，医疗们耐心治疗等待转机。",
        "Faced with the revenants' myriad mysteries, the Medics treat the wounded and wait for the tides to shift.",
        sourceRule("每拥有一个仅在诡谲断章生效的收藏品，【医疗】干员的攻击力+50%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_JUDGEMENT_CASTER() {
    return collectible(
        "judgement_caster",
        "论断：术师",
        "每拥有一个仅在诡谲断章生效的收藏品，【术师】干员的攻击力+20%，攻速+20",
        "For every Bizarre Fragment Collectible owned, Caster Operators gain +20% ATK and +20 ASPD",
        "面对魂灵昭示的诸多谜题，术师们施放技艺分解构造。",
        "Faced with the revenants' myriad mysteries, the Casters release their Arts to deconstruct them.",
        sourceRule("每拥有一个仅在诡谲断章生效的收藏品，【术师】干员的攻击力+20%，攻速+20"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_JUDGEMENT_SUPPORTER() {
    return collectible(
        "judgement_supporter",
        "论断：辅助",
        "每拥有一个仅在诡谲断章生效的收藏品，【辅助】干员的攻击力+50%",
        "For every Bizarre Fragment Collectible owned, Supporter Operators gain +50% ATK",
        "面对魂灵昭示的诸多谜题，辅助们别出心裁提供巧思。",
        "Faced with the revenants' myriad mysteries, the Supporters break the mold and offer their unique ingenuity.",
        sourceRule("每拥有一个仅在诡谲断章生效的收藏品，【辅助】干员的攻击力+50%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_HAND_OF_FIREWORKS() {
    return collectible(
        "hand_of_fireworks",
        "烟花之手",
        "【速射手】【回环射手】【哨戒铁卫】每次攻击命中时，有25%概率发射一枚额外子弹，造成相当于攻击力200%的群体物理伤害",
        "Marksman, Loopshooter, and Sentry Protector Operators have a 25% chance on each attack to shoot an extra projectile, dealing 200% ATK as AoE Physical Damage",
        "砰砰啪啪，意外之喜。",
        "Bang! Boom! A pleasant surprise.",
        sourceRule("【速射手】【回环射手】【哨戒铁卫】每次攻击命中时，有25%概率发射一枚额外子弹，造成相当于攻击力200%的群体物理伤害"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_CLOTHESKAZ() {
    return collectible(
        "clotheskaz",
        "衣卡兹",
        "干员首次技能结束后立刻获得30点技力",
        "Operators immediately obtain 30 SP after their skill ends for the first time",
        "借由穿戴它的生物间激烈的竞争与攀比行为，衣卡兹群体正在茁壮成长。",
        "The Clotheskaz community thrives on the fierce competition among the creatures that wear them.",
        sourceRule("干员首次技能结束后立刻获得30点技力"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_UNRIPE_YEARNING() {
    return collectible(
        "unripe_yearning",
        "青涩眷恋",
        "每携带一缕思绪，所有干员最大生命值+3%，防御力+3%",
        "For every Thought you have, all Operators gain +3% Max HP and DEF",
        "未成形的想法如婴孩般眷恋创造者，它们狂热地护卫着造物主，直至灵光乍现，蜕变成形，离家远行。",
        "These incomplete thoughts cling to their creators, zealously guarding them until they obtain their forms through a sudden revelation and embark on a long journey.",
        sourceRule("每携带一缕思绪，所有干员最大生命值+3%，防御力+3%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_CURSED_COUNTERBEAST() {
    return collectible(
        "cursed_counterbeast",
        "咒仪溯兽",
        "所有干员攻速+5，每次作战结束后消耗一个灵感，使所有干员攻速+5（可叠加10次）",
        "All Operators gain +5 ASPD; consumes 1 Inspirer after each battle to grant an additional 5 ASPD (stacks up to 10 times)",
        "奇想迸发的一瞬引来了这些生于谬误的野兽，它们毫无节制地吞食，直至时间破碎，悖论丛生。届时，它们便能产下新的幼崽。",
        "These bizarre beasts, born of falsehood, are drawn to fleeting flashes of odd thoughts. They devour wantonly, and when time shatters and paradoxes surge, they give birth to new cubs.",
        sourceRule("所有干员攻速+5，每次作战结束后消耗一个灵感，使所有干员攻速+5（可叠加10次）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_RHODER_S_GATE() {
    return collectible(
        "rhoder_s_gate",
        "罗德之门",
        "立即招募数名罗德岛预备干员，每场作战结束时，最后部署的干员会进入先行一步",
        "Immediately recruit multiple Reserve Operators; at the end of each combat, the last deployed Operator will be sent to a Scout node",
        "这扇经过改造的“门”将两段故事紧紧连接到了一起，“门”的另一头是罗德戴尔——萨卡兹与感染者们的避风港。",
        "This remodeled Portal binds two stories together, with the other end being Rhozdel—a safe haven for both Sarkaz and Infected.",
        sourceRule("立即招募数名罗德岛预备干员，每场作战结束时，最后部署的干员会进入先行一步"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_VISAGE_OF_CROWNED_FATES() {
    return collectible(
        "visage_of_crowned_fates",
        "王命凡形",
        "护盾值+3，将剩余的生命值上限全部转化为等量护盾值，之后将最大生命值变为1",
        "Shield +3; converts all remaining max Life Points into an equivalent amount of Shield before reducing max Life Points to 1",
        "王各有命，形亦不同。",
        "Each King bears a unique fate and form.",
        partialRule("护盾值+3，将剩余的生命值上限全部转化为等量护盾值，之后将最大生命值变为1", stats -> stats.addMaxHealth(3)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_KING_OF_SARKAZ_S_VESSEL() {
    return collectible(
        "king_of_sarkaz_s_vessel",
        "魔王的祭器",
        "目标生命值达到上限时，所有干员的最大生命值+80%，集齐3件及以上魔王收藏品后变为+200%",
        "All Operators gain +80% Max HP when Life Points are full, increased to 200% if you have collected 3 or more King of Sarkaz's Collectibles",
        "身居此位者，兴盛仅是泡影。",
        "For those in this position, prosperity is but an illusion.",
        sourceRule("目标生命值达到上限时，所有干员的最大生命值+80%，集齐3件及以上魔王收藏品后变为+200%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_EXPUNGER_OF_HATRED() {
    return collectible(
        "expunger_of_hatred",
        "死仇葬送者",
        "思维负荷+5，作战中如果使用了死斗，完美作战后将再次获得死斗",
        "+5 Mental Toil; if Deathmatch is used in a perfect clear, obtain a Deathmatch after the battle",
        "不愿被仇恨蒙蔽双眼的人们铸造了这把武器，它揭示了一条道路——当源自暴力的恐惧压倒一切仇恨时，或许，在那之后，人们能有机会窥见和平的曙光。",
        "This weapon was forged by those unwilling to be blinded by hatred. It reveals a path: When the fear born of violence overpowers all hatred, then maybe, there exists a glimpse of the first light of peace.",
        sourceRule("思维负荷+5，作战中如果使用了死斗，完美作战后将再次获得死斗"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BABY_DJALL() {
    return collectible(
        "baby_djall",
        "笞心魔宝宝",
        "希望+3，在失与得交换时希望额外+8",
        "+3 Hope, obtain an extra +8 Hope when this Collectible is exchanged in Lost and Found",
        "“哦，我的小可爱，不要在我心头上挠痒痒了，好吗？”",
        "'Oh, my little cutie-pie! Enough tickling my head, alright?'",
        partialRule("希望+3，在失与得交换时希望额外+8", stats -> stats.hope(3)),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_EYE_OF_FORTUNE() {
    return collectible(
        "eye_of_fortune",
        "财眼",
        "源石锭+5，被派遣的队员可以额外带回5源石锭",
        "Originium Ingots +5, Operators dispatched in Scout nodes will return with 5 extra Ingots",
        "“独眼巨人开眼见财”的谣言在萨卡兹消失千年后成了某种民俗文化。人们纷纷在家供上镶有独眼的圆盘，以求财运亨通、事事顺心。",
        "A millennium after the Sarkaz vanished, the saying 'the eye of a Cyclops opens for wealth' became folklore. People began worshipping discs with a single embedded eye, seeking wealth and happiness.",
        partialRule("源石锭+5，被派遣的队员可以额外带回5源石锭", stats -> stats.originiumIngots(5)),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_WONDROUS_GRAFFITI() {
    return collectible(
        "wondrous_graffiti",
        "妙用涂鸦",
        "每次敌人进入保护目标点后，立刻使全场干员获得20点技力",
        "Whenever an enemy enters the Objective Point, all Operators on field gain 20 SP",
        "这些街头“艺术作品”经常会被巫妖们加上几笔，按他们的说法，主要是为了以更经济的形式增强城市街道防御。",
        "The Liches often add a few more strokes to these pieces of 'street art'. According to them, it is mostly an economical way to bolster the city streets' defenses.",
        sourceRule("每次敌人进入保护目标点后，立刻使全场干员获得20点技力"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_OSMANTHUS_GOBLET() {
    return collectible(
        "osmanthus_goblet",
        "桂花杯",
        "希望+4，立即获得源石锭+5，此收藏品在失与得交换时额外获得1个收藏品",
        "Hope +4, immediately gain 5 Originium Ingots, obtain an extra Collectible when this Collectible is exchanged in Lost and Found",
        "价值连城的瓷杯，背后写有“执此杯者，折桂为居”的字样。听说一共有十二只，某收藏家似乎正好缺这一只。",
        "This priceless porcelain cup bears the words 'May the beholder be victorious'. Rumors say a complete set of twelve exist, and a certain collector is missing this last one.",
        partialRule("希望+4，立即获得源石锭+5，此收藏品在失与得交换时额外获得1个收藏品", statSet(stats -> stats.hope(4), stats -> stats.originiumIngots(5))),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_ENLISTMENT_ORDER() {
    return collectible(
        "enlistment_order",
        "封神令",
        "干员部署在侵入点周围8格时，攻击力+100%",
        "Operators deployed on the 8 tiles surrounding an Incursion Point gain +100% ATK",
        "博物馆收藏的大狩猎时代的征兵令。此令征集一支有能力深入巨兽巢穴的队伍，附在它后面的是签满了名字的生死状。",
        "An enlistment order from the museum collection, issued during the great hunt. It calls for members capable of infiltrating a Feranmut's lair, and attached is a list of names who have signed their lives away.",
        sourceRule("干员部署在侵入点周围8格时，攻击力+100%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_GEIST_BINDING_ROPE() {
    return collectible(
        "geist_binding_rope",
        "束伥索",
        "【召唤物】的部署费用-5，再部署时间-25%，攻击力+30%",
        "Summons have -5 DP Cost, -25% Redeployment Time, and gain +30% ATK",
        "被司岁台收编的伥怪在做活时所戴的标记、约束之索。主动倒水的茶壶、努力浇花的花洒，它们十分擅长简单重复的工作。",
        "Geists do the Sui Regulators' bidding while constrained by these ropes. Be it pouring tea or watering plants, they excel at simple, repetitive work.",
        sourceRule("【召唤物】的部署费用-5，再部署时间-25%，攻击力+30%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_AH_MENG() {
    return collectible(
        "ah_meng",
        "“阿猛”",
        "所有敌方单位受到的元素伤害+100%",
        "All enemies take +100% Elemental Damage",
        "百年前岁兽苏醒的灾祸中，司岁台曾借助经特殊训练的循兽，对伥怪进行全方位的搜索及消灭。此陶制品为纪念当时牺牲的循兽而制。",
        "During the calamity of the bestial Sui's awakening a century ago, the Sui Regulators used specially trained beckbeasts to track and eliminate geists. This ceramic sculpture commemorates the beckbeasts' sacrifice.",
        sourceRule("所有敌方单位受到的元素伤害+100%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_EVALUATION_MINISTER() {
    return collectible(
        "evaluation_minister",
        "赏善郎",
        "我方单位触发闪避或抵挡后，下次攻击造成的伤害+100%",
        "When an allied unit successfully Dodges or Resists damage, the next attack deals +100% damage",
        "天师府学徒自主研制的考校辅助器。答对问题时什么也得不到。答错时……还是别尝试了吧。",
        "An assessment support tool developed by Tianshi Bureau apprentices. You don't get anything even if you have the right answers. But when you're wrong... well, best to not even try.",
        sourceRule("我方单位触发闪避或抵挡后，下次攻击造成的伤害+100%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_BORDERBOUND_MIRROR() {
    return collectible(
        "borderbound_mirror",
        "镜中境",
        "敌人首次造成的伤害-90%",
        "Enemies' damage dealt on their first attack -90%",
        "常悬于界园门窗之上的镜子。当游客遇到突如其来的危机，镜中之境便是他们的紧急避难所——直至镜子碎裂。",
        "Mirrors like these often hung over windows and entrances of the Garden of Grotesqueries. When visitors run into unexpected danger, the realm within a mirror turns into an emergency shelter—until it shatters.",
        sourceRule("敌人首次造成的伤害-90%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_EVERLASTING_GUIDING_LIGHT() {
    return collectible(
        "everlasting_guiding_light",
        "长明灯引",
        "第一名部署的地面干员攻击力+50%、并获得可抵抗状态生效时间倍率-50%（非抵抗）、50%元素损伤减免，持续至被击倒或撤离",
        "The first deployed Ground Operator gains +50% ATK, status resistance, and 50% Elemental Injury reduction until they are defeated or retreated",
        "首次登升之人，为保其心智不被岁识淹没，持燃烛在手。瞢暗迫近，而烛火长明，为其在长夜中锚定意识归来之路。“秉烛”之名亦从中而来。",
        "First-timers scaling the mount wield a burning candle to shield their minds from being submerged in the Sui's consciousness. As dark encroaches, the candle burns bright, anchoring their own consciousness on their path back to the present, throughout their long night. This is how they came to be known as 'Candleholders'.",
        sourceRule("第一名部署的地面干员攻击力+50%、并获得可抵抗状态生效时间倍率-50%（非抵抗）、50%元素损伤减免，持续至被击倒或撤离"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FIREFLY_BOOKLIGHT() {
    return collectible(
        "firefly_booklight",
        "“萤灯映牍”",
        "敌方单位被击倒时，使周围的我方单位回复300点生命值",
        "When enemies are defeated, surrounding allied units recover 300 HP",
        "校舍窗边，家乡盆景渐枯，而书生不忍丢弃。终有一日，盆中草不见踪影，而盆上萤火萦绕，替他照亮了书页。",
        "A plant from a scholar's hometown gradually withers by the academy window, but he cannot bear to dispose of it. The pot is eventually bereft of vegetation, but is surrounded by fireflies illuminating the pages of his books.",
        sourceRule("敌方单位被击倒时，使周围的我方单位回复300点生命值"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_TOY_SLINGSHOT() {
    return collectible(
        "toy_slingshot",
        "玩具弹弓",
        "我方单位释放技能时对攻击范围内的所有敌人造成相当于攻击力30%的法术伤害",
        "When an Operator uses a skill, deals 30% of ATK as Arts damage to all enemies within Attack Range",
        "从洪炉中诞生的青铜弹弓，拉开皮筋时，小铜人会蜷成一团沉得惊人的弹丸，以待发射。“喏，拿去玩。危险个锤子，就是个玩具嘛。”",
        "A bronze slingshot born from the forge. When the rubber band is pulled, small copper companions huddle up into a surprising weighty ball, waiting to be launched. 'Here, have fun. It's not dangerous at all, no, it's just a toy.'",
        sourceRule("我方单位释放技能时对攻击范围内的所有敌人造成相当于攻击力30%的法术伤害"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SNACK_BASKET() {
    return collectible(
        "snack_basket",
        "零嘴背篓",
        "战斗开始时所有干员自然回复技力的速度+100%，任意干员手动释放技能时失效",
        "All Operators gain +100% natural SP recovery, effect lasts until any Operator manually activates a skill",
        "园林外贩卖的，装满零嘴的背篓，却在入园检查时被扣押。工作人员脸色难看地询问这东西从何而来，你如实回答后，他们一边安慰你没事，一边立刻抄起形状可疑的工具向你说的方向奔去。",
        "A backpack-basket filled with snacks to be sold outside the garden was confiscated at the entrance. The staff awkwardly ask where the items came from, and after you answer honestly, they assure you all is well as they dash in the direction you pointed out with suspicious-looking tools in hand.",
        sourceRule("战斗开始时所有干员自然回复技力的速度+100%，任意干员手动释放技能时失效"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_REMNANT_SPRING() {
    return collectible(
        "remnant_spring",
        "留春",
        "敌人被击倒时，使其周围所有敌人获得6秒的寒冷",
        "Enemies inflict Cold for 6 seconds on surrounding enemies when defeated",
        "何家仙人过，聊赠一枝春。",
        "What Xian came by and gifted a branch of spring?",
        sourceRule("敌人被击倒时，使其周围所有敌人获得6秒的寒冷"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_WALLEYE() {
    return collectible(
        "walleye",
        "墙眼",
        "我方干员受到来自自身攻击范围以外的物理与法术伤害降低50%",
        "Allied Operators take 50% less Physical and Arts damage from sources outside their Attack Range",
        "它的意识已然存在。它的步伐困于墙中。它为你遮风挡灾。它注视你的远去。",
        "Its consciousness still remains. Its steps stay trapped within the wall. It shields you from wind and calamity, and watches your departure.",
        sourceRule("我方干员受到来自自身攻击范围以外的物理与法术伤害降低50%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_WOODBLOCK_PRINTS() {
    return collectible(
        "woodblock_prints",
        "传芳雕版",
        "干员开启弹药类技能后，每次造成伤害时，攻击力+2%（最多叠加30层）",
        "After Operators use an ammo-type skill, gain +2% ATK every time they deal damage (Stacks to 30 times)",
        "流行诗集的枣木雕版。见到自己眼中欠佳的俗诗雕版广为流传时，清高文人往往嗤之以鼻：“哼，可悲枣树，罪不至此。”",
        "Popular poems engraved on jujube wood. When aloof scholars spot poems they deem inferior circulating amongst the masses, they often scoff and sneer: 'Hmph. What a tragedy, a crime, to waste fine wood like that.'",
        sourceRule("干员开启弹药类技能后，每次造成伤害时，攻击力+2%（最多叠加30层）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_THREE_FEET_TO_ALL() {
    return collectible(
        "three_feet_to_all",
        "三尺万象",
        "干员开启弹药类技能时，攻击速度+100，持续15秒",
        "After Operators use an ammo-type skill, +100 ASPD for 15 second",
        "古时炎国印书坊所用活字印刷机。君子以字为利器，而小人……曾有印书坊下印前，发现“非”字被偷，驳斥之文意思骤变，令人啼笑皆非。",
        "An ancient Yanese movable type printing press. A noble uses words as weapons, while villains... there was once a time when the printing house discovered the word block for 'not' was stolen right before they went to print a work, and the rebuttal text's meaning changed entirely, leaving one unsure whether to cry or not.",
        sourceRule("干员开启弹药类技能时，攻击速度+100，持续15秒"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_YANESE_DICTIONARY() {
    return collectible(
        "yanese_dictionary",
        "《炎国字汇》",
        "干员的弹药类技能每次开启后，首次剩余30%的弹药时，补充最大弹药数量30%的弹药",
        "When Operators use an ammo-type skill and consume 70% of ammo for the first time, replenishes 30% of max ammo count",
        "炎国的第一本通用字典，由民间作者历经十年完成。编者常叹书薄而字多，为无法收录、注定失传的生僻字立有一方字冢。",
        "Yan's first ever common dictionary, compiled by a civilian writer after over a decade of work. The editor often sighed over how thin the volumes were despite the amount of text, and dedicated a tomb to rare characters that could not be included or recorded.",
        sourceRule("干员的弹药类技能每次开启后，首次剩余30%的弹药时，补充最大弹药数量30%的弹药"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_COLLECTION_OF_ELEGANCE() {
    return collectible(
        "collection_of_elegance",
        "《雅集》",
        "干员的弹药类技能每次开启后，首次剩余30%的弹药时，补充最大弹药数量50%的弹药",
        "When Operators use an ammo-type skill and consume 70% of ammo for the first time, replenishes 50% of max ammo count",
        "炎国的第一部诗集，收录了当年传唱最广的诗篇。因其广受欢迎，促使民间抄诗与写诗的热情剧增，一度导致当年的纸墨供不应求。",
        "Yan's first poetry collection containing the most popular poems of the year it was published. Its immense popularity sparked a surge within the masses to copy the poems or write new ones, leading to a shortage of paper and ink.",
        sourceRule("干员的弹药类技能每次开启后，首次剩余30%的弹药时，补充最大弹药数量50%的弹药"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_A_JADE_JUE() {
    return collectible(
        "a_jade_jue",
        "“成玦”",
        "部署在化境地块上干员，首次被击倒时不撤退且回复30%生命",
        "Operators on Vagary Tiles defeated for the first time will recover 30% HP instead of retreating",
        "界园主人所赠玉佩。园中偶遇良友，一路同行，欲邀你去他处游玩，婉拒后其人嘴脸骤变，强行拉扯你后却如遭烫般缩手逃开。你松口气，发现腰间玉佩已裂了一块。",
        "A jade pendant gifted by the master of the Garden of Grotesqueries. A friend you happened to make and are traveling with invites you to explore elsewhere, but you politely decline, and his face blinks into a different expression. He tries to pull you by force, only to flinch and withdraw as if scalded by something. You breathe a sigh of relief, and discover the now-cracked pendant on your waist.",
        sourceRule("部署在化境地块上干员，首次被击倒时不撤退且回复30%生命"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_OLD_SANDAL() {
    return collectible(
        "old_sandal",
        "故履",
        "部署在化境地块上的召唤物，受到的物理法术伤害-50%，攻击力+50%",
        "Summons deployed on Vagary Tiles take -50% Physical and Arts damage, and gain +50% ATK",
        "桥下拾到的旧草鞋。桥上老者等着你将鞋归还，待上桥，却只看到另一只鞋。仔细想来，老者的面容似在某些古画中出现过……",
        "An old straw sandal found under a bridge. The old man atop the bridge was waiting for you to return it, but once you get back, all you see is the other sandal. You mull it over, and recall how you recognize the old man's face from a certain painting...",
        sourceRule("部署在化境地块上的召唤物，受到的物理法术伤害-50%，攻击力+50%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_YI_LOCK() {
    return collectible(
        "yi_lock",
        "易锁",
        "在化境地块上随机选择2个可部署位置，每次在该位置部署干员时，获得8点部署费用（在失与得中可以升级）",
        "Two random deployable Vagary Tiles will grant 8 DP when Operators are deployed on them (Can be upgraded in Lost and Found)",
        "易的发明之一，绩用此牟利颇多。既是摆件，又是益智玩具，若能弄清其中奥秘，或许对“岁”的理解能更多一分。",
        "One of Yi's inventions, used by Ji to rake in a fortune. Both an ornament and education toy, and if you can comprehend its secrets, perhaps you will understand 'Sui' much better.",
        sourceRule("在化境地块上随机选择2个可部署位置，每次在该位置部署干员时，获得8点部署费用（在失与得中可以升级）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_COMPLEX_YI_LOCK() {
    return collectible(
        "complex_yi_lock",
        "错易锁",
        "在化境地块上随机选择5个可部署位置，每次在该位置部署干员时，获得10点部署费用",
        "Five random deployable Vagary Tiles will grant 10 DP when Operators are deployed on them",
        "经发明者本人改造后的易锁。结构更复杂，嵌套之法似乎变得不循常理。然而，若是能了悟其中的奥秘，或许对“岁”的理解就能更进一步。",
        "A Yi lock, modified by its creator, now much more complicated. How the pieces lock in place no longer make much sense. However, grasp its secrets, and perhaps you will understand 'Sui' much, much better.",
        sourceRule("在化境地块上随机选择5个可部署位置，每次在该位置部署干员时，获得10点部署费用"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_MOTHER_CAULDRON() {
    return collectible(
        "mother_cauldron",
        "母鼎",
        "使所有部署在化境地块上的干员，攻击力+10%（在常乐中有机会升级，上限10次）",
        "Operators on Vagary Tiles gain +10% ATK (Can be upgraded in Leisure, to a max of 10 levels)",
        "九州万方，自用九鼎以代之。至于这些鼎是怎么铸造的……“当然是母鼎生的啊！”某位电影大师笑着说道。",
        "The nine provinces used nine cauldrons to represent them. As for how these cauldrons were even forged in the first place... 'The Mother Cauldron gave birth to them, duh!' Said a certain film master with a smile.",
        sourceRule("使所有部署在化境地块上的干员，攻击力+10%（在常乐中有机会升级，上限10次）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_DELICATE_BAMBOO() {
    return collectible(
        "delicate_bamboo",
        "嫰竹",
        "使所有部署在化境地块上的干员，生命值+10%（在常乐中有机会升级，上限10次）",
        "Operators on Vagary Tiles gain +10% HP (Can be upgraded in Leisure, to a max of 10 levels)",
        "从黍的宝田中种出的新竹，有果无因，循环往复，每循一遭便多一节，直至长出十节，首尾相连。",
        "A young bamboo shoot from Shu's treasured field. An effect sprouts with no cause, and grows a section every time the cycle repeats, until it has ten sections connected to one another, from start to end.",
        sourceRule("使所有部署在化境地块上的干员，生命值+10%（在常乐中有机会升级，上限10次）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_INTRO_TO_WEIQI_OPENINGS() {
    return collectible(
        "intro_to_weiqi_openings",
        "《围棋入门布局篇》",
        "战斗开始后，随机选择3个可部署的地面化境地块，部署“失修舞台雾机”",
        "3 Broken Fog Machines will randomly spawn on the map on Vagary Tiles",
        "方圆之间，此着为何？棋枰响止，酣畅淋漓。一局终了……时移世易。",
        "Why make that move within this space? The sound on the board ceases, a content game. The match is over... and times have changed.",
        sourceRule("战斗开始后，随机选择3个可部署的地面化境地块，部署“失修舞台雾机”"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BROCADE_BANNER() {
    return collectible(
        "brocade_banner",
        "飞锦战旌",
        "化境地块里的干员释放技能时，给其他所有化境地块里的干员回复1点技力",
        "Each time an Operator on a Vagary Tile uses a skill, all other Operators on Vagary Tiles recover 1 SP",
        "传说，曾为结束百氏之乱的那位真龙所用，战旗一挥，百将跟随……强大的从来不是旗帜，而是众人遏战乱、平天下之心。",
        "Legends say the banner was used by the True Lung who ended the Hundred Clan Rebellion. As it waved in the air, hundreds of generals followed its call... But what holds power is not the banner itself. Rather, it's the collective determination of the people to stop war and make peace.",
        sourceRule("化境地块里的干员释放技能时，给其他所有化境地块里的干员回复1点技力"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_DARK_RED_MASK() {
    return collectible(
        "dark_red_mask",
        "枣面",
        "所有干员受到来自自身阻挡单位的伤害降低50％，对被自身阻挡的敌人造成的伤害提高50%",
        "All Operators take 50% less damage from enemies blocked by them, and deal 50% more damage to the blocked enemies",
        "一位古代大炎英雄的脸谱。有记载称其忠心不二，为保结义金兰，千里一骑力克千军。",
        "The mask of an ancient Yanese hero. Records tell of his undying will so strong he even crossed a thousand li and vanquished a thousand foes to stay true to his sworn brotherhood.",
        sourceRule("所有干员受到来自自身阻挡单位的伤害降低50％，对被自身阻挡的敌人造成的伤害提高50%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_ELDER_MASK() {
    return collectible(
        "elder_mask",
        "老者面",
        "所有干员每秒对自身阻挡的敌人造成攻击力150%的法术伤害并减低目标15%的命中率",
        "All enemies blocked by an Operator take 150% of the Operator's ATK as Arts damage every second, and lose 15% hit rate",
        "一位上古大炎英雄的脸谱。天师一词的源流，传说其博学多闻，为炎氏谋千载太平后消失无踪。",
        "The mask of an ancient Yanese hero. This is where the term 'Tianshi' comes from, as his profound wisdom spread to the House of Yan, achieving a millennium of peace before vanishing into thin air.",
        sourceRule("所有干员每秒对自身阻挡的敌人造成攻击力150%的法术伤害并减低目标15%的命中率"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_CHARCOAL_MASK() {
    return collectible(
        "charcoal_mask",
        "炭面",
        "敌人首次被阻挡时，恐惧5秒",
        "Enemies blocked for the first time are inflicted with Fear for 5s",
        "一位古代大炎英雄的脸谱。有记载称其胆大心细，喝声可断江河。",
        "The mask of an ancient Yanese hero. Records tell of his courage and caution, and how his yells could split rivers into two.",
        sourceRule("敌人首次被阻挡时，恐惧5秒"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_MOON_LADY_MASK() {
    return collectible(
        "moon_lady_mask",
        "月女面",
        "干员每阻挡一名敌人，获得10%物理与法术闪避，最多叠加5层",
        "For every enemy blocked, Operators gain 10% Physical and Arts Dodge (Stacks up to 5 times)",
        "一位传说中的大炎人物的脸谱。其为寻夫泪洒成河飞身逐月的故事流传至今。",
        "The mask of a legendary Yanese character. The story of how she shed tears like a river in the search for her husband and flying to the moons has passed down the generations to the present day.",
        sourceRule("干员每阻挡一名敌人，获得10%物理与法术闪避，最多叠加5层"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_FIST_CLASSICS_123() {
    return collectible(
        "fist_classics_123",
        "《拳经三问》",
        "干员每使用过一次技能，自身的攻击力+5%，最多叠加40层",
        "Operators gain +5% ATK whenever they use a skill (Stacks up to 40 times)",
        "朔的第一本武学笔记，记录了他对拳法的思考。一问拳招，二问拳意，三问武境。据说言语晦涩，学生苦读不懂，他向妹妹请教后又做了改良。",
        "Shuo's first collection of his thoughts on Kung Fu. It discusses three aspects: first, fist techniques; second, meaning; and third, the pugilist realm itself. It is said the language used is so cryptic that his students could not understand it, so he asked his younger sister for revisions.",
        sourceRule("干员每使用过一次技能，自身的攻击力+5%，最多叠加40层"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_INK_OF_DAWN_AND_DUSK() {
    return collectible(
        "ink_of_dawn_and_dusk",
        "旦夕墨宝",
        "战斗开始时，随机获得一件仅用于该次战斗的战术道具",
        "When a battle begins, obtain a random Tactical Prop for that battle",
        "鳞在画卷中游动时吐出的一个又一个泡泡，会带来一些奇异之效。画卷主人不在乎有人擅自触碰它们，反正过不了多久，它们就会破裂，随风消散。",
        "Bubbles blown by the fin swimming within this scroll grant strange effects. The scroll's owner doesn't mind if they're touched, for they'll soon burst and vanish into the wind anyway.",
        sourceRule("战斗开始时，随机获得一件仅用于该次战斗的战术道具"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_CHAMBER_OF_GEISTS_LEDGER() {
    return collectible(
        "chamber_of_geists_ledger",
        "伥物房账簿",
        "战术道具，召唤物，装置的部署费用减10",
        "The DP Cost of Tactical Props, Summons, and Devices is reduced by 10",
        "柳儿热情邀请你“试用”伥物房的新发明，你以囊中羞涩为由婉拒，却看到她爽快地在账簿上划拉了几笔。现在，你的理由不成立了。",
        "Liu'er excitedly invites you to 'try' the Chamber of Geists' newest invention. You decline, citing your tight purse strings, but then you spot her casually scribbling on a ledger. Now, your excuse no longer stands.",
        sourceRule("战术道具，召唤物，装置的部署费用减10"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_TRAINING_LIGHTNING_ROD() {
    return collectible(
        "training_lightning_rod",
        "修习雷针",
        "干员每使用过一次技能，自身的技力自然回复速度+0.4/秒，最多叠加5层",
        "Operators gain +0.4/s SP recovery rate whenever they use a skill (Stacks up to 5 times)",
        "位于天师府山巅的修习雷法之所。雷法学徒初学引雷之时，都需借助雷针，直至熟能生巧。在天师府别名“前辈”。",
        "Located on the Tianshi Bureau's mountain peak where Tianshis train their Lei Fa. Apprentices always start with the lightning rod until they're familiar with manipulating thunder. Within the Bureau, it also goes by 'Senior'.",
        sourceRule("干员每使用过一次技能，自身的技力自然回复速度+0.4/秒，最多叠加5层"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_FIRE_POKING_STICK() {
    return collectible(
        "fire_poking_stick",
        "烧火棍",
        "所有干员对【化物】造成伤害时，额外造成一次相当于攻击力50%的法术伤害",
        "Operators' attacks on Apparition enemies deal additional Arts damage equal to 50% ATK",
        "老天师提着这根木棍扫邪魔破巨兽所向披靡。但大多数时候，它都静静靠在厨房一角，只有给炉灶添柴拨火时才会被拿起。",
        "The Old Tianshi uses this wooden stick to clean up wicked spirits and overpower Feranmuts, but it spends most days quietly leaning on a kitchen corner wall, only getting picked up to stoke the flames.",
        sourceRule("所有干员对【化物】造成伤害时，额外造成一次相当于攻击力50%的法术伤害"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_IGNITED_BEASTKITE() {
    return collectible(
        "ignited_beastkite",
        "引火兽筝",
        "每次进入岁兽残识时获得1点烛火",
        "Obtain 1 Candle when entering Sui's Remnant Consciousness",
        "参照巨兽造型设计制作的风筝。飘带用烛火点燃，不灭，也不毁风筝。平日里放飞，在步入岁识时可借其火光，多行一步。",
        "A kite based off a Feranmut. Candles set the ribbons alight, and it always burns, but the kite stays intact. Fly it on a regular day, and borrow its light to gain an extra step when you step into the Sui's consciousness.",
        sourceRule("每次进入岁兽残识时获得1点烛火"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_INTERDEPENDENT_LUCK() {
    return collectible(
        "interdependent_luck",
        "“福祸相依”",
        "钱盒中加入厉钱时，获得1张票券",
        "Obtain 1 Coupon when a Risk Coin is added to Coin Coffer",
        "如在界园中遇到半行诗，须迅速将此桃木挂饰按压其上。小灾已不可免，然而，祸兮福之所倚。切勿对诗。切勿对诗。切勿对诗。",
        "If you encounter a half-completed poem inside the Garden of Grotesqueries, press this peach wood ornament over it. Minor disasters may be inevitable, but misfortune and fortune are interdependent. Never respond or complete a poem, EVER.",
        sourceRule("钱盒中加入厉钱时，获得1张票券"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_FAMILY_STIR_FRY() {
    return collectible(
        "family_stir_fry",
        "家常小炒",
        "所有干员的生命值+20%，攻击力+20%；每通过一场岁兽残识中的战斗，效果额外提升10%",
        "All Operators gain +20% HP and ATK; every Sui's Remnant Consciousness battle cleared increases effects by 10%",
        "天为盖，地为锅，嬉笑怒骂，皆为调味。人哪，只要愿意好好吃饭，也就还能好好活着。",
        "Sky as lid, land as pot, with emotions of all variations sprinkled as seasoning on top. Humanity needs only eat well to live well.",
        partialRule("所有干员的生命值+20%，攻击力+20%；每通过一场岁兽残识中的战斗，效果额外提升10%", statSet(stats -> stats.multiplyMaxHealth(0.2), stats -> stats.multiplyAttack(0.2))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_RHODES_ISLAND_EMERGENCY_RESCUE_VEHICLE() {
    return collectible(
        "rhodes_island_emergency_rescue_vehicle",
        "罗德岛应急救援车",
        "希望+2，在岁兽残识战斗时，获得1名随机的罗德岛精英干员支援",
        "+2 Hope; Receive aid from a random Rhodes Island Elite Operator when fighting in Sui's Remnant Consciousness",
        "无论你的指令是去往何处——精英干员，使命必达！",
        "No matter where your orders may send them—Elite Operators are sure to succeed!",
        partialRule("希望+2，在岁兽残识战斗时，获得1名随机的罗德岛精英干员支援", stats -> stats.hope(2)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_WOODEN_LUMP() {
    return collectible(
        "wooden_lump",
        "木疙瘩",
        "下一次进入岁兽残识时，烛火+2；完成所有岁兽残识中的拾遗和传说节点后，获得10票券",
        "On next visit to Sui's Remnant Consciousness, +2 Candles; Gain 10 Coupons after clearing all Retrieve and Legend nodes",
        "平平无奇的伥物，据柳儿所说，乃是东海之滨收研的珍物。但除了木质温润，有股提神清香外，似乎并没有什么特别的……",
        "An ordinary geist object, which, as Liu'er says, is a treasure she found on the shores of the eastern. But apart from the warm wooden texture and refreshing, energizing fragrance, there doesn't seem to be anything special about it...",
        sourceRule("下一次进入岁兽残识时，烛火+2；完成所有岁兽残识中的拾遗和传说节点后，获得10票券"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_QUICKBEAST_TRANSPORT() {
    return collectible(
        "quickbeast_transport",
        "飞驮客运",
        "编队中每有一名【伺烛客】，所有【伺烛客】攻击速度+5（最大叠加10层）",
        "For every Candleguest in your squad, all Candleguests gain +5 ASPD (Stacks up to 10 times)",
        "炎国驰道初开时风靡一时的畜力载具。随着驰道普及，更高速耐久的载具兴起，驰道上飞奔的驮兽身影逐渐消失，随时可能被甩上大道的刺激乘车体验也慢慢被人们遗忘。",
        "Burdenbeast-drawn vehicles were popular when Yan's Chidao first opened. As the Chidao grew, faster and sturdier vehicles emerged, causing the once-common sight of galloping burdenbeasts to gradually disappear. The thrilling chance of being thrown to the ground at any moment was also slowly forgotten by the masses.",
        sourceRule("编队中每有一名【伺烛客】，所有【伺烛客】攻击速度+5（最大叠加10层）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BIG_BOB_S_CERTIFICATE() {
    return collectible(
        "big_bob_s_certificate",
        "鲍老板的凭证",
        "每有3源石锭，初始的部署费用+1",
        "For every 3 Ingots owned, +1 Starting DP",
        "“鲍老板正盯着你。”",
        "'BIG BOB IS WATCHING YOU.'",
        sourceRule("每有3源石锭，初始的部署费用+1"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_ROUND_WOODWHEEL() {
    return collectible(
        "round_woodwheel",
        "圆木轮",
        "初始费用+5，编队里每有一名【先锋】，初始费用额外+10",
        "+5 Starting DP, with additional +10 for every Vanguard in Squad",
        "群山中翻滚而下的圆石砸碎了天灾形成的晶簇，也第一次给予了人们快速移动的灵感。",
        "Round boulders rolling down from the mounts shattered the Catastrophe-formed Originium clusters, providing people with their first inspiration for rapid movement.",
        sourceRule("初始费用+5，编队里每有一名【先锋】，初始费用额外+10"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_DUO_CARRIER_SEDAN() {
    return collectible(
        "duo_carrier_sedan",
        "二抬肩舆",
        "部署费用上限+15，所有【先锋】攻击力+25%，部署时，返还扣除的所有费用",
        "+15 cost limit; All Vanguard Operators gain +25% ATK and refund DP when deployed",
        "一张椅子两根棍，将人分了个上下高低。如今，轿子已大多进了博物馆，可多少人心中还想着由人抬起，高高在上呢？",
        "A chair and two rods separate people into two classes. Today, most sedan chairs are homed in museums, but how many people hold a desire within to be raised by others, overlooking from above?",
        sourceRule("部署费用上限+15，所有【先锋】攻击力+25%，部署时，返还扣除的所有费用"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SCREECHING_BLOWGUN() {
    return collectible(
        "screeching_blowgun",
        "鸣镝吹筒",
        "干员损失30%、60%、90%的生命值时（也即最多可触发3次；被击倒时若仍有剩余可触发次数，立即触发1次），对攻击范围内的所有敌人造成一次10秒的恐惧",
        "When an Operator loses 30% HP, inflicts Fear on all enemies within Attack Range for 10s",
        "用于发讯与示警的吹箭筒，若是沾了血，鸣镝发射时啸叫声震耳欲聋，凄烈无比，令人下意识想要逃离。",
        "If blood gets into this blowgun meant for sounding signals and warnings, the screeching, deafening sound it makes when fired sends anyone who hears it running at once.",
        sourceRule("干员损失30%、60%、90%的生命值时（也即最多可触发3次；被击倒时若仍有剩余可触发次数，立即触发1次），对攻击范围内的所有敌人造成一次10秒的恐惧"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_STRANGE_PICKY_STATUE() {
    return collectible(
        "strange_picky_statue",
        "异食兽像",
        "投出3枚厉钱时，立即获得3点目标生命上限，3点护盾",
        "Whenever you toss 3 Risk Coins, gain 3 Max Life Points and 3 Objective Shield",
        "池中壳兽像，向它投币从未灵验。淘气顽童投以垃圾，石像张口便吞，顽童嚎啕大哭；石像复又张口，吐出漂亮珠子，顽童遂欢天喜地。",
        "A shellbeast inside a pond that never does anything when coins are thrown at it. A naughty child throws trash that gets swallowed by the statue, sending the child into tears. The statue then reopens its mouth to spit out pretty pearls, sending the child into high spirits.",
        sourceRule("投出3枚厉钱时，立即获得3点目标生命上限，3点护盾"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_VARIED_FORTUNE() {
    return collectible(
        "varied_fortune",
        "吉运有三",
        "投出3枚花钱时，立即获得8源石锭，1希望",
        "Whenever you toss 3 Flower Coins, gain 8 Originium Ingots and 1 Hope",
        "朝闻羽啼天似画，春嗅桂花云翻浪，冬起南风霜似花。",
        "At dawn fowlbeasts cry, the sky a painting. Spring comes, and clouds cover sweet osmanthus. South wind blows winter, frost turned to flowers.",
        sourceRule("投出3枚花钱时，立即获得8源石锭，1希望"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BREWED_REALM() {
    return collectible(
        "brewed_realm",
        "酿山河",
        "钱盒内每存在一枚花钱，所有干员获得+10%攻击力，+20%生命值",
        "For every Flower Coin in Coffer, all Operators +10% ATK and +20% HP",
        "劣酒琼浆，皆取一瓢。奔河飞瀑，尽入葫中。葫中自有天地转，经年再品滋味殊。",
        "Cheap wine, fine liquor, all fine as long as you drink a ladle. Rush like a river, right into the gourd. A whole realm within, its taste's truly unique.",
        sourceRule("钱盒内每存在一枚花钱，所有干员获得+10%攻击力，+20%生命值"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_REMNANT_FLAMES_AND_DREAMS() {
    return collectible(
        "remnant_flames_and_dreams",
        "旧烽遗梦",
        "当前每投出一枚厉钱，战斗获得的指挥经验+15%",
        "Gain +15% Command EXP from battles for every Risk coin currently tossed",
        "惊雷骤雨，战嚣冲天。金戈铁骑，踏梦而来。",
        "Thunder bellows a sudden storm as war drums into the sky. Golden infantry rush forth from your dreams.",
        sourceRule("当前每投出一枚厉钱，战斗获得的指挥经验+15%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_METAL_EXPEDITION_DRUM() {
    return collectible(
        "metal_expedition_drum",
        "铿金征鼓",
        "每次投钱，所有干员攻击力+15％（最多叠加10次），若只投掷出厉钱，则额外获得1次叠加次数",
        "Operators gain +15% ATK with every coin toss (Stacks up to 10 times); if only Risk Coins tossed, adds an extra stack",
        "军中一员“老将”。曾于阵上传令，历风霜，见胜败。如今，它已被更先进的传令方式替下，但战士出征前仍可闻征鼓隆隆，提振士气。",
        "A 'veteran' of the army. Once a messenger on the battlefield, it weathered hardships as it witnessed victory and defeat. Today more advanced communication methods have replaced it, but soldiers can still hear the morale-boosting drum beats before they head into battle.",
        sourceRule("每次投钱，所有干员攻击力+15％（最多叠加10次），若只投掷出厉钱，则额外获得1次叠加次数"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_PLUCKED_BALANCE() {
    return collectible(
        "plucked_balance",
        "“摘衡”",
        "所有【近战】干员，生命值+35%，攻击力+35%",
        "Melee Operators gain +35% HP and +35% ATK",
        "双符相合，故人昔事几何？",
        "When both signs come together, what old tale do they tell?",
        sourceRule("所有【近战】干员，生命值+35%，攻击力+35%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_PLUCKED_FLOWER() {
    return collectible(
        "plucked_flower",
        "“摘花”",
        "所有【近战】干员，部署费用+3，阻挡数+1，防御力+50%",
        "Melee Operators have +3 DP Cost, but gain +1 Block and +50% DEF",
        "双符相合，故人昔事几何？",
        "When both signs come together, what old tale do they tell?",
        sourceRule("所有【近战】干员，部署费用+3，阻挡数+1，防御力+50%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_PLUCKED_RISK() {
    return collectible(
        "plucked_risk",
        "“摘厉”",
        "所有【近战】干员部署后每秒恢复相当于最大生命值5%的生命值，持续20秒",
        "All Melee Operators restore HP equal to 5% of their Max HP every second after deployment for 20s",
        "双符相合，故人昔事几何？",
        "When both signs come together, what old tale do they tell?",
        sourceRule("所有【近战】干员部署后每秒恢复相当于最大生命值5%的生命值，持续20秒"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_THROWN_BALANCE() {
    return collectible(
        "thrown_balance",
        "“掷衡”",
        "所有【远程】干员，生命值+25%，攻击力+25%",
        "All Ranged Operators gain +25% HP and +25% ATK",
        "双符相合，故人昔事几何？",
        "When both signs come together, what old tale do they tell?",
        sourceRule("所有【远程】干员，生命值+25%，攻击力+25%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_THROWN_FLOWER() {
    return collectible(
        "thrown_flower",
        "“掷花”",
        "所有【远程】干员部署费用+2，部署时获得2层护盾",
        "All Ranged Operators +2 DP Cost, but gain 2 layers of Shield when deployed",
        "双符相合，故人昔事几何？",
        "When both signs come together, what old tale do they tell?",
        sourceRule("所有【远程】干员部署费用+2，部署时获得2层护盾"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_THROWN_RISK() {
    return collectible(
        "thrown_risk",
        "“掷厉”",
        "所有【远程】干员获得可抵抗状态生效时间倍率-0.5（非抵抗），法术闪避+15%",
        "All Ranged Operators gain status resistance and 15% Arts Dodge",
        "双符相合，故人昔事几何？",
        "When both signs come together, what old tale do they tell?",
        sourceRule("所有【远程】干员获得可抵抗状态生效时间倍率-0.5（非抵抗），法术闪避+15%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SEEN_BALANCE() {
    return collectible(
        "seen_balance",
        "“见衡”",
        "敌人进入失衡状态后，防御力，法术抗性-30%，持续10秒",
        "When enemies become Weightless, -30% DEF and RES for 10s",
        "双符相合，故人昔事几何？",
        "When both signs come together, what old tale do they tell?",
        sourceRule("敌人进入失衡状态后，防御力，法术抗性-30%，持续10秒"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SEEN_FLOWER() {
    return collectible(
        "seen_flower",
        "“见花”",
        "干员每次释放技能时，获得相当于最大生命值10%的屏障，持续10秒（不可叠加）",
        "On skill activation, Operators gain a Barrier equal to 10% of their Max HP that lasts for 10s (Does not stack)",
        "双符相合，故人昔事几何？",
        "When both signs come together, what old tale do they tell?",
        sourceRule("干员每次释放技能时，获得相当于最大生命值10%的屏障，持续10秒（不可叠加）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SEEN_RISK() {
    return collectible(
        "seen_risk",
        "“见厉”",
        "我方干员对【化物】敌人造成的物理和法术伤害提高50%",
        "Allied Operators deal 50% more Physical and Arts damage to Apparition enemies",
        "双符相合，故人昔事几何？",
        "When both signs come together, what old tale do they tell?",
        sourceRule("我方干员对【化物】敌人造成的物理和法术伤害提高50%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SUI_S_BALANCE() {
    return collectible(
        "sui_s_balance",
        "“岁衡”",
        "【伺烛客】的攻击力+50%，防御力+50%",
        "Candleguest Operators gain +50% ATK and DEF",
        "双符相合，故人昔事几何？",
        "When both signs come together, what old tale do they tell?",
        sourceRule("【伺烛客】的攻击力+50%，防御力+50%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SUI_S_FLOWER() {
    return collectible(
        "sui_s_flower",
        "“岁花”",
        "所有干员的部署费用+3，编队中每有一名【伺烛客】，则【伺烛客】的生命值，攻击力，防御力+10%",
        "All Operators have +3 DP Cost, but Candleguest Operators gain +10% HP, ATK, and DEF for every Candleguest in the Squad",
        "双符相合，故人昔事几何？",
        "When both signs come together, what old tale do they tell?",
        sourceRule("所有干员的部署费用+3，编队中每有一名【伺烛客】，则【伺烛客】的生命值，攻击力，防御力+10%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SUI_S_RISK() {
    return collectible(
        "sui_s_risk",
        "“岁厉”",
        "【伺烛客】部署后10秒内攻击力+100%，每有干员通过作战成为【伺烛客】时，持续时间+5秒(最多提升15次)",
        "Candleguests gain 100% ATK for 10s after deployment, duration increased by 5s for every Candleguest promoted via combat (Can be increased up to 15 times)",
        "双符相合，故人昔事几何？",
        "When both signs come together, what old tale do they tell?",
        sourceRule("【伺烛客】部署后10秒内攻击力+100%，每有干员通过作战成为【伺烛客】时，持续时间+5秒(最多提升15次)"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_INK_OF_THE_HIDDEN_BUTTERFLY() {
    return collectible(
        "ink_of_the_hidden_butterfly",
        "隐蝶墨",
        "携带此收藏品时，作战会遭遇“岁躯”的袭击",
        "When carrying this Collectible, 'Body of Sui' will raid your party in battles",
        "柳儿在研究的伥物，采朔之武籍，研岁陵残石而成。本为雕岁乌墨，岁形已逸，以蝶替之，然蝶畏岁威，需驱岁以安蝶。",
        "A creature researched by Liu'er, made from remnant rock of the Sui Tomb and martial tomes panned by Shuo. Originally Sui was carved on it, but Its form has faded, and now a butterfly is carved to take Its place. But the butterfly fears the Sui's might, so it is time to repel the Sui to appease it.",
        sourceRule("携带此收藏品时，作战会遭遇“岁躯”的袭击"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_CARVED_INKSTICK_REMNANT() {
    return collectible(
        "carved_inkstick_remnant",
        "墨化残碑",
        "战斗开始时，随机选择一名干员，使该干员本场战斗的部署费用变为0，携带此收藏品时，可以在命运所指中让探索开启不同的方向",
        "Every battle, a random Operator will have their DP Cost reduced to 0. When carrying this Collectible, can choose to lead expedition in a different direction in Prophecy",
        "蝶驻墨端，“文字”于层层包裹之下显现。失佚的部分已不可明辨，但依字形与偏旁推测，这是一份对于亲属的遥思。",
        "A butterfly rests on the edge of the inkstick, and 'words' emerge from its multitude of layers. The missing sections are no longer readable, but the radicals and shapes of the other characters imply longing for a relative.",
        sourceRule("战斗开始时，随机选择一名干员，使该干员本场战斗的部署费用变为0，携带此收藏品时，可以在命运所指中让探索开启不同的方向"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_CLOUD_AND_PAINT() {
    return collectible(
        "cloud_and_paint",
        "云与漆",
        "干员部署后60s内，每秒受到最大生命值2%的真实伤害，让探索开启不同的方向",
        "Operators lose 2% of Max HP for 60 seconds on deployment; Leads expedition in a different direction",
        "人有是非对错，棋有正反黑白，但见得分明与否，巨兽并不在意。就算明谋黑字改换天地，你也仍需得岁意，才可入岁陵。",
        "There are right and wrong moralities just as weiqi stones has black and white, but the Feranmut hardly cares. Change the realm with a cunning plan typed on paper, but you still require the Sui's will to enter the Tomb of Sui.",
        sourceRule("干员部署后60s内，每秒受到最大生命值2%的真实伤害，让探索开启不同的方向"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_UNFLINCHING_MASTERPLAN() {
    return collectible(
        "unflinching_masterplan",
        "忘生珍珑",
        "战斗开始999秒内，干员部署后损失50%的技力值；携带此收藏品时，指点迷津中会出现一场特殊的作战",
        "Operators lose 50% SP on deployment; When carrying this Collectible, a special Operation option will appear in Pathfinder",
        "一方木料，正面为棋盘，背面则为活字盘，互相倚靠，似无缝隙。活字无法印下内容，只因一枚刻有“吉页”的活字凸出，怎么都摁不下去。",
        "A square piece of wood, boasting a weiqi board on the top and a movable type plate on the back, perfectly supporting one another. The type cannot be used, for a block with 'Ji' and 'Ye' carved can be seen protruding, making it impossible for the frame to be pressed down.",
        sourceRule("战斗开始999秒内，干员部署后损失50%的技力值；携带此收藏品时，指点迷津中会出现一场特殊的作战"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_UNSEALED_CASE() {
    return collectible(
        "unsealed_case",
        "无封长盒",
        "领袖与精英敌人防御力+30%，攻击速度+20，关卡结束时每有一个仍在场的雕伥，失去1目标生命值（不会低于1），在先行一步中有奇妙的作用",
        "Leader and Elite enemies gain +30% DEF and +20 ASPD; every Statuegeist remaining on the field when the stage ends will deplete 1 Life Point (min 1); wondrous effects in Scout nodes",
        "易流落于岁兽残识中的藏盒。失名失绘，无刻无印。",
        "A box Yi left in the Sui's Remnant Consciousness. Name and decorations lost, bereft of carvings or markings.",
        sourceRule("领袖与精英敌人防御力+30%，攻击速度+20，关卡结束时每有一个仍在场的雕伥，失去1目标生命值（不会低于1），在先行一步中有奇妙的作用"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_INKLESS_SCROLL() {
    return collectible(
        "inkless_scroll",
        "无墨长卷",
        "希望+3，使见字祠中的险路恶敌更加艰难，让探索开启不同的方向",
        "+3 Hope, Dreadful Foe nodes in Jianzi Shrine will be more difficult; leads expedition in a different direction",
        "人与岁兽共同的记忆将藏盒开启，长卷远探，寻找能在这方素白上着墨的往昔。",
        "Humanity and Sui opened the box with their shared memories, and the long scroll searches for traces of where ink could have been.",
        partialRule("希望+3，使见字祠中的险路恶敌更加艰难，让探索开启不同的方向", stats -> stats.hope(3)),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_MEMORY_SCOPE() {
    return collectible(
        "memory_scope",
        "追忆仪",
        "获得时，失去所有的源石锭及希望，并使战斗后获得的指挥等级经验和源石锭-50％。在误入奇境有特殊用处",
        "When obtained, lose all Originium Ingots and Hope, and gain -50% Command Level EXP and Originium Ingots from battle. Special function in Wander into Wonderland",
        "由岁兽代理人创造，用以应对岁兽的造物，会在几无尽头的旋转中，剥露出其苏醒的一切可能性以供望提前谋划。",
        "Created by Sui proxies for the purpose of fighting the bestial Sui. In its endless spinning, the myriad possibilities of Its awakening are revealed, allowing Wang to plan ahead.",
        sourceRule("获得时，失去所有的源石锭及希望，并使战斗后获得的指挥等级经验和源石锭-50％。在误入奇境有特殊用处"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_LITTLE_MOJI() {
    return collectible(
        "little_moji",
        "“小磨唧”",
        "费用自然回复速度降低50％，让探索开启不同的方向。在故肆有特殊用处",
        "Reduce natural DP regeneration rate by 50%, leads expedition in a different direction. Special function in Tavern",
        "被岁兽代理人共同用记忆喂养起来的墨伥，动作不太麻利，却在无数可能性中从旁协助压制岁将醒的意识。",
        "Inkgeist fed with the Sui proxies' memories. Its movements are not particularly swift, but it assists with suppressing Sui's awakening consciousness in the infinite possibilities.",
        sourceRule("费用自然回复速度降低50％，让探索开启不同的方向。在故肆有特殊用处"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_MERCILESS() {
    return collectible(
        "merciless",
        "不赦",
        "所有敌方单位的生命值、防御力+50％，使“宿傲”获得召唤额外敌人的能力，部分“身形”获得持续召唤敌人的能力",
        "All enemies HP and DEF +50%; 'Olden Pride' and some 'Body' segments gain ability to continuously summon enemies",
        "描绘岁兽时诞生的奇物。即使尚未苏醒，岁兽也不会任由别人践踏祂的身躯，躯体的反射正在搜寻一切变化的预兆。",
        "Strange object created when depicting the bestial Sui. Sui will not allow others to trample upon Its vessel, even if It has yet to awaken. Its body instinctively searches for premonitions of change.",
        partialRule("所有敌方单位的生命值、防御力+50％，使“宿傲”获得召唤额外敌人的能力，部分“身形”获得持续召唤敌人的能力", stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> enemyStats.multiplyMaxHealth(0.5).multiplyDefense(0.5))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_CEASELESS() {
    return collectible(
        "ceaseless",
        "不息",
        "所有敌方单位的攻击力+30％；【伺烛客】在作战时以及【伺烛客】的召唤物在非岁兽残识作战时攻击力、生命值-0％，从第三层开始，每进入新的一层（包括第三层），额外-10％。进入“来去处”时，额外-50％",
        "All enemies ATK +30%; [Candleguests] and their summons -10% ATK and HP when fighting outside Sui's Remnant Consciousness, plus extra -10% upon entering a new floor, and extra -50% when entering 'Laiqu Point'",
        "描绘岁兽时诞生的奇物。即使尚未苏醒，岁兽也不会任由别人翻动祂的记忆与思维，知觉的延伸正在强硬地阻止一切变动。",
        "Strange object created when depicting the bestial Sui. Sui will not allow others to peruse Its memories and thoughts, even if It has yet to awaken. The extension of sensation staunchly obstructs all change.",
        partialRule("所有敌方单位的攻击力+30％；【伺烛客】在作战时以及【伺烛客】的召唤物在非岁兽残识作战时攻击力、生命值-0％，从第三层开始，每进入新的一层（包括第三层），额外-10％。进入“来去处”时，额外-50％", stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> enemyStats.multiplyAttack(0.3))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_GALLOPBEAST_CHARIOT() {
    return collectible(
        "gallopbeast_chariot",
        "奔兽战车",
        "部署费用上限+30，部署费用为99及以上时，所有我方单位生命值+50%，阻挡数+1",
        "+30 cost limit, when DP is at 99 or higher, all units gain +50% HP and +1 Block",
        "一个箱子两个轮，牵起四匹奔兽，其效率当然要远超两条腿走路的人类——吗？看看可汗的梦魇精兵吧。兽拉战车就算用十六条腿竭力奔驰，也追不上泰拉变化的速度。",
        "A box with two wheels pulled by four gallopbeasts is surely faster than mortals walking on their own two legs—Is that so? Just look at the Khagan's Nightzmora troops. A chariot traveling at full speed still cannot keep up with how fast Terra develops.",
        sourceRule("部署费用上限+30，部署费用为99及以上时，所有我方单位生命值+50%，阻挡数+1"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_RAMPART_BURDENBEAST() {
    return collectible(
        "rampart_burdenbeast",
        "驮垒",
        "部署费用上限+15，部署费用为99及以上时，保护目标点周围12格的敌方单位失去隐匿并且命中率下降35%",
        "+15 cost limit, when DP is at 99 or higher, enemies on the surrounding 12 tiles around an Objective Point lose Invisibility and have -35% hit rate",
        "矫健的勇士四散而开，拱卫着聚在驮兽背上的人们，向着天灾未至的乐土而去。这便是最初的“移动城市”。",
        "Stalwart warriors spread out and surround people gathered on burdenbeast backs as they journey toward a paradise free from any Catastrophes. This was the first 'nomadic city'.",
        sourceRule("部署费用上限+15，部署费用为99及以上时，保护目标点周围12格的敌方单位失去隐匿并且命中率下降35%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_LITTLE_BAIZAO() {
    return collectible(
        "little_baizao",
        "“小百灶”",
        "干员部署时，对全场所有敌人造成自身部署费用一定比例的真实伤害；若部署费用不小于30，还会造成5秒的晕眩",
        "When an Operator is deployed, all enemies take True damage equivalent to a certain percentage of DP used; if 30 or more DP used, also inflicts Stun for 5s",
        "战争、源石与血泪的结晶。",
        "A crystallization of war, Originium, and suffering.",
        sourceRule("干员部署时，对全场所有敌人造成自身部署费用一定比例的真实伤害；若部署费用不小于30，还会造成5秒的晕眩"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_CANGTONG() {
    return collectible(
        "cangtong",
        "“苍桐”",
        "部署费用为99及以上时，每隔10秒对全场敌人造成一次3000点的法术伤害和2秒的晕眩",
        "When DP reaches 99 or higher, all enemies take 3000 Arts damage and are Stunned for 2s every 10s",
        "麟青砚的配剑，出鞘不吟，藏巧守拙如木。若披覆苍霆，必是破风掠火，瞬息即至。",
        "Lin Qingyan's sword makes no sound when unsheathed, its branch-like appearance concealing its true form. Drape thunder over, and it will surely cleave through wind and blaze, reaching its target in an flash.",
        sourceRule("部署费用为99及以上时，每隔10秒对全场敌人造成一次3000点的法术伤害和2秒的晕眩"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_COIN_GEISTS() {
    return collectible(
        "coin_geists",
        "一串钱伥",
        "战斗开始时，打乱所有干员的部署费用",
        "When the battle begins, randomize all allied Operators' Deployment Cost",
        "八钱一体，成伥后却各有想法。即使抓住串起它们的线，也并不老实。",
        "Eight coins turned into one geist, but they retain their own minds. Even when one grabs the thread binding them together, they may stay defiant.",
        sourceRule("战斗开始时，打乱所有干员的部署费用"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_FOUR_CORNERS_PAINT() {
    return collectible(
        "four_corners_paint",
        "四方绘料",
        "每场战斗仅一次，已部署4名干员时，下一名部署的干员获得生命值+50%，防御力+50%，阻挡数+5",
        "For one time per battle, an Operator deployed with 4 Operators already on the field will gain +50% HP, +50% DEF, and +5 Block",
        "夕亲自调制，世所罕见的颜料，同时也是她的印信。蘸染时，四色置单景。",
        "Dusk's special mix of pigments that also serves as her seal. When a brush is dipped into the palette, the four colors fuse and form into a scene.",
        sourceRule("每场战斗仅一次，已部署4名干员时，下一名部署的干员获得生命值+50%，防御力+50%，阻挡数+5"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FOUR_SEASONS_BRUSH() {
    return collectible(
        "four_seasons_brush",
        "四时丹青毫",
        "每场战斗仅一次，已部署4名干员时，下一名部署的干员获得可抵抗状态生效时间倍率-0.5（非抵抗），攻击力+50%，受到的元素损伤降低50%",
        "For one time per battle, an Operator deployed with 4 Operators already on the field will gain status resistance, +50% ATK, and take 50% less Elemental Injury",
        "夕亲自制作，世所罕见的毛笔，同时也是她的印信。下笔时，四时恒如一。",
        "Dusk's specially created brush that also serves as her seal. When she puts brush to parchment, the four seasons stay as-is.",
        sourceRule("每场战斗仅一次，已部署4名干员时，下一名部署的干员获得可抵抗状态生效时间倍率-0.5（非抵抗），攻击力+50%，受到的元素损伤降低50%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SOLDIER_SHADOW() {
    return collectible(
        "soldier_shadow",
        "士影",
        "每次部署待部署区最右侧的干员时，使全场干员获得消耗费用一定比例的屏障",
        "Every time the rightmost Operator from the Deployment Waiting Zone is deployed, all deployed Operators gain a Barrier scaled to DP spent",
        "为纪念一位守城将士而制的皮影。传闻其在边防战中带数名士兵守城百日，城破之时，其屹立城头，随之没入黄沙。玉门城建成后，曾为其立像纪念。",
        "This shadow puppet was created to honor a soldier who defended the city. Legends say they led a party in a border defense for a hundred days, and when the city fell, they stood on its walls before the sands swallowed them up. When Yumen city was built, a commemorative state was also made.",
        sourceRule("每次部署待部署区最右侧的干员时，使全场干员获得消耗费用一定比例的屏障"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_CRAFTSMAN_SHADOW() {
    return collectible(
        "craftsman_shadow",
        "匠影",
        "每次部署待部署区最左侧的干员时，使全场干员获得消耗费用100%的技力",
        "Every time the leftmost Operator from the Deployment Waiting Zone is deployed, all deployed Operators gain 100% of the DP spent as SP",
        "为纪念一位巧手者而制的皮影。其一生追求技艺之极致。歹人垂涎其技，而其不为金钱所惑，亦不为皮肉苦所动，只望其技不染。后殁于贼穴，其著作亦付之一炬，未传后人。",
        "This shadow puppet was created to honor a skilled artisan who dedicated their life to pursuing perfection. Villains coveted their skills, but not even money or physical suffering made them waver, only hoping their skills would remain unscathed. They later died in a bandit's den, and their works set alight, never to be passed down to further generations.",
        sourceRule("每次部署待部署区最左侧的干员时，使全场干员获得消耗费用100%的技力"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ARTIST_SHADOW() {
    return collectible(
        "artist_shadow",
        "艺影",
        "进入战斗时，待部署区最左侧的干员初始技力+10，攻击速度+30，攻击力+30%",
        "The leftmost Operator in the Deployment Waiting Zone gains +10 Starting SP, +30 ASPD, and +30% ATK",
        "为纪念一位传奇舞者而制的皮影。传闻其舞于大江，舞出巨浪之势；舞于林间，舞出春芽之美。其一生为舞，年老后受伤隐退，不久便撒手人寰。无数舞者仿其姿而不得其意，可惜可叹。",
        "This shadow puppet was created to honor a legendary dancer. Legends tell of summoning mighty waves out of a river, and the beauty of spring drawn from forests with their dances. They dedicated their life to dance, but with age became injured and retired, leaving the mortal realm soon after. Countless dancers have copied their movements, but none have quite made it. What a shame.",
        sourceRule("进入战斗时，待部署区最左侧的干员初始技力+10，攻击速度+30，攻击力+30%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_MERCHANT_SHADOW() {
    return collectible(
        "merchant_shadow",
        "商影",
        "进入战斗时，待部署区最右侧的干员阻挡数+2，防御力+50%，最大生命值+50%，部署后返回50%的部署费用",
        "The rightmost Operator in the Deployment Waiting Zone gains +2 Block, +50% DEF, +50% Max HP, and refunds half the DP spent after deployment",
        "为纪念一位多财善贾之商人而制的皮影。其曾召集一支民间车队，穿梭于天灾高发地带，于城乡间买卖，缓物资之急。待城间驰道最终修成之时，却故于意外。",
        "This shadow puppet was created to honor a legendary merchant. They once assembled a caravan of civilians to trade goods between cities in Catastrophe-prone areas, meeting the towns' urgent needs. When the Chidao was finally completed, they perished in an accident.",
        sourceRule("进入战斗时，待部署区最右侧的干员阻挡数+2，防御力+50%，最大生命值+50%，部署后返回50%的部署费用"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_DEEPWATER_TALISMAN() {
    return collectible(
        "deepwater_talisman",
        "临渊符",
        "界园中，每层进入的首个战斗节点额外掉落一张随机招募券，可留存的招募券数量+1",
        "In Garden of Grotesqueries, the first combat node on every floor will drop an extra Recruitment Voucher, Recruitment Voucher conservation limit +1",
        "出于对你的信任，司岁台通过了你携带更多干员入园的申请。只不过，溪鳞入陌河，需慎之又慎，这个道理他们希望新来者明白。",
        "The Sui Regulator trusts you, and approved your application to bring more Operators into the garden. Though, just as a creekfin enters a new river with caution, they hope newcomers will understand the importance of staying sharp.",
        sourceRule("界园中，每层进入的首个战斗节点额外掉落一张随机招募券，可留存的招募券数量+1"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_THREE_LEGGED_GOLDFOWL() {
    return collectible(
        "three_legged_goldfowl",
        "三足金雏",
        "获得时随机使持有的以下资源之一翻倍：源石锭、希望、护盾",
        "Upon acquisition, doubles one of the following resources randomly: Ingots, Hope, or Objective Shield",
        "异足的祥瑞，听说三只脚分别对应盛衰、生死、兴亡，哪一只脚先落在人身上，其人便可在那方面永享福泽。据司岁台的研究，这生物虽有羽兽的样貌和习性，但恐怕……",
        "The legs of this strange auspicious figure apparently refers to the two-sided coins of prosperity and decline, life and death, and flourish and decay; whichever leg touches a person first grants eternal blessing in that aspect. According to Sui Regulator research, while this creature appears to resemble a fowlbeast, it's unfortunately...",
        sourceRule("获得时随机使持有的以下资源之一翻倍：源石锭、希望、护盾"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_RAIDIAN_S_HANDHELD_CONSOLE() {
    return collectible(
        "raidian_s_handheld_console",
        "电弧的掌机",
        "场上每存在一名我方召唤物，使全体我方【召唤物】的攻击力+100，防御力+100（最高叠加10层）",
        "For every Summon on the field, all Summons gain +100 ATK and DEF (Stacks up to 10 times)",
        "塞入了许多精心制作的小游戏，在游戏主角的身上总能看到电弧日常操控的器械的影子，偏偏没有她自己。",
        "Stuffed with many carefully designed mini-games. You can always see a trace of the equipment Raidian uses daily in the protagonists, but no sign of her herself.",
        sourceRule("场上每存在一名我方召唤物，使全体我方【召唤物】的攻击力+100，防御力+100（最高叠加10层）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BELLS_OF_AGREEMENT() {
    return collectible(
        "bells_of_agreement",
        "契心聆铃",
        "每当有干员通过作战成为伺烛客，所有【伺烛客】攻击力+40%，部署后获得4点部署费用，再部署时间-4%（最多4层）",
        "For every Candleguest promoted via combat, all Candleguests +40% ATK; after deployment, -4% Redeployment Time and gain 4 DP (Stacks up to 4 times)",
        "新秉烛人在就任仪式后去面见各位前辈——悬在办公处的结绳檐铃，一环扣着一环，颜色手法各不相同——聆听那清越铃声后，秉烛人在那环环相扣的绳结上新编了一环。",
        "After the inauguration ceremony, the new Candleholder went to meet his seniors—bells woven together with rope, with different techniques and colors—and after listening to the crisp bell chimes, the Candleholder ties a new link.",
        sourceRule("每当有干员通过作战成为伺烛客，所有【伺烛客】攻击力+40%，部署后获得4点部署费用，再部署时间-4%（最多4层）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_TZU_WU_BLADE() {
    return collectible(
        "tzu_wu_blade",
        "子武剑",
        "使当前或下次进入的岁兽残识中所有祸乱节点变为可探索状态，我方所有干员再部署时间-5%，每完成一个祸乱节点叠加1层，最多6层",
        "Reveals all Turmoil nodes on the current or next Sui's Remnant Consciousness visit; grants -5% Redeployment Time to all Operators, and gains 1 stack for each completed Turmoil node, up to 6 stacks",
        "岁兽残识第一个时辰入髓后凝聚的实物，形为刀兵。以厮杀洗砺，便可见其锋锐的怒意。",
        "An object condensed from the first Acute Hour of Sui, shaped as a weapon. With combat as whetstone, one can see keen fury within.",
        sourceRule("使当前或下次进入的岁兽残识中所有祸乱节点变为可探索状态，我方所有干员再部署时间-5%，每完成一个祸乱节点叠加1层，最多6层"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_WU_SHANG_SHUTTLE() {
    return collectible(
        "wu_shang_shuttle",
        "午商梭",
        "使当前或下次进入的岁兽残识中所有易与节点变为可探索状态，易与节点中的商品打折，且在任意商店中购买当前所有商品后，该商店下次刷新不消耗票券",
        "Reveals all Trade nodes on the current or next Sui's Remnant Consciousness visit; Trade nodes will offer discounted goods, and buying all stock in a shop grants a free chance to refresh stock",
        "岁兽残识第七个时辰入髓后凝聚的实物，形为织梭。以沟通编织，便可见其层叠的愁思。",
        "An object condensed from the seventh Acute Hour of Sui, shaped as a shuttle. With links and weaves, one can see layered anxiety within.",
        sourceRule("使当前或下次进入的岁兽残识中所有易与节点变为可探索状态，易与节点中的商品打折，且在任意商店中购买当前所有商品后，该商店下次刷新不消耗票券"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SHEN_CHU_WHEEL() {
    return collectible(
        "shen_chu_wheel",
        "申铸轮",
        "希望+1，源石锭+3，使当前或下次进入的岁兽残识中所有常乐节点变为可探索状态，在部分常乐节点中有奇妙的作用",
        "+1 Hope, +3 Ingots, reveals all Leisure nodes on the current and next Sui's Remnant Consciousness visit; wondrous effects in some Leisure nodes",
        "岁兽残识第九个时辰入髓后凝聚的实物，形为铜轮。以好奇煅烧，便可见其脱逸的豁达。",
        "An object condensed from the ninth Acute Hour of Sui, shaped as a wheel. With curiosity as forge, one can see optimistic escapism within.",
        partialRule("希望+1，源石锭+3，使当前或下次进入的岁兽残识中所有常乐节点变为可探索状态，在部分常乐节点中有奇妙的作用", statSet(stats -> stats.hope(1), stats -> stats.originiumIngots(3))),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_HAI_SHIH_STOVE() {
    return collectible(
        "hai_shih_stove",
        "亥食灶",
        "在拾遗节点中有奇妙的作用，完成所有拾遗节点后获得2烛火，最多触发1次",
        "Wondrous effects in Retrieve nodes; +2 Candles after clearing all Retrieve nodes (only triggers once)",
        "岁兽残识第十二个时辰入髓后凝聚的实物，形为锅灶。岁兽种种，人间是非，同在一片大地，为何要泾渭分明？",
        "An object condensed from the twelfth Acute Hour of Sui, shaped as a stove. With myriad bestial Sui deeds and mortal disputes all on the same land, why differentiate between the two?",
        sourceRule("在拾遗节点中有奇妙的作用，完成所有拾遗节点后获得2烛火，最多触发1次"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_YIN_SHIH_FLASK() {
    return collectible(
        "yin_shih_flask",
        "寅诗壶",
        "希望+2，使当前或下次进入的岁兽残识中部分传说与抉择节点变为可探索状态，在部分传说节点中有奇妙的作用",
        "+2 Hope, reveals some Legend and Decision nodes on the current or next Sui's Remnant Consciousness visit; wondrous effects in some Legend nodes",
        "岁兽残识第三个时辰入髓后凝聚的实物，形为酒壶。以轶事佐酒，便可见其虚漫的逍遥。",
        "An object condensed from the third Acute Hour of Sui, shaped as a flask. With tales as wine, one can see unrestrained freedom within.",
        partialRule("希望+2，使当前或下次进入的岁兽残识中部分传说与抉择节点变为可探索状态，在部分传说节点中有奇妙的作用", stats -> stats.hope(2)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_WEI_CHIEN_RULE() {
    return collectible(
        "wei_chien_rule",
        "未建尺",
        "源石锭+6，在故肆节点中有奇妙的作用",
        "+6 Ingots; wondrous effects in Tavern nodes",
        "岁兽残识第八个时辰入髓后凝聚的实物，形为量尺。以认知构筑，便可见其滞塞的亲和。",
        "An object condensed from the eighth Acute Hour of Sui, shaped as a rule. With knowledge of construction, one can see obstructive amiability within.",
        partialRule("源石锭+6，在故肆节点中有奇妙的作用", stats -> stats.originiumIngots(6)),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_GREAT_TOMB_WHISTLE() {
    return collectible(
        "great_tomb_whistle",
        "镇陵哨",
        "本次离开商店时，立即进入岁兽残识并获得3烛火",
        "After leaving this shop, immediately enter Sui's Remnant Consciousness and gain +3 Candles",
        "镇陵木所做的哨子，吹响后意识便会随着气流一同离体，木哨则会化作柴薪，点燃稳定心神的烛火。",
        "A wood whistle crafted from the Great Tomb Tree. When blown, one's consciousness drifts away from the body, while the whistle turns into fuel to light a steady fire inside the mind.",
        sourceRule("本次离开商店时，立即进入岁兽残识并获得3烛火"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SMOKY_CANDLE() {
    return collectible(
        "smoky_candle",
        "烟烛",
        "为一名未燃烛的干员燃烛",
        "Turns an Operator into a Candleguest",
        "收集烛堂烟火后制作的灰烛，点燃便会消散，然而在那逝去烟云中所见的往事，将在某人的心中塑成一根不灭的心烛。",
        "An ash candle created from the Hall of Candles's fireworks. It dissipates when lit, but with the past seen in the fleeting smoke, it lights an eternal flame within one's heart.",
        sourceRule("为一名未燃烛的干员燃烛"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_BROKEN_WAND_ABSORB() {
    return collectible(
        "broken_wand_absorb",
        "断杖-汲取",
        "所有【术师】干员每对一个单位造成伤害就回复2点技力值",
        "Caster Operators recover 2 SP every time they deal damage to an enemy unit",
        "摄取失败者的养分，踏上恒久的胜利。",
        "Consume the defeated as nourishment, and embark on an eternal path to victory.",
        sourceRule("所有【术师】干员每对一个单位造成伤害就回复2点技力值"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_COURT_STRIKING_BLOCK() {
    return collectible(
        "court_striking_block",
        "惊堂木",
        "钱盒里每有一枚衡钱，所有我方干员生命值+10％（最多+150％）",
        "Allied Operators +10% HP for each Balance Coin in Coffer (max +150%)",
        "据说某位断案的官员常被胡搅蛮缠的犯人气得想砸东西，于是她的弟弟发明了这块木头，既能发泄不满，又能威慑公堂，甚妙。",
        "The brother of a certain judge made this piece of wood for her so that she would have something to smash when annoyed by obstinate criminals, allowing her to vent her frustration while also intimidating the courtroom.",
        sourceRule("钱盒里每有一枚衡钱，所有我方干员生命值+10％（最多+150％）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BLAZE_S_CHAINSAW() {
    return collectible(
        "blaze_s_chainsaw",
        "Blaze的电锯",
        "所有敌方单位受到我方单位伤害时，距离伤害来源越近受到的伤害越高（最高100%）",
        "Enemies take increased damage from allied units as distance is reduced (max 100%)",
        "她将自己与敌人一同拖入地狱，用炽热来鉴定彼此的决心。她从未失败。",
        "She drags herself into hell with her enemies, appraising each other's determination with heat. She has never failed.",
        sourceRule("所有敌方单位受到我方单位伤害时，距离伤害来源越近受到的伤害越高（最高100%）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SAWBLADE_BANGLE() {
    return collectible(
        "sawblade_bangle",
        "手镯轮",
        "我方干员阻挡敌人时，攻击力+50％",
        "Operators have ATK +50% when blocking",
        "短兵相接，出奇制胜。",
        "Surprise is the key to victory in close quarters.",
        sourceRule("我方干员阻挡敌人时，攻击力+50％"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_OLD_MILLSTONE() {
    return collectible(
        "old_millstone",
        "老磨盘",
        "我方单位受到伤害后，攻击速度+6（最多叠加10次）",
        "Operators +6 ASPD after taking damage (Stacks 10x)",
        "拉磨的兽总挨鞭子，推磨的人总受责骂，结果磨盘是最先崩溃的那个，此后，它但凡听见声响，就会自顾自地转起来。",
        "The beast that pulled the millstone was whipped, the man who pushed the millstone was berated, but it was the millstone that broke first. Now it turns by itself, whenever it hears a sound.",
        sourceRule("我方单位受到伤害后，攻击速度+6（最多叠加10次）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_CROWING_ROOFTOP_FIGURE() {
    return collectible(
        "crowing_rooftop_figure",
        "鸣脊兽",
        "所有我方单位技能未开启时40秒内攻击速度逐渐提升至至高+40，每次技能结束时失去该加成",
        "Allied Operators gradually gain ASPD when not using a skill, up to a maximum of +40 after 40s; bonus is reset when skill ends",
        "这只脊兽负责给整个界园报时，工作人员在它身上花了许多心思，就为了让它快快起飞，一鸣惊园。",
        "The rooftop figure's call announces the time at the Garden of Grotesqueries. The staff put a great deal of effort into it so it can take flight quickly and let out a cry that reaches every corner of the garden.",
        sourceRule("所有我方单位技能未开启时40秒内攻击速度逐渐提升至至高+40，每次技能结束时失去该加成"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_HEAVEN_AXE() {
    return collectible(
        "heaven_axe",
        "登天斧",
        "所有我方单位攻击力、生命值+60％。每进入一次岁兽残识，所有我方单位攻击力、生命值-30％（最多叠加2次）",
        "Operators +60% ATK and HP, but -30% ATK and HP whenever you enter Sui's Remnant Consciousness (Stacks 2x)",
        "曾有樵夫迷失于界园，凭着一把钝斧头和求生欲从山脚一路砍到了见字祠的大门前。之后这位不知岁识为何物的大爷便被“聘”为界园的工作人员，而易则收藏了他再也用不到的斧头。",
        "A lumberjack who lost his way in the Garden of Grotesqueries managed to cut a way through to Jianzi Shrine with nothing more than a blunt axe and sheer force of will. This old fellow, who hadn't the faintest idea what consciousness of the Sui even was, was later 'hired' as a staff member, and Yi added the axe he no longer needed to his collection.",
        partialRule("所有我方单位攻击力、生命值+60％。每进入一次岁兽残识，所有我方单位攻击力、生命值-30％（最多叠加2次）", stats -> stats.multiplyAttack(0.6).multiplyMaxHealth(0.6)),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_YARN_OF_REAL_AND_UNREAL() {
    return collectible(
        "yarn_of_real_and_unreal",
        "虚实线团",
        "干员周围八格内有其他我方单位时，自身防御力+600，法术抗性+15",
        "Operator DEF +600 and RES +15 when there are other Operators in the 8 surrounding tiles",
        "易不知从何处收集来的，绩弃之不用的丝线，在虚线与实线都经过一番处理后，它们被丢入界园一隅，成了野兽们的玩具。",
        "Yarn that Yi collected, which Ji is loath to use. After the real and unreal threads were tidied up, it was tossed into a corner of the Garden of Grotesqueries for the beasts to toy with.",
        sourceRule("干员周围八格内有其他我方单位时，自身防御力+600，法术抗性+15"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SILK_KNOT() {
    return collectible(
        "silk_knot",
        "绸锁",
        "干员周围八格内有其他我方单位时，自身最大生命值上限+40％",
        "Operator Max HP +40% when there are other Operators in the 8 surrounding tiles",
        "易为一对老员工证婚时送上的礼物，若是有人变心，这把绸锁就会自己解开——他们已经离开很久了，这把锁似乎仍没有什么变化。",
        "Yi's wedding gift to a staff couple, made to unravel when either is unfaithful. The couple left a long time ago, but nothing has changed with the knot.",
        sourceRule("干员周围八格内有其他我方单位时，自身最大生命值上限+40％"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_FRAGMENT_OF_JU_S_ORDER() {
    return collectible(
        "fragment_of_ju_s_order",
        "循矩残片",
        "干员攻击力+50％，手动开启技能后对应干员失去加成",
        "Operator ATK +50%, bonus lost when skill is activated manually",
        "矩兽怪异身躯的一部分，如今藏于百灶博物馆。过去，有一组参观过该藏品的游客莫名迈着整齐划一的步伐走出展厅，这件物品随即被运入库房，永不展出。",
        "A piece of Ju's strange body, now part of the Baizao Museum collection. It was withdrawn from display and put into storage after a group of tourists marched out of the exhibition hall in perfect unison after viewing it.",
        sourceRule("干员攻击力+50％，手动开启技能后对应干员失去加成"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FRAGMENT_OF_HOU_S_SOIL() {
    return collectible(
        "fragment_of_hou_s_soil",
        "后土残片",
        "位于化境地块上的干员开启技能时，使全场干员攻击力+40％，持续30秒",
        "When an Operator on a Vagary Tile uses a skill, all Operators +40% ATK for 30s",
        "后兽沉重身躯的一部分，曾藏于百灶博物馆内。在将整个展馆化作花园后不知所终，或许祂已同这片新生的自然融为一体。",
        "A piece of Hou's heavy body, once part of the Baizao Museum collection. It disappeared when the exhibition hall turned into a garden; perhaps the Feranmut has already become one with this newborn corner of nature.",
        sourceRule("位于化境地块上的干员开启技能时，使全场干员攻击力+40％，持续30秒"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_VICTORY_GUQIN() {
    return collectible(
        "victory_guqin",
        "得胜琴",
        "我方单位攻击力+15％。每次损失目标生命值时，攻击力额外+5％（最多叠加17次）",
        "Allied Operators' ATK +15%, extra +5% whenever Life Point(s) lost (Stacks 17x)",
        "只有征战归来的勇士才能拨上一个音，一战之后，便得一谱，收为纪念。经历百战，琴体早已被染红。但得胜之音，绕耳不息。",
        "Only a brave warrior who came back from battle can make a sound with it. Each battle forms a music score that is preserved for commemoration. The body of the zither is red after hundreds of battles, but the music of victory lingers.",
        sourceRule("我方单位攻击力+15％。每次损失目标生命值时，攻击力额外+5％（最多叠加17次）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_PANACEA() {
    return collectible(
        "panacea",
        "“万灵方”",
        "我方单位位于异常状态（寒冷、冰冻、晕眩）时，防御力+1200、法术抗性+30",
        "Operator +1200 DEF and +30 RES when affected by negative status (Cold, Frozen, Stun)",
        "研发这药方的小郎中说它只可缓解疼痛，凝神安心，为医生对症下药争取时间。但许多人不疼了就以为是药到病除，便给这帖药安了这么个虚名。",
        "The doctor developed this recipe to soothe the patient's pain and anxiety, giving them more time to tackle the root of the ailment. However, patients often believe it to be a miracle cure because it makes their pain vanish.",
        sourceRule("我方单位位于异常状态（寒冷、冰冻、晕眩）时，防御力+1200、法术抗性+30"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_WAKING_TOWER() {
    return collectible(
        "waking_tower",
        "醒时塔",
        "醒觉一个岁时",
        "Awakens an Hour of Sui",
        "岁时难抗，但柳儿想了个绝妙的法子。在诸位岁兽代理人的帮助下，她研究出了这座能改换岁时的宝塔。",
        "The Hour of Sui is inexorable, but Liu'er came up with this tower that changes the Hour of Sui, with the help of the Sui proxies.",
        sourceRule("醒觉一个岁时"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_COIN_PLATTER() {
    return collectible(
        "coin_platter",
        "承钱盘",
        "投出花钱时，希望+1；投出衡钱时，源石锭+4；投出厉钱时，目标生命值+1",
        "+1 Hope on Flower Coin toss; +4 Ingots on Balance Coin toss; +1 Life Point on Risk Coin toss",
        "投钱就要听个叮当响，有好事者为此专门做了个盘子，还美其名曰“一个要摔，一个要挨”。不知不觉间，竟培养出这大地上一等一的识钱好盘。",
        "If you are going to toss a coin, it had better make a sound. Created for such a purpose, this plate has somehow become one of the most discerning coin connoisseurs in the land.",
        sourceRule("投出花钱时，希望+1；投出衡钱时，源石锭+4；投出厉钱时，目标生命值+1"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_LITTLE_BA_JIE_STICKER() {
    return collectible(
        "little_ba_jie_sticker",
        "小八界贴纸",
        "有概率额外投出通宝",
        "Chance to toss extra Tongbao",
        "时常与通宝一起被出售的界园吉祥物贴纸，偶尔会将想要离开藏钱木盒的好朋友们掷出去。",
        "Sticker of the Garden of Grotesqueries mascot, often sold in a set with Tongbao. Sometimes throws out its friends that want to leave the Coin Coffer.",
        sourceRule("有概率额外投出通宝"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SOLDIER_S_EDGE() {
    return collectible(
        "soldier_s_edge",
        "兵锋",
        "钱盒里每有一枚衡钱，所有我方干员攻击力+10％（最多+150％）",
        "Allied Operators +10% ATK for each Balance Coin in Coffer (max +150%)",
        "每一位禁军都是实力与气运造就的偶然，其兵刃同样如此。",
        "Every member of the Imperial Guard is the product of both skill and luck. The same is true of their weapons.",
        sourceRule("钱盒里每有一枚衡钱，所有我方干员攻击力+10％（最多+150％）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SWADDLED_PEGASUS() {
    return collectible(
        "swaddled_pegasus",
        "襁褓天马",
        "每次进入新区域时，初始行动力+1",
        "每次进入新区域时，初始行动力+1",
        "愿新生的它不再折翼。",
        "愿新生的它不再折翼。",
        sourceRule("每次进入新区域时，初始行动力+1"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SWADDLED_EAGLE() {
    return collectible(
        "swaddled_eagle",
        "襁褓骏鹰",
        "初始希望+1，初始揭示前两个探索区域中所有节点的信息",
        "初始希望+1，初始揭示前两个探索区域中所有节点的信息",
        "愿新生的它不再狂傲。",
        "愿新生的它不再狂傲。",
        sourceRule("初始希望+1，初始揭示前两个探索区域中所有节点的信息"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SWADDLED_DRAGON() {
    return collectible(
        "swaddled_dragon",
        "襁褓巨龙",
        "前两个探索区域的战斗中，所有敌人的最大生命-50%",
        "前两个探索区域的战斗中，所有敌人的最大生命-50%",
        "愿新生的它不再饥饿。",
        "愿新生的它不再饥饿。",
        sourceRule("前两个探索区域的战斗中，所有敌人的最大生命-50%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SWADDLED_HYDRA() {
    return collectible(
        "swaddled_hydra",
        "襁褓九头蛇",
        "所有敌人的攻击和生命+30%，每进入新区域时我方攻击力和生命+10%",
        "所有敌人的攻击和生命+30%，每进入新区域时我方攻击力和生命+10%",
        "愿新生的它不再痛苦。",
        "愿新生的它不再痛苦。",
        sourceRule("所有敌人的攻击和生命+30%，每进入新区域时我方攻击力和生命+10%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SWADDLED_BAI_ZE() {
    return collectible(
        "swaddled_bai_ze",
        "襁褓白泽",
        "干员招募时，出现临时招募的概率提升",
        "干员招募时，出现临时招募的概率提升",
        "愿新生的它不再孤独。",
        "愿新生的它不再孤独。",
        sourceRule("干员招募时，出现临时招募的概率提升"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SWADDLED_GOLDEN_CROW() {
    return collectible(
        "swaddled_golden_crow",
        "襁褓金乌",
        "进入区域Ⅲ血色空脉时，获得3个随机收藏品",
        "进入区域Ⅲ血色空脉时，获得3个随机收藏品",
        "愿新生的它不再陨落。",
        "愿新生的它不再陨落。",
        sourceRule("进入区域Ⅲ血色空脉时，获得3个随机收藏品"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_RED_SUN_CROWN() {
    return collectible(
        "red_sun_crown",
        "红日冠冕",
        "高台干员对每个敌人首次造成的伤害提升至300%",
        "高台干员对每个敌人首次造成的伤害提升至300%",
        "祂将自己所喜爱的冠冕赠与祂的黎博利孩子。黎博利接受了冠冕，也接受了白昼。于是黎博利翱翔于山巅之上，从此大地没有了黑夜。",
        "祂将自己所喜爱的冠冕赠与祂的黎博利孩子。黎博利接受了冠冕，也接受了白昼。于是黎博利翱翔于山巅之上，从此大地没有了黑夜。",
        sourceRule("高台干员对每个敌人首次造成的伤害提升至300%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_NIGHT_SHAWL() {
    return collectible(
        "night_shawl",
        "黑夜披肩",
        "地面干员对每个敌人首次造成的伤害提升至300%",
        "地面干员对每个敌人首次造成的伤害提升至300%",
        "祂将自己所喜爱的披肩赠与祂的斐迪亚孩子。斐迪亚接受了披肩，没有得到白昼。于是斐迪亚偷走了黎博利的冠冕，从此大地又有了黑夜。",
        "祂将自己所喜爱的披肩赠与祂的斐迪亚孩子。斐迪亚接受了披肩，没有得到白昼。于是斐迪亚偷走了黎博利的冠冕，从此大地又有了黑夜。",
        sourceRule("地面干员对每个敌人首次造成的伤害提升至300%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_FALSE_WINGS() {
    return collectible(
        "false_wings",
        "伪翅",
        "我方干员击倒敌人时，获得3秒迷彩",
        "我方干员击倒敌人时，获得3秒迷彩",
        "“那棵树还剩最后一片叶子，我们能挺过这个冬天……”",
        "“那棵树还剩最后一片叶子，我们能挺过这个冬天……”",
        sourceRule("我方干员击倒敌人时，获得3秒迷彩"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_THE_DESIRE_TO_BITE() {
    return collectible(
        "the_desire_to_bite",
        "“撕咬的渴望”",
        "我方干员击倒敌人时，攻击速度+100，持续2秒",
        "我方干员击倒敌人时，攻击速度+100，持续2秒",
        "鲜血只会让它们更加亢奋。它们在哪里？无处不在。",
        "鲜血只会让它们更加亢奋。它们在哪里？无处不在。",
        sourceRule("我方干员击倒敌人时，攻击速度+100，持续2秒"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FRAGRANT_HORN() {
    return collectible(
        "fragrant_horn",
        "异香角",
        "我方干员击倒敌人时，获得2点技力",
        "我方干员击倒敌人时，获得2点技力",
        "一截断角，散发着令人兴奋的味道，似乎是树海中两头雄性生物打架时掉下的，争斗永远使人成瘾。",
        "一截断角，散发着令人兴奋的味道，似乎是树海中两头雄性生物打架时掉下的，争斗永远使人成瘾。",
        sourceRule("我方干员击倒敌人时，获得2点技力"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BLOODTHIRSTY_PINCERBEAST() {
    return collectible(
        "bloodthirsty_pincerbeast",
        "渴血钳兽",
        "我方干员击倒敌人时，回复10%最大生命",
        "我方干员击倒敌人时，回复10%最大生命",
        "观赏钳兽争斗很有趣，前提是它们钳住的不是你的手指。",
        "观赏钳兽争斗很有趣，前提是它们钳住的不是你的手指。",
        sourceRule("我方干员击倒敌人时，回复10%最大生命"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SANCTUARY() {
    return collectible(
        "sanctuary",
        "“庇护”",
        "高台干员部署时立刻获得最大生命100%的屏障",
        "高台干员部署时立刻获得最大生命100%的屏障",
        "他们的汗水如树海的雨水般落下，但好在他们有遮挡雨水的叶子来遮挡烈日。",
        "他们的汗水如树海的雨水般落下，但好在他们有遮挡雨水的叶子来遮挡烈日。",
        sourceRule("高台干员部署时立刻获得最大生命100%的屏障"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_PLANTER_S_INSURANCE_POLICY() {
    return collectible(
        "planter_s_insurance_policy",
        "种植者保单",
        "若我方干员在5秒内未受到伤害，则获得最大生命20%的屏障",
        "若我方干员在5秒内未受到伤害，则获得最大生命20%的屏障",
        "一份印得密密麻麻的保单，但条款只在被保人没出意外时有效——好在他们也看不懂上面的内容。",
        "一份印得密密麻麻的保单，但条款只在被保人没出意外时有效——好在他们也看不懂上面的内容。",
        sourceRule("若我方干员在5秒内未受到伤害，则获得最大生命20%的屏障"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_TACTICAL_VEST() {
    return collectible(
        "tactical_vest",
        "战术背心",
        "我方干员拥有屏障时，造成的伤害提升至140%",
        "我方干员拥有屏障时，造成的伤害提升至140%",
        "“我觉得口袋最好再多一些，得找个地方把我老妈的照片塞进去。”",
        "“我觉得口袋最好再多一些，得找个地方把我老妈的照片塞进去。”",
        sourceRule("我方干员拥有屏障时，造成的伤害提升至140%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BLESSED_REBIRTH() {
    return collectible(
        "blessed_rebirth",
        "赐福新生",
        "首个干员部署时获得最大生命300%的屏障，且拥有屏障时攻击额外造成攻击力50%的法术伤害",
        "首个干员部署时获得最大生命300%的屏障，且拥有屏障时攻击额外造成攻击力50%的法术伤害",
        "赠予新生儿的特色礼物，最初由莱塔尼亚军官从玻利瓦尔归乡时带回，后经历了本土化，没有人会拒绝对新生命的祝福。",
        "赠予新生儿的特色礼物，最初由莱塔尼亚军官从玻利瓦尔归乡时带回，后经历了本土化，没有人会拒绝对新生命的祝福。",
        sourceRule("首个干员部署时获得最大生命300%的屏障，且拥有屏障时攻击额外造成攻击力50%的法术伤害"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ANOTHER_S_BONDS() {
    return collectible(
        "another_s_bonds",
        "他缚",
        "所有干员部署费用-50%，部署后损失70%的当前生命（退场前仅生效一次）",
        "所有干员部署费用-50%，部署后损失70%的当前生命（退场前仅生效一次）",
        "你从泥土中挖出了它，它却在为这片土地复仇。",
        "你从泥土中挖出了它，它却在为这片土地复仇。",
        sourceRule("所有干员部署费用-50%，部署后损失70%的当前生命（退场前仅生效一次）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BLOODSTAINED_DICTIONARY() {
    return collectible(
        "bloodstained_dictionary",
        "染血辞典",
        "所有干员生命值低于10%时，仅一次立刻获得最大生命100%的屏障",
        "所有干员生命值低于10%时，仅一次立刻获得最大生命100%的屏障",
        "它曾为一名正在学习书写的真正玻利瓦尔游击队员挡下致命一击，遗憾的是它的拥有者没能撑太久，也没能认识更多词。",
        "它曾为一名正在学习书写的真正玻利瓦尔游击队员挡下致命一击，遗憾的是它的拥有者没能撑太久，也没能认识更多词。",
        sourceRule("所有干员生命值低于10%时，仅一次立刻获得最大生命100%的屏障"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_LIQUID_FERTILIZER() {
    return collectible(
        "liquid_fertilizer",
        "液体化肥",
        "我方干员攻击时流失50点生命（至少保留1点），获得100屏障（此效果获得的屏障最大为100%生命上限）",
        "我方干员攻击时流失50点生命（至少保留1点），获得100屏障（此效果获得的屏障最大为100%生命上限）",
        "它所到之处皆是丰收，但代价呢？总之不是收获者来付出。",
        "它所到之处皆是丰收，但代价呢？总之不是收获者来付出。",
        sourceRule("我方干员攻击时流失50点生命（至少保留1点），获得100屏障（此效果获得的屏障最大为100%生命上限）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SPICY_COCOA() {
    return collectible(
        "spicy_cocoa",
        "热辣可可",
        "干员生命值越低，攻击力越高，30%生命值时达到最大可提升攻击力（+150%）",
        "干员生命值越低，攻击力越高，30%生命值时达到最大可提升攻击力（+150%）",
        "辣椒和可可结合的饮品，单听原料想象不出结合出来的口感，但在多索雷斯的游客当中很受欢迎。",
        "辣椒和可可结合的饮品，单听原料想象不出结合出来的口感，但在多索雷斯的游客当中很受欢迎。",
        sourceRule("干员生命值越低，攻击力越高，30%生命值时达到最大可提升攻击力（+150%）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_PAINKILLERS() {
    return collectible(
        "painkillers",
        "止痛片",
        "干员生命值越低，技力恢复速度越快，30%生命值时达到最大恢复速度（+2/s）",
        "干员生命值越低，技力恢复速度越快，30%生命值时达到最大恢复速度（+2/s）",
        "这种止痛片广受哥伦比亚人好评，获取其原材料却极其凶险，而负责采摘的玻利瓦尔人通常没有机会使用它。",
        "这种止痛片广受哥伦比亚人好评，获取其原材料却极其凶险，而负责采摘的玻利瓦尔人通常没有机会使用它。",
        sourceRule("干员生命值越低，技力恢复速度越快，30%生命值时达到最大恢复速度（+2/s）"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SKYROCKETING_ORIGINIUM_SLUG() {
    return collectible(
        "skyrocketing_originium_slug",
        "窜天源石虫",
        "干员起飞时，对攻击范围内所有敌人造成相当于自身攻击力400%的物理伤害",
        "干员起飞时，对攻击范围内所有敌人造成相当于自身攻击力400%的物理伤害",
        "要问什么东西是大炎小孩在除夕夜最喜欢的——非点燃后火速升空的源石虫爆竹莫属。",
        "要问什么东西是大炎小孩在除夕夜最喜欢的——非点燃后火速升空的源石虫爆竹莫属。",
        sourceRule("干员起飞时，对攻击范围内所有敌人造成相当于自身攻击力400%的物理伤害"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_GROUND_HUGGING_BOMB() {
    return collectible(
        "ground_hugging_bomb",
        "贴地炸",
        "干员降落时，对攻击范围内所有敌人造成相当于自身攻击力200%的物理伤害和2秒晕眩",
        "干员降落时，对攻击范围内所有敌人造成相当于自身攻击力200%的物理伤害和2秒晕眩",
        "虽然经常会出现哑炮的情况，但被摔在地上的一瞬间，路过的大炎人还是会快步闪开。",
        "虽然经常会出现哑炮的情况，但被摔在地上的一瞬间，路过的大炎人还是会快步闪开。",
        sourceRule("干员降落时，对攻击范围内所有敌人造成相当于自身攻击力200%的物理伤害和2秒晕眩"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SOARING_WING() {
    return collectible(
        "soaring_wing",
        "翱翼",
        "我方每有1个起飞的单位，所有起飞的单位攻击力+25%",
        "我方每有1个起飞的单位，所有起飞的单位攻击力+25%",
        "从这对完整的羽翼中，仍能窥见它们的所有者曾翱翔于空中称霸的模样，但胜负已定，落败者需付出代价。",
        "从这对完整的羽翼中，仍能窥见它们的所有者曾翱翔于空中称霸的模样，但胜负已定，落败者需付出代价。",
        sourceRule("我方每有1个起飞的单位，所有起飞的单位攻击力+25%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_DRAGON_S_MOLT() {
    return collectible(
        "dragon_s_molt",
        "虬蜕",
        "我方每有1个未起飞的单位，所有单位的防御力+10%",
        "我方每有1个未起飞的单位，所有单位的防御力+10%",
        "从这张完整的蛇蜕中，仍能窥见它的所有者曾盘踞于地面称霸的模样，但胜负已定，落败者需付出代价。",
        "从这张完整的蛇蜕中，仍能窥见它的所有者曾盘踞于地面称霸的模样，但胜负已定，落败者需付出代价。",
        sourceRule("我方每有1个未起飞的单位，所有单位的防御力+10%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_CELESTIAL_MASTER_S_BROCADE_POUCH() {
    return collectible(
        "celestial_master_s_brocade_pouch",
        "天师锦囊",
        "首次部署炎国干员时，在其位置召唤一只【炎佑】",
        "首次部署炎国干员时，在其位置召唤一只【炎佑】",
        "遇事不决，可掏出天师赠与的锦囊。锦囊不一定会提供建议，但至少可提供武力支援。",
        "遇事不决，可掏出天师赠与的锦囊。锦囊不一定会提供建议，但至少可提供武力支援。",
        sourceRule("首次部署炎国干员时，在其位置召唤一只【炎佑】"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FIRE_IN_ICE() {
    return collectible(
        "fire_in_ice",
        "冰中火",
        "敌人受到的元素损伤和元素伤害+75%",
        "敌人受到的元素损伤和元素伤害+75%",
        "火一直在燃烧，冰却一直未融。看起来很热，摸起来却是冷的，正如你在黑流树海中所见的一切。",
        "火一直在燃烧，冰却一直未融。看起来很热，摸起来却是冷的，正如你在黑流树海中所见的一切。",
        sourceRule("敌人受到的元素损伤和元素伤害+75%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_VALLEY_PRAYER() {
    return collectible(
        "valley_prayer",
        "河谷祭祈",
        "我方凋亡、灼燃、神经和侵蚀损伤的爆发效果增强",
        "我方凋亡、灼燃、神经和侵蚀损伤的爆发效果增强",
        "尘土阖上她的眼睛，溪流为她拭去泪水。",
        "尘土阖上她的眼睛，溪流为她拭去泪水。",
        sourceRule("我方凋亡、灼燃、神经和侵蚀损伤的爆发效果增强"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HOUND_DISEASE_CURE() {
    return collectible(
        "hound_disease_cure",
        "猎犬病特效药",
        "我方狂躁元素爆发后不再受到持续伤害，且期间攻击速度额外+50",
        "我方狂躁元素爆发后不再受到持续伤害，且期间攻击速度额外+50",
        "可以短暂抑制猎犬病症状的特效药，但仅仅是短暂抑制，医疗及研究人员至今无法根除此病。",
        "可以短暂抑制猎犬病症状的特效药，但仅仅是短暂抑制，医疗及研究人员至今无法根除此病。",
        sourceRule("我方狂躁元素爆发后不再受到持续伤害，且期间攻击速度额外+50"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SPECIAL_OPERATIONS_RIOT_SHIELD() {
    return collectible(
        "special_operations_riot_shield",
        "特种作战防暴盾",
        "被阻挡的敌人造成的伤害-30%",
        "被阻挡的敌人造成的伤害-30%",
        "黑钢国际的装备与应用技术部门不遗余力地追求轻量化，希望佣兵作战时手上的负担能小一点，心理上的负担也能小一点……也许吧。",
        "黑钢国际的装备与应用技术部门不遗余力地追求轻量化，希望佣兵作战时手上的负担能小一点，心理上的负担也能小一点……也许吧。",
        sourceRule("被阻挡的敌人造成的伤害-30%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_HOUND_COFFEE() {
    return collectible(
        "hound_coffee",
        "猎犬咖啡",
        "我方单位的生命和攻击力+30%，敌人出现时有3%概率生命+100%",
        "我方单位的生命和攻击力+30%，敌人出现时有3%概率生命+100%",
        "猎犬出没之地的野生咖啡豆总是更加美味，虽然不知道原因，但并不妨碍这些咖啡豆在哥伦比亚被炒出一波又一波高价，至于是否真的是从猎犬的领地摘取的，也并不重要了。",
        "猎犬出没之地的野生咖啡豆总是更加美味，虽然不知道原因，但并不妨碍这些咖啡豆在哥伦比亚被炒出一波又一波高价，至于是否真的是从猎犬的领地摘取的，也并不重要了。",
        sourceRule("我方单位的生命和攻击力+30%，敌人出现时有3%概率生命+100%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_ARENA_VIP_VOUCHER() {
    return collectible(
        "arena_vip_voucher",
        "竞技场贵宾券",
        "精英敌人的生命+10%，战斗获得的源石锭和指挥经验+40%",
        "精英敌人的生命+10%，战斗获得的源石锭和指挥经验+40%",
        "只要再加十万马克，您就可以从超超级贵宾升级为超超超级贵宾！",
        "只要再加十万马克，您就可以从超超级贵宾升级为超超超级贵宾！",
        sourceRule("精英敌人的生命+10%，战斗获得的源石锭和指挥经验+40%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_GOLDEN_PLAINS() {
    return collectible(
        "golden_plains",
        "黄金平原",
        "我方干员技能结束时获得2点技力",
        "我方干员技能结束时获得2点技力",
        "一望无际的金色麦田、微风、人们的欢声笑语，你的下一瓶精酿何必等待太久？",
        "一望无际的金色麦田、微风、人们的欢声笑语，你的下一瓶精酿何必等待太久？",
        sourceRule("我方干员技能结束时获得2点技力"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_THE_FIRST_LAW() {
    return collectible(
        "the_first_law",
        "第一法则",
        "我方干员技能结束时获得4点技力",
        "我方干员技能结束时获得4点技力",
        "叙拉古出品的红酒只有一种名字——赢家通吃，永远是这里的第一法则。",
        "叙拉古出品的红酒只有一种名字——赢家通吃，永远是这里的第一法则。",
        sourceRule("我方干员技能结束时获得4点技力"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SPECIAL_OPERATIONS_FOOTAGE() {
    return collectible(
        "special_operations_footage",
        "特勤任务影像",
        "我方单位的生命、攻击力、防御力+40%，指挥经验+20%",
        "我方单位的生命、攻击力、防御力+40%，指挥经验+20%",
        "录制影像的人活下来了，下一位观看影像的特勤调查员，祝你好运。",
        "录制影像的人活下来了，下一位观看影像的特勤调查员，祝你好运。",
        sourceRule("我方单位的生命、攻击力、防御力+40%，指挥经验+20%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_RECON_VANGUARD() {
    return collectible(
        "recon_vanguard",
        "探测先锋",
        "完成“追猎”作战后叠加1层并使下个区域初始行动力-1，每层使“猎犬proto”的生命和攻击力+20%",
        "完成“追猎”作战后叠加1层并使下个区域初始行动力-1，每层使“猎犬proto”的生命和攻击力+20%",
        "你幸运地在“追猎”中死里逃生，现颁发“探测先锋”奖章，以兹鼓励，期待下一次更精彩的表现。",
        "你幸运地在“追猎”中死里逃生，现颁发“探测先锋”奖章，以兹鼓励，期待下一次更精彩的表现。",
        sourceRule("完成“追猎”作战后叠加1层并使下个区域初始行动力-1，每层使“猎犬proto”的生命和攻击力+20%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BLUNTCLAW_BATTLE_HARDENED() {
    return collectible(
        "bluntclaw_battle_hardened",
        "钝爪-屡战",
        "所有【先锋】干员的攻击力+50%，防御力+50%",
        "所有【先锋】干员的攻击力+50%，防御力+50%",
        "“一鼓作气，克敌机先。”",
        "“一鼓作气，克敌机先。”",
        implementedRule("所有【先锋】干员的攻击力+50%，防御力+50%", statSet(forProfession(SkillProfession.VANGUARD, stats -> stats.multiplyAttack(0.5)), forProfession(SkillProfession.VANGUARD, stats -> stats.multiplyDefense(0.5)))),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_BLUNTCLAW_FIRST_MOVE() {
    return collectible(
        "bluntclaw_first_move",
        "钝爪-先机",
        "先锋干员开启技能时，立刻获得3点部署费用",
        "先锋干员开启技能时，立刻获得3点部署费用",
        "争分夺秒，志在夺取有利位置。",
        "争分夺秒，志在夺取有利位置。",
        sourceRule("先锋干员开启技能时，立刻获得3点部署费用"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BROKEN_HALBERD_AVOID_THE_EDGE() {
    return collectible(
        "broken_halberd_avoid_the_edge",
        "折戟-避锋",
        "近卫干员受到攻击范围外敌人的攻击时，有75%概率闪避",
        "近卫干员受到攻击范围外敌人的攻击时，有75%概率闪避",
        "退避并非怯战，而是为了下一次攻击。",
        "退避并非怯战，而是为了下一次攻击。",
        sourceRule("近卫干员受到攻击范围外敌人的攻击时，有75%概率闪避"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_IRON_GUARD_LIGHT_STEP() {
    return collectible(
        "iron_guard_light_step",
        "铁卫-轻行",
        "重装干员首次部署费用-75%",
        "重装干员首次部署费用-75%",
        "丢掉繁琐的行囊后，守护者坚韧依旧。",
        "丢掉繁琐的行囊后，守护者坚韧依旧。",
        sourceRule("重装干员首次部署费用-75%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_WORN_CROSSBOW_DIVINE_ACCURACY() {
    return collectible(
        "worn_crossbow_divine_accuracy",
        "残弩-神准",
        "狙击干员攻击面前直线上的敌人时，攻击力提升至140%",
        "狙击干员攻击面前直线上的敌人时，攻击力提升至140%",
        "锁定直线目标一直是狙击干员的强项，在此基础上，多加火力也照样稳健。",
        "锁定直线目标一直是狙击干员的强项，在此基础上，多加火力也照样稳健。",
        sourceRule("狙击干员攻击面前直线上的敌人时，攻击力提升至140%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BROKEN_WAND_RESONANCE() {
    return collectible(
        "broken_wand_resonance",
        "断杖-同调",
        "场上每有一名术师干员，术师干员技能结束时获得3点技力",
        "场上每有一名术师干员，术师干员技能结束时获得3点技力",
        "“你的荣耀，你的耻辱，我感同身受。”",
        "“你的荣耀，你的耻辱，我感同身受。”",
        sourceRule("场上每有一名术师干员，术师干员技能结束时获得3点技力"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HEALER_TERRAIN_STRATEGY() {
    return collectible(
        "healer_terrain_strategy",
        "医者-地缘策略",
        "医疗干员攻击范围内的地面干员阻挡数+1，可叠加",
        "医疗干员攻击范围内的地面干员阻挡数+1，可叠加",
        "其他干员总是喜欢围绕在医疗干员身边，听医者讲述关于即时疗愈的新方案。",
        "其他干员总是喜欢围绕在医疗干员身边，听医者讲述关于即时疗愈的新方案。",
        sourceRule("医疗干员攻击范围内的地面干员阻挡数+1，可叠加"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_HAND_OF_TRAILBLAZING() {
    return collectible(
        "hand_of_trailblazing",
        "开拓之手",
        "尖兵、冲锋手、策士的部署费用-6，技力需求-50%；拥有钝爪-先机时，开启技能会给其他场上干员回复2点技力",
        "尖兵、冲锋手、策士的部署费用-6，技力需求-50%；拥有钝爪-先机时，开启技能会给其他场上干员回复2点技力",
        "先发制人，抢占先机。",
        "先发制人，抢占先机。",
        sourceRule("尖兵、冲锋手、策士的部署费用-6，技力需求-50%；拥有钝爪-先机时，开启技能会给其他场上干员回复2点技力"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_SLAUGHTER() {
    return collectible(
        "hand_of_slaughter",
        "诛戮之手",
        "武者、无畏者、术战者对周围四格敌人造成的伤害提升至200%；拥有折戟-避锋时，击倒敌人后下次再部署时间和部署费用-50%",
        "武者、无畏者、术战者对周围四格敌人造成的伤害提升至200%；拥有折戟-避锋时，击倒敌人后下次再部署时间和部署费用-50%",
        "摧枯拉朽，力拔山兮。",
        "摧枯拉朽，力拔山兮。",
        sourceRule("武者、无畏者、术战者对周围四格敌人造成的伤害提升至200%；拥有折戟-避锋时，击倒敌人后下次再部署时间和部署费用-50%"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_FORTIFICATION() {
    return collectible(
        "hand_of_fortification",
        "坚御之手",
        "铁卫、哨戒铁卫、不屈者部署时和技能结束后攻击力和最大生命+100%，加成在60秒内递减；拥有铁卫-轻行时，生命高于50%时阻挡数+3",
        "铁卫、哨戒铁卫、不屈者部署时和技能结束后攻击力和最大生命+100%，加成在60秒内递减；拥有铁卫-轻行时，生命高于50%时阻挡数+3",
        "坚守不移，抵抗到底。",
        "坚守不移，抵抗到底。",
        sourceRule("铁卫、哨戒铁卫、不屈者部署时和技能结束后攻击力和最大生命+100%，加成在60秒内递减；拥有铁卫-轻行时，生命高于50%时阻挡数+3"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_ANTI_AIR() {
    return collectible(
        "hand_of_anti_air",
        "慑空之手",
        "炮手、散射手、裂空炮手开启技能时攻击力+150%，持续5秒；拥有残弩-神准时，每3秒获得3点技力",
        "炮手、散射手、裂空炮手开启技能时攻击力+150%，持续5秒；拥有残弩-神准时，每3秒获得3点技力",
        "我自向天，藐视晴苍。",
        "我自向天，藐视晴苍。",
        sourceRule("炮手、散射手、裂空炮手开启技能时攻击力+150%，持续5秒；拥有残弩-神准时，每3秒获得3点技力"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_HEALING() {
    return collectible(
        "hand_of_healing",
        "护愈之手",
        "疗养师、行医、护佑者攻击范围内的我方干员免疫异常状态，拥有医者-地缘策略时，溢出的治疗量可以转化成等量的屏障（最大为100%生命上限）",
        "疗养师、行医、护佑者攻击范围内的我方干员免疫异常状态，拥有医者-地缘策略时，溢出的治疗量可以转化成等量的屏障（最大为100%生命上限）",
        "将心比心，时刻呵护。",
        "将心比心，时刻呵护。",
        sourceRule("疗养师、行医、护佑者攻击范围内的我方干员免疫异常状态，拥有医者-地缘策略时，溢出的治疗量可以转化成等量的屏障（最大为100%生命上限）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HAND_OF_PURGE_AND_ASSAULT() {
    return collectible(
        "hand_of_purge_and_assault",
        "剿袭之手",
        "敌人进入秘术师、神射手、猎手的攻击范围内时，受到一次攻击力300%的法术或物理伤害和50%的法术或物理脆弱（持续3秒）",
        "敌人进入秘术师、神射手、猎手的攻击范围内时，受到一次攻击力300%的法术或物理伤害和50%的法术或物理脆弱（持续3秒）",
        "视域之内，寸草不生。",
        "视域之内，寸草不生。",
        sourceRule("敌人进入秘术师、神射手、猎手的攻击范围内时，受到一次攻击力300%的法术或物理伤害和50%的法术或物理脆弱（持续3秒）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BLUNTCLAW_NEW_DOCTRINE() {
    return collectible(
        "bluntclaw_new_doctrine",
        "钝爪-新典训",
        "立即进阶一个【先锋】干员（不消耗希望），并使其：每次开启技能后攻击力+10%（最多+60%）",
        "立即进阶一个【先锋】干员（不消耗希望），并使其：每次开启技能后攻击力+10%（最多+60%）",
        "一套新的强力装备，一颗更加勇往直前的心。",
        "一套新的强力装备，一颗更加勇往直前的心。",
        sourceRule("立即进阶一个【先锋】干员（不消耗希望），并使其：每次开启技能后攻击力+10%（最多+60%）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_BROKEN_WAND_NEW_DOCTRINE() {
    return collectible(
        "broken_wand_new_doctrine",
        "断杖-新典训",
        "立即进阶一个【术师】干员（不消耗希望），并使其：攻击时降低目标5点法术抗性（最多降低25）",
        "立即进阶一个【术师】干员（不消耗希望），并使其：攻击时降低目标5点法术抗性（最多降低25）",
        "“我在垃圾桶里看到一本莱塔尼亚教材，是哪位干员不小心丢失的吗？”",
        "“我在垃圾桶里看到一本莱塔尼亚教材，是哪位干员不小心丢失的吗？”",
        sourceRule("立即进阶一个【术师】干员（不消耗希望），并使其：攻击时降低目标5点法术抗性（最多降低25）"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_BROKEN_HALBERD_NEW_DOCTRINE() {
    return collectible(
        "broken_halberd_new_doctrine",
        "折戟-新典训",
        "立即进阶一个【近卫】干员（不消耗希望），并使其：首次被击倒时回复100%生命",
        "立即进阶一个【近卫】干员（不消耗希望），并使其：首次被击倒时回复100%生命",
        "百步之外，弩箭与法术更快，但三步之内，还是剑更快。",
        "百步之外，弩箭与法术更快，但三步之内，还是剑更快。",
        sourceRule("立即进阶一个【近卫】干员（不消耗希望），并使其：首次被击倒时回复100%生命"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_IRON_GUARD_NEW_DOCTRINE() {
    return collectible(
        "iron_guard_new_doctrine",
        "铁卫-新典训",
        "立即进阶一个【重装】干员（不消耗希望），并使其：受到攻击时回复2技力",
        "立即进阶一个【重装】干员（不消耗希望），并使其：受到攻击时回复2技力",
        "逝去的战士已成为划痕，永远被铭记于坚盾之上。",
        "逝去的战士已成为划痕，永远被铭记于坚盾之上。",
        sourceRule("立即进阶一个【重装】干员（不消耗希望），并使其：受到攻击时回复2技力"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_WORN_CROSSBOW_NEW_DOCTRINE() {
    return collectible(
        "worn_crossbow_new_doctrine",
        "残弩-新典训",
        "立即进阶一个【狙击】干员（不消耗希望），并使其：攻击时无视目标50%的防御",
        "立即进阶一个【狙击】干员（不消耗希望），并使其：攻击时无视目标50%的防御",
        "“没射中不是你准头不行，给我加大火力，再来！”",
        "“没射中不是你准头不行，给我加大火力，再来！”",
        sourceRule("立即进阶一个【狙击】干员（不消耗希望），并使其：攻击时无视目标50%的防御"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_HEALER_NEW_DOCTRINE() {
    return collectible(
        "healer_new_doctrine",
        "医者-新典训",
        "立即进阶一个【医疗】干员（不消耗希望），并使其：治疗时使目标获得2技力",
        "立即进阶一个【医疗】干员（不消耗希望），并使其：治疗时使目标获得2技力",
        "“我成为医疗干员的初衷是希望没有人再来找我看病，如果失业我也是心甘情愿的。”",
        "“我成为医疗干员的初衷是希望没有人再来找我看病，如果失业我也是心甘情愿的。”",
        sourceRule("立即进阶一个【医疗】干员（不消耗希望），并使其：治疗时使目标获得2技力"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_RUSTY_BLADE_NEW_DOCTRINE() {
    return collectible(
        "rusty_blade_new_doctrine",
        "锈刃-新典训",
        "立即进阶一个【特种】干员（不消耗希望），并使其：再部署时间-50%",
        "立即进阶一个【特种】干员（不消耗希望），并使其：再部署时间-50%",
        "即便是经常与特种干员进行实战练习的老手，依然无法精准把握“准备好被打”的时机。",
        "即便是经常与特种干员进行实战练习的老手，依然无法精准把握“准备好被打”的时机。",
        sourceRule("立即进阶一个【特种】干员（不消耗希望），并使其：再部署时间-50%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_SUPPORT_NEW_DOCTRINE() {
    return collectible(
        "support_new_doctrine",
        "支柱-新典训",
        "立即进阶一个【辅助】干员（不消耗希望），并使其：攻击范围内的敌人防御力和法术抗性-40%",
        "立即进阶一个【辅助】干员（不消耗希望），并使其：攻击范围内的敌人防御力和法术抗性-40%",
        "别试图为辅助干员指定行动点位，他们自己的策略往往比制定好的计划更有效。",
        "别试图为辅助干员指定行动点位，他们自己的策略往往比制定好的计划更有效。",
        sourceRule("立即进阶一个【辅助】干员（不消耗希望），并使其：攻击范围内的敌人防御力和法术抗性-40%"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_CONVALESCENCE_GIFT_CARD() {
    return collectible(
        "convalescence_gift_card",
        "疗养礼品卡",
        "下一个招募或进阶的干员攻击速度+50",
        "下一个招募或进阶的干员攻击速度+50",
        "疗养庭院永远欢迎想和朋友一起来放松身心的干员。",
        "疗养庭院永远欢迎想和朋友一起来放松身心的干员。",
        sourceRule("下一个招募或进阶的干员攻击速度+50"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_WOLF_BETWEEN_FINGERS() {
    return collectible(
        "wolf_between_fingers",
        "指中狼",
        "下一个招募或进阶的干员无法开启技能，但攻击力+100%，攻击速度+50",
        "下一个招募或进阶的干员无法开启技能，但攻击力+100%，攻击速度+50",
        "这种野蛮的斗殴方式在叙拉古街头被逐渐取缔，现在想要观赏一场酣畅淋漓的“狼斗”并非易事。",
        "这种野蛮的斗殴方式在叙拉古街头被逐渐取缔，现在想要观赏一场酣畅淋漓的“狼斗”并非易事。",
        sourceRule("下一个招募或进阶的干员无法开启技能，但攻击力+100%，攻击速度+50"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_MOM_S_ENCOURAGEMENT() {
    return collectible(
        "mom_s_encouragement",
        "“老妈的鼓励”",
        "下一个招募或进阶的干员首次部署时攻击力+50%",
        "下一个招募或进阶的干员首次部署时攻击力+50%",
        "约翰老妈旗下最畅销的能量棒，解决了大部分不爱吃早餐的干员低血糖的问题。据说每一根的包装内都有不同的鼓励语，用于开启崭新的一天。",
        "约翰老妈旗下最畅销的能量棒，解决了大部分不爱吃早餐的干员低血糖的问题。据说每一根的包装内都有不同的鼓励语，用于开启崭新的一天。",
        sourceRule("下一个招募或进阶的干员首次部署时攻击力+50%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FOAM_SEALANT() {
    return collectible(
        "foam_sealant",
        "发泡胶",
        "下一个招募或进阶的干员攻击速度-50，攻击力+120%",
        "下一个招募或进阶的干员攻击速度-50，攻击力+120%",
        "“就是你没收好的发泡胶绊了我一脚？！我鞋被粘在地上了，别跑——”",
        "“就是你没收好的发泡胶绊了我一脚？！我鞋被粘在地上了，别跑——”",
        sourceRule("下一个招募或进阶的干员攻击速度-50，攻击力+120%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SNACK_BOX() {
    return collectible(
        "snack_box",
        "零食盒",
        "下一个招募或进阶的干员的技能会自动开启，技力需求-20%",
        "下一个招募或进阶的干员的技能会自动开启，技力需求-20%",
        "“我好像一不小心发现了博士的小金库——”",
        "“我好像一不小心发现了博士的小金库——”",
        sourceRule("下一个招募或进阶的干员的技能会自动开启，技力需求-20%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_TRANQUIL_AROMA_STONE() {
    return collectible(
        "tranquil_aroma_stone",
        "静谧扩香石",
        "下一个招募或进阶的干员攻击范围内的所有干员每3秒获得1点技力",
        "下一个招募或进阶的干员攻击范围内的所有干员每3秒获得1点技力",
        "多种助眠香型可选，只需一滴，即享美梦。",
        "多种助眠香型可选，只需一滴，即享美梦。",
        sourceRule("下一个招募或进阶的干员攻击范围内的所有干员每3秒获得1点技力"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_FORTUNE_COOKIE() {
    return collectible(
        "fortune_cookie",
        "幸运饼干",
        "获得此收藏品时，使随机2名干员的技力回复速度+0.8/秒",
        "获得此收藏品时，使随机2名干员的技力回复速度+0.8/秒",
        "塞着博士寄语纸条的饼干，吃到纸条的干员可以让博士满足一个小心愿。据不完全统计，博士至少已经收到几十张字迹被洇湿的纸条了——数量和发出去的小饼干不相上下。",
        "塞着博士寄语纸条的饼干，吃到纸条的干员可以让博士满足一个小心愿。据不完全统计，博士至少已经收到几十张字迹被洇湿的纸条了——数量和发出去的小饼干不相上下。",
        sourceRule("获得此收藏品时，使随机2名干员的技力回复速度+0.8/秒"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_LIGHT_PACK() {
    return collectible(
        "light_pack",
        "薄行囊",
        "零件箱容量+4",
        "零件箱容量+4",
        "“如果我们站着不动也能填饱肚子就好了。”",
        "“如果我们站着不动也能填饱肚子就好了。”",
        sourceRule("零件箱容量+4"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SORROWFUL_RED() {
    return collectible(
        "sorrowful_red",
        "悲伤的红",
        "零件箱中每有1个零件，所有干员的生命和攻击力+8%",
        "零件箱中每有1个零件，所有干员的生命和攻击力+8%",
        "她在枝头望着我们，我们的骨骼、我们的骨灰，都混在了一起。",
        "她在枝头望着我们，我们的骨骼、我们的骨灰，都混在了一起。",
        sourceRule("零件箱中每有1个零件，所有干员的生命和攻击力+8%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_EMPTY_BED() {
    return collectible(
        "empty_bed",
        "空床",
        "零件箱中存在4个或更多空栏位时，所有干员部署费用-6",
        "零件箱中存在4个或更多空栏位时，所有干员部署费用-6",
        "两人投下三枚阴影，灵魂拥挤不堪。",
        "两人投下三枚阴影，灵魂拥挤不堪。",
        sourceRule("零件箱中存在4个或更多空栏位时，所有干员部署费用-6"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HIDE_AND_SEEK() {
    return collectible(
        "hide_and_seek",
        "迷藏",
        "每次进入新区域时标记一个节点，探索该节点后获得一个较为稀有的随机自然物",
        "每次进入新区域时标记一个节点，探索该节点后获得一个较为稀有的随机自然物",
        "藏起一颗橡实，以便让万物都认得我们。",
        "藏起一颗橡实，以便让万物都认得我们。",
        sourceRule("每次进入新区域时标记一个节点，探索该节点后获得一个较为稀有的随机自然物"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_WHEEL_REGAINED() {
    return collectible(
        "wheel_regained",
        "复得之轮",
        "加工品使用次数耗尽时，获得一个随机加工品",
        "加工品使用次数耗尽时，获得一个随机加工品",
        "我们将要去截获，一段飞奔而逃的树桩。",
        "我们将要去截获，一段飞奔而逃的树桩。",
        sourceRule("加工品使用次数耗尽时，获得一个随机加工品"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_SUSTENANCE() {
    return collectible(
        "sustenance",
        "果腹",
        "加工品的最后一次移动不再消耗行动力",
        "加工品的最后一次移动不再消耗行动力",
        "果实如锤砸在我身，每一块淤青都是你贪婪的形状。",
        "果实如锤砸在我身，每一块淤青都是你贪婪的形状。",
        sourceRule("加工品的最后一次移动不再消耗行动力"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BONE_IN_THE_BAG() {
    return collectible(
        "bone_in_the_bag",
        "囊中骨",
        "立刻获得3个随机的普通加工品",
        "立刻获得3个随机的普通加工品",
        "它的生命如此短暂，到死都没有离开孕育它的温床。",
        "它的生命如此短暂，到死都没有离开孕育它的温床。",
        sourceRule("立刻获得3个随机的普通加工品"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_LITTLE_HAND_IN_THE_WOODS() {
    return collectible(
        "little_hand_in_the_woods",
        "林中小手",
        "立刻获得3个随机的普通自然物",
        "立刻获得3个随机的普通自然物",
        "象征吉兆的植物，迷信的玻利瓦尔人相信，遇到林中小手后会立刻获得财富。",
        "象征吉兆的植物，迷信的玻利瓦尔人相信，遇到林中小手后会立刻获得财富。",
        sourceRule("立刻获得3个随机的普通自然物"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_BLOOD_CHILD() {
    return collectible(
        "blood_child",
        "血孩子",
        "获得自然物，直到零件箱被填满",
        "获得自然物，直到零件箱被填满",
        "“我有很多很多的孩子，但孙辈却越来越少。”",
        "“我有很多很多的孩子，但孙辈却越来越少。”",
        sourceRule("获得自然物，直到零件箱被填满"),
        Rarity.UNCOMMON
    );
  }

  private static CollectibleBuilder create_TRAVELING_COMPANION() {
    return collectible(
        "traveling_companion",
        "同行者",
        "应急助力节点中招募所需源石锭-50%，应急干员的生命值、攻击力和防御力+40%",
        "应急助力节点中招募所需源石锭-50%，应急干员的生命值、攻击力和防御力+40%",
        "“现在你是我们的一员了，让我们熟悉彼此的气味。”",
        "“现在你是我们的一员了，让我们熟悉彼此的气味。”",
        sourceRule("应急助力节点中招募所需源石锭-50%，应急干员的生命值、攻击力和防御力+40%"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_CALAMITY_FIRESTAFF() {
    return collectible(
        "calamity_firestaff",
        "厄运火杆",
        "与“居民”作战时，敌人生命-40%，且作战胜利后所有干员的攻击速度永久+10",
        "与“居民”作战时，敌人生命-40%，且作战胜利后所有干员的攻击速度永久+10",
        "“居民”们挥舞着手里的棍棒和石头，还不知道自己对付不了会冒烟的杆子。",
        "“居民”们挥舞着手里的棍棒和石头，还不知道自己对付不了会冒烟的杆子。",
        sourceRule("与“居民”作战时，敌人生命-40%，且作战胜利后所有干员的攻击速度永久+10"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_HUNTER_S_MARK() {
    return collectible(
        "hunter_s_mark",
        "猎印",
        "猎犬proto的攻击力-50%；每拥有1层【探测先锋】，我方法术抗性+10",
        "猎犬proto的攻击力-50%；每拥有1层【探测先锋】，我方法术抗性+10",
        "这并非兽主的恐惧，只是祂施舍给你的片刻喘息。",
        "这并非兽主的恐惧，只是祂施舍给你的片刻喘息。",
        sourceRule("猎犬proto的攻击力-50%；每拥有1层【探测先锋】，我方法术抗性+10"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_ORIGINIUM_PRIVATE_KEY() {
    return collectible(
        "originium_private_key",
        "源私钥",
        "立刻获得99源石锭，在部分事件中能发挥特殊的作用",
        "立刻获得99源石锭，在部分事件中能发挥特殊的作用",
        "一张珍贵的权限卡片，记住，它会以你的意志为转移。",
        "一张珍贵的权限卡片，记住，它会以你的意志为转移。",
        sourceRule("立刻获得99源石锭，在部分事件中能发挥特殊的作用"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BLACK_CURRENT_TREE_SEA_NOTES() {
    return collectible(
        "black_current_tree_sea_notes",
        "黑流树海笔记",
        "每探索一种新节点叠加1层，每层使作战后获得收藏品的概率提升",
        "每探索一种新节点叠加1层，每层使作战后获得收藏品的概率提升",
        "一本被反复增加过内容的笔记本，纸张上不同的字体说明它已几经易手。它似乎永远在等待着下一位记录者。",
        "一本被反复增加过内容的笔记本，纸张上不同的字体说明它已几经易手。它似乎永远在等待着下一位记录者。",
        sourceRule("每探索一种新节点叠加1层，每层使作战后获得收藏品的概率提升"),
        Rarity.RARE
    );
  }

  private static CollectibleBuilder create_BASIC_IOT_TERMINAL() {
    return collectible(
        "basic_iot_terminal",
        "基础物联终端",
        "探索2个诡意行商或秘境行商后，获得【高级物联终端】",
        "探索2个诡意行商或秘境行商后，获得【高级物联终端】",
        "据说是一个可将万物连接起来的终端设备，从此与万物心意相通不再是问题。你难道不想知道你家的冰箱在想什么吗？",
        "据说是一个可将万物连接起来的终端设备，从此与万物心意相通不再是问题。你难道不想知道你家的冰箱在想什么吗？",
        sourceRule("探索2个诡意行商或秘境行商后，获得【高级物联终端】"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ADVANCED_IOT_TERMINAL() {
    return collectible(
        "advanced_iot_terminal",
        "高级物联终端",
        "干员部署后与攻击范围内一名干员用高级物联终端连接，敌人触碰高级物联终端时停顿，每0.5秒受到所有相连的干员攻击力之和50%的法术伤害",
        "干员部署后与攻击范围内一名干员用高级物联终端连接，敌人触碰高级物联终端时停顿，每0.5秒受到所有相连的干员攻击力之和50%的法术伤害",
        "经过改造的终端，可连接范围指数级扩大，立起接收杆即可立刻体验心意相通！",
        "经过改造的终端，可连接范围指数级扩大，立起接收杆即可立刻体验心意相通！",
        sourceRule("干员部署后与攻击范围内一名干员用高级物联终端连接，敌人触碰高级物联终端时停顿，每0.5秒受到所有相连的干员攻击力之和50%的法术伤害"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_PLANT_PULP() {
    return collectible(
        "plant_pulp",
        "植浆",
        "完成1场【追猎】作战后，获得【犬植浆】",
        "完成1场【追猎】作战后，获得【犬植浆】",
        "以黑流树海中的树木汁液为基底制作的简易药水，可以掩盖自身的气味。",
        "以黑流树海中的树木汁液为基底制作的简易药水，可以掩盖自身的气味。",
        sourceRule("完成1场【追猎】作战后，获得【犬植浆】"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HOUND_PLANT_PULP() {
    return collectible(
        "hound_plant_pulp",
        "犬植浆",
        "干员攻击范围内的其他干员获得迷彩，攻击力+10%（可叠加）",
        "干员攻击范围内的其他干员获得迷彩，攻击力+10%（可叠加）",
        "混入了猎犬血液的植浆，可以最大程度掩盖自身的气味。",
        "混入了猎犬血液的植浆，可以最大程度掩盖自身的气味。",
        sourceRule("干员攻击范围内的其他干员获得迷彩，攻击力+10%（可叠加）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_REMINISCENCE() {
    return collectible(
        "reminiscence",
        "追忆",
        "进入下一区域时，变为重新进入本区域，之后该收藏品失效",
        "进入下一区域时，变为重新进入本区域，之后该收藏品失效",
        "顺着黑流，我将回到尚有余温的襁褓，回到这片大地仍可被唤作“婴孩”的旧日时光。",
        "顺着黑流，我将回到尚有余温的襁褓，回到这片大地仍可被唤作“婴孩”的旧日时光。",
        sourceRule("进入下一区域时，变为重新进入本区域，之后该收藏品失效"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_RAPPELLING_RAID_DEVICE() {
    return collectible(
        "rappelling_raid_device",
        "垂降劫掠装置",
        "我方干员的再部署时间-50%，部署时对随机1名敌人及周围8格的敌人造成攻击力1000%的物理伤害",
        "我方干员的再部署时间-50%，部署时对随机1名敌人及周围8格的敌人造成攻击力1000%的物理伤害",
        "哥伦比亚军方最新研发的机动装备，能让特种兵们从天而降，一瞬间完成对重要战略目标的打击！“我们可以用这种方式解决大地上的一切麻烦，真是太棒了！”",
        "哥伦比亚军方最新研发的机动装备，能让特种兵们从天而降，一瞬间完成对重要战略目标的打击！“我们可以用这种方式解决大地上的一切麻烦，真是太棒了！”",
        sourceRule("我方干员的再部署时间-50%，部署时对随机1名敌人及周围8格的敌人造成攻击力1000%的物理伤害"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_REGISTER_OF_HATED_NAMES() {
    return collectible(
        "register_of_hated_names",
        "仇名录",
        "我方干员的攻击力+2%，每击倒一名敌人永久额外+0.2%（最高+200%）",
        "我方干员的攻击力+2%，每击倒一名敌人永久额外+0.2%（最高+200%）",
        "它属于一位没有名字的大炎剑客。“永远都差一个。”",
        "它属于一位没有名字的大炎剑客。“永远都差一个。”",
        sourceRule("我方干员的攻击力+2%，每击倒一名敌人永久额外+0.2%（最高+200%）"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SAND_TABLE_ALPHA() {
    return collectible(
        "sand_table_alpha",
        "沙盘α",
        "我方单位部署费用+2，让探索开启不同的方向，同时持有【沙盘β】可以揭示源阶方的所在位置",
        "我方单位部署费用+2，让探索开启不同的方向，同时持有【沙盘β】可以揭示源阶方的所在位置",
        "来自梅兰德基金会的特殊信息，保密等级相当高。沙盘上显示的区域似乎极难深入，或许只能根据另一方的线索二次定位后找到她。",
        "来自梅兰德基金会的特殊信息，保密等级相当高。沙盘上显示的区域似乎极难深入，或许只能根据另一方的线索二次定位后找到她。",
        sourceRule("我方单位部署费用+2，让探索开启不同的方向，同时持有【沙盘β】可以揭示源阶方的所在位置"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_SAND_TABLE_BETA() {
    return collectible(
        "sand_table_beta",
        "沙盘β",
        "我方单位部署费用+3，让探索开启不同的方向，同时持有【沙盘α】可以揭示源阶方的所在位置",
        "我方单位部署费用+3，让探索开启不同的方向，同时持有【沙盘α】可以揭示源阶方的所在位置",
        "来自联合政府的特殊信息，保密等级相当高。沙盘上显示的区域似乎极难深入，或许只能根据另一方的线索二次定位后找到她。",
        "来自联合政府的特殊信息，保密等级相当高。沙盘上显示的区域似乎极难深入，或许只能根据另一方的线索二次定位后找到她。",
        sourceRule("我方单位部署费用+3，让探索开启不同的方向，同时持有【沙盘α】可以揭示源阶方的所在位置"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_HEART_STIRRING_BEACON() {
    return collectible(
        "heart_stirring_beacon",
        "怦然信标",
        "所有敌人的生命+20%，我方单位的生命-40%，让探索开启不同的方向",
        "所有敌人的生命+20%，我方单位的生命-40%，让探索开启不同的方向",
        "一颗被时刻监测着的心脏，似乎正在夺走你的力量。调整至某个方向后，它微弱地跳动了起来。",
        "一颗被时刻监测着的心脏，似乎正在夺走你的力量。调整至某个方向后，它微弱地跳动了起来。",
        partialRule("所有敌人的生命+20%，我方单位的生命-40%，让探索开启不同的方向", stats -> stats.addEnemySpawnStatEffect((enemy, enemyStats) -> enemyStats.multiplyMaxHealth(0.2))),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BRING_DOWN_THE_GOD() {
    return collectible(
        "bring_down_the_god",
        "击坠“神明”",
        "可以在特殊节点中，使“玻利瓦尔，症结之核”首次释放“灾厄之口”时不摧毁“危朽”",
        "可以在特殊节点中，使“玻利瓦尔，症结之核”首次释放“灾厄之口”时不摧毁“危朽”",
        "日志：未确认的异常个体入侵，非预期的错误。监测到对居民与建筑的破坏行为，严重不利于后续发展，优先确保乌托邦依照原计划正常运行，阻击目标。",
        "日志：未确认的异常个体入侵，非预期的错误。监测到对居民与建筑的破坏行为，严重不利于后续发展，优先确保乌托邦依照原计划正常运行，阻击目标。",
        sourceRule("可以在特殊节点中，使“玻利瓦尔，症结之核”首次释放“灾厄之口”时不摧毁“危朽”"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_ANNIHILATE_THE_LIGHT() {
    return collectible(
        "annihilate_the_light",
        "湮没“光明”",
        "可以在特殊节点中，使“玻利瓦尔，症结之核”的“灾厄之口”伤害降低",
        "可以在特殊节点中，使“玻利瓦尔，症结之核”的“灾厄之口”伤害降低",
        "日志：未确认的异常个体入侵，非预期的错误。监测到非自然的灾害肆虐，严重不利于后续发展，优先确保乌托邦依照原计划正常运行，阻击目标。",
        "日志：未确认的异常个体入侵，非预期的错误。监测到非自然的灾害肆虐，严重不利于后续发展，优先确保乌托邦依照原计划正常运行，阻击目标。",
        sourceRule("可以在特殊节点中，使“玻利瓦尔，症结之核”的“灾厄之口”伤害降低"),
        Rarity.EPIC
    );
  }

  private static CollectibleBuilder create_BURN_DOWN_CIVILIZATION() {
    return collectible(
        "burn_down_civilization",
        "焚毁“文明”",
        "可以在特殊节点中，使“玻利瓦尔，症结之核”的“灾厄之口”无法封锁待部署区",
        "可以在特殊节点中，使“玻利瓦尔，症结之核”的“灾厄之口”无法封锁待部署区",
        "日志：未确认的异常个体入侵，非预期的错误。监测到对认知根基的干涉，严重不利于后续发展，优先确保乌托邦依照原计划正常运行，阻击目标。",
        "日志：未确认的异常个体入侵，非预期的错误。监测到对认知根基的干涉，严重不利于后续发展，优先确保乌托邦依照原计划正常运行，阻击目标。",
        sourceRule("可以在特殊节点中，使“玻利瓦尔，症结之核”的“灾厄之口”无法封锁待部署区"),
        Rarity.EPIC
    );
  }

  // END GENERATED PRTS ADDITIONAL COLLECTIBLES

  public static final List<CollectibleBuilder> ALL = List.copyOf(Zinecraft.COLLECTIBLES.entries);

  static {
  }

  static {
    if (ALL.size() != EXPECTED_COUNT) {
      throw new IllegalStateException(
          "集成战略去重藏品应注册 " + EXPECTED_COUNT + " 件，实际为 " + ALL.size() + " 件"
      );
    }
  }

  private ModCollectible() {
  }

  private static CollectibleBuilder collectible(
      String path,
      String zhCn,
      String originalEffectZhCn,
      String originalEffectEnUs,
      String descriptionZhCn,
      String descriptionEnUs,
      PowerDefinition effect,
      Rarity rarity
  ) {
    return new CollectibleBuilder(Zinecraft.COLLECTIBLES, path, zhCn)
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

  /** 原规则已完整落地为运行时属性，不再在运行时解析 PRTS 文本。 */
  private static PowerDefinition implementedRule(String originalRule, CollectiblePower power) {
    return effect(
        "已实现藏品原始规则中的可执行效果",
        "Implements the executable effects from the original collectible rule",
        power
    );
  }

  /** 探索属性已登记，等待对应的集成战略探索结算运行时消费。 */
  private static PowerDefinition registeredRule(String originalRule, CollectiblePower power) {
    return explorationRule(originalRule, power);
  }

  /** 仅实现能够忠实映射的部分，其余原规则必须继续进入未实现清单。 */
  private static PowerDefinition partialRule(String originalRule, CollectiblePower power) {
    return effect(
        "已实现原始规则中可忠实映射的部分；其余部分等待专用运行时",
        "Implements the faithfully mapped portion; the remainder requires a dedicated runtime",
        power,
        List.of(originalRule)
    );
  }

  private static TierDefinition tier(String condition, PowerDefinition definition) {
    return new TierDefinition(condition, definition);
  }

  /** 将 PRTS 的每一档条件与效果保留为独立分支，由 CombatStat 当前档位选择。 */
  private static PowerDefinition tieredRule(TierDefinition... tiers) {
    if (tiers.length == 0) throw new IllegalArgumentException("多档藏品至少需要一档效果");
    CollectiblePower[] powers = new CollectiblePower[tiers.length];
    List<String> sourceRules = new java.util.ArrayList<>();
    for (int index = 0; index < tiers.length; index++) {
      TierDefinition tier = tiers[index];
      powers[index] = tier.definition().power();
      if (tier.definition().sourceRules().isEmpty()) {
        sourceRules.add(tier.condition());
      } else {
        tier.definition().sourceRules().forEach(rule ->
            sourceRules.add("【" + tier.condition() + "】" + rule));
      }
    }
    return effect(
        "根据集成战略运行时提供的特殊条件档位激活对应效果",
        "Activates the matching effect tier from the Integrated Strategies runtime condition",
        CollectiblePower.tiered(powers),
        sourceRules
    );
  }

  private static CombatStat enemyCoreStats(CombatStat stats, double bonus) {
    return stats.multiplyAttack(bonus).multiplyDefense(bonus).multiplyMaxHealth(bonus);
  }

  private static PowerDefinition professionRule(
      SkillProfession profession,
      PowerDefinition definition
  ) {
    return effect(
        definition.zhCn(),
        definition.enUs(),
        forProfession(profession, definition.power()),
        definition.sourceRules()
    );
  }

  private static CollectiblePower forProfession(
      SkillProfession profession,
      CollectiblePower power
  ) {
    return stats -> stats.addProfessionEffect(profession, power::apply);
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

  private static PowerDefinition ingotAttackSpeed(int attackSpeedPerFiveIngots) {
    return effect(
        "每5源石锭攻击速度+" + attackSpeedPerFiveIngots,
        "+" + attackSpeedPerFiveIngots + " ASPD per 5 Originium Ingots",
        stats -> stats.addPerSecondConditionalEffect(current -> current.addAttackSpeed(
            Math.floorDiv(Math.max(0, current.originiumIngots()), 5) * attackSpeedPerFiveIngots
        ))
    );
  }
  private record PowerDefinition(String zhCn, String enUs, CollectiblePower power, List<String> sourceRules) {
  }

  private record TierDefinition(String condition, PowerDefinition definition) {
  }

  public static void bootstrap() {
  }
}

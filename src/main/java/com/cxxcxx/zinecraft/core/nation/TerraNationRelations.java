package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.nation.*;
import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.registry.ModNation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class TerraNationRelations {
  public static final NationRelationshipNetwork NETWORK = new NationRelationshipNetwork(
      Zinecraft.NATIONS.entries(), states(), relations()
  );

  private TerraNationRelations() {
  }

  public static void bootstrap() {
  }

  private static List<NationState> states() {
    return List.of(
        state(ModNation.AEGIR, 72, 50, 80, 10, 15),
        state(ModNation.BOLIVAR, 35, 20, 45, 55, 55),
        state(ModNation.HIGASHI, 62, 40, 60, 45, 40),
        state(ModNation.DURIN, 82, 80, 35, 55, 15),
        state(ModNation.COLUMBIA, 88, 72, 85, 90, 65),
        state(ModNation.KAZIMIERZ, 78, 65, 72, 75, 45),
        state(ModNation.KAZDEL, 30, 30, 88, 35, 70),
        state(ModNation.LATERANO, 85, 88, 78, 82, 12),
        state(ModNation.LEITHANIEN, 80, 70, 82, 65, 35),
        state(ModNation.RIM_BILLITON, 72, 75, 45, 85, 20),
        state(ModNation.MINOS, 60, 68, 62, 55, 30),
        state(ModNation.SARGON, 65, 60, 70, 70, 40),
        state(ModNation.SAMI, 38, 70, 75, 25, 15),
        state(ModNation.VICTORIA, 76, 40, 90, 72, 55),
        state(ModNation.URSUS, 55, 38, 92, 30, 85),
        state(ModNation.KJERAG, 58, 72, 48, 52, 20),
        state(ModNation.SIRACUSA, 70, 55, 65, 72, 45),
        state(ModNation.SIESTA, 50, 50, 50, 50, 50),
        state(ModNation.YAN, 88, 88, 90, 60, 20),
        state(ModNation.IBERIA, 32, 52, 65, 15, 25)
    );
  }

  private static List<NationRelation> relations() {
    List<NationRelation> relations = new ArrayList<>();

    NationRelationEvidence bolivar = evidence(
        "https://prts.wiki/w/泰拉大典:地理/玻利瓦尔",
        "玻利瓦尔处于哥伦比亚与莱塔尼亚支持的政权长期割据之中。"
    );
    relations.addAll(mutual(
        ModNation.COLUMBIA, ModNation.BOLIVAR,
        -55, 72, 25, 85, -65, bolivar,
        NationRelationTag.ACTIVE_CONFLICT, NationRelationTag.PROXY_CONFLICT
    ));
    relations.addAll(mutual(
        ModNation.LEITHANIEN, ModNation.BOLIVAR,
        25, 22, 55, 55, 15, bolivar,
        NationRelationTag.INVESTMENT, NationRelationTag.PROXY_CONFLICT
    ));
    relations.addAll(mutual(
        ModNation.COLUMBIA, ModNation.LEITHANIEN,
        -40, 52, 35, 72, -45, bolivar,
        NationRelationTag.PROXY_CONFLICT
    ));

    NationRelationEvidence ursus = evidence(
        "https://prts.wiki/w/泰拉大典:地理/乌萨斯",
        "乌萨斯长期向卡西米尔和萨米扩张，并在血峰战役中进攻东国。"
    );
    relations.addAll(mutual(
        ModNation.URSUS, ModNation.KAZIMIERZ,
        -65, 78, 12, 85, -72, ursus,
        NationRelationTag.HISTORIC_WAR, NationRelationTag.BORDER_TENSION
    ));
    relations.addAll(mutual(
        ModNation.URSUS, ModNation.SAMI,
        -58, 72, 8, 80, -68, ursus,
        NationRelationTag.HISTORIC_WAR, NationRelationTag.BORDER_TENSION
    ));
    relations.addAll(mutual(
        ModNation.URSUS, ModNation.HIGASHI,
        -60, 68, 12, 82, -70, ursus,
        NationRelationTag.HISTORIC_WAR, NationRelationTag.BORDER_TENSION
    ));

    NationRelationEvidence victoria = evidence(
        "https://prts.wiki/w/泰拉大典:地理/维多利亚",
        "卡兹戴尔军事委员会进驻伦蒂尼姆并与维多利亚各方爆发战争；战后其残余势力撤离。"
    );
    relations.addAll(mutual(
        ModNation.VICTORIA, ModNation.KAZDEL,
        -65, 48, 8, 72, -78, victoria,
        NationRelationTag.HISTORIC_WAR, NationRelationTag.OCCUPATION
    ));

    NationRelationEvidence iberia = evidence(
        "https://prts.wiki/w/泰拉大典:地理/伊比利亚",
        "阿戈尔技术曾推动伊比利亚繁荣；大静谧后伊比利亚严格限制并打击岛民。"
    );
    relations.add(relation(
        ModNation.IBERIA, ModNation.AEGIR,
        -48, 24, 12, 68, -62, iberia,
        NationRelationTag.TECHNOLOGY_TRANSFER, NationRelationTag.DIPLOMATIC_EXCLUSION
    ));
    relations.add(relation(
        ModNation.AEGIR, ModNation.IBERIA,
        -25, 10, 18, 52, -45, iberia,
        NationRelationTag.TECHNOLOGY_TRANSFER
    ));

    NationRelationEvidence laterano = evidence(
        "https://prts.wiki/w/拉特兰",
        "拉特兰长期保持中立并充当国际见证与调解方，其宗教也影响伊比利亚。"
    );
    relations.addAll(mutual(
        ModNation.LATERANO, ModNation.IBERIA,
        20, 4, 42, 22, 18, laterano,
        NationRelationTag.RELIGIOUS_TIES, NationRelationTag.MEDIATION
    ));

    NationRelationEvidence lateranoKazdel = evidence(
        "https://prts.wiki/w/拉特兰",
        "拉特兰现行制度禁止萨卡兹入境。"
    );
    relations.add(relation(
        ModNation.LATERANO, ModNation.KAZDEL,
        -72, 18, 2, 75, -80, lateranoKazdel,
        NationRelationTag.DIPLOMATIC_EXCLUSION
    ));

    NationRelationEvidence kjerag = evidence(
        "https://prts.wiki/w/泰拉大典:地理/谢拉格",
        "维多利亚贵族曾支持谢拉格改革派；哥伦比亚、莱塔尼亚和雷姆必拓商人参与对谢拉格投资。"
    );
    relations.addAll(mutual(
        ModNation.KJERAG, ModNation.VICTORIA,
        38, 6, 62, 16, 32, kjerag,
        NationRelationTag.INVESTMENT, NationRelationTag.TRADE
    ));
    relations.addAll(mutual(
        ModNation.KJERAG, ModNation.COLUMBIA,
        32, 8, 72, 20, 25, kjerag,
        NationRelationTag.INVESTMENT, NationRelationTag.TRADE
    ));
    relations.addAll(mutual(
        ModNation.KJERAG, ModNation.LEITHANIEN,
        28, 6, 65, 16, 24, kjerag,
        NationRelationTag.INVESTMENT, NationRelationTag.TRADE
    ));
    relations.addAll(mutual(
        ModNation.KJERAG, ModNation.RIM_BILLITON,
        42, 4, 80, 12, 38, kjerag,
        NationRelationTag.INVESTMENT, NationRelationTag.TRADE
    ));

    NationRelationEvidence columbia = evidence(
        "https://prts.wiki/w/泰拉大典:地理/哥伦比亚",
        "哥伦比亚独立自维多利亚开拓区，双方在开拓区财产与军事存在问题上关系紧张。"
    );
    relations.addAll(mutual(
        ModNation.COLUMBIA, ModNation.VICTORIA,
        -28, 28, 62, 58, -30, columbia,
        NationRelationTag.TRADE, NationRelationTag.BORDER_TENSION
    ));

    return List.copyOf(relations);
  }

  private static NationState state(
      NationBuilder nation,
      int prosperity,
      int stability,
      int military,
      int openness,
      int aggression
  ) {
    return new NationState(nation, prosperity, stability, military, openness, aggression);
  }

  private static NationRelationEvidence evidence(String url, String fact) {
    return new NationRelationEvidence(url, fact);
  }

  private static List<NationRelation> mutual(
      NationBuilder first,
      NationBuilder second,
      int favor,
      int warDesire,
      int trade,
      int tension,
      int trust,
      NationRelationEvidence evidence,
      NationRelationTag... tags
  ) {
    return List.of(
        relation(first, second, favor, warDesire, trade, tension, trust, evidence, tags),
        relation(second, first, favor, warDesire, trade, tension, trust, evidence, tags)
    );
  }

  private static NationRelation relation(
      NationBuilder from,
      NationBuilder to,
      int favor,
      int warDesire,
      int trade,
      int tension,
      int trust,
      NationRelationEvidence evidence,
      NationRelationTag... tags
  ) {
    return new NationRelation(
        from,
        to,
        favor,
        warDesire,
        trade,
        tension,
        trust,
        Set.of(tags),
        evidence.factSummary(),
        evidence
    );
  }
}

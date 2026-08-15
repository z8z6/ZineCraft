package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.nation.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TerraNationRelations {
  @NotNull
  public static final TerraNationRelations INSTANCE = new TerraNationRelations();
  @NotNull
  private static final NationRelationshipNetwork NETWORK = new NationRelationshipNetwork(INSTANCE.states(), INSTANCE.relations());

  private TerraNationRelations() {
  }

  @NotNull
  public final NationRelationshipNetwork getNETWORK() {
    return NETWORK;
  }

  private final List<NationState> states() {
    NationState[] nationStates = new NationState[]{
        this.state(TerraNation.AEGIR, 72, 50, 80, 10, 15),
        this.state(TerraNation.BOLIVAR, 35, 20, 45, 55, 55),
        this.state(TerraNation.HIGASHI, 62, 40, 60, 45, 40),
        this.state(TerraNation.DURIN, 82, 80, 35, 55, 15),
        this.state(TerraNation.COLUMBIA, 88, 72, 85, 90, 65),
        this.state(TerraNation.KAZIMIERZ, 78, 65, 72, 75, 45),
        this.state(TerraNation.KAZDEL, 30, 30, 88, 35, 70),
        this.state(TerraNation.LATERANO, 85, 88, 78, 82, 12),
        this.state(TerraNation.LEITHANIEN, 80, 70, 82, 65, 35),
        this.state(TerraNation.RIM_BILLITON, 72, 75, 45, 85, 20),
        this.state(TerraNation.MINOS, 60, 68, 62, 55, 30),
        this.state(TerraNation.SARGON, 65, 60, 70, 70, 40),
        this.state(TerraNation.SAMI, 38, 70, 75, 25, 15),
        this.state(TerraNation.VICTORIA, 76, 40, 90, 72, 55),
        this.state(TerraNation.URSUS, 55, 38, 92, 30, 85),
        this.state(TerraNation.KJERAG, 58, 72, 48, 52, 20),
        this.state(TerraNation.SIRACUSA, 70, 55, 65, 72, 45),
        this.state(TerraNation.YAN, 88, 88, 90, 60, 20),
        this.state(TerraNation.IBERIA, 32, 52, 65, 15, 25)
    };
    return java.util.List.of(nationStates);
  }

  private final List<NationRelation> relations() {
    List list = new ArrayList();
    List list1 = list;
    int i = 0;
    NationRelationEvidence nationRelationEvidence = INSTANCE.evidence("https://prts.wiki/w/泰拉大典:地理/玻利瓦尔", "玻利瓦尔处于哥伦比亚与莱塔尼亚支持的政权长期割据之中。");
    TerraNationRelations terraNationRelations = INSTANCE;
    TerraNation terraNation = TerraNation.COLUMBIA;
    TerraNation terraNation1 = TerraNation.BOLIVAR;
    NationRelationTag[] ursus = new NationRelationTag[]{NationRelationTag.ACTIVE_CONFLICT, NationRelationTag.PROXY_CONFLICT};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, -55, 72, 25, 85, -65, nationRelationEvidence, ursus));
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.LEITHANIEN;
    terraNation1 = TerraNation.BOLIVAR;
    ursus = new NationRelationTag[]{NationRelationTag.INVESTMENT, NationRelationTag.PROXY_CONFLICT};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, 25, 22, 55, 55, 15, nationRelationEvidence, ursus));
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.COLUMBIA;
    terraNation1 = TerraNation.LEITHANIEN;
    ursus = new NationRelationTag[]{NationRelationTag.PROXY_CONFLICT};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, -40, 52, 35, 72, -45, nationRelationEvidence, ursus));
    NationRelationEvidence nationRelationEvidence1 = INSTANCE.evidence("https://prts.wiki/w/泰拉大典:地理/乌萨斯", "乌萨斯长期向卡西米尔和萨米扩张，并在血峰战役中进攻东国。");
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.URSUS;
    terraNation1 = TerraNation.KAZIMIERZ;
    NationRelationTag[] londinium = new NationRelationTag[]{NationRelationTag.HISTORIC_WAR, NationRelationTag.BORDER_TENSION};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, -65, 78, 12, 85, -72, nationRelationEvidence1, londinium));
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.URSUS;
    terraNation1 = TerraNation.SAMI;
    londinium = new NationRelationTag[]{NationRelationTag.HISTORIC_WAR, NationRelationTag.BORDER_TENSION};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, -58, 72, 8, 80, -68, nationRelationEvidence1, londinium));
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.URSUS;
    terraNation1 = TerraNation.HIGASHI;
    londinium = new NationRelationTag[]{NationRelationTag.HISTORIC_WAR, NationRelationTag.BORDER_TENSION};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, -60, 68, 12, 82, -70, nationRelationEvidence1, londinium));
    NationRelationEvidence nationRelationEvidence2 = INSTANCE.evidence("https://prts.wiki/w/泰拉大典:地理/维多利亚", "卡兹戴尔军事委员会进驻伦蒂尼姆并与维多利亚各方爆发战争；战后其残余势力撤离。");
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.VICTORIA;
    terraNation1 = TerraNation.KAZDEL;
    NationRelationTag[] iberia = new NationRelationTag[]{NationRelationTag.HISTORIC_WAR, NationRelationTag.OCCUPATION};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, -65, 48, 8, 72, -78, nationRelationEvidence2, iberia));
    NationRelationEvidence nationRelationEvidence3 = INSTANCE.evidence("https://prts.wiki/w/泰拉大典:地理/伊比利亚", "阿戈尔技术曾推动伊比利亚繁荣；大静谧后伊比利亚严格限制并打击岛民。");
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.IBERIA;
    terraNation1 = TerraNation.AEGIR;
    NationRelationTag[] laterano = new NationRelationTag[]{NationRelationTag.TECHNOLOGY_TRANSFER, NationRelationTag.DIPLOMATIC_EXCLUSION};
    list1.add(terraNationRelations.relation(terraNation, terraNation1, -48, 24, 12, 68, -62, nationRelationEvidence3, laterano));
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.AEGIR;
    terraNation1 = TerraNation.IBERIA;
    laterano = new NationRelationTag[]{NationRelationTag.TECHNOLOGY_TRANSFER};
    list1.add(terraNationRelations.relation(terraNation, terraNation1, -25, 10, 18, 52, -45, nationRelationEvidence3, laterano));
    NationRelationEvidence nationRelationEvidence4 = INSTANCE.evidence("https://prts.wiki/w/拉特兰", "拉特兰长期保持中立并充当国际见证与调解方，其宗教也影响伊比利亚。");
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.LATERANO;
    terraNation1 = TerraNation.IBERIA;
    NationRelationTag[] lateranoKazdel = new NationRelationTag[]{NationRelationTag.RELIGIOUS_TIES, NationRelationTag.MEDIATION};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, 20, 4, 42, 22, 18, nationRelationEvidence4, lateranoKazdel));
    NationRelationEvidence nationRelationEvidence5 = INSTANCE.evidence("https://prts.wiki/w/拉特兰", "拉特兰现行制度禁止萨卡兹入境。");
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.LATERANO;
    terraNation1 = TerraNation.KAZDEL;
    NationRelationTag[] kjerag = new NationRelationTag[]{NationRelationTag.DIPLOMATIC_EXCLUSION};
    list1.add(terraNationRelations.relation(terraNation, terraNation1, -72, 18, 2, 75, -80, nationRelationEvidence5, kjerag));
    NationRelationEvidence nationRelationEvidence6 = INSTANCE.evidence("https://prts.wiki/w/泰拉大典:地理/谢拉格", "维多利亚贵族曾支持谢拉格改革派；哥伦比亚、莱塔尼亚和雷姆必拓商人参与对谢拉格投资。");
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.KJERAG;
    terraNation1 = TerraNation.VICTORIA;
    NationRelationTag[] columbia = new NationRelationTag[]{NationRelationTag.INVESTMENT, NationRelationTag.TRADE};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, 38, 6, 62, 16, 32, nationRelationEvidence6, columbia));
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.KJERAG;
    terraNation1 = TerraNation.COLUMBIA;
    columbia = new NationRelationTag[]{NationRelationTag.INVESTMENT, NationRelationTag.TRADE};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, 32, 8, 72, 20, 25, nationRelationEvidence6, columbia));
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.KJERAG;
    terraNation1 = TerraNation.LEITHANIEN;
    columbia = new NationRelationTag[]{NationRelationTag.INVESTMENT, NationRelationTag.TRADE};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, 28, 6, 65, 16, 24, nationRelationEvidence6, columbia));
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.KJERAG;
    terraNation1 = TerraNation.RIM_BILLITON;
    columbia = new NationRelationTag[]{NationRelationTag.INVESTMENT, NationRelationTag.TRADE};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, 42, 4, 80, 12, 38, nationRelationEvidence6, columbia));
    NationRelationEvidence nationRelationEvidence7 = INSTANCE.evidence("https://prts.wiki/w/泰拉大典:地理/哥伦比亚", "哥伦比亚独立自维多利亚开拓区，双方在开拓区财产与军事存在问题上关系紧张。");
    terraNationRelations = INSTANCE;
    terraNation = TerraNation.COLUMBIA;
    terraNation1 = TerraNation.VICTORIA;
    NationRelationTag[] nationRelationTags7 = new NationRelationTag[]{NationRelationTag.TRADE, NationRelationTag.BORDER_TENSION};
    list1.addAll(terraNationRelations.mutual(terraNation, terraNation1, -28, 28, 62, 58, -30, nationRelationEvidence7, nationRelationTags7));
    return List.copyOf(list);
  }

  private final NationState state(TerraNation nation, int prosperity, int stability, int military, int openness, int aggression) {
    return new NationState(nation, prosperity, stability, military, openness, aggression);
  }

  private final NationRelationEvidence evidence(String url, String fact) {
    return new NationRelationEvidence(url, fact);
  }

  private final List<NationRelation> mutual(
      TerraNation first,
      TerraNation second,
      int favor,
      int warDesire,
      int trade,
      int tension,
      int trust,
      NationRelationEvidence evidence,
      NationRelationTag... tags
  ) {
    NationRelation[] nationRelations = new NationRelation[]{
        this.relation(first, second, favor, warDesire, trade, tension, trust, evidence, Arrays.copyOf(tags, tags.length)),
        this.relation(second, first, favor, warDesire, trade, tension, trust, evidence, Arrays.copyOf(tags, tags.length))
    };
    return java.util.List.of(nationRelations);
  }

  private final NationRelation relation(
      TerraNation from, TerraNation to, int favor, int warDesire, int trade, int tension, int trust, NationRelationEvidence evidence, NationRelationTag... tags
  ) {
    return new NationRelation(from, to, favor, warDesire, trade, tension, trust, java.util.Set.of(tags), evidence.getFactSummary(), evidence);
  }
}

package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.MusicDiscBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;

public final class ModSound {
  public static final MusicDiscBuilder AMBIENT_PICTURES_OF_THE_PAST = musicDisc("pictures_of_the_past", 95.0F, "James Primate - Pictures of the Past");
  public static final MusicDiscBuilder AMBIENT_RANDOM_GODS = musicDisc("random_gods", 199.0F, "James Primate - Random Gods (Theme III)");
  public static final MusicDiscBuilder AMBIENT_STRANGER_THINK = musicDisc("stranger_think", 240.0F, "C418 - Stranger Think");
  public static final MusicDiscBuilder AMBIENT_LIFEFLOW = musicDisc("lifeflow", 196.0F, "Monster Siren Records - Lifeflow");
  public static final MusicDiscBuilder AMBIENT_PROCUREMENT_CENTER = musicDisc("procurement_center", 288.67F, "塞壬唱片-MSR - 采购中心");
  public static final MusicDiscBuilder AMBIENT_DABAIXU = musicDisc("dabaixu", 129.38F, "塞壬唱片-MSR - 大柏墟");
  public static final MusicDiscBuilder AMBIENT_JOURNEY_AHEAD = musicDisc("journey_ahead", 202.29F, "塞壬唱片-MSR - 旅途前方");
  public static final MusicDiscBuilder AMBIENT_HUMANITY = musicDisc("humanity", 190.62F, "塞壬唱片-MSR - 人性");
  public static final MusicDiscBuilder AMBIENT_AS_I_SEE_IT = musicDisc("as_i_see_it", 298.48F, "塞壬唱片-MSR - 如我所见");
  public static final MusicDiscBuilder AMBIENT_ESCAPE_PART_2 = musicDisc("escape_part_2", 182.13F, "塞壬唱片-MSR - 逃亡 part2");

  private ModSound() {
  }

  private static MusicDiscBuilder musicDisc(String path, float length, String description) {
    return new MusicDiscBuilder(Zinecraft.SOUNDS, Zinecraft.ITEMS, path, length, description).build();
  }

  public static void bootstrap() {
  }
}

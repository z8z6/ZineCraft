package com.cxxcxx.zinecraft.core.weapon;

import com.cxxcxx.zinecraft.api.item.ItemCatalog;
import com.cxxcxx.zinecraft.api.item.ItemEntry;
import com.cxxcxx.zinecraft.api.weapon.*;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import com.cxxcxx.zinecraft.api.weapon.action.firearm.FirearmFireAction;
import com.cxxcxx.zinecraft.api.weapon.action.firearm.FirearmReloadAction;
import com.cxxcxx.zinecraft.api.weapon.action.firearm.ToggleAimAction;
import com.cxxcxx.zinecraft.api.weapon.action.melee.MeleeAttackAction;
import com.cxxcxx.zinecraft.api.weapon.action.staff.CastSkillAction;
import com.cxxcxx.zinecraft.api.weapon.item.ActionWeaponItem;
import com.cxxcxx.zinecraft.api.weapon.item.FirearmItem;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

public final class ModWeapons {
  @NotNull
  public static final ModWeapons INSTANCE = new ModWeapons();
  private static final int TEST_RIFLE_CAPACITY = 12;
  private static final int LIGHT_ATTACK_DURATION = 20;
  @NotNull
  public static final ItemEntry<SwordItem> TEST_SWORD_ITEM = ItemCatalog.registerWithDefaults(
      Zinecraft.ITEMS, "test_sword", "测试剑", "Test Sword", VANILLA_IRON_SWORD_MODEL, false, ModWeapons::TEST_SWORD_ITEMHelper0, 16, null
  );
  @NotNull
  public static final ItemEntry<Item> TEST_CARTRIDGE = ItemCatalog.registerWithDefaults(
      Zinecraft.ITEMS, "test_cartridge", "测试弹药", "Test Cartridge", INSTANCE.vanillaModel("iron_nugget"), null, false, 48, null
  );
  @NotNull
  public static final ItemEntry<FirearmItem> TEST_RIFLE_ITEM = ItemCatalog.registerWithDefaults(
      Zinecraft.ITEMS,
      "test_rifle",
      "测试步枪",
      "Test Rifle",
      INSTANCE.vanillaModel("crossbow"),
      false,
      ModWeapons::TEST_RIFLE_ITEMHelper0,
      16,
      null
  );
  @NotNull
  public static final ItemEntry<ActionWeaponItem> TEST_STAFF_ITEM = ItemCatalog.registerWithDefaults(
      Zinecraft.ITEMS,
      "test_staff",
      "测试法杖",
      "Test Staff",
      INSTANCE.vanillaModel("blaze_rod"),
      false,
      ModWeapons::TEST_STAFF_ITEMHelper0,
      16,
      null
  );
  @NotNull
  public static final WeaponDefinition TEST_SWORD;
  @NotNull
  public static final WeaponDefinition TEST_RIFLE;
  @NotNull
  public static final WeaponDefinition TEST_STAFF;
  @NotNull
  private static final ResourceLocation LIGHT_ATTACK_ID = Zinecraft.REGISTRAR.id("light_attack");
  @NotNull
  public static final MeleeAttackAction LIGHT_ATTACK = new MeleeAttackAction(LIGHT_ATTACK_ID, 7, 20, 7.0F, 3.25, 100.0);
  @NotNull
  private static final ResourceLocation PLAYER_LIGHT_ATTACK = Zinecraft.REGISTRAR.id("animation/player/light_attack");
  @NotNull
  private static final ResourceLocation WEAPON_LIGHT_ATTACK = Zinecraft.REGISTRAR.id("animation/weapon/test_sword_light_attack");
  @NotNull
  private static final ResourceLocation TEST_SWORD_TRAIL = Zinecraft.REGISTRAR.id("weapon/sword_slash");
  @NotNull
  private static final ResourceLocation TEST_SWORD_IMPACT = Zinecraft.REGISTRAR.id("vfx/test_sword_impact");
  @NotNull
  private static final ResourceLocation TEST_SWORD_SWING = Zinecraft.REGISTRAR.id("sound/test_sword_swing");
  @NotNull
  private static final ResourceLocation FIRE_ID = Zinecraft.REGISTRAR.id("test_rifle_fire");
  @NotNull
  public static final FirearmFireAction FIRE = new FirearmFireAction(FIRE_ID, 2, 10, 6.0F, 48.0);
  @NotNull
  private static final ResourceLocation RELOAD_ID = Zinecraft.REGISTRAR.id("test_rifle_reload");
  @NotNull
  public static final FirearmReloadAction RELOAD = new FirearmReloadAction(RELOAD_ID, 24, 32, 12, TEST_CARTRIDGE);
  @NotNull
  private static final ResourceLocation TOGGLE_AIM_ID = Zinecraft.REGISTRAR.id("test_rifle_toggle_aim");
  @NotNull
  private static final ModelTemplate VANILLA_IRON_SWORD_MODEL = INSTANCE.vanillaModel("iron_sword");
  @NotNull
  public static final ToggleAimAction TOGGLE_AIM = new ToggleAimAction(TOGGLE_AIM_ID, 6);
  @NotNull
  private static final ResourceLocation ARCANE_CAST_ID = Zinecraft.REGISTRAR.id("test_staff_arcane_cast");
  @NotNull
  public static final CastSkillAction ARCANE_CAST = new CastSkillAction(
      ARCANE_CAST_ID, ModWeaponSkillEffects.ARCANE_BOLT, Zinecraft.SKILL_SERVICE, 5, 18
  );
  @NotNull
  private static final ResourceLocation HEAL_CAST_ID = Zinecraft.REGISTRAR.id("test_staff_heal_cast");
  @NotNull
  public static final CastSkillAction HEAL_CAST = new CastSkillAction(
      HEAL_CAST_ID, ModWeaponSkillEffects.MENDING_LIGHT, Zinecraft.SKILL_SERVICE, 10, 28
  );
  @NotNull
  private static final ResourceLocation RIFLE_MUZZLE = Zinecraft.REGISTRAR.id("vfx/test_rifle_muzzle");
  @NotNull
  private static final ResourceLocation RIFLE_IMPACT = Zinecraft.REGISTRAR.id("weapon/explosion");
  @NotNull
  private static final ResourceLocation RIFLE_FIRE_SOUND = Zinecraft.REGISTRAR.id("sound/test_rifle_fire");
  @NotNull
  private static final ResourceLocation RIFLE_RELOAD_SOUND = Zinecraft.REGISTRAR.id("sound/test_rifle_reload");
  @NotNull
  private static final ResourceLocation STAFF_ARCANE_CAST = Zinecraft.REGISTRAR.id("vfx/test_staff_arcane_cast");
  @NotNull
  private static final ResourceLocation STAFF_ARCANE_IMPACT = Zinecraft.REGISTRAR.id("vfx/test_staff_arcane_impact");
  @NotNull
  private static final ResourceLocation STAFF_HEAL = Zinecraft.REGISTRAR.id("weapon/healing");
  @NotNull
  private static final ResourceLocation STAFF_CAST_SOUND = Zinecraft.REGISTRAR.id("sound/test_staff_cast");

  static {
    ResourceLocation resourceLocation = Zinecraft.REGISTRAR.id("test_sword");
    Map map3 = com.cxxcxx.zinecraft.api.util.CollectionSupport.mapOf(Pair.of(WeaponInput.PRIMARY, LIGHT_ATTACK_ID));
    ResourceLocation resourceLocation1 = LIGHT_ATTACK_ID;
    ResourceLocation resourceLocation3 = PLAYER_LIGHT_ATTACK;
    ResourceLocation resourceLocation4 = WEAPON_LIGHT_ATTACK;
    TimedWeaponVfx[] _this_forEach_iv = new TimedWeaponVfx[]{new TimedWeaponVfx(TEST_SWORD_TRAIL, 4), new TimedWeaponVfx(TEST_SWORD_IMPACT, 8)};
    Map map = com.cxxcxx.zinecraft.api.util.CollectionSupport.mapOf(
        Pair.of(
            resourceLocation1,
            new WeaponPresentation(
                resourceLocation3,
                resourceLocation4,
                java.util.List.of(_this_forEach_iv),
                java.util.List.of(new TimedWeaponSound(TEST_SWORD_SWING, 4)),
                20
            )
        )
    );
    String string = "item.zinecraft.test_sword";
    TEST_SWORD = new WeaponDefinition(resourceLocation, map3, map, new WeaponMetadata(string));
    resourceLocation = Zinecraft.REGISTRAR.id("test_rifle");
    Pair[] pairs = new Pair[]{
        Pair.of(WeaponInput.PRIMARY, FIRE_ID), Pair.of(WeaponInput.SECONDARY, TOGGLE_AIM_ID), Pair.of(WeaponInput.RELOAD, RELOAD_ID)
    };
    map3 = com.cxxcxx.zinecraft.api.util.CollectionSupport.mapOf(pairs);
    Pair[] pairs1 = new Pair[3];
    ResourceLocation resourceLocation2 = FIRE_ID;
    ResourceLocation resourceLocation5 = Zinecraft.REGISTRAR.id("animation/player/rifle_fire");
    ResourceLocation resourceLocation6 = Zinecraft.REGISTRAR.id("animation/weapon/test_rifle_fire");
    TimedWeaponVfx[] timedWeaponVfxs1 = new TimedWeaponVfx[]{new TimedWeaponVfx(RIFLE_MUZZLE, 2), new TimedWeaponVfx(RIFLE_IMPACT, 3)};
    pairs1[0] = Pair.of(
        resourceLocation2,
        new WeaponPresentation(
            resourceLocation5, resourceLocation6, java.util.List.of(timedWeaponVfxs1), java.util.List.of(new TimedWeaponSound(RIFLE_FIRE_SOUND, 2)), 10
        )
    );
    pairs1[1] = Pair.of(
        RELOAD_ID,
        new WeaponPresentation(
            Zinecraft.REGISTRAR.id("animation/player/rifle_reload"),
            Zinecraft.REGISTRAR.id("animation/weapon/test_rifle_reload"),
            java.util.List.of(),
            java.util.List.of(new TimedWeaponSound(RIFLE_RELOAD_SOUND, 24)),
            32
        )
    );
    pairs1[2] = Pair.of(
        TOGGLE_AIM_ID, new WeaponPresentation(Zinecraft.REGISTRAR.id("animation/player/rifle_aim"), null, java.util.List.of(), java.util.List.of(), 6)
    );
    Map map1 = com.cxxcxx.zinecraft.api.util.CollectionSupport.mapOf(pairs1);
    String string1 = "item.zinecraft.test_rifle";
    TEST_RIFLE = new WeaponDefinition(resourceLocation, map3, map1, new WeaponMetadata(string1));
    resourceLocation = Zinecraft.REGISTRAR.id("test_staff");
    Pair[] pairs2 = new Pair[]{Pair.of(WeaponInput.PRIMARY, ARCANE_CAST_ID), Pair.of(WeaponInput.SECONDARY, HEAL_CAST_ID)};
    map3 = com.cxxcxx.zinecraft.api.util.CollectionSupport.mapOf(pairs2);
    Pair[] pairs3 = new Pair[2];
    resourceLocation2 = ARCANE_CAST_ID;
    resourceLocation5 = Zinecraft.REGISTRAR.id("animation/player/staff_cast");
    resourceLocation6 = Zinecraft.REGISTRAR.id("animation/weapon/test_staff_cast");
    timedWeaponVfxs1 = new TimedWeaponVfx[]{new TimedWeaponVfx(STAFF_ARCANE_CAST, 5), new TimedWeaponVfx(STAFF_ARCANE_IMPACT, 6)};
    pairs3[0] = Pair.of(
        resourceLocation2,
        new WeaponPresentation(
            resourceLocation5, resourceLocation6, java.util.List.of(timedWeaponVfxs1), java.util.List.of(new TimedWeaponSound(STAFF_CAST_SOUND, 5)), 18
        )
    );
    pairs3[1] = Pair.of(
        HEAL_CAST_ID,
        new WeaponPresentation(
            Zinecraft.REGISTRAR.id("animation/player/staff_heal"),
            null,
            java.util.List.of(new TimedWeaponVfx(STAFF_HEAL, 10)),
            java.util.List.of(new TimedWeaponSound(STAFF_CAST_SOUND, 10)),
            28
        )
    );
    Map map2 = com.cxxcxx.zinecraft.api.util.CollectionSupport.mapOf(pairs3);
    String string2 = "item.zinecraft.test_staff";
    TEST_STAFF = new WeaponDefinition(resourceLocation, map3, map2, new WeaponMetadata(string2));
    WeaponAction[] weaponActions = new WeaponAction[]{LIGHT_ATTACK, FIRE, RELOAD, TOGGLE_AIM, ARCANE_CAST, HEAL_CAST};
    Iterable iterable = java.util.List.of(weaponActions);
    WeaponRegistry weaponRegistry = Zinecraft.WEAPONS;
    int i = 0;

    for (Object object : iterable) {
      WeaponAction weaponAction = (WeaponAction) object;
      int j = 0;
      weaponRegistry.registerAction(weaponAction);
    }

    Zinecraft.TRANSLATIONS.add("item.zinecraft.firearm.ammo", "弹药：%s / %s", "Ammo: %s / %s");
    Zinecraft.TRANSLATIONS.add("item.zinecraft.firearm.aiming", "瞄准模式", "Aiming");
    Zinecraft.TRANSLATIONS.add("item.zinecraft.firearm.hip_fire", "腰射模式", "Hip Fire");
    Zinecraft.TRANSLATIONS.add("key.zinecraft.weapon_reload", "装填武器", "Reload Weapon");
    Zinecraft.TRANSLATIONS.add("key.categories.zinecraft.weapon", "Zinecraft 武器", "Zinecraft Weapons");
  }

  private ModWeapons() {
  }

  public void bindRegisteredItems() {
    Zinecraft.WEAPONS.register(TEST_SWORD_ITEM.getItem(), TEST_SWORD);
    Zinecraft.WEAPONS.register(TEST_RIFLE_ITEM.getItem(), TEST_RIFLE);
    Zinecraft.WEAPONS.register(TEST_STAFF_ITEM.getItem(), TEST_STAFF);
  }

  private static final SwordItem TEST_SWORD_ITEMHelper0() {
    return new SwordItem((Tier) Tiers.IRON, new Properties().attributes(SwordItem.createAttributes((Tier) Tiers.IRON, 3, -2.4F)));
  }

  private static final FirearmItem TEST_RIFLE_ITEMHelper0() {
    Properties properties = new Properties()
        .stacksTo(1)
        .component(WeaponStateComponents.INSTANCE.getAMMO(), 12)
        .component(WeaponStateComponents.INSTANCE.getAIMING(), false);
    return new FirearmItem(12, properties);
  }

  private static final ActionWeaponItem TEST_STAFF_ITEMHelper0() {
    Properties properties = new Properties().stacksTo(1);
    return new ActionWeaponItem(properties);
  }

  private final ModelTemplate vanillaModel(String path) {
    return new ModelTemplate(Optional.of(ResourceLocation.withDefaultNamespace("item/" + path)), Optional.empty(), new TextureSlot[0]);
  }

}

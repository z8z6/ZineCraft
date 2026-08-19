package com.cxxcxx.zinecraft.core.weapon;

import com.cxxcxx.zinecraft.api.util.CollectionSupport;
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
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.Optional;

public final class ModWeapons {
  private static final int TEST_RIFLE_CAPACITY = 12;
  private static final int LIGHT_ATTACK_DURATION = 20;

  public static final DeferredItem<Item> TEST_CARTRIDGE = Zinecraft.ITEMS
      .builder("test_cartridge", "测试弹药", "Test Cartridge",
          () -> new Item(new Item.Properties()), vanillaModel("iron_nugget"), true)
      .getItem();
  public static final DeferredItem<FirearmItem> TEST_RIFLE_ITEM = Zinecraft.ITEMS
      .builder("test_rifle", "测试步枪", "Test Rifle", ModWeapons::createTestRifle,
          vanillaModel("crossbow"), true)
      .getItem();
  public static final DeferredItem<ActionWeaponItem> TEST_STAFF_ITEM = Zinecraft.ITEMS
      .builder("test_staff", "测试法杖", "Test Staff", ModWeapons::createTestStaff,
          vanillaModel("blaze_rod"), true)
      .getItem();
  private static final ModelTemplate VANILLA_IRON_SWORD_MODEL = vanillaModel("iron_sword");
  public static final DeferredItem<SwordItem> TEST_SWORD_ITEM = Zinecraft.ITEMS
      .builder("test_sword", "测试剑", "Test Sword", ModWeapons::createTestSword,
          VANILLA_IRON_SWORD_MODEL, true)
      .getItem();
  private static final ResourceLocation LIGHT_ATTACK_ID = id("light_attack");
  public static final MeleeAttackAction LIGHT_ATTACK = new MeleeAttackAction(
      LIGHT_ATTACK_ID, 7, LIGHT_ATTACK_DURATION, 7.0F, 3.25, 100.0
  );
  private static final ResourceLocation FIRE_ID = id("test_rifle_fire");
  private static final ResourceLocation RELOAD_ID = id("test_rifle_reload");
  public static final FirearmReloadAction RELOAD = new FirearmReloadAction(
      RELOAD_ID, 24, 32, TEST_RIFLE_CAPACITY, TEST_CARTRIDGE
  );
  private static final ResourceLocation TOGGLE_AIM_ID = id("test_rifle_toggle_aim");
  private static final ResourceLocation ARCANE_CAST_ID = id("test_staff_arcane_cast");
  public static final FirearmFireAction FIRE = new FirearmFireAction(FIRE_ID, 2, 10, 6.0F, 48.0);
  private static final ResourceLocation HEAL_CAST_ID = id("test_staff_heal_cast");
  public static final ToggleAimAction TOGGLE_AIM = new ToggleAimAction(TOGGLE_AIM_ID, 6);
  public static final CastSkillAction ARCANE_CAST = new CastSkillAction(
      ARCANE_CAST_ID, ModWeaponSkillEffects.ARCANE_BOLT, Zinecraft.SKILL_SERVICE, 5, 18
  );
  public static final CastSkillAction HEAL_CAST = new CastSkillAction(
      HEAL_CAST_ID, ModWeaponSkillEffects.MENDING_LIGHT, Zinecraft.SKILL_SERVICE, 10, 28
  );
  private static final ResourceLocation PLAYER_LIGHT_ATTACK = id("animation/player/light_attack");
  private static final ResourceLocation WEAPON_LIGHT_ATTACK = id("animation/weapon/test_sword_light_attack");
  private static final ResourceLocation TEST_SWORD_TRAIL = id("weapon/sword_slash");
  private static final ResourceLocation TEST_SWORD_IMPACT = id("vfx/test_sword_impact");
  private static final ResourceLocation TEST_SWORD_SWING = id("sound/test_sword_swing");
  public static final WeaponDefinition TEST_SWORD = createSwordDefinition();
  private static final ResourceLocation RIFLE_MUZZLE = id("vfx/test_rifle_muzzle");
  private static final ResourceLocation RIFLE_IMPACT = id("weapon/explosion");
  private static final ResourceLocation RIFLE_FIRE_SOUND = id("sound/test_rifle_fire");
  private static final ResourceLocation RIFLE_RELOAD_SOUND = id("sound/test_rifle_reload");
  public static final WeaponDefinition TEST_RIFLE = createRifleDefinition();
  private static final ResourceLocation STAFF_ARCANE_CAST = id("vfx/test_staff_arcane_cast");
  private static final ResourceLocation STAFF_ARCANE_IMPACT = id("vfx/test_staff_arcane_impact");
  private static final ResourceLocation STAFF_HEAL = id("weapon/healing");
  private static final ResourceLocation STAFF_CAST_SOUND = id("sound/test_staff_cast");
  public static final WeaponDefinition TEST_STAFF = createStaffDefinition();

  static {
    List.<WeaponAction>of(LIGHT_ATTACK, FIRE, RELOAD, TOGGLE_AIM, ARCANE_CAST, HEAL_CAST)
        .forEach(Zinecraft.WEAPONS::registerAction);
    Zinecraft.TRANSLATIONS.add("item.zinecraft.firearm.ammo", "弹药：%s / %s", "Ammo: %s / %s");
    Zinecraft.TRANSLATIONS.add("item.zinecraft.firearm.aiming", "瞄准模式", "Aiming");
    Zinecraft.TRANSLATIONS.add("item.zinecraft.firearm.hip_fire", "腰射模式", "Hip Fire");
    Zinecraft.TRANSLATIONS.add("key.zinecraft.weapon_reload", "装填武器", "Reload Weapon");
    Zinecraft.TRANSLATIONS.add("key.categories.zinecraft.weapon", "Zinecraft 武器", "Zinecraft Weapons");
  }

  private ModWeapons() {
  }

  public static void bindRegisteredItems() {
    Zinecraft.WEAPONS.register(TEST_SWORD_ITEM.get(), TEST_SWORD);
    Zinecraft.WEAPONS.register(TEST_RIFLE_ITEM.get(), TEST_RIFLE);
    Zinecraft.WEAPONS.register(TEST_STAFF_ITEM.get(), TEST_STAFF);
  }

  private static WeaponDefinition createSwordDefinition() {
    return new WeaponDefinition(
        id("test_sword"),
        CollectionSupport.mapOf(Pair.of(WeaponInput.PRIMARY, LIGHT_ATTACK_ID)),
        CollectionSupport.mapOf(Pair.of(
            LIGHT_ATTACK_ID,
            new WeaponPresentation(
                PLAYER_LIGHT_ATTACK,
                WEAPON_LIGHT_ATTACK,
                List.of(new TimedWeaponVfx(TEST_SWORD_TRAIL, 4), new TimedWeaponVfx(TEST_SWORD_IMPACT, 8)),
                List.of(new TimedWeaponSound(TEST_SWORD_SWING, 4)),
                LIGHT_ATTACK_DURATION
            )
        )),
        new WeaponMetadata("item.zinecraft.test_sword")
    );
  }

  private static WeaponDefinition createRifleDefinition() {
    return new WeaponDefinition(
        id("test_rifle"),
        CollectionSupport.mapOf(
            Pair.of(WeaponInput.PRIMARY, FIRE_ID),
            Pair.of(WeaponInput.SECONDARY, TOGGLE_AIM_ID),
            Pair.of(WeaponInput.RELOAD, RELOAD_ID)
        ),
        CollectionSupport.mapOf(
            Pair.of(FIRE_ID, new WeaponPresentation(
                id("animation/player/rifle_fire"),
                id("animation/weapon/test_rifle_fire"),
                List.of(new TimedWeaponVfx(RIFLE_MUZZLE, 2), new TimedWeaponVfx(RIFLE_IMPACT, 3)),
                List.of(new TimedWeaponSound(RIFLE_FIRE_SOUND, 2)),
                10
            )),
            Pair.of(RELOAD_ID, new WeaponPresentation(
                id("animation/player/rifle_reload"),
                id("animation/weapon/test_rifle_reload"),
                List.of(),
                List.of(new TimedWeaponSound(RIFLE_RELOAD_SOUND, 24)),
                32
            )),
            Pair.of(TOGGLE_AIM_ID, new WeaponPresentation(
                id("animation/player/rifle_aim"), null, List.of(), List.of(), 6
            ))
        ),
        new WeaponMetadata("item.zinecraft.test_rifle")
    );
  }

  private static WeaponDefinition createStaffDefinition() {
    return new WeaponDefinition(
        id("test_staff"),
        CollectionSupport.mapOf(
            Pair.of(WeaponInput.PRIMARY, ARCANE_CAST_ID),
            Pair.of(WeaponInput.SECONDARY, HEAL_CAST_ID)
        ),
        CollectionSupport.mapOf(
            Pair.of(ARCANE_CAST_ID, new WeaponPresentation(
                id("animation/player/staff_cast"),
                id("animation/weapon/test_staff_cast"),
                List.of(new TimedWeaponVfx(STAFF_ARCANE_CAST, 5), new TimedWeaponVfx(STAFF_ARCANE_IMPACT, 6)),
                List.of(new TimedWeaponSound(STAFF_CAST_SOUND, 5)),
                18
            )),
            Pair.of(HEAL_CAST_ID, new WeaponPresentation(
                id("animation/player/staff_heal"),
                null,
                List.of(new TimedWeaponVfx(STAFF_HEAL, 10)),
                List.of(new TimedWeaponSound(STAFF_CAST_SOUND, 10)),
                28
            ))
        ),
        new WeaponMetadata("item.zinecraft.test_staff")
    );
  }

  private static SwordItem createTestSword() {
    return new SwordItem(Tiers.IRON, new Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F)));
  }

  private static FirearmItem createTestRifle() {
    return new FirearmItem(
        TEST_RIFLE_CAPACITY,
        new Properties()
            .stacksTo(1)
            .component(WeaponStateComponents.AMMO, TEST_RIFLE_CAPACITY)
            .component(WeaponStateComponents.AIMING, false)
    );
  }

  private static ActionWeaponItem createTestStaff() {
    return new ActionWeaponItem(new Properties().stacksTo(1));
  }

  private static ModelTemplate vanillaModel(String path) {
    return new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("item/" + path)),
        Optional.empty(),
        new TextureSlot[0]
    );
  }

  private static ResourceLocation id(String path) {
    return Zinecraft.REGISTRAR.id(path);
  }

  public static void bootstrap() {
  }
}

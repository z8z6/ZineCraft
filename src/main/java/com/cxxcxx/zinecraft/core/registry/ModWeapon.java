package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.combat.CombatDamageProfile;
import com.cxxcxx.zinecraft.api.combat.CombatDamageType;
import com.cxxcxx.zinecraft.api.registry.builder.ItemBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.WeaponActionBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.WeaponBuilder;
import com.cxxcxx.zinecraft.api.weapon.WeaponInput;
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
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.cxxcxx.zinecraft.core.registry.ModWeaponPresentation.*;

public final class ModWeapon {
  private static final int TEST_RIFLE_CAPACITY = 12;
  private static final int LIGHT_ATTACK_DURATION = 20;

  public static final DeferredItem<Item> TEST_CARTRIDGE = new ItemBuilder<>(
      Zinecraft.ITEMS, "test_cartridge", "测试弹药", "Test Cartridge",
          () -> new Item(new Item.Properties()), vanillaModel("iron_nugget"), true)
      .build()
      .getItem();

  public static final WeaponActionBuilder<MeleeAttackAction> LIGHT_ATTACK = action(
      "light_attack",
      actionId -> new MeleeAttackAction(
          actionId, 7, LIGHT_ATTACK_DURATION,
          CombatDamageProfile.flat(7.0, CombatDamageType.PHYSICAL), 3.25, 100.0
      )
  );
  public static final WeaponActionBuilder<FirearmReloadAction> RELOAD = action(
      "test_rifle_reload",
      actionId -> new FirearmReloadAction(actionId, 24, 32, TEST_RIFLE_CAPACITY, TEST_CARTRIDGE)
  );
  public static final WeaponActionBuilder<FirearmFireAction> FIRE = action(
      "test_rifle_fire",
      actionId -> new FirearmFireAction(
          actionId, 2, 10, CombatDamageProfile.flat(6.0, CombatDamageType.PHYSICAL), 48.0
      )
  );
  public static final WeaponActionBuilder<ToggleAimAction> TOGGLE_AIM = action(
      "test_rifle_toggle_aim",
      actionId -> new ToggleAimAction(actionId, 6)
  );
  public static final WeaponActionBuilder<CastSkillAction> ARCANE_CAST = action(
      "test_staff_arcane_cast",
      actionId -> new CastSkillAction(
          actionId, ModWeaponSkillEffects.ARCANE_BOLT, 5, 18
      )
  );
  public static final WeaponActionBuilder<CastSkillAction> HEAL_CAST = action(
      "test_staff_heal_cast",
      actionId -> new CastSkillAction(
          actionId, ModWeaponSkillEffects.MENDING_LIGHT, 10, 28
      )
  );
  public static final WeaponBuilder TEST_SWORD = weapon(
      "test_sword", "测试剑", "Test Sword",
      () -> new SwordItem(
          Tiers.IRON,
          new Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F))
      ),
      vanillaModel("iron_sword")).action(WeaponInput.PRIMARY, LIGHT_ATTACK)
      .presentation(LIGHT_ATTACK, presentation -> presentation
          .duration(LIGHT_ATTACK_DURATION)
          .playerAnimation(PLAYER_LIGHT_ATTACK)
          .weaponAnimation(WEAPON_LIGHT_ATTACK)
          .vfx(TEST_SWORD_TRAIL, 4)
          .vfx(TEST_SWORD_IMPACT, 8)
          .sound(TEST_SWORD_SWING, 4))
      .build();

  public static final WeaponBuilder TEST_RIFLE = weapon(
      "test_rifle", "测试步枪", "Test Rifle", () -> new FirearmItem(
          TEST_RIFLE_CAPACITY,
          new Properties()
              .stacksTo(1)
              .component(WeaponStateComponents.AMMO, TEST_RIFLE_CAPACITY)
              .component(WeaponStateComponents.AIMING, false)),
      vanillaModel("crossbow")).action(WeaponInput.PRIMARY, FIRE)
      .action(WeaponInput.SECONDARY, TOGGLE_AIM)
      .action(WeaponInput.RELOAD, RELOAD)
      .presentation(FIRE, presentation -> presentation
          .duration(10)
          .playerAnimation(PLAYER_RIFLE_FIRE)
          .weaponAnimation(WEAPON_RIFLE_FIRE)
          .vfx(RIFLE_MUZZLE, 2)
          .vfx(RIFLE_IMPACT, 3)
          .sound(RIFLE_FIRE, 2))
      .presentation(RELOAD, presentation -> presentation
          .duration(32)
          .playerAnimation(PLAYER_RIFLE_RELOAD)
          .weaponAnimation(WEAPON_RIFLE_RELOAD)
          .sound(RIFLE_RELOAD, 24))
      .presentation(TOGGLE_AIM, presentation -> presentation
          .duration(6)
          .playerAnimation(PLAYER_RIFLE_AIM))
      .build();

  public static final WeaponBuilder TEST_STAFF = weapon(
      "test_staff", "测试法杖", "Test Staff",
      () -> new ActionWeaponItem(new Properties().stacksTo(1)),
      vanillaModel("blaze_rod")
  ).action(WeaponInput.PRIMARY, ARCANE_CAST)
      .action(WeaponInput.SECONDARY, HEAL_CAST)
      .presentation(ARCANE_CAST, presentation -> presentation
          .duration(18)
          .playerAnimation(PLAYER_STAFF_CAST)
          .weaponAnimation(WEAPON_STAFF_CAST)
          .vfx(STAFF_ARCANE_CAST, 5)
          .vfx(STAFF_ARCANE_IMPACT, 6)
          .sound(STAFF_CAST, 5))
      .presentation(HEAL_CAST, presentation -> presentation
          .duration(28)
          .playerAnimation(PLAYER_STAFF_HEAL)
          .vfx(STAFF_HEAL, 10)
          .sound(STAFF_CAST, 10))
      .build();

  static {
    Zinecraft.TRANSLATIONS.add("item.zinecraft.firearm.ammo", "弹药：%s / %s", "Ammo: %s / %s");
    Zinecraft.TRANSLATIONS.add("item.zinecraft.firearm.aiming", "瞄准模式", "Aiming");
    Zinecraft.TRANSLATIONS.add("item.zinecraft.firearm.hip_fire", "腰射模式", "Hip Fire");
    Zinecraft.TRANSLATIONS.add("key.zinecraft.weapon_reload", "装填武器", "Reload Weapon");
    Zinecraft.TRANSLATIONS.add("key.categories.zinecraft.weapon", "Zinecraft 武器", "Zinecraft Weapons");
  }

  private ModWeapon() {
  }

  private static <T extends Item> WeaponBuilder weapon(
      String path,
      String zhCn,
      String enUs,
      Supplier<T> factory,
      ModelTemplate model
  ) {
    ItemBuilder<T> item = new ItemBuilder<>(
        Zinecraft.ITEMS, path, zhCn, enUs, factory, model, true
    ).build();
    return new WeaponBuilder(Zinecraft.WEAPONS, path, item);
  }

  private static <T extends WeaponAction> WeaponActionBuilder<T> action(
      String path,
      Function<ResourceLocation, T> factory
  ) {
    return new WeaponActionBuilder<>(Zinecraft.WEAPONS, path, factory).build();
  }

  private static ModelTemplate vanillaModel(String path) {
    return new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("item/" + path)),
        Optional.empty(),
        new TextureSlot[0]
    );
  }

  public static void bootstrap() {
  }
}

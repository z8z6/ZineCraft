package com.cxxcxx.zinecraft.core.weapon

import com.cxxcxx.zinecraft.api.item.ItemEntry
import com.cxxcxx.zinecraft.api.weapon.WeaponDefinition
import com.cxxcxx.zinecraft.api.weapon.WeaponInput
import com.cxxcxx.zinecraft.api.weapon.WeaponMetadata
import com.cxxcxx.zinecraft.api.weapon.WeaponPresentation
import com.cxxcxx.zinecraft.api.weapon.TimedWeaponSound
import com.cxxcxx.zinecraft.api.weapon.TimedWeaponVfx
import com.cxxcxx.zinecraft.api.weapon.action.melee.MeleeAttackAction
import com.cxxcxx.zinecraft.api.weapon.action.firearm.FirearmFireAction
import com.cxxcxx.zinecraft.api.weapon.action.firearm.FirearmReloadAction
import com.cxxcxx.zinecraft.api.weapon.action.firearm.ToggleAimAction
import com.cxxcxx.zinecraft.api.weapon.action.staff.CastSkillAction
import com.cxxcxx.zinecraft.api.weapon.item.ActionWeaponItem
import com.cxxcxx.zinecraft.api.weapon.item.FirearmItem
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents
import com.cxxcxx.zinecraft.core.Zinecraft
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.SwordItem
import net.minecraft.world.item.Tiers
import java.util.Optional

object ModWeapons {
  private const val TEST_RIFLE_CAPACITY = 12
  private const val LIGHT_ATTACK_DURATION = 20
  private val LIGHT_ATTACK_ID = Zinecraft.REGISTRAR.id("light_attack")
  private val PLAYER_LIGHT_ATTACK = Zinecraft.REGISTRAR.id("animation/player/light_attack")
  private val WEAPON_LIGHT_ATTACK = Zinecraft.REGISTRAR.id("animation/weapon/test_sword_light_attack")
  private val TEST_SWORD_TRAIL = Zinecraft.REGISTRAR.id("vfx/test_sword_trail")
  private val TEST_SWORD_IMPACT = Zinecraft.REGISTRAR.id("vfx/test_sword_impact")
  private val TEST_SWORD_SWING = Zinecraft.REGISTRAR.id("sound/test_sword_swing")
  private val FIRE_ID = Zinecraft.REGISTRAR.id("test_rifle_fire")
  private val RELOAD_ID = Zinecraft.REGISTRAR.id("test_rifle_reload")
  private val TOGGLE_AIM_ID = Zinecraft.REGISTRAR.id("test_rifle_toggle_aim")
  private val ARCANE_CAST_ID = Zinecraft.REGISTRAR.id("test_staff_arcane_cast")
  private val HEAL_CAST_ID = Zinecraft.REGISTRAR.id("test_staff_heal_cast")

  private val RIFLE_MUZZLE = Zinecraft.REGISTRAR.id("vfx/test_rifle_muzzle")
  private val RIFLE_IMPACT = Zinecraft.REGISTRAR.id("vfx/test_rifle_impact")
  private val RIFLE_FIRE_SOUND = Zinecraft.REGISTRAR.id("sound/test_rifle_fire")
  private val RIFLE_RELOAD_SOUND = Zinecraft.REGISTRAR.id("sound/test_rifle_reload")
  private val STAFF_ARCANE_CAST = Zinecraft.REGISTRAR.id("vfx/test_staff_arcane_cast")
  private val STAFF_ARCANE_IMPACT = Zinecraft.REGISTRAR.id("vfx/test_staff_arcane_impact")
  private val STAFF_HEAL = Zinecraft.REGISTRAR.id("vfx/test_staff_heal")
  private val STAFF_CAST_SOUND = Zinecraft.REGISTRAR.id("sound/test_staff_cast")

  private fun vanillaModel(path: String) = ModelTemplate(
    Optional.of(ResourceLocation.withDefaultNamespace("item/$path")),
    Optional.empty()
  )

  private val VANILLA_IRON_SWORD_MODEL = vanillaModel("iron_sword")

  val TEST_SWORD_ITEM: ItemEntry<SwordItem> = Zinecraft.ITEMS.register(
    path = "test_sword",
    zhCn = "测试剑",
    enUs = "Test Sword",
    model = VANILLA_IRON_SWORD_MODEL
  ) {
    SwordItem(
      Tiers.IRON,
      Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4f))
    )
  }

  val TEST_CARTRIDGE: ItemEntry<Item> = Zinecraft.ITEMS.register(
    path = "test_cartridge",
    zhCn = "测试弹药",
    enUs = "Test Cartridge",
    model = vanillaModel("iron_nugget")
  )

  val TEST_RIFLE_ITEM: ItemEntry<FirearmItem> = Zinecraft.ITEMS.register(
    path = "test_rifle",
    zhCn = "测试步枪",
    enUs = "Test Rifle",
    model = vanillaModel("crossbow")
  ) {
    FirearmItem(
      TEST_RIFLE_CAPACITY,
      Item.Properties().stacksTo(1)
        .component(WeaponStateComponents.AMMO, TEST_RIFLE_CAPACITY)
        .component(WeaponStateComponents.AIMING, false)
    )
  }

  val TEST_STAFF_ITEM: ItemEntry<ActionWeaponItem> = Zinecraft.ITEMS.register(
    path = "test_staff",
    zhCn = "测试法杖",
    enUs = "Test Staff",
    model = vanillaModel("blaze_rod")
  ) { ActionWeaponItem(Item.Properties().stacksTo(1)) }

  val LIGHT_ATTACK = MeleeAttackAction(
    id = LIGHT_ATTACK_ID,
    hitTick = 7,
    durationTicks = LIGHT_ATTACK_DURATION,
    damage = 7.0f,
    range = 3.25,
    arcDegrees = 100.0
  )

  val FIRE = FirearmFireAction(FIRE_ID, fireTick = 2, durationTicks = 10, damage = 6.0f, range = 48.0)
  val RELOAD = FirearmReloadAction(
    RELOAD_ID,
    reloadTick = 24,
    durationTicks = 32,
    capacity = TEST_RIFLE_CAPACITY,
    ammunition = TEST_CARTRIDGE.item
  )
  val TOGGLE_AIM = ToggleAimAction(TOGGLE_AIM_ID)
  val ARCANE_CAST = CastSkillAction(
    ARCANE_CAST_ID,
    ModWeaponSkillEffects.ARCANE_BOLT,
    Zinecraft.SKILL_SERVICE,
    castTick = 5,
    durationTicks = 18
  )
  val HEAL_CAST = CastSkillAction(
    HEAL_CAST_ID,
    ModWeaponSkillEffects.MENDING_LIGHT,
    Zinecraft.SKILL_SERVICE,
    castTick = 10,
    durationTicks = 28
  )

  val TEST_SWORD = WeaponDefinition(
    id = Zinecraft.REGISTRAR.id("test_sword"),
    actions = mapOf(WeaponInput.PRIMARY to LIGHT_ATTACK_ID),
    presentations = mapOf(
      LIGHT_ATTACK_ID to WeaponPresentation(
        playerAnimation = PLAYER_LIGHT_ATTACK,
        weaponAnimation = WEAPON_LIGHT_ATTACK,
        vfx = listOf(
          TimedWeaponVfx(TEST_SWORD_TRAIL, 4),
          TimedWeaponVfx(TEST_SWORD_IMPACT, 8)
        ),
        sounds = listOf(TimedWeaponSound(TEST_SWORD_SWING, 4)),
        durationTicks = LIGHT_ATTACK_DURATION
      )
    ),
    metadata = WeaponMetadata(TEST_SWORD_ITEM.item.descriptionId)
  )

  val TEST_RIFLE = WeaponDefinition(
    id = Zinecraft.REGISTRAR.id("test_rifle"),
    actions = mapOf(
      WeaponInput.PRIMARY to FIRE_ID,
      WeaponInput.SECONDARY to TOGGLE_AIM_ID,
      WeaponInput.RELOAD to RELOAD_ID
    ),
    presentations = mapOf(
      FIRE_ID to WeaponPresentation(
        playerAnimation = Zinecraft.REGISTRAR.id("animation/player/rifle_fire"),
        weaponAnimation = Zinecraft.REGISTRAR.id("animation/weapon/test_rifle_fire"),
        vfx = listOf(TimedWeaponVfx(RIFLE_MUZZLE, 2), TimedWeaponVfx(RIFLE_IMPACT, 3)),
        sounds = listOf(TimedWeaponSound(RIFLE_FIRE_SOUND, 2)),
        durationTicks = 10
      ),
      RELOAD_ID to WeaponPresentation(
        playerAnimation = Zinecraft.REGISTRAR.id("animation/player/rifle_reload"),
        weaponAnimation = Zinecraft.REGISTRAR.id("animation/weapon/test_rifle_reload"),
        sounds = listOf(TimedWeaponSound(RIFLE_RELOAD_SOUND, 24)),
        durationTicks = 32
      ),
      TOGGLE_AIM_ID to WeaponPresentation(
        playerAnimation = Zinecraft.REGISTRAR.id("animation/player/rifle_aim"),
        durationTicks = 6
      )
    ),
    metadata = WeaponMetadata(TEST_RIFLE_ITEM.item.descriptionId)
  )

  val TEST_STAFF = WeaponDefinition(
    id = Zinecraft.REGISTRAR.id("test_staff"),
    actions = mapOf(
      WeaponInput.PRIMARY to ARCANE_CAST_ID,
      WeaponInput.SECONDARY to HEAL_CAST_ID
    ),
    presentations = mapOf(
      ARCANE_CAST_ID to WeaponPresentation(
        playerAnimation = Zinecraft.REGISTRAR.id("animation/player/staff_cast"),
        weaponAnimation = Zinecraft.REGISTRAR.id("animation/weapon/test_staff_cast"),
        vfx = listOf(TimedWeaponVfx(STAFF_ARCANE_CAST, 5), TimedWeaponVfx(STAFF_ARCANE_IMPACT, 6)),
        sounds = listOf(TimedWeaponSound(STAFF_CAST_SOUND, 5)),
        durationTicks = 18
      ),
      HEAL_CAST_ID to WeaponPresentation(
        playerAnimation = Zinecraft.REGISTRAR.id("animation/player/staff_heal"),
        vfx = listOf(TimedWeaponVfx(STAFF_HEAL, 10)),
        sounds = listOf(TimedWeaponSound(STAFF_CAST_SOUND, 10)),
        durationTicks = 28
      )
    ),
    metadata = WeaponMetadata(TEST_STAFF_ITEM.item.descriptionId)
  )

  init {
    ModWeaponSkillEffects
    listOf(LIGHT_ATTACK, FIRE, RELOAD, TOGGLE_AIM, ARCANE_CAST, HEAL_CAST).forEach(Zinecraft.WEAPONS::registerAction)
    Zinecraft.WEAPONS.register(TEST_SWORD_ITEM.item, TEST_SWORD)
    Zinecraft.WEAPONS.register(TEST_RIFLE_ITEM.item, TEST_RIFLE)
    Zinecraft.WEAPONS.register(TEST_STAFF_ITEM.item, TEST_STAFF)
    Zinecraft.TRANSLATIONS.add("item.zinecraft.firearm.ammo", "弹药：%s / %s", "Ammo: %s / %s")
    Zinecraft.TRANSLATIONS.add("item.zinecraft.firearm.aiming", "瞄准模式", "Aiming")
    Zinecraft.TRANSLATIONS.add("item.zinecraft.firearm.hip_fire", "腰射模式", "Hip Fire")
    Zinecraft.TRANSLATIONS.add("key.zinecraft.weapon_reload", "装填武器", "Reload Weapon")
    Zinecraft.TRANSLATIONS.add("key.categories.zinecraft.weapon", "Zinecraft 武器", "Zinecraft Weapons")
  }
}

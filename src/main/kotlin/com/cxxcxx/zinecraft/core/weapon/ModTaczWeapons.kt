package com.cxxcxx.zinecraft.core.weapon

import com.cxxcxx.zinecraft.api.item.ItemEntry
import com.cxxcxx.zinecraft.api.weapon.TimedWeaponSound
import com.cxxcxx.zinecraft.api.weapon.TimedWeaponVfx
import com.cxxcxx.zinecraft.api.weapon.WeaponDefinition
import com.cxxcxx.zinecraft.api.weapon.WeaponInput
import com.cxxcxx.zinecraft.api.weapon.WeaponMetadata
import com.cxxcxx.zinecraft.api.weapon.WeaponPresentation
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczAimAction
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczAmmoItem
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczFireAction
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunItem
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunPacks
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunSpec
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczBoltAction
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczFireSelectAction
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczInspectAction
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczMeleeAction
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczReloadAction
import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import java.util.Optional
import kotlin.math.ceil

object ModTaczWeapons {
  private val SOURCE = Zinecraft.REGISTRAR.id("tacz_external")
  val FIRE_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_fire")
  val RELOAD_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_reload")
  val AIM_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_aim")
  val FIRE_SELECT_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_fire_select")
  val INSPECT_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_inspect")
  val MELEE_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_melee")
  val BOLT_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_bolt")
  val FIRE_ANIMATION_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_animation/fire")
  val RELOAD_ANIMATION_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_animation/reload")
  val AIM_ANIMATION_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_animation/aim")
  val FIRE_SELECT_ANIMATION_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_animation/fire_select")
  val INSPECT_ANIMATION_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_animation/inspect")
  val MELEE_ANIMATION_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_animation/melee")
  val BOLT_ANIMATION_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_animation/bolt")
  val PLAYER_FIRE_ANIMATION_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_player/fire")
  val PLAYER_RELOAD_ANIMATION_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_player/reload")
  val PLAYER_MELEE_ANIMATION_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_player/melee")
  val RELOAD_SOUND_CUE_ID: ResourceLocation = Zinecraft.REGISTRAR.id("tacz_cue/reload")
  private val MUZZLE_ID = Zinecraft.REGISTRAR.id("vfx/test_rifle_muzzle")

  private val BUILTIN_ENTITY_MODEL = ModelTemplate(
    Optional.of(ResourceLocation.withDefaultNamespace("builtin/entity")),
    Optional.empty()
  )

  val GUN_ITEM: ItemEntry<TaczGunItem> = Zinecraft.ITEMS.register(
    path = "tacz_gun",
    zhCn = "TaCZ 枪械",
    enUs = "TaCZ Gun",
    model = BUILTIN_ENTITY_MODEL,
    includeInCreative = false
  ) {
    TaczGunItem(Item.Properties().stacksTo(1).component(WeaponStateComponents.AIMING, false))
  }

  val AMMO_ITEM: ItemEntry<TaczAmmoItem> = Zinecraft.ITEMS.register(
    path = "tacz_ammunition",
    zhCn = "TaCZ 弹药",
    enUs = "TaCZ Ammunition",
    model = BUILTIN_ENTITY_MODEL,
    includeInCreative = false
  ) {
    TaczAmmoItem(Item.Properties().stacksTo(99))
  }

  init {
    listOf(
      TaczFireAction(FIRE_ID), TaczReloadAction(RELOAD_ID), TaczAimAction(AIM_ID),
      TaczFireSelectAction(FIRE_SELECT_ID), TaczInspectAction(INSPECT_ID),
      TaczMeleeAction(MELEE_ID), TaczBoltAction(BOLT_ID)
    )
      .forEach(Zinecraft.WEAPONS::registerAction)
    Zinecraft.WEAPONS.registerResolver { stack ->
      if (stack.item !== GUN_ITEM.item) return@registerResolver null
      val id = stack.get(WeaponStateComponents.TACZ_GUN_ID) ?: return@registerResolver null
      TaczGunPacks.gun(id)?.let { Zinecraft.WEAPONS.definition(it.runtimeId) }
    }
    reloadDefinitions()
    ItemGroupEvents.modifyEntriesEvent(ModItem.ZINECRAFT_CORE_ITEM_GROUP_KEY).register { entries ->
      TaczGunPacks.snapshot.guns.values.sortedWith(compareBy(TaczGunSpec::type, TaczGunSpec::sort, TaczGunSpec::id))
        .forEach { gun ->
          entries.accept(gunStack(gun))
        }
      TaczGunPacks.snapshot.ammunition.values.sortedWith(compareBy({ it.sort }, { it.id.toString() })).forEach { ammo ->
        entries.accept(ItemStack(AMMO_ITEM.item, ammo.stackSize).apply {
          set(WeaponStateComponents.TACZ_AMMO_ID, ammo.id)
        })
      }
    }
    Zinecraft.TRANSLATIONS.add("item.zinecraft.tacz_gun.caliber", "口径：%s", "Caliber: %s")
    Zinecraft.TRANSLATIONS.add("item.zinecraft.tacz_gun.stats", "伤害：%s  射速：%s RPM", "Damage: %s  Rate: %s RPM")
    Zinecraft.TRANSLATIONS.add("item.zinecraft.tacz_gun.fire_mode", "开火模式：%s", "Fire mode: %s")
    Zinecraft.TRANSLATIONS.add("item.zinecraft.tacz_gun.fire_mode.auto", "全自动", "Automatic")
    Zinecraft.TRANSLATIONS.add("item.zinecraft.tacz_gun.fire_mode.semi", "半自动", "Semi-automatic")
    Zinecraft.TRANSLATIONS.add("item.zinecraft.tacz_gun.fire_mode.burst", "点射", "Burst")
    Zinecraft.TRANSLATIONS.add("item.zinecraft.tacz_gun.fire_mode.unknown", "未知", "Unknown")
    Zinecraft.TRANSLATIONS.add("key.zinecraft.weapon_fire_select", "切换开火模式", "Select Fire Mode")
    Zinecraft.TRANSLATIONS.add("key.zinecraft.weapon_inspect", "检视武器", "Inspect Weapon")
    Zinecraft.TRANSLATIONS.add("key.zinecraft.weapon_melee", "枪械近战", "Gun Melee")
  }

  fun reloadDefinitions() {
    val snapshot = TaczGunPacks.reload()
    Zinecraft.WEAPONS.replaceDynamic(SOURCE, snapshot.guns.values.map(::definition))
  }

  fun gunStack(gun: TaczGunSpec): ItemStack = ItemStack(GUN_ITEM.item).apply {
    set(WeaponStateComponents.TACZ_GUN_ID, gun.id)
    set(WeaponStateComponents.AMMO, gun.capacity)
    set(WeaponStateComponents.AIMING, false)
    set(
      WeaponStateComponents.FIRE_MODE, when (gun.fireModes.firstOrNull()) {
        "auto" -> 0; "semi" -> 1; "burst" -> 2; else -> 3
      }
    )
    set(WeaponStateComponents.NEEDS_BOLT, false)
  }

  private fun definition(gun: TaczGunSpec): WeaponDefinition {
    val singleShotTicks = ceil(1200.0 / gun.rpm).toInt().coerceAtLeast(1)
    val burstTicks = ceil(1200.0 / gun.burstRpm).toInt().coerceAtLeast(1) * gun.burstCount
    val shotTicks = if ("burst" in gun.fireModes) maxOf(singleShotTicks, burstTicks) else singleShotTicks
    val fireSounds = gun.assets.sounds["shoot"]?.let { listOf(TimedWeaponSound(it.runtimeId, 0)) } ?: emptyList()
    val reloadSound =
      if (gun.assets.sounds.containsKey("reload_empty") || gun.assets.sounds.containsKey("reload_tactical")) {
        listOf(TimedWeaponSound(RELOAD_SOUND_CUE_ID, 0))
      } else emptyList()
    return WeaponDefinition(
      id = gun.runtimeId,
      actions = mapOf(
        WeaponInput.PRIMARY to FIRE_ID,
        WeaponInput.SECONDARY to AIM_ID,
        WeaponInput.RELOAD to RELOAD_ID,
        WeaponInput.FIRE_SELECT to FIRE_SELECT_ID,
        WeaponInput.INSPECT to INSPECT_ID,
        WeaponInput.MELEE to MELEE_ID,
        WeaponInput.BOLT to BOLT_ID
      ),
      presentations = mapOf(
        FIRE_ID to WeaponPresentation(
          playerAnimation = PLAYER_FIRE_ANIMATION_ID,
          weaponAnimation = FIRE_ANIMATION_ID,
          vfx = listOf(TimedWeaponVfx(MUZZLE_ID, 0)),
          sounds = fireSounds,
          durationTicks = shotTicks
        ),
        RELOAD_ID to WeaponPresentation(
          playerAnimation = PLAYER_RELOAD_ANIMATION_ID,
          weaponAnimation = RELOAD_ANIMATION_ID,
          sounds = reloadSound,
          durationTicks = gun.reloadDurationTicks
        ),
        AIM_ID to WeaponPresentation(
          weaponAnimation = AIM_ANIMATION_ID,
          durationTicks = gun.aimTicks
        ),
        FIRE_SELECT_ID to WeaponPresentation(
          weaponAnimation = FIRE_SELECT_ANIMATION_ID,
          durationTicks = 4
        ),
        INSPECT_ID to WeaponPresentation(
          weaponAnimation = INSPECT_ANIMATION_ID,
          sounds = gun.assets.sounds["inspect"]?.let { listOf(TimedWeaponSound(it.runtimeId, 0)) } ?: emptyList(),
          durationTicks = 40
        ),
        MELEE_ID to WeaponPresentation(
          playerAnimation = PLAYER_MELEE_ANIMATION_ID,
          weaponAnimation = MELEE_ANIMATION_ID,
          sounds = (gun.assets.sounds["melee_push"] ?: gun.assets.sounds["melee_stock"])
            ?.let { listOf(TimedWeaponSound(it.runtimeId, 0)) } ?: emptyList(),
          durationTicks = gun.meleeCooldownTicks.coerceAtLeast(3)
        ),
        BOLT_ID to WeaponPresentation(
          weaponAnimation = BOLT_ANIMATION_ID,
          durationTicks = gun.boltActionTicks.coerceAtLeast(1)
        )
      ),
      metadata = WeaponMetadata(gun.translationKey)
    )
  }
}

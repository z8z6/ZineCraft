package com.cxxcxx.zinecraft.core.weapon;

import com.cxxcxx.zinecraft.api.item.ItemEntry;
import com.cxxcxx.zinecraft.api.weapon.*;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import com.cxxcxx.zinecraft.api.weapon.tacz.*;
import com.cxxcxx.zinecraft.core.Zinecraft;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class ModTaczWeapons {
  @NotNull
  public static final ModTaczWeapons INSTANCE = new ModTaczWeapons();
  @NotNull
  private static final ResourceLocation SOURCE = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_external");
  @NotNull
  private static final ResourceLocation FIRE_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_fire");
  @NotNull
  private static final ResourceLocation RELOAD_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_reload");
  @NotNull
  private static final ResourceLocation AIM_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_aim");
  @NotNull
  private static final ResourceLocation FIRE_SELECT_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_fire_select");
  @NotNull
  private static final ResourceLocation INSPECT_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_inspect");
  @NotNull
  private static final ResourceLocation MELEE_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_melee");
  @NotNull
  private static final ResourceLocation BOLT_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_bolt");
  @NotNull
  private static final ResourceLocation FIRE_ANIMATION_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_animation/fire");
  @NotNull
  private static final ResourceLocation RELOAD_ANIMATION_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_animation/reload");
  @NotNull
  private static final ResourceLocation AIM_ANIMATION_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_animation/aim");
  @NotNull
  private static final ResourceLocation FIRE_SELECT_ANIMATION_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_animation/fire_select");
  @NotNull
  private static final ResourceLocation INSPECT_ANIMATION_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_animation/inspect");
  @NotNull
  private static final ResourceLocation MELEE_ANIMATION_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_animation/melee");
  @NotNull
  private static final ResourceLocation BOLT_ANIMATION_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_animation/bolt");
  @NotNull
  private static final ResourceLocation PLAYER_FIRE_ANIMATION_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_player/fire");
  @NotNull
  private static final ResourceLocation PLAYER_RELOAD_ANIMATION_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_player/reload");
  @NotNull
  private static final ResourceLocation PLAYER_MELEE_ANIMATION_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_player/melee");
  @NotNull
  private static final ResourceLocation RELOAD_SOUND_CUE_ID = Zinecraft.INSTANCE.getREGISTRAR().id("tacz_cue/reload");
  @NotNull
  private static final ResourceLocation MUZZLE_ID = Zinecraft.INSTANCE.getREGISTRAR().id("vfx/test_rifle_muzzle");
  @NotNull
  private static final ModelTemplate BUILTIN_ENTITY_MODEL = new ModelTemplate(
      Optional.of(ResourceLocation.withDefaultNamespace("builtin/entity")), Optional.empty(), new TextureSlot[0]
  );
  @NotNull
  private static final ItemEntry<TaczGunItem> GUN_ITEM = Zinecraft.INSTANCE
      .getITEMS()
      .register("tacz_gun", "TaCZ 枪械", "TaCZ Gun", BUILTIN_ENTITY_MODEL, false, ModTaczWeapons::GUN_ITEM$lambda$0);
  @NotNull
  private static final ItemEntry<TaczAmmoItem> AMMO_ITEM = Zinecraft.INSTANCE
      .getITEMS()
      .register("tacz_ammunition", "TaCZ 弹药", "TaCZ Ammunition", BUILTIN_ENTITY_MODEL, false, ModTaczWeapons::AMMO_ITEM$lambda$0);

  static {
    WeaponAction[] $this$forEach$iv = new WeaponAction[]{
        new TaczFireAction(FIRE_ID),
        new TaczReloadAction(RELOAD_ID),
        new TaczAimAction(AIM_ID),
        new TaczFireSelectAction(FIRE_SELECT_ID),
        new TaczInspectAction(INSPECT_ID),
        new TaczMeleeAction(MELEE_ID),
        new TaczBoltAction(BOLT_ID)
    };
    Iterable iterable = CollectionsKt.listOf($this$forEach$iv);
    WeaponRegistry weaponRegistry = Zinecraft.INSTANCE.getWEAPONS();
    int i = 0;

    for (Object object : iterable) {
      WeaponAction weaponAction = (WeaponAction) object;
      int j = 0;
      weaponRegistry.registerAction(weaponAction);
    }

    Zinecraft.INSTANCE.getWEAPONS().registerResolver(ModTaczWeapons::_init_$lambda$0);
    INSTANCE.reloadDefinitions();
    Zinecraft.INSTANCE.getTRANSLATIONS().add("item.zinecraft.tacz_gun.caliber", "口径：%s", "Caliber: %s");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("item.zinecraft.tacz_gun.stats", "伤害：%s  射速：%s RPM", "Damage: %s  Rate: %s RPM");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("item.zinecraft.tacz_gun.fire_mode", "开火模式：%s", "Fire mode: %s");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("item.zinecraft.tacz_gun.fire_mode.auto", "全自动", "Automatic");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("item.zinecraft.tacz_gun.fire_mode.semi", "半自动", "Semi-automatic");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("item.zinecraft.tacz_gun.fire_mode.burst", "点射", "Burst");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("item.zinecraft.tacz_gun.fire_mode.unknown", "未知", "Unknown");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("key.zinecraft.weapon_fire_select", "切换开火模式", "Select Fire Mode");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("key.zinecraft.weapon_inspect", "检视武器", "Inspect Weapon");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("key.zinecraft.weapon_melee", "枪械近战", "Gun Melee");
  }

  private ModTaczWeapons() {
  }

  private static final TaczGunItem GUN_ITEM$lambda$0() {
    Properties properties = new Properties().stacksTo(1).component(WeaponStateComponents.INSTANCE.getAIMING(), false);
    return new TaczGunItem(properties);
  }

  private static final TaczAmmoItem AMMO_ITEM$lambda$0() {
    Properties properties = new Properties().stacksTo(99);
    return new TaczAmmoItem(properties);
  }

  private static final Comparable lambda$1$1(TaczAmmoSpec it) {
    return it.getSort();
  }

  private static final Comparable lambda$1$2(TaczAmmoSpec it) {
    return it.getId().toString();
  }

  private static final WeaponDefinition _init_$lambda$0(ItemStack stack) {
    if (stack.getItem() != GUN_ITEM.getItem()) {
      return null;
    }

    ResourceLocation resourceLocation1 = (ResourceLocation) stack.get(WeaponStateComponents.INSTANCE.getTACZ_GUN_ID());
    if (resourceLocation1 == null) {
      return null;
    }

    ResourceLocation resourceLocation = resourceLocation1;
    TaczGunSpec taczGunSpec1 = TaczGunPacks.INSTANCE.gun(resourceLocation);
    WeaponDefinition weaponDefinition;
    if (taczGunSpec1 != null) {
      TaczGunSpec taczGunSpec = taczGunSpec1;
      int i = 0;
      weaponDefinition = Zinecraft.INSTANCE.getWEAPONS().definition(taczGunSpec.getRuntimeId());
    } else {
      weaponDefinition = null;
    }

    return weaponDefinition;
  }

  public static void addCreativeItems(CreativeModeTab.Output entries) {
    var guns = new ArrayList<>(TaczGunPacks.INSTANCE.getSnapshot().getGuns().values());
    guns.sort(Comparator.comparing(TaczGunSpec::getType)
        .thenComparingInt(TaczGunSpec::getSort)
        .thenComparing(TaczGunSpec::getId));
    guns.forEach(gun -> entries.accept(INSTANCE.gunStack(gun)));
    var ammunition = new ArrayList<>(TaczGunPacks.INSTANCE.getSnapshot().getAmmunition().values());
    ammunition.sort(Comparator.comparingInt(TaczAmmoSpec::getSort)
        .thenComparing(ammo -> ammo.getId().toString()));
    ammunition.forEach(ammo -> {
      var stack = new ItemStack(AMMO_ITEM.getItem(), ammo.getStackSize());
      stack.set(WeaponStateComponents.INSTANCE.getTACZ_AMMO_ID(), ammo.getId());
      entries.accept(stack);
    });
  }

  @NotNull
  public final ResourceLocation getFIRE_ID() {
    return FIRE_ID;
  }

  @NotNull
  public final ResourceLocation getRELOAD_ID() {
    return RELOAD_ID;
  }

  @NotNull
  public final ResourceLocation getAIM_ID() {
    return AIM_ID;
  }

  @NotNull
  public final ResourceLocation getFIRE_SELECT_ID() {
    return FIRE_SELECT_ID;
  }

  @NotNull
  public final ResourceLocation getINSPECT_ID() {
    return INSPECT_ID;
  }

  @NotNull
  public final ResourceLocation getMELEE_ID() {
    return MELEE_ID;
  }

  @NotNull
  public final ResourceLocation getBOLT_ID() {
    return BOLT_ID;
  }

  @NotNull
  public final ResourceLocation getFIRE_ANIMATION_ID() {
    return FIRE_ANIMATION_ID;
  }

  @NotNull
  public final ResourceLocation getRELOAD_ANIMATION_ID() {
    return RELOAD_ANIMATION_ID;
  }

  @NotNull
  public final ResourceLocation getAIM_ANIMATION_ID() {
    return AIM_ANIMATION_ID;
  }

  @NotNull
  public final ResourceLocation getFIRE_SELECT_ANIMATION_ID() {
    return FIRE_SELECT_ANIMATION_ID;
  }

  @NotNull
  public final ResourceLocation getINSPECT_ANIMATION_ID() {
    return INSPECT_ANIMATION_ID;
  }

  @NotNull
  public final ResourceLocation getMELEE_ANIMATION_ID() {
    return MELEE_ANIMATION_ID;
  }

  @NotNull
  public final ResourceLocation getBOLT_ANIMATION_ID() {
    return BOLT_ANIMATION_ID;
  }

  @NotNull
  public final ResourceLocation getPLAYER_FIRE_ANIMATION_ID() {
    return PLAYER_FIRE_ANIMATION_ID;
  }

  @NotNull
  public final ResourceLocation getPLAYER_RELOAD_ANIMATION_ID() {
    return PLAYER_RELOAD_ANIMATION_ID;
  }

  @NotNull
  public final ResourceLocation getPLAYER_MELEE_ANIMATION_ID() {
    return PLAYER_MELEE_ANIMATION_ID;
  }

  @NotNull
  public final ResourceLocation getRELOAD_SOUND_CUE_ID() {
    return RELOAD_SOUND_CUE_ID;
  }

  @NotNull
  public final ItemEntry<TaczGunItem> getGUN_ITEM() {
    return GUN_ITEM;
  }

  @NotNull
  public final ItemEntry<TaczAmmoItem> getAMMO_ITEM() {
    return AMMO_ITEM;
  }

  public final void reloadDefinitions() {
    TaczCatalogSnapshot taczCatalogSnapshot = TaczGunPacks.INSTANCE.reload();
    WeaponRegistry weaponRegistry1 = Zinecraft.INSTANCE.getWEAPONS();
    ResourceLocation resourceLocation1 = SOURCE;
    Iterable $this$map$iv = taczCatalogSnapshot.getGuns().values();
    ResourceLocation resourceLocation = resourceLocation1;
    WeaponRegistry weaponRegistry = weaponRegistry1;
    int i = 0;
    Iterable $this$mapTo$iv$iv = $this$map$iv;
    var collection = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
    int j = 0;

    for (Object object : $this$mapTo$iv$iv) {
      TaczGunSpec p0 = (TaczGunSpec) object;
      Collection collection1 = collection;
      int k = 0;
      collection1.add(this.definition(p0));
    }

    List list = (List) collection;
    weaponRegistry.replaceDynamic(resourceLocation, list);
  }

  @NotNull
  public final ItemStack gunStack(@NotNull TaczGunSpec gun) {
    ItemStack itemStack;
    ItemStack itemStack1;
    DataComponentType dataComponentType;
    byte b;
    label24:
    {
      itemStack = new ItemStack((ItemLike) GUN_ITEM.getItem());
      itemStack1 = itemStack;
      int i = 0;
      itemStack1.set(WeaponStateComponents.INSTANCE.getTACZ_GUN_ID(), gun.getId());
      itemStack1.set(WeaponStateComponents.INSTANCE.getAMMO(), gun.getCapacity());
      itemStack1.set(WeaponStateComponents.INSTANCE.getAIMING(), false);
      dataComponentType = WeaponStateComponents.INSTANCE.getFIRE_MODE();
      String string = (String) CollectionsKt.firstOrNull(gun.getFireModes());
      if (string != null) {
        switch (string.hashCode()) {
          case 3005871:
            if (string.equals("auto")) {
              b = 0;
              break label24;
            }
            break;
          case 3526510:
            if (string.equals("semi")) {
              b = 1;
              break label24;
            }
            break;
          case 94103840:
            if (string.equals("burst")) {
              b = 2;
              break label24;
            }
        }
      }

      b = 3;
    }

    itemStack1.set(dataComponentType, Integer.valueOf(b));
    itemStack1.set(WeaponStateComponents.INSTANCE.getNEEDS_BOLT(), false);
    return itemStack;
  }

  private final WeaponDefinition definition(TaczGunSpec gun) {
    ResourceLocation resourceLocation11;
    int k;
    List list7;
    label45:
    {
      int i = RangesKt.coerceAtLeast((int) Math.ceil(1200.0 / gun.getRpm()), 1);
      int j = RangesKt.coerceAtLeast((int) Math.ceil(1200.0 / gun.getBurstRpm()), 1) * gun.getBurstCount();
      k = gun.getFireModes().contains("burst") ? Math.max(i, j) : i;
      TaczSoundAsset taczSoundAsset5 = gun.getAssets().getSounds().get("shoot");
      if (taczSoundAsset5 != null) {
        TaczSoundAsset taczSoundAsset1 = taczSoundAsset5;
        int l = 0;
        list7 = CollectionsKt.listOf(new TimedWeaponSound(taczSoundAsset1.getRuntimeId(), 0));
        if (list7 != null) {
          break label45;
        }
      }

      list7 = CollectionsKt.emptyList();
    }

    Pair[] pairs2;
    List list8;
    Map map5;
    Pair[] pairs5;
    byte h;
    ResourceLocation resourceLocation15;
    Object object9;
    ResourceLocation resourceLocation16;
    Object object10;
    label35:
    {
      List list = list7;
      List list1 = !gun.getAssets().getSounds().containsKey("reload_empty") && !gun.getAssets().getSounds().containsKey("reload_tactical")
          ? CollectionsKt.emptyList()
          : CollectionsKt.listOf(new TimedWeaponSound(RELOAD_SOUND_CUE_ID, 0));
      resourceLocation11 = gun.getRuntimeId();
      Pair[] pairs = new Pair[]{
          TuplesKt.to(WeaponInput.PRIMARY, FIRE_ID),
          TuplesKt.to(WeaponInput.SECONDARY, AIM_ID),
          TuplesKt.to(WeaponInput.RELOAD, RELOAD_ID),
          TuplesKt.to(WeaponInput.FIRE_SELECT, FIRE_SELECT_ID),
          TuplesKt.to(WeaponInput.INSPECT, INSPECT_ID),
          TuplesKt.to(WeaponInput.MELEE, MELEE_ID),
          TuplesKt.to(WeaponInput.BOLT, BOLT_ID)
      };
      map5 = MapsKt.mapOf(pairs);
      pairs2 = new Pair[]{
          TuplesKt.to(
              FIRE_ID, new WeaponPresentation(PLAYER_FIRE_ANIMATION_ID, FIRE_ANIMATION_ID, CollectionsKt.listOf(new TimedWeaponVfx(MUZZLE_ID, 0)), list, k)
          ),
          TuplesKt.to(RELOAD_ID, new WeaponPresentation(PLAYER_RELOAD_ANIMATION_ID, RELOAD_ANIMATION_ID, null, list1, gun.getReloadDurationTicks(), 4, null)),
          TuplesKt.to(AIM_ID, new WeaponPresentation(null, AIM_ANIMATION_ID, null, null, gun.getAimTicks(), 13, null)),
          TuplesKt.to(FIRE_SELECT_ID, new WeaponPresentation(null, FIRE_SELECT_ANIMATION_ID, null, null, 4, 13, null)),
          null,
          null,
          null
      };
      pairs5 = pairs2;
      h = 4;
      resourceLocation15 = INSPECT_ID;
      object9 = null;
      resourceLocation16 = INSPECT_ANIMATION_ID;
      object10 = null;
      TaczSoundAsset taczSoundAsset6 = gun.getAssets().getSounds().get("inspect");
      if (taczSoundAsset6 != null) {
        TaczSoundAsset it = taczSoundAsset6;
        Object object1 = null;
        ResourceLocation resourceLocation2 = resourceLocation16;
        Object object = null;
        ResourceLocation resourceLocation1 = resourceLocation15;
        byte b = 4;
        Pair[] pairs1 = pairs2;
        Map map = map5;
        ResourceLocation resourceLocation = resourceLocation11;
        int m = 0;
        List list2 = CollectionsKt.listOf(new TimedWeaponSound(it.getRuntimeId(), 0));
        resourceLocation11 = resourceLocation;
        map5 = map;
        pairs5 = pairs1;
        h = b;
        resourceLocation15 = resourceLocation1;
        object9 = object;
        resourceLocation16 = resourceLocation2;
        object10 = object1;
        list8 = list2;
        if (list2 != null) {
          break label35;
        }
      }

      list8 = CollectionsKt.emptyList();
    }

    Object object2 = null;
    byte c = 5;
    byte d = 40;
    List list3 = list8;
    Object object3 = object10;
    ResourceLocation resourceLocation3 = resourceLocation16;
    Object object4 = object9;
    pairs5[h] = TuplesKt.to(
        resourceLocation15,
        new WeaponPresentation((ResourceLocation) object4, resourceLocation3, (List) object3, list3, d, c, (DefaultConstructorMarker) object2)
    );
    Pair[] pairs4 = pairs2;
    byte g = 5;
    ResourceLocation resourceLocation12 = MELEE_ID;
    ResourceLocation resourceLocation13 = PLAYER_MELEE_ANIMATION_ID;
    ResourceLocation resourceLocation14 = MELEE_ANIMATION_ID;
    Object object8 = null;
    TaczSoundAsset taczSoundAsset4 = gun.getAssets().getSounds().get("melee_push");
    if (taczSoundAsset4 == null) {
      taczSoundAsset4 = gun.getAssets().getSounds().get("melee_stock");
    }

    label29:
    {
      TaczSoundAsset taczSoundAsset = taczSoundAsset4;
      if (taczSoundAsset != null) {
        TaczSoundAsset taczSoundAsset3 = taczSoundAsset;
        Object object7 = null;
        ResourceLocation resourceLocation10 = resourceLocation14;
        ResourceLocation resourceLocation9 = resourceLocation13;
        ResourceLocation resourceLocation8 = resourceLocation12;
        byte f = 5;
        Pair[] pairs3 = pairs2;
        Map map3 = map5;
        ResourceLocation resourceLocation7 = resourceLocation11;
        int o = 0;
        List list6 = CollectionsKt.listOf(new TimedWeaponSound(taczSoundAsset3.getRuntimeId(), 0));
        resourceLocation11 = resourceLocation7;
        map5 = map3;
        pairs4 = pairs3;
        g = f;
        resourceLocation12 = resourceLocation8;
        resourceLocation13 = resourceLocation9;
        resourceLocation14 = resourceLocation10;
        object8 = object7;
        List list5 = list6;
        if (list5 != null) {
          list8 = list5;
          break label29;
        }
      }

      list8 = CollectionsKt.emptyList();
    }

    int p = RangesKt.coerceAtLeast(gun.getMeleeCooldownTicks(), 3);
    Object object5 = null;
    byte e = 4;
    int n = p;
    List list4 = list8;
    Object object6 = object8;
    ResourceLocation resourceLocation4 = resourceLocation14;
    ResourceLocation resourceLocation5 = resourceLocation13;
    pairs4[g] = TuplesKt.to(
        resourceLocation12, new WeaponPresentation(resourceLocation5, resourceLocation4, (List) object6, list4, n, e, (DefaultConstructorMarker) object5)
    );
    pairs2[6] = TuplesKt.to(
        BOLT_ID, new WeaponPresentation(null, BOLT_ANIMATION_ID, null, null, RangesKt.coerceAtLeast(gun.getBoltActionTicks(), 1), 13, null)
    );
    Map map4 = MapsKt.mapOf(pairs2);
    WeaponMetadata weaponMetadata = new WeaponMetadata(gun.getTranslationKey());
    Map map1 = map4;
    Map map2 = map5;
    ResourceLocation resourceLocation6 = resourceLocation11;
    return new WeaponDefinition(resourceLocation6, map2, map1, weaponMetadata);
  }
}


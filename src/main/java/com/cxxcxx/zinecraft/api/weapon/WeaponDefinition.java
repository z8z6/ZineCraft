package com.cxxcxx.zinecraft.api.weapon;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class WeaponDefinition {
  @NotNull
  private final ResourceLocation id;
  @NotNull
  private final Map<WeaponInput, ResourceLocation> actions;
  @NotNull
  private final Map<ResourceLocation, WeaponPresentation> presentations;
  @NotNull
  private final WeaponMetadata metadata;

  public WeaponDefinition(
      @NotNull ResourceLocation id,
      @NotNull Map<WeaponInput, ResourceLocation> actions,
      @NotNull Map<ResourceLocation, WeaponPresentation> presentations,
      @NotNull WeaponMetadata metadata
  ) {
    super();
    this.id = id;
    this.actions = actions;
    this.presentations = presentations;
    this.metadata = metadata;
    if (this.actions.isEmpty()) {
      int m = 0;
      String string2 = "武器至少需要绑定一个动作";
      throw new IllegalArgumentException(string2.toString());
    }

    if (StringsKt.isBlank(this.metadata.getTranslationKey())) {
      int l = 0;
      String string1 = "武器翻译键不能为空";
      throw new IllegalArgumentException(string1.toString());
    }

    var $this$all$iv = this.presentations.keySet();
    Collection collection = this.actions.values();
    int i = 0;
    boolean bl;
    if ($this$all$iv instanceof Collection && ((Collection) $this$all$iv).isEmpty()) {
      bl = true;
    } else {
      Iterator iterator = $this$all$iv.iterator();

      while (true) {
        if (!iterator.hasNext()) {
          bl = true;
          break;
        }

        Object object = iterator.next();
        ResourceLocation resourceLocation = (ResourceLocation) object;
        int j = 0;
        if (!collection.contains(resourceLocation)) {
          bl = false;
          break;
        }
      }
    }

    if (!bl) {
      int k = 0;
      String string = "表现只能引用该武器已绑定的动作";
      throw new IllegalArgumentException(string.toString());
    }
  }

  // $VF: synthetic method
  public WeaponDefinition(ResourceLocation var1, Map var2, Map var3, WeaponMetadata var4, int var5, DefaultConstructorMarker var6) {
    this(var1, var2, (var5 & 4) != 0 ? java.util.Map.of() : var3, var4);
  }

  // $VF: synthetic method
  public static WeaponDefinition copy$default(WeaponDefinition var0, ResourceLocation var1, Map var2, Map var3, WeaponMetadata var4, int var5, Object var6) {
    if ((var5 & 1) != 0) {
      var1 = var0.id;
    }

    if ((var5 & 2) != 0) {
      var2 = var0.actions;
    }

    if ((var5 & 4) != 0) {
      var3 = var0.presentations;
    }

    if ((var5 & 8) != 0) {
      var4 = var0.metadata;
    }

    return var0.copy(var1, var2, var3, var4);
  }

  @NotNull
  public final ResourceLocation getId() {
    return this.id;
  }

  @NotNull
  public final Map<WeaponInput, ResourceLocation> getActions() {
    return this.actions;
  }

  @NotNull
  public final Map<ResourceLocation, WeaponPresentation> getPresentations() {
    return this.presentations;
  }

  @NotNull
  public final WeaponMetadata getMetadata() {
    return this.metadata;
  }

  @Nullable
  public final ResourceLocation action(@NotNull WeaponInput input) {
    return this.actions.get(input);
  }

  @Nullable
  public final WeaponPresentation presentation(@NotNull ResourceLocation actionId) {
    return this.presentations.get(actionId);
  }

  @NotNull
  public final ResourceLocation component1() {
    return this.id;
  }

  @NotNull
  public final Map<WeaponInput, ResourceLocation> component2() {
    return this.actions;
  }

  @NotNull
  public final Map<ResourceLocation, WeaponPresentation> component3() {
    return this.presentations;
  }

  @NotNull
  public final WeaponMetadata component4() {
    return this.metadata;
  }

  @NotNull
  public final WeaponDefinition copy(
      @NotNull ResourceLocation id,
      @NotNull Map<WeaponInput, ResourceLocation> actions,
      @NotNull Map<ResourceLocation, WeaponPresentation> presentations,
      @NotNull WeaponMetadata metadata
  ) {
    return new WeaponDefinition(id, actions, presentations, metadata);
  }

  @Override
  public int hashCode() {
    int i = this.id.hashCode();
    i = i * 31 + this.actions.hashCode();
    i = i * 31 + this.presentations.hashCode();
    return i * 31 + this.metadata.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof WeaponDefinition weaponDefinition)) {
      return false;
    } else if (!java.util.Objects.equals(this.id, weaponDefinition.id)) {
      return false;
    } else if (!java.util.Objects.equals(this.actions, weaponDefinition.actions)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.presentations, weaponDefinition.presentations)
          ? false
          : java.util.Objects.equals(this.metadata, weaponDefinition.metadata);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "WeaponDefinition(id=" + this.id + ", actions=" + this.actions + ", presentations=" + this.presentations + ", metadata=" + this.metadata + ")";
  }
}


package com.cxxcxx.zinecraft.api.weapon.tacz;

import net.minecraft.resources.ResourceLocation;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class TaczCatalogSnapshot {
  private final List<TaczPackInfo> packs;
  private final Map<ResourceLocation, TaczGunSpec> guns;
  private final Map<ResourceLocation, TaczAmmoSpec> ammunition;
  private final TaczLayeredResources resources;

  TaczCatalogSnapshot(List<TaczPackInfo> packs, Map<ResourceLocation, TaczGunSpec> guns,
                      Map<ResourceLocation, TaczAmmoSpec> ammunition, TaczLayeredResources resources) {
    this.packs = List.copyOf(packs);
    this.guns = Map.copyOf(guns);
    this.ammunition = Map.copyOf(ammunition);
    this.resources = resources;
  }

  public List<TaczPackInfo> getPacks() {
    return packs;
  }

  public Map<ResourceLocation, TaczGunSpec> getGuns() {
    return guns;
  }

  public Map<ResourceLocation, TaczAmmoSpec> getAmmunition() {
    return ammunition;
  }

  public InputStream open(String path) {
    return resources.open(path);
  }

  public Stream<String> paths() {
    return resources.paths();
  }
}

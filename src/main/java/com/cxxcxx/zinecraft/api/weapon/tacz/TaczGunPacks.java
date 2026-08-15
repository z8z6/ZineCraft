package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public final class TaczGunPacks {
  public static final TaczGunPacks INSTANCE = new TaczGunPacks();
  private volatile TaczCatalogSnapshot snapshot = emptySnapshot();

  private TaczGunPacks() {
  }

  private static TaczCatalogSnapshot emptySnapshot() {
    return new TaczCatalogSnapshot(List.of(), Map.of(), Map.of(), new TaczLayeredResources(List.of()));
  }

  public Path getDirectory() {
    return FMLPaths.GAMEDIR.get().resolve("tacz");
  }

  public TaczCatalogSnapshot getSnapshot() {
    return snapshot;
  }

  public TaczCatalogSnapshot reload() {
    try {
      Files.createDirectories(getDirectory());
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to create TaCZ pack directory " + getDirectory(), exception);
    }
    snapshot = TaczGunPackLoader.load(getDirectory());
    Zinecraft.INSTANCE.getLogger().info("Loaded {} TaCZ gun pack(s), {} gun(s), and {} ammunition type(s) from {}",
        snapshot.getPacks().size(), snapshot.getGuns().size(), snapshot.getAmmunition().size(), getDirectory());
    return snapshot;
  }

  public TaczGunSpec gun(ResourceLocation id) {
    return snapshot.getGuns().get(id);
  }

  public TaczAmmoSpec ammo(ResourceLocation id) {
    return snapshot.getAmmunition().get(id);
  }
}

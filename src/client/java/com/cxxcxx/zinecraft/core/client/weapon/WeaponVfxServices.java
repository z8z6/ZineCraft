package com.cxxcxx.zinecraft.core.client.weapon;

import com.cxxcxx.zinecraft.core.Zinecraft;
import net.neoforged.fml.ModList;

/**
 * Selects the optional client VFX backend without linking Photon classes on the normal path.
 */
public final class WeaponVfxServices {
  private static final String PHOTON_BACKEND =
      "com.cxxcxx.zinecraft.core.client.compat.photon.PhotonWeaponVfxService";

  private WeaponVfxServices() {
  }

  public static WeaponVfxService create() {
    if (!ModList.get().isLoaded("photon")) {
      return VanillaWeaponVfxService.INSTANCE;
    }

    try {
      Class<?> backendClass = Class.forName(PHOTON_BACKEND);
      return (WeaponVfxService) backendClass.getField("INSTANCE").get(null);
    } catch (ReflectiveOperationException | LinkageError exception) {
      Zinecraft.INSTANCE.getLogger().warn("Photon is present but the weapon VFX backend could not be loaded; using vanilla particles", exception);
      return VanillaWeaponVfxService.INSTANCE;
    }
  }
}

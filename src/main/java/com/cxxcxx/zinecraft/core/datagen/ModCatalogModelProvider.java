package com.cxxcxx.zinecraft.core.datagen;

import com.cxxcxx.zinecraft.api.datagen.CatalogModelProvider;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

final class ModCatalogModelProvider extends CatalogModelProvider {
  public ModCatalogModelProvider(@NotNull PackOutput output) {
    super(output, Zinecraft.INSTANCE.getITEMS(), Zinecraft.INSTANCE.getBLOCKS());
  }
}


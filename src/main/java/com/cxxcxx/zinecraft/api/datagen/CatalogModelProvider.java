package com.cxxcxx.zinecraft.api.datagen;

import com.cxxcxx.zinecraft.api.registry.catalog.BlockCatalog;
import com.cxxcxx.zinecraft.api.registry.catalog.ItemCatalog;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Generates only the item models and trivial cube blockstates declared by the catalogs.
 */
public class CatalogModelProvider implements DataProvider {
  private final PackOutput.PathProvider modelPaths;
  private final PackOutput.PathProvider blockStatePaths;
  private final ItemCatalog items;
  private final BlockCatalog blocks;

  public CatalogModelProvider(PackOutput output, ItemCatalog items, BlockCatalog blocks) {
    this.modelPaths = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
    this.blockStatePaths = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
    this.items = items;
    this.blocks = blocks;
  }

  @Override
  public CompletableFuture<?> run(CachedOutput cache) {
    Map<ResourceLocation, Supplier<JsonElement>> models = new LinkedHashMap<>();
    Map<ResourceLocation, Supplier<JsonElement>> states = new LinkedHashMap<>();

    for (var entry : blocks.entries) {
      if (!entry.cubeModel) continue;
      var block = entry.block();
      var model = ModelTemplates.CUBE_ALL.create(block.get(), TextureMapping.cube(block.get()), models::put);
      var state = MultiVariantGenerator.multiVariant(
          block.get(), Variant.variant().with(VariantProperties.MODEL, model)
      );
      states.put(block.get().builtInRegistryHolder().key().location(), state::get);
      if (entry.blockItem().isPresent()) {
        var itemModel = new JsonObject();
        itemModel.addProperty("parent", model.toString());
        models.put(ModelLocationUtils.getModelLocation(entry.blockItem().orElseThrow().get()), () -> itemModel);
      }
    }
    for (var entry : items.entries) {
      if (entry.model == null) continue;
      entry.model.create(
          ModelLocationUtils.getModelLocation(entry.getItem().get()),
          TextureMapping.layer0(entry.getItem().get()),
          models::put
      );
    }

    var modelWrites = models.entrySet().stream()
        .map(entry -> DataProvider.saveStable(cache, entry.getValue().get(), modelPaths.json(entry.getKey())))
        .toArray(CompletableFuture[]::new);
    var stateWrites = states.entrySet().stream()
        .map(entry -> DataProvider.saveStable(cache, entry.getValue().get(), blockStatePaths.json(entry.getKey())))
        .toArray(CompletableFuture[]::new);
    return CompletableFuture.allOf(
        CompletableFuture.allOf(modelWrites), CompletableFuture.allOf(stateWrites)
    );
  }

  @Override
  public String getName() {
    return "Zinecraft catalog models";
  }
}

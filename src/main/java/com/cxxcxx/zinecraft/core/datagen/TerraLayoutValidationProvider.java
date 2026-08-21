package com.cxxcxx.zinecraft.core.datagen;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/**
 * 将隔离任务算好的布局报告登记为 NeoForge 数据生成资源。
 */
public final class TerraLayoutValidationProvider implements DataProvider {
  public static final String OUTPUT_PROPERTY = "zinecraft.terraLayoutReport";
  private final Path reportPath;
  private final Path resourcePath;

  public TerraLayoutValidationProvider(PackOutput output) {
    this.reportPath = Path.of(System.getProperty(
        OUTPUT_PROPERTY,
        "build/reports/terra-layout/terra_layout.json"
    )).toAbsolutePath().normalize();
    this.resourcePath = output.getOutputFolder()
        .resolve("data")
        .resolve(Zinecraft.MOD_ID)
        .resolve("terra_layout.json");
  }

  @Override
  public CompletableFuture<?> run(CachedOutput cache) {
    try {
      JsonObject json = JsonParser.parseString(Files.readString(reportPath)).getAsJsonObject();
      return DataProvider.saveStable(cache, json, resourcePath);
    } catch (IOException exception) {
      return CompletableFuture.failedFuture(exception);
    }
  }

  @Override
  public String getName() {
    return "Terra layout runtime resource";
  }
}

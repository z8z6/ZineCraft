package com.cxxcxx.zinecraft.core.quest;

import com.cxxcxx.zinecraft.core.Zinecraft;
import dev.ftb.mods.ftblibrary.snbt.SNBT;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class FtbQuestGuideInstaller {
  public static final FtbQuestGuideInstaller INSTANCE = new FtbQuestGuideInstaller();
  private static final String RESOURCE_ROOT = "zinecraft/ftbquests/quests";
  private static final List<String> CHAPTERS = List.of("chapters/zinecraft_guide.snbt", "chapters/terra_relations.snbt");
  private static final List<String> LANGUAGES = List.of("lang/en_us.snbt", "lang/zh_cn.snbt");

  private FtbQuestGuideInstaller() {
  }

  public void install() {
    if (!ModList.get().isLoaded("ftbquests")) return;
    Path root = FMLPaths.CONFIGDIR.get().resolve("ftbquests/quests");
    CHAPTERS.forEach(path -> installChapter(root, path));
    LANGUAGES.forEach(path -> installTranslations(root, path));
  }

  private void installChapter(Path root, String relative) {
    Path target = root.resolve(relative);
    try {
      byte[] content = readResource(relative);
      SNBT.readLines(new String(content, StandardCharsets.UTF_8).lines().toList());
      if (Files.exists(target)) return;
      Files.createDirectories(target.getParent());
      Files.write(target, content);
    } catch (Exception error) {
      Zinecraft.INSTANCE.getLogger().error("无法安装 FTB Quests 指引章节 {}", target, error);
    }
  }

  private void installTranslations(Path root, String relative) {
    Path target = root.resolve(relative);
    try {
      byte[] content = readResource(relative);
      var bundled = SNBT.readLines(new String(content, StandardCharsets.UTF_8).lines().toList());
      Files.createDirectories(target.getParent());
      if (!Files.exists(target)) {
        Files.write(target, content);
        return;
      }
      var installed = SNBT.read(target);
      if (installed == null) throw new IOException("无法解析已有语言表：" + target);
      if (installed.getAllKeys().containsAll(bundled.getAllKeys())) return;
      installed.merge(bundled, false);
      SNBT.tryWrite(target, installed);
    } catch (Exception error) {
      Zinecraft.INSTANCE.getLogger().error("无法合并 FTB Quests 指引语言表 {}", target, error);
    }
  }

  private byte[] readResource(String relative) throws IOException {
    try (var input = FtbQuestGuideInstaller.class.getClassLoader().getResourceAsStream(RESOURCE_ROOT + "/" + relative)) {
      if (input == null) throw new IOException("缺少内置 FTB Quests 资源：" + relative);
      return input.readAllBytes();
    }
  }
}

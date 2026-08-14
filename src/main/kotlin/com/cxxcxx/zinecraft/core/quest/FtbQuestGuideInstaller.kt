package com.cxxcxx.zinecraft.core.quest

import com.cxxcxx.zinecraft.core.Zinecraft
import dev.ftb.mods.ftblibrary.snbt.SNBT
import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Files
import kotlin.text.Charsets.UTF_8

/**
 * 将随模组发布的任务章节安装进 FTB Quests 配置目录。
 *
 * 章节只在缺失时创建；语言表只补充缺失键，避免覆盖整合包作者或玩家的调整。
 */
object FtbQuestGuideInstaller {
  private const val RESOURCE_ROOT = "zinecraft/ftbquests/quests"
  private val CHAPTER_FILES = listOf(
    "chapters/zinecraft_guide.snbt",
    "chapters/terra_relations.snbt"
  )
  private val TRANSLATION_FILES = listOf("lang/en_us.snbt", "lang/zh_cn.snbt")

  fun install() {
    if (!FabricLoader.getInstance().isModLoaded("ftbquests")) return

    val questRoot = FabricLoader.getInstance().configDir.resolve("ftbquests/quests")
    CHAPTER_FILES.forEach { installChapter(questRoot, it) }
    TRANSLATION_FILES.forEach { installTranslations(questRoot, it) }
  }

  private fun installChapter(questRoot: java.nio.file.Path, relativePath: String) {
    val target = questRoot.resolve(relativePath)
    runCatching {
      val content = readResource(relativePath)
      SNBT.readLines(content.toString(UTF_8).lineSequence().toList())
      if (Files.exists(target)) return@runCatching

      Files.createDirectories(target.parent)
      Files.write(target, content)
    }.onFailure { error ->
      Zinecraft.logger.error("无法安装 FTB Quests 指引章节 {}", target, error)
    }
  }

  private fun installTranslations(questRoot: java.nio.file.Path, relativePath: String) {
    val target = questRoot.resolve(relativePath)
    runCatching {
      val content = readResource(relativePath)
      val bundled = SNBT.readLines(content.toString(UTF_8).lineSequence().toList())
      Files.createDirectories(target.parent)

      if (!Files.exists(target)) {
        Files.write(target, content)
        return@runCatching
      }

      val installed = SNBT.read(target) ?: error("无法解析已有语言表：$target")
      if (installed.allKeys.containsAll(bundled.allKeys)) return@runCatching

      installed.merge(bundled, false)
      SNBT.tryWrite(target, installed)
    }.onFailure { error ->
      Zinecraft.logger.error("无法合并 FTB Quests 指引语言表 {}", target, error)
    }
  }

  private fun readResource(relativePath: String): ByteArray =
    FtbQuestGuideInstaller::class.java.classLoader
      .getResourceAsStream("$RESOURCE_ROOT/$relativePath")
      ?.use { it.readBytes() }
      ?: error("缺少内置 FTB Quests 资源：$relativePath")
}

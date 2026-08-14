package com.cxxcxx.zinecraft.core.client.weapon.tacz

import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunPacks
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunSpec
import com.cxxcxx.zinecraft.core.Zinecraft
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import org.luaj.vm2.Globals
import org.luaj.vm2.LoadState
import org.luaj.vm2.LuaFunction
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue
import org.luaj.vm2.Varargs
import org.luaj.vm2.compiler.LuaC
import org.luaj.vm2.lib.Bit32Lib
import org.luaj.vm2.lib.PackageLib
import org.luaj.vm2.lib.TableLib
import org.luaj.vm2.lib.StringLib
import org.luaj.vm2.lib.ZeroArgFunction
import org.luaj.vm2.lib.jse.CoerceJavaToLua
import org.luaj.vm2.lib.jse.JseBaseLib
import org.luaj.vm2.lib.jse.JseMathLib
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import kotlin.math.max
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.resources.ResourceLocation

/** Independent TaCZ Lua animation compatibility runtime; external scripts stay in their packs. */
internal class TaczLuaAnimationRuntime(
  private val stack: ItemStack,
  val gun: TaczGunSpec,
  clips: Map<String, TaczAnimationClip>,
  entity: LivingEntity?
) {
  private val mixer = TaczTrackMixer(clips)
  private val context = TaczLuaAnimationContext(this, stack, gun, mixer, entity)
  private val script: LuaTable?
  private var states = mutableListOf<LuaTable>()
  private var lastUpdateNanos = 0L

  init {
    mixer.soundListener = context::playSound
    script = runCatching(::loadScript).onFailure {
      Zinecraft.logger.warn("Failed to initialize TaCZ state machine for {}", gun.id, it)
    }.getOrNull()
    initialize()
  }

  fun bind(entity: LivingEntity) = context.bind(entity)

  fun trigger(input: String, entity: LivingEntity? = null) {
    entity?.let(context::bind)
    if (input == "shoot") context.markShot()
    if (input == "reload") context.markReload()
    val owner = script ?: return fallback(input)
    val luaContext = CoerceJavaToLua.coerce(context)
    states.indices.forEach { index ->
      val state = states[index]
      val transition = state.function("transition") ?: return@forEach
      runCatching { transition.call(owner, luaContext, LuaValue.valueOf(input)) }
        .onSuccess { next ->
          if (next.istable()) {
            state.function("exit")?.call(owner, luaContext)
            states[index] = next.checktable()
            states[index].function("entry")?.call(owner, luaContext)
          }
        }.onFailure { Zinecraft.logger.warn("TaCZ state transition '{}' failed for {}", input, gun.id, it) }
    }
  }

  fun sample(entity: LivingEntity? = null): Map<String, TaczBoneTransform> {
    entity?.let(context::bind)
    val now = System.nanoTime()
    if (now - lastUpdateNanos >= 1_000_000L) {
      val owner = script
      if (owner != null) {
        val luaContext = CoerceJavaToLua.coerce(context)
        states.forEach { state ->
          runCatching { state.function("update")?.call(owner, luaContext) }
            .onFailure { Zinecraft.logger.warn("TaCZ state update failed for {}", gun.id, it) }
        }
      }
      lastUpdateNanos = now
    }
    return mixer.sample(now)
  }

  fun stop() {
    val owner = script ?: return
    val luaContext = CoerceJavaToLua.coerce(context)
    states.forEach { it.function("exit")?.call(owner, luaContext) }
    owner.function("exit")?.call(owner, luaContext)
    states.clear()
  }

  private fun initialize() {
    val owner = script ?: return fallback("draw")
    val luaContext = CoerceJavaToLua.coerce(context)
    owner.function("initialize")?.call(owner, luaContext)
    val stateValues = owner.function("states")?.call(owner)
    if (stateValues?.istable() == true) {
      val table = stateValues.checktable()
      for (index in 1..table.length()) {
        table.get(index).takeIf(LuaValue::istable)?.checktable()?.let(states::add)
      }
      states.forEach { it.function("entry")?.call(owner, luaContext) }
    }
    trigger("draw")
  }

  private fun loadScript(): LuaTable? {
    val target = gun.assets.stateMachinePath ?: return null
    val globals = secureGlobals()
    val pattern = Regex("^assets/([^/]+)/scripts/(.+)\\.lua$")
    TaczGunPacks.snapshot.paths().forEach { path ->
      val match = pattern.matchEntire(path) ?: return@forEach
      val module = "${match.groupValues[1]}_${match.groupValues[2]}"
      globals.get("package").get("preload").set(module, object : ZeroArgFunction() {
        override fun call(): LuaValue = TaczGunPacks.snapshot.open(path)?.use { input ->
          InputStreamReader(input, StandardCharsets.UTF_8).use { globals.load(it, module).call() }
        } ?: LuaValue.NIL
      })
    }
    val match = pattern.matchEntire(target) ?: return null
    return globals.get("require").call(LuaValue.valueOf("${match.groupValues[1]}_${match.groupValues[2]}")).checktable()
  }

  private fun fallback(input: String) {
    val clip = when (input) {
      "draw" -> "draw"
      "shoot" -> "shoot"
      "reload" -> if (context.getAmmoCount() == 0) "reload_empty" else "reload_tactical"
      "inspect" -> if (context.getAmmoCount() == 0) "inspect_empty" else "inspect"
      "fire_select" -> "fire_select"
      "blot" -> "bolt"
      "bayonet_muzzle" -> "melee_bayonet"
      "bayonet_stock" -> "melee_stock"
      "bayonet_push" -> "melee_push"
      else -> return
    }
    mixer.runAnimation(0, clip, false, 1, 0f)
  }

  private fun LuaTable.function(name: String): LuaFunction? = get(name).takeIf(LuaValue::isfunction) as? LuaFunction

  companion object {
    private fun secureGlobals(): Globals = Globals().also { globals ->
      globals.load(JseBaseLib())
      globals.load(PackageLib())
      globals.load(Bit32Lib())
      globals.load(TableLib())
      globals.load(StringLib())
      globals.load(JseMathLib())
      LoadState.install(globals)
      LuaC.install(globals)
      globals.set("PLAY_ONCE_HOLD", 0)
      globals.set("PLAY_ONCE_STOP", 1)
      globals.set("LOOP", 2)
      globals.set("AUTO", 0)
      globals.set("SEMI", 1)
      globals.set("BURST", 2)
      globals.set("UNKNOWN", 3)
      globals.set("NOT_RELOADING", 0)
      globals.set("EMPTY_RELOAD_FEEDING", 1)
      globals.set("EMPTY_RELOAD_FINISHING", 2)
      globals.set("TACTICAL_RELOAD_FEEDING", 3)
      globals.set("TACTICAL_RELOAD_FINISHING", 4)
      mapOf(
        "INPUT_BOLT" to "blot", "INPUT_DRAW" to "draw", "INPUT_PUT_AWAY" to "put_away",
        "INPUT_FIRE_SELECT" to "fire_select", "INPUT_INSPECT" to "inspect",
        "INPUT_BAYONET_MUZZLE" to "bayonet_muzzle", "INPUT_BAYONET_STOCK" to "bayonet_stock",
        "INPUT_BAYONET_PUSH" to "bayonet_push", "INPUT_RELOAD" to "reload",
        "INPUT_CANCEL_RELOAD" to "cancel_reload", "INPUT_SHOOT" to "shoot",
        "INPUT_WALK" to "walk", "INPUT_RUN" to "run", "INPUT_IDLE" to "idle"
      ).forEach(globals::set)
    }
  }
}

/** Public Java-shaped methods are intentional: TaCZ Lua packs invoke this object through LuaJ. */
internal class TaczLuaAnimationContext(
  private val runtime: TaczLuaAnimationRuntime,
  private val stack: ItemStack,
  private val gun: TaczGunSpec,
  private val mixer: TaczTrackMixer,
  entity: LivingEntity?
) {
  private var entity = entity
  private var lastShootAt = 0L
  private var reloadAt = 0L
  private var walkAnchor = 0f
  private var hideCrosshair = false
  private val parameters = gun.assets.stateMachineParameters.toLuaTable()

  fun bind(value: LivingEntity) {
    entity = value
  }

  fun markShot() {
    lastShootAt = System.currentTimeMillis()
  }

  fun markReload() {
    reloadAt = System.currentTimeMillis()
  }

  fun ensureTrackLineSize(size: Int) = mixer.ensureTrackLines(size)
  fun ensureTracksAmount(line: Int, amount: Int) = mixer.ensureTracks(line, amount)
  fun getTrack(line: Int, index: Int): Int = mixer.getTrack(line, index)
  fun findIdleTrack(line: Int, interruptHolding: Boolean): Int = mixer.findIdle(line, interruptHolding)
  fun runAnimation(name: String, track: Int, blending: Boolean, playType: Int, transitionTime: Float) =
    mixer.runAnimation(track, name, blending, playType, transitionTime)

  fun stopAnimation(track: Int) = mixer.stop(track)
  fun holdAnimation(track: Int) = mixer.hold(track)
  fun pauseAnimation(track: Int) = mixer.pause(track)
  fun resumeAnimation(track: Int) = mixer.resume(track)
  fun setAnimationProgress(track: Int, progress: Float, normalization: Boolean) =
    mixer.setProgress(track, progress, normalization)

  fun adjustAnimationProgress(track: Int, progress: Float, normalization: Boolean) =
    mixer.adjustProgress(track, progress, normalization)

  fun isHolding(track: Int): Boolean = mixer.isHolding(track)
  fun isStopped(track: Int): Boolean = mixer.isStopped(track)
  fun isPause(track: Int): Boolean = mixer.isPaused(track)
  fun hasAnimationPrototype(name: String): Boolean = mixer.hasClip(name)
  fun trigger(input: String) = runtime.trigger(input)

  fun getAmmoCount(): Int = stack.getOrDefault(WeaponStateComponents.AMMO, gun.capacity)
  fun getMaxAmmoCount(): Int = gun.capacity
  fun hasBulletInBarrel(): Boolean = getAmmoCount() > 0
  fun hasAmmoToConsume(): Boolean = getAmmoCount() > 0
  fun getMagExtentLevel(): Int = 0
  fun getFireMode(): Int = stack.getOrDefault(WeaponStateComponents.FIRE_MODE, 0).coerceIn(0, 3)
  fun getReloadState(): Int {
    if (reloadAt == 0L) return 0
    val elapsed = System.currentTimeMillis() - reloadAt
    if (elapsed >= gun.reloadDurationTicks * 50L) return 0
    val empty = getAmmoCount() == 0
    val feeding = elapsed < gun.reloadFeedTicks * 50L
    return if (empty) if (feeding) 1 else 2 else if (feeding) 3 else 4
  }

  fun getAimingProgress(): Float = if (stack.getOrDefault(WeaponStateComponents.AIMING, false)) 1f else 0f
  fun isAiming(): Boolean = getAimingProgress() > 0f
  fun getShootCoolDown(): Float =
    ((System.currentTimeMillis() - lastShootAt).toFloat() / getShootInterval()).coerceIn(0f, 1f)

  fun getShootInterval(): Long = max(1L, 60_000L / gun.rpm)
  fun adjustClientShootInterval(value: Long) {
    lastShootAt += value
  }

  fun getLastShootTimestamp(): Long = lastShootAt
  fun getCurrentTimestamp(): Long = System.currentTimeMillis()
  fun getPutAwayTime(): Long = gun.putAwayTicks * 50L
  fun isOverHeat(): Boolean = false
  fun getOverHeatProgress(): Float = 0f
  fun isCharging(): Boolean = false
  fun getChargeProgress(): Float = 0f
  fun isOnGround(): Boolean = entity?.onGround() ?: true
  fun isCrawl(): Boolean = entity?.pose == net.minecraft.world.entity.Pose.SWIMMING && entity?.isSwimming == false
  fun shouldSlide(): Boolean = false
  fun isInputUp(): Boolean = localKey { it.options.keyUp.isDown }
  fun isInputDown(): Boolean = localKey { it.options.keyDown.isDown }
  fun isInputLeft(): Boolean = localKey { it.options.keyLeft.isDown }
  fun isInputRight(): Boolean = localKey { it.options.keyRight.isDown }
  fun anchorWalkDist() {
    walkAnchor = entity?.walkAnimation?.position() ?: 0f
  }

  fun getWalkDist(): Float = (entity?.walkAnimation?.position() ?: walkAnchor) - walkAnchor
  fun popShellFrom(time: Long): Boolean = lastShootAt >= time
  fun getAttachment(type: String): String = "tacz:empty"
  fun getStateMachineParams(): LuaTable = parameters
  fun setShouldHideCrossHair(value: Boolean) {
    hideCrosshair = value
  }

  fun shouldHideCrossHair(): Boolean = hideCrosshair

  fun playSound(effect: ResourceLocation) {
    val owner = entity ?: return
    val source = if (effect.namespace == "minecraft") {
      gun.assets.sounds[effect.path]?.runtimeId ?: return
    } else ResourceLocation.fromNamespaceAndPath(Zinecraft.MOD_ID, "tacz/${effect.namespace}/${effect.path}")
    owner.level().playLocalSound(
      owner.x,
      owner.y,
      owner.z,
      SoundEvent.createVariableRangeEvent(source),
      SoundSource.PLAYERS,
      0.85f,
      1f,
      false
    )
  }

  private inline fun localKey(read: (Minecraft) -> Boolean): Boolean {
    val client = Minecraft.getInstance()
    return entity === client.player && read(client)
  }
}

internal class TaczTrackMixer(private val clips: Map<String, TaczAnimationClip>) {
  private data class Runner(
    val clipName: String,
    val clip: TaczAnimationClip,
    val blending: Boolean,
    val playType: Int,
    var startedAt: Long,
    var offsetSeconds: Float = 0f,
    var pausedAt: Float? = null,
    var forcedHold: Boolean = false,
    val previous: Runner? = null,
    val transitionSeconds: Float = 0f,
    var lastSoundProgress: Float = -0.0001f
  )

  private val lines = mutableListOf<MutableList<Int>>()
  private val tracks = mutableMapOf<Int, Runner>()
  private var nextTrack = 0
  var soundListener: ((ResourceLocation) -> Unit)? = null

  fun hasClip(name: String) = clips.containsKey(name)
  fun ensureTrackLines(size: Int) {
    while (lines.size < size) lines.add(mutableListOf())
  }

  fun ensureTracks(line: Int, amount: Int) {
    ensureTrackLines(line + 1); while (lines[line].size < amount) lines[line] += nextTrack++
  }

  fun getTrack(line: Int, index: Int): Int {
    ensureTracks(line, index + 1); return lines[line][index]
  }

  fun findIdle(line: Int, interruptHolding: Boolean): Int {
    ensureTrackLines(line + 1)
    return lines[line].firstOrNull { isStopped(it) || (interruptHolding && isHolding(it)) }
      ?: nextTrack++.also(lines[line]::add)
  }

  fun runAnimation(track: Int, name: String, blending: Boolean, playType: Int, transitionTime: Float) {
    val clip = clips[name] ?: return
    val old = tracks[track]
    tracks[track] = Runner(
      name,
      clip,
      blending,
      playType.coerceIn(0, 2),
      System.nanoTime(),
      previous = old,
      transitionSeconds = transitionTime.coerceAtLeast(0f)
    )
  }

  fun stop(track: Int) {
    tracks.remove(track)
  }

  fun hold(track: Int) {
    tracks[track]?.forcedHold = true
  }

  fun pause(track: Int) {
    tracks[track]?.let { if (it.pausedAt == null) it.pausedAt = progress(it, System.nanoTime()) }
  }

  fun resume(track: Int) {
    tracks[track]?.let { runner ->
      runner.pausedAt?.let {
        runner.offsetSeconds = it; runner.startedAt = System.nanoTime(); runner.pausedAt = null
      }
    }
  }

  fun setProgress(track: Int, value: Float, normalized: Boolean) {
    tracks[track]?.let { runner ->
      runner.offsetSeconds = if (normalized) value * runner.clip.length else value; runner.startedAt = System.nanoTime()
    }
  }

  fun adjustProgress(track: Int, value: Float, normalized: Boolean) {
    tracks[track]?.let { runner ->
      setProgress(
        track,
        progress(runner, System.nanoTime()) + if (normalized) value * runner.clip.length else value,
        false
      )
    }
  }

  fun isHolding(track: Int): Boolean =
    tracks[track]?.let { it.forcedHold || it.playType == 0 && progress(it, System.nanoTime()) >= it.clip.length }
      ?: false

  fun isStopped(track: Int): Boolean =
    tracks[track]?.let { it.playType == 1 && progress(it, System.nanoTime()) >= it.clip.length } ?: true

  fun isPaused(track: Int): Boolean = tracks[track]?.pausedAt != null

  fun sample(now: Long): Map<String, TaczBoneTransform> {
    val result = linkedMapOf<String, TaczBoneTransform>()
    val ordered = if (lines.isEmpty()) tracks.keys.sorted() else lines.flatten()
    ordered.forEach { track ->
      val runner = tracks[track] ?: return@forEach
      if (isStopped(track)) return@forEach
      playSounds(runner, now)
      val current = runner.clip.sample(sampleTime(runner, now))
      val sampled = if (runner.previous != null && runner.transitionSeconds > 0f) {
        val alpha = ((now - runner.startedAt) / 1_000_000_000f / runner.transitionSeconds).coerceIn(0f, 1f)
        blendMaps(runner.previous.clip.sample(sampleTime(runner.previous, now)), current, alpha)
      } else current
      sampled.forEach { (bone, transform) ->
        val prior = result[bone]
        result[bone] = if (runner.blending && prior != null) prior.add(transform) else transform
      }
    }
    return result
  }

  private fun progress(runner: Runner, now: Long): Float =
    runner.pausedAt ?: runner.offsetSeconds + (now - runner.startedAt) / 1_000_000_000f

  private fun playSounds(runner: Runner, now: Long) {
    if (runner.clip.sounds.isEmpty() || runner.pausedAt != null) return
    val raw = progress(runner, now)
    val length = runner.clip.length
    val current = if (runner.playType == 2 && length > 0f) raw % length else raw.coerceAtMost(length)
    val from = runner.lastSoundProgress
    if (runner.playType == 2 && current < from) {
      runner.clip.sounds.filter { it.time > from || it.time <= current }.forEach { soundListener?.invoke(it.id) }
    } else {
      runner.clip.sounds.filter { it.time > from && it.time <= current }.forEach { soundListener?.invoke(it.id) }
    }
    runner.lastSoundProgress = current
  }

  private fun sampleTime(runner: Runner, now: Long): Float {
    val value = progress(runner, now)
    return when (runner.playType) {
      2 -> if (runner.clip.length > 0f) value % runner.clip.length else 0f; else -> value.coerceAtMost(runner.clip.length)
    }
  }

  private fun blendMaps(
    a: Map<String, TaczBoneTransform>,
    b: Map<String, TaczBoneTransform>,
    alpha: Float
  ): Map<String, TaczBoneTransform> =
    (a.keys + b.keys).associateWith { key ->
      (a[key] ?: TaczBoneTransform()).lerp(
        b[key] ?: TaczBoneTransform(),
        alpha
      )
    }
}

private fun TaczBoneTransform.add(other: TaczBoneTransform) = TaczBoneTransform(
  position + other.position,
  rotation + other.rotation,
  TaczVector(scale.x * other.scale.x, scale.y * other.scale.y, scale.z * other.scale.z)
)

private fun TaczBoneTransform.lerp(other: TaczBoneTransform, alpha: Float) = TaczBoneTransform(
  position * (1f - alpha) + other.position * alpha,
  rotation * (1f - alpha) + other.rotation * alpha,
  scale * (1f - alpha) + other.scale * alpha
)

private fun JsonObject.toLuaTable(): LuaTable =
  LuaTable().also { table -> entrySet().forEach { (key, value) -> table.set(key, value.toLua()) } }

private fun JsonElement.toLua(): LuaValue = when {
  isJsonNull -> LuaValue.NIL
  isJsonObject -> asJsonObject.toLuaTable()
  isJsonArray -> LuaTable().also { table ->
    asJsonArray.forEachIndexed { index, value ->
      table.set(
        index + 1,
        value.toLua()
      )
    }
  }

  asJsonPrimitive.isBoolean -> LuaValue.valueOf(asBoolean)
  asJsonPrimitive.isNumber -> LuaValue.valueOf(asDouble)
  else -> LuaValue.valueOf(asString)
}

package com.cxxcxx.zinecraft.core.client.compat.photon;

import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.gameobject.emitter.data.EmissionSetting;
import com.lowdragmc.photon.client.gameobject.emitter.data.number.NumberFunction;
import com.lowdragmc.photon.client.gameobject.emitter.data.number.NumberFunction3;
import com.lowdragmc.photon.client.gameobject.emitter.data.shape.Cone;
import com.lowdragmc.photon.client.gameobject.emitter.data.shape.Sphere;
import com.lowdragmc.photon.client.gameobject.emitter.particle.ParticleEmitter;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Original, reusable Photon effects authored through Photon 2.2's Java FX API.
 */
final class PhotonWeaponEffects {
  static final ResourceLocation EXPLOSION = id("explosion");
  static final ResourceLocation SWORD_SLASH = id("sword_slash");
  static final ResourceLocation HEALING = id("healing");

  private static final Map<ResourceLocation, Supplier<FX>> FACTORIES = Map.of(
      EXPLOSION, PhotonWeaponEffects::explosion,
      SWORD_SLASH, PhotonWeaponEffects::swordSlash,
      HEALING, PhotonWeaponEffects::healing
  );
  private static final Map<ResourceLocation, FX> CACHE = new HashMap<>();

  private PhotonWeaponEffects() {
  }

  static FX get(ResourceLocation id) {
    Supplier<FX> factory = FACTORIES.get(id);
    return factory == null ? null : CACHE.computeIfAbsent(id, ignored -> factory.get());
  }

  private static FX explosion() {
    ParticleEmitter core = burst("explosion_core", 4, 10, 28, 0.32F, 2.8F, 0xFFFFB13B);
    Sphere coreSphere = new Sphere();
    coreSphere.setRadius(0.16F);
    core.config.shape.setShape(coreSphere);

    ParticleEmitter smoke = burst("explosion_smoke", 7, 18, 18, 0.5F, 1.1F, 0xB85A493F);
    Sphere smokeSphere = new Sphere();
    smokeSphere.setRadius(0.36F);
    smokeSphere.setRadiusThickness(0.45F);
    smoke.config.shape.setShape(smokeSphere);
    smoke.config.forceOverLifetime.setEnable(true);
    smoke.config.forceOverLifetime.setForce(new NumberFunction3(0, 0.018F, 0));
    return fx(EXPLOSION, core, smoke);
  }

  private static FX swordSlash() {
    ParticleEmitter slash = burst("sword_slash", 3, 9, 24, 0.2F, 1.8F, 0xE6A9F7FF);
    Sphere arc = new Sphere();
    arc.setRadius(0.8F);
    arc.setRadiusThickness(0.08F);
    arc.setArc(115F);
    slash.config.shape.setShape(arc);
    slash.config.shape.setScale(new NumberFunction3(1.7F, 0.12F, 0.55F));
    slash.config.velocityOverLifetime.setEnable(true);
    slash.config.velocityOverLifetime.setLinear(new NumberFunction3(0, 0, 0.85F));
    return fx(SWORD_SLASH, slash);
  }

  private static FX healing() {
    ParticleEmitter helix = burst("healing_helix", 12, 25, 20, 0.13F, 0.35F, 0xE648F29A);
    Cone ring = new Cone();
    ring.setAngle(8F);
    ring.setRadius(0.72F);
    ring.setRadiusThickness(0.08F);
    helix.config.shape.setShape(ring);
    helix.config.velocityOverLifetime.setEnable(true);
    helix.config.velocityOverLifetime.setLinear(new NumberFunction3(0, 0.75F, 0));
    helix.config.velocityOverLifetime.setOrbital(new NumberFunction3(0, 2.4F, 0));

    ParticleEmitter motes = burst("healing_motes", 5, 30, 10, 0.2F, 0.18F, 0xFFD8FFE8);
    Sphere motesSphere = new Sphere();
    motesSphere.setRadius(0.5F);
    motesSphere.setRadiusThickness(1F);
    motes.config.shape.setShape(motesSphere);
    motes.config.velocityOverLifetime.setEnable(true);
    motes.config.velocityOverLifetime.setLinear(new NumberFunction3(0, 0.42F, 0));
    return fx(HEALING, helix, motes);
  }

  private static ParticleEmitter burst(
      String name, int duration, int lifetime, int count, float size, float speed, int color
  ) {
    ParticleEmitter emitter = new ParticleEmitter();
    emitter.setName(name);
    emitter.config.setDuration(duration);
    emitter.config.setLooping(false);
    emitter.config.setStartLifetime(NumberFunction.constant(lifetime));
    emitter.config.setStartSpeed(NumberFunction.constant(speed));
    emitter.config.setStartSize(new NumberFunction3(size, size, size));
    emitter.config.setStartColor(NumberFunction.color(color));
    emitter.config.setMaxParticles(Math.max(count * 2, 32));
    emitter.config.emission.setEmissionRate(NumberFunction.constant(0));
    EmissionSetting.Burst burst = new EmissionSetting.Burst();
    burst.setCount(NumberFunction.constant(count));
    emitter.config.emission.getBursts().add(burst);
    return emitter;
  }

  private static FX fx(ResourceLocation id, ParticleEmitter... emitters) {
    FX fx = new FX();
    fx.setFxLocation(id);
    fx.getFxData().objects().addAll(java.util.List.of(emitters));
    return fx;
  }

  private static ResourceLocation id(String path) {
    return ResourceLocation.fromNamespaceAndPath("zinecraft", "weapon/" + path);
  }
}

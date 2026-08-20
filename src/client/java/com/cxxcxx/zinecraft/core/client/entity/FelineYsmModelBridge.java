package com.cxxcxx.zinecraft.core.client.entity;

import com.cxxcxx.zinecraft.core.entity.FelineVictorianResidentEntity;
import com.elfmcys.yesstevemodel.Oo0OoOO000OooOoooOoo0ooO;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

/**
 * Binds the entity to the model package consumed by YSM's generic entity renderer.
 */
public final class FelineYsmModelBridge {
  private static final String MODEL_ID = "feline_victorian_resident";

  private FelineYsmModelBridge() {
  }

  public static void onEntityJoin(EntityJoinLevelEvent event) {
    if (!event.getLevel().isClientSide() || !(event.getEntity() instanceof FelineVictorianResidentEntity)) return;

    // YSM 2.6.2 exposes its generic entity animation state through this attachment.
    Oo0OoOO000OooOoooOoo0ooO.ooO0000oO0o0o0o000Oooo0O(event.getEntity())
        .ifPresent(data -> data.ooO0000oO0o0o0o000Oooo0O()
            .ooO0000oO0o0o0o000Oooo0O(MODEL_ID));
  }
}

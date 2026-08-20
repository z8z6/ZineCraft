package com.cxxcxx.zinecraft.core.client.entity;

import com.cxxcxx.zinecraft.core.entity.SanktaFormalResidentEntity;
import com.elfmcys.yesstevemodel.Oo0OoOO000OooOoooOoo0ooO;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

/**
 * Binds the Sankta resident entity to its bundled YSM model package.
 */
public final class SanktaYsmModelBridge {
  private static final String MODEL_ID = "sankta_formal_resident";

  private SanktaYsmModelBridge() {
  }

  public static void onEntityJoin(EntityJoinLevelEvent event) {
    if (!event.getLevel().isClientSide() || !(event.getEntity() instanceof SanktaFormalResidentEntity)) return;

    Oo0OoOO000OooOoooOoo0ooO.ooO0000oO0o0o0o000Oooo0O(event.getEntity())
        .ifPresent(data -> data.ooO0000oO0o0o0o000Oooo0O()
            .ooO0000oO0o0o0o000Oooo0O(MODEL_ID));
  }
}

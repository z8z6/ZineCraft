package com.cxxcxx.zinecraft.api.collection.network;

import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

/** 将触发失败保护的藏品堆发送给本地玩家，用于第一人称激活动画。 */
public record CollectibleFailureRecoveryPayload(ItemStack stack) implements CustomPacketPayload {
  public static final Type<CollectibleFailureRecoveryPayload> TYPE =
      new Type<>(Zinecraft.id("collectible_failure_recovery"));
  public static final StreamCodec<RegistryFriendlyByteBuf, CollectibleFailureRecoveryPayload> CODEC =
      new StreamCodec<>() {
        @Override
        public CollectibleFailureRecoveryPayload decode(RegistryFriendlyByteBuf buffer) {
          return new CollectibleFailureRecoveryPayload(ItemStack.STREAM_CODEC.decode(buffer));
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buffer, CollectibleFailureRecoveryPayload payload) {
          ItemStack.STREAM_CODEC.encode(buffer, payload.stack);
        }
      };

  public CollectibleFailureRecoveryPayload {
    stack = stack.copyWithCount(1);
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}

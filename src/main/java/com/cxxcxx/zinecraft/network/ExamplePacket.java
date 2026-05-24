package com.cxxcxx.zinecraft.network;

import com.cxxcxx.zinecraft.entity.ExampleBlockEntity;
import com.cxxcxx.zinecraft.menu.ExampleMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.cxxcxx.zinecraft.ZineCraft.MODID;

public class ExamplePacket implements CustomPacketPayload {

    public BlockPos worldPos;

    public static final StreamCodec<RegistryFriendlyByteBuf,ExamplePacket> STREAM_CODEC =
            CustomPacketPayload.codec(ExamplePacket::encode,ExamplePacket::new);

    public static final CustomPacketPayload.Type<ExamplePacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID,"example_data"));

    public ExamplePacket(RegistryFriendlyByteBuf buf) {
        this.worldPos = buf.readBlockPos();
    }

    public ExamplePacket(BlockPos pos) {
        this.worldPos = pos;
    }

    private void encode(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(this.worldPos);

    }

    public static void handle(final ExamplePacket data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(data.worldPos) instanceof ExampleBlockEntity blockEntity) {
                if(Minecraft.getInstance().player.containerMenu instanceof ExampleMenu menu &&
                        menu.blockEntity.getBlockPos().equals(data.worldPos)) {
                }
            }
        });
    }
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

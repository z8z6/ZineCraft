package com.cxxcxx.zinecraft.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static com.cxxcxx.zinecraft.ZineCraft.MODID;

@EventBusSubscriber(modid = MODID)
public class PacketRegistry {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {

        final PayloadRegistrar registrar = event.registrar(MODID).playBidirectional(
                ExamplePacket.TYPE,
                ExamplePacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ExamplePacket::handle,
                        null
                )
        );
    }
}

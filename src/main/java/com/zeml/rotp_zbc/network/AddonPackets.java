package com.zeml.rotp_zbc.network;

import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.network.packets.client.*;
import com.zeml.rotp_zbc.network.packets.server.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class AddonPackets {
    private static final String PROTOCOL_VERSION = "1";
    private static SimpleChannel channel;
    private static SimpleChannel clientChannel;
    private static int packetIndex = 0;


    public static void init() {
        channel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "channel"))
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .simpleChannel();
        clientChannel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "client_channel"))
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .simpleChannel();


        packetIndex = 0;

        channel.registerMessage(packetIndex++, SetSoldierStayPacket.class,
                SetSoldierStayPacket::encode,SetSoldierStayPacket::decode,SetSoldierStayPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, SetUnitTypePacket.class,
                SetUnitTypePacket::encode, SetUnitTypePacket::decode,SetUnitTypePacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, LandedMinesPacket.class,
                LandedMinesPacket::encode, LandedMinesPacket::decode,LandedMinesPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, SetTankStayPacket.class,
                SetTankStayPacket::encode, SetTankStayPacket::decode,SetTankStayPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, SetCopterStayPacket.class,
                SetCopterStayPacket::encode, SetCopterStayPacket::decode,SetCopterStayPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, MissileModePacket.class,
                MissileModePacket::encode, MissileModePacket::decode,MissileModePacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, SoldierStayClosePacket.class,
                SoldierStayClosePacket::encode, SoldierStayClosePacket::decode,SoldierStayClosePacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, TankStayClosePacket.class,
                TankStayClosePacket::encode, TankStayClosePacket::decode,TankStayClosePacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, CopterStayClosePacket.class,
                CopterStayClosePacket::encode, CopterStayClosePacket::decode,CopterStayClosePacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));


        channel.registerMessage(packetIndex++, SummonSoldierPacket.class,
                SummonSoldierPacket::encode, SummonSoldierPacket::decode, SummonSoldierPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, SummonTankPacket.class,
                SummonTankPacket::encode, SummonTankPacket::decode, SummonTankPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, SummonCopterPacket.class,
                SummonCopterPacket::encode, SummonCopterPacket::decode, SummonCopterPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        packetIndex = 0;

        registerMessage(clientChannel,new PlayerTroopTypePacket.Handler(),Optional.of(NetworkDirection.PLAY_TO_SERVER));
        registerMessage(clientChannel,new PlayerFormationPacket.Handler(),Optional.of(NetworkDirection.PLAY_TO_SERVER));


    }

    public static void sendToServer(Object msg) {
        clientChannel.sendToServer(msg);
    }

    public static void sendToClient(Object msg, ServerPlayerEntity player) {
        if (!(player instanceof FakePlayer)) {
            channel.send(PacketDistributor.PLAYER.with(() -> player), msg);
        }
    }

    public static void sendToClientsTracking(Object msg, Entity entity) {
        channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
    }

    public static void sendToClientsTrackingAndSelf(Object msg, Entity entity) {
        channel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }

    private static <MSG> void registerMessage(SimpleChannel channel, IModPacketHandler<MSG> handler, Optional<NetworkDirection> networkDirection) {
        if (packetIndex > 127) {
            throw new IllegalStateException("Too many packets (> 127) registered for a single channel!");
        }
        channel.registerMessage(packetIndex++, handler.getPacketClass(), handler::encode, handler::decode, handler::enqueueHandleSetHandled, networkDirection);
    }
}

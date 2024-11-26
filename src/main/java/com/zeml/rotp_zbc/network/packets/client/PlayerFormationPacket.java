package com.zeml.rotp_zbc.network.packets.client;

import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerFormationPacket {
    private final int formation;

    public PlayerFormationPacket(int unitType){
        this.formation = unitType;
    }

    public static class Handler implements IModPacketHandler<PlayerFormationPacket> {
        @Override
        public void encode(PlayerFormationPacket msg, PacketBuffer buf) {
            buf.writeInt(msg.formation);
        }


        @Override
        public PlayerFormationPacket decode(PacketBuffer buf) {
            int packetType = buf.readInt();
            return new PlayerFormationPacket(packetType);
        }
        @Override
        public void handle(PlayerFormationPacket msg, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            context.enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                if (player != null){
                    player.getCapability(LivingDataProvider.CAPABILITY).resolve().ifPresent(cap -> {
                        cap.setFormation(msg.formation);
                        System.out.println("Formation Packet Sent");

                    });
                }
                context.setPacketHandled(true);}

            );
        }
        @Override
        public Class<PlayerFormationPacket> getPacketClass() {
            return PlayerFormationPacket.class;
        }
    }
}

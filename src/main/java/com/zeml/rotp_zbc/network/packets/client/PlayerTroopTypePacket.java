package com.zeml.rotp_zbc.network.packets.client;

import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerTroopTypePacket {
    private final int unitType;

    public PlayerTroopTypePacket(int unitType){
        this.unitType = unitType;
    }

    public static class Handler implements IModPacketHandler<PlayerTroopTypePacket> {
        @Override
        public void encode(PlayerTroopTypePacket msg, PacketBuffer buf) {
            buf.writeInt(msg.unitType);
        }


        @Override
        public PlayerTroopTypePacket decode(PacketBuffer buf) {
            int packetType = buf.readInt();
            return new PlayerTroopTypePacket(packetType);
        }


        @Override
        public void handle(PlayerTroopTypePacket msg, Supplier<NetworkEvent.Context> ctx) {

            NetworkEvent.Context context = ctx.get();
            context.enqueueWork(() -> {ServerPlayerEntity player = ctx.get().getSender();
                if (player != null){
                    player.getCapability(LivingDataProvider.CAPABILITY).resolve().ifPresent(cap -> {
                        cap.setUnitType(msg.unitType);
                        System.out.println("Troop Packet Sent");

                    });
                }
                context.setPacketHandled(true);}

            );




        }



        @Override
        public Class<PlayerTroopTypePacket> getPacketClass() {
            return PlayerTroopTypePacket.class;
        }
    }
}

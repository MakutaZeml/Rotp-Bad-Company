package com.zeml.rotp_zbc.network.packets.server;

import com.github.standobyte.jojo.client.ClientUtil;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SetTankStayPacket {
    private final int entityID;
    private final boolean tankStay;

    public SetTankStayPacket(int entityID, boolean tankStay){
        this.entityID = entityID;
        this.tankStay = tankStay;
    }


    public static void encode(SetTankStayPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityID);
        buf.writeBoolean(msg.tankStay);
    }

    public static SetTankStayPacket decode(PacketBuffer buf) {
        return new SetTankStayPacket(buf.readInt(), buf.readBoolean());
    }


    public static void handle(SetTankStayPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = ClientUtil.getEntityById(msg.entityID);
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                LazyOptional<LivingData> playerDataOptional = living.getCapability(LivingDataProvider.CAPABILITY);
                playerDataOptional.ifPresent(playerData ->{
                    playerData.setTankStay(msg.tankStay);
                });
            }
        });
        ctx.get().setPacketHandled(true);

    }

}

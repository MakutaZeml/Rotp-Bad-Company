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

public class TankStayClosePacket {
    private final int entityID;
    private final boolean tankStay;

    public TankStayClosePacket(int entityID, boolean tankStay){
        this.entityID = entityID;
        this.tankStay = tankStay;
    }


    public static void encode(TankStayClosePacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityID);
        buf.writeBoolean(msg.tankStay);
    }

    public static TankStayClosePacket decode(PacketBuffer buf) {
        return new TankStayClosePacket(buf.readInt(), buf.readBoolean());
    }


    public static void handle(TankStayClosePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = ClientUtil.getEntityById(msg.entityID);
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                LazyOptional<LivingData> playerDataOptional = living.getCapability(LivingDataProvider.CAPABILITY);
                playerDataOptional.ifPresent(playerData ->{
                    playerData.setTankClose(msg.tankStay);
                });
            }
        });
        ctx.get().setPacketHandled(true);

    }
}

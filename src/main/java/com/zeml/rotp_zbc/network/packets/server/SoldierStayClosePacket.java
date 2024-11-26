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

public class SoldierStayClosePacket {
    private final int entityID;
    private final boolean stay;

    public SoldierStayClosePacket(int entityID, boolean stay){
        this.entityID = entityID;
        this.stay = stay;
    }


    public static void encode(SoldierStayClosePacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityID);
        buf.writeBoolean(msg.stay);
    }

    public static SoldierStayClosePacket decode(PacketBuffer buf) {
        return new SoldierStayClosePacket(buf.readInt(), buf.readBoolean());
    }


    public static void handle(SoldierStayClosePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = ClientUtil.getEntityById(msg.entityID);
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                LazyOptional<LivingData> playerDataOptional = living.getCapability(LivingDataProvider.CAPABILITY);
                playerDataOptional.ifPresent(playerData ->{
                    playerData.setSoldierClose(msg.stay);
                });
            }
        });
        ctx.get().setPacketHandled(true);

    }

}

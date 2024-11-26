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

public class MissileModePacket {
    private final int entityID;
    private final boolean missileMode;

    public MissileModePacket(int entityID, boolean tankStay){
        this.entityID = entityID;
        this.missileMode = tankStay;
    }


    public static void encode(MissileModePacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityID);
        buf.writeBoolean(msg.missileMode);
    }

    public static MissileModePacket decode(PacketBuffer buf) {
        return new MissileModePacket(buf.readInt(), buf.readBoolean());
    }


    public static void handle(MissileModePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = ClientUtil.getEntityById(msg.entityID);
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                LazyOptional<LivingData> playerDataOptional = living.getCapability(LivingDataProvider.CAPABILITY);
                playerDataOptional.ifPresent(playerData ->{
                    playerData.setExplosiveMissiles(msg.missileMode);
                });
            }
        });
        ctx.get().setPacketHandled(true);

    }

}

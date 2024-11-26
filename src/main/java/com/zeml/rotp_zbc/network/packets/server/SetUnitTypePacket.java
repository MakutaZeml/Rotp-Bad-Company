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

public class SetUnitTypePacket {
    private final int entityID;
    private final long unitType;

    public SetUnitTypePacket(int entityID, int unitType){
        this.entityID = entityID;
        this.unitType = unitType;
    }

    public static void encode(SetUnitTypePacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityID);
        buf.writeLong(msg.unitType);
    }

    public static SetUnitTypePacket decode(PacketBuffer buf) {
        return new SetUnitTypePacket(buf.readInt(), (int) buf.readLong());
    }


    public static void handle(SetUnitTypePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = ClientUtil.getEntityById(msg.entityID);
            if (entity instanceof LivingEntity) {
                System.out.println(msg.unitType);
                LivingEntity living = (LivingEntity) entity;
                LazyOptional<LivingData> playerDataOptional = living.getCapability(LivingDataProvider.CAPABILITY);
                playerDataOptional.ifPresent(playerData ->{
                    playerData.setUnitType( ( int) msg.unitType);
                });
            }
        });
        ctx.get().setPacketHandled(true);

    }
}

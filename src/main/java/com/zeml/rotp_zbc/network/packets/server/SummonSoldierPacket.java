package com.zeml.rotp_zbc.network.packets.server;

import com.github.standobyte.jojo.client.ClientUtil;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SummonSoldierPacket {
    private final int entityID;
    private final boolean summonSoldier;


    public SummonSoldierPacket(int entityID, boolean summonSoldier){
        this.entityID = entityID;
        this.summonSoldier = summonSoldier;
    }

    public static void encode(SummonSoldierPacket msg, PacketBuffer buffer){
        buffer.writeInt(msg.entityID);
        buffer.writeBoolean(msg.summonSoldier);
    }

    public static SummonSoldierPacket decode(PacketBuffer buffer){
        return new SummonSoldierPacket(buffer.readInt(),buffer.readBoolean());
    }

    public static void handle(SummonSoldierPacket msg, Supplier<NetworkEvent.Context> ctx){
        Entity entity = ClientUtil.getEntityById(msg.entityID);
        if(entity instanceof LivingEntity){
            ((LivingEntity) entity).getCapability(LivingDataProvider.CAPABILITY).ifPresent(livingData -> {
                livingData.setSummonSoldier(msg.summonSoldier);
            });
        }
    }


}

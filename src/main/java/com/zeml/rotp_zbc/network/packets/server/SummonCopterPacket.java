package com.zeml.rotp_zbc.network.packets.server;

import com.github.standobyte.jojo.client.ClientUtil;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SummonCopterPacket {
    private final int entityID;
    private final boolean summonCopter;


    public SummonCopterPacket(int entityID, boolean summonSoldier){
        this.entityID = entityID;
        this.summonCopter = summonSoldier;
    }

    public static void encode(SummonCopterPacket msg, PacketBuffer buffer){
        buffer.writeInt(msg.entityID);
        buffer.writeBoolean(msg.summonCopter);
    }

    public static SummonCopterPacket decode(PacketBuffer buffer){
        return new SummonCopterPacket(buffer.readInt(),buffer.readBoolean());
    }

    public static void handle(SummonCopterPacket msg, Supplier<NetworkEvent.Context> ctx){
        Entity entity = ClientUtil.getEntityById(msg.entityID);
        if(entity instanceof LivingEntity){
            ((LivingEntity) entity).getCapability(LivingDataProvider.CAPABILITY).ifPresent(livingData -> {
                livingData.setSummonCopter(msg.summonCopter);
            });
        }
    }


}

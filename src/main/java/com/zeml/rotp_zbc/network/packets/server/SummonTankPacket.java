package com.zeml.rotp_zbc.network.packets.server;

import com.github.standobyte.jojo.client.ClientUtil;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SummonTankPacket {
    private final int entityID;
    private final boolean summonTank;


    public SummonTankPacket(int entityID, boolean summonSoldier){
        this.entityID = entityID;
        this.summonTank = summonSoldier;
    }

    public static void encode(SummonTankPacket msg, PacketBuffer buffer){
        buffer.writeInt(msg.entityID);
        buffer.writeBoolean(msg.summonTank);
    }

    public static SummonTankPacket decode(PacketBuffer buffer){
        return new SummonTankPacket(buffer.readInt(),buffer.readBoolean());
    }

    public static void handle(SummonTankPacket msg, Supplier<NetworkEvent.Context> ctx){
        Entity entity = ClientUtil.getEntityById(msg.entityID);
        if(entity instanceof LivingEntity){
            ((LivingEntity) entity).getCapability(LivingDataProvider.CAPABILITY).ifPresent(livingData -> {
                livingData.setSummonTank(msg.summonTank);
            });
        }
    }


}

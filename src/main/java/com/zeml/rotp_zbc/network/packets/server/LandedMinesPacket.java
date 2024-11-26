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

public class LandedMinesPacket {
    private final int entityID;
    private final boolean landedMines;

    public LandedMinesPacket(int entityID, boolean landedMines){
        this.entityID = entityID;
        this.landedMines = landedMines;
    }


    public static void encode(LandedMinesPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityID);
        buf.writeBoolean(msg.landedMines);
    }

    public static LandedMinesPacket decode(PacketBuffer buf) {
        return new LandedMinesPacket(buf.readInt(), buf.readBoolean());
    }


    public static void handle(LandedMinesPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = ClientUtil.getEntityById(msg.entityID);
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                LazyOptional<LivingData> playerDataOptional = living.getCapability(LivingDataProvider.CAPABILITY);
                playerDataOptional.ifPresent(playerData ->{
                    playerData.setLandedMines(msg.landedMines);
                });
            }
        });
        ctx.get().setPacketHandled(true);

    }

}

package com.zeml.rotp_zbc.events;

import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.entity.BadHelicopterEntity;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import com.zeml.rotp_zbc.entity.BadTankEntity;
import com.zeml.rotp_zbc.init.InitEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RotpBadCompanyAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void addEntityAttribues(EntityAttributeCreationEvent event){
        event.put(InitEntities.BAD_SOLDIER.get(), BadSoldierEntity.createAttributes().build());
        event.put(InitEntities.BAD_TANK.get(), BadTankEntity.createAttributes().build());
        event.put(InitEntities.BAD_HELICOPTER.get(), BadHelicopterEntity.createAttributes().build());
    }
}

package com.zeml.rotp_zbc.util;

import com.github.standobyte.jojo.power.IPower;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import com.zeml.rotp_zbc.entity.BadHelicopterEntity;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import com.zeml.rotp_zbc.entity.BadTankEntity;
import com.zeml.rotp_zbc.init.InitStands;
import com.zeml.rotp_zbc.power.impl.stand.type.BadCompanyStandType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = RotpBadCompanyAddon.MOD_ID)
public class GameplayHandler {


    @SubscribeEvent(priority =  EventPriority.NORMAL)
    public static void onSoliderDeath(LivingDeathEvent event){
        if(!event.getEntity().level.isClientSide){
            if(event.getEntity() instanceof BadCompanyUnitEntity){
                BadCompanyUnitEntity badCompanyUnit = (BadCompanyUnitEntity) event.getEntity();
                if(badCompanyUnit.getOwner() != null){
                    LivingEntity owner = badCompanyUnit.getOwner();
                    IStandPower.getStandPowerOptional(owner).ifPresent(power -> {
                        if(badCompanyUnit instanceof BadSoldierEntity){
                            BadSoldierEntity badSoldier = (BadSoldierEntity) badCompanyUnit;
                            if(((BadCompanyStandType<?>)power.getType()).getSumonSoldier(power)){
                                if(power.getStamina() >= 50){
                                    BadSoldierEntity newBadSolider = new BadSoldierEntity(badSoldier.level);
                                    newBadSolider.setOwnerUUID(badSoldier.getOwnerUUID());
                                    LivingEntity user = badSoldier.getOwner();
                                    newBadSolider.teleportTo(user.getX() + 5 - 10F * Math.random(), user.getY(), user.getZ() + 5 - 10F * Math.random());
                                    badSoldier.level.addFreshEntity(newBadSolider);
                                    power.consumeStamina(50);
                                }
                            }
                        } else if (badCompanyUnit instanceof BadTankEntity) {
                            if(((BadCompanyStandType<?>)power.getType()).getSumonTank(power)){
                                BadTankEntity badTankEntity = (BadTankEntity) badCompanyUnit;
                                if(power.getStamina() >= 100){
                                    BadTankEntity newBadTankEntity = new BadTankEntity(badTankEntity.getOwner());
                                    newBadTankEntity.setOwnerUUID(badTankEntity.getOwnerUUID());
                                    LivingEntity user = badTankEntity.getOwner();
                                    newBadTankEntity.teleportTo(user.getX() + 5 - 10F * Math.random(), user.getY(), user.getZ() + 5 - 10F * Math.random());
                                    badTankEntity.level.addFreshEntity(newBadTankEntity);
                                    power.consumeStamina(100);
                                }
                            }
                        } else if (badCompanyUnit instanceof BadHelicopterEntity) {
                            if(((BadCompanyStandType<?>)power.getType()).getSumonCopter(power)){
                                BadHelicopterEntity badHelicopter = (BadHelicopterEntity) badCompanyUnit;
                                if(power.getStamina() >= 100){
                                    BadHelicopterEntity newBadHelicopter = new BadHelicopterEntity(badHelicopter.getOwner());
                                    newBadHelicopter.setOwnerUUID(badHelicopter.getOwnerUUID());
                                    LivingEntity user = badHelicopter.getOwner();
                                    newBadHelicopter.teleportTo(user.getX() + 5 - 10F * Math.random(), user.getY(), user.getZ() + 5 - 10F * Math.random());
                                    badHelicopter.level.addFreshEntity(newBadHelicopter);
                                    power.consumeStamina(100);
                                }
                            }

                        }
                    });
                }

            }
        }
    }



}

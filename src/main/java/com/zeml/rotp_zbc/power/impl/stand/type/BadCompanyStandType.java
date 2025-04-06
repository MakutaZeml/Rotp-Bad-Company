package com.zeml.rotp_zbc.power.impl.stand.type;

import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.client.render.entity.model.BadSoldierModel;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import com.zeml.rotp_zbc.entity.BadHelicopterEntity;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import com.zeml.rotp_zbc.entity.BadTankEntity;
import com.zeml.rotp_zbc.entity.damaging.BadMineEntity;
import com.zeml.rotp_zbc.init.InitStatusEffect;
import com.zeml.rotp_zbc.network.AddonPackets;
import com.zeml.rotp_zbc.network.packets.server.SetUnitTypePacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class BadCompanyStandType <T extends StandStats> extends EntityStandType<T> {
    private boolean manualControlEnabled;

    @Deprecated
    public BadCompanyStandType(int color, ITextComponent partName, StandAction[] attacks, StandAction[] abilities, Class<T> statsClass, T defaultStats, @Nullable StandType.StandTypeOptionals additions) {
        super(color, partName, attacks, abilities, statsClass, defaultStats, additions);
    }

    protected BadCompanyStandType(EntityStandType.AbstractBuilder<?, T> builder) {
        super(builder);
    }

    @Override
    public void tickUser(LivingEntity user, IStandPower power) {
        super.tickUser(user, power);
        if(!user.level.isClientSide){
            if(getSoldierStay(power)){
                List<BadSoldierEntity> badSoldierEntities = MCUtil.entitiesAround(BadSoldierEntity.class,user,160,false,badSoldierEntity -> badSoldierEntity.getOwner() == user && !badSoldierEntity.getGoingTo());
                if(!badSoldierEntities.isEmpty()){
                    badSoldierEntities.forEach(badSoldierEntity -> {
                        badSoldierEntity.addEffect(new EffectInstance(InitStatusEffect.SAT.get(),20,0,false,false,false));
                    });
                }
            }
            if (getTankStay(power)){
                List<BadTankEntity> badTankEntities = MCUtil.entitiesAround(BadTankEntity.class,user,160,false,badSoldierEntity -> badSoldierEntity.getOwner() == user && !badSoldierEntity.getGoingTo());
                if(!badTankEntities.isEmpty()){
                    badTankEntities.forEach(badTankEntity -> {
                        badTankEntity.addEffect(new EffectInstance(InitStatusEffect.SAT.get(),20,0,false,false,false));
                    });
                }
            }
            if(getCopterStay(power)){
                List<BadHelicopterEntity> badHelicopterEntities = MCUtil.entitiesAround(BadHelicopterEntity.class,user,160,false, badSoldierEntity -> badSoldierEntity.getOwner() == user && !badSoldierEntity.getGoingTo());
                if(!badHelicopterEntities.isEmpty()){
                    badHelicopterEntities.forEach(badHelicopterEntity -> {
                        badHelicopterEntity.addEffect(new EffectInstance(InitStatusEffect.SAT.get(),20,0,false,false,false));
                    });
                }
            }
            List<BadCompanyUnitEntity> badCompanyUnitEntities = MCUtil.entitiesAround(BadCompanyUnitEntity.class,user,160,false,unitEntity -> unitEntity.getOwner() == user);
            if(!badCompanyUnitEntities.isEmpty()){
                badCompanyUnitEntities.forEach(unitEntity -> {
                    if(unitEntity instanceof BadSoldierEntity){
                        unitEntity.setStayClose(getSoldierClose(power));
                    }else if(unitEntity instanceof  BadTankEntity){
                        unitEntity.setStayClose(getTankClose(power));
                    }else if(unitEntity instanceof BadHelicopterEntity){
                        unitEntity.setStayClose(getCopterClose(power));
                    }

                });
            }
            manualControlEnabled = power.getResolveLevel()>=4;

            List<BadMineEntity> mines = MCUtil.entitiesAround(BadMineEntity.class, user, 60, false, badSoldierEntity -> badSoldierEntity.getOwner() ==user);
            if(mines.isEmpty()){
                LazyOptional<LivingData> lazyOptional = user.getCapability(LivingDataProvider.CAPABILITY);
                lazyOptional.ifPresent(livingData -> livingData.setLandedMines(false));
            }else {
                LazyOptional<LivingData> lazyOptional = user.getCapability(LivingDataProvider.CAPABILITY);
                lazyOptional.ifPresent(livingData -> livingData.setLandedMines(true));
            }
        }
    }

    @Override
    public boolean summon(LivingEntity user, IStandPower standPower, Consumer<StandEntity> beforeTheSummon, boolean withoutNameVoiceLine, boolean addToWorld) {
        if(!user.level.isClientSide){
            if(getSumonSoldier(standPower)){
                for(int i=0; i<=9;i++) {
                    BadSoldierEntity soldier = new BadSoldierEntity(user.level);
                    switch (getFormation(standPower)){
                        case 1:
                            soldier.teleportTo(user.getX() + 1.5F*MathHelper.cos(2F*3.14159F*i/10), user.getY(),  user.getZ()+1.5F *MathHelper.sin(2F*3.14159F*i/10));
                            break;
                        case 2:
                            float radius = (float) Math.floor((double) (i + 2) /2);
                            float radiansAngle = (float) (user.yBodyRot*3.14159/180);
                            float centerX = (float) (user.getX()-2*MathHelper.sin(radiansAngle));
                            float centerZ = (float) (user.getZ()+2*MathHelper.cos(radiansAngle));
                            soldier.teleportTo(centerX+radius*MathHelper.cos(radiansAngle+3.14159F*(i+ 1)),user.getY(),centerZ+radius*MathHelper.sin(radiansAngle+3.14159F*(i+ 1)));
                            break;
                        default:
                            soldier.teleportTo(user.getX()+5-10F* Math.random(), user.getY(),user.getZ()+5-10F* Math.random());
                            break;
                    }
                    soldier.setOwnerUUID(user.getUUID());
                    user.level.addFreshEntity(soldier);
                }

            }
            if(standPower.getResolveLevel()>1){
                if(getSumonTank(standPower)){
                    for(int i=0; i<=1; i++){
                        BadTankEntity tankEntity = new BadTankEntity(user);
                        if (getFormation(standPower) == 0) {
                            tankEntity.teleportTo(user.getX() + 5 - 10F * Math.random(), user.getY(), user.getZ() + 5 - 10F * Math.random());
                        } else {
                            float radiansAngle = (float) ((user.yBodyRot + 90) * 3.14159 / 180);
                            tankEntity.teleportTo(user.getX() + 2.5F * MathHelper.cos(radiansAngle + 3.14159F * (i + (float) 1 / 2)), user.getY(), user.getZ() + 2.5F * MathHelper.sin(radiansAngle + 3.14159F * (i + (float) 1 / 2)));
                        }
                        tankEntity.setOwnerUUID(user.getUUID());
                        user.level.addFreshEntity(tankEntity);
                    }
                }
            }
            if(standPower.getResolveLevel()>2){
                if(getSumonCopter(standPower)){
                    for(int i=0; i<=1; i++){
                        BadHelicopterEntity helicopter = new BadHelicopterEntity(user);
                        if (getFormation(standPower) == 0) {
                            helicopter.teleportTo(user.getX() + 5 - 10F * Math.random(), user.getY()+2, user.getZ() + 5 - 10F * Math.random());
                        } else {
                            float radiansAngle = (float) ((user.yBodyRot + 90) * 3.14159 / 180);
                            helicopter.teleportTo(user.getX() + 2.5F * MathHelper.cos(radiansAngle + 3.14159F * (i + (float) 1 / 2)), user.getY()+2, user.getZ() + 2.5F * MathHelper.sin(radiansAngle + 3.14159F * (i + (float) 1 / 2)));
                        }
                        helicopter.setNoGravity(true);
                        helicopter.setOwnerUUID(user.getUUID());
                        user.level.addFreshEntity(helicopter);
                    }
                }
            }

        }
        return super.summon(user, standPower, beforeTheSummon, withoutNameVoiceLine, addToWorld);
    }

    @Override
    public void unsummon(LivingEntity user, IStandPower standPower) {
        super.unsummon(user, standPower);
        if(!user.level.isClientSide){
            LazyOptional<LivingData> playerDataOptional =user.getCapability(LivingDataProvider.CAPABILITY);
            playerDataOptional.ifPresent(playerData ->{
                playerData.setUnitType(0);
                playerData.setSoldierStay(false);
                playerData.setLandedMines(false);
            });
        }
    }

    @Override
    public void forceUnsummon(LivingEntity user, IStandPower standPower) {
        super.forceUnsummon(user, standPower);
        if(!user.level.isClientSide){
            LazyOptional<LivingData> playerDataOptional =user.getCapability(LivingDataProvider.CAPABILITY);
            playerDataOptional.ifPresent(playerData ->{
                playerData.setUnitType(0);
                playerData.setSoldierStay(false);
                playerData.setLandedMines(false);
            });
        }
    }

    public static class Builder<T extends StandStats> extends EntityStandType.AbstractBuilder<Builder<T>, T> {

        @Override
        protected Builder<T> getThis() {
            return this;
        }

        @Override
        public BadCompanyStandType<T> build() {
            return new BadCompanyStandType<>(this);
        }

    }

    @Override
    public boolean canBeManuallyControlled() {
        return manualControlEnabled;
    }

    public boolean getSoldierStay(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::getSoldierStay).isPresent()? livingDataOptional.map(LivingData::getSoldierStay).get():false;
    }

    public boolean getLandedMines(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::getLandedMines).orElse(false);
    }

    public boolean getTankStay(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::getTankStay).isPresent()? livingDataOptional.map(LivingData::getTankStay).get():false;
    }

    public int getUnitType(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::getUnitType).isPresent()? livingDataOptional.map(LivingData::getUnitType).get():0;
    }

    public int getFormation(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::getFormation).isPresent()? livingDataOptional.map(LivingData::getFormation).get():0;
    }

    public boolean getCopterStay(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::getCopterStay).isPresent()? livingDataOptional.map(LivingData::getCopterStay).get():false;
    }


    public boolean getMissileMode(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::isExplosiveMissiles).orElse(false);
    }

    public BlockPos getSoldierPostGoing(IStandPower power){
        LazyOptional<LivingData> livingDataLazyOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return  livingDataLazyOptional.map(LivingData::getSoldierPostGoing).orElse(new BlockPos(0,0,0));
    }

    public BlockPos getTankPostGoing(IStandPower power){
        LazyOptional<LivingData> livingDataLazyOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return  livingDataLazyOptional.map(LivingData::getTankPostGoing).orElse(new BlockPos(0,0,0));
    }

    public BlockPos getCopterPostGoing(IStandPower power){
        LazyOptional<LivingData> livingDataLazyOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return  livingDataLazyOptional.map(LivingData::getCopterPostGoing).orElse(new BlockPos(0,0,0));
    }

    public boolean getSoldierClose(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::isSoldierClose).orElse(false);
    }

    public boolean getTankClose(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::isTankClose).orElse(false);
    }
    public boolean getCopterClose(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::isCopterClose).orElse(false);
    }

    public boolean getSumonSoldier(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::isSummonSoldier).orElse(false);
    }
    public boolean getSumonTank(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::isSummonTank).orElse(false);
    }

    public boolean getSumonCopter(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::isSummonCopter).orElse(false);
    }

}

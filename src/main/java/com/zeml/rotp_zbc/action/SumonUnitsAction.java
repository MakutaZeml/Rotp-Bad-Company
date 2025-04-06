package com.zeml.rotp_zbc.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import com.zeml.rotp_zbc.entity.BadHelicopterEntity;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import com.zeml.rotp_zbc.entity.BadTankEntity;
import com.zeml.rotp_zbc.power.impl.stand.type.BadCompanyStandType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class SumonUnitsAction extends StandAction {
    public SumonUnitsAction(StandAction.Builder builder) {
        super(builder);
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target, @Nullable PacketBuffer extraInput) {
        if(!world.isClientSide){
            user.getCapability(LivingDataProvider.CAPABILITY).resolve().ifPresent(livingData -> {
                switch (livingData.getUnitType()){
                    case 1:
                        if(livingData.isSummonSoldier()){
                            Stream<BadSoldierEntity> units = MCUtil.entitiesAround(BadSoldierEntity.class,user,50,false, badCompanyUnits -> badCompanyUnits.isAlive() && badCompanyUnits.getOwner() == user).stream();
                            units.forEach(Entity::remove);
                        }else {
                            if(power.getStandManifestation() instanceof StandEntity) summonSoldiers(user,livingData);

                        }
                        livingData.setSummonSoldier(!livingData.isSummonSoldier());
                        break;
                    case 2:
                        if(livingData.isSummonTank()){
                            Stream<BadTankEntity> units = MCUtil.entitiesAround(BadTankEntity.class,user,50,false, badCompanyUnits -> badCompanyUnits.isAlive() && badCompanyUnits.getOwner() == user).stream();
                            units.forEach(Entity::remove);
                        }else {
                            if(power.getStandManifestation() instanceof StandEntity) summonTanks(user,livingData,power);
                        }
                        livingData.setSummonTank(!livingData.isSummonTank());

                        break;
                    case 3:
                        if(livingData.isSummonCopter()){
                            Stream<BadHelicopterEntity> units = MCUtil.entitiesAround(BadHelicopterEntity.class,user,50,false, badCompanyUnits -> badCompanyUnits.isAlive() && badCompanyUnits.getOwner() == user).stream();
                            units.forEach(Entity::remove);
                        }else {
                            if(power.getStandManifestation() instanceof StandEntity) summonCopters(user,livingData,power);
                        }
                        livingData.setSummonCopter(!livingData.isSummonCopter());

                        break;
                    default:
                        if(!user.isShiftKeyDown()){
                            if(power.getStandManifestation() instanceof StandEntity){
                                if(!livingData.isSummonSoldier()){
                                    summonSoldiers(user,livingData);
                                }
                                if(!livingData.isSummonTank()) {
                                    summonTanks(user,livingData,power);
                                }
                                if(!livingData.isSummonCopter()){
                                    summonCopters(user,livingData,power);
                                }
                            }
                            livingData.setSummonSoldier(true);
                            livingData.setSummonTank(true);
                            livingData.setSummonCopter(true);

                        }else {
                            livingData.setSummonSoldier(false);
                            livingData.setSummonTank(false);
                            livingData.setSummonCopter(false);
                            Stream<BadCompanyUnitEntity> units = MCUtil.entitiesAround(BadCompanyUnitEntity.class,user,50,false, badCompanyUnits -> badCompanyUnits.isAlive() && badCompanyUnits.getOwner() == user).stream();
                            units.forEach(Entity::remove);
                        }

                        break;

                }
            });
        }
    }



    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) {
        TranslationTextComponent name = new TranslationTextComponent("entity.rotp_zbc.all");
        TranslationTextComponent summon = new TranslationTextComponent("entity.rotp_zbc.su");
        int type = ((BadCompanyStandType<?>) power.getType()).getUnitType(power);
        switch (type){
            case 1:
                name = new TranslationTextComponent("entity.rotp_zbc.bad_soldier");
                if(((BadCompanyStandType<?>) power.getType()).getSumonSoldier(power)){
                    summon = new TranslationTextComponent("entity.rotp_zbc.unsu");
                }

                break;
            case 2:
                name = new TranslationTextComponent("entity.rotp_zbc.bad_tank");
                if(((BadCompanyStandType<?>) power.getType()).getSumonTank(power)){
                    summon = new TranslationTextComponent("entity.rotp_zbc.unsu");
                }
                break;
            case 3:
                name = new TranslationTextComponent("entity.rotp_zbc.bad_helicopter");
                if(((BadCompanyStandType<?>) power.getType()).getSumonCopter(power)){
                    summon = new TranslationTextComponent("entity.rotp_zbc.unsu");
                }
                break;
            default:
                if(power.getUser().isShiftKeyDown()){
                    summon = new TranslationTextComponent("entity.rotp_zbc.unsu");
                }
        }
        return new TranslationTextComponent(key,summon,name);
    }

    public void summonSoldiers(LivingEntity user, LivingData livingData){
        for(int i=0; i<=9;i++) {
            BadSoldierEntity soldier = new BadSoldierEntity(user.level);
            switch (livingData.getFormation()){
                case 1:
                    soldier.teleportTo(user.getX() + 1.5F* MathHelper.cos(2F*3.14159F*i/10), user.getY(),  user.getZ()+1.5F *MathHelper.sin(2F*3.14159F*i/10));
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


    public void summonTanks(LivingEntity user, LivingData livingData, IStandPower standPower){
        if(standPower.getResolveLevel()>1){
            for(int i=0; i<=1; i++){
                BadTankEntity tankEntity = new BadTankEntity(user);
                if (livingData.getFormation() == 0) {
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

    public void summonCopters(LivingEntity user, LivingData livingData, IStandPower standPower){
        if(standPower.getResolveLevel()>2){
            for(int i=0; i<=1; i++){
                BadHelicopterEntity helicopter = new BadHelicopterEntity(user);
                if (livingData.getFormation() == 0) {
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

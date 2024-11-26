package com.zeml.rotp_zbc.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.List;

public class DontShootOrder extends StandAction {
    public DontShootOrder(StandAction.Builder builder){
        super(builder);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        return power.getStandManifestation() instanceof StandEntity? ActionConditionResult.POSITIVE: ActionConditionResult.NEGATIVE;
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if(!world.isClientSide){
            LazyOptional<LivingData> playerDataOptional = user.getCapability(LivingDataProvider.CAPABILITY);
            playerDataOptional.ifPresent(playerData ->{
                List<BadCompanyUnitEntity> badSoldierEntities ;

                switch (playerData.getUnitType()){
                    case 1:
                        badSoldierEntities = MCUtil.entitiesAround(BadSoldierEntity.class,user,160,false, badSoldierEntity -> badSoldierEntity.getOwner() == user);
                        break;
                    case 2:
                        badSoldierEntities = MCUtil.entitiesAround(BadTankEntity.class,user,160,false, badSoldierEntity -> badSoldierEntity.getOwner() == user);
                        break;
                    case 3:
                        badSoldierEntities = MCUtil.entitiesAround(BadHelicopterEntity.class,user,160,false, badSoldierEntity -> badSoldierEntity.getOwner() == user);
                        break;
                    default:
                        badSoldierEntities = MCUtil.entitiesAround(BadCompanyUnitEntity.class,user,160,false, badSoldierEntity -> badSoldierEntity.getOwner() == user);
                        break;
                }
                if(!badSoldierEntities.isEmpty()){
                    badSoldierEntities.forEach(badSoldier ->{
                        badSoldier.setTarget(null);
                    });
                }
            });
        }
    }

    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) {
        TranslationTextComponent name = new TranslationTextComponent("entity.rotp_zbc.all");
        int type = ((BadCompanyStandType<?>) power.getType()).getUnitType(power);
        switch (type){
            case 1: name = new TranslationTextComponent("entity.rotp_zbc.bad_soldier");
                break;
            case 2: name = new TranslationTextComponent("entity.rotp_zbc.bad_tank");
                break;
            case 3: name = new TranslationTextComponent("entity.rotp_zbc.bad_helicopter");
                break;
        }
        return new TranslationTextComponent(key,name);
    }

}

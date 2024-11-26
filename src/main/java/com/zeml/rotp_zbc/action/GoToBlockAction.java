package com.zeml.rotp_zbc.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import com.zeml.rotp_zbc.entity.BadHelicopterEntity;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import com.zeml.rotp_zbc.entity.BadTankEntity;
import com.zeml.rotp_zbc.power.impl.stand.type.BadCompanyStandType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class GoToBlockAction extends StandEntityAction {
    public GoToBlockAction(StandEntityAction.Builder builder){
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide){
            LivingEntity user = userPower.getUser();
            BlockPos blockPos = task.getTarget().getBlockPos();

            System.out.println(blockPos);

            LazyOptional<LivingData> playerDataOptional = user.getCapability(LivingDataProvider.CAPABILITY);
            playerDataOptional.ifPresent(playerData ->{
                List<BadCompanyUnitEntity> badCompanyUnitEntities ;
                switch (playerData.getUnitType()){
                    case 1:  playerData.setSoldierPostGoing(blockPos.above());
                        badCompanyUnitEntities = MCUtil.entitiesAround(BadSoldierEntity.class,user,160,false, badSoldierEntity -> badSoldierEntity.getOwner() == user);
                        break;
                    case 2: playerData.setTankPostGoing(blockPos.above());
                        badCompanyUnitEntities = MCUtil.entitiesAround(BadTankEntity.class,user,160,false, badSoldierEntity -> badSoldierEntity.getOwner() == user);
                        break;
                    case 3: playerData.setCopterPostGoing(blockPos.above(3));
                        badCompanyUnitEntities = MCUtil.entitiesAround(BadHelicopterEntity.class,user,160,false, badSoldierEntity -> badSoldierEntity.getOwner() == user);
                        break;
                    default:
                        playerData.setSoldierPostGoing(blockPos.above());
                        playerData.setTankPostGoing(blockPos.above());
                        playerData.setCopterPostGoing(blockPos.above(3));
                        badCompanyUnitEntities = MCUtil.entitiesAround(BadCompanyUnitEntity.class,user,160,false, badSoldierEntity -> badSoldierEntity.getOwner() == user);
                        break;
                }
                if(!badCompanyUnitEntities.isEmpty()){
                    badCompanyUnitEntities.forEach(badUnit->badUnit.setGoingTo(true));
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

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.BLOCK;
    }
}

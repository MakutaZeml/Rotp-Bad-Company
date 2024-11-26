package com.zeml.rotp_zbc.action;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import com.zeml.rotp_zbc.entity.damaging.BadMineEntity;
import com.zeml.rotp_zbc.init.InitStands;
import com.zeml.rotp_zbc.power.impl.stand.type.BadCompanyStandType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PutMine extends StandEntityAction {

    public PutMine(StandEntityAction.Builder builder){
        super(builder);
    }


    @Nullable
    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        return  ((BadCompanyStandType<?>) power.getType()).getLandedMines(power)&& power.getStandManifestation() instanceof  StandEntity ? InitStands.EXPLODE_MINE.get() :super.replaceAction(power, target);

    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide){
            LivingEntity user = userPower.getUser();
            List<BadSoldierEntity> soldierEntities = MCUtil.entitiesAround(BadSoldierEntity.class,standEntity,150,false,badSoldierEntity -> badSoldierEntity.getOwner() == userPower.getUser());
            if(!soldierEntities.isEmpty()){
                soldierEntities.forEach(badSoldierEntity -> {
                    if(badSoldierEntity.isAlive()){
                        BadMineEntity mine = new BadMineEntity(user,world);
                        mine.setOwner(user);
                        mine.copyPosition(badSoldierEntity);
                        world.addFreshEntity(mine);
                    }
                    LazyOptional<LivingData> livingDataLazyOptional = user.getCapability(LivingDataProvider.CAPABILITY);
                    livingDataLazyOptional.ifPresent(livingData -> livingData.setLandedMines(true));

                });
            }
        }
    }
}

package com.zeml.rotp_zbc.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import com.zeml.rotp_zbc.entity.damaging.BadMineEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class ExplodeMines extends StandEntityAction {
    public ExplodeMines(StandEntityAction.Builder builder){
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide) {
            LivingEntity user = userPower.getUser();
            float range = (float) standEntity.getMaxRange() * 50F;
            List<BadMineEntity> soldierEntities = MCUtil.entitiesAround(BadMineEntity.class, standEntity, range, false, badSoldierEntity -> badSoldierEntity.getOwner() ==user);
            if (!soldierEntities.isEmpty()) {
                soldierEntities.forEach(badSoldierEntity -> {
                   world.explode(user,badSoldierEntity.getX(),badSoldierEntity.getY(),badSoldierEntity.getZ(),1.5F, Explosion.Mode.NONE);
                   badSoldierEntity.remove();
                });
                LazyOptional<LivingData> livingDataLazyOptional = user.getCapability(LivingDataProvider.CAPABILITY);
                livingDataLazyOptional.ifPresent(livingData -> livingData.setLandedMines(false));
            }
        }
    }


}

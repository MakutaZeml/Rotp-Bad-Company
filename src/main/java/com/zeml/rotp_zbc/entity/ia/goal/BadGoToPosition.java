package com.zeml.rotp_zbc.entity.ia.goal;

import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import com.zeml.rotp_zbc.entity.BadHelicopterEntity;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import com.zeml.rotp_zbc.entity.BadTankEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

public class BadGoToPosition extends MoveToBlockGoal {
    private final BadCompanyUnitEntity unit;
    @Nullable
    private final LivingEntity owner;

    public BadGoToPosition(BadCompanyUnitEntity unitEntity) {
        super(unitEntity, 1, 60);
        this.unit = unitEntity;
        this.owner = unitEntity.getOwner();
    }

    @Override
    public boolean canUse() {
        if(owner != null){
            return unit.getGoingTo();
        }
        return false;
    }

    @Override
    protected boolean isValidTarget(IWorldReader p_179488_1_, BlockPos blockPos) {
        if(owner != null){
            LazyOptional<LivingData> livingDataLazyOptional = owner.getCapability(LivingDataProvider.CAPABILITY);
            if(unit instanceof BadSoldierEntity){
                return blockPos.equals(livingDataLazyOptional.map(LivingData::getSoldierPostGoing).orElse(new BlockPos(0,0,0)));
            } else if (unit instanceof BadTankEntity) {
                return blockPos.equals(livingDataLazyOptional.map(LivingData::getTankPostGoing).orElse(new BlockPos(0,0,0)));
            } else if (unit instanceof BadHelicopterEntity) {
                return blockPos.equals(livingDataLazyOptional.map(LivingData::getCopterPostGoing).orElse(new BlockPos(0,0,0)));

            }
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        System.out.println(unit.getGoingTo());
    }

    @Override
    public void stop() {
        super.stop();
        unit.setGoingTo(false);
    }
}

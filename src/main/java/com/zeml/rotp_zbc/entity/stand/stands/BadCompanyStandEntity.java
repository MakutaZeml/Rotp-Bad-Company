package com.zeml.rotp_zbc.entity.stand.stands;


import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class BadCompanyStandEntity extends StandEntity {

    public BadCompanyStandEntity(StandEntityType<BadCompanyStandEntity> type, World world){
        super(type, world);
        unsummonOffset = getDefaultOffsetFromUser().copy();
    }

    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide){
            if(this.getUser() != null){
                this.getUser().getCapability(LivingDataProvider.CAPABILITY).ifPresent(data ->{
                    if(data.isExplosiveMissiles() && !JojoModUtil.breakingBlocksEnabled(this.level)){
                        data.setExplosiveMissiles(false);
                    }
                });
            }
            List<BadCompanyUnitEntity> unitEntityList = MCUtil.entitiesAround(BadCompanyUnitEntity.class,this,this.getMaxRange(),false,unitEntity -> unitEntity.isAlliedTo(this) &&unitEntity.isAlive());
            if(!unitEntityList.isEmpty()){
                unitEntityList.forEach(unitEntity -> unitEntity.setStandSkin(this.getStandSkin()));
            }
        }
    }

    private final StandRelativeOffset offsetDefault = StandRelativeOffset.withYOffset(0, 1, 0);

    @Override
    public boolean isPickable(){ return false;}

    @Override
    public double getMaxRange() {
        return super.getMaxRange()*6;
    }

    @Override
    public double getMaxEffectiveRange() {
        return super.getMaxEffectiveRange()*6;
    }

    public StandRelativeOffset getDefaultOffsetFromUser() {return offsetDefault;}

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.addEffect(new EffectInstance(ModStatusEffects.FULL_INVISIBILITY.get(),Integer.MAX_VALUE,Integer.MAX_VALUE,false,false,false));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSrc) {
        return true;
    }
}

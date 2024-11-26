package com.zeml.rotp_zbc.entity.damaging;

import com.github.standobyte.jojo.entity.damaging.DamagingEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import com.zeml.rotp_zbc.init.InitEntities;
import com.zeml.rotp_zbc.init.InitStands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class BadMineEntity extends DamagingEntity {
    private UUID ownerUUID;

    private int ownerNetworkId;
    private static final DataParameter<Integer> ACTIVATION = EntityDataManager.defineId(BadMineEntity.class, DataSerializers.INT);

    private float range=30;

    public BadMineEntity(EntityType<BadMineEntity> badMineEntityEntityType, World world) {
        super(badMineEntityEntityType, world);
    }
    public BadMineEntity(@Nullable LivingEntity owner, World world) {
        super(InitEntities.BAD_MINE.get(), owner, world);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ACTIVATION, 100);
    }


    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide){
            if(this.getOwner() != null){
                IStandPower.getStandPowerOptional(this.getOwner()).ifPresent(power -> {
                    StandType<?> AC = InitStands.STAND_BAD_COMPANY.getStandType();
                    this.range = (float) power.getType().getStats().getMaxRange()*3;
                    if(power.getType() != AC || !power.isActive() || this.distanceTo(this.getOwner())>range || !this.getOwner().isAlive()){
                        this.remove();
                        MCUtil.runCommand(this.getOwner(),"kill "+this.getUUID());
                    }
                });
            }
            List<LivingEntity> list = MCUtil.entitiesAround(LivingEntity.class,this,this.getBbWidth(),false,LivingEntity::isAlive);
            if(!list.isEmpty()){
                list.forEach(entity -> {
                    if(this.getOwner() != null){
                        if(entity != this.getOwner() && !entity.isAlliedTo(this.getOwner())){
                            level.explode(this.getOwner(),this.getX(),this.getY(),this.getZ(),1.7F, Explosion.Mode.NONE);
                            this.remove();
                            MCUtil.runCommand(this.getOwner(),"kill "+this.getUUID());
                        }
                    }else {
                        level.explode(this,this.getX(),this.getY(),this.getZ(),1.7F, Explosion.Mode.NONE);
                        this.remove();
                    }

                });
            }
        }

    }



    @Override
    protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        Entity entity = entityRayTraceResult.getEntity();
        if(!this.level.isClientSide){
            Entity owner = this.getOwner() == null? this: this.getOwner();
            if(entity.isAlliedTo(owner)){
                level.explode(owner,this.getX(),this.getY(),this.getZ(),1.7F, Explosion.Mode.NONE);
                this.remove();
            }
        }
    }



    @Override
    public int ticksLifespan() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected float getBaseDamage() {
        return 0;
    }

    @Override
    protected float getMaxHardnessBreakable() {
        return 0;
    }

    @Override
    public boolean standDamage() {
        return false;
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        if (this.ownerUUID != null) {
            nbt.putUUID("StandOwner", this.ownerUUID);
        }
        nbt.putInt("Activation",this.entityData.get(ACTIVATION));
    }


    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        if (nbt.hasUUID("StandOwner")) {
            this.ownerUUID = nbt.getUUID("StandOwner");
        }
        this.entityData.set(ACTIVATION,nbt.getInt("Activation"));
    }



}

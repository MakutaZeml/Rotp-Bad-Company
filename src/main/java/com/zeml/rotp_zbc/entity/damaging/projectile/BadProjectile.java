package com.zeml.rotp_zbc.entity.damaging.projectile;

import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

public abstract class BadProjectile extends ModdedProjectileEntity {
    private UUID ownerUUID;
    private int ownerNetworkId;

    public BadProjectile(EntityType<? extends BadProjectile> type, World world){
        super(type, world);
    }

    protected BadProjectile(EntityType<? extends ModdedProjectileEntity> type, LivingEntity shooter, World world) {
        super(type, shooter, world);
    }


    public boolean getDeflectedUsingReflection() {
        try {
            Field field = ModdedProjectileEntity.class.getDeclaredField("IS_DEFLECTED");
            field.setAccessible(true);
            DataParameter<Boolean> deflectedParam = (DataParameter<Boolean>) field.get(this);
            return this.getEntityData().get(deflectedParam);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean isAlliedTo(Entity p_184191_1_) {
        if(this.getOwner() != null){
            return this.getOwner().isAlliedTo(p_184191_1_);
        }
        return super.isAlliedTo(p_184191_1_);
    }


    public void setStandOwner(@Nullable Entity standOwner) {
        if (standOwner != null) {
            this.ownerUUID = standOwner.getUUID();
            this.ownerNetworkId = standOwner.getId();
        }

    }

    @Nullable
    public Entity getStandOwner() {
        if (this.ownerUUID != null && this.level instanceof ServerWorld) {
            return ((ServerWorld)this.level).getEntity(this.ownerUUID);
        } else {
            return this.ownerNetworkId != 0 ? this.level.getEntity(this.ownerNetworkId) : null;
        }
    }


    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        if (this.ownerUUID != null) {
            p_213281_1_.putUUID("StandOwner", this.ownerUUID);
        }
    }


    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        if (p_70037_1_.hasUUID("StandOwner")) {
            this.ownerUUID = p_70037_1_.getUUID("StandOwner");
        }
    }


}

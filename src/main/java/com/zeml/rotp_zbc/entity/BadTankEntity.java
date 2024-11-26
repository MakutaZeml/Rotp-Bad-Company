package com.zeml.rotp_zbc.entity;

import com.zeml.rotp_zbc.entity.damaging.projectile.BadMissileEntity;
import com.zeml.rotp_zbc.init.InitEntities;
import com.zeml.rotp_zbc.init.InitSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BadTankEntity extends BadCompanyUnitEntity{

    public BadTankEntity(LivingEntity user){
        super(InitEntities.BAD_TANK.get(),user.level);
        this.setOwnerUUID(user.getUUID());
    }

    public BadTankEntity(EntityType<? extends BadTankEntity> p_i48574_1_, World p_i48574_2_) {
        super(p_i48574_1_, p_i48574_2_);
    }


    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return TameableEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.3D)
                .add(Attributes.ARMOR,20)
                .add(Attributes.KNOCKBACK_RESISTANCE,6);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 0, 40, 40.0F));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this,.1F));

    }


    @Override
    protected SoundEvent getAmbientSound() {
        return InitSounds.TANK_LIVING.get();
    }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(InitSounds.TANK_STEP.get(),1F,1F);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return InitSounds.TANK_HURT.get();
    }

    public void performRangedAttack(LivingEntity target, float p_82196_2_) {
        if(this.getShoot()){
            if(this.getOwner() != null){
                if(this.getOwner().distanceTo(target)>6){
                    shootMissile(target);
                }else {

                }
            }else {
                shootMissile(target);
            }
        }
    }



    private void shootMissile( LivingEntity target){
        double dx = target.getX() - this.getX();
        double dy = target.getEyeY() - (this.getEyeY()+.5);
        double dz = target.getZ() - this.getZ();
        BadMissileEntity bulletEntity = new BadMissileEntity(this, this.level);
        if(this.getOwner()!=null){
            bulletEntity.setStandOwner(this.getOwner());
        }
        bulletEntity.shoot(dx, dy, dz, 1.6F, 0.02F);
        this.playSound(InitSounds.TANK_SHOT.get(), 2.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(bulletEntity);
        bulletEntity.setOwner(this.getOwner()==null?this:this.getOwner());
    }


}

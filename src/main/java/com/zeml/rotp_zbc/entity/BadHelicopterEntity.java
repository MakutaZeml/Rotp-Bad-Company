package com.zeml.rotp_zbc.entity;

import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zbc.entity.damaging.projectile.BadBulletEntity;
import com.zeml.rotp_zbc.entity.damaging.projectile.BadMissileEntity;
import com.zeml.rotp_zbc.entity.ia.goal.BadFollowOwnerGoal;
import com.zeml.rotp_zbc.init.InitEntities;
import com.zeml.rotp_zbc.init.InitSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BadHelicopterEntity extends BadCompanyUnitEntity implements IFlyingAnimal {
    private static final DataParameter<Integer> MISSILES = EntityDataManager.defineId(BadHelicopterEntity.class, DataSerializers.INT);

    private float range=30;

    public BadHelicopterEntity(EntityType<? extends TameableEntity> p_i48574_1_, World p_i48574_2_) {
        super(p_i48574_1_, p_i48574_2_);
        this.moveControl = new FlyingMovementController(this, 70, true);
        this.setNoGravity(true);
    }

    public BadHelicopterEntity(LivingEntity user){
        super(InitEntities.BAD_HELICOPTER.get(),user.level);
        this.setOwnerUUID(user.getUUID());
        this.moveControl = new FlyingMovementController(this, 70, true);
        this.navigation = new FlyingPathNavigator(this,this.level);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, -1.0F);
        this.setNoGravity(true);

    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(MISSILES,6);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Missiles",this.entityData.get(MISSILES));
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(MISSILES,nbt.getInt("Missiles"));
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 0, 5,40, 30.0F));
        this.goalSelector.addGoal(3,new RandomWalkingGoal(this,1));
    }


    @Override
    public float getWalkTargetValue(BlockPos p_205022_1_, IWorldReader p_205022_2_) {
        return p_205022_2_.getBlockState(p_205022_1_).isAir() ? 10.0F : 5.0F;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return TameableEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED,.4)
                .add(Attributes.FLYING_SPEED, 1)
                .add(Attributes.ARMOR,10)
                .add(Attributes.KNOCKBACK_RESISTANCE,4);
    }

    private int counter = 0;
    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide) {
            if(getMissiles() <=0){
                counter++;
                if(counter == 600){
                    entityData.set(MISSILES,6);
                    counter = 0;
                }
            }
        }
    }

    @Override
    protected PathNavigator createNavigation(World p_175447_1_) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, p_175447_1_);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
        }

    @Override
    public void performRangedAttack(LivingEntity target, float p_82196_2_) {
        if(this.getShoot()){
            BadBulletEntity bulletEntity = new BadBulletEntity(this, this.level);
            double dx = target.getX() - this.getX();
            double dy = target.getEyeY() - this.getEyeY();
            double dz = target.getZ() - this.getZ();
            bulletEntity.shoot(dx, dy, dz, 1.6F, 0.02F);
            this.playSound(InitSounds.SOLDIER_SHOT.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level.addFreshEntity(bulletEntity);
            if(this.getOwner()!=null){
                bulletEntity.setStandOwner(this.getOwner());
            }
            if(entityData.get(MISSILES)>0 && Math.random() <.2){
                if(this.getOwner() != null){
                    if(target.distanceTo(this.getOwner())>6){
                        shootMissile(target);
                    }
                }else {
                    shootMissile(target);
                }
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return InitSounds.COPTER.get();
    }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(InitSounds.COPTER.get(),1F,1F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 30;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return InitSounds.TANK_HURT.get();
    }

    public int getMissiles(){
        return  this.entityData.get(MISSILES);
    }

    public void setMissiles(int missiles){
        this.entityData.set(MISSILES,missiles);
    }

    private void shootMissile( LivingEntity target){
        double dx = target.getX() - this.getX();
        double dy = target.getEyeY() - (this.getEyeY()+.5);
        double dz = target.getZ() - this.getZ();
        BadMissileEntity bulletEntity = new BadMissileEntity(this, this.level);
        bulletEntity.shoot(dx, dy, dz, 1.6F, 0.02F);
        if(this.getOwner()!=null){
            bulletEntity.setStandOwner(this.getOwner());
        }
        this.playSound(InitSounds.TANK_SHOT.get(), 2.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(bulletEntity);
    }

}

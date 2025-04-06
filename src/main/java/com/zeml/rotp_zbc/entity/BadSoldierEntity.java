package com.zeml.rotp_zbc.entity;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zbc.entity.damaging.projectile.BadBulletEntity;
import com.zeml.rotp_zbc.entity.stand.stands.BadCompanyStandEntity;
import com.zeml.rotp_zbc.init.InitEntities;
import com.zeml.rotp_zbc.init.InitItems;
import com.zeml.rotp_zbc.init.InitSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class BadSoldierEntity extends BadCompanyUnitEntity {
    private static final DataParameter<Boolean> IS_SHOOTING = EntityDataManager.defineId(BadSoldierEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> PARACHUTE = EntityDataManager.defineId(BadSoldierEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.defineId(BadSoldierEntity.class, DataSerializers.BYTE);

    private boolean toMe = false;
    private float range=30;
    public BadSoldierEntity(@NotNull World world){
        super(InitEntities.BAD_SOLDIER.get(), world);
    }
    public BadSoldierEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }


    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return TameableEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 1D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_SHOOTING,false);
        this.entityData.define(PARACHUTE,false);
        this.entityData.define(DATA_FLAGS_ID, (byte)0);

    }


    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Shoot",this.entityData.get(IS_SHOOTING));
        nbt.putBoolean("Parachuting",this.entityData.get(PARACHUTE));
        nbt.putBoolean("toMe", this.toMe);
    }


    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(IS_SHOOTING,nbt.getBoolean ("Shoot"));
        this.entityData.set(PARACHUTE,nbt.getBoolean ("Parachuting"));
        this.toMe = nbt.getBoolean("toMe");

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this,.1F));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 0, 20, 30.0F));

    }

    ItemStack gun = new ItemStack(InitItems.BAD_GUN.get());
    @Override
    public void tick() {
        if(!level.isClientSide){
            this.setClimbing(this.horizontalCollision);
            setShooting(this.getTarget() != null && this.getTarget().isAlive());
            if(this.getTarget() != null){
                this.setItemInHand(Hand.MAIN_HAND, gun);
            }else {
                this.setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
            }

            boolean para = !this.isOnGround() && this.level.getBlockState(this.blockPosition().below().below()).is(Blocks.AIR) && !this.isClimbing();
            this.entityData.set(PARACHUTE,para);
            if(para){
                this.addEffect(new EffectInstance(Effects.SLOW_FALLING,40,1,false,false,false));
            }


        }
        super.tick();
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }



    public void performRangedAttack(LivingEntity target, float p_82196_2_) {
        if(this.getShoot()){
            double dx = target.getX() - this.getX();
            double dy = target.getEyeY() - this.getEyeY();
            double dz = target.getZ() - this.getZ();
            BadBulletEntity bulletEntity = new BadBulletEntity(this, this.level);
            bulletEntity.shoot(dx, dy, dz, 1.6F, 0.02F);
            if(this.getOwner()!=null){
                bulletEntity.setStandOwner(this.getOwner());
                IStandPower.getStandPowerOptional(this.getOwner()).ifPresent(power -> {
                    bulletEntity.withStandSkin(((StandEntity)power.getStandManifestation()).getStandSkin());
                });
            }
            this.playSound(InitSounds.SOLDIER_SHOT.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level.addFreshEntity(bulletEntity);
        }
    }

    protected PathNavigator createNavigation(World p_175447_1_) {
        return new ClimberPathNavigator(this, p_175447_1_);
    }


    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        if(!this.isUnderWater()){
            this.playSound(InitSounds.SOLDIER_STEP.get(), .5F, 1.0F);
        }else {
            super.playStepSound(p_180429_1_, p_180429_2_);
        }
    }

    @Override
    public boolean canBeLeashed(PlayerEntity p_184652_1_) {
        return false;
    }


    @Override
    protected int getExperienceReward(PlayerEntity p_70693_1_) {
        return 0;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isShooting() {
        return this.entityData.get(IS_SHOOTING);
    }

    public void setShooting(boolean p_213671_1_) {
        this.entityData.set(IS_SHOOTING, p_213671_1_);
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }
    public void setClimbing(boolean p_70839_1_) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (p_70839_1_) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    public boolean isToMe() {
        return this.toMe;
    }

    public void setToMe(boolean toMe) {
        this.toMe = toMe;
    }

    @OnlyIn(Dist.CLIENT)
    public ArmPose getArmPose(){
        if(this.entityData.get(PARACHUTE)){
            if(this.isShooting()){
                return ArmPose.GUN_PARACHUTE;
            }
            return ArmPose.PARACHUTE;
        }
        if(this.isShooting()){
            return ArmPose.GUN;
        }
        if(this.isClimbing()){
            return ArmPose.CLIM;
        }
        return ArmPose.NEUTRAL;
    }

    @OnlyIn(Dist.CLIENT)
    public static enum ArmPose {
        GUN,
        PARACHUTE,
        GUN_PARACHUTE,
        NEUTRAL,
        CLIM;
    }


}

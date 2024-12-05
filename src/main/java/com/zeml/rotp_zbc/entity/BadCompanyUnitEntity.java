package com.zeml.rotp_zbc.entity;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.github.standobyte.jojo.util.mc.damage.IModdedDamageSource;
import com.github.standobyte.jojo.util.mc.damage.IStandDamageSource;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.entity.ia.goal.BadFollowOwnerGoal;
import com.zeml.rotp_zbc.entity.ia.goal.BadGoToPosition;
import com.zeml.rotp_zbc.entity.ia.goal.BadNearrestAttackableGoal;
import com.zeml.rotp_zbc.init.InitStands;
import com.zeml.rotp_zbc.init.InitStatusEffect;
import com.zeml.rotp_zbc.power.impl.stand.type.BadCompanyStandType;
import com.zeml.rotp_zbc.util.AddonUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class BadCompanyUnitEntity extends TameableEntity implements IRangedAttackMob {
    private static final DataParameter<Boolean> STAY_CLOSE = EntityDataManager.defineId(BadCompanyUnitEntity.class,DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SHOOT = EntityDataManager.defineId(BadCompanyUnitEntity.class,DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SEARCH_PLAYER = EntityDataManager.defineId(BadCompanyUnitEntity.class,DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GOINGTO = EntityDataManager.defineId(BadCompanyUnitEntity.class,DataSerializers.BOOLEAN);
    private static final DataParameter<BlockPos> POSITION = EntityDataManager.defineId(BadCompanyUnitEntity.class,DataSerializers.BLOCK_POS);
    protected BadCompanyUnitEntity(EntityType<? extends TameableEntity> p_i48574_1_, World p_i48574_2_) {
        super(p_i48574_1_, p_i48574_2_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new LookAtGoal(this, MobEntity.class, 50F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 30.0F, 5.0F, this instanceof BadSoldierEntity));
        this.goalSelector.addGoal(1, new BadGoToPosition(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 5, false, false, (p_234199_0_) -> {
            return p_234199_0_ instanceof IMob && p_234199_0_ != this.getOwner();
        }));
        this.targetSelector.addGoal(3,  new BadNearrestAttackableGoal<>(this, PlayerEntity.class,5,false,false, LivingEntity::isAlive));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STAY_CLOSE,false);
        this.entityData.define(SHOOT,true);
        this.entityData.define(SEARCH_PLAYER,false);
        this.entityData.define(GOINGTO, false);
    }



    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide){
            if(this.getOwner() != null){
                IStandPower.getStandPowerOptional(this.getOwner()).ifPresent(power -> {
                    StandType<?> AC = InitStands.STAND_BAD_COMPANY.getStandType();
                    if(power.getType() != AC || !power.isActive()  || !this.getOwner().isAlive()){
                        this.remove();
                    }

                    if(AddonUtil.getLivingFromUUID(this.getOwnerUUID(),this) != null){
                        this.remove();
                    }

                    if(power != null){
                        StandEntity stand = (StandEntity) power.getStandManifestation();
                        if(stand != null){
                            float range = this.entityData.get(STAY_CLOSE)? 10F: (float) stand.getMaxRange()*6F;

                            if(this.distanceTo(this.getOwner()) > range+10){
                                if(this instanceof BadHelicopterEntity){
                                    this.teleportTo(this.getOwner().getX()+5-10F* Math.random(),this.getOwner().getY()+3,this.getOwner().getZ()+5-10F* Math.random());
                                }else {
                                    this.teleportTo(this.getOwner().getX()+5-10F* Math.random(),this.getOwner().getY(),this.getOwner().getZ()+5-10F* Math.random());

                                }
                            }
                            if(this.getTarget() != null && this.distanceTo(this.getOwner())>range){
                                if(this instanceof BadHelicopterEntity){
                                    this.navigation.moveTo(this.getOwner().getX(),this.getOwner().getY()+3,this.getOwner().getZ(),.5);
                                }else {
                                    this.navigation.moveTo(this.getOwner(),.5);

                                }
                            }
                        }
                    }

                    if(this.entityData.get(GOINGTO) && this.getTarget() == null){
                        if(this instanceof BadSoldierEntity){
                            this.removeEffect(InitStatusEffect.SAT.get());
                            BlockPos pos = ((BadCompanyStandType<?>) power.getType()).getSoldierPostGoing(power);
                            this.navigation.moveTo(pos.getX(),pos.getY(),pos.getZ(),.3);
                            if(this.distanceToSqr(Vector3d.atCenterOf(pos))<1){
                                this.entityData.set(GOINGTO,false);
                                if(((BadCompanyStandType<?>) power.getType()).getSoldierStay(power)){
                                    this.addEffect(new EffectInstance(InitStatusEffect.SAT.get(),Integer.MAX_VALUE));
                                }
                            }
                        }
                        if(this instanceof BadTankEntity){
                            this.removeEffect(InitStatusEffect.SAT.get());
                            BlockPos pos = ((BadCompanyStandType<?>) power.getType()).getTankPostGoing(power);
                            this.navigation.moveTo(pos.getX(),pos.getY(),pos.getZ(),.3);
                            if(this.distanceToSqr(Vector3d.atCenterOf(pos))<1){
                                this.entityData.set(GOINGTO,false);
                                if(((BadCompanyStandType<?>) power.getType()).getTankStay(power)){
                                    this.addEffect(new EffectInstance(InitStatusEffect.SAT.get(),Integer.MAX_VALUE));
                                }
                            }
                        }
                        if(this instanceof BadHelicopterEntity){
                            BlockPos pos = ((BadCompanyStandType<?>) power.getType()).getCopterPostGoing(power);
                            this.navigation.moveTo(pos.getX(),pos.getY(),pos.getZ(),.3);
                            if(this.distanceToSqr(Vector3d.atCenterOf(pos))<1){
                                this.entityData.set(GOINGTO,false);
                            }
                        }

                    }
                });


                if(this.getOwner().getLastHurtMob() != null && this.getOwner().getLastHurtMob().isAlive()){
                    if(this.getOwner().tickCount-this.getOwner().getLastHurtMobTimestamp() <2){
                        this.setTarget(this.getOwner().getLastHurtMob());
                    }
                }
                if(this.getOwner().getLastHurtByMob() != null && this.getOwner().getLastHurtByMob().isAlive()){
                    if(this.getOwner().tickCount-this.getOwner().getLastHurtByMobTimestamp() <2){
                        this.setTarget(this.getOwner().getLastHurtByMob());
                    }
                }
            }else {
                this.remove();
            }
        }
    }




    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }


    @Override
    public boolean isInvulnerableTo(DamageSource damageSrc) {
        if (damageSrc == DamageSource.OUT_OF_WORLD) {
            return false;
        }
        if (this.is(damageSrc.getEntity())
                || getOwner() != null && getOwner().is(damageSrc.getEntity())
                || getOwner() instanceof PlayerEntity && ((PlayerEntity) getOwner()).abilities.invulnerable && !damageSrc.isBypassInvul()
                || damageSrc.isFire() && !level.getGameRules().getBoolean(GameRules.RULE_FIRE_DAMAGE)) {
            return true;
        }

        if (canTakeDamageFrom(damageSrc)) {
            return isInvulnerable();
        }

        return true;
    }


    public boolean canTakeDamageFrom(DamageSource damageSrc) {
        return damageSrc instanceof IStandDamageSource
                || damageSrc instanceof IModdedDamageSource && ((IModdedDamageSource) damageSrc).canHurtStands()
                || damageSrc.getMsgId().contains("stand")
                || damageSrc == DamageSource.ON_FIRE;
    }



    public void setStayClose(boolean stayClose){
        this.entityData.set(STAY_CLOSE,stayClose);
    }

    public boolean getStayClose(){
        return this.entityData.get(STAY_CLOSE);
    }

    public void setShoot(boolean shoot){
        this.entityData.set(SHOOT,shoot);
    }

    public boolean getShoot(){
        return  this.entityData.get(SHOOT);
    }


    @Override
    protected int getExperienceReward(PlayerEntity p_70693_1_) {
        return 0;
    }

    @Override
    protected boolean shouldDropLoot() {
        return false;
    }

    public boolean getSearchPlayer(){
        return this.entityData.get(SEARCH_PLAYER);
    }


    public void setGoingTo(boolean goingTo){
        this.entityData.set(GOINGTO,goingTo);
    }

    public boolean getGoingTo() {
        return this.entityData.get(GOINGTO);
    }


    @Override
    public Team getTeam() {
        if (this.getOwner() != null) {
            LivingEntity user = this.getOwner();
            if (user != null) {
                return user.getTeam();
            }
        }
        return super.getTeam();
    }



    @Override
    public boolean isAlliedTo(Entity entity) {
        if (this.getOwner() != null) {
            LivingEntity user =this.getOwner();
            if (entity == user) {
                return true;
            }
            if (user != null) {
                return entity.isAlliedTo(user);
            }
        }
        return super.isAlliedTo(entity);
    }



}

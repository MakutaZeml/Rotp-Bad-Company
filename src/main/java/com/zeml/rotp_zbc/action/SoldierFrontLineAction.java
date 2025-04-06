package com.zeml.rotp_zbc.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandStatFormulas;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import com.zeml.rotp_zbc.entity.damaging.projectile.BadBulletEntity;
import com.zeml.rotp_zbc.init.InitItems;
import com.zeml.rotp_zbc.init.InitSounds;
import com.zeml.rotp_zbc.init.InitStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoldierFrontLineAction extends StandEntityAction {

    public SoldierFrontLineAction(StandEntityAction.Builder builder){
        super(builder);
    }




    @Override
    public void onHoldTick(World world, LivingEntity user, IStandPower power, int ticksHeld, ActionTarget target, boolean requirementsFulfilled) {
            if(ticksHeld  % 5 ==0 ){
                List<BadSoldierEntity> soldiers = MCUtil.entitiesAround(BadSoldierEntity.class,user,3,false,soldier ->soldier.isAlive() && soldier.getOwner()==user );
                if(!soldiers.isEmpty()){
                    soldiers.forEach(soldier->{
                        if(!world.isClientSide){
                            soldier.yRotO = soldier.yRot = user.yRot;
                            soldier.xRot = soldier.xRotO = user.getViewXRot(1F);
                            BadBulletEntity bullet = new BadBulletEntity(soldier,world);
                            bullet.shootFromRotation(soldier,1.6F,.02F);
                            bullet.withStandSkin(((StandEntity) power.getStandManifestation()).getStandSkin());
                            bullet.setStandOwner(user);
                            world.addFreshEntity(bullet);

                        }else {
                            soldier.setShooting(true);
                            soldier.yRotO = soldier.yRot = user.yRot;
                            soldier.setItemInHand(Hand.MAIN_HAND,new ItemStack(InitItems.BAD_GUN.get()));
                        }
                    });
                    if(world.isClientSide){
                        if(ClientUtil.canSeeStands()){
                            user.playSound(InitSounds.SOLDIER_BARRAGE.get(),1F,1F);
                        }
                    }else {
                        power.consumeStamina(20);
                    }
                }

        }

    }


    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide){
            List<BadSoldierEntity> soldiers = MCUtil.entitiesAround(BadSoldierEntity.class,userPower.getUser(),3,false,soldier ->soldier.isAlive() && soldier.getOwner()==userPower.getUser() );
            if(!soldiers.isEmpty()){
                soldiers.forEach(soldier->{
                    soldier.setShoot(false);
                    if(soldier.distanceTo(userPower.getUser())<1){
                        soldier.addEffect(new EffectInstance(InitStatusEffect.SAT.get(),20,20,false,false,false));
                    }

                });
            }
            List<BadSoldierEntity> winterSoldiers =MCUtil.entitiesAround(BadSoldierEntity.class,userPower.getUser(),50,false,soldier ->soldier.isAlive() && soldier.getOwner()==userPower.getUser() );
            if(!winterSoldiers.isEmpty()){
                winterSoldiers.forEach(soldier->{
                    if(soldier.distanceTo(userPower.getUser())>3){
                        soldier.getNavigation().moveTo(userPower.getUser(),.5F);
                    }
                });
            }
        }

    }



    @Override
    public void stoppedHolding(World world, LivingEntity user, IStandPower power, int ticksHeld, boolean willFire) {
        super.stoppedHolding(world, user, power, ticksHeld, willFire);
        List<BadSoldierEntity> soldiers = MCUtil.entitiesAround(BadSoldierEntity.class,user,3,false,soldier ->soldier.isAlive() && soldier.getOwner()==user );
        if(!soldiers.isEmpty()){
            soldiers.forEach(soldier->soldier.setShoot(true));
        }
    }

    @Override
    public int getHoldDurationMax(IStandPower standPower) {
        LivingEntity user = standPower.getUser();
        if (user != null && user.hasEffect(ModStatusEffects.RESOLVE.get())) {
            return Integer.MAX_VALUE;
        }
        if (standPower.getStandManifestation() instanceof StandEntity) {
            StandEntity standEntity = (StandEntity) standPower.getStandManifestation();
            return StandStatFormulas.getBarrageMaxDuration(standEntity.getDurability());
        }
        return 20;
    }
}

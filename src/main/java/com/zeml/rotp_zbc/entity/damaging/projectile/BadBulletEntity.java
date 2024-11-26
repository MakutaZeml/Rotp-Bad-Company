package com.zeml.rotp_zbc.entity.damaging.projectile;

import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.IndirectStandEntityDamageSource;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import com.zeml.rotp_zbc.entity.stand.stands.BadCompanyStandEntity;
import com.zeml.rotp_zbc.init.InitEntities;
import com.zeml.rotp_zbc.init.InitItems;
import com.zeml.rotp_zbc.util.AddonUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Objects;

public class BadBulletEntity extends BadProjectile {

    public BadBulletEntity(EntityType<? extends BadBulletEntity> type, World world){
        super(type, world);
    }

    public BadBulletEntity(LivingEntity shooter, World world){
        super(InitEntities.BAD_BULLET.get(), shooter,world);
        this.setNoGravity(true);
    }


    float MUL = JojoModConfig.getCommonConfigInstance(false).standDamageMultiplier.get().floatValue();

    @Override
    protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        if(!level.isClientSide){
            Entity target = entityRayTraceResult.getEntity();
            if(this.getStandOwner() != null){
                if(!this.getDeflectedUsingReflection()){
                    if(!this.isAlliedTo(target)){
                        IStandPower.getStandPowerOptional( (LivingEntity) this.getStandOwner()).ifPresent(power -> {
                            target.hurt(new IndirectStandEntityDamageSource("arrow",this,this.getStandOwner(),power), 7*this.getDamageAmount()/8);
                            target.hurt(new IndirectStandEntityDamageSource("arrow",this,this.getStandOwner(),power), this.getDamageAmount()/8);

                        });
                    }
                }else {
                    IStandPower.getStandPowerOptional( (LivingEntity) this.getStandOwner()).ifPresent(power -> {
                        target.hurt(new IndirectStandEntityDamageSource("arrow",this,this.getStandOwner(),power), 7*this.getDamageAmount()/8);
                        target.hurt(new IndirectStandEntityDamageSource("arrow",this,this.getStandOwner(),power), this.getDamageAmount()/8);
                    });
                }
            }else {
                target.hurt(new IndirectEntityDamageSource("arrow",this, this),this.getDamageAmount());
            }
            this.remove();
        }
    }


    @Override
    public int ticksLifespan() {
        return 100;
    }

    @Override
    protected float getBaseDamage() {
        return 5;
    }

    @Override
    protected float getMaxHardnessBreakable() {
        return 0.3F;
    }

    @Override
    public boolean standDamage() {
        return true;
    }




}

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
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BadBulletEntity extends BadProjectile {
    public final List<Vector3d> tracePos = new LinkedList<>();
    public Vector3d initialPos;

    public BadBulletEntity(EntityType<? extends BadBulletEntity> type, World world){
        super(type, world);
    }

    public BadBulletEntity(LivingEntity shooter, World world){
        super(InitEntities.BAD_BULLET.get(), shooter,world);
        this.setNoGravity(true);
    }


    @Override
    public void tick() {
        super.tick();
        if (tickCount == 5) {
            setNoGravity(false);
        }
        if (level.isClientSide()) {
            Vector3d pos = position();
            boolean addPos = true;
            if (tracePos.size() > 1) {
                Vector3d lastPos = tracePos.get(tracePos.size() - 1);
                addPos &= pos.distanceToSqr(lastPos) >= 0.0625;
            }
            if (addPos) {
                tracePos.add(pos);
            }
        }
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
                            this.remove();
                        });
                    }
                }else {
                    IStandPower.getStandPowerOptional( (LivingEntity) this.getStandOwner()).ifPresent(power -> {
                        target.hurt(new IndirectStandEntityDamageSource("arrow",this,this.getStandOwner(),power), 7*this.getDamageAmount()/8);
                        target.hurt(new IndirectStandEntityDamageSource("arrow",this,this.getStandOwner(),power), this.getDamageAmount()/8);
                        this.remove();
                    });
                }
            }else {
                target.hurt(new IndirectEntityDamageSource("arrow",this, this),this.getDamageAmount());
                this.remove();
            }
        }
    }

    @Override
    protected float knockbackMultiplier() {
        return 0.2F;
    }

    @Override
    public int ticksLifespan() {
        return 40;
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

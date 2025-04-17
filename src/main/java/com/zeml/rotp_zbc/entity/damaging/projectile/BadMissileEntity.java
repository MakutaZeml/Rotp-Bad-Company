package com.zeml.rotp_zbc.entity.damaging.projectile;

import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import com.zeml.rotp_zbc.init.InitEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class BadMissileEntity extends BadProjectile {

    public BadMissileEntity(EntityType<BadMissileEntity> badMisileEntityEntityType, World world) {
        super(badMisileEntityEntityType,world);
    }

    public BadMissileEntity(LivingEntity shooter, World world) {
        super(InitEntities.BAD_MISSILE.get(), shooter, world);
        this.setOwner(shooter);
    }



    @Override
    public void tick() {
        super.tick();
        if(level.isClientSide){
            if(ClientUtil.canSeeStands()){
                for(int i=0;i<4;i++){
                    level.addParticle(ParticleTypes.FLAME,this.getX(),this.getY(),this.getZ(),.1*(.5-1F* Math.random()),.1*(.5-1F* Math.random()),.1*(.5-1F* Math.random()));
                }
                for (int i=0; i<6; i++){
                    level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,this.getX(),this.getY(),this.getZ(),.2*(.5-1F* Math.random()),.2*(.5-1F* Math.random()),.2*(.5-1F* Math.random()));
                }
            }
        }
    }



    @Override
    protected void onHitBlock(BlockRayTraceResult blockRayTraceResult) {
        if(!level.isClientSide){
            Explosion.Mode MODE = Explosion.Mode.BREAK;



            Entity source = this.getOwner() == null?this:this.getOwner();
            source =this.getStandOwner() == null? source:this.getStandOwner();

            if(this.getStandOwner() != null){
                LazyOptional<LivingData> livingDataOptional = this.getStandOwner().getCapability(LivingDataProvider.CAPABILITY);


                MODE = livingDataOptional.map(LivingData::isExplosiveMissiles).orElse(true)? Explosion.Mode.BREAK: Explosion.Mode.NONE;
            }

            level.explode(source,this.getX(),this.getY(),this.getZ(),1.75F, MODE);
            this.remove();
        }
    }


    @Override
    protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        if(!level.isClientSide){
            Explosion.Mode MODE = Explosion.Mode.BREAK;
            Entity target = entityRayTraceResult.getEntity();
            if(this.getOwner() != null){
                Entity exploding = this.getStandOwner() == null? this.getOwner():this.getStandOwner();

                if(this.getStandOwner() != null){
                    LazyOptional<LivingData> livingDataOptional = this.getStandOwner().getCapability(LivingDataProvider.CAPABILITY);

                    MODE = livingDataOptional.map(LivingData::isExplosiveMissiles).orElse(true)? Explosion.Mode.BREAK: Explosion.Mode.NONE;
                }

                if(!this.getDeflectedUsingReflection()){
                    if(!this.isAlliedTo(target)){
                        level.explode(exploding,this.getX(),this.getY(),this.getZ(),2, MODE);
                        this.remove();
                    }
                }else {
                    level.explode(exploding,this.getX(),this.getY(),this.getZ(),2, MODE);
                    this.remove();
                }
            }else {
                level.explode(this,this.getX(),this.getY(),this.getZ(),2, MODE);
                this.remove();
            }
        }
    }

    @Override
    public int ticksLifespan() {
        return 100;
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
        return true;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

    }




}

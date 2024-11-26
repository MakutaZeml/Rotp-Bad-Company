package com.zeml.rotp_zbc.init;

import com.zeml.rotp_zbc.RotpBadCompanyAddon;

import com.zeml.rotp_zbc.entity.BadHelicopterEntity;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import com.zeml.rotp_zbc.entity.BadTankEntity;
import com.zeml.rotp_zbc.entity.damaging.projectile.BadBulletEntity;
import com.zeml.rotp_zbc.entity.damaging.BadMineEntity;

import com.zeml.rotp_zbc.entity.damaging.projectile.BadMissileEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = RotpBadCompanyAddon.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =DeferredRegister.create(ForgeRegistries.ENTITIES, RotpBadCompanyAddon.MOD_ID);

    public static final RegistryObject<EntityType<BadBulletEntity>> BAD_BULLET = ENTITIES.register("bad_bullet",
            ()->EntityType.Builder.<BadBulletEntity>of(BadBulletEntity::new, EntityClassification.MISC).sized(0.2F,0.6F)
                    .setUpdateInterval(2).build((new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bad_bullet").toString())));

    public static final RegistryObject<EntityType<BadMissileEntity>> BAD_MISSILE = ENTITIES.register("bad_missile",
            ()->EntityType.Builder.<BadMissileEntity>of(BadMissileEntity::new, EntityClassification.MISC).sized(0.2F,0.6F)
                    .setUpdateInterval(2).build((new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bad_missile").toString())));

    public static final RegistryObject<EntityType<BadMineEntity>> BAD_MINE = ENTITIES.register("bad_mine",
            ()->EntityType.Builder.<BadMineEntity>of(BadMineEntity::new, EntityClassification.MISC).sized(0.2F,0.0416F)
                    .setUpdateInterval(2).build((new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bad_mine").toString())));

    public static final RegistryObject<EntityType<BadSoldierEntity>> BAD_SOLDIER = ENTITIES.register("bad_soldier",
            ()->EntityType.Builder.<BadSoldierEntity>of(BadSoldierEntity::new, EntityClassification.MISC).sized(0.2F,0.6F)
                    .setUpdateInterval(2).build((new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bad_soldier").toString())));

    public static final RegistryObject<EntityType<BadTankEntity>> BAD_TANK = ENTITIES.register("bad_tank",
            ()->EntityType.Builder.<BadTankEntity>of(BadTankEntity::new, EntityClassification.MISC).sized(1F,0.5F)
                    .setUpdateInterval(2).build((new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bad_tank").toString())));
    public static final RegistryObject<EntityType<BadHelicopterEntity>> BAD_HELICOPTER = ENTITIES.register("bad_helicopter",
            ()->EntityType.Builder.<BadHelicopterEntity>of(BadHelicopterEntity::new, EntityClassification.MISC).sized(1F,0.4F)
                    .setUpdateInterval(2).build((new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bad_helicopter").toString())));




}

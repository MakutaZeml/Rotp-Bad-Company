package com.zeml.rotp_zbc.init;

import com.github.standobyte.jojo.init.ModSounds;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.github.standobyte.jojo.util.mc.OstSoundList;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RotpBadCompanyAddon.MOD_ID);

    public static final RegistryObject<SoundEvent> BAD_COMPANY_SUMMON = ModSounds.STAND_SUMMON_DEFAULT;

    public static final RegistryObject<SoundEvent> VOID =SOUNDS.register("void",
            ()->new SoundEvent(new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"void")));

    public static final RegistryObject<SoundEvent> BAD_COMPANY_UNSUMMON = ModSounds.STAND_UNSUMMON_DEFAULT;

    public static final RegistryObject<SoundEvent> SOLDIER_STEP =SOUNDS.register("bc_soldier_step",
            ()->new SoundEvent(new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bc_soldier_step")));
    public static final RegistryObject<SoundEvent> SOLDIER_SHOT =SOUNDS.register("bc_soldier_shoot",
            ()->new SoundEvent(new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bc_soldier_shoot")));
    public static final RegistryObject<SoundEvent> SOLDIER_BARRAGE =SOUNDS.register("bc_barrage",
            ()->new SoundEvent(new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bc_barrage")));
    public static final RegistryObject<SoundEvent> TANK_SHOT =SOUNDS.register("bc_tank_shoot",
            ()->new SoundEvent(new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bc_tank_shoot")));
    public static final RegistryObject<SoundEvent> TANK_HURT =SOUNDS.register("bc_tank_hurt",
            ()->new SoundEvent(new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bc_tank_hurt")));
    public static final RegistryObject<SoundEvent> TANK_STEP =SOUNDS.register("bc_tank_step",
            ()->new SoundEvent(new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bc_tank_step")));
    public static final RegistryObject<SoundEvent> TANK_LIVING =SOUNDS.register("bc_tank_living",
            ()->new SoundEvent(new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bc_tank_living")));
    public static final RegistryObject<SoundEvent> COPTER =SOUNDS.register("bc_copter",
            ()->new SoundEvent(new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"bc_copter")));
    public static final RegistryObject<SoundEvent> BAD_COMPANY_USER = SOUNDS.register("keicho_bad_company",
            ()->new SoundEvent(new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "keicho_bad_company"))
            );



    static final OstSoundList BAD_COMPANY_OST = new OstSoundList(new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "bad_company_ost"), SOUNDS);
}

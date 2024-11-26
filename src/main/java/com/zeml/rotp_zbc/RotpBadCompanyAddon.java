package com.zeml.rotp_zbc;

import com.zeml.rotp_zbc.capability.entity.CapabilityHandler;
import com.zeml.rotp_zbc.init.*;
import com.zeml.rotp_zbc.network.AddonPackets;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RotpBadCompanyAddon.MOD_ID)
public class RotpBadCompanyAddon {
    // The value here should match an entry in the META-INF/mods.toml file
    public static final String MOD_ID = "rotp_zbc";
    public static final Logger LOGGER = LogManager.getLogger();

    public RotpBadCompanyAddon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitParticles.PARTICLES.register(modEventBus);
        InitStatusEffect.EFFECTS.register(modEventBus);

        IntTags.iniTags();

        modEventBus.addListener(this::preInit);
    }


    private void preInit(FMLCommonSetupEvent event){
        AddonPackets.init();
        CapabilityHandler.commonSetupRegister();
    }
    public static Logger getLogger() {
        return LOGGER;
    }
}

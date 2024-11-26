package com.zeml.rotp_zbc.init;

import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.potion.OrderToStayEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = RotpBadCompanyAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitStatusEffect {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, RotpBadCompanyAddon.MOD_ID);

    public static final RegistryObject<OrderToStayEffect> SAT = EFFECTS.register("sat",
            () -> new OrderToStayEffect(0x237841));

}

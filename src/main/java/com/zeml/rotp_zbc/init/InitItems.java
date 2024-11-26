package com.zeml.rotp_zbc.init;

import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RotpBadCompanyAddon.MOD_ID);

    public static final RegistryObject<Item> BAD_GUN = ITEMS.register("bad_gun", () -> new Item(new Item.Properties().stacksTo(1)));

}

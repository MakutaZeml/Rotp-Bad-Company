package com.zeml.rotp_zbc.client;

import com.github.standobyte.jojo.client.ClientUtil;
import com.zeml.rotp_zbc.client.render.entity.renderer.BadHelicopterRenderer;
import com.zeml.rotp_zbc.client.render.entity.renderer.BadSoldierRenderer;
import com.zeml.rotp_zbc.client.render.entity.renderer.BadTankRenderer;
import com.zeml.rotp_zbc.client.render.entity.renderer.projectile.BadBulletRenderer;
import com.zeml.rotp_zbc.client.render.entity.renderer.projectile.BadMineRenderer;
import com.zeml.rotp_zbc.client.render.entity.renderer.projectile.BadMissileRenderer;
import com.zeml.rotp_zbc.client.render.entity.renderer.stand.BadCompanyStandRenderer;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.entity.damaging.projectile.BadBulletEntity;
import com.zeml.rotp_zbc.init.AddonStands;

import com.zeml.rotp_zbc.init.InitEntities;
import com.zeml.rotp_zbc.init.InitItems;
import net.minecraft.client.Minecraft;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RotpBadCompanyAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {

    private static final IItemPropertyGetter STAND_ITEM_INVISIBLE = (itemStack, clientWorld, livingEntity) -> {
        return !ClientUtil.canSeeStands() ? 1 : 0;
    };

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        Minecraft mc = event.getMinecraftSupplier().get();;

        RenderingRegistry.registerEntityRenderingHandler(AddonStands.BAD_COMPANY.getEntityType(), BadCompanyStandRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.BAD_SOLDIER.get(), BadSoldierRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.BAD_BULLET.get(), BadBulletRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.BAD_MISSILE.get(), BadMissileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.BAD_TANK.get(), BadTankRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.BAD_MINE.get(), BadMineRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.BAD_HELICOPTER.get(), BadHelicopterRenderer::new);

        /*Delete all the event.enqueueWork stuff if the Stand it's visible to no-stand Users like Cream Starter*/
        event.enqueueWork(() -> {
            ItemModelsProperties.register(InitItems.BAD_GUN.get(),
                    new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "stand_invisible"),
                    STAND_ITEM_INVISIBLE);
        });

    }


    @SubscribeEvent
    public static void onMcConstructor(ParticleFactoryRegisterEvent event){
        Minecraft mc = Minecraft.getInstance();


    }

}

package com.zeml.rotp_zbc.client.ui.screen;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.init.InitStands;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RotpBadCompanyAddon.MOD_ID,bus =  Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class AerialScreen {
    protected static final ResourceLocation SCREEN = new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"textures/gui/remote.png");


    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event){
        MatrixStack matrixStack = event.getMatrixStack();
        if(event.getType() == RenderGameOverlayEvent.ElementType.HELMET){
            Minecraft mc = Minecraft.getInstance();
            if(mc.player.isAlive()){
                StandType<?> BC = InitStands.STAND_BAD_COMPANY.getStandType();
                StandEntity entity =  (StandEntity) IStandPower.getPlayerStandPower(mc.player).getStandManifestation();

                if(IStandPower.getPlayerStandPower(mc.player).getType() == BC && entity != null && entity.isManuallyControlled()){
                    mc.getTextureManager().bind(SCREEN);
                    int textureWidth = event.getWindow().getGuiScaledWidth();
                    int textureHeight = event.getWindow().getGuiScaledHeight();
                    AbstractGui.blit(matrixStack, 0, 0, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);

                }
            }
        }
    }

}

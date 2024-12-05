package com.zeml.rotp_zbc.client.ui.screen;

import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.concurrent.atomic.AtomicReference;

public class FormationIcon {
    private static ResourceLocation RANDOM = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/action/random.png");
    private static ResourceLocation CIRCLE = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/action/circle.png");
    private static ResourceLocation LINE = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/action/line.png");
    private static final ResourceLocation ARROW = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/action/arrow.png");

    public static ResourceLocation getIconByType(FormationType type){
        switch (type){
            case CIRCLE:
                return CIRCLE;
            case LINE:
                return LINE;
            default:
                return RANDOM;
        }
    }

    public static void renderIcon(FormationType type, MatrixStack matrixStack, float x, float y){
        AtomicReference<ResourceLocation> icon = new AtomicReference<>(getIconByType(type));
        IStandPower.getStandPowerOptional(Minecraft.getInstance().player).ifPresent(power -> {
            icon.set(StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                    .getStandSkin(power.getStandInstance().get()), icon.get()));
        });
        Minecraft.getInstance().getTextureManager().bind(icon.get());
        BlitFloat.blitFloat(matrixStack, x, y, 0, 0, 16, 16, 16, 16);
    }

}

package com.zeml.rotp_zbc.client.ui.screen;

import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FormationIcon {
    private static ResourceLocation RANDOM = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/action/random.png");
    private static ResourceLocation CIRCLE = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/action/circle.png");
    private static ResourceLocation LINE = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/action/line.png");
    private static final ResourceLocation ARROW = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/action/arrow.png");

    public static ResourceLocation getIconByType(FormationType type){
        switch (type){
            case CIRCLE:
                IStandPower.getStandPowerOptional(Minecraft.getInstance().player).ifPresent(power -> {
                    CIRCLE = StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                            .getStandSkin(power.getStandInstance().get()), CIRCLE);
                });
                return CIRCLE;
            case LINE:
                IStandPower.getStandPowerOptional(Minecraft.getInstance().player).ifPresent(power -> {
                    LINE = StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                            .getStandSkin(power.getStandInstance().get()), LINE);
                });
                return LINE;
            default:
                IStandPower.getStandPowerOptional(Minecraft.getInstance().player).ifPresent(power -> {
                    RANDOM = StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                            .getStandSkin(power.getStandInstance().get()), RANDOM);
                });
                return RANDOM;
        }
    }

    public static void renderIcon(FormationType type, MatrixStack matrixStack, float x, float y){
        ResourceLocation icon = getIconByType(type);
        Minecraft.getInstance().getTextureManager().bind(icon);
        BlitFloat.blitFloat(matrixStack, x, y, 0, 0, 16, 16, 16, 16);
    }

}

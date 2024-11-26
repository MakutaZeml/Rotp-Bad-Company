package com.zeml.rotp_zbc.client.ui.screen;

import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class UnitIcon {
    private static ResourceLocation ALL = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/power/bad_company.png");
    private static ResourceLocation SOLDIER = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/action/soldier.png");
    private static ResourceLocation TANK = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/action/tank.png");
    private static ResourceLocation HELICOPTER =new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/action/copter.png");

    public static ResourceLocation getIconByType(UnitType type){
        switch (type){
            case SOLDIER:
                IStandPower.getStandPowerOptional(Minecraft.getInstance().player).ifPresent(power -> {
                    SOLDIER = StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                            .getStandSkin(power.getStandInstance().get()), SOLDIER);
                });
                return SOLDIER;
            case TANK:
                IStandPower.getStandPowerOptional(Minecraft.getInstance().player).ifPresent(power -> {
                    TANK = StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                            .getStandSkin(power.getStandInstance().get()), TANK);
                });
                return TANK;
            case HELICOPTER:
                IStandPower.getStandPowerOptional(Minecraft.getInstance().player).ifPresent(power -> {
                    HELICOPTER = StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                            .getStandSkin(power.getStandInstance().get()), HELICOPTER);
                });
                return HELICOPTER;
            default:
                IStandPower.getStandPowerOptional(Minecraft.getInstance().player).ifPresent(power -> {
                    ALL = StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                            .getStandSkin(power.getStandInstance().get()), ALL);
                });
                return ALL;
        }
    }

    public static void renderIcon(UnitType type, MatrixStack matrixStack, float x, float y){
        ResourceLocation icon = getIconByType(type);
        Minecraft.getInstance().getTextureManager().bind(icon);
        BlitFloat.blitFloat(matrixStack, x, y, 0, 0, 16, 16, 16, 16);
    }
}

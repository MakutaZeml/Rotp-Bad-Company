package com.zeml.rotp_zbc.client.render.entity.renderer;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.client.render.entity.model.BadHelicopterModel;
import com.zeml.rotp_zbc.client.render.entity.model.BadTankModel;
import com.zeml.rotp_zbc.entity.BadHelicopterEntity;
import com.zeml.rotp_zbc.entity.BadTankEntity;
import com.zeml.rotp_zbc.init.InitStands;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BadHelicopterRenderer extends MobRenderer<BadHelicopterEntity, BadHelicopterModel> {
    protected static final ResourceLocation TEXTURE =  new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"textures/entity/stand/helicopter.png");
    protected static final ResourceLocation VOID = new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"/textures/entity/stand/void.png");
    protected static ResourceLocation SOLDIER = TEXTURE;

    public BadHelicopterRenderer(EntityRendererManager rendererManager){
        super(rendererManager,new BadHelicopterModel(),0F);

    }

    @Override
    public void render(BadHelicopterEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        matrixStack.pushPose();
        super.render(p_225623_1_, p_225623_2_, p_225623_3_, matrixStack, p_225623_5_, p_225623_6_);
        matrixStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(BadHelicopterEntity p_110775_1_) {
        if(p_110775_1_.getOwner() != null){
            IStandPower.getStandPowerOptional(p_110775_1_.getOwner()).ifPresent(power -> {
                StandType<?> KQ = InitStands.STAND_BAD_COMPANY.getStandType();
                if(power.getType() == KQ){
                    SOLDIER = StandSkinsManager.getInstance() != null? (StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                            .getStandSkin(power.getStandInstance().get()), TEXTURE)): TEXTURE ;
                }else {
                    SOLDIER = TEXTURE;
                }
            });
        }
        return ClientUtil.canSeeStands() ?SOLDIER : VOID;
    }
}

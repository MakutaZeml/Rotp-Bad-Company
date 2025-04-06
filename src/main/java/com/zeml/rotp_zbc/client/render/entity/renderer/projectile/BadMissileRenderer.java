package com.zeml.rotp_zbc.client.render.entity.renderer.projectile;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.client.render.entity.model.projectile.BadMissileModel;
import com.zeml.rotp_zbc.entity.damaging.projectile.BadMissileEntity;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BadMissileRenderer extends SimpleEntityRenderer<BadMissileEntity, BadMissileModel> {
    public BadMissileRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BadMissileModel(), new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/entity/damaging/misile.png"));
    }

    @Override
    public boolean shouldRender(BadMissileEntity p_225626_1_, ClippingHelper p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return ClientUtil.canSeeStands() && super.shouldRender(p_225626_1_, p_225626_2_, p_225626_3_, p_225626_5_, p_225626_7_);
    }
}

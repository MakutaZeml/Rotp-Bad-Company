package com.zeml.rotp_zbc.client.render.entity.renderer.stand;

import com.zeml.rotp_zbc.client.render.entity.model.stand.BadCompanyStandModel;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.entity.stand.stands.BadCompanyStandEntity;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BadCompanyStandRenderer extends StandEntityRenderer<BadCompanyStandEntity, BadCompanyStandModel> {

    public BadCompanyStandRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BadCompanyStandModel(), new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/entity/stand/bad_company.png"), 0);
    }

}

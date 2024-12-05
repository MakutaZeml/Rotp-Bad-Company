package com.zeml.rotp_zbc.client.render.entity.renderer;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.client.render.entity.model.BadSoldierModel;
import com.zeml.rotp_zbc.client.render.entity.renderer.layer.ParachuteLayer;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import com.zeml.rotp_zbc.init.InitStands;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BadSoldierRenderer extends MobRenderer<BadSoldierEntity, BadSoldierModel<BadSoldierEntity>> {
    protected static final ResourceLocation TEXTURE =  new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"textures/entity/stand/soldier.png");
    protected static final ResourceLocation VOID = new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"/textures/entity/stand/void.png");
    protected static ResourceLocation SOLDIER = TEXTURE;

    public BadSoldierRenderer(EntityRendererManager rendererManager){
        super(rendererManager,new BadSoldierModel<>(),0F);
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new ParachuteLayer(this));
    }



    @Override
    public ResourceLocation getTextureLocation(BadSoldierEntity p_110775_1_) {
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

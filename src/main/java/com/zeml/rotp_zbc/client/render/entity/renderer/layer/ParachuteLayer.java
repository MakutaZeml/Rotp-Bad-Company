package com.zeml.rotp_zbc.client.render.entity.renderer.layer;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.client.render.entity.model.BadSoldierModel;
import com.zeml.rotp_zbc.client.render.entity.model.layer.ParachuteLayerModel;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParachuteLayer extends LayerRenderer<BadSoldierEntity, BadSoldierModel<BadSoldierEntity>> {
    public ParachuteLayer(IEntityRenderer<BadSoldierEntity, BadSoldierModel<BadSoldierEntity>> p_i50926_1_) {
        super(p_i50926_1_);
    }

    private final ParachuteLayerModel<BadSoldierEntity> parachuteLayerModel = new ParachuteLayerModel<>();
    private static final ResourceLocation TEXTURE = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/entity/stand/parachute.png");


    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, BadSoldierEntity entity,
                       float limbSwing, float limbSwingAmount, float partialTicks, float ticks, float yRot, float xRot) {
        if (!ClientUtil.canSeeStands() || entity.isInvisible()) {
            return;
        }

        if(entity.getArmPose() == BadSoldierEntity.ArmPose.GUN_PARACHUTE || entity.getArmPose() == BadSoldierEntity.ArmPose.PARACHUTE){
            BadSoldierModel<BadSoldierEntity> soldierModel = getParentModel();
            parachuteLayerModel.prepareMobModel(entity,limbSwing,limbSwingAmount,partialTicks);
            soldierModel.copyPropertiesTo(parachuteLayerModel);
            parachuteLayerModel.setupAnim(entity, limbSwing, limbSwingAmount, ticks, yRot, xRot);
            ResourceLocation texture = TEXTURE;
            if(entity.getOwner() != null){
                LivingEntity owner = entity.getOwner();
                IStandPower stand = IStandPower.getStandPowerOptional(owner).orElse(null);
                if(stand != null){
                    texture = StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                            .getStandSkin(stand.getStandInstance().orElse(null)), TEXTURE);
                }
            }
            IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), false, false);
            parachuteLayerModel.renderToBuffer(matrixStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
    }




}

package com.zeml.rotp_zbc.client.render.entity.renderer.projectile;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.client.render.entity.model.projectile.BadBulletModel;
import com.zeml.rotp_zbc.client.render.entity.model.projectile.BadMineModel;
import com.zeml.rotp_zbc.entity.damaging.BadMineEntity;
import com.zeml.rotp_zbc.entity.damaging.projectile.BadBulletEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BadMineRenderer extends SimpleEntityRenderer<BadMineEntity, BadMineModel> {

    public BadMineRenderer(EntityRendererManager rendererManager){
        super(rendererManager,new BadMineModel(), new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/entity/damaging/bad_mine.png"));
    }


    @Override
    protected void renderModel(BadMineEntity entity, BadMineModel model, float partialTick, MatrixStack matrixStack, IVertexBuilder vertexBuilder, int packedLight) {
        matrixStack.pushPose();
        matrixStack.scale(.3333F,.3333F,.3333F);
        super.renderModel(entity, model, partialTick, matrixStack, vertexBuilder, packedLight);
        matrixStack.popPose();
    }
}

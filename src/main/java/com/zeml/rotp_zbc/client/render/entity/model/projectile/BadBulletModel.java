package com.zeml.rotp_zbc.client.render.entity.model.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zbc.entity.damaging.projectile.BadBulletEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BadBulletModel extends EntityModel<BadBulletEntity> {

    public BadBulletModel(){
        texWidth = 16;
        texHeight = 16;

    }


    @Override
    public void setupAnim(BadBulletEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
    }
}

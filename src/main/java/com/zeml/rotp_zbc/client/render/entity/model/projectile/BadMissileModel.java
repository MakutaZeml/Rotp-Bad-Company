package com.zeml.rotp_zbc.client.render.entity.model.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zbc.entity.damaging.projectile.BadMissileEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BadMissileModel extends EntityModel<BadMissileEntity> {
    private final ModelRenderer bb_main;

    public BadMissileModel(){
        texWidth = 16;
        texHeight = 16;

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 0.0F, 0.0F);
        bb_main.texOffs(0, 0).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
        bb_main.texOffs(0, 5).addBox(-0.5F, -1.5F, -2.0F, 1.0F, 1.0F, 3.0F, 0.3F, false);
        bb_main.texOffs(5, 5).addBox(-1.0F, -2.0F, 1.2F, 2.0F, 2.0F, 1.0F, 0.0F, false);
    }
    @Override
    public void setupAnim(BadMissileEntity entity, float walkAnimPos, float walkAnimSpeed, float ticks, float yRotationOffset, float xRotation) {
        bb_main.yRot = yRotationOffset * ((float)Math.PI / 180F);
        bb_main.xRot = xRotation * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}

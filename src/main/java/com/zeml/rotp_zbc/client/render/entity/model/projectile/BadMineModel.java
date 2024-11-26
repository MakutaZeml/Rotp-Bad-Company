package com.zeml.rotp_zbc.client.render.entity.model.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zbc.entity.damaging.BadMineEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BadMineModel extends EntityModel<BadMineEntity> {
    private final ModelRenderer mine;
    private final ModelRenderer cube_r1;
    public BadMineModel(){
        texWidth = 32;
        texHeight = 32;

        mine = new ModelRenderer(this);
        mine.setPos(0.0F, 0.0F, 0.0F);
        mine.texOffs(0, 0).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, false);
        mine.texOffs(12, 14).addBox(-2.0F, -1.0F, -5.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);
        mine.texOffs(0, 14).addBox(-2.0F, -1.0F, 3.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);
        mine.texOffs(8, 9).addBox(3.0F, -1.0F, -2.0F, 2.0F, 1.0F, 4.0F, 0.0F, false);
        mine.texOffs(0, 8).addBox(-5.0F, -1.0F, -2.0F, 2.0F, 1.0F, 4.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(0.0F, 0.0F, 0.0F);
        mine.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, -0.7854F, 0.0F);
        cube_r1.texOffs(16, 8).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.4F, false);
    }

    @Override
    public void setupAnim(BadMineEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        mine.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}

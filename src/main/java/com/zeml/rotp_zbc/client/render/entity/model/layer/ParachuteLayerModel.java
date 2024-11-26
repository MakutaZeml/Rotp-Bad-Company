package com.zeml.rotp_zbc.client.render.entity.model.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zbc.client.render.entity.model.BadSoldierModel;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ParachuteLayerModel<T extends BadSoldierEntity> extends BadSoldierModel<T> {

    private final ModelRenderer BackPack;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;

    public ParachuteLayerModel() {
        texWidth = 128;
        texHeight = 128;


        torso.setPos(0.0F, 0.0F, 0.0F);
        torso.texOffs(0, 46).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
        torso.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);

        BackPack = new ModelRenderer(this);
        BackPack.setPos(0.0F, 0.0F, 0.0F);
        torso.addChild(BackPack);
        BackPack.texOffs(24, 46).addBox(-5.0F, 7.0F, 2.0F, 10.0F, 5.0F, 5.0F, 0.0F, false);
        BackPack.texOffs(54, 46).addBox(-4.5F, 0.0F, 2.0F, 9.0F, 7.0F, 4.0F, 0.1F, false);
        BackPack.texOffs(104, 0).addBox(-4.0F, 9.0F, -2.0F, 8.0F, 1.0F, 4.0F, 0.1F, false);
        BackPack.texOffs(-3, -1).addBox(-3.0F, 8.0F, -5.0F, 6.0F, 4.0F, 3.0F, 0.0F, false);
        BackPack.texOffs(0, 101).addBox(-4.0F, 2.0F, -2.0F, 2.0F, 7.0F, 1.0F, 0.1F, false);
        BackPack.texOffs(0, 72).addBox(2.0F, 2.0F, -2.0F, 2.0F, 7.0F, 1.0F, 0.1F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-3.0F, 2.0F, -1.0F);
        BackPack.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.2898F, 0.1975F, -0.582F);
        cube_r1.texOffs(90, 0).addBox(-1.0F, -13.0F, -1.0F, 2.0F, 13.0F, 0.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-4.0F, 2.0F, 3.0F);
        BackPack.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.4084F, -0.5843F, -0.0961F);
        cube_r2.texOffs(90, 0).addBox(-1.0F, -13.0F, -1.0F, 2.0F, 13.0F, 0.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(4.0F, 2.0F, 3.0F);
        BackPack.addChild(cube_r3);
        setRotationAngle(cube_r3, -0.4084F, 0.5843F, 0.0961F);
        cube_r3.texOffs(90, 0).addBox(-1.0F, -13.0F, -1.0F, 2.0F, 13.0F, 0.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(3.0F, 2.0F, -1.0F);
        BackPack.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.2898F, -0.1975F, 0.582F);
        cube_r4.texOffs(90, 0).addBox(-1.0F, -13.0F, -1.0F, 2.0F, 13.0F, 0.0F, 0.0F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(0.0F, -18.0F, 0.0F);
        BackPack.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, -0.7854F, 0.0F);
        cube_r5.texOffs(0, 0).addBox(-10.0F, -10.0F, -10.0F, 20.0F, 10.0F, 20.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(BadSoldierEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        BackPack.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}

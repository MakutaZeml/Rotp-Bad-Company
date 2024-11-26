package com.zeml.rotp_zbc.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zbc.entity.BadTankEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BadTankModel extends EntityModel<BadTankEntity> {
    private final ModelRenderer main;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cabina;
    private final ModelRenderer cannon;
    public BadTankModel(){
        texWidth = 512;
        texHeight = 512;

        main = new ModelRenderer(this);
        main.setPos(0.0F, 24.0F, 0.0F);
        main.texOffs(0, 0).addBox(-24.0F, -32.0F, -43.0F, 48.0F, 22.0F, 86.0F, 0.0F, false);
        main.texOffs(184, 236).addBox(-30.0F, -32.0F, -43.0F, 6.0F, 22.0F, 86.0F, 0.0F, false);
        main.texOffs(240, 118).addBox(-27.0F, -10.0F, -43.0F, 10.0F, 10.0F, 86.0F, 0.0F, false);
        main.texOffs(182, 22).addBox(17.0F, -10.0F, -43.0F, 10.0F, 10.0F, 86.0F, 0.0F, false);
        main.texOffs(120, 122).addBox(17.0F, -24.0F, -48.0F, 10.0F, 14.0F, 100.0F, 0.0F, false);
        main.texOffs(0, 108).addBox(-27.0F, -24.0F, -48.0F, 10.0F, 14.0F, 100.0F, 0.0F, false);
        main.texOffs(0, 222).addBox(24.0F, -32.0F, -43.0F, 6.0F, 22.0F, 86.0F, 0.0F, false);
        main.texOffs(0, 40).addBox(15.0F, -40.0F, 22.0F, 9.0F, 8.0F, 32.0F, 0.0F, false);
        main.texOffs(0, 131).addBox(15.0F, -37.0F, 4.0F, 9.0F, 5.0F, 18.0F, 0.0F, false);
        main.texOffs(38, 113).addBox(-24.0F, -37.0F, 4.0F, 9.0F, 5.0F, 18.0F, 0.0F, false);
        main.texOffs(0, 0).addBox(-24.0F, -40.0F, 22.0F, 9.0F, 8.0F, 32.0F, 0.0F, false);
        main.texOffs(288, 50).addBox(-24.0F, -32.0F, 41.0F, 48.0F, 13.0F, 12.0F, 0.0F, false);
        main.texOffs(282, 287).addBox(-24.0F, -32.0F, -52.0F, 48.0F, 13.0F, 12.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-29.0F, -30.0F, -42.0F);
        main.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.8727F, 0.0F, 0.0F);
        cube_r1.texOffs(0, 154).addBox(-1.0F, -2.0F, -14.0F, 8.0F, 1.0F, 15.0F, 0.0F, false);
        cube_r1.texOffs(147, 154).addBox(51.0F, -2.0F, -14.0F, 8.0F, 1.0F, 15.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(23.0F, -26.0F, -43.0F);
        main.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.7854F, 0.0F, 0.0F);
        cube_r2.texOffs(288, 0).addBox(-47.0F, -2.0F, -1.0F, 48.0F, 13.0F, 12.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(23.0F, -26.0F, 43.0F);
        main.addChild(cube_r3);
        setRotationAngle(cube_r3, -0.7854F, 0.0F, 0.0F);
        cube_r3.texOffs(288, 25).addBox(-47.0F, -2.0F, -1.0F, 48.0F, 13.0F, 12.0F, 0.0F, false);

        cabina = new ModelRenderer(this);
        cabina.setPos(0.0F, 0.0F, 0.0F);
        main.addChild(cabina);
        cabina.texOffs(282, 236).addBox(-14.0F, -53.0F, -17.0F, 28.0F, 21.0F, 30.0F, 0.0F, false);
        cabina.texOffs(120, 108).addBox(-8.0F, -47.0F, -27.0F, 16.0F, 15.0F, 10.0F, 0.0F, false);
        cabina.texOffs(0, 108).addBox(-8.0F, -47.0F, 13.0F, 16.0F, 11.0F, 12.0F, 0.0F, false);
        cabina.texOffs(120, 149).addBox(-6.0F, -44.0F, -36.0F, 12.0F, 11.0F, 9.0F, 0.0F, false);
        cabina.texOffs(45, 145).addBox(-8.0F, -47.0F, 25.0F, 16.0F, 5.0F, 9.0F, 0.0F, false);
        cabina.texOffs(120, 133).addBox(-10.0F, -49.0F, 34.0F, 20.0F, 8.0F, 8.0F, 0.0F, false);

        cannon = new ModelRenderer(this);
        cannon.setPos(0.0F, -39.0F, -34.0F);
        cabina.addChild(cannon);
        cannon.texOffs(98, 236).addBox(-2.0F, -3.0F, -58.0F, 4.0F, 4.0F, 59.0F, 0.0F, false);
        cannon.texOffs(0, 0).addBox(-2.0F, -3.0F, -28.0F, 4.0F, 4.0F, 11.0F, 0.4F, false);
    }

    @Override
    public void setupAnim(BadTankEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        this.cabina.yRot = p_225597_5_ * ((float)Math.PI / 180F);
        this.cannon.xRot =  p_225597_6_ * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        matrixStack.translate(0,1.25,0);
        matrixStack.scale(.16666F,.16666F,.16666F);
        main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }



    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
